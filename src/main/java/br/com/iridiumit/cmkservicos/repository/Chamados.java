package br.com.iridiumit.cmkservicos.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.iridiumit.cmkservicos.modelos.Chamado;
import br.com.iridiumit.cmkservicos.modelos.Equipamento;

public interface Chamados extends JpaRepository<Chamado, Long>, PagingAndSortingRepository<Chamado, Long> {

	@Query("select c from Chamado c where lower(c.equipamento.tipo) like lower(concat('%', :tipo,'%'))")
	Page<Chamado> findByEquipamentoTipo(@Param("tipo")String tipo, Pageable pageable);

	Page<Chamado> findAll(Pageable pageable);

	Chamado findByTipo(String textoFiltro);

	Page<Chamado> findByTipoContainingIgnoreCase(String tipo, Pageable pageable);

	List<Chamado> findByEquipamento(Equipamento e);

	List<Chamado> findByEmissor(String emissor);

}
