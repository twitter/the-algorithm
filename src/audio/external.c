#include <ultra64.h>
#include "sm64.h"
#include "heap.h"
#include "load.h"
#include "data.h"
#include "seqplayer.h"
#include "external.h"
#include "playback.h"
#include "synthesis.h"
#include "game/mario.h"
#include "game/level_update.h"
#include "game/area.h"
#include "game/object_list_processor.h"
#include "game/camera.h"
#include "seq_ids.h"
#include "dialog_ids.h"
#include "level_table.h"

#ifdef VERSION_EU
#define EU_FLOAT(x) x ## f
#else
#define EU_FLOAT(x) x
#endif

#ifdef VERSION_EU
u8 audioString1[] = "pitch %x: delaybytes %d : olddelay %d\n";
u8 audioString2[] = "cont %x: delaybytes %d : olddelay %d\n";
u8 audioString3[] = "Warning:Kill Note  %x \n";
u8 audioString4[] = "Kill Voice %d (ID %d) %d\n";
u8 audioString5[] = "Warning: Running Sequence's data disappear!\n";
u8 audioString6[] = "Heap OverFlow : Not Allocate %d!\n";
u8 audioString7[] = "DataHeap Not Allocate \n";
u8 audioString8[] = "StayHeap Not Allocate %d\n";
u8 audioString9[] = "AutoHeap Not Allocate %d\n";
u8 audioString10[] = "WARNING: NO FREE AUTOSEQ AREA.\n";
u8 audioString11[] = "WARNING: NO STOP AUTO AREA.\n";
u8 audioString12[] = "         AND TRY FORCE TO STOP SIDE \n";
u8 audioString13[] = "TWO SIDES ARE LOADING... ALLOC CANCELED.\n";
u8 audioString14[] = "WARNING: Before Area Overlaid After.";
u8 audioString15[] = "WARNING: After Area Overlaid Before.";
u8 audioString16[] = "MEMORY:SzHeapAlloc ERROR: sza->side %d\n";
u8 audioString17[] = "MEMORY:StayHeap OVERFLOW.";
u8 audioString18[] = "MEMORY:StayHeap OVERFLOW (REQ:%d)";
u8 audioString19[] = "Auto Heap Unhit for ID %d\n";
u8 audioString20[] = "Cache hit %d at stay %d\n";
u8 audioString20_[] = "%d ";
u8 audioString20__[] = "\n";
u8 audioString20___[] = "%d ";
u8 audioString20____[] = "\n";
u8 audioString21[] = "Heap Reconstruct Start %x\n";
u8 audioString22[] = "SFrame Sample %d %d %d\n";
u8 audioString23[] = "AHPBASE %x\n";
u8 audioString24[] = "AHPCUR  %x\n";
u8 audioString25[] = "HeapTop %x\n";
u8 audioString26[] = "SynoutRate %d / %d \n";
u8 audioString27[] = "FXSIZE %d\n";
u8 audioString28[] = "FXCOMP %d\n";
u8 audioString29[] = "FXDOWN %d\n";
u8 audioString30[] = "WaveCacheLen: %d\n";
u8 audioString31[] = "SpecChange Finished\n";
u8 audioString31_[] = "";
u8 audioString32[] = "Romcopy %x -> %x ,size %x\n";
u8 audioString33[] = "Romcopyend\n";
u8 audioString34[] = "CAUTION:WAVE CACHE FULL %d";
u8 audioString35[] = "BASE %x %x\n";
u8 audioString36[] = "LOAD %x %x %x\n";
u8 audioString37[] = "INSTTOP    %x\n";
u8 audioString38[] = "INSTMAP[0] %x\n";
u8 audioString39[] = "already flags %d\n";
u8 audioString40[] = "already flags %d\n";
u8 audioString41[] = "ERR:SLOW BANK DMA BUSY\n";
u8 audioString42[] = "ERR:SLOW DMA BUSY\n";
u8 audioString43[] = "Check %d  bank %d\n";
u8 audioString44[] = "Cache Check\n";
u8 audioString45[] = "NO BANK ERROR\n";
u8 audioString46[] = "BANK %d LOADING START\n";
u8 audioString47[] = "BANK %d LOAD MISS (NO MEMORY)!\n";
u8 audioString48[] = "BANK %d ALREADY CACHED\n";
u8 audioString49[] = "BANK LOAD MISS! FOR %d\n";
u8 audioString50[] = "Seq %d Loading Start\n";
u8 audioString51[] = "Heap Overflow Error\n";
u8 audioString52[] = "SEQ  %d ALREADY CACHED\n";
u8 audioString53[] = "Ok,one bank slow load Start \n";
u8 audioString54[] = "Sorry,too many %d bank is none.fast load Start \n";
u8 audioString55[] = "Seq %d:Default Load Id is %d\n";
u8 audioString56[] = "Seq Loading Start\n";
u8 audioString57[] = "Error:Before Sequence-SlowDma remain.\n";
u8 audioString58[] = "      Cancel Seq Start.\n";
u8 audioString59[] = "SEQ  %d ALREADY CACHED\n";
u8 audioString60[] = "Clear Workarea %x -%x size %x \n";
u8 audioString61[] = "AudioHeap is %x\n";
u8 audioString62[] = "Heap reset.Synth Change %x \n";
u8 audioString63[] = "Heap %x %x %x\n";
u8 audioString64[] = "Main Heap Initialize.\n";
u8 audioString65[] = "---------- Init Completed. ------------\n";
u8 audioString66[] = " Syndrv    :[%6d]\n";
u8 audioString67[] = " Seqdrv    :[%6d]\n";
u8 audioString68[] = " audiodata :[%6d]\n";
u8 audioString69[] = "---------------------------------------\n";
u8 audioString69_[] = "";
u8 audioString70[] = "Audio: setvol: volume minus %f\n";
u8 audioString71[] = "Audio: setvol: volume overflow %f\n";
u8 audioString72[] = "Audio: setpitch: pitch minus %f\n";
u8 audioString73[] = "Audio: voiceman: No bank error %d\n";
u8 audioString74[] = "Audio: voiceman: progNo. overflow %d,%d\n";
u8 audioString75[] = "Audio: voiceman: progNo. undefined %d,%d\n";
u8 audioString76[] = "Audio: voiceman: BAD Voicepointer %x,%d,%d\n";
u8 audioString77[] = "Audio: voiceman: Percussion Overflow %d,%d\n";
u8 audioString78[] = "Percussion Pointer Error\n";
u8 audioString79[] = "Audio: voiceman: Percpointer NULL %d,%d\n";
u8 audioString80[] = "CAUTION:SUB IS SEPARATED FROM GROUP";
u8 audioString81[] = "Error:Wait Track disappear\n";
u8 audioString82[] = "Slow Release Batting\n";
u8 audioString83[] = "Audio:Wavemem: Bad voiceno (%d)\n";
u8 audioString84[] = "Audio: C-Alloc : Dealloc voice is NULL\n";
u8 audioString85[] = "Alloc Error:Dim voice-Alloc %d";
u8 audioString86[] = "Error:Same List Add\n";
u8 audioString87[] = "Already Cut\n";
u8 audioString88[] = "Audio: C-Alloc : lowerPrio is NULL\n";
u8 audioString89[] = "Sub Limited Warning: Drop Voice";
u8 audioString90[] = "Warning: Drop Voice";
u8 audioString91[] = "Warning: Drop Voice";
u8 audioString92[] = "Warning: Drop Voice";
u8 audioString93[] = "Audio:Envp: overflow  %f\n";
u8 audioString93_[] = "";
u8 audioString94[] = "Audio:Track:Warning: No Free Notetrack\n";
u8 audioString95[] = "SUBTRACK DIM\n";
u8 audioString96[] = "Audio:Track: Warning SUBTRACK PARENT CHANGED\n";
u8 audioString97[] = "GROUP 0:";
u8 audioString98[] = "GROUP 1:";
u8 audioString99[] = "SEQID %d,BANKID %d\n";
u8 audioString100[] = "ERR:SUBTRACK %d NOT ALLOCATED\n";
u8 audioString101[] = "Error:Same List Add\n";
u8 audioString102[] = "Macro Level Over Error!\n";
u8 audioString103[] = "Macro Level Over Error!\n";
u8 audioString104[] = "WARNING: NPRG: cannot change %d\n";
u8 audioString105[] = "Audio:Track:NOTE:UNDEFINED NOTE COM. %x\n";
u8 audioString106[] = "Audio: Note:Velocity Error %d\n";
u8 audioString107[] = "Error: Your assignchannel is stolen.\n";
u8 audioString108[] = "Audio:Track :Call Macro Level Over Error!\n";
u8 audioString109[] = "Audio:Track :Loops Macro Level Over Error!\n";
u8 audioString110[] = "SUB:ERR:BANK %d NOT CACHED.\n";
u8 audioString111[] = "SUB:ERR:BANK %d NOT CACHED.\n";
u8 audioString112[] = "Audio:Track: CTBLCALL Macro Level Over Error!\n";
u8 audioString113[] = "Err :Sub %x ,address %x:Undefined SubTrack Function %x";
u8 audioString114[] = "Disappear Sequence or Bank %d\n";
u8 audioString115[] = "Macro Level Over Error!\n";
u8 audioString116[] = "Macro Level Over Error!\n";
u8 audioString117[] = "Group:Undefine upper C0h command (%x)\n";
u8 audioString118[] = "Group:Undefined Command\n";
u8 audioString118_[] = "";
u8 audioString118__[] = "";
#endif

// N.B. sound banks are different from the audio banks referred to in other
// files. We should really fix our naming to be less ambiguous...
#define MAX_BG_MUSIC_QUEUE_SIZE 6
#define SOUND_BANK_COUNT 10
#define MAX_CHANNELS_PER_SOUND 1

#define SEQUENCE_NONE 0xFF

#define SAMPLES_TO_OVERPRODUCE 0x10
#define EXTRA_BUFFERED_AI_SAMPLES_TARGET 0x40

// No-op printf macro which leaves string literals in rodata in IDO. (IDO
// doesn't support variadic macros, so instead they let the parameter list
// expand to a no-op comma expression.) See also goddard/gd_main.h.
#ifdef __sgi
#define stubbed_printf
#else
#define stubbed_printf(...)
#endif

struct Sound {
    s32 soundBits;
    f32 *position;
}; // size = 0x8

struct ChannelVolumeScaleFade {
    f32 velocity;
    u8 target;
    f32 current;
    u16 remDuration;
}; // size = 0x10

struct SoundCharacteristics {
    f32 *x;
    f32 *y;
    f32 *z;
    f32 distance;
    u32 priority;
    u32 soundBits; // packed bits, same as first arg to play_sound
    u8 soundStatus;
    u8 unk19; // ttl? sometimes set to 10
    u8 prev;
    u8 next;
}; // size = 0x1C

struct SequenceQueueItem {
    u8 seqId;
    u8 priority;
}; // size = 0x2

// data
#ifdef VERSION_EU
// moved to bss in data.c
s32 gAudioErrorFlags2 = 0;
#else
s32 gAudioErrorFlags = 0;
#endif
s32 sGameLoopTicked = 0;

// Dialog sounds
// The US difference is the sound for DIALOG_037 ("I win! You lose! Ha ha ha ha!
// You're no slouch, but I'm a better sledder! Better luck next time!"), spoken
// by Koopa instead of the penguin in JP.

#define UKIKI 0
#define TUXIE 1
#define BOWS1 2 // Bowser Intro / Doors Laugh
#define KOOPA 3
#define KBOMB 4
#define BOO 5
#define BOMB 6
#define BOWS2 7 // Bowser Battle Laugh
#define GRUNT 8
#define WIGLR 9
#define YOSHI 10
#define _ 0xFF

#ifdef VERSION_JP
#define DIFF KOOPA
#else
#define DIFF TUXIE
#endif

u8 sDialogSpeaker[] = {
    //       0      1      2      3      4      5      6      7      8      9
    /* 0*/ _,     BOMB,  BOMB,  BOMB,  BOMB,  KOOPA, KOOPA, KOOPA, _,     KOOPA,
    /* 1*/ _,     _,     _,     _,     _,     _,     _,     KBOMB, _,     _,
    /* 2*/ _,     BOWS1, BOWS1, BOWS1, BOWS1, BOWS1, BOWS1, BOWS1, BOWS1, BOWS1,
    /* 3*/ _,     _,     _,     _,     _,     _,     _,     DIFF,  _,     _,
    /* 4*/ _,     KOOPA, _,     _,     _,     _,     _,     BOMB,  _,     _,
    /* 5*/ _,     _,     _,     _,     _,     TUXIE, TUXIE, TUXIE, TUXIE, TUXIE,
    /* 6*/ _,     _,     _,     _,     _,     _,     _,     BOWS2, _,     _,
    /* 7*/ _,     _,     _,     _,     _,     _,     _,     _,     _,     UKIKI,
    /* 8*/ UKIKI, _,     _,     _,     _,     BOO,   _,     _,     _,     _,
    /* 9*/ BOWS2, _,     BOWS2, BOWS2, _,     _,     _,     _,     BOO,   BOO,
    /*10*/ UKIKI, UKIKI, _,     _,     _,     BOMB,  BOMB,  BOO,   BOO,   _,
    /*11*/ _,     _,     _,     _,     GRUNT, GRUNT, KBOMB, GRUNT, GRUNT, _,
    /*12*/ _,     _,     _,     _,     _,     _,     _,     _,     KBOMB, _,
    /*13*/ _,     _,     TUXIE, _,     _,     _,     _,     _,     _,     _,
    /*14*/ _,     _,     _,     _,     _,     _,     _,     _,     _,     _,
    /*15*/ WIGLR, WIGLR, WIGLR, _,     _,     _,     _,     _,     _,     _,
    /*16*/ _,     YOSHI, _,     _,     _,     _,     _,     _,     WIGLR, _
};
#undef _
STATIC_ASSERT(ARRAY_COUNT(sDialogSpeaker) == DIALOG_COUNT, "change this array if you are adding dialogs");

