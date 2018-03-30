package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Operador;

public class DAOOperador {

	
	public final static String USUARIO = "ISIS2304A481810";
	
	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;
	
	public DAOOperador() {
		recursos = new ArrayList<Object>();
	}
	
	public ArrayList<Operador> getOperadores() throws SQLException, Exception {
		ArrayList<Operador> operadores = new ArrayList<Operador>();

		String sql = String.format("SELECT * FROM %1$s.OPERADORES", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			operadores.add(convertResultSetToOperador(rs));
		}
		
		return operadores;
	}
	
	public double gananciasOperador(String id) throws SQLException, Exception {
		double respuesta = 0;

		String sql = String.format("Select Sum(cobro) as ganancias"
				+ "from (select cobro "
				+ "from %1$s.reservas "
				+ "where operador = '%2$s' and FECHAFIN > '1/1/2018')filtro", USUARIO,id);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			
			if(rs.getString("ganancias") != null){
				respuesta = rs.getDouble("ganancias");
			}
		}
		
		return respuesta;
	}
	
	public Operador findOperadorrById(Long id) throws SQLException, Exception 
	{
		Operador operador = null;

		String sql = String.format("SELECT * FROM %1$s.OPERADORES WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			operador = convertResultSetToOperador(rs);
		}

		return operador;
	}
	
	public void addOperador(Operador operador) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.OPERADORES (ID, CAPACIDAD, NOMBRE, TELEFONO, TIPO) VALUES (%2$s, '%3$s', '%4$s', '%5$s','%6$s' )", 
									USUARIO, 
									operador.getId(), 
									operador.getCapacidad(),
									operador.getNombre(), 
									operador.getTelefono(), operador.getTipo());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateOperador(Operador operador) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.OPERADORES SET ", USUARIO));
		sql.append(String.format("CAPACIDAD = '%1$s' AND NOMBRE = '%2$s' AND TELEFONO = '%3$s' ", operador.getCapacidad(), operador.getNombre(), operador.getTelefono(), operador.getTipo()));
		
		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	public void deleteOperador(Operador operador) throws SQLException, Exception {


//		FALTA SACAR TODA LA INFO CORRESPONDIENTE A ESTE OPERADOR EN LAS OTRAS TABLAS
//		String sqlPr = String.format("DELETE FROM %1$s.OFERTAS WHERE OPERADOR = %2$d", USUARIO, operador.getId());
//		
//		PreparedStatement prepStmtPr = conn.prepareStatement(sqlPr);
//		recursos.add(prepStmtPr);
//		prepStmtPr.executeQuery();
		
		
		String sql = String.format("DELETE FROM %1$s.OPERADORES WHERE ID = %2$d", USUARIO, operador.getId());

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
	
	public Operador convertResultSetToOperador(ResultSet resultSet) throws SQLException {
	
		Long id = resultSet.getLong("ID");
		Integer capacidad = resultSet.getInt("CAPACIDAD");
		String nombre = resultSet.getString("NOMBRE");
		Integer telefono = resultSet.getInt("TELEFONO");
		String tipo = resultSet.getString("TIPO");

		Operador op = new Operador(id, capacidad, nombre, telefono, tipo);

		return op;
	}

}
