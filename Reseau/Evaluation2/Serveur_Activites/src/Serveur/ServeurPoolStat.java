/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Serveur;

import Helpers.EasyFile;
import ProtocoleHIDP.NetworkServer;
import ProtocoleHIDP.TraitementPacket;


public class ServeurPoolStat implements Runnable{
    NetworkServer reseau;
    int port;
    PoolThread poolThread;

    ServeurPoolStat() {
        port = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Activites", "PORT_ACTIVITES"));
        System.out.println("Mise à l'écoute sur le port: " + port);
        reseau = new NetworkServer(port);
        poolThread = new PoolThread(3);
    }

    ServeurPoolStat(int port) {
        System.out.println("Mise à l'écoute sur le port: " + port);
        reseau = new NetworkServer(port);
        poolThread = new PoolThread(3);
    }

    @Override
    public void run() {
        if(reseau.isConnected()){
            boolean goOn = true;
            int nbClient = 0;
            while(goOn){
                goOn = reseau.accept();
                System.out.println("Nouveau client !");
                TraitementPacket traitement = new TraitementPacket(new NetworkServer(reseau.getSocketClient()));
                poolThread.assign(traitement);
                nbClient++;
            }
            reseau.disconnect();
        }else{
            System.out.println("Réseau non démarré");
        }
    }

}
