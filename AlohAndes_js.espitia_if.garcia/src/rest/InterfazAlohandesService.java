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
	@Path("{idAlojamiento:[0-9]}/{fechaI:[0-3][0-9]-[0-1][0-9]-[0-9][0-9][0-9][0-9]}/to/{fechaF:[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]}/ord/{order:[a-z]}/{tipoOrd:[a-s]}/{group:[a-z]}")
	public Response rfc10(@PathParam("idAlojamiento") Long idAlojamiento,
			@PathParam("fechaI") String fechaInicio,
			@PathParam("fechaF") String fechaFin,  
			@PathParam("order") String ordenamiento,
			@PathParam("tipoOrd") String tipoOrd,
			@PathParam("group") String agrupamiento) {

		// TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos
		// anteriores y utilizando el Transaction Manager de Parranderos
		try {
			if(tipoOrd.equals("no")) { tipoOrd = null; }
			if(agrupamiento.equals("no")) { agrupamiento = null; }
			if(ordenamiento.equals("no")) { ordenamiento = null; }
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			ArrayList<Cliente> cl = tm.getConsumo(fechaInicio, fechaFin, idAlojamiento, ordenamiento, tipoOrd, agrupamiento);
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}

	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("RFC10/{idAlojamiento:[0-9]}/{fechaI:[0-3][0-9]-[0-1][0-9]-[0-9][0-9][0-9][0-9]}/to/{fechaF:[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]}/ord/{order:[a-z]}/{tipoOrd:[a-s]}/{group:[a-z]}/{operador:[0-9]}")
	public Response rfc10Prov(@PathParam("idAlojamiento") Long idAlojamiento,
			@PathParam("fechaI") String fechaInicio,
			@PathParam("fechaF") String fechaFin,  
			@PathParam("order") String ordenamiento,
			@PathParam("tipoOrd") String tipoOrd,
			@PathParam("group") String agrupamiento, 
			@PathParam("idOperador") Long idOperador) {

		// TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos
		// anteriores y utilizando el Transaction Manager de Parranderos
		try {
			if(tipoOrd.equals("no")) { tipoOrd = null; }
			if(agrupamiento.equals("no")) { agrupamiento = null; }
			if(ordenamiento.equals("no")) { ordenamiento = null; }
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			ArrayList<Cliente> cl = tm.getConsumoParaProveedor(fechaInicio, fechaFin, idAlojamiento, ordenamiento, tipoOrd, agrupamiento, idOperador);
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}

	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("RFC11/{idAlojamiento:[0-9]}/{fechaI:[0-3][0-9]-[0-1][0-9]-[0-9][0-9][0-9][0-9]}/to/{fechaF:[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]}/ord/{order:[a-z]}/{tipoOrd:[a-s]}/{group:[a-z]}")
	public Response rfc11(@PathParam("idAlojamiento") Long idAlojamiento,
			@PathParam("fechaI") String fechaInicio,
			@PathParam("fechaF") String fechaFin,  
			@PathParam("order") String ordenamiento,
			@PathParam("tipoOrd") String tipoOrd,
			@PathParam("group") String agrupamiento) {

		// TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos
		// anteriores y utilizando el Transaction Manager de Parranderos
		try {
			if(tipoOrd.equals("no")) { tipoOrd = null; }
			if(agrupamiento.equals("no")) { agrupamiento = null; }
			if(ordenamiento.equals("no")) { ordenamiento = null; }
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			ArrayList<Cliente> cl = tm.getConsumoNoRes(fechaInicio, fechaFin, idAlojamiento, ordenamiento, tipoOrd, agrupamiento);
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}

	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("RFC11/{idAlojamiento:[0-9]}/{fechaI:[0-3][0-9]-[0-1][0-9]-[0-9][0-9][0-9][0-9]}/to/{fechaF:[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]}/ord/{order:[a-z]}/{tipoOrd:[a-s]}/{group:[a-z]}/{idOperador:[0-9]}")
	public Response rfc11Prov(@PathParam("idAlojamiento") Long idAlojamiento,
			@PathParam("fechaI") String fechaInicio,
			@PathParam("fechaF") String fechaFin,  
			@PathParam("order") String ordenamiento,
			@PathParam("tipoOrd") String tipoOrd,
			@PathParam("group") String agrupamiento,
			@PathParam("idOperador") Long idOperador) {

		// TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos
		// anteriores y utilizando el Transaction Manager de Parranderos
		try {
			if(tipoOrd.equals("no")) { tipoOrd = null; }
			if(agrupamiento.equals("no")) { agrupamiento = null; }
			if(ordenamiento.equals("no")) { ordenamiento = null; }
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			ArrayList<Cliente> cl = tm.getConsumoNoResProveedor(fechaInicio, fechaFin, idAlojamiento, ordenamiento, tipoOrd, agrupamiento, idOperador);
			
			return Response.status(200).entity(cl).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
}

