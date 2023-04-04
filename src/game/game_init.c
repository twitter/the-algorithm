#include <ultra64.h>

#include "sm64.h"
#include "gfx_dimensions.h"
#include "audio/external.h"
#include "buffers/buffers.h"
#include "buffers/gfx_output_buffer.h"
#include "buffers/framebuffers.h"
#include "buffers/zbuffer.h"
#include "engine/level_script.h"
#include "game_init.h"
#include "main.h"
#include "memory.h"
#include "profiler.h"
#include "save_file.h"
#include "seq_ids.h"
#include "sound_init.h"
#include "print.h"
#include "segment2.h"
#include "segment_symbols.h"
#include "rumble_init.h"

// First 3 controller slots
struct Controller gControllers[3];

// Gfx handlers
struct SPTask *gGfxSPTask;
Gfx *gDisplayListHead;
u8 *gGfxPoolEnd;
struct GfxPool *gGfxPool;

// OS Controllers
OSContStatus gControllerStatuses[4];
OSContPad gControllerPads[4];
u8 gControllerBits;
s8 gEepromProbe; // Save Data Probe

// OS Messages
OSMesgQueue gGameVblankQueue;
OSMesgQueue gGfxVblankQueue;
OSMesg gGameMesgBuf[1];
OSMesg gGfxMesgBuf[1];

// Vblank Handler
struct VblankHandler gGameVblankHandler;

// Buffers
uintptr_t gPhysicalFramebuffers[3];
uintptr_t gPhysicalZBuffer;

// Mario Anims and Demo allocation
void *gMarioAnimsMemAlloc;
void *gDemoInputsMemAlloc;
struct DmaHandlerList gMarioAnimsBuf;
struct DmaHandlerList gDemoInputsBuf;

// fillers
UNUSED static u8 sfillerGameInit[0x90];
static s32 sUnusedGameInitValue = 0;

// General timer that runs as the game starts
u32 gGlobalTimer = 0;

// Framebuffer rendering values (max 3)
u16 sRenderedFramebuffer = 0;
u16 sRenderingFramebuffer = 0;

// Goddard Vblank Function Caller
void (*gGoddardVblankCallback)(void) = NULL;

// Defined controller slots
struct Controller *gPlayer1Controller = &gControllers[0];
struct Controller *gPlayer2Controller = &gControllers[1];
struct Controller *gPlayer3Controller = &gControllers[2]; // Probably debug only, see note below

// Title Screen Demo Handler
struct DemoInput *gCurrDemoInput = NULL;
u16 gDemoInputListID = 0;
struct DemoInput gRecordedDemoInput = { 0 };

// Display
// ----------------------------------------------------------------------------------------------------

/**
 * Sets the initial RDP (Reality Display Processor) rendering settings.
 */
