package com.calculadoralaser.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.calculadoralaser.models.entity.Vector;



public interface IVectorService {
	
	public List<Vector> findAll();
	public Page<Vector> findAll(Pageable pageable);
	public void save(Vector vector);
	public Vector findOne(Long id);
	public void delete(Long id);
	public List<Vector> findByNombre(String term);
	public Vector findVectorById(Long id);
	public List<Vector> findByPrecioFabricaIsNotNull();
	public List<Vector> findByDescripcion(String term);
	public Page<Vector> findAllByNombre(String busqueda, Pageable pageRequest);
	

}