s32 sDialogSpeakerVoice[] = {
    SOUND_OBJ_UKIKI_CHATTER_LONG,
    SOUND_OBJ_BIG_PENGUIN_YELL,
    SOUND_OBJ_BOWSER_INTRO_LAUGH,
    SOUND_OBJ_KOOPA_TALK,
    SOUND_OBJ_KING_BOBOMB_TALK,
    SOUND_OBJ_BOO_LAUGH_LONG,
    SOUND_OBJ_BOBOMB_BUDDY_TALK,
    SOUND_OBJ_BOWSER_LAUGH,
    SOUND_OBJ2_BOSS_DIALOG_GRUNT,
    SOUND_OBJ_WIGGLER_TALK,
    SOUND_GENERAL_YOSHI_TALK,
#ifndef VERSION_EU
    NO_SOUND,
    NO_SOUND,
    NO_SOUND,
    NO_SOUND,
#endif
};

u8 sNumProcessedSoundRequests = 0;
u8 sSoundRequestCount = 0;

// Music dynamic tables. A dynamic describes which volumes to apply to which
// channels of a sequence (I think?), and different parts of a level can have
// different dynamics. Each table below specifies first the sequence to apply
// the dynamics to, then a bunch of conditions for when each dynamic applies
// (e.g. "only if Mario's X position is between 100 and 300"), and finally a
// fallback dynamic. Due to the encoding of the tables, the conditions must
// come in the same order as the macros.
// TODO: dynamic isn't a great term for this, it means other things in music...

#define MARIO_X_GE 0
#define MARIO_Y_GE 1
#define MARIO_Z_GE 2
#define MARIO_X_LT 3
#define MARIO_Y_LT 4
#define MARIO_Z_LT 5
#define MARIO_IS_IN_AREA 6
#define MARIO_IS_IN_ROOM 7

#define DYN1(cond1, val1, res) (s16)(1 << (15 - cond1) | res), val1
#define DYN2(cond1, val1, cond2, val2, res)                                                            \
    (s16)(1 << (15 - cond1) | 1 << (15 - cond2) | res), val1, val2
#define DYN3(cond1, val1, cond2, val2, cond3, val3, res)                                               \
    (s16)(1 << (15 - cond1) | 1 << (15 - cond2) | 1 << (15 - cond3) | res), val1, val2, val3

s16 sDynBbh[] = {
    SEQ_LEVEL_SPOOKY,
    DYN1(MARIO_IS_IN_ROOM, BBH_OUTSIDE_ROOM, 6),
    DYN1(MARIO_IS_IN_ROOM, BBH_NEAR_MERRY_GO_ROUND_ROOM, 6),
    5,
};
s16 sDynDdd[] = {
    SEQ_LEVEL_WATER,
    DYN2(MARIO_X_LT, -800, MARIO_IS_IN_AREA, AREA_DDD_WHIRLPOOL & 0xf, 0),
    DYN3(MARIO_Y_GE, -2000, MARIO_X_LT, 470, MARIO_IS_IN_AREA, AREA_DDD_WHIRLPOOL & 0xf, 0),
    DYN2(MARIO_Y_GE, 100, MARIO_IS_IN_AREA, AREA_DDD_SUB & 0xf, 2),
    1,
};
s16 sDynJrb[] = {
    SEQ_LEVEL_WATER,
    DYN2(MARIO_Y_GE, 945, MARIO_X_LT, -5260, 0),
    DYN1(MARIO_IS_IN_AREA, AREA_JRB_SHIP & 0xf, 0),
    DYN1(MARIO_Y_GE, 1000, 0),
    DYN2(MARIO_Y_GE, -3100, MARIO_Z_LT, -900, 2),
    1,
    5, // bogus entry, ignored (was JRB originally intended to have spooky music?)
};
s16 sDynWdw[] = {
    SEQ_LEVEL_UNDERGROUND,
    DYN2(MARIO_Y_LT, -670, MARIO_IS_IN_AREA, AREA_WDW_MAIN & 0xf, 4),
    DYN1(MARIO_IS_IN_AREA, AREA_WDW_TOWN & 0xf, 4),
    3,
};
s16 sDynHmc[] = {
    SEQ_LEVEL_UNDERGROUND,
    DYN2(MARIO_X_GE, 0, MARIO_Y_LT, -203, 4),
    DYN2(MARIO_X_LT, 0, MARIO_Y_LT, -2400, 4),
    3,
};
s16 sDynUnk38[] = {
    SEQ_LEVEL_UNDERGROUND,
    DYN1(MARIO_IS_IN_AREA, 1, 3),
    DYN1(MARIO_IS_IN_AREA, 2, 4),
    DYN1(MARIO_IS_IN_AREA, 3, 7),
    0,
};
s16 sDynNone[] = { SEQ_SOUND_PLAYER, 0 };

u8 sCurrentMusicDynamic = 0xff;
u8 sBackgroundMusicForDynamics = SEQUENCE_NONE;

#define STUB_LEVEL(_0, _1, _2, _3, _4, _5, _6, leveldyn, _8) leveldyn,
#define DEFINE_LEVEL(_0, _1, _2, _3, _4, _5, _6, _7, _8, leveldyn, _10) leveldyn,

#define _ sDynNone
s16 *sLevelDynamics[LEVEL_COUNT] = {
    _,         // LEVEL_NONE
    #include "levels/level_defines.h"
};
#undef _
#undef STUB_LEVEL
#undef DEFINE_LEVEL

struct MusicDynamic {
    /*0x0*/ s16 bits1;
    /*0x2*/ u16 volScale1;
    /*0x4*/ s16 dur1;
    /*0x6*/ s16 bits2;
    /*0x8*/ u16 volScale2;
    /*0xA*/ s16 dur2;
}; // size = 0xC

struct MusicDynamic sMusicDynamics[8] = {
    { 0x0000, 127, 100, 0x0e43, 0, 100 }, // SEQ_LEVEL_WATER
    { 0x0003, 127, 100, 0x0e40, 0, 100 }, // SEQ_LEVEL_WATER
    { 0x0e43, 127, 200, 0x0000, 0, 200 }, // SEQ_LEVEL_WATER
    { 0x02ff, 127, 100, 0x0100, 0, 100 }, // SEQ_LEVEL_UNDERGROUND
    { 0x03f7, 127, 100, 0x0008, 0, 100 }, // SEQ_LEVEL_UNDERGROUND
    { 0x0070, 127, 10, 0x0000, 0, 100 },  // SEQ_LEVEL_SPOOKY
    { 0x0000, 127, 100, 0x0070, 0, 10 },  // SEQ_LEVEL_SPOOKY
    { 0xffff, 127, 100, 0x0000, 0, 100 }, // any (unused)
};

#define STUB_LEVEL(_0, _1, _2, _3, echo1, echo2, echo3, _7, _8) { echo1, echo2, echo3 },
#define DEFINE_LEVEL(_0, _1, _2, _3, _4, _5, echo1, echo2, echo3, _9, _10) { echo1, echo2, echo3 },

u8 gAreaEchoLevel[LEVEL_COUNT][3] = {
    { 0x00, 0x00, 0x00 }, // LEVEL_NONE
#include "levels/level_defines.h"
};
#undef STUB_LEVEL
#undef DEFINE_LEVEL

#define STUB_LEVEL(_0, _1, _2, volume, _4, _5, _6, _7, _8) volume,
#define DEFINE_LEVEL(_0, _1, _2, _3, _4, volume, _6, _7, _8, _9, _10) volume,

u16 D_80332028[LEVEL_COUNT] = {
    20000,    // LEVEL_NONE
    #include "levels/level_defines.h"
};

#undef STUB_LEVEL
#undef DEFINE_LEVEL

#define AUDIO_MAX_DISTANCE US_FLOAT(22000.0)

// Default volume for background music sequences (playing on player 0).
u8 sBackgroundMusicDefaultVolume[] = {
    127, // SEQ_SOUND_PLAYER
    80,  // SEQ_EVENT_CUTSCENE_COLLECT_STAR
    80,  // SEQ_MENU_TITLE_SCREEN
    75,  // SEQ_LEVEL_GRASS
    70,  // SEQ_LEVEL_INSIDE_CASTLE
    75,  // SEQ_LEVEL_WATER
    75,  // SEQ_LEVEL_HOT
    75,  // SEQ_LEVEL_BOSS_KOOPA
    70,  // SEQ_LEVEL_SNOW
    65,  // SEQ_LEVEL_SLIDE
    80,  // SEQ_LEVEL_SPOOKY
    65,  // SEQ_EVENT_PIRANHA_PLANT
    85,  // SEQ_LEVEL_UNDERGROUND
    75,  // SEQ_MENU_STAR_SELECT
    65,  // SEQ_EVENT_POWERUP
    70,  // SEQ_EVENT_METAL_CAP
    65,  // SEQ_EVENT_KOOPA_MESSAGE
    70,  // SEQ_LEVEL_KOOPA_ROAD
    70,  // SEQ_EVENT_HIGH_SCORE
    65,  // SEQ_EVENT_MERRY_GO_ROUND
    80,  // SEQ_EVENT_RACE
    70,  // SEQ_EVENT_CUTSCENE_STAR_SPAWN
    85,  // SEQ_EVENT_BOSS
    75,  // SEQ_EVENT_CUTSCENE_COLLECT_KEY
    75,  // SEQ_EVENT_ENDLESS_STAIRS
    85,  // SEQ_LEVEL_BOSS_KOOPA_FINAL
    70,  // SEQ_EVENT_CUTSCENE_CREDITS
    80,  // SEQ_EVENT_SOLVE_PUZZLE
    80,  // SEQ_EVENT_TOAD_MESSAGE
    70,  // SEQ_EVENT_PEACH_MESSAGE
    75,  // SEQ_EVENT_CUTSCENE_INTRO
    80,  // SEQ_EVENT_CUTSCENE_VICTORY
    70,  // SEQ_EVENT_CUTSCENE_ENDING
    65,  // SEQ_MENU_FILE_SELECT
    0,   // SEQ_EVENT_CUTSCENE_LAKITU (not in JP)
};

STATIC_ASSERT(ARRAY_COUNT(sBackgroundMusicDefaultVolume) == SEQ_COUNT,
              "change this array if you are adding sequences");

u8 sPlayer0CurSeqId = SEQUENCE_NONE;
u8 sMusicDynamicDelay = 0;
u8 D_803320A4[SOUND_BANK_COUNT] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // pointers to head of list
u8 D_803320B0[SOUND_BANK_COUNT] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }; // pointers to head of list
u8 D_803320BC[SOUND_BANK_COUNT] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // only used for debugging
u8 sMaxChannelsForSoundBank[SOUND_BANK_COUNT] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

// Banks 2 and 7 both grew from 0x30 sounds to 0x40 in size in US.
#ifdef VERSION_JP
#define BANK27_SIZE 0x30
#else
#define BANK27_SIZE 0x40
#endif
u8 sNumSoundsPerBank[SOUND_BANK_COUNT] = {
    0x70, 0x30, BANK27_SIZE, 0x80, 0x20, 0x80, 0x20, BANK27_SIZE, 0x80, 0x80,
};
#undef BANK27_SIZE

f32 gDefaultSoundArgs[3] = { 0.0f, 0.0f, 0.0f };
f32 sUnusedSoundArgs[3] = { 1.0f, 1.0f, 1.0f };
u8 sSoundBankDisabled[16] = { 0 };
u8 D_80332108 = 0;
u8 sHasStartedFadeOut = FALSE;
u16 D_80332110 = 0;
u8 sUnused80332114 = 0;  // never read, set to 0
u16 sUnused80332118 = 0; // never read, set to 0
u8 D_8033211C = 0;
u8 D_80332120 = 0;
u8 D_80332124 = 0;

#ifdef VERSION_EU
u8 D_EU_80300558 = 0;
#endif

u8 sBackgroundMusicQueueSize = 0;

#ifndef VERSION_JP
u8 sUnused8033323C = 0; // never read, set to 0
#endif

