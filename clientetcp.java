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
* Abre el canal de comunicación.
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
* Creación de un stream(canal) de entrada y de salida con 
* la información que se envia y recibe del servidor.
*/
         DataOutputStream out =
            new DataOutputStream(socket.getOutputStream());
	   DataInputStream inSer =
            new DataInputStream(socket.getInputStream());


/*
* Lectura de la entrada de datos.
* Envio y recepción de datos hasta que llegue
* el mensaje "FIN" que termina la ejecución.
*/   
       do {
            mensaje = in.readLine();          
/*
* aquí va el mensaje enviado
*/

		out.writeUTF(mensaje);	
/*
* aquí viene el mensaje recibido
*/
		String mensaje2 ="";
           	mensaje2 = inSer.readUTF();
      	System.out.println(mensaje2);

         } while (!mensaje.startsWith("FIN"));
	socket.close();
      }
/*
* Salida de mensajes de error que se produzcan durante la 
* ejecución. 
*/
      catch (Exception e) {
         System.err.println(e.getMessage());
         System.exit(1);
      }
   }
}
