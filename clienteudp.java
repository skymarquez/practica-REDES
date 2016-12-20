import java.net.*;
import java.io.*;
/**
 * Cliente UDP.
 * @author AVMD 2012
 * @version 1.0
 */
public class clienteudp {
   public static void main(String argv[]) {
      if (argv.length == 0) {
         System.err.println("java clienteudp servidor");
         System.exit(1);
      }

      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

      DatagramSocket socket;
      InetAddress address;
      byte[] mensaje_bytes = new byte[256];
      String mensaje="";
	String mensaje2="";
	DatagramPacket paquete;
	DatagramPacket paquete2;

      mensaje_bytes = mensaje.getBytes(); 

      try {
/**
* Instancia de la clase DatagramSocket que pone a la
* escucha el puerto UDP 
*/
         socket = new DatagramSocket();
         
         address=InetAddress.getByName(argv[0]);

         do {
/*
* Lectura de la entrada de datos.
*/   
           mensaje = in.readLine();
           mensaje_bytes = mensaje.getBytes(); 
	
/**
* Instancia de la clase DatagramPacket para enviar y recibir mensajes
* que van el el array que pasa como parámetro junto a la longitud máxima.
* En este caso es de envio ya que además lleva la InetAdress y el puerto
* de escucha.
*/

paquete = new 
DatagramPacket(mensaje_bytes,mensaje.length(),address,6000);

/*
* Envio de datos hasta que llegue
* el mensaje "FIN" que termina la ejecución.
*/   
     	     socket.send(paquete);
/*
* Rececpión de datos 
*/   
           byte[] mensaje_bytes2 = new byte[256];
           paquete2 = new DatagramPacket(mensaje_bytes2,256);
	     socket.receive(paquete2);
           mensaje2="";
	     mensaje2= new String(mensaje_bytes2);
	     System.out.println(mensaje2);

         } while (!mensaje.startsWith("FIN"));
      }
      catch (Exception e) {
         System.err.println(e.getMessage());
         System.exit(1);
      }
   }
}