// bss
#ifndef VERSION_EU
u16 *gCurrAiBuffer;
#endif
struct Sound sSoundRequests[0x100];
// Curiously, this has size 3, despite SEQUENCE_PLAYERS == 4 on EU
struct ChannelVolumeScaleFade D_80360928[3][CHANNELS_MAX];
u8 sUsedChannelsForSoundBank[SOUND_BANK_COUNT];
u8 sCurrentSound[SOUND_BANK_COUNT][MAX_CHANNELS_PER_SOUND]; // index into gSoundBanks
// list item memory for D_803320A4 and D_803320B0
struct SoundCharacteristics gSoundBanks[SOUND_BANK_COUNT][40];
u8 D_80363808[SOUND_BANK_COUNT];
u8 D_80363812;
static u8 sCapVolumeTo40;
struct SequenceQueueItem sBackgroundMusicQueue[MAX_BG_MUSIC_QUEUE_SIZE];

#ifdef VERSION_EU
s32 unk;
OSMesgQueue OSMesgQueue0;
OSMesgQueue OSMesgQueue1;
OSMesgQueue OSMesgQueue2;
OSMesgQueue OSMesgQueue3;
extern OSMesgQueue *OSMesgQueues[];
struct EuAudioCmd sAudioCmd[0x100];
OSMesg OSMesg0;
s32 pad1; // why is there 1 s32 here
OSMesg OSMesg1;
s32 pad2[2]; // it's not just that the struct is bigger than we think, because there are 2 here
OSMesg OSMesg2; // and none here. wth nintendo
OSMesg OSMesg3;
#endif


#ifdef VERSION_JP
typedef u16 FadeT;
#else
typedef s32 FadeT;
#endif

// some sort of main thread -> sound thread dispatchers
extern void func_802ad728(u32 bits, f32 arg);
extern void func_802ad74c(u32 bits, u32 arg);
extern void func_802ad770(u32 bits, s8 arg);

void func_8031E0E4(u8 bankIndex, u8 item);

// Local functions that could be static but are defined in/called from GLOBAL_ASM blocks,
// or not part of the large block of static functions.
void update_game_sound(void);
void play_sequence(u8 player, u8 seqId, u16 fadeTimer);
void fade_channel_volume_scale(u8 player, u8 channelId, u8 targetScale, u16 fadeTimer);
void func_8031F96C(u8 player);
void process_level_music_dynamics(void);
u8 func_803200E4(u16 fadeTimer);
void func_80320ED8(void);

#ifndef VERSION_JP
void unused_8031E4F0(void) {
    // This is a debug function which is almost entirely optimized away,
    // except for loops, string literals, and a read of a volatile variable.
    // The string literals have allowed it to be partially reconstructed.
    s32 i;

    stubbed_printf("AUTOSEQ ");
    stubbed_printf("%2x %2x <%5x : %5x / %5x>\n", gSeqLoadedPool.temporary.entries[0].id,
                   gSeqLoadedPool.temporary.entries[1].id, gSeqLoadedPool.temporary.entries[0].size,
                   gSeqLoadedPool.temporary.entries[1].size, gSeqLoadedPool.temporary.pool.size);

    stubbed_printf("AUTOBNK ");
    stubbed_printf("%2x %3x <%5x : %5x / %5x>\n", gBankLoadedPool.temporary.entries[0].id,
                   gBankLoadedPool.temporary.entries[1].id, gBankLoadedPool.temporary.entries[0].size,
                   gBankLoadedPool.temporary.entries[1].size, gBankLoadedPool.temporary.pool.size);

    stubbed_printf("STAYSEQ ");
    stubbed_printf("[%2x] <%5x / %5x>\n", gSeqLoadedPool.persistent.numEntries,
                   gSeqLoadedPool.persistent.pool.cur - gSeqLoadedPool.persistent.pool.start,
                   gSeqLoadedPool.persistent.pool.size);
    for (i = 0; (u32) i < gSeqLoadedPool.persistent.numEntries; i++) {
        stubbed_printf("%2x ", gSeqLoadedPool.persistent.entries[i].id);
    }
    stubbed_printf("\n");

    stubbed_printf("STAYBNK ");
    stubbed_printf("[%2x] <%5x / %5x>\n", gBankLoadedPool.persistent.numEntries,
                   gBankLoadedPool.persistent.pool.cur - gBankLoadedPool.persistent.pool.start,
                   gBankLoadedPool.persistent.pool.size);
    for (i = 0; (u32) i < gBankLoadedPool.persistent.numEntries; i++) {
        stubbed_printf("%2x ", gBankLoadedPool.persistent.entries[i].id);
    }
    stubbed_printf("\n\n");

    stubbed_printf("    0123456789ABCDEF0123456789ABCDEF01234567\n");
    stubbed_printf("--------------------------------------------\n");

    // gSeqLoadStatus/gBankLoadStatus, 4 entries at a time?
    stubbed_printf("SEQ ");
    for (i = 0; i < 40; i++) {
        stubbed_printf("%1x", 0);
    }
    stubbed_printf("\n");

    stubbed_printf("BNK ");
    for (i = 0; i < 40; i += 4) {
        stubbed_printf("%1x", 0);
    }
    stubbed_printf("\n");

    stubbed_printf("FIXHEAP ");
    stubbed_printf("%4x / %4x\n", 0, 0);
    stubbed_printf("DRVHEAP ");
    stubbed_printf("%5x / %5x\n", 0, 0);
    stubbed_printf("DMACACHE  %4d Blocks\n", 0);
    stubbed_printf("CHANNELS  %2d / MAX %3d \n", 0, 0);

    stubbed_printf("TEMPOMAX  %d\n", gTempoInternalToExternal / TEMPO_SCALE);
    stubbed_printf("TEMPO G0  %d\n", gSequencePlayers[SEQ_PLAYER_LEVEL].tempo / TEMPO_SCALE);
    stubbed_printf("TEMPO G1  %d\n", gSequencePlayers[SEQ_PLAYER_ENV].tempo / TEMPO_SCALE);
    stubbed_printf("TEMPO G2  %d\n", gSequencePlayers[SEQ_PLAYER_SFX].tempo / TEMPO_SCALE);
    stubbed_printf("DEBUGFLAG  %8x\n", gAudioErrorFlags);
}

void unused_8031E568(void) {
    stubbed_printf("COUNT %8d\n", gAudioFrameCount);
}
#endif

#ifdef VERSION_EU
const char unusedErrorStr1[] = "Error : Queue is not empty ( %x ) \n";
const char unusedErrorStr2[] = "specchg error\n";
#endif


/**
 * Fade out a sequence player
 */
#if defined(VERSION_EU)

void audio_reset_session_eu(s32 presetId) {
    OSMesg mesg;

    osRecvMesg(OSMesgQueues[3], &mesg, OS_MESG_NOBLOCK);
    osSendMesg(OSMesgQueues[2], (OSMesg) presetId, OS_MESG_NOBLOCK);
    osRecvMesg(OSMesgQueues[3], &mesg, OS_MESG_BLOCK);
    if ((s32) mesg != presetId) {
        osRecvMesg(OSMesgQueues[3], &mesg, OS_MESG_BLOCK);
    }
}

#else

void sequence_player_fade_out_internal(s32 player, FadeT fadeOutTime) {
    struct SequencePlayer *seqPlayer = &gSequencePlayers[player];
#ifndef VERSION_JP
    if (fadeOutTime == 0) {
        fadeOutTime++;
    }
#endif

    seqPlayer->fadeVelocity = -(seqPlayer->fadeVolume / fadeOutTime);
    seqPlayer->state = SEQUENCE_PLAYER_STATE_FADE_OUT;
    seqPlayer->fadeTimer = fadeOutTime;
}

void func_8031D690(s32 player, FadeT fadeInTime) {
    struct SequencePlayer *seqPlayer = &gSequencePlayers[player];

    if (fadeInTime == 0 || seqPlayer->state == SEQUENCE_PLAYER_STATE_FADE_OUT) {
        return;
    }

    seqPlayer->state = SEQUENCE_PLAYER_STATE_2;
    seqPlayer->fadeTimer = fadeInTime;
    seqPlayer->fadeVolume = 0.0f;
    seqPlayer->fadeVelocity = 0.0f;
}
#endif

void func_8031D6E4(s32 player, FadeT fadeTimer, u8 fadePercentage) {
    struct SequencePlayer *seqPlayer = &gSequencePlayers[player];
    f32 fadeVolume;

#ifdef VERSION_EU
    if (seqPlayer->state == 2) {
        return;
    }
#else
    if (seqPlayer->state == SEQUENCE_PLAYER_STATE_FADE_OUT) {
        return;
    }
#endif

    fadeVolume = (FLOAT_CAST(fadePercentage) / EU_FLOAT(100.0) * seqPlayer->fadeVolume);
    seqPlayer->volume = seqPlayer->fadeVolume;
    seqPlayer->fadeTimer = 0;
    if (fadeTimer == 0) {
        seqPlayer->fadeVolume = fadeVolume;
        return;
    }
    seqPlayer->fadeVelocity = (fadeVolume - seqPlayer->fadeVolume) / fadeTimer;
#ifdef VERSION_EU
    seqPlayer->state = 0;
#else
    seqPlayer->state = SEQUENCE_PLAYER_STATE_4;
#endif
    seqPlayer->fadeTimer = fadeTimer;
}

void func_8031D7B0(s32 player, FadeT fadeTimer) {
    struct SequencePlayer *seqPlayer = &gSequencePlayers[player];

#ifdef VERSION_EU
    if (seqPlayer->state == 2) {
        return;
    }
#else
    if (seqPlayer->state == SEQUENCE_PLAYER_STATE_FADE_OUT) {
        return;
    }
#endif

    seqPlayer->fadeTimer = 0;
    if (fadeTimer == 0) {
        seqPlayer->fadeVolume = seqPlayer->volume;
        return;
    }
    seqPlayer->fadeVelocity = (seqPlayer->volume - seqPlayer->fadeVolume) / fadeTimer;
#ifdef VERSION_EU
    seqPlayer->state = 0;
#else
    seqPlayer->state = SEQUENCE_PLAYER_STATE_2;
#endif
    seqPlayer->fadeTimer = fadeTimer;
}

void func_8031D838(s32 player, FadeT fadeInTime, u8 targetVolume) {
    struct SequencePlayer *seqPlayer = &gSequencePlayers[player];

#ifndef VERSION_EU
    if (seqPlayer->state == SEQUENCE_PLAYER_STATE_FADE_OUT) {
        return;
    }
#endif

    seqPlayer->fadeTimer = 0;
    if (fadeInTime == 0) {
        seqPlayer->fadeVolume = (FLOAT_CAST(targetVolume) / EU_FLOAT(127.0));
        return;
    }
    seqPlayer->fadeVelocity =
        (((f32)(FLOAT_CAST(targetVolume) / EU_FLOAT(127.0)) - seqPlayer->fadeVolume) / (f32) fadeInTime);
#ifdef VERSION_EU
    seqPlayer->state = 0;
#else
    seqPlayer->state = SEQUENCE_PLAYER_STATE_4;
#endif
    seqPlayer->fadeTimer = fadeInTime;
}

#ifdef VERSION_EU
extern void func_802ad7a0(void);

void maybe_tick_game_sound(void) {
    if (sGameLoopTicked != 0) {
        update_game_sound();
        sGameLoopTicked = 0;
    }
    func_802ad7a0();
}

void func_eu_802e9bec(s32 player, s32 channel, s32 arg2) {
    // EU verson of unused_803209D8
    // chan->stopSomething2 = arg2?
    func_802ad770(0x08000000 | (player & 0xff) << 16 | (channel & 0xff) << 8, (s8) arg2);
}

#else

struct SPTask *create_next_audio_frame_task(void) {
    u32 samplesRemainingInAI;
    s32 writtenCmds;
    s32 index;
    OSTask_t *task;
    s32 oldDmaCount;
    s32 flags;

    gAudioFrameCount++;
    if (gAudioLoadLock != AUDIO_LOCK_NOT_LOADING) {
        stubbed_printf("DAC:Lost 1 Frame.\n");
        return NULL;
    }

    gAudioTaskIndex ^= 1;
    gCurrAiBufferIndex++;
    gCurrAiBufferIndex %= NUMAIBUFFERS;
    index = (gCurrAiBufferIndex - 2 + NUMAIBUFFERS) % NUMAIBUFFERS;
    samplesRemainingInAI = osAiGetLength() / 4;

    // Audio is triple buffered; the audio interface reads from two buffers
    // while the third is being written by the RSP. More precisely, the
    // lifecycle is:
    // - this function computes an audio command list
    // - wait for vblank
    // - the command list is sent to the RSP (we could have sent it to the
    //   RSP before the vblank, but that gives the RSP less time to finish)
    // - wait for vblank
    // - the RSP is now expected to be finished, and we can send its output
    //   on to the AI
    // Here we thus send to the AI the sound that was generated two frames ago.
    if (gAiBufferLengths[index] != 0) {
        osAiSetNextBuffer(gAiBuffers[index], gAiBufferLengths[index] * 4);
    }

    oldDmaCount = gCurrAudioFrameDmaCount;
    // There has to be some sort of no-op if here, but it's not exactly clear
    // how it should look... It's also very unclear why gCurrAudioFrameDmaQueue
    // isn't read from here, despite gCurrAudioFrameDmaCount being reset.
    if (oldDmaCount > AUDIO_FRAME_DMA_QUEUE_SIZE) {
        stubbed_printf("DMA: Request queue over.( %d )\n", oldDmaCount);
    }
    gCurrAudioFrameDmaCount = 0;

