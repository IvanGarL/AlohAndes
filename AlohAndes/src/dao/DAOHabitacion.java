package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Habitacion;

public class DAOHabitacion extends DAOAlojamiento{
	
	public final static String USUARIO = "ISIS2304A481810";
	
	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;
	
	public DAOHabitacion() {
		recursos = new ArrayList<Object>();
	}
	
	public ArrayList<Habitacion> getHabitacionesUniversitarias() throws SQLException, Exception {
		ArrayList<Habitacion> hab = new ArrayList<Habitacion>();

		String sql = String.format("SELECT * FROM %1$s.HABITACIONES", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			hab.add(convertResultSetToHabitacionUniversitaria(rs));
		}
		
		return hab;
	}
	
	public Habitacion findHabitacion(Long id) throws SQLException, Exception 
	{
		Habitacion hab = null;

		String sql = String.format("SELECT * FROM %1$s.HABITACIONES WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			hab = convertResultSetToHabitacionUniversitaria(rs);
		}

		return hab;
	}
	
	public void addHabitacion(Habitacion hab) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.HABITACIONES(ID, COMPARTIDA, NUMERO, HOSTAL, PERSONANAT) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s')", 
									USUARIO,
									hab.getId(),
									hab.getCompartida(),
									hab.getNumero(),
									hab.getHostal(),
									hab.getPersonaNat());
		System.out.println(sql);
		
		//
		//
		addAlojamiento(hab);
		//
		//
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateHabitacion(Habitacion hab) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.HABITACIONES SET ", USUARIO));
		sql.append(String.format( "ID = '%1$s' AND COMPARTIDA = '%2$s' AND NUMERO = '%3$s' AND HOSTAL = '%4$s' AND PERSONANAT = '%5$s' ", 
				hab.getId(),
				hab.getCompartida(),
				hab.getNumero(),
				hab.getHostal(),
				hab.getPersonaNat()));
		
		System.out.println(sql);
		
		//
		//
		updateAlojamiento(hab);
		//
		//
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	public void deleteHabUniversitaria(Habitacion habUniv) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.HABSUNIV WHERE ID = %2$d", USUARIO, habUniv.getId());

		System.out.println(sql);
		
		//
		//
		deleteAlojamiento(habUniv);
		//
		//
		
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
	
	public Habitacion convertResultSetToHabitacionUniversitaria(ResultSet resultSet) throws SQLException {
	
		Long id = resultSet.getLong("ID");
		Boolean compartida = resultSet.getBoolean("COMPARTIDA");
		Integer numero = resultSet.getInt("NUMERO");
		Integer capacidad = resultSet.getInt("CAPACIDAD");
		Integer tamanho = resultSet.getInt("TAMANHO");
		Long hostal = resultSet.getLong("HOSTAL");
		Long personaNat = resultSet.getLong("PERSONANAT");

		Habitacion hab = new Habitacion(id, compartida, numero, capacidad, tamanho, hostal, personaNat);

		return hab;
	}
}
