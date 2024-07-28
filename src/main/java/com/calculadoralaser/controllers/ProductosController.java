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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calculadoralaser.models.entity.Foto;
import com.calculadoralaser.models.entity.Producto;
import com.calculadoralaser.models.entity.Vector;
import com.calculadoralaser.models.service.IFotoService;
import com.calculadoralaser.models.service.IProductoService;
import com.calculadoralaser.models.service.IVectorService;
import com.calculadoralaser.util.paginator.PageRender;

@Controller
@RequestMapping("/productos")
@SessionAttributes({ "producto", "madera", "maquina" })
public class ProductosController {

	@Autowired
	private IProductoService productoService;

	@Autowired
	private IVectorService vectorService;

	@Autowired
	private IFotoService fotoService;

//	@Autowired
//	private IUploadFileService uploadFileService;

	@GetMapping(value = "/ver/{id}")
	public String verp(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Producto producto = productoService.findProductoById(id);
		if (producto == null) {
			flash.addFlashAttribute("error", "El producto no existe en la base de datos");
			return "redirect:/productos/listar";
		}
		model.addAttribute("producto", producto);
		model.addAttribute("titulo", "Detalle del producto: " + producto.getNombre());
		return "productos/ver";

	}

	@GetMapping(value = { "/listar", "/", "" })
	public String listar(@RequestParam(defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 12);
		Page<Producto> productos = productoService.findAll(pageRequest);
		PageRender<Producto> pageRender = new PageRender<>("/productos/listar", productos);

		model.addAttribute("titulo", "Listado de productos");
		model.addAttribute("productos", productos);
		model.addAttribute("page", pageRender);
		model.addAttribute("total", productos.getTotalElements());

		return "productos/listar";
	}

	@GetMapping(value = { "/buscar/{busqueda}", "/buscar/" })
	public String buscar(@RequestParam(defaultValue = "0") int page,
			@RequestParam(name = "busqueda", required = false) String busqueda,
			@PathVariable(value = "busqueda", required = false) String busqueda2, Model model) {
		String busq = busqueda != null ? busqueda : busqueda2;
		if (busq.length() == 0) {
			return "redirect:/productos/listar";
		}
		System.out.println("busq :" + busq);
		Pageable pageRequest = PageRequest.of(page, 12);
		Page<Producto> productos = productoService.findAllByDescripcion(busq, pageRequest);
		PageRender<Producto> pageRender = new PageRender<>("/productos/buscar/" + busq, productos);
		model.addAttribute("titulo", "Resultado Busqueda de productos");
		model.addAttribute("productos", productos);
		model.addAttribute("page", pageRender);
		model.addAttribute("total", productos.getTotalElements());
		model.addAttribute("busqueda", busq);

		return "productos/listar";
	}

	@GetMapping({ "/form", "/form/foto/{id_foto}" })
	public String form(Model model, @PathVariable(name = "id_foto", required = false) Long IdFoto,
			@RequestParam(name = "vector_id[]", required = false) Long[] vectorId) {

		model.addAttribute("titulo", "Crear Nuevo Producto");
		model.addAttribute("accion", "Crear Nuevo");
		Producto producto = new Producto();
		producto.setPrecioFabrica(0);
		producto.setPrecioVenta(0);
		producto.setCreateAt(new Date());
		List<Foto> fotos=new ArrayList<>();
		List<Vector> vectors=new ArrayList<>();
		
		if (IdFoto != null) {
			Optional<Foto> fot = fotoService.findById(IdFoto);
			if (fot.isPresent()) {
				String nom=fot.get().getNombre();
				fotos=fotoService.findByNombres(nom);
				producto.setNombre(nom.substring(0, nom.indexOf("n-")));
				producto.setCodigo(nom.substring((nom.indexOf("n-")+2), (nom.length()-4)));
				producto.setDescripcion(nom.substring(0, (nom.length()-4)));
				//fotos.add(fot.get());
				vectors=vectorService.findByDescripcion(nom.substring(0, (nom.length()-4)));
				int precio_fabrica=0;
				int precio_venta=0;
				for (Vector vector : vectors) {
					precio_fabrica+=vector.getPrecioFabrica();
					precio_venta+=vector.getPrecioVenta();
				}
				producto.setPrecioFabrica(precio_fabrica);
				producto.setPrecioVenta(precio_venta);
			}
		}
		if(vectorId!=null) {
			

		}
		model.addAttribute("producto", producto);
		model.addAttribute("fotos", fotos);
		model.addAttribute("vectores", vectors);

		return "productos/form";
	}

