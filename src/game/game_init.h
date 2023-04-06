#ifndef _GAME_INIT_H_
#define _GAME_INIT_H_

#include "memory.h"

struct DemoInput
{
    u8 timer; // time until next input. if this value is 0, it means the demo is over
    s8 rawStickX;
    s8 rawStickY;
    u8 buttonMask;
};

extern struct Controller gControllers[3];
extern OSContStatus gControllerStatuses[4];
extern OSContPad gControllerPads[4];
extern OSMesgQueue gGameVblankQueue;
extern OSMesgQueue D_80339CB8;
extern OSMesg D_80339CD0;
extern OSMesg D_80339CD4;
extern struct VblankHandler gGameVblankHandler;
extern uintptr_t gPhysicalFrameBuffers[3];
extern uintptr_t gPhysicalZBuffer;
extern void *D_80339CF0;
extern void *D_80339CF4;
extern struct SPTask *gGfxSPTask;
extern Gfx *gDisplayListHead;
extern u8 *gGfxPoolEnd;
extern struct GfxPool *gGfxPool;
extern u8 gControllerBits;
extern s8 gEepromProbe;

extern void (*D_8032C6A0)(void);
extern struct Controller *gPlayer1Controller;
extern struct Controller *gPlayer2Controller;
extern struct Controller *gPlayer3Controller;
extern struct DemoInput *gCurrDemoInput;
extern u16 gDemoInputListID;
extern struct DemoInput gRecordedDemoInput;

extern void init_controllers(void);
extern void setup_game_memory(void);
extern void thread5_game_loop(void *);
extern u16 func_802495B0(u32);

// this area is the demo input + the header. when the demo is loaded in, there is a header the size
// of a single word next to the input list. this word is the current ID count.
extern struct MarioAnimation D_80339D10;
extern struct MarioAnimation gDemo;

extern u8 gMarioAnims[];
extern u8 gDemoInputs[];

#define GFX_POOL_SIZE 6400

struct GfxPool {
    Gfx buffer[GFX_POOL_SIZE];
    struct SPTask spTask;
};

extern u16 frameBufferIndex;
extern u32 gGlobalTimer;

// extern ? my_rdp_init(?);
// extern ? my_rsp_init(?);
// extern ? clear_z_buffer(?);
// extern ? display_frame_buffer(?);
extern void clear_frame_buffer(s32);
extern void clear_viewport(Vp *, s32);
// extern ? draw_screen_borders(?);
void make_viewport_clip_rect(Vp *viewport);
extern void init_render_image(void);
extern void end_master_display_list(void);
extern void draw_reset_bars(void);
extern void rendering_init(void);
extern void config_gfx_pool(void);
extern void display_and_vsync(void);

#endif
