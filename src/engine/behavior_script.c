#include <ultra64.h>

#include "sm64.h"
#include "behavior_data.h"
#include "behavior_script.h"
#include "game/area.h"
#include "game/behavior_actions.h"
#include "game/game_init.h"
#include "game/mario.h"
#include "game/memory.h"
#include "game/obj_behaviors_2.h"
#include "game/object_helpers.h"
#include "game/object_list_processor.h"
#include "graph_node.h"
#include "surface_collision.h"

// Macros for retrieving arguments from behavior scripts.
#define BHV_CMD_GET_1ST_U8(index)  (u8)((gCurBhvCommand[index] >> 24) & 0xFF) // unused
#define BHV_CMD_GET_2ND_U8(index)  (u8)((gCurBhvCommand[index] >> 16) & 0xFF)
#define BHV_CMD_GET_3RD_U8(index)  (u8)((gCurBhvCommand[index] >> 8) & 0xFF)
#define BHV_CMD_GET_4TH_U8(index)  (u8)((gCurBhvCommand[index]) & 0xFF)

#define BHV_CMD_GET_1ST_S16(index) (s16)(gCurBhvCommand[index] >> 16)
#define BHV_CMD_GET_2ND_S16(index) (s16)(gCurBhvCommand[index] & 0xFFFF)

#define BHV_CMD_GET_U32(index)     (u32)(gCurBhvCommand[index])
#define BHV_CMD_GET_VPTR(index)    (void *)(gCurBhvCommand[index])

#define BHV_CMD_GET_ADDR_OF_CMD(index) (uintptr_t)(&gCurBhvCommand[index])

static u16 gRandomSeed16;

// Unused function that directly jumps to a behavior command and resets the object's stack index.
UNUSED static void goto_behavior_unused(const BehaviorScript *bhvAddr) {
    gCurBhvCommand = segmented_to_virtual(bhvAddr);
    gCurrentObject->bhvStackIndex = 0;
}

// Generate a pseudorandom integer from 0 to 65535 from the random seed, and update the seed.
u16 random_u16(void) {
    u16 temp1, temp2;

    if (gRandomSeed16 == 22026) {
        gRandomSeed16 = 0;
    }

    temp1 = (gRandomSeed16 & 0x00FF) << 8;
    temp1 = temp1 ^ gRandomSeed16;

    gRandomSeed16 = ((temp1 & 0x00FF) << 8) + ((temp1 & 0xFF00) >> 8);

    temp1 = ((temp1 & 0x00FF) << 1) ^ gRandomSeed16;
    temp2 = (temp1 >> 1) ^ 0xFF80;

    if ((temp1 & 1) == 0) {
        if (temp2 == 43605) {
            gRandomSeed16 = 0;
        } else {
            gRandomSeed16 = temp2 ^ 0x1FF4;
        }
    } else {
        gRandomSeed16 = temp2 ^ 0x8180;
    }

    return gRandomSeed16;
}

// Generate a pseudorandom float in the range [0, 1).
f32 random_float(void) {
    f32 rnd = random_u16();
    return rnd / (double) 0x10000;
}

// Return either -1 or 1 with a 50:50 chance.
s32 random_sign(void) {
    if (random_u16() >= 0x7FFF) {
        return 1;
    } else {
        return -1;
    }
}

// Update an object's graphical position and rotation to match its real position and rotation.
void obj_update_gfx_pos_and_angle(struct Object *obj) {
    obj->header.gfx.pos[0] = obj->oPosX;
    obj->header.gfx.pos[1] = obj->oPosY + obj->oGraphYOffset;
    obj->header.gfx.pos[2] = obj->oPosZ;

    obj->header.gfx.angle[0] = obj->oFaceAnglePitch & 0xFFFF;
    obj->header.gfx.angle[1] = obj->oFaceAngleYaw & 0xFFFF;
    obj->header.gfx.angle[2] = obj->oFaceAngleRoll & 0xFFFF;
}

// Push the address of a behavior command to the object's behavior stack.
static void cur_obj_bhv_stack_push(uintptr_t bhvAddr) {
    gCurrentObject->bhvStack[gCurrentObject->bhvStackIndex] = bhvAddr;
    gCurrentObject->bhvStackIndex++;
}

// Retrieve the last behavior command address from the object's behavior stack.
static uintptr_t cur_obj_bhv_stack_pop(void) {
    uintptr_t bhvAddr;

    gCurrentObject->bhvStackIndex--;
    bhvAddr = gCurrentObject->bhvStack[gCurrentObject->bhvStackIndex];

    return bhvAddr;
}

UNUSED static void stub_behavior_script_1(void) {
    for (;;) {
        ;
    }
}

