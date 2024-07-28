package com.calculadoralaser.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.calculadoralaser.models.entity.Producto;


public interface IProductoService {
	
	public List<Producto> findAll();
	public Page<Producto> findAll(Pageable pageable);
	public void save(Producto producto);
	public void delete(Long id);
	public List<Producto> findByNombre(String term);
	public Producto findProductoById(Long id);
	public Page<Producto> findAllByDescripcion(String busq, Pageable pageRequest);
	

}
