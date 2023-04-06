// 0x07009070 - 0x07009088
static const Lights1 wf_seg7_lights_07009070 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07009088 - 0x070090C8
static const Vtx wf_seg7_vertex_07009088[] = {
    {{{  -127,      0,   -127}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   128,    256,   -127}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   128,      0,   -127}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -127,    256,   -127}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x070090C8 - 0x070091C8
static const Vtx wf_seg7_vertex_070090C8[] = {
    {{{  -127,      0,    179}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -127,    256,    179}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -127,    256,   -127}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   128,      0,   -127}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   128,    256,   -127}, 0, {   990,    -30}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   128,    256,    179}, 0, {     0,    -30}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   128,      0,    179}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -127,    256,   -127}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -127,    256,    179}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   128,    256,    179}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   128,    256,   -127}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   128,      0,    179}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -127,    256,    179}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -127,      0,    179}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   128,    256,    179}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -127,      0,   -127}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x070091C8 - 0x07009210
static const Gfx wf_seg7_dl_070091C8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, wf_seg7_texture_07000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_07009070.l, 1),
    gsSPLight(&wf_seg7_lights_07009070.a, 2),
    gsSPVertex(wf_seg7_vertex_07009088, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07009210 - 0x07009278
static const Gfx wf_seg7_dl_07009210[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, wf_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(wf_seg7_vertex_070090C8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0,  2, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x07009278 - 0x070092F0
const Gfx wf_seg7_dl_07009278[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_070091C8),
    gsSPDisplayList(wf_seg7_dl_07009210),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
