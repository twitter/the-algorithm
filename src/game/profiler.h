#ifndef PROFILER_H
#define PROFILER_H

#include <PR/ultratypes.h>
#include <PR/os_time.h>

#include "types.h"

extern u64 osClockRate;

struct ProfilerFrameData {
    /* 0x00 */ s16 numSoundTimes;
    /* 0x02 */ s16 numVblankTimes;
    // gameTimes:
    // 0: thread 5 start
    // 1: level script execution
    // 2: render
    // 3: display lists
    // 4: thread 4 end (0 terminated)
    /* 0x08 */ OSTime gameTimes[5];
    // gfxTimes:
    // 0: processors queued
    // 1: rsp completed
    // 2: rdp completed
    /* 0x30 */ OSTime gfxTimes[3];
    /* 0x48 */ OSTime soundTimes[8];
    /* 0x88 */ OSTime vblankTimes[8];
};

// thread event IDs
enum ProfilerGameEvent {
    THREAD5_START,
    LEVEL_SCRIPT_EXECUTE,
    BEFORE_DISPLAY_LISTS,
    AFTER_DISPLAY_LISTS,
    THREAD5_END
};

enum ProfilerGfxEvent {
    TASKS_QUEUED,
    RSP_COMPLETE,
    RDP_COMPLETE
};

void profiler_log_thread5_time(enum ProfilerGameEvent eventID);
void profiler_log_thread4_time(void);
void profiler_log_gfx_time(enum ProfilerGfxEvent eventID);
void profiler_log_vblank_time(void);
void draw_profiler(void);

#endif // PROFILER_H
