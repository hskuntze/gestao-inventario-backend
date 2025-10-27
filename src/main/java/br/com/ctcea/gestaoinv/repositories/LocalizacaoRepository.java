package br.com.ctcea.gestaoinv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ctcea.gestaoinv.entities.Localizacao;

@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long>{

}
