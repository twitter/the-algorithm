#include "libultra_internal.h"
#include "osAi.h"
#include "hardware.h"

extern s32 D_8033491C;

s32 osAiSetFrequency(u32 freq) {
    register u32 a1;
    register s32 a2;
    register float ftmp;
    ftmp = D_8033491C / (float) freq + .5f;

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
    return D_8033491C / (s32) a1;
}

// put some extra jr $ra's down there please
static void filler1(void) {
}
static void filler2(void) {
}
