package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class HabitacionUniversitaria extends Alojamiento{

	
	@JsonProperty(value="ubicacion")
	private String ubicacion;
	
	@JsonProperty(value="numero")
	private Integer numero;
	
	@JsonProperty(value="menaje")
	private Boolean menaje;
	
	
	public HabitacionUniversitaria(@JsonProperty(value="id")Long id,
			@JsonProperty(value="ubicacion") String ubicacion, 
			@JsonProperty(value="numero") Integer numero,
			@JsonProperty(value="menaje") Boolean menaje,
			@JsonProperty(value="tamanho") Integer tamanho, 
			@JsonProperty(value="capacidad") Integer capacidad,
			@JsonProperty(value="idVivuni") Long idVivuni,
			@JsonProperty(value="idOferta") Long idOferta){
		super(id, tamanho, capacidad, Alojamiento.HABITACION_UNIVERSITARIA, idOferta, idVivuni);
		this.ubicacion = ubicacion;
		this.numero = numero;
		this.menaje = menaje;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
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
