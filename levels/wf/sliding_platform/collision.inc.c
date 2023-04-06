// 0x0700FAA4 - 0x0700FAEC
const Collision wf_seg7_collision_sliding_brick_platform[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x6),
    COL_VERTEX(256, 0, 256),
    COL_VERTEX(256, -50, -255),
    COL_VERTEX(256, 0, -255),
    COL_VERTEX(256, -50, 256),
    COL_VERTEX(-255, 0, -255),
    COL_VERTEX(-255, 0, 256),
    COL_TRI_INIT(SURFACE_DEFAULT, 4),
    COL_TRI(0, 1, 2),
    COL_TRI(0, 3, 1),
    COL_TRI(0, 4, 5),
    COL_TRI(0, 2, 4),
    COL_TRI_STOP(),
    COL_END(),
};
