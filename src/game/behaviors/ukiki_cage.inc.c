// ukiki_cage.c.inc

/**
 * @file Contains behavior for the ukiki's cage
 * and the star within the cage.
 *
 * The cage is parent of the cage star and another
 * dummy object referenced by ukiki (bhvUkikiCageChild).
 */

/**
 * Behavior for the cage star.
 * Only really used for initializing then spawning the star.
 *
 * Not an actual star object until later.
 */
void bhv_ukiki_cage_star_loop(void) {
    switch (o->oAction) {
        case UKIKI_CAGE_STAR_ACT_IN_CAGE:
            // Initialization to see if the star is collected (blue) or not (yellow).
            if (o->oTimer == 0) {
                if (bit_shift_left(1)
                    & save_file_get_star_flags(gCurrSaveFileNum - 1, gCurrCourseNum - 1)) {
                        cur_obj_set_model(MODEL_TRANSPARENT_STAR);
                    }
            }

            obj_copy_pos(o, o->parentObj);
            obj_copy_behavior_params(o, o->parentObj);

            // When they cage hides itself, spawn particles and the star.
            if (o->parentObj->oAction == UKIKI_CAGE_ACT_HIDE) {
                o->oAction++;
            }
            break;
        case UKIKI_CAGE_STAR_ACT_SPAWN_STAR:
            obj_mark_for_deletion(o);
            spawn_mist_particles();
            spawn_triangle_break_particles(20, 138, 0.7, 3);
            spawn_default_star(2500.0f, -1200.0f, 1300.0f);
            break;
    }

    // Spin to look like a star.
    o->oFaceAngleYaw += 0x400;
}

/**
 * Default state until ukiki reaches the cage.
 */
void ukiki_cage_act_wait_for_ukiki(void) {
    if (o->oUkikiCageNextAction != UKIKI_CAGE_ACT_WAIT_FOR_UKIKI) {
        o->oAction = UKIKI_CAGE_ACT_SPIN;
    }

    load_object_collision_model();
}

/**
 * Spin once ukiki has landed on the cage.
 */
void ukiki_cage_act_spin(void) {
    if (o->oUkikiCageNextAction != UKIKI_CAGE_ACT_SPIN) {
        o->oAction = UKIKI_CAGE_ACT_FALL;
    }

    o->oMoveAngleYaw += 0x800;
    load_object_collision_model();
}

/**
 * Assist ukiki in his death.
 */
void ukiki_cage_act_fall(void) {
    //! (PARTIAL_UPDATE)
    cur_obj_update_floor_and_walls();
    cur_obj_move_standard(78);
    if (o->oMoveFlags & (OBJ_MOVE_LANDED | OBJ_MOVE_ENTERED_WATER)) {
        o->oAction = UKIKI_CAGE_ACT_HIDE;
    }
}

/**
 * Hide once we've exploded.
 *
 * Perhaps deleting the object would be better than
 * hiding the object?
 */
void ukiki_cage_act_hide(void) {
    cur_obj_hide();
}

/**
 * An array of the cage's actions.
 */
void (*sUkikiCageActions[])(void) = {
    ukiki_cage_act_wait_for_ukiki, ukiki_cage_act_spin,
    ukiki_cage_act_fall,           ukiki_cage_act_hide,
};

/**
 * Main behavior loop for the cage. Only calls the relevant action.
 */
void bhv_ukiki_cage_loop(void) {
    cur_obj_call_action_function(sUkikiCageActions);
}
