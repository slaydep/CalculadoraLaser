package com.calculadoralaser.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import com.calculadoralaser.models.entity.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> findAll();
	public Usuario save(Usuario usuario);
	public Usuario findOne(Long id);
	public void delete(Long id);
	public Page<Usuario> findAll(Pageable pageable);
	public Optional<Usuario> findByEmail(String email);
	public Usuario findByUsername(String username);
	public List<Usuario> findAllsinMe(String username);
	public Usuario register(Usuario nuevoUsuario, BindingResult result);

}
