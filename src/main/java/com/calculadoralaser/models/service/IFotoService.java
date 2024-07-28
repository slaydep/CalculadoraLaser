package com.calculadoralaser.models.service;

import com.calculadoralaser.models.entity.Foto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFotoService {

    public List<Foto> findAll();
    public Page<Foto> findAll(Pageable pageable);
    public void save(Foto foto);

    public void delete(Long id);

    public Optional<Foto> findById(Long id);
    
    public Optional<Foto> findByNombre(String term);
	public Page<Foto> findAllByNombre(String busqueda, Pageable pageRequest);
	public List<Foto> findByNombres(String term);
}
