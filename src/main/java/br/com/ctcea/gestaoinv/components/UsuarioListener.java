package br.com.ctcea.gestaoinv.components;

import javax.persistence.PostLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ctcea.gestaoinv.entities.safe.Usuario;
import br.com.ctcea.gestaoinv.repositories.safe.UsuarioRepository;

@Component
public class UsuarioListener {

    private static UsuarioRepository usuarioRepository;

    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        UsuarioListener.usuarioRepository = usuarioRepository;
    }

    @PostLoad
    public void carregarAssociacoes(Usuario usuario) {
        if (usuarioRepository != null) {
            var grupos = usuarioRepository.findGroupsByLogin(usuario.getLogin());
            usuario.setAssociacoes(grupos);
        } else {
            System.out.println("⚠️ Repositório ainda não injetado!");
        }
    }
}
