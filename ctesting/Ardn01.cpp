//
// Created by David Asensio on 28/02/2018.
// gcc -o Ardn01.o Ardn01.cpp & ./Ardn01.o
//

#include <stdio.h>
#include <stdlib.h>

int counter = 0;

void setup() {

}

void loop() {
    int number = 5;
    int *ptrNumber;
    int k;

    printf("The lvalue for ptrNumber is: ");
    printf("%d", &ptrNumber);
    printf(" and the rvalue is: ");
    printf("%d", ptrNumber);
    printf("\n");

    ptrNumber = &number;
    *ptrNumber = 10;
    k = *ptrNumber;

    printf("The lvalue for number is: ");
    printf("%d", &number);
    printf(" and the rvalue is: ");
    printf("%d", number);
    printf("\n");

    printf("The lvalue of k is: ");
    printf("%d", k);

    counter++;

    if (counter > 10) {
        exit(0);
    }

}

int main() {
    printf("Ardn template\n");

    setup();

    while (true) {
        loop();
    }
}

