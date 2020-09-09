package br.com.iridiumit.cmkservicos.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.iridiumit.cmkservicos.modelos.Apontamento;
import br.com.iridiumit.cmkservicos.modelos.Atendimento;
import br.com.iridiumit.cmkservicos.modelos.UsuarioSistema;
import br.com.iridiumit.cmkservicos.repository.Apontamentos;
import br.com.iridiumit.cmkservicos.repository.Atendimentos;
import br.com.iridiumit.cmkservicos.repository.Usuarios;

@Controller
@RequestMapping("/tecnico/apontamento")
public class ApontamentoConroller {

	UsuarioSistema userLogin;

	@Autowired
	private Atendimentos atendimentos;

	@Autowired
	private Usuarios usuarios;

	@Autowired
	private Apontamentos apontamentos;

	@GetMapping("{id}")
	public ModelAndView apontamento(@PathVariable Long id) {

		userLogin = ((UsuarioSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		Atendimento a = atendimentos.getOne(id);

		Apontamento apontamento = new Apontamento();

		apontamento.setDataApontamento(LocalDate.now());

		apontamento.setResponsavel(userLogin.getUsuario().getNome());

		apontamento.setTecnico(userLogin.getUsuario().getNome());

		ModelAndView modelAndView = new ModelAndView("atendimento/apontamento/cadastro-apontamento");

		modelAndView.addObject("tecnicos", usuarios.UsuariosPorPermissao("ROLE_CMK_TECNICO"));

		modelAndView.addObject(apontamento);

		modelAndView.addObject(a);

		return modelAndView;
	}

	@PostMapping("salvar/{id}")
	public ModelAndView salvarApontamento(@PathVariable Long id, @Valid Apontamento apontamento, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			System.out.println(result.getErrorCount());
			return apontamento(id);
		}

		apontamento.setDataApontamento(LocalDate.now());
		apontamentos.save(apontamento);

		attributes.addFlashAttribute("sucesso", "Apontamento(s) salvo(s) com sucesso!!");

		return new ModelAndView("redirect:/tecnico/pendencias");

	}
}
