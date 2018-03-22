package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Reserva {

	@JsonProperty(value="id")
	private Long id;
	
	@JsonProperty(value="cobro")
	private Double cobro;
	
	@JsonProperty(value="fechaInicio")
	private String fechaInicio;
	
	@JsonProperty(value="fechaCierre")
	private String fechaCierre;
	
	@JsonProperty(value="fechaRealizacion")
	private String fechaRealizacion;
	
	@JsonProperty(value="idOperador")
	private Integer idOperador;
	
	@JsonProperty(value="idOferta")
	private Integer idOferta;
	
	@JsonProperty(value="idCliente")
	private Integer idCliente;

	
	
	public Reserva(@JsonProperty(value="id") Long id, 
		@JsonProperty(value="cobro") Double cobro, 
		@JsonProperty(value="fechaInicio") String fechaInicio, 
		@JsonProperty(value="fechaCierre") String fechaCierre, 
		@JsonProperty(value="fechaRealizacion") String fechaRealizacion,
		@JsonProperty(value="idOperador") Integer idOperador,
		@JsonProperty(value="idOferta") Integer idOferta,
		@JsonProperty(value="idCliente") Integer idCliente) {
		
		this.id = id;
		this.cobro = cobro;
		this.fechaInicio = fechaInicio;
		this.fechaCierre = fechaCierre;
		this.fechaRealizacion = fechaRealizacion;
		this.idOperador = idOperador;
		this.idOferta = idOferta;
		this.idCliente = idCliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCobro() {
		return cobro;
	}

	public void setCobro(Double pago) {
		this.cobro = pago;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(String fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getFechaRealizacion() {
		return fechaRealizacion;
	}

	public void setFechaRealizacion(String fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}

	public Integer getIdOperador() {
		return idOperador;
	}

	public void setIdOperador(Integer idOperador) {
		this.idOperador = idOperador;
	}

	public Integer getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(Integer idOferta) {
		this.idOferta = idOferta;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	
	
		
}
