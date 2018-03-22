package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cliente;

public class DAOCliente extends DAOUsuario{

	public final static String USUARIO = "ISIS2304A481810";

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	public DAOCliente() {
		recursos = new ArrayList<Object>();
	}

	public ArrayList<Cliente> getClientes() throws SQLException, Exception {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		String sql = String.format("SELECT * FROM %1$s.CLIENTES", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			clientes.add(convertResultSetToCliente(rs));
		}

		return clientes;
	}

	public Cliente findClienteById(Long id) throws SQLException, Exception 
	{
		Cliente cliente = null;

		String sql = String.format("SELECT * FROM %1$s.CLIENTES WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			cliente = convertResultSetToCliente(rs);
		}

		return cliente;
	}

	public void addCliente(Cliente cliente) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.CLIENTES (ID, CEDULA, EDAD, NOMBRE, TELEFONO) VALUES (%2$s ,%3$s, '%4$s', '%5$s', '%6$s')", 
				USUARIO, 
				cliente.getId(), 
				cliente.getCedula(),
				cliente.getEdad(),
				cliente.getNombre(),
				cliente.getTelefono());
		System.out.println(sql);

		//
		addUsuario(cliente);
		//
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateUsuario(Cliente cliente) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.CLIENTES SET ", USUARIO));
		sql.append(String.format("ID = '%1$s' AND CEDULA = '%2$s' AND EDAD = '%3$s' AND NOMBRE = '%4$s' AND TELEFONO = '%5$s' ", 
				cliente.getId(),
				cliente.getCedula(), 
				cliente.getEdad(), 
				cliente.getNombre(),
				cliente.getTelefono()));

		System.out.println(sql);

		updateUsuario(cliente);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void deleteUsuario(Cliente cliente) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.USUARIOS WHERE ID = %2$d", USUARIO, cliente.getId());

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

	public Cliente convertResultSetToCliente(ResultSet resultSet) throws SQLException {

		Long id = resultSet.getLong("ID");
		Integer cedula = resultSet.getInt("CEDULA");
		Integer edad = resultSet.getInt("EDAD");
		String nombre = resultSet.getString("NOMBRE");
		Integer telefono = resultSet.getInt("TELEFONO");
		String login = resultSet.getString("LOGIN");
		String contrasenia = resultSet.getString("CONTRASENIA");

		Cliente usu = new Cliente(id, nombre, edad, telefono, cedula, login, contrasenia);

		return usu;
	}

}
