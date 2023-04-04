// bowser_flame.inc.c

struct ObjectHitbox sGrowingBowserFlameHitbox = {
    /* interactType:      */ INTERACT_FLAME,
    /* downOffset:        */ 20,
    /* damageOrCoinValue: */ 1,
    /* health:            */ 0,
    /* numLootCoins:      */ 0,
    /* radius:            */ 10,
    /* height:            */ 40,
    /* hurtboxRadius:     */ 0,
    /* hurtboxHeight:     */ 0,
};

struct ObjectHitbox sBowserFlameHitbox = {
    /* interactType:      */ INTERACT_FLAME,
    /* downOffset:        */ 0,
    /* damageOrCoinValue: */ 1,
    /* health:            */ 0,
    /* numLootCoins:      */ 0,
    /* radius:            */ 10,
    /* height:            */ 40,
    /* hurtboxRadius:     */ 0,
    /* hurtboxHeight:     */ 0,
};

void bowser_flame_despawn(void) {
    obj_mark_for_deletion(o);
    spawn_object_with_scale(o, MODEL_NONE, bhvBlackSmokeUpward, 1.0f);
    if (random_float() < 0.1) {
        spawn_object(o, MODEL_YELLOW_COIN, bhvTemporaryYellowCoin);
    }
}

s32 bowser_flame_should_despawn(s32 maxTime) {
    if (maxTime < o->oTimer) {
        return TRUE;
    }

    // Flames should despawn if they fall off the arena.
    if (o->oFloorType == SURFACE_BURNING) {
        return TRUE;
    }
    if (o->oFloorType == SURFACE_DEATH_PLANE) {
        return TRUE;
    }

    return FALSE;
}

void bhv_flame_bowser_init(void) {
    o->oAnimState = (s32)(random_float() * 10.0f);
    o->oMoveAngleYaw = random_u16();
    o->oVelY = random_float() < 0.2 ? 80.0f : 20.0f;
    o->oForwardVel = 10.0f;
    o->oGravity = -1.0f;
    o->oFlameScale = random_float() + 1.0f;
}

void bhv_flame_large_burning_out_init(void) {
    o->oAnimState = (s32)(random_float() * 10.0f);
    o->oMoveAngleYaw = random_u16();
    o->oVelY = 10.0f;
    o->oForwardVel = 0.0f;
    o->oFlameScale = 7.0f;
}

void bowser_flame_move(void) {
    s32 timer = ((o->oFlameSpeedTimerOffset + gGlobalTimer) & 0x3F) << 10;
    o->oPosX += sins(o->oMoveAngleYaw) * sins(timer) * 4.0f;
    o->oPosZ += coss(o->oMoveAngleYaw) * sins(timer) * 4.0f;
}

void bhv_flame_bowser_loop(void) {
    cur_obj_update_floor_and_walls();
    cur_obj_move_standard(78);

    if (o->oVelY < -4.0f) {
        o->oVelY = -4.0f;
    }

    if (o->oAction == 0) {
        cur_obj_become_intangible();
        bowser_flame_move();

        if (o->oMoveFlags & OBJ_MOVE_LANDED) {
            o->oAction++;
            if (cur_obj_has_behavior(bhvFlameLargeBurningOut)) {
                o->oFlameScale = 8.0f;
            } else {
                o->oFlameScale = random_float() * 2 + 6.0f;
            }
            o->oForwardVel = 0;
            o->oVelY = 0;
            o->oGravity = 0;
        }
    } else {
        cur_obj_become_tangible();

        if (o->oTimer > o->oFlameScale * 10 + 5.0f) {
            o->oFlameScale -= 0.15;
            if (o->oFlameScale <= 0) {
                bowser_flame_despawn();
            }
        }
    }

    cur_obj_scale(o->oFlameScale);
    o->oGraphYOffset = o->header.gfx.scale[1] * 14.0f;
    obj_set_hitbox(o, &sBowserFlameHitbox);
}

void bhv_flame_moving_forward_growing_init(void) {
    o->oForwardVel = 30.0f;
    obj_translate_xz_random(o, 80.0f);
    o->oAnimState = (s32)(random_float() * 10.0f);
    o->oFlameScale = 3.0f;
}

void bhv_flame_moving_forward_growing_loop(void) {
    UNUSED u8 filler[4];
    UNUSED struct Object *flame;

    obj_set_hitbox(o, &sGrowingBowserFlameHitbox);
    o->oFlameScale = o->oFlameScale + 0.5;
    cur_obj_scale(o->oFlameScale);

    if (o->oMoveAnglePitch > 0x800) {
        o->oMoveAnglePitch -= 0x200;
    }

    cur_obj_set_pos_via_transform();
    cur_obj_update_floor_height();

    if (o->oFlameScale > 30.0f) {
        obj_mark_for_deletion(o);
    }

    if (o->oPosY < o->oFloorHeight) {
        o->oPosY = o->oFloorHeight;
        flame = spawn_object(o, MODEL_RED_FLAME, bhvFlameBowser);
        obj_mark_for_deletion(o);
    }
}

