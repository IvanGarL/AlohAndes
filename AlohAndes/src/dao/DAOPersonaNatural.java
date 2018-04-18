package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.PersonaNatural;

public class DAOPersonaNatural extends DAOOperador {
	public final static String USUARIO = "ISIS2304A481810";

	/**
	 * Arraylists de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	public DAOPersonaNatural() {
		recursos = new ArrayList<Object>();
	}

	public ArrayList<PersonaNatural> getPersonasNatural() throws SQLException, Exception {
		ArrayList<PersonaNatural> personasNatural = new ArrayList<PersonaNatural>();

		String sql = String.format("SELECT * FROM ( %1$s.PERSONASNAT NATURAL INNER JOIN %1$s.OPERADORES)", USUARIO);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			personasNatural.add(convertResultSetToPersonaNatural(rs));
		}

		return personasNatural;
	}

	public PersonaNatural findPersonaNaturalById(Long id) throws SQLException, Exception {
		String sql = String.format("SELECT * FROM ( %1$s.PERSONASNAT NATURAL INNER JOIN %1$s.OPERADORES) WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			return convertResultSetToPersonaNatural(rs);
		}
		return null;
	}

	public void addPersonaNatural(PersonaNatural personaNatural) throws SQLException, Exception {

		addOperador(personaNatural);
		
		String sql = String.format("INSERT INTO %1$s.PERSONASNAT (ID, CEDULA, EDAD) VALUES (%2$s, '%3$s', '%4$s')", 
				USUARIO, 
				personaNatural.getId(),
				personaNatural.getCedula(),
				personaNatural.getEdad());	
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updatePersonaNatural(PersonaNatural personaNatural) throws SQLException, Exception {
		updateOperador(personaNatural);

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.PERSONASNAT SET ", USUARIO));
		sql.append(String.format( "EDAD = '%1$s', CEDULA = '%2$s'", 
				personaNatural.getEdad(),
				personaNatural.getCedula()));
		sql.append ("WHERE ID = " + personaNatural.getId ());


		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void deletePersonaNatural(PersonaNatural personaNatural) throws SQLException, Exception {

		deleteOperador(personaNatural);

		String sql = String.format("DELETE FROM %1$s.PERSONASNAT WHERE ID = %2$d", USUARIO, personaNatural.getId());

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
	private PersonaNatural convertResultSetToPersonaNatural(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub

		//Atributos heredados
		Long id = rs.getLong("ID");
		Integer capacidad = rs.getInt("CAPACIDAD");
		String nombre = rs.getString("NOMBRE");
		Integer telefono = rs.getInt("TELEFONO");
		//Atributos propios
		Integer edad = rs.getInt("EDAD");
		Long cedula = rs.getLong("CEDULA");
		
		return new PersonaNatural(nombre, edad, cedula, id, capacidad, telefono);
	}
}
