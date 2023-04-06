// 0x0700FAEC - 0x0700FB1C
const Collision wf_seg7_collision_tumbling_bridge[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x4),
    COL_VERTEX(-127, 64, -63),
    COL_VERTEX(-127, 64, 64),
    COL_VERTEX(215, 64, 64),
    COL_VERTEX(215, 64, -63),
    COL_TRI_INIT(SURFACE_DEFAULT, 2),
    COL_TRI(0, 1, 2),
    COL_TRI(0, 2, 3),
    COL_TRI_STOP(),
    COL_END(),
};
