package com.calculadoralaser.models.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.calculadoralaser.models.entity.Calculo;


public interface ICalculoDao extends PagingAndSortingRepository<Calculo, Long> {
	
	@Query(value="select * from calculos c left join maderas m on c.fk_madera = m.id left join maquinas ma on c.fk_maquina = ma.id where upper(c.descripcion) like '%' || upper(?1) || '%'",nativeQuery = true)
	//@Query("select c from Calculo c where c.descripcion like %?1%")
	public List<Calculo> findByDescripcion(String term);
	
	@Query("select c from Calculo c left join fetch c.vectores v where c.id=?1")
	public Calculo findByIdWithVectores(Long id);
	
	@Query(value="select c.* from calculos as c where c.fk_madera=?1" ,nativeQuery = true)
	public List<Calculo> findAllByMadera(Long id);
	
	
	@Query(value="select * from calculos c where upper(c.descripcion) like '%' || upper(?1) || '%'",nativeQuery = true)
	public Page<Calculo> findAllByDescripcionLike(String busqueda,Pageable pageable);
	
	
}
