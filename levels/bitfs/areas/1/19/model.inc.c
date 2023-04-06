// 0x0700ECF0 - 0x0700ED50
static const Vtx bitfs_seg7_vertex_0700ECF0[] = {
    {{{     0,      0,   -306}, 0, {   334,    528}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  -278,      0,    283}, 0, {   362,    620}, {0xff, 0xff, 0x00, 0xff}}},
    {{{    -7,      0,    135}, 0, {   336,    530}, {0xff, 0xff, 0x00, 0xff}}},
    {{{   262,      0,    283}, 0, {   310,    440}, {0xff, 0xff, 0x00, 0xff}}},
    {{{   326,      0,   -118}, 0, {   304,    420}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  -330,      0,   -118}, 0, {   366,    638}, {0xff, 0xff, 0x00, 0xff}}},
};

// 0x0700ED50 - 0x0700ED90
static const Gfx bitfs_seg7_dl_0700ED50[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_0700ECF0, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP1Triangle( 2,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700ED90 - 0x0700EE10
const Gfx bitfs_seg7_dl_0700ED90[] = {
    gsDPPipeSync(),
    gsDPSetEnvColor(255, 255, 255, 100),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitfs_seg7_dl_0700ED50),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};
