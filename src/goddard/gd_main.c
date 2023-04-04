#include <PR/ultratypes.h>

#include "debug_utils.h"
#include "gd_main.h"
#include "gd_memory.h"
#include "macros.h"
#include "objects.h"
#include "renderer.h"

/* This file was spilt out of debug_memory.asm based on rodata.
 * The rodata for functions after this "__main__" function have string literals following
 * f32 literal, which implies that this is its own file
 */

// data
s32 gGdMoveScene = TRUE; // @ 801A8050
UNUSED static s32 sUnref801A8054 = TRUE;
f32 D_801A8058 = -600.0f;
s32 gGdUseVtxNormal = TRUE; // @ 801A805C; instead of face normals
UNUSED static s32 sUnrefScnWidth = 320;
UNUSED static s32 sUnrefScnHeight = 240;

// bss
struct GdControl gGdCtrl;     // @ 801B9920; processed controller info
struct GdControl gGdCtrlPrev; // @ 801B9A18; previous frame's controller info

/**
 * Unused main function possibly from when this was a standalone demo
 */
u32 __main__(void) {
    UNUSED u8 filler[4];

    gd_printf("%x, %x\n", (u32) (uintptr_t) &D_801A8058, (u32) (uintptr_t) &gGdMoveScene);
    imin("main");
    gd_init();

    gGdCtrl.unk88 = 0.46799f;
    gGdCtrl.unkA0 = -34.0f;
    gGdCtrl.unkAC = 34.0f;
    gGdCtrl.unk00 = 2;
    gGdCtrl.newStartPress = FALSE;
    gGdCtrl.prevFrame = &gGdCtrlPrev;

    imin("main - make_scene");
    make_scene();  // make_scene does nothing, though
    imout();

    gd_init_controllers();
    print_all_memtrackers();

    start_timer("dlgen");
    stop_timer("dlgen");
    mem_stats();

    while (TRUE) {
        func_801A520C();
    }

    imout();
    return 0;
}