void init_rdp(void) {
    gDPPipeSync(gDisplayListHead++);
    gDPPipelineMode(gDisplayListHead++, G_PM_1PRIMITIVE);

    gDPSetScissor(gDisplayListHead++, G_SC_NON_INTERLACE, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    gDPSetCombineMode(gDisplayListHead++, G_CC_SHADE, G_CC_SHADE);

    gDPSetTextureLOD(gDisplayListHead++, G_TL_TILE);
    gDPSetTextureLUT(gDisplayListHead++, G_TT_NONE);
    gDPSetTextureDetail(gDisplayListHead++, G_TD_CLAMP);
    gDPSetTexturePersp(gDisplayListHead++, G_TP_PERSP);
    gDPSetTextureFilter(gDisplayListHead++, G_TF_BILERP);
    gDPSetTextureConvert(gDisplayListHead++, G_TC_FILT);

    gDPSetCombineKey(gDisplayListHead++, G_CK_NONE);
    gDPSetAlphaCompare(gDisplayListHead++, G_AC_NONE);
    gDPSetRenderMode(gDisplayListHead++, G_RM_OPA_SURF, G_RM_OPA_SURF2);
    gDPSetColorDither(gDisplayListHead++, G_CD_MAGICSQ);
    gDPSetCycleType(gDisplayListHead++, G_CYC_FILL);

#ifdef VERSION_SH
    gDPSetAlphaDither(gDisplayListHead++, G_AD_PATTERN);
#endif
    gDPPipeSync(gDisplayListHead++);
}

/**
 * Sets the initial RSP (Reality Signal Processor) settings.
 */
void init_rsp(void) {
    gSPClearGeometryMode(gDisplayListHead++, G_SHADE | G_SHADING_SMOOTH | G_CULL_BOTH | G_FOG
                        | G_LIGHTING | G_TEXTURE_GEN | G_TEXTURE_GEN_LINEAR | G_LOD);

    gSPSetGeometryMode(gDisplayListHead++, G_SHADE | G_SHADING_SMOOTH | G_CULL_BACK | G_LIGHTING);

    gSPNumLights(gDisplayListHead++, NUMLIGHTS_1);
    gSPTexture(gDisplayListHead++, 0, 0, 0, G_TX_RENDERTILE, G_OFF);

    // @bug Failing to set the clip ratio will result in warped triangles in F3DEX2
    // without this change: https://jrra.zone/n64/doc/n64man/gsp/gSPClipRatio.htm
#ifdef F3DEX_GBI_2
    gSPClipRatio(gDisplayListHead++, FRUSTRATIO_1);
#endif
}

/**
 * Initialize the z buffer for the current frame.
 */
void init_z_buffer(void) {
    gDPPipeSync(gDisplayListHead++);

    gDPSetDepthSource(gDisplayListHead++, G_ZS_PIXEL);
    gDPSetDepthImage(gDisplayListHead++, gPhysicalZBuffer);

    gDPSetColorImage(gDisplayListHead++, G_IM_FMT_RGBA, G_IM_SIZ_16b, SCREEN_WIDTH, gPhysicalZBuffer);
    gDPSetFillColor(gDisplayListHead++,
                    GPACK_ZDZ(G_MAXFBZ, 0) << 16 | GPACK_ZDZ(G_MAXFBZ, 0));

    gDPFillRectangle(gDisplayListHead++, 0, BORDER_HEIGHT, SCREEN_WIDTH - 1,
                     SCREEN_HEIGHT - 1 - BORDER_HEIGHT);
}

/**
 * Tells the RDP which of the three framebuffers it shall draw to.
 */
void select_framebuffer(void) {
    gDPPipeSync(gDisplayListHead++);

    gDPSetCycleType(gDisplayListHead++, G_CYC_1CYCLE);
    gDPSetColorImage(gDisplayListHead++, G_IM_FMT_RGBA, G_IM_SIZ_16b, SCREEN_WIDTH,
                     gPhysicalFramebuffers[sRenderingFramebuffer]);
    gDPSetScissor(gDisplayListHead++, G_SC_NON_INTERLACE, 0, BORDER_HEIGHT, SCREEN_WIDTH,
                  SCREEN_HEIGHT - BORDER_HEIGHT);
}

/**
 * Clear the framebuffer and fill it with a 32-bit color.
 * Information about the color argument: https://jrra.zone/n64/doc/n64man/gdp/gDPSetFillColor.htm
 */
void clear_framebuffer(s32 color) {
    gDPPipeSync(gDisplayListHead++);

    gDPSetRenderMode(gDisplayListHead++, G_RM_OPA_SURF, G_RM_OPA_SURF2);
    gDPSetCycleType(gDisplayListHead++, G_CYC_FILL);

    gDPSetFillColor(gDisplayListHead++, color);
    gDPFillRectangle(gDisplayListHead++,
                     GFX_DIMENSIONS_RECT_FROM_LEFT_EDGE(0), BORDER_HEIGHT,
                     GFX_DIMENSIONS_RECT_FROM_RIGHT_EDGE(0) - 1, SCREEN_HEIGHT - BORDER_HEIGHT - 1);

    gDPPipeSync(gDisplayListHead++);

    gDPSetCycleType(gDisplayListHead++, G_CYC_1CYCLE);
}

/**
 * Resets the viewport, readying it for the final image.
 */
void clear_viewport(Vp *viewport, s32 color) {
    s16 vpUlx = (viewport->vp.vtrans[0] - viewport->vp.vscale[0]) / 4 + 1;
    s16 vpUly = (viewport->vp.vtrans[1] - viewport->vp.vscale[1]) / 4 + 1;
    s16 vpLrx = (viewport->vp.vtrans[0] + viewport->vp.vscale[0]) / 4 - 2;
    s16 vpLry = (viewport->vp.vtrans[1] + viewport->vp.vscale[1]) / 4 - 2;

#ifdef WIDESCREEN
    vpUlx = GFX_DIMENSIONS_RECT_FROM_LEFT_EDGE(vpUlx);
    vpLrx = GFX_DIMENSIONS_RECT_FROM_RIGHT_EDGE(SCREEN_WIDTH - vpLrx);
#endif

    gDPPipeSync(gDisplayListHead++);

    gDPSetRenderMode(gDisplayListHead++, G_RM_OPA_SURF, G_RM_OPA_SURF2);
    gDPSetCycleType(gDisplayListHead++, G_CYC_FILL);

    gDPSetFillColor(gDisplayListHead++, color);
    gDPFillRectangle(gDisplayListHead++, vpUlx, vpUly, vpLrx, vpLry);

    gDPPipeSync(gDisplayListHead++);

    gDPSetCycleType(gDisplayListHead++, G_CYC_1CYCLE);
}

/**
 * Draw the horizontal screen borders.
 */
void draw_screen_borders(void) {
    gDPPipeSync(gDisplayListHead++);

    gDPSetScissor(gDisplayListHead++, G_SC_NON_INTERLACE, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    gDPSetRenderMode(gDisplayListHead++, G_RM_OPA_SURF, G_RM_OPA_SURF2);
    gDPSetCycleType(gDisplayListHead++, G_CYC_FILL);

    gDPSetFillColor(gDisplayListHead++, GPACK_RGBA5551(0, 0, 0, 0) << 16 | GPACK_RGBA5551(0, 0, 0, 0));

#if BORDER_HEIGHT != 0
    gDPFillRectangle(gDisplayListHead++, GFX_DIMENSIONS_RECT_FROM_LEFT_EDGE(0), 0,
                     GFX_DIMENSIONS_RECT_FROM_RIGHT_EDGE(0) - 1, BORDER_HEIGHT - 1);
    gDPFillRectangle(gDisplayListHead++,
                     GFX_DIMENSIONS_RECT_FROM_LEFT_EDGE(0), SCREEN_HEIGHT - BORDER_HEIGHT,
                     GFX_DIMENSIONS_RECT_FROM_RIGHT_EDGE(0) - 1, SCREEN_HEIGHT - 1);
#endif
}

/**
 * Defines the viewport scissoring rectangle.
 * Scissoring: https://jrra.zone/n64/doc/pro-man/pro12/12-03.htm#01
 */
void make_viewport_clip_rect(Vp *viewport) {
    s16 vpUlx = (viewport->vp.vtrans[0] - viewport->vp.vscale[0]) / 4 + 1;
    s16 vpPly = (viewport->vp.vtrans[1] - viewport->vp.vscale[1]) / 4 + 1;
    s16 vpLrx = (viewport->vp.vtrans[0] + viewport->vp.vscale[0]) / 4 - 1;
    s16 vpLry = (viewport->vp.vtrans[1] + viewport->vp.vscale[1]) / 4 - 1;

    gDPSetScissor(gDisplayListHead++, G_SC_NON_INTERLACE, vpUlx, vpPly, vpLrx, vpLry);
}

/**
 * Initializes the Fast3D OSTask structure.
 * If you plan on using gSPLoadUcode, make sure to add OS_TASK_LOADABLE to the flags member.
 */
void create_gfx_task_structure(void) {
    s32 entries = gDisplayListHead - gGfxPool->buffer;

    gGfxSPTask->msgqueue = &gGfxVblankQueue;
    gGfxSPTask->msg = (OSMesg) 2;
    gGfxSPTask->task.t.type = M_GFXTASK;
    gGfxSPTask->task.t.ucode_boot = rspF3DBootStart;
    gGfxSPTask->task.t.ucode_boot_size = ((u8 *) rspF3DBootEnd - (u8 *) rspF3DBootStart);
    gGfxSPTask->task.t.flags = 0;
    gGfxSPTask->task.t.ucode = rspF3DStart;
    gGfxSPTask->task.t.ucode_data = rspF3DDataStart;
    gGfxSPTask->task.t.ucode_size = SP_UCODE_SIZE; // (this size is ignored)
    gGfxSPTask->task.t.ucode_data_size = SP_UCODE_DATA_SIZE;
    gGfxSPTask->task.t.dram_stack = (u64 *) gGfxSPTaskStack;
    gGfxSPTask->task.t.dram_stack_size = SP_DRAM_STACK_SIZE8;
    gGfxSPTask->task.t.output_buff = gGfxSPTaskOutputBuffer;
    gGfxSPTask->task.t.output_buff_size =
        (u64 *)((u8 *) gGfxSPTaskOutputBuffer + sizeof(gGfxSPTaskOutputBuffer));
    gGfxSPTask->task.t.data_ptr = (u64 *) &gGfxPool->buffer;
    gGfxSPTask->task.t.data_size = entries * sizeof(Gfx);
    gGfxSPTask->task.t.yield_data_ptr = (u64 *) gGfxSPTaskYieldBuffer;
    gGfxSPTask->task.t.yield_data_size = OS_YIELD_DATA_SIZE;
}

/**
 * Set default RCP (Reality Co-Processor) settings.
 */
void init_rcp(void) {
    move_segment_table_to_dmem();
    init_rdp();
    init_rsp();
    init_z_buffer();
    select_framebuffer();
}

/**
 * End the master display list and initialize the graphics task structure for the next frame to be rendered.
 */
void end_master_display_list(void) {
    draw_screen_borders();
    if (gShowProfiler) {
        draw_profiler();
    }

    gDPFullSync(gDisplayListHead++);
    gSPEndDisplayList(gDisplayListHead++);

    create_gfx_task_structure();
}

/**
 * Draw the bars that appear when the N64 is soft reset.
 */
void draw_reset_bars(void) {
    s32 width, height;
    s32 fbNum;
    u64 *fbPtr;

    if (gResetTimer != 0 && gNmiResetBarsTimer < 15) {
        if (sRenderedFramebuffer == 0) {
            fbNum = 2;
        } else {
            fbNum = sRenderedFramebuffer - 1;
        }

        fbPtr = (u64 *) PHYSICAL_TO_VIRTUAL(gPhysicalFramebuffers[fbNum]);
        fbPtr += gNmiResetBarsTimer++ * (SCREEN_WIDTH / 4);

        for (width = 0; width < ((SCREEN_HEIGHT / 16) + 1); width++) {
            // Loop must be one line to match on -O2
            for (height = 0; height < (SCREEN_WIDTH / 4); height++) *fbPtr++ = 0;
            fbPtr += ((SCREEN_WIDTH / 4) * 14);
        }
    }

    osWritebackDCacheAll();
    osRecvMesg(&gGameVblankQueue, &gMainReceivedMesg, OS_MESG_BLOCK);
    osRecvMesg(&gGameVblankQueue, &gMainReceivedMesg, OS_MESG_BLOCK);
}

/**
 * Initial settings for the first rendered frame.
 */
void render_init(void) {
    gGfxPool = &gGfxPools[0];
    set_segment_base_addr(1, gGfxPool->buffer);
    gGfxSPTask = &gGfxPool->spTask;
    gDisplayListHead = gGfxPool->buffer;
    gGfxPoolEnd = (u8 *)(gGfxPool->buffer + GFX_POOL_SIZE);
    init_rcp();
    clear_framebuffer(0);
    end_master_display_list();
    exec_display_list(&gGfxPool->spTask);

    sRenderingFramebuffer++;
    gGlobalTimer++;
}

/**
 * Selects the location of the F3D output buffer (gDisplayListHead).
 */
void select_gfx_pool(void) {
    gGfxPool = &gGfxPools[gGlobalTimer % ARRAY_COUNT(gGfxPools)];
    set_segment_base_addr(1, gGfxPool->buffer);
    gGfxSPTask = &gGfxPool->spTask;
    gDisplayListHead = gGfxPool->buffer;
    gGfxPoolEnd = (u8 *) (gGfxPool->buffer + GFX_POOL_SIZE);
}

/**
 * This function:
 * - Sends the current master display list out to be rendered.
 * - Tells the VI which color framebuffer to be displayed.
 * - Yields to the VI framerate twice, locking the game at 30 FPS.
 * - Selects which framebuffer will be rendered and displayed to next time.
 */
void display_and_vsync(void) {
    profiler_log_thread5_time(BEFORE_DISPLAY_LISTS);
    osRecvMesg(&gGfxVblankQueue, &gMainReceivedMesg, OS_MESG_BLOCK);
    if (gGoddardVblankCallback != NULL) {
        gGoddardVblankCallback();
        gGoddardVblankCallback = NULL;
    }
    exec_display_list(&gGfxPool->spTask);
    profiler_log_thread5_time(AFTER_DISPLAY_LISTS);
    osRecvMesg(&gGameVblankQueue, &gMainReceivedMesg, OS_MESG_BLOCK);
    osViSwapBuffer((void *) PHYSICAL_TO_VIRTUAL(gPhysicalFramebuffers[sRenderedFramebuffer]));
    profiler_log_thread5_time(THREAD5_END);
    osRecvMesg(&gGameVblankQueue, &gMainReceivedMesg, OS_MESG_BLOCK);
    if (++sRenderedFramebuffer == 3) {
        sRenderedFramebuffer = 0;
    }
    if (++sRenderingFramebuffer == 3) {
        sRenderingFramebuffer = 0;
    }
    gGlobalTimer++;
}

// Controls
// ----------------------------------------------------------------------------------------------------

/**
 * This function records distinct inputs over a 255-frame interval to RAM locations and was likely
 * used to record the demo sequences seen in the final game. This function is unused.
 */
UNUSED static void record_demo(void) {
    // Record the player's button mask and current rawStickX and rawStickY.
    u8 buttonMask =
        ((gPlayer1Controller->buttonDown & (A_BUTTON | B_BUTTON | Z_TRIG | START_BUTTON)) >> 8)
        | (gPlayer1Controller->buttonDown & (U_CBUTTONS | D_CBUTTONS | L_CBUTTONS | R_CBUTTONS));
    s8 rawStickX = gPlayer1Controller->rawStickX;
    s8 rawStickY = gPlayer1Controller->rawStickY;

    // If the stick is in deadzone, set its value to 0 to
    // nullify the effects. We do not record deadzone inputs.
    if (rawStickX > -8 && rawStickX < 8) {
        rawStickX = 0;
    }

    if (rawStickY > -8 && rawStickY < 8) {
        rawStickY = 0;
    }

    // Rrecord the distinct input and timer so long as they are unique.
    // If the timer hits 0xFF, reset the timer for the next demo input.
    if (gRecordedDemoInput.timer == 0xFF || buttonMask != gRecordedDemoInput.buttonMask
        || rawStickX != gRecordedDemoInput.rawStickX || rawStickY != gRecordedDemoInput.rawStickY) {
        gRecordedDemoInput.timer = 0;
        gRecordedDemoInput.buttonMask = buttonMask;
        gRecordedDemoInput.rawStickX = rawStickX;
        gRecordedDemoInput.rawStickY = rawStickY;
    }
    gRecordedDemoInput.timer++;
}

/**
 * Take the updated controller struct and calculate the new x, y, and distance floats.
 */
void adjust_analog_stick(struct Controller *controller) {
    UNUSED u8 filler[8];

    // Reset the controller's x and y floats.
    controller->stickX = 0;
    controller->stickY = 0;

    // Modulate the rawStickX and rawStickY to be the new f32 values by adding/subtracting 6.
    if (controller->rawStickX <= -8) {
        controller->stickX = controller->rawStickX + 6;
    }

    if (controller->rawStickX >= 8) {
        controller->stickX = controller->rawStickX - 6;
    }

    if (controller->rawStickY <= -8) {
        controller->stickY = controller->rawStickY + 6;
    }

    if (controller->rawStickY >= 8) {
        controller->stickY = controller->rawStickY - 6;
    }

    // Calculate f32 magnitude from the center by vector length.
    controller->stickMag =
        sqrtf(controller->stickX * controller->stickX + controller->stickY * controller->stickY);

    // Magnitude cannot exceed 64.0f: if it does, modify the values
    // appropriately to flatten the values down to the allowed maximum value.
    if (controller->stickMag > 64) {
        controller->stickX *= 64 / controller->stickMag;
        controller->stickY *= 64 / controller->stickMag;
        controller->stickMag = 64;
    }
}

/**
 * If a demo sequence exists, this will run the demo input list until it is complete.
 */
void run_demo_inputs(void) {
    // Eliminate the unused bits.
    gControllers[0].controllerData->button &= VALID_BUTTONS;

    // Check if a demo inputs list exists and if so,
    // run the active demo input list.
    if (gCurrDemoInput != NULL) {
        // Clear player 2's inputs if they exist. Player 2's controller
        // cannot be used to influence a demo. At some point, Nintendo
        // may have planned for there to be a demo where 2 players moved
        // around instead of just one, so clearing player 2's influence from
        // the demo had to have been necessary to perform this. Co-op mode, perhaps?
        if (gControllers[1].controllerData != NULL) {
            gControllers[1].controllerData->stick_x = 0;
            gControllers[1].controllerData->stick_y = 0;
            gControllers[1].controllerData->button = 0;
        }

        // The timer variable being 0 at the current input means the demo is over.
        // Set the button to the END_DEMO mask to end the demo.
        if (gCurrDemoInput->timer == 0) {
            gControllers[0].controllerData->stick_x = 0;
            gControllers[0].controllerData->stick_y = 0;
            gControllers[0].controllerData->button = END_DEMO;
        } else {
            // Backup the start button if it is pressed, since we don't want the
            // demo input to override the mask where start may have been pressed.
            u16 startPushed = gControllers[0].controllerData->button & START_BUTTON;

            // Perform the demo inputs by assigning the current button mask and the stick inputs.
            gControllers[0].controllerData->stick_x = gCurrDemoInput->rawStickX;
            gControllers[0].controllerData->stick_y = gCurrDemoInput->rawStickY;

            // To assign the demo input, the button information is stored in
            // an 8-bit mask rather than a 16-bit mask. this is because only
            // A, B, Z, Start, and the C-Buttons are used in a demo, as bits
            // in that order. In order to assign the mask, we need to take the
            // upper 4 bits (A, B, Z, and Start) and shift then left by 8 to
            // match the correct input mask. We then add this to the masked
            // lower 4 bits to get the correct button mask.
            gControllers[0].controllerData->button =
                ((gCurrDemoInput->buttonMask & 0xF0) << 8) + ((gCurrDemoInput->buttonMask & 0xF));

            // If start was pushed, put it into the demo sequence being input to end the demo.
            gControllers[0].controllerData->button |= startPushed;

            // Run the current demo input's timer down. if it hits 0, advance the demo input list.
            if (--gCurrDemoInput->timer == 0) {
                gCurrDemoInput++;
            }
        }
    }
}

/**
 * Update the controller struct with available inputs if present.
 */
void read_controller_inputs(void) {
    s32 i;

    // If any controllers are plugged in, update the controller information.
    if (gControllerBits) {
        osRecvMesg(&gSIEventMesgQueue, &gMainReceivedMesg, OS_MESG_BLOCK);
        osContGetReadData(&gControllerPads[0]);
#if ENABLE_RUMBLE
        release_rumble_pak_control();
#endif
    }
    run_demo_inputs();

    for (i = 0; i < 2; i++) {
        struct Controller *controller = &gControllers[i];

        // if we're receiving inputs, update the controller struct with the new button info.
        if (controller->controllerData != NULL) {
            controller->rawStickX = controller->controllerData->stick_x;
            controller->rawStickY = controller->controllerData->stick_y;
            controller->buttonPressed = controller->controllerData->button
                                        & (controller->controllerData->button ^ controller->buttonDown);
            // 0.5x A presses are a good meme
            controller->buttonDown = controller->controllerData->button;
            adjust_analog_stick(controller);
        } else { // otherwise, if the controllerData is NULL, 0 out all of the inputs.
            controller->rawStickX = 0;
            controller->rawStickY = 0;
            controller->buttonPressed = 0;
            controller->buttonDown = 0;
            controller->stickX = 0;
            controller->stickY = 0;
            controller->stickMag = 0;
        }
    }

    // For some reason, player 1's inputs are copied to player 3's port.
    // This potentially may have been a way the developers "recorded"
    // the inputs for demos, despite record_demo existing.
    gPlayer3Controller->rawStickX = gPlayer1Controller->rawStickX;
    gPlayer3Controller->rawStickY = gPlayer1Controller->rawStickY;
    gPlayer3Controller->stickX = gPlayer1Controller->stickX;
    gPlayer3Controller->stickY = gPlayer1Controller->stickY;
    gPlayer3Controller->stickMag = gPlayer1Controller->stickMag;
    gPlayer3Controller->buttonPressed = gPlayer1Controller->buttonPressed;
    gPlayer3Controller->buttonDown = gPlayer1Controller->buttonDown;
}

/**
 * Initialize the controller structs to point at the OSCont information.
 */
void init_controllers(void) {
    s16 port, cont;

    // Set controller 1 to point to the set of status/pads for input 1 and
    // init the controllers.
    gControllers[0].statusData = &gControllerStatuses[0];
    gControllers[0].controllerData = &gControllerPads[0];
    osContInit(&gSIEventMesgQueue, &gControllerBits, &gControllerStatuses[0]);

    // Strangely enough, the EEPROM probe for save data is done in this function.
    // Save Pak detection?
    gEepromProbe = osEepromProbe(&gSIEventMesgQueue);

    // Loop over the 4 ports and link the controller structs to the appropriate
    // status and pad. Interestingly, although there are pointers to 3 controllers,
    // only 2 are connected here. The third seems to have been reserved for debug
    // purposes and was never connected in the retail ROM, thus gPlayer3Controller
    // cannot be used, despite being referenced in various code.
    for (cont = 0, port = 0; port < 4 && cont < 2; port++) {
        // Is controller plugged in?
        if (gControllerBits & (1 << port)) {
            // The game allows you to have just 1 controller plugged
            // into any port in order to play the game. this was probably
            // so if any of the ports didn't work, you can have controllers
            // plugged into any of them and it will work.
#if ENABLE_RUMBLE
            gControllers[cont].port = port;
#endif
            gControllers[cont].statusData = &gControllerStatuses[port];
            gControllers[cont++].controllerData = &gControllerPads[port];
        }
    }
}

// Game thread core
// ----------------------------------------------------------------------------------------------------

/**
 * Setup main segments and framebuffers.
 */
void setup_game_memory(void) {
    UNUSED u8 filler[8];

    // Setup general Segment 0
    set_segment_base_addr(0, (void *) 0x80000000);
    // Create Mesg Queues
    osCreateMesgQueue(&gGfxVblankQueue, gGfxMesgBuf, ARRAY_COUNT(gGfxMesgBuf));
    osCreateMesgQueue(&gGameVblankQueue, gGameMesgBuf, ARRAY_COUNT(gGameMesgBuf));
    // Setup z buffer and framebuffer
    gPhysicalZBuffer = VIRTUAL_TO_PHYSICAL(gZBuffer);
    gPhysicalFramebuffers[0] = VIRTUAL_TO_PHYSICAL(gFramebuffer0);
    gPhysicalFramebuffers[1] = VIRTUAL_TO_PHYSICAL(gFramebuffer1);
    gPhysicalFramebuffers[2] = VIRTUAL_TO_PHYSICAL(gFramebuffer2);
    // Setup Mario Animations
    gMarioAnimsMemAlloc = main_pool_alloc(0x4000, MEMORY_POOL_LEFT);
    set_segment_base_addr(17, (void *) gMarioAnimsMemAlloc);
    setup_dma_table_list(&gMarioAnimsBuf, gMarioAnims, gMarioAnimsMemAlloc);
    // Setup Demo Inputs List
    gDemoInputsMemAlloc = main_pool_alloc(0x800, MEMORY_POOL_LEFT);
    set_segment_base_addr(24, (void *) gDemoInputsMemAlloc);
    setup_dma_table_list(&gDemoInputsBuf, gDemoInputs, gDemoInputsMemAlloc);
    // Setup Level Script Entry
    load_segment(0x10, _entrySegmentRomStart, _entrySegmentRomEnd, MEMORY_POOL_LEFT);
    // Setup Segment 2 (Fonts, Text, etc)
    load_segment_decompress(2, _segment2_mio0SegmentRomStart, _segment2_mio0SegmentRomEnd);
}

/**
 * Main game loop thread. Runs forever as long as the game continues.
 */
void thread5_game_loop(UNUSED void *arg) {
    struct LevelCommand *addr;

    setup_game_memory();
#if ENABLE_RUMBLE
    init_rumble_pak_scheduler_queue();
#endif
    init_controllers();
#if ENABLE_RUMBLE
    create_thread_6();
#endif
    save_file_load_all();

    set_vblank_handler(2, &gGameVblankHandler, &gGameVblankQueue, (OSMesg) 1);

    // Point address to the entry point into the level script data.
    addr = segmented_to_virtual(level_script_entry);

    play_music(SEQ_PLAYER_SFX, SEQUENCE_ARGS(0, SEQ_SOUND_PLAYER), 0);
    set_sound_mode(save_file_get_sound_mode());
    render_init();

    while (TRUE) {
        // If the reset timer is active, run the process to reset the game.
        if (gResetTimer != 0) {
            draw_reset_bars();
            continue;
        }
        profiler_log_thread5_time(THREAD5_START);

        // If any controllers are plugged in, start read the data for when
        // read_controller_inputs is called later.
        if (gControllerBits) {
#if ENABLE_RUMBLE
            block_until_rumble_pak_free();
#endif
            osContStartReadData(&gSIEventMesgQueue);
        }

        audio_game_loop_tick();
        select_gfx_pool();
        read_controller_inputs();
        addr = level_script_execute(addr);

        display_and_vsync();

        // when debug info is enabled, print the "BUF %d" information.
        if (gShowDebugText) {
            // subtract the end of the gfx pool with the display list to obtain the
            // amount of free space remaining.
            print_text_fmt_int(180, 20, "BUF %d", gGfxPoolEnd - (u8 *) gDisplayListHead);
        }
    }
}
