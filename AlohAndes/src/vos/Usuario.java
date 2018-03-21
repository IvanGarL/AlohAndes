package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {

	/**
	 * login del usuario
	 */
	@JsonProperty(value="login")
	protected String login;
	
	
	/**
	 * contraseña del usuario
	 */
	@JsonProperty(value="contrasenia")
	protected String contrasenia;
	
	public Usuario(@JsonProperty(value="login")String login, @JsonProperty(value="contrasenia") String contrasenia){
		
		this.login = login;
		this.contrasenia = contrasenia;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	
	
}
