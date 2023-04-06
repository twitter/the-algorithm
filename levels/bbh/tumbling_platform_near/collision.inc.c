// 0x07026B1C - 0x07026B4C
const Collision bbh_seg7_collision_07026B1C[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x4),
    COL_VERTEX(102, 0, -50),
    COL_VERTEX(-101, 0, -50),
    COL_VERTEX(-101, 0, 51),
    COL_VERTEX(102, 0, 51),
    COL_TRI_INIT(SURFACE_NOISE_DEFAULT, 2),
    COL_TRI(0, 1, 2),
    COL_TRI(0, 2, 3),
    COL_TRI_STOP(),
    COL_END(),
};
