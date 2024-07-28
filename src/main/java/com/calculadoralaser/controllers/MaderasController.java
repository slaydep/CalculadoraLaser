package com.calculadoralaser.controllers;

import java.security.Principal;
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

import com.calculadoralaser.models.entity.Madera;
import com.calculadoralaser.models.service.IMaderaService;

@Controller
@RequestMapping("/maderas")
@SessionAttributes({ "madera" })
public class MaderasController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IMaderaService maderaService;



	@GetMapping(value = "/listar")
	public String listar(Model model, Authentication authentication, RedirectAttributes flash, Principal principal) {

	

			if (authentication != null) {
				logger.info("Maderas, Hola Usuario autenticado: " + authentication.getName());
			}
			List<Madera> maderas = maderaService.findAll();
			model.addAttribute("maderas", maderas);

			return "maderas/listar";
		
	}

	@GetMapping("/form")
	public String form(Model model) {

			model.addAttribute("titulo", "Crear Nueva Madera");
			model.addAttribute("accion", "Crear Nueva");
			Madera madera = new Madera();
			madera.setCreateAt(new Date());
			model.addAttribute("madera", madera);

			return "maderas/form";
	
	}

	@PostMapping("/form")
	public String guardar(@Valid Madera madera, BindingResult result, Model model, SessionStatus status,
			RedirectAttributes flash) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Nueva Madera");
			model.addAttribute("accion", "Crear Nueva");
			return "maderas/form";
		}

		String msg = (madera.getId() != null) ? "Madera Editada con Exito" : "Madera Creada con Exito";
		maderaService.save(madera);
		status.setComplete();
		flash.addFlashAttribute("success", msg);
		return "redirect:/maderas/listar";
	}

	@RequestMapping("/form/{id}")
	public String editar(Model model, @PathVariable Long id, RedirectAttributes flash) {

		Madera madera = null;

		if (id > 0) {
			madera = maderaService.findOne(id);
			if (madera == null) {
				flash.addFlashAttribute("error", "Madera No encontrada");
				return "redirect:/maderas/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El Id no puede ser cero");
			return "redirect:/maderas/listar";
		}
		model.addAttribute("madera", madera);
		model.addAttribute("titulo", "Editar madera");
		model.addAttribute("accion", "Editar");
		return "maderas/form";
	}

	@RequestMapping("/eliminar/{id}")
	public String eliminar(Model model, @PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			Madera madera = maderaService.findOne(id);

			maderaService.delete(id);
			flash.addFlashAttribute("success", "Madera " + madera.getNombre() + " eliminada");

		}

		return "redirect:/maderas/listar";
	}

}