    gAudioTask = &gAudioTasks[gAudioTaskIndex];
    gAudioCmd = gAudioCmdBuffers[gAudioTaskIndex];

    index = gCurrAiBufferIndex;
    gCurrAiBuffer = gAiBuffers[index];
    gAiBufferLengths[index] = ((gSamplesPerFrameTarget - samplesRemainingInAI +
         EXTRA_BUFFERED_AI_SAMPLES_TARGET) & ~0xf) + SAMPLES_TO_OVERPRODUCE;
    if (gAiBufferLengths[index] < gMinAiBufferLength) {
        gAiBufferLengths[index] = gMinAiBufferLength;
    }
    if (gAiBufferLengths[index] > gSamplesPerFrameTarget + SAMPLES_TO_OVERPRODUCE) {
        gAiBufferLengths[index] = gSamplesPerFrameTarget + SAMPLES_TO_OVERPRODUCE;
    }

    if (sGameLoopTicked != 0) {
        update_game_sound();
        sGameLoopTicked = 0;
    }

    // For the function to match we have to preserve some arbitrary variable
    // across this function call.
    flags = 0;
    gAudioCmd = synthesis_execute(gAudioCmd, &writtenCmds, gCurrAiBuffer, gAiBufferLengths[index]);
    gAudioRandom = ((gAudioRandom + gAudioFrameCount) * gAudioFrameCount);

    index = gAudioTaskIndex;
    gAudioTask->msgqueue = NULL;
    gAudioTask->msg = NULL;

    task = &gAudioTask->task.t;
    task->type = M_AUDTASK;
    task->flags = flags;
    task->ucode_boot = rspF3DBootStart;
    task->ucode_boot_size = (u8 *) rspF3DBootEnd - (u8 *) rspF3DBootStart;
    task->ucode = rspAspMainStart;
    task->ucode_size = 0x800; // (this size is ignored)
    task->ucode_data = rspAspMainDataStart;
    task->ucode_data_size = (rspAspMainDataEnd - rspAspMainDataStart) * sizeof(u64);
    task->dram_stack = NULL;
    task->dram_stack_size = 0;
    task->output_buff = NULL;
    task->output_buff_size = NULL;
    task->data_ptr = gAudioCmdBuffers[index];
    task->data_size = writtenCmds * sizeof(u64);

    // The audio task never yields, so having a yield buffer is pointless.
    // This wastefulness was fixed in US.
#ifdef VERSION_JP
    task->yield_data_ptr = (u64 *) gAudioSPTaskYieldBuffer;
    task->yield_data_size = OS_YIELD_AUDIO_SIZE;
#else
    task->yield_data_ptr = NULL;
    task->yield_data_size = 0;
#endif

    decrease_sample_dma_ttls();
    return gAudioTask;
}
#endif

void play_sound(s32 soundBits, f32 *pos) {
    sSoundRequests[sSoundRequestCount].soundBits = soundBits;
    sSoundRequests[sSoundRequestCount].position = pos;
    sSoundRequestCount++;
}

void process_sound_request(u32 bits, f32 *pos) {
    u8 bankIndex;
    u8 index;
    u8 counter = 0;
    u8 soundId;
    f32 dist;
    const f32 one = 1.0f;

    bankIndex = (bits & SOUNDARGS_MASK_BANK) >> SOUNDARGS_SHIFT_BANK;
    soundId = (bits & SOUNDARGS_MASK_SOUNDID) >> SOUNDARGS_SHIFT_SOUNDID;
    if (soundId >= sNumSoundsPerBank[bankIndex] || sSoundBankDisabled[bankIndex]) {
        return;
    }

    index = gSoundBanks[bankIndex][0].next;
    while (index != 0xff && index != 0) {
        if (gSoundBanks[bankIndex][index].x == pos) {
            if ((gSoundBanks[bankIndex][index].soundBits & SOUNDARGS_MASK_PRIORITY)
                <= (bits & SOUNDARGS_MASK_PRIORITY)) {
                if ((gSoundBanks[bankIndex][index].soundBits & SOUND_LO_BITFLAG_UNK8) != 0
                    || (bits & SOUNDARGS_MASK_SOUNDID)
                           != (gSoundBanks[bankIndex][index].soundBits & SOUNDARGS_MASK_SOUNDID)) {
                    func_8031E0E4(bankIndex, index);
                    gSoundBanks[bankIndex][index].soundBits = bits;
                    gSoundBanks[bankIndex][index].soundStatus = bits & SOUNDARGS_MASK_STATUS;
                }
                gSoundBanks[bankIndex][index].unk19 = 10;
            }
            index = 0;
        } else {
            index = gSoundBanks[bankIndex][index].next;
        }
        counter++;
    }

    if (counter == 0) {
        D_80363808[bankIndex] = 32;
    }

    if (gSoundBanks[bankIndex][D_803320B0[bankIndex]].next != 0xff && index != 0) {
        index = D_803320B0[bankIndex];
        dist = sqrtf(pos[0] * pos[0] + pos[1] * pos[1] + pos[2] * pos[2]) * one;
        gSoundBanks[bankIndex][index].x = &pos[0];
        gSoundBanks[bankIndex][index].y = &pos[1];
        gSoundBanks[bankIndex][index].z = &pos[2];
        gSoundBanks[bankIndex][index].distance = dist;
        gSoundBanks[bankIndex][index].soundBits = bits;
        gSoundBanks[bankIndex][index].soundStatus = bits & SOUNDARGS_MASK_STATUS;
        gSoundBanks[bankIndex][index].unk19 = 10;
        gSoundBanks[bankIndex][index].prev = D_803320A4[bankIndex];
        gSoundBanks[bankIndex][D_803320A4[bankIndex]].next = D_803320B0[bankIndex];
        D_803320A4[bankIndex] = D_803320B0[bankIndex];
        D_803320B0[bankIndex] = gSoundBanks[bankIndex][D_803320B0[bankIndex]].next;
        gSoundBanks[bankIndex][D_803320B0[bankIndex]].prev = 0xff;
        gSoundBanks[bankIndex][index].next = 0xff;
    }
}

/**
 * Processes all sound requests
 */
void process_all_sound_requests(void) {
    struct Sound *sound;

    while (sSoundRequestCount != sNumProcessedSoundRequests) {
        sound = &sSoundRequests[sNumProcessedSoundRequests];
        process_sound_request(sound->soundBits, sound->position);
        sNumProcessedSoundRequests++;
    }
}

void func_8031DFE8(u8 bankIndex, u8 item) {
    // move item from list D_803320A4 to list D_803320B0
    if (D_803320A4[bankIndex] == item) {
        D_803320A4[bankIndex] = gSoundBanks[bankIndex][item].prev;
    } else {
        gSoundBanks[bankIndex][gSoundBanks[bankIndex][item].next].prev =
            gSoundBanks[bankIndex][item].prev;
    }

    gSoundBanks[bankIndex][gSoundBanks[bankIndex][item].prev].next = gSoundBanks[bankIndex][item].next;
    gSoundBanks[bankIndex][item].next = D_803320B0[bankIndex];
    gSoundBanks[bankIndex][item].prev = -1;
    gSoundBanks[bankIndex][D_803320B0[bankIndex]].prev = item;
    D_803320B0[bankIndex] = item;
}

void func_8031E0E4(u8 bankIndex, u8 item) {
    if (gSoundBanks[bankIndex][item].soundBits & SOUND_LO_BITFLAG_UNK1) {
        D_80332110 &= (1 << bankIndex) ^ 0xffff;
        func_803200E4(50);
    }
}

void func_8031E16C(u8 bankIndex) {
    u32 val2;
    u8 spDB;
    u8 i;
    u8 j;
    u8 soundIndex;
    u32 sp98[16] = { 0x10000000, 0x10000000, 0x10000000, 0x10000000, 0x10000000, 0x10000000,
                     0x10000000, 0x10000000, 0x10000000, 0x10000000, 0x10000000, 0x10000000,
                     0x10000000, 0x10000000, 0x10000000, 0x10000000 };
    u8 sp88[16] = { 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff,
                    0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff };
    u8 sp78[16] = { 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff,
                    0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff };
    u8 sp77 = 0;
    u8 val;

    soundIndex = gSoundBanks[bankIndex][0].next;
    while (soundIndex != 0xff) {
        spDB = soundIndex;

        if ((gSoundBanks[bankIndex][soundIndex].soundBits
             & (SOUND_LO_BITFLAG_UNK8 | SOUNDARGS_MASK_STATUS))
            == (SOUND_LO_BITFLAG_UNK8 | SOUND_STATUS_STARTING)) {
            if (gSoundBanks[bankIndex][soundIndex].unk19-- == 0) {
                gSoundBanks[bankIndex][soundIndex].soundBits = NO_SOUND;
            }
        } else if ((gSoundBanks[bankIndex][soundIndex].soundBits & SOUND_LO_BITFLAG_UNK8) == 0) {
            if (gSoundBanks[bankIndex][soundIndex].unk19-- == 8) {
                func_8031E0E4(bankIndex, soundIndex);
                gSoundBanks[bankIndex][soundIndex].soundBits = NO_SOUND;
            }
        }

        if (gSoundBanks[bankIndex][soundIndex].soundBits == NO_SOUND
            && gSoundBanks[bankIndex][soundIndex].soundStatus == SOUND_STATUS_STARTING) {
            spDB = gSoundBanks[bankIndex][soundIndex].prev;
            gSoundBanks[bankIndex][soundIndex].soundStatus = SOUND_STATUS_STOPPED;
            func_8031DFE8(bankIndex, soundIndex);
        }

        if (gSoundBanks[bankIndex][soundIndex].soundStatus != SOUND_STATUS_STOPPED
            && soundIndex == spDB) {
            gSoundBanks[bankIndex][soundIndex].distance =
                sqrtf((*gSoundBanks[bankIndex][soundIndex].x * *gSoundBanks[bankIndex][soundIndex].x)
                      + (*gSoundBanks[bankIndex][soundIndex].y * *gSoundBanks[bankIndex][soundIndex].y)
                      + (*gSoundBanks[bankIndex][soundIndex].z * *gSoundBanks[bankIndex][soundIndex].z))
                * 1;

            val = (gSoundBanks[bankIndex][soundIndex].soundBits & SOUNDARGS_MASK_PRIORITY)
                  >> SOUNDARGS_SHIFT_PRIORITY;
            if (gSoundBanks[bankIndex][soundIndex].soundBits & SOUND_PL_BITFLAG_UNK4) {
                gSoundBanks[bankIndex][soundIndex].priority = 0x4c * (0xff - val);
            } else if (*gSoundBanks[bankIndex][soundIndex].z > 0.0f) {
                gSoundBanks[bankIndex][soundIndex].priority =
                    (u32) gSoundBanks[bankIndex][soundIndex].distance
                    + (u32)(*gSoundBanks[bankIndex][soundIndex].z / US_FLOAT(6.0))
                    + 0x4c * (0xff - val);
            } else {
                gSoundBanks[bankIndex][soundIndex].priority =
                    (u32) gSoundBanks[bankIndex][soundIndex].distance + 0x4c * (0xff - val);
            }

            for (i = 0; i < sMaxChannelsForSoundBank[bankIndex]; i++) {
                if (sp98[i] >= gSoundBanks[bankIndex][soundIndex].priority) {
                    for (j = sMaxChannelsForSoundBank[bankIndex] - 1; j > i; j--) {
                        sp98[j] = sp98[j - 1];
                        sp88[j] = sp88[j - 1];
                        sp78[j] = sp78[j - 1];
                    }
                    sp98[i] = gSoundBanks[bankIndex][soundIndex].priority;
                    sp88[i] = soundIndex;
                    sp78[i] = gSoundBanks[bankIndex][soundIndex].soundStatus;
                    i = sMaxChannelsForSoundBank[bankIndex];
                }
            }
            sp77++;
        }
        soundIndex = gSoundBanks[bankIndex][spDB].next;
    }

    D_803320BC[bankIndex] = sp77;
    sUsedChannelsForSoundBank[bankIndex] = sMaxChannelsForSoundBank[bankIndex];

    for (i = 0; i < sUsedChannelsForSoundBank[bankIndex]; i++) {
        for (soundIndex = 0; soundIndex < sUsedChannelsForSoundBank[bankIndex]; soundIndex++) {
            if (sp88[soundIndex] != 0xff && sCurrentSound[bankIndex][i] == sp88[soundIndex]) {
                sp88[soundIndex] = 0xff;
                soundIndex = 0xfe;
            }
        }

        if (soundIndex != 0xff) {
            if (sCurrentSound[bankIndex][i] != 0xff) {
                if (gSoundBanks[bankIndex][sCurrentSound[bankIndex][i]].soundBits == NO_SOUND) {
                    if (gSoundBanks[bankIndex][sCurrentSound[bankIndex][i]].soundStatus
                        == SOUND_STATUS_PLAYING) {
                        gSoundBanks[bankIndex][sCurrentSound[bankIndex][i]].soundStatus =
                            SOUND_STATUS_STOPPED;
                        func_8031DFE8(bankIndex, sCurrentSound[bankIndex][i]);
                    }
                }
                val2 = gSoundBanks[bankIndex][sCurrentSound[bankIndex][i]].soundBits
                       & (SOUND_LO_BITFLAG_UNK8 | SOUNDARGS_MASK_STATUS);
                if (val2 >= (SOUND_LO_BITFLAG_UNK8 | SOUND_STATUS_PLAYING)
                    && gSoundBanks[bankIndex][sCurrentSound[bankIndex][i]].soundStatus
                           != SOUND_STATUS_STOPPED) {
#ifndef VERSION_JP
                    func_8031E0E4(bankIndex, sCurrentSound[bankIndex][i]);
#endif
                    gSoundBanks[bankIndex][sCurrentSound[bankIndex][i]].soundBits = NO_SOUND;
                    gSoundBanks[bankIndex][sCurrentSound[bankIndex][i]].soundStatus = SOUND_STATUS_STOPPED;
                    func_8031DFE8(bankIndex, sCurrentSound[bankIndex][i]);
                } else {
                    if (val2 == SOUND_STATUS_PLAYING
                        && gSoundBanks[bankIndex][sCurrentSound[bankIndex][i]].soundStatus
                               != SOUND_STATUS_STOPPED) {
                        gSoundBanks[bankIndex][sCurrentSound[bankIndex][i]].soundStatus =
                            SOUND_STATUS_STARTING;
                    }
                }
            }
            sCurrentSound[bankIndex][i] = 0xff;
        }
    }

    for (soundIndex = 0; soundIndex < sUsedChannelsForSoundBank[bankIndex]; soundIndex++) {
        if (sp88[soundIndex] != 0xff) {
            for (i = 0; i < sUsedChannelsForSoundBank[bankIndex]; i++) {
                if (sCurrentSound[bankIndex][i] == 0xff) {
                    sCurrentSound[bankIndex][i] = sp88[soundIndex];
                    gSoundBanks[bankIndex][sp88[soundIndex]].soundBits =
                        (gSoundBanks[bankIndex][sp88[soundIndex]].soundBits & ~SOUNDARGS_MASK_STATUS)
                        + 1;
                    sp88[i] = 0xff;
                    i = 0xfe; // "break;"
                }
            }
        }
    }
}

