// end_birds_2.inc.c

void bhv_end_birds_2_loop(void) {
    Vec3f sp3C;
    UNUSED f32 sp38;
    f32 sp34;
    s16 sp32, sp30;

    sp38 = random_float();
    gCurrentObject->oForwardVel = (random_float() * 10.f) + 25.f;

    switch (gCurrentObject->oAction) {
        case 0:
            cur_obj_scale(0.7f);
            gCurrentObject->oAction += 1;
            break;
        case 1:
            vec3f_get_dist_and_angle(gCamera->pos, gCamera->focus, &sp34, &sp32,
                                     &sp30);
            sp30 += 0x1000;
            sp32 += 0; // nice work, Nintendo
            vec3f_set_dist_and_angle(gCamera->pos, sp3C, 14000.f, sp32, sp30);
            obj_rotate_towards_point(gCurrentObject, sp3C, 0, 0, 8, 8);

            if ((gCurrentObject->oEndBirdUnk104 == 0.f) && (gCurrentObject->oTimer == 0))
                cur_obj_play_sound_2(SOUND_GENERAL_BIRDS_FLY_AWAY);
            break;
    }

    cur_obj_set_pos_via_transform();
}
