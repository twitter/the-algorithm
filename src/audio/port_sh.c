#ifdef VERSION_SH
// TODO: merge this with port_eu.c?

#include <ultra64.h>

#include "data.h"
#include "heap.h"
#include "load.h"
#include "synthesis.h"
#include "internal.h"
#include "seqplayer.h"

#define EXTRA_BUFFERED_AI_SAMPLES_TARGET 0x80
#define SAMPLES_TO_OVERPRODUCE 0x10

extern s32 D_SH_80314FC8;
extern struct SPTask *D_SH_80314FCC;
extern u8 D_SH_80315098;
extern u8 D_SH_8031509C;
extern OSMesgQueue *D_SH_80350F68;

void func_8031D690(s32 playerIndex, s32 numFrames);
void seq_player_fade_to_zero_volume(s32 arg0, s32 numFrames);
void func_802ad7ec(u32 arg0);

struct SPTask *create_next_audio_frame_task(void) {
    u32 samplesRemainingInAI;
    s32 writtenCmds;
    s32 index;
    OSTask_t *task;
    s32 flags;
    s16 *currAiBuffer;
    s32 oldDmaCount;
    s32 sp38;
    s32 sp34;
    s32 writtenCmdsCopy;

    gAudioFrameCount++;
    if (gAudioFrameCount % gAudioBufferParameters.presetUnk4 != 0) {
        if ((gAudioFrameCount % gAudioBufferParameters.presetUnk4) + 1 == gAudioBufferParameters.presetUnk4) {
            return D_SH_80314FCC;
        }
        return NULL;
    }
    osSendMesg(D_SH_80350F38, (OSMesg) gAudioFrameCount, OS_MESG_NOBLOCK);

    gAudioTaskIndex ^= 1;
    gCurrAiBufferIndex++;
    gCurrAiBufferIndex %= NUMAIBUFFERS;
    index = (gCurrAiBufferIndex - 2 + NUMAIBUFFERS) % NUMAIBUFFERS;
    samplesRemainingInAI = osAiGetLength() / 4;

    if (gAudioLoadLockSH < 0x10U) {
        if (gAiBufferLengths[index] != 0) {
            osAiSetNextBuffer(gAiBuffers[index], gAiBufferLengths[index] * 4);
        }
    }

    oldDmaCount = gCurrAudioFrameDmaCount;
    if (oldDmaCount > AUDIO_FRAME_DMA_QUEUE_SIZE) {
    }
    gCurrAudioFrameDmaCount = 0;

    decrease_sample_dma_ttls();
    func_sh_802f41e4(gAudioResetStatus);
    if (osRecvMesg(D_SH_80350F88, (OSMesg *) &sp38, OS_MESG_NOBLOCK) != -1) {
        if (gAudioResetStatus == 0) {
            gAudioResetStatus = 5;
        }
        gAudioResetPresetIdToLoad = (u8) sp38;
    }

    if (gAudioResetStatus != 0) {
        if (audio_shut_down_and_reset_step() == 0) {
            if (gAudioResetStatus == 0) {
                osSendMesg(D_SH_80350FA8, (OSMesg) (s32) gAudioResetPresetIdToLoad, OS_MESG_NOBLOCK);
            }
            D_SH_80314FCC = 0;
            return NULL;
        }
    }
    if (gAudioLoadLockSH >= 0x11U) {
        return NULL;
    }
    if (gAudioLoadLockSH != 0) {
        gAudioLoadLockSH++;
    }

    gAudioTask = &gAudioTasks[gAudioTaskIndex];
    gAudioCmd = (u64 *) gAudioCmdBuffers[gAudioTaskIndex];
    index = gCurrAiBufferIndex;
    currAiBuffer = gAiBuffers[index];

