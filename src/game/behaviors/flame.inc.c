// flame.inc.c

void bhv_small_piranha_flame_loop(void) {
    if ((u16)(o->oBehParams >> 16) == 0) {
        if (o->oTimer > 0) {
            obj_mark_for_deletion(o);
        } else {
            f32 rnd = random_float() - 0.5f;

            o->header.gfx.scale[1] = o->header.gfx.scale[2] * (1.0f + 0.7f * rnd);
            o->header.gfx.scale[0] = o->header.gfx.scale[2] * (0.9f - 0.5f * rnd);

            o->oAnimState = random_u16();
        }
    } else {
        cur_obj_update_floor_and_walls();

        if (approach_f32_ptr(&o->oSmallPiranhaFlameStartSpeed, o->oSmallPiranhaFlameEndSpeed, 0.6f)) {
            cur_obj_rotate_yaw_toward(o->oAngleToMario, 0x200);
        }

        obj_compute_vel_from_move_pitch(o->oSmallPiranhaFlameStartSpeed);
        cur_obj_move_standard(-78);
        spawn_object_with_scale(o, o->oSmallPiranhaFlameModel, bhvSmallPiranhaFlame,
                                0.4f * o->header.gfx.scale[0]);

        if (o->oTimer > o->oSmallPiranhaFlameNextFlameTimer) {
            spawn_object_relative_with_scale(1, 0, o->oGraphYOffset, 0, o->header.gfx.scale[0], o,
                                             o->oSmallPiranhaFlameModel, bhvFlyguyFlame);
            o->oSmallPiranhaFlameNextFlameTimer = random_linear_offset(8, 15);
            o->oTimer = 0;
        }

        obj_check_attacks(&sPiranhaPlantFireHitbox, o->oAction);
        o->oSmallPiranhaFlameSpeed += o->oSmallPiranhaFlameStartSpeed;

        if (o->oSmallPiranhaFlameSpeed > 1500.0f
            || (o->oMoveFlags & (OBJ_MOVE_HIT_WALL | OBJ_MOVE_MASK_IN_WATER))) {
            obj_die_if_health_non_positive();
        }
    }

    o->oGraphYOffset = 15.0f * o->header.gfx.scale[1];
}

void bhv_fly_guy_flame_loop(void) {
    cur_obj_move_using_fvel_and_gravity();

    if (approach_f32_ptr(&o->header.gfx.scale[0], 0.0f, 0.6f)) {
        obj_mark_for_deletion(o);
    }

    cur_obj_scale(o->header.gfx.scale[0]);
}
