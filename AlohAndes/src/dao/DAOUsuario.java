package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cliente;
import vos.Responsable;
import vos.Usuario;

public class DAOUsuario {


	public final static String USUARIO = "ISIS2304A481810";

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	public DAOUsuario() {
		recursos = new ArrayList<Object>();
	}

	public ArrayList<Usuario> getUsuarios() throws SQLException, Exception {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = String.format("SELECT * FROM %1$s.USUARIOS", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			usuarios.add(convertResultSetToUsuario(rs));
		}

		return usuarios;
	}

	public Usuario findUsuariorById(Long id) throws SQLException, Exception 
	{
		Usuario usuario = null;

		String sql = String.format("SELECT * FROM %1$s.USUARIOS WHERE ID = %2$d", USUARIO, id); 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			usuario = convertResultSetToUsuario(rs);
		}

		return usuario;
	}

	public Usuario addUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.USUARIOS (LOGIN, CONTRASENIA, TIPO) VALUES (%2$s ,%3$s, '%4$s')", 
				USUARIO, 
				usuario.getLogin(), 
				usuario.getContrasenia(),
				usuario.getTipo());
		
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		PreparedStatement prepStmt2 = conn.prepareStatement(String.format("SELECT MAX(ID) FROM %1$s.USUARIOS", USUARIO));
		recursos.add(prepStmt2);
		ResultSet rs = prepStmt2.executeQuery();
		
		if(usuario.getTipo() == Usuario.CLIENTE){
			Cliente cl = (Cliente) usuario;
			DAOCliente dao = new DAOCliente();
			dao.addCliente(cl);
		}else if(usuario.getTipo() == Usuario.RESPONSABLE){
			Responsable r = (Responsable) usuario;
			DAOResponsable dao = new DAOResponsable();
			dao.addResponsable(r);
		}
		
		usuario.setId(rs.getLong(0));
		return usuario;

	}

	public void updateUsuario(Usuario usuario) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.USUARIOS SET ", USUARIO));
		sql.append(String.format("ID = '%1$s' AND LOGIN = '%2$s' AND CONTRASENIA = '%3$s' AND TIPO = '%3$s' ", 
				usuario.getId(),
				usuario.getLogin(), 
				usuario.getContrasenia(), usuario.getTipo()));

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void deleteUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.USUARIOS WHERE ID = %2$d", USUARIO, usuario.getId());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// RFC5
	//----------------------------------------------------------------------------------------------------------------------------------

	public ArrayList<String> getIndicesOcupacion() throws SQLException, Exception{
		ArrayList<String> respuesta = new ArrayList<>();

		String sql = String.format("select t1.nombre, count(reservas), t1.capacidad"
				+ "from (%1$s.alojamientos INNER JOIN %1$s.ofertas ON oferta = id) t1, %1$s.reservas, %1$s.dual"
				+ "WHERE TO_CHAR(dual.CURRENT_DATE, 'DD-MON-YYYY')=TO_CHAR(reservas.fecha, 'DD-MON-YYYY')"
				+ "group by t1.nombre)", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			respuesta.add(rs.getString(0) + " : " + rs.getInt(1) + " de "+ rs.getInt(2));
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

	public Usuario convertResultSetToUsuario(ResultSet resultSet) throws SQLException {

		Long id = resultSet.getLong("ID");
		String login = resultSet.getString("LOGIN");
		String contrasenia = resultSet.getString("CONTRASENIA");
		String tipo = resultSet.getString("TIPO");

		Usuario usu = new Usuario(id, login, contrasenia, tipo);

		return usu;
	}

}
