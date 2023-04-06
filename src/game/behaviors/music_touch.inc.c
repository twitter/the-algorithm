// music_touch.c.inc

void bhv_play_music_track_when_touched_loop(void) {
    if (o->oAction == 0) {
        if (o->oDistanceToMario < 200.0f) {
            play_puzzle_jingle();
            o->oAction++;
        }
    }
}
