/**
 * Behavior for bhvHauntedBookshelf.
 * This is the bookshelf that recedes after solving the puzzle of the haunted books.
 * Its sole purpose is to recede when its action is set to 1 by a bhvHauntedBookshelfManager.
 */

/**
 * Update function for bhvHauntedBookshelf.
 */
void bhv_haunted_bookshelf_loop(void) {
    // oDistanceToMario is unused by this object.
    // This may have been used for revealing the books when Mario comes near,
    // but in the final game this is done by bhvHauntedBookshelfManager.
    o->oDistanceToMario = dist_between_objects(o, gMarioObject);

    o->oFaceAngleYaw = 0;

    switch (o->oAction) {
        case HAUNTED_BOOKSHELF_ACT_IDLE:
            // ???
            if (o->oTimer == 0) {
            }

            // This code never runs, since the action is set to 1 directly
            // by bhvHauntedBookshelfManager. Maybe this was
            // intended to be used to set the action instead?
            if (o->oHauntedBookshelfShouldOpen != FALSE) {
                o->oAction++;
            }

            break;
        case HAUNTED_BOOKSHELF_ACT_RECEDE:
            // Move the bookshelf and play the sound
            o->oPosX += 5.0f;
            cur_obj_play_sound_1(SOUND_ENV_ELEVATOR4_2);

            // Delete the object after 102 frames
            if (o->oTimer > 101) {
                obj_mark_for_deletion(o);
            }

            break;
        default:
            break;
    }
}
