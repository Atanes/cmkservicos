package br.com.iridiumit.cmkservicos.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
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
import br.com.iridiumit.cmkservicos.modelos.Chamado;
import br.com.iridiumit.cmkservicos.modelos.Cliente;
import br.com.iridiumit.cmkservicos.modelos.Lista_de_Status;
import br.com.iridiumit.cmkservicos.modelos.Lista_de_Tipos;
import br.com.iridiumit.cmkservicos.relatorio.AtendimentosREL;
import br.com.iridiumit.cmkservicos.repository.Atendimentos;
import br.com.iridiumit.cmkservicos.repository.Chamados;
import br.com.iridiumit.cmkservicos.repository.Clientes;
import br.com.iridiumit.cmkservicos.repository.Usuarios;
import br.com.iridiumit.cmkservicos.repository.filtros.FiltroGeral;
import br.com.iridiumit.cmkservicos.utils.PageUtils;

@Controller
@RequestMapping("/atendimentos")
public class AtendimentoController {

	private static final String ORDERBYATENDIMENTO = "dataAtendimento";
	private static final int RECORDSPERPAGE = 6;

	@Autowired
	private Atendimentos atendimentos;

	@Autowired
	private Clientes clientes;

	@Autowired
	private Chamados chamados;

	@Autowired
	private Usuarios usuarios;

	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") FiltroGeral filtro,
			@PageableDefault(size = RECORDSPERPAGE, sort = ORDERBYATENDIMENTO, direction = Direction.ASC) Pageable pageable,
			HttpServletRequest httpServletRequest) {

		ModelAndView modelAndView = new ModelAndView("atendimento/lista-atendimentos");

		if (filtro.getTextoFiltro() == null) {
			modelAndView.addObject("atendimentos", atendimentos.findAll(pageable));
		} else {
			modelAndView.addObject("atendimentos",
					atendimentos.findByEquipamentoTipo(filtro.getTextoFiltro(), pageable));
		}
		
		PageUtils pageUtils = new PageUtils(httpServletRequest, pageable);

		modelAndView.addObject("controlePagina", pageUtils);

		return modelAndView;
	}


	@GetMapping("/novo/{id}")
	public ModelAndView atendimentoEquipamento(Atendimento atendimento, @PathVariable Long id) {

		ModelAndView modelAndView = new ModelAndView("atendimento/cadastro-atendimento");

		Chamado c = chamados.getOne(id);

		atendimento.setChamado(c);

		modelAndView.addObject("usuarios", usuarios.findAllByOrderByNome());

		modelAndView.addObject(atendimento);

		return modelAndView;
	}

	

	@GetMapping("/cliente/{id}")
	public ModelAndView SelecaoPorCliente(@ModelAttribute("filtro") FiltroGeral filtro, @PathVariable Integer id,
			@PageableDefault(size = RECORDSPERPAGE, sort = ORDERBYATENDIMENTO, direction = Direction.ASC) Pageable pageable,
			HttpServletRequest httpServletRequest) {

		Cliente c = clientes.getOne(id);

		ModelAndView modelAndView = new ModelAndView("atendimento/lista-RACliente");

		modelAndView.addObject(c);

		if (filtro.getTextoFiltro() == null) {
			modelAndView.addObject("atendimentos", atendimentos.findByCliente(id, pageable));
		} else {
			modelAndView.addObject("atendimentos",
					atendimentos.findByClienteTipo(id, filtro.getTextoFiltro(), pageable));
		}

		return modelAndView;

	}

	@DeleteMapping("excluir/{id}")
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {

		atendimentos.delete(atendimentos.getOne(id));

		attributes.addFlashAttribute("sucesso", "Atendimento excluida com sucesso!!");

		return "redirect:/atendimentos";
	}

	@GetMapping("editar/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		Atendimento a = atendimentos.getOne(id);

		return atendimentoEquipamento(a, a.getChamado().getNra());
	}

	@GetMapping("/novo")
	public ModelAndView novo(Atendimento atendimento) {

		ModelAndView modelAndView = new ModelAndView("/atendimento/cadastro-RAEquipamento");

		modelAndView.addObject(atendimento);

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Atendimento atendimento, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return novo(atendimento);
		}

		atendimentos.save(atendimento);

		Chamado c = chamados.getOne(atendimento.getChamado().getNra());

		c.setStatus("AGENDADO");

		chamados.save(c);

		attributes.addFlashAttribute("sucesso", "Atendimento salvo com sucesso!!");

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
	public List<Lista_de_Status> ListaStatus() {
		return Arrays.asList(Lista_de_Status.values());
	}

	@ModelAttribute("ListaTipos")
	public List<Lista_de_Tipos> ListaTipos() {
		return Arrays.asList(Lista_de_Tipos.values());
	}

}
