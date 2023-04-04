#ifndef TYPES_H
#define TYPES_H

// This file contains various data types used in Super Mario 64 that don't yet
// have an appropriate header.

#include <ultra64.h>
#include "macros.h"
#include "config.h"


// Certain functions are marked as having return values, but do not
// actually return a value. This causes undefined behavior, which we'd rather
// avoid on modern GCC. This only impacts -O2 and can matter for both the function
// itself and functions that call it.
#ifdef AVOID_UB
    #define BAD_RETURN(cmd) void
#else
    #define BAD_RETURN(cmd) cmd
#endif


struct Controller {
  /*0x00*/ s16 rawStickX;       //
  /*0x02*/ s16 rawStickY;       //
  /*0x04*/ float stickX;        // [-64, 64] positive is right
  /*0x08*/ float stickY;        // [-64, 64] positive is up
  /*0x0C*/ float stickMag;      // distance from center [0, 64]
  /*0x10*/ u16 buttonDown;
  /*0x12*/ u16 buttonPressed;
  /*0x14*/ OSContStatus *statusData;
  /*0x18*/ OSContPad *controllerData;
#if ENABLE_RUMBLE
  /*0x1C*/ s32 port;
#endif
};

typedef f32 Vec2f[2];
typedef f32 Vec3f[3]; // X, Y, Z, where Y is up
typedef s16 Vec3s[3];
typedef s32 Vec3i[3];
typedef f32 Vec4f[4];
typedef s16 Vec4s[4];

typedef f32 Mat4[4][4];

typedef uintptr_t GeoLayout;
typedef uintptr_t LevelScript;
typedef s16 Movtex;
typedef s16 MacroObject;
typedef s16 Collision;
typedef s16 Trajectory;
typedef s16 PaintingData;
typedef uintptr_t BehaviorScript;
typedef u8 Texture;

enum SpTaskState {
    SPTASK_STATE_NOT_STARTED,
    SPTASK_STATE_RUNNING,
    SPTASK_STATE_INTERRUPTED,
    SPTASK_STATE_FINISHED,
    SPTASK_STATE_FINISHED_DP
};

struct SPTask {
    /*0x00*/ OSTask task;
    /*0x40*/ OSMesgQueue *msgqueue;
    /*0x44*/ OSMesg msg;
    /*0x48*/ enum SpTaskState state;
}; // size = 0x4C, align = 0x8

struct VblankHandler {
    OSMesgQueue *queue;
    OSMesg msg;
};

#define ANIM_FLAG_NOLOOP     (1 << 0) // 0x01
#define ANIM_FLAG_FORWARD    (1 << 1) // 0x02
#define ANIM_FLAG_2          (1 << 2) // 0x04
#define ANIM_FLAG_HOR_TRANS  (1 << 3) // 0x08
#define ANIM_FLAG_VERT_TRANS (1 << 4) // 0x10
#define ANIM_FLAG_5          (1 << 5) // 0x20
#define ANIM_FLAG_6          (1 << 6) // 0x40
#define ANIM_FLAG_7          (1 << 7) // 0x80

struct Animation {
    /*0x00*/ s16 flags;
    /*0x02*/ s16 animYTransDivisor;
    /*0x04*/ s16 startFrame;
    /*0x06*/ s16 loopStart;
    /*0x08*/ s16 loopEnd;
    /*0x0A*/ s16 unusedBoneCount;
    /*0x0C*/ const s16 *values;
    /*0x10*/ const u16 *index;
    /*0x14*/ u32 length; // only used with Mario animations to determine how much to load. 0 otherwise.
};

#define ANIMINDEX_NUMPARTS(animindex) (sizeof(animindex) / sizeof(u16) / 6 - 1)

struct GraphNode {
    /*0x00*/ s16 type; // structure type
    /*0x02*/ s16 flags; // hi = drawing layer, lo = rendering modes
    /*0x04*/ struct GraphNode *prev;
    /*0x08*/ struct GraphNode *next;
    /*0x0C*/ struct GraphNode *parent;
    /*0x10*/ struct GraphNode *children;
};

struct AnimInfo {
    /*0x00 0x38*/ s16 animID;
    /*0x02 0x3A*/ s16 animYTrans;
    /*0x04 0x3C*/ struct Animation *curAnim;
    /*0x08 0x40*/ s16 animFrame;
    /*0x0A 0x42*/ u16 animTimer;
    /*0x0C 0x44*/ s32 animFrameAccelAssist;
    /*0x10 0x48*/ s32 animAccel;
};

