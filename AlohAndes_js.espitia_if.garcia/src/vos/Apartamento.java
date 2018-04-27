package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Apartamento extends Alojamiento{

	@JsonProperty(value="direccion")
	private String direccion;
	
	@JsonProperty(value="menaje")
	private Boolean menaje;
	
	@JsonProperty(value="numHabitaciones")
	private Integer numHabitaciones;
	
	public Apartamento(@JsonProperty(value="id")Long id,
			@JsonProperty(value="direccion")String direccion,
			@JsonProperty(value="menaje") Boolean menaje,
			@JsonProperty(value="numHabitaciones") Integer numHabitaciones, 
			@JsonProperty(value="capacidad") Integer capacidad,
			@JsonProperty(value="tamanho") Integer tamanho,
			@JsonProperty(value="idOferta") Long idOferta) {

		super(id, tamanho, capacidad, Alojamiento.APARTAMENTO, idOferta);
		this.menaje = menaje;
		this.numHabitaciones = numHabitaciones;
		this.direccion = direccion;
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
