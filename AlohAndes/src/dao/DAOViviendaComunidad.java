package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ViviendaComunidad;

public class DAOViviendaComunidad extends DAOAlojamiento{
	public final static String USUARIO = "ISIS2304A481810";
	
	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;
	
	public DAOViviendaComunidad() {
		recursos = new ArrayList<Object>();
	}
	
	public ArrayList<ViviendaComunidad> getViviendasComunidad() throws SQLException, Exception {
		ArrayList<ViviendaComunidad> vivsCom = new ArrayList<ViviendaComunidad>();

		String sql = String.format("SELECT * FROM %1$s.VDASCOMUNIDAD", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			vivsCom.add(convertResultSetToViviendaComunidad(rs));
		}
		
		return vivsCom;
	}
	
	public ViviendaComunidad findViviendaComunidadById(Long id) throws SQLException, Exception 
	{
		ViviendaComunidad vivsCom = null;

		String sql = String.format("SELECT * FROM %1$s.VDASCOMUNIDAD WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			vivsCom = convertResultSetToViviendaComunidad(rs);
		}

		return vivsCom;
	}
	
	public void addViviendaComunidad(ViviendaComunidad vivCom) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.VDASCOMUNIDAD (ID, DIRECCION, MENAJE, DIASUSO, NUMHABITACIONES, PERSONACOM) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s' )", 
									USUARIO,
									vivCom.getId(),
									vivCom.getMenaje(),
									vivCom.getDiasUso(),
									vivCom.getNumHabitaciones(),
									vivCom.getPersonaComunidad());
		System.out.println(sql);
		
		//
		//
		addAlojamiento(vivCom);
		//
		//
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateViviendaComunidad(ViviendaComunidad vivCom) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.VDASCOMUNIDAD SET ", USUARIO));
		sql.append(String.format( "ID = '%1$s' AND DIRECCION = '%2$s' AND MENAJE = '%3$s' AND DIASUSO = '%4$s' AND NUMHABITACIONES = '%5$s' AND PERSONACOM = '%6$s' ", 
				vivCom.getId(),
				vivCom.getDireccion(),
				vivCom.getMenaje(),
				vivCom.getDiasUso(),
				vivCom.getNumHabitaciones(),
				vivCom.getPersonaComunidad()));
		
		System.out.println(sql);
		
		//
		//
		updateAlojamiento(vivCom);
		//
		//
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	public void deleteViviendaComunidad(ViviendaComunidad vivCom) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.SERVICIOS WHERE ID = %2$d", USUARIO, vivCom.getId());

		System.out.println(sql);
		
		//
		//
		deleteAlojamiento(vivCom);
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
	
	public ViviendaComunidad convertResultSetToViviendaComunidad(ResultSet resultSet) throws SQLException {
	
		Long id = resultSet.getLong("ID");
		Boolean menaje = resultSet.getBoolean("MENAJE");
		Integer diasUso = resultSet.getInt("DIASUSO");
		Integer numHabitaciones = resultSet.getInt("NUMHABITACIONES");
		String direccion = resultSet.getString("DIRECCION");
		Integer capacidad = resultSet.getInt("CAPACIDAD");
		Integer tamanho = resultSet.getInt("TAMANHO");
		Long personaComunidad = resultSet.getLong("HOTEL");


		ViviendaComunidad vivCom = new ViviendaComunidad(id, menaje, diasUso, numHabitaciones, direccion, capacidad, tamanho, personaComunidad);

		return vivCom;
	}
}
