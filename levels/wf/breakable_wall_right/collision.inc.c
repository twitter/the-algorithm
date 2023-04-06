// 0x0700FC0C - 0x0700FC42
const Collision wf_seg7_collision_breakable_wall[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x4),
    COL_VERTEX(0, 384, 128),
    COL_VERTEX(184, 384, -378),
    COL_VERTEX(0, 384, -378),
    COL_VERTEX(0, -383, 128),
    COL_TRI_INIT(SURFACE_DEFAULT, 3),
    COL_TRI(3, 1, 0),
    COL_TRI(0, 1, 2),
    COL_TRI(3, 0, 2),
    COL_TRI_STOP(),
    COL_END(),
};
