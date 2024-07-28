package com.calculadoralaser.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calculadoralaser.models.dao.IMaquinaDao;
import com.calculadoralaser.models.entity.Maquina;


@Service
public class MaquinaServiceImpl implements IMaquinaService {

	@Autowired
	private IMaquinaDao maquinaDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Maquina> findAll() {
		// TODO Auto-generated method stub
		return (List<Maquina>) maquinaDao.findAll();
	}

	@Transactional
	@Override
	public void save(Maquina maquina) {
		maquinaDao.save(maquina);
		
	}

	@Transactional(readOnly = true)
	@Override
	public Maquina findOne(Long id) {
		
		return maquinaDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		maquinaDao.deleteById(id);
	}

}
