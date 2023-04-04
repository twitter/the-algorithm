// sound_ambient.inc.c

void bhv_ambient_sounds_init(void) {
    if (gCamera->mode == CAMERA_MODE_BEHIND_MARIO) {
        return;
    }

    play_sound(SOUND_AIR_CASTLE_OUTDOORS_AMBIENT, gGlobalSoundSource);
}
