package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ViviendaComunidad extends Alojamiento{

	
	@JsonProperty(value="menaje")
	private Boolean menaje;
	
	@JsonProperty(value="diasUso")
	private Integer diasUso;
	
	@JsonProperty(value="numHabitaciones")
	private Integer numHabitaciones;
	
}
