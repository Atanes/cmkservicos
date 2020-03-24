package br.com.iridiumit.cmkservicos.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.iridiumit.cmkservicos.modelos.Cliente;
import br.com.iridiumit.cmkservicos.modelos.Equipamento;

public interface Equipamentos extends JpaRepository<Equipamento, Long>, PagingAndSortingRepository<Equipamento, Long> {
	
	List<Equipamento> findByFabricanteContainingIgnoreCase(String fabricante);
	
	List<Equipamento> findByModeloContainingIgnoreCase(String modelo);
	
	List<Equipamento> findByCliente(Cliente c);
	
	Equipamento findByNrcmk(Integer nr);
	
	Page<Equipamento> findByTipoContainingIgnoreCase(String tipo, Pageable pageable);
	
	Page<Equipamento> findAll(Pageable pageable);

}
