package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ViviendaComunidad extends Alojamiento{

	@JsonProperty(value="menaje")
	private Boolean menaje;

	@JsonProperty(value="diasUso")
	private Integer diasUso;

	@JsonProperty(value="numHabitaciones")
	private Integer numHabitaciones;

	@JsonProperty(value="direccion")
	private String direccion;

	public ViviendaComunidad(@JsonProperty(value="menaje") Boolean menaje, @JsonProperty(value="diasUso") Integer diasUso, @JsonProperty(value="numHabitaciones") Integer numHabitaciones, @JsonProperty(value="direccion")String direccion, @JsonProperty(value="capacidad") Integer capacidad, @JsonProperty(value="tamanho") Integer tamanho) {

		super(tamanho, capacidad);
		this.menaje = menaje;
		this.diasUso = diasUso;
		this.direccion = direccion;
		this.numHabitaciones = numHabitaciones;
	}

	public Boolean getMenaje() {
		return menaje;
	}

	public void setMenaje(Boolean menaje) {
		this.menaje = menaje;
	}

	public Integer getDiasUso() {
		return diasUso;
	}

	public void setDiasUso(Integer diasUso) {
		this.diasUso = diasUso;
	}

	public Integer getNumHabitaciones() {
		return numHabitaciones;
	}

	public void setNumHabitaciones(Integer numHabitaciones) {
		this.numHabitaciones = numHabitaciones;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


}
