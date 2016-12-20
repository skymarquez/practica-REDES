import java.net.*;
import java.io.*;
/**
 * Herramienta UDP.
 * @author AVMD 2012
 * @version 1.0
 */


public class herra {

  public static final int TIMEOUT = 5000;

  public static String sendAndReceivePacket(DatagramSocket socket,
      InetAddress ipTo, int portTo, String message) throws SocketTimeoutException,IOException {
    DatagramPacket paquete;
      try {
        sendPacket(socket, ipTo, portTo, message);
        paquete = receivePacket(socket);
        return getData(paquete);
      } catch (SocketTimeoutException e) {
        System.out.println("ERROR ENVIO MENSAJE");
      }
   
  }

  public static void sendPacket(DatagramSocket socket, InetAddress ipTo,
      int portTo, String message) throws IOException {
    byte[] message_bytes = new byte[256];
    message_bytes = message.getBytes();
    DatagramPacket paquete = new DatagramPacket(message_bytes, message
        .length(), ipTo, portTo);
    socket.send(paquete);
  }

  public static DatagramPacket receivePacket(DatagramSocket socket)
      throws SocketTimeoutException, IOException {
    byte[] message_bytes = new byte[256];
    DatagramPacket paquete = new DatagramPacket(message_bytes, 256);
    socket.receive(paquete);
    return paquete;
  }

  public static String getData(DatagramPacket paquete) {
    return new String(paquete.getData()).trim();
  }

}
