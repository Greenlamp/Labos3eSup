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
    static const char* getValue(const char* nameFile, const char* key);
    static void putValue(const char* nameFile, const char* key, const char* value);
    static void delValue(const char* nameFile, const char* key);
    static bool containsKey(const char* nameFile, const char* key);
};

#endif