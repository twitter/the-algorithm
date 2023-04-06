#include "libultra_internal.h"

uintptr_t osVirtualToPhysical(void *addr) {
    if ((uintptr_t) addr >= 0x80000000 && (uintptr_t) addr < 0xa0000000) {
        return ((uintptr_t) addr & 0x1fffffff);
    } else if ((uintptr_t) addr >= 0xa0000000 && (uintptr_t) addr < 0xc0000000) {
        return ((uintptr_t) addr & 0x1fffffff);
    } else {
        return __osProbeTLB(addr);
    }
}
