#include "libultra_internal.h"

void osSpTaskYield(void) {
    __osSpSetStatus(SPSTATUS_SET_SIGNAL0);
}
