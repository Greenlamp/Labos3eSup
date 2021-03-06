#ifndef EASYCSV_H
#define EASYCSV_H

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
    
#include "string.h"
    
class EasyCSV{
private:
public:
    EasyCSV();
    static string getValue(string nameFile, string key);
    static void putValue(string nameFile, string key, string value);
    static void delValue(string nameFile, string key);
    static bool containsKey(string nameFile, string key);
    static bool containsName(string nameFile, string key);
    static char* getLast(string nameFile);
    static string materielIsDispo(string nameFile, string materiel);
};

#endif