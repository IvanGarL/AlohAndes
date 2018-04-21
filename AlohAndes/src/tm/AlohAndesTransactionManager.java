package tm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

	/**
	 * Constatne que representa el numero maximo de Bebedores que pueden haber en una ciudad
	 */
	private final static Integer CANTIDAD_MAXIMA = 345;

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
		System.out.println("[PARRANDEROS APP] Attempting Connection to: " + url + " - By User: " + user);
		return DriverManager.getConnection(url, user, password);
	}


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS TRANSACCIONALES
	//----------------------------------------------------------------------------------------------------------------------------------

	//-----------------------------------------------------------------------------------------------------------------------
	//RF1
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
	//RF2
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
	//RF3
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
	//RF4
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
			//TODO: Llamar el metodo correspondiente
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
	//RF5
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
	//RF7
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
	//RF8
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
	//RFC1
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
	//RFC2
	//-----------------------------------------------------------------------------------------------------------------------

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
	//RFC3
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
	//RFC5
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * 
	 */
	public ArrayList<String> getUsoAlohandes() throws Exception 
	{
		DAOCliente daoCliente = new DAOCliente();
		try
		{
			this.conn = darConexion();
			daoCliente.setConn( conn );
			return daoCliente.getUso();
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
	//RF7
	//-----------------------------------------------------------------------------------------------------------------------

	/**
	 * 
	 */
	public void addReservaColectiva(int num, String tipo) throws Exception 
	{
		DAOCliente daoCliente = new DAOCliente();
		try
		{
			this.conn = darConexion();
			daoCliente.setConn( conn );
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
}
