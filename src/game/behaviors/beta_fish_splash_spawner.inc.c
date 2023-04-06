/**
 * Behavior for bhvBetaFishSplashSpawner.
 * This is a now non-functional fish splash object found in WF
 * that can be seen in Shoshinkai 1995 footage. It used to create
 * a fish that would splash up when the player walked through it.
 * This functionality was probably moved; in the final game,
 * an identical fish splash can occur with a 1/256 chance every time
 * Mario splashes in water.
 */

/**
 * Update function for bhvBetaFishSplashSpawner.
 */
void bhv_beta_fish_splash_spawner_loop(void) {
    UNUSED u8 pad[12];
    UNUSED f32 water_level = find_water_level(o->oPosX, o->oPosZ);
}
