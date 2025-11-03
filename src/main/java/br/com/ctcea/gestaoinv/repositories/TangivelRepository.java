package br.com.ctcea.gestaoinv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ctcea.gestaoinv.entities.Tangivel;

@Repository
public interface TangivelRepository extends JpaRepository<Tangivel, Long>{

	@Query(nativeQuery = true, value = "SELECT COUNT(*) AS quantidade FROM tb_ativo_tangivel")
	Integer getCount();

	boolean existsByIdPatrimonial(String idGerado);
}
