// Checkerboard Platform

// 0x0800D710 - 0x0800D794
const Collision checkerboard_platform_seg8_collision_0800D710[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x8),
    COL_VERTEX(256, -25, -153),
    COL_VERTEX(256, 26, -153),
    COL_VERTEX(256, 26, 154),
    COL_VERTEX(-255, 26, -153),
    COL_VERTEX(-255, 26, 154),
    COL_VERTEX(-255, -25, -153),
    COL_VERTEX(-255, -25, 154),
    COL_VERTEX(256, -25, 154),

    COL_TRI_INIT(SURFACE_WALL_MISC, 12),
    COL_TRI(0, 1, 2),
    COL_TRI(1, 3, 4),
    COL_TRI(1, 4, 2),
    COL_TRI(5, 3, 1),
    COL_TRI(5, 1, 0),
    COL_TRI(6, 4, 3),
    COL_TRI(6, 3, 5),
    COL_TRI(7, 4, 6),
    COL_TRI(7, 2, 4),
    COL_TRI(0, 2, 7),
    COL_TRI(7, 6, 5),
    COL_TRI(7, 5, 0),
    COL_TRI_STOP(),
    COL_END(),
};
