#ifndef INGAME_MENU_3_H
#define INGAME_MENU_3_H

#include "types.h"

#define ENVFX_STATE_UNUSED          0
#define ENVFX_STATE_SRC_X           1 // whirlpool / jetsream bubble source position
#define ENVFX_STATE_SRC_Y           2
#define ENVFX_STATE_SRC_Z           3
#define ENVFX_STATE_DEST_X          4 // only for whirlpool, where bubbles get sucked in
#define ENVFX_STATE_DEST_Y          5
#define ENVFX_STATE_DEST_Z          6
#define ENVFX_STATE_PARTICLECOUNT   7
#define ENVFX_STATE_PITCH           8 // whirlpool can rotate around DEST point
#define ENVFX_STATE_YAW             9

// Used to communicate from whirlpool behavior to envfx
extern s16 gEnvFxBubbleConfig[10];
extern Gfx *envfx_update_bubbles(s32 sp28, Vec3s sp2C, Vec3s sp30, Vec3s sp34);

#endif // INGAME_MENU_3_H
