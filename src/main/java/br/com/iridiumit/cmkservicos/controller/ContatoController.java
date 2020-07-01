package br.com.iridiumit.cmkservicos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.iridiumit.cmkservicos.modelos.Cliente;
import br.com.iridiumit.cmkservicos.modelos.Contato;
import br.com.iridiumit.cmkservicos.repository.Clientes;
import br.com.iridiumit.cmkservicos.repository.Contatos;
import br.com.iridiumit.cmkservicos.repository.filtros.FiltroGeral;

@Controller
@RequestMapping("/contatos")
public class ContatoController {

	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Contatos contatos;

	@GetMapping("/{id}")
	public ModelAndView listarContatos(@PathVariable Integer id, @ModelAttribute("filtro") FiltroGeral filtro) {
		
		Cliente c = clientes.getOne(id);
		
		ModelAndView modelAndView = new ModelAndView("administracao/cliente/contato/lista-contatos");
		modelAndView.addObject(c);

		modelAndView.addObject("contatos", contatos.findByCliente(c));
		return modelAndView;
	}
	
	@GetMapping("/novo/{id}")
	public ModelAndView novoContato(@PathVariable Integer id, Contato contato) {
		
		Cliente c = clientes.getOne(id);

		ModelAndView modelAndView = new ModelAndView("administracao/cliente/contato/cadastro-contato");
		
		modelAndView.addObject(c);

		modelAndView.addObject(contato);

		return modelAndView;
	}
	
	@PostMapping("/salvar/{id}")
	public ModelAndView salvarContato(@PathVariable Integer id, @Valid Contato contato, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return novoContato(id, contato);
		}
		
		Contato c = contatos.findByEmail(contato.getEmail());

		if (c != null) {
			if (!contato.getId().equals(c.getId())) {

				result.rejectValue("email", "email.contato.existente");
			}
		}

		if (result.hasErrors()) {
			return novoContato(id, contato);
		}
		
		contato.setCliente(clientes.getOne(id));

		contatos.save(contato);

		attributes.addFlashAttribute("mensagem", "Contato salvo com sucesso!!");

		return new ModelAndView("redirect:/contatos/" + contato.getCliente().getId());

	}
	
	@GetMapping("/excluir/{id}")
	public String excluirContato(@PathVariable Integer id, RedirectAttributes attributes) {
		
		Cliente c = contatos.getOne(id).getCliente();

		contatos.delete(contatos.getOne(id));

		attributes.addFlashAttribute("sucesso", "Contato excluido com sucesso!!");

		return "redirect:/contatos/" + c.getId() ;
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editarContato(@PathVariable Integer id) {
		
		Contato c = contatos.getOne(id);
		
		return novoContato(c.getCliente().getId(), c);
	}
	
	@RequestMapping(value = "/incluirContato", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> salvarContato(@RequestParam("id") Integer id,
			@RequestParam("nome") String nome, @RequestParam("email") String email,
			@RequestParam("fone") String fone, @RequestParam("depart") String depart) {

		if (contatos.findByEmail(email) != null) {
			return ResponseEntity.badRequest().body("Já existe um contato com esse e-mail no banco de dados!!");
		}
		
		Contato c = new Contato(clientes.getOne(id), nome, email,fone,depart);
		
		c = contatos.saveAndFlush(c);

		return ResponseEntity.ok(c.getNome());
	}

}
