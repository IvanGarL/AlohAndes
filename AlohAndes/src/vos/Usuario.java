package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {

	public final static String CLIENTE = "Cliente";
	public final static String RESPONSABLE = "Responsable";
	
	@JsonProperty(value="id")
	protected Long id;
	
	@JsonProperty(value="login")
	protected String login;
	
	@JsonProperty(value="tipo")
	protected String tipo;

	@JsonProperty(value="contrasenia")
	protected String contrasenia;
	
	public Usuario(@JsonProperty(value="id") Long id,
			@JsonProperty(value="login")String login,
			@JsonProperty(value="contrasenia") String contrasenia,
			@JsonProperty(value="tipo") String tipo){
		
		this.id = id;
		this.tipo = tipo;
		this.login = login;
		this.contrasenia = contrasenia;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	
	
}
