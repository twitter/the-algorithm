#ifndef SEGMENTS_H
#define SEGMENTS_H

#include "config.h"

/*
 * Memory addresses for segments. Ideally, this header file would not be
 * needed, and the addresses would be defined in sm64.ld and linker-inserted
 * into C code. However, there are some cases where that would not match, where
 * addresses are loaded using lui/ori rather than lui/addiu.
 * To avoid duplication, this file is included from sm64.ld. We make sure not
 * to cast the addresses to pointers in this file, since that would be invalid
 * linker script syntax.
*/

#ifndef USE_EXT_RAM /* Default: Runs out of memory quickly when importing custom assets. */

#define SEG_POOL_START   0x8005C000
#define SEG_POOL_END     SEG_BUFFERS

#define SEG_GODDARD      0x8016F000

#define SEG_BUFFERS      0x801C1000

#if defined(VERSION_SH) || ENABLE_RUMBLE
#define SEG_MAIN         0x80249000
#elif defined(VERSION_EU)
#define SEG_MAIN         0x80241800 // TODO: Investigate why it's different?
#else
#define SEG_MAIN         0x80246000
#endif

#ifdef VERSION_EU
#define SEG_ENGINE       0x8036FF00
#else
#define SEG_ENGINE       0x80378800
#endif

#define SEG_FRAMEBUFFERS 0x8038F800

#else /* Use Expansion Pak space for pool. */

/*
 * Workaround for running out of pool space due to
 * importing large custom content.
 */

#define SEG_BUFFERS      0x8005C000 // 0x0085000 in size
#define SEG_MAIN         0x800E1000 // 0x0132800 in size
#define SEG_ENGINE       0x80213800 // 0x0017000 in size
#define SEG_FRAMEBUFFERS 0x8022A800 // 0x0070800 in size
#define SEG_POOL_START   0x8029B000 // 0x0165000 in size
#define SEG_POOL_END     0x80800000
#define SEG_POOL_END_4MB 0x80400000 // For the error message screen enhancement.
#define SEG_GODDARD      SEG_POOL_START + 0x113000
#endif

#endif // SEGMENTS_H
