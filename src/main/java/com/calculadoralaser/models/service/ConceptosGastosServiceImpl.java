package com.calculadoralaser.models.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calculadoralaser.models.dao.IConceptosGastosDao;
import com.calculadoralaser.models.entity.ConceptosGastos;


@Service
public class ConceptosGastosServiceImpl implements IConceptosGastosService{

	@Autowired
	private IConceptosGastosDao conceptosGastosDao;
	
	@Transactional (readOnly = true)
	@Override
	public List<ConceptosGastos> findAll() {
		// TODO Auto-generated method stub
		return (List<ConceptosGastos>) conceptosGastosDao.findAll();
	}

	@Transactional
	@Override
	public void save(ConceptosGastos conceptosGastos) {
		// TODO Auto-generated method stub
		conceptosGastosDao.save(conceptosGastos);
	}

	@Override
	@Transactional (readOnly = true)
	public ConceptosGastos findOne(Long id) {
		// TODO Auto-generated method stub
		return conceptosGastosDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		conceptosGastosDao.deleteById(id);
	}

}