	@PostMapping({ "/form"})
	public String guardar(@Valid Producto producto, BindingResult result, Model model,
			@RequestParam(name = "vector_id[]", required = false) Long[] vectorId,
			@RequestParam(name = "foto_id[]", required = false) Long[] fotoId, SessionStatus status,
			RedirectAttributes flash) {

//		if(productoService.findByNombre(producto.getNombre()).size()>1) {
//			result.rejectValue("nombre", "Unique", "El producto ya existe.");
//		}
		if (fotoId != null) {
			List<Foto> fotos = new ArrayList<Foto>();
			for (int i = 0; i < fotoId.length; i++) {
				Optional<Foto> foto = fotoService.findById(fotoId[i]);
				if (foto.isPresent()) {
					fotos.add(foto.get());
				}
			}
			producto.setFotos(fotos);
		} else {
			producto.setFotos(new ArrayList<Foto>());
		}

		if (vectorId != null) {
			Integer precioFabrica = 0;
			Integer precioVenta = 0;
			List<Vector> vectores = new ArrayList<Vector>();
			for (int i = 0; i < vectorId.length; i++) {
				Vector vector = vectorService.findVectorById(vectorId[i]);
				if (vector != null) {
					vectores.add(vector);
					precioFabrica += vector.getPrecioFabrica();
					precioVenta += vector.getPrecioVenta();
				}
			}
			producto.setPrecioFabrica(precioFabrica);
			producto.setPrecioVenta(precioVenta);
			producto.setVectores(vectores);
		} else {
			producto.setVectores(new ArrayList<Vector>());
		}

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Nuevo Producto");
			model.addAttribute("accion", "Crear Nuevo");
			return "productos/form";
		}

		productoService.save(producto);
		String msg = (producto.getId() != null) ? "Producto Editado con Exito" : "Producto Creado con Exito";

		status.setComplete();
		flash.addFlashAttribute("success", msg);
		return "redirect:/productos/listar";
	}

	@RequestMapping("/form/{id}")
	public String editar(Model model, @PathVariable Long id, RedirectAttributes flash) {

		Producto producto = null;

		if (id > 0) {
			producto = productoService.findProductoById(id);
			if (producto == null) {
				flash.addFlashAttribute("error", "Producto No encontrado");
				return "redirect:/productos/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El Id no puede ser cero");
			return "redirect:/productos/listar";
		}
		List<Vector> vectores = producto.getVectores();
		List<Foto> fotos = producto.getFotos();
		model.addAttribute("fotos", fotos);
		model.addAttribute("vectores", vectores);
		model.addAttribute("producto", producto);
		model.addAttribute("titulo", "Editar producto");
		model.addAttribute("accion", "Editar");
		return "productos/form";
	}

	@RequestMapping("/eliminar/{id}")
	public String eliminar(Model model, @PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			Producto producto = productoService.findProductoById(id);

			productoService.delete(id);
			flash.addFlashAttribute("success", "Producto " + producto.getNombre() + " eliminado");

//			if (uploadFileService.delete(producto.getFoto())) {
//				flash.addFlashAttribute("success",
//						"Producto " + producto.getNombre() + ", Foto " + producto.getFoto() + " eliminados con exito!");
//			}

		}

		return "redirect:/productos/listar";
	}

	@GetMapping(value = "/cargar-vectores/{term}", produces = { "application/json" })
	public @ResponseBody List<Vector> cargarVectores(@PathVariable String term) {
		return vectorService.findByDescripcion(term);
	}

}
