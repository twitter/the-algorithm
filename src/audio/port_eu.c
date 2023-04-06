#include <ultra64.h>
#include "internal.h"
#include "load.h"
#include "data.h"
#include "seqplayer.h"
#include "synthesis.h"

#ifdef VERSION_EU

#ifdef __sgi
#define stubbed_printf
#else
#define stubbed_printf(...)
#endif

#define SAMPLES_TO_OVERPRODUCE 0x10
#define EXTRA_BUFFERED_AI_SAMPLES_TARGET 0x40

#ifdef VERSION_JP
typedef u16 FadeT;
#else
typedef s32 FadeT;
#endif

extern volatile u8 gAudioResetStatus;
extern u8 gAudioResetPresetIdToLoad;
extern OSMesgQueue *OSMesgQueues[];
extern struct EuAudioCmd sAudioCmd[0x100];

void func_8031D690(s32 player, FadeT fadeInTime);
void sequence_player_fade_out_internal(s32 player, FadeT fadeOutTime);
void port_eu_init_queues(void);
void decrease_sample_dma_ttls(void);
s32 audio_shut_down_and_reset_step(void);
void func_802ad7ec(u32);

struct SPTask *create_next_audio_frame_task(void) {
    u32 samplesRemainingInAI;
    s32 writtenCmds;
    s32 index;
    OSTask_t *task;
    s32 flags;
    u16 *currAiBuffer;
    s32 oldDmaCount;
    OSMesg sp30;
    OSMesg sp2C;

    gAudioFrameCount++;
    if (gAudioFrameCount % gAudioBufferParameters.presetUnk4 != 0) {
        stubbed_printf("DAC:Lost 1 Frame.\n");
        return NULL;
    }

    osSendMesg(OSMesgQueues[0], (OSMesg) gAudioFrameCount, 0);

    gAudioTaskIndex ^= 1;
    gCurrAiBufferIndex++;
    gCurrAiBufferIndex %= NUMAIBUFFERS;
    index = (gCurrAiBufferIndex - 2 + NUMAIBUFFERS) % NUMAIBUFFERS;
    samplesRemainingInAI = osAiGetLength() / 4;

    if (gAiBufferLengths[index] != 0) {
        osAiSetNextBuffer(gAiBuffers[index], gAiBufferLengths[index] * 4);
    }

    oldDmaCount = gCurrAudioFrameDmaCount;
    if (oldDmaCount > AUDIO_FRAME_DMA_QUEUE_SIZE) {
        stubbed_printf("DMA: Request queue over.( %d )\n", oldDmaCount);
    }
    gCurrAudioFrameDmaCount = 0;

    decrease_sample_dma_ttls();
    if (osRecvMesg(OSMesgQueues[2], &sp30, 0) != -1) {
        gAudioResetPresetIdToLoad = (u8) (s32) sp30;
        gAudioResetStatus = 5;
    }

    if (gAudioResetStatus != 0) {
        if (audio_shut_down_and_reset_step() == 0) {
            if (gAudioResetStatus == 0) {
                osSendMesg(OSMesgQueues[3], (OSMesg) (s32) gAudioResetPresetIdToLoad, OS_MESG_NOBLOCK);
            }
            return NULL;
        }
    }

    gAudioTask = &gAudioTasks[gAudioTaskIndex];
    gAudioCmd = gAudioCmdBuffers[gAudioTaskIndex];
    index = gCurrAiBufferIndex;
    currAiBuffer = gAiBuffers[index];

    gAiBufferLengths[index] = ((gAudioBufferParameters.samplesPerFrameTarget - samplesRemainingInAI +
         EXTRA_BUFFERED_AI_SAMPLES_TARGET) & ~0xf) + SAMPLES_TO_OVERPRODUCE;
    if (gAiBufferLengths[index] < gAudioBufferParameters.minAiBufferLength) {
        gAiBufferLengths[index] = gAudioBufferParameters.minAiBufferLength;
    }
    if (gAiBufferLengths[index] > gAudioBufferParameters.maxAiBufferLength) {
        gAiBufferLengths[index] = gAudioBufferParameters.maxAiBufferLength;
    }

