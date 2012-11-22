#include "FHMP.h"

FHMP::FHMP()
{

}

const char* FHMP::treatPacketServer(const char* packet)
{
    char* type = new char[SIZEMESSAGE];
    char* message = new char[SIZEMESSAGE];
    int nbByte = strlen(packet);
    FHMP::parsePacket(packet, type, message);
    return this->actionTypeServer(type, message);
}
const char* FHMP::treatPacketClient(const char* packet)
{
    char* type = new char[SIZEMESSAGE];
    char* message = new char[SIZEMESSAGE];
    int nbByte = strlen(packet);
    FHMP::parsePacket(packet, type, message);
    return this->actionTypeClient(type, message);
}

void FHMP::parsePacket(const char* packet, char* type, char* message)
{
    char *buffer, *buff;
    char* temp = new char[strlen(packet)];
    strcpy(temp, packet);
    
    buff = strtok_r(temp, ";", &buffer);
    strcpy(type, buff);
    strcpy(message, buffer);
}

const char* FHMP::actionTypeServer(const char* type, const char* message)
{
    if(!strcmp(type, EOC)){
        return EOC;
    }else if(!strcmp(type, LOGIN)){
        return LOGIN_OUI;
    }else{
        return CLOSE;
    }
}

const char* FHMP::actionTypeClient(const char* type, const char* message){
    if(!strcmp(type, LOGIN_OUI)){
        return LOGIN_OUI;
    }else if(!strcmp(type, LOGIN_NON)){
        return LOGIN_NON;
    }else{
        return CLOSE;
    }
}

char* FHMP::createPacket(const char* type, const char* message){
    char* buffer = new char[255];
    sprintf(buffer, "%s;%s", type, message);
    return buffer;
}



