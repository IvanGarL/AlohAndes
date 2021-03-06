package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Alojamiento;
import vos.Oferta;
import vos.Operador;
import vos.Reserva;


public class DAOOferta {

	public final static String USUARIO = "ISIS2304A481810";

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	public DAOOferta() {
		recursos = new ArrayList<Object>();
	}

	public ArrayList<Oferta> getOfertas() throws SQLException, Exception {
		ArrayList<Oferta> reservas = new ArrayList<Oferta>();

		String sql = String.format("SELECT * FROM %1$s.OFERTAS", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			reservas.add(convertResultSetToOferta(rs));
		}

		return reservas;
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// TODO RFC2
	//----------------------------------------------------------------------------------------------------------------------------------

	public ArrayList<Oferta> getOfertasPopulares() throws SQLException, Exception {
		ArrayList<Oferta> reservas = new ArrayList<Oferta>();

		String sql = String.format("select count(idreserva) as numreservas, ofertas.id, ofertas.costo, ofertas.ESTADO, ofertas.NOMBRE from ofertas inner join ofertasreservas on id = idoferta  where rownum <=20 group by ofertas.id, ofertas.costo, ofertas.ESTADO, ofertas.NOMBRE order by count(idreserva)\r\n" + 
				";", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			reservas.add(convertResultSetToOferta(rs));
		}

		return reservas;
	}

	public Oferta findOfertaById(Long id) throws SQLException, Exception 
	{
		Oferta oferta = null;

		String sql = String.format("SELECT * FROM %1$s.OFERTAS WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			oferta = convertResultSetToOferta(rs);
		}

		return oferta;
	}


	public void addOferta(Oferta oferta) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.OFERTAS (ID, COSTO, FECHARETIRO, NOMBRE, OPERADOR, ALOJAMIENTO) VALUES (%2$s ,%3$s, '%4$s', '5$s', '%6$s')", 
				USUARIO, 
				oferta.getId(),
				oferta.getCosto(),
				oferta.getNombre(),
				oferta.getIdOperador(),
				oferta.getIdAlojamiento());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/*
	 * TODO RF9 -------------- DESHABILITAR OFERTA DE ALOJAMIENTO
	 */
	public void updateOferta(Oferta oferta) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %1$s.OFERTAS SET ESTADO = '%2$s", USUARIO, oferta.getEstado()));

		if(oferta.getEstado()=="deshabilitado"){
			cambiarReservas(oferta);
		}

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	private void cambiarReservas(Oferta oferta) {

	}

	/*
	 * RF6 -------- RETIRAR UNA OFERTA DE ALOJAMIENTO
	 */
	public void deleteOferta(Oferta oferta) throws SQLException, Exception {

		//Elimina alojamiento asociado a la oferta que se va a eliminar
		DAOAlojamiento daoAl = new DAOAlojamiento();
		String sqlAlojamientoSelect = String.format("SELECT FROM %1$s.ALOJAMIENTOS WHERE ALOJAMIENTO.ID = %2$s",USUARIO,oferta.getIdAlojamiento());
		PreparedStatement prepStmtSelect = conn.prepareStatement(sqlAlojamientoSelect);
		recursos.add(prepStmtSelect);
		ResultSet rsSelect = prepStmtSelect.executeQuery();
		Alojamiento alo = daoAl.convertResultSetToAlojamiento(rsSelect);
		System.out.println(eliminarOfertaEspecifica(alo));

		//Elimina de la tabla de alojamientos
		String sqlAlojamientos = String.format("DELETE FROM %1$s.ALOJAMIENTOS WHERE ALOJAMIENTO.ID = %2$d", USUARIO, oferta.getIdAlojamiento());
		PreparedStatement prepStmtAlojamientos = conn.prepareStatement(sqlAlojamientos);
		recursos.add(prepStmtAlojamientos);
		prepStmtAlojamientos.executeQuery();
		System.out.println(sqlAlojamientos);

		//Elimina de la tabla join
		String sqlJoinAlRe = String.format("DELETE FROM %1$s.OFERTASRESERVAS WHERE OFERTASRESERVAS.IDOFERTA = %2$d", USUARIO, oferta.getId());
		PreparedStatement prepStmtJoinAlRe = conn.prepareStatement(sqlJoinAlRe);
		recursos.add(prepStmtJoinAlRe);
		prepStmtJoinAlRe.executeQuery();
		System.out.println(sqlJoinAlRe);

		//Elimina de la tabla de ofertas
		String sqlfinal = String.format("DELETE FROM %1$s.OFERTAS WHERE ID = %2$d", USUARIO, oferta.getId());
		PreparedStatement prepStmtfinal = conn.prepareStatement(sqlfinal);
		recursos.add(prepStmtfinal);
		prepStmtfinal.executeQuery();
		System.out.println(sqlfinal);
	}

