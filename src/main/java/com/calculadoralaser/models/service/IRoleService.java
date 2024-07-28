package com.calculadoralaser.models.service;

import java.util.List;
import java.util.Optional;

import com.calculadoralaser.models.entity.Role;

public interface IRoleService {
	
	public List<Role> findAll();
	public Role save(Role role);
	public Optional<Role> findOne(Long id);
	public void delete(Long id);
	

}
