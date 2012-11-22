#include "FHMP.h"

FHMP::FHMP()
{

}

const char* FHMP::treatPacket(const char* packet)
{
    char* type = new char[SIZEMESSAGE];
    char* message = new char[SIZEMESSAGE];
    int nbByte = strlen(packet);
    FHMP::parsePacket(packet, type, message);
    return this->actionType(type, message);
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

const char* FHMP::actionType(const char* type, const char* message)
{
    if(!strcmp(type, EOC)){
        return EOC;
    }else if(!strcmp(type, LOGIN)){
        return LOGIN_OUI;
    }else{
        return CLOSE;
    }
}


