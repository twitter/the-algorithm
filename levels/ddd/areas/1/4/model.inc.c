// 0x07005CB0 - 0x07005D90
static const Vtx ddd_seg7_vertex_07005CB0[] = {
    {{{  7322,  -2746,   -255}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  7322,  -2746,    256}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  7680,  -2746,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  7014,  -2746,   -153}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  7322,  -2746,     51}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  7322,  -2746,    -50}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  7014,  -2746,    154}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  3123,  -2149,   -255}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  3123,  -2149,    256}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  3123,  -1791,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  3123,  -2457,   -153}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  3123,  -2149,     51}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  3123,  -2149,    -50}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  3123,  -2457,    154}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
};

// 0x07005D90 - 0x07005E70
static const Vtx ddd_seg7_vertex_07005D90[] = {
    {{{  5478,  -2746,   -153}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  5478,  -2746,    154}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  5786,  -2746,     51}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  5786,  -2746,    -50}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  5786,  -2746,   -255}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  5786,  -2746,    256}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  6144,  -2746,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  5988,    244,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  5734,    497,    256}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  5734,    497,   -255}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  5734,    497,    -50}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  5517,    715,    154}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  5517,    715,   -153}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  5734,    497,     51}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
};

// 0x07005E70 - 0x07005EE0
static const Vtx ddd_seg7_vertex_07005E70[] = {
    {{{  3123,  -4505,   -153}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  3123,  -4505,    154}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  3123,  -4197,     51}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  3123,  -4197,   -255}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  3123,  -4197,    256}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  3123,  -3839,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  3123,  -4197,    -50}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
};

// 0x07005EE0 - 0x07005F78
static const Gfx ddd_seg7_dl_07005EE0[] = {
    gsSPVertex(ddd_seg7_vertex_07005CB0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSPVertex(ddd_seg7_vertex_07005D90, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSPVertex(ddd_seg7_vertex_07005E70, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 0,  2,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x07005F78 - 0x07005FC8
const Gfx ddd_seg7_dl_07005F78[] = {
    gsDPPipeSync(),
    gsDPSetEnvColor(255, 255, 255, 98),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(ddd_seg7_dl_07005EE0),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};
