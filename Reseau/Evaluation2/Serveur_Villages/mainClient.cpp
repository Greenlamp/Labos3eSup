#define cls() cout << "\033[H\033[2J" << endl;
#include<stdlib.h>
#include<string.h>
#ifdef LIN
    using namespace std;
    #include<iostream>
    #include<fstream>
#endif

#ifdef SUN
    #include<iostream.h>
    #include<fstream.h>
#endif

#include "properties.h"
#include "NetworkClient.h"
#include "FHMP.h"

string gestionLogin();
int menu();
void pauseScreen();
string gestionChoix(int choix);
int menuAction();
string gestionChoixAction(int choix);
void gestionMessageFromServer(string messageFromServer);
    
int main(){
    string host = EasyProp::getValue("properties.prop", "HOST");
    int port = atoi((EasyProp::getValue("properties.prop", "PORT_VILLAGE")).c_str());
    
    string chaine = gestionLogin();
    
    NetworkClient client(host, port);
    FHMP fhmp;
    string messageToSend = LOGIN;
    messageToSend += ";";
    messageToSend += chaine;
    cout << "messageToSend: " << messageToSend << endl;
    client.sendMessage(messageToSend);
    string reponseFromServer = fhmp.treatPacketClient(client.receiveMessage());
    cout << "reponseFromServer: " << reponseFromServer << endl;
    if(reponseFromServer == LOGIN_OUI){
        int choix = -1;
        do{
            choix = menu();
            string packetToSend = gestionChoix(choix);
            if(packetToSend == EOC){
                
            }else{
                client.sendMessage(packetToSend);
                string messageFromServer = fhmp.treatPacketClient(client.receiveMessage());
                cout << "messageFromServer: " << messageFromServer << endl;
                gestionMessageFromServer(messageFromServer);
            }
        }while(choix > 0 && choix <= 3);
    }else{
        cout << "Login ou password incorrect." << endl;
    }
    client.disconnect();
    return 0;
}

string gestionLogin(){
    string login, password;
    cout << "Veuillez entrez votre login: ";
    login = "admin";
    //cin >> login;
    cout << "Veuillez entrez votre password: ";
    password = "admin";
    //cin >> password;
    string retour;
    retour = login;
    retour += "#";
    retour += password;
    return retour;
}

int menu(){
    int choix = 0;
    cout << "========================" << endl;
    cout << "[0] Quitter" << endl;
    cout << "[1] Demander une action" << endl;
    cout << "[2] Supprimer une action" << endl;
    cout << "[3] Commande de matériel" << endl;
    cout << "========================" << endl;
    cout << "Votre choix: ";
    cin >> choix;
    return choix;
}

int menuAction(){
    int choix = 0;
    cout << "========================" << endl;
    cout << "[0] Retour" << endl;
    cout << "[1] Demander une livraison" << endl;
    cout << "[2] Demander une réparation" << endl;
    cout << "[3] Demander un déclassement" << endl;
    cout << "========================" << endl;
    cout << "Votre choix: ";
    cin >> choix;
    return choix;
}

void gestionMessageFromServer(string messageFromServer){
    char* bType;
    char* bMessage;
    string type, message;
    char* cstr = new char[messageFromServer.size()+1];
    strcpy(cstr, messageFromServer.c_str());
    bType = strtok_r(cstr, ";", &bMessage);
    type = bType;
    message = bMessage;
    delete [] cstr;
    
    if(type == BMAT_OUI){
        cout << "Booking Material OK" << endl;
        cout << "Id action: " << message << endl;
    }else if(type == BMAT_NON){
       cout << "Booking Material pas OK" << endl;
       cout << "erreur: " << message << endl;
    }
}

string gestionChoix(int choix){
    int choixAction = 0;
    switch(choix){
        case 1:
            do{
                choixAction = menuAction();
            }while(choixAction < 0 || choixAction > 3);
            return gestionChoixAction(choixAction);
            break;
        case 2:
            break;
        case 3:
            break;
        default:
            return EOC;
            break;
    }
}

string gestionChoixAction(int choix){
    string chaine, nomMateriel, retour, date;
    
    switch(choix){
        case 1:
            cout << "Quel matériel souhaitez-vous la livraison: ";
            cin >> nomMateriel;
            chaine = "LIVRAISON#16-08-91#";
            chaine += nomMateriel;
            retour = BMAT;
            retour += ";";
            retour += chaine;
            break;
        case 2:
            cout << "Quel matériel souhaitez-vous la réparation: ";
            cin >> nomMateriel;
            chaine = "REPARATION#";
            chaine += nomMateriel;
            retour = BMAT;
            retour += ";";
            retour += chaine;
            break;
        case 3:
            cout << "Quel matériel souhaitez-vous le déclassement: ";
            cin >> nomMateriel;
            chaine = "DECLASSEMENT#";
            chaine += nomMateriel;
            retour = BMAT;
            retour += ";";
            retour += chaine;
            break;
    }
    return retour;
}
