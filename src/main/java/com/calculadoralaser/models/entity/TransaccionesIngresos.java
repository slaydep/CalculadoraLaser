package com.calculadoralaser.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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


@Entity
@Table(name="transacciones_ingresos")
public class TransaccionesIngresos implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(max = 200)
	private String descripcion;
	
	
	@Min(0)
	private Double cantidad;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private ConceptosIngresos conceptosIngresos;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Cuentas cuentas;
	
//	@NotEmpty
//	@Size(max = 200)
//	private String nota;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createAt;
	
	//@NotNull
	@Column(name = "last_update")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date lastUpdate;
	
	
	private Boolean flagVigencia;
	
	@PrePersist
	private void prepers() {
		this.lastUpdate=new Date();
		//System.out.println("fecha: "+this.createAt);
	}
	
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public Double getCantidad() {
		return cantidad;
	}



	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	

	public ConceptosIngresos getConceptosIngresos() {
		return conceptosIngresos;
	}



	public void setConceptosIngresos(ConceptosIngresos conceptoIngreso) {
		this.conceptosIngresos = conceptoIngreso;
	}



	public Cuentas getCuentas() {
		return cuentas;
	}



	public void setCuentas(Cuentas cuenta) {
		this.cuentas = cuenta;
	}



//	public String getNota() {
//		return nota;
//	}
//
//
//
//	public void setNota(String nota) {
//		this.nota = nota;
//	}



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



	public Boolean getFlagVigencia() {
		return flagVigencia;
	}



	public void setFlagVigencia(Boolean flagVigencia) {
		this.flagVigencia = flagVigencia;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}

