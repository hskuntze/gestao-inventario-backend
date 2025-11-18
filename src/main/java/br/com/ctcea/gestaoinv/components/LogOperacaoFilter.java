package br.com.ctcea.gestaoinv.components;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.ctcea.gestaoinv.services.LogOperacaoService;

@Component
public class LogOperacaoFilter extends OncePerRequestFilter {

	@Autowired
	private LogOperacaoService logService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain)
	        throws ServletException, IOException {

	    String metodo = request.getMethod();
	    String url = request.getRequestURI();
	    String ip = request.getRemoteAddr();
	    String usuario = obterUsuarioAutenticado();
	    String detalhes = request.getQueryString();

	    int status = 200;

	    try {
	        filterChain.doFilter(request, response);
	        status = response.getStatus();
	    } catch (Exception e) {
	        status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
	        logService.registrarLog(usuario, ip, metodo, url, status, e.getMessage());
	        throw e;
	    } finally {
	        logService.registrarLog(usuario, ip, metodo, url, status, detalhes);
	    }
	}

    private String obterUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            return authentication.getName();
        }
        return "Anônimo";
    }
}
