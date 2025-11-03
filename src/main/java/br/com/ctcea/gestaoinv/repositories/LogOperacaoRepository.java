package br.com.ctcea.gestaoinv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ctcea.gestaoinv.logs.LogOperacao;

@Repository
public interface LogOperacaoRepository extends JpaRepository<LogOperacao, Long>{
}