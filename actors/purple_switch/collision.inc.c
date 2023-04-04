// Purple Switch

// 0x0800C7A8 - 0x0800C820
const Collision purple_switch_seg8_collision_0800C7A8[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x8),
    COL_VERTEX(61, 36, -60),
    COL_VERTEX(-60, 36, -60),
    COL_VERTEX(-60, 36, 61),
    COL_VERTEX(61, 36, 61),
    COL_VERTEX(-101, 0, -101),
    COL_VERTEX(102, 0, -101),
    COL_VERTEX(-101, 0, 102),
    COL_VERTEX(102, 0, 102),

    COL_TRI_INIT(SURFACE_SWITCH, 10),
    COL_TRI(0, 1, 2),
    COL_TRI(0, 2, 3),
    COL_TRI(4, 1, 0),
    COL_TRI(4, 0, 5),
    COL_TRI(6, 1, 4),
    COL_TRI(6, 2, 1),
    COL_TRI(7, 3, 2),
    COL_TRI(7, 2, 6),
    COL_TRI(5, 0, 3),
    COL_TRI(5, 3, 7),
    COL_TRI_STOP(),
    COL_END(),
};
