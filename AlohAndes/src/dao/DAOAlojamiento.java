package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Alojamiento;

public class DAOAlojamiento {

	
	public final static String USUARIO = "ISIS2304A481810";
	
	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;
	
	public DAOAlojamiento() {
		recursos = new ArrayList<Object>();
	}
	
	public ArrayList<Alojamiento> getAlojamientos() throws SQLException, Exception {
		ArrayList<Alojamiento> Alojamientos = new ArrayList<Alojamiento>();

		String sql = String.format("SELECT * FROM %1$s.ALOJAMIENTOS", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Alojamientos.add(convertResultSetToAlojamiento(rs));
		}
		
		return Alojamientos;
	}
	
	public Alojamiento findAlojamientoById(Long id) throws SQLException, Exception 
	{
		Alojamiento alojamiento = null;

		String sql = String.format("SELECT * FROM %1$s.ALOJAMIENTOS WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			alojamiento = convertResultSetToAlojamiento(rs);
		}

		return alojamiento;
	}
	
	public void addAlojamiento(Alojamiento alojamiento) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.ALOJAMIENTOS (ID, TAMANHO, CAPACIDAD, TIPO) VALUES (%2$s, '%3$s', '%4$s', '%5$s')", 
									USUARIO,
									alojamiento.getId(),
									alojamiento.getTamanho(), 
									alojamiento.getCapacidad(),
									alojamiento.getTipo());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateAlojamiento(Alojamiento alojamiento) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.ALOJAMIENTOS SET ", USUARIO));
		sql.append(String.format( "ID = '%1$s' AND TAMANHO = '%2$s' AND CAPACIDAD = '%3$s' AND TIPO = '%4$s' ", 
				alojamiento.getId(),
				alojamiento.getTamanho(), 
				alojamiento.getCapacidad(),
				alojamiento.getTipo()));
		
		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	public void deleteAlojamiento(Alojamiento alojamiento) throws SQLException, Exception {


//		FALTA SACAR TODA LA INFO CORRESPONDIENTE A ESTE ALOJAMIENTO EN LAS OTRAS TABLAS
//		String sqlPr = String.format("DELETE FROM %1$s.OFERTAS WHERE ALOJAMIENTO = %2$d", USUARIO, alojamiento.getId());
//		
//		PreparedStatement prepStmtPr = conn.prepareStatement(sqlPr);
//		recursos.add(prepStmtPr);
//		prepStmtPr.executeQuery();
		
		String sql = String.format("DELETE FROM %1$s.ALOJAMIENTOS WHERE ID = %2$d", USUARIO, alojamiento.getId());

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
	
	public Alojamiento convertResultSetToAlojamiento(ResultSet resultSet) throws SQLException {
	
		Long id = resultSet.getLong("ID");
		Integer tamanho = resultSet.getInt("TAMANHO");
		Integer capacidad = resultSet.getInt("CAPACIDAD");
		String tipo = resultSet.getString("TIPO");

		Alojamiento alo = new Alojamiento(id, tamanho, capacidad, tipo);

		return alo;
	}

}
