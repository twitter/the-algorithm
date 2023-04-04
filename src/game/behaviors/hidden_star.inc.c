// hidden_star.inc.c

void bhv_hidden_star_init(void) {
    s16 count = count_objects_with_behavior(bhvHiddenStarTrigger);

    if (count == 0) {
        struct Object *star = spawn_object_abs_with_rot(o, 0, MODEL_STAR, bhvStar,
                                                        o->oPosX, o->oPosY, o->oPosZ, 0, 0, 0);
        star->oBehParams = o->oBehParams;
        o->activeFlags = ACTIVE_FLAG_DEACTIVATED;
    }

    o->oHiddenStarTriggerCounter = 5 - count;
}

void bhv_hidden_star_loop(void) {
    switch (o->oAction) {
        case 0:
            if (o->oHiddenStarTriggerCounter == 5) {
                o->oAction = 1;
            }
            break;

        case 1:
            if (o->oTimer > 2) {
                spawn_red_coin_cutscene_star(o->oPosX, o->oPosY, o->oPosZ);
                spawn_mist_particles();
                o->activeFlags = ACTIVE_FLAG_DEACTIVATED;
            }
            break;
    }
}

void bhv_hidden_star_trigger_loop(void) {
    if (obj_check_if_collided_with_object(o, gMarioObject) == TRUE) {
        struct Object *hiddenStar = cur_obj_nearest_object_with_behavior(bhvHiddenStar);

        if (hiddenStar != NULL) {
            hiddenStar->oHiddenStarTriggerCounter++;

            if (hiddenStar->oHiddenStarTriggerCounter != 5) {
                spawn_orange_number(hiddenStar->oHiddenStarTriggerCounter, 0, 0, 0);
            }

#ifdef VERSION_JP
            play_sound(SOUND_MENU_STAR_SOUND, gGlobalSoundSource);
#else
            play_sound(SOUND_MENU_COLLECT_SECRET
                       + (((u8) hiddenStar->oHiddenStarTriggerCounter - 1) << 16), gGlobalSoundSource);
#endif
        }

        o->activeFlags = ACTIVE_FLAG_DEACTIVATED;
    }
}

void bhv_bowser_course_red_coin_star_loop(void) {
    gRedCoinsCollected = o->oHiddenStarTriggerCounter;

    switch (o->oAction) {
        case 0:
            if (o->oHiddenStarTriggerCounter == 8) {
                o->oAction = 1;
            }
            break;

        case 1:
            if (o->oTimer > 2) {
                spawn_no_exit_star(o->oPosX, o->oPosY, o->oPosZ);
                spawn_mist_particles();
                o->activeFlags = ACTIVE_FLAG_DEACTIVATED;
            }
            break;
    }
}
