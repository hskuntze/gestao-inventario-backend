package br.com.ctcea.gestaoinv.excel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.ctcea.gestaoinv.components.GeradorIDPatrimonial;
import br.com.ctcea.gestaoinv.entities.Area;
import br.com.ctcea.gestaoinv.entities.Fornecedor;
import br.com.ctcea.gestaoinv.entities.Intangivel;
import br.com.ctcea.gestaoinv.entities.Localizacao;
import br.com.ctcea.gestaoinv.entities.UsuarioResponsavel;
import br.com.ctcea.gestaoinv.enums.Categoria;
import br.com.ctcea.gestaoinv.enums.TermoParceria;
import br.com.ctcea.gestaoinv.exceptions.RecursoNaoEncontradoException;
import br.com.ctcea.gestaoinv.repositories.AreaRepository;
import br.com.ctcea.gestaoinv.repositories.FornecedorRepository;
import br.com.ctcea.gestaoinv.repositories.LocalizacaoRepository;
import br.com.ctcea.gestaoinv.repositories.UsuarioResponsavelRepository;
import br.com.ctcea.gestaoinv.services.UsuarioService;

@Component
public class IntangivelExcelReader implements AtivoExcelReader<Intangivel> {

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private LocalizacaoRepository localizacaoRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private UsuarioResponsavelRepository usuarioResponsavelRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private GeradorIDPatrimonial geradorId;

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Override
	public List<Intangivel> read(MultipartFile file) throws IOException {
		List<Intangivel> lista = new ArrayList<>();

		// FAZER A PROGRAMAÇÃO DEFENSIVA
		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);

			TermoParceria tp = usuarioService.getAuthenticatedUser().getTermoParceria();

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				Intangivel t = new Intangivel();

				String idPatrimonial = getContent(row, 0);

				if (idPatrimonial.equals("N/A")) {
					t.setGerarIdPatrimonial(true);
					t.setIdPatrimonial(geradorId.generate());
				} else {
					t.setIdPatrimonial(idPatrimonial);
				}

				t.setCategoria(Categoria.fromTipo(getContent(row, 1)));
				t.setDescricao(getContent(row, 2));

				String nomeArea = getContent(row, 3);
				if(nomeArea.isBlank()) {
					t.setArea(null);
				} else {
					Area area = areaRepository.findByNome(nomeArea).orElseThrow(() -> new RecursoNaoEncontradoException("Não possível encontrar uma área com nome " + nomeArea));
					t.setArea(area);
				}

				String nomeLoc = getContent(row, 4);
				Localizacao loc;
				if (!nomeArea.isBlank() && nomeLoc.equals("Vinculado ao perfil do usuário")) {
					loc = localizacaoRepository.findByNomeAndArea(nomeLoc, nomeArea).orElseThrow(
							() -> new RecursoNaoEncontradoException("Não possível encontrar uma localização com nome "
									+ nomeLoc + " da área " + nomeArea));
				} else if (nomeArea.isBlank() && !nomeLoc.isBlank()
						&& nomeLoc.equals("Vinculado ao perfil do usuário")) {
					loc = localizacaoRepository.findByNomeAndArea(nomeLoc, null)
							.orElseThrow(() -> new RecursoNaoEncontradoException(
									"Não possível encontrar uma localização com nome " + nomeLoc));
				} else if (!nomeLoc.isBlank()) {
					loc = localizacaoRepository.findByNome(nomeLoc).orElseThrow(() -> new RecursoNaoEncontradoException(
							"Não possível encontrar uma localização com nome " + nomeLoc));
				} else {
					loc = null;
				}
				t.setLocalizacao(loc);

				String nomeFornecedor = getContent(row, 5);
				Fornecedor fornecedor = fornecedorRepository.findByNome(nomeFornecedor)
						.orElseThrow(() -> new RecursoNaoEncontradoException(
								"Não possível encontrar um fornecedor com nome " + nomeFornecedor));
				t.setFornecedor(fornecedor);

				String dataAquisicaoTexto = getContent(row, 6);
				LocalDate dataAquisicao = LocalDate.parse(dataAquisicaoTexto, FORMATTER);
				t.setDataAquisicao(dataAquisicao);

				t.setCodigoSerie(getContent(row, 7));

				String nomeUsuarioResponsavel = getContent(row, 8);
				UsuarioResponsavel usuarioResponsavel = usuarioResponsavelRepository.findByNome(nomeUsuarioResponsavel)
						.orElseThrow(() -> new RecursoNaoEncontradoException(
								"Não foi possível encontrar um usuário responsável com nome "
										+ nomeUsuarioResponsavel));
				t.setUsuarioResponsavel(usuarioResponsavel);

				t.setObservacoes(getContent(row, 9));
				t.setTermoParceria(tp);

				lista.add(t);
			}
		}

		return lista;
	}

	private String getContent(Row row, int col) {
		Cell cell = row.getCell(col);

		if (cell == null) {
			return null;
		}

		switch (cell.getCellType()) {

		case STRING:
			return cell.getStringCellValue().strip();

		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				// Converter data para texto no formato dd/MM/yyyy
				LocalDate date = cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).strip();
			} else {
				// Se for número, converter removendo ".0" quando inteiro
				double value = cell.getNumericCellValue();

				if (value == (long) value) {
					return String.valueOf((long) value).strip(); // sem decimal
				} else {
					return String.valueOf(value).strip();
				}
			}

		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue()).strip();

		case BLANK:
			return ""; // ou null, como preferir

		case FORMULA:
			// Retorna o valor calculado da fórmula
			return cell.getRichStringCellValue().toString().strip();

		default:
			return null;
		}
	}
}
