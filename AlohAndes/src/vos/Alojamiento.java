package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Alojamiento {
	
	public final static String HABITACION_HOTEL = "habhot";
	public final static String APARTAMENTO = "aparta";
	public final static String HABITACION = "habita";
	public final static String HABITACION_UNIVERSITARIA = "habiuni";
	public final static String VIVIENDA_COMUNITARIA = "vivcom";

	@JsonProperty(value="id")
	protected Long id;
	
	@JsonProperty(value="capacidad")
	protected Integer capacidad;
	
	@JsonProperty(value="tamanho")
	protected Integer tamanho;
	
	@JsonProperty(value="tipo")
	protected String tipo;
	
	@JsonProperty(value="idOferta")
	protected Long idOferta;
	
	@JsonProperty(value="idOperador")
	protected Long idOperador;
	
	
	public Alojamiento(@JsonProperty(value="id") Long id, 
			@JsonProperty(value="tamanho") Integer tamanho,
			@JsonProperty(value="capacidad") Integer capacidad,
			@JsonProperty(value="tipo") String tipo,
			@JsonProperty(value="idOferta") Long idOferta) {
		this.tipo = tipo;
		this.capacidad = capacidad;
		this.tamanho = tamanho;
		this.id = id;
		this.idOferta = idOferta;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(Long idOferta) {
		this.idOferta = idOferta;
	}

	public Long getIdOperador() {
		return idOperador;
	}

	public void setIdOperador(Long idOperador) {
		this.idOperador = idOperador;
	}
	
	
	
}
