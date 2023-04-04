// 0x0700FD00 - 0x0700FD30
const Collision wf_seg7_collision_tower_door[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x4),
    COL_VERTEX(0, 0, -107),
    COL_VERTEX(0, 0, 108),
    COL_VERTEX(0, 205, 108),
    COL_VERTEX(0, 205, -107),
    COL_TRI_INIT(SURFACE_DEFAULT, 2),
    COL_TRI(0, 1, 2),
    COL_TRI(0, 2, 3),
    COL_TRI_STOP(),
    COL_END(),
};
