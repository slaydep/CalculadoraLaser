package com.calculadoralaser.models.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculadoralaser.models.dao.ITransaccionesIngresosDao;
import com.calculadoralaser.models.entity.TransaccionesIngresos;


@Service
public class TransaccionesIngresosServiceImpl implements ITransaccionesIngresosService{

	@Autowired
	private ITransaccionesIngresosDao transaccionesIngresosDao;
	
	@Override
	public List<TransaccionesIngresos> findAll() {
		// TODO Auto-generated method stub
		return (List<TransaccionesIngresos>)transaccionesIngresosDao.findAll();
	}

	@Override
	public void save(TransaccionesIngresos transaccionesIngresos) {
		// TODO Auto-generated method stub
		transaccionesIngresosDao.save(transaccionesIngresos);
	}

	@Override
	public TransaccionesIngresos findOne(Long id) {
		// TODO Auto-generated method stub
		return transaccionesIngresosDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}

