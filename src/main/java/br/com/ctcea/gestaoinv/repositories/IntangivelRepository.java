package br.com.ctcea.gestaoinv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ctcea.gestaoinv.entities.Intangivel;

@Repository
public interface IntangivelRepository extends JpaRepository<Intangivel, Long>{

	@Query("SELECT COUNT(i) FROM Intangivel i")
	Integer getCount();

	boolean existsByIdPatrimonial(String idGerado);
}
