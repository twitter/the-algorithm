// 0x0701D408 - 0x0701D450
const Collision lll_seg7_collision_slow_tilting_platform[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x6),
    COL_VERTEX(-383, 0, -1023),
    COL_VERTEX(5, 41, 1024),
    COL_VERTEX(5, 41, -1023),
    COL_VERTEX(-383, 0, 1024),
    COL_VERTEX(384, 0, -1023),
    COL_VERTEX(384, 0, 1024),
    COL_TRI_INIT(SURFACE_DEFAULT, 4),
    COL_TRI(0, 1, 2),
    COL_TRI(0, 3, 1),
    COL_TRI(1, 4, 2),
    COL_TRI(1, 5, 4),
    COL_TRI_STOP(),
    COL_END(),
};
