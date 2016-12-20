import java.net.*;
import java.io.*;
/**
 * Cliente TCP.
 * @author AVMD 2012
 * @version 1.0
 */
public class clientetcp {
   public static void main(String argv[]) {
      if (argv.length == 0) {
         System.err.println("java clientetcp servidor");
         System.exit(1);
      }

      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
/*
* Abre el canal de comunicaci�n.
*/   
      Socket socket;
      InetAddress address;
      byte[] mensaje_bytes = new byte[256];
      String mensaje="";

      try {

         address=InetAddress.getByName(argv[0]);

/*
* crea un socket de entrada/salida y lo
* conecta al puerto 6001 del servidor
*/

         socket = new Socket(address,6002);

/*
* Creaci�n de un stream(canal) de entrada y de salida con 
* la informaci�n que se envia y recibe del servidor.
*/
         DataOutputStream out =
            new DataOutputStream(socket.getOutputStream());
	   DataInputStream inSer =
            new DataInputStream(socket.getInputStream());


/*
* Lectura de la entrada de datos.
* Envio y recepci�n de datos hasta que llegue
* el mensaje "FIN" que termina la ejecuci�n.
*/   
       do {
            mensaje = in.readLine();          
/*
* aqu� va el mensaje enviado
*/

		out.writeUTF(mensaje);	
/*
* aqu� viene el mensaje recibido
*/
		String mensaje2 ="";
           	mensaje2 = inSer.readUTF();
      	System.out.println(mensaje2);

         } while (!mensaje.startsWith("FIN"));
	socket.close();
      }
/*
* Salida de mensajes de error que se produzcan durante la 
* ejecuci�n. 
*/
      catch (Exception e) {
         System.err.println(e.getMessage());
         System.exit(1);
      }
   }
}
