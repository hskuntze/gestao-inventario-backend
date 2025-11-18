package br.com.ctcea.gestaoinv.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ctcea.gestaoinv.entities.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long>{

	Optional<Area> findByNome(String nome);
}