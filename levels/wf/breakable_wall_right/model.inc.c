// 0x0700F088 - 0x0700F0A0
static const Lights1 wf_seg7_lights_0700F088 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700F0A0 - 0x0700F0D0
static const Vtx wf_seg7_vertex_0700F0A0[] = {
    {{{     0,    384,    128}, 0, { 16320,  10186}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   184,    384,   -378}, 0, { 17056,   8162}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    384,   -378}, 0, { 16320,   8162}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0700F0D0 - 0x0700F130
static const Vtx wf_seg7_vertex_0700F0D0[] = {
    {{{     0,   -383,    128}, 0, {   990,   3032}, {0x81, 0x00, 0x00, 0xff}}},
    {{{     0,    384,    128}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{     0,    384,   -378}, 0, { -1032,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{     0,   -383,    128}, 0, {     0,   3032}, {0x77, 0x00, 0x2b, 0xff}}},
    {{{   184,    384,   -378}, 0, {  2122,      0}, {0x77, 0x00, 0x2b, 0xff}}},
    {{{     0,    384,    128}, 0, {     0,      0}, {0x77, 0x00, 0x2b, 0xff}}},
};

// 0x0700F130 - 0x0700F170
static const Gfx wf_seg7_dl_0700F130[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_0700F088.l, 1),
    gsSPLight(&wf_seg7_lights_0700F088.a, 2),
    gsSPVertex(wf_seg7_vertex_0700F0A0, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700F170 - 0x0700F1A8
static const Gfx wf_seg7_dl_0700F170[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(wf_seg7_vertex_0700F0D0, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700F1A8 - 0x0700F220
const Gfx wf_seg7_dl_0700F1A8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_0700F130),
    gsSPDisplayList(wf_seg7_dl_0700F170),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
