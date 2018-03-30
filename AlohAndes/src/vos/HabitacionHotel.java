package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class HabitacionHotel extends Alojamiento{
	
	public final static String ESTANDAR = "ESTANDAR";
	
	public final static String SEMI_SUITE = "SEMI SUITE";
	
	public final static String SUITE = "SUITE";

	
	@JsonProperty(value="numero")
	private Integer numero;
	
	@JsonProperty(value="categoria")
	private String categoria;
	
	@JsonProperty(value="ubicacion")
	private String ubicacion;
	
	@JsonProperty(value="hotel")
	private Long hotel;
	
	public HabitacionHotel(@JsonProperty(value="id")Long id,
			@JsonProperty(value="numero") Integer numero,
			@JsonProperty(value="categoria") String categoria, 
			@JsonProperty(value="ubicacion") String ubicacion,
			@JsonProperty(value="tamanho") Integer tamanho, 
			@JsonProperty(value="capacidad") Integer capacidad,
			@JsonProperty(value="hotel") Long hotel){
		super(id, tamanho, capacidad, Alojamiento.HABITACION_HOTEL);
		this.ubicacion = ubicacion;
		this.numero = numero;
		this.categoria = categoria;
		this.hotel = hotel;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Long getHotel() {
		return hotel;
	}

	public void setHotel(Long hotel) {
		this.hotel = hotel;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	
	
}
