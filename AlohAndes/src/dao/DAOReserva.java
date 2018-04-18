package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Reserva;

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

		String sql = String.format("INSERT INTO %1$s.RESERVAS (ID, COBRO, FECHAREALIZACION, FECHAINICIO, FECHAFIN, OPERADOR, OFERTA, CLIENTE) VALUES (%2$s ,%3$s, '%4$s', '%5$s', '%6$s', '%7$s', '%8$s')", 
				USUARIO, 
				reserva.getId(),
				reserva.getCobro(),
				reserva.getFechaRealizacion(),
				reserva.getFechaInicio(),
				reserva.getFechaCierre(),
				reserva.getIdOperador(),
				reserva.getIdOferta(),
				reserva.getIdCliente());
		System.out.println(sql);


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// RF7
	//----------------------------------------------------------------------------------------------------------------------------------

	public void addReservaColectiva() throws SQLException, Exception{
		
	
	}

	public void updateReserva(Reserva reserva) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.RESERVAS SET ", USUARIO));
		sql.append(String.format("ID = '%1$s' AND COBRO = '%2$s' AND FECHAREALIZACION = '%3$s' AND FECHAINICIO = '%4$s' AND FECHAFIN = '%5$s' AND OPERADOR = '%6$s'  AND OFERTA = '%7$s'  AND CLIENTE = '%8$s'  ", 
				reserva.getId(),
				reserva.getCobro(),
				reserva.getFechaRealizacion(), 
				reserva.getFechaInicio(), 
				reserva.getFechaCierre(),
				reserva.getIdOperador(), 
				reserva.getIdOferta(),
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
