package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Servicio;

public class DAOServicio {

	public final static String USUARIO = "ISIS2304A481810";
	
	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;
	
	public DAOServicio() {
		recursos = new ArrayList<Object>();
	}
	
	public ArrayList<Servicio> getServicios() throws SQLException, Exception {
		ArrayList<Servicio> Servicios = new ArrayList<Servicio>();

		String sql = String.format("SELECT * FROM %1$s.SERVICIOS", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Servicios.add(convertResultSetToServicio(rs));
		}
		
		return Servicios;
	}
	
	public Servicio findServicioById(Long id) throws SQLException, Exception 
	{
		Servicio servicio = null;

		String sql = String.format("SELECT * FROM %1$s.SERVICIOS WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			servicio = convertResultSetToServicio(rs);
		}

		return servicio;
	}
	
	public void addServicio(Servicio servicio) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.SERVICIOS (ID, COSTO, NOMBRE, DESCRIPCION) VALUES (%2$s, '%3$s', '%4$s', '%5$s')", 
									USUARIO,
									servicio.getId(),
									servicio.getCosto(), 
									servicio.getNombre(),
									servicio.getDescripcion());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateServicio(Servicio servicio) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.SERVICIOS SET ", USUARIO));
		sql.append(String.format( "ID = '%1$s' AND COSTO = '%2$s' AND NOMBRE = '%3$s' AND DESCRIPCION = '%4$s' ", 
				servicio.getId(),
				servicio.getCosto(), 
				servicio.getNombre(),
				servicio.getDescripcion()));
		
		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	public void deleteServicio(Servicio servicio) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.SERVICIOS WHERE ID = %2$d", USUARIO, servicio.getId());

		System.out.println(sql);
		
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
	
	//TODO: Arreglar el result set y su transformación para crear un objeto de tipo servicio
	
	public Servicio convertResultSetToServicio(ResultSet resultSet) throws SQLException {
	
		Long id = resultSet.getLong("ID");
		Double costo = resultSet.getDouble("COSTO");
		String nombre = resultSet.getString("NOMBRE");
		String tipo = resultSet.getString("DESCRIPCION");
		Long idOferta = null;

		Servicio serv = new Servicio(id, costo, nombre, tipo, idOferta);

		return serv;
	}
}