struct GraphNodeObject {
    /*0x00*/ struct GraphNode node;
    /*0x14*/ struct GraphNode *sharedChild;
    /*0x18*/ s8 areaIndex;
    /*0x19*/ s8 activeAreaIndex;
    /*0x1A*/ Vec3s angle;
    /*0x20*/ Vec3f pos;
    /*0x2C*/ Vec3f scale;
    /*0x38*/ struct AnimInfo animInfo;
    /*0x4C*/ struct SpawnInfo *unk4C;
    /*0x50*/ Mat4 *throwMatrix; // matrix ptr
    /*0x54*/ Vec3f cameraToObject;
};

struct ObjectNode {
    struct GraphNodeObject gfx;
    struct ObjectNode *next;
    struct ObjectNode *prev;
};

// NOTE: Since ObjectNode is the first member of Object, it is difficult to determine
// whether some of these pointers point to ObjectNode or Object.

struct Object {
    /*0x000*/ struct ObjectNode header;
    /*0x068*/ struct Object *parentObj;
    /*0x06C*/ struct Object *prevObj;
    /*0x070*/ u32 collidedObjInteractTypes;
    /*0x074*/ s16 activeFlags;
    /*0x076*/ s16 numCollidedObjs;
    /*0x078*/ struct Object *collidedObjs[4];
    /*0x088*/
    union {
        // Object fields. See object_fields.h.
        u32 asU32[0x50];
        s32 asS32[0x50];
        s16 asS16[0x50][2];
        f32 asF32[0x50];
#if !IS_64_BIT
        s16 *asS16P[0x50];
        s32 *asS32P[0x50];
        struct Animation **asAnims[0x50];
        struct Waypoint *asWaypoint[0x50];
        struct ChainSegment *asChainSegment[0x50];
        struct Object *asObject[0x50];
        struct Surface *asSurface[0x50];
        void *asVoidPtr[0x50];
        const void *asConstVoidPtr[0x50];
#endif
    } rawData;
#if IS_64_BIT
    union {
        s16 *asS16P[0x50];
        s32 *asS32P[0x50];
        struct Animation **asAnims[0x50];
        struct Waypoint *asWaypoint[0x50];
        struct ChainSegment *asChainSegment[0x50];
        struct Object *asObject[0x50];
        struct Surface *asSurface[0x50];
        void *asVoidPtr[0x50];
        const void *asConstVoidPtr[0x50];
    } ptrData;
#endif
    /*0x1C8*/ u32 unused1;
    /*0x1CC*/ const BehaviorScript *curBhvCommand;
    /*0x1D0*/ u32 bhvStackIndex;
    /*0x1D4*/ uintptr_t bhvStack[8];
    /*0x1F4*/ s16 bhvDelayTimer;
    /*0x1F6*/ s16 respawnInfoType;
    /*0x1F8*/ f32 hitboxRadius;
    /*0x1FC*/ f32 hitboxHeight;
    /*0x200*/ f32 hurtboxRadius;
    /*0x204*/ f32 hurtboxHeight;
    /*0x208*/ f32 hitboxDownOffset;
    /*0x20C*/ const BehaviorScript *behavior;
    /*0x210*/ u32 unused2;
    /*0x214*/ struct Object *platform;
    /*0x218*/ void *collisionData;
    /*0x21C*/ Mat4 transform;
    /*0x25C*/ void *respawnInfo;
};

struct ObjectHitbox {
    /*0x00*/ u32 interactType;
    /*0x04*/ u8 downOffset;
    /*0x05*/ s8 damageOrCoinValue;
    /*0x06*/ s8 health;
    /*0x07*/ s8 numLootCoins;
    /*0x08*/ s16 radius;
    /*0x0A*/ s16 height;
    /*0x0C*/ s16 hurtboxRadius;
    /*0x0E*/ s16 hurtboxHeight;
};

struct Waypoint {
    s16 flags;
    Vec3s pos;
};