    gAiBufferLengths[index] = (s16) ((((gAudioBufferParameters.samplesPerFrameTarget - samplesRemainingInAI) +
         EXTRA_BUFFERED_AI_SAMPLES_TARGET) & ~0xf) + SAMPLES_TO_OVERPRODUCE);
    if (gAiBufferLengths[index] < gAudioBufferParameters.minAiBufferLength) {
        gAiBufferLengths[index] = gAudioBufferParameters.minAiBufferLength;
    }
    if (gAiBufferLengths[index] > gAudioBufferParameters.maxAiBufferLength) {
        gAiBufferLengths[index] = gAudioBufferParameters.maxAiBufferLength;
    }

    if (osRecvMesg(D_SH_80350F68, (OSMesg *) &sp34, 0) != -1) {
        do {
            func_802ad7ec(sp34);
        } while (osRecvMesg(D_SH_80350F68, (OSMesg *) &sp34, 0) != -1);
    }

    flags = 0;
    gAudioCmd = synthesis_execute(gAudioCmd, &writtenCmds, currAiBuffer, gAiBufferLengths[index]);
    gAudioRandom = (u32) (osGetCount() * (gAudioRandom + gAudioFrameCount));
    gAudioRandom = (u32) (gAiBuffers[index][gAudioFrameCount & 0xFF] + gAudioRandom);

    index = gAudioTaskIndex;
    gAudioTask->msgqueue = NULL;
    gAudioTask->msg = NULL;

    task = &gAudioTask->task.t;
    task->type = M_AUDTASK;
    task->flags = flags;
    task->ucode_boot = rspF3DBootStart;
    task->ucode_boot_size = (u8 *) rspF3DStart - (u8 *) rspF3DBootStart;
    task->ucode = rspAspMainStart;
    task->ucode_data = rspAspMainDataStart;
    task->ucode_size = 0x1000;
    task->ucode_data_size = (rspAspMainDataEnd - rspAspMainDataStart) * sizeof(u64);
    task->dram_stack = NULL;
    task->dram_stack_size = 0;
    task->output_buff = NULL;
    task->output_buff_size = NULL;
    task->data_ptr = gAudioCmdBuffers[index];
    task->data_size = writtenCmds * sizeof(u64);
    task->yield_data_ptr = NULL;
    task->yield_data_size = 0;

    writtenCmdsCopy = writtenCmds;
    if (D_SH_80314FC8 < writtenCmdsCopy) {
        D_SH_80314FC8 = writtenCmdsCopy;
    }

    if (gAudioBufferParameters.presetUnk4 == 1) {
        return gAudioTask;
    } else {
        D_SH_80314FCC = gAudioTask;
        return NULL;
    }
}

void eu_process_audio_cmd(struct EuAudioCmd *cmd) {
    s32 i;
    struct Note *note;
    struct NoteSubEu *sub;

    switch (cmd->u.s.op) {
        case 0x81:
            preload_sequence(cmd->u.s.arg2, 3);
            break;

        case 0x82:
        case 0x88:
            load_sequence(cmd->u.s.arg1, cmd->u.s.arg2, cmd->u.s.arg3);
            func_8031D690(cmd->u.s.arg1, cmd->u2.as_s32);
            break;

        case 0x83:
            if (gSequencePlayers[cmd->u.s.arg1].enabled != FALSE) {
                if (cmd->u2.as_s32 == 0) {
                    sequence_player_disable(&gSequencePlayers[cmd->u.s.arg1]);
                }
                else {
                    seq_player_fade_to_zero_volume(cmd->u.s.arg1, cmd->u2.as_s32);
                }
            }
            break;

        case 0x84:
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
            if (cmd->u2.as_s32 == 1) {
                for (i = 0; i < gMaxSimultaneousNotes; i++) {
                    note = &gNotes[i];
                    sub = &note->noteSubEu;
                    if (note->noteSubEu.enabled && note->unkSH34 == 0) {
                        if ((note->parentLayer->seqChannel->muteBehavior & 8) != 0) {
                            sub->finished = TRUE;
                        }
                    }
                }
            }
            for (i = 0; i < 4; i++) {
                gSequencePlayers[i].muted = FALSE;
                gSequencePlayers[i].recalculateVolume = TRUE;
            }
            break;

        case 0xf3:
            func_sh_802f3024(cmd->u.s.arg1, cmd->u.s.arg2, cmd->u.s.arg3);
            break;

        case 0xf4:
            func_sh_802f30f4(cmd->u.s.arg1, cmd->u.s.arg2, cmd->u.s.arg3, &gUnkQueue1);
            break;

        case 0xf5:
            func_sh_802f3158(cmd->u.s.arg1, cmd->u.s.arg2, cmd->u.s.arg3, &gUnkQueue1);
            break;

        case 0xf6:
            func_sh_802f3288(cmd->u.s.arg2);
            break;
    }
}

