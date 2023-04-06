/**
 * Behavior for bhvActivatedBackAndForthPlatform.
 * There are only 2 of these in the game; the BitFS gray elevator
 * and the BitS arrow platform.
 * Note: The filename is abbreviated to avoid compiler seg fault on long paths
 */

/**
 * Table of activated back-and-forth platform collision models.
 * The second entry is unused. It corresponds to the mesh platform
 * at the beginning of BitFS. In the game, it's a bhvPlatformOnTrack,
 * which allows for more complex movement; its path is mostly a straight line
 * except for where it dips into the lava. It seems the programmers
 * had it as a bhvActivatedBackAndForthPlatform initially, which moves
 * in a straight line, and wanted it to dip into the lava to make Mario have to
 * move off of it. To do this, they changed it to a bhvPlatformOnTrack, but
 * forgot to remove its entry in this table.
 */
static void *sActivatedBackAndForthPlatformCollisionModels[] = {
    /* ACTIVATED_BF_PLAT_TYPE_BITS_ARROW_PLAT */ bits_seg7_collision_0701AD54,
    /* ACTIVATED_BF_PLAT_TYPE_BITFS_MESH_PLAT */ bitfs_seg7_collision_070157E0,
    /* ACTIVATED_BF_PLAT_TYPE_BITFS_ELEVATOR  */ bitfs_seg7_collision_07015124
};

/**
 * Activated back-and-forth platform initialization function.
 */
void bhv_activated_back_and_forth_platform_init(void) {
    // Equivalent to the first behavior param byte & 3 (last 2 bits of the byte).
    s32 platformType = ((u16)(o->oBehParams >> 16) & 0x0300) >> 8;

    // The BitS arrow platform should flip 180º (0x8000 angle units), but
    // there is no reason for the other platforms to flip.
    if (platformType != ACTIVATED_BF_PLAT_TYPE_BITS_ARROW_PLAT) {
        o->oActivatedBackAndForthPlatformFlipRotation = 0;
    } else {
        o->oActivatedBackAndForthPlatformFlipRotation = 0x8000;
    }

    o->collisionData =
        segmented_to_virtual(sActivatedBackAndForthPlatformCollisionModels[platformType]);

    // Max distance the platform should move.
    // Equivalent to 50 * (oBehParams2ndByte & 0x7F), i.e. 50 * (oBehParams2ndByte % 128).
    // The maximum possible value of this is 50 * 127 = 6350.
    // It's 50 * 97 = 4850 in BitS and 50 * 31 = 1550 in BitFS.
    o->oActivatedBackAndForthPlatformMaxOffset = 50.0f * ((u16)(o->oBehParams >> 16) & 0x007F);

    if (platformType == ACTIVATED_BF_PLAT_TYPE_BITFS_ELEVATOR) {
        o->oActivatedBackAndForthPlatformMaxOffset -= 12.0f;
    }

    // Truthy/falsy value that determines the direction of movement.
    // Equivalent to oBehParams2ndByte & 0x80, i.e. the most significant bit of oBehParams2ndByte.
    o->oActivatedBackAndForthPlatformVertical = (u16)(o->oBehParams >> 16) & 0x0080;

    o->oActivatedBackAndForthPlatformStartYaw = o->oFaceAngleYaw;
}

/**
 * Activated back-and-forth platform update function.	
 */
void bhv_activated_back_and_forth_platform_update(void) {
    UNUSED s32 unused[3];

    // oVelY is used for vertical platforms' movement and also for
    // horizontal platforms' dipping up/down when Mario gets on/off them
    if (gMarioObject->platform == o) {
        o->oVelY = -6.0f;
    } else {
        o->oVelY = 6.0f;
    }

    // If the platform's velocity is set...
    if (o->oActivatedBackAndForthPlatformVel != 0.0f) {
        // ...wait until the countdown is 0 before moving.
        // Since there's a 1 frame "lag" after the countdown is set to 20,
        // and one more frame of "lag" after it finally reaches 0 here,
        // Mario actually has to wait 22 frames before the platform starts moving.
        if (o->oActivatedBackAndForthPlatformCountdown != 0) {
            o->oActivatedBackAndForthPlatformCountdown -= 1;
        } else {
            // After the wait period is over, we start moving, by adding the velocity
            // to the positional offset.
            o->oActivatedBackAndForthPlatformOffset += o->oActivatedBackAndForthPlatformVel;

            // clamp_f32 returns whether the value needed to be clamped.
            // So if the offset got out of bounds (i.e. platform has reached an end of its path),
            // or Mario is over 3000 units away, the platform will reset the wait timer and flip around.
            if (clamp_f32(&o->oActivatedBackAndForthPlatformOffset, 0.0f,
                          o->oActivatedBackAndForthPlatformMaxOffset)
                ||
                // The platform will not reset if Mario goes far away and it's travelling backwards
                (o->oActivatedBackAndForthPlatformVel > 0.0f && o->oDistanceToMario > 3000.0f)) {
                // Reset the wait timer
                o->oActivatedBackAndForthPlatformCountdown = 20;

                // oVelY is only negative if Mario is on the platform,
                // so if Mario is on the platform or the platform is going forwards when it resets,
                // the platform will reverse directions. Otherwise, it will stop.
                // This means that if Mario touches the platform initially, then gets off,
                // it will do a full round trip then stop (assuming Mario stays within 3000 units).
                if (o->oVelY < 0.0f || o->oActivatedBackAndForthPlatformVel > 0.0f) {
                    o->oActivatedBackAndForthPlatformVel = -o->oActivatedBackAndForthPlatformVel;
                } else {
                    o->oActivatedBackAndForthPlatformVel = 0.0f;
                }

                // Make the platform face the opposite way if it should.
                // This is for the BitS arrow platform, which has an indicated direction.
                o->oFaceAngleYaw += o->oActivatedBackAndForthPlatformFlipRotation;
            }
        }
    } else {
        // oVelY is only negative if Mario is on the platform
        if (o->oVelY < 0.0f) {
            o->oActivatedBackAndForthPlatformVel = 10.0f;
        }

        // Set waiting countdown to 20 frames
        o->oActivatedBackAndForthPlatformCountdown = 20;
    }

    // Save the object's current position to a safe location.
    obj_perform_position_op(POS_OP_SAVE_POSITION);

    // Update the object's position.
    // If the platform moves vertically...
    if (o->oActivatedBackAndForthPlatformVertical != FALSE) {
        // ...set its position to its original position + the offset.
        o->oPosY = o->oHomeY + o->oActivatedBackAndForthPlatformOffset;
    } else {
        // Otherwise, dip down 20 units if Mario gets on the horizontal platform, and undo if he gets
        // off.
        o->oPosY += o->oVelY;
        clamp_f32(&o->oPosY, o->oHomeY - 20.0f, o->oHomeY);

        // Update the position using the object's home (original position), facing angle, and offset.
        // This has to be done manually when the platform is vertical because only the yaw is used
        // by this function; it doesn't update the Y position.
        obj_set_dist_from_home(-o->oActivatedBackAndForthPlatformOffset);
    }

    // Compute the object's velocity using the old saved position.
    obj_perform_position_op(POS_OP_COMPUTE_VELOCITY);
}
