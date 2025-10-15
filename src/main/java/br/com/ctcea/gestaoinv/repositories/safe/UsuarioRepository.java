package br.com.ctcea.gestaoinv.repositories.safe;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ctcea.gestaoinv.entities.safe.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByLogin(String login);
	
	@Query(nativeQuery = true, value = "SELECT GROUP_CODE FROM fdn_groupuserrole WHERE LOGIN = :login")
	List<String> findGroupsByLogin(String login);
	
	@EntityGraph(attributePaths = "usuarioBase")
    List<Usuario> findAll();
}
