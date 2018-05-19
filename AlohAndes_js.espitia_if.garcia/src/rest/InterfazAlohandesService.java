package rest;

import javax.servlet.ServletContext;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohAndesTransactionManager;
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
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addOperador(Operador operador) {

		// TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos
		// anteriores y utilizando el Transaction Manager de Parranderos
		try {
			AlohAndesTransactionManager tm = new AlohAndesTransactionManager(getPath());
			tm.addOperador(operador);

			return Response.status(200).entity(operador).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}

	}
	
	
}
