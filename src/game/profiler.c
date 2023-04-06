#include <ultra64.h>

#include "sm64.h"
#include "profiler.h"
#include "game_init.h"

s16 gProfilerMode = 0;

// the thread 3 info is logged on the opposite profiler from what is used by
// the thread4 and 5 loggers. It's likely because the sound thread runs at a
// much faster rate and shouldn't be flipping the index for the "slower" game
// threads, which could leave the frame data in a possibly corrupt or incomplete
// state.
s16 gCurrentFrameIndex1 = 1;
s16 gCurrentFrameIndex2 = 0;

struct ProfilerFrameData gProfilerFrameData[2];

// log the current osTime to the appropriate idx for current thread5 processes.
void profiler_log_thread5_time(enum ProfilerGameEvent eventID) {
    gProfilerFrameData[gCurrentFrameIndex1].gameTimes[eventID] = osGetTime();

    // event ID 4 is the last profiler event for after swapping
    // buffers: switch the Info after updating.
    if (eventID == THREAD5_END) {
        gCurrentFrameIndex1 ^= 1;
        gProfilerFrameData[gCurrentFrameIndex1].numSoundTimes = 0;
    }
}

// log the audio system before and after osTimes in pairs to the soundTimes array.
void profiler_log_thread4_time(void) {
    struct ProfilerFrameData *profiler = &gProfilerFrameData[gCurrentFrameIndex1];

    if (profiler->numSoundTimes < ARRAY_COUNT(profiler->soundTimes)) {
        profiler->soundTimes[profiler->numSoundTimes++] = osGetTime();
    }
}

// log the times for gfxTimes: RSP completes, and RDP completes.
void profiler_log_gfx_time(enum ProfilerGfxEvent eventID) {
    if (eventID == 0) {
        gCurrentFrameIndex2 ^= 1;
        gProfilerFrameData[gCurrentFrameIndex2].numVblankTimes = 0;
    }

    gProfilerFrameData[gCurrentFrameIndex2].gfxTimes[eventID] = osGetTime();
}

// log the times between vblank started and ended.
void profiler_log_vblank_time(void) {
    struct ProfilerFrameData *profiler = &gProfilerFrameData[gCurrentFrameIndex2];

    if (profiler->numVblankTimes < ARRAY_COUNT(profiler->vblankTimes)) {
        profiler->vblankTimes[profiler->numVblankTimes++] = osGetTime();
    }
}

// draw the specified profiler given the information passed.
void draw_profiler_bar(OSTime clock_base, OSTime clock_start, OSTime clock_end, s16 pos_y, u16 color) {
    s64 duration_start, duration_end;
    s32 rect_x1, rect_x2;

    // set the duration to start, and floor to 0 if the result is below 0.
    if ((duration_start = clock_start - clock_base) < 0) {
        duration_start = 0;
    }
    // like the above, but with end.
    if ((duration_end = clock_end - clock_base) < 0) {
        duration_end = 0;
    }

    // calculate the x coordinates of where start and end begins, respectively.
    rect_x1 = ((((duration_start * 1000000) / osClockRate * 3) / 1000) + 30);
    rect_x2 = ((((duration_end * 1000000) / osClockRate * 3) / 1000) + 30);

    //! I believe this is supposed to cap rect_x1 and rect_x2 to 320, but the
    //  code seems to use the wrong variables... it's possible that the variable
    //  names were very similar within a single letter.
    if (rect_x1 > 319) {
        clock_start = 319;
    }
    if (rect_x2 > 319) {
        clock_end = 319;
    }

    // perform the render if start is less than end. in most cases, it should be.
    if (rect_x1 < rect_x2) {
        gDPPipeSync(gDisplayListHead++);
        gDPSetFillColor(gDisplayListHead++, color << 16 | color);
        gDPFillRectangle(gDisplayListHead++, rect_x1, pos_y, rect_x2, pos_y + 2);
    }
}

