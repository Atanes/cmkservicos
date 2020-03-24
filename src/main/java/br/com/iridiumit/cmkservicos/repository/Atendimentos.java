package br.com.iridiumit.cmkservicos.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.iridiumit.cmkservicos.modelos.Atendimento;

public interface Atendimentos extends JpaRepository<Atendimento, Long>, PagingAndSortingRepository<Atendimento, Long>{
	
	@Query("select a from Atendimento a where lower(a.chamado.equipamento.tipo) like lower(concat('%', :tipo,'%'))")
	Page<Atendimento> findByEquipamentoTipo(@Param("tipo")String tipo, Pageable pageable);
	
	@Query("select a from Atendimento a where a.chamado.equipamento.cliente.id = ?1")
	Page<Atendimento> findByCliente(Integer id, Pageable pageable);
	
	@Query("SELECT a FROM Atendimento a WHERE a.chamado.tipo = ?1 and a.executor = ?2")
	List<Atendimento> findByTipoAndExecutor(String tipo, String executor);
	
	Atendimento findByNros(Integer nros);
	
	List<Atendimento> findByAprovador(String aprovador);
	
	List<Atendimento> findByExecutor(String executor);
	
	Page<Atendimento> findAll(Pageable pageable);
	
	Page<Atendimento> findByExecutor(String executor, Pageable pageable);

}
