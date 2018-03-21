package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Reserva {

	@JsonProperty(value="id")
	private Long id;
	
	@JsonProperty(value="pago")
	private Double pago;
	
	@JsonProperty(value="fechaInicio")
	private String fechaInicio;
	
	@JsonProperty(value="fechaCierre")
	private String fechaCierre;
	
	@JsonProperty(value="fechaRealizacion")
	private String fechaRealizacion;

	
	
	public Reserva(@JsonProperty(value="id") Long id, 
		@JsonProperty(value="pago") Double pago, 
		@JsonProperty(value="fechaInicio") String fechaInicio, 
		@JsonProperty(value="fechaCierre") String fechaCierre, 
		@JsonProperty(value="fechaRealizacion") String fechaRealizacion) {
		
		this.id = id;
		this.pago = pago;
		this.fechaInicio = fechaInicio;
		this.fechaCierre = fechaCierre;
		this.fechaRealizacion = fechaRealizacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPago() {
		return pago;
	}

	public void setPago(Double pago) {
		this.pago = pago;
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

	
	
		
}
