package com.calculadoralaser.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calculadoralaser.models.entity.Calculo;
import com.calculadoralaser.models.entity.Foto;
import com.calculadoralaser.models.entity.Madera;
import com.calculadoralaser.models.entity.Maquina;
import com.calculadoralaser.models.entity.Vector;
import com.calculadoralaser.models.service.ICalculoService;
import com.calculadoralaser.models.service.IFotoService;
import com.calculadoralaser.models.service.IMaderaService;
import com.calculadoralaser.models.service.IMaquinaService;
import com.calculadoralaser.models.service.IVectorService;
import com.calculadoralaser.util.paginator.PageRender;

@Controller
@RequestMapping("/calculadora")
@SessionAttributes({ "madera", "maquina", "calculo" })
public class CalculadoraController {

	@Autowired
	private IMaderaService maderaService;

	@Autowired
	private IMaquinaService maquinaService;

	@Autowired
	private ICalculoService calculoService;

	@Autowired
	private IVectorService vectorService;

	@Autowired
	private IFotoService fotoService;

	@GetMapping(value = { "/listar", "/", "" })
	public String listar(@RequestParam(defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 12);
		Page<Calculo> calculos = calculoService.findAll(pageRequest);
		PageRender<Calculo> pageRender = new PageRender<>("/calculadora/listar", calculos);

		model.addAttribute("titulo", "Listado de calculos");
		model.addAttribute("calculos", calculos);
		model.addAttribute("page", pageRender);
		model.addAttribute("total", calculos.getTotalElements());

		return "calculadora/listar";
	}

	@GetMapping(value = { "/buscar/{busqueda}", "/buscar/" })
	public String buscar(@RequestParam(defaultValue = "0") int page,
			@RequestParam(name = "busqueda", required = false) String busqueda,
			@PathVariable(value = "busqueda", required = false) String busqueda2, Model model) {
		String busq = busqueda != null ? busqueda : busqueda2;
		if (busq.length() == 0) {
			return "redirect:/calculadora/listar";
		}
		Pageable pageRequest = PageRequest.of(page, 12);
		Page<Calculo> calculos = calculoService.findAllByDescripcion(busq, pageRequest);
		PageRender<Calculo> pageRender = new PageRender<>("/calculadora/buscar/" + busq, calculos);
		model.addAttribute("titulo", "Resultado Busqueda de calculos");
		model.addAttribute("calculos", calculos);
		model.addAttribute("page", pageRender);
		model.addAttribute("total", calculos.getTotalElements());
		model.addAttribute("busqueda", busq);

		return "calculadora/listar";
	}

	@GetMapping({ "/form", "/form/foto/{id_foto}"})
	public String form(Model model,@PathVariable(name = "id_foto", required = false) Long IdFoto) {
		
		List<Vector> vectors=new ArrayList<>();
		if (IdFoto != null) {
			Optional<Foto> fot = fotoService.findById(IdFoto);
			if (fot.isPresent()) {
				String nom=fot.get().getNombre();
				vectors=vectorService.findByDescripcion(nom.substring(0, (nom.length()-4)));
			}
		}
		Calculo calculo;
		calculo = new Calculo();
		calculo.setCreatedAt(new Date());
		// valores por defecto del calculo
		calculo.setHoras(0);
		calculo.setMinutos(0);
		calculo.setSegundos(0);
		calculo.setLado1((float) 0);
		calculo.setLado2((float) 0);
		calculo.setFactor((float) 2);
		List<Madera> maderas = maderaService.findAll();
		List<Maquina> maquinas = maquinaService.findAll();
		List<Calculo> calculos = calculoService.findAll();
		model.addAttribute("maquinas", maquinas);
		model.addAttribute("maderas", maderas);
		model.addAttribute("calculos", calculos);
		model.addAttribute("calculo", calculo);
		model.addAttribute("titulo", "Calculadora Laser CNC");
		model.addAttribute("accion", "Calcular");
		model.addAttribute("vectores", vectors);
		return "calculadora/form";

	}

