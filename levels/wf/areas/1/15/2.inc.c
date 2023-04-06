// 0x0700AC70 - 0x0700AC88
static const Lights1 wf_seg7_lights_0700AC70 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700AC88 - 0x0700ACE8
static const Vtx wf_seg7_vertex_0700AC88[] = {
    {{{  1536,    102,   -767}, 0, {   990,   5076}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1023,    102,    768}, 0, { -2074,   1244}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1024,    102,    768}, 0, { -2074,   5332}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1535,    102,    256}, 0, { -1052,    224}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1536,    102,    256}, 0, { -1052,   6354}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1535,    102,   -767}, 0, {   990,    224}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0700ACE8 - 0x0700ADE8
static const Vtx wf_seg7_vertex_0700ACE8[] = {
    {{{  -695,    133,    292}, 0, { -2074,  -2076}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -419,    133,    568}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -327,    133,    476}, 0, {  2012,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -327,    133,    292}, 0, {  2012,  -2076}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -419,    133,    200}, 0, {   990,  -3098}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -603,    133,    200}, 0, { -1052,  -3098}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -603,    133,    568}, 0, { -1052,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -695,    133,    476}, 0, { -2074,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   768,    154,      0}, 0, {  3034,  -3098}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   896,    154,    384}, 0, {     0,  -2076}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1152,    154,    384}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   768,    154,    256}, 0, {   990,  -3098}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1152,    154,   -127}, 0, {  4056,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   896,    154,   -127}, 0, {  4056,  -2076}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1280,    154,      0}, 0, {  3034,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1280,    154,    256}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0700ADE8 - 0x0700AE40
static const Gfx wf_seg7_dl_0700ADE8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09005800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_0700AC70.l, 1),
    gsSPLight(&wf_seg7_lights_0700AC70.a, 2),
    gsSPVertex(wf_seg7_vertex_0700AC88, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  0,  5,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700AE40 - 0x0700AEC8
static const Gfx wf_seg7_dl_0700AE40[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09002000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(wf_seg7_vertex_0700ACE8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  4,  5, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 0,  6,  1, 0x0,  0,  7,  6, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles( 8, 12, 13, 0x0,  8, 14, 12, 0x0),
    gsSP2Triangles( 8, 10, 15, 0x0,  8, 15, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700AEC8 - 0x0700AF40
const Gfx wf_seg7_dl_0700AEC8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_0700ADE8),
    gsSPDisplayList(wf_seg7_dl_0700AE40),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
