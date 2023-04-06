// treasure_chest.c.inc

/**
 * Hitbox for treasure chest bottom.
 */
static struct ObjectHitbox sTreasureChestBottomHitbox = {
    /* interactType:      */ INTERACT_SHOCK,
    /* downOffset:        */ 0,
    /* damageOrCoinValue: */ 1,
    /* health:            */ 0,
    /* numLootCoins:      */ 0,
    /* radius:            */ 300,
    /* height:            */ 300,
    /* hurtboxRadius:     */ 310,
    /* hurtboxHeight:     */ 310,
};

void bhv_treasure_chest_top_loop(void) {
    struct Object *sp34 = o->parentObj->parentObj;

    switch (o->oAction) {
        case 0:
            if (o->parentObj->oAction == 1)
                o->oAction = 1;
            break;

        case 1:
            if (o->oTimer == 0) {
                if (sp34->oTreasureChestUnkFC == 0) {
                    spawn_object_relative(0, 0, -80, 120, o, MODEL_BUBBLE, bhvWaterAirBubble);
                    play_sound(SOUND_GENERAL_CLAM_SHELL1, o->header.gfx.cameraToObject);
                } else {
                    play_sound(SOUND_GENERAL_OPEN_CHEST, o->header.gfx.cameraToObject);
                }
            }

            o->oFaceAnglePitch += -0x200;
            if (o->oFaceAnglePitch < -0x4000) {
                o->oFaceAnglePitch = -0x4000;
                o->oAction++;
                if (o->parentObj->oBehParams2ndByte != 4)
                    spawn_orange_number(o->parentObj->oBehParams2ndByte, 0, -40, 0);
            }
            break;

        case 2:
            if (o->parentObj->oAction == 0)
                o->oAction = 3;
            break;

        case 3:
            o->oFaceAnglePitch += 0x800;
            if (o->oFaceAnglePitch > 0) {
                o->oFaceAnglePitch = 0;
                o->oAction = 0;
            }
    }
}

void bhv_treasure_chest_bottom_init(void) {
    spawn_object_relative(0, 0, 102, -77, o, MODEL_TREASURE_CHEST_LID, bhvTreasureChestTop);
    obj_set_hitbox(o, &sTreasureChestBottomHitbox);
}

void bhv_treasure_chest_bottom_loop(void) {
    switch (o->oAction) {
        case 0:
            if (obj_check_if_facing_toward_angle(o->oMoveAngleYaw, gMarioObject->header.gfx.angle[1] + 0x8000, 0x3000)) {
                if (is_point_within_radius_of_mario(o->oPosX, o->oPosY, o->oPosZ, 150)) {
                    if (!o->parentObj->oTreasureChestUnkF8) {
                        if (o->parentObj->oTreasureChestUnkF4 == o->oBehParams2ndByte) {
                            play_sound(SOUND_GENERAL2_RIGHT_ANSWER, gDefaultSoundArgs);
                            o->parentObj->oTreasureChestUnkF4++;
                            o->oAction = 1;
                        } else {
                            o->parentObj->oTreasureChestUnkF4 = 1;
                            o->parentObj->oTreasureChestUnkF8 = 1;
                            o->oAction = 2;
                            cur_obj_become_tangible();
                            play_sound(SOUND_MENU_CAMERA_BUZZ, gDefaultSoundArgs);
                        }
                    }
                }
            }
            break;

        case 1:
            if (o->parentObj->oTreasureChestUnkF8 == 1)
                o->oAction = 0;
            break;

        case 2:
            cur_obj_become_intangible();
            if (!is_point_within_radius_of_mario(o->oPosX, o->oPosY, o->oPosZ, 500)) {
                o->parentObj->oTreasureChestUnkF8 = 0;
                o->oAction = 0;
            }
    }

    cur_obj_push_mario_away_from_cylinder(150.0f, 150.0f);
    o->oInteractStatus = 0;
}

void spawn_treasure_chest(s8 sp3B, s32 sp3C, s32 sp40, s32 sp44, s16 sp4A) {
    struct Object *sp34;
    sp34 = spawn_object_abs_with_rot(o, 0, MODEL_TREASURE_CHEST_BASE, bhvTreasureChestBottom, sp3C,
                                     sp40, sp44, 0, sp4A, 0);
    sp34->oBehParams2ndByte = sp3B;
}

void bhv_treasure_chest_ship_init(void) {
    spawn_treasure_chest(1, 400, -350, -2700, 0);
    spawn_treasure_chest(2, 650, -350, -940, -0x6001);
    spawn_treasure_chest(3, -550, -350, -770, 0x5FFF);
    spawn_treasure_chest(4, 100, -350, -1700, 0);
    o->oTreasureChestUnkF4 = 1;
    o->oTreasureChestUnkFC = 0;
}

void bhv_treasure_chest_ship_loop(void) {
    switch (o->oAction) {
        case 0:
            if (o->oTreasureChestUnkF4 == 5) {
                play_puzzle_jingle();
                fade_volume_scale(0, 127, 1000);
                o->oAction = 1;
            }
            break;

        case 1:
            if (gEnvironmentRegions != NULL) {
                gEnvironmentRegions[6] += -5;
                play_sound(SOUND_ENV_WATER_DRAIN, gDefaultSoundArgs);
                set_environmental_camera_shake(SHAKE_ENV_JRB_SHIP_DRAIN);
                if (gEnvironmentRegions[6] < -335) {
                    gEnvironmentRegions[6] = -335;
                    o->activeFlags = 0;
                }
            }
            break;
    }
}

void bhv_treasure_chest_jrb_init(void) {
    spawn_treasure_chest(1, -1700, -2812, -1150, 0x7FFF);
    spawn_treasure_chest(2, -1150, -2812, -1550, 0x7FFF);
    spawn_treasure_chest(3, -2400, -2812, -1800, 0x7FFF);
    spawn_treasure_chest(4, -1800, -2812, -2100, 0x7FFF);
    o->oTreasureChestUnkF4 = 1;
    o->oTreasureChestUnkFC = 1;
}

void bhv_treasure_chest_jrb_loop(void) {
    switch (o->oAction) {
        case 0:
            if (o->oTreasureChestUnkF4 == 5) {
                play_puzzle_jingle();
                o->oAction = 1;
            }
            break;

        case 1:
            if (o->oTimer == 60) {
                spawn_mist_particles();
                spawn_default_star(-1800.0f, -2500.0f, -1700.0f);
                o->oAction = 2;
            }
            break;

        case 2:
            break;
    }
}

void bhv_treasure_chest_init(void) {
    spawn_treasure_chest(1, -4500, -5119, 1300, -0x6001);
    spawn_treasure_chest(2, -1800, -5119, 1050, 0x1FFF);
    spawn_treasure_chest(3, -4500, -5119, -1100, 9102);
    spawn_treasure_chest(4, -2400, -4607, 125, 16019);

    o->oTreasureChestUnkF4 = 1;
    o->oTreasureChestUnkFC = 0;
}

void bhv_treasure_chest_loop(void) {
    switch (o->oAction) {
        case 0:
            if (o->oTreasureChestUnkF4 == 5) {
                play_puzzle_jingle();
                o->oAction = 1;
            }
            break;

        case 1:
            if (o->oTimer == 60) {
                spawn_mist_particles();
                spawn_default_star(-1900.0f, -4000.0f, -1400.0f);
                o->oAction = 2;
            }
            break;

        case 2:
            break;
    }
}
