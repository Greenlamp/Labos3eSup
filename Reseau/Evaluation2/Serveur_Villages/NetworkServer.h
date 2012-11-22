#ifndef DEF_NETWORKSERVER
#define DEF_NETWORKSERVER

#ifdef LIN
    using namespace std;
    #include<iostream>
    #include<fstream>
    #include<string>
#endif

#ifdef SUN
    #include<iostream.h>
    #include<fstream.h>
#endif
#include "properties.h"
#include "EasyProp.h"
    
class NetworkServer{
private:
    int socketServer;
    int socketClient;
    char *adresseIp;
    int port;
    struct sockaddr_in adresseSocket;
    bool connected;
    int sizeMessage;
public:
    NetworkServer();
    NetworkServer(const char* host, int port);
    NetworkServer(const NetworkServer &n);
    NetworkServer(int socket);
    ~NetworkServer();
    int createSocket();
    void injectAdress();
    void initInfos(const char* adresseIp, int port);
    void listenSocket();
    void disconnect();
    void acceptSocket();
    void sendMessage(const char* message);
    const char* receiveMessage();
    bool verifMarqueur(char* message, int nbByte);
    void setSocketClient(int socket);
    
    //setters
    void setAdresseIp(const char* adresseIp);
    //getters
    const char* getAdresseIp() const;
    bool isConnected() const;
    int getSocketClient();
};
#endif