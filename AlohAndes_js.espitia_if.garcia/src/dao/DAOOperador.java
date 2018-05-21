package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vos.Hostal;
import vos.Hotel;
import vos.Operador;
import vos.PersonaComunidad;
import vos.PersonaNatural;
import vos.ViviendaUniversitaria;

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


	//----------------------------------------------------------------------------------------------------------------------------------
	// RFC1
	//----------------------------------------------------------------------------------------------------------------------------------

	public double gananciasOperadores(Long id) throws SQLException, Exception {
		double respuesta = 0;

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		Date initDate = new Date(date.getYear()-1, date.getMonth(), date.getDate());

		String sql = String.format("Select Sum(cobro) as ganancias"
				+ "from (select cobro "
				+ "from %1$s.reservas "
				+ "where operador = '%2$s' and FECHAINICIO <= %3$s AND FECHAINICIO >= %4$s);", 
				USUARIO, id, dateFormat.format(date), dateFormat.format(initDate));

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

	public Operador addOperador(Operador operador) throws SQLException, Exception {
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		String sql, idAsignado;
		Long nuevoId;
		if(operador.getIdResponsable() != null){
			sql = String.format("INSERT INTO %1$s.OPERADORES (CAPACIDAD, NOMBRE, TELEFONO, TIPO, IDRESPONSABLE) "
					+ "VALUES (%2$s, '%3$s', '%4$s', '%5$s','%6$s')", 
					USUARIO, 
					operador.getCapacidad(),
					operador.getNombre(), 
					operador.getTelefono(), 
					operador.getTipo(),
					operador.getIdResponsable());
		}
		else{
			sql = String.format("INSERT INTO %1$s.OPERADORES (CAPACIDAD, NOMBRE, TELEFONO, TIPO) VALUES (%2$s, '%3$s', '%4$s', '%5$s)", 
					USUARIO, 
					operador.getCapacidad(),
					operador.getNombre(), 
					operador.getTelefono(), 
					operador.getTipo());
		}
		idAsignado = String.format("SELECT MAX(ID) FROM %1$s.OPERADORES", USUARIO);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		PreparedStatement pSId = conn.prepareStatement(idAsignado);
		recursos.add(pSId);
		ResultSet rs = pSId.executeQuery();

		addOperadorEspecifico(operador);

		nuevoId = rs.getLong(0);
		operador.setId(nuevoId);
		conn.commit();
		return operador;
	}


	public void updateOperador(Operador operador) throws SQLException, Exception {

		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.OPERADORES SET ", USUARIO));
		sql.append(String.format("CAPACIDAD = '%1$s', NOMBRE = '%2$s', TELEFONO = '%3$s' ", operador.getCapacidad(), operador.getNombre(), operador.getTelefono(), operador.getTipo()));
		sql.append(String.format("WHERE ID = %1$s", operador.getId()));
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		conn.commit();
	}


	public void deleteOperador(Operador operador) throws SQLException, Exception {

		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		//Borrar sus alojamientos. Primero de las tablas especificas y luego de la general

		borrarAlojamientosEspecificos(operador);

		String sqlAlojamientos = String.format("DELETE FROM %1$s.ALOJAMIENTOS"
				+ " WHERE IDOFERTA IN (SELECT ID FROM %1$s.OFERTAS WHERE IDOPERADOR = %2$s)", 
				USUARIO, operador.getId());

		PreparedStatement prepStmtAloj = conn.prepareStatement(sqlAlojamientos);
		recursos.add(prepStmtAloj);
		prepStmtAloj.executeQuery();

		//Borrar relacion reservas y ofertas suyas
		String sqlOfRes = String.format("DELETE FROM %1$s.OFERTASRESERVAS"
				+ " WHERE IDOFERTA IN (SELECT ID FROM %1$s.OFERTAS WHERE IDOPERADOR = %2$s)"
				+ " OR IDRESERVA IN (SELECT ID FROM %1$s.RESERVAS WHERE IDOPERADOR = %2$s)",
				USUARIO, operador.getId());

		PreparedStatement prepStmtOfRs = conn.prepareStatement(sqlOfRes);
		recursos.add(prepStmtOfRs);
		prepStmtOfRs.executeQuery();

		//Borrar sus ofertas, con los servicios que esta posee
		String sqlServicios = String.format("DELETE FROM %1$s.SERVICIOS "
				+ "WHERE IDOFERTA IN (SELECT ID FROM %1$s.OFERTAS WHERE IDOPERADOR = %2$s)",
				USUARIO, operador.getId());

		PreparedStatement prepStmtServicios = conn.prepareStatement(sqlServicios);
		recursos.add(prepStmtServicios);
		prepStmtServicios.executeQuery();

		String sqlPr = String.format("DELETE FROM %1$s.OFERTAS WHERE IDOPERADOR = %2$d",
				USUARIO, operador.getId());

		PreparedStatement prepStmtPr = conn.prepareStatement(sqlPr);
		recursos.add(prepStmtPr);
		prepStmtPr.executeQuery();

		//Borrar sus reservas
		String sqlPrRes = String.format("DELETE FROM %1$s.RESERVAS WHERE IDOPERADOR = %2$d", 
				USUARIO, operador.getId());

		PreparedStatement prepStmtPrRes = conn.prepareStatement(sqlPrRes);
		recursos.add(prepStmtPrRes);
		prepStmtPrRes.executeQuery();

		//Finalmente se borra el operador de la tabla especifica y la general
		borrarOperadorEspecifico(operador);

		String sql = String.format("DELETE FROM %1$s.OPERADORES WHERE ID = %2$d",
				USUARIO, operador.getId());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		conn.commit();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// TODO RFC5
	//----------------------------------------------------------------------------------------------------------------------------------

	public String getUso(Long id) throws SQLException, Exception {

		String resp = "";
		PreparedStatement prepStmt = conn.prepareStatement(String.format("Select tipo from %1$s.operadores where id = %2$s.", USUARIO, id));
		recursos.add(prepStmt);
		ResultSet tipoSet = prepStmt.executeQuery();
		if(tipoSet.next()) {
			String tipo = tipoSet.getString("tipo");			

			resp = "El operador con id " + id + ", que es un "+ tipo +", tiene:\n";

			String sqlHot = String.format("select count(*)as numreservas, sum(cobro) as totalRecibido \r\n" + 
					"from %1$s.operadores inner join %1$s.reservas on %1$s.operadores.id = idoperador \r\n" + 
					"where idoperador = %2$s", USUARIO, id);
			PreparedStatement prepStmth = conn.prepareStatement(sqlHot);
			recursos.add(prepStmth);
			ResultSet hotSet = prepStmth.executeQuery();
			resp += (hotSet.getInt("numreservas") + " reservas, que se reparten entre sus ");

			String sqlhabs = String.format("select count(*) as numofertas\r\n" + 
					"from %1$s.ofertas\r\n" + 
					"where idoperador = %2$s", USUARIO, id);
			PreparedStatement pf = conn.prepareStatement(sqlhabs);
			recursos.add(pf);
			ResultSet alojsSet = pf.executeQuery();

			resp+=(alojsSet.getInt("numofertas") + " ofertas de habitaciones\n y que le han generado $"+hotSet.getInt("totalRecibido"));

		}else {
			throw new Exception("No existe el operador con ese id");
		}

		return resp;
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS AUXILIARES
	//----------------------------------------------------------------------------------------------------------------------------------

	private void borrarAlojamientosEspecificos(Operador operador) throws SQLException {

		String seleccionIdAlojamiento = String.format("SELECT ID FROM %1$s.ALOJAMIENTOS "
				+ "WHERE IDOFERTA IN (SELECT ID FROM %1$s.OFERTAS WHERE IDOPERADOR = %2$s)", 
				USUARIO, operador.getId());
		String tabla = "no";
		String tabla2 = "no";

		switch (operador.getTipo()) {
		case Operador.HOSTAL:
			tabla = "HABITACIONES";
			break;
		case Operador.HOTEL:
			tabla = "HABSHOTEL";
			break;
		case Operador.PERSONA_COMUNIDAD:
			tabla = "APARTAMENTOS";
			tabla2 = "VIVIENDASCOMUNIDAD";
			break;
		case Operador.PERSONA_NATURAL:
			tabla = "HABITACIONES";
			break;
		case Operador.VIVIENDA_UNIVERSITARIA:
			tabla = "HABSUNIVERSITARIAS";
			break;
		default:
			return;
		}

		if(tabla!="no"){
			String sql = String.format("DELETE FROM %1$s.%2$s WHERE ID IN %3$s", 
					USUARIO, tabla, seleccionIdAlojamiento);

			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
		if(tabla2!="no"){
			String sql = String.format("DELETE FROM %1$s.%2$s WHERE ID IN %3$s",
					USUARIO, tabla, seleccionIdAlojamiento);

			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}

	private void borrarOperadorEspecifico(Operador operador) throws SQLException {

		String tabla = "no";
		switch (operador.getTipo()) {
		case Operador.HOSTAL:
			tabla = "HOSTALES";
			break;
		case Operador.HOTEL:
			tabla = "HOTELES";
			break;
		case Operador.PERSONA_COMUNIDAD:
			tabla = "PERSONASCOMUNIDAD";
			break;
		case Operador.PERSONA_NATURAL:
			tabla = "PERSONASNATURALES";
			break;
		case Operador.VIVIENDA_UNIVERSITARIA:
			tabla = "VIVIENDASUNIVERSITARIAS";
			break;
		default:
			return;
		}
		String sql = String.format("DELETE FROM %1$s.%2$s WHERE ID %3$s", 
				USUARIO, tabla, operador.getId());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	private void addOperadorEspecifico(Operador operador) throws SQLException {
		String sql = "";
		String tabla = "no";
		if(operador.getTipo() == Operador.HOSTAL) {
			Hostal host = (Hostal) operador;
			tabla = "HOSTALES";
			sql = String.format("INSERT INTO %1$s.%2$s (HORAAPERTURA, HORACIERRE, HABOCUPADAS, HABDISPONIBLES) VALUES (%3$s, %4$s, %5$s, %6$s)", 
					USUARIO, tabla, host.getHoraApertura(), host.getHoraCierre(), host.getHabOcupadas(), host.getHabDisponibles());
		}
		else if(operador.getTipo() == Operador.HOTEL) {
			Hotel hot = (Hotel) operador;
			tabla = "HOTELES";
			sql = String.format("INSERT INTO %1$s.%2$s (DIRECCION, HABDISPONIBLES, HABOCUPADAS, ESTRELLAS) VALUES (%3$s, %4$s, %5$s, %6$s)", 
					USUARIO, tabla, hot.getDireccion(), hot.getHabDisponibles(), hot.getHabOcupadas(), hot.getEstrellas());
		}
		else if(operador.getTipo() == Operador.PERSONA_COMUNIDAD) {
			PersonaComunidad pc = (PersonaComunidad) operador;
			tabla = "PERSONASCOMUNIDAD";
			sql = String.format("INSERT INTO %1$s.%2$s (CEDULA, EDAD, NOMBRE, TELEFONO) VALUES (%3$s, %4$s, %5$s, %6$s)", 
					USUARIO, tabla, pc.getCedula(), pc.getEdad(), pc.getNombre(), pc.getTelefono());
		}
		else if(operador.getTipo() == Operador.PERSONA_NATURAL) {
			PersonaNatural pn = (PersonaNatural) operador;
			tabla = "PERSONASNATURALES";
			sql = String.format("INSERT INTO %1$s.%2$s (NOMBRE, EDAD, CEDULA, TELEFONO) VALUES (%3$s, %4$s, %5$s, %6$s)", 
					USUARIO, tabla, pn.getNombre(), pn.getEdad(), pn.getCedula(), pn.getTelefono());
		}
		else if(operador.getTipo() == Operador.VIVIENDA_UNIVERSITARIA) {
			ViviendaUniversitaria vu = (ViviendaUniversitaria) operador;
			tabla = "VIVIENDASUNIVERSITARIAS";
			sql = String.format("INSERT INTO %1$s.%2$s (DIRECCION, HABDISPONIBLES, HABOCUPADAS) VALUES (%3$s, %4$s, %5$s)", 
					USUARIO, tabla, vu.getDireccion(), vu.getHabDisponibles(), vu.getHabOcupadas());
		}
		else return;

		System.out.println(sql);
		if(sql != ""){
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}

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
		Long idResponsable = resultSet.getLong("IDRESPONSABLE");

		Operador op = new Operador(id, capacidad, nombre, telefono, tipo, idResponsable);

		return op;
	}



	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public Operador[] getMasSolicitado() throws SQLException{

		Operador[] resp = new Operador[53];
		for(int i=1; i < 53; i++) {
			PreparedStatement prepStmt = conn.prepareStatement(masSolicitadoSemana(i));
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if(rs.next()){
				resp[i] = convertResultSetToOperador(rs);
			}
		}
		return resp;
	}

	public Operador[] getMenosSolicitado() throws SQLException{
		Operador[] resp = new Operador[53];
		for(int i=1; i < 53; i++) {
			PreparedStatement prepStmt = conn.prepareStatement(menosSolicitadoSemana(i));
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if(rs.next()){
				resp[i] = convertResultSetToOperador(rs);
			}
		}
		return resp;
	}

	private String menosSolicitadoSemana(int i) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * FROM"); 
		sql.append(String.format("(SELECT COUNT(%s.OFRS.IDRESERVA) as NUMRESERVAS, %s.OFERTAS.IDOPERADOR", USUARIO));
		sql.append("FROM    (SELECT IDOFERTA, IDRESERVA, COBRO, ESTADO, TO_CHAR(FECHAINICIO, 'WW') SEMANAINICIO, TO_CHAR(FECHAFIN, 'WW') SEMANAFIN, IDCLIENTE, COLECTIVA");
		sql.append(String.format("FROM %s.OFERTASRESERVAS INNER JOIN %s.RESERVAS ON %s.RESERVAS.ID = IDRESERVA", USUARIO));
		sql.append(String.format("WHERE TO_CHAR(FECHAINICIO, 'WW') <%s AND ((TO_CHAR(FECHAFIN, 'WW') >%s) OR (TO_CHAR(FECHAFIN, 'WW') < TO_CHAR(FECHAINICIO, 'WW')))) OFRS", i));
		sql.append(String.format(", %s.OFERTAS WHERE OFRS.IDOFERTA = %s.OFERTAS.ID", USUARIO));
		sql.append("group by IDOPERADOR"); 
		sql.append(String.format("order by COUNT(OFRS.IDRESERVA) ASC), %s.OPERADORES WHERE IDOPERADOR = %s.OPERADORES.ID AND ROWNUM = 1", USUARIO)); 

		return sql.toString();
	}

	private String masSolicitadoSemana(int semana) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * FROM"); 
		sql.append(String.format("(SELECT COUNT(%s.OFRS.IDRESERVA) as NUMRESERVAS, %s.OFERTAS.IDOPERADOR", USUARIO));
		sql.append("FROM    (SELECT IDOFERTA, IDRESERVA, COBRO, ESTADO, TO_CHAR(FECHAINICIO, 'WW') SEMANAINICIO, TO_CHAR(FECHAFIN, 'WW') SEMANAFIN, IDCLIENTE, COLECTIVA");
		sql.append(String.format("FROM %s.OFERTASRESERVAS INNER JOIN %s.RESERVAS ON %s.RESERVAS.ID = IDRESERVA", USUARIO));
		sql.append(String.format("WHERE TO_CHAR(FECHAINICIO, 'WW') <%s AND ((TO_CHAR(FECHAFIN, 'WW') >%s) OR (TO_CHAR(FECHAFIN, 'WW') < TO_CHAR(FECHAINICIO, 'WW')))) OFRS", semana));
		sql.append(String.format(", %s.OFERTAS WHERE OFRS.IDOFERTA = %s.OFERTAS.ID", USUARIO));
		sql.append("group by IDOPERADOR"); 
		sql.append(String.format("order by COUNT(OFRS.IDRESERVA) DESC), %s.OPERADORES WHERE IDOPERADOR = %s.OPERADORES.ID AND ROWNUM = 1", USUARIO)); 

		return sql.toString();
	}
}
