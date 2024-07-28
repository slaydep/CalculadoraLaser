package com.calculadoralaser.controllers;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calculadoralaser.models.entity.Calculo;
import com.calculadoralaser.models.entity.Madera;
import com.calculadoralaser.models.entity.Vector;
import com.calculadoralaser.models.service.ICalculoService;
import com.calculadoralaser.models.service.IMaderaService;
import com.calculadoralaser.models.service.IUploadFileService;
import com.calculadoralaser.models.service.IVectorService;
import com.calculadoralaser.util.paginator.PageRender;

@Controller
@RequestMapping("/vectores")
@SessionAttributes({ "vector","busqueda"})
public class VectoresController {

	@Autowired
	private IVectorService vectorService;

	@Autowired
	private IMaderaService maderaService;

	@Autowired
	private ICalculoService calculoService;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	@GetMapping(value = {"/uploads/{filename:.+}","/buscar/uploads/{filename:.+}"})
	public ResponseEntity<Resource> verVector(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.loadVector(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	
	@GetMapping(value = { "/listar", "/", "" })
	public String listar(@RequestParam(defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 10);
		
		Page<Vector> vectores = vectorService.findAll(pageRequest);
		PageRender<Vector> pageRender = new PageRender<>("/vectores/listar", vectores);
		model.addAttribute("titulo", "Listado de vectores");
		model.addAttribute("vectores", vectores);
		model.addAttribute("page", pageRender);
		model.addAttribute("total", vectores.getTotalElements());
		model.addAttribute("busqueda", "");
		return "vectores/listar";
	}
	
	@GetMapping(value = { "/buscar/{busqueda}","/buscar/"})
	public String buscar(@RequestParam(defaultValue = "0") int page,
			@RequestParam(name="busqueda" , required = false) String busqueda, 
			@PathVariable(value = "busqueda", required = false) String busqueda2,
			Model model) {
		String busq=busqueda!=null?busqueda:busqueda2;
		if(busq.length()==0) {
			return "redirect:/vectores/listar";
		}
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Vector> vectores = vectorService.findAllByNombre(busq,pageRequest);
		PageRender<Vector> pageRender = new PageRender<>("/vectores/buscar/"+busq, vectores);
		model.addAttribute("titulo", "Resultado Busqueda de vectores");
		model.addAttribute("vectores", vectores);
		model.addAttribute("page", pageRender);
		model.addAttribute("total", vectores.getTotalElements());
		model.addAttribute("busqueda", busq);
		
		return "vectores/listar";
	}
	

	@GetMapping(value = "/ver/{id}")
	public String verp(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Vector vector = vectorService.findVectorById(id);
		if (vector == null) {
			flash.addFlashAttribute("error", "El vector no existe en la base de datos");
			return "redirect:/vectores/listar";
		}
		model.addAttribute("vector", vector);
		model.addAttribute("titulo", "Detalle del vector: " + vector.getNombre());
		return "vectores/ver";
	}

	@GetMapping("/form")
	public String form(Model model) {

		List<Madera> maderas = maderaService.findAll();

		model.addAttribute("maderas", maderas);
		model.addAttribute("titulo", "Crear Nuevo Vector");
		model.addAttribute("accion", "Crear Nuevo");
		Vector vector = new Vector();
		vector.setPrecioFabrica(0);
		vector.setPrecioVenta(0);
		vector.setCreateAt(new Date());
		model.addAttribute("vector", vector);

		return "vectores/form";
	}

	@PostMapping("/form")
	public String guardar(@Valid Vector vector, BindingResult result, Model model,
			@RequestParam(name = "calculo_id[]", required = false) Long[] calculoId, SessionStatus status,
			RedirectAttributes flash, @RequestParam(name = "madera", required = true) Long idmadera) {

		vector.setMadera(maderaService.findOne(idmadera));
		if (calculoId != null) {
			Integer precioFabrica = 0;
			Integer precioVenta = 0;
			List<Calculo> calculos = new ArrayList<Calculo>();
			for (int i = 0; i < calculoId.length; i++) {
				Calculo calculo = calculoService.findOne(calculoId[i]);
				calculos.add(calculo);
				precioFabrica += calculo.getPrecioFabrica();
				precioVenta += calculo.getPrecioVenta();

			}
			vector.setPrecioFabrica(precioFabrica);
			vector.setPrecioVenta(precioVenta);
			vector.setCalculos(calculos);
		} else {
			vector.setCalculos(new ArrayList<Calculo>());
		}
		if (result.hasErrors()) {
			List<Madera> maderas = maderaService.findAll();
			model.addAttribute("maderas", maderas);
			model.addAttribute("titulo", "Crear Nuevo Vector");
			model.addAttribute("accion", "Crear Nuevo");
			return "vectores/form";
		}

		// if (!foto.isEmpty()) {
//		if (!foto.isEmpty()) {
//			if (vector.getId() != null && vector.getId() > 0 && vector.getFoto() != null
//					&& vector.getFoto().length() > 0) {
//
//				uploadFileService.deleteVector(vector.getFoto());
//			}
//
//			String uniqueFilename = null;
//			try {
//				uniqueFilename = uploadFileService.copyVector(foto);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			flash.addFlashAttribute("info", "Archivo '" + uniqueFilename + "' subido correctamente");
//
//			vector.setFoto(uniqueFilename);
//
//		}
//		else {
//			vector.setFoto("");
//		}

		vectorService.save(vector);
		String msg = (vector.getId() != null) ? "Vector Editado con Exito" : "Vector Creado con Exito";

		status.setComplete();
		flash.addFlashAttribute("success", msg);
		return "redirect:/vectores/listar";
	}

	@RequestMapping("/form/{id}")
	public String editar(Model model, @PathVariable Long id, RedirectAttributes flash) {

		Vector vector = null;

		if (id > 0) {
			vector = vectorService.findVectorById(id);
			if (vector == null) {
				flash.addFlashAttribute("error", "Vector No encontrado");
				return "redirect:/vectores/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El Id no puede ser cero");
			return "redirect:/vectores/listar";
		}
		List<Madera> maderas = maderaService.findAll();
		List<Calculo> calculos = vector.getCalculos();
		model.addAttribute("calculos", calculos);
		model.addAttribute("maderas", maderas);
		model.addAttribute("vector", vector);
		model.addAttribute("titulo", "Editar vector");
		model.addAttribute("accion", "Editar");
		return "vectores/form";
	}

	@RequestMapping("/eliminar/{id}")
	public String eliminar(Model model, @PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			Vector vector = vectorService.findVectorById(id);

			vectorService.delete(id);
			flash.addFlashAttribute("success", "Vector " + vector.getNombre() + " eliminado");

//			if (uploadFileService.delete(vector.getFoto())) {
//				flash.addFlashAttribute("success",
//						"Vector " + vector.getNombre() + ", Foto " + vector.getFoto() + " eliminados con exito!");
//			}

		}

		return "redirect:/vectores/listar";
	}
	
	

	@GetMapping(value = "/cargar-calculos/{term}", produces = { "application/json" })
	public @ResponseBody List<Calculo> cargarCalculos(@PathVariable String term) {
		return calculoService.findByDescripcion(term);
	}

}
