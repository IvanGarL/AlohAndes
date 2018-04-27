package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReservaEjColectiva {
	@JsonProperty(value="fechaInicio")
	private String fechaInicio;
	
	@JsonProperty(value="fechaCierre")
	private String fechaCierre;

	@JsonProperty(value="idCliente")
	private Long idCliente;
	
	@JsonProperty(value="tipoViv")
	private String tipoViv;
	
	@JsonProperty(value="idCol")
	private Long idCol;
	
	
	public ReservaEjColectiva(
			@JsonProperty(value="fechaInicio") String fechaInicio, 
			@JsonProperty(value="fechaCierre") String fechaCierre,
			@JsonProperty(value="idCliente") Long idCliente,
			@JsonProperty(value="tipoViv") String tipoViv,
			@JsonProperty(value="idCol") Long idCol) {
			
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

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getTipoViv() {
		return tipoViv;
	}

	public void setTipoViv(String tipoViv) {
		this.tipoViv = tipoViv;
	}

	public Long getIdCol() {
		return idCol;
	}

	public void setIdCol(Long idCol) {
		this.idCol = idCol;
	}
	
	
}
