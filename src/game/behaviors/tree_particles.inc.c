// tree_particles.c.inc

void bhv_tree_snow_or_leaf_loop(void) {
    cur_obj_update_floor_height();
    if (o->oTimer == 0) {
        o->oAngleVelPitch = (random_float() - 0.5) * 0x1000;
        o->oAngleVelRoll = (random_float() - 0.5) * 0x1000;
        o->oTreeSnowOrLeafUnkF8 = 4;
        o->oTreeSnowOrLeafUnkFC = random_float() * 0x400 + 0x600;
    }
    if (o->oPosY < o->oFloorHeight)
        obj_mark_for_deletion(o);
    if (o->oFloorHeight < -11000.0f)
        obj_mark_for_deletion(o);
    if (o->oTimer > 100)
        obj_mark_for_deletion(o);
    if (gPrevFrameObjectCount > 212)
        obj_mark_for_deletion(o);
    o->oFaceAnglePitch += o->oAngleVelPitch;
    o->oFaceAngleRoll += o->oAngleVelRoll;
    o->oVelY += -3.0f;
    if (o->oVelY < -8.0f)
        o->oVelY = -8.0f;
    if (o->oForwardVel > 0)
        o->oForwardVel -= 0.3;
    else
        o->oForwardVel = 0;
    o->oPosX += sins(o->oMoveAngleYaw) * sins(o->oTreeSnowOrLeafUnkF4) * o->oTreeSnowOrLeafUnkF8;
    o->oPosZ += coss(o->oMoveAngleYaw) * sins(o->oTreeSnowOrLeafUnkF4) * o->oTreeSnowOrLeafUnkF8;
    o->oTreeSnowOrLeafUnkF4 += o->oTreeSnowOrLeafUnkFC;
    o->oPosY += o->oVelY;
}

void bhv_snow_leaf_particle_spawn_init(void) {
    struct Object *obj; // Either snow or leaf
    UNUSED s32 unused;
    s32 isSnow;
    f32 scale;
    UNUSED s32 unused2;
    gMarioObject->oActiveParticleFlags &= ~0x2000;
    if (gCurrLevelNum == LEVEL_CCM || gCurrLevelNum == LEVEL_SL)
        isSnow = 1;
    else
        isSnow = 0;
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
