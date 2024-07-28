package com.calculadoralaser.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calculadoralaser.models.entity.ConceptosGastos;
import com.calculadoralaser.models.entity.ConceptosIngresos;
import com.calculadoralaser.models.entity.Cuentas;
import com.calculadoralaser.models.entity.TransaccionesGastos;
import com.calculadoralaser.models.entity.TransaccionesIngresos;
import com.calculadoralaser.models.service.IConceptosGastosService;
import com.calculadoralaser.models.service.IConceptosIngresosService;
import com.calculadoralaser.models.service.ICuentasService;
import com.calculadoralaser.models.service.ITransaccionesGastosService;
import com.calculadoralaser.models.service.ITransaccionesIngresosService;

@Controller
@RequestMapping("/ventas")
@SessionAttributes({"cuentas","conceptosGastos","transaccionesGastos"})
public class VentasController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private ITransaccionesGastosService transaccionesGastosService;

	@Autowired
	private ITransaccionesIngresosService transaccionesIngresosService;
	
	@Autowired
	private ICuentasService cuentasService;

	@Autowired
	private IConceptosGastosService conceptosGastosService;

	@Autowired
	private IConceptosIngresosService conceptosIngresosService;

	@GetMapping(value = "/listar")
	public String listar(Model model,RedirectAttributes flash) {
		
		
		
		List<Cuentas> cuentas=cuentasService.findAll();
		List<ConceptosGastos> conceptosGastos=conceptosGastosService.findAll();
		List<ConceptosIngresos> conceptosIngresos=conceptosIngresosService.findAll();
		List<TransaccionesGastos> transaccionesGastos=transaccionesGastosService.findAll();
		List<TransaccionesIngresos> transaccionesIngresos=transaccionesIngresosService.findAll();
		//System.out.println("transaccions Gastos: "+transaccionesgastos.get(0).getCuenta());
		model.addAttribute("titulocuentas", "Listado de Cuentas");
		model.addAttribute("tituloConceptosGastos", "Listado de Conceptos de Gastos");
		model.addAttribute("tituloConceptosIngresos", "Listado de Conceptos de Ingresos");
		model.addAttribute("titulotransaccionesGastos", "Listado de Transacciones de Gastos");
		model.addAttribute("titulotransaccionesIngresos", "Listado de Transacciones de Ingresos");
		model.addAttribute("cuentas", cuentas);
		model.addAttribute("conceptosGastos",conceptosGastos);
		model.addAttribute("conceptosIngresos",conceptosIngresos);
		model.addAttribute("transaccionesGastos",transaccionesGastos);
		model.addAttribute("transaccionesIngresos",transaccionesIngresos);
		
		return "ventas/listar";
	}
	@GetMapping("/formcuentas")
	public String formcuentas(Model model) {
		
		
		model.addAttribute("titulo", "Crear Nueva cuenta");
		model.addAttribute("accion", "Crear Nuevo");
		Cuentas cuentas=new Cuentas();
		cuentas.setCreateAt(new Date());
		model.addAttribute("cuentas", cuentas);
		//System.out.println("hasta aqui llegue");
		return "ventas/formcuentas";
	}
	
	@PostMapping("/formcuentas")
	public String guardarcuentas(@Valid Cuentas cuentas, BindingResult result, Model model,
			SessionStatus status, RedirectAttributes flash) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Nueva Cuenta");
			model.addAttribute("accion", "Crear Nuevo");
			return "ventas/formcuentas";
		}

		
		String msg = (cuentas.getId() != null) ? "Cuenta Editada con Exito" : "Cuenta Creada con Exito";
		cuentasService.save(cuentas);
		status.setComplete();
		flash.addFlashAttribute("success", msg);
		return "redirect:/ventas/listar";
	}
	
	@RequestMapping("/formcuentas/{id}")
	public String editarcuentas(Model model, @PathVariable Long id, RedirectAttributes flash) {
		
		Cuentas cuentas=null;
		

		if (id > 0) {
			cuentas = cuentasService.findOne(id);
			if (cuentas == null) {
				flash.addFlashAttribute("error", "Cuenta No encontrada");
				return "redirect:/ventas/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El Id no puede ser cero");
			return "redirect:/ventas/listar";
		}
		model.addAttribute("cuentas", cuentas);
		model.addAttribute("titulo", "Editar cuenta");
		model.addAttribute("accion", "Editar");
		return "ventas/formcuentas";
	}
	
	@RequestMapping("/eliminarcuentas/{id}")
	public String eliminarcuentas(Model model, @PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			Cuentas cuentas = cuentasService.findOne(id);

			cuentasService.delete(id);
			flash.addFlashAttribute("success", "Cuenta " + cuentas.getNombre() + " eliminada");

			

		}

		return "redirect:/ventas/listar";
	}
	
	@GetMapping("/formconceptosgastos")
	public String formconceptosgastos(Model model) {
		
		
		model.addAttribute("titulo", "Crear Nuevo Concepto de gasto");
		model.addAttribute("accion", "Crear Nuevo");
		ConceptosGastos conceptosGastos=new ConceptosGastos();
		conceptosGastos.setCreateAt(new Date());
		model.addAttribute("conceptosGastos", conceptosGastos);
		return "ventas/formconceptosgastos";
	}
	
	@PostMapping("/formconceptosgastos")
	public String guardarconceptosgastos(@Valid ConceptosGastos conceptosGastos, BindingResult result, Model model,
			SessionStatus status, RedirectAttributes flash) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Nuevo Concepto de gasto");
			model.addAttribute("accion", "Crear Nuevo");
			return "ventas/formconceptosgastos";
		}

		
		String msg = (conceptosGastos.getId() != null) ? "Concepto de Gasto Editado con Exito" : "Concepto de Gasto Creado con Exito";
		conceptosGastosService.save(conceptosGastos);
		status.setComplete();
		flash.addFlashAttribute("success", msg);
		return "redirect:/ventas/listar";
	}
	
	@RequestMapping("/formconceptosgastos/{id}")
	public String editarconceptosgastos(Model model, @PathVariable Long id, RedirectAttributes flash) {
		
		ConceptosGastos conceptosGastos=null;
		

		if (id > 0) {
			conceptosGastos = conceptosGastosService.findOne(id);
			if (conceptosGastos == null) {
				flash.addFlashAttribute("error", "Concepto de Gasto No encontrado");
				return "redirect:/ventas/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El Id no puede ser cero");
			return "redirect:/ventas/listar";
		}
		model.addAttribute("conceptosGastos", conceptosGastos);
		model.addAttribute("titulo", "Editar Concepto de Gasto");
		model.addAttribute("accion", "Editar");
		return "ventas/formconceptosgastos";
	}
	
	@RequestMapping("/eliminarconceptosgastos/{id}")
	public String eliminarconceptosgastos(Model model, @PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			ConceptosGastos conceptosGastos= conceptosGastosService.findOne(id);

			conceptosGastosService.delete(id);
			flash.addFlashAttribute("success", "Concepto de Gasto " + conceptosGastos.getNombre() + " eliminado");

			

		}

		return "redirect:/ventas/listar";
	}
	
	@GetMapping("/formconceptosingresos")
	public String formconceptosingresos(Model model) {
		
		
		model.addAttribute("titulo", "Crear Nuevo Concepto de ingreso");
		model.addAttribute("accion", "Crear Nuevo");
		ConceptosIngresos conceptosIngresos=new ConceptosIngresos();
		conceptosIngresos.setCreateAt(new Date());
		model.addAttribute("conceptosIngresos", conceptosIngresos);
		return "ventas/formconceptosingresos";
	}
	
	@PostMapping("/formconceptosingresos")
	public String guardarconceptosingresos(@Valid ConceptosIngresos conceptosIngresos, BindingResult result, Model model,
			SessionStatus status, RedirectAttributes flash) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Nuevo Concepto de ingreso");
			model.addAttribute("accion", "Crear Nuevo");
			return "ventas/formconceptosingresos";
		}

		
		String msg = (conceptosIngresos.getId() != null) ? "Concepto de Ingreso Editado con Exito" : "Concepto de Ingreso Creado con Exito";
		conceptosIngresosService.save(conceptosIngresos);
		status.setComplete();
		flash.addFlashAttribute("success", msg);
		return "redirect:/ventas/listar";
	}
	
	@RequestMapping("/formconceptosingresos/{id}")
	public String editarconceptosingresos(Model model, @PathVariable Long id, RedirectAttributes flash) {
		
		ConceptosIngresos conceptosIngresos=null;
		

		if (id > 0) {
			conceptosIngresos = conceptosIngresosService.findOne(id);
			if (conceptosIngresos == null) {
				flash.addFlashAttribute("error", "Concepto de Ingreso No encontrado");
				return "redirect:/ventas/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El Id no puede ser cero");
			return "redirect:/ventas/listar";
		}
		model.addAttribute("conceptosIngresos", conceptosIngresos);
		model.addAttribute("titulo", "Editar Concepto de Ingreso");
		model.addAttribute("accion", "Editar");
		return "ventas/formconceptosingresos";
	}
	
	@RequestMapping("/eliminarconceptosingresos/{id}")
	public String eliminarconceptosingresos(Model model, @PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			ConceptosIngresos conceptosIngresos= conceptosIngresosService.findOne(id);

			conceptosIngresosService.delete(id);
			flash.addFlashAttribute("success", "Concepto de Ingreso" + conceptosIngresos.getNombre() + " eliminado");

			

		}

		return "redirect:/ventas/listar";
	}
	
	@GetMapping("/formtransaccionesgastos")
	public String formtransaccionesgastos(Model model) {
		
		
		model.addAttribute("titulo", "Crear Nueva Transaccion de gasto");
		model.addAttribute("accion", "Crear Nuevo");
		TransaccionesGastos transaccionesGastos=new TransaccionesGastos();
		List<ConceptosGastos> conceptosGastos=conceptosGastosService.findAll();
		List<Cuentas> cuentas=cuentasService.findAll();
		transaccionesGastos.setCreateAt(new Date());
		transaccionesGastos.setFlagVigencia(true);
		model.addAttribute("transaccionesGastos", transaccionesGastos);
		model.addAttribute("conceptosGastos",conceptosGastos);
		model.addAttribute("cuentas", cuentas);
		return "ventas/formtransaccionesgastos";
	}
	
	@PostMapping("/formtransaccionesgastos")
	public String guardartransaccionesgastos(
			@Valid TransaccionesGastos transaccionesGastos, 
			//@Valid ConceptosGastos conceptosGastos,
			//@Valid Cuentas cuentas,
			BindingResult result, 
			Model model,
			SessionStatus status, 
			RedirectAttributes flash,
			@RequestParam(name="cuentas",required=false) Long idcuenta,
			@RequestParam(name="conceptosGastos",required=false) Long idconceptoGasto
			) {
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Nueva Transaccion de gasto");
			model.addAttribute("accion", "Crear Nuevo");
			model.addAttribute("cuentaId",idcuenta);
			model.addAttribute("conceptoGastoId",idconceptoGasto);
			return "ventas/formtransaccionesgastos";
		}

		transaccionesGastos.setCuentas(cuentasService.findOne(idcuenta));
		transaccionesGastos.setConceptosGastos(conceptosGastosService.findOne(idconceptoGasto));
		String msg = (transaccionesGastos.getId() != null) ? "Transaccion gasto Editado con Exito" : "Transaccion gasto Creado con Exito";
		transaccionesGastosService.save(transaccionesGastos);
		status.setComplete();
		flash.addFlashAttribute("success", msg);
		return "redirect:/ventas/listar";
	}
	
	@RequestMapping("/formtransaccionesgastos/{id}")
	public String editartransaccionesgastos(Model model, @PathVariable Long id, RedirectAttributes flash) {
		
		TransaccionesGastos transaccionesGastos=null;
		

		if (id > 0) {
			transaccionesGastos = transaccionesGastosService.findOne(id);
			if (transaccionesGastos == null) {
				flash.addFlashAttribute("error", "Transaccion de gasto No encontrada");
				return "redirect:/ventas/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El Id no puede ser cero");
			return "redirect:/ventas/listar";
		}
		model.addAttribute("transaccionesGastos", transaccionesGastos);
		model.addAttribute("titulo", "Editar transaccion de Gasto");
		model.addAttribute("accion", "Editar");
		return "ventas/formtransaccionesgastos";
	}
	
	@RequestMapping("/eliminartransaccionesgastos/{id}")
	public String eliminartransaccionesgastos(Model model, @PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			TransaccionesGastos transaccionesGastos= transaccionesGastosService.findOne(id);

			transaccionesGastosService.delete(id);
			flash.addFlashAttribute("success", "Transaccion de gasto " + transaccionesGastos.getDescripcion() + " eliminado");

			

		}

		return "redirect:/ventas/listar";
	}
	
