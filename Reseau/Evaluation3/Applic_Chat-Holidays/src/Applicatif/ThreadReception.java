/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Applicatif;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTextArea;


public class ThreadReception implements Runnable{
    JTextArea zoneChat;
    MulticastSocket socket;
    JComboBox Gnumero;

    public ThreadReception(JTextArea zoneChat, MulticastSocket socket, JComboBox Gnumero){
        this.zoneChat = zoneChat;
        this.socket = socket;
        this.Gnumero = Gnumero;
    }

    @Override
    public void run() {
        while(true){
            try {
                byte[] buffer = new byte[255];
                DatagramPacket packetRecu = new DatagramPacket(buffer, buffer.length);
                socket.receive(packetRecu);
                zoneChat.append(new String(buffer) + "\n");
                traiterNumero(new String(buffer));
            } catch (IOException ex) {
                Logger.getLogger(ThreadReception.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void traiterNumero(String message) {
        System.out.println("message: " + message);
        String droite = null;
        String type = null;
        String gauche = null;
        String[] split = message.split("Question");
        if(split.length > 1){
            //Question
            //split[1] " id: 57601 : aze                                                                    "
            String[] split2 = split[1].split("id: ");
            //split2[1] "53149 : aze                                                                    "
            String[] split3 = split2[1].split(" :");
            String code = split3[0];
            Gnumero.addItem(code);
            System.out.println("Question");
        }else{
            split = message.split("Reponse");
            if(split.length > 1){
                //Réponse
                //Split[1] " id: 56431 : aze                                                                                                                                                                                                                                "
                String[] split2 = split[1].split("id: ");
                String[] split3 = split2[1].split(" :");
                String code = split3[0];

                for(int i=0; i<Gnumero.getItemCount(); i++){
                    if(Gnumero.getItemAt(i).toString().equals(code)){
                        Gnumero.removeItemAt(i);
                    }
                }

                System.out.println("Reponse");
            }
        }
    }

}
