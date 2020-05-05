package br.com.iridiumit.cmkservicos.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.iridiumit.cmkservicos.modelos.Usuario;
import br.com.iridiumit.cmkservicos.modelos.UsuarioSistema;
import br.com.iridiumit.cmkservicos.repository.Usuarios;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	Usuarios usuarios;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Usuario usuario = usuarios.findByEmailAndAtivoTrue(email);
		if (usuario == null) {
			new UsernameNotFoundException("Usuário ou senha inválidos!");
		}

		return new UsuarioSistema(usuario, getPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		List<String> permissoes = usuarios.listaPermissoes(usuario);
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
		
		System.out.println(authorities.toString());

		return authorities;
	}

}
