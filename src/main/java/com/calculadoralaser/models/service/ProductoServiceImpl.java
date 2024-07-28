package com.calculadoralaser.models.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calculadoralaser.models.dao.IProductoDao;
import com.calculadoralaser.models.entity.Producto;
import com.calculadoralaser.models.entity.Vector;


@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private IProductoDao productoDao;
	
	@Transactional (readOnly = true)
	@Override
	public List<Producto> findAll() {
		// TODO Auto-generated method stub
		return (List<Producto>) productoDao.findAll();
	}

	@Transactional
	@Override
	public void save(Producto producto) {
		
		if(producto.getVectores().size()>0) {
			Integer totalFabrica=0, totalVenta=0;
			for (Vector vector : producto.getVectores()) {
				totalFabrica+=vector.getPrecioFabrica();
				totalVenta+=vector.getPrecioVenta();
			}
			producto.setPrecioFabrica(totalFabrica);
			producto.setPrecioVenta(totalVenta);
		}else {
			producto.setPrecioFabrica(0);
			producto.setPrecioVenta(0);
		}
		
		productoDao.save(producto);

	}
	
	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		productoDao.deleteById(id);

	}

	@Override
	@Transactional (readOnly = true)
	public Page<Producto> findAll(Pageable pageable) {
		
		return productoDao.findAll(pageable);
	}

	@Override
	@Transactional (readOnly = true)
	public List<Producto> findByNombre(String term) {
		
//		return productoDao.findByNombre(term);
		return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional (readOnly = true)
	public Producto findProductoById(Long id) {
		// TODO Auto-generated method stub
		return productoDao.findById(id).orElse(null);
	}

	@Override
	public Page<Producto> findAllByDescripcion(String busq, Pageable pageRequest) {
		// TODO Auto-generated method stub
		
		return productoDao.findAllByDescripcion(busq, pageRequest);
	}

	
}
