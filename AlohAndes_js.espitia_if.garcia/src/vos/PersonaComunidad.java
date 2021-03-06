package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class PersonaComunidad extends Operador{

	@JsonProperty(value="cedula")
	private Long cedula;


	@JsonProperty(value="edad")
	private Integer edad;
	
	public PersonaComunidad(@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="edad") Integer edad, 
			@JsonProperty(value = "cedula") Long cedula,
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="capacidad") Integer capacidad, 
			@JsonProperty(value="telefono") Integer telefono,
			@JsonProperty(value="idResponsable") Long idResponsable){

		super(id, capacidad, nombre, telefono, Operador.PERSONA_COMUNIDAD, idResponsable);
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
