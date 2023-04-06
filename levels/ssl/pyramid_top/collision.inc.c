// 0x070125F4 - 0x07012642
const Collision ssl_seg7_collision_pyramid_top[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x5),
    COL_VERTEX(-511, -255, 512),
    COL_VERTEX(512, -255, -511),
    COL_VERTEX(512, -255, 512),
    COL_VERTEX(0, 256, 0),
    COL_VERTEX(-511, -255, -511),
    COL_TRI_INIT(SURFACE_HARD_SLIPPERY, 6),
    COL_TRI(0, 1, 2),
    COL_TRI(0, 2, 3),
    COL_TRI(2, 1, 3),
    COL_TRI(0, 4, 1),
    COL_TRI(1, 4, 3),
    COL_TRI(4, 0, 3),
    COL_TRI_STOP(),
    COL_END(),
};
