package com.calculadoralaser.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calculadoralaser.models.dao.ICalculoDao;
import com.calculadoralaser.models.entity.Calculo;
import com.calculadoralaser.models.entity.Vector;


@Service
public class CalculoServiceImpl implements ICalculoService{

	@Autowired
	private ICalculoDao calculoDao;
	
	@Autowired
	private IVectorService vectorService;
	
	@Transactional(readOnly = true)
	@Override
	public List<Calculo> findAll() {
		
		return (List<Calculo>) calculoDao.findAll();
	}

	@Transactional
	@Override
	public void save(Calculo calculo) {
		
		Float horas=(float) (calculo.getHoras()*60.0);
		Integer minutos=calculo.getMinutos();
		Float segundos=(float) (calculo.getSegundos()!=0.0?calculo.getSegundos()/60.0:0.0);
		Float lado1=calculo.getLado1();
		Float lado2=calculo.getLado2();
		Float factor =calculo.getFactor();
		Float resultado=(float) 0.0;
//		System.out.println("hora: "+horas);
//		System.out.println("minutos: "+minutos);
//		System.out.println("segundos: "+segundos);
//		System.out.println("lado1: "+lado1);
//		System.out.println("lado2: "+lado2);
//		System.out.println("factor: "+factor);
//		System.out.println("precio maquina: "+calculo.getMaquina().getPrecio());
//		System.out.println("precio madera: "+calculo.getMadera().getPrecio());
		resultado=((horas+minutos+segundos)*calculo.getMaquina().getPrecio())+((lado1*lado2)*calculo.getMadera().getPrecio());
		
		calculo.setPrecioFabrica(resultado.intValue());
		calculo.setPrecioVenta((int)(resultado*factor));
		
		calculoDao.save(calculo);
		
		if(calculo.getId()!=null) {
			
			Calculo calculotmp=calculoDao.findByIdWithVectores(calculo.getId());
			List<Vector> vectores=calculotmp.getVectores();
			for (Vector vector : vectores) {
				
				vectorService.save(vector);
			}
		}
		
		
		
	}

	@Transactional(readOnly = true)
	@Override
	public Calculo findOne(Long id) {
		
		return calculoDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		calculoDao.deleteById(id);
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Calculo> findByDescripcion(String term) {
		// TODO Auto-generated method stub
		return calculoDao.findByDescripcion(term);
	}

	@Override
	public List<Calculo> findByMadera(Long id) {
		// TODO Auto-generated method stub
		return calculoDao.findAllByMadera(id);
	}

	@Override
	public Page<Calculo> findAllByDescripcion(String busq, Pageable pageRequest) {
		return calculoDao.findAllByDescripcionLike(busq, pageRequest);
	}

	@Override
	public Page<Calculo> findAll(Pageable pageRequest) {
		
		return calculoDao.findAll(pageRequest);
	}

}
