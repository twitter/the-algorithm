#include "libultra_internal.h"
#include "osAi.h"
#include "hardware.h"
#include "macros.h"

extern s32 osViClock;

s32 osAiSetFrequency(u32 freq) {
    register u32 a1;
    register s32 a2;
    register float ftmp;
    ftmp = osViClock / (float) freq + .5f;

    a1 = ftmp;

    if (a1 < 0x84) {
        return -1;
    }

    a2 = (a1 / 66) & 0xff;
    if (a2 > 16) {
        a2 = 16;
    }

    HW_REG(AI_DACRATE_REG, u32) = a1 - 1;
    HW_REG(AI_BITRATE_REG, u32) = a2 - 1;
    HW_REG(AI_CONTROL_REG, u32) = 1; // enable dma
    return osViClock / (s32) a1;
}

#ifndef VERSION_SH
// put some extra jr $ra's down there please
UNUSED static void filler1(void) {
}

UNUSED static void filler2(void) {
}
#endif
