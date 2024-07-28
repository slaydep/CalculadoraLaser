package com.calculadoralaser.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.calculadoralaser.models.entity.Role;

public interface IRoleDao  extends CrudRepository<Role, Long>{

}
