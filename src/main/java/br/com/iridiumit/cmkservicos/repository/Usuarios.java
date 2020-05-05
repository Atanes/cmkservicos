package br.com.iridiumit.cmkservicos.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.iridiumit.cmkservicos.modelos.Grupo;
import br.com.iridiumit.cmkservicos.modelos.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Long> {
	
	Usuario findByCpf(String cpf);
	
	List<Usuario> findByGrupos(Grupo g);
	
	List<Usuario> findByNomeContainingIgnoreCase(String nome);
	
	List<Usuario> findAllByOrderByNome();
	
	boolean existsById(long id);
	boolean existsByEmail(String email);
	Usuario findByEmail(String email);
	
	Usuario findByDataNascimento(LocalDate data);
	
	Usuario findByEmailAndAtivoTrue(String email);
	
	@Query("select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = ?1")
	List<String> listaPermissoes(Usuario u);
	
	@Query("select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p")
	List<String> listaPermissoes();
}