void draw_reference_profiler_bars(void) {
    // Draws the reference "max" bars underneath the real thing.

    // Blue
    gDPPipeSync(gDisplayListHead++);
    gDPSetFillColor(gDisplayListHead++,
                    GPACK_RGBA5551(40, 80, 255, 1) << 16 | GPACK_RGBA5551(40, 80, 255, 1));
    gDPFillRectangle(gDisplayListHead++, 30, 220, 79, 222);

    // Yellow
    gDPPipeSync(gDisplayListHead++);
    gDPSetFillColor(gDisplayListHead++,
                    GPACK_RGBA5551(255, 255, 40, 1) << 16 | GPACK_RGBA5551(255, 255, 40, 1));
    gDPFillRectangle(gDisplayListHead++, 79, 220, 128, 222);

    // Orange
    gDPPipeSync(gDisplayListHead++);
    gDPSetFillColor(gDisplayListHead++,
                    GPACK_RGBA5551(255, 120, 40, 1) << 16 | GPACK_RGBA5551(255, 120, 40, 1));
    gDPFillRectangle(gDisplayListHead++, 128, 220, 177, 222);

    // Red
    gDPPipeSync(gDisplayListHead++);
    gDPSetFillColor(gDisplayListHead++,
                    GPACK_RGBA5551(255, 40, 40, 1) << 16 | GPACK_RGBA5551(255, 40, 40, 1));
    gDPFillRectangle(gDisplayListHead++, 177, 220, 226, 222);
}

/*
  Draw Profiler Mode 1. This mode draws a traditional per-event bar for durations of tasks
  recorded by the profiler calls of various threads.

  Information:

  (yellow): Level Scripts Execution
  (orange): Rendering
  (blue): Display Lists Send
  (red): Sound Updates
  (yellow): Time from SP tasks queued to RSP complete
  (orange): Time from RSP complete to RDP complete (possibly bugged, see //! note below)
  (red): VBlank Times
*/
void draw_profiler_mode_1(void) {
    s32 i;
    struct ProfilerFrameData *profiler;
    OSTime clock_base;

    // the profiler logs 2 frames of data: last frame and current frame. Indexes are used
    // to keep track of the current frame, so the index is xor'd to retrieve the last frame's
    // data.
    profiler = &gProfilerFrameData[gCurrentFrameIndex1 ^ 1];

    // calculate the clock_base.
    clock_base = profiler->soundTimes[0] - (16433 * osClockRate / 1000000);

    // draw the profiler for the time it takes for level scripts to execute. (yellow)
    draw_profiler_bar(clock_base, profiler->gameTimes[0], profiler->gameTimes[1], 212,
                      GPACK_RGBA5551(255, 255, 40, 1));

    // draw the profiler for the time it takes for the game to render (between level scripts and
    // pre-display lists). (orange)
    draw_profiler_bar(clock_base, profiler->gameTimes[1], profiler->gameTimes[2], 212,
                      GPACK_RGBA5551(255, 120, 40, 1));

    // draw the profiler for the time it takes for the display lists to send. (blue)
    draw_profiler_bar(clock_base, profiler->gameTimes[2], profiler->gameTimes[3], 212,
                      GPACK_RGBA5551(40, 192, 230, 1));

    // we need to get the amount of finished numSoundTimes pairs, so get rid of the odd bit to get the
    // limit of finished pairs.
    profiler->numSoundTimes &= 0xFFFE;

    // draw the sound update times. (red)
    for (i = 0; i < profiler->numSoundTimes; i += 2) {
        draw_profiler_bar(clock_base, profiler->soundTimes[i], profiler->soundTimes[i + 1], 212,
                          GPACK_RGBA5551(255, 40, 40, 1));
    }

    //! RSP and RDP run in parallel, so while they are not absolutely guaranteed to return in order,
    //  it is theoretically possible they might not. In all cases, the RDP should finish later than RSP.
    //  Thus, this is not really a bug in practice, but should still be noted that the C doesn't check
    //  this.
    draw_profiler_bar(clock_base, profiler->gfxTimes[0], profiler->gfxTimes[1], 216,
                      GPACK_RGBA5551(255, 255, 40, 1));
    // (orange)
    draw_profiler_bar(clock_base, profiler->gfxTimes[1], profiler->gfxTimes[2], 216,
                      GPACK_RGBA5551(255, 120, 40, 1));

    // like earlier, toss the odd bit.
    profiler->numVblankTimes &= 0xFFFE;

    // render the vblank time pairs. (red)
    for (i = 0; i < profiler->numVblankTimes; i += 2) {
        draw_profiler_bar(clock_base, profiler->vblankTimes[i], profiler->vblankTimes[i + 1], 216,
                          GPACK_RGBA5551(255, 40, 40, 1));
    }

    draw_reference_profiler_bars();
}

