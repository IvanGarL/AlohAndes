package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class PersonaComunidad extends Operador{

	@JsonProperty(value="cedula")
	private Long cedula;


	@JsonProperty(value="edad")
	private Integer edad;
	
	//ES IMPORTANTE REVISAR SI SE NECESITAN LOS ATRIBUTOS LOGIN Y CONTRASENIA
	public PersonaComunidad(@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="edad") Integer edad, 
			@JsonProperty(value = "cedula") Long cedula, 
			@JsonProperty(value="login")String login, 
			@JsonProperty(value="contrasenia")String contrasenia,
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="capacidad") Integer capacidad, 
			@JsonProperty(value="telefono") Integer telefono)
			{

		super(id, capacidad, nombre, telefono, Operador.PERSONA_COMUNIDAD);
		this.edad = edad;
		this.cedula = cedula;
	}


	public Long getCedula() {
		return cedula;
	}


	public void setCedula(Long cedula) {
		this.cedula = cedula;
	}


	public Integer getEdad() {
		return edad;
	}


	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	
}
