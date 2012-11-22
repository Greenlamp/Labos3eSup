#include "NetworkServer.h"

NetworkServer::NetworkServer(){
    this->socketServer = this->createSocket();
    this->socketClient = -1;
    this->initInfos(IP, PORT_VILLAGE);
    this->injectAdress();
    this->listenSocket();
    this->acceptSocket();
}

NetworkServer::NetworkServer(int socketClient)
{
    this->socketClient = socketClient;
    this->connected = true;
}


NetworkServer::NetworkServer(const NetworkServer& n)
{
    this->socketServer = n.socketServer;
    this->socketClient = n.socketClient;
    this->setAdresseIp(n.getAdresseIp());
    this->port = n.port;
    this->adresseSocket = n.adresseSocket;
    this->connected = n.connected;
}


NetworkServer::~NetworkServer()
{
    delete [] adresseIp;
}


int NetworkServer::createSocket()
{
    int hSocket = -1;
    //AF_INET = pour dire a TCP/IP d'utiliser une adresse Ip sur 4 octets.
    //SOCK_STREAM = pour dire qu'on veut travailler en mode connecté
    //0 = pour prendre le protocole par défaut, c'est à dire TCP/IP (6 normalement)
    hSocket = socket(AF_INET, SOCK_STREAM, 0);
    if(hSocket == -1){
        cout << "Erreur de création de la socket: " << errno << endl;
        exit(1);
    }else{
        cout << "Création de la socket: OK" << endl;
    }
    
    return hSocket;
}

void NetworkServer::initInfos(const char* adresseIp, int port)
{
    struct hostent *infoHost;
    struct in_addr addIp;

    this->setAdresseIp(adresseIp);
    this->port = port;
    cout << "Mise à l'écoute sur l'adresse " << this->getAdresseIp() << " et le port " << this->port << endl;

    infoHost = gethostbyname(adresseIp);
    if(infoHost == 0){
        cout << "Erreur d'acquisition des infos sur le host: " << errno << endl;
        exit(1);
    }else{
        cout << "Acquisition des infos sur le host: OK" << endl;
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


void NetworkServer::injectAdress()
{
    cout << "== [BIND] ==" << endl;
    int ret = bind(this->socketServer, (struct sockaddr*) &this->adresseSocket, sizeof(struct sockaddr_in));
    if(ret == -1){
        cout << "Erreur sur le bind de la socket: " << errno << endl;
        exit(1);
    }
}

void NetworkServer::listenSocket()
{
    cout << "== [LISTEN] ==" << endl;
    int ret = -1;
    //SOMAXCONN = nombre de connexion pendante maximum ici 1024.
    ret = listen(this->socketServer, SOMAXCONN);
    if(ret == -1){
        cout << "Erreur sur le listen de la socket: " << errno << endl;
        this->disconnect();
        exit(1);
    }
}

void NetworkServer::disconnect()
{
    if(this->socketServer != -1){
        close(this->socketServer);
        this->socketServer = -1;
    }
    if(this->socketClient != -1){
        close(this->socketClient);
        this->socketClient = -1;
    }
    this->connected = false;
}

void NetworkServer::acceptSocket()
{
    cout << "En attente d'un client" << endl;
    int tailleStruct = sizeof(struct sockaddr_in);
    int ret = accept(this->socketServer, (struct sockaddr*) &this->adresseSocket, (socklen_t*)&tailleStruct);
    if(ret == -1){
        cout << "Erreur sur l'accept de la socket: " << errno << endl;
        this->disconnect();
        exit(1);
    }
    this->connected = true;
    this->socketClient = ret;
}

void NetworkServer::sendMessage(const char* message)
{
    int taille = strlen(message);
    char* buffer = new char[taille + 2];
    strcpy(buffer, message);
    buffer[taille] = '\r';
    buffer[taille + 1] = '\n';
    buffer[taille + 2] = '\0';
    int ret = send(this->socketClient, buffer, taille + 2, 0);
    if(ret == -1){
        cout << "Erreur sur le send de la socket: " << errno << endl;
        this->disconnect();
        exit(1);
    }
}

const char* NetworkServer::receiveMessage()
{
    int tailleSegment = 0;
    int nbByteRecu = 0;
    int tailleMessage = 0;
    int sizeInt = sizeof(int);
    char *buffer = new char[SIZEMESSAGE];
    char *msg = new char[SIZEMESSAGE];
    bool finDetecte = false;
    
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
    *(msg + tailleMessage - 2) = '\0'; //-2 pour enlever le \r\n
    return msg;
}

bool NetworkServer::verifMarqueur(char* message, int nbByte)
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
void NetworkServer::setAdresseIp(const char* adresseIp)
{
    this->adresseIp = new char[strlen(adresseIp) + 1];
    strcpy(this->adresseIp, adresseIp);
}

//Getters
const char* NetworkServer::getAdresseIp() const
{
    return this->adresseIp;
}

bool NetworkServer::isConnected() const
{
    return this->connected;
}

int NetworkServer::getSocketClient()
{
    return this->socketClient;
}

void NetworkServer::setSocketClient(int socket)
{
    this->socketClient = socket;
}



