/**
 * Behavior for bhvUnusedPoundablePlatform.
 *
 * This unused behavior controls a platform that shatters into small triangles
 * if Mario ground pounds it.
 *
 * Its collision model perfectly aligns with the hole leading to In the
 * Deep Freeze in Snowman's Land. It is likely that players early in development
 * would have to ground pound this platform before being able to access the star.
 *
 * Curiously, the triangles spawned when the platform breaks use a model that
 * is not used anywhere else.
 */

void bhv_unused_poundable_platform(void) {
    cur_obj_scale(1.02f);

    if (o->oAction == 0) {
        if (cur_obj_is_mario_ground_pounding_platform()) {
            spawn_mist_particles();
            spawn_triangle_break_particles(20, 56, 3.0f, 0);
            o->oAction++;
        }
    } else if (o->oTimer > 7) {
        obj_mark_for_deletion(o);
    }
    load_object_collision_model();
}
