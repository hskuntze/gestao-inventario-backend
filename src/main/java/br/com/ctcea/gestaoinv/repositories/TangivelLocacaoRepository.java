package br.com.ctcea.gestaoinv.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ctcea.gestaoinv.entities.TangivelLocacao;

@Repository
public interface TangivelLocacaoRepository extends JpaRepository<TangivelLocacao, Long>{

	@Query(nativeQuery = true, value = "SELECT COUNT(*) AS quantidade FROM tb_ativo_tangivel_locacao")
	Integer getCount();

	boolean existsByIdPatrimonial(String idGerado);
	
	@Query("SELECT atl FROM TangivelLocacao atl WHERE atl.dataDevolucaoPrevista < :data AND atl.dataDevolucaoRealizada IS NULL")
	List<TangivelLocacao> findAtivosComDevolucaoExpirada(LocalDate data);

	@Query("SELECT atl FROM TangivelLocacao atl WHERE atl.dataDevolucaoPrevista BETWEEN :hoje AND :limite AND atl.dataDevolucaoRealizada IS NULL")
	List<TangivelLocacao> findAtivosComDevolucaoParaExpirar(LocalDate hoje, LocalDate limite);
}