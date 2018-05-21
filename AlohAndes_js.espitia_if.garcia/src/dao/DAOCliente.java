package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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



	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public ArrayList<Cliente> getConsumoRFC10(String fechaInicio, String fechaFin, Long idAlojamiento, String ordenamiento, String tipoOrd) throws SQLException{
		ArrayList<Cliente> resp = new ArrayList<>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ");
		sql.append(String.format("(SELECT %s.RESERVAS.ID AS IDRESERVA, COBRO, ESTADO, FECHAINICIO, FECHAFIN, FECHAREALIZACION, IDCLIENTE, COLECTIVA, %s.CLIENTES.ID, CEDULA, NOMBRE, EDAD, TELEFONO", USUARIO));
		sql.append(String.format("FROM %s.RESERVAS INNER JOIN %s.CLIENTES ON %s.CLIENTES.ID = %s.RESERVAS.IDCLIENTE", USUARIO)); 
		sql.append(String.format("WHERE FECHAREALIZACION >='%1$s' AND FECHAREALIZACION <= '%2%s') T1", 
				fechaInicio, fechaFin));
		sql.append("INNER JOIN");
		sql.append(String.format("(SELECT * FROM %s.OFERTASRESERVAS INNER JOIN %s.OFERTAS ON %s.OFERTAS.ID = %s.OFERTASRESERVAS.IDOFERTA", USUARIO));
		sql.append(String.format("WHERE %1$s.OFERTAS.IDALOJAMIENTO = %2$s) T2", USUARIO, idAlojamiento));
		sql.append("ON T1.IDRESERVA = T2.IDRESERVA");

		if(ordenamiento != null ){
			sql.append(String.format("ORDER BY %1$s", ordenamiento));
			if(tipoOrd != null){
				sql.append(" " + tipoOrd);
			}
		}

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()){
			resp.add(convertResultSetToCliente(rs));
		}
		return resp;
	}

	public ArrayList<Cliente> getConsumoRFC11(String fechaInicio, String fechaFin, Long idAlojamiento, String ordenamiento, String tipoOrd) throws SQLException{
		ArrayList<Cliente> resp = new ArrayList<>();

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT * FROM %s.CLIENTES, %.RESERVAS WHERE %s.CLIENTES.id NOT IN ", USUARIO));
		sql.append("(SELECT IDCLIENTE FROM ");
		sql.append(String.format("(SELECT %s.RESERVAS.ID AS IDRESERVA, COBRO, ESTADO, FECHAINICIO, FECHAFIN, FECHAREALIZACION, IDCLIENTE, COLECTIVA, %s.CLIENTES.ID, CEDULA, NOMBRE, EDAD, TELEFONO", USUARIO));
		sql.append(String.format("FROM %s.RESERVAS INNER JOIN %s.CLIENTES ON %s.CLIENTES.ID = %s.RESERVAS.IDCLIENTE", USUARIO)); 
		sql.append(String.format("WHERE FECHAREALIZACION >='%1$s' AND FECHAREALIZACION <= '%2%s') T1", 
				fechaInicio, fechaFin));
		sql.append("INNER JOIN");
		sql.append(String.format("(SELECT * FROM %s.OFERTASRESERVAS INNER JOIN %s.OFERTAS ON %s.OFERTAS.ID = %s.OFERTASRESERVAS.IDOFERTA", USUARIO));
		sql.append(String.format("WHERE %1$s.OFERTAS.IDALOJAMIENTO = %2$s) T2", USUARIO, idAlojamiento));
		sql.append("ON T1.IDRESERVA = T2.IDRESERVA");
		sql.append(String.format(") AND %s.CLIENTES.ID = %s.RESERVAS.IDCLIENTE", USUARIO));

		if(ordenamiento != null ){
			sql.append(String.format("ORDER BY %1$s", ordenamiento));
			if(tipoOrd != null){
				sql.append(" " + tipoOrd);
			}
		}

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()){
			resp.add(convertResultSetToCliente(rs));
		}
		return resp;
	}

	public ArrayList<Cliente> getConsumoRFC10Prov(String fechaInicio, String fechaFin, Long idAlojamiento,
			String ordenamiento, String tipoOrd, Long idProveedor) throws SQLException {

		ArrayList<Cliente> resp = new ArrayList<>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ");
		sql.append(String.format("(SELECT %s.RESERVAS.ID AS IDRESERVA, COBRO, ESTADO, FECHAINICIO, FECHAFIN, FECHAREALIZACION, IDCLIENTE, COLECTIVA, %s.CLIENTES.ID, CEDULA, NOMBRE, EDAD, TELEFONO", USUARIO));
		sql.append(String.format("FROM %s.RESERVAS INNER JOIN %s.CLIENTES ON %s.CLIENTES.ID = %s.RESERVAS.IDCLIENTE", USUARIO)); 
		sql.append(String.format("WHERE FECHAREALIZACION >='%1$s' AND FECHAREALIZACION <= '%2%s') T1", 
				fechaInicio, fechaFin));
		sql.append("INNER JOIN");
		sql.append(String.format("(SELECT * FROM %s.OFERTASRESERVAS INNER JOIN %s.OFERTAS ON %s.OFERTAS.ID = %s.OFERTASRESERVAS.IDOFERTA", USUARIO));
		sql.append(String.format("WHERE %1$s.OFERTAS.IDALOJAMIENTO = %2$s AND OFERTAS.IDOPERADOR = %3$s) T2", USUARIO, idAlojamiento, idProveedor));
		sql.append("ON T1.IDRESERVA = T2.IDRESERVA");

		if(ordenamiento != null ){
			sql.append(String.format("ORDER BY %1$s", ordenamiento));
			if(tipoOrd != null){
				sql.append(" " + tipoOrd);
			}
		}
		

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()){
			resp.add(convertResultSetToCliente(rs));
		}
		return resp;
	}

	public ArrayList<Cliente> getConsumoRFC11Prov(String fechaInicio, String fechaFin, Long idAlojamiento,
			String ordenamiento, String tipoOrd, Long idProveedor) throws SQLException {
		ArrayList<Cliente> resp = new ArrayList<>();

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT * FROM %s.CLIENTES, %.RESERVAS WHERE %s.CLIENTES.id NOT IN ", USUARIO));
		sql.append("(SELECT IDCLIENTE FROM ");
		sql.append(String.format("(SELECT %s.RESERVAS.ID AS IDRESERVA, COBRO, ESTADO, FECHAINICIO, FECHAFIN, FECHAREALIZACION, IDCLIENTE, COLECTIVA, %s.CLIENTES.ID, CEDULA, NOMBRE, EDAD, TELEFONO", USUARIO));
		sql.append(String.format("FROM %s.RESERVAS INNER JOIN %s.CLIENTES ON %s.CLIENTES.ID = %s.RESERVAS.IDCLIENTE", USUARIO)); 
		sql.append(String.format("WHERE FECHAREALIZACION >='%1$s/%2$s/%3$s' AND FECHAREALIZACION <= '%4%s/%5$s/%6$s') T1", 
				fechaInicio, fechaFin));
		sql.append("INNER JOIN");
		sql.append(String.format("(SELECT * FROM %s.OFERTASRESERVAS INNER JOIN %s.OFERTAS ON %s.OFERTAS.ID = %s.OFERTASRESERVAS.IDOFERTA", USUARIO));
		sql.append(String.format("WHERE %1$s.OFERTAS.IDALOJAMIENTO = %2$s AND OFERTAS.IDOPERADOR = %3$s) T2", USUARIO, idAlojamiento, idProveedor));
		sql.append("ON T1.IDRESERVA = T2.IDRESERVA");
		sql.append(String.format(") AND %s.CLIENTES.ID = %s.RESERVAS.IDCLIENTE", USUARIO));

		if(ordenamiento != null ){
			sql.append(String.format("ORDER BY %1$s", ordenamiento));
			if(tipoOrd != null){
				sql.append(" " + tipoOrd);
			}
		}

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()){
			resp.add(convertResultSetToCliente(rs));
		}
		return resp;
	}


	public ArrayList<Cliente> getReservanCadaMes() throws SQLException{
		ArrayList<Cliente> resp = new ArrayList<>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * FROM"); 
		//en la siguiente se puede intentar poner el distinct
		sql.append("\r\n (SELECT IDCLIENTE,(TO_CHAR(FECHAREALIZACION-365, 'MM') as MES)\r\n"); 
		sql.append(String.format("FROM %s.RESERVAS \r\n", USUARIO)); 
		sql.append("WHERE FECHAREALIZACION >= (TO_CHAR(SYSDATE-365, 'DD-MM-YYYY')) \r\n"); 
		sql.append("group by IDCLIENTE, FECHAREALIZACION, (TO_CHAR(FECHAREALIZACION-365, 'MM'))\r\n"); 
		sql.append("ORDER BY IDCLIENTE, FECHAREALIZACION);\r\n");
		sql.append("INNER JOIN");
		sql.append(String.format("%s.CLIENTES ON %s.CLIENTES.ID = IDCLIENTE", USUARIO));

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		Long idClienteActual = new Long(-1);
		boolean[] mesesAc = new boolean[12];
		while(rs.next()){
			if(idClienteActual != rs.getLong("IDCLIENTE")) {
				idClienteActual = rs.getLong("IDCLIENTE");
				int i = 0;
				while(i < mesesAc.length && mesesAc[i]) {
					i++;
				}
				if(i == mesesAc.length) {
					resp.add(convertResultSetToCliente(rs));
				}
				mesesAc = new boolean[12];
			}
			int mesActual = rs.getInt("MES")-1;
			mesesAc[mesActual] = true;
		}
		return resp;
	}

	public ArrayList<Cliente> getReservanCaro() throws SQLException{
		ArrayList<Cliente> resp = new ArrayList<>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * FROM");
		sql.append(String.format("(SELECT COUNT(*) AS RESERVASTOTALES, %s.CLIENTES.ID AS CLIENTE, %s.CLIENTES.CEDULA, %s.CLIENTES.EDAD, %s.CLIENTES.NOMBRE, %s.CLIENTES.TELEFONO",USUARIO));
		sql.append(String.format("FROM %s.CLIENTES INNER JOIN %s.RESERVAS ON %s.CLIENTES.ID = %s.RESERVAS.IDCLIENTE", USUARIO)); 
		sql.append(String.format("GROUP BY %s.CLIENTES.ID, %s.CLIENTES.CEDULA, %s.CLIENTES.EDAD, %s.CLIENTES.NOMBRE, %s.CLIENTES.TELEFONO)", USUARIO));
		sql.append("NATURAL INNER JOIN"); 
		sql.append(String.format("( SELECT count(*) AS RESERVASCARAS, %s.clientes.id as cliente, %s.CLIENTES.CEDULA, %s.CLIENTES.EDAD, %s.CLIENTES.NOMBRE, %s.CLIENTES.TELEFONO", USUARIO));
		sql.append(String.format("FROM %s.CLIENTES INNER JOIN %s.RESERVAS ON %s.CLIENTES.ID = %s.RESERVAS.IDCLIENTE", USUARIO));
		sql.append(String.format("WHERE %s.RESERVAS.COBRO/(FECHAFIN-FECHAINICIO) >= 150", USUARIO));
		sql.append(String.format("GROUP BY %s.clientes.id, %s.CLIENTES.CEDULA, %s.CLIENTES.EDAD, %s.CLIENTES.NOMBRE, %s.CLIENTES.TELEFONO)",USUARIO));
		sql.append("WHERE RESERVASTOTALES <= RESERVASCARAS)"); 

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()){
			resp.add(convertResultSetToCliente(rs));
		}
		return resp;
	}
	
	public ArrayList<Cliente> getReservanSuites() throws SQLException{
		ArrayList<Cliente> resp = new ArrayList<>();
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT * \r\n");
		sql.append(String.format("FROM(SELECT COUNT(*) AS RESERVASTOTALES, %s.CLIENTES.ID AS CLIENTE, %s.CLIENTES.CEDULA, %s.CLIENTES.EDAD, %s.CLIENTES.NOMBRE, %s.CLIENTES.TELEFONO\r\n", USUARIO )); 
		sql.append(String.format("  FROM %s.CLIENTES INNER JOIN %s.RESERVAS ON %s.CLIENTES.ID = %s.RESERVAS.IDCLIENTE \r\n", USUARIO)); 
		sql.append(String.format(" GROUP BY %s.CLIENTES.ID, %s.CLIENTES.CEDULA, %s.CLIENTES.EDAD, %s.CLIENTES.NOMBRE, %s.CLIENTES.TELEFONO) T1,\r\n", USUARIO )); 
		sql.append(" (SELECT COUNT(IDRESERVA) AS RESERVASSUITE, IDCLIENTE, CEDULA, NOMBRE, EDAD, TELEFONO \r\n"); 
 		sql.append(String.format(" FROM    (SELECT * FROM %s.CLIENTES INNER JOIN (SELECT ID AS RESERVA, IDCLIENTE FROM %s.RESERVAS) ON %s.CLIENTES.ID = IDCLIENTE)\r\n", USUARIO)); 
		sql.append(" INNER JOIN\r\n" ); 
		sql.append(String.format(" (SELECT * FROM %s.OFERTASRESERVAS \r\n", USUARIO));  
		sql.append(" INNER JOIN\r\n"); 
		sql.append(String.format(" (SELECT CATEGORIA, NUMERO, UBICACION, IDHOTEL, IDALOJAMIENTO, %s.OFERTAS.ID AS IDOFERTA1\r\n", USUARIO )); 
		sql.append(String.format(" FROM %s.HABSHOTEL INNER JOIN %s.OFERTAS ON %s.HABSHOTEL.ID = %s.OFERTAS.IDALOJAMIENTO\r\n", USUARIO)); 
		sql.append(" WHERE CATEGORIA = 'SUITE')\r\n"); 
		sql.append(" ON IDOFERTA = IDOFERTA1) \r\n"); 
		sql.append(" ON IDRESERVA = RESERVA \r\n" ); 
		sql.append(" group by IDCLIENTE, CEDULA, NOMBRE, EDAD, TELEFONO) T2\r\n"); 
		sql.append("WHERE T1.CLIENTE = T2.IDCLIENTE AND RESERVASTOTALES = RESERVASSUITE");

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()){
			resp.add(convertResultSetToCliente(rs));
		}
		return resp;
	}	
}
