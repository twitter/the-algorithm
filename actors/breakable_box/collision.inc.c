// Breakable Box

// 0x08012D70 - 0x08012DF4
const Collision breakable_box_seg8_collision_08012D70[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x8),
    COL_VERTEX(-100, 0, -100),
    COL_VERTEX(-100, 0, 100),
    COL_VERTEX(-100, 200, 100),
    COL_VERTEX(100, 0, 100),
    COL_VERTEX(100, 200, 100),
    COL_VERTEX(100, 0, -100),
    COL_VERTEX(100, 200, -100),
    COL_VERTEX(-100, 200, -100),

    COL_TRI_INIT(SURFACE_NO_CAM_COLLISION, 12),
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

UNUSED static const u32 breakable_box_unused_1 = 0;
UNUSED static const u64 breakable_box_unused_2 = 0;
