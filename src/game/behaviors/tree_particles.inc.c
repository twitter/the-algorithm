// tree_particles.inc.c

void bhv_tree_snow_or_leaf_loop(void) {
    cur_obj_update_floor_height();

    if (o->oTimer == 0) {
        o->oAngleVelPitch = (random_float() - 0.5) * 0x1000;
        o->oAngleVelRoll = (random_float() - 0.5) * 0x1000;
        o->oTreeSnowOrLeafUnkF8 = 4;
        o->oTreeSnowOrLeafUnkFC = random_float() * 0x400 + 0x600;
    }

    if (o->oPosY < o->oFloorHeight) {
        obj_mark_for_deletion(o);
    }
    if (o->oFloorHeight < FLOOR_LOWER_LIMIT) {
        obj_mark_for_deletion(o);
    }
    if (o->oTimer > 100) {
        obj_mark_for_deletion(o);
    }
    if (gPrevFrameObjectCount > (OBJECT_POOL_CAPACITY - 28)) {
        obj_mark_for_deletion(o);
    }

    o->oFaceAnglePitch += o->oAngleVelPitch;
    o->oFaceAngleRoll += o->oAngleVelRoll;
    o->oVelY += -3.0f;

    if (o->oVelY < -8.0f) {
        o->oVelY = -8.0f;
    }

    if (o->oForwardVel > 0) {
        o->oForwardVel -= 0.3;
    } else {
        o->oForwardVel = 0;
    }

    o->oPosX += sins(o->oMoveAngleYaw) * sins(o->oTreeSnowOrLeafUnkF4) * o->oTreeSnowOrLeafUnkF8;
    o->oPosZ += coss(o->oMoveAngleYaw) * sins(o->oTreeSnowOrLeafUnkF4) * o->oTreeSnowOrLeafUnkF8;
    o->oTreeSnowOrLeafUnkF4 += o->oTreeSnowOrLeafUnkFC;
    o->oPosY += o->oVelY;
}

void bhv_snow_leaf_particle_spawn_init(void) {
    struct Object *obj; // Either snow or leaf
    UNUSED u8 filler1[4];
    s32 isSnow;
    f32 scale;
    UNUSED u8 filler2[4];

    gMarioObject->oActiveParticleFlags &= ~ACTIVE_PARTICLE_LEAF;

    if (gCurrLevelNum == LEVEL_CCM || gCurrLevelNum == LEVEL_SL) {
        isSnow = TRUE;
    } else {
        isSnow = FALSE;
    }

    if (isSnow) {
        if (random_float() < 0.5) {
            obj = spawn_object(o, MODEL_WHITE_PARTICLE_DL, bhvTreeSnow);
            scale = random_float();
            obj_scale_xyz(obj, scale, scale, scale);
            obj->oMoveAngleYaw = random_u16();
            obj->oForwardVel = random_float() * 5.0f;
            obj->oVelY = random_float() * 15.0f;
        }
    } else {
        if (random_float() < 0.3) {
            obj = spawn_object(o, MODEL_LEAVES, bhvTreeLeaf);
            scale = random_float() * 3.0f;
            obj_scale_xyz(obj, scale, scale, scale);
            obj->oMoveAngleYaw = random_u16();
            obj->oForwardVel = random_float() * 5.0f + 5.0f;
            obj->oVelY = random_float() * 15.0f;
            obj->oFaceAnglePitch = random_u16();
            obj->oFaceAngleRoll = random_u16();
            obj->oFaceAngleYaw = random_u16();
        }
    }
}
