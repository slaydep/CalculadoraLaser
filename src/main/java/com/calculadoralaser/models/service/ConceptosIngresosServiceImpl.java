package com.calculadoralaser.models.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calculadoralaser.models.dao.IConceptosIngresosDao;
import com.calculadoralaser.models.entity.ConceptosIngresos;


@Service
public class ConceptosIngresosServiceImpl implements IConceptosIngresosService{

	@Autowired
	private IConceptosIngresosDao conceptosIngresosDao;
	
	@Transactional (readOnly = true)
	@Override
	public List<ConceptosIngresos> findAll() {
		// TODO Auto-generated method stub
		return (List<ConceptosIngresos>)conceptosIngresosDao.findAll();
	}

	@Transactional
	@Override
	public void save(ConceptosIngresos conceptosIngresos) {
		// TODO Auto-generated method stub
		conceptosIngresosDao.save(conceptosIngresos);
	}

	@Transactional (readOnly = true)
	@Override
	public ConceptosIngresos findOne(Long id) {
		// TODO Auto-generated method stub
		return conceptosIngresosDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		conceptosIngresosDao.deleteById(id);
	}

}

