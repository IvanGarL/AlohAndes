package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class HabitacionUniversitaria extends Alojamiento{

	
	@JsonProperty(value="ubicacion")
	private String ubicacion;
	
	@JsonProperty(value="numero")
	private Integer numero;
	
	@JsonProperty(value="menaje")
	private Boolean menaje;
	
	@JsonProperty(value="viviendaUniv")
	private Long viviendaUniv;
	
	
	public HabitacionUniversitaria(@JsonProperty(value="id")Long id,
			@JsonProperty(value="ubicacion") String ubicacion, 
			@JsonProperty(value="numero") Integer numero,
			@JsonProperty(value="menaje") Boolean menaje,
			@JsonProperty(value="tamanho") Integer tamanho, 
			@JsonProperty(value="capacidad") Integer capacidad,
			@JsonProperty(value="viviendaUniv") Long viviendaUniv){
		super(id, tamanho, capacidad, Alojamiento.HABITACION_UNIVERSITARIA, viviendaUniv, ubicacion);
		this.ubicacion = ubicacion;
		this.numero = numero;
		this.menaje = menaje;
		this.viviendaUniv = viviendaUniv;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Long getViviendaUniv() {
		return viviendaUniv;
	}

	public void setViviendaUniv(Long viviendaUni) {
		this.viviendaUniv = viviendaUni;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Boolean getMenaje() {
		return menaje;
	}

	public void setMenaje(Boolean menaje) {
		this.menaje = menaje;
	}
	
}
