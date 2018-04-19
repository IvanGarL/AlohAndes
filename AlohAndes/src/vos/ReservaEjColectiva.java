package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReservaEjColectiva {
	@JsonProperty(value="fechaInicio")
	private String fechaInicio;
	
	@JsonProperty(value="fechaCierre")
	private String fechaCierre;

	@JsonProperty(value="idCliente")
	private Integer idCliente;
	
	@JsonProperty(value="tipoViv")
	private String tipoViv;
	
	public ReservaEjColectiva(
			@JsonProperty(value="fechaInicio") String fechaInicio, 
			@JsonProperty(value="fechaCierre") String fechaCierre,
			@JsonProperty(value="idCliente") Integer idCliente,
			@JsonProperty(value="tipoViv") String tipoViv) {
			
			this.fechaInicio = fechaInicio;
			this.fechaCierre = fechaCierre;
			this.idCliente = idCliente;
			this.tipoViv = tipoViv;
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

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getTipoViv() {
		return tipoViv;
	}

	public void setTipoViv(String tipoViv) {
		this.tipoViv = tipoViv;
	}
	
	
}
