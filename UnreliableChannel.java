/**
 * Networks 2018/2019
 * Lab 7:
 * - file transmission via UDP Alternating-Bit-Protocol compared to TCP
 * - unreliable channel
 * - finite state machine for FileSender and FileReceiver
 *
 * @author Erwin Kupris, kupris@hm.edu // Bahadir Süzer, suezer@hm.edu
 * @version 2019-01-13
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UnreliableChannel {
    private static int lost = 0;
    private static int manipulated =0;
    private static int duplicated = 0;


    public static DatagramPacket checkIfSomethingHappened(DatagramPacket packetIn, double pLose, double pDup, double pMan) {
        double pLoseCalc = Math.random();
        double pDupCalc = Math.random();
        double pManCalc = Math.random();
        DatagramPacket result = packetIn;
        if (pLoseCalc < pLose){
            byte[] data = new byte[1400];
            result.setData(data);
            lost ++;
            //System.out.println("got lost package number "+lost );
        }
        else if (pDupCalc < pDup){
            duplicated++;
            //System.out.println("\n\n\ngot duplicated package number "+duplicated );
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }
            try {
                socket.send(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (pManCalc < pMan){
            manipulated++;
            //System.out.println("\n\n\ngot manipulated package number "+manipulated );
            byte[] payload = result.getData();
            payload[12] ^= 1;
            result.setData(payload);
        }
        return result;
    }


}
