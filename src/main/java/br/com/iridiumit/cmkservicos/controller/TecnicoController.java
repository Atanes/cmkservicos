package br.com.iridiumit.cmkservicos.controller;

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

import br.com.iridiumit.cmkservicos.modelos.Atendimento;
import br.com.iridiumit.cmkservicos.modelos.UsuarioSistema;
import br.com.iridiumit.cmkservicos.repository.Atendimentos;
import br.com.iridiumit.cmkservicos.repository.filtros.FiltroGeral;
import br.com.iridiumit.cmkservicos.utils.PageUtils;

@Controller
@RequestMapping("/tecnico")
public class TecnicoController {
	
	private static final String ORDERBYATENDIMENTO = "dataAtendimento";
	private static final int RECORDSPERPAGE = 6;
	
	UsuarioSistema userLogin;
	
	@Autowired
	private Atendimentos atendimentos;
	
	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") FiltroGeral filtro,
			@PageableDefault(size = RECORDSPERPAGE, sort = ORDERBYATENDIMENTO, direction = Direction.ASC) Pageable pageable,
			HttpServletRequest httpServletRequest) {
		
		userLogin = ((UsuarioSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		ModelAndView modelAndView = new ModelAndView("atendimento/lista-atendimentos");

		if (filtro.getTextoFiltro() == null) {
			modelAndView.addObject("atendimentos", atendimentos.findByExecutor(userLogin.getUsuario().getNome(), pageable));
		} else {
			modelAndView.addObject("atendimentos",
					atendimentos.findByEquipamentoTipoExecutor(filtro.getTextoFiltro(), userLogin.getUsuario().getNome(), pageable));
		}

		modelAndView.addObject("controlePagina", new PageUtils(httpServletRequest, pageable));

		return modelAndView;
	}
	
	@GetMapping("/pendencias")
	public ModelAndView listarPendencias(@ModelAttribute("filtro") FiltroGeral filtro, 
			@PageableDefault(size = RECORDSPERPAGE, sort = ORDERBYATENDIMENTO, direction = Direction.ASC) Pageable pageable,
			HttpServletRequest httpServletRequest) {
		
		userLogin = ((UsuarioSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		ModelAndView modelAndView = new ModelAndView("atendimento/lista-pendencias");

		if (filtro.getTextoFiltro() == null) {
			modelAndView.addObject("atendimentos", atendimentos.findByExecutorAberto(userLogin.getUsuario().getNome(), pageable));
		} else {
			modelAndView.addObject("atendimentos",
					atendimentos.findByEquipamentoTipoExecutorAberto(filtro.getTextoFiltro(), userLogin.getUsuario().getNome(), pageable));
		}
		
		modelAndView.addObject("controlePagina", new PageUtils(httpServletRequest, pageable));

		return modelAndView;
	}
	
	@GetMapping("/realizar/{id}")
	public ModelAndView realizarAtendimento(@PathVariable Long id) {

		ModelAndView modelAndView = new ModelAndView("atendimento/realizar-atendimento");

		Atendimento atendimento = atendimentos.getOne(id);
		
		atendimento.getChamado().setStatus("ATENDIMENTO");

		modelAndView.addObject(atendimento);

		return modelAndView;
	}

	@PostMapping("/realizar/salvar")
	public ModelAndView realizarAtendimentoSalvar(@Valid Atendimento atendimento, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			System.out.println(result.getFieldErrorCount() + ", " + result.getFieldError());
			return realizarAtendimento(atendimento.getNumero());
		}
		
		atendimento.getChamado().setStatus("FINALIZADO");

		atendimentos.save(atendimento);

		attributes.addFlashAttribute("sucesso", "Atendimento finalizado e salvo com sucesso!!");

		return new ModelAndView("redirect:/tecnico/pendencias");

	}
	
	
}