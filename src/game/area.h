#ifndef _AREA_H
#define _AREA_H

#include "types.h"

struct WarpNode
{
    /*00*/ u8 id;
    /*01*/ u8 destLevel;
    /*02*/ u8 destArea;
    /*03*/ u8 destNode;
};

struct ObjectWarpNode
{
    /*0x00*/ struct WarpNode node;
    /*0x04*/ struct Object *object;
    /*0x08*/ struct ObjectWarpNode *next;
};

// From Surface 0x1B to 0x1E
#define INSTANT_WARP_INDEX_START  0x00 // Equal and greater than Surface 0x1B
#define INSTANT_WARP_INDEX_STOP   0x04 // Less than Surface 0x1F

struct InstantWarp
{
    /*0x00*/ u8 id; // 0 = 0x1B / 1 = 0x1C / 2 = 0x1D / 3 = 0x1E
    /*0x01*/ u8 area;
    /*0x02*/ Vec3s displacement;
};

struct SpawnInfo
{
    /*0x00*/ Vec3s startPos;
    /*0x06*/ Vec3s startAngle;
    /*0x0C*/ s8 areaIndex;
    /*0x0D*/ s8 activeAreaIndex;
    /*0x10*/ u32 behaviorArg;
    /*0x14*/ void *behaviorScript;
    /*0x18*/ struct GraphNode *unk18;
    /*0x1C*/ struct SpawnInfo *next;
};

struct UnusedArea28
{
    /*0x00*/ s16 unk00;
    /*0x02*/ s16 unk02;
    /*0x04*/ s16 unk04;
    /*0x06*/ s16 unk06;
    /*0x08*/ s16 unk08;
};

struct Whirlpool
{
    /*0x00*/ Vec3s pos;
    /*0x03*/ s16 strength;
};

struct Area
{
    /*0x00*/ s8 index;
    /*0x01*/ s8 flags; // Only has 1 flag: 0x01 = Is this the active area?
    /*0x02*/ u16 terrainType; // default terrain of the level (set from level script cmd 0x31)
    /*0x04*/ struct GraphNode *unk04; // geometry layout data
    /*0x08*/ s16 *terrainData; // collision data (set from level script cmd 0x2E)
    /*0x0C*/ s8 *surfaceRooms; // (set from level script cmd 0x2F)
    /*0x10*/ s16 *macroObjects; // Macro Objects Ptr (set from level script cmd 0x39)
    /*0x14*/ struct ObjectWarpNode *warpNodes;
    /*0x18*/ struct WarpNode *paintingWarpNodes;
    /*0x1C*/ struct InstantWarp *instantWarps;
    /*0x20*/ struct SpawnInfo *objectSpawnInfos;
    /*0x24*/ struct Camera *camera;
    /*0x28*/ struct UnusedArea28 *unused28; // Filled by level script 0x3A, but is unused.
    /*0x2C*/ struct Whirlpool *whirlpools[2];
    /*0x34*/ u8 dialog[2]; // Level start dialog number (set by level script cmd 0x30)
    /*0x36*/ u16 musicParam;
    /*0x38*/ u16 musicParam2;
};

// All the transition data to be used in screen_transition.c
struct WarpTransitionData
{
    /*0x00*/ u8 red;
    /*0x01*/ u8 green;
    /*0x02*/ u8 blue;

    /*0x04*/ s16 startTexRadius;
    /*0x06*/ s16 endTexRadius;
    /*0x08*/ s16 startTexX;
    /*0x0A*/ s16 startTexY;
    /*0x0C*/ s16 endTexX;
    /*0x0E*/ s16 endTexY;

    /*0x10*/ s16 texTimer; // always 0, does seems to affect transition when disabled
};

#define WARP_TRANSITION_FADE_FROM_COLOR  0x00
#define WARP_TRANSITION_FADE_INTO_COLOR  0x01
#define WARP_TRANSITION_FADE_FROM_STAR   0x08
#define WARP_TRANSITION_FADE_INTO_STAR   0x09
#define WARP_TRANSITION_FADE_FROM_CIRCLE 0x0A
#define WARP_TRANSITION_FADE_INTO_CIRCLE 0x0B
#define WARP_TRANSITION_FADE_FROM_MARIO  0x10
#define WARP_TRANSITION_FADE_INTO_MARIO  0x11
#define WARP_TRANSITION_FADE_FROM_BOWSER 0x12
#define WARP_TRANSITION_FADE_INTO_BOWSER 0x13

struct WarpTransition
{
    /*0x00*/ u8 isActive;       // Is the transition active. (either TRUE or FALSE)
    /*0x01*/ u8 type;           // Determines the type of transition to use (circle, star, etc.)
    /*0x02*/ u8 time;           // Amount of time to complete the transition (in frames)
    /*0x03*/ u8 pauseRendering; // Should the game stop rendering. (either TRUE or FALSE)
    /*0x04*/ struct WarpTransitionData data;
};

extern struct SpawnInfo gPlayerSpawnInfos[];
extern struct GraphNode *D_8033A160[];
extern struct Area gAreaData[];
extern struct WarpTransition gWarpTransition;
extern s16 gCurrCourseNum;
extern s16 gCurrActNum;
extern s16 gCurrAreaIndex;
extern s16 gSavedCourseNum;
extern s16 gPauseScreenMode;
extern s16 gSaveOptSelectIndex;

extern struct SpawnInfo *gMarioSpawnInfo;

extern struct Area *gAreas;
extern struct Area *gCurrentArea;

extern s16 gCurrSaveFileNum;
extern s16 gCurrLevelNum;


void override_viewport_and_clip(Vp *a, Vp *b, u8 c, u8 d, u8 e);
void print_intro_text(void);
u32 get_mario_spawn_type(struct Object *o);
struct ObjectWarpNode *area_get_warp_node(u8 id);
void clear_areas(void);
void clear_area_graph_nodes(void);
void load_area(s32 index);
void unload_area(void);
void load_mario_area(void);
void unload_mario_area(void);
void change_area(s32 index);
void area_update_objects(void);
void play_transition(s16 transType, s16 time, u8 red, u8 green, u8 blue);
void play_transition_after_delay(s16 transType, s16 time, u8 red, u8 green, u8 blue, s16 delay);
void render_game(void);


#endif
