// grand_star.c.inc

s32 arc_to_goal_pos(Vec3f a0, Vec3f a1, f32 yVel, f32 gravity) {
    f32 dx = a0[0] - a1[0];
    f32 dz = a0[2] - a1[2];
    f32 planarDist = sqrtf(dx * dx + dz * dz);
    s32 time;
    o->oMoveAngleYaw = atan2s(dz, dx);
    o->oVelY = yVel;
    o->oGravity = gravity;
    time = -2.0f / o->oGravity * yVel - 1.0f;
    o->oForwardVel = planarDist / time;
    return time;
}

void grand_star_zero_velocity(void) {
    o->oGravity = 0.0f;
    o->oVelY = 0.0f;
    o->oForwardVel = 0.0f;
}

void bhv_grand_star_loop(void) {
    UNUSED s32 unused;
    Vec3f sp28;
    sp28[0] = sp28[1] = sp28[2] = 0.0f;
    if (o->oAction == 0) {
        if (o->oTimer == 0) {
            obj_set_angle(o, 0, 0, 0);
            o->oAngleVelYaw = 0x400;
            cur_obj_play_sound_2(SOUND_GENERAL2_STAR_APPEARS);
        }
        if (o->oTimer > 70)
            o->oAction++;
        spawn_sparkle_particles(3, 200, 80, -60);
    } else if (o->oAction == 1) {
        if (o->oTimer == 0) {
            cur_obj_play_sound_2(SOUND_GENERAL_GRAND_STAR);
            cutscene_object(CUTSCENE_STAR_SPAWN, o);
            o->oGrandStarUnk108 = arc_to_goal_pos(sp28, &o->oPosX, 80.0f, -2.0f);
        }
        cur_obj_move_using_fvel_and_gravity();
        if (o->oSubAction == 0) {
            if (o->oPosY < o->oHomeY) {
                o->oPosY = o->oHomeY;
                o->oVelY = 60.0f;
                o->oForwardVel = 0.0f;
                o->oSubAction++;
                cur_obj_play_sound_2(SOUND_GENERAL_GRAND_STAR_JUMP);
            }
        } else if (o->oVelY < 0.0f && o->oPosY < o->oHomeY + 200.0f) {
            o->oPosY = o->oHomeY + 200.0f;
            grand_star_zero_velocity();
            gObjCutsceneDone = 1;
            set_mario_npc_dialog(0);
            o->oAction++;
            o->oInteractStatus = 0;
            cur_obj_play_sound_2(SOUND_GENERAL_GRAND_STAR_JUMP);
        }
        spawn_sparkle_particles(3, 200, 80, -60);
    } else {
        cur_obj_become_tangible();
        if (o->oInteractStatus & INT_STATUS_INTERACTED) {
            obj_mark_for_deletion(o);
            o->oInteractStatus = 0;
        }
    }
    if (o->oAngleVelYaw > 0x400)
        o->oAngleVelYaw -= 0x100;
    o->oFaceAngleYaw += o->oAngleVelYaw;
    cur_obj_scale(2.0f);
    o->oGraphYOffset = 110.0f;
}
