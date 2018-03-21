package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Responsable extends Usuario{
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	@JsonProperty(value="cedula")
	private Long cedula;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * Edad del responsable
	 */
	@JsonProperty(value="edad")
	private Integer edad;
	
	/**
	 * Telefono del responsable
	 */
	@JsonProperty(value="telefono")
	private Integer telefono;
	
	
	public Responsable(@JsonProperty(value="id") Long id,
			@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="edad") Integer edad,
			@JsonProperty(value = "telefono")Integer telefono,
			@JsonProperty(value = "cedula") Long cedula,
			@JsonProperty(value="login")String login,
			@JsonProperty(value="contrasenia")String contrasenia) {
		
		super(id, login, contrasenia, Usuario.RESPONSABLE);
		this.edad = edad;
		this.telefono = telefono;
		this.nombre = nombre;
		this.cedula = cedula;
	}


	public Long getCedula() {
		return cedula;
	}


	public void setCedula(Long cedula) {
		this.cedula = cedula;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Integer getEdad() {
		return edad;
	}


	public void setEdad(Integer edad) {
		this.edad = edad;
	}


	public Integer getTelefono() {
		return telefono;
	}


	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}
	
	
}
