package com.calculadoralaser.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="vectores")
public class Vector implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(max = 100)
	private String nombre;
	
	@NotEmpty
	@Size(max=10)
	@Column(unique=true)
	private String codigo;
	
	@NotEmpty
	@Size(max = 200)
	private String descripcion;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_madera")
	private Madera madera;
	
	@ManyToMany 
    @JoinTable( 
        name = "productos_vectores", 
        joinColumns = @JoinColumn(
          name = "fk_vector"), 
        inverseJoinColumns = @JoinColumn(
          name = "fk_producto")) 
	@JsonBackReference
    private List<Producto> productos;
	

	@ManyToMany 
    @JoinTable( 
        name = "vectores_calculos", 
        joinColumns = @JoinColumn(
          name = "fk_vector"), 
        inverseJoinColumns = @JoinColumn(
          name = "fk_calculo")) 
	@JsonManagedReference
    private List<Calculo> calculos;
	
	@Min(0)
	private Integer precioFabrica;
	
	@Min(0)
	private Integer precioVenta;
	
	
	
	public Vector() {
		productos=new ArrayList<Producto>();
		calculos=new ArrayList<Calculo>();
		
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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Madera getMadera() {
		return madera;
	}

	public void setMadera(Madera madera) {
		this.madera = madera;
	}

	public List<Calculo> getCalculos() {
		return calculos;
	}

	public void setCalculos(List<Calculo> calculos) {
		this.calculos = calculos;
	}
	
	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
