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

	private static void printMenu() {
		// TODO Auto-generated method stub
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
