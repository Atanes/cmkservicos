package br.com.iridiumit.cmkservicos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.iridiumit.cmkservicos.modelos.Permissao;
import br.com.iridiumit.cmkservicos.modelos.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Long> {
	
	Usuario findByLogin(String login);
	
	Usuario findByCpf(String cpf);
	
	List<Usuario> findByNomeContainingIgnoreCase(String nome);
	
	List<Usuario> findByPermissoes(Permissao p);
	
	List<Usuario> findAllByOrderByNome();
}
