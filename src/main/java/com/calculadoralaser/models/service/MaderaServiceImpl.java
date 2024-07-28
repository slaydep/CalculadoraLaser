package com.calculadoralaser.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calculadoralaser.models.dao.ICalculoDao;
import com.calculadoralaser.models.dao.IMaderaDao;
import com.calculadoralaser.models.entity.Calculo;
import com.calculadoralaser.models.entity.Madera;


@Service
public class MaderaServiceImpl implements IMaderaService {

	@Autowired
	private IMaderaDao maderaDao;
	
	@Autowired
	private ICalculoDao calculoDao;
	
	@Transactional (readOnly = true)
	@Override
	public List<Madera> findAll() {
		
		return (List<Madera>) maderaDao.findAll();
	}
	
	@Transactional
	@Override
	public void save(Madera madera) {
		
		maderaDao.save(madera);
		if (madera.getId()!=null) {
			List<Calculo> calculos=calculoDao.findAllByMadera(madera.getId());
			for (Calculo calculo : calculos) {
				calculoDao.save(calculo);
			}
		}
		
	}

	@Transactional (readOnly = true)
	@Override
	public Madera findOne(Long id) {
		
		return maderaDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		maderaDao.deleteById(id);
		
	}


}
