package br.com.iridiumit.cmkservicos.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.iridiumit.cmkservicos.modelos.Chamado;
import br.com.iridiumit.cmkservicos.modelos.Equipamento;
import br.com.iridiumit.cmkservicos.modelos.Lista_de_Status;
import br.com.iridiumit.cmkservicos.modelos.Lista_de_Tipos;
import br.com.iridiumit.cmkservicos.modelos.UsuarioSistema;
import br.com.iridiumit.cmkservicos.repository.Chamados;
import br.com.iridiumit.cmkservicos.repository.Contatos;
import br.com.iridiumit.cmkservicos.repository.Equipamentos;
import br.com.iridiumit.cmkservicos.repository.filtros.FiltroGeral;
import br.com.iridiumit.cmkservicos.utils.PageUtils;

@Controller
@RequestMapping("/chamados")
public class ChamadoController {
	
	private static final String ORDERBYCHAMADO = "dataAbertura";
	private static final int RECORDSPERPAGE = 10;
	
	UsuarioSistema userLogin;

	@Autowired
	private Chamados chamados;

	@Autowired
	private Equipamentos equipamentos;
	
	@Autowired
	private Contatos contatos;

	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") FiltroGeral filtro, 
			@PageableDefault(size = RECORDSPERPAGE, sort = ORDERBYCHAMADO, direction = Direction.ASC) Pageable pageable
			, HttpServletRequest httpServletRequest) {

		ModelAndView modelAndView = new ModelAndView("chamado/lista-chamados");

		if (filtro.getTextoFiltro() == null) {
			modelAndView.addObject("chamados", chamados.findAll(pageable));
		} else {
			modelAndView.addObject("chamados",
					chamados.findByEquipamentoTipo(filtro.getTextoFiltro(), pageable));
		}
		
		PageUtils pageUtils = new PageUtils(httpServletRequest, pageable);

		modelAndView.addObject("controlePagina", pageUtils);

		return modelAndView;
	}

	@GetMapping("/novo/{id}")
	public ModelAndView chamadoEquipamento(Chamado chamado, @PathVariable Long id) {
		
		userLogin = ((UsuarioSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		ModelAndView modelAndView = new ModelAndView("chamado/cadastro-chamado");
		
		Equipamento e = equipamentos.getOne(id);

		chamado.setEquipamento(e);
		
		chamado.setEmissor(userLogin.getUsuario().getNome());
		
		chamado.setDataAbertura(new Date());
		
		chamado.setStatus("ELABORADO");
		
		modelAndView.addObject("contatos", contatos.findByCliente(e.getCliente()));
		
		modelAndView.addObject(chamado);

		return modelAndView;
	}
	
	
	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Chamado chamado, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return chamadoEquipamento(chamado, chamado.getEquipamento().getId());
		}
		
		chamados.save(chamado);

		attributes.addFlashAttribute("sucesso", "Chamado salvo com sucesso!!");

		return new ModelAndView("redirect:/chamados");

	}
	
	@PostMapping("/salvaregeraratendimento")
	public ModelAndView salvaregeraratendimento(@Valid Chamado chamado, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return chamadoEquipamento(chamado, chamado.getEquipamento().getId());
		}
		
		Chamado c = chamados.saveAndFlush(chamado);

		attributes.addFlashAttribute("sucesso", "Chamado salvo com sucesso!!");

		return new ModelAndView("redirect:/atendimentos/novo/" + c.getNra());

	}
	
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		
		ModelAndView modelAndView = new ModelAndView("chamado/cadastro-chamado");
		
		Chamado c = chamados.getOne(id);
		
		modelAndView.addObject(c);

		return modelAndView;
	}
	
	@PostMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {

		chamados.delete(chamados.getOne(id));

		attributes.addFlashAttribute("sucesso", "Chamado excluido com sucesso!!");

		return "redirect:/chamados";
	}
	
	
	@ModelAttribute("ListaStatus")
	public List<Lista_de_Status> ListaStatus(){
		return Arrays.asList(Lista_de_Status.values());
	}
	
	@ModelAttribute("ListaTipos")
	public List<Lista_de_Tipos> ListaTipos(){
		return Arrays.asList(Lista_de_Tipos.values());
	}

}
