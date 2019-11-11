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
	
	@GetMapping("/pendencias")
	public ModelAndView listarPendencias(@ModelAttribute("filtro") FiltroGeral filtro) {
		
		String userLogin = ((cmkUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLogin();

		ModelAndView modelAndView = new ModelAndView("atendimento/lista-pendencias");

		if (filtro.getTextoFiltro() == null) {
			modelAndView.addObject("atendimentos", atendimentos.findByExecutor(userLogin));
		} else {
			modelAndView.addObject("atendimentos",
					atendimentos.findByTipoAndExecutor(filtro.getTextoFiltro(), userLogin));
		}

		return modelAndView;
	}

	@GetMapping("/{id}")
	public ModelAndView atendimentoEquipamento(Atendimento atendimento, @PathVariable Long id) {
		
		String userLogin = ((cmkUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLogin();

		ModelAndView modelAndView = new ModelAndView("atendimento/cadastro-RAEquipamento");
		
		Equipamento e = equipamentos.getOne(id);
		
		String cliente = e.getCliente().getNome();

		atendimento.setEquipamento(e);
		
		atendimento.setCliente(cliente);
		
		atendimento.setEmissor(userLogin);
		
		modelAndView.addObject("usuarios", usuarios.findAllByOrderByNome());
		
		modelAndView.addObject(atendimento);

		return modelAndView;
	}
	
	@GetMapping("/realizar/{id}")
	public ModelAndView realizarAtendimento(@PathVariable Long id) {

		ModelAndView modelAndView = new ModelAndView("atendimento/realizar-atendimento");
		
		Atendimento atendimento = atendimentos.getOne(id);
		
		modelAndView.addObject(atendimento);

		return modelAndView;
	}

	@GetMapping("/selecao/{id}")
	public ModelAndView SelecaoPorCliente(@PathVariable Integer id, @ModelAttribute("filtro") FiltroGeral filtro) {

		Cliente c = clientes.getOne(id);

		ModelAndView modelAndView = new ModelAndView("atendimento/lista-RACliente");

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

		return atendimentoEquipamento(atendimentos.getOne(id), atendimentos.getOne(id).getEquipamento().getId());
	}

	@GetMapping("/novo")
	public ModelAndView novo(Atendimento atendimento) {
		
		String userLogin = ((cmkUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLogin();

		ModelAndView modelAndView = new ModelAndView("/atendimento/cadastro-RAEquipamento");
		
		atendimento.setEmissor(userLogin);
		
		modelAndView.addObject(atendimento);

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Atendimento atendimento, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return novo(atendimento);
		}
		
		atendimentos.save(atendimento);

		attributes.addFlashAttribute("mensagem", "Atendimento salvo com sucesso!!");

		return new ModelAndView("redirect:/atendimentos");

	}
	
	@PostMapping("/realizar/salvar")
	public ModelAndView realizarSalvar(@Valid Atendimento atendimento, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			System.out.println(result.getFieldErrorCount() + ", " + result.getFieldError());
			return realizarAtendimento(atendimento.getNumero());
		}
		
		atendimentos.save(atendimento);

		attributes.addFlashAttribute("mensagem", "Atendimento salvo com sucesso!!");

		return new ModelAndView("redirect:/atendimentos/pendencias");

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
