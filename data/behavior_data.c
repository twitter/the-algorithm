#define OBJECT_FIELDS_INDEX_DIRECTLY

#include "sm64.h"

#include "object_constants.h"
#include "game/object_list_processor.h"
#include "game/interaction.h"
#include "game/behavior_actions.h"
#include "game/mario_actions_cutscene.h"
#include "game/mario_misc.h"
#include "game/object_helpers.h"
#include "game/debug.h"
#include "menu/file_select.h"
#include "engine/surface_load.h"

#include "actors/common0.h"
#include "actors/common1.h"
#include "actors/group1.h"
#include "actors/group2.h"
#include "actors/group3.h"
#include "actors/group4.h"
#include "actors/group5.h"
#include "actors/group6.h"
#include "actors/group7.h"
#include "actors/group8.h"
#include "actors/group9.h"
#include "actors/group10.h"
#include "actors/group11.h"
#include "actors/group12.h"
#include "actors/group13.h"
#include "actors/group14.h"
#include "actors/group15.h"
#include "actors/group16.h"
#include "actors/group17.h"
#include "levels/bbh/header.h"
#include "levels/castle_inside/header.h"
#include "levels/hmc/header.h"
#include "levels/ssl/header.h"
#include "levels/bob/header.h"
#include "levels/sl/header.h"
#include "levels/wdw/header.h"
#include "levels/jrb/header.h"
#include "levels/thi/header.h"
#include "levels/ttc/header.h"
#include "levels/rr/header.h"
#include "levels/castle_grounds/header.h"
#include "levels/bitdw/header.h"
#include "levels/lll/header.h"
#include "levels/sa/header.h"
#include "levels/bitfs/header.h"
#include "levels/ddd/header.h"
#include "levels/wf/header.h"
#include "levels/bowser_2/header.h"
#include "levels/ttm/header.h"

#include "make_const_nonconst.h"
#include "behavior_data.h"

#define BC_B(a) _SHIFTL(a, 24, 8)
#define BC_BB(a, b) (_SHIFTL(a, 24, 8) | _SHIFTL(b, 16, 8))
#define BC_BBBB(a, b, c, d) (_SHIFTL(a, 24, 8) | _SHIFTL(b, 16, 8) | _SHIFTL(c, 8, 8) | _SHIFTL(d, 0, 8))
#define BC_BBH(a, b, c) (_SHIFTL(a, 24, 8) | _SHIFTL(b, 16, 8) | _SHIFTL(c, 0, 16))
#define BC_B0H(a, b) (_SHIFTL(a, 24, 8) | _SHIFTL(b, 0, 16))
#define BC_H(a) _SHIFTL(a, 16, 16)
#define BC_HH(a, b) (_SHIFTL(a, 16, 16) | _SHIFTL(b, 0, 16))
#define BC_W(a) ((uintptr_t)(u32)(a))
#define BC_PTR(a) ((uintptr_t)(a))


// Defines the start of the behavior script as well as the object list the object belongs to.
// Has some special behavior for certain objects.
#define BEGIN(objList) \
    BC_BB(0x00, objList)

// Delays the behavior script for a certain number of frames.
#define DELAY(num) \
    BC_B0H(0x01, num)

// Jumps to a new behavior command and stores the return address in the object's stack.
#define CALL(addr) \
    BC_B(0x02), \
    BC_PTR(addr)

// Jumps back to the behavior command stored in the object's stack.
#define RETURN() \
    BC_B(0x03)

// Jumps to a new behavior script without saving anything.
#define GOTO(addr) \
    BC_B(0x04), \
    BC_PTR(addr)

// Marks the start of a loop that will repeat a certain number of times.
#define BEGIN_REPEAT(count) \
    BC_B0H(0x05, count)

// Marks the end of a repeating loop.
#define END_REPEAT() \
    BC_B(0x06)

// Also marks the end of a repeating loop, but continues executing commands following the loop on the same frame.
#define END_REPEAT_CONTINUE() \
    BC_B(0x07)

// Marks the beginning of an infinite loop.
#define BEGIN_LOOP() \
    BC_B(0x08)

// Marks the end of an infinite loop.
#define END_LOOP() \
    BC_B(0x09)

// Exits the behavior script.
// Often used to end behavior scripts that do not contain an infinite loop.
#define BREAK() \
    BC_B(0x0A)
    
// Exits the behavior script, unused.
#define BREAK_UNUSED() \
    BC_B(0x0B)

// Executes a native game function.
#define CALL_NATIVE(func) \
    BC_B(0x0C), \
    BC_PTR(func)

// Adds a float to the specified field.
#define ADD_FLOAT(field, value) \
    BC_BBH(0x0D, field, value)

// Sets the specified field to a float.
#define SET_FLOAT(field, value) \
    BC_BBH(0x0E, field, value)

// Adds an integer to the specified field.
#define ADD_INT(field, value) \
    BC_BBH(0x0F, field, value)

// Sets the specified field to an integer.
#define SET_INT(field, value) \
    BC_BBH(0x10, field, value)

// Performs a bitwise OR with the specified field and the given integer.
// Usually used to set an object's flags.
#define OR_INT(field, value) \
    BC_BBH(0x11, field, value)

// Performs a bit clear with the specified short. Unused in favor of the 32-bit version.
#define BIT_CLEAR(field, value) \
    BC_BBH(0x12, field, value)

// TODO: this one needs a better name / labelling
// Gets a random short, right shifts it the specified amount and adds min to it, then sets the specified field to that value.
#define SET_INT_RAND_RSHIFT(field, min, rshift) \
    BC_BBH(0x13, field, min), \
    BC_H(rshift)

// Sets the specified field to a random float in the given range.
#define SET_RANDOM_FLOAT(field, min, range) \
    BC_BBH(0x14, field, min), \
    BC_H(range)

// Sets the specified field to a random integer in the given range.
#define SET_RANDOM_INT(field, min, range) \
    BC_BBH(0x15, field, min), \
    BC_H(range)

// Adds a random float in the given range to the specified field.
#define ADD_RANDOM_FLOAT(field, min, range) \
    BC_BBH(0x16, field, min), \
    BC_H(range)

// TODO: better name (unused anyway)
// Gets a random short, right shifts it the specified amount and adds min to it, then adds the value to the specified field. Unused.
#define ADD_INT_RAND_RSHIFT(field, min, rshift) \
    BC_BBH(0x17, field, min), \
    BC_H(rshift)
    
// No operation. Unused.
#define CMD_NOP_1(field) \
    BC_BB(0x18, field)
    
// No operation. Unused.
#define CMD_NOP_2(field) \
    BC_BB(0x19, field)
    
// No operation. Unused.
#define CMD_NOP_3(field) \
    BC_BB(0x1A, field)

// Sets the current model ID of the object.
#define SET_MODEL(modelID) \
    BC_B0H(0x1B, modelID)

// Spawns a child object with the specified model and behavior.
#define SPAWN_CHILD(modelID, behavior) \
    BC_B(0x1C), \
    BC_W(modelID), \
    BC_PTR(behavior)

// Exits the behavior script and despawns the object.
// Often used to end behavior scripts that do not contain an infinite loop.
#define DEACTIVATE() \
    BC_B(0x1D)

// Finds the floor triangle directly under the object and moves the object down to it.
#define DROP_TO_FLOOR() \
    BC_B(0x1E)

// Sets the destination float field to the sum of the values of the given float fields.
#define SUM_FLOAT(fieldDst, fieldSrc1, fieldSrc2) \
    BC_BBBB(0x1F, fieldDst, fieldSrc1, fieldSrc2)

// Sets the destination integer field to the sum of the values of the given integer fields. Unused.
#define SUM_INT(fieldDst, fieldSrc1, fieldSrc2) \
    BC_BBBB(0x20, fieldDst, fieldSrc1, fieldSrc2)

// Billboards the current object, making it always face the camera.
#define BILLBOARD() \
    BC_B(0x21)

// Hides the current object.
#define HIDE() \
    BC_B(0x22)

// Sets the size of the object's cylindrical hitbox.
#define SET_HITBOX(radius, height) \
    BC_B(0x23), \
    BC_HH(radius, height)

// No operation. Unused.
#define CMD_NOP_4(field, value) \
    BC_BBH(0x24, field, value)

// Delays the behavior script for the number of frames given by the value of the specified field.
#define DELAY_VAR(field) \
    BC_BB(0x25, field)

// Unused. Marks the start of a loop that will repeat a certain number of times.
// Uses a u8 as the argument, instead of a s16 like the other version does.
#define BEGIN_REPEAT_UNUSED(count) \
    BC_BB(0x26, count)

// Loads the animations for the object. <field> is always set to oAnimations.
#define LOAD_ANIMATIONS(field, anims) \
    BC_BB(0x27, field), \
    BC_PTR(anims)

// Begins animation and sets the object's current animation index to the specified value.
#define ANIMATE(animIndex) \
    BC_BB(0x28, animIndex)

// Spawns a child object with the specified model and behavior, plus a behavior param.
#define SPAWN_CHILD_WITH_PARAM(bhvParam, modelID, behavior) \
    BC_B0H(0x29, bhvParam), \
    BC_W(modelID), \
    BC_PTR(behavior)

// Loads collision data for the object.
#define LOAD_COLLISION_DATA(collisionData) \
    BC_B(0x2A), \
    BC_PTR(collisionData)

// Sets the size of the object's cylindrical hitbox, and applies a downwards offset.
#define SET_HITBOX_WITH_OFFSET(radius, height, downOffset) \
    BC_B(0x2B), \
    BC_HH(radius, height), \
    BC_H(downOffset)

// Spawns a new object with the specified model and behavior.
#define SPAWN_OBJ(modelID, behavior) \
    BC_B(0x2C), \
    BC_W(modelID), \
    BC_PTR(behavior)

// Sets the home position of the object to its current position.
#define SET_HOME() \
    BC_B(0x2D)

// Sets the size of the object's cylindrical hurtbox.
#define SET_HURTBOX(radius, height) \
    BC_B(0x2E), \
    BC_HH(radius, height)

// Sets the object's interaction type.
#define SET_INTERACT_TYPE(type) \
    BC_B(0x2F), \
    BC_W(type)

// Sets various parameters that the object uses for calculating physics.
#define SET_OBJ_PHYSICS(wallHitboxRadius, gravity, bounciness, dragStrength, friction, buoyancy, unused1, unused2) \
    BC_B(0x30), \
    BC_HH(wallHitboxRadius, gravity), \
    BC_HH(bounciness, dragStrength), \
    BC_HH(friction, buoyancy), \
    BC_HH(unused1, unused2)

// Sets the object's interaction subtype. Unused.
#define SET_INTERACT_SUBTYPE(subtype) \
    BC_B(0x31), \
    BC_W(subtype)

// Sets the object's size to the specified percentage.
#define SCALE(unusedField, percent) \
    BC_BBH(0x32, unusedField, percent)

// Performs a bit clear on the object's parent's field with the specified value.
// Used for clearing active particle flags fron Mario's object.
#define PARENT_BIT_CLEAR(field, flags) \
    BC_BB(0x33, field), \
    BC_W(flags)

// Animates an object using texture animation. <field> is always set to oAnimState.
#define ANIMATE_TEXTURE(field, rate) \
    BC_BBH(0x34, field, rate)

// Disables rendering for the object.
#define DISABLE_RENDERING() \
    BC_B(0x35)

// Unused. Sets the specified field to an integer. Wastes 4 bytes of space for no reason at all.
#define SET_INT_UNUSED(field, value) \
    BC_BB(0x36, field), \
    BC_HH(0, value)

// Spawns a water droplet with the given parameters.
#define SPAWN_WATER_DROPLET(dropletParams) \
    BC_B(0x37), \
    BC_PTR(dropletParams)


const BehaviorScript bhvStarDoor[] = {
    BEGIN(OBJ_LIST_SURFACE),
    SET_INT(oInteractType, INTERACT_DOOR),
    LOAD_COLLISION_DATA(inside_castle_seg7_collision_star_door),
    SET_INT(oInteractionSubtype, INT_SUBTYPE_STAR_DOOR),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HITBOX(/*Radius*/ 80, /*Height*/ 100),
    SET_HOME(),
    SET_FLOAT(oDrawingDistance, 20000),
    CALL_NATIVE(bhv_door_init),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_star_door_loop),
        CALL_NATIVE(bhv_star_door_loop_2),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvMrI[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SPAWN_CHILD(/*Model*/ MODEL_MR_I_IRIS, /*Behavior*/ bhvMrIBody),
    SET_MODEL(MODEL_MR_I),
    BILLBOARD(),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_mr_i_loop),
    END_LOOP(),
};

const BehaviorScript bhvMrIBody[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_mr_i_body_loop),
    END_LOOP(),
};

const BehaviorScript bhvMrIParticle[] = {
    BEGIN(OBJ_LIST_LEVEL),
    BILLBOARD(),
    OR_INT(oFlags, (OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_INT(oIntangibleTimer, 0),
    SET_HITBOX(50, 50),
    SET_INT(oDamageOrCoinValue, 1),
    SET_INT(oInteractType, INTERACT_DAMAGE),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ 0, /*Bounciness*/ 0, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 0, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_mr_i_particle_loop),
    END_LOOP(),
};

const BehaviorScript bhvPurpleParticle[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    BILLBOARD(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_REPEAT(10),
        CALL_NATIVE(bhv_piranha_particle_loop),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvGiantPole[] = {
    BEGIN(OBJ_LIST_POLELIKE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INT(oInteractType, INTERACT_POLE),
    SET_HITBOX(/*Radius*/ 80, /*Height*/ 2100),
    SET_HOME(),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_giant_pole_loop),
    END_LOOP(),
};

const BehaviorScript bhvPoleGrabbing[] = {
    BEGIN(OBJ_LIST_POLELIKE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INT(oInteractType, INTERACT_POLE),
    SET_HITBOX(/*Radius*/ 80, /*Height*/ 1500),
    CALL_NATIVE(bhv_pole_init),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_pole_base_loop),
    END_LOOP(),
};

const BehaviorScript bhvThiHugeIslandTop[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(thi_seg7_collision_top_trap),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_thi_huge_island_top_loop),
    END_LOOP(),
};

const BehaviorScript bhvThiTinyIslandTop[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_thi_tiny_island_top_loop),
    END_LOOP(),
};

const BehaviorScript bhvCapSwitchBase[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(capswitch_collision_05003448),
    BEGIN_LOOP(),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvCapSwitch[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(capswitch_collision_050033D0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_cap_switch_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvKingBobomb[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, king_bobomb_seg5_anims_0500FE30),
    SET_INT(oInteractType, INTERACT_GRABBABLE),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 100),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_INT(oIntangibleTimer, 0),
    DROP_TO_FLOOR(),
    SET_HOME(),
    SPAWN_OBJ(/*Model*/ MODEL_NONE, /*Behavior*/ bhvBobombAnchorMario),
    SET_INT(oHealth, 3),
    SET_INT(oDamageOrCoinValue, 1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_king_bobomb_loop),
    END_LOOP(),
};

const BehaviorScript bhvBobombAnchorMario[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_FLOAT(oParentRelativePosX, 100),
    SET_FLOAT(oParentRelativePosZ, 150),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bobomb_anchor_mario_loop),
    END_LOOP(),
};

const BehaviorScript bhvBetaChestBottom[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    CALL_NATIVE(bhv_beta_chest_bottom_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_beta_chest_bottom_loop),
    END_LOOP(),
};

const BehaviorScript bhvBetaChestLid[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_beta_chest_lid_loop),
    END_LOOP(),
};

const BehaviorScript bhvBubbleParticleSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    DISABLE_RENDERING(),
    SET_RANDOM_INT(oWaterObjUnkF4, /*Minimum*/ 2, /*Range*/ 9),
    DELAY_VAR(oWaterObjUnkF4),
    SPAWN_CHILD(/*Model*/ MODEL_BUBBLE, /*Behavior*/ bhvSmallWaterWave),
    PARENT_BIT_CLEAR(oActiveParticleFlags, ACTIVE_PARTICLE_BUBBLE),
    DEACTIVATE(),
};

