package com.calculadoralaser.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.calculadoralaser.models.entity.Calculo;


public interface ICalculoService {
	
	public List<Calculo> findAll();
	
	public void save(Calculo calculo);
	
	public Calculo findOne(Long id);
	
	public void delete(Long id);
	
	public List<Calculo> findByDescripcion(String term);
	
	public List<Calculo> findByMadera(Long id);

	public Page<Calculo> findAllByDescripcion(String busq, Pageable pageRequest);

	public Page<Calculo> findAll(Pageable pageRequest);
	

}
