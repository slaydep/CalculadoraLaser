package com.calculadoralaser.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculadoralaser.models.dao.IRoleDao;
import com.calculadoralaser.models.entity.Role;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao roleDao;
	
	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return (List<Role>) roleDao.findAll();
	}

	@Override
	public Role save(Role role) {
		// TODO Auto-generated method stub
		return roleDao.save(role);
	}

	@Override
	public Optional<Role> findOne(Long id) {
		// TODO Auto-generated method stub
		return roleDao.findById(id);
	}

	@Override
	public void delete(Long id) {
		roleDao.deleteById(id);
		
	}

}
