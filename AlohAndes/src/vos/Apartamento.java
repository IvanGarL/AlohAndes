package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Apartamento extends Alojamiento{

	
	@JsonProperty(value="menaje")
	private Boolean menaje;
	
	@JsonProperty(value="direccion")
	private String direccion;
	
	@JsonProperty(value="numHabitaciones")
	private Integer numHabitaciones;
	
	public Apartamento(@JsonProperty(value="id")Long id,
			@JsonProperty(value="menaje") Boolean menaje, 
			@JsonProperty(value="numHabitaciones") Integer numHabitaciones, 
			@JsonProperty(value="direccion")String direccion,
			@JsonProperty(value="capacidad") Integer capacidad,
			@JsonProperty(value="tamanho") Integer tamanho) {

		super(id, tamanho, capacidad, Alojamiento.APARTAMENTO);
		this.menaje = menaje;
		this.direccion = direccion;
		this.numHabitaciones = numHabitaciones;
	}

	public Boolean getMenaje() {
		return menaje;
	}

	public void setMenaje(Boolean menaje) {
		this.menaje = menaje;
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
