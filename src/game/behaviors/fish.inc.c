/**
 * @file fish.inc.c
 * Implements behaviour and spawning for fish located in the Secret Aquarium and other levels.
 */

/**
 * Spawns fish with settings chosen by the field o->oBehParams2ndByte.
 * These settings are animations, colour, and spawn quantity.
 * Fish spawning restricted to within a set distance from Mario.
 */
void fish_act_spawn(void) {
    s32 i;
    s32 schoolQuantity;
    s16 model;
    f32 minDistToMario;
    struct Animation **fishAnimation;
    struct Object *fishObject;
    switch (o->oBehParams2ndByte) {
        
        // Blue fish with a quanitiy of twenty.
        case 0:
            model = MODEL_FISH;    schoolQuantity = 20;    minDistToMario = 1500.0f;    fishAnimation = blue_fish_seg3_anims_0301C2B0;
            break;
            
        // Blue fish with a quanitiy of five.
        case 1:
            model = MODEL_FISH;    schoolQuantity = 5;    minDistToMario = 1500.0f;    fishAnimation = blue_fish_seg3_anims_0301C2B0;
            break;
            
        // Cyan fish with a quanitiy of twenty.
        case 2:
            model = MODEL_CYAN_FISH;    schoolQuantity = 20;    minDistToMario = 1500.0f;    fishAnimation = cyan_fish_seg6_anims_0600E264;
            break;
            
        // Cyan fish with a quanitiy of five.
        case 3:
            model = MODEL_CYAN_FISH;    schoolQuantity = 5;    minDistToMario = 1500.0f;    fishAnimation = cyan_fish_seg6_anims_0600E264;
            break;
    }
    /**
     * Spawn and animate the schoolQuantity of fish if Mario enters render distance
     * If the current level is Secret Aquarium, ignore this requirement.
     * Fish moves at random with a max-range of 700.0f.
     */
    if (o->oDistanceToMario < minDistToMario || gCurrLevelNum == LEVEL_SA) {
        for (i = 0; i < schoolQuantity; i++) {
            fishObject = spawn_object(o, model, bhvFish);
            fishObject->oBehParams2ndByte = o->oBehParams2ndByte;
            obj_init_animation_with_sound(fishObject, fishAnimation, 0);
            obj_translate_xyz_random(fishObject, 700.0f);
        }
        o->oAction = FISH_ACT_ACTIVE;
    }
}

/**
 * If the current level is not Secret Aquarium and the distance from Mario's
 * Y coordinate is greater than 2000.0f then spawn another fish.
 */
void fish_act_respawn(void) {
    if (gCurrLevelNum != LEVEL_SA) {
        if (gMarioObject->oPosY - o->oPosY > 2000.0f) {
            o->oAction = FISH_ACT_RESPAWN;
        }
    }
}

/**
 * Sets the next call of sFishActions to spawn a new fish.
 */
void fish_act_init(void) {
    o->oAction = FISH_ACT_INIT;
}

/**
 * An array of action methods chosen one at a time by bhv_fish_loop
 */
void (*sFishActions[])(void) = {
    fish_act_spawn,    fish_act_respawn,    fish_act_init 
};

void bhv_large_fish_group_loop(void) {
    cur_obj_call_action_function(sFishActions);
}

/**
 * Adjusts the Y coordinate of fish depending on circumstances
 * such as proximity to other fish.
 */
void fish_regroup(s32 speed) {
    // Store parentY for calculating when the fish should move towards oFishPosY.
    f32 parentY = o->parentObj->oPosY;
    
    // Sets speed of fish in SA to a leisurely speed of 10 when close to other fish. 
    if (gCurrLevelNum == LEVEL_SA) {
        if (500.0f < absf(o->oPosY - o->oFishPosY)) {
            speed = 10;
        }
        // Applies movement to fish.
        o->oPosY = approach_f32_symmetric(o->oPosY, o->oFishPosY, speed);
    /**
     * Brings fish Y coordinate towards another fish if they are too far apart.
     */
    } else if (parentY - 100.0f - o->oFishDepthDistance < o->oPosY
               && o->oPosY < parentY + 1000.0f + o->oFishDepthDistance) {
        o->oPosY = approach_f32_symmetric(o->oPosY, o->oFishPosY, speed);
    }
}
/**
 * Moves fish forward at a random velocity and sets a random rotation.
 */
void fish_group_act_rotation(void) {
    f32 fishY = o->oPosY - gMarioObject->oPosY;
    
    // Alters speed of animation for natural movement.
    if (o->oTimer < 10) {
        cur_obj_init_animation_with_accel_and_sound(0, 2.0f);
    } else {
        cur_obj_init_animation_with_accel_and_sound(0, 1.0f);
    }
    
    /**
     * Assigns oForwardVel, oFishRandomOffset, & oFishRespawnDistance to a random floats.
     * Determines fish movement.
     */
    if (o->oTimer == 0) {
        o->oForwardVel = random_float() * 2 + 3.0f;
        if (gCurrLevelNum == LEVEL_SA) {
            o->oFishRandomOffset = random_float() * 700.0f;
        } else {
            o->oFishRandomOffset = random_float() * 100.0f;
        }
        o->oFishRespawnDistance = random_float() * 500 + 200.0f;
    }
    
    // Interact with Mario through rotating towards him.
    o->oFishPosY = gMarioObject->oPosY + o->oFishRandomOffset;
    cur_obj_rotate_yaw_toward(o->oAngleToMario, 0x400);
    
    // If fish groups are too close, call fish_regroup()
    if (o->oPosY < o->oFishWaterLevel - 50.0f) {
        if (fishY < 0.0f) {
            fishY = 0.0f - fishY;
        }
        if (fishY < 500.0f) {
            fish_regroup(2);
        } else {
            fish_regroup(4);
        }
    } else {
        o->oPosY = o->oFishWaterLevel - 50.0f;
        if (fishY > 300.0f) {
            o->oPosY = o->oPosY - 1.0f;
        }
    }
    
    /**
     * Delete current fish and create a new one if distance to Mario is
     * smaller than his distance to oFishRespawnDistance + 150.0f.
     */
    if (o->oDistanceToMario < o->oFishRespawnDistance + 150.0f) {
        o->oAction = FISH_ACT_RESPAWN;
    }
}

