package br.com.iridiumit.cmkservicos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.iridiumit.cmkservicos.modelos.Permissao;

public interface Permissoes extends JpaRepository<Permissao, Long>{
	
	Permissao findByNome(String nome);

}
