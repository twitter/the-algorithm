#ifndef GAME_INIT_H
#define GAME_INIT_H

#include <PR/ultratypes.h>
#include <PR/gbi.h>
#include <PR/os_cont.h>
#include <PR/os_message.h>

#include "types.h"
#include "memory.h"

#define GFX_POOL_SIZE 6400 // Size of how large the master display list (gDisplayListHead) can be

struct GfxPool {
    Gfx buffer[GFX_POOL_SIZE];
    struct SPTask spTask;
};

struct DemoInput {
    u8 timer; // time until next input. if this value is 0, it means the demo is over
    s8 rawStickX;
    s8 rawStickY;
    u8 buttonMask;
};

extern struct Controller gControllers[3];
extern OSContStatus gControllerStatuses[4];
extern OSContPad gControllerPads[4];
extern OSMesgQueue gGameVblankQueue;
extern OSMesgQueue gGfxVblankQueue;
extern OSMesg gGameMesgBuf[1];
extern OSMesg gGfxMesgBuf[1];
extern struct VblankHandler gGameVblankHandler;
extern uintptr_t gPhysicalFramebuffers[3];
extern uintptr_t gPhysicalZBuffer;
extern void *gMarioAnimsMemAlloc;
extern void *gDemoInputsMemAlloc;
extern struct SPTask *gGfxSPTask;
extern Gfx *gDisplayListHead;
extern u8 *gGfxPoolEnd;
extern struct GfxPool *gGfxPool;
extern u8 gControllerBits;
extern s8 gEepromProbe;

extern void (*gGoddardVblankCallback)(void);
extern struct Controller *gPlayer1Controller;
extern struct Controller *gPlayer2Controller;
extern struct Controller *gPlayer3Controller;
extern struct DemoInput *gCurrDemoInput;
extern u16 gDemoInputListID;
extern struct DemoInput gRecordedDemoInput;

// this area is the demo input + the header. when the demo is loaded in, there is a header the size
// of a single word next to the input list. this word is the current ID count.
extern struct DmaHandlerList gMarioAnimsBuf;
extern struct DmaHandlerList gDemoInputsBuf;

extern u8 gMarioAnims[];
extern u8 gDemoInputs[];

extern u16 sRenderingFramebuffer;
extern u32 gGlobalTimer;

void setup_game_memory(void);
void thread5_game_loop(UNUSED void *arg);
void clear_framebuffer(s32 color);
void clear_viewport(Vp *viewport, s32 color);
void make_viewport_clip_rect(Vp *viewport);
void init_rcp(void);
void end_master_display_list(void);
void render_init(void);
void select_gfx_pool(void);
void display_and_vsync(void);

#endif // GAME_INIT_H
