// coin.c.inc

struct ObjectHitbox sYellowCoinHitbox = {
    /* interactType: */ INTERACT_COIN,
    /* downOffset: */ 0,
    /* damageOrCoinValue: */ 1,
    /* health: */ 0,
    /* numLootCoins: */ 0,
    /* radius: */ 100,
    /* height: */ 64,
    /* hurtboxRadius: */ 0,
    /* hurtboxHeight: */ 0,
};

s16 D_8032F2A4[][2] = { { 0, -150 },  { 0, -50 },   { 0, 50 },   { 0, 150 },
                        { -50, 100 }, { -100, 50 }, { 50, 100 }, { 100, 50 } };

s32 bhv_coin_sparkles_init(void) {
    if (o->oInteractStatus & INT_STATUS_INTERACTED && !(o->oInteractStatus & INT_STATUS_TOUCHED_BOB_OMB)) {
        spawn_object(o, MODEL_SPARKLES, bhvGoldenCoinSparkles);
        obj_mark_for_deletion(o);
        return 1;
    }
    o->oInteractStatus = 0;
    return 0;
}

void bhv_yellow_coin_init(void) {
    cur_obj_set_behavior(bhvYellowCoin);
    obj_set_hitbox(o, &sYellowCoinHitbox);
    bhv_init_room();
    cur_obj_update_floor_height();
    if (500.0f < absf(o->oPosY - o->oFloorHeight))
        cur_obj_set_model(MODEL_YELLOW_COIN_NO_SHADOW);
    if (o->oFloorHeight < -10000.0f)
        obj_mark_for_deletion(o);
}

void bhv_yellow_coin_loop(void) {
    bhv_coin_sparkles_init();
    o->oAnimState++;
}

void bhv_temp_coin_loop(void) {
    o->oAnimState++;
    if (cur_obj_wait_then_blink(200, 20))
        obj_mark_for_deletion(o);
    bhv_coin_sparkles_init();
}

void bhv_coin_init(void) {
    o->oVelY = random_float() * 10.0f + 30 + o->oCoinUnk110;
    o->oForwardVel = random_float() * 10.0f;
    o->oMoveAngleYaw = random_u16();
    cur_obj_set_behavior(bhvYellowCoin);
    obj_set_hitbox(o, &sYellowCoinHitbox);
    cur_obj_become_intangible();
}

void bhv_coin_loop(void) {
    struct Surface *sp1C;
    s16 sp1A;
    cur_obj_update_floor_and_walls();
    cur_obj_if_hit_wall_bounce_away();
    cur_obj_move_standard(-62);
    if ((sp1C = o->oFloor) != NULL) {
        if (o->oMoveFlags & OBJ_MOVE_ON_GROUND)
            o->oSubAction = 1;
        if (o->oSubAction == 1) {
            o->oBounciness = 0;
            if (sp1C->normal.y < 0.9) {
                sp1A = atan2s(sp1C->normal.z, sp1C->normal.x);
                cur_obj_rotate_yaw_toward(sp1A, 0x400);
            }
        }
    }
    if (o->oTimer == 0)
#ifdef VERSION_US
        cur_obj_play_sound_2(SOUND_GENERAL_COIN_SPURT_2);
#elif VERSION_EU
        cur_obj_play_sound_2(SOUND_GENERAL_COIN_SPURT_EU);
#else
        cur_obj_play_sound_2(SOUND_GENERAL_COIN_SPURT);
#endif
    if (o->oVelY < 0)
        cur_obj_become_tangible();
    if (o->oMoveFlags & OBJ_MOVE_LANDED) {
#ifndef VERSION_JP
        if (o->oMoveFlags & (OBJ_MOVE_ABOVE_DEATH_BARRIER | OBJ_MOVE_ABOVE_LAVA))
#else
        if (o->oMoveFlags & OBJ_MOVE_ABOVE_LAVA)
#endif
            obj_mark_for_deletion(o);
    }
#ifndef VERSION_JP
    if (o->oMoveFlags & OBJ_MOVE_13) {
        if (o->oCoinUnk1B0 < 5)
            cur_obj_play_sound_2(0x30364081);
        o->oCoinUnk1B0++;
    }
#else
    if (o->oMoveFlags & OBJ_MOVE_13)
        cur_obj_play_sound_2(SOUND_GENERAL_COIN_DROP);
#endif
    if (cur_obj_wait_then_blink(400, 20))
        obj_mark_for_deletion(o);
    bhv_coin_sparkles_init();
}

void bhv_coin_formation_spawn_loop(void) {
    if (o->oTimer == 0) {
        cur_obj_set_behavior(bhvYellowCoin);
        obj_set_hitbox(o, &sYellowCoinHitbox);
        bhv_init_room();
        if (o->oCoinUnkF8) {
            o->oPosY += 300.0f;
            cur_obj_update_floor_height();
            if (o->oPosY < o->oFloorHeight || o->oFloorHeight < -10000.0f)
                obj_mark_for_deletion(o);
            else
                o->oPosY = o->oFloorHeight;
        } else {
            cur_obj_update_floor_height();
            if (absf(o->oPosY - o->oFloorHeight) > 250.0f)
                cur_obj_set_model(MODEL_YELLOW_COIN_NO_SHADOW);
        }
    } else {
        if (bhv_coin_sparkles_init())
            o->parentObj->oCoinUnkF4 |= bit_shift_left(o->oBehParams2ndByte);
        o->oAnimState++;
    }
    if (o->parentObj->oAction == 2)
        obj_mark_for_deletion(o);
}

