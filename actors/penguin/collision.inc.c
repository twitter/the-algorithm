// Penguin

// 0x05008B88 - 0x05008C00
const Collision penguin_seg5_collision_05008B88[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x8),
    COL_VERTEX(12, 66, -12),
    COL_VERTEX(12, 0, -12),
    COL_VERTEX(12, 0, 12),
    COL_VERTEX(12, 66, 12),
    COL_VERTEX(-12, 0, -12),
    COL_VERTEX(-12, 66, -12),
    COL_VERTEX(-12, 0, 12),
    COL_VERTEX(-12, 66, 12),

    COL_TRI_INIT(SURFACE_NO_CAM_COLLISION, 10),
    COL_TRI(2, 1, 0),
    COL_TRI(3, 2, 0),
    COL_TRI(0, 1, 4),
    COL_TRI(5, 0, 4),
    COL_TRI(4, 6, 5),
    COL_TRI(6, 7, 5),
    COL_TRI(3, 0, 5),
    COL_TRI(7, 3, 5),
    COL_TRI(2, 3, 6),
    COL_TRI(3, 7, 6),
    COL_TRI_STOP(),
    COL_END(),
};
