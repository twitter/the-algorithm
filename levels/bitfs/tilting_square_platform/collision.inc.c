// 0x07015238 - 0x07015286
const Collision bitfs_seg7_collision_inverted_pyramid[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x5),
    COL_VERTEX(307, 307, -306),
    COL_VERTEX(-306, 307, -306),
    COL_VERTEX(-306, 307, 307),
    COL_VERTEX(307, 307, 307),
    COL_VERTEX(0, 0, 0),
    COL_TRI_INIT(SURFACE_DEFAULT, 6),
    COL_TRI(0, 1, 2),
    COL_TRI(0, 2, 3),
    COL_TRI(2, 1, 4),
    COL_TRI(4, 3, 2),
    COL_TRI(0, 4, 1),
    COL_TRI(4, 0, 3),
    COL_TRI_STOP(),
    COL_END(),
};
