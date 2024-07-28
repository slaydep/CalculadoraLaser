package com.calculadoralaser.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.calculadoralaser.models.dao.IUsuarioDao;
import com.calculadoralaser.models.entity.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDao usuarioDao;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAllsinMe(String user) {
		return (List<Usuario>) usuarioDao.findAllSinMe(user);
	}

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findOne(Long id) {
		// TODO Auto-generated method stub
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		usuarioDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioDao.findAll(pageable);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		// TODO Auto-generated method stub
		return usuarioDao.findByEmail(email);
	}

	@Override
	public Usuario findByUsername(String username) {
		// TODO Auto-generated method stub
		return usuarioDao.findByUsername(username);
	}

	@Override
	public Usuario register(Usuario nuevoUsuario, BindingResult result) {
		
		System.out.println("password :"+nuevoUsuario.getPassword());
		System.out.println("length password :"+(nuevoUsuario.getPassword().length()+12));
		System.out.println("confirm :"+nuevoUsuario.getConfirm());
		System.out.println("length confirm :"+(nuevoUsuario.getConfirm().length()+12));
		System.out.println("contraseñas iguales? :"+(nuevoUsuario.getPassword().equals(nuevoUsuario.getConfirm())));
		System.out.println("mayor que cero? :"+(nuevoUsuario.getPassword().length()>0));
		System.out.println("comprobacion :"+((nuevoUsuario.getPassword().length()>0) && (!nuevoUsuario.getPassword().equals(nuevoUsuario.getConfirm()))));
		
		Usuario usuartemp =usuarioDao.findByUsername(nuevoUsuario.getUsername());
		
		if(nuevoUsuario.getId()==null && usuartemp!=null) {
			result.rejectValue("username", "Unique", "El usuario ya existe.");
		}
		if(nuevoUsuario.getId()!=null && usuartemp!=null && nuevoUsuario.getId()!=usuartemp.getId()) {
			result.rejectValue("username", "Unique", "El usuario ya existe.");
		}
		
		if (nuevoUsuario.getId()==null && (nuevoUsuario.getPassword().length() ==0)) {
			result.rejectValue("password",null , "Se requiere password.");
		}
		
		
		
		if (!nuevoUsuario.getPassword().equals(nuevoUsuario.getConfirm())) {
			
			result.rejectValue("confirm", "Matches", "Las contraseñas no coinciden...");
			System.out.println("...entro a las contraseñas no coinciden...");
		}
		
//		System.out.println("errores"+result.getFieldErrors().toString());
//		System.out.println("usuario id: " + nuevoUsuario.getId());
//		System.out.println("usuario nombre: " + nuevoUsuario.getUsername());
//		System.out.println("usuario email: " + nuevoUsuario.getEmail());
//		System.out.println("usuario estado: " + nuevoUsuario.getEnabled());
//		System.out.println("usuario pass: " + nuevoUsuario.getPassword());
//		System.out.println("usuario confirm: " + nuevoUsuario.getConfirm());
//		System.out.println("usuario roles: " + nuevoUsuario.getRoles());
		
		if (result.hasErrors()) {
			return null;
		} else {
			
			if(nuevoUsuario.getPassword().length() !=0) {
				String contraEncriptada = BCrypt.hashpw(nuevoUsuario.getPassword(), BCrypt.gensalt());
				nuevoUsuario.setPassword(contraEncriptada);
				System.out.println("contraseña encriptada :"+contraEncriptada);
			}
			
			if (nuevoUsuario.getId()!=null && (nuevoUsuario.getPassword().length()==0)) {
				nuevoUsuario.setPassword(usuartemp.getPassword());
				System.out.println("usuartemp.getPassword...:"+usuartemp.getPassword());
				System.out.println("nuevoUsuario.getPassword...:"+nuevoUsuario.getPassword());
			}
			

			return usuarioDao.save(nuevoUsuario);
		}
	}

}
