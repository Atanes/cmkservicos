package br.com.iridiumit.cmkservicos.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
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

import br.com.iridiumit.cmkservicos.modelos.Atendimento;
import br.com.iridiumit.cmkservicos.modelos.Cliente;
import br.com.iridiumit.cmkservicos.modelos.Equipamento;
import br.com.iridiumit.cmkservicos.modelos.StatusAtendimento;
import br.com.iridiumit.cmkservicos.modelos.TipoAtendimento;
import br.com.iridiumit.cmkservicos.relatorio.AtendimentosREL;
import br.com.iridiumit.cmkservicos.repository.Atendimentos;
import br.com.iridiumit.cmkservicos.repository.Clientes;
import br.com.iridiumit.cmkservicos.repository.Enderecos;
import br.com.iridiumit.cmkservicos.repository.Equipamentos;
import br.com.iridiumit.cmkservicos.repository.Usuarios;
import br.com.iridiumit.cmkservicos.repository.filtros.FiltroGeral;
import br.com.iridiumit.cmkservicos.security.cmkUserDetails;

@Controller
@RequestMapping("/atendimentos")
public class AtendimentoController {

	@Autowired
	private Atendimentos atendimentos;
	
	@Autowired
	private Clientes clientes;

	@Autowired
	private Equipamentos equipamentos;
	
	@Autowired
	private Usuarios usuarios;

	@Autowired
	private Enderecos enderecos;

	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") FiltroGeral filtro) {

		ModelAndView modelAndView = new ModelAndView("atendimento/lista-atendimentos");

		if (filtro.getTextoFiltro() == null) {
			modelAndView.addObject("atendimentos", atendimentos.findAll());
		} else {
			modelAndView.addObject("atendimentos",
					atendimentos.findByTipo(filtro.getTextoFiltro()));
		}

		return modelAndView;
	}

	@GetMapping("/{id}")
	public ModelAndView raClientes(@PathVariable Integer id) {

		ModelAndView modelAndView = new ModelAndView("atendimento/lista-atendimentos-e-clientes");

		Cliente c = clientes.getOne(id);

		modelAndView.addObject(c);

		modelAndView.addObject("atendimentos", atendimentos.findByCliente(c.getNome()));

		modelAndView.addObject("mensagem", "Atendimento salvo com sucesso!");

		return modelAndView;
	}

	@GetMapping("/selecao/{id}")
	public ModelAndView SelecaoPorCliente(@PathVariable Integer id, @ModelAttribute("filtro") FiltroGeral filtro) {

		Cliente c = clientes.getOne(id);

		ModelAndView modelAndView = new ModelAndView("atendimento/lista-atendimentos-e-clientes");

		modelAndView.addObject(c);

		modelAndView.addObject("atendimentos", atendimentos.findByCliente(c.getNome()));

		return modelAndView;

	}

	@DeleteMapping("excluir/{id}")
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {

		atendimentos.delete(atendimentos.getOne(id));

		attributes.addFlashAttribute("mensagem", "Atendimento excluida com sucesso!!");

		return "redirect:/atendimentos";
	}

	@GetMapping("editar/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(atendimentos.getOne(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Atendimento atendimento) {
		
		String userLogin = ((cmkUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLogin();

		ModelAndView modelAndView = new ModelAndView("/atendimento/cadastro-Atendimento");
		
		modelAndView.addObject(atendimento);
		
		modelAndView.addObject("emissor", userLogin);
		
		modelAndView.addObject("clientes", clientes.findAll());
		
		modelAndView.addObject("equipamentos", equipamentos.findAll());
		
		modelAndView.addObject("usuarios", usuarios.findAllByOrderByNome());

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Atendimento atendimento, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return novo(atendimento);
		}

		Equipamento e = equipamentos.getOne(atendimento.getEquipamento().getId());
		
		atendimento.setEquipamento(e);
		
		atendimentos.save(atendimento);

		attributes.addFlashAttribute("mensagem", "Atendimento salvo com sucesso!!");

		return new ModelAndView("redirect:/atendimentos");

	}

	@GetMapping(value = "/rel-atendimentos", produces = MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody byte[] getRelRAs() throws IOException {

		AtendimentosREL relatorio = new AtendimentosREL();

		try {
			relatorio.imprimir(atendimentos.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}

		InputStream in = this.getClass().getResourceAsStream("/relatorios/Relatorio_de_Atendimentos.pdf");
		return IOUtils.toByteArray(in);

	}
	
	@ModelAttribute("ListaStatus")
	public List<StatusAtendimento> ListaStatus(){
		return Arrays.asList(StatusAtendimento.values());
	}
	
	@ModelAttribute("ListaTipos")
	public List<TipoAtendimento> ListaTipos(){
		return Arrays.asList(TipoAtendimento.values());
	}

}
