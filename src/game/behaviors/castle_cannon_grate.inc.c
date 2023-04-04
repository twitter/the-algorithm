// castle_cannon_grate.inc.c

void bhv_castle_cannon_grate_init(void) {
    if (save_file_get_total_star_count(gCurrSaveFileNum - 1, COURSE_MIN - 1, COURSE_MAX - 1) >= 120) {
        o->activeFlags = ACTIVE_FLAG_DEACTIVATED;
    }
}
