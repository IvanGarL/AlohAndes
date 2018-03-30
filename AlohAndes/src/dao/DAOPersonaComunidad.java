package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Operador;
import vos.PersonaComunidad;

public class DAOPersonaComunidad extends DAOOperador {
	public final static String USUARIO = "ISIS2304A481810";

	/**
	 * Arraylists de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	public DAOPersonaComunidad() {
		recursos = new ArrayList<Object>();
	}

	public ArrayList<PersonaComunidad> getPersonasComunidad() throws SQLException, Exception {
		ArrayList<PersonaComunidad> personasComunidad = new ArrayList<PersonaComunidad>();

		String sql = String.format("SELECT * FROM ( %1$s.PERSONASCOM NATURAL INNER JOIN %1$s.OPERADORES)", USUARIO);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			personasComunidad.add(convertResultSetToPersonaComunidad(rs));
		}

		return personasComunidad;
	}

	public PersonaComunidad findPersonaComunidadById(Long id) throws SQLException, Exception {
		String sql = String.format("SELECT * FROM ( %1$s.PERSONASCOM NATURAL INNER JOIN %1$s.OPERADORES) WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			return convertResultSetToPersonaComunidad(rs);
		}
		return null;
	}

	public void addPersonaComunidad(PersonaComunidad personaComunidad) throws SQLException, Exception {

		addOperador(personaComunidad);
		
		String sql = String.format("INSERT INTO %1$s.PERSONASCOM (ID, CEDULA, EDAD) VALUES (%2$s, '%3$s', '%4$s')", 
				USUARIO, 
				personaComunidad.getId(),
				personaComunidad.getCedula(),
				personaComunidad.getEdad());	
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updatePersonaComunidad(PersonaComunidad personaComunidad) throws SQLException, Exception {
		updateOperador(personaComunidad);

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.PERSONASCOM SET ", USUARIO));
		sql.append(String.format( "EDAD = '%1$s', CEDULA = '%2$s'", 
				personaComunidad.getEdad(),
				personaComunidad.getCedula()));
		sql.append ("WHERE ID = " + personaComunidad.getId ());


		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void deletePersonaComunidad(PersonaComunidad personaComunidad) throws SQLException, Exception {

		deleteOperador(personaComunidad);

		String sql = String.format("DELETE FROM %1$s.PERSONASCOM WHERE ID = %2$d", USUARIO, personaComunidad.getId());

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS AUXILIARES
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * Metodo encargado de inicializar la conexion del DAO a la Base de Datos a partir del parametro <br/>
	 * <b>Postcondicion: </b> el atributo conn es inicializado <br/>
	 * @param connection la conexion generada en el TransactionManager para la comunicacion con la Base de Datos
	 */
	public void setConn(Connection connection){
		this.conn = connection;
	}

	/**
	 * Metodo que cierra todos los recursos que se encuentran en el arreglo de recursos<br/>
	 * <b>Postcondicion: </b> Todos los recurso del arreglo de recursos han sido cerrados.
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}
	//..................
	private PersonaComunidad convertResultSetToPersonaComunidad(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub

		//Atributos heredados
		Long id = rs.getLong("ID");
		Integer capacidad = rs.getInt("CAPACIDAD");
		String nombre = rs.getString("NOMBRE");
		Integer telefono = rs.getInt("TELEFONO");
		//Atributos propios
		Integer edad = rs.getInt("EDAD");
		Long cedula = rs.getLong("CEDULA");
		
		//ES IMPORTANTE REVISAR SI SE NECESITAN LOS ATRIBUTOS DE LOGIN Y CONTRASENIA
		String login;
		String contrasenia;
		
		return null;//new PersonaComunidad(nombre, edad, cedula, login, contrasenia, id, capacidad, telefono);
	}
}