/**
 * Given an x and z coordinates, return the pan. This is a value between 0 and
 * 1 that represents the audio direction.
 *
 * Pan:
 * 0.0 - fully left
 * 0.5 - center pan
 * 1.0 - fully right
 */
f32 get_sound_pan(f32 x, f32 z) {
    f32 absX;
    f32 absZ;
    f32 pan;

    absX = (x < 0 ? -x : x);
    if (absX > AUDIO_MAX_DISTANCE) {
        absX = AUDIO_MAX_DISTANCE;
    }

    absZ = (z < 0 ? -z : z);
    if (absZ > AUDIO_MAX_DISTANCE) {
        absZ = AUDIO_MAX_DISTANCE;
    }

    // There are 4 panning equations (12-hr clock used for angles)
    // 1. (0,0) fully-centered pan
    // 2. far right pan: between 1:30 and 4:30
    // 3. far left pan: between 7:30 and 10:30
    // 4. center pan: between 4:30 and 7:30 or between 10:30 and 1:30
    if (x == US_FLOAT(0.0) && z == US_FLOAT(0.0)) {
        // x and y being 0 results in a center pan
        pan = US_FLOAT(0.5);
    } else if (x >= US_FLOAT(0.0) && absX >= absZ) {
        // far right pan
        pan = US_FLOAT(1.0) - (US_FLOAT(44000.0) - absX) / (US_FLOAT(3.0) * (US_FLOAT(44000.0) - absZ));
    } else if (x < 0 && absX > absZ) {
        // far left pan
        pan = (US_FLOAT(44000.0) - absX) / (US_FLOAT(3.0) * (US_FLOAT(44000.0) - absZ));
    } else {
        // center pan
        pan = 0.5 + x / (US_FLOAT(6.0) * absZ);
    }

    return pan;
}

f32 get_sound_dynamics(u8 bankIndex, u8 item, f32 arg2) {
    f32 f0;
    f32 intensity;
#ifndef VERSION_JP
    s32 div = bankIndex < 3 ? 2 : 3;
#endif

    if (!(gSoundBanks[bankIndex][item].soundBits & SOUND_PL_BITFLAG_UNK1)) {
#ifdef VERSION_JP
        f0 = D_80332028[gCurrLevelNum];
        if (f0 < gSoundBanks[bankIndex][item].distance) {
            intensity = 0.0f;
        } else {
            intensity = 1.0 - gSoundBanks[bankIndex][item].distance / f0;
        }
#else
        if (gSoundBanks[bankIndex][item].distance > AUDIO_MAX_DISTANCE) {
            intensity = 0.0f;
        } else {
            f0 = D_80332028[gCurrLevelNum] / div;
            if (f0 < gSoundBanks[bankIndex][item].distance) {
                intensity = ((AUDIO_MAX_DISTANCE - gSoundBanks[bankIndex][item].distance)
                             / (AUDIO_MAX_DISTANCE - f0))
                            * (1.0f - arg2);
            } else {
                intensity = 1.0f - gSoundBanks[bankIndex][item].distance / f0 * arg2;
            }
        }
#endif

        if (gSoundBanks[bankIndex][item].soundBits & SOUND_PL_BITFLAG_UNK2) {
#ifdef VERSION_JP
            if (intensity != 0.0)
#else
            if (intensity >= 0.08f)
#endif
            {
                intensity -= (f32)(gAudioRandom & 0xf) / US_FLOAT(192.0);
            }
        }
    } else {
        intensity = 1.0f;
    }

    return arg2 * intensity * intensity + 1.0f - arg2;
}

f32 get_sound_freq_scale(u8 bankIndex, u8 item) {
    f32 f2;

    if (!(gSoundBanks[bankIndex][item].soundBits & SOUND_PL_BITFLAG_UNK8)) {
        f2 = gSoundBanks[bankIndex][item].distance / AUDIO_MAX_DISTANCE;
        if (gSoundBanks[bankIndex][item].soundBits & SOUND_PL_BITFLAG_UNK2) {
            f2 += (f32)(gAudioRandom & 0xff) / US_FLOAT(64.0);
        }
    } else {
        f2 = 0.0f;
    }
    return f2 / US_FLOAT(15.0) + US_FLOAT(1.0);
}
#ifdef VERSION_JP
#define VAL 48.0
#else
#define VAL 40.0f
#endif
u8 get_sound_reverb(UNUSED u8 bankIndex, UNUSED u8 item, u8 channelIndex) {
    u8 area;
    u8 level;
    u8 reverb;

#ifndef VERSION_JP
    if (gSoundBanks[bankIndex][item].soundBits & SOUND_NO_ECHO) {
        level = 0;
        area = 0;
    } else {
#endif
        level = (gCurrLevelNum > LEVEL_MAX ? LEVEL_MAX : gCurrLevelNum);
        area = gCurrAreaIndex - 1;
        if (area > 2) {
            area = 2;
        }
#ifndef VERSION_JP
    }
#endif
    reverb = (u8)((u8) gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->soundScriptIO[5]
                  + gAreaEchoLevel[level][area]
                   + (US_FLOAT(1.0) - gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->volume) * VAL);

    if (reverb > 0x7f) {
        reverb = 0x7f;
    }
    return reverb;
}

#undef VAL
static void noop_8031EEC8(void) {
}

void audio_signal_game_loop_tick(void) {
    sGameLoopTicked = 1;
#ifdef VERSION_EU
    maybe_tick_game_sound();
#endif
    noop_8031EEC8();
}

#ifdef VERSION_JP
#define ARG2_VAL1 0.8f
#define ARG2_VAL2 1.0f
#else
#define ARG2_VAL1 0.9f
#define ARG2_VAL2 0.8f
#endif

