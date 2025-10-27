package br.com.ctcea.gestaoinv.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import br.com.ctcea.gestaoinv.components.LoginTokenEnhancer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
    @Value("${security.oauth2.client.client-id}") 
    private String clientId;
    
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${jwt.duration}")
    private Integer duration;

    @Autowired
	private BCryptPasswordEncoder encoder;
    
    @Autowired
	private JwtAccessTokenConverter converter;
    
    @Autowired
	private JwtTokenStore tokenStore;
    
    @Autowired
    private AuthenticationManager authManager;
    
    @Autowired
    private LoginTokenEnhancer loginTokenEnhancer;
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient(clientId)
            .secret(encoder.encode(clientSecret)) 
            .scopes("read", "write")
            .authorizedGrantTypes("password", "refresh_token")
            .accessTokenValiditySeconds(duration);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	TokenEnhancerChain chain = new TokenEnhancerChain();
    	chain.setTokenEnhancers(Arrays.asList(converter, loginTokenEnhancer));
    	
    	endpoints.authenticationManager(authManager)
			.tokenStore(tokenStore)
			.accessTokenConverter(converter)
			.tokenEnhancer(chain);
    }
}
