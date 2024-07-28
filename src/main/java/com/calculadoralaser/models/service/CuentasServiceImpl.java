package com.calculadoralaser.models.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calculadoralaser.models.dao.ICuentasDao;
import com.calculadoralaser.models.entity.Cuentas;


@Service
public class CuentasServiceImpl implements ICuentasService  {

	@Autowired
	private ICuentasDao cuentasDao;
	
	@Transactional (readOnly = true)
	@Override
	public List<Cuentas> findAll() {
		// TODO Auto-generated method stub
		
		return (List<Cuentas>)cuentasDao.findAll();
	}
	

	@Transactional
	@Override
	public void save(Cuentas cuentas) {
		// TODO Auto-generated method stub
		cuentasDao.save(cuentas);
	}

	@Transactional (readOnly = true)
	@Override
	public Cuentas findOne(Long id) {
		// TODO Auto-generated method stub
		return cuentasDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		cuentasDao.deleteById(id);
	}


	@Override
	public void sumaSaldo(Long id, Integer valor) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void restaSaldo(Long id, Integer valor) {
		// TODO Auto-generated method stub
		
	}


	

	

}

