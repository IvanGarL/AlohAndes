package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Alojamiento;
import vos.Cliente;
import vos.Servicio;

//TODO revisar manejo de datos -> IdOferta
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

		String sql = String.format("INSERT INTO %1$s.ALOJAMIENTOS (ID, TAMANHO, CAPACIDAD, TIPO, IDOFERTA) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s')", 
				USUARIO,
				alojamiento.getId(),
				alojamiento.getTamanho(), 
				alojamiento.getCapacidad(),
				alojamiento.getTipo(),
				alojamiento.getIdOferta());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateAlojamiento(Alojamiento alojamiento) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.ALOJAMIENTOS SET ", USUARIO));
		sql.append(String.format( "ID = '%1$s' AND TAMANHO = '%2$s' AND CAPACIDAD = '%3$s' ", 
				alojamiento.getId(),
				alojamiento.getTamanho(), 
				alojamiento.getCapacidad()));

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}




	public void deleteAlojamiento(Alojamiento alojamiento) throws SQLException, Exception {


		//		FALTA SACAR TODA LA INFO CORRESPONDIENTE A ESTE ALOJAMIENTO EN LAS OTRAS TABLAS
		//		String sqlPr = String.format("DELETE FROM %1$s.OFERTAS WHERE ALOJAMIENTO = %2$d", USUARIO, alojamiento.getId());
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
	// TODO RFC3
	//----------------------------------------------------------------------------------------------------------------------------------

	public String getIndiceOcupacion(Long idAlojamiento) throws SQLException, Exception{

		String sql = String.format("select t1.nombre, count(reservas), t1.capacidad"
				+ " from (%1$s.alojamientos INNER JOIN %1$s.ofertas ON oferta = id) t1, %1$s.reservas, %1$s.dual"
				+ " WHERE TO_CHAR(dual.CURRENT_DATE, 'DD-MON-YYYY')=TO_CHAR(reservas.fecha, 'DD-MON-YYYY') AND ID = " + idAlojamiento
				+ " group by t1.nombre)", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			return (rs.getString(0) + " : " + rs.getInt(1) + " de "+ rs.getInt(2));
		}else{
			return "No está el alojamiento indicado";
		}


	}
	
	//------------------------------------------------
	// TODO RFC4
	//------------------------------------------------
	public ArrayList<Alojamiento> alojamientosFechaServicios(String fechaI, String fechaF,ArrayList<String> servicios) throws SQLException{
		
		ArrayList<Alojamiento> resp = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM");
		sql.append(String.format("(select count(t2.id) as numreservasfecha, idalojamiento, idoferta " + 
				"        from (%1$s.ofertas full outer join (%1$s.ofertasreservas on id = idoferta)full outer join \r\n" + 
				"                        (select * from (%1$s.reservas where (fechainicio between '%2$s' and '%3$s'or fechafin between '%2$s' and '%3$s'or (fechainicio < '%2$s' and fechafin>'%3$s'))) t2 \r\n" + 
				"                on idreserva = t2.id\r\n" + 
				"        group by idalojamiento, idoferta) reservashechas,", USUARIO, fechaI, fechaF));
		sql.append(String.format("(select %1$s.ofertas.id as idoferta, idalojamiento, %1$s.servicios.id as idservicio, %1$s.servicios.nombre as servicio\\r\\n\" + \r\n" + 
				"				\"        from (%1$s.ofertas inner join %1$s.servicios on ofertas.id = %1$s.servicios.idoferta)\\r\\n\" + \r\n" + 
				"				\"        where ", USUARIO));
		if (servicios.get(0)!=null) {
			sql.append(String.format("%1$s.servicios.nombre = '%2$s' ", USUARIO, servicios.get(0)));
		}
		for(int i = 1; i < servicios.size(); i++) {
			sql.append(String.format(" or %1$s.servicios.nombre = '%2$s'",servicios.get(i)));
		}
		sql.append(") serviciosofrecidos ");
		sql.append(String.format("where reservashechas.numreservasfecha = 0 and serviciosofrecidos.idoferta = reservashechas.idoferta;", USUARIO));
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while (rs.next()) {
			resp.add(convertResultSetToAlojamiento(rs));
		}
		
		return resp;
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// TODO RFC8
	//----------------------------------------------------------------------------------------------------------------------------------

	public ArrayList<Cliente> getClientesFrecuentes(Long idAlojamiento) throws SQLException, Exception{
		ArrayList<Cliente> respuesta = new ArrayList<>();
		DAOCliente dao = new DAOCliente();
	
		String sql = String.format("select clientes15d.idcliente, totaldias, vecesreservadas\r\n" + 
				" from (select sum(dias) as totaldias, idcliente\r\n" + 
				"        from  ((select id as idreserva, fechafin - fechainicio as dias, idcliente from %1$s.reservas) natural inner join %1$s.ofertasreservas)\r\n" + 
				"                natural inner join (select id as idoferta, idalojamiento from %1$s.ofertas) \r\n" + 
				"        where idalojamiento = %2$s \r\n" + 
				"        group by idcliente) clientes15d,\r\n" + 
				"       (select count(idreserva) as vecesreservadas, idcliente\r\n" + 
				"        from ((select id as idreserva, fechafin, fechainicio, idcliente from %1$s.reservas) natural inner join %1$s.ofertasreservas)\r\n" + 
				"                natural inner join (select id as idoferta, idalojamiento from %1$s.ofertas)\r\n" + 
				"        where idalojamiento = %2$s \r\n" + 
				"        group by idcliente) clientes3r\r\n" + 
				"where clientes15d.idcliente = clientes3r.idcliente\r\n" + 
				"        and (clientes15d.totaldias > 15 or clientes3r.vecesreservadas >= 3);", USUARIO, idAlojamiento);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			PreparedStatement prepStmtcliente = conn.prepareStatement("SELECT * FROM CLIENTES WHERE ID = " + rs.getLong("IDCLIENTE"));
			recursos.add(prepStmtcliente);
			ResultSet rsCliente = prepStmtcliente.executeQuery();
			dao.convertResultSetToCliente(rsCliente);
		}

		return respuesta;
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// TODO RFC9
	//----------------------------------------------------------------------------------------------------------------------------------

	public ArrayList<Alojamiento> getMenosUsados() throws SQLException, Exception{
		ArrayList<Alojamiento> respuesta = new ArrayList<>();

		String sql = String.format("select *"
				+ "from (%1$s.alojamientos INNER JOIN %1$s.ofertas ON oferta = id) t1 INNER JOIN %1$s.reservas ON t1.oferta = reservas.oferta"
				+ "order by fechaInicio, fechaFin)", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			respuesta.add(convertResultSetToAlojamiento(rs));
		}

		return respuesta;
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
		Long idOferta = resultSet.getLong("IDOFERTA");
		Long idOperador = resultSet.getLong("IDOPERADOR");
		
		Alojamiento alo = new Alojamiento(id, tamanho, capacidad, tipo, idOferta, idOperador);

		return alo;
	}

}