void bhv_flame_floating_landing_init(void) {
    o->oAnimState = (s32)(random_float() * 10.0f);
    o->oMoveAngleYaw = random_u16();
    if (o->oBehParams2ndByte != 0) {
        o->oForwardVel = random_float() * 5.0f;
    } else {
        o->oForwardVel = random_float() * 70.0f;
    }
    o->oVelY = random_float() * 20.0f;
    o->oGravity = -1.0f;
    o->oFlameSpeedTimerOffset = random_float() * 64.0f;
}

f32 sFlameFloatingYLimit[] = { -8.0f, -6.0f, -3.0f };

void bhv_flame_floating_landing_loop(void) {
    UNUSED u8 filler[4];

    cur_obj_update_floor_and_walls();
    cur_obj_move_standard(78);
    bowser_flame_move();

    if (bowser_flame_should_despawn(900)) {
        obj_mark_for_deletion(o);
    }

    if (o->oVelY < sFlameFloatingYLimit[o->oBehParams2ndByte]) {
        o->oVelY = sFlameFloatingYLimit[o->oBehParams2ndByte];
    }

    if (o->oMoveFlags & OBJ_MOVE_LANDED) {
        if (o->oBehParams2ndByte == 0) {
            spawn_object(o, MODEL_RED_FLAME, bhvFlameLargeBurningOut);
        } else {
            spawn_object(o, MODEL_NONE, bhvBlueFlamesGroup); //? wonder if they meant MODEL_BLUE_FLAME?
        }
        obj_mark_for_deletion(o);
    }

    o->oGraphYOffset = o->header.gfx.scale[1] * 14.0f;
}

void bhv_blue_bowser_flame_init(void) {
    obj_translate_xz_random(o, 80.0f);
    o->oAnimState = (s32)(random_float() * 10.0f);
    o->oVelY = 7.0f;
    o->oForwardVel = 35.0f;
    o->oFlameScale = 3.0f;
    o->oFlameUnusedRand = random_float() * 0.5;
    o->oGravity = 1.0f;
    o->oFlameSpeedTimerOffset = (s32)(random_float() * 64.0f);
}

void bhv_blue_bowser_flame_loop(void) {
    s32 i;

    obj_set_hitbox(o, &sGrowingBowserFlameHitbox);

    if (o->oFlameScale < 16.0f) {
        o->oFlameScale = o->oFlameScale + 0.5;
    }

    cur_obj_scale(o->oFlameScale);
    cur_obj_update_floor_and_walls();
    cur_obj_move_standard(78);

    if (o->oTimer > 20) {
        if (o->oBehParams2ndByte == 0) {
            for (i = 0; i < 3; i++) {
                spawn_object_relative_with_scale(0, 0, 0, 0, 5.0f, o, MODEL_RED_FLAME,
                                                 bhvFlameFloatingLanding);
            }
        } else {
            spawn_object_relative_with_scale(1, 0, 0, 0, 8.0f, o, MODEL_BLUE_FLAME,
                                             bhvFlameFloatingLanding);
            spawn_object_relative_with_scale(2, 0, 0, 0, 8.0f, o, MODEL_BLUE_FLAME,
                                             bhvFlameFloatingLanding);
        }
        obj_mark_for_deletion(o);
    }
}

void bhv_flame_bouncing_init(void) {
    o->oAnimState = (s32)(random_float() * 10.0f);
    o->oVelY = 30.0f;
    o->oForwardVel = 20.0f;
    o->oFlameScale = o->header.gfx.scale[0];
    o->oFlameSpeedTimerOffset = (s32)(random_float() * 64.0f);
}

void bhv_flame_bouncing_loop(void) {
    struct Object *bowser;

    if (o->oTimer == 0) {
        o->oFlameBowser = cur_obj_nearest_object_with_behavior(bhvBowser);
    }

    bowser = o->oFlameBowser;
    o->oForwardVel = 15.0f;
    o->oBounciness = -1.0f;
    cur_obj_scale(o->oFlameScale);
    obj_set_hitbox(o, &sGrowingBowserFlameHitbox);
    cur_obj_update_floor_and_walls();
    cur_obj_move_standard(78);

    if (bowser_flame_should_despawn(300)) {
        obj_mark_for_deletion(o);
    }

    if (bowser != NULL) {
        if (bowser->oHeldState == HELD_FREE) {
            if (lateral_dist_between_objects(o, bowser) < 300.0f) {
                obj_mark_for_deletion(o);
            }
        }
    }
}

void bhv_blue_flames_group_loop(void) {
    struct Object *flame;
    s32 i;

    if (o->oTimer == 0) {
        o->oMoveAngleYaw = obj_angle_to_object(o, gMarioObject);
        o->oBlueFlameNextScale = 5.0f;
    }

    if (o->oTimer < 16) {
        if (!(o->oTimer & 1)) {
            for (i = 0; i < 3; i++) {
                flame = spawn_object(o, MODEL_BLUE_FLAME, bhvFlameBouncing);
                flame->oMoveAngleYaw += i * 0x5555;
                flame->header.gfx.scale[0] = o->oBlueFlameNextScale;
            }
            o->oBlueFlameNextScale -= 0.5;
        }
    } else {
        obj_mark_for_deletion(o);
    }
}