    if (osRecvMesg(OSMesgQueues[1], &sp2C, OS_MESG_NOBLOCK) != -1) {
        func_802ad7ec((u32) sp2C);
    }

    flags = 0;
    gAudioCmd = synthesis_execute(gAudioCmd, &writtenCmds, currAiBuffer, gAiBufferLengths[index]);
    gAudioRandom = ((gAudioRandom + gAudioFrameCount) * gAudioFrameCount);
    gAudioRandom = gAudioRandom + writtenCmds / 8;

    index = gAudioTaskIndex;
    gAudioTask->msgqueue = NULL;
    gAudioTask->msg = NULL;

    task = &gAudioTask->task.t;
    task->type = M_AUDTASK;
    task->flags = flags;
    task->ucode_boot = rspF3DBootStart;
    task->ucode_boot_size = (u8 *) rspF3DBootEnd - (u8 *) rspF3DBootStart;
    task->ucode = rspAspMainStart;
    task->ucode_data = rspAspMainDataStart;
    task->ucode_size = 0x800; // (this size is ignored)
    task->ucode_data_size = (rspAspMainDataEnd - rspAspMainDataStart) * sizeof(u64);
    task->dram_stack = NULL;
    task->dram_stack_size = 0;
    task->output_buff = NULL;
    task->output_buff_size = NULL;
    task->data_ptr = gAudioCmdBuffers[index];
    task->data_size = writtenCmds * sizeof(u64);
    task->yield_data_ptr = NULL;
    task->yield_data_size = 0;
    return gAudioTask;
}

void eu_process_audio_cmd(struct EuAudioCmd *cmd) {
    s32 i;

    switch (cmd->u.s.op) {
    case 0x81:
        preload_sequence(cmd->u.s.arg2, 3);
        break;

    case 0x82:
    case 0x88:
        // load_sequence(arg1, arg2, 0);
        load_sequence(cmd->u.s.arg1, cmd->u.s.arg2, cmd->u.s.arg3);
        func_8031D690(cmd->u.s.arg1, cmd->u2.as_s32);
        break;

    case 0x83:
        if (gSequencePlayers[cmd->u.s.arg1].enabled != FALSE) {
            if (cmd->u2.as_s32 == 0) {
                sequence_player_disable(&gSequencePlayers[cmd->u.s.arg1]);
            }
            else {
                sequence_player_fade_out_internal(cmd->u.s.arg1, cmd->u2.as_s32);
            }
        }
        break;

    case 0xf0:
        gSoundMode = cmd->u2.as_s32;
        break;

    case 0xf1:
        for (i = 0; i < 4; i++) {
            gSequencePlayers[i].muted = TRUE;
            gSequencePlayers[i].recalculateVolume = TRUE;
        }
        break;

    case 0xf2:
        for (i = 0; i < 4; i++) {
            gSequencePlayers[i].muted = FALSE;
            gSequencePlayers[i].recalculateVolume = TRUE;
        }
        break;
    }
}

const char undefportcmd[] = "Undefined Port Command %d\n";

extern OSMesgQueue *OSMesgQueues[];
extern u8 D_EU_80302010;
extern u8 D_EU_80302014;
extern OSMesg OSMesg0;
extern OSMesg OSMesg1;
extern OSMesg OSMesg2;
extern OSMesg OSMesg3;

void sequence_player_fade_out_internal(s32 player, FadeT fadeOutTime) {
    if (fadeOutTime == 0) {
        fadeOutTime = 1;
    }
    gSequencePlayers[player].fadeVelocity = -(gSequencePlayers[player].fadeVolume / fadeOutTime);
    gSequencePlayers[player].state = 2;
    gSequencePlayers[player].fadeTimer = fadeOutTime;

}

void func_8031D690(s32 player, FadeT fadeInTime) {
    if (fadeInTime != 0) {
        gSequencePlayers[player].state = 1;
        gSequencePlayers[player].fadeTimerUnkEu = fadeInTime;
        gSequencePlayers[player].fadeTimer = fadeInTime;
        gSequencePlayers[player].fadeVolume = 0.0f;
        gSequencePlayers[player].fadeVelocity = 0.0f;
    }
}

