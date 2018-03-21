package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Oferta {

	@JsonProperty(value="id")
	private Long id;
	
	@JsonProperty(value="costo")
	private Double costo;
	
	@JsonProperty(value="nombre")
	private String nombre;

	public Oferta(@JsonProperty(value="id") Long id, 
			@JsonProperty(value="costo") Double costo,
			@JsonProperty(value="nombre") String nombre) {
		this.id = id;
		this.costo = costo;
		this.nombre = nombre;
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
	
	
	
	
}
