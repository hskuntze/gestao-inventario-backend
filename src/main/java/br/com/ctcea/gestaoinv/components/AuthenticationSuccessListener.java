package br.com.ctcea.gestaoinv.components;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.ctcea.gestaoinv.services.LogAcessoService;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

	private final LogAcessoService logAcessoService;

	public AuthenticationSuccessListener(LogAcessoService logAcessoService) {
		this.logAcessoService = logAcessoService;
	}

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		try {
			if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
				ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
				
				String ip = "N/A";
				
				if (attrs != null) {
					HttpServletRequest request = attrs.getRequest();
					
					ip = request.getHeader("X-Forwarded-For");
					if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
						ip = request.getRemoteAddr();
					}
					
					if ("0:0:0:0:0:0:0:1".equals(ip)) {
						ip = "127.0.0.1";
					}
					
					String username = event.getAuthentication().getName();
					logAcessoService.registrarAcesso(username, ip, "Sucesso");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
