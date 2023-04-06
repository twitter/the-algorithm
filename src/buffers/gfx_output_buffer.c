#include <ultra64.h>

// 0x1f000 bytes, aligned to a 0x1000-byte boundary through sm64.ld. (This results
// in a bunch of unused space: ~0x100 in JP, ~0x300 in US.)
u64 gGfxSPTaskOutputBuffer[0x3e00];
