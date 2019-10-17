package br.com.iridiumit.cmkservicos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.iridiumit.cmkservicos.modelos.Endereco;

public interface Enderecos extends JpaRepository<Endereco, Long>{
	
	Endereco findByid(Long id);
	
}
