package com.calculadoralaser.models.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.calculadoralaser.models.entity.ParametrosGenerales;

public interface IParametrosGeneralesDao extends PagingAndSortingRepository<ParametrosGenerales,Long>{

	Optional<ParametrosGenerales> findByNombreIgnoreCase(String term);

}
