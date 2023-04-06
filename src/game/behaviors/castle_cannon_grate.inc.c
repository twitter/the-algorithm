// castle_cannon_grate.inc.c

void bhv_castle_cannon_grate_init(void) {
    if (save_file_get_total_star_count(gCurrSaveFileNum - 1, 0, 24) >= 120)
        o->activeFlags = 0;
}
