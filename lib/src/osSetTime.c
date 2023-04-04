#include "libultra_internal.h"

extern OSTime __osCurrentTime;

void osSetTime(OSTime time) {
    __osCurrentTime = time;
}
