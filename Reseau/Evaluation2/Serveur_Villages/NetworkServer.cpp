#include "NetworkServer.h"

NetworkServer::NetworkServer(){
    this->sizeMessage = atoi((EasyProp::getValue("properties.prop", "SIZEMESSAGE")).c_str());
    this->socketServer = this->createSocket();
    this->socketClient = -1;
    this->initInfos(EasyProp::getValue("properties.prop", "HOST"), atoi((EasyProp::getValue("properties.prop", "PORT_VILLAGE")).c_str()));
    this->injectAdress();
    this->listenSocket();
    this->acceptSocket();
}

NetworkServer::NetworkServer(string host, int port)
{
    this->sizeMessage = atoi((EasyProp::getValue("properties.prop", "SIZEMESSAGE")).c_str());
    this->socketServer = this->createSocket();
    this->socketClient = -1;
    this->initInfos(host, port);
    this->injectAdress();
    this->listenSocket();
    this->acceptSocket();
}


NetworkServer::NetworkServer(int socketClient)
{
    this->sizeMessage = atoi((EasyProp::getValue("properties.prop", "SIZEMESSAGE")).c_str());
    this->socketClient = socketClient;
    this->connected = true;
}


NetworkServer::NetworkServer(const NetworkServer& n)
{
    this->socketServer = n.socketServer;
    this->socketClient = n.socketClient;
    this->adresseIp = n.getAdresseIp();
    this->port = n.port;
    this->adresseSocket = n.adresseSocket;
    this->connected = n.connected;
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
    }
    
    return hSocket;
}

void NetworkServer::initInfos(string adresseIp, int port)
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
    this->adresseSocket.sin_addr.s_addr = htonl(INADDR_ANY);
    //memcpy(&this->adresseSocket.sin_addr, infoHost->h_addr, infoHost->h_length);
}


void NetworkServer::injectAdress()
{
    int ret = bind(this->socketServer, (struct sockaddr*) &this->adresseSocket, sizeof(struct sockaddr_in));
    if(ret == -1){
        cout << "Erreur sur le bind de la socket: " << errno << endl;
        exit(1);
    }
}

void NetworkServer::listenSocket()
{
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
    cout << "Disconnected" << endl;
}

void NetworkServer::acceptSocket()
{
    cout << "Mise à l'écoute sur l'adresse " << this->getAdresseIp() << " et le port " << this->port << endl;
    int tailleStruct = sizeof(struct sockaddr_in);
    int ret = accept(this->socketServer, (struct sockaddr*) &this->adresseSocket, (socklen_t*)&tailleStruct);
    if(ret == -1){
        cout << "Erreur sur l'accept de la socket: " << errno << endl;
        this->disconnect();
        exit(1);
    }
    this->connected = true;
    this->socketClient = ret;
    cout << "Nouveau client sur le port: " << this->port <<"!" << endl;
}

void NetworkServer::sendMessage(string message)
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
bool NetworkServer::receiveString(string *line)
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

string NetworkServer::receiveMessage()
{
    int tailleSegment = 0;
    int nbByteRecu = 0;
    int tailleMessage = 0;
    int sizeInt = sizeof(int);
    char *buffer = new char[this->sizeMessage];
    char *msg = new char[this->sizeMessage];
    bool finDetecte = false;
    string retour;
    
    int ret = getsockopt(this->socketClient, IPPROTO_TCP, TCP_MAXSEG, &tailleSegment, (socklen_t*)&sizeInt);
    if(ret == -1){
        cout << "Erreur sur le getSockOpt de la socket: " << errno << endl;
        this->disconnect();
        exit(1);
    }
    
    memset(buffer, 0, this->sizeMessage);
    memset(msg, 0, this->sizeMessage);
    
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

//Getters
string NetworkServer::getAdresseIp() const
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



