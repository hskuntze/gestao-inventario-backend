package br.com.ctcea.gestaoinv.components;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ctcea.gestaoinv.entities.Usuario;
import br.com.ctcea.gestaoinv.enums.TermoParceria;
import br.com.ctcea.gestaoinv.services.UsuarioService;

@Component
public class GeradorIDPatrimonial {

	@Autowired
	private UsuarioService usuarioService;
	
	private static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static SecureRandom random = new SecureRandom();

	private String generateRandomString(int length) {
	    StringBuilder sb = new StringBuilder(length);
	    for (int i = 0; i < length; i++) {
	        int randomIndex = random.nextInt(ALPHANUMERIC.length());
	        sb.append(ALPHANUMERIC.charAt(randomIndex));
	    }
	    return sb.toString();
	}
	
	public String generate() {
		Usuario usuario = usuarioService.getAuthenticatedUser();
		
		TermoParceria tp = usuario.getTermoParceria();
		
		String prefixoTP = tp.toString();
		String parteRandomica = generateRandomString(6);
		
		return prefixoTP + "/" + parteRandomica;
	}
}
