package com.calculadoralaser.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.calculadoralaser.models.entity.Vector;


public interface IVectorDao extends PagingAndSortingRepository<Vector, Long>{
	
	//@Query(value="select * from vectores v where upper(v.nombre) like '%' || upper(?1) || '%'",nativeQuery = true)
	@Query(value="select * from buscar_vector(?1)",nativeQuery = true)
	public List<Vector> findByNombre(String term);
	
	//@Query(value="select * from vectores v where upper(v.descripcion) like '%' || upper(?1) || '%'",nativeQuery = true)
	@Query(value="select * from buscar_vector(?1)",nativeQuery = true)
	public Page<Vector> findAllByNombre(String busqueda,Pageable pageable);
	
	@Query("select v from Vector v left join fetch v.madera left join fetch v.calculos where v.descripcion like %?1%")
	//@Query(value="select * from buscar_vector(?1)",nativeQuery = true)
	public List<Vector> findByDescripcion(String term);
	
	public List<Vector> findByPrecioFabricaIsNotNull();
}
