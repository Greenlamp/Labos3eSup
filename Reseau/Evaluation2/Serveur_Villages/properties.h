#include<stdio.h>
#include<stdlib.h> // exit(1)
#include<sys/types.h>
#include<sys/socket.h> // type de socket (tcp/ip, 0 ou 6)
#include<errno.h>
#include<netdb.h> // struct hostent
#include<string.h> // memcpy
#include<netinet/tcp.h> // TCP_MAXSEG

#define PORT_VILLAGE 32020
#define IP "127.0.0.1"

#define NB_MAX_CLIENT 5
#define SIZEMESSAGE 255
#define EOC "END_OF_CONNEXION"
#define DOC "DENY_OF_CONNEXION"

//Protocole
#define LOGIN "LOGIN"
#define LOGIN_OUI "LOGIN_OUI"
#define LOGIN_NON "LOGIN_NON"
#define CLOSE "CLOSE"