/*
  Draw Profiler Mode 0. This mode renders bars over each other to make it
  easier to see which processes take the longest.

  Information:

  (red): Sound Updates
  (yellow): Level Script Execution
  (orange): Rendering
  (orange): RDP Duration
  (yellow): RSP Duration
  (red): VBlank Duration
*/
void draw_profiler_mode_0(void) {
    s32 i;
    struct ProfilerFrameData *profiler;

    u64 clock_start;
    u64 level_script_duration;
    u64 render_duration;
    u64 task_start;
    u64 rsp_duration;
    u64 rdp_duration;
    u64 vblank;
    u64 sound_duration;

    // get the last frame profiler. gCurrentFrameIndex1 has the current frame being processed, so
    // xor it to get the last frame profiler.
    profiler = &gProfilerFrameData[gCurrentFrameIndex1 ^ 1];

    // was thread 5 ran before thread 4? set the lower one to be the clock_start.
    clock_start = profiler->gameTimes[0] <= profiler->soundTimes[0] ? profiler->gameTimes[0]
                                                                    : profiler->soundTimes[0];

    // set variables for duration of tasks.
    level_script_duration = profiler->gameTimes[1] - clock_start;
    render_duration = profiler->gameTimes[2] - profiler->gameTimes[1];
    task_start = 0;
    rsp_duration = profiler->gfxTimes[1] - profiler->gfxTimes[0];
    rdp_duration = profiler->gfxTimes[2] - profiler->gfxTimes[0];
    vblank = 0;

    // like above functions, toss the odd bit.
    profiler->numSoundTimes &= 0xFFFE;

    // sound duration seems to be rendered with empty space and not actually drawn.
    for (i = 0; i < profiler->numSoundTimes; i += 2) {
        // calculate sound duration of max - min
        sound_duration = profiler->soundTimes[i + 1] - profiler->soundTimes[i];
        task_start += sound_duration;
        // was the sound time minimum less than level script execution?
        if (profiler->soundTimes[i] < profiler->gameTimes[1]) {
            // overlay the level_script_duration onto the profiler by subtracting the sound_duration.
            level_script_duration -= sound_duration;
        } else if (profiler->soundTimes[i] < profiler->gameTimes[2]) {
            // overlay the render_duration onto the profiler by subtracting the sound_duration.
            render_duration -= sound_duration;
        }
    }

    // same as above. toss the odd bit.
    profiler->numSoundTimes &= 0xFFFE;

    //! wrong index used to retrieve vblankTimes, thus empty pairs can
    //  potentially be passed to draw_profiler_bar, because it could be sending
    //  pairs that are beyond the numVblankTimes enforced non-odd limit, due to
    //  using the wrong num value.
    for (i = 0; i < profiler->numSoundTimes; i += 2) {
        vblank += (profiler->vblankTimes[i + 1] - profiler->vblankTimes[i]);
    }

    // Draw top profilers.

    // draw sound duration as the first bar. (red)
    clock_start = 0;
    draw_profiler_bar(0, clock_start, clock_start + task_start, 212, GPACK_RGBA5551(255, 40, 40, 1));

    // draw level script execution duration. (yellow)
    clock_start += task_start;
    draw_profiler_bar(0, clock_start, clock_start + level_script_duration, 212,
                      GPACK_RGBA5551(255, 255, 40, 1));

    // draw render duration. (orange)
    clock_start += level_script_duration;
    draw_profiler_bar(0, clock_start, clock_start + render_duration, 212,
                      GPACK_RGBA5551(255, 120, 40, 1));

    // Draw bottom profilers.

    // rdp duration (orange)
    draw_profiler_bar(0, 0, rdp_duration, 216, GPACK_RGBA5551(255, 120, 40, 1));
    // rsp duration (yellow)
    draw_profiler_bar(0, 0, rsp_duration, 216, GPACK_RGBA5551(255, 255, 40, 1));
    // vblank duration (red)
    draw_profiler_bar(0, 0, vblank, 216, GPACK_RGBA5551(255, 40, 40, 1));

    draw_reference_profiler_bars();
}

// Draw the Profiler per frame. Toggle the mode if the player presses L while this
// renderer is active.
void draw_profiler(void) {
    if (gPlayer1Controller->buttonPressed & L_TRIG) {
        gProfilerMode ^= 1;
    }

    if (gProfilerMode == 0) {
        draw_profiler_mode_0();
    } else {
        draw_profiler_mode_1();
    }
}
