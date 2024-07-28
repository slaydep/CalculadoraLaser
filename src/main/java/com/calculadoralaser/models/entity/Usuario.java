package com.calculadoralaser.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="¡Se requiere Usuario!")
	@Column(length = 30, unique = true)
	private String username;

	
	//@Size(min=8, max=128, message="Password must be between 8 and 128 characters")
	private String password;
	
	@NotEmpty(message="¡Se requiere Email!")
    @Email(message="¡Ingrese un Email válido!")
    private String email;
	
	@Transient
    //@NotEmpty(message="Confirm Password is required!")
    //@Size(min=8, max=128, message="Confirm Password must be between 8 and 128 characters")
    private String confirm;

	private Boolean enabled;
	
	@NotNull
	@ManyToMany 
    @JoinTable( 
        name = "usuarios_roles", 
        joinColumns = @JoinColumn(
          name = "fk_usuario"), 
        inverseJoinColumns = @JoinColumn(
          name = "fk_role")) 
    private List<Role> roles;
	
//	@OneToMany(fetch = FetchType.LAZY)
//	@JoinColumn(name = "fk_usuario")
//	private List<Calculo> calculos;
	
//	@OneToMany(fetch = FetchType.LAZY)
//	@JoinColumn(name = "fk_usuario")
//	private List<Madera> maderas;
//	
//	@OneToMany(fetch = FetchType.LAZY)
//	@JoinColumn(name = "fk_usuario")
//	private List<Maquina> maquinas;
	
//	public List<Madera> getMaderas() {
//		return maderas;
//	}
//	public void setMaderas(List<Madera> maderas) {
//		this.maderas = maderas;
//	}
//	public List<Maquina> getMaquinas() {
//		return maquinas;
//	}
//	public void setMaquinas(List<Maquina> maquinas) {
//		this.maquinas = maquinas;
//	}

	@Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    
    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

//	public List<Calculo> getCalculos() {
//		return calculos;
//	}
//	public void setCalculos(List<Calculo> calculos) {
//		this.calculos = calculos;
//	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
