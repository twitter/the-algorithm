// bowser_falling_platform.inc.c

struct BowserFallingPlatformData {
    const Collision *collision;
    s16 posX;
    s16 posZ;
    s16 angle;
};

struct BowserFallingPlatformData sBowserFallingPlatform[] = {
    { NULL, 0, 0, 0 },
    { bowser_3_seg7_collision_07004B94,  -800, -1000, -20992 },
    { bowser_3_seg7_collision_07004C18, -1158,   390, -18432 },
    { bowser_3_seg7_collision_07004C9C, -1158,   390,  -7680 },
    { bowser_3_seg7_collision_07004D20,     0,  1240,  -6144 },
    { bowser_3_seg7_collision_07004DA4,     0,  1240,   6144 },
    { bowser_3_seg7_collision_07004E28,  1158,   390,   7680 },
    { bowser_3_seg7_collision_07004EAC,  1158,   390,  18432 },
    { bowser_3_seg7_collision_07004F30,   800, -1000,  20992 },
    { bowser_3_seg7_collision_07004FB4,   800, -1000, -31744 },
    { bowser_3_seg7_collision_07005038,  -800, -1000,  31744 },
};

void falling_bowser_plat_act_start(void) {
    o->oBitsPlatformBowser = cur_obj_nearest_object_with_behavior(bhvBowser);
    obj_set_collision_data(o, sBowserFallingPlatform[o->oBehParams2ndByte].collision);
    if (o->oBitsPlatformBowser != NULL) {
        o->oAction = BOWSER_BITS_PLAT_ACT_CHECK;
    }
}

void falling_bowser_plat_act_check(void) {
    UNUSED u8 filler[4];
    struct Object *bowser = o->oBitsPlatformBowser;

    if (bowser->platform == o) {
        if (bowser->oAction == BOWSER_ACT_BIG_JUMP
            && bowser->oBowserStatus & BOWSER_STATUS_BIG_JUMP) {
            o->oAction = BOWSER_BITS_PLAT_ACT_FALL;
        }
    }

    if (bowser->oHealth == 1
        && (bowser->oAction == BOWSER_ACT_DANCE || bowser->oHeldState != HELD_FREE)) {
        o->oSubAction = 1;
    }

    if (o->oSubAction == 0) {
        o->oBitsPlatformTimer = 0;
    } else {
        if ((gDebugInfo[DEBUG_PAGE_EFFECTINFO][6] + 20)
            * (o->oBehParams2ndByte - 1) < o->oBitsPlatformTimer) {
            o->oAction = BOWSER_BITS_PLAT_ACT_FALL;
        }
        o->oBitsPlatformTimer++;
    }
}

void falling_bowser_plat_act_fall(void) {
    Vec3f pos;
    s16 angle;
    f32 val;
    UNUSED struct Object *bowser = o->oBitsPlatformBowser;

    if (o->oTimer == 0 || o->oTimer == 22) {
        cur_obj_play_sound_2(SOUND_GENERAL_BOWSER_PLATFORM_2);
    }

    if (o->oTimer < 22) {
        set_environmental_camera_shake(SHAKE_ENV_FALLING_BITS_PLAT);
        o->oVelY = 8.0f;
        o->oGravity = 0.0f;
    } else {
        o->oGravity = -4.0f;
    }

    if (!(o->oTimer & 1) && o->oTimer < 14) {
        angle = sBowserFallingPlatform[o->oBehParams2ndByte].angle
                    + (gDebugInfo[DEBUG_PAGE_EFFECTINFO][1] << 8);
        val = -(o->oTimer / 2) * 290 + 1740;
        vec3f_copy_2(pos, &o->oPosX);
        o->oPosX = sBowserFallingPlatform[o->oBehParams2ndByte].posX + sins(angle + 0x14B0) * val;
        o->oPosZ = sBowserFallingPlatform[o->oBehParams2ndByte].posZ + coss(angle + 0x14B0) * val;
        o->oPosY = 307.0f;
        spawn_mist_particles_variable(4, 0, 100.0f);
        o->oPosX = sBowserFallingPlatform[o->oBehParams2ndByte].posX + sins(angle - 0x14B0) * val;
        o->oPosZ = sBowserFallingPlatform[o->oBehParams2ndByte].posZ + coss(angle - 0x14B0) * val;
        spawn_mist_particles_variable(4, 0, 100);
        vec3f_copy_2(&o->oPosX, pos);
    }

    cur_obj_move_using_fvel_and_gravity();

    if (o->oTimer > 300) {
        obj_mark_for_deletion(o);
    }
}

void (*sFallingBowserPlatformActions[])(void) = {
    falling_bowser_plat_act_start,
    falling_bowser_plat_act_check,
    falling_bowser_plat_act_fall,
};

void bhv_falling_bowser_platform_loop(void) {
    cur_obj_call_action_function(sFallingBowserPlatformActions);
}
