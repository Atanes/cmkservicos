package br.com.iridiumit.cmkservicos.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.iridiumit.cmkservicos.modelos.Equipamento;
import br.com.iridiumit.cmkservicos.relatorio.EquipamentoREL;
import br.com.iridiumit.cmkservicos.repository.Clientes;
import br.com.iridiumit.cmkservicos.repository.Equipamentos;
import br.com.iridiumit.cmkservicos.repository.filtros.FiltroGeral;
import br.com.iridiumit.cmkservicos.utils.PageUtils;

@Controller
@RequestMapping("/administracao/equipamentos")
public class EquipamentoController {

	private static final String ORDERBYEQUIPAMENTO = "nrcmk";
	private static final int RECORDSPERPAGE = 10;

	@Autowired
	private Equipamentos equipamentos;

	@Autowired
	private Clientes clientes;

	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") FiltroGeral filtro,
			@PageableDefault(size = RECORDSPERPAGE, sort = ORDERBYEQUIPAMENTO, direction = Direction.ASC) Pageable pageable,
			HttpServletRequest httpServletRequest) {

		ModelAndView modelAndView = new ModelAndView("administracao/equipamento/lista-equipamentos");

		if (filtro.getTextoFiltro() == null) {
			modelAndView.addObject("equipamentos", equipamentos.findAll(pageable));
		} else {
			modelAndView.addObject("equipamentos",
					equipamentos.findByTipoContainingIgnoreCase(filtro.getTextoFiltro(), pageable));
		}

		PageUtils pageUtils = new PageUtils(httpServletRequest, pageable);

		modelAndView.addObject("controlePagina", pageUtils);

		return modelAndView;
	}

	@PostMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {

		try {
			
			equipamentos.delete(equipamentos.getOne(id));
			
			attributes.addFlashAttribute("sucesso", "Equipamento excluido com sucesso!!");
			
		} catch (DataIntegrityViolationException e) {
			
			System.out.println("Mensagem de erro: " + e.getMessage());
			
			attributes.addFlashAttribute("alerta", "Equipamento não pode ser excluido, existem chamados relacionados a ele!!");
		}

		return "redirect:/administracao/equipamentos";
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(equipamentos.getOne(id));
	}

	@GetMapping("/incluirEquipamento/{id}")
	public ModelAndView incluirEquipamento(@PathVariable Integer id) {

		ModelAndView modelAndView = new ModelAndView("administracao/equipamento/cadastro-equipamento");

		Equipamento e = new Equipamento(clientes.getOne(id));

		modelAndView.addObject(e);

		return modelAndView;
	}

	@GetMapping("/novo")
	public ModelAndView novo(Equipamento equipamento) {
		ModelAndView modelAndView = new ModelAndView("administracao/equipamento/cadastro-equipamento");

		modelAndView.addObject(equipamento);

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Equipamento equipamento, BindingResult result, RedirectAttributes attributes) {

		Equipamento e = equipamentos.findByNrcmk(equipamento.getNrcmk());

		if (e != null) {
			if (!e.getId().equals(equipamento.getId())) {

				result.rejectValue("nrcmk", "nrcmk.existente");
			}
		}

		if (result.hasErrors()) {
			return novo(equipamento);
		}

		equipamentos.save(equipamento);

		attributes.addFlashAttribute("sucesso", "Equipamento salvo com sucesso!!");

		return new ModelAndView("redirect:/administracao/equipamentos");

	}

	@GetMapping(value = "/rel-equipamentos", produces = MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody byte[] getRelClientes() throws IOException {

		EquipamentoREL relatorio = new EquipamentoREL();

		try {
			relatorio.imprimir(equipamentos.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}

		InputStream in = this.getClass().getResourceAsStream("/relatorios/Relatorio_de_equipamentos.pdf");
		return IOUtils.toByteArray(in);

	}

}
