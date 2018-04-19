package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Habitacion extends Alojamiento{

	@JsonProperty(value="compartida")
	private Boolean compartida;
	
	@JsonProperty(value="numero")
	private Integer numero;
	
	@JsonProperty(value="hostal")
	private Long hostal;
	
	@JsonProperty(value="personaNat")
	private Long personaNat;
	
	
	public Habitacion(@JsonProperty(value="id")Long id,
			@JsonProperty(value="compartida") Boolean compartida,
			@JsonProperty(value="numero") Integer numero,
			@JsonProperty(value="tamanho") Integer tamanho, 
			@JsonProperty(value="capacidad") Integer capacidad,
			@JsonProperty(value="hostal") Long hostal,
			@JsonProperty(value="personaNat") Long personaNat){
		
		super(id, tamanho, capacidad, Alojamiento.HABITACION, personaNat, "");
		this.compartida = compartida;
		this.numero = numero;
		this.hostal = hostal;
		this.personaNat = personaNat;
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

	public Long getHostal() {
		return hostal;
	}

	public void setHostal(Long hostal) {
		this.hostal = hostal;
	}

	public Long getPersonaNat() {
		return personaNat;
	}

	public void setPersonaNat(Long personaNat) {
		this.personaNat = personaNat;
	}
	
	
}
