#ifndef FHMPA_H
#define FHMPA_h

#ifdef LIN
    using namespace std;
    #include<iostream>
    #include<fstream>
    #include<sstream>
    #include<string>
#endif

#ifdef SUN
    #include<iostream.h>
    #include<fstream.h>
    #include<sstream.h>
#endif
#include "properties.h"
#include "PoolThread.h"
#include "EasyCSV.h"
#include "EasyDate.h"
#include<sstream>
#include<list>
    
class FHMPA: public Protocoles{
private:
    PoolThread *poolThread;
public:
    FHMPA();
    FHMPA(PoolThread *poolThread);
    string treatPacketServer(string packet);
    string treatPacketServer(int numSocket, string packet) {}
    string actionTypeServer(string type, string contenu);
    string createPacket(string type, string message);
    string createPacket(string type, int message);
    
    string actionGestionLogina(string contenu);
    string actionGestionLclients(string contenu);
    string actionGestionPause(string contenu);
    string actionGestionStop(string contenu);
    string actionGestionResume(string contenu);
    string getLogin(){}
    map<int, string> getUsers() {}
    void removeUser(int numSocket) {}
};

#endif