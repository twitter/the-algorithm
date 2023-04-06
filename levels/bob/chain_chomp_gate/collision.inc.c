// 0x070113C0 - 0x070113F0
const Collision bob_seg7_collision_chain_chomp_gate[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x4),
    COL_VERTEX(512, 0, 0),
    COL_VERTEX(512, 640, 0),
    COL_VERTEX(-511, 640, 0),
    COL_VERTEX(-511, 0, 0),
    COL_TRI_INIT(SURFACE_DEFAULT, 2),
    COL_TRI(0, 1, 2),
    COL_TRI(3, 0, 2),
    COL_TRI_STOP(),
    COL_END(),
};
