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
			@JsonProperty(value="tamanho") Integer tamanho, 
			@JsonProperty(value="capacidad") Integer capacidad,
			@JsonProperty(value="idOperador") Long idOperador,
			@JsonProperty(value="idOferta") Long idOferta){
		
		super(id, tamanho, capacidad, Alojamiento.HABITACION, idOferta);
		this.compartida = compartida;
		this.numero = numero;
		this.idOperador = idOperador;
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
