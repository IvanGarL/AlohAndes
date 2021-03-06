package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Responsable;

public class DAOResponsable extends DAOUsuario{

	public final static String USUARIO = "ISIS2304A481810";

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;
	
	public DAOResponsable() {
		recursos = new ArrayList<Object>();
	}
	
	public ArrayList<Responsable> getResponsables() throws SQLException, Exception {
		ArrayList<Responsable> responsables = new ArrayList<Responsable>();

		String sql = String.format("SELECT * FROM %1$s.RESPONSABLES", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			responsables.add(convertResultSetToResponsable(rs));
		}
		
		return responsables;
	}
	
	public Responsable findResponsableById(Long id) throws SQLException, Exception 
	{
		Responsable responsable = null;
		
		String sql = String.format("SELECT * FROM %1$s.RESPONSABLES WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			responsable = convertResultSetToResponsable(rs);
		}

		return responsable;
	}
	
	public void addResponsable(Responsable responsable) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.RESPONSABLES (ID, CEDULA, EDAD, NOMBRE, TELEFONO, OPERADORA) VALUES (%2$s ,%3$s, '%4$s', '%5$s', '%6$s', '%7$s')", 
									USUARIO, 
									responsable.getId(), 
									responsable.getCedula(),
									responsable.getEdad(),
									responsable.getNombre(),
									responsable.getTelefono(),
									responsable.getIdOperador());
		System.out.println(sql);
		
		//
		addUsuario(responsable);
		//
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateResponsable(Responsable responsable) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.RESPONSABLES SET ", USUARIO));
		sql.append(String.format("ID = '%1$s' AND CEDULA = '%2$s' AND EDAD = '%3$s' AND NOMBRE = '%4$s' AND TELEFONO = '%5$s' AND OPERADOR = '%6$s'  ", 
				responsable.getId(),
				responsable.getCedula(), 
				responsable.getEdad(), 
				responsable.getNombre(),
				responsable.getTelefono(), 
				responsable.getIdOperador()));
		
		System.out.println(sql);
		
		updateUsuario(responsable);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	public void deleteResponsable(Responsable responsable) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.RESPONSABLES WHERE ID = %2$d", USUARIO, responsable.getId());

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
	
	public Responsable convertResultSetToResponsable(ResultSet resultSet) throws SQLException {
	
		Long id = resultSet.getLong("ID");
		Long cedula = resultSet.getLong("CEDULA");
		Integer edad = resultSet.getInt("EDAD");
		String nombre = resultSet.getString("NOMBRE");
		Integer telefono = resultSet.getInt("TELEFONO");
		String login = resultSet.getString("LOGIN");
		String contrasenia = resultSet.getString("CONTRASENIA");
		Integer idOperador = resultSet.getInt("OPERADOR");

		Responsable usu = new Responsable(id, nombre, edad, telefono, cedula, login, contrasenia, idOperador);

		return usu;
	}
}
