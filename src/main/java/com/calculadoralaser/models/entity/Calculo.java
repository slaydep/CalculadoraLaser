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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "calculos")
public class Calculo implements Serializable {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descripcion;
	
	@NotNull
	@Min(0)
	private Integer horas;
	
	@NotNull
	@Min(0)
	@Max(59)
	private Integer minutos;
	
	@NotNull
	@Min(0)
	@Max(59)
	private Integer segundos;
	
	@NotNull
	@Min(0)
	private Float lado1;
	
	@NotNull
	@Min(0)
	private Float lado2;
	
	@NotNull
	@Min(0)
	private Float factor;
	
	@Min(0)
	private Integer precioFabrica;
	
	
	@Min(0)
	private Integer precioVenta;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_madera")
	@JsonBackReference
	private Madera madera;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_maquina")
	@JsonBackReference
	private Maquina maquina;
	

	@ManyToMany 
    @JoinTable( 
        name = "vectores_calculos", 
        joinColumns = @JoinColumn(
          name = "fk_calculo"), 
        inverseJoinColumns = @JoinColumn(
          name = "fk_vector")) 
	@JsonBackReference
    private List<Vector> vectores;

	@NotNull
	@Column(name = "create_at", updatable=false)
	@Temporal(TemporalType.DATE)
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
	
	private static final long serialVersionUID = 1L;

	public Calculo() {
		vectores=new ArrayList<Vector>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Madera getMadera() {
		return madera;
	}

	public void setMadera(Madera madera) {
		this.madera = madera;
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}


	public Integer getHoras() {
		return horas;
	}

	public void setHoras(Integer horas) {
		this.horas = horas;
	}

	public Integer getMinutos() {
		return minutos;
	}

	public void setMinutos(Integer minutos) {
		this.minutos = minutos;
	}

	public Integer getSegundos() {
		return segundos;
	}

	public void setSegundos(Integer segundos) {
		this.segundos = segundos;
	}

	public Float getLado1() {
		return lado1;
	}

	public void setLado1(Float lado1) {
		this.lado1 = lado1;
	}

	public Float getLado2() {
		return lado2;
	}

	public void setLado2(Float lado2) {
		this.lado2 = lado2;
	}

	public Float getFactor() {
		return factor;
	}

	public void setFactor(Float factor) {
		this.factor = factor;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<Vector> getVectores() {
		return vectores;
	}
	public void setVectores(List<Vector> vectores) {
		this.vectores = vectores;
	}
	
}
