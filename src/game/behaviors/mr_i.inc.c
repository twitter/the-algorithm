// mr_i.c.inc

// this is actually the MrI particle loop function. piranha
// plant code later on reuses this function.
void bhv_piranha_particle_loop(void) {
    if (o->oTimer == 0) {
        o->oVelY = 20.0f + 20.0f * random_float();
        o->oForwardVel = 20.0f + 20.0f * random_float();
        o->oMoveAngleYaw = random_u16();
    }
    cur_obj_move_using_fvel_and_gravity();
}

void mr_i_piranha_particle_act_0(void) {
    cur_obj_scale(3.0f);
    o->oForwardVel = 20.0f;
    cur_obj_update_floor_and_walls();
    if (0x8000 & o->oInteractStatus)
        o->oAction = 1;
    else if ((o->oTimer >= 101) || (0x200 & o->oMoveFlags) || (8 & (s16) o->activeFlags)) {
        obj_mark_for_deletion(o);
        spawn_mist_particles();
    }
}

void mr_i_piranha_particle_act_1(void) {
    s32 i;
    obj_mark_for_deletion(o);
    for (i = 0; i < 10; i++)
        spawn_object(o, MODEL_PURPLE_MARBLE, bhvPurpleParticle);
}

void (*sMrIParticleActions[])(void) = { mr_i_piranha_particle_act_0, mr_i_piranha_particle_act_1 };

void bhv_mr_i_particle_loop(void) {
    cur_obj_call_action_function(sMrIParticleActions);
}

void spawn_mr_i_particle(void) {
    struct Object *particle;
    f32 sp18 = o->header.gfx.scale[1];
    particle = spawn_object(o, MODEL_PURPLE_MARBLE, bhvMrIParticle);
    particle->oPosY += 50.0f * sp18;
    particle->oPosX += sins(o->oMoveAngleYaw) * 90.0f * sp18;
    particle->oPosZ += coss(o->oMoveAngleYaw) * 90.0f * sp18;
    cur_obj_play_sound_2(SOUND_OBJ_MRI_SHOOT);
}

void bhv_mr_i_body_loop(void) {
    obj_copy_pos_and_angle(o, o->parentObj);
    if (!(8 & o->activeFlags)) {
        obj_copy_scale(o, o->parentObj);
        obj_set_parent_relative_pos(o, 0, 0, o->header.gfx.scale[1] * 100.0f);
        obj_build_transform_from_pos_and_angle(o, 44, 15);
        obj_translate_local(o, 6, 44);
        o->oFaceAnglePitch = o->oMoveAnglePitch;
        o->oGraphYOffset = o->header.gfx.scale[1] * 100.f;
    }
    if (o->parentObj->oMrIUnk110 != 1)
        o->oAnimState = -1;
    else {
        o->oAnimState++;
        if (o->oAnimState == 15)
            o->parentObj->oMrIUnk110 = 0;
    }
    if (!o->parentObj->activeFlags)
        obj_mark_for_deletion(o);
}

void mr_i_act_3(void) {
    s16 sp36;
    s16 sp34;
    f32 sp30;
    f32 sp2C;
    UNUSED u8 pad[8];
    f32 sp20;
    f32 sp1C;
    if (o->oBehParams2ndByte)
        sp1C = 2.0f;
    else
        sp1C = 1.0f;
    if (o->oMrIUnk100 < 0)
        sp34 = 0x1000;
    else
        sp34 = -0x1000;
    sp2C = (o->oTimer + 1) / 96.0f;
    if (o->oTimer < 64) {
        sp36 = o->oMoveAngleYaw;
        o->oMoveAngleYaw += sp34 * coss(0x4000 * sp2C);
        if (sp36 < 0 && o->oMoveAngleYaw >= 0)
            cur_obj_play_sound_2(SOUND_OBJ2_MRI_SPINNING);
        o->oMoveAnglePitch = (1.0 - coss(0x4000 * sp2C)) * -0x4000;
        cur_obj_shake_y(4.0f);
    } else if (o->oTimer < 96) {
        if (o->oTimer == 64)
            cur_obj_play_sound_2(SOUND_OBJ_MRI_DEATH);
        sp30 = (f32)(o->oTimer - 63) / 32;
        o->oMoveAngleYaw += sp34 * coss(0x4000 * sp2C);
        o->oMoveAnglePitch = (1.0 - coss(0x4000 * sp2C)) * -0x4000;
        cur_obj_shake_y((s32)((1.0f - sp30) * 4)); // trucating the f32?
        sp20 = coss(0x4000 * sp30) * 0.4 + 0.6;
        cur_obj_scale(sp20 * sp1C);
    } else if (o->oTimer < 104) {
        // do nothing
    } else if (o->oTimer < 168) {
        if (o->oTimer == 104) {
            cur_obj_become_intangible();
            spawn_mist_particles();
            o->oMrISize = sp1C * 0.6;
            if (o->oBehParams2ndByte) {
                o->oPosY += 100.0f;
                spawn_default_star(1370, 2000.0f, -320.0f);
                obj_mark_for_deletion(o);
            } else
                cur_obj_spawn_loot_blue_coin();
        }
        o->oMrISize -= 0.2 * sp1C;
        if (o->oMrISize < 0)
            o->oMrISize = 0;
        cur_obj_scale(o->oMrISize);
    } else
        obj_mark_for_deletion(o);
}

