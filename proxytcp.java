import java.net.*; //Importa la libreria java.net para poder usar las clases Socket y ServerSocket e InetAddress
import java.io.*; //Para usar las clases que nos permiten manejar los flujos de datos de entrada y salida

/**
 * Servidor Proxy TCP.
 * @author AVMD 2012
 * @version 1.0
 */

public class proxytcp {
	/**
	 * Metodo main. Hay que indicar la direccion del servidor como parametro
	 */
	public static void main(String argv[]) {
		if (argv.length == 0) {
			System.err.println("java proxy servidor");
			System.exit(1);
		}
		
		//Declaracion e inicializacion del flujo de entrada convirtiendo los Bytes recibidos en cadenas de caracteres.         
		Socket socketServ; //Declara un objeto de tipo socket para comunicarse con el servidor.
		Socket socket_cli;
		ServerSocket socketCli; //Declara un objeto de tipo socket para comunicarse con el cliente.
		InetAddress address; //Declara address como un objeto que almacenara una direccion IP.
		String mensajeIn=""; //Inicializa el mensaje que se recibe del cliente como una cadena vacia
		String mensajeOut=""; //Inicializa el mensaje que se envia al servidor del como una cadena vacia
		String[] comandos;
		String comando = "";
		String param = "";
		String paramResp = "";
		String cadResp = "";
		DataInputStream inCli;
		DataOutputStream outCli;

		
		ServerSocket socket; // Declaracion de un objeto ServerSocket.
		boolean fin = false; // Inicializa fin a falso para controlar el fin de la ejecucion cuando se recibe el comando FIN.
		boolean finDelTodo = false;
		boolean saludo = false; //Inicializa saludos a falso para saber si se ha recibido el comando HOLA.
		try {
			/*
			*Convierte el nombre del servidor que se ha pasado como parametro 
			*a un objeto InetAddress que contiene la direccion IP a la que corresponde el servidor.
			*/
			address=InetAddress.getByName(argv[0]); 
			/*
			*Creacion del socket hacia el servidor mediante el constructor de 
			*la clase Socket asignandole la direccion IP address y el puerto TCP 6001
			*/
			socketServ = new Socket(address,6001); 

			/*
			*Asocia al objeto in el flujo de datos que llegan desde el servidor.
			*/
			DataInputStream inServ = new DataInputStream(socketServ.getInputStream()); 
			/*
			*Asocia out al flujo de datos que se envian al servidor.
			*/
			DataOutputStream outServ = new DataOutputStream(socketServ.getOutputStream()); 

			/*
			*Creacion del socket hacia el cliente asociado al puerto 6002.
			*/
			socketCli = new ServerSocket(6002); 

			
			
			//Saluda al servidor
			System.out.println("HOLA");
			outServ.writeUTF("HOLA");
			mensajeOut = inServ.readUTF();
			System.out.println(mensajeOut);
			


			/**
			* Bucle principal en el que se van leyendo los mensajes recibidos y se muestran por pantalla.
			* Acaba cuando llega el comando FIN.
			*/
			do {			
				//Detiene la ejecucion hasta que llega una peticion de conexion entrante y la acepta.
				socket_cli = socketCli.accept(); 
				
				//Asocia al objeto inCli el flujo de datos que llegan desde el cliente.
				inCli = new DataInputStream(socket_cli.getInputStream()); 

				//Asocia outCli al flujo de datos que se envian al cliente.
				outCli = new DataOutputStream(socket_cli.getOutputStream()); 
				
				saludo = false;
				fin = false;
				total = 0;
				parcial = 0;
				
					do {
						//Inicializa las variables y el mensaje borrando el contenido anterior.
						mensajeIn = ""; 
						mensajeOut = ""; 
		
						//Obtiene un mensaje leyendo del flujo de datos de entrada empleando codificacion UTF.
						mensajeIn = inCli.readUTF(); 
						//Muestra el mensaje obtenido en pantalla.
						if (mensajeIn != null) System.out.println(mensajeIn); 
						comandos = mensajeIn.split("\\s");
						if (comandos.length > 1) {
							comando = comandos[0];
							param = comandos[1].trim();
						} else {
							comando = mensajeIn;
							param = "0";
						}

						//Cuando el mensaje comienza por FIN acaba la ejecucion.
						if (comando.startsWith("FIN")) { 
							System.out.println("FIN");
							outCli.writeUTF("FIN");
							fin=true;
						// Cierra el canal del entrada desde el cliente
							inCli.close();  
						// Cierra el canal del salida hacia el cliente
							outCli.close(); 
						// Cierra el socket hacia el cliente
							socket_cli.close(); 
						//Con el comando FIN se paran el cliente, el proxy y el servidor.
							if (param.startsWith("FIN")) {  
								outServ.writeUTF(param);
								inServ.close();
								outServ.close();
								socketServ.close();
								finDelTodo = true;
							}
							
						} else if (comando.startsWith("HOLA")){
							saludo=true;
							System.out.println("Bienvenido");
							outCli.writeUTF("Bienvenido");
						} else if (!saludo) {
							System.out.println("ERROR: HOLA necesario");
							outCli.writeUTF("ERROR: HOLA necesario");
						} else if (comando.startsWith("LIST")){
							outServ.writeUTF(comando);
							mensajeOut = inServ.readUTF();
							System.out.println(mensajeOut);
							outCli.writeUTF(mensajeOut);
							mensajeOut="";

							
		
						} else { // Se envia el comando al servidor y la respuesta al cliente.
							outServ.writeUTF(comando);
							mensajeOut = inServ.readUTF();
							System.out.println(mensajeOut);
							paramResp = mensajeOut.split("\\s")[1];
							if (paramResp.contains("ERROR")) {
								System.out.println(mensajeIn + ":" + " ERROR");
								outCli.writeUTF(mensajeIn + ":" + " ERROR");
							} else {																
								System.out.println(mensajeIn);
								outCli.writeUTF(mensajeIn);
							}
		
						} 
						
					} while (!fin); 


			} while (!finDelTodo);
		}
		/**
		* Este bloque se ejecuta en caso de que se produzca algun error en el bloque try.
		* Muestra el error y finaliza la ejecución.
		*/
		catch (Exception e) {
			System.err.println(e.getMessage()); 
			System.exit(1); 
		}
	}
}