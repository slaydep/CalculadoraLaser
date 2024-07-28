package com.calculadoralaser.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.calculadoralaser.models.dao.IVectorDao;
import com.calculadoralaser.models.entity.Calculo;
import com.calculadoralaser.models.entity.Producto;
import com.calculadoralaser.models.entity.Vector;


@Service
public class VectorServiceImpl implements IVectorService{

	@Autowired
	private IVectorDao vectorDao;
	
	@Autowired
	private IProductoService productoService;
	
	@Override
	public List<Vector> findAll() {
		// TODO Auto-generated method stub
		return (List<Vector>) vectorDao.findAll();
	}

	@Override
	public Page<Vector> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return vectorDao.findAll(pageable);
	}

	@Override
	public void save(Vector vector) {
		
		
		
		if(vector.getCalculos().size()>0) {
			Integer totalFabrica=0, totalVenta=0;
			for (Calculo calculo : vector.getCalculos()) {
				totalFabrica+=calculo.getPrecioFabrica();
				totalVenta+=calculo.getPrecioVenta();
			}
			vector.setPrecioFabrica(totalFabrica);
			vector.setPrecioVenta(totalVenta);
		}else {
			vector.setPrecioFabrica(0);
			vector.setPrecioVenta(0);
		}
		
		vectorDao.save(vector);
		if(vector.getId()!=null) {
			vector=this.findOne(vector.getId());
			List<Producto> productos=vector.getProductos();
			for (Producto producto : productos) {
				productoService.save(producto);
			}
		}
	}

	@Override
	public Vector findOne(Long id) {
		// TODO Auto-generated method stub
		return vectorDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		vectorDao.deleteById(id);
		
	}

	@Override
	public List<Vector> findByNombre(String term) {
		// TODO Auto-generated method stub
		return vectorDao.findByNombre(term);
	}
	
	@Override
	public List<Vector> findByDescripcion(String term) {
		// TODO Auto-generated method stub
		return vectorDao.findByDescripcion(term);
	}

	@Override
	public Vector findVectorById(Long id) {
		// TODO Auto-generated method stub
		return vectorDao.findById(id).orElse(null);
	}

	@Override
	public List<Vector> findByPrecioFabricaIsNotNull() {
		
		return vectorDao.findByPrecioFabricaIsNotNull();
	}

	@Override
	public Page<Vector> findAllByNombre(String busqueda, Pageable pageRequest) {
		// TODO Auto-generated method stub
		return vectorDao.findAllByNombre(busqueda, pageRequest);
	}
	

}