const BehaviorScript bhvBubbleMaybe[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    CALL_NATIVE(bhv_bubble_wave_init),
    SET_RANDOM_FLOAT(oWaterObjUnkF4, /*Minimum*/ -75, /*Range*/ 150),
    SET_RANDOM_FLOAT(oWaterObjUnkF8, /*Minimum*/ -75, /*Range*/ 150),
    SET_RANDOM_FLOAT(oWaterObjUnkFC, /*Minimum*/ -75, /*Range*/ 150),
    SUM_FLOAT(/*Dest*/ oPosX, /*Value 1*/ oPosX, /*Value 2*/ oWaterObjUnkF4),
    SUM_FLOAT(/*Dest*/ oPosZ, /*Value 1*/ oPosZ, /*Value 2*/ oWaterObjUnkF8),
    SUM_FLOAT(/*Dest*/ oPosY, /*Value 1*/ oPosY, /*Value 2*/ oWaterObjUnkFC),
    SET_INT(oAnimState, -1),
    BEGIN_REPEAT(60),
        ADD_INT(oAnimState, 1),
        CALL_NATIVE(bhv_bubble_maybe_loop),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvSmallWaterWave[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    CALL_NATIVE(bhv_bubble_wave_init),
    SET_RANDOM_FLOAT(oWaterObjUnkF4, /*Minimum*/ -50, /*Range*/ 100),
    SET_RANDOM_FLOAT(oWaterObjUnkF8, /*Minimum*/ -50, /*Range*/ 100),
    SUM_FLOAT(/*Dest*/ oPosX, /*Value 1*/ oPosX, /*Value 2*/ oWaterObjUnkF4),
    SUM_FLOAT(/*Dest*/ oPosZ, /*Value 1*/ oPosZ, /*Value 2*/ oWaterObjUnkF8),
    SET_RANDOM_FLOAT(oWaterObjUnkFC, /*Minimum*/ 0, /*Range*/ 50),
    SUM_FLOAT(/*Dest*/ oPosY, /*Value 1*/ oPosY, /*Value 2*/ oWaterObjUnkFC),
    SET_INT(oAnimState, -1),
    CALL(bhvSmallWaterWave398),
    BEGIN_REPEAT(60),
        CALL(bhvSmallWaterWave398),
        CALL_NATIVE(bhv_small_water_wave_loop),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvSmallWaterWave398[] = {
    ADD_INT(oAnimState, 1),
    ADD_FLOAT(oPosY, 7),
    SET_RANDOM_FLOAT(oWaterObjUnkF4, /*Minimum*/ -2, /*Range*/ 5),
    SET_RANDOM_FLOAT(oWaterObjUnkF8, /*Minimum*/ -2, /*Range*/ 5),
    SUM_FLOAT(/*Dest*/ oPosX, /*Value 1*/ oPosX, /*Value 2*/ oWaterObjUnkF4),
    SUM_FLOAT(/*Dest*/ oPosZ, /*Value 1*/ oPosZ, /*Value 2*/ oWaterObjUnkF8),
    RETURN(),
};

const BehaviorScript bhvWaterAirBubble[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 400, /*Height*/ 150, /*Downwards offset*/ -150),
    SET_INT(oIntangibleTimer, 0),
    SET_INTERACT_TYPE(INTERACT_WATER_RING),
    SET_INT(oDamageOrCoinValue, 5),
    CALL_NATIVE(bhv_water_air_bubble_init),
    SET_INT(oAnimState, -1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_water_air_bubble_loop),
    END_LOOP(),
};

const BehaviorScript bhvSmallParticle[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    BILLBOARD(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_particle_init),
    BEGIN_REPEAT(70),
        CALL_NATIVE(bhv_particle_loop),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvPlungeBubble[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    PARENT_BIT_CLEAR(oActiveParticleFlags, ACTIVE_PARTICLE_PLUNGE_BUBBLE),
    DISABLE_RENDERING(),
    CALL_NATIVE(bhv_water_waves_init),
    DEACTIVATE(),
};

const BehaviorScript bhvSmallParticleSnow[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    BILLBOARD(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_particle_init),
    BEGIN_REPEAT(30),
        CALL_NATIVE(bhv_particle_loop),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvSmallParticleBubbles[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    BILLBOARD(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_particle_init),
    BEGIN_REPEAT(70),
        CALL_NATIVE(bhv_small_bubbles_loop),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvFishGroup[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_fish_group_loop),
    END_LOOP(),
};

const BehaviorScript bhvCannon[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SPAWN_CHILD(/*Model*/ MODEL_CANNON_BARREL, /*Behavior*/ bhvCannonBarrel),
    SET_INT(oInteractType, INTERACT_CANNON_BASE),
    ADD_FLOAT(oPosY, -340),
    SET_HOME(),
    SET_HITBOX(/*Radius*/ 150, /*Height*/ 150),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_cannon_base_loop),
    END_LOOP(),
};

const BehaviorScript bhvCannonBarrel[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_cannon_barrel_loop),
    END_LOOP(),
};

const BehaviorScript bhvCannonBaseUnused[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_INT(oAnimState, -1),
    BEGIN_REPEAT(8),
        CALL_NATIVE(bhv_cannon_base_unused_loop),
        ADD_INT(oAnimState, 1),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvChuckya[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_HOLDABLE | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, chuckya_seg8_anims_0800C070),
    ANIMATE(5),
    SET_INT(oInteractType, INTERACT_GRABBABLE),
    SET_HITBOX(/*Radius*/ 150, /*Height*/ 100),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SPAWN_OBJ(/*Model*/ MODEL_NONE, /*Behavior*/ bhvChuckyaAnchorMario),
    SET_INT(oNumLootCoins, 5),
    SET_INT(oIntangibleTimer, 0),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_chuckya_loop),
    END_LOOP(),
};

const BehaviorScript bhvChuckyaAnchorMario[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_FLOAT(oParentRelativePosY, -60),
    SET_FLOAT(oParentRelativePosZ, 150),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_chuckya_anchor_mario_loop),
    END_LOOP(),
};

const BehaviorScript bhvUnused05A8[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BREAK(),
};

const BehaviorScript bhvRotatingPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_rotating_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvTower[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(wf_seg7_collision_tower),
    SET_FLOAT(oCollisionDistance, 3000),
    SET_FLOAT(oDrawingDistance, 20000),
    BEGIN_LOOP(),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvBulletBillCannon[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(wf_seg7_collision_bullet_bill_cannon),
    SET_FLOAT(oCollisionDistance, 300),
    BEGIN_LOOP(),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvWfBreakableWallRight[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(wf_seg7_collision_breakable_wall),
    GOTO(bhvWfBreakableWallLeft + 1 + 2),
};

const BehaviorScript bhvWfBreakableWallLeft[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(wf_seg7_collision_breakable_wall_2),
    // WF breakable walls - common:
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HITBOX(/*Radius*/ 300, /*Height*/ 400),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_wf_breakable_wall_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvKickableBoard[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(wf_seg7_collision_kickable_board),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 1200),
    SET_HURTBOX(/*Radius*/ 1, /*Height*/ 1),
    SET_FLOAT(oCollisionDistance, 1500),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_kickable_board_loop),
    END_LOOP(),
};

const BehaviorScript bhvTowerDoor[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(wf_seg7_collision_tower_door),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 100),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tower_door_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvRotatingCounterClockwise[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BREAK(),
};

const BehaviorScript bhvWfRotatingWoodenPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(wf_seg7_collision_clocklike_rotation),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_wf_rotating_wooden_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvKoopaShellUnderwater[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_HOLDABLE | OBJ_FLAG_COMPUTE_DIST_TO_MARIO  | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_koopa_shell_underwater_loop),
    END_LOOP(),
};

const BehaviorScript bhvExitPodiumWarp[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_INT(oInteractType, INTERACT_WARP),
    DROP_TO_FLOOR(),
    SET_FLOAT(oCollisionDistance, 8000),
    LOAD_COLLISION_DATA(ttm_seg7_collision_podium_warp),
    SET_INT(oIntangibleTimer, 0),
    SET_HITBOX(/*Radius*/ 50, /*Height*/ 50),
    BEGIN_LOOP(),
        CALL_NATIVE(load_object_collision_model),
        SET_INT(oInteractStatus, 0),
    END_LOOP(),
};

const BehaviorScript bhvFadingWarp[] = {
    BEGIN(OBJ_LIST_LEVEL),
    SET_INT(oInteractionSubtype, INT_SUBTYPE_FADING_WARP),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_INT(oInteractType, INTERACT_WARP),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_fading_warp_loop),
    END_LOOP(),
};

const BehaviorScript bhvWarp[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_INT(oInteractType, INTERACT_WARP),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_warp_loop),
    END_LOOP(),
};

const BehaviorScript bhvWarpPipe[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_INT(oInteractType, INTERACT_WARP),
    LOAD_COLLISION_DATA(warp_pipe_seg3_collision_03009AC8),
    SET_FLOAT(oDrawingDistance, 16000),
    SET_INT(oIntangibleTimer, 0),
    SET_HITBOX(/*Radius*/ 70, /*Height*/ 50),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_warp_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvWhitePuffExplosion[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_white_puff_exploding_loop),
    END_LOOP(),
};

const BehaviorScript bhvSpawnedStar[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INT(oBehParams2ndByte, 1),
    GOTO(bhvSpawnedStarNoLevelExit + 1 + 1),
};

const BehaviorScript bhvSpawnedStarNoLevelExit[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    // Spawned star - common:
    SET_HOME(),
    CALL_NATIVE(bhv_spawned_star_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_spawned_star_loop),
    END_LOOP(),
};

const BehaviorScript bhvMrIBlueCoin[] = {
    BEGIN(OBJ_LIST_LEVEL),
    SET_INT(oInteractType, INTERACT_COIN),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_INT(oIntangibleTimer, 0),
    SET_FLOAT(oCoinUnk110, 20),
    SET_INT(oAnimState, -1),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -70, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_coin_init),
    SET_INT(oDamageOrCoinValue, 5),
    SET_HITBOX(/*Radius*/ 120, /*Height*/ 64),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_coin_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvCoinInsideBoo[] = {
    BEGIN(OBJ_LIST_LEVEL),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 64),
    SET_INT(oInteractType, INTERACT_COIN),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -70, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BILLBOARD(),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_coin_inside_boo_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvCoinFormationSpawn[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_coin_formation_spawn_loop),
    END_LOOP(),
};

const BehaviorScript bhvCoinFormation[] = {
    BEGIN(OBJ_LIST_SPAWNER),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_coin_formation_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_coin_formation_loop),
    END_LOOP(),
};

const BehaviorScript bhvOneCoin[] = {
    BEGIN(OBJ_LIST_LEVEL),
    SET_INT(oBehParams2ndByte, 1),
    GOTO(bhvYellowCoin + 1),
};

const BehaviorScript bhvYellowCoin[] = {
    BEGIN(OBJ_LIST_LEVEL),
    // Yellow coin - common:
    BILLBOARD(),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_yellow_coin_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_yellow_coin_loop),
    END_LOOP(),
};

const BehaviorScript bhvTemporaryYellowCoin[] = {
    BEGIN(OBJ_LIST_LEVEL),
    BILLBOARD(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_yellow_coin_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_temp_coin_loop),
    END_LOOP(),
};

const BehaviorScript bhvThreeCoinsSpawn[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_REPEAT(3),
        SPAWN_CHILD(/*Model*/ MODEL_YELLOW_COIN, /*Behavior*/ bhvSingleCoinGetsSpawned),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvTenCoinsSpawn[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_REPEAT(10),
        SPAWN_CHILD(/*Model*/ MODEL_YELLOW_COIN, /*Behavior*/ bhvSingleCoinGetsSpawned),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvSingleCoinGetsSpawned[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    CALL_NATIVE(bhv_coin_init),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -70, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_coin_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvCoinSparkles[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_FLOAT(oGraphYOffset, 25),
    SET_INT(oAnimState, -1),
    BEGIN_REPEAT(8),
        ADD_INT(oAnimState, 1),
    END_REPEAT(),
    BEGIN_REPEAT(2),
        CALL_NATIVE(bhv_coin_sparkles_loop),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvGoldenCoinSparkles[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    DISABLE_RENDERING(),
    BEGIN_REPEAT(3),
        CALL_NATIVE(bhv_golden_coin_sparkles_loop),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvWallTinyStarParticle[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    BEGIN_REPEAT(10),
        CALL_NATIVE(bhv_wall_tiny_star_particle_loop),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvVertStarParticleSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    DISABLE_RENDERING(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    PARENT_BIT_CLEAR(oActiveParticleFlags, ACTIVE_PARTICLE_V_STAR),
    CALL_NATIVE(bhv_tiny_star_particles_init),
    DELAY(1),
    DEACTIVATE(),
};

const BehaviorScript bhvPoundTinyStarParticle[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    BEGIN_REPEAT(10),
        CALL_NATIVE(bhv_pound_tiny_star_particle_loop),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvHorStarParticleSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    DISABLE_RENDERING(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    PARENT_BIT_CLEAR(oActiveParticleFlags, ACTIVE_PARTICLE_H_STAR),
    CALL_NATIVE(bhv_pound_tiny_star_particle_init),
    DELAY(1),
    DEACTIVATE(),
};

const BehaviorScript bhvPunchTinyTriangle[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_punch_tiny_triangle_loop),
    END_LOOP(),
};

const BehaviorScript bhvTriangleParticleSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    DISABLE_RENDERING(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    PARENT_BIT_CLEAR(oActiveParticleFlags, ACTIVE_PARTICLE_TRIANGLE),
    CALL_NATIVE(bhv_punch_tiny_triangle_init),
    DELAY(1),
    DEACTIVATE(),
};

const BehaviorScript bhvDoorWarp[] = {
    BEGIN(OBJ_LIST_SURFACE),
    SET_INT(oInteractType, INTERACT_WARP_DOOR),
    GOTO(bhvDoor + 1 + 1),
};

const BehaviorScript bhvDoor[] = {
    BEGIN(OBJ_LIST_SURFACE),
    SET_INT(oInteractType, INTERACT_DOOR),
    // Door - common:
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, door_seg3_anims_030156C0),
    ANIMATE(0),
    LOAD_COLLISION_DATA(door_seg3_collision_0301CE78),
    SET_HITBOX(/*Radius*/ 80, /*Height*/ 100),
    SET_INT(oIntangibleTimer, 0),
    SET_FLOAT(oCollisionDistance, 1000),
    SET_HOME(),
    CALL_NATIVE(bhv_door_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_door_loop),
    END_LOOP(),
};

const BehaviorScript bhvGrindel[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(ssl_seg7_collision_grindel),
    DROP_TO_FLOOR(),
    ADD_FLOAT(oPosY, 1),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_grindel_thwomp_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvThwomp2[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(thwomp_seg5_collision_0500B92C),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    ADD_FLOAT(oPosY, 1),
    SET_HOME(),
    SCALE(/*Unused*/ 0, /*Field*/ 140),
    SET_FLOAT(oDrawingDistance, 4000),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_grindel_thwomp_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvThwomp[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(thwomp_seg5_collision_0500B7D0),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    ADD_FLOAT(oPosY, 1),
    SCALE(/*Unused*/ 0, /*Field*/ 140),
    SET_HOME(),
    SET_FLOAT(oDrawingDistance, 4000),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_grindel_thwomp_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvTumblingBridgePlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_FLOAT(oCollisionDistance, 300),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tumbling_bridge_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvWfTumblingBridge[] = {
    BEGIN(OBJ_LIST_SPAWNER),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tumbling_bridge_loop),
    END_LOOP(),
};

const BehaviorScript bhvBbhTumblingBridge[] = {
    BEGIN(OBJ_LIST_SPAWNER),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SET_INT(oBehParams2ndByte, 1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tumbling_bridge_loop),
    END_LOOP(),
};

const BehaviorScript bhvLllTumblingBridge[] = {
    BEGIN(OBJ_LIST_SPAWNER),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SET_INT(oBehParams2ndByte, 2),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tumbling_bridge_loop),
    END_LOOP(),
};

const BehaviorScript bhvFlame[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_HOME(),
    SCALE(/*Unused*/ 0, /*Field*/ 700),
    SET_INTERACT_TYPE(INTERACT_FLAME),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 50, /*Height*/ 25, /*Downwards offset*/ 25),
    SET_INT(oIntangibleTimer, 0),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        SET_INT(oInteractStatus, 0),
        ANIMATE_TEXTURE(oAnimState, 2),
    END_LOOP(),
};

const BehaviorScript bhvAnotherElavator[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(hmc_seg7_collision_elevator),
    SET_HOME(),
    CALL_NATIVE(bhv_elevator_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_elevator_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvRrElevatorPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(rr_seg7_collision_elevator_platform),
    SET_HOME(),
    CALL_NATIVE(bhv_elevator_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_elevator_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvHmcElevatorPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(hmc_seg7_collision_elevator),
    SET_HOME(),
    CALL_NATIVE(bhv_elevator_init),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_elevator_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvWaterMist[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_INT(oOpacity, 254),
    SET_FLOAT(oForwardVel, 20),
    SET_FLOAT(oVelY, -8),
    ADD_FLOAT(oPosY, 62),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_water_mist_loop),
    END_LOOP(),
};

const BehaviorScript bhvBreathParticleSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_REPEAT(8),
        CALL_NATIVE(bhv_water_mist_spawn_loop),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvBreakBoxTriangle[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_REPEAT(18),
        CALL_NATIVE(cur_obj_rotate_face_angle_using_vel),
        CALL_NATIVE(cur_obj_move_using_fvel_and_gravity),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvWaterMist2[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SET_INT(oFaceAnglePitch, 0xC000),
    SCALE(/*Unused*/ 0, /*Field*/ 2100),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_water_mist_2_loop),
    END_LOOP(),
};

const BehaviorScript bhvUnused0DFC[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INT(oAnimState, -1),
    SET_FLOAT(oFaceAnglePitch, 0),
    SET_FLOAT(oFaceAngleYaw, 0),
    SET_FLOAT(oFaceAngleRoll, 0),
    BEGIN_REPEAT(6),
        ADD_INT(oAnimState, 1),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvMistCircParticleSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_pound_white_puffs_init),
    DELAY(1),
    DEACTIVATE(),
};

const BehaviorScript bhvDirtParticleSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_ground_sand_init),
    DELAY(1),
    DEACTIVATE(),
};

const BehaviorScript bhvSnowParticleSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_ground_snow_init),
    DELAY(1),
    DEACTIVATE(),
};

const BehaviorScript bhvWind[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_wind_loop),
    END_LOOP(),
};

const BehaviorScript bhvEndToad[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_ANIMATIONS(oAnimations, toad_seg6_anims_0600FB58),
    ANIMATE(0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_end_toad_loop),
    END_LOOP(),
};

const BehaviorScript bhvEndPeach[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_ANIMATIONS(oAnimations, peach_seg5_anims_0501C41C),
    ANIMATE(0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_end_peach_loop),
    END_LOOP(),
};

const BehaviorScript bhvUnusedParticleSpawn[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_INT(oIntangibleTimer, 0),
    SET_HITBOX(/*Radius*/ 40, /*Height*/ 40),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_unused_particle_spawn_loop),
    END_LOOP(),
};

const BehaviorScript bhvUkiki[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    GOTO(bhvMacroUkiki + 1),
};

const BehaviorScript bhvUkikiCageChild[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_FLOAT(oPosX, 2560),
    SET_FLOAT(oPosY, 1457),
    SET_FLOAT(oPosZ, 1898),
    BREAK(),
};

