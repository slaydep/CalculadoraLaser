package com.calculadoralaser.models.service;

import java.util.List;

import com.calculadoralaser.models.entity.ConceptosIngresos;



public interface IConceptosIngresosService {
	
	public List<ConceptosIngresos> findAll();
	
	public void save(ConceptosIngresos conceptosIngresos);
	
	public ConceptosIngresos findOne(Long id);
	
	public void delete(Long id);

}

