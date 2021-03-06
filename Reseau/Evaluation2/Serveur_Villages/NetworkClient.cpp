#include "NetworkClient.h"

NetworkClient::NetworkClient(){
    this->socketClient = this->createSocket();
    this->initInfos(EasyProp::getValue("properties.prop", "HOST"), atoi((EasyProp::getValue("properties.prop", "PORT_VILLAGE")).c_str()));
    this->connection();
}

NetworkClient::NetworkClient(string adresseIP, int port){
    this->socketClient = this->createSocket();
    this->initInfos(adresseIP, port);
    this->connection();
}

NetworkClient::NetworkClient(const NetworkClient& n)
{
    this->socketClient = n.socketClient;
    this->adresseIp = n.getAdresseIp();
    this->port = n.port;
    this->adresseSocket = n.adresseSocket;
}


int NetworkClient::createSocket()
{
    int hSocket = -1;
    //AF_INET = pour dire a TCP/IP d'utiliser une adresse Ip sur 4 octets.
    //SOCK_STREAM = pour dire qu'on veut travailler en mode connecté
    //0 = pour prendre le protocole par défaut, c'est à dire TCP/IP (6 normalement)
    hSocket = socket(AF_INET, SOCK_STREAM, 0);
    if(hSocket == -1){
        cout << "Erreur de création de la socket: " << errno << endl;
        exit(1);
    }
    
    return hSocket;
}

void NetworkClient::initInfos(string adresseIp, int port)
{
    struct hostent *infoHost;
    struct in_addr addIp;

    this->adresseIp = adresseIp;
    this->port = port;

    infoHost = gethostbyname(adresseIp.c_str());
    if(infoHost == 0){
        cout << "Erreur d'acquisition des infos sur le host: " << errno << endl;
        exit(1);
    }

    //infoHost = les infos sur la machine comme par exemple les carte réseau installé etc.
    // h_addr est une macro qui recupere la premiere adresse du tableau des cartes réseau.
    memcpy(&addIp, infoHost->h_addr, infoHost->h_length);
    //on initialise la structure sockaddr_in qui contiendra l'adresse et le port.
    memset(&this->adresseSocket, 0, sizeof(struct sockaddr_in));
    this->adresseSocket.sin_family = AF_INET;
    //On fait htons pour mettre en big endian si ce n'est pas le cas.
    this->adresseSocket.sin_port = htons(port);
    memcpy(&this->adresseSocket.sin_addr, infoHost->h_addr, infoHost->h_length);
}

void NetworkClient::disconnect()
{
    close(this->socketClient);
}

void NetworkClient::connection()
{
    unsigned int tailleStruct = sizeof(struct sockaddr_in);
    int ret = connect(this->socketClient, (struct sockaddr*) &this->adresseSocket, tailleStruct);
    if(ret == -1){
        cout << "Erreur sur le connect de la socket: " << errno << endl;
    }
}

void NetworkClient::sendMessage(string message)
{
    string buffer;
    buffer = message;
    buffer += '\r';
    buffer += '\n';
    buffer += '\0';
    int ret = send(this->socketClient, buffer.c_str(), buffer.size(), 0);
    if(ret == -1){
        cout << "Erreur sur le send de la socket: " << errno << endl;
        this->disconnect();
        exit(1);
    }
}
bool NetworkClient::receiveString(string *line)
{
    int tailleSegment = 0;
    int sizeInt = sizeof(int);
    string terminator = "\r\n";
    char* buffer = new char[SIZEMESSAGE];
    int nbByteRecu = 0;
    string tempString;
    size_t term = 0;
    
    int ret = getsockopt(this->socketClient, IPPROTO_TCP, TCP_MAXSEG, &tailleSegment, (socklen_t*)&sizeInt);
    if(ret == -1){
        cout << "Erreur sur le getSockOpt de la socket: " << errno << endl;
        this->disconnect();
        exit(1);
    }
    
    memset(buffer, 0, SIZEMESSAGE);
    
    do{
        nbByteRecu = recv(this->socketClient, buffer, tailleSegment, 0);
        if(nbByteRecu == -1){
            cout << "Erreur sur le recv de la socket: " << errno << endl;
            this->disconnect();
            exit(1);
        }
        if(nbByteRecu == 0){
            return false;
        }
        tempString = buffer;
        term = tempString.find(terminator);
        if(term != -1){
            buffer[term] = '\0';
            *line = buffer;
            delete [] buffer;
            return true;
        }
    }while(nbByteRecu != 0 && nbByteRecu != -1);
    return false;
    
}

string NetworkClient::receiveMessage()
{
    int tailleSegment = 0;
    int nbByteRecu = 0;
    int tailleMessage = 0;
    int sizeInt = sizeof(int);
    char *buffer = new char[SIZEMESSAGE];
    char *msg = new char[SIZEMESSAGE];
    bool finDetecte = false;
    string retour;
    
    int ret = getsockopt(this->socketClient, IPPROTO_TCP, TCP_MAXSEG, &tailleSegment, (socklen_t*)&sizeInt);
    if(ret == -1){
        cout << "Erreur sur le getSockOpt de la socket: " << errno << endl;
        this->disconnect();
        exit(1);
    }
    
    memset(buffer, 0, SIZEMESSAGE);
    memset(msg, 0, SIZEMESSAGE);
    
    do{
        nbByteRecu = recv(this->socketClient, buffer, tailleSegment, 0);
        if(nbByteRecu == -1){
            cout << "Erreur sur le recv de la socket: " << errno << endl;
            this->disconnect();
            exit(1);
        }
        finDetecte = this->verifMarqueur(buffer, nbByteRecu);
        memcpy((char*)msg + tailleMessage, buffer, nbByteRecu);
        tailleMessage += nbByteRecu;
    }while(nbByteRecu != 0 && nbByteRecu != -1 && !finDetecte);
    if(nbByteRecu == 0) return EOC;
    *(msg + tailleMessage - 3) = '\0'; //-2 pour enlever le \r\n
    retour = msg;
    return retour;
}

bool NetworkClient::verifMarqueur(char* message, int nbByte)
{
    static bool rTrouver = false;
    bool nTrouver = false;
    int i=0;
    char temp = message[0];
    if(rTrouver == true && message[0] == '\n') return true;
    else rTrouver = false;
    
    for(i=0; i<nbByte-1 && !nTrouver; i++){
        char msgI = message[i];
        char msgN = message[i+1];
        if(message[i] == '\r' && message[i+1] == '\n') nTrouver = true;        
    }
    
    char msgNbByte = message[nbByte] == '\r';
    if(nTrouver) return true;
    else if(message[nbByte] == '\r'){
        rTrouver = true;
        return false;
    }else return false;
}

//Setters

//Getters
string NetworkClient::getAdresseIp() const
{
    return this->adresseIp;
}

int NetworkClient::getSocketClient()
{
    return this->socketClient;
}