const BehaviorScript bhvUkikiCageStar[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ukiki_cage_star_loop),
    END_LOOP(),
};

const BehaviorScript bhvUkikiCage[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    LOAD_COLLISION_DATA(ttm_seg7_collision_ukiki_cage),
    SPAWN_CHILD(/*Model*/ MODEL_STAR, /*Behavior*/ bhvUkikiCageStar),
    SPAWN_CHILD(/*Model*/ MODEL_NONE, /*Behavior*/ bhvUkikiCageChild),
    SET_FLOAT(oCollisionDistance, 20000),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ukiki_cage_loop),
    END_LOOP(),
};

const BehaviorScript bhvBitfsSinkingPlatforms[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(bitfs_seg7_collision_sinking_platform),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bitfs_sinking_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvBitfsSinkingCagePlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(bitfs_seg7_collision_sinking_cage_platform),
    SET_HOME(),
    SPAWN_CHILD(/*Model*/ MODEL_BITFS_BLUE_POLE, /*Behavior*/ bhvDddMovingPole),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bitfs_sinking_cage_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvDddMovingPole[] = {
    BEGIN(OBJ_LIST_POLELIKE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INT(oInteractType, INTERACT_POLE),
    SET_HITBOX(/*Radius*/ 80, /*Height*/ 710),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ddd_moving_pole_loop),
        CALL_NATIVE(bhv_pole_base_loop),
    END_LOOP(),
};

const BehaviorScript bhvBitfsTiltingInvertedPyramid[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(bitfs_seg7_collision_inverted_pyramid),
    SET_HOME(),
    CALL_NATIVE(bhv_platform_normals_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tilting_inverted_pyramid_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvSquishablePlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(bitfs_seg7_collision_squishable_platform),
    SET_FLOAT(oCollisionDistance, 10000),
    CALL_NATIVE(bhv_platform_normals_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_squishable_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvCutOutObject[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    DISABLE_RENDERING(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BREAK(),
};

const BehaviorScript bhvBetaMovingFlamesSpawn[] = {
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_beta_moving_flames_spawn_loop),
    END_LOOP(),
};

const BehaviorScript bhvBetaMovingFlames[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_beta_moving_flames_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvRrRotatingBridgePlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(rr_seg7_collision_rotating_platform_with_fire),
    SET_FLOAT(oCollisionDistance, 1500),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_rr_rotating_bridge_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvFlamethrower[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_flamethrower_loop),
    END_LOOP(),
};

const BehaviorScript bhvFlamethrowerFlame[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INTERACT_TYPE(INTERACT_FLAME),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 50, /*Height*/ 25, /*Downwards offset*/ 25),
    BILLBOARD(),
    SET_HOME(),
    SET_INT(oIntangibleTimer, 0),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_flamethrower_flame_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvBouncingFireball[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DISABLE_RENDERING(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bouncing_fireball_loop),
    END_LOOP(),
};

const BehaviorScript bhvBouncingFireballFlame[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INTERACT_TYPE(INTERACT_FLAME),
    SET_FLOAT(oGraphYOffset, 30),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 50, /*Height*/ 25, /*Downwards offset*/ 25),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -70, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bouncing_fireball_flame_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvBowserShockWave[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_INT(oOpacity, 255),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bowser_shock_wave_loop),
    END_LOOP(),
};

const BehaviorScript bhvFireParticleSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_FLOAT(oGraphYOffset, 70),
    SET_INT(oAnimState, -1),
    BEGIN_LOOP(),
        ADD_INT(oAnimState, 1),
        CALL_NATIVE(bhv_flame_mario_loop),
    END_LOOP(),
};

const BehaviorScript bhvBlackSmokeMario[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_INT(oAnimState, 4),
    SET_FLOAT(oGraphYOffset, 50),
    BEGIN_REPEAT(8),
        CALL_NATIVE(bhv_black_smoke_mario_loop),
        DELAY(1),
        CALL_NATIVE(bhv_black_smoke_mario_loop),
        DELAY(1),
        CALL_NATIVE(bhv_black_smoke_mario_loop),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvBlackSmokeBowser[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_FLOAT(oGraphYOffset, 0),
    BEGIN_REPEAT(8),
        CALL_NATIVE(bhv_black_smoke_bowser_loop),
        ANIMATE_TEXTURE(oAnimState, 4),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvBlackSmokeUpward[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_REPEAT(4),
        CALL_NATIVE(bhv_black_smoke_upward_loop),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvBetaFishSplashSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DISABLE_RENDERING(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_beta_fish_splash_spawner_loop),
    END_LOOP(),
};

const BehaviorScript bhvSpindrift[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, spindrift_seg5_anims_05002D68),
    ANIMATE(0),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ 0, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_HOME(),
    SET_INT(oInteractionSubtype, INT_SUBTYPE_TWIRL_BOUNCE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_spindrift_loop),
    END_LOOP(),
};

const BehaviorScript bhvTowerPlatformGroup[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    DISABLE_RENDERING(),
    ADD_FLOAT(oPosY, 300),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tower_platform_group_loop),
    END_LOOP(),
};

const BehaviorScript bhvWfSlidingTowerPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(wf_seg7_collision_platform),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_wf_sliding_tower_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvWfElevatorTowerPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(wf_seg7_collision_platform),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_wf_elevator_tower_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvWfSolidTowerPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(wf_seg7_collision_platform),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_wf_solid_tower_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvLeafParticleSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_snow_leaf_particle_spawn_init),
    DELAY(1),
    DEACTIVATE(),
};

const BehaviorScript bhvTreeSnow[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, (OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tree_snow_or_leaf_loop),
    END_LOOP(),
};

const BehaviorScript bhvTreeLeaf[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, (OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tree_snow_or_leaf_loop),
    END_LOOP(),
};

const BehaviorScript bhvAnotherTiltingPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    CALL_NATIVE(bhv_platform_normals_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tilting_inverted_pyramid_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvSquarishPathMoving[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(bitdw_seg7_collision_moving_pyramid),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_squarish_path_moving_loop),
    END_LOOP(),
};

const BehaviorScript bhvPiranhaPlantBubble[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_piranha_plant_bubble_loop),
    END_LOOP(),
};

const BehaviorScript bhvPiranhaPlantWakingBubbles[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    BILLBOARD(),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_REPEAT(10),
        CALL_NATIVE(bhv_piranha_plant_waking_bubbles_loop),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvFloorSwitchAnimatesObject[] = {
    BEGIN(OBJ_LIST_SURFACE),
    SET_INT(oBehParams2ndByte, 1),
    GOTO(bhvFloorSwitchHardcodedModel + 1),
};

const BehaviorScript bhvFloorSwitchGrills[] = {
    BEGIN(OBJ_LIST_SURFACE),
    GOTO(bhvFloorSwitchHardcodedModel + 1),
};

const BehaviorScript bhvFloorSwitchHardcodedModel[] = {
    BEGIN(OBJ_LIST_SURFACE),
    // Floor switch - common:
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(purple_switch_seg8_collision_0800C7A8),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_purple_switch_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvFloorSwitchHiddenObjects[] = {
    BEGIN(OBJ_LIST_SURFACE),
    SET_INT(oBehParams2ndByte, 2),
    GOTO(bhvFloorSwitchHardcodedModel + 1),
};

const BehaviorScript bhvHiddenObject[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(breakable_box_seg8_collision_08012D70),
    SET_FLOAT(oCollisionDistance, 300),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_hidden_object_loop),
    END_LOOP(),
};

const BehaviorScript bhvBreakableBox[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(breakable_box_seg8_collision_08012D70),
    SET_FLOAT(oCollisionDistance, 500),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_breakable_box_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
    BREAK(),
};

const BehaviorScript bhvPushableMetalBox[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(metal_box_seg8_collision_08024C28),
    SET_FLOAT(oCollisionDistance, 500),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_pushable_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvHeaveHo[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_HOLDABLE | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, heave_ho_seg5_anims_0501534C),
    ANIMATE(0),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 200, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 600, /*Unused*/ 0, 0),
    SPAWN_OBJ(/*Model*/ MODEL_NONE, /*Behavior*/ bhvHeaveHoThrowMario),
    SET_INT(oInteractType, INTERACT_GRABBABLE),
    SET_INT(oInteractionSubtype, INT_SUBTYPE_NOT_GRABBABLE | INT_SUBTYPE_GRABS_MARIO),
    SET_HITBOX(/*Radius*/ 120, /*Height*/ 100),
    SET_HOME(),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_heave_ho_loop),
    END_LOOP(),
};

const BehaviorScript bhvHeaveHoThrowMario[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_heave_ho_throw_mario_loop),
    END_LOOP(),
};

const BehaviorScript bhvCcmTouchedStarSpawn[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_PERSISTENT_RESPAWN | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HITBOX(/*Radius*/ 500, /*Height*/ 500),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ccm_touched_star_spawn_loop),
    END_LOOP(),
};

const BehaviorScript bhvUnusedPoundablePlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(sl_seg7_collision_pound_explodes),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_unused_poundable_platform),
    END_LOOP(),
};

const BehaviorScript bhvBetaTrampolineTop[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(springboard_collision_05001A28),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_beta_trampoline_top_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvBetaTrampolineSpring[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_beta_trampoline_spring_loop),
    END_LOOP(),
};

const BehaviorScript bhvJumpingBox[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_HOLDABLE | OBJ_FLAG_COMPUTE_DIST_TO_MARIO  | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 600, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_jumping_box_loop),
    END_LOOP(),
};

const BehaviorScript bhvBooCage[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_FLOAT(oGraphYOffset, 10),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_boo_cage_loop),
    END_LOOP(),
};

const BehaviorScript bhvStub[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    DISABLE_RENDERING(),
    BREAK(),
};

const BehaviorScript bhvIgloo[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INTERACT_TYPE(INTERACT_IGLOO_BARRIER),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 200),
    SET_INT(oIntangibleTimer, 0),
    SET_HOME(),
    BEGIN_LOOP(),
        SET_INT(oInteractStatus, 0),
    END_LOOP(),
};

const BehaviorScript bhvBowserKey[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -70, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bowser_key_loop),
    END_LOOP(),
};

const BehaviorScript bhvGrandStar[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INTERACT_TYPE(INTERACT_STAR_OR_KEY),
    SET_INT(oInteractionSubtype, INT_SUBTYPE_GRAND_STAR),
    SET_HITBOX(/*Radius*/ 160, /*Height*/ 100),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_grand_star_loop),
    END_LOOP(),
};

const BehaviorScript bhvBetaBooKey[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HITBOX(/*Radius*/ 32, /*Height*/ 64),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -70, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_beta_boo_key_loop),
    END_LOOP(),
};

const BehaviorScript bhvAlphaBooKey[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HITBOX(/*Radius*/ 32, /*Height*/ 64),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_alpha_boo_key_loop),
    END_LOOP(),
};

const BehaviorScript bhvBulletBill[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 50, /*Height*/ 50, /*Downwards offset*/ 50),
    SET_INTERACT_TYPE(INTERACT_DAMAGE),
    SET_INT(oDamageOrCoinValue, 3),
    SCALE(/*Unused*/ 0, /*Field*/ 40),
    SET_INT(oIntangibleTimer, 0),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ 0, /*Bounciness*/ 0, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 0, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_bullet_bill_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bullet_bill_loop),
    END_LOOP(),
};

const BehaviorScript bhvWhitePuffSmoke[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    ADD_FLOAT(oPosY, -100),
    CALL_NATIVE(bhv_white_puff_smoke_init),
    SET_INT(oAnimState, -1),
    BEGIN_REPEAT(10),
        ADD_INT(oAnimState, 1),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvUnused1820[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BREAK(),
};

const BehaviorScript bhvBowserTailAnchor[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 100, /*Height*/ 50, /*Downwards offset*/ -50),
    SET_INT(oIntangibleTimer, 0),
    DISABLE_RENDERING(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bowser_tail_anchor_loop),
    END_LOOP(),
};

const BehaviorScript bhvBowser[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_HOLDABLE | OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_INT(oInteractType, INTERACT_GRABBABLE),
    SET_HITBOX(/*Radius*/ 400, /*Height*/ 400),
    DROP_TO_FLOOR(),
    SET_HOME(),
    LOAD_ANIMATIONS(oAnimations, bowser_seg6_anims_06057690),
    SPAWN_CHILD(/*Model*/ MODEL_NONE, /*Behavior*/ bhvBowserBodyAnchor),
    SPAWN_CHILD(/*Model*/ MODEL_BOWSER_BOMB_CHILD_OBJ, /*Behavior*/ bhvBowserFlameSpawn),
    SPAWN_OBJ(/*Model*/ MODEL_NONE, /*Behavior*/ bhvBowserTailAnchor),
    // Beta leftover that spawn 50 coins when Bowser is defeated
    SET_INT(oNumLootCoins, 50),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 0, /*Gravity*/ -400, /*Bounciness*/ -70, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_HOME(),
    CALL_NATIVE(bhv_bowser_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bowser_loop),
    END_LOOP(),
};

const BehaviorScript bhvBowserBodyAnchor[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 300),
    SET_INTERACT_TYPE(INTERACT_DAMAGE),
    SET_INT(oInteractionSubtype, INT_SUBTYPE_BIG_KNOCKBACK),
    DISABLE_RENDERING(),
    SET_INT(oDamageOrCoinValue, 2),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bowser_body_anchor_loop),
    END_LOOP(),
};

const BehaviorScript bhvBowserFlameSpawn[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_MODEL(MODEL_NONE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bowser_flame_spawn_loop),
    END_LOOP(),
};

const BehaviorScript bhvTiltingBowserLavaPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(bowser_2_seg7_collision_tilting_platform),
    SET_FLOAT(oDrawingDistance, 20000),
    SET_FLOAT(oCollisionDistance, 20000),
    SET_INT(oFaceAngleYaw, 0),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(cur_obj_rotate_face_angle_using_vel),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvFallingBowserPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_FLOAT(oDrawingDistance, 20000),
    SET_FLOAT(oCollisionDistance, 20000),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_falling_bowser_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvBlueBowserFlame[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INTERACT_TYPE(INTERACT_FLAME),
    BILLBOARD(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 0, /*Gravity*/ -400, /*Bounciness*/ -70, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_blue_bowser_flame_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_blue_bowser_flame_loop),
        ANIMATE_TEXTURE(oAnimState, 2),
    END_LOOP(),
};

const BehaviorScript bhvFlameFloatingLanding[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INTERACT_TYPE(INTERACT_FLAME),
    BILLBOARD(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 0, /*Gravity*/ -400, /*Bounciness*/ -70, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_flame_floating_landing_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_flame_floating_landing_loop),
        ANIMATE_TEXTURE(oAnimState, 2),
    END_LOOP(),
};

const BehaviorScript bhvBlueFlamesGroup[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_INTERACT_TYPE(INTERACT_FLAME),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_blue_flames_group_loop),
    END_LOOP(),
};

const BehaviorScript bhvFlameBouncing[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_INTERACT_TYPE(INTERACT_FLAME),
    BILLBOARD(),
    CALL_NATIVE(bhv_flame_bouncing_init),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 0, /*Gravity*/ -400, /*Bounciness*/ -70, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_flame_bouncing_loop),
        ANIMATE_TEXTURE(oAnimState, 2),
    END_LOOP(),
};

const BehaviorScript bhvFlameMovingForwardGrowing[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INTERACT_TYPE(INTERACT_FLAME),
    BILLBOARD(),
    CALL_NATIVE(bhv_flame_moving_forward_growing_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_flame_moving_forward_growing_loop),
        ANIMATE_TEXTURE(oAnimState, 2),
    END_LOOP(),
};

const BehaviorScript bhvFlameBowser[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INTERACT_TYPE(INTERACT_FLAME),
    BILLBOARD(),
    CALL_NATIVE(bhv_flame_bowser_init),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 0, /*Gravity*/ -400, /*Bounciness*/ -70, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_flame_bowser_loop),
        ANIMATE_TEXTURE(oAnimState, 2),
    END_LOOP(),
};

const BehaviorScript bhvFlameLargeBurningOut[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INTERACT_TYPE(INTERACT_FLAME),
    BILLBOARD(),
    CALL_NATIVE(bhv_flame_large_burning_out_init),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 0, /*Gravity*/ -400, /*Bounciness*/ -70, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_flame_bowser_loop),
        ANIMATE_TEXTURE(oAnimState, 2),
    END_LOOP(),
};

const BehaviorScript bhvBlueFish[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    LOAD_ANIMATIONS(oAnimations, blue_fish_seg3_anims_0301C2B0),
    ANIMATE(0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_blue_fish_movement_loop),
    END_LOOP(),
};

const BehaviorScript bhvTankFishGroup[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tank_fish_group_loop),
    END_LOOP(),
};

const BehaviorScript bhvCheckerboardElevatorGroup[] = {
    BEGIN(OBJ_LIST_SPAWNER),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_checkerboard_elevator_group_init),
    DELAY(1),
    DEACTIVATE(),
};

const BehaviorScript bhvCheckerboardPlatformSub[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(checkerboard_platform_seg8_collision_0800D710),
    CALL_NATIVE(bhv_checkerboard_platform_init),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_checkerboard_platform_loop),
    END_LOOP(),
};

const BehaviorScript bhvBowserKeyUnlockDoor[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    LOAD_ANIMATIONS(oAnimations, bowser_key_seg3_anims_list),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bowser_key_unlock_door_loop),
    END_LOOP(),
};

const BehaviorScript bhvBowserKeyCourseExit[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    LOAD_ANIMATIONS(oAnimations, bowser_key_seg3_anims_list),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bowser_key_course_exit_loop),
    END_LOOP(),
};

const BehaviorScript bhvInvisibleObjectsUnderBridge[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    CALL_NATIVE(bhv_invisible_objects_under_bridge_init),
    BREAK(),
};

