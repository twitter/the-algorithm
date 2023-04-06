#include <ultra64.h>
#include "macros.h"

#include "buffers/buffers.h"
#include "main.h"
#include "thread6.h"

#ifdef VERSION_SH

static s8 D_SH_8030CCB4;
static s32 sUnusedDisableRumble;
static s32 sRumblePakThreadActive;
static s32 sRumblePakActive;
static s32 sRumblePakErrorCount;
s32 gRumblePakTimer;

// These void* are OSPfs* but we don't have that header
extern s32 osMotorStop(void *);
extern s32 osMotorStart(void *);
extern u32 osMotorInit(OSMesgQueue *, void *, s32);

void init_rumble_pak_scheduler_queue(void) {
    osCreateMesgQueue(&gRumblePakSchedulerMesgQueue, gRumblePakSchedulerMesgBuf, 1);
    osSendMesg(&gRumblePakSchedulerMesgQueue, (OSMesg) 0, OS_MESG_NOBLOCK);
}

void block_until_rumble_pak_free(void) {
    OSMesg msg;
    osRecvMesg(&gRumblePakSchedulerMesgQueue, &msg, OS_MESG_BLOCK);
}

void release_rumble_pak_control(void) {
    osSendMesg(&gRumblePakSchedulerMesgQueue, (OSMesg) 0, OS_MESG_NOBLOCK);
}

static void start_rumble(void) {
    if (!sRumblePakActive) {
        return;
    }

    block_until_rumble_pak_free();

    if (!osMotorStart(&gRumblePakPfs)) {
        sRumblePakErrorCount = 0;
    } else {
        sRumblePakErrorCount++;
    }

    release_rumble_pak_control();
}

static void stop_rumble(void) {
    if (!sRumblePakActive) {
        return;
    }

    block_until_rumble_pak_free();

    if (!osMotorStop(&gRumblePakPfs)) {
        sRumblePakErrorCount = 0;
    } else {
        sRumblePakErrorCount++;
    }

    release_rumble_pak_control();
}

static void update_rumble_pak(void) {
    if (D_SH_8030CCB4 > 0) {
        stop_rumble();
        return;
    }

    if (gCurrRumbleSettings.unk08 > 0) {
        gCurrRumbleSettings.unk08--;
        start_rumble();
    } else if (gCurrRumbleSettings.unk04 > 0) {
        gCurrRumbleSettings.unk04--;

        gCurrRumbleSettings.unk02 -= gCurrRumbleSettings.unk0E;
        if (gCurrRumbleSettings.unk02 < 0) {
            gCurrRumbleSettings.unk02 = 0;
        }

        if (gCurrRumbleSettings.unk00 == 1) {
            start_rumble();
        } else if (gCurrRumbleSettings.unk06 >= 0x100) {
            gCurrRumbleSettings.unk06 -= 0x100;
            start_rumble();
        } else {
            gCurrRumbleSettings.unk06 +=
                ((gCurrRumbleSettings.unk02 * gCurrRumbleSettings.unk02 * gCurrRumbleSettings.unk02) / (1 << 9)) + 4;

            stop_rumble();
        }
    } else {
        gCurrRumbleSettings.unk04 = 0;

        if (gCurrRumbleSettings.unk0A >= 5) {
            start_rumble();
        } else if ((gCurrRumbleSettings.unk0A >= 2) && (gGlobalTimer % gCurrRumbleSettings.unk0C == 0)) {
            start_rumble();
        } else {
            stop_rumble();
        }
    }

    if (gCurrRumbleSettings.unk0A > 0) {
        gCurrRumbleSettings.unk0A--;
    }
}

static void update_rumble_data_queue(void) {
    if (gRumbleDataQueue[0].unk00) {
        gCurrRumbleSettings.unk06 = 0;
        gCurrRumbleSettings.unk08 = 4;
        gCurrRumbleSettings.unk00 = gRumbleDataQueue[0].unk00;
        gCurrRumbleSettings.unk04 = gRumbleDataQueue[0].unk02;
        gCurrRumbleSettings.unk02 = gRumbleDataQueue[0].unk01;
        gCurrRumbleSettings.unk0E = gRumbleDataQueue[0].unk04;
    }

    gRumbleDataQueue[0] = gRumbleDataQueue[1];
    gRumbleDataQueue[1] = gRumbleDataQueue[2];

    gRumbleDataQueue[2].unk00 = 0;
}

