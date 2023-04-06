#ifndef GD_MAIN_H
#define GD_MAIN_H

#include <ultra64.h>


// In various files of the Goddard subsystem, there are miscellaneous
// unused rodata strings. These are likely byproducts of a printf macro
// that was stubbed out as "#define printf", letting printf calls expand
// to no-op comma expressions. (IDO doesn't support variadic macros, so
// "#define printf(...) /* nothing */" wasn't an option.)
// This macro is separate from the gd_printf function; one probably
// forwarded to the other, but it is hard to tell in which direction.
#ifdef __GNUC__
#define printf(...)                                       \
    _Pragma ("GCC diagnostic push")                       \
    _Pragma ("GCC diagnostic ignored \"-Wunused-value\"") \
    (__VA_ARGS__);                                        \
    _Pragma ("GCC diagnostic pop")
#else
#define printf
#endif

// structs
struct GdControl { // gGdCtrl
    /* 0x00 */ s32 unk00;
    /* 0x04 */ u8  pad04[4];
    /* 0x08 */ s32 dleft; // Dpad-left (mask)
    /* 0x0C */ s32 dright; // Dpad-right (mask)
    /* 0x10 */ s32 dup; // Dpad-up (mask)
    /* 0x14 */ s32 ddown; // Dpad-down (mask)
    /* 0x18 */ s32 cleft; // bool C-left
    /* 0x1C */ s32 cright; // bool C-right
    /* 0x20 */ s32 cup; // bool C-up
    /* 0x24 */ s32 cdown; // bool C-down
    /* 0x28 */ void * unk28;     // null-checked ptr? symbol not deref-ed in extant code?
    /* 0x2C */ void * unk2C;     // some sort of old texture ptr? symbol not deref-ed in extant code?
    /* 0x30 */ void * unk30;     // null-checked ptr? symbol not deref-ed in extant code?
    /* 0x34 */ s32 btnA; // bool A button
    /* 0x38 */ s32 btnB; // bool B button
    /* 0x3C */ u8  pad3C[0x44-0x3C];
    /* 0x44 */ s32 trgL; // bool L trigger pressed
    /* 0x48 */ s32 trgR; // bool R trigger pressed
    /* 0x4C */ s32 unk4C;
    /* 0x50 */ s32 unk50;
    /* 0x54 */ s32 newStartPress; // toggle bit? start pressed?
    /* 0x58 */ u8  pad58[0x7C-0x58];
    /* 0x7C */ f32 stickXf;
    /* 0x80 */ f32 stickYf;
    /* 0x84 */ u8  pad84[4];
    /* 0x88 */ f32 unk88;
    /* 0x8C */ u8  pad8c[0xA0-0x8C];
    /* 0xA0 */ f32 unkA0;
    /* 0xA4 */ u8  padA4[0xAC-0xA4];
    /* 0xAC */ f32 unkAC;
    /* 0xB0 */ u8  padB0[0xB8-0xB0];
    /* 0xB8 */ s32 csrXatApress; // cursor x position when there was a new (A) press?
    /* 0xBC */ s32 csrYatApress; // cursor y position when there was a new (A) press?
    /* 0xC0 */ s32 stickDeltaX;
    /* 0xC4 */ s32 stickDeltaY;
    /* 0xC8 */ s32 stickX;
    /* 0xCC */ s32 stickY;
    /* 0xD0 */ s32 csrX; // bounded by screen view
    /* 0xD4 */ s32 csrY; // bounded by screen view
    /* 0xD8 */ /* hand/cursor state bitfield? */
        /* b80 */ u8 btnApressed : 1;  // bool (A) pressed
        /* b40 */ u8 unkD8b40 : 1; // set to FALSE and unused
        /* b20 */ u8 unkD8b20 : 1; // set to FALSE and unused
        /* b10 */ u8 btnAnewPress : 1;  // bool new (A) press
        /* b08 */ u8 unkD8b08 : 1;
        /* b04 */ u8 unkD8b04 : 1;
        /* b02 */ u8 AbtnPressWait : 1;  // bool 10 frames between (A) presses (cursor cool down?)
    /* 0xDC */ u32 frameAbtnPressed; // first frame of new a press
    /* 0xE0 */ u8  padE0[0xE8-0xE0];
    /* 0xE8 */ u32 frameCount; // frame count?
    /* 0xEC */ u8  padEC[0xF0-0xEC];
    /* 0xF0 */ struct GdControl *prevFrame; // previous frame data
};

// data
extern s32 gGdMoveScene;
extern f32 D_801A8058;
extern s32 gGdUseVtxNormal;

// bss
extern struct GdControl gGdCtrl;
extern struct GdControl gGdCtrlPrev;

#endif /* GD_MAIN_H */
