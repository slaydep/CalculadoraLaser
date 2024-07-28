package com.calculadoralaser.models.service;

import java.util.List;

import com.calculadoralaser.models.entity.Cuentas;

public interface ICuentasService {
	
	public List<Cuentas> findAll();
	
	public void save(Cuentas cuentas);
	
	public Cuentas findOne(Long id);
	
	public void delete(Long id);
	
	public void sumaSaldo(Long id,Integer valor);
	
	public void restaSaldo(Long id,Integer valor);

}

