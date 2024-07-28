package com.calculadoralaser.models.service;


import java.util.List;

import com.calculadoralaser.models.entity.ConceptosGastos;




public interface IConceptosGastosService {
	
public List<ConceptosGastos> findAll();
	
	public void save(ConceptosGastos conceptosGastos);
	
	public ConceptosGastos findOne(Long id);
	
	public void delete(Long id);

}

