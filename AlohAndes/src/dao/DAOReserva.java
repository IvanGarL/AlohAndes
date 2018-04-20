package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vos.Alojamiento;
import vos.Reserva;
import vos.ReservaEjColectiva;

public class DAOReserva {

	public final static String USUARIO = "ISIS2304A481810";

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	public DAOReserva() {
		recursos = new ArrayList<Object>();
	}

	public ArrayList<Reserva> getReservas() throws SQLException, Exception {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();

		String sql = String.format("SELECT * FROM %1$s.RESERVAS", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			reservas.add(convertResultSetToReserva(rs));
		}

		return reservas;
	}



	public Reserva findReservaById(Long id) throws SQLException, Exception 
	{
		Reserva reserva = null;

		String sql = String.format("SELECT * FROM %1$s.RESERVAS WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			reserva = convertResultSetToReserva(rs);
		}

		return reserva;
	}

	public void addReserva(Reserva reserva) throws SQLException, Exception {
		
		//Las líneas de código respectivas al aislamiento no estoy seguro si hagan falta o estén correctas

		PreparedStatement isolation = conn.prepareStatement("SET ISOLATION TRANSACTION LEVEL SERIALIZABLE");
		recursos.add(isolation);
		isolation.executeQuery();

		
		String sql = String.format("INSERT INTO %1$s.RESERVAS (ID, COBRO, ESTADO, FECHAFIN, FECHAINICIO, FECHAREALIZACION, IDCLIENTE, IDOPERADOR, COLECTIVA) VALUES (%2$s ,%3$s, '%4$s', '%5$s', '%6$s', '%7$s', '%8$s', '%9$s', '%10$s')", 
				USUARIO, 
				reserva.getId(),
				reserva.getCobro(),
				reserva.getFechaRealizacion(),
				reserva.getFechaInicio(),
				reserva.getFechaCierre(),
				reserva.getIdOperador(),
				reserva.getIdCliente());
		System.out.println(sql);

		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		
		PreparedStatement commit = conn.prepareStatement("COMMIT");
		recursos.add(commit);
		commit.executeQuery();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// RF7
	//----------------------------------------------------------------------------------------------------------------------------------

	public void addReservaColectiva(ReservaEjColectiva reserva, int num) throws SQLException, Exception{
		//Elegir cuáles alojamientos son candidatos
		StringBuilder sqlConsulta = new StringBuilder(); 
		sqlConsulta.append("SELECT T2.OFERTA, COUNT(T2.RESERVA) as OCUPACION, T2.CAPACIDAD");
		sqlConsulta.append(String.format("FROM ((%1$s.ALOJAMIENTOS INNER JOIN %1$s.OFERTA ON ALOJAMIENTOS.OFERTA = OFERTA.ID)T1 INNER JOIN (SELECT * FROM %1$s.RESERVAS WHERE RESERVAS.FECHAINICIO < SYSDATE AND RESERVAS.FECHAFIN > SYSDATE)) ON RESERVAS.ID = OFERTA.RESERVA T2", USUARIO));
		sqlConsulta.append(" WHERE ALOJAMIENTOS.TIPO = " + reserva.getTipoViv()); 
		sqlConsulta.append("GROUP BY T2.OFERTA");
		PreparedStatement prepStmt = conn.prepareStatement(sqlConsulta.toString());
		recursos.add(prepStmt);
		ResultSet rs1 = prepStmt.executeQuery();
		

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("INSERT ALL", USUARIO));

		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		int numReservasHechasAct = 0;
		while(numReservasHechasAct < num && rs1.next()) {
			int capacidadActual = rs1.getInt("CAPACIDAD");
			int ocupacionActual = rs1.getInt("OCUPACION");
			for(int i = 0; i < (capacidadActual-ocupacionActual) ; i++) {

				sql.append("INTO reservas(fecharealizacion, fechainicio, fechafin, cobro, idOperador, idOferta, idCliente, estado)");
				sql.append(String.format("VALUES ( %1$s, %2$s, %3$s, %4$s, %5$s, %6$s, %7$s, %8$s )",
						date,
						reserva.getFechaInicio(), 
						reserva.getFechaCierre(),
						rs1.getInt("COBRO"),
						rs1.getInt("OPERADOR"),
						rs1.getInt("OFERTA"),
						reserva.getIdCliente(),
						"reserva"));
			}		
		}
		sql.append("SELECT * FROM DUAL");
		if(numReservasHechasAct!=num && !rs1.next()) {
			//No hay más alojamientos, hay que avisar
		}
		else {
			PreparedStatement prepStFin = conn.prepareStatement(sql.toString());
			recursos.add(prepStFin);
			prepStFin.executeQuery();
		}

	}

	public void updateReserva(Reserva reserva) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.RESERVAS SET ", USUARIO));
		sql.append(String.format("COBRO = '%1$s' AND ESTADO = '%2$s AND FECHAREALIZACION = '%3$s' AND FECHAINICIO = '%4$s' AND FECHAFIN = '%5$s'  AND CLIENTE = '%6$s' ", 
				reserva.getCobro(),
				reserva.getEstado(),
				reserva.getFechaRealizacion(), 
				reserva.getFechaInicio(), 
				reserva.getFechaCierre(),
				reserva.getIdCliente()));

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void deleteReserva(Reserva reserva) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.RESERVAS WHERE ID = %2$d", USUARIO, reserva.getId());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	//----------------------------------------------------------------------------------------------------------------------------------
	// RF9
	//----------------------------------------------------------------------------------------------------------------------------------
	
	public void deleteReservaColectiva(ReservaEjColectiva reserva) throws SQLException, Exception {
		String sql = String.format("DELETE FROM %1$s.RESERVAS WHERE OPERADOR = %2$s AND FECHAINICIO = %3$s AND FECHAFIN = %4$s" , USUARIO, reserva.getIdCliente(), reserva.getFechaInicio(), reserva.getFechaCierre());

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

	public Reserva convertResultSetToReserva(ResultSet resultSet) throws SQLException {

		Long id = resultSet.getLong("ID");
		Double cobro = resultSet.getDouble("COBRO");
		String fechaR = resultSet.getString("FECHAREALIZACION");
		String fechaI = resultSet.getString("FECHAINICIO");
		String fechaF = resultSet.getString("FECHAFIN");
		Integer idOperador = resultSet.getInt("OPERADOR");
		Integer idOferta = resultSet.getInt("OFERTA");
		Integer idCliente = resultSet.getInt("CLIENTE");
		String estado = resultSet.getString("ESTADO"); 

		Reserva res = new Reserva(id, cobro, fechaR, fechaI, fechaF, idOperador, idOferta, idCliente, estado);

		return res;
	}
}
