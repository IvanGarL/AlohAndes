package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Hotel;

public class DAOHotel extends DAOOperador{

	public final static String USUARIO = "ISIS2304A481810";
	
	/**
	 * Arraylists de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;
	
	public DAOHotel() {
		recursos = new ArrayList<Object>();
	}
	
	public ArrayList<Hotel> getHoteles() throws SQLException, Exception {
		ArrayList<Hotel> hoteles = new ArrayList<Hotel>();

		String sql = String.format("SELECT * FROM ( %1$s.HOTELES NATURAL INNER JOIN %1$s.OPERADORES)", USUARIO);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			hoteles.add(convertResultSetToHotel(rs));
		}
		
		return hoteles;
	}
	
	public Hotel findHotelById(Long id) throws SQLException, Exception {
		String sql = String.format("SELECT * FROM ( %1$s.HOTELES NATURAL INNER JOIN %1$s.OPERADORES) WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			return convertResultSetToHotel(rs);
		}
		return null;
	}
	
	public void addHotel(Hotel hotel) throws SQLException, Exception {
		
		//Revisar si la inserción incluye el id o es autogenerado
		addOperador(hotel);
		String sql = String.format("INSERT INTO %1$s.HOTELES (ID, DIRECCION, HABDISPONIBLES, HABOCUPADAS, RUT, ESTRELLAS) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s', '%7$s' )", 
									USUARIO, 
									hotel.getId(),
									hotel.getDireccion(),
									hotel.getHabDisponibles(),
									hotel.getHabOcupadas(),
									hotel.getRut(),
									hotel.getEstrellas());	
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateHotel(Hotel hotel) throws SQLException, Exception {
		updateOperador(hotel);
		
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.HOTELES SET ", USUARIO));
		sql.append(String.format( "ESTRELLAS = '%1$s', DIRECCION = '%2$s', HABDISPONIBLES = '%3$s', HABOCUPADAS = '%4$s', RUT = '%5$s' ", 
				hotel.getEstrellas(),
				hotel.getDireccion(),
				hotel.getHabDisponibles(),
				hotel.getHabOcupadas(),
				hotel.getRut()));
		sql.append ("WHERE ID = " + hotel.getId ());
			
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	public void deleteHotel(Hotel hotel) throws SQLException, Exception {

		deleteOperador(hotel);
				
		String sql = String.format("DELETE FROM %1$s.HOTELES WHERE ID = %2$d", USUARIO, hotel.getId());
		
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
	private Hotel convertResultSetToHotel(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		
		//Atributos heredados
		Long id = rs.getLong("ID");
		Integer capacidad = rs.getInt("CAPACIDAD");
		String nombre = rs.getString("NOMBRE");
		Integer telefono = rs.getInt("TELEFONO");
		//Atributos propios
		String direccion = rs.getString("DIRECCION");
		Integer habDisponibles = rs.getInt("HABDISPONIBLES");
		Integer habOcupadas = rs.getInt("HABOCUPADAS");
		Integer rut = rs.getInt("RUT");
		Integer estrellas = rs.getInt("ESTRELLAS");
		
		return new Hotel(direccion, habDisponibles, habOcupadas, rut, estrellas, id, capacidad, nombre, telefono);
	}
}
