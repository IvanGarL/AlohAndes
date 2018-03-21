package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Operador {
	
	public final static String HOTEL = "hotel";
	public final static String HOSTAL = "hostal";
	public final static String PERSONA_NATURAL = "pernat";
	public final static String PERSONA_COMUNIDAD = "percom";
	public final static String VIVIENDA_UNIVERSITARIA = "vivuni";
	
	@JsonProperty(value="id")
	private Long id;	
	
	@JsonProperty(value="capacidad")
	private Integer capacidad;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="telefono")
	private Integer telefono;

	public Operador(@JsonProperty(value="id") Long id,
			@JsonProperty(value="capacidad") Integer capacidad, 
			@JsonProperty(value="nombre") String nombre, 
			@JsonProperty(value="telefono") Integer telefono) {
		this.id = id;
		this.capacidad = capacidad;
		this.nombre = nombre;
		this.telefono = telefono;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
