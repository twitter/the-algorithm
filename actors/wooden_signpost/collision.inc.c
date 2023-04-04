// Wooden Signpost

// 0x0302DD80 - 0x0302DE04
const Collision wooden_signpost_seg3_collision_0302DD80[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x8),
    COL_VERTEX(-44, -9, -12),
    COL_VERTEX(-44, 126, 20),
    COL_VERTEX(-44, 126, -12),
    COL_VERTEX(45, 126, 20),
    COL_VERTEX(45, 126, -12),
    COL_VERTEX(45, -9, -12),
    COL_VERTEX(-44, -9, 20),
    COL_VERTEX(45, -9, 20),

    COL_TRI_INIT(SURFACE_DEFAULT, 12),
    COL_TRI(0, 1, 2),
    COL_TRI(2, 1, 3),
    COL_TRI(2, 3, 4),
    COL_TRI(5, 0, 2),
    COL_TRI(5, 2, 4),
    COL_TRI(6, 3, 1),
    COL_TRI(0, 6, 1),
    COL_TRI(7, 4, 3),
    COL_TRI(6, 7, 3),
    COL_TRI(7, 5, 4),
    COL_TRI(5, 7, 6),
    COL_TRI(5, 6, 0),
    COL_TRI_STOP(),
    COL_END(),
};