void seq_player_fade_to_zero_volume(s32 arg0, s32 fadeOutTime) {
    struct SequencePlayer *player;

    if (fadeOutTime == 0) {
        fadeOutTime = 1;
    }
    player = &gSequencePlayers[arg0];
    player->state = 2;
    player->fadeRemainingFrames = fadeOutTime;
    player->fadeVelocity = -(player->fadeVolume / (f32) fadeOutTime);
}

void func_8031D690(s32 playerIndex, s32 fadeInTime) {
    struct SequencePlayer *player;

    if (fadeInTime != 0) {
        player = &gSequencePlayers[playerIndex];
        player->state = 1;
        player->fadeTimerUnkEu = fadeInTime;
        player->fadeRemainingFrames = fadeInTime;
        player->fadeVolume = 0.0f;
        player->fadeVelocity = 0.0f;
    }
}

void port_eu_init_queues(void) {
    D_SH_80350F18 = 0;
    D_SH_80350F19 = 0;
    D_SH_80350F38 = &D_SH_80350F20;
    D_SH_80350F68 = &D_SH_80350F50;
    D_SH_80350F88 = &D_SH_80350F70;
    D_SH_80350FA8 = &D_SH_80350F90;
    osCreateMesgQueue(D_SH_80350F38, D_SH_80350F1C, 1);
    osCreateMesgQueue(D_SH_80350F68, D_SH_80350F40, 4);
    osCreateMesgQueue(D_SH_80350F88, D_SH_80350F6C, 1);
    osCreateMesgQueue(D_SH_80350FA8, D_SH_80350F8C, 1);
}

extern struct EuAudioCmd sAudioCmd[0x100];
void func_802ad6f0(s32 arg0, s32 *arg1) {
    struct EuAudioCmd *cmd = &sAudioCmd[D_SH_80350F18 & 0xff];
    cmd->u.first = arg0;
    cmd->u2.as_u32 = *arg1;
    D_SH_80350F18++;
    if (D_SH_80350F18 == D_SH_80350F19) {
        D_SH_80350F18--;
    }
}

void func_802ad728(u32 arg0, f32 arg1) {
    func_802ad6f0(arg0, (s32 *) &arg1);
}

void func_802ad74c(u32 arg0, u32 arg1) {
    func_802ad6f0(arg0, (s32 *) &arg1);
}

void func_802ad770(u32 arg0, s8 arg1) {
    s32 sp1C = arg1 << 24;
    func_802ad6f0(arg0, &sp1C);
}

char shindouDebugPrint133[] = "AudioSend: %d -> %d (%d)\n";

void func_sh_802F64C8(void) {
    static s32 D_SH_8031503C = 0;
    s32 mesg;

    if (((D_SH_80350F18 - D_SH_80350F19 + 0x100) & 0xff) > D_SH_8031503C) {
        D_SH_8031503C = (D_SH_80350F18 - D_SH_80350F19 + 0x100) & 0xff;
    }
    mesg = ((D_SH_80350F19 & 0xff) << 8) | (D_SH_80350F18 & 0xff);
    osSendMesg(D_SH_80350F68, (OSMesg)mesg, OS_MESG_NOBLOCK);
    D_SH_80350F19 = D_SH_80350F18;
}

