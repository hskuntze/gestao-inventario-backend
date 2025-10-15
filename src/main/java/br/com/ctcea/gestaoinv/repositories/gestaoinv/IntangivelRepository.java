package br.com.ctcea.gestaoinv.repositories.gestaoinv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ctcea.gestaoinv.entities.gestaoinv.Intangivel;

@Repository
public interface IntangivelRepository extends JpaRepository<Intangivel, Long>{

	@Query(nativeQuery = true, value = "SELECT COUNT(*) AS quantidade FROM tb_ativo_intangivel")
	Integer getCount();
}
