package br.com.ctcea.gestaoinv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // Ativa a configuração de segurança da aplicação usando Spring Security.
public class WebSecurityConfig {
	
	// Injeta o UserDetailsService (que é o seu UsuarioService)
	@Autowired
	private UserDetailsService userDetailsService;
	
	// Injeta o BCryptPasswordEncoder (definido em AppConfig)
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

    /**
     * Define o provedor de autenticação para o Spring Security.
     * Isso liga o UserDetailsService (UsuarioService) ao PasswordEncoder (BCryptPasswordEncoder),
     * resolvendo o erro "There is no PasswordEncoder mapped for the id null".
     */
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
	
	/**
     * Configura endpoints que devem ser ignorados pelo Spring Security.<br>
     */
	@Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/actuator/**");
    }
	
	/**
     * Configura o gerenciador de autenticação da aplicação.<br>
     * O `AuthenticationManager` obtido usará automaticamente o `DaoAuthenticationProvider`
     * que foi definido como um Bean (@Bean) nesta classe.
     */
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
