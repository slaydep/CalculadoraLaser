package com.calculadoralaser.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String authority;
	
	
	
	@ManyToMany 
    @JoinTable( 
        name = "usuarios_roles", 
        joinColumns = @JoinColumn(name = "fk_role"), 
        inverseJoinColumns = @JoinColumn(name = "fk_usuario"))
    private List<Usuario> usuarios;
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuario) {
		this.usuarios = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
