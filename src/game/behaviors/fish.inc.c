
/**
 * @file fish.inc.c
 * Implements behaviour and spawning for fish located in the Secret Aquarium and other levels.
 */

/**
 * Spawns fish with settings chosen by oBehParams2ndByte.
 * These settings are animations, colour, and spawn quantity.
 */
static void fish_spawner_act_spawn(void) {
    s32 i;
    s32 schoolQuantity;
    s16 model;
    f32 minDistToMario;
    const struct Animation * const *fishAnimation;

    switch (o->oBehParams2ndByte) {
        // Cases need to be on one line to match with and without optimizations.
        case FISH_SPAWNER_BP_MANY_BLUE:
            model = MODEL_FISH;      schoolQuantity = 20; minDistToMario = 1500.0f; fishAnimation = blue_fish_seg3_anims_0301C2B0;
            break;

        case FISH_SPAWNER_BP_FEW_BLUE:
            model = MODEL_FISH;      schoolQuantity = 5;  minDistToMario = 1500.0f; fishAnimation = blue_fish_seg3_anims_0301C2B0;
            break;

        case FISH_SPAWNER_BP_MANY_CYAN:
            model = MODEL_CYAN_FISH; schoolQuantity = 20; minDistToMario = 1500.0f; fishAnimation = cyan_fish_seg6_anims_0600E264;
            break;

        case FISH_SPAWNER_BP_FEW_CYAN:
            model = MODEL_CYAN_FISH; schoolQuantity = 5;  minDistToMario = 1500.0f; fishAnimation = cyan_fish_seg6_anims_0600E264;
            break;
    }

    // Spawn and animate the schoolQuantity of fish if Mario enters render distance
    // or the stage is Secret Aquarium.
    // Fish moves randomly within a range of 700.0f.
    if (o->oDistanceToMario < minDistToMario || gCurrLevelNum == LEVEL_SA) {
        for (i = 0; i < schoolQuantity; i++) {
            struct Object *fishObject = spawn_object(o, model, bhvFish);
            fishObject->oBehParams2ndByte = o->oBehParams2ndByte;
            obj_init_animation_with_sound(fishObject, fishAnimation, 0);
            obj_translate_xyz_random(fishObject, 700.0f);
        }
        o->oAction = FISH_SPAWNER_ACT_IDLE;
    }
}

/**
 * Sets the spawner to respawn fish if the stage is not Secret Aquarium and
 * Mario is more than 2000 units higher.
 */
static void fish_spawner_act_idle(void) {
    if ((gCurrLevelNum != LEVEL_SA) && (gMarioObject->oPosY - o->oPosY > 2000.0f)) {
        o->oAction = FISH_SPAWNER_ACT_RESPAWN;
    }
}

/**
 * Temp action that sets the action to spawn fish. This triggers the old fish to despawn.
 */
static void fish_spawner_act_respawn(void) {
    o->oAction = FISH_SPAWNER_ACT_SPAWN;
}

static void (*sFishSpawnerActions[])(void) = {
    fish_spawner_act_spawn,
    fish_spawner_act_idle,
    fish_spawner_act_respawn,
};

void bhv_fish_spawner_loop(void) {
    cur_obj_call_action_function(sFishSpawnerActions);
}

/**
 * Allows the fish to swim vertically.
 */
static void fish_vertical_roam(s32 speed) {
    f32 parentY = o->parentObj->oPosY;

    // If the stage is Secret Aquarium, the fish can 
    // travel as far vertically as they wish.
    if (gCurrLevelNum == LEVEL_SA) {
        if (500.0f < absf(o->oPosY - o->oFishGoalY)) {
            speed = 10;
        }
        o->oPosY = approach_f32_symmetric(o->oPosY, o->oFishGoalY, speed);

     // Allow the fish to roam vertically if within
     // range of the fish spawner.
     } else if (parentY - 100.0f - o->oFishDepthDistance < o->oPosY
               && o->oPosY < parentY + 1000.0f + o->oFishDepthDistance) {
        o->oPosY = approach_f32_symmetric(o->oPosY, o->oFishGoalY, speed);
    }
}

/**
 * Fish action that randomly roams within a set range.
 */
static void fish_act_roam(void) {
    f32 fishY = o->oPosY - gMarioObject->oPosY;

    // Alters speed of animation for natural movement.
    if (o->oTimer < 10) {
        cur_obj_init_animation_with_accel_and_sound(0, 2.0f);
    } else {
        cur_obj_init_animation_with_accel_and_sound(0, 1.0f);
    }

    // Initializes some variables when the fish first begins roaming.
    if (o->oTimer == 0) {
        o->oForwardVel = random_float() * 2 + 3.0f;
        if (gCurrLevelNum == LEVEL_SA) {
            o->oFishHeightOffset = random_float() * 700.0f;
        } else {
            o->oFishHeightOffset = random_float() * 100.0f;
        }
        o->oFishRoamDistance = random_float() * 500 + 200.0f;
    }

    o->oFishGoalY = gMarioObject->oPosY + o->oFishHeightOffset;

    // Rotate the fish towards Mario.
    cur_obj_rotate_yaw_toward(o->oAngleToMario, 0x400);

    if (o->oPosY < o->oFishWaterLevel - 50.0f) {
        if (fishY < 0.0f) {
            fishY = 0.0f - fishY;
        }
        if (fishY < 500.0f) {
            fish_vertical_roam(2);
        } else {
            fish_vertical_roam(4);
        }

    // Don't let the fish leave the water vertically.
    } else {
        o->oPosY = o->oFishWaterLevel - 50.0f;
        if (fishY > 300.0f) {
            o->oPosY = o->oPosY - 1.0f;
        }
    }

    // Flee from Mario if the fish gets too close.
    if (o->oDistanceToMario < o->oFishRoamDistance + 150.0f) {
        o->oAction = FISH_ACT_FLEE;
    }
}

