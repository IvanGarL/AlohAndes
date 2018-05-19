package PobladorDeBD;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CSV {
	public static void main(String[]args) throws FileNotFoundException{
	
		tablaReservas();
		
    }
	
	public static String nombreAleatorio(){
		String[] nombres = {"Daniel", "Fidel", "Ferran", "Alejandro", "Víctor", "Mariano", "Abel", "Jonatan", 
				"José", "Eva", "Mariana", "Camila", "Juliana", "Jessica", "José", "Albert",
				"Viviana", "Camilo", "Adolfo", "Guillermo", "Edgar", "Josefina", "Juan", "Nicolás", 
				"Luis", "Santiago", "David", "Samuel", "Rut", "Juana", "Jean Paul", "Carla",
				"Carlos", "Laura", "Yolanda", "Sol", "Flor", "Luna", "Natalia", "Daniela"};
		String[] apellidos = {"Arcadio Buendía", "Ortiz", "Espitia", "Lopez", "Martinez", "García",
				"Castro", "Laverde", "Rodriguez", "Santamaría", "Peñaloza", "Montaño",
				"Alfonso", "Nieto", "Puertas", "Muñoz", "Acero", "Saenz",
				"Reyes", "Gutierres", "Guzman", "Avila", "Castillo", "Romero",
				"Velasco", "Bejarano", "Gaviria", "Arias"};
		int random1 = (int) ((Math.random()*39));
		int random2 = (int) ((Math.random()*27));
		
		String letras = "ABCDEFGHIJLMNOPQRSTUVYZ";
		char al = letras.charAt((int) (Math.random() * (letras.length()-1)));
		char bl = letras.charAt((int) (Math.random() * (letras.length()-1)));

		return nombres[random1] + " " + al + " "+ apellidos[random2] + " " + bl;
		
	}
	
	public static String cedulaAleatoria(){
		String num = "";
		for(int i = 0; i < 10; i++){
			num += String.valueOf((int) (Math.random()*9));
		}
		return num;
	}
	
	public static String numeroTelefonoAleatorio(){
		String num = "";
		for(int i = 0; i < 9; i++){
			num += String.valueOf((int) (Math.random()*9));
		}
		return num;
	}
	
	public static String edadAletatoria(){
		
		return String.valueOf((int) (Math.random()*5 + 2)) + String.valueOf((int) (Math.random()*9));
	}
	
	public static String fechaFinAleatoria(){
		
		
		int alA = (int) ((Math.random()*6)+6),  alB = (int) ((Math.random()*30) +1);
		
		
		String dia = String.valueOf(alB);
		String mes = String.valueOf(alA);
		String anio = "2018";
		return dia + "/" + mes + "/" + anio;
	}
	
	public static String fechaInicioAleatoria(){
		String[] inicio = {"2016", "2017"};
		
		
		int alA = (int) ((Math.random()*2)), alB = (int) ((Math.random()*11)+1),  alC = (int) ((Math.random()*30) +1);
		
		String dia = String.valueOf(alC);
		String mes = String.valueOf(alB);
		String anio = inicio[alA];

		return dia + "/" + mes + "/" + anio;
	}
	
	public static String cobroAleatorio() {
		int[] costos = {15500, 24300, 12100, 37600, 50700, 27500};
		return String.valueOf(costos[(int) ((Math.random()*6))]);
	}
	
	public static String capacidadAleatoria() {
		int[] costos = {50, 240, 1200, 600, 75, 270, 150, 138, 245};
		return String.valueOf(costos[(int) ((Math.random()*9))]);
	}
	
	public static String estadoAleatorio() {
		String[] estados = {"reservado", "activo", "cancelado", "terminado"};
		return estados[(int) ((Math.random()*4))];
	}
	
	public static String tipoOperadorAleatorio(){
		String[] tipos = {"hostal", "hotel", "pernat", "percom", "vivuni"};
		return tipos[(int) ((Math.random()*5))];
	}
	
	public static String fechaRealizacionAleatoria(){
		String[] realizacion = {"2017", "2018"};
		
		
		int alA = (int) ((Math.random()*2)), alB = (int) ((Math.random()*4)+1),  alC = (int) ((Math.random()*30) +1);
		
		String dia = String.valueOf(alC);
		String mes = String.valueOf(alB);
		String anio = realizacion[alA];

		return dia + "/" + mes + "/" + anio;
	}
	
	public static void tablaUsuarios() throws FileNotFoundException{

        PrintWriter pw = new PrintWriter(new File("tablaUsuarios.csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("CONTRASENIA");
        sb.append(';');
        sb.append("TIPO");
        sb.append(';');
        sb.append("LOGIN");
        sb.append('\n');
        
        int i = 0;
        for(i = 0; i < 50000; i++){
	        sb.append("contrasenia" + i);
	        sb.append(';');
	        if((i % 1000) == 0) sb.append("responsable");
	        else sb.append("cliente");
	        sb.append(';');
	        sb.append(nombreAleatorio());
	        sb.append('\n');
        }
        pw.write(sb.toString());
        pw.close();
        System.out.println("done Usuarios!");
	}
	
	public static void tablaClientes() throws FileNotFoundException{
		 PrintWriter pw2 = new PrintWriter(new File("C:\\Users\\IvánFelipe\\Documents\\SISTRANS\\ITERACION III\\Tablas csv\\tablaClientes.csv"));
	        StringBuilder sb = new StringBuilder();
	        sb.append("CEDULA");
	        sb.append(';');
	        sb.append("NOMBRE");
	        sb.append(';');
	        sb.append("EDAD");
	        sb.append(';');
	        sb.append("TELEFONO");
	        sb.append('\n');
	        
	        int i = 0;
	        for(i = 0; i < 49949; i++){
		        sb.append(cedulaAleatoria());
		        sb.append(';');
		        sb.append(nombreAleatorio());
		        sb.append(';');
		        sb.append(edadAletatoria());
		        sb.append(';');
		        sb.append(numeroTelefonoAleatorio());
		        sb.append('\n');
	        }
	        pw2.write(sb.toString());
	        pw2.close();
	        System.out.println("done Clientes!");
	}
	
	public static void tablaResponsables() throws FileNotFoundException{
		 PrintWriter pw2 = new PrintWriter(new File("C:\\Users\\IvánFelipe\\Documents\\SISTRANS\\ITERACION III\\Tablas csv\\tablaResponsables.csv"));
	        StringBuilder sb = new StringBuilder();
	        sb.append("CEDULA");
	        sb.append(';');
	        sb.append("NOMBRE");
	        sb.append(';');
	        sb.append("EDAD");
	        sb.append(';');
	        sb.append("TELEFONO");
	        sb.append('\n');
	        
	        int i = 0;
	        for(i = 0; i < 50; i++){
		        sb.append(cedulaAleatoria());
		        sb.append(';');
		        sb.append(nombreAleatorio());
		        sb.append(';');
		        sb.append(edadAletatoria());
		        sb.append(';');
		        sb.append(numeroTelefonoAleatorio());
		        sb.append('\n');
	        }
	        pw2.write(sb.toString());
	        pw2.close();
	        System.out.println("done Responsables!");
	}
	
	public static void tablaReservas() throws FileNotFoundException{
		 PrintWriter pw2 = new PrintWriter(new File("C:\\Users\\IvánFelipe\\Documents\\SISTRANS\\ITERACION III\\Tablas csv\\tablaReservas.csv"));
	        StringBuilder sb = new StringBuilder();
	        sb.append("COBRO");
	        sb.append(';');
	        sb.append("ESTADO");
	        sb.append(';');
	        sb.append("FECHAFIN");
	        sb.append(';');
	        sb.append("FECHAINICIO");
	        sb.append(';');
	        sb.append("FECHAREALIZACION");
	        sb.append(';');
	        sb.append("IDCLIENTE");
	        sb.append(';');
	        sb.append("COLECTIVA");
	        sb.append('\n');
	        
	        int i = 0;
	        for(i = 50042; i < 100039; i++){
	        	if(((i % 7 == 0) || (i % 3 == 0) )&& !(String.valueOf(i).endsWith("41"))){
	        		sb.append(cobroAleatorio());
	    	        sb.append(';');
	    	        sb.append(estadoAleatorio());
	    	        sb.append(';');
	    	        sb.append(fechaFinAleatoria());
	    	        sb.append(';');
	    	        sb.append(fechaInicioAleatoria());
	    	        sb.append(';');
	    	        sb.append(fechaRealizacionAleatoria());
	    	        sb.append(';');
	    	        sb.append(i);
	    	        sb.append(';');
	    	        sb.append((int)(Math.random()*2));
	    	        sb.append('\n');
	        	}
	        }
	        pw2.write(sb.toString());
	        pw2.close();
	        System.out.println("done Reservas!");
	}
	
	public static void tablaOperadores() throws FileNotFoundException{
		 PrintWriter pw2 = new PrintWriter(new File("C:\\Users\\IvánFelipe\\Documents\\SISTRANS\\ITERACION III\\Tablas csv\\tablaOperadores.csv"));
	        StringBuilder sb = new StringBuilder();
	        sb.append("CAPACIDAD");
	        sb.append(';');
	        sb.append("NOMBRE");
	        sb.append(';');
	        sb.append("TELEFONO");
	        sb.append(';');
	        sb.append("TIPO");
	        sb.append(';');
	        sb.append("IDRESPONSABLE");
	        sb.append('\n');
	        
	        int i = 0;
	        for(i = 50041; i < 99042; i+=1000){
		        sb.append(capacidadAleatoria());
		        sb.append(';');
		        sb.append(nombreAleatorio());
		        sb.append(';');
		        sb.append(numeroTelefonoAleatorio());
		        sb.append(';');
		        sb.append(numeroTelefonoAleatorio());
		        sb.append('\n');
	        }
	        pw2.write(sb.toString());
	        pw2.close();
	        System.out.println("done Clientes!");
	}
}