	@PostMapping({ "/form"})
	public String guardar(@Valid @ModelAttribute("calculo") Calculo calculo, BindingResult result, Model model,
			@RequestParam(name = "maquina", required = true) Long idmaquina,
			@RequestParam(name = "madera", required = true) Long idmadera,
			@RequestParam(name = "vector_id[]", required = false) Long[] vectorId, 
			SessionStatus status,
			RedirectAttributes flash,
			@RequestParam(value = "check_si", required = false) boolean continuar) {
		Vector vector = null;
		calculo.setMaquina(maquinaService.findOne(idmaquina));
		calculo.setMadera(maderaService.findOne(idmadera));
		if (vectorId != null) {
			List<Vector> vectores = new ArrayList<Vector>();
			for (int i = 0; i < vectorId.length; i++) {
				vector = vectorService.findVectorById(vectorId[i]);
				vectores.add(vector);

			}
			calculo.setVectores(vectores);
		} else {
			calculo.setVectores(new ArrayList<Vector>());
		}
		if (result.hasErrors()) {

			System.out.println("errores :" + result.getAllErrors());
			List<Madera> maderas = maderaService.findAll();
			List<Maquina> maquinas = maquinaService.findAll();
			List<Calculo> calculos = calculoService.findAll();
			model.addAttribute("maquinas", maquinas);
			model.addAttribute("maderas", maderas);
			model.addAttribute("calculo", calculo);
			model.addAttribute("titulo", "Crear Nuevo calculo");
			model.addAttribute("accion", "Crear Nueva");
			return "calculadora/form";
		}

		String msg = (calculo.getId() != null) ? "Calculo Editado con Exito" : "Calculo Creado con Exito";

		calculoService.save(calculo);

		status.setComplete();
		flash.addFlashAttribute("success", msg);
		if (continuar) {

			Optional<Foto> foto = fotoService.findByNombre(vector.getDescripcion()+".jpg");
			System.out.println("nombre: "+vector.getNombre()+".jpg");
			System.out.println("ruta: "+ (foto.isPresent()?"redirect:/productos/form/foto/"+foto.get().getId():"redirect:/productos/form/"));
			return foto.isPresent()?"redirect:/productos/form/foto/"+foto.get().getId():"redirect:/productos/form/";
			
		}
		return "redirect:/calculadora/listar";
	}

	@RequestMapping("/form/{id}")
	public String editar(Model model, @PathVariable Long id, RedirectAttributes flash) {

		Calculo calculo = null;

		if (id > 0) {

			calculo = calculoService.findOne(id);
			List<Madera> maderas = maderaService.findAll();
			List<Maquina> maquinas = maquinaService.findAll();
			List<Calculo> calculos = calculoService.findAll();
			model.addAttribute("maquinas", maquinas);
			model.addAttribute("maderas", maderas);
			model.addAttribute("calculos", calculos);
			model.addAttribute("calculo", calculo);
			if (calculo == null) {
				flash.addFlashAttribute("error", "Calculo No encontrado");
				return "redirect:/calculadora/form";
			}
		} else {
			flash.addFlashAttribute("error", "El Id no puede ser cero");
			return "redirect:/calculadora/form";
		}
		List<Vector> vectores = calculo.getVectores();
		model.addAttribute("vectores", vectores);
		model.addAttribute("titulo", "Editar Calculo");
		model.addAttribute("accion", "Recalcular");
		return "calculadora/form";
	}

	@RequestMapping("/eliminar/{id}")
	public String eliminar(Model model, @PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			Calculo calculo = calculoService.findOne(id);

			calculoService.delete(id);
			flash.addFlashAttribute("success", "Calculo " + calculo.getId() + " eliminado");

		}

		return "redirect:/calculadora/listar";
	}

}
