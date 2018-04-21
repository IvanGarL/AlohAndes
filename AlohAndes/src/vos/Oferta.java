package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class Oferta {

	@JsonProperty(value="id")
	private Long id;
	
	@JsonProperty(value="costo")
	private Double costo;
	
	@JsonProperty(value="fechaRetiro")
	private String fechaRetiro;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="idOperador")
	private Long idOperador;
	
	@JsonProperty(value="idAlojamiento")
	private Long idAlojamiento;
	
	@JsonProperty(value="estado")
	private String estado;
		

	public Oferta(@JsonProperty(value="id") Long id, 
			@JsonProperty(value="costo") Double costo,
			@JsonProperty(value="nombre") String nombre,
			@JsonProperty(value="estado") String estado,
			@JsonProperty(value="idOperador") Long idOperador,
			@JsonProperty(value="idAlojamiento") Long idAlojamiento) {
		this.id = id;
		this.costo = costo;
		this.nombre = nombre;
		this.idOperador = idOperador;
		this.idAlojamiento = idAlojamiento;
		this.estado = estado;
	}

	public Double getCosto() {
		return costo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdOperador() {
		return idOperador;
	}

	public void setIdOperador(Long idOperador) {
		this.idOperador = idOperador;
	}

	public Long getIdAlojamiento() {
		return idAlojamiento;
	}

	public void setIdAlojamiento(Long idAlojamiento) {
		this.idAlojamiento = idAlojamiento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	
}
