/**
 * Behavior for bhvBetaTrampolineTop and bhvBetaTrampolineSpring.
 * This was a trampoline that was never finished. The model and collision
 * are nearly finished, but the code was abandoned very early on
 * in its development. The trampoline consists of 3 objects: the top,
 * the spring, and the base. The base is a static object with no behavior.
 */

/**
 * Update function for bhvBetaTrampolineSpring.
 * The spring continuously updates to be 75 units below the top.
 * It then uses its displacement from its original position
 * (i.e. how much the trampoline has compressed) to calculate its
 * vertical scale factor, so that it compresses visually along with
 * the trampoline. The devs were on the right track with the math,
 * but it is incomplete.
 */
void bhv_beta_trampoline_spring_loop(void) {
    f32 yScale;
    f32 yDisplacement;

    // Update to be 75 units under the trampoline top
    obj_copy_pos_and_angle(o, o->parentObj);
    obj_copy_graph_y_offset(o, o->parentObj);
    o->oPosY -= 75.0f;

    // If the trampoline top is above its original position,
    // scale the spring by (the displacement)/10 + 1.
    // For this to work correctly, the arbitrary value of 10
    // must be replaced with 150 (the height of the trampoline).
    // Note that all of the numbers in this if/else block are doubles.
    if ((yDisplacement = o->oPosY - o->oHomeY) >= 0) {
        yScale = yDisplacement / 10.0 + 1.0;
    } else {
        // Otherwise (if the trampoline is compressed),
        // scale by 1 - (the displacement)/500.
        // For this to work correctly, the arbitrary value of 500
        // must be replaced with 150 (the height of the trampoline),
        // as with the above code.
        yDisplacement = -yDisplacement;
        yScale = 1.0 - yDisplacement / 500.0;
    }

    // Scale the spring
    obj_scale_xyz(o, 1.0f, yScale, 1.0f);
}

/**
 * Update function for bhvBetaTrampolineTop.
 * This spawns the other 2 trampoline parts when initialized,
 * and sets a boolean for whether Mario's on or off the trampoline.
 * The trampoline top never actually moves, so the spring will never
 * do anything.
 */
void bhv_beta_trampoline_top_loop(void) {
    cur_obj_set_model(MODEL_TRAMPOLINE);

    // When initialized, spawn the rest of the trampoline
    if (o->oTimer == 0) {
        struct Object *trampolinePart;

        trampolinePart = spawn_object(o, MODEL_TRAMPOLINE_CENTER, bhvBetaTrampolineSpring);
        trampolinePart->oPosY -= 75.0f;

        trampolinePart = spawn_object(o, MODEL_TRAMPOLINE_BASE, bhvStaticObject);
        trampolinePart->oPosY -= 150.0f;
    }

    // Update o->oBetaTrampolineMarioOnTrampoline, and reset
    // the trampoline's position if Mario's not on it.
    // Since the trampoline never moves, this doesn't do anything.
    // Maybe they intended to decrease the trampoline's position
    // when Mario's on it in this if statement?
    if (gMarioObject->platform == o) {
        o->oBetaTrampolineMarioOnTrampoline = TRUE;
    } else {
        o->oBetaTrampolineMarioOnTrampoline = FALSE;
        o->oPosY = o->oHomeY;
    }

    // This function is from mario_step.c, and is empty.
    // It was probably intended to be used to "let the game know"
    // that the trampoline is currently in use. This potential
    // trampoline infrastructure is found in mario_step.c. See
    // that file for more details.
    stub_mario_step_2();
}