	private String eliminarOfertaEspecifica(Alojamiento alo) throws SQLException{
		String tabla = "no";
		switch (alo.getTipo()) {
		case Alojamiento.APARTAMENTO:
			tabla = "APARTAMENTOS";
			break;
		case Alojamiento.HABITACION:
			tabla = "HABITACIONES";
			break;
		case Alojamiento.HABITACION_HOTEL:
			tabla = "HABSHOTEL";
			break;
		case Alojamiento.HABITACION_UNIVERSITARIA:
			tabla = "HABSUNIVERSITARIAS";
			break;
		case Alojamiento.VIVIENDA_COMUNITARIA:
			tabla = "VIVIENDASUNIVERSITARIAS";
			break;	
		default:
			return "error";
		}
		String sql = String.format("DELETE FROM %1$s.%2$s WHERE ID %3$s", USUARIO, tabla, alo.getId());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		return sql;
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

	public Oferta convertResultSetToOferta(ResultSet resultSet) throws SQLException {

		Long id = resultSet.getLong("ID");
		Double costo = resultSet.getDouble("COSTO");
		String nombre = resultSet.getString("NOMBRE");
		Long idOperador = resultSet.getLong("IDOPERADOR");
		Long idAlojamiento = resultSet.getLong("IDALOJAMIENTO");
		String estado = resultSet.getString("ESTADO");
		Oferta of = new Oferta(id, costo, estado,nombre, idOperador, idAlojamiento);

		return of;
	}







	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public Oferta[] getOfertaMasOcupada() throws SQLException{

		Oferta[] resp = new Oferta[53];

		for(int i = 0; i < 53; i++) {
			PreparedStatement prepStmt = conn.prepareStatement(masOcupadaSemana(i));
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if(rs.next()) {
				resp[i] = convertResultSetToOferta(rs);
			}
		}

		return resp;
	}

	public Oferta[] getOfertaMenosOcupada() throws SQLException{

		Oferta[] resp = new Oferta[53];

		for(int i = 0; i < 53; i++) {
			PreparedStatement prepStmt = conn.prepareStatement(menosOcupadaSemana(i));
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if(rs.next()) {
				resp[i] = convertResultSetToOferta(rs);
			}
		}

		return resp;
	}

	private String menosOcupadaSemana(int semana) {
		StringBuilder sql = new StringBuilder("SELECT * FROM"); 
		sql.append(String.format("SELECT * FROM \r\n" + 
				"    (SELECT COUNT(OFRS.IDRESERVA), %1$s.OFERTAS.ID, %1$s.OFERTAS.IDOPERADOR, %1$s.OFERTAS.IDALOJAMIENTO, %1$s.OFERTAS.COSTO, %1$s.OFERTAS.ESTADO, %1$s.OFERTAS.NOMBRE\r\n" + 
				"    FROM (SELECT IDOFERTA, IDRESERVA, COBRO, ESTADO, TO_CHAR(FECHAINICIO, 'WW') SEMANAINICIO, TO_CHAR(FECHAFIN, 'WW') SEMANAFIN, IDCLIENTE, COLECTIVA\r\n" + 
				"            FROM %1$s.OFERTASRESERVAS INNER JOIN %1$s.RESERVAS ON %1$s.RESERVAS.ID = IDRESERVA\r\n" + 
				"            WHERE TO_CHAR(FECHAINICIO, 'WW') <%2$s AND ((TO_CHAR(FECHAFIN, 'WW') >%2$s) OR (TO_CHAR(FECHAFIN, 'WW') < TO_CHAR(FECHAINICIO, 'WW')))) OFRS\r\n" + 
				"        , %1$s.OFERTAS\r\n" + 
				"    WHERE OFRS.IDOFERTA = %1$s.OFERTAS.ID \r\n" + 
				"    group by %1$s.OFERTAS.ID, %1$s.OFERTAS.IDOPERADOR, %1$s.OFERTAS.IDALOJAMIENTO, %1$s.OFERTAS.COSTO, %1$s.OFERTAS.ESTADO, %1$s.OFERTAS.NOMBRE\r\n" + 
				"    order by COUNT(OFRS.IDRESERVA) ASC), ALOJAMIENTOS \r\n" + 
				"WHERE  IDALOJAMIENTO = %1$s.ALOJAMIENTOS.ID", USUARIO, semana));
		return sql.toString();
	}

	private String masOcupadaSemana(int semana) {
		StringBuilder sql = new StringBuilder("SELECT * FROM"); 
		sql.append(String.format("(SELECT COUNT(%s.OFERTASRESERVAS.IDRESERVA), %s.OFERTAS.ID, %s.OFERTAS.IDOPERADOR, %s.OFERTAS.IDALOJAMIENTO, %s.OFERTAS.COSTO, %s.OFERTAS.ESTADO, %s.OFERTAS.NOMBRE", USUARIO));
		sql.append(String.format("FROM %s.OFERTASRESERVAS, %s.OFERTAS", USUARIO));
		sql.append(String.format("WHERE %s.OFERTASRESERVAS.IDOFERTA = %s.OFERTAS.ID", USUARIO)); 
		sql.append(String.format("group by %s.OFERTAS.ID, %s.OFERTAS.IDOPERADOR, %s.OFERTAS.IDALOJAMIENTO, %s.OFERTAS.COSTO, %s.OFERTAS.ESTADO, %s.OFERTAS.NOMBRE", USUARIO));
		sql.append(String.format("order by COUNT(%s.OFERTASRESERVAS.IDRESERVA) DESC), %sALOJAMIENTOS", USUARIO)); 
		sql.append(String.format("WHERE ROWNUM = 1 AND IDALOJAMIENTO = %s.ALOJAMIENTOS.ID", USUARIO));
		return sql.toString();
	}
}
