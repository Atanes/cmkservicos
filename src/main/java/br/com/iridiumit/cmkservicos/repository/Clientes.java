package br.com.iridiumit.cmkservicos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.iridiumit.cmkservicos.modelos.Cliente;

public interface Clientes extends JpaRepository<Cliente, Integer>{

	List<Cliente> findByNomeContainingIgnoreCaseAndAtivo(String nome, boolean ativo);
	
	Cliente findByEmail(String email);
	
	List<Cliente> findByAtivo(boolean ativo);

}
