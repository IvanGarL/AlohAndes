package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Apartamento;

public class DAOApartamento extends DAOAlojamiento{
	
	public final static String USUARIO = "ISIS2304A481810";
	
	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;
	
	public DAOApartamento() {
		recursos = new ArrayList<Object>();
	}
	
	public ArrayList<Apartamento> getApartamentos() throws SQLException, Exception {
		ArrayList<Apartamento> aptos = new ArrayList<Apartamento>();

		String sql = String.format("SELECT * FROM %1$s.APARTAMENTOS", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			aptos.add(convertResultSetToApartamento(rs));
		}
		
		return aptos;
	}
	
	public Apartamento findApartamentoById(Long id) throws SQLException, Exception 
	{
		Apartamento apto = null;

		String sql = String.format("SELECT * FROM %1$s.APARTAMENTOS WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			apto = convertResultSetToApartamento(rs);
		}

		return apto;
	}
	
	public void addApartamento(Apartamento apto) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.APARTAMENTOS (ID, DIRECCION, MENAJE, AMOBLADO, NUMHABITACIONES, PERSONACOM) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s', '%7$s')", 
									USUARIO,
									apto.getId(),
									apto.getDireccion(),
									apto.getMenaje(),
									apto.getAmoblado(),
									apto.getNumHabitaciones(),
									apto.getPersonaComunidad());
		System.out.println(sql);
		
		//
		//
		addAlojamiento(apto);
		//
		//
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateApartamento(Apartamento apto) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.APARTAMENTOS SET ", USUARIO));
		sql.append(String.format( "ID = '%1$s' AND DIRECCION = '%2$s' AND MENAJE = '%3$s' AND AMOBLADO = '%4$s' AND NUMHABITACIONES = '%5$s' AND PERSONACOM = '%6$s' ", 
				apto.getId(),
				apto.getDireccion(),
				apto.getMenaje(),
				apto.getAmoblado(),
				apto.getNumHabitaciones(),
				apto.getPersonaComunidad()));
		
		System.out.println(sql);
		
		//
		//
		updateAlojamiento(apto);
		//
		//
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	public void deleteApartamento(Apartamento apto) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.APARTAMENTOS WHERE ID = %2$d", USUARIO, apto.getId());

		System.out.println(sql);
		
		//
		//
		deleteAlojamiento(apto);
		//
		//
		
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
	
	public Apartamento convertResultSetToApartamento(ResultSet resultSet) throws SQLException {
	
		Long id = resultSet.getLong("ID");
		Boolean menaje = resultSet.getBoolean("MENAJE");
		Boolean amoblado = resultSet.getBoolean("AMOBLADO");
		Integer numHabitaciones = resultSet.getInt("NUMHABITACIONES");
		String direccion = resultSet.getString("DIRECCION");
		Integer capacidad = resultSet.getInt("CAPACIDAD");
		Integer tamanho = resultSet.getInt("TAMANHO");
		Long personaComunidad = resultSet.getLong("HOTEL");


		Apartamento apto = new Apartamento(id, direccion, menaje, amoblado, numHabitaciones, capacidad, tamanho, personaComunidad);

		return apto;
	}
}
