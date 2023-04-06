
/**
 * Behavior for bhvTTCTreadmill.
 * The first treadmill in processing order is the "master treadmill". It is
 * responsible for playing sounds and calling RNG to control all the other
 * treadmills.
 */

/**
 * Collision models for the different sized treadmills.
 */
static void *sTTCTreadmillCollisionModels[] = {
    ttc_seg7_collision_070152B4,
    ttc_seg7_collision_070153E0,
};

static s16 sTTCTreadmillSpeeds[] = {
    /* TTC_SPEED_SLOW    */ 50,
    /* TTC_SPEED_FAST    */ 100,
    /* TTC_SPEED_RANDOM  */ 0,
    /* TTC_SPEED_STOPPED */ 0,
};

extern s16 ttc_movtex_tris_big_surface_treadmill[];
extern s16 ttc_movtex_tris_small_surface_treadmill[];

void bhv_ttc_treadmill_init(void) {
    o->collisionData = segmented_to_virtual(sTTCTreadmillCollisionModels[o->oBehParams2ndByte & 0x1]);

    o->oTTCTreadmillBigSurface = segmented_to_virtual(ttc_movtex_tris_big_surface_treadmill);
    o->oTTCTreadmillSmallSurface = segmented_to_virtual(ttc_movtex_tris_small_surface_treadmill);

    *o->oTTCTreadmillBigSurface = *o->oTTCTreadmillSmallSurface = sTTCTreadmillSpeeds[gTTCSpeedSetting];

    sMasterTreadmill = NULL;
}

/**
 * Update function for bhvTTCTreadmill. It calls cur_obj_compute_vel_xz afterward.
 */
void bhv_ttc_treadmill_update(void) {
    if (sMasterTreadmill == o || sMasterTreadmill == NULL) {
        sMasterTreadmill = o;

        cur_obj_play_sound_1(SOUND_ENV_ELEVATOR2);

        if (gTTCSpeedSetting == TTC_SPEED_RANDOM) {
            // Stay still for 5 frames, then accelerate toward the target speed
            // until it's time to switch
            if (o->oTimer > o->oTTCTreadmillTimeUntilSwitch) {
                // Then stop and select new target speed and time until switch
                if (approach_f32_ptr(&o->oTTCTreadmillSpeed, 0.0f, 10.0f)) {
                    o->oTTCTreadmillTimeUntilSwitch = random_mod_offset(10, 20, 7);
                    o->oTTCTreadmillTargetSpeed = random_sign() * 50.0f;
                    o->oTimer = 0;
                }
            } else if (o->oTimer > 5) {
                approach_f32_ptr(&o->oTTCTreadmillSpeed, o->oTTCTreadmillTargetSpeed, 10.0f);
            }

            *o->oTTCTreadmillBigSurface = *o->oTTCTreadmillSmallSurface = o->oTTCTreadmillSpeed;
        }
    }

    o->oForwardVel = 0.084f * *o->oTTCTreadmillBigSurface;
}
