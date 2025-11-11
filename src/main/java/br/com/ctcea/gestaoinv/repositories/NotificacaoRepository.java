package br.com.ctcea.gestaoinv.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ctcea.gestaoinv.entities.Notificacao;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
	
	@Query("SELECT n FROM Notificacao n WHERE n.idAtivo = :idAtivo")
	Optional<Notificacao> getByAtivoId(Long idAtivo);
}