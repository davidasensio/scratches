//
// Created by David Asensio on 26/02/2018.
// Command: gcc -o Test1 Test1.cpp & ./Test1 (run template)
//

#include <stdio.h>
#include "Test1.h"
#include "Test2.cpp"

extern int numero;

int main() {
    printf("Hello World\n");

    printf("Num is %d", num);

    printf("\n\n");

    return 0;
}

