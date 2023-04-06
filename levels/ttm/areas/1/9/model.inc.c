// 0x0700C0E0 - 0x0700C0F8
static const Lights1 ttm_seg7_lights_0700C0E0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700C0F8 - 0x0700C110
static const Lights1 ttm_seg7_lights_0700C0F8 = gdSPDefLights1(
    0x4c, 0x4c, 0x4c,
    0x99, 0x99, 0x99, 0x28, 0x28, 0x28
);

// 0x0700C110 - 0x0700C128
static const Lights1 ttm_seg7_lights_0700C110 = gdSPDefLights1(
    0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x28, 0x28, 0x28
);

// 0x0700C128 - 0x0700C228
static const Vtx ttm_seg7_vertex_0700C128[] = {
    {{{   162,    256,     35}, 0, {     0,  -2076}, {0xa6, 0x00, 0xa7, 0xff}}},
    {{{    18,   -255,    180}, 0, {   990,    480}, {0xa6, 0x00, 0xa7, 0xff}}},
    {{{    18,    256,    180}, 0, {   990,  -2076}, {0xa6, 0x00, 0xa7, 0xff}}},
    {{{    18,    256,    180}, 0, {     0,  -1566}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{    18,   -255,    180}, 0, {     0,    990}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{  -126,   -255,     35}, 0, {   990,    990}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{  -126,    256,     35}, 0, {   990,  -1566}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{  -126,    256,     35}, 0, {   990,  -2076}, {0x59, 0x00, 0x59, 0xff}}},
    {{{  -126,   -255,     35}, 0, {   990,    480}, {0x59, 0x00, 0x59, 0xff}}},
    {{{    18,   -255,   -109}, 0, {     0,    478}, {0x59, 0x00, 0x59, 0xff}}},
    {{{    18,    256,   -109}, 0, {     0,  -2076}, {0x59, 0x00, 0x59, 0xff}}},
    {{{    18,    256,   -109}, 0, {   990,  -1566}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{   162,   -255,     35}, 0, {     0,    990}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{   162,    256,     35}, 0, {     0,  -1566}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{    18,   -255,   -109}, 0, {   990,    990}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{   162,   -255,     35}, 0, {     0,    478}, {0xa6, 0x00, 0xa7, 0xff}}},
};

// 0x0700C228 - 0x0700C2A8
static const Vtx ttm_seg7_vertex_0700C228[] = {
    {{{   307,    256,     35}, 0, { 14686, -17952}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    18,    256,   -253}, 0, { 14108, -18530}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   162,    256,     35}, 0, { 14396, -17952}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    18,    256,   -109}, 0, { 14108, -18240}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -126,    256,     35}, 0, { 13818, -17952}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -271,    256,     35}, 0, { 13528, -17952}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    18,    256,    325}, 0, { 14108, -17374}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    18,    256,    180}, 0, { 14108, -17662}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0700C2A8 - 0x0700C2E8
static const Vtx ttm_seg7_vertex_0700C2A8[] = {
    {{{    18,   -255,    180}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   162,   -255,     35}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    18,   -255,   -109}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -126,   -255,     35}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0700C2E8 - 0x0700C360
static const Gfx ttm_seg7_dl_0700C2E8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mountain_09000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttm_seg7_lights_0700C0E0.l, 1),
    gsSPLight(&ttm_seg7_lights_0700C0E0.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700C128, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700C360 - 0x0700C3D8
static const Gfx ttm_seg7_dl_0700C360[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mountain_09003800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttm_seg7_lights_0700C0F8.l, 1),
    gsSPLight(&ttm_seg7_lights_0700C0F8.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700C228, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  3,  1, 0x0,  4,  1,  5, 0x0),
    gsSP2Triangles( 0,  2,  6, 0x0,  2,  7,  6, 0x0),
    gsSP2Triangles( 7,  4,  5, 0x0,  7,  5,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700C3D8 - 0x0700C408
static const Gfx ttm_seg7_dl_0700C3D8[] = {
    gsSPLight(&ttm_seg7_lights_0700C110.l, 1),
    gsSPLight(&ttm_seg7_lights_0700C110.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700C2A8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700C408 - 0x0700C488
const Gfx ttm_seg7_dl_0700C408[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttm_seg7_dl_0700C2E8),
    gsSPDisplayList(ttm_seg7_dl_0700C360),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(ttm_seg7_dl_0700C3D8),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
