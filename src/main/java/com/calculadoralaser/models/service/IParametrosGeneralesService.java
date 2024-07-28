package com.calculadoralaser.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.calculadoralaser.models.entity.ParametrosGenerales;


public interface IParametrosGeneralesService {

	public List<ParametrosGenerales> findAll();
    public Page<ParametrosGenerales> findAll(Pageable pageable);
    public void save(ParametrosGenerales parametrosGenerales);

    public void delete(Long id);

    public Optional<ParametrosGenerales> findById(Long id);
    
    public Optional<ParametrosGenerales> findByNombre(String term);
	public Page<ParametrosGenerales> findAllByNombre(String busqueda, Pageable pageRequest);
	public List<ParametrosGenerales> findByNombres(String term);
}