const BehaviorScript bhvWaterLevelPillar[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(inside_castle_seg7_collision_water_level_pillar),
    CALL_NATIVE(bhv_water_level_pillar_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_water_level_pillar_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvDddWarp[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_FLOAT(oCollisionDistance, 30000),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ddd_warp_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvMoatGrills[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(castle_grounds_seg7_collision_moat_grills),
    SET_FLOAT(oCollisionDistance, 30000),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_moat_grills_loop),
    END_LOOP(),
};

const BehaviorScript bhvClockMinuteHand[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    SET_INT(oAngleVelRoll, -0x180),
    GOTO(bhvClockHourHand + 1 + 1),
};

const BehaviorScript bhvClockHourHand[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    SET_INT(oAngleVelRoll, -0x20),
    // Clock hand - common:
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_rotating_clock_arm_loop),
    END_LOOP(),
};

const BehaviorScript bhvMacroUkiki[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    // Ukiki - common:
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_HOLDABLE | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_INT(oInteractType, INTERACT_GRABBABLE),
    SET_INT(oInteractionSubtype, INT_SUBTYPE_HOLDABLE_NPC),
    SET_HITBOX(/*Radius*/ 40, /*Height*/ 40),
    SET_INT(oIntangibleTimer, 0),
    DROP_TO_FLOOR(),
    LOAD_ANIMATIONS(oAnimations, ukiki_seg5_anims_05015784),
    ANIMATE(0),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_HOME(),
    CALL_NATIVE(bhv_ukiki_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ukiki_loop),
    END_LOOP(),
};

const BehaviorScript bhvStub1D0C[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    DEACTIVATE(),
};

const BehaviorScript bhvLllRotatingHexagonalPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(lll_seg7_collision_hexagonal_platform),
    SET_HOME(),
    BEGIN_LOOP(),
        SET_INT(oAngleVelYaw,  0x100),
        ADD_INT(oMoveAngleYaw, 0x100),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvLllSinkingRockBlock[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(lll_seg7_collision_floating_block),
    ADD_FLOAT(oPosY, -50),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_lll_sinking_rock_block_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvStub1D70[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BREAK(),
};

const BehaviorScript bhvLllMovingOctagonalMeshPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    ADD_FLOAT(oPosY, -50),
    LOAD_COLLISION_DATA(lll_seg7_collision_octagonal_moving_platform),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_lll_moving_octagonal_mesh_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvSnowBall[] = {
    BREAK(),
};

const BehaviorScript bhvLllRotatingBlockWithFireBars[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(lll_seg7_collision_rotating_fire_bars),
    SET_FLOAT(oCollisionDistance, 4000),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_lll_rotating_block_fire_bars_loop),
    END_LOOP(),
};

const BehaviorScript bhvLllRotatingHexFlame[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_INTERACT_TYPE(INTERACT_FLAME),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 50, /*Height*/ 100, /*Downwards offset*/ 50),
    SET_INT(oIntangibleTimer, 0),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_lll_rotating_hex_flame_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvLllWoodPiece[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(lll_seg7_collision_wood_piece),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_lll_wood_piece_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvLllFloatingWoodBridge[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_MODEL(MODEL_NONE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_lll_floating_wood_bridge_loop),
    END_LOOP(),
};

const BehaviorScript bhvVolcanoFlames[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    BEGIN_LOOP(),
        ADD_INT(oAnimState, 1),
        CALL_NATIVE(bhv_volcano_flames_loop),
    END_LOOP(),
};

const BehaviorScript bhvLllRotatingHexagonalRing[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(lll_seg7_collision_rotating_platform),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_lll_rotating_hexagonal_ring_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvLllSinkingRectangularPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(lll_seg7_collision_slow_tilting_platform),
    SET_FLOAT(oCollisionDistance, 2000),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_lll_sinking_rectangular_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvLllSinkingSquarePlatforms[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(lll_seg7_collision_sinking_pyramids),
    ADD_FLOAT(oPosY, 5),
    SET_FLOAT(oCollisionDistance, 2000),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_lll_sinking_square_platforms_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvLllTiltingInvertedPyramid[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(lll_seg7_collision_inverted_pyramid),
    ADD_FLOAT(oPosY, 5),
    SET_HOME(),
    CALL_NATIVE(bhv_platform_normals_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tilting_inverted_pyramid_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvUnused1F30[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BREAK(),
};

const BehaviorScript bhvKoopaShell[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_koopa_shell_loop),
    END_LOOP(),
};

const BehaviorScript bhvKoopaShellFlame[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INTERACT_TYPE(INTERACT_FLAME),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_koopa_shell_flame_loop),
        ANIMATE_TEXTURE(oAnimState, 2),
    END_LOOP(),
};

const BehaviorScript bhvToxBox[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(ssl_seg7_collision_tox_box),
    ADD_FLOAT(oPosY, 256),
    SET_FLOAT(oDrawingDistance, 8000),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tox_box_loop),
    END_LOOP(),
};

const BehaviorScript bhvPiranhaPlant[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, piranha_plant_seg6_anims_0601C31C),
    ANIMATE(0),
    SET_INTERACT_TYPE(INTERACT_DAMAGE),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 200),
    SET_HURTBOX(/*Radius*/ 50, /*Height*/ 200),
    SET_INT(oIntangibleTimer, 0),
    SET_INT(oDamageOrCoinValue, 3),
    SET_INT(oNumLootCoins, 5),
    SPAWN_CHILD(/*Model*/ MODEL_BUBBLE, /*Behavior*/ bhvPiranhaPlantBubble),
    SET_FLOAT(oDrawingDistance, 2000),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_piranha_plant_loop),
    END_LOOP(),
};

const BehaviorScript bhvLllHexagonalMesh[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(lll_hexagonal_mesh_seg3_collision_0301CECC),
    BEGIN_LOOP(),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvLllBowserPuzzlePiece[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(lll_seg7_collision_puzzle_piece),
    SET_HOME(),
    SET_FLOAT(oCollisionDistance, 3000),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_lll_bowser_puzzle_piece_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvLllBowserPuzzle[] = {
    BEGIN(OBJ_LIST_SPAWNER),
    DISABLE_RENDERING(),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    ADD_FLOAT(oPosZ, -50),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_lll_bowser_puzzle_loop),
    END_LOOP(),
};

const BehaviorScript bhvTuxiesMother[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, penguin_seg5_anims_05008B74),
    ANIMATE(3),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 0, /*Unused*/ 0, 0),
    SET_HOME(),
    SET_INTERACT_TYPE(INTERACT_TEXT),
    SET_HITBOX(/*Radius*/ 200, /*Height*/ 300),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tuxies_mother_loop),
    END_LOOP(),
};

const BehaviorScript bhvPenguinBaby[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    BREAK(),
};

const BehaviorScript bhvUnused20E0[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    BREAK(),
};

const BehaviorScript bhvSmallPenguin[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_HOLDABLE | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    LOAD_ANIMATIONS(oAnimations, penguin_seg5_anims_05008B74),
    ANIMATE(0),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_INT(oInteractType, INTERACT_GRABBABLE),
    SET_INT(oInteractionSubtype, INT_SUBTYPE_HOLDABLE_NPC),
    SET_INT(oIntangibleTimer, 0),
    SET_HITBOX(/*Radius*/ 40, /*Height*/ 40),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_small_penguin_loop),
    END_LOOP(),
};

const BehaviorScript bhvManyBlueFishSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    SET_INT(oBehParams2ndByte, 0),
    GOTO(bhvFishSpawner + 1),
};

const BehaviorScript bhvFewBlueFishSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    SET_INT(oBehParams2ndByte, 1),
    GOTO(bhvFishSpawner + 1),
};

const BehaviorScript bhvFishSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    // Fish Spawner - common:
    DISABLE_RENDERING(),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_fish_spawner_loop),
    END_LOOP(),
};

const BehaviorScript bhvFish[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_fish_loop),
    END_LOOP(),
};

const BehaviorScript bhvWdwExpressElevator[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(wdw_seg7_collision_express_elevator_platform),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_wdw_express_elevator_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvWdwExpressElevatorPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(wdw_seg7_collision_express_elevator_platform),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvChirpChirp[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    SET_INT(oBirdChirpChirpUnkF4, 1),
    GOTO(bhvChirpChirpUnused),
};

const BehaviorScript bhvChirpChirpUnused[] = {
    DISABLE_RENDERING(),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bub_spawner_loop),
    END_LOOP(),
};

const BehaviorScript bhvBub[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, bub_seg6_anims_06012354),
    ANIMATE(0),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 20, /*Height*/ 10, /*Downwards offset*/ 10),
    SET_INTERACT_TYPE(INTERACT_DAMAGE),
    SET_INT(oDamageOrCoinValue, 1),
    SET_HOME(),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bub_loop),
    END_LOOP(),
};

const BehaviorScript bhvExclamationBox[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(exclamation_box_outline_seg8_collision_08025F78),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_FLOAT(oCollisionDistance, 300),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_exclamation_box_loop),
    END_LOOP(),
};

const BehaviorScript bhvRotatingExclamationMark[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SCALE(/*Unused*/ 0, /*Field*/ 200),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_rotating_exclamation_box_loop),
        ADD_INT(oMoveAngleYaw, 0x800),
    END_LOOP(),
};

const BehaviorScript bhvSoundSpawner[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    DELAY(3),
    CALL_NATIVE(bhv_sound_spawner_init),
    DELAY(30),
    DEACTIVATE(),
};

const BehaviorScript bhvRockSolid[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(jrb_seg7_collision_rock_solid),
    BEGIN_LOOP(),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvBowserSubDoor[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(ddd_seg7_collision_bowser_sub_door),
    SET_FLOAT(oDrawingDistance, 20000),
    SET_FLOAT(oCollisionDistance, 20000),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bowsers_sub_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvBowsersSub[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_FLOAT(oDrawingDistance, 20000),
    SET_FLOAT(oCollisionDistance, 20000),
    LOAD_COLLISION_DATA(ddd_seg7_collision_submarine),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bowsers_sub_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvSushiShark[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, sushi_seg5_anims_0500AE54),
    SPAWN_OBJ(/*Model*/ MODEL_NONE, /*Behavior*/ bhvSushiSharkCollisionChild),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 100, /*Height*/ 50, /*Downwards offset*/ 50),
    SET_INTERACT_TYPE(INTERACT_DAMAGE),
    SET_INT(oDamageOrCoinValue, 3),
    SET_HOME(),
    ANIMATE(0),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_sushi_shark_loop),
    END_LOOP(),
};

const BehaviorScript bhvSushiSharkCollisionChild[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    DISABLE_RENDERING(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_sushi_shark_collision_loop),
    END_LOOP(),
};

const BehaviorScript bhvJrbSlidingBox[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(jrb_seg7_collision_floating_box),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_jrb_sliding_box_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvShipPart3[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ship_part_3_loop),
    END_LOOP(),
};

const BehaviorScript bhvInSunkenShip3[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(jrb_seg7_collision_in_sunken_ship_3),
    SET_HOME(),
    SET_FLOAT(oCollisionDistance, 4000),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ship_part_3_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvSunkenShipPart[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SCALE(/*Unused*/ 0, /*Field*/ 50),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_sunken_ship_part_loop),
    END_LOOP(),
};

const BehaviorScript bhvSunkenShipSetRotation[] = {
    SET_INT(oFaceAnglePitch, 0xE958),
    SET_INT(oFaceAngleYaw, 0xEE6C),
    SET_INT(oFaceAngleRoll, 0x0C80),
    RETURN(),
};

const BehaviorScript bhvSunkenShipPart2[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SCALE(/*Unused*/ 0, /*Field*/ 100),
    SET_FLOAT(oDrawingDistance, 6000),
    SET_HOME(),
    CALL(bhvSunkenShipSetRotation),
    BREAK(),
};

const BehaviorScript bhvInSunkenShip[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(jrb_seg7_collision_in_sunken_ship),
    GOTO(bhvInSunkenShip2 + 1 + 2),
};

const BehaviorScript bhvInSunkenShip2[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(jrb_seg7_collision_in_sunken_ship_2),
    // Sunken ship - common:
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_FLOAT(oCollisionDistance, 4000),
    CALL(bhvSunkenShipSetRotation),
    BEGIN_LOOP(),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvMistParticleSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    PARENT_BIT_CLEAR(oActiveParticleFlags, ACTIVE_PARTICLE_DUST),
    DISABLE_RENDERING(),
    SPAWN_CHILD(/*Model*/ MODEL_MIST, /*Behavior*/ bhvWhitePuff1),
    SPAWN_CHILD(/*Model*/ MODEL_SMOKE, /*Behavior*/ bhvWhitePuff2),
    DELAY(1),
    DEACTIVATE(),
};

const BehaviorScript bhvWhitePuff1[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    PARENT_BIT_CLEAR(oActiveParticleFlags, ACTIVE_PARTICLE_DUST),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_white_puff_1_loop),
    END_LOOP(),
};

const BehaviorScript bhvWhitePuff2[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, (OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_INT(oAnimState, -1),
    BEGIN_REPEAT(7),
        CALL_NATIVE(bhv_white_puff_2_loop),
        ADD_INT(oAnimState, 1),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvWhitePuffSmoke2[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_INT(oAnimState, -1),
    BEGIN_REPEAT(7),
        CALL_NATIVE(bhv_white_puff_2_loop),
        CALL_NATIVE(cur_obj_move_using_fvel_and_gravity),
        ADD_INT(oAnimState, 1),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvPurpleSwitchHiddenBoxes[] = {
    BEGIN(OBJ_LIST_SURFACE),
    SET_INT(oBehParams2ndByte, 2),
    GOTO(bhvFloorSwitchHardcodedModel + 1),
};

const BehaviorScript bhvBlueCoinSwitch[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(blue_coin_switch_seg8_collision_08000E98),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_blue_coin_switch_loop),
    END_LOOP(),
};

const BehaviorScript bhvHiddenBlueCoin[] = {
    BEGIN(OBJ_LIST_LEVEL),
    SET_INT(oInteractType, INTERACT_COIN),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 64),
    SET_INT(oDamageOrCoinValue, 5),
    SET_INT(oIntangibleTimer, 0),
    SET_INT(oAnimState, -1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_hidden_blue_coin_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvOpenableCageDoor[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_openable_cage_door_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvOpenableGrill[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_openable_grill_loop),
    END_LOOP(),
};

const BehaviorScript bhvWaterLevelDiamond[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HITBOX(/*Radius*/ 70, /*Height*/ 30),
    SET_FLOAT(oCollisionDistance, 200),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_water_level_diamond_loop),
    END_LOOP(),
};

const BehaviorScript bhvInitializeChangingWaterLevel[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_init_changing_water_level_loop),
    END_LOOP(),
};

const BehaviorScript bhvTweesterSandParticle[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, (OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tweester_sand_particle_loop),
    END_LOOP(),
};

const BehaviorScript bhvTweester[] = {
    BEGIN(OBJ_LIST_POLELIKE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ 0, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    DROP_TO_FLOOR(),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_tweester_loop),
    END_LOOP(),
};

const BehaviorScript bhvMerryGoRoundBooManager[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_merry_go_round_boo_manager_loop),
    END_LOOP(),
};

const BehaviorScript bhvAnimatedTexture[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -70, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_animated_texture_loop),
        ADD_INT(oAnimState, 1),
        ANIMATE_TEXTURE(oAnimState, 2),
    END_LOOP(),
};

const BehaviorScript bhvBooInCastle[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SET_FLOAT(oGraphYOffset, 60),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ 0, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_boo_in_castle_loop),
    END_LOOP(),
};

const BehaviorScript bhvBooWithCage[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SET_INT(oDamageOrCoinValue, 3),
    SET_HURTBOX(/*Radius*/ 80, /*Height*/ 120),
    SET_HITBOX(/*Radius*/ 180, /*Height*/ 140),
    SET_FLOAT(oGraphYOffset, 60),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ 0, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_boo_with_cage_init),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_boo_with_cage_loop),
    END_LOOP(),
};

const BehaviorScript bhvBalconyBigBoo[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    SET_INT(oBehParams2ndByte, 2),
    SET_INT(oBigBooNumMinionBoosKilled, 10),
    GOTO(bhvGhostHuntBigBoo + 1),
};

const BehaviorScript bhvMerryGoRoundBigBoo[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    SET_INT(oBehParams2ndByte, 1),
    // Set number of minion boos killed to 10, which is greater than 5 so that the boo always loads without needing to kill any boos.
    SET_INT(oBigBooNumMinionBoosKilled, 10),
    GOTO(bhvGhostHuntBigBoo + 1),
};

const BehaviorScript bhvGhostHuntBigBoo[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    // Big boo - common:
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ 0, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_init_room),
    CALL_NATIVE(bhv_boo_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_big_boo_loop),
    END_LOOP(),
};

const BehaviorScript bhvCourtyardBooTriplet[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    DISABLE_RENDERING(),
    CALL_NATIVE(bhv_courtyard_boo_triplet_init),
    DEACTIVATE(),
};

const BehaviorScript bhvBoo[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    SET_INT(oBehParams2ndByte, 1),
    GOTO(bhvGhostHuntBoo + 1),
};

const BehaviorScript bhvMerryGoRoundBoo[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    SET_INT(oBehParams2ndByte, 2),
    GOTO(bhvGhostHuntBoo + 1),
};

const BehaviorScript bhvGhostHuntBoo[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    // Boo - common:
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_INT(oIntangibleTimer, 0),
    SET_HOME(),
    SET_INT(oDamageOrCoinValue, 2),
    SET_HITBOX(/*Radius*/ 140, /*Height*/ 80),
    SET_HURTBOX(/*Radius*/ 40, /*Height*/ 60),
    SET_FLOAT(oGraphYOffset, 30),
    CALL_NATIVE(bhv_init_room),
    SPAWN_CHILD(/*Model*/ MODEL_YELLOW_COIN, /*Behavior*/ bhvCoinInsideBoo),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ 0, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_boo_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_boo_loop),
    END_LOOP(),
};

