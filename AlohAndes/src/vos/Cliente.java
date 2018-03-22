package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cliente extends Usuario{
	
	
	@JsonProperty(value="cedula")
	private Integer cedula;
	
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	
	@JsonProperty(value="edad")
	private Integer edad;
	
	@JsonProperty(value="telefono")
	private Integer telefono;
	
	
	public Cliente(@JsonProperty(value="id") Long id,
			@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="edad") Integer edad,
			@JsonProperty(value = "telefono")Integer telefono,
			@JsonProperty(value = "cedula") Integer cedula,
			@JsonProperty(value="login")String login,
			@JsonProperty(value="contrasenia")String contrasenia ){
		super(id, login,contrasenia, Usuario.CLIENTE);
		this.nombre = nombre;
		this.edad = edad;
		this.cedula = cedula;
		this.telefono = telefono;
	}


	public Integer getCedula() {
		return cedula;
	}


	public void setCedula(Integer cedula) {
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
