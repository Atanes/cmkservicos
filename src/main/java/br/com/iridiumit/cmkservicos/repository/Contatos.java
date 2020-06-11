package br.com.iridiumit.cmkservicos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.iridiumit.cmkservicos.modelos.Cliente;
import br.com.iridiumit.cmkservicos.modelos.Contato;

public interface Contatos extends JpaRepository<Contato, Integer>{

	Contato findByEmail(String email);
	
	List<Contato> findByCliente(Cliente c);

	Contato findByNomeContainingIgnoreCaseAndCliente(String nome, Cliente c);

}
