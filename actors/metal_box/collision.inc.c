// Metal Box

// 0x08024C28 - 0x08024CAC
const Collision metal_box_seg8_collision_08024C28[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x8),
    COL_VERTEX(154, 307, -153),
    COL_VERTEX(-153, 307, -153),
    COL_VERTEX(-153, 307, 154),
    COL_VERTEX(154, 307, 154),
    COL_VERTEX(154, 0, 154),
    COL_VERTEX(-153, 0, 154),
    COL_VERTEX(-153, 0, -153),
    COL_VERTEX(154, 0, -153),

    COL_TRI_INIT(SURFACE_DEFAULT, 12),
    COL_TRI(0, 1, 2),
    COL_TRI(0, 2, 3),
    COL_TRI(4, 5, 6),
    COL_TRI(4, 6, 7),
    COL_TRI(6, 1, 0),
    COL_TRI(6, 0, 7),
    COL_TRI(5, 1, 6),
    COL_TRI(5, 2, 1),
    COL_TRI(7, 0, 3),
    COL_TRI(7, 3, 4),
    COL_TRI(4, 2, 5),
    COL_TRI(4, 3, 2),
    COL_TRI_STOP(),
    COL_END(),
};
