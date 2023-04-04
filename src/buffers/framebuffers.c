#include <ultra64.h>

#include "config.h"

// 0x70800 bytes
#ifdef AVOID_UB
u16 gFramebuffers[3][SCREEN_WIDTH * SCREEN_HEIGHT];
#else
u16 gFramebuffer0[SCREEN_WIDTH * SCREEN_HEIGHT];
u16 gFramebuffer1[SCREEN_WIDTH * SCREEN_HEIGHT];
u16 gFramebuffer2[SCREEN_WIDTH * SCREEN_HEIGHT];
#endif