void spawn_coin_in_formation(s32 sp50, s32 sp54) {
    struct Object *sp4C;
    Vec3i sp40;
    s32 sp3C = 1;
    s32 sp38 = 1;
    UNUSED s32 unused;
    sp40[2] = 0;
    sp40[0] = (sp40[1] = sp40[2]);
    switch (sp54 & 7) {
        case 0:
            sp40[2] = 160 * (sp50 - 2);
            if (sp50 > 4)
                sp3C = 0;
            break;
        case 1:
            sp38 = 0;
            sp40[1] = 160 * sp50 * 0.8; // 128 * sp50
            if (sp50 > 4)
                sp3C = 0;
            break;
        case 2:
            sp40[0] = sins(sp50 << 13) * 300.0f;
            sp40[2] = coss(sp50 << 13) * 300.0f;
            break;
        case 3:
            sp38 = 0;
            sp40[0] = coss(sp50 << 13) * 200.0f;
            sp40[1] = sins(sp50 << 13) * 200.0f + 200.0f;
            break;
        case 4:
            sp40[0] = D_8032F2A4[sp50][0];
            sp40[2] = D_8032F2A4[sp50][1];
            break;
    }
    if (sp54 & 0x10)
        sp38 = 0;
    if (sp3C) {
        sp4C = spawn_object_relative(sp50, sp40[0], sp40[1], sp40[2], o, MODEL_YELLOW_COIN,
                                     bhvCoinFormationSpawn);
        sp4C->oCoinUnkF8 = sp38;
    }
}

void bhv_coin_formation_init(void) {
    o->oCoinUnkF4 = (o->oBehParams >> 8) & 0xFF;
}

void bhv_coin_formation_loop(void) {
    s32 bitIndex;
    switch (o->oAction) {
        case 0:
            if (o->oDistanceToMario < 2000.0f) {
                for (bitIndex = 0; bitIndex < 8; bitIndex++) {
                    if (!(o->oCoinUnkF4 & (1 << bitIndex)))
                        spawn_coin_in_formation(bitIndex, o->oBehParams2ndByte);
                }
                o->oAction++;
            }
            break;
        case 1:
            if (o->oDistanceToMario > 2100.0f)
                o->oAction++;
            break;
        case 2:
            o->oAction = 0;
            break;
    }

    // Casting to u8 doesn't seem to match
    set_object_respawn_info_bits(o, o->oCoinUnkF4 & 0xFF);
}

void coin_inside_boo_act_1(void) {
    cur_obj_update_floor_and_walls();
    cur_obj_if_hit_wall_bounce_away();
    if (o->oMoveFlags & OBJ_MOVE_13)
        cur_obj_play_sound_2(SOUND_GENERAL_COIN_DROP);
    if (o->oTimer > 90 || (o->oMoveFlags & OBJ_MOVE_LANDED)) {
        obj_set_hitbox(o, &sYellowCoinHitbox);
        cur_obj_become_tangible();
        cur_obj_set_behavior(bhvYellowCoin);
    }
    cur_obj_move_standard(-30);
    bhv_coin_sparkles_init();
    if (cur_obj_has_model(MODEL_BLUE_COIN))
        o->oDamageOrCoinValue = 5;
    if (cur_obj_wait_then_blink(400, 20))
        obj_mark_for_deletion(o);
}

void coin_inside_boo_act_0(void) {
    s16 sp26;
    f32 sp20;
    struct Object *parent = o->parentObj;
    cur_obj_become_intangible();
    if (o->oTimer == 0 && gCurrLevelNum == LEVEL_BBH) {
        cur_obj_set_model(MODEL_BLUE_COIN);
        cur_obj_scale(0.7);
    }
    obj_copy_pos(o, parent);
    if (parent->oBooDeathStatus == BOO_DEATH_STATUS_DYING) {
        o->oAction = 1;
        sp26 = gMarioObject->oMoveAngleYaw;
        sp20 = 3.0f;
        o->oVelX = sins(sp26) * sp20;
        o->oVelZ = coss(sp26) * sp20;
        o->oVelY = 35.0f;
    }
}

void (*sCoinInsideBooActions[])(void) = { coin_inside_boo_act_0, coin_inside_boo_act_1 };

void bhv_coin_inside_boo_loop(void) {
    cur_obj_call_action_function(sCoinInsideBooActions);
}

void bhv_coin_sparkles_loop(void) {
    cur_obj_scale(0.6f);
}

void bhv_golden_coin_sparkles_loop(void) {
    struct Object *sp2C;
    UNUSED s32 unused;
    f32 sp24 = 30.0f;
    sp2C = spawn_object(o, MODEL_SPARKLES, bhvCoinSparkles);
    sp2C->oPosX += random_float() * sp24 - sp24 / 2;
    sp2C->oPosZ += random_float() * sp24 - sp24 / 2;
}
