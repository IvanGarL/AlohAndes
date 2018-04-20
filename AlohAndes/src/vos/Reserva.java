package vos;

import java.util.ArrayList;

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
	private Long idOperador;
	
	@JsonProperty(value="ofertas")
	private ArrayList<Oferta> ofertas;
	
	@JsonProperty(value="idCliente")
	private Long idCliente;

	@JsonProperty(value="estado")
	private String estado;
	
	@JsonProperty(value="colectiva")
	private String colectiva;
	
	public Reserva(@JsonProperty(value="id") Long id, 
		@JsonProperty(value="cobro") Double cobro,
		@JsonProperty(value="estado") String estado,
		@JsonProperty(value="fechaInicio") String fechaInicio, 
		@JsonProperty(value="fechaCierre") String fechaCierre, 
		@JsonProperty(value="fechaRealizacion") String fechaRealizacion,
		@JsonProperty(value="ofertas") ArrayList<Oferta> ofertas,
		@JsonProperty(value="idOperador") Long idOperador,
		@JsonProperty(value="idCliente") Long idCliente,
		@JsonProperty(value="estado") String colectiva) {
		
		this.id = id;
		this.cobro = cobro;
		this.fechaInicio = fechaInicio;
		this.fechaCierre = fechaCierre;
		this.fechaRealizacion = fechaRealizacion;
		this.idOperador = idOperador;
		this.idCliente = idCliente;
		this.estado = estado;
		this.ofertas = ofertas;
		this.colectiva = colectiva;
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

	public Long getIdOperador() {
		return idOperador;
	}

	public void setIdOperador(Long idOperador) {
		this.idOperador = idOperador;
	}


	public ArrayList<Oferta> getOfertas() {
		return ofertas;
	}

	public void setOfertas(ArrayList<Oferta> ofertas) {
		this.ofertas = ofertas;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getColectiva() {
		return colectiva;
	}

	public void setColectiva(String colectiva) {
		this.colectiva = colectiva;
	}
	
	
		
}