/**
 * Interactively maneuver fish in relation to its distance from other fish and Mario.
 */
void fish_group_act_move(void) {
    f32 fishY = o->oPosY - gMarioObject->oPosY;
    // Marked unused, but has arithmetic performed on it in a useless manner.
    UNUSED s32 distance;
    o->oFishPosY = gMarioObject->oPosY + o->oFishRandomOffset;
    /**
     * Set fish variables to random floats when timer reaches zero and plays sound effect.
     * This allows fish to move in seemingly natural patterns.
     */
    if (o->oTimer == 0) {
        o->oFishActiveDistance = random_float() * 300.0f;
        o->oFishRandomSpeed = random_float() * 1024.0f + 1024.0f;
        o->oFishRandomVel = random_float() * 4.0f + 8.0f + 5.0f;
        if (o->oDistanceToMario < 600.0f) {
            distance = 1;
        } else {
            distance = (s32)(1.0 / (o->oDistanceToMario / 600.0));
        }
        distance *= 127;
        cur_obj_play_sound_2(SOUND_GENERAL_MOVING_WATER);
    }
    // Enable fish animation in a natural manner.
    if (o->oTimer < 20) {
        cur_obj_init_animation_with_accel_and_sound(0, 4.0f);
    } else {
        cur_obj_init_animation_with_accel_and_sound(0, 1.0f);
    }
    // Set randomized forward velocity so fish have differing velocities
    if (o->oForwardVel < o->oFishRandomVel) {
        o->oForwardVel = o->oForwardVel + 0.5;
    }
    o->oFishPosY = gMarioObject->oPosY + o->oFishRandomOffset;
    // Rotate fish away from Mario.
    cur_obj_rotate_yaw_toward(o->oAngleToMario + 0x8000, o->oFishRandomSpeed);
    // If fish groups are too close, call fish_regroup()
    if (o->oPosY < o->oFishWaterLevel - 50.0f) {
        if (fishY < 0.0f) {
            fishY = 0.0f - fishY;
        }
        if (fishY < 500.0f) {
            fish_regroup(2);
        } else {
            fish_regroup(4);
        }
    } else {
        o->oPosY = o->oFishWaterLevel - 50.0f;
        if (fishY > 300.0f) {
            o->oPosY -= 1.0f;
        }
    }
    // If distance to Mario is too great, then set fish to active.
    if (o->oDistanceToMario > o->oFishActiveDistance + 500.0f) {
        o->oAction = FISH_ACT_ACTIVE;
    }
}
/**
 * Animate fish and alter scaling at random for a magnifying effect from the water.
 */
void fish_group_act_animate(void) {
    cur_obj_init_animation_with_accel_and_sound(0, 1.0f);
    o->header.gfx.unk38.animFrame = (s16)(random_float() * 28.0f);
    o->oFishDepthDistance = random_float() * 300.0f;
    cur_obj_scale(random_float() * 0.4 + 0.8);
    o->oAction = FISH_ACT_ACTIVE;
}

void (*sFishGroupActions[])(void) = { 
    fish_group_act_animate,    fish_group_act_rotation,    fish_group_act_move
};

/**
 * Main loop for fish
 */
void bhv_fish_loop(void)
{
    UNUSED s32 unused[4];
    cur_obj_scale(1.0f);

    /**
     * Tracks water level to delete fish outside of bounds.
     * In SA oFishWaterLevel is set to zero because fish cannot exit the water.
     * This prevents accidental deletion.
     */
    o->oFishWaterLevel = find_water_level(o->oPosX, o->oPosZ);
    if (gCurrLevelNum == LEVEL_SA) {
        o->oFishWaterLevel = 0.0f;
    }
    // Apply hitbox and resolve wall collisions 
    o->oWallHitboxRadius = 30.0f;
    cur_obj_resolve_wall_collisions();
    
    // Delete fish below the water depth bounds of -10000.0f.
    if (gCurrLevelNum != LEVEL_UNKNOWN_32) {
        if (o->oFishWaterLevel < -10000.0f) {
            obj_mark_for_deletion(o);
            return;
        }
        
    // Unreachable code, perhaps for debugging or testing.
    } else {
        o->oFishWaterLevel = 1000.0f;
    }
    
    // Call fish action methods and apply physics engine.
    cur_obj_call_action_function(sFishGroupActions);
    cur_obj_move_using_fvel_and_gravity();
    
    // If the parent object has action set to two, then delete the fish object.
    if (o->parentObj->oAction == FISH_ACT_RESPAWN) {
        obj_mark_for_deletion(o);
    }
}
