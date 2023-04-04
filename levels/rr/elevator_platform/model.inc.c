// 0x0701B010 - 0x0701B110
static const Vtx rr_seg7_vertex_0701B010[] = {
    {{{   205,    102,   -306}, 0, {  2522,   1498}, {0xad, 0xbb, 0xd1, 0xff}}},
    {{{  -204,    102,    307}, 0, {     0,    990}, {0xad, 0xbb, 0xd1, 0xff}}},
    {{{   205,    102,    307}, 0, {   990,      0}, {0xad, 0xbb, 0xd1, 0xff}}},
    {{{  -204,    102,   -306}, 0, {  1500,   2520}, {0xad, 0xbb, 0xd1, 0xff}}},
    {{{  -204,    102,   -306}, 0, {    36,   1124}, {0x59, 0x7a, 0xb3, 0xff}}},
    {{{  -204,      0,   -306}, 0, {     0,    990}, {0x59, 0x7a, 0xb3, 0xff}}},
    {{{  -204,      0,    307}, 0, {   786,    582}, {0x59, 0x7a, 0xb3, 0xff}}},
    {{{  -204,    102,    307}, 0, {   854,    718}, {0x59, 0x7a, 0xb3, 0xff}}},
    {{{   205,    102,    307}, 0, {   854,    718}, {0x59, 0x7a, 0xb3, 0xff}}},
    {{{   205,      0,    307}, 0, {   786,    582}, {0x59, 0x7a, 0xb3, 0xff}}},
    {{{   205,      0,   -306}, 0, {     0,    990}, {0x59, 0x7a, 0xb3, 0xff}}},
    {{{   205,    102,   -306}, 0, {    36,   1124}, {0x59, 0x7a, 0xb3, 0xff}}},
    {{{   205,      0,    307}, 0, {   990,      0}, {0x38, 0x5e, 0x96, 0xff}}},
    {{{  -204,      0,    307}, 0, {     0,    990}, {0x38, 0x5e, 0x96, 0xff}}},
    {{{  -204,      0,   -306}, 0, {  1500,   2520}, {0x38, 0x5e, 0x96, 0xff}}},
    {{{   205,      0,   -306}, 0, {  2522,   1498}, {0x38, 0x5e, 0x96, 0xff}}},
};

// 0x0701B110 - 0x0701B190
static const Vtx rr_seg7_vertex_0701B110[] = {
    {{{  -204,    102,    307}, 0, {   138,    820}, {0x75, 0x92, 0xcb, 0xff}}},
    {{{  -204,      0,    307}, 0, {     0,    990}, {0x75, 0x92, 0xcb, 0xff}}},
    {{{   205,      0,    307}, 0, {   650,   1670}, {0x75, 0x92, 0xcb, 0xff}}},
    {{{   205,    102,    307}, 0, {   820,   1500}, {0x75, 0x92, 0xcb, 0xff}}},
    {{{   205,    102,   -306}, 0, {   820,   1500}, {0x75, 0x92, 0xcb, 0xff}}},
    {{{   205,      0,   -306}, 0, {   650,   1670}, {0x75, 0x92, 0xcb, 0xff}}},
    {{{  -204,      0,   -306}, 0, {     0,    990}, {0x75, 0x92, 0xcb, 0xff}}},
    {{{  -204,    102,   -306}, 0, {   138,    820}, {0x75, 0x92, 0xcb, 0xff}}},
};

// 0x0701B190 - 0x0701B220
static const Gfx rr_seg7_dl_0701B190[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(rr_seg7_vertex_0701B010, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(rr_seg7_vertex_0701B110, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701B220 - 0x0701B290
const Gfx rr_seg7_dl_0701B220[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(rr_seg7_dl_0701B190),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
