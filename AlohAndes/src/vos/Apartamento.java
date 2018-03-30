package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Apartamento extends Alojamiento{

	@JsonProperty(value="direccion")
	private String direccion;
	
	@JsonProperty(value="menaje")
	private Boolean menaje;
	
	@JsonProperty(value="amoblado")
	private Boolean amoblado;
	
	@JsonProperty(value="numHabitaciones")
	private Integer numHabitaciones;
	
	@JsonProperty(value="personaComunidad")
	private Long personaComunidad;
	
	public Apartamento(@JsonProperty(value="id")Long id,
			@JsonProperty(value="direccion")String direccion,
			@JsonProperty(value="menaje") Boolean menaje,
			@JsonProperty(value="amoblado") Boolean amoblado,
			@JsonProperty(value="numHabitaciones") Integer numHabitaciones, 
			@JsonProperty(value="capacidad") Integer capacidad,
			@JsonProperty(value="tamanho") Integer tamanho,
			@JsonProperty(value="personaComunidad") Long personaComunidad) {

		super(id, tamanho, capacidad, Alojamiento.APARTAMENTO);
		this.menaje = menaje;
		this.direccion = direccion;
		this.numHabitaciones = numHabitaciones;
		this.personaComunidad = personaComunidad;

	}

	public Boolean getMenaje() {
		return menaje;
	}

	public void setMenaje(Boolean menaje) {
		this.menaje = menaje;
	}
	
	
	
	public Boolean getAmoblado() {
		return amoblado;
	}

	public void setAmoblado(Boolean amoblado) {
		this.amoblado = amoblado;
	}

	public Long getPersonaComunidad() {
		return personaComunidad;
	}

	public void setPersonaComunidad(Long personaComunidad) {
		this.personaComunidad = personaComunidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getNumHabitaciones() {
		return numHabitaciones;
	}

	public void setNumHabitaciones(Integer numHabitaciones) {
		this.numHabitaciones = numHabitaciones;
	}
	
	
}
