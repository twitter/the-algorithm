// intro_peach.inc.c

/**
 * Set peach's location relative to the camera focus.
 * If nonzero, make peach's opacity approach targetOpacity by increment
 */
void intro_peach_set_pos_and_opacity(struct Object *obj, f32 targetOpacity, f32 increment) {
    Vec3f newPos;
    s16 focusPitch, focusYaw;
    f32 UNUSED dist, newOpacity;

    vec3f_get_dist_and_angle(gLakituState.pos, gLakituState.focus, &dist, &focusPitch, &focusYaw);
    vec3f_set_dist_and_angle(gLakituState.pos, newPos, obj->oIntroPeachDistToCamera,
                             obj->oIntroPeachPitchFromFocus + focusPitch,
                             obj->oIntroPeachYawFromFocus + focusYaw);
    vec3f_to_object_pos(obj, newPos);

    newOpacity = obj->oOpacity;
    camera_approach_f32_symmetric_bool(&newOpacity, targetOpacity, increment);
    obj->oOpacity = newOpacity;
}

void bhv_intro_peach_loop(void) {
    switch (o->oAction) {
        case 0:
            o->oAction++;
            o->oFaceAnglePitch = 0x400;
            o->oFaceAngleYaw = 0x7500;
            o->oFaceAngleRoll = -0x3700;
            o->oIntroPeachDistToCamera = 186.0f;
            o->oIntroPeachPitchFromFocus = -9984.0f;
            o->oIntroPeachYawFromFocus = -768.0f;
            o->oOpacity = 255;
            o->header.gfx.animInfo.animFrame = 100;
            break;

        case 1:
            intro_peach_set_pos_and_opacity(o, 0.0f, 0.0f);

            if (o->oTimer > 20) {
                o->oAction++;
            }
            break;

        case 2:
            intro_peach_set_pos_and_opacity(o, 255.0f, 3.0f);

            if ((o->oTimer > 100) && (get_dialog_id() == DIALOG_NONE)) {
                o->oAction++;
            }
            break;

        case 3:
            intro_peach_set_pos_and_opacity(o, 0.0f, 8.0f);

            if (o->oTimer > 60) {
                obj_mark_for_deletion(o);
            }
            break;
    }
}
