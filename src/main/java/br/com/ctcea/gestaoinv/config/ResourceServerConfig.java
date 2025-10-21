package br.com.ctcea.gestaoinv.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private JwtTokenStore tokenStore;
    
    private static final String[] PUBLIC = { "/oauth/token", "/h2-console/**", "/swagger-ui/**", "/usuarios/registrar" };

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .authorizeRequests(requests -> requests
                .antMatchers(PUBLIC).permitAll() 
                .anyRequest().authenticated()
            )
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .headers(headers -> headers.frameOptions().sameOrigin());
    }
    
    /**
     * Configura a política de CORS para a aplicação.<br>
     * - Permite origens específicas.<br>
     * - Permite métodos HTTP específicos.<br>
     * - Permite cabeçalhos específicos, incluindo "Authorization" e "Content-Type".<br>
     * - Habilita credenciais em solicitações.<br>
     */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Arrays.asList(
				"http://localhost",
				"http://localhost:3000", 
				"http://172.20.71.103:3000", 
				"http://localhost:3001", 
				"http://172.20.71.103:3001"));
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	/**
     * Registra o filtro de CORS para ser aplicado a todas as requisições.<br>
     * Define alta prioridade para o filtro.
     */
	@Bean
	FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
				new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}