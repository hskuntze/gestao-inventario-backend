package br.com.ctcea.gestaoinv.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ctcea.gestaoinv.entities.UsuarioResponsavel;

@Repository
public interface UsuarioResponsavelRepository extends JpaRepository<UsuarioResponsavel, Long>{

	Optional<UsuarioResponsavel> findByNome(String nome);
	
	@Query("SELECT ur FROM UsuarioResponsavel ur WHERE ur.area.id = :idArea")
	List<UsuarioResponsavel> getAllByAreaId(Long idArea);
}