void mr_i_act_2() {
    s16 sp1E;
    s16 sp1C;
    sp1E = o->oMoveAngleYaw;
    if (o->oTimer == 0) {
        if (o->oBehParams2ndByte)
            o->oMrIUnkF4 = 200;
        else
            o->oMrIUnkF4 = 120;
        o->oMrIUnkFC = 0;
        o->oMrIUnk100 = 0;
        o->oMrIUnk104 = 0;
    }
    obj_turn_toward_object(o, gMarioObject, 0x10, 0x800);
    obj_turn_toward_object(o, gMarioObject, 0x0F, 0x400);
    sp1C = sp1E - (s16)(o->oMoveAngleYaw);
    if (!sp1C) {
        o->oMrIUnkFC = 0;
        o->oMrIUnk100 = 0;
    } else if (sp1C > 0) {
        if (o->oMrIUnk100 > 0)
            o->oMrIUnkFC += sp1C;
        else
            o->oMrIUnkFC = 0;
        o->oMrIUnk100 = 1;
    } else {
        if (o->oMrIUnk100 < 0)
            o->oMrIUnkFC -= sp1C;
        else
            o->oMrIUnkFC = 0;
        o->oMrIUnk100 = -1;
    }
    if (!o->oMrIUnkFC)
        o->oMrIUnkF4 = 120;
    if (o->oMrIUnkFC > 1 << 16)
        o->oAction = 3;
    o->oMrIUnkF4 -= 1;
    if (!o->oMrIUnkF4) {
        o->oMrIUnkF4 = 120;
        o->oMrIUnkFC = 0;
    }
    if (o->oMrIUnkFC < 5000) {
        if (o->oMrIUnk104 == o->oMrIUnk108)
            o->oMrIUnk110 = 1;
        if (o->oMrIUnk104 == o->oMrIUnk108 + 20) {
            spawn_mr_i_particle();
            o->oMrIUnk104 = 0;
            o->oMrIUnk108 = (s32)(random_float() * 50.0f + 50.0f);
        }
        o->oMrIUnk104++;
    } else {
        o->oMrIUnk104 = 0;
        o->oMrIUnk108 = (s32)(random_float() * 50.0f + 50.0f);
    }
    if (o->oDistanceToMario > 800.0f)
        o->oAction = 1;
}

void mr_i_act_1(void) {
    s16 sp1E;
    s16 sp1C;
    s16 sp1A;
    sp1E = obj_angle_to_object(o, gMarioObject);
    sp1C = abs_angle_diff(o->oMoveAngleYaw, sp1E);
    sp1A = abs_angle_diff(o->oMoveAngleYaw, gMarioObject->oFaceAngleYaw);
    if (o->oTimer == 0) {
        cur_obj_become_tangible();
        o->oMoveAnglePitch = 0;
        o->oMrIUnk104 = 30;
        o->oMrIUnk108 = random_float() * 20.0f;
        if (o->oMrIUnk108 & 1)
            o->oAngleVelYaw = -256;
        else
            o->oAngleVelYaw = 256;
    }
    if (sp1C < 1024 && sp1A > 0x4000) {
        if (o->oDistanceToMario < 700.0f)
            o->oAction = 2;
        else
            o->oMrIUnk104++;
    } else {
        o->oMoveAngleYaw += o->oAngleVelYaw;
        o->oMrIUnk104 = 30;
    }
    if (o->oMrIUnk104 == o->oMrIUnk108 + 60)
        o->oMrIUnk110 = 1;
    if (o->oMrIUnk108 + 80 < o->oMrIUnk104) {
        o->oMrIUnk104 = 0;
        o->oMrIUnk108 = random_float() * 80.0f;
        spawn_mr_i_particle();
    }
}

void mr_i_act_0(void) {
#ifndef VERSION_JP
    obj_set_angle(o, 0, 0, 0);
#else
    o->oMoveAnglePitch = 0;
    o->oMoveAngleYaw = 0;
    o->oMoveAngleRoll = 0;
#endif
    cur_obj_scale(o->oBehParams2ndByte + 1);
    if (o->oTimer == 0)
        cur_obj_set_pos_to_home();
    if (o->oDistanceToMario < 1500.0f)
        o->oAction = 1;
}

void (*sMrIActions[])(void) = { mr_i_act_0, mr_i_act_1, mr_i_act_2, mr_i_act_3 };

struct ObjectHitbox sMrIHitbox = {
    /* interactType: */ INTERACT_DAMAGE,
    /* downOffset: */ 0,
    /* damageOrCoinValue: */ 2,
    /* health: */ 2,
    /* numLootCoins: */ 5,
    /* radius: */ 80,
    /* height: */ 150,
    /* hurtboxRadius: */ 0,
    /* hurtboxHeight: */ 0,
};

void bhv_mr_i_loop(void) {
    obj_set_hitbox(o, &sMrIHitbox);
    cur_obj_call_action_function(sMrIActions);
    if (o->oAction != 3)
        if (o->oDistanceToMario > 3000.0f || o->activeFlags & 8)
            o->oAction = 0;
    o->oInteractStatus = 0;
}
