package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Oferta {

	@JsonProperty(value="id")
	private Long id;
	
	@JsonProperty(value="costo")
	private Double costo;
	
	@JsonProperty(value="fechaRetiro")
	private String fechaRetiro;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="idOperador")
	private Integer idOperador;
	
	@JsonProperty(value="idAlojamiento")
	private Integer idAlojamiento;
	
	@JsonProperty(value="estado")
	private String estado;

	public Oferta(@JsonProperty(value="id") Long id, 
			@JsonProperty(value="costo") Double costo,
			@JsonProperty(value="fechaRetiro") String fechaRetiro,
			@JsonProperty(value="nombre") String nombre,
			@JsonProperty(value="idOperador") Integer idOperador,
			@JsonProperty(value="idAlojamiento") Integer idAlojamiento,
			@JsonProperty(value="estado") String estado) {
		this.id = id;
		this.fechaRetiro = fechaRetiro;
		this.costo = costo;
		this.nombre = nombre;
		this.idOperador = idOperador;
		this.idAlojamiento = idAlojamiento;
		this.estado = estado;
	}

	public Double getCosto() {
		return costo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaRetiro() {
		return fechaRetiro;
	}

	public void setFechaRetiro(String fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}

	public Integer getIdOperador() {
		return idOperador;
	}

	public void setIdOperador(Integer idOperador) {
		this.idOperador = idOperador;
	}

	public Integer getIdAlojamiento() {
		return idAlojamiento;
	}

	public void setIdAlojamiento(Integer idAlojamiento) {
		this.idAlojamiento = idAlojamiento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	
}