struct Surface {
    /*0x00*/ s16 type;
    /*0x02*/ s16 force;
    /*0x04*/ s8 flags;
    /*0x05*/ s8 room;
    /*0x06*/ s16 lowerY;
    /*0x08*/ s16 upperY;
    /*0x0A*/ Vec3s vertex1;
    /*0x10*/ Vec3s vertex2;
    /*0x16*/ Vec3s vertex3;
    /*0x1C*/ struct {
        f32 x;
        f32 y;
        f32 z;
    } normal;
    /*0x28*/ f32 originOffset;
    /*0x2C*/ struct Object *object;
};

struct MarioBodyState {
    /*0x00*/ u32 action;
    /*0x04*/ s8 capState; /// see MarioCapGSCId
    /*0x05*/ s8 eyeState;
    /*0x06*/ s8 handState;
    /*0x07*/ s8 wingFlutter; /// whether Mario's wing cap wings are fluttering
    /*0x08*/ s16 modelState;
    /*0x0A*/ s8 grabPos;
    /*0x0B*/ u8 punchState; /// 2 bits for type of punch, 6 bits for punch animation timer
    /*0x0C*/ Vec3s torsoAngle;
    /*0x12*/ Vec3s headAngle;
    /*0x18*/ Vec3f heldObjLastPosition; /// also known as HOLP
    u8 filler[4];
};

struct MarioState {
    /*0x00*/ u16 unk00;
    /*0x02*/ u16 input;
    /*0x04*/ u32 flags;
    /*0x08*/ u32 particleFlags;
    /*0x0C*/ u32 action;
    /*0x10*/ u32 prevAction;
    /*0x14*/ u32 terrainSoundAddend;
    /*0x18*/ u16 actionState;
    /*0x1A*/ u16 actionTimer;
    /*0x1C*/ u32 actionArg;
    /*0x20*/ f32 intendedMag;
    /*0x24*/ s16 intendedYaw;
    /*0x26*/ s16 invincTimer;
    /*0x28*/ u8 framesSinceA;
    /*0x29*/ u8 framesSinceB;
    /*0x2A*/ u8 wallKickTimer;
    /*0x2B*/ u8 doubleJumpTimer;
    /*0x2C*/ Vec3s faceAngle;
    /*0x32*/ Vec3s angleVel;
    /*0x38*/ s16 slideYaw;
    /*0x3A*/ s16 twirlYaw;
    /*0x3C*/ Vec3f pos;
    /*0x48*/ Vec3f vel;
    /*0x54*/ f32 forwardVel;
    /*0x58*/ f32 slideVelX;
    /*0x5C*/ f32 slideVelZ;
    /*0x60*/ struct Surface *wall;
    /*0x64*/ struct Surface *ceil;
    /*0x68*/ struct Surface *floor;
    /*0x6C*/ f32 ceilHeight;
    /*0x70*/ f32 floorHeight;
    /*0x74*/ s16 floorAngle;
    /*0x76*/ s16 waterLevel;
    /*0x78*/ struct Object *interactObj;
    /*0x7C*/ struct Object *heldObj;
    /*0x80*/ struct Object *usedObj;
    /*0x84*/ struct Object *riddenObj;
    /*0x88*/ struct Object *marioObj;
    /*0x8C*/ struct SpawnInfo *spawnInfo;
    /*0x90*/ struct Area *area;
    /*0x94*/ struct PlayerCameraState *statusForCamera;
    /*0x98*/ struct MarioBodyState *marioBodyState;
    /*0x9C*/ struct Controller *controller;
    /*0xA0*/ struct DmaHandlerList *animList;
    /*0xA4*/ u32 collidedObjInteractTypes;
    /*0xA8*/ s16 numCoins;
    /*0xAA*/ s16 numStars;
    /*0xAC*/ s8 numKeys; // Unused key mechanic
    /*0xAD*/ s8 numLives;
    /*0xAE*/ s16 health;
    /*0xB0*/ s16 unkB0;
    /*0xB2*/ u8 hurtCounter;
    /*0xB3*/ u8 healCounter;
    /*0xB4*/ u8 squishTimer;
    /*0xB5*/ u8 fadeWarpOpacity;
    /*0xB6*/ u16 capTimer;
    /*0xB8*/ s16 prevNumStarsForDialog;
    /*0xBC*/ f32 peakHeight;
    /*0xC0*/ f32 quicksandDepth;
    /*0xC4*/ f32 unkC4;
};

#endif // TYPES_H
