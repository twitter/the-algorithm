// bowser_key_cutscene.inc.c

Gfx *geo_scale_bowser_key(s32 run, struct GraphNode *node, UNUSED f32 mtx[4][4]) {
    struct Object *sp4;
    if (run == TRUE) {
        sp4 = (struct Object *) gCurGraphNodeObject;
        ((struct GraphNodeScale *) node->next)->scale = sp4->oBowserKeyScale;
    }
    return 0;
}

void bhv_bowser_key_unlock_door_loop(void) {
    s32 animTimer;
    animTimer = o->header.gfx.unk38.animFrame;
    cur_obj_init_animation_with_sound(0);
    if (animTimer < 38)
        o->oBowserKeyScale = 0.0f;
    else if (animTimer < 49)
        o->oBowserKeyScale = 0.2f;
    else if (animTimer < 58)
        o->oBowserKeyScale = (animTimer - 53) * 0.11875f + 0.2; // 0.11875?
    else if (animTimer < 59)
        o->oBowserKeyScale = 1.1f;
    else if (animTimer < 60)
        o->oBowserKeyScale = 1.05f;
    else
        o->oBowserKeyScale = 1.0f;
    if (o->oTimer > 150)
        obj_mark_for_deletion(o);
}

void bhv_bowser_key_course_exit_loop(void) {
    s32 animTimer = o->header.gfx.unk38.animFrame;
    cur_obj_init_animation_with_sound(1);
    if (animTimer < 38)
        o->oBowserKeyScale = 0.2f;
    else if (animTimer < 52)
        o->oBowserKeyScale = (animTimer - 42) * 0.042857f + 0.2; // TODO 3/70?
    else if (animTimer < 94)
        o->oBowserKeyScale = 0.8f;
    else if (animTimer < 101)
        o->oBowserKeyScale = (101 - animTimer) * 0.085714f + 0.2; // TODO 6/70?
    else
        o->oBowserKeyScale = 0.2f;
    if (o->oTimer > 138)
        obj_mark_for_deletion(o);
}