const BehaviorScript bhvHiddenStaircaseStep[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(bbh_seg7_collision_staircase_step),
    SET_INT(oRoom, 1),
    SET_FLOAT(oCollisionDistance, 1000),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvBooStaircase[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(bbh_seg7_collision_staircase_step),
    SET_INT(oRoom, 1),
    SET_FLOAT(oCollisionDistance, 1000),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_boo_staircase),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvBbhTiltingTrapPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(bbh_seg7_collision_tilt_floor_platform),
    SET_HOME(),
    SET_INT(oRoom, 2),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bbh_tilting_trap_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvHauntedBookshelf[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(bbh_seg7_collision_haunted_bookshelf),
    SET_HOME(),
    SET_INT(oRoom, 6),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_haunted_bookshelf_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvMeshElevator[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(bbh_seg7_collision_mesh_elevator),
    SET_HOME(),
    SET_INT(oRoom, 12),
    SET_INT(oBehParams2ndByte, 4),
    CALL_NATIVE(bhv_elevator_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_elevator_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvMerryGoRound[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(bbh_seg7_collision_merry_go_round),
    SET_FLOAT(oCollisionDistance, 2000),
    SET_INT(oRoom, 10),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_merry_go_round_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

#ifndef VERSION_JP
const BehaviorScript bhvPlaysMusicTrackWhenTouched[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_play_music_track_when_touched_loop),
    END_LOOP(),
};
#endif

const BehaviorScript bhvInsideCannon[] = {
    BREAK(),
};

const BehaviorScript bhvBetaBowserAnchor[] = {
    BEGIN(OBJ_LIST_DESTRUCTIVE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_HOME(),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 300),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        ADD_INT(oAnimState, 1),
        CALL_NATIVE(bhv_beta_bowser_anchor_loop),
    END_LOOP(),
};

const BehaviorScript bhvStaticCheckeredPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(checkerboard_platform_seg8_collision_0800D710),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_static_checkered_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvUnused2A10[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BILLBOARD(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BREAK(),
};

const BehaviorScript bhvUnusedFakeStar[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_LOOP(),
        ADD_INT(oFaceAnglePitch, 0x100),
        ADD_INT(oFaceAngleYaw, 0x100),
    END_LOOP(),
};

// What is this?
UNUSED static const BehaviorScript unused_1[] = {
    BREAK(),
    BREAK(),
    BREAK(),
    BREAK(),
};

const BehaviorScript bhvStaticObject[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BREAK(),
};

const BehaviorScript bhvUnused2A54[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BREAK(),
};

const BehaviorScript bhvCastleFloorTrap[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    DISABLE_RENDERING(),
    CALL_NATIVE(bhv_castle_floor_trap_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_castle_floor_trap_loop),
    END_LOOP(),
};

const BehaviorScript bhvFloorTrapInCastle[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(inside_castle_seg7_collision_floor_trap),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_floor_trap_in_castle_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvTree[] = {
    BEGIN(OBJ_LIST_POLELIKE),
    BILLBOARD(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INT(oInteractType, INTERACT_POLE),
    SET_HITBOX(/*Radius*/ 80, /*Height*/ 500),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_pole_base_loop),
    END_LOOP(),
};

const BehaviorScript bhvSparkle[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    BILLBOARD(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INT(oAnimState, -1),
    BEGIN_REPEAT(9),
        ADD_INT(oAnimState, 1),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvSparkleSpawn[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_sparkle_spawn_loop),
    END_LOOP(),
};

const BehaviorScript bhvSparkleParticleSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    PARENT_BIT_CLEAR(oActiveParticleFlags, ACTIVE_PARTICLE_SPARKLES),
    BEGIN(OBJ_LIST_UNIMPORTANT),
    BILLBOARD(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_FLOAT(oGraphYOffset, 25),
    SET_RANDOM_FLOAT(oMarioParticleFlags, /*Minimum*/ -50, /*Range*/ 100),
    SUM_FLOAT(/*Dest*/ oPosX, /*Value 1*/ oPosX, /*Value 2*/ oMarioParticleFlags),
    SET_RANDOM_FLOAT(oMarioParticleFlags, /*Minimum*/ -50, /*Range*/ 100),
    SUM_FLOAT(/*Dest*/ oPosZ, /*Value 1*/ oPosZ, /*Value 2*/ oMarioParticleFlags),
    SET_RANDOM_FLOAT(oMarioParticleFlags, /*Minimum*/ -50, /*Range*/ 100),
    SUM_FLOAT(/*Dest*/ oPosY, /*Value 1*/ oPosY, /*Value 2*/ oMarioParticleFlags),
    SET_INT(oAnimState, -1),
    BEGIN_REPEAT(12),
        ADD_INT(oAnimState, 1),
    END_REPEAT(),
    DEACTIVATE(),
};

const BehaviorScript bhvScuttlebug[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, scuttlebug_seg6_anims_06015064),
    ANIMATE(0),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 80, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_HOME(),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_scuttlebug_loop),
    END_LOOP(),
};

const BehaviorScript bhvScuttlebugSpawn[] = {
    BEGIN(OBJ_LIST_SPAWNER),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_scuttlebug_spawn_loop),
    END_LOOP(),
};

const BehaviorScript bhvWhompKingBoss[] = {
    BEGIN(OBJ_LIST_SURFACE),
    SET_INT(oBehParams2ndByte, 1),
    SET_INT(oHealth, 3),
    GOTO(bhvSmallWhomp + 1 + 1),
};

const BehaviorScript bhvSmallWhomp[] = {
    BEGIN(OBJ_LIST_SURFACE),
    SET_INT(oNumLootCoins, 5),
    // Whomp - common:
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, whomp_seg6_anims_06020A04),
    LOAD_COLLISION_DATA(whomp_seg6_collision_06020A0C),
    ANIMATE(0),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 0, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_whomp_loop),
    END_LOOP(),
};

// The large splash Mario makes when he jumps into a pool of water.
const BehaviorScript bhvWaterSplash[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_INT(oAnimState, -1),
    BEGIN_REPEAT(3),
        ADD_INT(oAnimState, 1),
        CALL_NATIVE(bhv_water_splash_spawn_droplets),
        DELAY(1),
        CALL_NATIVE(bhv_water_splash_spawn_droplets),
    END_REPEAT(),
    BEGIN_REPEAT(5),
        ADD_INT(oAnimState, 1),
        DELAY(1),
    END_REPEAT(),
    PARENT_BIT_CLEAR(oActiveParticleFlags, ACTIVE_PARTICLE_WATER_SPLASH),
    DEACTIVATE(),
};

// Droplets of water that spawn as a result of various water splashes.
const BehaviorScript bhvWaterDroplet[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_water_droplet_loop),
    END_LOOP(),
};

// Small splashes that are seen when a water droplet lands back into the water.
const BehaviorScript bhvWaterDropletSplash[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
#ifndef VERSION_JP
    SET_INT(oFaceAnglePitch, 0),
    SET_INT(oFaceAngleYaw, 0),
    SET_INT(oFaceAngleRoll, 0),
#endif
    CALL_NATIVE(bhv_water_droplet_splash_init),
    ADD_FLOAT(oPosY, 5),
    SET_INT(oAnimState, -1),
    BEGIN_REPEAT(6),
        ADD_INT(oAnimState, 1),
    END_REPEAT(),
    DEACTIVATE(),
};

// The splash created when an air bubble hits the surface of the water.
const BehaviorScript bhvBubbleSplash[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
#ifdef VERSION_JP
    SET_FLOAT(oFaceAnglePitch, 0),
    SET_FLOAT(oFaceAngleYaw, 0),
    SET_FLOAT(oFaceAngleRoll, 0),
#endif
#ifndef VERSION_JP
    SET_INT(oFaceAnglePitch, 0),
    SET_INT(oFaceAngleYaw, 0),
    SET_INT(oFaceAngleRoll, 0),
#endif
    SET_INT(oAnimState, -1),
    CALL_NATIVE(bhv_bubble_splash_init),
    BEGIN_REPEAT(6),
        ADD_INT(oAnimState, 1),
    END_REPEAT(),
    DEACTIVATE(),
};

// The water wave surrounding Mario when he is idle in a pool of water.
const BehaviorScript bhvIdleWaterWave[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
#ifdef VERSION_JP
    SET_FLOAT(oFaceAnglePitch, 0),
    SET_FLOAT(oFaceAngleYaw, 0),
    SET_FLOAT(oFaceAngleRoll, 0),
#endif
#ifndef VERSION_JP
    SET_INT(oFaceAnglePitch, 0),
    SET_INT(oFaceAngleYaw, 0),
    SET_INT(oFaceAngleRoll, 0),
#endif
    SET_INT(oAnimState, -1),
    ADD_INT(oAnimState, 1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_idle_water_wave_loop),
        ADD_INT(oAnimState, 1),
        BEGIN_REPEAT(6),
            CALL_NATIVE(bhv_idle_water_wave_loop),
        END_REPEAT(),
        CALL_NATIVE(bhv_idle_water_wave_loop),
    END_LOOP(),
};

// Water splashes similar to the splashes created by water droplets, but are created by other objects.
// Unlike water droplet splashes, they are unimportant objects.
const BehaviorScript bhvObjectWaterSplash[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
#ifdef VERSION_JP
    SET_FLOAT(oFaceAnglePitch, 0),
    SET_FLOAT(oFaceAngleYaw, 0),
    SET_FLOAT(oFaceAngleRoll, 0),
#endif
#ifndef VERSION_JP
    SET_INT(oFaceAnglePitch, 0),
    SET_INT(oFaceAngleYaw, 0),
    SET_INT(oFaceAngleRoll, 0),
#endif
    SET_INT(oAnimState, -1),
    BEGIN_REPEAT(6),
        ADD_INT(oAnimState, 1),
    END_REPEAT(),
    DEACTIVATE(),
};

// Waves that are generated when running in shallow water.
const BehaviorScript bhvShallowWaterWave[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    DISABLE_RENDERING(),
    BEGIN_REPEAT(5),
        SPAWN_WATER_DROPLET(&gShallowWaterWaveDropletParams),
    END_REPEAT_CONTINUE(),
    DELAY(1),
    PARENT_BIT_CLEAR(oActiveParticleFlags, ACTIVE_PARTICLE_SHALLOW_WATER_WAVE),
    DEACTIVATE(),
};

// A small water splash that occurs when jumping in and out of shallow water.
// Unlike the larger water splash it has no visible model of its own.
// It has a 1 in 256 chance of spawning the fish particle easter egg.
const BehaviorScript bhvShallowWaterSplash[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    DISABLE_RENDERING(),
    BEGIN_REPEAT(18),
        SPAWN_WATER_DROPLET(&gShallowWaterSplashDropletParams),
    END_REPEAT_CONTINUE(),
    CALL_NATIVE(bhv_shallow_water_splash_init),
    DELAY(1),
    PARENT_BIT_CLEAR(oActiveParticleFlags, ACTIVE_PARTICLE_SHALLOW_WATER_SPLASH),
    DEACTIVATE(),
};

// Waves created by other objects along the water's surface, specifically the koopa shell and Sushi.
// Unlike Mario's waves, they are unimportant objects.
const BehaviorScript bhvObjectWaveTrail[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    GOTO(bhvWaveTrail + 1 + 1 + 2), // Wave trail - common
};

// The waves created by Mario while he is swimming.
const BehaviorScript bhvWaveTrail[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    PARENT_BIT_CLEAR(oActiveParticleFlags, ACTIVE_PARTICLE_WAVE_TRAIL),
    // Wave trail - common:
    SET_FLOAT(oFaceAnglePitch, 0),
    SET_FLOAT(oFaceAngleYaw, 0),
    SET_FLOAT(oFaceAngleRoll, 0),
    SET_INT(oAnimState, -1),
    BEGIN_REPEAT(8),
        ADD_INT(oAnimState, 1),
        CALL_NATIVE(bhv_wave_trail_shrink),
        DELAY(1),
        CALL_NATIVE(bhv_wave_trail_shrink),
    END_REPEAT(),
    DEACTIVATE(),
};

// Tiny wind particles that provide aesthetics to the strong winds generated by the Snowman and Fwoosh.
// As they are unimportant objects, they don't have collision with Mario.
const BehaviorScript bhvTinyStrongWindParticle[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_strong_wind_particle_loop),
    END_LOOP(),
};

// Strong wind particles generated by the Snowman and Fwoosh that blow Mario back and knock his cap off.
const BehaviorScript bhvStrongWindParticle[] = {
    BEGIN(OBJ_LIST_POLELIKE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_strong_wind_particle_loop),
    END_LOOP(),
};

// The handler for the strong wind blown by the Snowman in SL. Triggers dialog and then aims towards Mario.
const BehaviorScript bhvSLSnowmanWind[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_sl_snowman_wind_loop),
    END_LOOP(),
};

// The penguin that walks erratically along the ice bridge in front of the Snowman in SL.
// Blocks strong wind particles, allowing Mario to walk behind it.
const BehaviorScript bhvSLWalkingPenguin[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(penguin_seg5_collision_05008B88),
    LOAD_ANIMATIONS(oAnimations, penguin_seg5_anims_05008B74),
    ANIMATE(0),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 0, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SCALE(/*Unused*/ 0, /*Field*/ 600),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_sl_walking_penguin_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvYellowBall[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    BREAK(),
};

UNUSED static const u64 behavior_data_unused_0 = 0;
const BehaviorScript bhvMario[] = {
    BEGIN(OBJ_LIST_PLAYER),
    SET_INT(oIntangibleTimer, 0),
    OR_INT(oFlags, OBJ_FLAG_0100),
    OR_INT(oUnk94, 0x0001),
    SET_HITBOX(/*Radius*/ 37, /*Height*/ 160),
    BEGIN_LOOP(),
        CALL_NATIVE(try_print_debug_mario_level_info),
        CALL_NATIVE(bhv_mario_update),
        CALL_NATIVE(try_do_mario_debug_object_spawn),
    END_LOOP(),
};

const BehaviorScript bhvToadMessage[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_PERSISTENT_RESPAWN | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, toad_seg6_anims_0600FB58),
    ANIMATE(6),
    SET_INTERACT_TYPE(INTERACT_TEXT),
    SET_HITBOX(/*Radius*/ 80, /*Height*/ 100),
    SET_INT(oIntangibleTimer, 0),
    CALL_NATIVE(bhv_init_room),
    CALL_NATIVE(bhv_toad_message_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_toad_message_loop),
    END_LOOP(),
};

const BehaviorScript bhvUnlockDoorStar[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_unlock_door_star_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_unlock_door_star_loop),
    END_LOOP(),
};

const BehaviorScript bhvInstantActiveWarp[] = {
    BREAK(),
};

const BehaviorScript bhvAirborneWarp[] = {
    BREAK(),
};

const BehaviorScript bhvHardAirKnockBackWarp[] = {
    BREAK(),
};

const BehaviorScript bhvSpinAirborneCircleWarp[] = {
    BREAK(),
};

const BehaviorScript bhvDeathWarp[] = {
    BREAK(),
};

const BehaviorScript bhvSpinAirborneWarp[] = {
    BREAK(),
};

const BehaviorScript bhvFlyingWarp[] = {
    BREAK(),
};

const BehaviorScript bhvPaintingStarCollectWarp[] = {
    BREAK(),
};

const BehaviorScript bhvPaintingDeathWarp[] = {
    BREAK(),
};

const BehaviorScript bhvAirborneDeathWarp[] = {
    BREAK(),
};

const BehaviorScript bhvAirborneStarCollectWarp[] = {
    BREAK(),
};

const BehaviorScript bhvLaunchStarCollectWarp[] = {
    BREAK(),
};

const BehaviorScript bhvLaunchDeathWarp[] = {
    BREAK(),
};

const BehaviorScript bhvSwimmingWarp[] = {
    BREAK(),
};

UNUSED static const u64 behavior_data_unused_1 = 0;
const BehaviorScript bhvRandomAnimatedTexture[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_FLOAT(oGraphYOffset, -16),
    BILLBOARD(),
    SET_INT(oAnimState, -1),
    BEGIN_LOOP(),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvYellowBackgroundInMenu[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(beh_yellow_background_menu_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(beh_yellow_background_menu_loop),
    END_LOOP(),
};

const BehaviorScript bhvMenuButton[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_menu_button_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_menu_button_loop),
    END_LOOP(),
};

const BehaviorScript bhvMenuButtonManager[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_SET_THROW_MATRIX_FROM_TRANSFORM | OBJ_FLAG_0020 | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_menu_button_manager_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_menu_button_manager_loop),
    END_LOOP(),
};

const BehaviorScript bhvActSelectorStarType[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_act_selector_star_type_loop),
    END_LOOP(),
};

const BehaviorScript bhvActSelector[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_act_selector_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_act_selector_loop),
    END_LOOP(),
};

