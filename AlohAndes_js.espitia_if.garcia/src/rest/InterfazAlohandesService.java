package rest;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohAndesTransactionManager;
import vos.Cliente;
import vos.Oferta;
import vos.Operador;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 * 
 * @author IvánFelipe
 *
 */

@Path("alohandes")
public class InterfazAlohandesService{

	
	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}

	private String doErrorMessage(Exception e) {
		return "{ \"ERROR---\": \"" + e.getMessage() + "\"}";
	}
	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response addOperador(Operador operador) {
//
//		// TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos
//		// anteriores y utilizando el Transaction Manager de Parranderos
//		try {
//			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
//			tm.addOperador(operador);
//
//			return Response.status(200).entity(operador).build();
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//
//	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/RFC10")
	public Response rfc10(@QueryParam("idAlojamiento") Long idAlojamiento,
			@QueryParam("fechaI") String fechaInicio,
			@QueryParam("fechaF") String fechaFin,  
			@QueryParam("order") String ordenamiento,
			@QueryParam("tipoOrd") String tipoOrd) {

		// TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos
		// anteriores y utilizando el Transaction Manager de Parranderos
		try {
			if(tipoOrd.equals("no")) { tipoOrd = null; }
			if(ordenamiento.equals("no")) { ordenamiento = null; }
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			ArrayList<Cliente> cl = tm.getConsumo(fechaInicio, fechaFin, idAlojamiento, ordenamiento, tipoOrd);
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}

	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/RFC10/{idOperador:[0-9]}")
	public Response rfc10Prov(@QueryParam("idAlojamiento") Long idAlojamiento,
			@QueryParam("fechaI") String fechaInicio,
			@QueryParam("fechaF") String fechaFin,  
			@QueryParam("order") String ordenamiento,
			@QueryParam("tipoOrd") String tipoOrd,
			@PathParam("idOperador") Long idOperador) {

		// TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos
		// anteriores y utilizando el Transaction Manager de Parranderos
		try {
			if(tipoOrd.equals("no")) { tipoOrd = null; }
			if(ordenamiento.equals("no")) { ordenamiento = null; }
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			ArrayList<Cliente> cl = tm.getConsumoParaProveedor(fechaInicio, fechaFin, idAlojamiento, ordenamiento, tipoOrd, idOperador);
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}

	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/RFC11")
	public Response rfc11(@PathParam("idAlojamiento") Long idAlojamiento,
			@PathParam("fechaI") String fechaInicio,
			@PathParam("fechaF") String fechaFin,  
			@PathParam("order") String ordenamiento,
			@PathParam("tipoOrd") String tipoOrd) {

		// TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos
		// anteriores y utilizando el Transaction Manager de Parranderos
		try {
			if(tipoOrd.equals("no")) { tipoOrd = null; }
			if(ordenamiento.equals("no")) { ordenamiento = null; }
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			ArrayList<Cliente> cl = tm.getConsumoNoRes(fechaInicio, fechaFin, idAlojamiento, ordenamiento, tipoOrd);
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}

	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/RFC11/{idOperador:[0-9]}")
	public Response rfc11Prov(@QueryParam("idAlojamiento") Long idAlojamiento,
			@QueryParam("fechaI") String fechaInicio,
			@QueryParam("fechaF") String fechaFin,  
			@QueryParam("order") String ordenamiento,
			@QueryParam("tipoOrd") String tipoOrd,
			@PathParam("idOperador") Long idOperador) {

		// TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos
		// anteriores y utilizando el Transaction Manager de Parranderos
		try {
			if(tipoOrd.equals("no")) { tipoOrd = null; }
			if(ordenamiento.equals("no")) { ordenamiento = null; }
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			ArrayList<Cliente> cl = tm.getConsumoNoResProveedor(fechaInicio, fechaFin, idAlojamiento, ordenamiento, tipoOrd, idOperador);
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("clientes/suites")
	public Response getGomelos() {

		// TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos
		// anteriores y utilizando el Transaction Manager de Parranderos
		try {
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			ArrayList<Cliente> cl = tm.getClientesReservanSuites();
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("clientes/cadaMes")
	public Response getFirmes() {

		try {
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			ArrayList<Cliente> cl = tm.getClientesReservanMensual();
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("clientes/costoso")
	public Response getLuqueados() {

		try {
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			ArrayList<Cliente> cl = tm.getClientesReservanCaro();
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ofertas/masOcupadaxsem")
	public Response getOfertaMasOcupacionSemanal() {

		try {
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			Oferta[] cl = tm.getOfertasMasOcupacionSemanal();
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ofertas/menosOcupadaxsem")
	public Response getOfertaMenosOcupacionSemanal() {

		try {
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			Oferta[] cl = tm.getOfertasMenosOcupacionSemanal();
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/operadores/masSolicitadoxsem")
	public Response getOperadorMasSolicitadoSemanal() {

		try {
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			Operador[] cl = tm.getOperadorMasSolicitadoSemanal();
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/operadores/menosSolicitadoxsem")
	public Response getOperadorMenosSolicitadoSemanal() {

		try {
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			Operador[] cl = tm.getOperadorMenosSolicitadoSemanal();
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
}

