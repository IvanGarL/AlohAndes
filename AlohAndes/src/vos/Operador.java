package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Operador {
	
	public final static String HOTEL = "hotel";
	public final static String HOSTAL = "hostal";
	public final static String PERSONA_NATURAL = "pernat";
	public final static String PERSONA_COMUNIDAD = "percom";
	public final static String VIVIENDA_UNIVERSITARIA = "vivuni";
	
	@JsonProperty(value="id")
	protected Long id;	
	
	@JsonProperty(value="capacidad")
	protected Integer capacidad;
	
	@JsonProperty(value="nombre")
	protected String nombre;
	
	@JsonProperty(value="telefono")
	protected Integer telefono;

	@JsonProperty(value="tipo")
	protected String tipo;

	public Operador(@JsonProperty(value="id") Long id,
			@JsonProperty(value="capacidad") Integer capacidad, 
			@JsonProperty(value="nombre") String nombre, 
			@JsonProperty(value="telefono") Integer telefono,
			@JsonProperty(value="tipo") String tipo) {
		this.id = id;
		this.capacidad = capacidad;
		this.nombre = nombre;
		this.telefono = telefono;
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}
	
	
	
	
}
