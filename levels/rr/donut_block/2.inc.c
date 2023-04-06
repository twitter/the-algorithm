// 0x0701B8B8 - 0x0701B938
static const Vtx rr_seg7_vertex_0701B8B8[] = {
    {{{    61,    143,    317}, 0, {   990,      0}, {0xc7, 0xa2, 0x68, 0xff}}},
    {{{   -60,     20,    317}, 0, {     0,    990}, {0xc7, 0xa2, 0x68, 0xff}}},
    {{{    61,     20,    317}, 0, {   990,    990}, {0xc7, 0xa2, 0x68, 0xff}}},
    {{{   -60,    143,    317}, 0, {     0,      0}, {0xc7, 0xa2, 0x68, 0xff}}},
    {{{    61,     20,   -316}, 0, {   990,    990}, {0xc7, 0xa2, 0x68, 0xff}}},
    {{{   -60,    143,   -316}, 0, {     0,      0}, {0xc7, 0xa2, 0x68, 0xff}}},
    {{{    61,    143,   -316}, 0, {   990,      0}, {0xc7, 0xa2, 0x68, 0xff}}},
    {{{   -60,     20,   -316}, 0, {     0,    990}, {0xc7, 0xa2, 0x68, 0xff}}},
};

// 0x0701B938 - 0x0701B980
static const Gfx rr_seg7_dl_0701B938[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_metal_hole),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(rr_seg7_vertex_0701B8B8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701B980 - 0x0701B9F0
const Gfx rr_seg7_dl_0701B980[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(rr_seg7_dl_0701B938),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