// Command 0x22: Hides the current object.
// Usage: HIDE()
static s32 bhv_cmd_hide(void) {
    cur_obj_hide();

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x35: Disables rendering for the object.
// Usage: DISABLE_RENDERING()
static s32 bhv_cmd_disable_rendering(void) {
    gCurrentObject->header.gfx.node.flags &= ~GRAPH_RENDER_ACTIVE;

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x21: Billboards the current object, making it always face the camera.
// Usage: BILLBOARD()
static s32 bhv_cmd_billboard(void) {
    gCurrentObject->header.gfx.node.flags |= GRAPH_RENDER_BILLBOARD;

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x1B: Sets the current model ID of the object.
// Usage: SET_MODEL(modelID)
static s32 bhv_cmd_set_model(void) {
    s32 modelID = BHV_CMD_GET_2ND_S16(0);

    gCurrentObject->header.gfx.sharedChild = gLoadedGraphNodes[modelID];

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x1C: Spawns a child object with the specified model and behavior.
// Usage: SPAWN_CHILD(modelID, behavior)
static s32 bhv_cmd_spawn_child(void) {
    u32 model = BHV_CMD_GET_U32(1);
    const BehaviorScript *behavior = BHV_CMD_GET_VPTR(2);

    struct Object *child = spawn_object_at_origin(gCurrentObject, 0, model, behavior);
    obj_copy_pos_and_angle(child, gCurrentObject);

    gCurBhvCommand += 3;
    return BHV_PROC_CONTINUE;
}

// Command 0x2C: Spawns a new object with the specified model and behavior.
// Usage: SPAWN_OBJ(modelID, behavior)
static s32 bhv_cmd_spawn_obj(void) {
    u32 model = BHV_CMD_GET_U32(1);
    const BehaviorScript *behavior = BHV_CMD_GET_VPTR(2);

    struct Object *object = spawn_object_at_origin(gCurrentObject, 0, model, behavior);
    obj_copy_pos_and_angle(object, gCurrentObject);
    // TODO: Does this cmd need renaming? This line is the only difference between this and the above func.
    gCurrentObject->prevObj = object;

    gCurBhvCommand += 3;
    return BHV_PROC_CONTINUE;
}

// Command 0x29: Spawns a child object with the specified model and behavior, plus a behavior param.
// Usage: SPAWN_CHILD_WITH_PARAM(bhvParam, modelID, behavior)
static s32 bhv_cmd_spawn_child_with_param(void) {
    u32 bhvParam = BHV_CMD_GET_2ND_S16(0);
    u32 modelID = BHV_CMD_GET_U32(1);
    const BehaviorScript *behavior = BHV_CMD_GET_VPTR(2);

    struct Object *child = spawn_object_at_origin(gCurrentObject, 0, modelID, behavior);
    obj_copy_pos_and_angle(child, gCurrentObject);
    child->oBehParams2ndByte = bhvParam;

    gCurBhvCommand += 3;
    return BHV_PROC_CONTINUE;
}

// Command 0x1D: Exits the behavior script and despawns the object.
// Usage: DEACTIVATE()
static s32 bhv_cmd_deactivate(void) {
    gCurrentObject->activeFlags = ACTIVE_FLAG_DEACTIVATED;
    return BHV_PROC_BREAK;
}

// Command 0x0A: Exits the behavior script.
// Usage: BREAK()
static s32 bhv_cmd_break(void) {
    return BHV_PROC_BREAK;
}

// Command 0x0B: Exits the behavior script, unused.
// Usage: BREAK_UNUSED()
static s32 bhv_cmd_break_unused(void) {
    return BHV_PROC_BREAK;
}

// Command 0x02: Jumps to a new behavior command and stores the return address in the object's behavior stack.
// Usage: CALL(addr)
static s32 bhv_cmd_call(void) {
    const BehaviorScript *jumpAddress;
    gCurBhvCommand++;

    cur_obj_bhv_stack_push(BHV_CMD_GET_ADDR_OF_CMD(1)); // Store address of the next bhv command in the stack.
    jumpAddress = segmented_to_virtual(BHV_CMD_GET_VPTR(0));
    gCurBhvCommand = jumpAddress; // Jump to the new address.

    return BHV_PROC_CONTINUE;
}

// Command 0x03: Jumps back to the behavior command stored in the object's behavior stack. Used after CALL.
// Usage: RETURN()
static s32 bhv_cmd_return(void) {
    gCurBhvCommand = (const BehaviorScript *) cur_obj_bhv_stack_pop(); // Retrieve command address and jump to it.
    return BHV_PROC_CONTINUE;
}

// Command 0x01: Delays the behavior script for a certain number of frames.
// Usage: DELAY(num)
static s32 bhv_cmd_delay(void) {
    s16 num = BHV_CMD_GET_2ND_S16(0);

    if (gCurrentObject->bhvDelayTimer < num - 1) {
        gCurrentObject->bhvDelayTimer++; // Increment timer
    } else {
        gCurrentObject->bhvDelayTimer = 0;
        gCurBhvCommand++; // Delay ended, move to next bhv command (note: following commands will not execute until next frame)
    }

    return BHV_PROC_BREAK;
}

// Command 0x25: Delays the behavior script for the number of frames given by the value of the specified field.
// Usage: DELAY_VAR(field)
static s32 bhv_cmd_delay_var(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    s32 num = cur_obj_get_int(field);

    if (gCurrentObject->bhvDelayTimer < num - 1) {
        gCurrentObject->bhvDelayTimer++; // Increment timer
    } else {
        gCurrentObject->bhvDelayTimer = 0;
        gCurBhvCommand++; // Delay ended, move to next bhv command
    }

    return BHV_PROC_BREAK;
}

// Command 0x04: Jumps to a new behavior script without saving anything.
// Usage: GOTO(addr)
static s32 bhv_cmd_goto(void) {
    gCurBhvCommand++; // Useless
    gCurBhvCommand = segmented_to_virtual(BHV_CMD_GET_VPTR(0)); // Jump directly to address
    return BHV_PROC_CONTINUE;
}

// Command 0x26: Unused. Marks the start of a loop that will repeat a certain number of times.
// Uses a u8 as the argument, instead of a s16 like the other version does.
// Usage: BEGIN_REPEAT_UNUSED(count)
static s32 bhv_cmd_begin_repeat_unused(void) {
    s32 count = BHV_CMD_GET_2ND_U8(0);

    cur_obj_bhv_stack_push(BHV_CMD_GET_ADDR_OF_CMD(1)); // Store address of the first command of the loop in the stack
    cur_obj_bhv_stack_push(count); // Store repeat count in the stack too

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x05: Marks the start of a loop that will repeat a certain number of times.
// Usage: BEGIN_REPEAT(count)
static s32 bhv_cmd_begin_repeat(void) {
    s32 count = BHV_CMD_GET_2ND_S16(0);

    cur_obj_bhv_stack_push(BHV_CMD_GET_ADDR_OF_CMD(1)); // Store address of the first command of the loop in the stack
    cur_obj_bhv_stack_push(count); // Store repeat count in the stack too

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x06: Marks the end of a repeating loop.
// Usage: END_REPEAT()
static s32 bhv_cmd_end_repeat(void) {
    u32 count = cur_obj_bhv_stack_pop(); // Retrieve loop count from the stack.
    count--;

    if (count != 0) {
        gCurBhvCommand = (const BehaviorScript *) cur_obj_bhv_stack_pop(); // Jump back to the first command in the loop
        // Save address and count to the stack again
        cur_obj_bhv_stack_push(BHV_CMD_GET_ADDR_OF_CMD(0));
        cur_obj_bhv_stack_push(count);
    } else { // Finished iterating over the loop
        cur_obj_bhv_stack_pop(); // Necessary to remove address from the stack
        gCurBhvCommand++;
    }

    // Don't execute following commands until next frame
    return BHV_PROC_BREAK;
}

// Command 0x07: Also marks the end of a repeating loop, but continues executing commands following the loop on the same frame.
// Usage: END_REPEAT_CONTINUE()
static s32 bhv_cmd_end_repeat_continue(void) {
    u32 count = cur_obj_bhv_stack_pop();
    count--;

    if (count != 0) {
        gCurBhvCommand = (const BehaviorScript *) cur_obj_bhv_stack_pop(); // Jump back to the first command in the loop
        // Save address and count to the stack again
        cur_obj_bhv_stack_push(BHV_CMD_GET_ADDR_OF_CMD(0));
        cur_obj_bhv_stack_push(count);
    } else { // Finished iterating over the loop
        cur_obj_bhv_stack_pop(); // Necessary to remove address from the stack
        gCurBhvCommand++;
    }

    // Start executing following commands immediately
    return BHV_PROC_CONTINUE;
}

// Command 0x08: Marks the beginning of an infinite loop.
// Usage: BEGIN_LOOP()
static s32 bhv_cmd_begin_loop(void) {
    cur_obj_bhv_stack_push(BHV_CMD_GET_ADDR_OF_CMD(1)); // Store address of the first command of the loop in the stack

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x09: Marks the end of an infinite loop.
// Usage: END_LOOP()
static s32 bhv_cmd_end_loop(void) {
    gCurBhvCommand = (const BehaviorScript *) cur_obj_bhv_stack_pop(); // Jump back to the first command in the loop
    cur_obj_bhv_stack_push(BHV_CMD_GET_ADDR_OF_CMD(0)); // Save address to the stack again

    return BHV_PROC_BREAK;
}

// Command 0x0C: Executes a native game function. Function must not take or return any values.
// Usage: CALL_NATIVE(func)
typedef void (*NativeBhvFunc)(void);
static s32 bhv_cmd_call_native(void) {
    NativeBhvFunc behaviorFunc = BHV_CMD_GET_VPTR(1);

    behaviorFunc();

    gCurBhvCommand += 2;
    return BHV_PROC_CONTINUE;
}

// Command 0x0E: Sets the specified field to a float.
// Usage: SET_FLOAT(field, value)
static s32 bhv_cmd_set_float(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    f32 value = BHV_CMD_GET_2ND_S16(0);

    cur_obj_set_float(field, value);

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x10: Sets the specified field to an integer.
// Usage: SET_INT(field, value)
static s32 bhv_cmd_set_int(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    s16 value = BHV_CMD_GET_2ND_S16(0);

    cur_obj_set_int(field, value);

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x36: Unused. Sets the specified field to an integer. Wastes 4 bytes of space for no reason at all.
static s32 bhv_cmd_set_int_unused(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    s32 value = BHV_CMD_GET_2ND_S16(1); // Taken from 2nd word instead of 1st

    cur_obj_set_int(field, value);

    gCurBhvCommand += 2; // Twice as long
    return BHV_PROC_CONTINUE;
}

// Command 0x14: Sets the specified field to a random float in the given range.
// Usage: SET_RANDOM_FLOAT(field, min, range)
static s32 bhv_cmd_set_random_float(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    f32 min = BHV_CMD_GET_2ND_S16(0);
    f32 range = BHV_CMD_GET_1ST_S16(1);

    cur_obj_set_float(field, (range * random_float()) + min);

    gCurBhvCommand += 2;
    return BHV_PROC_CONTINUE;
}

// Command 0x15: Sets the specified field to a random integer in the given range.
// Usage: SET_RANDOM_INT(field, min, range)
static s32 bhv_cmd_set_random_int(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    s32 min = BHV_CMD_GET_2ND_S16(0);
    s32 range = BHV_CMD_GET_1ST_S16(1);

    cur_obj_set_int(field, (s32)(range * random_float()) + min);

    gCurBhvCommand += 2;
    return BHV_PROC_CONTINUE;
}

// Command 0x13: Gets a random short, right shifts it the specified amount and adds min to it, then sets the specified field to that value.
// Usage: SET_INT_RAND_RSHIFT(field, min, rshift)
static s32 bhv_cmd_set_int_rand_rshift(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    s32 min = BHV_CMD_GET_2ND_S16(0);
    s32 rshift = BHV_CMD_GET_1ST_S16(1);

    cur_obj_set_int(field, (random_u16() >> rshift) + min);

    gCurBhvCommand += 2;
    return BHV_PROC_CONTINUE;
}

// Command 0x16: Adds a random float in the given range to the specified field.
// Usage: ADD_RANDOM_FLOAT(field, min, range)
static s32 bhv_cmd_add_random_float(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    f32 min = BHV_CMD_GET_2ND_S16(0);
    f32 range = BHV_CMD_GET_1ST_S16(1);

    cur_obj_set_float(field, cur_obj_get_float(field) + min + (range * random_float()));

    gCurBhvCommand += 2;
    return BHV_PROC_CONTINUE;
}

// Command 0x17: Gets a random short, right shifts it the specified amount and adds min to it, then adds the value to the specified field. Unused.
// Usage: ADD_INT_RAND_RSHIFT(field, min, rshift)
static s32 bhv_cmd_add_int_rand_rshift(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    s32 min = BHV_CMD_GET_2ND_S16(0);
    s32 rshift = BHV_CMD_GET_1ST_S16(1);
    s32 rnd = random_u16();

    cur_obj_set_int(field, (cur_obj_get_int(field) + min) + (rnd >> rshift));

    gCurBhvCommand += 2;
    return BHV_PROC_CONTINUE;
}

// Command 0x0D: Adds a float to the specified field.
// Usage: ADD_FLOAT(field, value)
static s32 bhv_cmd_add_float(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    f32 value = BHV_CMD_GET_2ND_S16(0);

    cur_obj_add_float(field, value);

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x0F: Adds an integer to the specified field.
// Usage: ADD_INT(field, value)
static s32 bhv_cmd_add_int(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    s16 value = BHV_CMD_GET_2ND_S16(0);

    cur_obj_add_int(field, value);

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x11: Performs a bitwise OR with the specified field and the given integer.
// Usually used to set an object's flags.
// Usage: OR_INT(field, value)
static s32 bhv_cmd_or_int(void) {
    u8 objectOffset = BHV_CMD_GET_2ND_U8(0);
    s32 value = BHV_CMD_GET_2ND_S16(0);

    value &= 0xFFFF;
    cur_obj_or_int(objectOffset, value);

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x12: Performs a bit clear with the specified short. Unused.
// Usage: BIT_CLEAR(field, value)
static s32 bhv_cmd_bit_clear(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    s32 value = BHV_CMD_GET_2ND_S16(0);

    value = (value & 0xFFFF) ^ 0xFFFF;
    cur_obj_and_int(field, value);

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x27: Loads the animations for the object. <field> is always set to oAnimations.
// Usage: LOAD_ANIMATIONS(field, anims)
static s32 bhv_cmd_load_animations(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);

    cur_obj_set_vptr(field, BHV_CMD_GET_VPTR(1));

    gCurBhvCommand += 2;
    return BHV_PROC_CONTINUE;
}

// Command 0x28: Begins animation and sets the object's current animation index to the specified value.
// Usage: ANIMATE(animIndex)
static s32 bhv_cmd_animate(void) {
    s32 animIndex = BHV_CMD_GET_2ND_U8(0);
    struct Animation **animations = gCurrentObject->oAnimations;

    geo_obj_init_animation(&gCurrentObject->header.gfx, &animations[animIndex]);

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x1E: Finds the floor triangle directly under the object and moves the object down to it.
// Usage: DROP_TO_FLOOR()
static s32 bhv_cmd_drop_to_floor(void) {
    f32 x = gCurrentObject->oPosX;
    f32 y = gCurrentObject->oPosY;
    f32 z = gCurrentObject->oPosZ;

    f32 floor = find_floor_height(x, y + 200.0f, z);
    gCurrentObject->oPosY = floor;
    gCurrentObject->oMoveFlags |= OBJ_MOVE_ON_GROUND;

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x18: No operation. Unused.
// Usage: CMD_NOP_1(field)
static s32 bhv_cmd_nop_1(void) {
    UNUSED u8 field = BHV_CMD_GET_2ND_U8(0);

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x1A: No operation. Unused.
// Usage: CMD_NOP_3(field)
static s32 bhv_cmd_nop_3(void) {
    UNUSED u8 field = BHV_CMD_GET_2ND_U8(0);

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x19: No operation. Unused.
// Usage: CMD_NOP_2(field)
static s32 bhv_cmd_nop_2(void) {
    UNUSED u8 field = BHV_CMD_GET_2ND_U8(0);

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x1F: Sets the destination float field to the sum of the values of the given float fields.
// Usage: SUM_FLOAT(fieldDst, fieldSrc1, fieldSrc2)
static s32 bhv_cmd_sum_float(void) {
    u32 fieldDst = BHV_CMD_GET_2ND_U8(0);
    u32 fieldSrc1 = BHV_CMD_GET_3RD_U8(0);
    u32 fieldSrc2 = BHV_CMD_GET_4TH_U8(0);

    cur_obj_set_float(fieldDst, cur_obj_get_float(fieldSrc1) + cur_obj_get_float(fieldSrc2));

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x20: Sets the destination integer field to the sum of the values of the given integer fields. Unused.
// Usage: SUM_INT(fieldDst, fieldSrc1, fieldSrc2)
static s32 bhv_cmd_sum_int(void) {
    u32 fieldDst = BHV_CMD_GET_2ND_U8(0);
    u32 fieldSrc1 = BHV_CMD_GET_3RD_U8(0);
    u32 fieldSrc2 = BHV_CMD_GET_4TH_U8(0);

    cur_obj_set_int(fieldDst, cur_obj_get_int(fieldSrc1) + cur_obj_get_int(fieldSrc2));

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x23: Sets the size of the object's cylindrical hitbox.
// Usage: SET_HITBOX(radius, height)
static s32 bhv_cmd_set_hitbox(void) {
    s16 radius = BHV_CMD_GET_1ST_S16(1);
    s16 height = BHV_CMD_GET_2ND_S16(1);

    gCurrentObject->hitboxRadius = radius;
    gCurrentObject->hitboxHeight = height;

    gCurBhvCommand += 2;
    return BHV_PROC_CONTINUE;
}

// Command 0x2E: Sets the size of the object's cylindrical hurtbox.
// Usage: SET_HURTBOX(radius, height)
static s32 bhv_cmd_set_hurtbox(void) {
    s16 radius = BHV_CMD_GET_1ST_S16(1);
    s16 height = BHV_CMD_GET_2ND_S16(1);

    gCurrentObject->hurtboxRadius = radius;
    gCurrentObject->hurtboxHeight = height;

    gCurBhvCommand += 2;
    return BHV_PROC_CONTINUE;
}

// Command 0x2B: Sets the size of the object's cylindrical hitbox, and applies a downwards offset.
// Usage: SET_HITBOX_WITH_OFFSET(radius, height, downOffset)
static s32 bhv_cmd_set_hitbox_with_offset(void) {
    s16 radius = BHV_CMD_GET_1ST_S16(1);
    s16 height = BHV_CMD_GET_2ND_S16(1);
    s16 downOffset = BHV_CMD_GET_1ST_S16(2);

    gCurrentObject->hitboxRadius = radius;
    gCurrentObject->hitboxHeight = height;
    gCurrentObject->hitboxDownOffset = downOffset;

    gCurBhvCommand += 3;
    return BHV_PROC_CONTINUE;
}

// Command 0x24: No operation. Unused.
// Usage: CMD_NOP_4(field, value)
static s32 bhv_cmd_nop_4(void) {
    UNUSED s16 field = BHV_CMD_GET_2ND_U8(0);
    UNUSED s16 value = BHV_CMD_GET_2ND_S16(0);

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x00: Defines the start of the behavior script as well as the object list the object belongs to.
// Has some special behavior for certain objects.
// Usage: BEGIN(objList)
static s32 bhv_cmd_begin(void) {
    // These objects were likely very early objects, which is why this code is here
    // instead of in the respective behavior scripts.

    // Initiate the room if the object is a haunted chair or the mad piano.
    if (cur_obj_has_behavior(bhvHauntedChair)) {
        bhv_init_room();
    }
    if (cur_obj_has_behavior(bhvMadPiano)) {
        bhv_init_room();
    }
    // Set collision distance if the object is a message panel.
    if (cur_obj_has_behavior(bhvMessagePanel)) {
        gCurrentObject->oCollisionDistance = 150.0f;
    }
    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// An unused, incomplete behavior command that does not have an entry in the lookup table, and so no command number.
// It cannot be simply re-added to the table, as unlike all other bhv commands it takes a parameter.
// Theoretically this command would have been of variable size.
// Included below is a modified/repaired version of this function that would work properly.
UNUSED static void bhv_cmd_set_int_random_from_table(s32 tableSize) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    s32 table[16];
    s32 i;
    // This for loop would not work as intended at all...
    for (i = 0; i <= tableSize / 2; i += 2) {
        table[i] = BHV_CMD_GET_1ST_S16(i + 1);
        table[i + 1] = BHV_CMD_GET_2ND_S16(i + 1);
    }

    cur_obj_set_int(field, table[(s32)(tableSize * random_float())]);

    // Does not increment gCurBhvCommand or return a bhv status
}

/**
// Command 0x??: Sets the specified field to a random entry in the given table, up to size 16.
// Bytes: ?? FF SS SS V1 V1 V2 V2 V3 V3 V4 V4... ...V15 V15 V16 V16 (no macro exists)
// F -> field, S -> table size, V1, V2, etc. -> table entries (up to 16)
static s32 bhv_cmd_set_int_random_from_table(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    // Retrieve tableSize from the bhv command instead of as a parameter.
    s16 tableSize = BHV_CMD_GET_2ND_S16(0); // tableSize should not be greater than 16
    s32 table[16];
    s32 i;

    // Construct the table from the behavior command.
    for (i = 0; i <= tableSize; i += 2) {
        table[i] = BHV_CMD_GET_1ST_S16((i / 2) + 1);
        table[i + 1] = BHV_CMD_GET_2ND_S16((i / 2) + 1);
    }

    // Set the field to a random entry of the table.
    cur_obj_set_int(field, table[(s32)(tableSize * random_float())]);

    gCurBhvCommand += (tableSize / 2) + 1;
    return BHV_PROC_CONTINUE;
}
**/

// Command 0x2A: Loads collision data for the object.
// Usage: LOAD_COLLISION_DATA(collisionData)
static s32 bhv_cmd_load_collision_data(void) {
    u32 *collisionData = segmented_to_virtual(BHV_CMD_GET_VPTR(1));

    gCurrentObject->collisionData = collisionData;

    gCurBhvCommand += 2;
    return BHV_PROC_CONTINUE;
}

// Command 0x2D: Sets the home position of the object to its current position.
// Usage: SET_HOME()
static s32 bhv_cmd_set_home(void) {
    gCurrentObject->oHomeX = gCurrentObject->oPosX;
    gCurrentObject->oHomeY = gCurrentObject->oPosY;
    gCurrentObject->oHomeZ = gCurrentObject->oPosZ;

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

// Command 0x2F: Sets the object's interaction type.
// Usage: SET_INTERACT_TYPE(type)
static s32 bhv_cmd_set_interact_type(void) {
    gCurrentObject->oInteractType = BHV_CMD_GET_U32(1);

    gCurBhvCommand += 2;
    return BHV_PROC_CONTINUE;
}

// Command 0x31: Sets the object's interaction subtype. Unused.
// Usage: SET_INTERACT_SUBTYPE(subtype)
static s32 bhv_cmd_set_interact_subtype(void) {
    gCurrentObject->oInteractionSubtype = BHV_CMD_GET_U32(1);

    gCurBhvCommand += 2;
    return BHV_PROC_CONTINUE;
}

// Command 0x32: Sets the object's size to the specified percentage.
// Usage: SCALE(unusedField, percent)
static s32 bhv_cmd_scale(void) {
    UNUSED u8 unusedField = BHV_CMD_GET_2ND_U8(0);
    s16 percent = BHV_CMD_GET_2ND_S16(0);

    cur_obj_scale(percent / 100.0f);

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}


// Command 0x30: Sets various parameters that the object uses for calculating physics.
// Usage: SET_OBJ_PHYSICS(wallHitboxRadius, gravity, bounciness, dragStrength, friction, buoyancy, unused1, unused2)
static s32 bhv_cmd_set_obj_physics(void) {
    UNUSED f32 unused1, unused2;

    gCurrentObject->oWallHitboxRadius = BHV_CMD_GET_1ST_S16(1);
    gCurrentObject->oGravity = BHV_CMD_GET_2ND_S16(1) / 100.0f;
    gCurrentObject->oBounciness = BHV_CMD_GET_1ST_S16(2) / 100.0f;
    gCurrentObject->oDragStrength = BHV_CMD_GET_2ND_S16(2) / 100.0f;
    gCurrentObject->oFriction = BHV_CMD_GET_1ST_S16(3) / 100.0f;
    gCurrentObject->oBuoyancy = BHV_CMD_GET_2ND_S16(3) / 100.0f;

    unused1 = BHV_CMD_GET_1ST_S16(4) / 100.0f;
    unused2 = BHV_CMD_GET_2ND_S16(4) / 100.0f;

    gCurBhvCommand += 5;
    return BHV_PROC_CONTINUE;
}

// Command 0x33: Performs a bit clear on the object's parent's field with the specified value.
// Used for clearing active particle flags fron Mario's object.
// Usage: PARENT_BIT_CLEAR(field, value)
static s32 bhv_cmd_parent_bit_clear(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    s32 value = BHV_CMD_GET_U32(1);

    value = value ^ 0xFFFFFFFF;
    obj_and_int(gCurrentObject->parentObj, field, value);

    gCurBhvCommand += 2;
    return BHV_PROC_CONTINUE;
}

// Command 0x37: Spawns a water droplet with the given parameters.
// Usage: SPAWN_WATER_DROPLET(dropletParams)
static s32 bhv_cmd_spawn_water_droplet(void) {
    struct WaterDropletParams *dropletParams = BHV_CMD_GET_VPTR(1);

    spawn_water_droplet(gCurrentObject, dropletParams);

    gCurBhvCommand += 2;
    return BHV_PROC_CONTINUE;
}

// Command 0x34: Animates an object using texture animation. <field> is always set to oAnimState.
// Usage: ANIMATE_TEXTURE(field, rate)
static s32 bhv_cmd_animate_texture(void) {
    u8 field = BHV_CMD_GET_2ND_U8(0);
    s16 rate = BHV_CMD_GET_2ND_S16(0);

    // Increase the field (oAnimState) by 1 every <rate> frames.
    if ((gGlobalTimer % rate) == 0) {
        cur_obj_add_int(field, 1);
    }

    gCurBhvCommand++;
    return BHV_PROC_CONTINUE;
}

void stub_behavior_script_2(void) {
}

typedef s32 (*BhvCommandProc)(void);
static BhvCommandProc BehaviorCmdTable[] = {
    bhv_cmd_begin,
    bhv_cmd_delay,
    bhv_cmd_call,
    bhv_cmd_return,
    bhv_cmd_goto,
    bhv_cmd_begin_repeat,
    bhv_cmd_end_repeat,
    bhv_cmd_end_repeat_continue,
    bhv_cmd_begin_loop,
    bhv_cmd_end_loop,
    bhv_cmd_break,
    bhv_cmd_break_unused,
    bhv_cmd_call_native,
    bhv_cmd_add_float,
    bhv_cmd_set_float,
    bhv_cmd_add_int,
    bhv_cmd_set_int,
    bhv_cmd_or_int,
    bhv_cmd_bit_clear,
    bhv_cmd_set_int_rand_rshift,
    bhv_cmd_set_random_float,
    bhv_cmd_set_random_int,
    bhv_cmd_add_random_float,
    bhv_cmd_add_int_rand_rshift,
    bhv_cmd_nop_1,
    bhv_cmd_nop_2,
    bhv_cmd_nop_3,
    bhv_cmd_set_model,
    bhv_cmd_spawn_child,
    bhv_cmd_deactivate,
    bhv_cmd_drop_to_floor,
    bhv_cmd_sum_float,
    bhv_cmd_sum_int,
    bhv_cmd_billboard,
    bhv_cmd_hide,
    bhv_cmd_set_hitbox,
    bhv_cmd_nop_4,
    bhv_cmd_delay_var,
    bhv_cmd_begin_repeat_unused,
    bhv_cmd_load_animations,
    bhv_cmd_animate,
    bhv_cmd_spawn_child_with_param,
    bhv_cmd_load_collision_data,
    bhv_cmd_set_hitbox_with_offset,
    bhv_cmd_spawn_obj,
    bhv_cmd_set_home,
    bhv_cmd_set_hurtbox,
    bhv_cmd_set_interact_type,
    bhv_cmd_set_obj_physics,
    bhv_cmd_set_interact_subtype,
    bhv_cmd_scale,
    bhv_cmd_parent_bit_clear,
    bhv_cmd_animate_texture,
    bhv_cmd_disable_rendering,
    bhv_cmd_set_int_unused,
    bhv_cmd_spawn_water_droplet,
};

// Execute the behavior script of the current object, process the object flags, and other miscellaneous code for updating objects.
void cur_obj_update(void) {
    UNUSED u8 filler[4];

    s16 objFlags = gCurrentObject->oFlags;
    f32 distanceFromMario;
    BhvCommandProc bhvCmdProc;
    s32 bhvProcResult;

    // Calculate the distance from the object to Mario.
    if (objFlags & OBJ_FLAG_COMPUTE_DIST_TO_MARIO) {
        gCurrentObject->oDistanceToMario = dist_between_objects(gCurrentObject, gMarioObject);
        distanceFromMario = gCurrentObject->oDistanceToMario;
    } else {
        distanceFromMario = 0.0f;
    }

    // Calculate the angle from the object to Mario.
    if (objFlags & OBJ_FLAG_COMPUTE_ANGLE_TO_MARIO) {
        gCurrentObject->oAngleToMario = obj_angle_to_object(gCurrentObject, gMarioObject);
    }

    // If the object's action has changed, reset the action timer.
    if (gCurrentObject->oAction != gCurrentObject->oPrevAction) {
        (void) (gCurrentObject->oTimer = 0, gCurrentObject->oSubAction = 0,
                gCurrentObject->oPrevAction = gCurrentObject->oAction);
    }

    // Execute the behavior script.
    gCurBhvCommand = gCurrentObject->curBhvCommand;

    do {
        bhvCmdProc = BehaviorCmdTable[*gCurBhvCommand >> 24];
        bhvProcResult = bhvCmdProc();
    } while (bhvProcResult == BHV_PROC_CONTINUE);

    gCurrentObject->curBhvCommand = gCurBhvCommand;

    // Increment the object's timer.
    if (gCurrentObject->oTimer < 0x3FFFFFFF) {
        gCurrentObject->oTimer++;
    }

    // If the object's action has changed, reset the action timer.
    if (gCurrentObject->oAction != gCurrentObject->oPrevAction) {
        (void) (gCurrentObject->oTimer = 0, gCurrentObject->oSubAction = 0,
                gCurrentObject->oPrevAction = gCurrentObject->oAction);
    }

    // Execute various code based on object flags.
    objFlags = (s16) gCurrentObject->oFlags;

    if (objFlags & OBJ_FLAG_SET_FACE_ANGLE_TO_MOVE_ANGLE) {
        obj_set_face_angle_to_move_angle(gCurrentObject);
    }

    if (objFlags & OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW) {
        gCurrentObject->oFaceAngleYaw = gCurrentObject->oMoveAngleYaw;
    }

    if (objFlags & OBJ_FLAG_MOVE_XZ_USING_FVEL) {
        cur_obj_move_xz_using_fvel_and_yaw();
    }

    if (objFlags & OBJ_FLAG_MOVE_Y_WITH_TERMINAL_VEL) {
        cur_obj_move_y_with_terminal_vel();
    }

    if (objFlags & OBJ_FLAG_TRANSFORM_RELATIVE_TO_PARENT) {
        obj_build_transform_relative_to_parent(gCurrentObject);
    }

    if (objFlags & OBJ_FLAG_SET_THROW_MATRIX_FROM_TRANSFORM) {
        obj_set_throw_matrix_from_transform(gCurrentObject);
    }

    if (objFlags & OBJ_FLAG_UPDATE_GFX_POS_AND_ANGLE) {
        obj_update_gfx_pos_and_angle(gCurrentObject);
    }

    // Handle visibility of object
    if (gCurrentObject->oRoom != -1) {
        // If the object is in a room, only show it when Mario is in the room.
        cur_obj_enable_rendering_if_mario_in_room();
    } else if ((objFlags & OBJ_FLAG_COMPUTE_DIST_TO_MARIO) && gCurrentObject->collisionData == NULL) {
        if (!(objFlags & OBJ_FLAG_ACTIVE_FROM_AFAR)) {
            // If the object has a render distance, check if it should be shown.
            if (distanceFromMario > gCurrentObject->oDrawingDistance) {
                // Out of render distance, hide the object.
                gCurrentObject->header.gfx.node.flags &= ~GRAPH_RENDER_ACTIVE;
                gCurrentObject->activeFlags |= ACTIVE_FLAG_FAR_AWAY;
            } else if (gCurrentObject->oHeldState == HELD_FREE) {
                // In render distance (and not being held), show the object.
                gCurrentObject->header.gfx.node.flags |= GRAPH_RENDER_ACTIVE;
                gCurrentObject->activeFlags &= ~ACTIVE_FLAG_FAR_AWAY;
            }
        }
    }
}
