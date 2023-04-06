// flamethrower.c.inc

void bhv_flamethrower_flame_loop(void) {
    f32 size;
    s32 sp18;
    if (o->oTimer == 0) {
        o->oAnimState = (s32)(random_float() * 10.0f);
        obj_translate_xyz_random(o, 10.0f);
    }
    if (o->oBehParams2ndByte == 2)
        size = o->oTimer * (o->oForwardVel - 6.0f) / 100.0 + 2.0;
    else
        size = o->oTimer * (o->oForwardVel - 20.0) / 100.0 + 1.0;
    if (o->oBehParams2ndByte == 3) {
        o->hitboxHeight = 200.0f;
        o->hitboxDownOffset = 150.0f;
        o->oVelY = -28.0f;
        cur_obj_update_floor_height();
        if (o->oPosY - 25.0f * size < o->oFloorHeight) {
            o->oVelY = 0;
            o->oPosY = o->oFloorHeight + 25.0f * size;
        }
        sp18 = o->parentObj->oFlameThowerFlameUnk110 / 1.2;
    } else
        sp18 = o->parentObj->oFlameThowerFlameUnk110;
    cur_obj_scale(size);
    if (o->oBehParams2ndByte == 4)
        o->oPosY += o->oForwardVel; // weird?
    else
        cur_obj_move_using_fvel_and_gravity();
    if (o->oTimer > sp18)
        obj_mark_for_deletion(o);
    o->oInteractStatus = 0;
}

void bhv_flamethrower_loop(void) {
    struct Object *flame;
    f32 flameVel;
    s32 sp34;
    s32 model;
    UNUSED u8 pad[8];
    if (o->oAction == 0) {
        if (gCurrLevelNum != LEVEL_BBH || gMarioOnMerryGoRound == 1)
            if (o->oDistanceToMario < 2000.0f)
                o->oAction++;
    } else if (o->oAction == 1) {
        model = MODEL_RED_FLAME;
        flameVel = 95.0f;
        if (o->oBehParams2ndByte == 1)
            model = MODEL_BLUE_FLAME;
        if (o->oBehParams2ndByte == 2)
            flameVel = 50.0f;
        sp34 = 1;
        if (o->oTimer < 60)
            sp34 = 15;
        else if (o->oTimer < 74)
            sp34 = 75 - o->oTimer; // Range: [15..2]
        else
            o->oAction++;
        o->oFlameThowerUnk110 = sp34;
        flame = spawn_object_relative(o->oBehParams2ndByte, 0, 0, 0, o, model, bhvFlamethrowerFlame);
        flame->oForwardVel = flameVel;
        cur_obj_play_sound_1(SOUND_AIR_BLOW_FIRE);
    } else if (o->oTimer > 60)
        o->oAction = 0;
}

void bhv_rr_rotating_bridge_platform_loop(void) {
    o->oMoveAngleYaw -= 0x80;
    o->oAngleVelYaw = -0x80;
    bhv_flamethrower_loop();
}
