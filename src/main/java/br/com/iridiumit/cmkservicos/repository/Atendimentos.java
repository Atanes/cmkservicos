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
	
	@Query("select a from Atendimento a where lower(a.chamado.equipamento.tipo) like "
			+ "lower(concat('%', :tipo,'%'))")
	Page<Atendimento> findByEquipamentoTipo(@Param("tipo")String tipo, Pageable pageable);
	
	@Query("select a from Atendimento a where lower(a.chamado.equipamento.tipo) like "
			+ "lower(concat('%', :tipo,'%')) and a.executor = :executor")
	Page<Atendimento> findByEquipamentoTipoExecutor(@Param("tipo")String tipo, 
			@Param("executor")String executor, Pageable pageable);
	
	@Query("select a from Atendimento a where lower(a.chamado.equipamento.tipo) like "
			+ "lower(concat('%', :tipo,'%')) and a.executor = :executor and a.chamado.status != 'FINALIZADO'")
	Page<Atendimento> findByEquipamentoTipoExecutorAberto(@Param("tipo")String tipo, 
			@Param("executor")String executor, Pageable pageable);
	
	@Query("select a from Atendimento a where a.chamado.equipamento.cliente.id = :id")
	Page<Atendimento> findByCliente(@Param("id")Integer id, Pageable pageable);
	
	@Query("select a from Atendimento a where lower(a.chamado.equipamento.tipo) like "
			+ "lower(concat('%', :tipo,'%')) and a.chamado.equipamento.cliente.id = :id")
	Page<Atendimento> findByClienteTipo(@Param("id")Integer id, @Param("tipo")String tipo, Pageable pageable);
	
	@Query("SELECT a FROM Atendimento a WHERE a.chamado.tipo = ?1 and a.executor = ?2")
	List<Atendimento> findByTipoAndExecutor(String tipo, String executor);
	
	@Query("SELECT a FROM Atendimento a WHERE a.executor = ?1 and a.chamado.status != 'FINALIZADO' ORDER BY a.chamado.dataAbertura")
	Page<Atendimento> findByExecutorAberto(String executor, Pageable pageable);
	
	Atendimento findByNros(Integer nros);
	
	List<Atendimento> findByAprovador(String aprovador);
	
	List<Atendimento> findByExecutor(String executor);
	
	Page<Atendimento> findAll(Pageable pageable);
	
	Page<Atendimento> findByExecutor(String executor, Pageable pageable);

}