void func_sh_802f6540(void) {
    D_SH_80350F19 = D_SH_80350F18;
}

void func_802ad7ec(u32 arg0) {
    struct EuAudioCmd *cmd;
    struct SequencePlayer *seqPlayer;
    struct SequenceChannel *chan;
    u8 end;

    UNUSED static char shindouDebugPrint134[] = "Continue Port\n";
    UNUSED static char shindouDebugPrint135[] = "%d -> %d\n";
    UNUSED static char shindouDebugPrint136[] = "Sync-Frame  Break. (Remain %d)\n";
    UNUSED static char shindouDebugPrint137[] = "Undefined Port Command %d\n";

    static u8 D_SH_80315098 = 0;
    static u8 D_SH_8031509C = 0;

    if (D_SH_8031509C == 0) {
        D_SH_80315098 = (arg0 >> 8) & 0xff;
    }

    end = arg0 & 0xff;

    for (;;) {
        if (D_SH_80315098 == end) {
            D_SH_8031509C = 0;
            break;
        }
        cmd = &sAudioCmd[D_SH_80315098 & 0xff];
        D_SH_80315098++;
        if (cmd->u.s.op == 0xf8) {
            D_SH_8031509C = 1;
            break;
        }
        else if ((cmd->u.s.op & 0xf0) == 0xf0) {
            eu_process_audio_cmd(cmd);
        }
        else if (cmd->u.s.arg1 < SEQUENCE_PLAYERS) {
            seqPlayer = &gSequencePlayers[cmd->u.s.arg1];
            if ((cmd->u.s.op & 0x80) != 0) {
                eu_process_audio_cmd(cmd);
            }
            else if ((cmd->u.s.op & 0x40) != 0) {
                switch (cmd->u.s.op) {
                    case 0x41:
                        if (seqPlayer->fadeVolumeScale != cmd->u2.as_f32) {
                            seqPlayer->fadeVolumeScale = cmd->u2.as_f32;
                            seqPlayer->recalculateVolume = TRUE;
                        }
                        break;

                    case 0x47:
                        seqPlayer->tempo = cmd->u2.as_s32 * TATUMS_PER_BEAT;
                        break;

                    case 0x49:
                        seqPlayer->tempoAdd = cmd->u2.as_s32 * TEMPO_SCALE;
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
                if (IS_SEQUENCE_CHANNEL_VALID(chan)) {
                    switch (cmd->u.s.op) {
                        case 1:
                            if (chan->volumeScale != cmd->u2.as_f32) {
                                chan->volumeScale = cmd->u2.as_f32;
                                chan->changes.as_bitfields.volume = TRUE;
                            }
                            break;
                        case 2:
                            if (chan->volume != cmd->u2.as_f32) {
                                chan->volume = cmd->u2.as_f32;
                                chan->changes.as_bitfields.volume = TRUE;
                            }
                            break;
                        case 3:
                            if (chan->newPan != cmd->u2.as_s8) {
                                chan->newPan = cmd->u2.as_s8;
                                chan->changes.as_bitfields.pan = TRUE;
                            }
                            break;
                        case 4:
                            if (chan->freqScale != cmd->u2.as_f32) {
                                chan->freqScale = cmd->u2.as_f32;
                                chan->changes.as_bitfields.freqScale = TRUE;
                            }
                            break;
                        case 5:
                            //! @bug u8 s8 comparison (but harmless)
                            if (chan->reverbVol != cmd->u2.as_s8) {
                                chan->reverbVol = cmd->u2.as_s8;
                            }
                            break;
                        case 6:
                            if (cmd->u.s.arg3 < 8) {
                                chan->soundScriptIO[cmd->u.s.arg3] = cmd->u2.as_s8;
                            }
                            break;
                        case 8:
                            chan->stopSomething2 = cmd->u2.as_s8;
                            break;
                        case 9:
                            chan->muteBehavior = cmd->u2.as_s8;
                    }
                }
            }
        }

        cmd->u.s.op = 0;
    }
}

u32 func_sh_802f6878(s32 *arg0) {
    u32 sp1C;

    if (osRecvMesg(&gUnkQueue1, (OSMesg *) &sp1C, 0) == -1) {
        *arg0 = 0;
        return 0U;
    }
    *arg0 = (s32) (sp1C & 0xFFFFFF);
    return sp1C >> 0x18;
}

u8 *func_sh_802f68e0(u32 index, u32 *a1) {
    return func_sh_802f3220(index, a1);
}

s32 func_sh_802f6900(void) {
    s32 ret;
    s32 sp18;

    ret = osRecvMesg(D_SH_80350FA8, (OSMesg *) &sp18, 0);

    if (ret == -1) {
        return 0;
    }
    if (sp18 != gAudioResetPresetIdToLoad) {
        return 0;
    } else {
        return 1;
    }
}

// TODO: (Scrub C)
void func_sh_802f6958(OSMesg mesg) {
    s32 a;
    OSMesg recvMesg;

    do {
        a = -1;
    } while (osRecvMesg(D_SH_80350FA8, &recvMesg, OS_MESG_NOBLOCK) != a);
    func_sh_802f6540();
    osSendMesg(D_SH_80350F88, mesg, OS_MESG_NOBLOCK);
}

void func_sh_802f69cc(void) {
    gAudioLoadLockSH = 1;
    func_sh_802f6958(0);
    gAudioResetStatus = 0;
}

s32 func_sh_802f6a08(s32 playerIndex, s32 channelIndex, s32 soundScriptIOIndex) {
    struct SequenceChannel *seqChannel;
    struct SequencePlayer *player;

    player = &gSequencePlayers[playerIndex];
    if (player->enabled) {
        seqChannel = player->channels[channelIndex];
        if (IS_SEQUENCE_CHANNEL_VALID(seqChannel)) {
            return seqChannel->soundScriptIO[soundScriptIOIndex];
        }
    }
    return -1;
}

s8 func_sh_802f6a6c(s32 playerIndex, s32 index) {
    return gSequencePlayers[playerIndex].seqVariationEu[index];
}

void port_eu_init(void) {
    port_eu_init_queues();
}

char shindouDebugPrint138[] = "specchg conjunction error (Msg:%d Cur:%d)\n";
char shindouDebugPrint139[] = "Error : Queue is not empty ( %x ) \n";
char shindouDebugPrint140[] = "Audio: setvol: volume minus %f\n";
char shindouDebugPrint141[] = "Audio: setvol: volume overflow %f\n";
char shindouDebugPrint142[] = "Audio: setpitch: pitch zero or minus %f\n";
char shindouDebugPrint143[] = "----------------------Double-Error CH: %x %f\n";
char shindouDebugPrint144[] = "----------------------Double-Error NT: %x\n";
char shindouDebugPrint145[] = "CAUTION:SUB IS SEPARATED FROM GROUP\n";
char shindouDebugPrint146[] = "CAUTION:PAUSE EMERGENCY\n";
char shindouDebugPrint147[] = "Error:Wait Track disappear\n";
char shindouDebugPrint148[] = "Audio: voiceman: No bank error %d\n";
char shindouDebugPrint149[] = "Audio: voiceman: progNo. overflow %d,%d\n";
char shindouDebugPrint150[] = "ptr2 %x\n";
char shindouDebugPrint151[] = "Audio: voiceman: progNo. undefined %d,%d\n";
char shindouDebugPrint152[] = "Audio: voiceman: No bank error %d\n";
char shindouDebugPrint153[] = "Audio: voiceman: Percussion Overflow %d,%d\n";
char shindouDebugPrint154[] = "Audio: voiceman: Percussion table pointer (bank %d) is irregular %x.\n";
char shindouDebugPrint155[] = "Audio: voiceman: Percpointer NULL %d,%d\n";
char shindouDebugPrint156[] = "--4 %x\n";
char shindouDebugPrint157[] = "NoteOff Comes during wait release %x (note %x)\n";
char shindouDebugPrint158[] = "Slow Release Batting\n";
u8 euUnknownData_8030194c[4] = { 0x40, 0x20, 0x10, 0x08 };
char shindouDebugPrint159[] = "Audio:Wavemem: Bad voiceno (%d)\n";
char shindouDebugPrint160[] = "Audio: C-Alloc : Dealloc voice is NULL\n";
char shindouDebugPrint161[] = "Alloc Error:Dim voice-Alloc %d";
char shindouDebugPrint162[] = "Error:Same List Add\n";
char shindouDebugPrint163[] = "Already Cut\n";
char shindouDebugPrint164[] = "Audio: C-Alloc : lowerPrio is NULL\n";
char shindouDebugPrint165[] = "Intterupt UseStop %d (Kill %d)\n";
char shindouDebugPrint166[] = "Intterupt RelWait %d (Kill %d)\n";
char shindouDebugPrint167[] = "Drop Voice (Prio %x)\n";
s32 D_SH_803154CC = 0; // file boundary

// effects.c
char shindouDebugPrint168[] = "Audio:Envp: overflow  %f\n";
s32 D_SH_803154EC = 0; // file boundary

// seqplayer.c
char shindouDebugPrint169[] = "Audio:Track:Warning: No Free Notetrack\n";
char shindouDebugPrint170[] = "SUBTRACK DIM\n";
char shindouDebugPrint171[] = "Audio:Track: Warning :SUBTRACK had been stolen by other Group.\n";
char shindouDebugPrint172[] = "SEQID %d,BANKID %d\n";
char shindouDebugPrint173[] = "ERR:SUBTRACK %d NOT ALLOCATED\n";
char shindouDebugPrint174[] = "Stop Release\n";
char shindouDebugPrint175[] = "Error:Same List Add\n";
char shindouDebugPrint176[] = "Wait Time out!\n";
char shindouDebugPrint177[] = "Macro Level Over Error!\n";
char shindouDebugPrint178[] = "Macro Level Over Error!\n"; // Again
char shindouDebugPrint179[] = "WARNING: NPRG: cannot change %d\n";
char shindouDebugPrint180[] = "Audio:Track:NOTE:UNDEFINED NOTE COM. %x\n";
char shindouDebugPrint181[] = "Error: Subtrack no prg.\n";
char shindouDebugPrint182[] = "ERR %x\n";
char shindouDebugPrint183[] = "Note OverFlow %d\n";
char shindouDebugPrint184[] = "trs %d , %d, %d\n";
char shindouDebugPrint185[] = "Audio: Note:Velocity Error %d\n";
char shindouDebugPrint186[] = "Audio:Track :Call Macro Level Over Error!\n";
char shindouDebugPrint187[] = "Audio:Track :Loops Macro Level Over Error!\n";
char shindouDebugPrint188[] = "SUB:ERR:BANK %d NOT CACHED.\n";
char shindouDebugPrint189[] = "SUB:ERR:BANK %d NOT CACHED.\n";
char shindouDebugPrint190[] = "Audio:Track: CTBLCALL Macro Level Over Error!\n";
char shindouDebugPrint191[] = "Set Noise %d\n";
char shindouDebugPrint192[] = "[%2x] \n";
char shindouDebugPrint193[] = "Err :Sub %x ,address %x:Undefined SubTrack Function %x";
char shindouDebugPrint194[] = "VoiceLoad Error Bank:%d,Prog:%d\n";
char shindouDebugPrint195[] = "Disappear Sequence or Bank %d\n";
char shindouDebugPrint196[] = "[FIN] group.\n";
char shindouDebugPrint197[] = "Macro Level Over Error!\n";
char shindouDebugPrint198[] = "Macro Level Over Error!\n";
char shindouDebugPrint199[] = "Group:Undefine upper C0h command (%x)\n";
char shindouDebugPrint200[] = "Group:Undefined Command\n";

#endif
