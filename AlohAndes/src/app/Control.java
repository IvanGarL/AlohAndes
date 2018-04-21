package app;

import java.util.Scanner;

import tm.AlohAndesTransactionManager;

public class Control {

	private static AlohAndesTransactionManager tm = new AlohAndesTransactionManager("/WebContent/WEB-INF/ConnectionData");

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		boolean fin=false;
		while(!fin){
			printMenu();
			int option = sc.nextInt();
			switch(option){
			case 1:
				registroOperador(sc);
				break;
			case 2:

				break;
			case 3:

				break;
			case 4:

				break;
			case 5:

				break;
			case 6:

				break;
			case 7:

				break;
			case 8:

				break;
			case 9:	
				fin=true;
				break;
			}


		}
		sc.close();
	}



	private static void registroOperador(Scanner sc) {
		System.out.println("Elija el tipo del nuevo operador");
		System.out.println("1. Hotel");
		System.out.println("2. Hostal");
		System.out.println("3. Persona natural");
		System.out.println("4. Empresa de vivienda universitaria");
		System.out.println("5. Persona de la comunidad");
		int opcion = sc.nextInt();
		switch (opcion) {
		case 1:
			System.out.println("Ingrese la informacion del hotel en el siguiente formato");
			System.out.println();
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		case 4:
			
			break;
		case 5:
			
			break;
		}
	}



	private static void printMenu() {
		System.out.println("------------------------------------------------");
		System.out.println("AlohAndes");
		System.out.println("¿Que desea hacer?");
		System.out.println("------------------------------------------------");
		System.out.println("1. Registrar un operador");
		System.out.println("2. Registrar un usuario");
		System.out.println("3. Habilitar/deshabilitar una oferta");
		System.out.println("4. Registrar una nueva reserva");
		System.out.println("5. Cancelar una reserva");
		System.out.println("6. Registrar una propuesta de alojamiento");
		System.out.println("7. Retirar una propuesta de alojamiento");
		System.out.println("8. Consultas");
		System.out.println("9. Salir");
	}
}
