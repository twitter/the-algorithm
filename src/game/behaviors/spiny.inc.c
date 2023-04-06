
/**
 * Behavior for bhvSpiny.
 * When spawned by lakitu, its parent object is the lakitu.
 * Lakitu comes before it spawned spinies in processing order.
 */

/**
 * Hitbox for spiny both while thrown and walking. The interaction type is
 * changed to INTERACT_UNKNOWN_08 while walking.
 */
static struct ObjectHitbox sSpinyHitbox = {
    /* interactType:      */ INTERACT_MR_BLIZZARD,
    /* downOffset:        */ 0,
    /* damageOrCoinValue: */ 2,
    /* health:            */ 0,
    /* numLootCoins:      */ 0,
    /* radius:            */ 80,
    /* height:            */ 50,
    /* hurtboxRadius:     */ 40,
    /* hurtboxHeight:     */ 40,
};

/**
 * Attack handlers for spiny while walking.
 */
static u8 sSpinyWalkAttackHandlers[] = {
    /* ATTACK_PUNCH:                 */ ATTACK_HANDLER_KNOCKBACK,
    /* ATTACK_KICK_OR_TRIP:          */ ATTACK_HANDLER_KNOCKBACK,
    /* ATTACK_FROM_ABOVE:            */ ATTACK_HANDLER_NOP,
    /* ATTACK_GROUND_POUND_OR_TWIRL: */ ATTACK_HANDLER_NOP,
    /* ATTACK_FAST_ATTACK:           */ ATTACK_HANDLER_KNOCKBACK,
    /* ATTACK_FROM_BELOW:            */ ATTACK_HANDLER_KNOCKBACK,
};

/**
 * If the spiny was spawned by lakitu and mario is far away, despawn.
 */
static s32 spiny_check_active(void) {
    if (o->parentObj != o) {
        if (o->oDistanceToMario > 2500.0f) {
            //! It's possible for the lakitu to despawn while the spiny still
            //  references it. This line allows us to decrement the 0x1B field
            //  in an object that loads into the lakitu's former slot.
            //  This can be used in practice to corrupt a huge goomba to
            //  behave similar to a regular goomba.
            //  It can also be used on a bob-omb respawner to change its model
            //  to a butterfly or fish.
            o->parentObj->oEnemyLakituNumSpinies -= 1;
            obj_mark_for_deletion(o);
            return FALSE;
        }
    }

    return TRUE;
}

/**
 * Walk around randomly, and dick around with oFlags, oMoveFlags, and oInteractType.
 */
static void spiny_act_walk(void) {
    if (spiny_check_active()) {
        cur_obj_update_floor_and_walls();

        o->oGraphYOffset = -17.0f;
        cur_obj_init_animation_with_sound(0);

        if (o->oMoveFlags & OBJ_MOVE_MASK_ON_GROUND) {
            // After touching the ground for the first time, stop. From now on,
            // ensure that face angle and move angle agree
            if (!(o->oFlags & OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW)) {
                if (obj_forward_vel_approach(0.0f, 1.0f)) {
                    o->oFlags |= OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW;
                    o->oMoveAngleYaw = o->oFaceAngleYaw;
                }
            } else {
                obj_forward_vel_approach(1.0f, 0.2f);
            }

            if (o->oSpinyTurningAwayFromWall) {
                o->oSpinyTurningAwayFromWall =
                    obj_resolve_collisions_and_turn(o->oSpinyTargetYaw, 0x80);
            } else {
                if (!(o->oSpinyTurningAwayFromWall =
                          obj_bounce_off_walls_edges_objects(&o->oSpinyTargetYaw))) {
                    // Walk and occasionally randomly change direction
                    if (o->oSpinyTimeUntilTurn != 0) {
                        o->oSpinyTimeUntilTurn -= 1;
                    } else {
                        o->oSpinyTargetYaw = o->oMoveAngleYaw + (s16) random_sign() * 0x2000;
                        o->oSpinyTimeUntilTurn = random_linear_offset(100, 100);
                    }
                }

                cur_obj_rotate_yaw_toward(o->oSpinyTargetYaw, 0x80);
            }

        } else if (o->oMoveFlags & OBJ_MOVE_HIT_WALL) {
            // Bounce off walls while falling
            o->oMoveAngleYaw = cur_obj_reflect_move_angle_off_wall();
        }

        cur_obj_move_standard(-78);

        if (obj_handle_attacks(&sSpinyHitbox, SPINY_ACT_ATTACKED_MARIO, sSpinyWalkAttackHandlers)) {
            // When attacked by mario, lessen the knockback
            o->oAction = SPINY_ACT_WALK;
            o->oForwardVel *= 0.1f;
            o->oVelY *= 0.7f;

            o->oMoveFlags = 0; // weird flex but okay

            // Don't allow mario to punch the spiny two frames in a row?
            o->oInteractType = INTERACT_MR_BLIZZARD;
        } else {
            o->oInteractType = INTERACT_UNKNOWN_08;
        }
    }
}

