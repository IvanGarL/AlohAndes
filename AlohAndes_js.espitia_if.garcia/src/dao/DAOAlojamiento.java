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
		//		
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
	// TODO RFC6
	//------------------------------------------------
	public ArrayList<Alojamiento> alojamientosFechaServicios(Date fechaI, Date fechaF,ArrayList<Servicio> servicios){
		ArrayList<Alojamiento> resp = new ArrayList<>();
		
		//String sql = String.format("", USUARIO,args);
		
		return resp;
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// TODO RFC8
	//----------------------------------------------------------------------------------------------------------------------------------

	public ArrayList<Cliente> getClientesFrecuentes() throws SQLException, Exception{
		ArrayList<Cliente> respuesta = new ArrayList<>();
		DAOCliente dao = new DAOCliente();
	
		String sql = String.format("select *"
				+ "from (%1$s.alojamientos INNER JOIN %1$s.ofertas ON oferta = id) t1 INNER JOIN %1$s.reservas ON t1.oferta = reservas.oferta"
				+ "order by fechaInicio, fechaFin)", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			dao.convertResultSetToCliente(rs);
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
