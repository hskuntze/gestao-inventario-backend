package br.com.ctcea.gestaoinv.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ctcea.gestaoinv.entities.Historico;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long> {
	
	@Query("SELECT h FROM Historico h WHERE h.ativo.id = :id")
	List<Historico> getHistoricoByAtivoId(Long id);
}
