package com.calculadoralaser.models.service;


import java.util.List;

import com.calculadoralaser.models.entity.TransaccionesIngresos;



public interface ITransaccionesIngresosService {
	
	public List<TransaccionesIngresos> findAll();
	
	public void save(TransaccionesIngresos transaccionesIngresos);
	
	public TransaccionesIngresos findOne(Long id);
	
	public void delete(Long id);

}

