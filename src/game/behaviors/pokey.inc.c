
/**
 * Behavior for bhvPokey and bhvPokeyBodyPart.
 * bhvPokey is responsible for the behavior of the pokey itself, as well as
 * spawning the body parts.
 * Pokey comes before its body parts in processing order, and the body parts
 * are processed top to bottom.
 */

/**
 * Hitbox for a single pokey body part.
 */
static struct ObjectHitbox sPokeyBodyPartHitbox = {
    /* interactType:      */ INTERACT_BOUNCE_TOP,
    /* downOffset:        */ 10,
    /* damageOrCoinValue: */ 2,
    /* health:            */ 0,
    /* numLootCoins:      */ 0,
    /* radius:            */ 40,
    /* height:            */ 20,
    /* hurtboxRadius:     */ 20,
    /* hurtboxHeight:     */ 20,
};

/**
 * Attack handlers for pokey body part.
 */
static u8 sPokeyBodyPartAttackHandlers[] = {
    /* ATTACK_PUNCH:                 */ ATTACK_HANDLER_KNOCKBACK,
    /* ATTACK_KICK_OR_TRIP:          */ ATTACK_HANDLER_KNOCKBACK,
    /* ATTACK_FROM_ABOVE:            */ ATTACK_HANDLER_SQUISHED,
    /* ATTACK_GROUND_POUND_OR_TWIRL: */ ATTACK_HANDLER_SQUISHED,
    /* ATTACK_FAST_ATTACK:           */ ATTACK_HANDLER_KNOCKBACK,
    /* ATTACK_FROM_BELOW:            */ ATTACK_HANDLER_KNOCKBACK,
};

/**
 * Update function for pokey body part.
 * The behavior parameter is the body part's index from 0 to 4, with 0 at the
 * top.
 */
void bhv_pokey_body_part_update(void) {
    // PARTIAL_UPDATE

    s16 offsetAngle;
    f32 baseHeight;

    if (obj_update_standard_actions(3.0f)) {
        if (o->parentObj->oAction == POKEY_ACT_UNLOAD_PARTS) {
            obj_mark_for_deletion(o);
        } else {
            cur_obj_update_floor_and_walls();
            obj_update_blinking(&o->oPokeyBodyPartBlinkTimer, 30, 60, 4);

            // If the body part above us is dead, then decrease body part index
            // by one, since new parts are spawned from the bottom.
            //! It is possible to briefly get two body parts to have the same
            //  index by killing two body parts on the frame before a new part
            //  spawns, but one of the body parts shifts upward immediately,
            //  so not very interesting
            if (o->oBehParams2ndByte > 1
                && !(o->parentObj->oPokeyAliveBodyPartFlags & (1 << (o->oBehParams2ndByte - 1)))) {
                o->parentObj->oPokeyAliveBodyPartFlags =
                    o->parentObj->oPokeyAliveBodyPartFlags | 1 << (o->oBehParams2ndByte - 1);

                o->parentObj->oPokeyAliveBodyPartFlags =
                    o->parentObj->oPokeyAliveBodyPartFlags & ((1 << o->oBehParams2ndByte) ^ ~0);

                o->oBehParams2ndByte -= 1;
            }

            // Set the bottom body part size, and gradually increase it.
            //! This "else if" means that if a body part above the expanding
            //  one dies, then the expanding will pause for one frame.
            //! If you kill a body part as it's expanding, the body part that
            //  was above it will instantly shrink and begin expanding in its
            //  place.
            else if (o->parentObj->oPokeyBottomBodyPartSize < 1.0f
                     && o->oBehParams2ndByte + 1 == o->parentObj->oPokeyNumAliveBodyParts) {
                approach_f32_ptr(&o->parentObj->oPokeyBottomBodyPartSize, 1.0f, 0.1f);
                cur_obj_scale(o->parentObj->oPokeyBottomBodyPartSize * 3.0f);
            }

            //! Pausing causes jumps in offset angle
            offsetAngle = o->oBehParams2ndByte * 0x4000 + gGlobalTimer * 0x800;
            o->oPosX = o->parentObj->oPosX + coss(offsetAngle) * 6.0f;
            o->oPosZ = o->parentObj->oPosZ + sins(offsetAngle) * 6.0f;

            // This is the height of the tower beneath the body part
            baseHeight = o->parentObj->oPosY
                         + (120 * (o->parentObj->oPokeyNumAliveBodyParts - o->oBehParams2ndByte) - 240)
                         + 120.0f * o->parentObj->oPokeyBottomBodyPartSize;

            // We treat the base height as a minimum height, allowing the body
            // part to briefly stay in the air after a part below it dies
            if (o->oPosY < baseHeight) {
                o->oPosY = baseHeight;
                o->oVelY = 0.0f;
            }

            // Only the head has loot coins
            if (o->oBehParams2ndByte == 0) {
                o->oNumLootCoins = 1;
            } else {
                o->oNumLootCoins = 0;
            }

            // If the body part was attacked, then die. If the head was killed,
            // then die after a delay.

            if (obj_handle_attacks(&sPokeyBodyPartHitbox, o->oAction, sPokeyBodyPartAttackHandlers)) {
                o->parentObj->oPokeyNumAliveBodyParts -= 1;
                if (o->oBehParams2ndByte == 0) {
                    o->parentObj->oPokeyHeadWasKilled = TRUE;
                    // Last minute change to blue coins - not sure why they didn't
                    // just set it to -1 above
                    o->oNumLootCoins = -1;
                }

                o->parentObj->oPokeyAliveBodyPartFlags =
                    o->parentObj->oPokeyAliveBodyPartFlags & ((1 << o->oBehParams2ndByte) ^ ~0);
            } else if (o->parentObj->oPokeyHeadWasKilled) {
                cur_obj_become_intangible();

                if (--o->oPokeyBodyPartDeathDelayAfterHeadKilled < 0) {
                    o->parentObj->oPokeyNumAliveBodyParts -= 1;
                    obj_die_if_health_non_positive();
                }
            } else {
                // Die in order from top to bottom
                // If a new body part spawns after the head has been killed, its
                // death delay will be 0
                o->oPokeyBodyPartDeathDelayAfterHeadKilled = (o->oBehParams2ndByte << 2) + 20;
            }

            cur_obj_move_standard(-78);
        }
    } else {
        o->oAnimState = 1;
    }

    o->oGraphYOffset = o->header.gfx.scale[1] * 22.0f;
}

