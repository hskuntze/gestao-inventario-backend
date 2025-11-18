package br.com.ctcea.gestaoinv.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ctcea.gestaoinv.entities.Localizacao;

@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long>{

	@Query("SELECT l FROM Localizacao l WHERE l.area.id = :id")
	List<Localizacao> getAllFromArea(Long id);
	
	Optional<Localizacao> findByNome(String nome);

	@Query("SELECT l FROM Localizacao l WHERE l.nome = :nome AND l.area.nome = :area")
	Optional<Localizacao> findByNomeAndArea(String nome, String area);
}