// Test Platform (small tiny platform 1/4th the size of a box. Probably used in early modeling tests?)

// 0x080262F8 - 0x0802637C
const Collision unknown_seg8_collision_080262F8[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x8),
    COL_VERTEX(200, 0, -200),
    COL_VERTEX(200, -100, -200),
    COL_VERTEX(200, -100, 200),
    COL_VERTEX(200, 0, 200),
    COL_VERTEX(-200, -100, -200),
    COL_VERTEX(-200, -100, 200),
    COL_VERTEX(-200, 0, -200),
    COL_VERTEX(-200, 0, 200),

    COL_TRI_INIT(SURFACE_DEFAULT, 12),
    COL_TRI(2, 1, 0),
    COL_TRI(3, 2, 0),
    COL_TRI(1, 2, 4),
    COL_TRI(2, 5, 4),
    COL_TRI(0, 1, 4),
    COL_TRI(6, 0, 4),
    COL_TRI(4, 5, 6),
    COL_TRI(5, 7, 6),
    COL_TRI(3, 0, 6),
    COL_TRI(7, 3, 6),
    COL_TRI(2, 3, 5),
    COL_TRI(3, 7, 5),
    COL_TRI_STOP(),
    COL_END(),
};
