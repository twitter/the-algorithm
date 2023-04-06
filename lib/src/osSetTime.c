#include "libultra_internal.h"

extern OSTime _osCurrentTime;

void osSetTime(OSTime time) {
    _osCurrentTime = time;
}