const BehaviorScript bhvMovingYellowCoin[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 64),
    SET_INT(oInteractType, INTERACT_COIN),
    SET_INT(oIntangibleTimer, 0),
    SET_INT(oAnimState, -1),
    CALL_NATIVE(bhv_moving_yellow_coin_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_moving_yellow_coin_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvMovingBlueCoin[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_INT(oIntangibleTimer, 0),
    SET_INT(oAnimState, -1),
    CALL_NATIVE(bhv_moving_blue_coin_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_moving_blue_coin_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvBlueCoinSliding[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_INT(oIntangibleTimer, 0),
    SET_INT(oAnimState, -1),
    CALL_NATIVE(bhv_blue_coin_sliding_jumping_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_blue_coin_sliding_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvBlueCoinJumping[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_INT(oIntangibleTimer, 0),
    SET_INT(oAnimState, -1),
    CALL_NATIVE(bhv_blue_coin_sliding_jumping_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_blue_coin_jumping_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvSeaweed[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_ANIMATIONS(oAnimations, seaweed_seg6_anims_0600A4D4),
    ANIMATE(0),
    CALL_NATIVE(bhv_seaweed_init),
    BEGIN_LOOP(),
    END_LOOP(),
};

const BehaviorScript bhvSeaweedBundle[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    DROP_TO_FLOOR(),
    CALL_NATIVE(bhv_seaweed_bundle_init),
    BEGIN_LOOP(),
    END_LOOP(),
};

const BehaviorScript bhvBobomb[] = {
    BEGIN(OBJ_LIST_DESTRUCTIVE),
    OR_INT(oFlags, (OBJ_FLAG_PERSISTENT_RESPAWN | OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_HOLDABLE | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, bobomb_seg8_anims_0802396C),
    DROP_TO_FLOOR(),
    ANIMATE(0),
    SET_INT(oIntangibleTimer, 0),
    SET_HOME(),
    CALL_NATIVE(bhv_bobomb_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bobomb_loop),
    END_LOOP(),
};

const BehaviorScript bhvBobombFuseSmoke[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_INT(oAnimState, -1),
    CALL_NATIVE(bhv_bobomb_fuse_smoke_init),
    DELAY(1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_dust_smoke_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvBobombBuddy[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_HOLDABLE | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, bobomb_seg8_anims_0802396C),
    SET_INTERACT_TYPE(INTERACT_TEXT),
    DROP_TO_FLOOR(),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 60),
    ANIMATE(0),
    SET_INT(oBobombBuddyRole, 0),
    SET_HOME(),
    CALL_NATIVE(bhv_bobomb_buddy_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_bobomb_buddy_loop),
    END_LOOP(),
};

// The only difference between this and the previous behavior are what oFlags and oBobombBuddyRole are set to, why didn't they just use a jump?
const BehaviorScript bhvBobombBuddyOpensCannon[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_PERSISTENT_RESPAWN | OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_HOLDABLE | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, bobomb_seg8_anims_0802396C),
    SET_INTERACT_TYPE(INTERACT_TEXT),
    DROP_TO_FLOOR(),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 60),
    ANIMATE(0),
    SET_INT(oBobombBuddyRole, 1),
    SET_HOME(),
    CALL_NATIVE(bhv_bobomb_buddy_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_bobomb_buddy_loop),
    END_LOOP(),
};

const BehaviorScript bhvCannonClosed[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_PERSISTENT_RESPAWN | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(cannon_lid_seg8_collision_08004950),
    SET_HOME(),
    CALL_NATIVE(bhv_cannon_closed_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_cannon_closed_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvWhirlpool[] = {
    BEGIN(OBJ_LIST_POLELIKE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_whirlpool_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_whirlpool_loop),
    END_LOOP(),
};

const BehaviorScript bhvJetStream[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_jet_stream_loop),
    END_LOOP(),
};

const BehaviorScript bhvMessagePanel[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(wooden_signpost_seg3_collision_0302DD80),
    SET_INTERACT_TYPE(INTERACT_TEXT),
    SET_INT(oInteractionSubtype, INT_SUBTYPE_SIGN),
    DROP_TO_FLOOR(),
    SET_HITBOX(/*Radius*/ 150, /*Height*/ 80),
    SET_INT(oWoodenPostTotalMarioAngle, 0),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(load_object_collision_model),
        SET_INT(oInteractStatus, 0),
    END_LOOP(),
};

const BehaviorScript bhvSignOnWall[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INTERACT_TYPE(INTERACT_TEXT),
    SET_INT(oInteractionSubtype, INT_SUBTYPE_SIGN),
    SET_HITBOX(/*Radius*/ 150, /*Height*/ 80),
    SET_INT(oWoodenPostTotalMarioAngle, 0),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        SET_INT(oInteractStatus, 0),
    END_LOOP(),
};

const BehaviorScript bhvHomingAmp[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, dAmpAnimsList),
    ANIMATE(0),
    SET_FLOAT(oGraphYOffset, 40),
    SET_INT(oIntangibleTimer, 0),
    CALL_NATIVE(bhv_homing_amp_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_homing_amp_loop),
    END_LOOP(),
};

const BehaviorScript bhvCirclingAmp[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, dAmpAnimsList),
    ANIMATE(0),
    SET_FLOAT(oGraphYOffset, 40),
    SET_INT(oIntangibleTimer, 0),
    CALL_NATIVE(bhv_circling_amp_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_circling_amp_loop),
    END_LOOP(),
};

const BehaviorScript bhvButterfly[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, butterfly_seg3_anims_030056B0),
    DROP_TO_FLOOR(),
    SET_FLOAT(oGraphYOffset, 5),
    CALL_NATIVE(bhv_butterfly_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_butterfly_loop),
    END_LOOP(),
};

const BehaviorScript bhvHoot[] = {
    BEGIN(OBJ_LIST_POLELIKE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, hoot_seg5_anims_05005768),
    SET_INT(oInteractType, INTERACT_HOOT),
    SET_HITBOX(/*Radius*/ 75, /*Height*/ 75),
    CALL_NATIVE(bhv_hoot_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_hoot_loop),
    END_LOOP(),
};

const BehaviorScript bhvBetaHoldableObject[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_HOLDABLE | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_INT(oInteractType, INTERACT_GRABBABLE),
    DROP_TO_FLOOR(),
    SET_HITBOX(/*Radius*/ 40, /*Height*/ 50),
    CALL_NATIVE(bhv_beta_holdable_object_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_beta_holdable_object_loop),
    END_LOOP(),
};

const BehaviorScript bhvCarrySomething1[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BREAK(),
};

const BehaviorScript bhvCarrySomething2[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BREAK(),
};

const BehaviorScript bhvCarrySomething3[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BREAK(),
};

const BehaviorScript bhvCarrySomething4[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BREAK(),
};

const BehaviorScript bhvCarrySomething5[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BREAK(),
};

const BehaviorScript bhvCarrySomething6[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BREAK(),
};

const BehaviorScript bhvObjectBubble[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, (OBJ_FLAG_MOVE_Y_WITH_TERMINAL_VEL | OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INT(oAnimState, -1),
    CALL_NATIVE(bhv_object_bubble_init),
    SET_RANDOM_FLOAT(oVelY, /*Minimum*/ 3, /*Range*/ 6),
    SET_INT_RAND_RSHIFT(oMoveAngleYaw, /*Minimum*/ 0, /*Right shift*/ 0),
    DELAY(1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_object_bubble_loop),
    END_LOOP(),
};

const BehaviorScript bhvObjectWaterWave[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_FLOAT(oFaceAnglePitch, 0),
    SET_FLOAT(oFaceAngleYaw, 0),
    SET_FLOAT(oFaceAngleRoll, 0),
    SET_INT(oAnimState, -1),
    CALL_NATIVE(bhv_object_water_wave_init),
    ADD_INT(oAnimState, 1),
    DELAY(6),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_object_water_wave_loop),
        ADD_INT(oAnimState, 1),
    BEGIN_REPEAT(6),
        CALL_NATIVE(bhv_object_water_wave_loop),
    END_REPEAT(),
    END_LOOP(),
};

const BehaviorScript bhvExplosion[] = {
    BEGIN(OBJ_LIST_DESTRUCTIVE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_INTERACT_TYPE(INTERACT_DAMAGE),
    SET_INT(oDamageOrCoinValue, 2),
    SET_INT(oIntangibleTimer, 0),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 150, /*Height*/ 150, /*Downwards offset*/ 150),
    SET_INT(oAnimState, -1),
    CALL_NATIVE(bhv_explosion_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_explosion_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvBobombBullyDeathSmoke[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, (OBJ_FLAG_MOVE_Y_WITH_TERMINAL_VEL | OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_INT(oAnimState, -1),
    CALL_NATIVE(bhv_bobomb_bully_death_smoke_init),
    DELAY(1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_dust_smoke_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvSmoke[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, (OBJ_FLAG_MOVE_Y_WITH_TERMINAL_VEL | OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_INT(oAnimState, -1),
    DELAY(1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_dust_smoke_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvBobombExplosionBubble[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BILLBOARD(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_bobomb_explosion_bubble_init),
    ADD_RANDOM_FLOAT(oPosX, /*Minimum*/ -50, /*Range*/ 100),
    ADD_RANDOM_FLOAT(oPosY, /*Minimum*/ -50, /*Range*/ 100),
    ADD_RANDOM_FLOAT(oPosZ, /*Minimum*/ -50, /*Range*/ 100),
    CALL(bhvBobombExplosionBubble3600),
    DELAY(1),
    BEGIN_LOOP(),
        CALL(bhvBobombExplosionBubble3600),
        CALL_NATIVE(bhv_bobomb_explosion_bubble_loop),
    END_LOOP(),
};

const BehaviorScript bhvBobombExplosionBubble3600[] = {
    ADD_RANDOM_FLOAT(oPosX, /*Minimum*/ -2, /*Range*/ 4),
    ADD_RANDOM_FLOAT(oPosZ, /*Minimum*/ -2, /*Range*/ 4),
    RETURN(),
};

const BehaviorScript bhvRespawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_respawner_loop),
    END_LOOP(),
};

const BehaviorScript bhvSmallBully[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, bully_seg5_anims_0500470C),
    DROP_TO_FLOOR(),
    SET_HOME(),
    CALL_NATIVE(bhv_small_bully_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_bully_loop),
    END_LOOP(),
};

const BehaviorScript bhvBigBully[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, bully_seg5_anims_0500470C),
    DROP_TO_FLOOR(),
    SET_HOME(),
    CALL_NATIVE(bhv_big_bully_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_bully_loop),
    END_LOOP(),
};

const BehaviorScript bhvBigBullyWithMinions[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, bully_seg5_anims_0500470C),
    SET_HOME(),
    CALL_NATIVE(bhv_big_bully_init),
    CALL_NATIVE(bhv_big_bully_with_minions_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_big_bully_with_minions_loop),
    END_LOOP(),
};

const BehaviorScript bhvSmallChillBully[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, chilly_chief_seg6_anims_06003994),
    DROP_TO_FLOOR(),
    SET_HOME(),
    SET_INT(oBullySubtype, 0x0010),
    CALL_NATIVE(bhv_small_bully_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_bully_loop),
    END_LOOP(),
};

const BehaviorScript bhvBigChillBully[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, chilly_chief_seg6_anims_06003994),
    DROP_TO_FLOOR(),
    SET_HOME(),
    SET_INT(oBullySubtype, 0x0010),
    CALL_NATIVE(bhv_big_bully_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_bully_loop),
    END_LOOP(),
};

const BehaviorScript bhvJetStreamRingSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    HIDE(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_jet_stream_ring_spawner_loop),
    END_LOOP(),
};

const BehaviorScript bhvJetStreamWaterRing[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_ANIMATIONS(oAnimations, water_ring_seg6_anims_06013F7C),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 75, /*Height*/ 20, /*Downwards offset*/ 20),
    SET_INTERACT_TYPE(INTERACT_WATER_RING),
    SET_INT(oDamageOrCoinValue, 2),
    SET_INT(oIntangibleTimer, 0),
    CALL_NATIVE(bhv_jet_stream_water_ring_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_jet_stream_water_ring_loop),
    END_LOOP(),
};

const BehaviorScript bhvMantaRayWaterRing[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_ANIMATIONS(oAnimations, water_ring_seg6_anims_06013F7C),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 75, /*Height*/ 20, /*Downwards offset*/ 20),
    SET_INTERACT_TYPE(INTERACT_WATER_RING),
    SET_INT(oDamageOrCoinValue, 2),
    SET_INT(oIntangibleTimer, 0),
    CALL_NATIVE(bhv_manta_ray_water_ring_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_manta_ray_water_ring_loop),
    END_LOOP(),
};

const BehaviorScript bhvMantaRayRingManager[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BEGIN_LOOP(),
    END_LOOP(),
};

const BehaviorScript bhvBowserBomb[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INT(oIntangibleTimer, 0),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 40, /*Height*/ 40, /*Downwards offset*/ 40),
    DELAY(1),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_bowser_bomb_loop),
    END_LOOP(),
};

const BehaviorScript bhvBowserBombExplosion[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_FLOAT(oGraphYOffset, -288),
    SET_INT(oAnimState, -1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bowser_bomb_explosion_loop),
    END_LOOP(),
};

const BehaviorScript bhvBowserBombSmoke[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_FLOAT(oGraphYOffset, -288),
    SET_INT(oOpacity, 255),
    SET_INT(oAnimState, -1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bowser_bomb_smoke_loop),
    END_LOOP(),
};

const BehaviorScript bhvCelebrationStar[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_celebration_star_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_celebration_star_loop),
    END_LOOP(),
};

const BehaviorScript bhvCelebrationStarSparkle[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    BILLBOARD(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_FLOAT(oGraphYOffset, 25),
    SET_INT(oAnimState, -1),
    BEGIN_LOOP(),
        ADD_INT(oAnimState, 1),
        CALL_NATIVE(bhv_celebration_star_sparkle_loop),
    END_LOOP(),
};

const BehaviorScript bhvStarKeyCollectionPuffSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BILLBOARD(),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INT(oAnimState, -1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_star_key_collection_puff_spawner_loop),
    END_LOOP(),
};

const BehaviorScript bhvLllDrawbridgeSpawner[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    HIDE(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_lll_drawbridge_spawner_loop),
    END_LOOP(),
};

const BehaviorScript bhvLllDrawbridge[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(lll_seg7_collision_drawbridge),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_lll_drawbridge_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvSmallBomp[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(wf_seg7_collision_small_bomp),
    CALL_NATIVE(bhv_small_bomp_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_small_bomp_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvLargeBomp[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(wf_seg7_collision_large_bomp),
    CALL_NATIVE(bhv_large_bomp_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_large_bomp_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvWfSlidingPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_MOVE_XZ_USING_FVEL | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(wf_seg7_collision_sliding_brick_platform),
    CALL_NATIVE(bhv_wf_sliding_platform_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_wf_sliding_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvMoneybag[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, moneybag_seg6_anims_06005E5C),
    DROP_TO_FLOOR(),
    SET_HOME(),
    SET_INT(oIntangibleTimer, -1),
    CALL_NATIVE(bhv_moneybag_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_moneybag_loop),
    END_LOOP(),
};

const BehaviorScript bhvMoneybagHidden[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_FLOAT(oGraphYOffset, 27),
    BILLBOARD(),
    SET_HITBOX(/*Radius*/ 110, /*Height*/ 100),
    SET_INT(oIntangibleTimer, 0),
    SET_INT(oAnimState, -1),
    BEGIN_LOOP(),
        ADD_INT(oAnimState, 1),
        CALL_NATIVE(bhv_moneybag_hidden_loop),
    END_LOOP(),
};

const BehaviorScript bhvPitBowlingBall[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_FLOAT(oGraphYOffset, 130),
    CALL_NATIVE(bhv_bob_pit_bowling_ball_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bob_pit_bowling_ball_loop),
    END_LOOP(),
};

const BehaviorScript bhvFreeBowlingBall[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_FLOAT(oGraphYOffset, 130),
    CALL_NATIVE(bhv_free_bowling_ball_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_free_bowling_ball_loop),
    END_LOOP(),
};

const BehaviorScript bhvBowlingBall[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_FLOAT(oGraphYOffset, 130),
    CALL_NATIVE(bhv_bowling_ball_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bowling_ball_loop),
    END_LOOP(),
};

const BehaviorScript bhvTtmBowlingBallSpawner[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INT(oBBallSpawnerPeriodMinus1, 63),
    CALL_NATIVE(bhv_generic_bowling_ball_spawner_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_generic_bowling_ball_spawner_loop),
    END_LOOP(),
};

const BehaviorScript bhvBobBowlingBallSpawner[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INT(oBBallSpawnerPeriodMinus1, 127),
    CALL_NATIVE(bhv_generic_bowling_ball_spawner_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_generic_bowling_ball_spawner_loop),
    END_LOOP(),
};

const BehaviorScript bhvThiBowlingBallSpawner[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_thi_bowling_ball_spawner_loop),
    END_LOOP(),
};

const BehaviorScript bhvRrCruiserWing[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_rr_cruiser_wing_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_rr_cruiser_wing_loop),
    END_LOOP(),
};

const BehaviorScript bhvSpindel[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_ANGLE_TO_MOVE_ANGLE | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(ssl_seg7_collision_spindel),
    CALL_NATIVE(bhv_spindel_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_spindel_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvSslMovingPyramidWall[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_ANGLE_TO_MOVE_ANGLE | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(ssl_seg7_collision_0702808C),
    CALL_NATIVE(bhv_ssl_moving_pyramid_wall_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ssl_moving_pyramid_wall_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvPyramidElevator[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(ssl_seg7_collision_pyramid_elevator),
    SET_HOME(),
    SET_FLOAT(oCollisionDistance, 20000),
    CALL_NATIVE(bhv_pyramid_elevator_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_pyramid_elevator_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvPyramidElevatorTrajectoryMarkerBall[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_pyramid_elevator_trajectory_marker_ball_loop),
    END_LOOP(),
};

const BehaviorScript bhvPyramidTop[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(ssl_seg7_collision_pyramid_top),
    SET_HOME(),
    SET_FLOAT(oCollisionDistance, 20000),
    CALL_NATIVE(bhv_pyramid_top_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_pyramid_top_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvPyramidTopFragment[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_pyramid_top_fragment_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_pyramid_top_fragment_loop),
    END_LOOP(),
};

const BehaviorScript bhvPyramidPillarTouchDetector[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HITBOX(/*Radius*/ 50, /*Height*/ 50),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_pyramid_pillar_touch_detector_loop),
    END_LOOP(),
};

const BehaviorScript bhvWaterfallSoundLoop[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_waterfall_sound_loop),
    END_LOOP(),
};

const BehaviorScript bhvVolcanoSoundLoop[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_volcano_sound_loop),
    END_LOOP(),
};

const BehaviorScript bhvCastleFlagWaving[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_ANIMATIONS(oAnimations, castle_grounds_seg7_anims_flags),
    ANIMATE(0),
    CALL_NATIVE(bhv_castle_flag_init),
    BEGIN_LOOP(),
    END_LOOP(),
};

const BehaviorScript bhvBirdsSoundLoop[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_birds_sound_loop),
    END_LOOP(),
};

const BehaviorScript bhvAmbientSounds[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    CALL_NATIVE(bhv_ambient_sounds_init),
    BEGIN_LOOP(),
    END_LOOP(),
};

const BehaviorScript bhvSandSoundLoop[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_sand_sound_loop),
    END_LOOP(),
};

const BehaviorScript bhvHiddenAt120Stars[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(castle_grounds_seg7_collision_cannon_grill),
    SET_FLOAT(oCollisionDistance, 4000),
    CALL_NATIVE(bhv_castle_cannon_grate_init),
    BEGIN_LOOP(),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvSnowmansBottom[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    SET_INT(oIntangibleTimer, 0),
    CALL_NATIVE(bhv_snowmans_bottom_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_snowmans_bottom_loop),
    END_LOOP(),
};

const BehaviorScript bhvSnowmansHead[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    SET_FLOAT(oGraphYOffset, 110),
    CALL_NATIVE(bhv_snowmans_head_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_snowmans_head_loop),
    END_LOOP(),
};

const BehaviorScript bhvSnowmansBodyCheckpoint[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_snowmans_body_checkpoint_loop),
    END_LOOP(),
};

const BehaviorScript bhvBigSnowmanWhole[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_FLOAT(oGraphYOffset, 180),
    SET_INTERACT_TYPE(INTERACT_TEXT),
    SET_HITBOX(/*Radius*/ 210, /*Height*/ 550),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
    END_LOOP(),
};

const BehaviorScript bhvBigBoulder[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_FLOAT(oGraphYOffset, 180),
    CALL_NATIVE(bhv_big_boulder_init),
    SET_FLOAT(oCollisionDistance, 20000),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_big_boulder_loop),
    END_LOOP(),
};

const BehaviorScript bhvBigBoulderGenerator[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_big_boulder_generator_loop),
    END_LOOP(),
};

const BehaviorScript bhvWingCap[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_wing_cap_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_wing_vanish_cap_loop),
    END_LOOP(),
};

const BehaviorScript bhvMetalCap[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_metal_cap_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_metal_cap_loop),
    END_LOOP(),
};

