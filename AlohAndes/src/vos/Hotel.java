package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Hotel extends Operador{

	@JsonProperty(value="direccion")
	private String direccion;

	@JsonProperty(value="habDisponibles")
	private Integer habDisponibles;

	@JsonProperty(value="habOcupadas")
	private Integer habOcupadas;

	@JsonProperty(value="estrellas")
	private Integer estrellas;

	@JsonProperty(value="rut")
	private Integer rut;

	public Hotel(@JsonProperty(value="direccion") String direccion, 
			@JsonProperty(value="habDisponibles") Integer habDisponibles, 
			@JsonProperty(value="habOcupadas") Integer habOcupadas, 
			@JsonProperty(value="rut") Integer rut,
			@JsonProperty(value="estrellas") Integer estrellas, 
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="capacidad") Integer capacidad, 
			@JsonProperty(value="nombre") String nombre, 
			@JsonProperty(value="telefono") Integer telefono){

		super(id, capacidad, nombre, telefono, Operador.HOTEL);
		this.direccion = direccion;
		this.habDisponibles = habDisponibles;
		this.habOcupadas = habOcupadas;
		this.rut = rut;
		this.estrellas = estrellas;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getHabDisponibles() {
		return habDisponibles;
	}

	public void setHabDisponibles(Integer habDisponibles) {
		this.habDisponibles = habDisponibles;
	}

	public Integer getHabOcupadas() {
		return habOcupadas;
	}

	public void setHabOcupadas(Integer habOcupadas) {
		this.habOcupadas = habOcupadas;
	}

	public Integer getRut() {
		return rut;
	}

	public void setRut(Integer rut) {
		this.rut = rut;
	}



}
