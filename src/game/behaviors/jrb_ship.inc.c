// jrb_ship.c.inc

struct ObjectHitbox sSkullSlidingBoxHitbox = {
    /* interactType: */ INTERACT_DAMAGE,
    /* downOffset: */ 0,
    /* damageOrCoinValue: */ 1,
    /* health: */ 1,
    /* numLootCoins: */ 0,
    /* radius: */ 130,
    /* height: */ 100,
    /* hurtboxRadius: */ 0,
    /* hurtboxHeight: */ 0,
};

void bhv_sunken_ship_part_loop(void) {
    if (o->oDistanceToMario > 10000.0f)
        o->oOpacity = 140;
    else
        o->oOpacity = o->oDistanceToMario * 140.0f / 10000.0;
    cur_obj_disable_rendering();
}

void bhv_ship_part_3_loop(void) {
    s16 sp1E = o->oFaceAnglePitch;
    s16 sp1C = o->oFaceAngleRoll;
    cur_obj_set_pos_to_home_with_debug();
    o->oShipPart3UnkF4 += 0x100;
    o->oFaceAnglePitch = sins(o->oShipPart3UnkF4) * 1024.0f;
    o->oFaceAngleRoll = sins(o->oShipPart3UnkF8) * 1024.0f;
    o->oAngleVelPitch = o->oFaceAnglePitch - sp1E;
    o->oAngleVelRoll = o->oFaceAngleRoll - sp1C;
    if (gMarioObject->oPosY > 1000.0f)
        cur_obj_play_sound_1(SOUND_ENV_BOAT_ROCKING1);
}

void bhv_jrb_sliding_box_loop(void) {
    Mat4 sp60;
    Vec3f sp54;
    Vec3f sp48;
    Vec3s sp40;
    struct Object *sp3C;
    struct Surface *sp38;
    UNUSED Vec3f sp2C;
    Vec3f sp20;
    s16 sp1E;
    if (o->oJrbSlidingBoxUnkF4 == NULL) {
        sp3C = cur_obj_nearest_object_with_behavior(bhvInSunkenShip3);
        if (sp3C != NULL) // NULL check only for assignment, not for dereference?
            o->oJrbSlidingBoxUnkF4 = sp3C;
        o->oParentRelativePosX = o->oPosX - sp3C->oPosX;
        o->oParentRelativePosY = o->oPosY - sp3C->oPosY;
        o->oParentRelativePosZ = o->oPosZ - sp3C->oPosZ;
    } else {
        sp3C = o->oJrbSlidingBoxUnkF4;
        sp40[0] = sp3C->oFaceAnglePitch;
        sp40[1] = sp3C->oFaceAngleYaw;
        sp40[2] = sp3C->oFaceAngleRoll;
        sp54[0] = o->oParentRelativePosX;
        sp54[1] = o->oParentRelativePosY;
        sp54[2] = o->oParentRelativePosZ;
        mtxf_rotate_zxy_and_translate(sp60, sp54, sp40);
        linear_mtxf_mul_vec3f(sp60, sp48, sp54);
        o->oPosX = sp3C->oPosX + sp48[0];
        o->oPosY = sp3C->oPosY + sp48[1];
        o->oPosZ = sp3C->oPosZ + sp48[2];
        sp1E = sp3C->oFaceAnglePitch;
    }
    sp20[0] = o->oPosX;
    sp20[1] = o->oPosY;
    sp20[2] = o->oPosZ;
    find_floor(sp20[0], sp20[1], sp20[2], &sp38);
    if (sp38 != NULL) {
        sp2C[0] = sp38->normal.x;
        sp2C[1] = sp38->normal.y;
        sp2C[2] = sp38->normal.z;
        o->oFaceAnglePitch = sp1E;
    }
    o->oJrbSlidingBoxUnkFC = sins(o->oJrbSlidingBoxUnkF8) * 20.0f;
    o->oJrbSlidingBoxUnkF8 += 0x100;
    o->oParentRelativePosZ += o->oJrbSlidingBoxUnkFC;
    if (gMarioObject->oPosY > 1000.0f)
        if (absf(o->oJrbSlidingBoxUnkFC) > 3.0f)
            cur_obj_play_sound_1(SOUND_AIR_ROUGH_SLIDE);
    obj_set_hitbox(o, &sSkullSlidingBoxHitbox);
    if (!(o->oJrbSlidingBoxUnkF8 & 0x7FFF))
        cur_obj_become_tangible();
    if (obj_check_if_collided_with_object(o, gMarioObject)) {
        o->oInteractStatus = 0;
        cur_obj_become_intangible();
    }
}
