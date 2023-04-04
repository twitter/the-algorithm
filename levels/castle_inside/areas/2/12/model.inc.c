// 0x07051A38 - 0x07051A50
static const Lights1 inside_castle_seg7_lights_07051A38 = gdSPDefLights1(
    0x47, 0x3f, 0x17,
    0xbf, 0xaa, 0x3f, 0x28, 0x28, 0x28
);

// 0x07051A50 - 0x07051B10
static const Vtx inside_castle_seg7_vertex_07051A50[] = {
    {{{     0,   2611,   7130}, 0, {     0,      0}, {0x72, 0x00, 0xc9, 0x80}}},
    {{{     0,   2253,   7130}, 0, {     0,      0}, {0x72, 0x00, 0xc9, 0x80}}},
    {{{   -50,   2253,   7027}, 0, {     0,      0}, {0x72, 0x00, 0xc9, 0x80}}},
    {{{   -50,   2611,   7027}, 0, {     0,      0}, {0x72, 0x00, 0xc9, 0x80}}},
    {{{   -50,   2611,   7027}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0x80}}},
    {{{   -50,   2253,   7027}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0x80}}},
    {{{  -357,   2253,   7027}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0x80}}},
    {{{  -357,   2611,   7027}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0x80}}},
    {{{  -357,   2611,   7027}, 0, {     0,      0}, {0x8f, 0x00, 0xc7, 0x80}}},
    {{{  -357,   2253,   7027}, 0, {     0,      0}, {0x8f, 0x00, 0xc7, 0x80}}},
    {{{  -409,   2253,   7130}, 0, {     0,      0}, {0x8f, 0x00, 0xc7, 0x80}}},
    {{{  -409,   2611,   7130}, 0, {     0,      0}, {0x8f, 0x00, 0xc7, 0x80}}},
};

// 0x07051B10 - 0x07051B60
static const Gfx inside_castle_seg7_dl_07051B10[] = {
    gsSPLight(&inside_castle_seg7_lights_07051A38.l, 1),
    gsSPLight(&inside_castle_seg7_lights_07051A38.a, 2),
    gsSPVertex(inside_castle_seg7_vertex_07051A50, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x07051B60 - 0x07051B88
const Gfx inside_castle_seg7_dl_07051B60[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPDisplayList(inside_castle_seg7_dl_07051B10),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
