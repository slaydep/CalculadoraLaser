package com.calculadoralaser.models.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.calculadoralaser.models.entity.Usuario;

//sin uso
@Service
public class AppService {

	@Autowired
	private IUsuarioService userRepo;
	

	public Usuario register(Usuario nuevoUsuario, BindingResult result) {
		
		
		Usuario usertemp=userRepo.findByUsername(nuevoUsuario.getUsername());
		if(nuevoUsuario.getId()==null && usertemp!=null) {
			result.rejectValue("username", "Unique", "El usuario ya existe.");
		}
		
		if (!nuevoUsuario.getPassword().equals(nuevoUsuario.getConfirm())) {
			result.rejectValue("password", "Matches", "Las contraseñas no coinciden.");
		}
		
		System.out.println("usuario id: "+nuevoUsuario.getId());
		System.out.println("usuario nombre: "+nuevoUsuario.getUsername());
		System.out.println("usuario email: "+nuevoUsuario.getEmail());
		System.out.println("usuario estado: "+nuevoUsuario.getEnabled());
		System.out.println("usuario pass: "+nuevoUsuario.getPassword());
		System.out.println("usuario confirm: "+nuevoUsuario.getConfirm());
		System.out.println("usuario roles: "+nuevoUsuario.getRoles());
		
		if (result.hasErrors()) {
			return null;
		} else {
			String contraEncriptada = BCrypt.hashpw(nuevoUsuario.getPassword(), BCrypt.gensalt());
			nuevoUsuario.setPassword(contraEncriptada);
			
			return userRepo.save(nuevoUsuario);
		}
	}

	

}
