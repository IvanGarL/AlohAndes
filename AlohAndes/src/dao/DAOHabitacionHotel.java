package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.HabitacionHotel;

public class DAOHabitacionHotel extends DAOAlojamiento{

	public final static String USUARIO = "ISIS2304A481810";
	
	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;
	
	public DAOHabitacionHotel() {
		recursos = new ArrayList<Object>();
	}
	
	public ArrayList<HabitacionHotel> getHabitacionesHotel() throws SQLException, Exception {
		ArrayList<HabitacionHotel> habsHotel = new ArrayList<HabitacionHotel>();

		String sql = String.format("SELECT * FROM %1$s.HABSHOTEL", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habsHotel.add(convertResultSetToHabitacionHotel(rs));
		}
		
		return habsHotel;
	}
	
	public HabitacionHotel findHabitacionHotelById(Long id) throws SQLException, Exception 
	{
		HabitacionHotel habHotel = null;

		String sql = String.format("SELECT * FROM %1$s.HABSHOTEL WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			habHotel = convertResultSetToHabitacionHotel(rs);
		}

		return habHotel;
	}
	
	public void addHabitacionHotel(HabitacionHotel habHotel) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.HABSHOTEL (ID, CATEGORIA, UBICACION, NUMERO, HOTEL) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s' )", 
									USUARIO,
									habHotel.getId(),
									habHotel.getCategoria(),
									habHotel.getUbicacion(),
									habHotel.getNumero(),
									habHotel.getHotel());
		System.out.println(sql);
		
		//
		//
		addAlojamiento(habHotel);
		//
		//
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateHabitacionHotel(HabitacionHotel habHotel) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.HABSHOTEL SET ", USUARIO));
		sql.append(String.format( "ID = '%1$s' AND CATEGORIA = '%2$s' AND UBICACION = '%3$s' AND NUMERO = '%4$s' AND HOTEL = '%5$s' ", 
				habHotel.getId(),
				habHotel.getCategoria(),
				habHotel.getUbicacion(),
				habHotel.getNumero(),
				habHotel.getHotel()));
		
		System.out.println(sql);
		
		//
		//
		updateAlojamiento(habHotel);
		//
		//
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	public void deleteHabitacionHotel(HabitacionHotel habHotel) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.HABSHOTEL WHERE ID = %2$d", USUARIO, habHotel.getId());

		System.out.println(sql);
		
		//
		//
		deleteAlojamiento(habHotel);
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
	
	public HabitacionHotel convertResultSetToHabitacionHotel(ResultSet resultSet) throws SQLException {
	
		Long id = resultSet.getLong("ID");
		Integer numero = resultSet.getInt("NUMERO");
		String categoria = resultSet.getString("CATEGORIA");
		String ubicacion = resultSet.getString("UBICACION");
		Integer tamanho = resultSet.getInt("TAMANHO");
		Integer capacidad = resultSet.getInt("CAPACIDAD");
		Long hotel = resultSet.getLong("HOTEL");


		HabitacionHotel habHot = new HabitacionHotel(id, numero, categoria, ubicacion, tamanho, capacidad, hotel);

		return habHot;
	}
}
