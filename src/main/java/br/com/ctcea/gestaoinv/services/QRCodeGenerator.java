package br.com.ctcea.gestaoinv.services;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import br.com.ctcea.gestaoinv.exceptions.RecursoNaoEncontradoException;
import br.com.ctcea.gestaoinv.exceptions.RequisicaoNaoProcessavelException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRFontNotFoundException;

@Service
public class QRCodeGenerator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QRCodeGenerator.class);

	private static final String QR_CODE = "classpath:jasper/qr-code.jrxml";
	
	private Map<String, Object> params = new HashMap<>();
	
	public void addParam(String key, Object value) {
		this.params.put(key, value);
	}

	public void clearParams() {
		this.params.clear();
	}

	public static BufferedImage gerarQRCode(String texto) throws WriterException, IOException {
		int size = 300;
		
		Map<EncodeHintType, Object> hints = new HashMap<>();
	    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
	    hints.put(EncodeHintType.MARGIN, 1);
		
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix matrix = writer.encode(texto, BarcodeFormat.QR_CODE, size, size, hints);
		BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix, new MatrixToImageConfig(0xFF1B5E20, 0xFFFFFFFF));
		
		ClassPathResource resource = new ClassPathResource("images/Gestao_Inventario_Icone.png");
		BufferedImage logo = ImageIO.read(resource.getInputStream());
		
		int logoSize = size / 6; // 1/6 do QR
	    Image scaledLogo = logo.getScaledInstance(logoSize, logoSize, Image.SCALE_SMOOTH);
	    
	    int centerX = (qrImage.getWidth() - logoSize) / 2;
	    int centerY = (qrImage.getHeight() - logoSize) / 2;
		
	    Graphics2D g = qrImage.createGraphics();

	    // Ativa antialiasing
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setColor(Color.WHITE);

	    int padding = 6;
	    g.fillRoundRect(centerX - padding, centerY - padding, logoSize + padding * 2, logoSize + padding * 2, 10, 10);
	    g.drawImage(scaledLogo, centerX, centerY, null);
	    g.dispose();
	    
        return qrImage;
	}
	
	public byte[] qrCodeFromJasper() {
		LOGGER.info("[LOG] - Gerando QRCode");

		byte[] pdf = null;

		try {
			File file = ResourceUtils.getFile(QR_CODE);
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			
			/**
			 * O DataSource para este caso é vazio, uma vez que todos os dados do arquivo
			 * são preenchidos através de parâmetros adicionados ao Map<String, Object>
			 * params.
			 */
			JRDataSource dataSource = new JREmptyDataSource();
			JasperPrint print = JasperFillManager.fillReport(jasperReport, params, dataSource);

			pdf = JasperExportManager.exportReportToPdf(print);
		} catch (FileNotFoundException e) {
			throw new RecursoNaoEncontradoException("Erro ao localizar arquivo JASPER");
		} catch (JRException | JRFontNotFoundException e) {
			throw new RequisicaoNaoProcessavelException(e.getMessage());
		}

		return pdf;
	}
}
