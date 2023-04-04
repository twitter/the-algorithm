// 0x0700EFC0 - 0x0700EFFC
const Collision sl_seg7_collision_pound_explodes[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x5),
    COL_VERTEX(-101, 0, 102),
    COL_VERTEX(102, 0, 102),
    COL_VERTEX(0, 0, 0),
    COL_VERTEX(-101, 0, -101),
    COL_VERTEX(102, 0, -101),
    COL_TRI_INIT(SURFACE_ICE, 3),
    COL_TRI(4, 3, 1),
    COL_TRI(0, 1, 2),
    COL_TRI(0, 2, 3),
    COL_TRI_STOP(),
    COL_END(),
};
