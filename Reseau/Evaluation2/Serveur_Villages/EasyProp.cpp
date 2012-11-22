#include "EasyProp.h"

const char* EasyProp::getValue(const char* nameFile, const char* key){
    FILE *file;
    file = fopen(nameFile, "r");
    char* buffer = new char[255];
    
    char* buff;
    char* gauche;
    
    do{
        fgets(buffer, 255, file);
        gauche = strtok_r(buffer, "=", &buff);
        if(!strcmp(gauche, key)){
            buff[strlen(buff)-1] = '\0';
            fclose(file);
            return buff;
        }
    }while(!feof(file));
    fclose(file);
    return NULL;
}

bool EasyProp::containsKey(const char* nameFile, const char* key){
    FILE *file;
    file = fopen(nameFile, "r");
    char* buffer = new char[255];
    
    char* buff;
    char* gauche;
    
    do{
        fgets(buffer, 255, file);
        gauche = strtok_r(buffer, "=", &buff);
        if(!strcmp(gauche, key)){
            fclose(file);
            return true;
        }
    }while(!feof(file));
    fclose(file);
    return false;
}