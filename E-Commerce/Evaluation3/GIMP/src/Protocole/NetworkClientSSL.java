/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Protocole;

import Securite.MyCertificate;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


public class NetworkClientSSL {
    private SSLSocket socketClient;
    GIMP protocole;
    MyCertificate myCertificate;
    MyCertificate myCertificateSsl;

    public NetworkClientSSL(String host, int port, MyCertificate myCertificate, MyCertificate myCertificateSsl){
        this.myCertificate = myCertificate;
        this.myCertificateSsl = myCertificateSsl;
        protocole = new GIMP(myCertificate, myCertificateSsl);
        try {
            InetAddress ip = InetAddress.getByName(host);

            SSLContext context = SSLContext.getInstance("SSLv3");

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(myCertificateSsl.getKeystore(), myCertificateSsl.getPassword().toCharArray());

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(myCertificateSsl.getKeystore());

            context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

            SSLSocketFactory sslSocketFactory = context.getSocketFactory();
            this.socketClient = (SSLSocket)sslSocketFactory.createSocket(ip, port);
        } catch (Exception ex) {
            Logger.getLogger(NetworkClientSSL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isConnected(){
        if(socketClient != null){
            return true;
        }else{
            return false;
        }
    }

    public void disconnect(){
        if(this.isConnected()){
            try {
                socketClient.close();
                socketClient = null;
                System.out.println("Déconnection réussie");
            } catch (IOException ex) {
                Logger.getLogger(NetworkServerSSL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void send(PacketCom packet){
        if(this.isConnected()){
            try {
                OutputStream os = this.socketClient.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject((Object)packet);
            } catch (IOException ex) {
                Logger.getLogger(NetworkServerSSL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("Socket non connectée");
        }
    }

    public PacketCom receive() throws Exception{
        try{
            InputStream is = this.socketClient.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            Object objet = (Object)ois.readObject();
            PacketCom MessageFromServer = protocole.messageFromServer(objet);
            return MessageFromServer;
        }catch(Exception ex){
            this.disconnect();
            throw (Exception)ex;
        }
    }

}