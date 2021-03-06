/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MAMP;

import Commun.MyCertificateSSL;
import Commun.PacketComSSL;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;


public class MAMPNetworkServerSSL {
    private SSLServerSocket socketServeur;
    private SSLSocket socketClient;
    MAMP protocole;
    boolean connected;
    MyCertificateSSL myCertificate_no_ssl;
    MyCertificateSSL myCertificate_ssl;
    String orga;

    public MAMPNetworkServerSSL(int port, MyCertificateSSL myCertificate_no_ssl, MyCertificateSSL myCertificate_ssl, String orga){
        this.orga = orga;
        this.myCertificate_no_ssl = myCertificate_no_ssl;
        this.myCertificate_ssl = myCertificate_ssl;
        protocole = new MAMP(this.myCertificate_no_ssl, this.myCertificate_ssl, 666, orga);
        try {
            SSLContext context = SSLContext.getInstance("SSLv3");

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(myCertificate_ssl.getKeystore(), myCertificate_ssl.getPassword().toCharArray());

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(myCertificate_ssl.getKeystore());

            context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            SSLServerSocketFactory sslServerSocketFactory = context.getServerSocketFactory();
            this.socketServeur = (SSLServerSocket)sslServerSocketFactory.createServerSocket(port);

            connected = true;
            System.out.println("Socket créer");

        } catch (IOException ex) {
            Logger.getLogger(MAMPNetworkServerSSL.class.getName()).log(Level.SEVERE, null, ex);
            connected = false;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MAMPNetworkServerSSL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(MAMPNetworkServerSSL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(MAMPNetworkServerSSL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(MAMPNetworkServerSSL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MAMPNetworkServerSSL(SSLSocket socket, MyCertificateSSL myCertificate_no_ssl, MyCertificateSSL myCertificate_ssl, String orga){
        this.orga = orga;
        this.myCertificate_no_ssl = myCertificate_no_ssl;
        this.myCertificate_ssl = myCertificate_ssl;
        protocole = new MAMP(this.myCertificate_no_ssl, this.myCertificate_ssl, 123, orga);
        this.setSocketClient(socket);
        this.connected = true;
    }

    public boolean accept(){
        try {
            System.out.println("En attente d'un client");
            socketClient = (SSLSocket) socketServeur.accept();
            System.out.println("client connecté");
            return true;
        } catch (IOException ex) {
            Logger.getLogger(MAMPNetworkServerSSL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void disconnect(){
        if(!socketServeur.isClosed()){
            try {
                socketServeur.close();
                System.out.println("Déconnection réussie");
            } catch (IOException ex) {
                Logger.getLogger(MAMPNetworkServerSSL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void disconnectClient(){
        if(!socketClient.isClosed()){
            try {
                getSocketClient().close();
                System.out.println("Déconnection client réussie");
            } catch (IOException ex) {
                Logger.getLogger(MAMPNetworkServerSSL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isConnected(){
        return connected;
    }

    public void send(PacketComSSL packet){
        if(this.isConnected()){
            try {
                OutputStream os = this.socketClient.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject((Object)packet);
            } catch (IOException ex) {
                Logger.getLogger(MAMPNetworkServerSSL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("Socket non connectée");
        }
    }

    public PacketComSSL receive() throws Exception{
        try{
            InputStream is = this.socketClient.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            Object objet = (Object)ois.readObject();
            PacketComSSL MessageToClient = protocole.messageFromClient(objet);
            return MessageToClient;
        }catch(Exception ex){
            this.disconnectClient();
            throw (Exception)ex;
        }
    }

    public SSLSocket getSocketClient() {
        return socketClient;
    }

    public void setSocketClient(SSLSocket socketClient) {
        this.socketClient = socketClient;
    }

    public String getOrga(){
        return this.orga;
    }
}
