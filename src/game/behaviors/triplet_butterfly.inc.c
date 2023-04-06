struct TripletButterflyActivationData {
    s32 model;
    const BehaviorScript *behavior;
    f32 scale;
};

static struct ObjectHitbox sTripletButterflyExplodeHitbox = {
    /* interactType:      */ INTERACT_MR_BLIZZARD,
    /* downOffset:        */ 50,
    /* damageOrCoinValue: */ 2,
    /* health:            */ 1,
    /* numLootCoins:      */ 0,
    /* radius:            */ 100,
    /* height:            */ 50,
    /* hurtboxRadius:     */ 100,
    /* hurtboxHeight:     */ 50,
};

static struct TripletButterflyActivationData sTripletButterflyActivationData[] = {
    { MODEL_BOWLING_BALL, NULL, 0.5f },
    { MODEL_1UP, bhv1upWalking, 1.0f },
};

static void triplet_butterfly_act_init(void) {
    s32 butterflyNum;
    s32 i;

    butterflyNum = o->oBehParams2ndByte & TRIPLET_BUTTERFLY_BP_BUTTERFLY_NUM;
    if (butterflyNum != 0 || o->oDistanceToMario < 200.0f) {
        if (butterflyNum == 0) {
            for (i = 1; i <= 2; i++) {
                spawn_object_relative(i, 0, 0, 0, o, MODEL_BUTTERFLY, bhvTripletButterfly);
            }

            o->oTripletButterflySelectedButterfly = random_u16() % 3;
        }

        //! TODO: Describe this glitch
        if (o->parentObj->oTripletButterflySelectedButterfly == o->oBehParams2ndByte) {
            o->oTripletButterflyType = TRIPLET_BUTTERFLY_TYPE_SPAWN_1UP;
        } else if (o->parentObj->oBehParams2ndByte & TRIPLET_BUTTERFLY_BP_NO_BOMBS) {
            o->oTripletButterflyType = TRIPLET_BUTTERFLY_TYPE_NORMAL;
        }
        // Default butterfly type is TRIPLET_BUTTERFLY_TYPE_EXPLODES

        o->oAction = TRIPLET_BUTTERFLY_ACT_WANDER;

        o->oTripletButterflyBaseYaw = o->oBehParams2ndByte * (0x10000 / 3);
        o->oMoveAngleYaw = (s32)(o->oTripletButterflyBaseYaw + random_linear_offset(0, 0x5555));
        o->oTripletButterflySpeed = random_linear_offset(15, 15);

        cur_obj_unhide();
    }
}

static void triplet_butterfly_act_wander(void) {
    if (o->oDistanceToMario > 1500.0f) {
        obj_mark_for_deletion(o);
    } else {
        approach_f32_ptr(&o->oTripletButterflySpeed, 8.0f, 0.5f);
        if (o->oTimer < 60) {
            o->oTripletButterflyTargetYaw = cur_obj_angle_to_home();
        } else {
            o->oTripletButterflyTargetYaw = (s32) o->oTripletButterflyBaseYaw;

            if (o->oTimer > 110 && o->oDistanceToMario < 200.0f
                && o->oTripletButterflyType > TRIPLET_BUTTERFLY_TYPE_NORMAL) {
                o->oAction = TRIPLET_BUTTERFLY_ACT_ACTIVATE;
                o->oTripletButterflySpeed = 0.0f;
            }
        }

        if (o->oHomeY < o->oFloorHeight) {
            o->oHomeY = o->oFloorHeight;
        }

        if (o->oPosY < o->oHomeY + random_linear_offset(50, 50)) {
            o->oTripletButterflyTargetPitch = -0x2000;
        } else {
            o->oTripletButterflyTargetPitch = 0x2000;
        }

        obj_move_pitch_approach(o->oTripletButterflyTargetPitch, 400);
        cur_obj_rotate_yaw_toward(o->oTripletButterflyTargetYaw, random_linear_offset(400, 800));
    }
}

static void triplet_butterfly_act_activate(void) {
    if (o->oTimer > 20) {
        if (o->oTripletButterflyModel == 0) {
            spawn_object_relative_with_scale(0, 0, -40, 0, 1.5f, o, MODEL_SMOKE, bhvWhitePuffSmoke2);
            o->oTripletButterflyModel = sTripletButterflyActivationData[o->oTripletButterflyType].model;
            cur_obj_set_model(o->oTripletButterflyModel);
            obj_set_billboard(o);
            o->oTripletButterflyScale = 0.0f;
            o->oHomeY = o->oPosY;
        } else if (o->oTripletButterflyScale
                   >= sTripletButterflyActivationData[o->oTripletButterflyType].scale) {
            if (o->oTripletButterflyType != TRIPLET_BUTTERFLY_TYPE_EXPLODES) {
                spawn_object(o, o->oTripletButterflyModel,
                             sTripletButterflyActivationData[o->oTripletButterflyType].behavior);
                obj_mark_for_deletion(o);
            } else {
                o->oAction = TRIPLET_BUTTERFLY_ACT_EXPLODE;
                o->oWallHitboxRadius = 100.0f;
            }
        }

        o->oTripletButterflyScale +=
            sTripletButterflyActivationData[o->oTripletButterflyType].scale / 30.0f;
        if (o->oTripletButterflyType == TRIPLET_BUTTERFLY_TYPE_EXPLODES) {
            o->oGraphYOffset = 250.0f * o->oTripletButterflyScale;
            o->oPosY = o->oHomeY - o->oGraphYOffset;
        }
    }
}

static void triplet_butterfly_act_explode(void) {
    f32 scaleIncrease;

    obj_check_attacks(&sTripletButterflyExplodeHitbox, -1);

    if (o->oAction == -1 || (o->oMoveFlags & 0x00000200) || o->oTimer >= 158) {
        o->oPosY += o->oGraphYOffset;
        spawn_object(o, MODEL_EXPLOSION, bhvExplosion);
        obj_mark_for_deletion(o);
    } else {
        if (o->oTimer > 120) {
            scaleIncrease = 0.04f * coss(o->oTripletButterflyScalePhase);
            if (scaleIncrease > 0.0f) {
                scaleIncrease *= 4.5f;
                o->oTripletButterflyScalePhase += 10000;
            } else {
                o->oTripletButterflyScalePhase += 4000;
            }

            o->oTripletButterflyScale += scaleIncrease;
        }

        approach_f32_ptr(&o->oTripletButterflySpeed, 20.0f, 1.0f);
        cur_obj_rotate_yaw_toward(o->oAngleToMario, 800);
        obj_turn_pitch_toward_mario(-100.0f, 800);
    }
}

void bhv_triplet_butterfly_update(void) {
    cur_obj_update_floor_and_walls();

    switch (o->oAction) {
        case TRIPLET_BUTTERFLY_ACT_INIT:
            triplet_butterfly_act_init();
            break;
        case TRIPLET_BUTTERFLY_ACT_WANDER:
            triplet_butterfly_act_wander();
            break;
        case TRIPLET_BUTTERFLY_ACT_ACTIVATE:
            triplet_butterfly_act_activate();
            break;
        case TRIPLET_BUTTERFLY_ACT_EXPLODE:
            triplet_butterfly_act_explode();
            break;
    }

    cur_obj_scale(o->oTripletButterflyScale);
    obj_compute_vel_from_move_pitch(o->oTripletButterflySpeed);
    cur_obj_move_standard(78);
}
