// metal_box.c.inc

struct ObjectHitbox sMetalBoxHitbox = {
    /* interactType: */ 0,
    /* downOffset: */ 0,
    /* damageOrCoinValue: */ 0,
    /* health: */ 1,
    /* numLootCoins: */ 0,
    /* radius: */ 220,
    /* height: */ 300,
    /* hurtboxRadius: */ 220,
    /* hurtboxHeight: */ 300,
};

s32 check_if_moving_over_floor(f32 a0, f32 a1) {
    struct Surface *sp24;
    f32 sp20 = o->oPosX + sins(o->oMoveAngleYaw) * a1;
    f32 floorHeight;
    f32 sp18 = o->oPosZ + coss(o->oMoveAngleYaw) * a1;
    floorHeight = find_floor(sp20, o->oPosY, sp18, &sp24);
    if (absf(floorHeight - o->oPosY) < a0) // abs
        return 1;
    else
        return 0;
}

void bhv_pushable_loop(void) {
    UNUSED s16 unused;
    s16 sp1C;
    obj_set_hitbox(o, &sMetalBoxHitbox);
    o->oForwardVel = 0.0f;
    if (obj_check_if_collided_with_object(o, gMarioObject) && gMarioStates->flags & 0x80000000) {
        sp1C = obj_angle_to_object(o, gMarioObject);
        if (abs_angle_diff(sp1C, gMarioObject->oMoveAngleYaw) > 0x4000) {
            o->oMoveAngleYaw = (s16)((gMarioObject->oMoveAngleYaw + 0x2000) & 0xc000);
            if (check_if_moving_over_floor(8.0f, 150.0f)) {
                o->oForwardVel = 4.0f;
                cur_obj_play_sound_1(SOUND_ENV_METAL_BOX_PUSH);
            }
        }
    }
    cur_obj_move_using_fvel_and_gravity();
}
