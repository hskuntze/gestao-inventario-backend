package br.com.ctcea.gestaoinv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.com.ctcea.gestaoinv.components.Mds5PasswordEncoder;
import br.com.ctcea.gestaoinv.services.UsuarioService;

@Configuration
@EnableWebSecurity
public class AuthenticationManagerConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private UsuarioService userDetailsService;

    @Autowired
    private Mds5PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
