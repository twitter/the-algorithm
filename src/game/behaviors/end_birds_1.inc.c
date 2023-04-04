// end_birds_1.inc.c

void bhv_end_birds_1_loop(void) {
    Vec3f sp34;
    UNUSED f32 sp30 = random_float();

    switch (o->oAction) {
        case 0:
            cur_obj_scale(0.7f);
            o->oIntroLakituUnk110 = -554.0f;
            o->oIntroLakituUnk10C = 3044.0f;
            o->oIntroLakituUnk108 = -1314.0f;
            o->oAction++;
            break;

        case 1:
            vec3f_set(sp34, o->oIntroLakituUnk110, o->oIntroLakituUnk10C,
                      o->oIntroLakituUnk108);

            if (o->oTimer < 100) {
                obj_rotate_towards_point(o, sp34, 0, 0, 0x20, 0x20);
            }
            if ((o->oEndBirdUnk104 == 0.0f) && (o->oTimer == 0)) {
                cur_obj_play_sound_2(SOUND_GENERAL_BIRDS_FLY_AWAY);
            }
            if (gCutsceneTimer == 0) {
                obj_mark_for_deletion(o);
            }
            break;
    }

    cur_obj_set_pos_via_transform();
}
