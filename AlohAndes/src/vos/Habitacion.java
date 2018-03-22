package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Habitacion extends Alojamiento{

	@JsonProperty(value="compartida")
	private Boolean compartida;
	
	@JsonProperty(value="numero")
	private Integer numero;
	
	
	public Habitacion(@JsonProperty(value="id")Long id,
			@JsonProperty(value="compartida") Boolean compartida,
			@JsonProperty(value="numero") Integer numero,
			@JsonProperty(value="categoria") String categoria, 
			@JsonProperty(value="tamanho") Integer tamanho, 
			@JsonProperty(value="capacidad") Integer capacidad){
		super(id, tamanho, capacidad, Alojamiento.HABITACION);
		this.compartida = compartida;
		this.numero = numero;
	}

	public Boolean getCompartida() {
		return compartida;
	}

	public void setCompartida(Boolean compartida) {
		this.compartida = compartida;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
}
