// bowser_key_cutscene.inc.c

Gfx *geo_scale_bowser_key(s32 run, struct GraphNode *node, UNUSED f32 mtx[4][4]) {
    if (run == TRUE) {
        struct Object *sp4 = (struct Object *) gCurGraphNodeObject;
        ((struct GraphNodeScale *) node->next)->scale = sp4->oBowserKeyScale;
    }
    return NULL;
}

void bhv_bowser_key_unlock_door_loop(void) {
    s32 animFrame = o->header.gfx.animInfo.animFrame;

    cur_obj_init_animation_with_sound(0);

    if (animFrame < 38) {
        o->oBowserKeyScale = 0.0f;
    } else if (animFrame < 49) {
        o->oBowserKeyScale = 0.2f;
    } else if (animFrame < 58) {
        o->oBowserKeyScale = (animFrame - 53) * 0.11875f + 0.2; // 0.11875?
    } else if (animFrame < 59) {
        o->oBowserKeyScale = 1.1f;
    } else if (animFrame < 60) {
        o->oBowserKeyScale = 1.05f;
    } else {
        o->oBowserKeyScale = 1.0f;
    }

    if (o->oTimer > 150) {
        obj_mark_for_deletion(o);
    }
}

void bhv_bowser_key_course_exit_loop(void) {
    s32 animFrame = o->header.gfx.animInfo.animFrame;

    cur_obj_init_animation_with_sound(1);

    if (animFrame < 38) {
        o->oBowserKeyScale = 0.2f;
    } else if (animFrame < 52) {
        o->oBowserKeyScale = (animFrame - 42) * 0.042857f + 0.2; // TODO 3/70?
    } else if (animFrame < 94) {
        o->oBowserKeyScale = 0.8f;
    } else if (animFrame < 101) {
        o->oBowserKeyScale = (101 - animFrame) * 0.085714f + 0.2; // TODO 6/70?
    } else {
        o->oBowserKeyScale = 0.2f;
    }

    if (o->oTimer > 138) {
        obj_mark_for_deletion(o);
    }
}
