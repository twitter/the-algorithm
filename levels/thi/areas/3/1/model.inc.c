// 0x070079D0 - 0x07007AB0
static const Vtx thi_seg7_vertex_070079D0[] = {
    {{{  -101,   2458,    102}, 0, {   376,    990}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   102,   3072,    102}, 0, {     0,    744}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   102,   2458,    102}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   614,   1024,   2048}, 0, {   274,    662}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   614,   1229,   2048}, 0, {    70,    662}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   717,   1331,   1229}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   717,    922,   1229}, 0, {   376,    990}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   307,   1331,   1229}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   410,   1024,   2048}, 0, {   274,    662}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   307,    922,   1229}, 0, {   376,    990}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   410,   1229,   2048}, 0, {    70,    662}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   614,   1229,   2048}, 0, {    70,    716}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   410,   1229,   2048}, 0, {   274,    716}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   307,   1331,   1229}, 0, {   376,    990}, {0xff, 0xff, 0xff, 0x80}}},
};

// 0x07007AB0 - 0x07007B80
static const Vtx thi_seg7_vertex_07007AB0[] = {
    {{{   102,   2458,    102}, 0, {   376,    990}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   102,   3072,   -101}, 0, {    70,    762}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   102,   2458,   -101}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0x80}}},
    {{{  -101,   2458,    102}, 0, {   376,    990}, {0xff, 0xff, 0xff, 0x80}}},
    {{{  -101,   3072,    102}, 0, {   376,    744}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   102,   3072,    102}, 0, {     0,    744}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   102,   3072,   -101}, 0, {     0,    744}, {0xff, 0xff, 0xff, 0x80}}},
    {{{  -101,   3072,   -101}, 0, {   376,    744}, {0xff, 0xff, 0xff, 0x80}}},
    {{{  -101,   2458,   -101}, 0, {   376,    990}, {0xff, 0xff, 0xff, 0x80}}},
    {{{  -101,   3072,   -101}, 0, {   274,    762}, {0xff, 0xff, 0xff, 0x80}}},
    {{{  -101,   3072,    102}, 0, {    70,    762}, {0xff, 0xff, 0xff, 0x80}}},
    {{{  -101,   2458,    102}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0x80}}},
    {{{   102,   3072,    102}, 0, {   274,    762}, {0xff, 0xff, 0xff, 0x80}}},
};

// 0x07007B80 - 0x07007C20
static const Gfx thi_seg7_dl_07007B80[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, grass_0900B800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(thi_seg7_vertex_070079D0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0,  5, 11, 12, 0x0),
    gsSP1Triangle( 5, 12, 13, 0x0),
    gsSPVertex(thi_seg7_vertex_07007AB0, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 2,  6,  7, 0x0,  2,  7,  8, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP1Triangle( 0, 12,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07007C20 - 0x07007C90
const Gfx thi_seg7_dl_07007C20[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(thi_seg7_dl_07007B80),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
