package br.com.ctcea.gestaoinv.components;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.ctcea.gestaoinv.services.LogAcessoService;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final LogAcessoService logAcessoService;

    public AuthenticationFailureListener(LogAcessoService logAcessoService) {
        this.logAcessoService = logAcessoService;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        try {
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
            }

            String username = (String) event.getAuthentication().getPrincipal();
            String motivoFalha = event.getException().getClass().getSimpleName();
            logAcessoService.registrarAcesso(username, ip, "Falha - " + motivoFalha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
