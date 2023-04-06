#include "libultra_internal.h"
#include "osAi.h"
#include "hardware.h"

u8 D_80334820 = 0;

/**
 * It is worth noting that a previous hardware bug has been fixed by a software
 * patch in osAiSetNextBuffer. This bug occurred when the address of the end of the
 * buffer specified by osAiSetNextBuffer was at a specific value. This value
 * occurred when the following was true:
 *
 *     (vaddr + nbytes) & 0x00003FFF == 0x00002000
 *
 * (when the buffer ends with address of lower 14 bits 0x2000) In this case, the
 * DMA transfer does not complete successfully. This can cause clicks and pops in
 * the audio output. This bug no longer requires special handling by the application
 * because it is now patched by osAiSetNextBuffer.
 */

s32 osAiSetNextBuffer(void *buff, u32 len) {
    u8 *sp1c = buff;
    if (D_80334820 != 0) {
        sp1c -= 0x2000;
    }

    if ((((uintptr_t) buff + len) & 0x3fff) == 0x2000) {
        D_80334820 = 1;
    } else {
        D_80334820 = 0;
    }

    if (__osAiDeviceBusy()) {
        return -1;
    }

    HW_REG(AI_DRAM_ADDR_REG, void *) = (void *) osVirtualToPhysical(sp1c);
    HW_REG(AI_LEN_REG, u32) = len;
    return 0;
}
