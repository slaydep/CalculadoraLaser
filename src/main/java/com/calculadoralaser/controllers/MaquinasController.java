package com.calculadoralaser.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calculadoralaser.models.entity.Maquina;
import com.calculadoralaser.models.service.IMaquinaService;

@Controller
@RequestMapping("/maquinas")
@SessionAttributes({ "maquina" })
public class MaquinasController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IMaquinaService maquinaService;



	@GetMapping(value = "/listar")
	public String listar(Model model, Authentication authentication, RedirectAttributes flash) {

	

			if (authentication != null) {
				logger.info("Maquinas, Hola Usuario autenticado: " + authentication.getName());
			}
			List<Maquina> maquinas = maquinaService.findAll();
			model.addAttribute("maquinas", maquinas);
			return "maquinas/listar";
	
	}

	@GetMapping("/form")
	public String form(Model model) {

			model.addAttribute("titulo", "Crear Nueva Maquina");
			model.addAttribute("accion", "Crear Nueva");
			Maquina maquina = new Maquina();
			maquina.setCreateAt(new Date());
			model.addAttribute("maquina", maquina);

			return "maquinas/form";
		
	}

	@PostMapping("/form")
	public String guardar(@Valid Maquina maquina, BindingResult result, Model model, SessionStatus status,
			RedirectAttributes flash) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Nueva Maquina");
			model.addAttribute("accion", "Crear Nueva");
			return "maquinas/form";
		}

		String msg = (maquina.getId() != null) ? "Maquina Editada con Exito" : "Maquina Creada con Exito";
		maquinaService.save(maquina);
		status.setComplete();
		flash.addFlashAttribute("success", msg);
		return "redirect:/maquinas/listar";
	}

	@RequestMapping("/form/{id}")
	public String editar(Model model, @PathVariable Long id, RedirectAttributes flash) {

		Maquina maquina = null;

		if (id > 0) {
			maquina = maquinaService.findOne(id);
			if (maquina == null) {
				flash.addFlashAttribute("error", "Maquina No encontrada");
				return "redirect:/maquinas/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El Id no puede ser cero");
			return "redirect:/maquinas/listar";
		}
		model.addAttribute("maquina", maquina);
		model.addAttribute("titulo", "Editar maquina");
		model.addAttribute("accion", "Editar");
		return "maquinas/form";
	}

	@RequestMapping("/eliminar/{id}")
	public String eliminar(Model model, @PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			Maquina maquina = maquinaService.findOne(id);

			maquinaService.delete(id);
			flash.addFlashAttribute("success", "Maquina " + maquina.getNombre() + " eliminada");

		}

		return "redirect:/maquinas/listar";
	}

}
