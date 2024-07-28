package com.calculadoralaser.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.calculadoralaser.models.entity.Usuario;

public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long>{
	
	Optional<Usuario> findByEmail(String email);

	public Usuario findByUsername(String username);
	
	@Query(value="select * from users u where upper(u.username) <> upper(?1);" , nativeQuery = true)
	List<Usuario> findAllSinMe(String username);
}
