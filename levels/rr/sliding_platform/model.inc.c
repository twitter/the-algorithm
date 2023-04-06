// 0x0701ABE8 - 0x0701ACE8
static const Vtx rr_seg7_vertex_0701ABE8[] = {
    {{{  -409,    102,    307}, 0, {     0,    990}, {0xec, 0xef, 0x8e, 0xff}}},
    {{{   410,    102,   -306}, 0, {  3544,    480}, {0xec, 0xef, 0x8e, 0xff}}},
    {{{  -409,    102,   -306}, 0, {  1500,   2520}, {0xec, 0xef, 0x8e, 0xff}}},
    {{{   410,    102,    307}, 0, {  2012,  -1054}, {0xec, 0xef, 0x8e, 0xff}}},
    {{{  -409,      0,    307}, 0, {  1478,   -520}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{  -409,    102,    307}, 0, {  1732,   -264}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{  -409,    102,   -306}, 0, {   200,   1268}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{  -409,      0,   -306}, 0, {   -54,   1012}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{   410,      0,   -306}, 0, {   -54,   1012}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{   410,    102,    307}, 0, {  1732,   -264}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{   410,      0,    307}, 0, {  1478,   -520}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{   410,    102,   -306}, 0, {   200,   1268}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{   410,      0,    307}, 0, {  2012,   3032}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -409,    102,    307}, 0, {   224,    734}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -409,      0,    307}, 0, {     0,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   410,    102,    307}, 0, {  2268,   2776}, {0xff, 0xd4, 0x00, 0xff}}},
};

// 0x0701ACE8 - 0x0701AD68
static const Vtx rr_seg7_vertex_0701ACE8[] = {
    {{{  -409,      0,   -306}, 0, {     0,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   410,    102,   -306}, 0, {  2268,   2776}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   410,      0,   -306}, 0, {  2012,   3032}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -409,    102,   -306}, 0, {   224,    734}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -409,      0,   -306}, 0, {   -52,   4032}, {0x78, 0x78, 0x00, 0xff}}},
    {{{   410,      0,    307}, 0, {   456,    456}, {0x78, 0x78, 0x00, 0xff}}},
    {{{  -409,      0,    307}, 0, { -1586,   2498}, {0x78, 0x78, 0x00, 0xff}}},
    {{{   410,      0,   -306}, 0, {  1990,   1988}, {0x78, 0x78, 0x00, 0xff}}},
};

// 0x0701AD68 - 0x0701ADF8
static const Gfx rr_seg7_dl_0701AD68[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(rr_seg7_vertex_0701ABE8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15, 13, 0x0),
    gsSPVertex(rr_seg7_vertex_0701ACE8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701ADF8 - 0x0701AE68
const Gfx rr_seg7_dl_0701ADF8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(rr_seg7_dl_0701AD68),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