void port_eu_init_queues(void) {
    D_EU_80302010 = 0;
    D_EU_80302014 = 0;
    osCreateMesgQueue(OSMesgQueues[0], &OSMesg0, 1);
    osCreateMesgQueue(OSMesgQueues[1], &OSMesg1, 4);
    osCreateMesgQueue(OSMesgQueues[2], &OSMesg2, 1);
    osCreateMesgQueue(OSMesgQueues[3], &OSMesg3, 1);
}

void func_802ad6f0(s32 arg0, s32 *arg1) {
    struct EuAudioCmd *cmd = &sAudioCmd[D_EU_80302010 & 0xff];
    cmd->u.first = arg0;
    cmd->u2.as_u32 = *arg1;
    D_EU_80302010++;
}

void func_802ad728(u32 arg0, f32 arg1) {
    func_802ad6f0(arg0, (s32*) &arg1);
}

void func_802ad74c(u32 arg0, u32 arg1) {
    func_802ad6f0(arg0, (s32*) &arg1);
}

void func_802ad770(u32 arg0, s8 arg1) {
    s32 sp1C = arg1 << 24;
    func_802ad6f0(arg0, &sp1C);
}

void func_802ad7a0(void) {
    osSendMesg(OSMesgQueues[1],
            (OSMesg)(u32)((D_EU_80302014 & 0xff) << 8 | (D_EU_80302010 & 0xff)),
            OS_MESG_NOBLOCK);
    D_EU_80302014 = D_EU_80302010;
}

void func_802ad7ec(u32 arg0) {
    struct EuAudioCmd *cmd;
    struct SequencePlayer *seqPlayer;
    struct SequenceChannel *chan;
    u8 end = arg0 & 0xff;
    u8 i = (arg0 >> 8) & 0xff;

    for (;;) {
        if (i == end) break;
        cmd = &sAudioCmd[i++ & 0xff];

        if (cmd->u.s.arg1 < SEQUENCE_PLAYERS) {
            seqPlayer = &gSequencePlayers[cmd->u.s.arg1];
            if ((cmd->u.s.op & 0x80) != 0) {
                eu_process_audio_cmd(cmd);
            }
            else if ((cmd->u.s.op & 0x40) != 0) {
                switch (cmd->u.s.op) {
                case 0x41:
                    seqPlayer->fadeVolumeScale = cmd->u2.as_f32;
                    seqPlayer->recalculateVolume = TRUE;
                    break;

                case 0x47:
                    seqPlayer->tempo = cmd->u2.as_s32 * TATUMS_PER_BEAT;
                    break;

                case 0x48:
                    seqPlayer->transposition = cmd->u2.as_s8;
                    break;

                case 0x46:
                    seqPlayer->seqVariationEu[cmd->u.s.arg3] = cmd->u2.as_s8;
                    break;
                }
            }
            else if (seqPlayer->enabled != FALSE && cmd->u.s.arg2 < 0x10) {
                chan = seqPlayer->channels[cmd->u.s.arg2];
                if (IS_SEQUENCE_CHANNEL_VALID(chan))
                {
                    switch (cmd->u.s.op) {
                    case 1:
                        chan->volumeScale = cmd->u2.as_f32;
                        chan->changes.as_bitfields.volume = TRUE;
                        break;
                    case 2:
                        chan->volume = cmd->u2.as_f32;
                        chan->changes.as_bitfields.volume = TRUE;
                        break;
                    case 3:
                        chan->newPan = cmd->u2.as_s8;
                        chan->changes.as_bitfields.pan = TRUE;
                        break;
                    case 4:
                        chan->freqScale = cmd->u2.as_f32;
                        chan->changes.as_bitfields.freqScale = TRUE;
                        break;
                    case 5:
                        chan->reverb = cmd->u2.as_s8;
                        break;
                    case 6:
                        if (cmd->u.s.arg3 < 8) {
                            chan->soundScriptIO[cmd->u.s.arg3] = cmd->u2.as_s8;
                        }
                        break;
                    case 8:
                        chan->stopSomething2 = cmd->u2.as_s8;
                    }
                }
            }
        }

        cmd->u.s.op = 0;
    }
}

void port_eu_init(void) {
    port_eu_init_queues();
}

#endif
