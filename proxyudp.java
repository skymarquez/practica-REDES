import java.net.*;
import java.io.*;
/**
 * Proxy UDP.
 * @author AVMD 2012
 * @version 1.0
 */

public class proxyudp {

  public static void main(String argv[]) {
    DatagramSocket socket = null; // Socket
    DatagramSocket socketToServer = null; // Socket
    DatagramPacket paquete = null; // Paquete

    try {
      socket = new DatagramSocket(6001);
      do
        while (true) {
          paquete = herra.receivePacket(socket);
          herra.sendPacket(socket, paquete.getAddress(), paquete.getPort(), "");
          InetAddress serverAddress = 
			InetAddress.getByName(herra.getData(paquete));
          if (socketToServer == null) {
            socketToServer = new DatagramSocket();
            socketToServer.setSoTimeout(herra.TIMEOUT);
          }
          String message = "";
          try {    
            do {
              message="";
              String messageOut = "";
              paquete = herra.receivePacket(socket);
              InetAddress clientAddress = paquete.getAddress();
              int portClient = paquete.getPort();
              message = herra.getData(paquete);
              util.sendPacket(socket, clientAddress, portClient,messageOut);
            } while (!message.equals("FIN"));
          } catch (SocketTimeoutException e) {}
        } while (true);
    } catch (IOException e) {
      System.err.println("Error en Entrada/Salida del socket: "
          + e.getMessage());
      System.exit(1);
    } catch (SecurityException e) {
      System.err.println("Error de Seguridad: " + e.getMessage());
      System.exit(1);
    } finally {
      try {
        socket.close();
        socketToServer.close();
      } catch (Exception e) {
        System.err.println("Error cerrando sockets: " + e.getMessage());
        System.exit(1);
      }
    }
  }
}
