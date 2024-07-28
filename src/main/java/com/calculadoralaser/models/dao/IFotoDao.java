package com.calculadoralaser.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.calculadoralaser.models.entity.Foto;

public interface IFotoDao extends PagingAndSortingRepository<Foto,Long>{
	
	@Query(value="select * from fotos f where upper(f.foto) like '%' || upper(?1) || '%'",nativeQuery = true)
	public Optional<Foto> findByNombre(String term);
	
	@Query(value="select * from fotos f where upper(f.nombre) like '%' || upper(?1) || '%'",nativeQuery = true)
	public List<Foto> findByNombres(String term);
	
	@Query(value="select * from fotos f where upper(f.nombre) like '%' || upper(?1) || '%'",nativeQuery = true)
	public Page<Foto> findAllByNombre(String busqueda,Pageable pageable);
	
	
}
