package com.calculadoralaser.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="conceptos_ingresos")
public class ConceptosIngresos implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min = 3,max = 100)
	private String nombre;
	
	@Min(0)
	private Integer previsto;
	
	
	@Min(0)
	private Integer real;
	
	@Min(0)
	private Integer diferencia;
	
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



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public Integer getPrevisto() {
		return previsto;
	}



	public void setPrevisto(Integer previsto) {
		this.previsto = previsto;
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



	public Boolean getFlagVigencia() {
		return flagVigencia;
	}



	public void setFlagVigencia(Boolean flagVigencia) {
		this.flagVigencia = flagVigencia;
	}



	public Integer getReal() {
		return real;
	}



	public void setReal(Integer real) {
		this.real = real;
	}



	public Integer getDiferencia() {
		return diferencia;
	}



	public void setDiferencia(Integer diferencia) {
		this.diferencia = diferencia;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

