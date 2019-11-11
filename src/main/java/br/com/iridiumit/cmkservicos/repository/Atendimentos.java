package br.com.iridiumit.cmkservicos.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.iridiumit.cmkservicos.modelos.Atendimento;

public interface Atendimentos extends JpaRepository<Atendimento, Long>, PagingAndSortingRepository<Atendimento, Long>{

	List<Atendimento> findByTipo(String tipo);
	
	List<Atendimento> findByTipoAndExecutor(String tipo, String executor);
	
	Atendimento findByNros(Integer nros);
	
	List<Atendimento> findByStatus(String Status);
	
	List<Atendimento> findByCliente(String cliente);
	
	List<Atendimento> findByEmissor(String emissor);
	
	List<Atendimento> findByAprovador(String aprovador);
	
	List<Atendimento> findByExecutor(String executor);
	
	Page<Atendimento> findAll(Pageable pageable);
	
	Page<Atendimento> findByExecutor(String executor, Pageable pageable);

}
