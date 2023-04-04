// strong_wind_particle.inc.c

struct ObjectHitbox sStrongWindParticleHitbox = {
    /* interactType:      */ INTERACT_STRONG_WIND,
    /* downOffset:        */ 0,
    /* damageOrCoinValue: */ 0,
    /* health:            */ 0,
    /* numLootCoins:      */ 0,
    /* radius:            */ 20,
    /* height:            */ 70,
    /* hurtboxRadius:     */ 20,
    /* hurtboxHeight:     */ 70,
};

void bhv_strong_wind_particle_loop(void) {
    struct Object *penguinObj;
    f32 distanceFromPenguin;
    f32 penguinXDist, penguinZDist;

    obj_set_hitbox(o, &sStrongWindParticleHitbox);

    if (o->oTimer == 0) {
        o->oStrongWindParticlePenguinObj = cur_obj_nearest_object_with_behavior(bhvSLWalkingPenguin);
        obj_translate_xyz_random(o, 100.0f);

        o->oForwardVel = coss(o->oMoveAnglePitch) * 100.0f;
        o->oVelY = sins(o->oMoveAnglePitch) * -100.0f;

        o->oMoveAngleYaw += random_f32_around_zero(o->oBehParams2ndByte * 500); // Wind spread
        o->oOpacity = 100;
    }

    cur_obj_move_using_fvel_and_gravity();
    if (o->oTimer > 15) { // Deactivate after 15 frames
        obj_mark_for_deletion(o);
    }

    // If collided with the SL walking penguin, deactivate.
    penguinObj = o->oStrongWindParticlePenguinObj;
    if (penguinObj != NULL) {
        penguinXDist = penguinObj->oSLWalkingPenguinWindCollisionXPos - o->oPosX;
        penguinZDist = penguinObj->oSLWalkingPenguinWindCollisionZPos - o->oPosZ;
        distanceFromPenguin = sqrtf(penguinXDist * penguinXDist + penguinZDist * penguinZDist);
        if (distanceFromPenguin < 300.0f) {
            obj_mark_for_deletion(o);
            cur_obj_become_intangible();
        }
    }
}

// Spawn particles that blow Mario away and knock his cap off from the current object.
// Used for the Snowman in SL and Fwoosh.
void cur_obj_spawn_strong_wind_particles(s32 windSpread, f32 scale, f32 relPosX, f32 relPosY, f32 relPosZ) {
    // Alternate between tiny particles and regular particles each frame.
    if (gGlobalTimer & 1) {
        // Because the tiny particles are unimportant objects, invisible wind particles are spawned to provide collision.
        // There was absolutely no reason to make the smaller particles unimportant, though...
        spawn_object_relative_with_scale(windSpread, relPosX, relPosY, relPosZ, 0.5f, o, MODEL_WHITE_PARTICLE_DL, bhvTinyStrongWindParticle);
        spawn_object_relative_with_scale(windSpread, relPosX, relPosY, relPosZ, scale, o, MODEL_NONE, bhvStrongWindParticle);
    } else {
        spawn_object_relative_with_scale(windSpread, relPosX, relPosY, relPosZ, scale, o, MODEL_MIST, bhvStrongWindParticle);
    }
    // There is also no need to spawn additional invisible wind particles here.
    // If the devs were worried about object overload when making small particles unimportant, why spawn these?
    // It isn't to ensure collision, as even 1 particle every 2 frames is enough to ensure this reliably.
    spawn_object_relative_with_scale(windSpread, relPosX, relPosY, relPosZ, scale, o, MODEL_NONE, bhvStrongWindParticle);
}
