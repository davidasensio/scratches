#include <stdio.h>

int main() {
    //printf("Testing pointers");
    //exit(0);
    int a;
    int b = 5;
    a = b;
    int *ptrNum = &a;

    printf("a is %d", a);
    printf("ptrNum is %d\n\n", *ptrNum);

    b = 921;

    printf("a is %d", a);
    printf("ptrNum is %d\n\n", *ptrNum);

    a = 39;

    printf("a is %d", a);
    printf("ptrNum is %d\n\n", *ptrNum);

    *ptrNum = 888;

    printf("a is %d", a);
    printf("ptrNum is %d\n\n", *ptrNum);
}
