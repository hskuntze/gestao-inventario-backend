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
import br.com.ctcea.gestaoinv.wrappers.CustomHttpResponseWrapper;

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

        CustomHttpResponseWrapper responseWrapper = new CustomHttpResponseWrapper(response);
        filterChain.doFilter(request, responseWrapper);

        int status = responseWrapper.getStatus();

        // dispara a gravação assíncrona
        logService.registrarLog(usuario, ip, metodo, url, status, detalhes);
    }

    private String obterUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            return authentication.getName();
        }
        return "Anônimo";
    }

}
