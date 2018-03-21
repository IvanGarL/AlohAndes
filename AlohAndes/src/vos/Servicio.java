package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Servicio {
	
	@JsonProperty(value="id")
	private Long id;
	
	@JsonProperty(value="costo")
	private Double costo;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="descripcion")
	private String descripcion;

	public Servicio(@JsonProperty(value="id") Long id, 
			@JsonProperty(value="costo") Double costo,
			@JsonProperty(value="nombre") String nombre,
			@JsonProperty(value="descripcion") String descripcion){
		this.id = id;
		this.costo = costo;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCosto() {
		return costo;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
