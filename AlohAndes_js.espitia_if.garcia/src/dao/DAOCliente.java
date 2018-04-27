package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import vos.Alojamiento;
import vos.Cliente;
import vos.Operador;

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

	public Cliente findClienteById(Long id) throws SQLException, Exception{
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
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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
		conn.commit();

	}

	public void updateCliente(Cliente cliente) throws SQLException, Exception {

		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.CLIENTES SET ", USUARIO));
		sql.append(String.format(" CEDULA = '%1$s' AND EDAD = '%2$s' AND NOMBRE = '%3$s' AND TELEFONO = '%4$s' ", 
				cliente.getCedula(), 
				cliente.getEdad(), 
				cliente.getNombre(),
				cliente.getTelefono()));

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		conn.commit();
	}


	public void deleteCliente(Cliente cliente) throws SQLException, Exception {

		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		
		String sqlPr = String.format("DELETE FROM %1$s.reservas WHERE IDCLIENTE = %2$d", USUARIO, cliente.getId());

		PreparedStatement prepStmtPr = conn.prepareStatement(sqlPr);
		recursos.add(prepStmtPr);
		prepStmtPr.executeQuery();

		String sql = String.format("DELETE FROM %1$s.USUARIOS WHERE ID = %2$d", USUARIO, cliente.getId());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		conn.commit();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// TODO RFC6 
	//----------------------------------------------------------------------------------------------------------------------------------

	public String getUso(Long idCliente) throws SQLException, Exception{

		String sql = String.format("select * from\r\n" + 
				"( select *       \r\n" + 
				"from (select id as idreserva, cobro, estado, fechafin- fechainicio, idcliente\r\n" + 
				"from %1$s.reservas) natural inner join %1$s.ofertasreservas) \r\n" + 
				"natural inner join (select * from (select id as idoferta, idalojamiento, idoperador, tipo from %1$s.ofertas natural inner join (select id as idalojamiento, tipo from %1$s.alojamientos))natural inner join (select id as idoperador, tipo as tipoOp from %1$s.operadores))\r\n" + 
				"where idcliente = %2$s;"
				, USUARIO, idCliente);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		int numNoches = 0;
		int dineroTotal = 0;
		int hotelesContratados = 0, hostalesContratados = 0, vivCContratadas = 0, vivUContratadas = 0, aptosContratados = 0, habsNormContratadas = 0;
		while (rs.next()) {
			long diff = rs.getInt("diff");
			numNoches += diff;
			dineroTotal += rs.getInt("COBRO");
			String tipo = rs.getString("TIPO");
			String tipoOp = rs.getString("TIPOOP");
			if(tipo.equals(Alojamiento.APARTAMENTO)) {
				aptosContratados++;
			}
			else if (tipo.equals(Alojamiento.HABITACION)){
				if(tipoOp.equals(Operador.HOSTAL) ){
					hostalesContratados++;
				}else if(tipoOp.equals(Operador.PERSONA_NATURAL)) {
					habsNormContratadas++;
				}
			}
			else if (tipo.equals(Alojamiento.HABITACION_HOTEL)){
				hotelesContratados++;
			}
			else if (tipo.equals(Alojamiento.HABITACION_UNIVERSITARIA)){
				vivUContratadas++;
			}
			else if (tipo.equals(Alojamiento.VIVIENDA_COMUNITARIA)){
				vivCContratadas++;
			}
		}
		return "Cliente con id " + rs.getLong("ID")
		+ "\nHa contratado " + aptosContratados + " apartamentos"
		+ "\nHa contratado " + hostalesContratados + " habitaciones de hostal"
		+ "\nHa contratado " + hotelesContratados + " habitaciones de hotel"
		+ "\nHa contratado " + habsNormContratadas + " habitaciones de personas naturales"
		+ "\nHa contratado " + vivUContratadas + " habitaciones de vivienda universitaria"
		+ "\nHa contratado " + vivCContratadas + " viviendas de la comunidad"
		+ "\nHa reservado un total de " + numNoches + " noches gracias a AlohAndes"
		+ "\nY ha pagado un total de $" + dineroTotal;
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