/**
 * When mario gets within range, spawn the 5 body parts and enter the wander
 * action.
 */
static void pokey_act_uninitialized(void) {
    struct Object *bodyPart;
    s32 i;
    s16 partModel;

    if (o->oDistanceToMario < 2000.0f) {
        partModel = MODEL_POKEY_HEAD;

        for (i = 0; i < 5; i++) {
            // Spawn body parts at y offsets 480, 360, 240, 120, 0
            // behavior param 0 = head, 4 = lowest body part
            bodyPart = spawn_object_relative(i, 0, -i * 120 + 480, 0, o, partModel, bhvPokeyBodyPart);

            if (bodyPart != NULL) {
                obj_scale(bodyPart, 3.0f);
            }

            partModel = MODEL_POKEY_BODY_PART;
        }

        o->oPokeyAliveBodyPartFlags = 0x1F;
        o->oPokeyNumAliveBodyParts = 5;
        o->oPokeyBottomBodyPartSize = 1.0f;
        o->oAction = POKEY_ACT_WANDER;
    }
}

/**
 * Wander around, replenishing body parts if they are killed. When mario moves
 * far away, enter the unload parts action.
 * While wandering, if mario is within 2000 units, try to move toward him. But
 * if mario gets too close, then shy away from him.
 */
static void pokey_act_wander(void) {
    s32 targetAngleOffset;
    struct Object *bodyPart;

    if (o->oPokeyNumAliveBodyParts == 0) {
        obj_mark_for_deletion(o);
    } else if (o->oDistanceToMario > 2500.0f) {
        o->oAction = POKEY_ACT_UNLOAD_PARTS;
        o->oForwardVel = 0.0f;
    } else {
        treat_far_home_as_mario(1000.0f);
        cur_obj_update_floor_and_walls();

        if (o->oPokeyHeadWasKilled) {
            o->oForwardVel = 0.0f;
        } else {
            o->oForwardVel = 5.0f;

            // If a body part is missing, replenish it after 100 frames
            if (o->oPokeyNumAliveBodyParts < 5) {
                if (o->oTimer > 100) {
                    // Because the body parts shift index whenever a body part
                    // is killed, the new part's index is equal to the number
                    // of living body parts

                    bodyPart = spawn_object_relative(o->oPokeyNumAliveBodyParts, 0, 0, 0, o,
                                                     MODEL_POKEY_BODY_PART, bhvPokeyBodyPart);

                    if (bodyPart != NULL) {
                        o->oPokeyAliveBodyPartFlags =
                            o->oPokeyAliveBodyPartFlags | (1 << o->oPokeyNumAliveBodyParts);
                        o->oPokeyNumAliveBodyParts += 1;
                        o->oPokeyBottomBodyPartSize = 0.0f;

                        obj_scale(bodyPart, 0.0f);
                    }

                    o->oTimer = 0;
                }
            } else {
                o->oTimer = 0;
            }

            if (o->oPokeyTurningAwayFromWall) {
                o->oPokeyTurningAwayFromWall =
                    obj_resolve_collisions_and_turn(o->oPokeyTargetYaw, 0x200);
            } else {
                // If far from home, turn back toward home
                if (o->oDistanceToMario >= 25000.0f) {
                    o->oPokeyTargetYaw = o->oAngleToMario;
                }

                if (!(o->oPokeyTurningAwayFromWall =
                          obj_bounce_off_walls_edges_objects(&o->oPokeyTargetYaw))) {
                    if (o->oPokeyChangeTargetTimer != 0) {
                        o->oPokeyChangeTargetTimer -= 1;
                    } else if (o->oDistanceToMario > 2000.0f) {
                        o->oPokeyTargetYaw = obj_random_fixed_turn(0x2000);
                        o->oPokeyChangeTargetTimer = random_linear_offset(30, 50);
                    } else {
                        // The goal of this computation is to make pokey approach
                        // mario directly if he is far away, but to shy away from
                        // him when he is nearby

                        // targetAngleOffset is 0 when distance to mario is >= 1838.4
                        // and 0x4000 when distance to mario is <= 200
                        targetAngleOffset = (s32)(0x4000 - (o->oDistanceToMario - 200.0f) * 10.0f);
                        if (targetAngleOffset < 0) {
                            targetAngleOffset = 0;
                        } else if (targetAngleOffset > 0x4000) {
                            targetAngleOffset = 0x4000;
                        }

                        // If we need to rotate CCW to get to mario, then negate
                        // the target angle offset
                        if ((s16)(o->oAngleToMario - o->oMoveAngleYaw) > 0) {
                            targetAngleOffset = -targetAngleOffset;
                        }

                        // When mario is far, targetAngleOffset is 0, so he moves
                        // toward him directly. When mario is close,
                        // targetAngleOffset is 0x4000, so he turns 90 degrees
                        // away from mario
                        o->oPokeyTargetYaw = o->oAngleToMario + targetAngleOffset;
                    }
                }

                cur_obj_rotate_yaw_toward(o->oPokeyTargetYaw, 0x200);
            }
        }

        cur_obj_move_standard(-78);
    }
}

/**
 * Move back to home and enter the uninitialized action.
 * The pokey body parts check to see if pokey is in this action, and if so,
 * unload themselves.
 */
static void pokey_act_unload_parts(void) {
    o->oAction = POKEY_ACT_UNINITIALIZED;
    cur_obj_set_pos_to_home();
}

/**
 * Update function for pokey.
 */
void bhv_pokey_update(void) {
    // PARTIAL_UPDATE

    o->oDeathSound = SOUND_OBJ_POKEY_DEATH;

    switch (o->oAction) {
        case POKEY_ACT_UNINITIALIZED:
            pokey_act_uninitialized();
            break;
        case POKEY_ACT_WANDER:
            pokey_act_wander();
            break;
        case POKEY_ACT_UNLOAD_PARTS:
            pokey_act_unload_parts();
            break;
    }
}
