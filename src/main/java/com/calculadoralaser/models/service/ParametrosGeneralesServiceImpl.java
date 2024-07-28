package com.calculadoralaser.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calculadoralaser.models.dao.IParametrosGeneralesDao;
import com.calculadoralaser.models.entity.ParametrosGenerales;

@Service
public class ParametrosGeneralesServiceImpl implements IParametrosGeneralesService {

	@Autowired
	public IParametrosGeneralesDao parametrosGeneralesDao;
	
	
	@Transactional(readOnly = true)
	@Override
	public List<ParametrosGenerales> findAll() {

		return (List<ParametrosGenerales>) parametrosGeneralesDao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<ParametrosGenerales> findAll(Pageable pageable) {
		
		return parametrosGeneralesDao.findAll(pageable);
	}

	@Transactional
	@Override
	public void save(ParametrosGenerales parametrosGenerales) {
		parametrosGeneralesDao.save(parametrosGenerales);

	}

	@Transactional
	@Override
	public void delete(Long id) {
		parametrosGeneralesDao.deleteById(id);

	}

	@Transactional(readOnly = true)
	@Override
	public Optional<ParametrosGenerales> findById(Long id) {
		
		return parametrosGeneralesDao.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<ParametrosGenerales> findByNombre(String term) {
		
		return parametrosGeneralesDao.findByNombreIgnoreCase(term);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<ParametrosGenerales> findAllByNombre(String busqueda, Pageable pageRequest) {
		
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ParametrosGenerales> findByNombres(String term) {
		
		return null;
	}

}
