package com.calculadoralaser.controllers;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calculadoralaser.models.entity.Role;
import com.calculadoralaser.models.entity.Usuario;
import com.calculadoralaser.models.service.IRoleService;
import com.calculadoralaser.models.service.IUsuarioService;
import com.calculadoralaser.util.paginator.PageRender;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/usuarios")
@SessionAttributes("usuario")
public class UsuarioController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IRoleService roleService;
	
	

	

	@RequestMapping(value = { "/listar", "/" }, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, HttpServletRequest request) {

		if (authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			logger.info(
					"Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: "
							.concat(auth.getName()));
		}

		if (hasRole("ROLE_ADMIN")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}

		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request,
				"");

		if (securityContext.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName())
					.concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName())
					.concat(" NO tienes acceso!"));
		}

		if (request.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Usuario> usuarios = usuarioService.findAll(pageRequest);

		PageRender<Usuario> pageRender = new PageRender<Usuario>("/usuarios/listar", usuarios);
		model.addAttribute("titulo", "Listado de usuarios");
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("page", pageRender);
		return "usuarios/listar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form")
	public String crear(Model model) {

		Usuario usuario = new Usuario();
		usuario.setPassword(null);
		usuario.setConfirm(null);
		List<Role> roles=roleService.findAll();
		model.addAttribute("roles", roles);
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Crear Usuario");
		return "usuarios/form";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/form")
	public String register(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model,
			HttpSession session,RedirectAttributes flash, SessionStatus status) {
		
		
		usuarioService.register(usuario, result);
//		
//		System.out.println("usuario nombre: "+usuario.getUsername());
//		System.out.println("usuario email: "+usuario.getEmail());
//		System.out.println("usuario estado: "+usuario.getEnabled());
//		System.out.println("usuario pass: "+usuario.getPassword());
//		System.out.println("usuario confirm: "+usuario.getConfirm());
//		System.out.println("usuario roles: "+usuario.getRoles());
		

		if (result.hasErrors()) {
			
			List<Role> roles=roleService.findAll();
			model.addAttribute("roles", roles);
			model.addAttribute("titulo", (usuario.getId() != null) ? "Editar Usuario" : "Crear usuario");
			return "usuarios/form";
		} else {
			//usuarioService.save(usuario);
			session.setAttribute("userSession", usuario);
			String mensajeFlash = (usuario.getId() != null) ? "Usuario editado con éxito!" : "Usuario creado con éxito!";
			status.setComplete();
			flash.addFlashAttribute("success", mensajeFlash);
		}

		

		return "redirect:/usuarios/listar";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Usuario usuario = null;

		if (id > 0) {
			usuario = usuarioService.findOne(id);
			if (usuario == null) {
				flash.addFlashAttribute("error", "El ID del usuario no existe en la BBDD!");
				return "redirect:/usuarios/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del usuario no puede ser cero!");
			return "redirect:/listar";
		}
		List<Role> roles=roleService.findAll();
		model.addAttribute("roles", roles);
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Editar Usuario");
		return "usuarios/form";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			

			usuarioService.delete(id);
			flash.addFlashAttribute("success", "Usuario eliminado con éxito!");
		}
		return "redirect:/usuarios/listar";
	}
	
	private boolean hasRole(String role) {

		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) {
			return false;
		}

		Authentication auth = context.getAuthentication();

		if (auth == null) {
			return false;
		}

		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

		return authorities.contains(new SimpleGrantedAuthority(role));

		

	}

}
