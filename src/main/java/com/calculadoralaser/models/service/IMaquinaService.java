package com.calculadoralaser.models.service;

import java.util.List;

import com.calculadoralaser.models.entity.Maquina;


public interface IMaquinaService {
	
	public List<Maquina> findAll();
	
	public void save(Maquina maquina);
	
	public Maquina findOne(Long id);
	
	public void delete(Long id);


}
