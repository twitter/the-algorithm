// jumping_box.c.inc

struct ObjectHitbox sJumpingBoxHitbox = {
    /* interactType: */ INTERACT_GRABBABLE,
    /* downOffset: */ 20,
    /* damageOrCoinValue: */ 0,
    /* health: */ 1,
    /* numLootCoins: */ 5,
    /* radius: */ 150,
    /* height: */ 250,
    /* hurtboxRadius: */ 150,
    /* hurtboxHeight: */ 250,
};

void jumping_box_act_0(void) {
    if (o->oSubAction == 0) {
        if (o->oJumpingBoxUnkF8-- < 0)
            o->oSubAction++;
        if (o->oTimer > o->oJumpingBoxUnkF4) {
            o->oVelY = random_float() * 5.0f + 15.0f;
            o->oSubAction++;
        }
    } else if (o->oMoveFlags & 2) {
        o->oSubAction = 0;
        o->oJumpingBoxUnkF8 = random_float() * 60.0f + 30.0f;
    }
}

void jumping_box_act_1(void) {
    if (o->oMoveFlags & (0x200 | 0x40 | 0x20 | 0x10 | 0x8 | 0x1)) {
        obj_mark_for_deletion(o);
        spawn_mist_particles();
    }
}

void (*sJumpingBoxActions[])(void) = { jumping_box_act_0, jumping_box_act_1 };

void jumping_box_free_update(void) {
    cur_obj_set_model(MODEL_BREAKABLE_BOX);
    cur_obj_scale(0.5f);
    obj_set_hitbox(o, &sJumpingBoxHitbox);
    cur_obj_update_floor_and_walls();
    cur_obj_move_standard(78);
    cur_obj_call_action_function(sJumpingBoxActions);
}

void bhv_jumping_box_loop(void) {
    switch (o->oHeldState) {
        case HELD_FREE:
            jumping_box_free_update();
            break;
        case HELD_HELD:
            obj_copy_pos(o, gMarioObject);
            cur_obj_set_model(MODEL_BREAKABLE_BOX_SMALL);
            cur_obj_unrender_and_reset_state(-1, 0);
            break;
        case HELD_THROWN:
            cur_obj_get_thrown_or_placed(40.0f, 20.0f, 1);
            break;
        case HELD_DROPPED:
            cur_obj_get_dropped();
            o->oAction = 1;
            break;
    }
    if (o->oInteractStatus & INT_STATUS_STOP_RIDING) {
        create_sound_spawner(SOUND_GENERAL_BREAK_BOX);
        obj_explode_and_spawn_coins(46.0f, 1);
    }
    o->oInteractStatus = 0;
}