const BehaviorScript bhvNormalCap[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_normal_cap_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_normal_cap_loop),
    END_LOOP(),
};

const BehaviorScript bhvVanishCap[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_vanish_cap_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_wing_vanish_cap_loop),
    END_LOOP(),
};

const BehaviorScript bhvStar[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_init_room),
    CALL_NATIVE(bhv_collect_star_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_collect_star_loop),
    END_LOOP(),
};

const BehaviorScript bhvStarSpawnCoordinates[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_collect_star_init),
    CALL_NATIVE(bhv_star_spawn_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_star_spawn_loop),
    END_LOOP(),
};

const BehaviorScript bhvHiddenRedCoinStar[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_PERSISTENT_RESPAWN | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_hidden_red_coin_star_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_hidden_red_coin_star_loop),
    END_LOOP(),
};

const BehaviorScript bhvRedCoin[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_INT(oIntangibleTimer, 0),
    SET_INT(oAnimState, -1),
    CALL_NATIVE(bhv_init_room),
    CALL_NATIVE(bhv_red_coin_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_red_coin_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvBowserCourseRedCoinStar[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_PERSISTENT_RESPAWN | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bowser_course_red_coin_star_loop),
    END_LOOP(),
};

const BehaviorScript bhvHiddenStar[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_PERSISTENT_RESPAWN | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_hidden_star_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_hidden_star_loop),
    END_LOOP(),
};

const BehaviorScript bhvHiddenStarTrigger[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 100),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_hidden_star_trigger_loop),
    END_LOOP(),
};

const BehaviorScript bhvTtmRollingLog[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(ttm_seg7_collision_pitoune_2),
    SET_HOME(),
    SET_FLOAT(oCollisionDistance, 2000),
    CALL_NATIVE(bhv_ttm_rolling_log_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_rolling_log_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvLllVolcanoFallingTrap[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(lll_seg7_collision_falling_wall),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_volcano_trap_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvLllRollingLog[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(lll_seg7_collision_pitoune),
    SET_HOME(),
    SET_FLOAT(oCollisionDistance, 2000),
    CALL_NATIVE(bhv_lll_rolling_log_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_rolling_log_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhv1upWalking[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 30, /*Height*/ 30, /*Downwards offset*/ 0),
    SET_FLOAT(oGraphYOffset, 30),
    CALL_NATIVE(bhv_1up_common_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_1up_walking_loop),
    END_LOOP(),
};

const BehaviorScript bhv1upRunningAway[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 30, /*Height*/ 30, /*Downwards offset*/ 0),
    SET_FLOAT(oGraphYOffset, 30),
    CALL_NATIVE(bhv_1up_common_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_1up_running_away_loop),
    END_LOOP(),
};

const BehaviorScript bhv1upSliding[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 30, /*Height*/ 30, /*Downwards offset*/ 0),
    SET_FLOAT(oGraphYOffset, 30),
    CALL_NATIVE(bhv_1up_common_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_1up_sliding_loop),
    END_LOOP(),
};

const BehaviorScript bhv1Up[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 30, /*Height*/ 30, /*Downwards offset*/ 0),
    SET_FLOAT(oGraphYOffset, 30),
    CALL_NATIVE(bhv_1up_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_1up_loop),
    END_LOOP(),
};

const BehaviorScript bhv1upJumpOnApproach[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 30, /*Height*/ 30, /*Downwards offset*/ 0),
    SET_FLOAT(oGraphYOffset, 30),
    CALL_NATIVE(bhv_1up_common_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_1up_jump_on_approach_loop),
    END_LOOP(),
};

const BehaviorScript bhvHidden1up[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 30, /*Height*/ 30, /*Downwards offset*/ 0),
    SET_FLOAT(oGraphYOffset, 30),
    CALL_NATIVE(bhv_1up_common_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_1up_hidden_loop),
    END_LOOP(),
};

const BehaviorScript bhvHidden1upTrigger[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 100),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_1up_hidden_trigger_loop),
    END_LOOP(),
};

const BehaviorScript bhvHidden1upInPole[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_HITBOX_WITH_OFFSET(/*Radius*/ 30, /*Height*/ 30, /*Downwards offset*/ 0),
    SET_FLOAT(oGraphYOffset, 30),
    CALL_NATIVE(bhv_1up_common_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_1up_hidden_in_pole_loop),
    END_LOOP(),
};

const BehaviorScript bhvHidden1upInPoleTrigger[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HITBOX(/*Radius*/ 100, /*Height*/ 100),
    SET_INT(oIntangibleTimer, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_1up_hidden_in_pole_trigger_loop),
    END_LOOP(),
};

const BehaviorScript bhvHidden1upInPoleSpawner[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_1up_hidden_in_pole_spawner_loop),
    END_LOOP(),
};

const BehaviorScript bhvControllablePlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_THROW_MATRIX_FROM_TRANSFORM | OBJ_FLAG_0020 | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(hmc_seg7_collision_controllable_platform),
    SET_HOME(),
    CALL_NATIVE(bhv_controllable_platform_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_controllable_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvControllablePlatformSub[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(hmc_seg7_collision_controllable_platform_sub),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_controllable_platform_sub_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvBreakableBoxSmall[] = {
    BEGIN(OBJ_LIST_DESTRUCTIVE),
    OR_INT(oFlags, (OBJ_FLAG_HOLDABLE | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    SET_HOME(),
    CALL_NATIVE(bhv_breakable_box_small_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_breakable_box_small_loop),
    END_LOOP(),
};

const BehaviorScript bhvSlidingSnowMound[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(sl_seg7_collision_sliding_snow_mound),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_sliding_snow_mound_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvSnowMoundSpawn[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_snow_mound_spawn_loop),
    END_LOOP(),
};

const BehaviorScript bhvWdwSquareFloatingPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(wdw_seg7_collision_square_floating_platform),
    SET_FLOAT(oFloatingPlatformUnkFC, 64),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_floating_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvWdwRectangularFloatingPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(wdw_seg7_collision_rect_floating_platform),
    SET_FLOAT(oFloatingPlatformUnkFC, 64),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_floating_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvJrbFloatingPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_COLLISION_DATA(jrb_seg7_collision_floating_platform),
    SET_FLOAT(oFloatingPlatformUnkFC, 64),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_floating_platform_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvArrowLift[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(wdw_seg7_collision_arrow_lift),
    SET_INT_RAND_RSHIFT(oArrowLiftUnk100, /*Minimum*/ 1, /*Right shift*/ 32),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_arrow_lift_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvOrangeNumber[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_HOME(),
    CALL_NATIVE(bhv_orange_number_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_orange_number_loop),
    END_LOOP(),
};

const BehaviorScript bhvMantaRay[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_ANGLE_TO_MOVE_ANGLE | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, manta_seg5_anims_05008EB4),
    ANIMATE(0),
    CALL_NATIVE(bhv_manta_ray_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_manta_ray_loop),
    END_LOOP(),
};

const BehaviorScript bhvFallingPillar[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    CALL_NATIVE(bhv_falling_pillar_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_falling_pillar_loop),
    END_LOOP(),
};

const BehaviorScript bhvFallingPillarHitbox[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_falling_pillar_hitbox_loop),
    END_LOOP(),
};

const BehaviorScript bhvPillarBase[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(jrb_seg7_collision_pillar_base),
    BEGIN_LOOP(),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvJrbFloatingBox[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_COLLISION_DATA(jrb_seg7_collision_floating_box),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_jrb_floating_box_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvDecorativePendulum[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_decorative_pendulum_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_decorative_pendulum_loop),
    END_LOOP(),
};

const BehaviorScript bhvTreasureChestsShip[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    DROP_TO_FLOOR(),
    CALL_NATIVE(bhv_treasure_chest_ship_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_treasure_chest_ship_loop),
    END_LOOP(),
};

const BehaviorScript bhvTreasureChestsJrb[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    DROP_TO_FLOOR(),
    CALL_NATIVE(bhv_treasure_chest_jrb_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_treasure_chest_jrb_loop),
    END_LOOP(),
};

const BehaviorScript bhvTreasureChests[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    DROP_TO_FLOOR(),
    CALL_NATIVE(bhv_treasure_chest_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_treasure_chest_loop),
    END_LOOP(),
};

const BehaviorScript bhvTreasureChestBottom[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    DROP_TO_FLOOR(),
    CALL_NATIVE(bhv_treasure_chest_bottom_init),
    SET_INT(oIntangibleTimer, -1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_treasure_chest_bottom_loop),
    END_LOOP(),
};

const BehaviorScript bhvTreasureChestTop[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_treasure_chest_top_loop),
    END_LOOP(),
};

const BehaviorScript bhvMips[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_HOLDABLE | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, mips_seg6_anims_06015634),
    SET_INT(oInteractType, INTERACT_GRABBABLE),
    DROP_TO_FLOOR(),
    SET_HITBOX(/*Radius*/ 50, /*Height*/ 75),
    SET_INT(oIntangibleTimer, 0),
    CALL_NATIVE(bhv_mips_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_mips_loop),
    END_LOOP(),
};

const BehaviorScript bhvYoshi[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, yoshi_seg5_anims_05024100),
    SET_INTERACT_TYPE(INTERACT_TEXT),
    DROP_TO_FLOOR(),
    SET_HITBOX(/*Radius*/ 160, /*Height*/ 150),
    ANIMATE(0),
    SET_HOME(),
    CALL_NATIVE(bhv_yoshi_init),
    BEGIN_LOOP(),
        SET_INT(oIntangibleTimer, 0),
        CALL_NATIVE(bhv_yoshi_loop),
    END_LOOP(),
};

const BehaviorScript bhvKoopa[] = {
    BEGIN(OBJ_LIST_PUSHABLE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    LOAD_ANIMATIONS(oAnimations, koopa_seg6_anims_06011364),
    ANIMATE(9),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 50, /*Gravity*/ -400, /*Bounciness*/ 0, /*Drag strength*/ 0, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SCALE(/*Unused*/ 0, /*Field*/ 150),
    SET_FLOAT(oKoopaAgility, 1),
    CALL_NATIVE(bhv_koopa_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_koopa_update),
    END_LOOP(),
};

const BehaviorScript bhvKoopaRaceEndpoint[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    SPAWN_CHILD_WITH_PARAM(/*Bhv param*/ 0, /*Model*/ MODEL_KOOPA_FLAG, /*Behavior*/ bhvKoopaFlag),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_koopa_race_endpoint_update),
    END_LOOP(),
};

const BehaviorScript bhvKoopaFlag[] = {
    BEGIN(OBJ_LIST_POLELIKE),
    SET_INTERACT_TYPE(INTERACT_POLE),
    SET_HITBOX(/*Radius*/ 80, /*Height*/ 700),
    SET_INT(oIntangibleTimer, 0),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    DROP_TO_FLOOR(),
    LOAD_ANIMATIONS(oAnimations, koopa_flag_seg6_anims_06001028),
    ANIMATE(0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_pole_base_loop),
    END_LOOP(),
};

const BehaviorScript bhvPokey[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 60, /*Gravity*/ -400, /*Bounciness*/ 0, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_pokey_update),
    END_LOOP(),
};

const BehaviorScript bhvPokeyBodyPart[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 60, /*Gravity*/ -400, /*Bounciness*/ 0, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BILLBOARD(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_pokey_body_part_update),
    END_LOOP(),
};

const BehaviorScript bhvSwoop[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, swoop_seg6_anims_060070D0),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 50, /*Gravity*/ 0, /*Bounciness*/ -50, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 0, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_init_room),
    SCALE(/*Unused*/ 0, /*Field*/ 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_swoop_update),
    END_LOOP(),
};

const BehaviorScript bhvFlyGuy[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, flyguy_seg8_anims_08011A64),
    ANIMATE(0),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 50, /*Gravity*/ 0, /*Bounciness*/ 0, /*Drag strength*/ 0, /*Friction*/ 1000, /*Buoyancy*/ 600, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_init_room),
    SET_INT(oInteractionSubtype, INT_SUBTYPE_TWIRL_BOUNCE),
    SET_FLOAT(oGraphYOffset, 30),
    SCALE(/*Unused*/ 0, /*Field*/ 150),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_fly_guy_update),
    END_LOOP(),
};

const BehaviorScript bhvGoomba[] = {
    BEGIN(OBJ_LIST_PUSHABLE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    LOAD_ANIMATIONS(oAnimations, goomba_seg8_anims_0801DA4C),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 40, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 0, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_goomba_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_goomba_update),
    END_LOOP(),
};

const BehaviorScript bhvGoombaTripletSpawner[] = {
    BEGIN(OBJ_LIST_PUSHABLE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_goomba_triplet_spawner_update),
    END_LOOP(),
};

const BehaviorScript bhvChainChomp[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    LOAD_ANIMATIONS(oAnimations, chain_chomp_seg6_anims_06025178),
    ANIMATE(0),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 0, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 0, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    HIDE(),
    SET_HOME(),
    SET_FLOAT(oGraphYOffset, 240),
    SCALE(/*Unused*/ 0, /*Field*/ 200),
    SPAWN_CHILD_WITH_PARAM(/*Bhv param*/ 0, /*Model*/ MODEL_WOODEN_POST, /*Behavior*/ bhvWoodenPost),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_chain_chomp_update),
    END_LOOP(),
};

const BehaviorScript bhvChainChompChainPart[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 0, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_FLOAT(oGraphYOffset, 40),
    SCALE(/*Unused*/ 0, /*Field*/ 200),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_chain_chomp_chain_part_update),
    END_LOOP(),
};

const BehaviorScript bhvWoodenPost[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(poundable_pole_collision_06002490),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 0, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_INT(oNumLootCoins, 5),
    DROP_TO_FLOOR(),
    SET_HOME(),
    SCALE(/*Unused*/ 0, /*Field*/ 50),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_wooden_post_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvChainChompGate[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(bob_seg7_collision_chain_chomp_gate),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_chain_chomp_gate_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_chain_chomp_gate_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvWigglerHead[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    LOAD_ANIMATIONS(oAnimations, wiggler_seg5_anims_0500EC8C),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 60, /*Gravity*/ -400, /*Bounciness*/ 0, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    HIDE(),
    SCALE(/*Unused*/ 0, /*Field*/ 400),
    SET_FLOAT(oWigglerFallThroughFloorsHeight, 5000),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_wiggler_update),
    END_LOOP(),
};

const BehaviorScript bhvWigglerBody[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_ANIMATIONS(oAnimations, wiggler_seg5_anims_0500C874),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 0, /*Gravity*/ -400, /*Bounciness*/ 0, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SCALE(/*Unused*/ 0, /*Field*/ 400),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_wiggler_body_part_update),
    END_LOOP(),
};

const BehaviorScript bhvEnemyLakitu[] = {
    BEGIN(OBJ_LIST_PUSHABLE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, lakitu_enemy_seg5_anims_050144D4),
    ANIMATE(0),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 40, /*Gravity*/ 0, /*Bounciness*/ -50, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_enemy_lakitu_update),
    END_LOOP(),
};

const BehaviorScript bhvCameraLakitu[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, lakitu_seg6_anims_060058F8),
    ANIMATE(0),
    CALL_NATIVE(bhv_init_room),
    CALL_NATIVE(bhv_camera_lakitu_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_camera_lakitu_update),
    END_LOOP(),
};

