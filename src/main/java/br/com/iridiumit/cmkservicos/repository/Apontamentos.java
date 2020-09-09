package br.com.iridiumit.cmkservicos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.iridiumit.cmkservicos.modelos.Apontamento;

public interface Apontamentos extends JpaRepository<Apontamento, Long> {

}
