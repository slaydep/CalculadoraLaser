package com.calculadoralaser.models.service;

import java.util.List;

import com.calculadoralaser.models.entity.Madera;




public interface IMaderaService {
	
	public List<Madera> findAll();
	
	public void save(Madera madera);
	
	public Madera findOne(Long id);
	
	public void delete(Long id);


}
