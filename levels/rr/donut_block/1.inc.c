// 0x0701B530 - 0x0701B630
static const Vtx rr_seg7_vertex_0701B530[] = {
    {{{   102,     51,   -306}, 0, {   224,   1244}, {0x72, 0x5d, 0x29, 0xff}}},
    {{{    51,      0,    307}, 0, {   122,   1192}, {0x72, 0x5d, 0x29, 0xff}}},
    {{{    51,      0,   -306}, 0, {   122,   1192}, {0x72, 0x5d, 0x29, 0xff}}},
    {{{   102,     51,    307}, 0, {   224,   1244}, {0x72, 0x5d, 0x29, 0xff}}},
    {{{   -50,      0,   -306}, 0, {    70,   1040}, {0x72, 0x5d, 0x29, 0xff}}},
    {{{  -101,     51,    307}, 0, {   122,    938}, {0x72, 0x5d, 0x29, 0xff}}},
    {{{  -101,     51,   -306}, 0, {   122,    938}, {0x72, 0x5d, 0x29, 0xff}}},
    {{{   -50,      0,    307}, 0, {    70,   1040}, {0x72, 0x5d, 0x29, 0xff}}},
    {{{    51,      0,   -306}, 0, {   122,   1192}, {0x66, 0x50, 0x21, 0xff}}},
    {{{   -50,      0,    307}, 0, {    70,   1040}, {0x66, 0x50, 0x21, 0xff}}},
    {{{   -50,      0,   -306}, 0, {    70,   1040}, {0x66, 0x50, 0x21, 0xff}}},
    {{{    51,      0,    307}, 0, {   122,   1192}, {0x66, 0x50, 0x21, 0xff}}},
    {{{   102,    154,   -306}, 0, {   376,   1192}, {0x9b, 0x7c, 0x3d, 0xff}}},
    {{{   102,    154,    307}, 0, {   376,   1192}, {0x9b, 0x7c, 0x3d, 0xff}}},
    {{{   102,     51,    307}, 0, {   224,   1244}, {0x9b, 0x7c, 0x3d, 0xff}}},
    {{{   102,     51,   -306}, 0, {   224,   1244}, {0x9b, 0x7c, 0x3d, 0xff}}},
};

// 0x0701B630 - 0x0701B710
static const Vtx rr_seg7_vertex_0701B630[] = {
    {{{  -101,     51,   -306}, 0, {   122,    938}, {0x9b, 0x7c, 0x3d, 0xff}}},
    {{{  -101,    154,    307}, 0, {   274,    888}, {0x9b, 0x7c, 0x3d, 0xff}}},
    {{{  -101,    154,   -306}, 0, {   274,    888}, {0x9b, 0x7c, 0x3d, 0xff}}},
    {{{  -101,     51,    307}, 0, {   122,    938}, {0x9b, 0x7c, 0x3d, 0xff}}},
    {{{  -101,    154,   -306}, 0, {   274,    888}, {0xcd, 0xc4, 0x97, 0xff}}},
    {{{  -101,    154,    307}, 0, {   274,    888}, {0xcd, 0xc4, 0x97, 0xff}}},
    {{{   102,    154,    307}, 0, {   376,   1192}, {0xcd, 0xc4, 0x97, 0xff}}},
    {{{   102,    154,   -306}, 0, {   376,   1192}, {0xcd, 0xc4, 0x97, 0xff}}},
    {{{   102,    154,    307}, 0, {   376,   1192}, {0xac, 0x9d, 0x52, 0xff}}},
    {{{  -101,    154,    307}, 0, {   274,    888}, {0xac, 0x9d, 0x52, 0xff}}},
    {{{  -101,     51,    307}, 0, {   122,    938}, {0xac, 0x9d, 0x52, 0xff}}},
    {{{    51,      0,    307}, 0, {   122,   1192}, {0xac, 0x9d, 0x52, 0xff}}},
    {{{   102,     51,    307}, 0, {   224,   1244}, {0xac, 0x9d, 0x52, 0xff}}},
    {{{   -50,      0,    307}, 0, {    70,   1040}, {0xac, 0x9d, 0x52, 0xff}}},
};

// 0x0701B710 - 0x0701B770
static const Vtx rr_seg7_vertex_0701B710[] = {
    {{{  -101,     51,   -306}, 0, {   122,    938}, {0xac, 0x9d, 0x52, 0xff}}},
    {{{    51,      0,   -306}, 0, {   122,   1192}, {0xac, 0x9d, 0x52, 0xff}}},
    {{{   -50,      0,   -306}, 0, {    70,   1040}, {0xac, 0x9d, 0x52, 0xff}}},
    {{{   102,    154,   -306}, 0, {   376,   1192}, {0xac, 0x9d, 0x52, 0xff}}},
    {{{   102,     51,   -306}, 0, {   224,   1244}, {0xac, 0x9d, 0x52, 0xff}}},
    {{{  -101,    154,   -306}, 0, {   274,    888}, {0xac, 0x9d, 0x52, 0xff}}},
};

// 0x0701B770 - 0x0701B848
static const Gfx rr_seg7_dl_0701B770[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(rr_seg7_vertex_0701B530, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(rr_seg7_vertex_0701B630, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11, 12, 0x0),
    gsSP2Triangles( 8, 13, 11, 0x0,  8, 10, 13, 0x0),
    gsSPVertex(rr_seg7_vertex_0701B710, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  0,  5,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701B848 - 0x0701B8B8
const Gfx rr_seg7_dl_0701B848[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(rr_seg7_dl_0701B770),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
