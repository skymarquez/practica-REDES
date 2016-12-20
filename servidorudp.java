import java.net.*;
import java.io.*;
/**
 * Servidor UDP.
 * @author AVMD 2012
 * @version 1.0
 */
public class servidorudp {
   public static void main(String argv[]) {

      DatagramSocket socket;
      DatagramPacket paquete;
	String mensaje="";	
      boolean fin = false;
	boolean hola=false;
	boolean peti=true;
	boolean macb=true;
	boolean quij=true;

      try {
/**
* Instancia de la clase DatagramSocket que pone a la
* escucha el puerto UDP 6000
*/
       socket = new DatagramSocket(6000);

         do {

	 byte[] mensaje_bytes = new byte[256];

/**
* Instancia de la clase DatagramPacket para enviar y recibir mensajes
* que van el el array que pasa como parámetro junto a la longitud máxima.
* En este caso es de recepción ya que no lleva la InetAdress ni el puerto
* de escucha-
*/
paquete = new DatagramPacket(mensaje_bytes,256);

		
/*
* Recepción de datos hasta que llegue
* el mensaje "FIN" que termina la ejecución.
*/   

           socket.receive(paquete);

	     mensaje="";        
	     mensaje= new String(mensaje_bytes);

           System.out.println(mensaje);
//*********************
	      if (mensaje.startsWith("FIN")) {
	         fin=true;
	         mensaje="Adiós";
	      }else
	        if (!mensaje.startsWith("HOLA") && !hola) { 
			mensaje="ERROR: HOLA necesario";
		  }else
		    if (mensaje.startsWith("HOLA") && !hola) {
			  hola=true;
			  mensaje="Bienvenido";
		    }else {
		      if (mensaje.startsWith("PETI")&& peti) {
		          peti=false;
		          mensaje="PETI: RESERVADO";				
		      }else
			  if (mensaje.startsWith("MACB")&& macb) {
			     macb=false;
			     mensaje="MACB: RESERVADO";
			  }else
			    if (mensaje.startsWith("QUIJ")&& quij) {
			       quij=false;
			       mensaje="QUIJ: RESERVADO";
			    }else
			      if (mensaje.startsWith("RPETI")&& !peti) {
			          peti=true;
				    mensaje="RPETI: DEVUELTO";
		            }else
			        if (mensaje.startsWith("RMACB")&& !macb) {
				     macb=true;
				     mensaje="RMACB: DEVUELTO";
			        }else
			          if (mensaje.startsWith("RQUIJ")&& !quij) {
				       quij=true;
				       mensaje="RQUIJ: DEVUELTO";
			          }else							
			            if (mensaje.startsWith("LIST")) {
				          mensaje="PETI; MACB; QUIJ";
			            }else					  
			     	      { mensaje=mensaje.trim()+": ERROR";}
		}
/*
*aqui se envia el mensaje de vuelta
*/
		System.out.println(mensaje);
		mensaje_bytes=mensaje.getBytes();
		InetAddress address=paquete.getAddress();
		int port=paquete.getPort();

DatagramPacket paquete2 = new DatagramPacket(mensaje_bytes,mensaje.length(),address,port);		
		socket.send(paquete2);

//*********************
         } while (!fin);
      }
      catch (Exception e) {
         System.err.println(e.getMessage());
         System.exit(1);
      }
   }
}
