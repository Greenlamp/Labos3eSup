#ifndef EASYPROP_H
#define EASYPROP_H

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
    
class EasyProp{
private:
public:
    EasyProp();
    static const char* getValue(const char* nameFile, const char* key);
    static bool containsKey(const char* nameFile, const char* key);
};

#endif