const BehaviorScript bhvCloud[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_HOME(),
    SET_INT(oOpacity, 240),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_cloud_update),
    END_LOOP(),
};

const BehaviorScript bhvCloudPart[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_INT(oOpacity, 240),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_cloud_part_update),
    END_LOOP(),
};

const BehaviorScript bhvSpiny[] = {
    BEGIN(OBJ_LIST_PUSHABLE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, spiny_seg5_anims_05016EAC),
    ANIMATE(0),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 40, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_spiny_update),
    END_LOOP(),
};

const BehaviorScript bhvMontyMole[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    LOAD_ANIMATIONS(oAnimations, monty_mole_seg5_anims_05007248),
    ANIMATE(3),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ 0, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    HIDE(),
    SET_INT(oIntangibleTimer, -1),
    SET_FLOAT(oGraphYOffset, -60),
    SCALE(/*Unused*/ 0, /*Field*/ 150),
    DELAY(1),
    CALL_NATIVE(bhv_monty_mole_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_monty_mole_update),
    END_LOOP(),
};

const BehaviorScript bhvMontyMoleHole[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    SCALE(/*Unused*/ 0, /*Field*/ 150),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_monty_mole_hole_update),
    END_LOOP(),
};

const BehaviorScript bhvMontyMoleRock[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_FLOAT(oGraphYOffset, 10),
    SCALE(/*Unused*/ 0, /*Field*/ 200),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_monty_mole_rock_update),
    END_LOOP(),
};

const BehaviorScript bhvPlatformOnTrack[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 50, /*Gravity*/ -100, /*Bounciness*/ -50, /*Drag strength*/ 100, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_init_room),
    CALL_NATIVE(bhv_platform_on_track_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_platform_on_track_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvTrackBall[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    CALL_NATIVE(bhv_init_room),
    SCALE(/*Unused*/ 0, /*Field*/ 15),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_track_ball_update),
    END_LOOP(),
};

const BehaviorScript bhvSeesawPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_seesaw_platform_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_seesaw_platform_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvFerrisWheelAxle[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    ADD_INT(oMoveAngleYaw, 0x4000),
    CALL_NATIVE(bhv_ferris_wheel_axle_init),
    BEGIN_LOOP(),
        ADD_INT(oFaceAngleRoll, 400),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvFerrisWheelPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ferris_wheel_platform_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvWaterBombSpawner[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_water_bomb_spawner_update),
    END_LOOP(),
};

const BehaviorScript bhvWaterBomb[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 120, /*Gravity*/ -400, /*Bounciness*/ 0, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_water_bomb_update),
    END_LOOP(),
};

const BehaviorScript bhvWaterBombShadow[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SCALE(/*Unused*/ 0, /*Field*/ 150),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_water_bomb_shadow_update),
    END_LOOP(),
};

const BehaviorScript bhvTTCRotatingSolid[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SET_FLOAT(oCollisionDistance, 450),
    CALL_NATIVE(bhv_ttc_rotating_solid_init),
    SET_INT(oTTCRotatingSolidNumTurns, 1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ttc_rotating_solid_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvTTCPendulum[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(ttc_seg7_collision_clock_pendulum),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_FLOAT(oCollisionDistance, 1500),
    CALL_NATIVE(bhv_ttc_pendulum_init),
    SET_FLOAT(oTTCPendulumAccelDir, 1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ttc_pendulum_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvTTCTreadmill[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_FLOAT(oCollisionDistance, 750),
    CALL_NATIVE(bhv_ttc_treadmill_init),
    DELAY(1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ttc_treadmill_update),
        CALL_NATIVE(cur_obj_compute_vel_xz),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvTTCMovingBar[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(ttc_seg7_collision_sliding_surface),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SET_FLOAT(oCollisionDistance, 550),
    CALL_NATIVE(bhv_ttc_moving_bar_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ttc_moving_bar_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvTTCCog[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_FLOAT(oCollisionDistance, 400),
    CALL_NATIVE(bhv_ttc_cog_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ttc_cog_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvTTCPitBlock[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SET_FLOAT(oCollisionDistance, 350),
    CALL_NATIVE(bhv_ttc_pit_block_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ttc_pit_block_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvTTCElevator[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(ttc_seg7_collision_clock_platform),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SET_FLOAT(oCollisionDistance, 400),
    CALL_NATIVE(bhv_ttc_elevator_init),
    SET_FLOAT(oTTCElevatorDir, 1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ttc_elevator_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvTTC2DRotator[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(ttc_seg7_collision_clock_main_rotation),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_FLOAT(oCollisionDistance, 1800),
    CALL_NATIVE(bhv_ttc_2d_rotator_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ttc_2d_rotator_update),
    END_LOOP(),
};

const BehaviorScript bhvTTCSpinner[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(ttc_seg7_collision_rotating_clock_platform2),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_FLOAT(oCollisionDistance, 450),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ttc_spinner_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvMrBlizzard[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    LOAD_ANIMATIONS(oAnimations, snowman_seg5_anims_0500D118),
    ANIMATE(0),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -400, /*Bounciness*/ 0, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_mr_blizzard_init),
    SET_FLOAT(oMrBlizzardScale, 1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_mr_blizzard_update),
    END_LOOP(),
};

const BehaviorScript bhvMrBlizzardSnowball[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ -300, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SCALE(/*Unused*/ 0, /*Field*/ 200),
    ADD_INT(oMoveAngleYaw, -0x5B58),
    SET_FLOAT(oForwardVel, 5),
    SET_FLOAT(oVelY, -1),
    SET_FLOAT(oGraphYOffset, 10),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_mr_blizzard_snowball),
    END_LOOP(),
};

const BehaviorScript bhvSlidingPlatform2[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    CALL_NATIVE(bhv_sliding_plat_2_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_sliding_plat_2_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvOctagonalPlatformRotating[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    CALL_NATIVE(bhv_rotating_octagonal_plat_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_rotating_octagonal_plat_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvAnimatesOnFloorSwitchPress[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_FLOAT(oCollisionDistance, 8000),
    CALL_NATIVE(bhv_animates_on_floor_switch_press_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_animates_on_floor_switch_press_loop),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvActivatedBackAndForthPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    CALL_NATIVE(bhv_activated_back_and_forth_platform_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_activated_back_and_forth_platform_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvRecoveryHeart[] = {
    BEGIN(OBJ_LIST_LEVEL),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_recovery_heart_loop),
    END_LOOP(),
};

const BehaviorScript bhvWaterBombCannon[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_water_bomb_cannon_loop),
    END_LOOP(),
};

const BehaviorScript bhvCannonBarrelBubbles[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bubble_cannon_barrel_loop),
    END_LOOP(),
};

const BehaviorScript bhvUnagi[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, unagi_seg5_anims_05012824),
    ANIMATE(6),
    SET_HOME(),
    SCALE(/*Unused*/ 0, /*Field*/ 300),
    SET_FLOAT(oDrawingDistance, 6000),
    CALL_NATIVE(bhv_unagi_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_unagi_loop),
    END_LOOP(),
};

const BehaviorScript bhvUnagiSubobject[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_unagi_subobject_loop),
    END_LOOP(),
};

const BehaviorScript bhvDorrie[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(dorrie_seg6_collision_0600F644),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, dorrie_seg6_anims_0600F638),
    SET_HOME(),
    SET_FLOAT(oCollisionDistance, 30000),
    ADD_FLOAT(oPosX, 2000),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_dorrie_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvHauntedChair[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    LOAD_ANIMATIONS(oAnimations, chair_seg5_anims_05005784),
    ANIMATE(0),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 40, /*Gravity*/ 0, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_HOME(),
    CALL_NATIVE(bhv_init_room),
    CALL_NATIVE(bhv_haunted_chair_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_haunted_chair_loop),
    END_LOOP(),
};

const BehaviorScript bhvMadPiano[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    LOAD_ANIMATIONS(oAnimations, mad_piano_seg5_anims_05009B14),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 40, /*Gravity*/ 0, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_HOME(),
    ADD_INT(oMoveAngleYaw, 0x4000),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_mad_piano_update),
    END_LOOP(),
};

const BehaviorScript bhvFlyingBookend[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, bookend_seg5_anims_05002540),
    ANIMATE(0),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 60, /*Gravity*/ 0, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_INT(oMoveFlags, 0),
    SCALE(/*Unused*/ 0, /*Field*/ 70),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_flying_bookend_loop),
    END_LOOP(),
};

const BehaviorScript bhvBookendSpawn[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bookend_spawn_loop),
    END_LOOP(),
};

const BehaviorScript bhvHauntedBookshelfManager[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_haunted_bookshelf_manager_loop),
    END_LOOP(),
};

const BehaviorScript bhvBookSwitch[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SET_FLOAT(oGraphYOffset, 30),
    ADD_INT(oMoveAngleYaw, 0x4000),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_book_switch_loop),
    END_LOOP(),
};

const BehaviorScript bhvFirePiranhaPlant[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    LOAD_ANIMATIONS(oAnimations, piranha_plant_seg6_anims_0601C31C),
    ANIMATE(0),
    SET_HOME(),
    HIDE(),
    CALL_NATIVE(bhv_fire_piranha_plant_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_fire_piranha_plant_update),
    END_LOOP(),
};

const BehaviorScript bhvSmallPiranhaFlame[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ 0, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_small_piranha_flame_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvFireSpitter[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SCALE(/*Unused*/ 0, /*Field*/ 40),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_fire_spitter_update),
    END_LOOP(),
};

const BehaviorScript bhvFlyguyFlame[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BILLBOARD(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 0, /*Gravity*/ 200, /*Bounciness*/ 0, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_fly_guy_flame_loop),
        ADD_INT(oAnimState, 1),
    END_LOOP(),
};

const BehaviorScript bhvSnufit[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 30, /*Gravity*/ 0, /*Bounciness*/ -50, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 0, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        SET_INT(oSnufitRecoil, 0),
        CALL_NATIVE(bhv_snufit_loop),
    END_LOOP(),
};

const BehaviorScript bhvSnufitBalls[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BILLBOARD(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 10, /*Gravity*/ 0, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    CALL_NATIVE(bhv_init_room),
    SET_FLOAT(oGraphYOffset, 10),
    SCALE(/*Unused*/ 0, /*Field*/ 10),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_snufit_balls_loop),
    END_LOOP(),
};

const BehaviorScript bhvHorizontalGrindel[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(ssl_seg7_collision_grindel),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    DROP_TO_FLOOR(),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 40, /*Gravity*/ -400, /*Bounciness*/ 0, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SCALE(/*Unused*/ 0, /*Field*/ 90),
    CALL_NATIVE(bhv_horizontal_grindel_init),
    BEGIN_LOOP(),
        CALL_NATIVE(cur_obj_update_floor_and_walls),
        CALL_NATIVE(bhv_horizontal_grindel_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvEyerokBoss[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_eyerok_boss_loop),
    END_LOOP(),
};

const BehaviorScript bhvEyerokHand[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, eyerok_seg5_anims_050116E4),
    ANIMATE(6),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 150, /*Gravity*/ 0, /*Bounciness*/ 0, /*Drag strength*/ 0, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_HOME(),
    SET_INT(oAnimState, 3),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_eyerok_hand_loop),
    END_LOOP(),
};

const BehaviorScript bhvKlepto[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, klepto_seg5_anims_05008CFC),
    ANIMATE(0),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 100, /*Gravity*/ 0, /*Bounciness*/ -20, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_HOME(),
    CALL_NATIVE(bhv_klepto_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_klepto_update),
    END_LOOP(),
};

const BehaviorScript bhvBird[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, birds_seg5_anims_050009E8),
    ANIMATE(0),
    HIDE(),
    SCALE(/*Unused*/ 0, /*Field*/ 70),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bird_update),
    END_LOOP(),
};

const BehaviorScript bhvRacingPenguin[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, penguin_seg5_anims_05008B74),
    ANIMATE(3),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 300, /*Gravity*/ -800, /*Bounciness*/ -5, /*Drag strength*/ 0, /*Friction*/ 0, /*Buoyancy*/ 0, /*Unused*/ 0, 0),
    SCALE(/*Unused*/ 0, /*Field*/ 400),
    CALL_NATIVE(bhv_racing_penguin_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_racing_penguin_update),
    END_LOOP(),
};

const BehaviorScript bhvPenguinRaceFinishLine[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_ACTIVE_FROM_AFAR | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_penguin_race_finish_line_update),
    END_LOOP(),
};

const BehaviorScript bhvPenguinRaceShortcutCheck[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_penguin_race_shortcut_check_update),
    END_LOOP(),
};

const BehaviorScript bhvCoffinSpawner[] = {
    BEGIN(OBJ_LIST_SURFACE),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_coffin_spawner_loop),
    END_LOOP(),
};

const BehaviorScript bhvCoffin[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(bbh_seg7_collision_coffin),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    CALL_NATIVE(bhv_init_room),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_coffin_loop),
    END_LOOP(),
};

const BehaviorScript bhvClamShell[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    DROP_TO_FLOOR(),
    LOAD_ANIMATIONS(oAnimations, clam_shell_seg5_anims_05001744),
    SET_FLOAT(oGraphYOffset, 10),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_clam_loop),
    END_LOOP(),
};

const BehaviorScript bhvSkeeter[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, skeeter_seg6_anims_06007DE0),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 180, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 1200, /*Unused*/ 0, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_skeeter_update),
    END_LOOP(),
};

const BehaviorScript bhvSkeeterWave[] = {
    BEGIN(OBJ_LIST_UNIMPORTANT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_skeeter_wave_update),
    END_LOOP(),
};

const BehaviorScript bhvSwingPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(rr_seg7_collision_pendulum),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_FLOAT(oCollisionDistance, 2000),
    CALL_NATIVE(bhv_swing_platform_init),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_swing_platform_update),
        CALL_NATIVE(load_object_collision_model),
    END_LOOP(),
};

const BehaviorScript bhvDonutPlatformSpawner[] = {
    BEGIN(OBJ_LIST_SPAWNER),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_donut_platform_spawner_update),
    END_LOOP(),
};

const BehaviorScript bhvDonutPlatform[] = {
    BEGIN(OBJ_LIST_SURFACE),
    LOAD_COLLISION_DATA(rr_seg7_collision_donut_platform),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_donut_platform_update),
    END_LOOP(),
};

const BehaviorScript bhvDDDPole[] = {
    BEGIN(OBJ_LIST_POLELIKE),
    SET_INTERACT_TYPE(INTERACT_POLE),
    SET_HITBOX(/*Radius*/ 80, /*Height*/ 800),
    SET_INT(oIntangibleTimer, 0),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    SET_HOME(),
    CALL_NATIVE(bhv_ddd_pole_init),
    SET_FLOAT(oDDDPoleVel, 10),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_ddd_pole_update),
        CALL_NATIVE(bhv_pole_base_loop),
    END_LOOP(),
};

const BehaviorScript bhvRedCoinStarMarker[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    DROP_TO_FLOOR(),
    SCALE(/*Unused*/ 0, /*Field*/ 150),
    SET_INT(oFaceAnglePitch, 0x4000),
    ADD_FLOAT(oPosY, 60),
    CALL_NATIVE(bhv_red_coin_star_marker_init),
    BEGIN_LOOP(),
        ADD_INT(oFaceAngleYaw, 0x100),
    END_LOOP(),
};

const BehaviorScript bhvTripletButterfly[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, butterfly_seg3_anims_030056B0),
    ANIMATE(0),
    HIDE(),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 0, /*Gravity*/ 0, /*Bounciness*/ 0, /*Drag strength*/ 0, /*Friction*/ 1000, /*Buoyancy*/ 200, /*Unused*/ 0, 0),
    SET_FLOAT(oTripletButterflyScale, 1),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_triplet_butterfly_update),
    END_LOOP(),
};

const BehaviorScript bhvBubba[] = {
    BEGIN(OBJ_LIST_GENACTOR),
    OR_INT(oFlags, (OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO | OBJ_FLAG_COMPUTE_DIST_TO_MARIO | OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    SET_HOME(),
    SET_OBJ_PHYSICS(/*Wall hitbox radius*/ 200, /*Gravity*/ -400, /*Bounciness*/ -50, /*Drag strength*/ 1000, /*Friction*/ 1000, /*Buoyancy*/ 0, /*Unused*/ 0, 0),
    SCALE(/*Unused*/ 0, /*Field*/ 50),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_bubba_loop),
    END_LOOP(),
};

const BehaviorScript bhvBeginningLakitu[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_ANIMATIONS(oAnimations, lakitu_seg6_anims_060058F8),
    ANIMATE(0),
    SET_FLOAT(oOpacity, 0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_intro_lakitu_loop),
    END_LOOP(),
};

const BehaviorScript bhvBeginningPeach[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    LOAD_ANIMATIONS(oAnimations, peach_seg5_anims_0501C41C),
    ANIMATE(0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_intro_peach_loop),
    END_LOOP(),
};

const BehaviorScript bhvEndBirds1[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_ANGLE_TO_MOVE_ANGLE | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, birds_seg5_anims_050009E8),
    ANIMATE(0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_end_birds_1_loop),
    END_LOOP(),
};

const BehaviorScript bhvEndBirds2[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, (OBJ_FLAG_SET_FACE_ANGLE_TO_MOVE_ANGLE | OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE)),
    LOAD_ANIMATIONS(oAnimations, birds_seg5_anims_050009E8),
    ANIMATE(0),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_end_birds_2_loop),
    END_LOOP(),
};

const BehaviorScript bhvIntroScene[] = {
    BEGIN(OBJ_LIST_DEFAULT),
    OR_INT(oFlags, OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE),
    BEGIN_LOOP(),
        CALL_NATIVE(bhv_intro_scene_loop),
    END_LOOP(),
};


