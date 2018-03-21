package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Alojamiento {

	@JsonProperty(value="capacidad")
	private Integer capacidad;
	
	@JsonProperty(value="tamanho")
	private Integer tamanho;
	
	public Alojamiento(@JsonProperty(value="tamanho") Integer tamanho, @JsonProperty(value="capacidad") Integer capacidad) {
		this.capacidad = capacidad;
		this.tamanho = tamanho;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public Integer getTamanho() {
		return tamanho;
	}

	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}
	
}
