package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

		String sql = String.format("Select Sum(cobro) as ganancias"
				+ "from (select cobro "
				+ "from %1$s.reservas "
				+ "where operador = '%2$s' and FECHAFIN > '1/1/2018')filtro", USUARIO, id);

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
		String sql, idAsignado;
		Long nuevoId;
		if(operador.getIdResponsable() != null){
			sql = String.format("INSERT INTO %1$s.OPERADORES (CAPACIDAD, NOMBRE, TELEFONO, TIPO, IDRESPONSABLE) VALUES (%2$s, '%3$s', '%4$s', '%5$s','%6$s' )", 
					USUARIO, 
					operador.getCapacidad(),
					operador.getNombre(), 
					operador.getTelefono(), 
					operador.getTipo(),
					operador.getIdResponsable());
			idAsignado = String.format("SELECT ID FROM %1$s.OPERADORES WHERE CAPACIDAD = %2$s AND NOMBRE = '%3$s', TELEFONO = '%4$s', TIPO = '%5$s', IDRESPONSABLE = '%6$s'", 
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
			idAsignado = String.format("SELECT ID FROM %1$s.OPERADORES WHERE CAPACIDAD = %2$s AND NOMBRE = '%3$s', TELEFONO = '%4$s', TIPO = '%5$s', IDRESPONSABLE = '%6$s'", 
					USUARIO, 
					operador.getCapacidad(),
					operador.getNombre(), 
					operador.getTelefono(), 
					operador.getTipo(),
					operador.getIdResponsable());
		}

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		PreparedStatement pSId = conn.prepareStatement(idAsignado);
		recursos.add(pSId);
		ResultSet rs = pSId.executeQuery();

		addOperadorEspecifico(operador);

		nuevoId = rs.getLong(0);
		operador.setId(nuevoId);
		return operador;

	}


	public void updateOperador(Operador operador) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.OPERADORES SET ", USUARIO));
		sql.append(String.format("CAPACIDAD = '%1$s', NOMBRE = '%2$s', TELEFONO = '%3$s' ", operador.getCapacidad(), operador.getNombre(), operador.getTelefono(), operador.getTipo()));

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void deleteOperador(Operador operador) throws SQLException, Exception {

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

		//Borrar sus ofertas
		String sqlPr = String.format("DELETE FROM %1$s.OFERTAS WHERE IDOPERADOR = %2$d", USUARIO, operador.getId());

		PreparedStatement prepStmtPr = conn.prepareStatement(sqlPr);
		recursos.add(prepStmtPr);
		prepStmtPr.executeQuery();

		//Borrar sus reservas
		String sqlPrRes = String.format("DELETE FROM %1$s.RESERVAS WHERE IDOPERADOR = %2$d", USUARIO, operador.getId());

		PreparedStatement prepStmtPrRes = conn.prepareStatement(sqlPrRes);
		recursos.add(prepStmtPrRes);
		prepStmtPrRes.executeQuery();

		//Finalmente se borra el operador de la tabla especifica y la general
		borrarOperadorEspecifico(operador);

		String sql = String.format("DELETE FROM %1$s.OPERADORES WHERE ID = %2$d", USUARIO, operador.getId());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS AUXILIARES
	//----------------------------------------------------------------------------------------------------------------------------------

	private void borrarAlojamientosEspecificos(Operador operador) throws SQLException {

		String seleccionIdAlojamiento = String.format("SELECT ID FROM %1$s.ALOJAMIENTOS "
				+ "WHERE IDOFERTA IN (SELECT ID FROM %1$s.OFERTAS WHERE IDOPERADOR = %2$s)", USUARIO, operador.getId());
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
			String sql = String.format("DELETE FROM %1$s.%2$s WHERE ID IN %3$s", USUARIO, tabla, seleccionIdAlojamiento);

			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
		if(tabla2!="no"){
			String sql = String.format("DELETE FROM %1$s.%2$s WHERE ID IN %3$s", USUARIO, tabla, seleccionIdAlojamiento);

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
		String sql = String.format("DELETE FROM %1$s.%2$s WHERE ID %3$s", USUARIO, tabla, operador.getId());

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
			sql = "INSERT INTO ";
		}
		else if(operador.getTipo() == Operador.HOTEL) {
			Hotel hot = (Hotel) operador;
			tabla = "HOTELES";
		}
		else if(operador.getTipo() == Operador.PERSONA_COMUNIDAD) {
			PersonaComunidad pc = (PersonaComunidad) operador;
			tabla = "PERSONASCOMUNIDAD";
		}
		else if(operador.getTipo() == Operador.PERSONA_NATURAL) {
			PersonaNatural pn = (PersonaNatural) operador;
			tabla = "PERSONASNATURALES";
		}
		else if(operador.getTipo() == Operador.VIVIENDA_UNIVERSITARIA) {
			ViviendaUniversitaria vu = (ViviendaUniversitaria) operador;
			tabla = "VIVIENDASUNIVERSITARIAS";
			
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

}
