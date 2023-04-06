// 0x0705E310 - 0x0705E328
static const Lights1 inside_castle_seg7_lights_0705E310 = gdSPDefLights1(
    0x00, 0x5f, 0x5f,
    0x00, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0705E328 - 0x0705E3E8
static const Vtx inside_castle_seg7_vertex_0705E328[] = {
    {{{  -101,   -429,   1075}, 0, {   990,   -102}, {0x00, 0x00, 0x81, 0x60}}},
    {{{  -562,   -858,   1075}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0x60}}},
    {{{  -562,   -429,   1075}, 0, {     0,   -102}, {0x00, 0x00, 0x81, 0x60}}},
    {{{  -101,   -858,   1075}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0x60}}},
    {{{  -562,   -858,   1075}, 0, {     0,    990}, {0x00, 0x81, 0x00, 0x60}}},
    {{{  -101,   -858,   1075}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0x60}}},
    {{{  -101,   -858,   1178}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0x60}}},
    {{{  -562,   -858,   1178}, 0, {     0,    990}, {0x00, 0x81, 0x00, 0x60}}},
    {{{  -562,   -460,   1178}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0x60}}},
    {{{  -101,   -858,   1178}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0x60}}},
    {{{  -101,   -460,   1178}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0x60}}},
    {{{  -562,   -858,   1178}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0x60}}},
};

// 0x0705E3E8 - 0x0705E450
static const Gfx inside_castle_seg7_dl_0705E3E8[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, texture_castle_light),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&inside_castle_seg7_lights_0705E310.l, 1),
    gsSPLight(&inside_castle_seg7_lights_0705E310.a, 2),
    gsSPVertex(inside_castle_seg7_vertex_0705E328, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0705E450 - 0x0705E4C0
const Gfx inside_castle_seg7_dl_0705E450[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_0705E3E8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
