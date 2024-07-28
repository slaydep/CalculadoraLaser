package com.calculadoralaser.models.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.calculadoralaser.models.entity.Producto;



public interface IProductoDao extends PagingAndSortingRepository<Producto, Long> {
	
	//@Query(value="select * from productos p where upper(p.nombre) like '%' || upper(?1) || '%'",nativeQuery = true)
	@Query(value="select * from buscar_producto(?1)",nativeQuery = true)
	public List<Producto> findByNombre(String term);
	
	public Producto findByCodigo(String term);
	
	public List<Producto> findByNombreLikeIgnoreCase(String term);
	
	//@Query(value="select * from productos p where upper(p.descripcion) like '%' || upper(?1) || '%'",nativeQuery = true)
	@Query(value="select * from buscar_producto(?1)",nativeQuery = true)
	public Page<Producto> findAllByDescripcion(String busqueda,Pageable pageable);

}
