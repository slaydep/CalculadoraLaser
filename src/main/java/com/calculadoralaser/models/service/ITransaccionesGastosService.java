package com.calculadoralaser.models.service;


import java.util.List;

import com.calculadoralaser.models.entity.TransaccionesGastos;



public interface ITransaccionesGastosService {
	
	//public List<TransaccionesGastos> findAll_();
	public List<TransaccionesGastos> findAll();
	
	public void save(TransaccionesGastos transaccionesGastos);
	
	public TransaccionesGastos findOne(Long id);
	
	public void delete(Long id);

}

