
/**
 * Behavior for bhvUnusedParticleSpawn.
 *
 * This unused behavior spawns 10 purple particles if Mario collides with its
 * associated object.
 */

void bhv_unused_particle_spawn_loop(void) {
    s32 i;
    cur_obj_update_floor_and_walls();
    cur_obj_move_standard(78);

    if (o->oMoveFlags & OBJ_MOVE_ON_GROUND) {
        obj_mark_for_deletion(o);
    }

    if (obj_check_if_collided_with_object(o, gMarioObject)) {
        obj_mark_for_deletion(o);

        for (i = 0; i < 10; i++) {
            spawn_object(o, MODEL_PURPLE_MARBLE, bhvPurpleParticle);
        }
    }
}
