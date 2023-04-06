// 0x07009078 - 0x07009168
static const Vtx bitdw_seg7_vertex_07009078[] = {
    {{{    39,    178,    -60}, 0, {   794,      0}, {0xe4, 0xff, 0xff, 0xff}}},
    {{{   -37,    178,    -60}, 0, {   204,      0}, {0xe4, 0xff, 0xff, 0xff}}},
    {{{     0,    256,      0}, 0, {   494,    476}, {0xe4, 0xff, 0xff, 0xff}}},
    {{{   -37,    178,     62}, 0, {   204,    990}, {0xe4, 0xff, 0xff, 0xff}}},
    {{{    39,    178,     62}, 0, {   794,    990}, {0xe4, 0xff, 0xff, 0xff}}},
    {{{     0,    256,      0}, 0, {   494,    476}, {0xc7, 0xee, 0xff, 0xff}}},
    {{{   -66,    178,      0}, 0, {   -24,    476}, {0xc7, 0xee, 0xff, 0xff}}},
    {{{   -37,    178,     62}, 0, {   204,    990}, {0xc7, 0xee, 0xff, 0xff}}},
    {{{    67,    178,      0}, 0, {  1010,    476}, {0xc7, 0xee, 0xff, 0xff}}},
    {{{    39,    178,    -60}, 0, {   794,      0}, {0xc7, 0xee, 0xff, 0xff}}},
    {{{   -37,    178,    -60}, 0, {   204,      0}, {0xa0, 0xd8, 0xff, 0xff}}},
    {{{   -66,    178,      0}, 0, {   -24,    476}, {0xa0, 0xd8, 0xff, 0xff}}},
    {{{     0,    256,      0}, 0, {   494,    476}, {0xa0, 0xd8, 0xff, 0xff}}},
    {{{    39,    178,     62}, 0, {   794,    990}, {0xa0, 0xd8, 0xff, 0xff}}},
    {{{    67,    178,      0}, 0, {  1010,    476}, {0xa0, 0xd8, 0xff, 0xff}}},
};

// 0x07009168 - 0x07009268
static const Vtx bitdw_seg7_vertex_07009168[] = {
    {{{   -37,    178,     62}, 0, {   204,    990}, {0x82, 0xc2, 0xff, 0xff}}},
    {{{   -17,     25,     37}, 0, {   356,    786}, {0x82, 0xc2, 0xff, 0xff}}},
    {{{    20,     25,     37}, 0, {   648,    786}, {0x82, 0xc2, 0xff, 0xff}}},
    {{{    39,    178,     62}, 0, {   794,    990}, {0x82, 0xc2, 0xff, 0xff}}},
    {{{    39,    178,    -60}, 0, {   794,      0}, {0x82, 0xc2, 0xff, 0xff}}},
    {{{    20,     25,    -36}, 0, {   648,    172}, {0x82, 0xc2, 0xff, 0xff}}},
    {{{   -17,     25,    -36}, 0, {   356,    172}, {0x82, 0xc2, 0xff, 0xff}}},
    {{{   -37,    178,    -60}, 0, {   204,      0}, {0x82, 0xc2, 0xff, 0xff}}},
    {{{    39,    178,     62}, 0, {   794,    990}, {0x69, 0xb4, 0xff, 0xff}}},
    {{{    43,     25,      0}, 0, {   824,    474}, {0x69, 0xb4, 0xff, 0xff}}},
    {{{    67,    178,      0}, 0, {  1010,    476}, {0x69, 0xb4, 0xff, 0xff}}},
    {{{    20,     25,     37}, 0, {   648,    786}, {0x69, 0xb4, 0xff, 0xff}}},
    {{{   -37,    178,    -60}, 0, {   204,      0}, {0x69, 0xb4, 0xff, 0xff}}},
    {{{   -40,     25,      0}, 0, {   180,    474}, {0x69, 0xb4, 0xff, 0xff}}},
    {{{   -66,    178,      0}, 0, {   -24,    476}, {0x69, 0xb4, 0xff, 0xff}}},
    {{{   -17,     25,    -36}, 0, {   356,    172}, {0x69, 0xb4, 0xff, 0xff}}},
};

// 0x07009268 - 0x070092E8
static const Vtx bitdw_seg7_vertex_07009268[] = {
    {{{    43,     25,      0}, 0, {   824,    474}, {0x5f, 0x9c, 0xff, 0xff}}},
    {{{    20,     25,    -36}, 0, {   648,    172}, {0x5f, 0x9c, 0xff, 0xff}}},
    {{{    39,    178,    -60}, 0, {   794,      0}, {0x5f, 0x9c, 0xff, 0xff}}},
    {{{    67,    178,      0}, 0, {  1010,    476}, {0x5f, 0x9c, 0xff, 0xff}}},
    {{{   -40,     25,      0}, 0, {   180,    474}, {0x5f, 0x9c, 0xff, 0xff}}},
    {{{   -17,     25,     37}, 0, {   356,    786}, {0x5f, 0x9c, 0xff, 0xff}}},
    {{{   -37,    178,     62}, 0, {   204,    990}, {0x5f, 0x9c, 0xff, 0xff}}},
    {{{   -66,    178,      0}, 0, {   -24,    476}, {0x5f, 0x9c, 0xff, 0xff}}},
};

// 0x070092E8 - 0x070093B0
static const Gfx bitdw_seg7_dl_070092E8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bitdw_seg7_texture_07001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitdw_seg7_vertex_07009078, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  5,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 13, 14, 0x0),
    gsSPVertex(bitdw_seg7_vertex_07009168, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15, 13, 0x0),
    gsSPVertex(bitdw_seg7_vertex_07009268, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  4,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x070093B0 - 0x07009430
const Gfx bitdw_seg7_dl_070093B0[] = {
    gsDPPipeSync(),
    gsDPSetEnvColor(255, 255, 255, 180),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitdw_seg7_dl_070092E8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};
