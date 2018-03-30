package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Hostal;

public class DAOHostal extends DAOOperador{

	public final static String USUARIO = "ISIS2304A481810";
	
	/**
	 * Arraylists de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;
	
	public DAOHostal() {
		recursos = new ArrayList<Object>();
	}
	
	public ArrayList<Hostal> getHostales() throws SQLException, Exception {
		ArrayList<Hostal> hostales = new ArrayList<Hostal>();

		String sql = String.format("SELECT * FROM ( %1$s.HOSTALES NATURAL INNER JOIN %1$s.OPERADORES)", USUARIO);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			hostales.add(convertResultSetToHostal(rs));
		}
		
		return hostales;
	}
	
	public Hostal findHostalById(Long id) throws SQLException, Exception {
		String sql = String.format("SELECT * FROM ( %1$s.HOSTALES NATURAL INNER JOIN %1$s.OPERADORES) WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			return convertResultSetToHostal(rs);
		}
		return null;
	}
	
	public void addHostal(Hostal hostal) throws SQLException, Exception {
		
		addOperador(hostal);
		String sql = String.format("INSERT INTO %1$s.HOSTALES (ID, DIRECCION, HABDISPONIBLES, HABOCUPADAS, RUT, HORAAPERTURA, HORACIERRE) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s' )", 
									USUARIO, 
									hostal.getDireccion(),
									hostal.getHabDisponibles(),
									hostal.getHabOcupadas(),
									hostal.getRut(),
									hostal.getHoraApertura(),
									hostal.getHoraCierre());	
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateHostal(Hostal hostal) throws SQLException, Exception {
		updateOperador(hostal);
		
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.HOSTALES SET ", USUARIO));
		sql.append(String.format( "HORAAPERTURA = '%1$s', DIRECCION = '%2$s', HABDISPONIBLES = '%3$s', HABOCUPADAS = '%4$s', RUT = '%5$s', HORACIERRE = '%6$s' ", 
				hostal.getHoraApertura(),
				hostal.getDireccion(),
				hostal.getHabDisponibles(),
				hostal.getHabOcupadas(),
				hostal.getRut(),
				hostal.getHoraCierre()));
			
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	public void deleteHostal(Hostal hostal) throws SQLException, Exception {

		deleteOperador(hostal);
				
		String sql = String.format("DELETE FROM %1$s.HOSTALES WHERE ID = %2$d", USUARIO, hostal.getId());
		
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
	private Hostal convertResultSetToHostal(ResultSet rs) throws SQLException {
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
		Integer horaApertura = rs.getInt("HORAAPERTURA");
		Integer horaCierre = rs.getInt("HORACIERRE");
		
		return new Hostal(direccion, habDisponibles, habOcupadas, rut, horaApertura, horaCierre, id, capacidad, nombre, telefono);
	}
}
