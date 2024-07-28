package com.calculadoralaser.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calculadoralaser.models.entity.Foto;
import com.calculadoralaser.models.service.IFotoService;
import com.calculadoralaser.models.service.IUploadFileService;
import com.calculadoralaser.util.paginator.PageRender;

@Controller
@RequestMapping("/fotos")
@SessionAttributes({ "foto" })
public class FotosController {

	@Autowired
	private IFotoService fotoService;

	@Autowired
	private IUploadFileService uploadFileService;

	
	
	@GetMapping(value = {"/uploads/{filename:.+}","/buscar/uploads/{filename:.+}"})
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.loadFoto(filename);
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

		Page<Foto> fotos = fotoService.findAll(pageRequest);
		PageRender<Foto> pageRender = new PageRender<>("/fotos/listar", fotos);
		model.addAttribute("titulo", "Listado de fotos");
		model.addAttribute("fotos", fotos);
		model.addAttribute("page", pageRender);
		model.addAttribute("total", fotos.getTotalElements());
		model.addAttribute("busqueda", "");
		return "fotos/listar";
	}

	@GetMapping(value = { "/buscar/{busqueda}", "/buscar/" })
	public String buscar(@RequestParam(defaultValue = "0") int page,
			@RequestParam(name = "busqueda", required = false) String busqueda,
			@PathVariable(value = "busqueda", required = false) String busqueda2, Model model) {
		String busq = busqueda != null ? busqueda : busqueda2;
		if (busq.length() == 0) {
			return "redirect:/fotos/listar";
		}
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Foto> fotos = fotoService.findAllByNombre(busq, pageRequest);
		PageRender<Foto> pageRender = new PageRender<>("/fotos/buscar/" + busq, fotos);
		model.addAttribute("titulo", "Resultado Busqueda de fotos");
		model.addAttribute("fotos", fotos);
		model.addAttribute("page", pageRender);
		model.addAttribute("total", fotos.getTotalElements());
		model.addAttribute("busqueda", busq);

		return "fotos/listar";
	}

	@GetMapping(value = "/ver/{id}")
	public String verp(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Optional<Foto> foto = fotoService.findById(id);
		if (foto.isEmpty()) {
			flash.addFlashAttribute("error", "La foto no existe en la base de datos");
			return "redirect:/fotos/listar";
		}
		model.addAttribute("foto", foto.get());
		model.addAttribute("titulo", "Detalle de la foto: " + foto.get().getNombre());
		return "fotos/ver";
	}

	@GetMapping("/form")
	public String form(Model model) {

		model.addAttribute("titulo", "Crear Nueva Foto");
		model.addAttribute("accion", "Crear Nuevo");
		Foto foto = new Foto();
		foto.setCreateAt(new Date());
		model.addAttribute("foto", foto);

		return "fotos/form";
	}

	@PostMapping("/form")
	public String guardar(Model model,
			@RequestParam(name = "fot", required = true) MultipartFile[] fot, SessionStatus status,
			RedirectAttributes flash) {

		
		String msg = "";
		if (fot.length > 0) {

			for (MultipartFile multipartFile : fot) {
				String uniqueFilename = multipartFile.getOriginalFilename();
				try {
					if (uploadFileService.deleteFoto(uniqueFilename)){
						
						Optional<Foto> opt=fotoService.findByNombre(uniqueFilename);
						if (opt.isPresent()) {
							fotoService.delete(opt.get().getId());
						}
						
					}
					uniqueFilename = uploadFileService.copyFoto(multipartFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Foto fotoo=new Foto();
				fotoo.setNombre(uniqueFilename);
				fotoo.setFoto(uniqueFilename);
				fotoo.setCreateAt(new Date());
				msg.concat(uniqueFilename+", ");
				fotoService.save(fotoo);
			}

		}

		flash.addFlashAttribute("info", "Archivos: '" + msg + "' subido correctamente");
		

		status.setComplete();
		flash.addFlashAttribute("success", msg);
		return "redirect:/fotos/listar";
	}

	

	@RequestMapping("/eliminar/{id}")
	public String eliminar(Model model, @PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			Optional<Foto> foto = fotoService.findById(id);
			if (foto.isPresent()) {
				fotoService.delete(id);
				uploadFileService.deleteFoto(foto.get().getFoto());
			}

			flash.addFlashAttribute("success", "Foto " + foto.get().getNombre() + " eliminada");

//			if (uploadFileService.delete(vector.getFoto())) {
//				flash.addFlashAttribute("success",
//						"Vector " + vector.getNombre() + ", Foto " + vector.getFoto() + " eliminados con exito!");
//			}

		}

		return "redirect:/fotos/listar";
	}

	@GetMapping(value = "/cargar-fotos/{term}", produces = { "application/json" })
	public @ResponseBody List<Foto> cargarFotos(@PathVariable String term) {
		
		List<Foto> consulta=fotoService.findByNombres(term);
		
		
		return consulta;
	}
}