void update_game_sound(void) {
    u8 soundStatus;
    u8 j;
    u8 soundId;
    u8 bankIndex;
    u8 channelIndex = 0;
    u8 index;
#ifndef VERSION_EU
    f32 ret;
#endif

    process_all_sound_requests();
    process_level_music_dynamics();
    if (gSequencePlayers[SEQ_PLAYER_SFX].channels[0] == &gSequenceChannelNone) {
        return;
    }

    for (bankIndex = 0; bankIndex < SOUND_BANK_COUNT; bankIndex++) {
        func_8031E16C(bankIndex);
        for (j = 0; j < MAX_CHANNELS_PER_SOUND; j++) {
            index = sCurrentSound[bankIndex][j];
            if (index < 0xff && gSoundBanks[bankIndex][index].soundStatus != SOUND_STATUS_STOPPED) {
                soundStatus = gSoundBanks[bankIndex][index].soundBits & SOUNDARGS_MASK_STATUS;
                soundId = (gSoundBanks[bankIndex][index].soundBits >> SOUNDARGS_SHIFT_SOUNDID);
                gSoundBanks[bankIndex][index].soundStatus = soundStatus;
                if (soundStatus == SOUND_STATUS_STARTING) {
                    if (gSoundBanks[bankIndex][index].soundBits & SOUND_LO_BITFLAG_UNK1) {
                        D_80332110 |= 1 << bankIndex;
                        func_803200E4(50);
                    }

                    gSoundBanks[bankIndex][index].soundBits++;
                    gSoundBanks[bankIndex][index].soundStatus = SOUND_STATUS_PLAYING;
                    gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->soundScriptIO[4] = soundId;
                    gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->soundScriptIO[0] = 1;

                    switch (bankIndex) {
                        case 1:
                            if (!(gSoundBanks[bankIndex][index].soundBits & SOUND_PL_BITFLAG_UNK8)) {
                                if (D_80363808[bankIndex] > 8) {
#ifdef VERSION_EU
                                    func_802ad728(0x02020000 | ((channelIndex & 0xff) << 8),
                                                  get_sound_dynamics(bankIndex, index, ARG2_VAL1));
#else
                                    ret = get_sound_dynamics(bankIndex, index, ARG2_VAL1);
                                    gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->volume = ret;
#endif
                                } else {
#ifdef VERSION_EU
                                    func_802ad728(0x02020000 | ((channelIndex & 0xff) << 8),
                                                  get_sound_dynamics(bankIndex, index, ARG2_VAL1) *
                                                  ((D_80363808[bankIndex] + 8.0f) / 16));
#else
                                    ret = get_sound_dynamics(bankIndex, index, ARG2_VAL1);
                                    gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->volume =
                                        (D_80363808[bankIndex] + 8.0f) / 16 * ret;
#endif
                                }
#ifdef VERSION_EU
                                func_802ad770(0x03020000 | ((channelIndex & 0xff) << 8),
                                              get_sound_pan(*gSoundBanks[bankIndex][index].x,
                                                            *gSoundBanks[bankIndex][index].z));
#else
                                gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->pan = get_sound_pan(
                                    *gSoundBanks[bankIndex][index].x, *gSoundBanks[bankIndex][index].z);
#endif

                                if ((gSoundBanks[bankIndex][index].soundBits & SOUNDARGS_MASK_SOUNDID)
                                    == (SOUND_MOVING_FLYING & SOUNDARGS_MASK_SOUNDID)) {
#ifdef VERSION_EU
                                    func_802ad728(0x04020000 | ((channelIndex & 0xff) << 8),
                                                  get_sound_freq_scale(bankIndex, index) +
                                                  ((f32) D_80363808[bankIndex] / US_FLOAT(80.0)));
#else
                                    ret = get_sound_freq_scale(bankIndex, index);
                                    gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->freqScale =
                                        ((f32) D_80363808[bankIndex] / US_FLOAT(80.0)) + ret;
#endif
                                } else {
#ifdef VERSION_EU
                                    func_802ad728(0x04020000 | ((channelIndex & 0xff) << 8),
                                                  get_sound_freq_scale(bankIndex, index) +
                                                  ((f32) D_80363808[bankIndex] / US_FLOAT(400.0)));
#else
                                    ret = get_sound_freq_scale(bankIndex, index);
                                    gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->freqScale =
                                        ((f32) D_80363808[bankIndex] / US_FLOAT(400.0)) + ret;
#endif
                                }
#ifdef VERSION_EU
                                func_802ad770(0x05020000 | ((channelIndex & 0xff) << 8),
                                              get_sound_reverb(bankIndex, index, channelIndex));
#else
                                gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->reverb =
                                    get_sound_reverb(bankIndex, index, channelIndex);
#endif

                                break;
                            }
                        // fallthrough
                        case 7:
#ifdef VERSION_EU
                            func_802ad728(0x02020000 | ((channelIndex & 0xff) << 8), 1);
                            func_802ad770(0x03020000 | ((channelIndex & 0xff) << 8), 64);
                            func_802ad728(0x04020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_freq_scale(bankIndex, index));
#else
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->volume = 1.0f;
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->pan = 0.5f;
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->freqScale = 1.0f;
#endif
                            break;
                        case 0:
                        case 2:
#ifdef VERSION_EU
                            func_802ad770(0x05020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_reverb(bankIndex, index, channelIndex));
                            func_802ad728(0x02020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_dynamics(bankIndex, index, ARG2_VAL1));
                            func_802ad770(0x03020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_pan(*gSoundBanks[bankIndex][index].x,
                                                        *gSoundBanks[bankIndex][index].z) * 127.0f + 0.5f);
                            func_802ad728(0x04020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_freq_scale(bankIndex, index));
#else
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->volume =
                                get_sound_dynamics(bankIndex, index, ARG2_VAL1);
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->pan = get_sound_pan(
                                *gSoundBanks[bankIndex][index].x, *gSoundBanks[bankIndex][index].z);
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->freqScale =
                                get_sound_freq_scale(bankIndex, index);
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->reverb =
                                get_sound_reverb(bankIndex, index, channelIndex);
#endif
                            break;
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
#ifdef VERSION_EU
                            func_802ad770(0x05020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_reverb(bankIndex, index, channelIndex));
                            func_802ad728(0x02020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_dynamics(bankIndex, index, ARG2_VAL2));
                            func_802ad770(0x03020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_pan(*gSoundBanks[bankIndex][index].x,
                                                        *gSoundBanks[bankIndex][index].z) * 127.0f + 0.5f);
                            func_802ad728(0x04020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_freq_scale(bankIndex, index));
#else
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->reverb =
                                get_sound_reverb(bankIndex, index, channelIndex);
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->volume =
                                get_sound_dynamics(bankIndex, index, ARG2_VAL2);
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->pan = get_sound_pan(
                                *gSoundBanks[bankIndex][index].x, *gSoundBanks[bankIndex][index].z);
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->freqScale =
                                get_sound_freq_scale(bankIndex, index);
#endif
                            break;
                    }
                }
#ifdef VERSION_JP
                else if (soundStatus == SOUND_STATUS_STOPPED) {
                    func_8031E0E4(bankIndex, index);
                    gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->soundScriptIO[0] = 0;
                    func_8031DFE8(bankIndex, index);
                }
#else
                else if (gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->layers[0] == NULL) {
                    func_8031E0E4(bankIndex, index);
                    gSoundBanks[bankIndex][index].soundStatus = SOUND_STATUS_STOPPED;
                    func_8031DFE8(bankIndex, index);
                } else if (soundStatus == SOUND_STATUS_STOPPED
                           && gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->layers[0]->finished
                                  == FALSE) {
                    func_8031E0E4(bankIndex, index);
                    gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->soundScriptIO[0] = 0;
                    func_8031DFE8(bankIndex, index);
                }
#endif
                else if (gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->layers[0]->enabled == FALSE) {
                    func_8031E0E4(bankIndex, index);
                    gSoundBanks[bankIndex][index].soundStatus = SOUND_STATUS_STOPPED;
                    func_8031DFE8(bankIndex, index);
                } else {
                    // Exactly the same code as before. Unfortunately we can't
                    // make a macro out of this, because then everything ends up
                    // on the same line after preprocessing, and the compiler,
                    // somehow caring about line numbers, makes it not match (it
                    // computes function arguments in the wrong order).
                    switch (bankIndex) {
                        case 1:
                            if (!(gSoundBanks[bankIndex][index].soundBits & SOUND_PL_BITFLAG_UNK8)) {
                                if (D_80363808[bankIndex] > 8) {
#ifdef VERSION_EU
                                    func_802ad728(0x02020000 | ((channelIndex & 0xff) << 8),
                                                  get_sound_dynamics(bankIndex, index, ARG2_VAL1));
#else
                                    ret = get_sound_dynamics(bankIndex, index, ARG2_VAL1);
                                    gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->volume = ret;
#endif
                                } else {
#ifdef VERSION_EU
                                    func_802ad728(0x02020000 | ((channelIndex & 0xff) << 8),
                                                  get_sound_dynamics(bankIndex, index, ARG2_VAL1) *
                                                  ((D_80363808[bankIndex] + 8.0f) / 16));
#else
                                    ret = get_sound_dynamics(bankIndex, index, ARG2_VAL1);
                                    gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->volume =
                                        (D_80363808[bankIndex] + 8.0f) / 16 * ret;
#endif
                                }
#ifdef VERSION_EU
                                func_802ad770(0x03020000 | ((channelIndex & 0xff) << 8),
                                              get_sound_pan(*gSoundBanks[bankIndex][index].x,
                                                            *gSoundBanks[bankIndex][index].z));
#else
                                gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->pan = get_sound_pan(
                                    *gSoundBanks[bankIndex][index].x, *gSoundBanks[bankIndex][index].z);
#endif

                                if ((gSoundBanks[bankIndex][index].soundBits & SOUNDARGS_MASK_SOUNDID)
                                    == (SOUND_MOVING_FLYING & SOUNDARGS_MASK_SOUNDID)) {
#ifdef VERSION_EU
                                    func_802ad728(0x04020000 | ((channelIndex & 0xff) << 8),
                                                  get_sound_freq_scale(bankIndex, index) +
                                                  ((f32) D_80363808[bankIndex] / US_FLOAT(80.0)));
#else
                                    ret = get_sound_freq_scale(bankIndex, index);
                                    gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->freqScale =
                                        ((f32) D_80363808[bankIndex] / US_FLOAT(80.0)) + ret;
#endif
                                } else {
#ifdef VERSION_EU
                                    func_802ad728(0x04020000 | ((channelIndex & 0xff) << 8),
                                                  get_sound_freq_scale(bankIndex, index) +
                                                  ((f32) D_80363808[bankIndex] / US_FLOAT(400.0)));
#else
                                    ret = get_sound_freq_scale(bankIndex, index);
                                    gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->freqScale =
                                        ((f32) D_80363808[bankIndex] / US_FLOAT(400.0)) + ret;
#endif
                                }
#ifdef VERSION_EU
                                func_802ad770(0x05020000 | ((channelIndex & 0xff) << 8),
                                              get_sound_reverb(bankIndex, index, channelIndex));
#else
                                gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->reverb =
                                    get_sound_reverb(bankIndex, index, channelIndex);
#endif

                                break;
                            }
                        // fallthrough
                        case 7:
#ifdef VERSION_EU
                            func_802ad728(0x02020000 | ((channelIndex & 0xff) << 8), 1);
                            func_802ad770(0x03020000 | ((channelIndex & 0xff) << 8), 64);
                            func_802ad728(0x04020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_freq_scale(bankIndex, index));
#else
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->volume = 1.0f;
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->pan = 0.5f;
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->freqScale = 1.0f;
#endif
                            break;
                        case 0:
                        case 2:
#ifdef VERSION_EU
                            func_802ad770(0x05020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_reverb(bankIndex, index, channelIndex));
                            func_802ad728(0x02020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_dynamics(bankIndex, index, ARG2_VAL1));
                            func_802ad770(0x03020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_pan(*gSoundBanks[bankIndex][index].x,
                                                        *gSoundBanks[bankIndex][index].z) * 127.0f + 0.5f);
                            func_802ad728(0x04020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_freq_scale(bankIndex, index));
#else
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->volume =
                                get_sound_dynamics(bankIndex, index, ARG2_VAL1);
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->pan = get_sound_pan(
                                *gSoundBanks[bankIndex][index].x, *gSoundBanks[bankIndex][index].z);
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->freqScale =
                                get_sound_freq_scale(bankIndex, index);
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->reverb =
                                get_sound_reverb(bankIndex, index, channelIndex);
#endif
                            break;
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
#ifdef VERSION_EU
                            func_802ad770(0x05020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_reverb(bankIndex, index, channelIndex));
                            func_802ad728(0x02020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_dynamics(bankIndex, index, ARG2_VAL2));
                            func_802ad770(0x03020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_pan(*gSoundBanks[bankIndex][index].x,
                                                        *gSoundBanks[bankIndex][index].z) * 127.0f + 0.5f);
                            func_802ad728(0x04020000 | ((channelIndex & 0xff) << 8),
                                          get_sound_freq_scale(bankIndex, index));
#else
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->reverb =
                                get_sound_reverb(bankIndex, index, channelIndex);
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->volume =
                                get_sound_dynamics(bankIndex, index, ARG2_VAL2);
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->pan = get_sound_pan(
                                *gSoundBanks[bankIndex][index].x, *gSoundBanks[bankIndex][index].z);
                            gSequencePlayers[SEQ_PLAYER_SFX].channels[channelIndex]->freqScale =
                                get_sound_freq_scale(bankIndex, index);
#endif
                            break;
                    }
                }
            }
            channelIndex++;
        }

        // sUsedChannelsForSoundBank[i] = sMaxChannelsForSoundBank[i] = 1, so this doesn't do anything
        channelIndex += sMaxChannelsForSoundBank[bankIndex] - sUsedChannelsForSoundBank[bankIndex];
    }
}
#undef ARG2_VAL1
#undef ARG2_VAL2

void play_sequence(u8 player, u8 seqId, u16 fadeTimer) {
    u8 temp_ret;
    u8 i;

    if (player == 0) {
        sPlayer0CurSeqId = seqId & 0x7f;
        sBackgroundMusicForDynamics = SEQUENCE_NONE;
        sCurrentMusicDynamic = 0xff;
        sMusicDynamicDelay = 2;
    }

    for (i = 0; i < 0x10; i++) {
        D_80360928[player][i].remDuration = 0;
    }

#ifdef VERSION_EU
    func_802ad770(0x46000000 | ((u8)(u32)player) << 16, seqId & 0x80);
    func_802ad74c(0x82000000 | ((u8)(u32)player) << 16 | ((u8)(seqId & 0x7f)) << 8, fadeTimer);

    if (player == 0) {
        temp_ret = func_803200E4(0);
        if (temp_ret != 0xff) {
            gSequencePlayers[SEQ_PLAYER_LEVEL].fadeVolumeScale = (f32) temp_ret / US_FLOAT(127.0);
        }
    }
#else
    gSequencePlayers[player].seqVariation = seqId & 0x80;
    load_sequence(player, seqId & 0x7f, 0);

    if (player == 0) {
        temp_ret = func_803200E4(0);
        if (temp_ret != 0xff) {
            gSequencePlayers[SEQ_PLAYER_LEVEL].state = SEQUENCE_PLAYER_STATE_4;
            gSequencePlayers[SEQ_PLAYER_LEVEL].fadeVolume = (f32) temp_ret / US_FLOAT(127.0);
        }
    } else {
        func_8031D690(player, fadeTimer);
    }
#endif
}

void sequence_player_fade_out(u8 player, u16 fadeTimer) {
#ifdef VERSION_EU
    if (!player) {
        sPlayer0CurSeqId = SEQUENCE_NONE;
    }
    func_802ad74c(0x83000000 | (player & 0xff) << 16, fadeTimer);
#else
    if (player == 0) {
        sPlayer0CurSeqId = SEQUENCE_NONE;
    }
    sequence_player_fade_out_internal(player, fadeTimer);
#endif
}

void fade_volume_scale(u8 player, u8 targetScale, u16 fadeTimer) {
    u8 i;
    for (i = 0; i < CHANNELS_MAX; i++) {
        fade_channel_volume_scale(player, i, targetScale, fadeTimer);
    }
}

void fade_channel_volume_scale(u8 player, u8 channelId, u8 targetScale, u16 fadeTimer) {
    struct ChannelVolumeScaleFade *temp;

    if (gSequencePlayers[player].channels[channelId] != &gSequenceChannelNone) {
        temp = &D_80360928[player][channelId];
        temp->remDuration = fadeTimer;
        temp->velocity = ((f32)(targetScale / US_FLOAT(127.0))
                          - gSequencePlayers[player].channels[channelId]->volumeScale)
                         / fadeTimer;
        temp->target = targetScale;
        temp->current = gSequencePlayers[player].channels[channelId]->volumeScale;
    }
}

void func_8031F96C(u8 player) {
    u8 i;

    // Loop over channels
    for (i = 0; i < CHANNELS_MAX; i++) {
        if (gSequencePlayers[player].channels[i] != &gSequenceChannelNone
            && D_80360928[player][i].remDuration != 0) {
            D_80360928[player][i].current += D_80360928[player][i].velocity;
#ifdef VERSION_EU
            func_802ad728(0x01000000 | (player & 0xff) << 16 | (i & 0xff) << 8, D_80360928[player][i].current);
#else
            gSequencePlayers[player].channels[i]->volumeScale = D_80360928[player][i].current;
#endif
            D_80360928[player][i].remDuration--;
            if (D_80360928[player][i].remDuration == 0) {
#ifdef VERSION_EU
                func_802ad728(0x01000000 | (player & 0xff) << 16 | (i & 0xff) << 8,
                        FLOAT_CAST(D_80360928[player][i].target) / 127.0);
#else
                gSequencePlayers[player].channels[i]->volumeScale =
                    D_80360928[player][i].target / 127.0f;
#endif
            }
        }
    }
}

#ifdef NON_MATCHING

