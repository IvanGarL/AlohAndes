package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Oferta;


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
				oferta.getFechaRetiro(),
				oferta.getNombre(),
				oferta.getIdOperador(),
				oferta.getIdAlojamiento());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateOferta(Oferta oferta) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.OFERTAS SET ", USUARIO));
		sql.append(String.format("ID = '%1$s' AND COSTO = '%2$s' AND FECHARETIRO = '%3$s' AND NOMBRE = '%4$s' AND OPERADOR = '%5$s' AND ALOJAMIENTO = '%6$s' ", 
				oferta.getId(),
				oferta.getCosto(),
				oferta.getFechaRetiro(),
				oferta.getNombre(),
				oferta.getIdOperador(),
				oferta.getIdAlojamiento()));

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void deleteOferta(Oferta oferta) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.OFERTAS WHERE ID = %2$d", USUARIO, oferta.getId());

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

	public Oferta convertResultSetToOferta(ResultSet resultSet) throws SQLException {

		Long id = resultSet.getLong("ID");
		Double costo = resultSet.getDouble("COSTO");
		String fechaR = resultSet.getString("FECHARETIRO");
		String nombre = resultSet.getString("NOMBRE");
		Integer idOperador = resultSet.getInt("OPERADOR");
		Integer idAlojamiento = resultSet.getInt("ALOJAMIENTO");

		Oferta of = new Oferta(id, costo, fechaR, nombre, idOperador, idAlojamiento);

		return of;
	}
}
