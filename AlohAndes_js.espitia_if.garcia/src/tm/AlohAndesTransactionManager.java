package tm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import dao.DAOAlojamiento;
import dao.DAOCliente;
import dao.DAOOferta;
import dao.DAOOperador;
import dao.DAOReserva;
import vos.Alojamiento;
import vos.Cliente;
import vos.Oferta;
import vos.Operador;
import vos.Reserva;
import vos.ReservaEjColectiva;


public class AlohAndesTransactionManager {

	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private static String CONNECTION_DATA_PATH;

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * Atributo que representa la conexion a la base de datos
	 */
	private Connection conn;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE CONEXION E INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * <b>Metodo Contructor de la Clase ParranderosTransactionManager</b> <br/>
	 * <b>Postcondicion: </b>	Se crea un objeto  ParranderosTransactionManager,
	 * 						 	Se inicializa el path absoluto del archivo de conexion,
	 * 							Se inicializna los atributos para la conexion con la Base de Datos
	 * @param contextPathP Path absoluto que se encuentra en el servidor del contexto del deploy actual
	 * @throws IOException Se genera una excepcion al tener dificultades con la inicializacion de la conexion<br/>
	 * @throws ClassNotFoundException 
	 */
	public AlohAndesTransactionManager(String contextPathP) {

		try {
			CONNECTION_DATA_PATH = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
			initializeConnectionData();
		} 
		catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo encargado de inicializar los atributos utilizados para la conexion con la Base de Datos.<br/>
	 * <b>post: </b> Se inicializan los atributos para la conexion<br/>
	 * @throws IOException Se genera una excepcion al no encontrar el archivo o al tener dificultades durante su lectura<br/>
	 * @throws ClassNotFoundException 
	 */
	private void initializeConnectionData() throws IOException, ClassNotFoundException {

		FileInputStream fileInputStream = new FileInputStream(new File(CONNECTION_DATA_PATH));
		Properties properties = new Properties();

		properties.load(fileInputStream);
		fileInputStream.close();

		this.url = properties.getProperty("url");
		this.user = properties.getProperty("usuario");
		this.password = properties.getProperty("clave");
		this.driver = properties.getProperty("driver");

		//Class.forName(driver);
	}

	/**
	 * Metodo encargado de generar una conexion con la Base de Datos.<br/>
	 * <b>Precondicion: </b>Los atributos para la conexion con la Base de Datos han sido inicializados<br/>
	 * @return Objeto Connection, el cual hace referencia a la conexion a la base de datos
	 * @throws SQLException Cualquier error que se pueda llegar a generar durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("[Alohandes APP] Attempting Connection to: " + url + " - By User: " + user);
		return DriverManager.getConnection(url, user, password);
	}


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS TRANSACCIONALES
	//----------------------------------------------------------------------------------------------------------------------------------

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RF1
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que modela la transaccion que agrega un operador a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el operador que entra como parametro <br/>
	 * @param operador - el operador a agregar. operador != null
	 * @throws Exception - Cualquier error que se genere agregando el operador
	 */
	public void addOperador(Operador operador) throws Exception 
	{
		DAOOperador daoOperador = new DAOOperador();
		try
		{
			this.conn = darConexion();
			daoOperador.setConn(conn);
			daoOperador.addOperador(operador);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RF2
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que modela la transaccion que agrega un alojamiento a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el alojamiento que entra como parametro <br/>
	 * @param alojamiento - el alojamiento a agregar. alojamiento!= null
	 * @throws Exception - Cualquier error que se genere agregando al alojamiento
	 */
	public void addAlojamiento(Alojamiento alojamiento) throws Exception 
	{

		DAOAlojamiento daoAlojamiento = new DAOAlojamiento( );
		try
		{
			this.conn = darConexion();
			daoAlojamiento.setConn(conn);
			daoAlojamiento.addAlojamiento(alojamiento);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoAlojamiento.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RF3
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que modela la transaccion que agrega un cliente a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el cliente que entra como parametro <br/>
	 * @param cliente - el cliente a agregar. cliente != null
	 * @throws Exception - Cualquier error que se genere agregando al cliente
	 */
	public void addCliente(Cliente cliente) throws Exception 
	{

		DAOCliente daoCliente = new DAOCliente( );
		try
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			daoCliente.addCliente(cliente);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RF4
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * TODO: CAMBIAR NOMBRES Y TIPOS VARIABLES
	 * Metodo que modela la transaccion que agrega un cliente a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el cliente que entra como parametro <br/>
	 * @param cliente - el cliente a agregar. cliente != null
	 * @throws Exception - Cualquier error que se genere agregando al cliente
	 */
	public void addReserva(Reserva reserva) throws Exception 
	{
		DAOReserva daoReserva = new DAOReserva( );
		try
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoReserva.addReserva(reserva);
		}
		catch (SQLException sqlException){
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RF5
	//-----------------------------------------------------------------------------------------------------------------------
	/**
	 * Metodo que modela la transaccion que elimina de la base de datos a la reserva que entra por parametro. <br/>
	 * Solamente se actualiza si existe la reserva en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado la reserva que entra por parametro <br/>
	 * @param reserva - reserva a eliminar. reserva != null
	 * @throws Exception - Cualquier error que se genere eliminando la reserva.
	 */
	public void deleteReserva(Reserva reserva) throws Exception 
	{
		DAOReserva daoReserva = new DAOReserva( );
		try
		{
			this.conn = darConexion();
			daoReserva.setConn( conn );
			if(daoReserva.findReservaById(reserva.getId()) == null) {
				throw new Exception("No existe una reserva con ese identificador");
			}else {
				daoReserva.deleteReserva(reserva);
			}
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RF6
	//-----------------------------------------------------------------------------------------------------------------------
	/**
	 * Metodo que modela la transaccion que elimina de la base de datos a la oferta que entra por parametro. <br/>
	 * Solamente se actualiza si existe la oferta en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado la oferta que entra por parametro <br/>
	 * @param oferta - reserva a eliminar. oferta != null
	 * @throws Exception - Cualquier error que se genere eliminando la oferta.
	 */
	public void deleteOferta(Oferta oferta) throws Exception 
	{
		DAOOferta daoOferta = new DAOOferta( );
		try
		{
			this.conn = darConexion();
			daoOferta.setConn( conn );
			if(daoOferta.findOfertaById(oferta.getId()) == null) {
				throw new Exception("No existe una oferta con ese identificador");
			}else {
				daoOferta.deleteOferta(oferta);
			}
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOferta.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RF7
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que modela la transaccion que agrega en la base de datos a la reserva que entra por parametro. <br/>
	 * Solamente se actualiza si existe la reserva en la Base de Datos <br/>
	 * <b> post: </b> se ha agregado la reserva que entra por parametro <br/>
	 * @param reserva - reserva a agregar. reserva != null
	 * @throws Exception - Cualquier error que se genere agregando la reserva.
	 */
	public void addReservaColectiva(ReservaEjColectiva reserva, int num) throws Exception 
	{
		DAOReserva daoReserva = new DAOReserva( );
		try
		{
			this.conn = darConexion();
			daoReserva.setConn( conn );
			if(daoReserva.findReservaById(reserva.getIdCol()) == null) {
				throw new Exception("No existe una reserva con ese identificador");
			}else {
				daoReserva.addReservaColectiva(reserva, num);
			}
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RF8
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que modela la transaccion que elimina de la base de datos a la reserva que entra por parametro. <br/>
	 * Solamente se actualiza si existe la reserva en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado la reserva que entra por parametro <br/>
	 * @param reserva - reserva a eliminar. reserva != null
	 * @throws Exception - Cualquier error que se genere eliminando la reserva.
	 */
	public void deleteReservaColectiva(ReservaEjColectiva reserva) throws Exception 
	{
		DAOReserva daoReserva = new DAOReserva( );
		try
		{
			this.conn = darConexion();
			daoReserva.setConn( conn );
			if(daoReserva.findReservaById(reserva.getIdCol()) == null) {
				throw new Exception("No existe una reserva con ese identificador");
			}else {
				daoReserva.deleteReservaColectiva(reserva);
			}
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RF9
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que modela la transaccion que inhabilita a la oferta que entra por parametro. <br/>
	 * Solamente se actualiza si existe la oferta en la Base de Datos <br/>
	 * <b> post: </b> se ha cambiado el estado de la oferta que entra por parametro, y sus reservas se han reacomodado <br/>
	 * @param oferta - oferta a deshabilitar. oferta != null
	 * @throws Exception - Cualquier error que se genere inhabilitando la oferta.
	 */
	public void inhabilitarOferta(Oferta oferta) throws Exception 
	{
		DAOOferta daoOferta = new DAOOferta( );
		try
		{
			this.conn = darConexion();
			daoOferta.setConn( conn );
			if(daoOferta.findOfertaById(oferta.getId()) == null) {
				throw new Exception("No existe una reserva con ese identificador");
			}else {
				oferta.setEstado("deshabilitado");
				daoOferta.updateOferta(oferta);
			}
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOferta.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO Rf10
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que modela la transaccion que rehabilita a la oferta que entra por parametro. <br/>
	 * Solamente se actualiza si existe la oferta en la Base de Datos <br/>
	 * <b> post: </b> se ha cambiado el estado de la oferta que entra por parametro <br/>
	 * @param oferta - oferta a deshabilitar. oferta != null
	 * @throws Exception - Cualquier error que se genere rehabilitando la oferta.
	 */
	public void rehabilitarOferta(Oferta oferta) throws Exception 
	{
		DAOOferta daoOferta = new DAOOferta( );
		try
		{
			this.conn = darConexion();
			daoOferta.setConn( conn );
			if(daoOferta.findOfertaById(oferta.getId()) == null) {
				throw new Exception("No existe una reserva con ese identificador");
			}else {
				oferta.setEstado("habilitado");
				daoOferta.updateOferta(oferta);
			}
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOferta.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC1
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * El método retorna las ganancias del año para el proveedor
	 */
	public double getDineroAnhoProveedor(Long idProveedor) throws Exception 
	{
		DAOOperador daoOperador = new DAOOperador( );
		try
		{
			this.conn = darConexion();
			daoOperador.setConn( conn );
			if(daoOperador.findOperadorrById(idProveedor) == null) {
				throw new Exception("No existe un operador con ese identificador");
			}else {
				return daoOperador.gananciasOperadores(idProveedor);
			}
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC2 
	//-----------------------------------------------------------------------------------------------------------------------
	//TODO revisar entrega de String
	public ArrayList<Oferta> getOfertasPopulares() throws Exception 
	{
		DAOOferta daoOferta = new DAOOferta( );
		try
		{
			this.conn = darConexion();
			daoOferta.setConn( conn );
			return daoOferta.getOfertasPopulares();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOferta.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC3
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * El método retorna las ganancias del año para el proveedor
	 */
	public String getIndiceOcupacion(Long idAlojamiento) throws Exception 
	{
		DAOAlojamiento daoAlojamiento = new DAOAlojamiento( );
		try
		{
			this.conn = darConexion();
			daoAlojamiento.setConn( conn );
			if(daoAlojamiento.findAlojamientoById(idAlojamiento) == null) {
				throw new Exception("No existe un operador con ese identificador");
			}else {
				return daoAlojamiento.getIndiceOcupacion(idAlojamiento);
			}
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoAlojamiento.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC4
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * El método retorna las ganancias del año para el proveedor
	 */
	public ArrayList<Alojamiento> getViviendasEspecificaciones(String fechaI, String fechaF, ArrayList<String> servicios) throws Exception 
	{
		DAOAlojamiento daoAlojamiento = new DAOAlojamiento( );
		try
		{
			this.conn = darConexion();
			daoAlojamiento.setConn( conn );
			return daoAlojamiento.alojamientosFechaServicios(fechaI, fechaF, servicios);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoAlojamiento.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC5
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * 
	 */
	public String getUsoOperador(Long id) throws Exception 
	{
		DAOOperador daoOperador = new DAOOperador();
		try
		{
			this.conn = darConexion();
			daoOperador.setConn( conn );
			return daoOperador.getUso(id);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC6 
	//-----------------------------------------------------------------------------------------------------------------------

	public String getUsoCliente(Long id) throws Exception 
	{
		DAOCliente daoCliente = new DAOCliente();
		try
		{
			this.conn = darConexion();
			daoCliente.setConn( conn );
			return daoCliente.getUso(id);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC8
	//-----------------------------------------------------------------------------------------------------------------------

	public ArrayList<Cliente> getClientesFrecuentes(Long id) throws Exception 
	{
		DAOAlojamiento daoAlojamiento = new DAOAlojamiento();
		try
		{
			this.conn = darConexion();
			daoAlojamiento.setConn( conn );
			return daoAlojamiento.getClientesFrecuentes(id);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoAlojamiento.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}


	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC10-admin
	//-----------------------------------------------------------------------------------------------------------------------

	public ArrayList<Cliente> getConsumo(String fechaInicio, String fechaFin, Long idAlojamiento, String ordenamiento, String tipoOrd) throws Exception 
	{
		DAOCliente dao = new DAOCliente();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			return dao.getConsumoRFC10(fechaInicio, fechaFin, idAlojamiento, ordenamiento, tipoOrd);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC10-proveedor
	//-----------------------------------------------------------------------------------------------------------------------

	public ArrayList<Cliente> getConsumoParaProveedor(String fechaInicio, String fechaFin, Long idAlojamiento, String ordenamiento, String tipoOrd, Long idProveedor) throws Exception 
	{
		DAOCliente dao = new DAOCliente();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			return dao.getConsumoRFC10Prov(fechaInicio, fechaFin, idAlojamiento, ordenamiento, tipoOrd, idProveedor);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC11-admin
	//-----------------------------------------------------------------------------------------------------------------------

	public ArrayList<Cliente> getConsumoNoRes(String fechaInicio, String fechaFin, Long idAlojamiento, String ordenamiento, String tipoOrd) throws Exception 
	{
		DAOCliente dao = new DAOCliente();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			return dao.getConsumoRFC11(fechaInicio, fechaFin, idAlojamiento, ordenamiento, tipoOrd);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}
	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC11-proveedor
	//-----------------------------------------------------------------------------------------------------------------------

	public ArrayList<Cliente> getConsumoNoResProveedor(String fechaInicio, String fechaFin, Long idAlojamiento, String ordenamiento, String tipoOrd, Long idProveedor) throws Exception 
	{
		DAOCliente dao = new DAOCliente();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			return dao.getConsumoRFC11Prov(fechaInicio, fechaFin, idAlojamiento, ordenamiento, tipoOrd, idProveedor);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}



	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC13 - Clientes que reservan una vez al mes
	//-----------------------------------------------------------------------------------------------------------------------

	public ArrayList<Cliente> getClientesReservanMensual() throws Exception 
	{
		DAOCliente dao = new DAOCliente();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			return dao.getReservanCadaMes();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC13 - Clientes que reservan solo alojamientos costosos
	//-----------------------------------------------------------------------------------------------------------------------

	public ArrayList<Cliente> getClientesReservanCaro() throws Exception 
	{
		DAOCliente dao = new DAOCliente();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			return dao.getReservanCaro();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}
	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC13 - Clientes que reservan solo suites gomelas
	//-----------------------------------------------------------------------------------------------------------------------

	public ArrayList<Cliente> getClientesReservanSuites() throws Exception 
	{
		DAOCliente dao = new DAOCliente();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			return dao.getReservanSuites();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}


	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC12 - OfertasMasOcupadasPorSemana
	//-----------------------------------------------------------------------------------------------------------------------

	public Oferta[] getOfertasMasOcupacionSemanal() throws SQLException {
		DAOOferta dao = new DAOOferta();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			return dao.getOfertaMasOcupada();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC12 - OfertasMenosOcupadasPorSemana
	//-----------------------------------------------------------------------------------------------------------------------

	public Oferta[] getOfertasMenosOcupacionSemanal() throws SQLException {
		DAOOferta dao = new DAOOferta();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			return dao.getOfertaMenosOcupada();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC12 - Operador mas solicitado por semana
	//-----------------------------------------------------------------------------------------------------------------------

	public Operador[] getOperadorMasSolicitadoSemanal() throws SQLException {
		DAOOperador dao = new DAOOperador();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			return dao.getMasSolicitado();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------
	//TODO RFC12 - Operador menos solicitado por semana
	//-----------------------------------------------------------------------------------------------------------------------

	public Operador[] getOperadorMenosSolicitadoSemanal() throws SQLException {
		DAOOperador dao = new DAOOperador();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			return dao.getMenosSolicitado();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
}
