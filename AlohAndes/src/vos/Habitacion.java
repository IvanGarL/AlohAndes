package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Habitacion extends Alojamiento{

	@JsonProperty(value="compartida")
	private Boolean compartida;
	
	@JsonProperty(value="numero")
	private Integer numero;
	
	
	@JsonProperty(value="idHostal")
	private Long idHostal;
	
	@JsonProperty(value="idPersonaNat")
	private Long idPersonaNat;
	
	public Habitacion(@JsonProperty(value="id")Long id,
			@JsonProperty(value="compartida") Boolean compartida,
			@JsonProperty(value="numero") Integer numero,
			@JsonProperty(value="tamanho") Integer tamanho, 
			@JsonProperty(value="capacidad") Integer capacidad,
			@JsonProperty(value="idPersonaNat") Long idPersonaNat,
			@JsonProperty(value="idHostal") Long idHostal,
			@JsonProperty(value="idOferta") Long idOferta) throws Exception{
		
		super(id, tamanho, capacidad, Alojamiento.HABITACION, idOferta);
		this.compartida = compartida;
		this.numero = numero;
		if(idPersonaNat != null && idHostal == null){
			this.idOperador = idPersonaNat;
			this.idPersonaNat = idPersonaNat;
		}
		else if(idHostal != null && idPersonaNat == null){
			this.idOperador = idHostal;
			this.idHostal = idHostal;
		}
		else if(idHostal == null && idPersonaNat == null){
			throw new Exception("los dos identificadores de operador no pueden ser null, por favor asigne un operador");
		}
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

	public Long getIdHostal() {
		return idHostal;
	}

	public void setIdHostal(Long idHostal) {
		this.idHostal = idHostal;
	}

	public Long getIdPersonaNat() {
		return idPersonaNat;
	}

	public void setIdPersonaNat(Long idPersonaNat) {
		this.idPersonaNat = idPersonaNat;
	}
	

	
}
