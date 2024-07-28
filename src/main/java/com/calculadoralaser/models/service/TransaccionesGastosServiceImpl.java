package com.calculadoralaser.models.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculadoralaser.models.dao.ITransaccionesGastosDao;
import com.calculadoralaser.models.entity.TransaccionesGastos;


@Service
public class TransaccionesGastosServiceImpl implements ITransaccionesGastosService{

	@Autowired
	private ITransaccionesGastosDao transaccionesGastosDao;
	
//	@Override
//	public List<TransaccionesGastos> findAll_() {
//		// TODO Auto-generated method stub
//		return (List<TransaccionesGastos>)transaccionesGastosDao.encontrarTodos();
//	}

	@Override
	public void save(TransaccionesGastos transaccionesGastos) {
		// TODO Auto-generated method stub
		transaccionesGastosDao.save(transaccionesGastos);
	}

	@Override
	public TransaccionesGastos findOne(Long id) {
		// TODO Auto-generated method stub
		return transaccionesGastosDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		transaccionesGastosDao.deleteById(id);
		
	}

	@Override
	public List<TransaccionesGastos> findAll() {
		// TODO Auto-generated method stub
		return (List<TransaccionesGastos>) transaccionesGastosDao.findAll();
	}
	

}

