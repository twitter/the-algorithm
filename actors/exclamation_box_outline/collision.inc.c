// Exclamation Box Outline

// 0x08025F78 - 0x08025FFC
const Collision exclamation_box_outline_seg8_collision_08025F78[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x8),
    COL_VERTEX(-26, 30, -26),
    COL_VERTEX(-26, 30, 26),
    COL_VERTEX(-26, 52, 26),
    COL_VERTEX(26, 30, 26),
    COL_VERTEX(26, 52, 26),
    COL_VERTEX(26, 30, -26),
    COL_VERTEX(26, 52, -26),
    COL_VERTEX(-26, 52, -26),

    COL_TRI_INIT(SURFACE_DEFAULT, 12),
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
