import java.net.*;
import java.io.*;
/**
 * Servidor TCP.
 * @author AVMD 2012
 * @version 1.0
 */
public class servidortcp {
   public static void main(String argv[]) {
/**
* Abre el canal de comunicación
*/
      ServerSocket socket;
      boolean fin = false;
	boolean hola=false;
	boolean peti=true;
	boolean macb=true;
	boolean quij=true;
	int peti=3;
	int quij=3;
	int macb=3;

      try {
/**
* Publicar en la red la dirección del 
* canal de comunicación
* apertura del socket
*/
      socket = new ServerSocket(6001);
/**
* Espera peticiones de conexión de los clientes
* y recepciona las entradas que envien los clientes 
* conectados.
*/   
      Socket socket_cli = socket.accept();
         DataInputStream in =
            new DataInputStream(socket_cli.getInputStream());
         DataOutputStream out =
            new DataOutputStream(socket_cli.getOutputStream());

/*
* A la espera a recibir solicitudes.
* Recepción de datos. 
* Según lo que llegue aparece el mensaje correspondiente
* Y cuando llega "FIN" acaba la ejecución.
*/
	do {
            String mensaje ="";
            mensaje = in.readUTF();
	      System.out.println(mensaje);
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
			    peti--;			
			    if (peti<0) {
				peti=0;
				mensaje="PETI: ERROR 3";
				}
		      }else
			  if (mensaje.startsWith("MACB")&& macb) {
			     macb=false;
			     mensaje="MACB: RESERVADO";
			     macb--;
			    if (macb<0) {
				macb=0;
				mensaje="MACB: ERROR 3";
				}
			  }else
			    if (mensaje.startsWith("QUIJ")&& quij) {
			       quij=false;
			       mensaje="QUIJ: RESERVADO";
				 quij--;
			    if (quij<0) {
				quij=0;
				mensaje="QUIJ: ERROR 3";
				}
			    }else
			      if (mensaje.startsWith("RPETI")&& !peti) {
			          peti=true;
				    mensaje="RPETI: DEVUELTO";
     				    quij++;
		            }else
			        if (mensaje.startsWith("RMACB")&& !macb) {
				     macb=true;
				     mensaje="RMACB: DEVUELTO";
				     macb++;
			        }else
			          if (mensaje.startsWith("RQUIJ")&& !quij) {
				       quij=true;
				       mensaje="RQUIJ: DEVUELTO";
					 quij++;
			          }else							
			            if (mensaje.startsWith("LIST")) {
				          mensaje="PETI "+peti+"; MACB"+macb+"; QUIJ "+quij;
			            }else					  
			     	      {   mensaje=mensaje+": ERROR";}
		
		}
/*
*aqui se envia el mensaje de vuelta
*/
		System.out.println(mensaje);
		out.writeUTF(mensaje);

        } while (!fin);
//cierre de socket. Por si acaso.
        socket.close();
	  socket_cli.close();
/*
* Salida de mensajes de error que se produzcan durante la 
* ejecución. 
*/
      } catch (Exception e) {
         System.err.println(e.getMessage());
         System.exit(1);
      }
   }