/**
 * Wait for the lakitu to throw the spiny. The spiny is placed in this action
 * after being spawned by a lakitu.
 */
static void spiny_act_held_by_lakitu(void) {
    o->oGraphYOffset = 15.0f;
    cur_obj_init_animation_with_sound(0);

    o->oParentRelativePosX = -50.0f;
    o->oParentRelativePosY = 35.0f;
    o->oParentRelativePosZ = -100.0f;

    if (o->parentObj->prevObj == NULL) {
        o->oAction = SPINY_ACT_THROWN_BY_LAKITU;
        o->oMoveAngleYaw = o->parentObj->oFaceAngleYaw;

        // Move more quickly if the lakitu is moving forward
        o->oForwardVel =
            o->parentObj->oForwardVel * coss(o->oMoveAngleYaw - o->parentObj->oMoveAngleYaw) + 10.0f;
        o->oVelY = 30.0f;

        o->oMoveFlags = 0; // you do you, spiny
    }
}

/**
 * Spin around. After landing, enter the walk action.
 */
static void spiny_act_thrown_by_lakitu(void) {
    if (spiny_check_active()) {
        cur_obj_update_floor_and_walls();

        o->oGraphYOffset = 15.0f;
        o->oFaceAnglePitch -= 0x2000;

        cur_obj_init_animation_with_sound(0);

        if (o->oMoveFlags & OBJ_MOVE_LANDED) {
            cur_obj_play_sound_2(SOUND_OBJ_SPINY_UNK59);
            cur_obj_set_model(MODEL_SPINY);
            obj_init_animation_with_sound(o, spiny_seg5_anims_05016EAC, 0);
            o->oGraphYOffset = -17.0f;

            o->oFaceAnglePitch = 0;
            o->oAction = SPINY_ACT_WALK;
        } else if (o->oMoveFlags & OBJ_MOVE_HIT_WALL) {
            o->oMoveAngleYaw = cur_obj_reflect_move_angle_off_wall();
        }

        cur_obj_move_standard(-78);

        if (obj_check_attacks(&sSpinyHitbox, o->oAction)) {
            if (o->parentObj != o) {
                o->parentObj->oEnemyLakituNumSpinies -= 1;
            }
        }
    }
}

/**
 * Update function for bhvSpiny.
 */
void bhv_spiny_update(void) {
    // PARTIAL_UPDATE

    switch (o->oAction) {
        case SPINY_ACT_WALK:
            spiny_act_walk();
            break;
        case SPINY_ACT_HELD_BY_LAKITU:
            spiny_act_held_by_lakitu();
            break;
        case SPINY_ACT_THROWN_BY_LAKITU:
            spiny_act_thrown_by_lakitu();
            break;
        case SPINY_ACT_ATTACKED_MARIO:
            obj_move_for_one_second(SPINY_ACT_WALK);
            break;
    }
}
