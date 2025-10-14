package br.com.ctcea.gestaoinv.repositories.gestaoinv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ctcea.gestaoinv.entities.gestaoinv.Tangivel;

@Repository
public interface TangivelRepository extends JpaRepository<Tangivel, Long>{

}