void queue_rumble_data(s16 a0, s16 a1) {
    if (sUnusedDisableRumble) {
        return;
    }

    if (a1 > 70) {
        gRumbleDataQueue[2].unk00 = 1;
    } else {
        gRumbleDataQueue[2].unk00 = 2;
    }

    gRumbleDataQueue[2].unk01 = a1;
    gRumbleDataQueue[2].unk02 = a0;
    gRumbleDataQueue[2].unk04 = 0;
}

void func_sh_8024C89C(s16 a0) {
    gRumbleDataQueue[2].unk04 = a0;
}

u8 is_rumble_finished_and_queue_empty(void) {
    if (gCurrRumbleSettings.unk08 + gCurrRumbleSettings.unk04 >= 4) {
        return FALSE;
    }

    if (gRumbleDataQueue[0].unk00 != 0) {
        return FALSE;
    }

    if (gRumbleDataQueue[1].unk00 != 0) {
        return FALSE;
    }

    if (gRumbleDataQueue[2].unk00 != 0) {
        return FALSE;
    }

    return TRUE;
}

void reset_rumble_timers(void) {
    if (sUnusedDisableRumble) {
        return;
    }

    if (gCurrRumbleSettings.unk0A == 0) {
        gCurrRumbleSettings.unk0A = 7;
    }

    if (gCurrRumbleSettings.unk0A < 4) {
        gCurrRumbleSettings.unk0A = 4;
    }

    gCurrRumbleSettings.unk0C = 7;
}

void reset_rumble_timers_2(s32 a0) {
    if (sUnusedDisableRumble) {
        return;
    }

    if (gCurrRumbleSettings.unk0A == 0) {
        gCurrRumbleSettings.unk0A = 7;
    }

    if (gCurrRumbleSettings.unk0A < 4) {
        gCurrRumbleSettings.unk0A = 4;
    }

    if (a0 == 4) {
        gCurrRumbleSettings.unk0C = 1;
    }

    if (a0 == 3) {
        gCurrRumbleSettings.unk0C = 2;
    }

    if (a0 == 2) {
        gCurrRumbleSettings.unk0C = 3;
    }

    if (a0 == 1) {
        gCurrRumbleSettings.unk0C = 4;
    }

    if (a0 == 0) {
        gCurrRumbleSettings.unk0C = 5;
    }
}

void func_sh_8024CA04(void) {
    if (sUnusedDisableRumble) {
        return;
    }

    gCurrRumbleSettings.unk0A = 4;
    gCurrRumbleSettings.unk0C = 4;
}

static void thread6_rumble_loop(UNUSED void *a0) {
    OSMesg msg;

    cancel_rumble();

    sRumblePakThreadActive = TRUE;

    while (TRUE) {
        // Block until VI
        osRecvMesg(&gRumbleThreadVIMesgQueue, &msg, OS_MESG_BLOCK);

        update_rumble_data_queue();
        update_rumble_pak();

        if (sRumblePakActive) {
            if (sRumblePakErrorCount >= 30) {
                sRumblePakActive = FALSE;
            }
        } else if (gGlobalTimer % 60 == 0) {
            sRumblePakActive = osMotorInit(&gSIEventMesgQueue, &gRumblePakPfs, gPlayer1Controller->port) < 1;
            sRumblePakErrorCount = 0;
        }

        if (gRumblePakTimer > 0) {
            gRumblePakTimer--;
        }
    }
}

void cancel_rumble(void) {
    sRumblePakActive = osMotorInit(&gSIEventMesgQueue, &gRumblePakPfs, gPlayer1Controller->port) < 1;

    if (sRumblePakActive) {
        osMotorStop(&gRumblePakPfs);
    }

    gRumbleDataQueue[0].unk00 = 0;
    gRumbleDataQueue[1].unk00 = 0;
    gRumbleDataQueue[2].unk00 = 0;

    gCurrRumbleSettings.unk04 = 0;
    gCurrRumbleSettings.unk0A = 0;

    gRumblePakTimer = 0;
}

void create_thread_6(void) {
    osCreateMesgQueue(&gRumbleThreadVIMesgQueue, gRumbleThreadVIMesgBuf, 1);
    osCreateThread(&gRumblePakThread, 6, thread6_rumble_loop, NULL, gThread6Stack + 0x2000, 30);
    osStartThread(&gRumblePakThread);
}

void rumble_thread_update_vi(void) {
    if (sRumblePakThreadActive == FALSE) {
        return;
    }

    osSendMesg(&gRumbleThreadVIMesgQueue, (OSMesg) 0x56525443, OS_MESG_NOBLOCK);
}

#endif
