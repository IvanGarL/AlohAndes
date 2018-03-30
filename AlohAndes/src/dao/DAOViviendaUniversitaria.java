package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Operador;
import vos.ViviendaUniversitaria;

public class DAOViviendaUniversitaria extends DAOOperador{
	public final static String USUARIO = "ISIS2304A481810";

	/**
	 * Arraylists de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	public DAOViviendaUniversitaria() {
		recursos = new ArrayList<Object>();
	}

	public ArrayList<ViviendaUniversitaria> getViviendasUniversitarias() throws SQLException, Exception {
		ArrayList<ViviendaUniversitaria> viviendasUniversitarias = new ArrayList<ViviendaUniversitaria>();

		String sql = String.format("SELECT * FROM ( %1$s.VIVIENDASUNIVS NATURAL INNER JOIN %1$s.OPERADORES)", USUARIO);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			viviendasUniversitarias.add(convertResultSetToViviendaUniversitaria(rs));
		}

		return viviendasUniversitarias;
	}

	public ViviendaUniversitaria findViviendaUniversitariaById(Long id) throws SQLException, Exception {
		String sql = String.format("SELECT * FROM ( %1$s.VIVIENDASUNIVS NATURAL INNER JOIN %1$s.OPERADORES) WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			return convertResultSetToViviendaUniversitaria(rs);
		}
		return null;
	}

	public void addViviendaUniversitaria(ViviendaUniversitaria viviendaUniversitaria) throws SQLException, Exception {

		addOperador(viviendaUniversitaria);
		String sql = String.format("INSERT INTO %1$s.VIVIENDASUNIVS (ID, DIRECCION, HABDISPONIBLES, HABOCUPADAS, RUT) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s' )", 
				USUARIO, 
				viviendaUniversitaria.getId(),
				viviendaUniversitaria.getDireccion(),
				viviendaUniversitaria.getHabDisponibles(),
				viviendaUniversitaria.getHabOcupadas(),
				viviendaUniversitaria.getRut());	
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateViviendaUniversitaria(ViviendaUniversitaria viviendaUniversitaria) throws SQLException, Exception {
		updateOperador(viviendaUniversitaria);

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.VIVIENDASUNIVS SET ", USUARIO));
		sql.append(String.format( "DIRECCION = '%1$s', HABDISPONIBLES = '%2$s', HABOCUPADAS = '%3$s', RUT = '%4$s'", 
				viviendaUniversitaria.getDireccion(),
				viviendaUniversitaria.getHabDisponibles(),
				viviendaUniversitaria.getHabOcupadas(),
				viviendaUniversitaria.getRut()));
		sql.append ("WHERE ID = " + viviendaUniversitaria.getId ());


		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void deleteViviendaUniversitaria(ViviendaUniversitaria viviendaUniversitaria) throws SQLException, Exception {

		deleteOperador(viviendaUniversitaria);

		String sql = String.format("DELETE FROM %1$s.VIVIENDASUNIVS WHERE ID = %2$d", USUARIO, viviendaUniversitaria.getId());

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
	//..................
	private ViviendaUniversitaria convertResultSetToViviendaUniversitaria(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub

		//Atributos heredados
		Long id = rs.getLong("ID");
		Integer capacidad = rs.getInt("CAPACIDAD");
		String nombre = rs.getString("NOMBRE");
		Integer telefono = rs.getInt("TELEFONO");
		//Atributos propios
		String direccion = rs.getString("DIRECCION");
		Integer habDisponibles = rs.getInt("HABDISPONIBLES");
		Integer habOcupadas = rs.getInt("HABOCUPADAS");
		Integer rut = rs.getInt("RUT");

		return new ViviendaUniversitaria(direccion, habDisponibles, habOcupadas, rut, id, capacidad, nombre, telefono, Operador.VIVIENDA_UNIVERSITARIA);
	}
}
