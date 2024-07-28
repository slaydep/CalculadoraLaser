package com.calculadoralaser.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="productos")
public class Producto implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(max = 100)
	private String nombre;
	
	@NotEmpty
	@Size(max=10)
	private String codigo;
	
	@NotEmpty
	@Size(max = 200)
	private String descripcion;
	
	@Min(0)
	private Integer precioFabrica;
	
	@Min(0)
	private Integer precioVenta;
	
	@ManyToMany 
    @JoinTable( 
        name = "productos_vectores", 
        joinColumns = @JoinColumn(
          name = "fk_producto"), 
        inverseJoinColumns = @JoinColumn(
          name = "fk_vector")) 
	@JsonManagedReference
    private List<Vector> vectores;
	
	public List<Vector> getVectores() {
		return vectores;
	}

	public void setVectores(List<Vector> vectores) {
		this.vectores = vectores;
	}
	//@NotNull
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createAt;
	
	//@NotNull
	@Column(name = "last_update")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date lastUpdate;
	
	@PrePersist
	private void prepers() {
		this.lastUpdate=new Date();
		
	}
	
	@ManyToMany 
    @JoinTable( 
        name = "productos_fotos", 
        joinColumns = @JoinColumn(
          name = "fk_producto"), 
        inverseJoinColumns = @JoinColumn(
          name = "fk_foto")) 
	@JsonManagedReference
    private List<Foto> fotos;
	
	public List<Foto> getFotos() {
		return fotos;
	}


	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}
	

	public Producto() {
		this.vectores=new ArrayList<Vector>();
		this.fotos=new ArrayList<Foto>();
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private static final long serialVersionUID = 1L;

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Integer getPrecioFabrica() {
		return precioFabrica;
	}

	public void setPrecioFabrica(Integer precioFabrica) {
		this.precioFabrica = precioFabrica;
	}

	public Integer getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Integer precioVenta) {
		this.precioVenta = precioVenta;
	}
	
	
}
