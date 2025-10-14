package br.com.ctcea.gestaoinv.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import br.com.ctcea.gestaoinv.entities.safe.Usuario;
import br.com.ctcea.gestaoinv.repositories.safe.UsuarioRepository;
import br.com.ctcea.gestaoinv.services.exceptions.RecursoNaoEncontradoException;

@Component
public class LoginTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UsuarioRepository userRepo;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Usuario user = userRepo.findByLogin(authentication.getName())
				.orElseThrow(() -> new RecursoNaoEncontradoException("Erro ao tentar localizar usuário com login "
						+ authentication.getName() + " durante a operação de incrementar token de login."));
		
		Map<String, Object> map = new HashMap<>();
		map.put("email", user.getEmail());
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
		token.setAdditionalInformation(map);
		
		return token;
	}

}