/**
 * Interactively maneuver fish in relation to its distance from other fish and Mario.
 */
static void fish_act_flee(void) {
    f32 fishY = o->oPosY - gMarioObject->oPosY;
    UNUSED s32 distance;

    o->oFishGoalY = gMarioObject->oPosY + o->oFishHeightOffset;

    // Initialize some variables when the flee action first starts.
    if (o->oTimer == 0) {
        o->oFishActiveDistance = random_float() * 300.0f;
        o->oFishYawVel = random_float() * 1024.0f + 1024.0f;
        o->oFishGoalVel = random_float() * 4.0f + 8.0f + 5.0f;

        if (o->oDistanceToMario < 600.0f) {
            distance = 1;
        } else {
            distance = (s32)(1.0 / (o->oDistanceToMario / 600.0));
        }

        distance *= 127;

        cur_obj_play_sound_2(SOUND_GENERAL_MOVING_WATER);
    }

    // Speed the animation up over time.
    if (o->oTimer < 20) {
        cur_obj_init_animation_with_accel_and_sound(0, 4.0f);
    } else {
        cur_obj_init_animation_with_accel_and_sound(0, 1.0f);
    }

    // Accelerate over time.
    if (o->oForwardVel < o->oFishGoalVel) {
        o->oForwardVel = o->oForwardVel + 0.5;
    }

    o->oFishGoalY = gMarioObject->oPosY + o->oFishHeightOffset;

    // Rotate fish away from Mario.
    cur_obj_rotate_yaw_toward(o->oAngleToMario + 0x8000, o->oFishYawVel);

    if (o->oPosY < o->oFishWaterLevel - 50.0f) {
        if (fishY < 0.0f) {
            fishY = 0.0f - fishY;
        }

        if (fishY < 500.0f) {
            fish_vertical_roam(2);
        } else {
            fish_vertical_roam(4);
        }

    // Don't let the fish leave the water vertically.
    } else {
        o->oPosY = o->oFishWaterLevel - 50.0f;
        if (fishY > 300.0f) {
            o->oPosY -= 1.0f;
        }
    }

    // If distance to Mario is too great, then set fish to active.
    if (o->oDistanceToMario > o->oFishActiveDistance + 500.0f) {
        o->oAction = FISH_ACT_ROAM;
    }
}

/**
 * Animate fish and alter scaling at random for a magnifying effect from the water.
 */
static void fish_act_init(void) {
    cur_obj_init_animation_with_accel_and_sound(0, 1.0f);
    o->header.gfx.animInfo.animFrame = (s16)(random_float() * 28.0f);
    o->oFishDepthDistance = random_float() * 300.0f;
    cur_obj_scale(random_float() * 0.4 + 0.8);
    o->oAction = FISH_ACT_ROAM;
}

static void (*sFishActions[])(void) = {
    fish_act_init,
    fish_act_roam,
    fish_act_flee,
};

/**
 * Main loop for fish
 */
void bhv_fish_loop(void) {
    UNUSED u8 filler[16];
    cur_obj_scale(1.0f);

    // oFishWaterLevel tracks if a fish has roamed out of water.
    // This can't happen in Secret Aquarium, so set it to 0.
    o->oFishWaterLevel = find_water_level(o->oPosX, o->oPosZ);
    if (gCurrLevelNum == LEVEL_SA) {
        o->oFishWaterLevel = 0.0f;
    }

    // Apply hitbox and resolve wall collisions
    o->oWallHitboxRadius = 30.0f;
    cur_obj_resolve_wall_collisions();

    // Delete fish if it's drifted to an area with no water.
    if (gCurrLevelNum != LEVEL_UNKNOWN_32) {
        if (o->oFishWaterLevel < FLOOR_LOWER_LIMIT_MISC) {
            obj_mark_for_deletion(o);
            return;
        }

    // Unreachable code, perhaps for debugging or testing.
    } else {
        o->oFishWaterLevel = 1000.0f;
    }

    // Call fish action methods and apply physics engine.
    cur_obj_call_action_function(sFishActions);
    cur_obj_move_using_fvel_and_gravity();

    // If the parent object has action set to two, then delete the fish object.
    if (o->parentObj->oAction == FISH_SPAWNER_ACT_RESPAWN) {
        obj_mark_for_deletion(o);
    }
}
