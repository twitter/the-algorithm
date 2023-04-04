// bouncing_fireball.inc.c

void bhv_bouncing_fireball_flame_loop(void) {
    o->activeFlags |= ACTIVE_FLAG_UNK10;

    cur_obj_update_floor_and_walls();

    switch (o->oAction) {
        case 0:
            if (o->oTimer == 0) {
                o->oAnimState = random_float() * 10.0f;
                o->oVelY = 30.0f;
            }

            if (o->oMoveFlags & OBJ_MOVE_LANDED) {
                o->oAction++;
            }
            break;

        case 1:
            if (o->oTimer == 0) {
                o->oVelY = 50.0f;
                o->oForwardVel = 30.0f;
            }

            if (o->oMoveFlags
                & (OBJ_MOVE_ON_GROUND | OBJ_MOVE_AT_WATER_SURFACE | OBJ_MOVE_UNDERWATER_ON_GROUND)
                && o->oTimer > 100) {
                obj_mark_for_deletion(o);
            }
            break;
    }

    if (o->oTimer > 300) {
        obj_mark_for_deletion(o);
    }

    cur_obj_move_standard(78);

    o->oInteractStatus = 0;
}

void bhv_bouncing_fireball_loop(void) {
    struct Object *sp2C;
    f32 sp28;

    switch (o->oAction) {
        case 0:
            if (o->oDistanceToMario < 2000.0f) {
                o->oAction = 1;
            }
            break;

        case 1:
            sp2C = spawn_object(o, MODEL_RED_FLAME, bhvBouncingFireballFlame);
            sp28 = (10 - o->oTimer) * 0.5;

            obj_scale_xyz(sp2C, sp28, sp28, sp28);

            if (o->oTimer == 0) {
                obj_become_tangible(sp2C);
            }

            if (o->oTimer > 10) {
                o->oAction++;
            }
            break;

        case 2:
            if (o->oTimer == 0) {
                o->oBouncingFireBallUnkF4 = random_float() * 100.0f;
            }

            if (o->oBouncingFireBallUnkF4 + 100 < o->oTimer) {
                o->oAction = 0;
            }
            break;
    }
}
