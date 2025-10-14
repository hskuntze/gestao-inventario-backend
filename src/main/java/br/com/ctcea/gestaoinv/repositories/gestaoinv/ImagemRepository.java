package br.com.ctcea.gestaoinv.repositories.gestaoinv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ctcea.gestaoinv.entities.gestaoinv.Imagem;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long>{

	
}
