#ifndef _OBJ_BEHAVIORS_2_H
#define _OBJ_BEHAVIORS_2_H

#include "types.h"

#define ATTACK_HANDLER_NOP 0
#define ATTACK_HANDLER_DIE_IF_HEALTH_NON_POSITIVE 1
#define ATTACK_HANDLER_KNOCKBACK 2
#define ATTACK_HANDLER_SQUISHED 3
#define ATTACK_HANDLER_SPECIAL_KOOPA_LOSE_SHELL 4
#define ATTACK_HANDLER_SET_SPEED_TO_ZERO 5
#define ATTACK_HANDLER_SPECIAL_WIGGLER_JUMPED_ON 6
#define ATTACK_HANDLER_SPECIAL_HUGE_GOOMBA_WEAKLY_ATTACKED 7
#define ATTACK_HANDLER_SQUISHED_WITH_BLUE_COIN 8

/* BSS (declared to force order) */
extern s32 sNumActiveFirePiranhaPlants;
extern s32 sNumKilledFirePiranhaPlants;
extern f32 sObjSavedPosX;
extern f32 sObjSavedPosY;
extern f32 sObjSavedPosZ;
extern struct Object *sMontyMoleHoleList;
extern s32 sMontyMoleKillStreak;
extern f32 sMontyMoleLastKilledPosX;
extern f32 sMontyMoleLastKilledPosY;
extern f32 sMontyMoleLastKilledPosZ;
extern struct Object *sMasterTreadmill;

extern void shelled_koopa_attack_handler(s32);
extern void obj_spit_fire(s16, s16, s16, f32, s32, f32, f32, s16);
extern void obj_set_speed_to_zero(void);

#endif /* _OBJ_BEHAVIORS_2_H */
