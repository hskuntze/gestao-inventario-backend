package br.com.ctcea.gestaoinv.excel;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.com.ctcea.gestaoinv.entities.Ativo;

public interface AtivoExcelReader<T extends Ativo> {

	List<T> read(MultipartFile file) throws IOException;
}
