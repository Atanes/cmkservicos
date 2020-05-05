package br.com.iridiumit.cmkservicos.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.iridiumit.cmkservicos.modelos.Endereco;
import br.com.iridiumit.cmkservicos.modelos.Usuario;
import br.com.iridiumit.cmkservicos.relatorio.UsuarioREL;
import br.com.iridiumit.cmkservicos.repository.Enderecos;
import br.com.iridiumit.cmkservicos.repository.Grupos;
import br.com.iridiumit.cmkservicos.repository.Usuarios;
import br.com.iridiumit.cmkservicos.repository.filtros.FiltroGeral;

@Controller
@RequestMapping("/administracao/usuarios")
public class UsuarioController {
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Enderecos enderecos;
	
	@Autowired
	private Grupos grupos;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") FiltroGeral filtro) {
		
		ModelAndView modelAndView = new ModelAndView("administracao/usuario/lista-usuarios");
		
		if(filtro.getTextoFiltro() == null) {
			modelAndView.addObject("usuarios", usuarios.findAll());
		}else {
			modelAndView.addObject("usuarios", usuarios.findByNomeContainingIgnoreCase(filtro.getTextoFiltro()));
		}
		
		return modelAndView;
	}
	
	@DeleteMapping("/excluir/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		
		usuarios.deleteById(id);

		attributes.addFlashAttribute("sucesso", "Usuário inativado com sucesso!!");
		
		return "redirect:/administracao/usuarios";
	}
	
	@GetMapping("/inativar/{id}")
	public String inativar(@PathVariable Long id, RedirectAttributes attributes) {
		
		Usuario u = usuarios.getOne(id);
		
		u.setAtivo(false);
		
		usuarios.save(u);

		attributes.addFlashAttribute("sucesso", "Usuário inativado com sucesso!!");
		
		return "redirect:/administracao/usuarios";
	}
	
	@GetMapping("/ativar/{id}")
	public String ativar(@PathVariable Long id, RedirectAttributes attributes) {
		
		Usuario u = usuarios.getOne(id);
		
		u.setAtivo(true);
		
		usuarios.save(u);

		attributes.addFlashAttribute("sucesso", "Usuário re-ativado com sucesso!!");
		
		return "redirect:/administracao/usuarios";
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return editar(usuarios.getOne(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("administracao/usuario/cadastro-usuario");
		
		modelAndView.addObject(usuario);
		
		modelAndView.addObject("grupos", grupos.findAll());

		return modelAndView;
	}
	
	@GetMapping("/editar")
	public ModelAndView editar(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("administracao/usuario/editar-usuario");

		modelAndView.addObject(usuario);
		
		modelAndView.addObject("grupos", grupos.findAll());

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
	
		Usuario u = usuarios.findByCpf(usuario.getCpf());
		Endereco e = usuario.getEndereco();
		
		if (u != null) {
			result.rejectValue("cpf", "usuario.cpf.existente", "Já existe um usuário com esse CPF cadastrado!!");
        }
		
		if (result.hasErrors()) {
			usuario.setSenha(null);
            return novo(usuario);
        } else {
        	enderecos.save(e);
        	usuario.setEndereco(e);
        	
        	String senha = passwordEncoder.encode(usuario.getSenha());
    		usuario.setSenha(senha);
        
        	usuarios.save(usuario);
        	attributes.addFlashAttribute("sucesso", "Usuario salvo com sucesso!!");
        }
		
		return new ModelAndView("redirect:/administracao/usuarios/novo");
	}
	
	@PostMapping("/atualizar")
	public ModelAndView atualizar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		
		Endereco e = enderecos.findByid(usuario.getEndereco().getId());
		
		enderecos.save(e);

		if (result.hasErrors()) {
            return editar(usuario);
        } else {
        	usuarios.save(usuario);
        	attributes.addFlashAttribute("sucesso", "Usuario atualizado com sucesso!!");
        }
		
		return new ModelAndView("redirect:/administracao/usuarios");
			
	}
	
	@GetMapping(value = "/rel-usuarios", produces = MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody byte[] getRelUsuarios() throws IOException {

		UsuarioREL relatorio = new UsuarioREL();
		try {
			relatorio.imprimir(usuarios.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		InputStream in = this.getClass().getResourceAsStream("/relatorios/Relatorio_de_Usuarios.pdf");
		return IOUtils.toByteArray(in);
	}

}
