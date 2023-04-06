// This file only has a collision entry and no model. It may have been some sort of
// collision hitbox for an object that didn't have a model.

// TODO: this appears to be a lid for an unused box
// 0x0700D1DC - 0x0700D20C
const Collision jrb_seg7_collision_0700D1DC[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x4),
    COL_VERTEX(123, 0, -122),
    COL_VERTEX(-122, 0, -122),
    COL_VERTEX(-122, 0, 123),
    COL_VERTEX(123, 0, 123),
    COL_TRI_INIT(SURFACE_DEFAULT, 2),
    COL_TRI(0, 1, 2),
    COL_TRI(0, 2, 3),
    COL_TRI_STOP(),
    COL_END(),
};
