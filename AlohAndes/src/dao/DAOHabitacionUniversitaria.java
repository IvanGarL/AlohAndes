package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.HabitacionUniversitaria;

public class DAOHabitacionUniversitaria  extends DAOAlojamiento{
	public final static String USUARIO = "ISIS2304A481810";
	
	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;
	
	public DAOHabitacionUniversitaria() {
		recursos = new ArrayList<Object>();
	}
	
	public ArrayList<HabitacionUniversitaria> getHabitacionesUniversitarias() throws SQLException, Exception {
		ArrayList<HabitacionUniversitaria> habsUniv = new ArrayList<HabitacionUniversitaria>();

		String sql = String.format("SELECT * FROM %1$s.HABSUNIV", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habsUniv.add(convertResultSetToHabitacionUniversitaria(rs));
		}
		
		return habsUniv;
	}
	
	public HabitacionUniversitaria findHabUniversitariaById(Long id) throws SQLException, Exception 
	{
		HabitacionUniversitaria habUniv = null;

		String sql = String.format("SELECT * FROM %1$s.HABSUNIV WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			habUniv = convertResultSetToHabitacionUniversitaria(rs);
		}

		return habUniv;
	}
	
	public void addHabUniversitaria(HabitacionUniversitaria habUniv) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.HABSUNIV(ID, UBICACION, NUMERO, MENAJE, VIVIENDAUNIV) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s')", 
									USUARIO,
									habUniv.getId(),
									habUniv.getUbicacion(),
									habUniv.getNumero(),
									habUniv.getMenaje(),
									habUniv.getViviendaUniv());
		System.out.println(sql);
		
		//
		//
		addAlojamiento(habUniv);
		//
		//
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateHabUniversitaria(HabitacionUniversitaria habUniv) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.HABSUNIV SET ", USUARIO));
		sql.append(String.format( "ID = '%1$s' AND UBICACION = '%2$s' AND NUMERO = '%3$s' AND MENAJE = '%4$s' AND VIVIENDAUNIV = '%5$s' ", 
				habUniv.getId(),
				habUniv.getUbicacion(),
				habUniv.getNumero(),
				habUniv.getMenaje(),
				habUniv.getViviendaUniv()));
		
		System.out.println(sql);
		
		//
		//
		updateAlojamiento(habUniv);
		//
		//
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	public void deleteHabUniversitaria(HabitacionUniversitaria habUniv) throws SQLException, Exception {

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
	
	public HabitacionUniversitaria convertResultSetToHabitacionUniversitaria(ResultSet resultSet) throws SQLException {
	
		Long id = resultSet.getLong("ID");
		Boolean menaje = resultSet.getBoolean("MENAJE");
		Integer numero = resultSet.getInt("NUMERO");
		String ubicacion = resultSet.getString("UBICACION");
		Integer capacidad = resultSet.getInt("CAPACIDAD");
		Integer tamanho = resultSet.getInt("TAMANHO");
		Long viviendaUniv = resultSet.getLong("VIVIENDAUNIV");

		HabitacionUniversitaria apto = new HabitacionUniversitaria(id, ubicacion, numero, menaje, capacidad, tamanho, viviendaUniv);

		return apto;
	}
}