//    INGRESOS
	
	@GetMapping("/formtransaccionesingresos")
	public String formtransaccionesingresos(Model model) {
		
		
		model.addAttribute("titulo", "Crear Nueva Transaccion de ingreso");
		model.addAttribute("accion", "Crear Nuevo");
		TransaccionesIngresos transaccionesIngresos=new TransaccionesIngresos();
		List<ConceptosIngresos> conceptosIngresos=conceptosIngresosService.findAll();
		List<Cuentas> cuentas=cuentasService.findAll();
		transaccionesIngresos.setCreateAt(new Date());
		transaccionesIngresos.setFlagVigencia(true);
		model.addAttribute("transaccionesIngresos", transaccionesIngresos);
		model.addAttribute("conceptosIngresos",conceptosIngresos);
		model.addAttribute("cuentas", cuentas);
		return "ventas/formtransaccionesingresos";
	}
	
	@PostMapping("/formtransaccionesingresos")
	public String guardartransaccionesingresos(
			@Valid TransaccionesIngresos transaccionesIngresos, 
			BindingResult result, 
			Model model,
			SessionStatus status, 
			RedirectAttributes flash,
			@RequestParam(name="cuentas",required=false) Long idcuenta,
			@RequestParam(name="conceptosIngresos",required=false) Long idconceptoIngreso
			) {
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Nueva Transaccion de ingreso");
			model.addAttribute("accion", "Crear Nuevo");
			model.addAttribute("cuentaId",idcuenta);
			model.addAttribute("conceptoIngresoId",idconceptoIngreso);
			return "ventas/formtransaccionesingresos";
		}

		transaccionesIngresos.setCuentas(cuentasService.findOne(idcuenta));
		transaccionesIngresos.setConceptosIngresos(conceptosIngresosService.findOne(idconceptoIngreso));
		String msg = (transaccionesIngresos.getId() != null) ? "Transaccion ingreso Editado con Exito" : "Transaccion ingreso Creado con Exito";
		transaccionesIngresosService.save(transaccionesIngresos);
		status.setComplete();
		flash.addFlashAttribute("success", msg);
		return "redirect:/ventas/listar";
	}
	
	@RequestMapping("/formtransaccionesingresos/{id}")
	public String editartransaccionesingresos(Model model, @PathVariable Long id, RedirectAttributes flash) {
		
		TransaccionesGastos transaccionesGastos=null;
		

		if (id > 0) {
			transaccionesGastos = transaccionesGastosService.findOne(id);
			if (transaccionesGastos == null) {
				flash.addFlashAttribute("error", "Transaccion de gasto No encontrada");
				return "redirect:/ventas/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El Id no puede ser cero");
			return "redirect:/ventas/listar";
		}
		model.addAttribute("transaccionesGastos", transaccionesGastos);
		model.addAttribute("titulo", "Editar transaccion de Gasto");
		model.addAttribute("accion", "Editar");
		return "ventas/formtransaccionesingresos";
	}
	
	@RequestMapping("/eliminartransaccionesingresos/{id}")
	public String eliminartransaccionesingresos(Model model, @PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			TransaccionesGastos transaccionesGastos= transaccionesGastosService.findOne(id);

			transaccionesGastosService.delete(id);
			flash.addFlashAttribute("success", "Transaccion de gasto " + transaccionesGastos.getDescripcion() + " eliminado");

			

		}

		return "redirect:/ventas/listar";
	}
	
}
