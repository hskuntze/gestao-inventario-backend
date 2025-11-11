package br.com.ctcea.gestaoinv.components;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import br.com.ctcea.gestaoinv.entities.Usuario;

@Component
public class TenantFilterInterceptor {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired 
    private UserDetailsService userDetailsService; 

    public void applyFilter() {
        Usuario usuarioAutenticado = null;
        
        try {
            Session session = entityManager.unwrap(Session.class);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                session.disableFilter("filialFilter");
                return;
            }
            
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // 1. TENTA OBTER O USUÁRIO PELO TIPO (melhor caso)
            if (principal instanceof Usuario) {
                usuarioAutenticado = (Usuario) principal;

            // 2. SE FOR STRING (o seu caso), BUSCA O USUÁRIO PELO LOGIN
            } else if (principal instanceof String) {
                String login = (String) principal;
                
                // Use o UserDetailsService para recarregar o objeto Usuario COMPLETO
                // Presumimos que seu UserDetailsService retorna a sua classe Usuario que implementa UserDetails
                UserDetails userDetails = userDetailsService.loadUserByUsername(login);
                
                if (userDetails instanceof Usuario) {
                    usuarioAutenticado = (Usuario) userDetails;
                }
            }

            // 3. APLICA O FILTRO com o objeto Usuario recarregado
            if (usuarioAutenticado != null) {
                var termo = usuarioAutenticado.getTermoParceria();

                if (termo != null && !"MATRIZ".equalsIgnoreCase(termo.name())) {
                    session.enableFilter("filialFilter").setParameter("termoParceria", termo.name());
                } else {
                    session.disableFilter("filialFilter");
                }
            } else {
                 session.disableFilter("filialFilter");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