void process_level_music_dynamics(void) {
    s32 conditionBits;      // s0
    u32 tempBits;           // v1
    u8 musicDynIndex;       // sp57 87
    u8 condIndex;           // a0, v1
    u8 i;                   // s1
    u8 j;                   // v0
    s16 conditionValues[8]; // sp44 68
    u8 conditionTypes[8];   // sp3C 60
    s16 dur1;               // sp3A 58
    s16 dur2;               // sp38 56
    u16 bit;                // a1 (in first loop), s0, v1
    // room for 16 bits without affecting stack

    func_8031F96C(0);
    func_8031F96C(2);
    func_80320ED8();
    if (sMusicDynamicDelay != 0) {
        sMusicDynamicDelay--;
    } else {
        sBackgroundMusicForDynamics = sPlayer0CurSeqId;
    }

    if (sBackgroundMusicForDynamics != sLevelDynamics[gCurrLevelNum][0]) {
        return;
    }

    // conditionBits uses a3 instead of s0
    // s16 cast is unnecessary, u16 cast fixes regalloc in the switch
    conditionBits = ((s16) sLevelDynamics[gCurrLevelNum][1]) & 0xff00;
    musicDynIndex = ((u16) sLevelDynamics[gCurrLevelNum][1]) & 0xff;
    i = 2;
    while (conditionBits & 0xff00) {
        j = 0;
        condIndex = 0;
        bit = 0x8000;
        while (j < 8) {
            if (conditionBits & bit) {
                conditionValues[condIndex] = sLevelDynamics[gCurrLevelNum][i++];
                conditionTypes[condIndex] = j;
                condIndex++;
            }

            j++;
            bit = bit >> 1;
        }

        // condIndex uses a0 (the same register as 'bit') instead of v1
        for (j = 0; j < condIndex; j++) {
            switch (conditionTypes[j]) {
                case MARIO_X_GE: {
                    if (((s16) gMarioStates[0].pos[0]) < conditionValues[j])
                        j = condIndex + 1;
                    break;
                }
                case MARIO_Y_GE: {
                    if (((s16) gMarioStates[0].pos[1]) < conditionValues[j])
                        j = condIndex + 1;
                    break;
                }
                case MARIO_Z_GE: {
                    if (((s16) gMarioStates[0].pos[2]) < conditionValues[j])
                        j = condIndex + 1;
                    break;
                }
                case MARIO_X_LT: {
                    if (((s16) gMarioStates[0].pos[0]) >= conditionValues[j])
                        j = condIndex + 1;
                    break;
                }
                case MARIO_Y_LT: {
                    if (((s16) gMarioStates[0].pos[1]) >= conditionValues[j])
                        j = condIndex + 1;
                    break;
                }
                case MARIO_Z_LT: {
                    if (((s16) gMarioStates[0].pos[2]) >= conditionValues[j])
                        j = condIndex + 1;
                    break;
                }
                case MARIO_IS_IN_AREA: {
                    if (gCurrAreaIndex != conditionValues[j])
                        j = condIndex + 1;
                    break;
                }
                case MARIO_IS_IN_ROOM: {
                    if (gMarioCurrentRoom != conditionValues[j])
                        j = condIndex + 1;
                    break;
                }
            }
        }

        if (j == condIndex) {
            // The area matches. Break out of the loop.
            tempBits = 0;
        } else {
            // s16 cast is unnecessary, u16 cast fixes regalloc
            // While conditionBits didn't need a cast above, the opposite is the case here
            tempBits      = ((u16) sLevelDynamics[gCurrLevelNum][i]) & 0xff00;
            musicDynIndex = ((s16) sLevelDynamics[gCurrLevelNum][i]) & 0xff;
            i++;
        }

        conditionBits = tempBits;
    }

    if (sCurrentMusicDynamic != musicDynIndex) {
        // bit keeps using a0, should use v1
        bit = 1;
        if (sCurrentMusicDynamic == 0xff) {
            dur1 = 1;
            dur2 = 1;
        } else {
            dur1 = sMusicDynamics[musicDynIndex].dur1;
            dur2 = sMusicDynamics[musicDynIndex].dur2;
        }

        for (i = 0; i < CHANNELS_MAX; i++) {
            if (sMusicDynamics[musicDynIndex].bits1 & bit) {
                // The instructions setting a0 and a1 are swapped, but get fixed pretty easily by a
                // branch or anything that changes regalloc
                fade_channel_volume_scale(SEQ_PLAYER_LEVEL, i, sMusicDynamics[musicDynIndex].volScale1, dur1);
            }
            if (sMusicDynamics[musicDynIndex].bits2 & bit) {
                fade_channel_volume_scale(SEQ_PLAYER_LEVEL, i, sMusicDynamics[musicDynIndex].volScale2, dur2);
            }
            bit <<= 1;
        }

        sCurrentMusicDynamic = musicDynIndex;
    }
}

#else
GLOBAL_ASM("asm/non_matchings/process_level_music_dynamics.s")
#endif

void unused_8031FED0(u8 player, u32 bits, s8 arg2) {
    u8 i;

    if (arg2 < 0) {
        arg2 = -arg2;
    }

    for (i = 0; i < CHANNELS_MAX; i++) {
        if (gSequencePlayers[player].channels[i] != &gSequenceChannelNone) {
            if ((bits & 3) == 0) {
                gSequencePlayers[player].channels[i]->volumeScale = 1.0f;
            } else if ((bits & 1) != 0) {
                gSequencePlayers[player].channels[i]->volumeScale = (f32) arg2 / US_FLOAT(127.0);
            } else {
                gSequencePlayers[player].channels[i]->volumeScale =
                    US_FLOAT(1.0) - (f32) arg2 / US_FLOAT(127.0);
            }
        }
        bits >>= 2;
    }
}

void func_8031FFB4(u8 player, u16 fadeTimer, u8 arg2) {
    if (player == 0) {
        sCapVolumeTo40 = TRUE;
        func_803200E4(fadeTimer);
    } else if (gSequencePlayers[player].enabled == TRUE) {
        func_8031D6E4(player, fadeTimer, arg2);
    }
}

void sequence_player_unlower(u8 player, u16 fadeTimer) {
    sCapVolumeTo40 = FALSE;
    if (player == 0) {
        if (gSequencePlayers[player].state != SEQUENCE_PLAYER_STATE_FADE_OUT) {
            func_803200E4(fadeTimer);
        }
    } else {
        if (gSequencePlayers[player].enabled == TRUE) {
            func_8031D7B0(player, fadeTimer);
        }
    }
}

// returns fade volume or 0xff for background music
u8 func_803200E4(u16 fadeTimer) {
    u8 vol = 0xff;
    u8 temp;

    if (sPlayer0CurSeqId == SEQUENCE_NONE || sPlayer0CurSeqId == SEQ_EVENT_CUTSCENE_CREDITS) {
        return 0xff;
    }

    if (gSequencePlayers[SEQ_PLAYER_LEVEL].volume == 0.0f && fadeTimer) {
        gSequencePlayers[SEQ_PLAYER_LEVEL].volume = gSequencePlayers[SEQ_PLAYER_LEVEL].fadeVolume;
    }

    if (D_80363812 != 0) {
        vol = (D_80363812 & 0x7f);
    }

    if (D_8033211C != 0) {
        temp = (D_8033211C & 0x7f);
        if (vol > temp) {
            vol = temp;
        }
    }

    if (sCapVolumeTo40 && vol > 40) {
        vol = 40;
    }

    if (D_80332110 != 0 && vol > 20) {
        vol = 20;
    }

    if (gSequencePlayers[SEQ_PLAYER_LEVEL].enabled == TRUE) {
        if (vol != 0xff) {
            func_8031D838(SEQ_PLAYER_LEVEL, fadeTimer, vol);
        } else {
#if defined(VERSION_JP) || defined(VERSION_US)
            gSequencePlayers[SEQ_PLAYER_LEVEL].volume = sBackgroundMusicDefaultVolume[sPlayer0CurSeqId] / 127.0f;
#endif
            func_8031D7B0(SEQ_PLAYER_LEVEL, fadeTimer);
        }
    }
    return vol;
}

void set_sound_disabled(u8 disabled) {
    u8 i;

    for (i = 0; i < SEQUENCE_PLAYERS; i++) {
#ifdef VERSION_EU
        if (disabled)
            func_802ad74c(0xf1000000, 0);
        else
            func_802ad74c(0xf2000000, 0);
#else
        gSequencePlayers[i].muted = disabled;
#endif
    }
}

void sound_init(void) {
    u8 i;
    u8 j;

    for (i = 0; i < SOUND_BANK_COUNT; i++) {
        for (j = 0; j < 40; j++) {
            gSoundBanks[i][j].soundStatus = SOUND_STATUS_STOPPED;
        }

        for (j = 0; j < MAX_CHANNELS_PER_SOUND; j++) {
            sCurrentSound[i][j] = 0xff;
        }

        D_803320A4[i] = 0;
        D_803320B0[i] = 1;
        D_803320BC[i] = 0;
    }

    for (i = 0; i < SOUND_BANK_COUNT; i++) {
        gSoundBanks[i][0].prev = 0xff;
        gSoundBanks[i][0].next = 0xff;

        for (j = 1; j < 40 - 1; j++) {
            gSoundBanks[i][j].prev = j - 1;
            gSoundBanks[i][j].next = j + 1;
        }

        gSoundBanks[i][j].prev = j - 1;
        gSoundBanks[i][j].next = 0xff;
    }

    for (j = 0; j < 3; j++) {
        for (i = 0; i < CHANNELS_MAX; i++) {
            D_80360928[j][i].remDuration = 0;
        }
    }

    for (i = 0; i < MAX_BG_MUSIC_QUEUE_SIZE; i++) {
        sBackgroundMusicQueue[i].priority = 0;
    }

    sound_banks_enable(2, 0xffff);
    sUnused80332118 = 0;
    D_80363812 = 0;
    sCapVolumeTo40 = FALSE;
    D_80332110 = 0;
    sUnused80332114 = 0;
    sPlayer0CurSeqId = 0xff;
    gSoundMode = SOUND_MODE_STEREO;
    sBackgroundMusicQueueSize = 0;
    D_8033211C = 0;
    D_80332120 = 0;
    D_80332124 = 0;
    sNumProcessedSoundRequests = 0;
    sSoundRequestCount = 0;
}

// (unused)
void get_currently_playing_sound(u8 bankIndex, u8 *numPlayingSounds, u8 *arg2, u8 *soundId) {
    u8 i;
    u8 count = 0;

    for (i = 0; i < sMaxChannelsForSoundBank[bankIndex]; i++) {
        if (sCurrentSound[bankIndex][i] != 0xff) {
            count++;
        }
    }

    *numPlayingSounds = count;
    *arg2 = D_803320BC[bankIndex];
    if (sCurrentSound[bankIndex][0] != 0xff) {
        *soundId = (u8)(gSoundBanks[bankIndex][sCurrentSound[bankIndex][0]].soundBits >> SOUNDARGS_SHIFT_SOUNDID);
    } else {
        *soundId = 0xff;
    }
}

void func_803205E8(u32 soundBits, f32 *vec) {
    u8 bankIndex;
    u8 item;

    bankIndex = (soundBits & SOUNDARGS_MASK_BANK) >> SOUNDARGS_SHIFT_BANK;
    item = gSoundBanks[bankIndex][0].next;
    while (item != 0xff) {
        if ((u16)(soundBits >> SOUNDARGS_SHIFT_SOUNDID)
                == (u16)(gSoundBanks[bankIndex][item].soundBits >> SOUNDARGS_SHIFT_SOUNDID)
            && gSoundBanks[bankIndex][item].x == vec) {
            func_8031E0E4(bankIndex, item);
            gSoundBanks[bankIndex][item].soundBits = NO_SOUND;
            item = 0xff;
        } else {
            item = gSoundBanks[bankIndex][item].next;
        }
    }
}

void func_803206F8(f32 *arg0) {
    u8 bankIndex;
    u8 item;

    for (bankIndex = 0; bankIndex < SOUND_BANK_COUNT; bankIndex++) {
        item = gSoundBanks[bankIndex][0].next;
        while (item != 0xff) {
            if (gSoundBanks[bankIndex][item].x == arg0) {
                func_8031E0E4(bankIndex, item);
                gSoundBanks[bankIndex][item].soundBits = NO_SOUND;
            }
            item = gSoundBanks[bankIndex][item].next;
        }
    }
}

static void func_803207DC(u8 bankIndex) {
    u8 item = gSoundBanks[bankIndex][0].next;

    while (item != 0xff) {
        func_8031E0E4(bankIndex, item);
        gSoundBanks[bankIndex][item].soundBits = NO_SOUND;
        item = gSoundBanks[bankIndex][item].next;
    }
}

void func_80320890(void) {
    func_803207DC(1);
    func_803207DC(4);
    func_803207DC(6);
}

void sound_banks_disable(UNUSED u8 player, u16 bankMask) {
    u8 i;

    for (i = 0; i < SOUND_BANK_COUNT; i++) {
        if (bankMask & 1) {
            sSoundBankDisabled[i] = TRUE;
        }
        bankMask = bankMask >> 1;
    }
}

void disable_all_sequence_players(void) {
    u8 i;

    for (i = 0; i < SEQUENCE_PLAYERS; i++) {
        sequence_player_disable(&gSequencePlayers[i]);
    }
}

void sound_banks_enable(UNUSED u8 player, u16 bankMask) {
    u8 i;

    for (i = 0; i < SOUND_BANK_COUNT; i++) {
        if (bankMask & 1) {
            sSoundBankDisabled[i] = FALSE;
        }
        bankMask = bankMask >> 1;
    }
}

