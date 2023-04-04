// Warp Collision (seems strange to put these collision in another file. Only instance of this seperation thus far. Not sure what this is doing as a file.)

// 0x0301CE78 - 0x0301CECC
const Collision door_seg3_collision_0301CE78[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x8),
    COL_VERTEX(-80, 240, 30),
    COL_VERTEX(80, 240, 30),
    COL_VERTEX(80, 0, 30),
    COL_VERTEX(-80, 0, 30),
    COL_VERTEX(-80, 240, -30),
    COL_VERTEX(80, 0, -30),
    COL_VERTEX(80, 240, -30),
    COL_VERTEX(-80, 0, -30),

    COL_TRI_INIT(SURFACE_DEFAULT, 4),
    COL_TRI(2, 1, 0),
    COL_TRI(3, 2, 0),
    COL_TRI(6, 5, 4),
    COL_TRI(5, 7, 4),
    COL_TRI_STOP(),
    COL_END(),
};

// 0x0301CECC - 0x0301CEFC
const Collision lll_hexagonal_mesh_seg3_collision_0301CECC[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x4),
    COL_VERTEX(-200, 0, -200),
    COL_VERTEX(200, 0, -200),
    COL_VERTEX(200, 0, 200),
    COL_VERTEX(-200, 0, 200),

    COL_TRI_INIT(SURFACE_DEFAULT, 2),
    COL_TRI(2, 1, 0),
    COL_TRI(3, 2, 0),
    COL_TRI_STOP(),
    COL_END(),
};
