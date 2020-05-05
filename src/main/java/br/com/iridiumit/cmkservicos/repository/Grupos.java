package br.com.iridiumit.cmkservicos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.iridiumit.cmkservicos.modelos.Grupo;

public interface Grupos extends JpaRepository<Grupo, Long>{
	
	Grupo findByNome(String nome);

}