u8 unused_803209D8(u8 player, u8 channelIndex, u8 arg2) {
    u8 ret = 0;
    if (gSequencePlayers[player].channels[channelIndex] != &gSequenceChannelNone) {
        gSequencePlayers[player].channels[channelIndex]->stopSomething2 = arg2;
        ret = arg2;
    }
    return ret;
}

void func_80320A4C(u8 bankIndex, u8 arg1) {
    D_80363808[bankIndex] = arg1;
}

void play_dialog_sound(u8 dialogID) {
    u8 speaker;

    if (dialogID >= 170) {
        dialogID = 0;
    }

    speaker = sDialogSpeaker[dialogID];
    if (speaker != 0xff) {
        play_sound(sDialogSpeakerVoice[speaker], gDefaultSoundArgs);
        if (speaker == 2) // SOUND_OBJ_BOWSER_INTRO_LAUGH
        {
            play_sequence(SEQ_PLAYER_ENV, SEQ_EVENT_KOOPA_MESSAGE, 0);
        }
    }

#ifndef VERSION_JP
    // "You've stepped on the (Wing|Metal|Vanish) Cap Switch"
    if (dialogID == DIALOG_010 || dialogID == DIALOG_011 || dialogID == DIALOG_012) {
        play_puzzle_jingle();
    }
#endif
}

void play_music(u8 player, u16 seqArgs, u16 fadeTimer) {
    u8 seqId = seqArgs & 0xff;
    u8 priority = seqArgs >> 8;
    u8 i;
    u8 foundIndex = 0;

    // Except for the background music player, we don't support queued
    // sequences. Just play them immediately, stopping any old sequence.
    if (player != 0) {
        play_sequence(player, seqId, fadeTimer);
        return;
    }

    // Abort if the queue is already full.
    if (sBackgroundMusicQueueSize == MAX_BG_MUSIC_QUEUE_SIZE) {
        return;
    }

    // If already in the queue, abort, after first restarting the sequence if
    // it is first, and handling disabled music somehow.
    // (That handling probably ought to occur even when the queue is full...)
    for (i = 0; i < sBackgroundMusicQueueSize; i++) {
        if (sBackgroundMusicQueue[i].seqId == seqId) {
            if (i == 0) {
                play_sequence(SEQ_PLAYER_LEVEL, seqId, fadeTimer);
            } else if (!gSequencePlayers[SEQ_PLAYER_LEVEL].enabled) {
                stop_background_music(sBackgroundMusicQueue[0].seqId);
            }
            return;
        }
    }

    // Find the next sequence slot by priority.
    for (i = 0; i < sBackgroundMusicQueueSize; i++) {
        if (sBackgroundMusicQueue[i].priority <= priority) {
            foundIndex = i;
            i = sBackgroundMusicQueueSize;
        }
    }

    // If the sequence ends up first in the queue, start it, and make space for
    // more entries in the queue.
    if (foundIndex == 0) {
        play_sequence(SEQ_PLAYER_LEVEL, seqId, fadeTimer);
        sBackgroundMusicQueueSize++;
    }

    // Move all items up in queue, throwing away the last one if we didn't put
    // the new sequence first.
    for (i = sBackgroundMusicQueueSize - 1; i > foundIndex; i--) {
        sBackgroundMusicQueue[i].priority = sBackgroundMusicQueue[i - 1].priority;
        sBackgroundMusicQueue[i].seqId = sBackgroundMusicQueue[i - 1].seqId;
    }

    // Insert item into queue.
    sBackgroundMusicQueue[foundIndex].priority = priority;
    sBackgroundMusicQueue[foundIndex].seqId = seqId;
}

void stop_background_music(u16 seqId) {
    u8 foundIndex;
    u8 i;

    if (sBackgroundMusicQueueSize == 0) {
        return;
    }

    // If sequence is not found, remove an empty queue item (the next empty
    // queue slot).
    foundIndex = sBackgroundMusicQueueSize;

    // Search for the sequence.
    for (i = 0; i < sBackgroundMusicQueueSize; i++) {
        if (sBackgroundMusicQueue[i].seqId == (u8)(seqId & 0xff)) {
            // Remove sequence from queue. If it was first, play the next one,
            // or fade out the music.
            sBackgroundMusicQueueSize--;
            if (i == 0) {
                if (sBackgroundMusicQueueSize != 0) {
                    play_sequence(SEQ_PLAYER_LEVEL, sBackgroundMusicQueue[1].seqId, 0);
                } else {
                    sequence_player_fade_out(SEQ_PLAYER_LEVEL, 20);
                }
            }
            foundIndex = i;
            i = sBackgroundMusicQueueSize; // "break;"
        }
    }

    // Move later slots down.
    for (i = foundIndex; i < sBackgroundMusicQueueSize; i++) {
        sBackgroundMusicQueue[i].priority = sBackgroundMusicQueue[i + 1].priority;
        sBackgroundMusicQueue[i].seqId = sBackgroundMusicQueue[i + 1].seqId;
    }

    // @bug? If the sequence queue is full and we attempt to stop a sequence
    // that isn't in the queue, this writes out of bounds. Can that happen?
    sBackgroundMusicQueue[i].priority = 0;
}

void fadeout_background_music(u16 seqId, u16 fadeOut) {
    if (sBackgroundMusicQueueSize != 0 && sBackgroundMusicQueue[0].seqId == (u8)(seqId & 0xff)) {
        sequence_player_fade_out(SEQ_PLAYER_LEVEL, fadeOut);
    }
}

void drop_queued_background_music(void) {
    if (sBackgroundMusicQueueSize != 0) {
        sBackgroundMusicQueueSize = 1;
    }
}

u16 get_current_background_music(void) {
    if (sBackgroundMusicQueueSize != 0) {
        return (sBackgroundMusicQueue[0].priority << 8) + sBackgroundMusicQueue[0].seqId;
    }
    return -1;
}

void func_80320ED8(void) {
#ifdef VERSION_EU
    if (D_EU_80300558 != 0) {
        D_EU_80300558--;
    }

    if (gSequencePlayers[SEQ_PLAYER_ENV].enabled || D_8033211C == 0 || D_EU_80300558 != 0) {
#else
    if (gSequencePlayers[SEQ_PLAYER_ENV].enabled || D_8033211C == 0) {
#endif
        return;
    }

    D_8033211C = 0;
    func_803200E4(50);

    if (D_80363812 != 0
        && (D_80332120 == SEQ_EVENT_MERRY_GO_ROUND || D_80332120 == SEQ_EVENT_PIRANHA_PLANT)) {
        play_sequence(SEQ_PLAYER_ENV, D_80332120, 1);
        if (D_80332124 != 0xff) {
            func_8031D838(SEQ_PLAYER_ENV, 1, D_80332124);
        }
    }
}

void play_secondary_music(u8 seqId, u8 bgMusicVolume, u8 volume, u16 fadeTimer) {
    UNUSED u32 dummy;

    sUnused80332118 = 0;
    if (sPlayer0CurSeqId == 0xff || sPlayer0CurSeqId == SEQ_MENU_TITLE_SCREEN) {
        return;
    }

    if (D_80363812 == 0) {
        D_80363812 = bgMusicVolume + 0x80;
        func_803200E4(fadeTimer);
        play_sequence(SEQ_PLAYER_ENV, seqId, fadeTimer >> 1);
        if (volume < 0x80) {
            func_8031D838(SEQ_PLAYER_ENV, fadeTimer, volume);
        }
        D_80332124 = volume;
        D_80332120 = seqId;
    } else if (volume != 0xff) {
        D_80363812 = bgMusicVolume + 0x80;
        func_803200E4(fadeTimer);
        func_8031D838(SEQ_PLAYER_ENV, fadeTimer, volume);
        D_80332124 = volume;
    }
}

void func_80321080(u16 fadeTimer) {
    if (D_80363812 != 0) {
        D_80363812 = 0;
        D_80332120 = 0;
        D_80332124 = 0;
        func_803200E4(fadeTimer);
        sequence_player_fade_out(SEQ_PLAYER_ENV, fadeTimer);
    }
}

void func_803210D4(u16 fadeOutTime) {
    u8 i;

    if (sHasStartedFadeOut) {
        return;
    }

    if (gSequencePlayers[SEQ_PLAYER_LEVEL].enabled == TRUE) {
#ifdef VERSION_EU
        func_802ad74c(0x83000000, fadeOutTime);
#else
        sequence_player_fade_out_internal(SEQ_PLAYER_LEVEL, fadeOutTime);
#endif
    }
    if (gSequencePlayers[SEQ_PLAYER_ENV].enabled == TRUE) {
#ifdef VERSION_EU
        func_802ad74c(0x83010000, fadeOutTime);
#else
        sequence_player_fade_out_internal(SEQ_PLAYER_ENV, fadeOutTime);
#endif
    }

    for (i = 0; i < SOUND_BANK_COUNT; i++) {
        if (i != 7) {
            fade_channel_volume_scale(SEQ_PLAYER_SFX, i, 0, fadeOutTime / 16);
        }
    }
    sHasStartedFadeOut = TRUE;
}

void play_course_clear(void) {
    play_sequence(SEQ_PLAYER_ENV, SEQ_EVENT_CUTSCENE_COLLECT_STAR, 0);
    D_8033211C = 0x80 | 0;
#ifdef VERSION_EU
    D_EU_80300558 = 2;
#endif
    func_803200E4(50);
}

void play_peachs_jingle(void) {
    play_sequence(SEQ_PLAYER_ENV, SEQ_EVENT_PEACH_MESSAGE, 0);
    D_8033211C = 0x80 | 0;
#ifdef VERSION_EU
    D_EU_80300558 = 2;
#endif
    func_803200E4(50);
}

/**
 * Plays the puzzle jingle. Plays the dadada dadada *dadada* jingle
 * that usually plays when you solve a "puzzle", like chests, talking to
 * yoshi, releasing chain chomp, opening the pyramid top, etc.
 */
void play_puzzle_jingle(void) {
    play_sequence(SEQ_PLAYER_ENV, SEQ_EVENT_SOLVE_PUZZLE, 0);
    D_8033211C = 0x80 | 20;
#ifdef VERSION_EU
    D_EU_80300558 = 2;
#endif
    func_803200E4(50);
}

void play_star_fanfare(void) {
    play_sequence(SEQ_PLAYER_ENV, SEQ_EVENT_HIGH_SCORE, 0);
    D_8033211C = 0x80 | 20;
#ifdef VERSION_EU
    D_EU_80300558 = 2;
#endif
    func_803200E4(50);
}

void play_power_star_jingle(u8 arg0) {
    if (!arg0) {
        D_80363812 = 0;
    }
    play_sequence(SEQ_PLAYER_ENV, SEQ_EVENT_CUTSCENE_STAR_SPAWN, 0);
    D_8033211C = 0x80 | 20;
#ifdef VERSION_EU
    D_EU_80300558 = 2;
#endif
    func_803200E4(50);
}

void play_race_fanfare(void) {
    play_sequence(SEQ_PLAYER_ENV, SEQ_EVENT_RACE, 0);
    D_8033211C = 0x80 | 20;
#ifdef VERSION_EU
    D_EU_80300558 = 2;
#endif
    func_803200E4(50);
}

void play_toads_jingle(void) {
    play_sequence(SEQ_PLAYER_ENV, SEQ_EVENT_TOAD_MESSAGE, 0);
    D_8033211C = 0x80 | 20;
#ifdef VERSION_EU
    D_EU_80300558 = 2;
#endif
    func_803200E4(50);
}

void sound_reset(u8 presetId) {
#ifndef VERSION_JP
    if (presetId >= 8) {
        presetId = 0;
        sUnused8033323C = 0;
    }
#endif
    sGameLoopTicked = 0;
    disable_all_sequence_players();
    sound_init();
#if defined(VERSION_JP) || defined(VERSION_US) || defined(VERSION_SH)
    audio_reset_session(&gAudioSessionPresets[presetId]);
#else
    audio_reset_session_eu(presetId);
#endif
    osWritebackDCacheAll();
    if (presetId != 7) {
        preload_sequence(SEQ_EVENT_SOLVE_PUZZLE, PRELOAD_BANKS | PRELOAD_SEQUENCE);
        preload_sequence(SEQ_EVENT_PEACH_MESSAGE, PRELOAD_BANKS | PRELOAD_SEQUENCE);
        preload_sequence(SEQ_EVENT_CUTSCENE_STAR_SPAWN, PRELOAD_BANKS | PRELOAD_SEQUENCE);
    }
    play_sequence(SEQ_PLAYER_SFX, SEQ_SOUND_PLAYER, 0);
    D_80332108 = (D_80332108 & 0xf0) + presetId;
    gSoundMode = D_80332108 >> 4;
    sHasStartedFadeOut = FALSE;
}

void audio_set_sound_mode(u8 soundMode) {
    D_80332108 = (D_80332108 & 0xf) + (soundMode << 4);
    gSoundMode = soundMode;
}

#ifndef VERSION_EU
void unused_80321460(UNUSED s32 arg0, UNUSED s32 arg1, UNUSED s32 arg2, UNUSED s32 arg3) {
}

void unused_80321474(UNUSED s32 arg0) {
}
#endif
