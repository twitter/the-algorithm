// 0x07018708 - 0x07018808
static const Vtx lll_seg7_vertex_07018708[] = {
    {{{  -165,      0,      0}, 0, {     0,    514}, {0x95, 0x1a, 0xc2, 0xff}}},
    {{{  -115,    205,      0}, 0, {   168,  -1014}, {0x95, 0x1a, 0xc2, 0xff}}},
    {{{   -57,    205,   -100}, 0, {  1098,  -1014}, {0x95, 0x1a, 0xc2, 0xff}}},
    {{{   -82,      0,   -143}, 0, {  1296,    514}, {0x95, 0x1a, 0xc2, 0xff}}},
    {{{   -57,    205,   -100}, 0, {  -496,   -838}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -115,    205,      0}, 0, {  -960,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -57,    205,    101}, 0, {  -496,    774}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    58,    205,    101}, 0, {   434,    774}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   116,    205,      0}, 0, {   898,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    58,    205,   -100}, 0, {   434,   -838}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -82,      0,    144}, 0, {  1190,   1638}, {0x95, 0x1a, 0x3e, 0xff}}},
    {{{   -57,    205,    101}, 0, {   990,      0}, {0x95, 0x1a, 0x3e, 0xff}}},
    {{{  -165,      0,      0}, 0, {  -138,   1638}, {0x95, 0x1a, 0x3e, 0xff}}},
    {{{   -57,    205,    101}, 0, {   990,      0}, {0x95, 0x1a, 0x3d, 0xff}}},
    {{{  -115,    205,      0}, 0, {    60,      0}, {0x95, 0x1a, 0x3d, 0xff}}},
    {{{  -165,      0,      0}, 0, {  -138,   1638}, {0x95, 0x1a, 0x3d, 0xff}}},
};

// 0x07018808 - 0x07018908
static const Vtx lll_seg7_vertex_07018808[] = {
    {{{   -82,      0,   -143}, 0, { -1358,    514}, {0x00, 0x1a, 0x84, 0xff}}},
    {{{   -57,    205,   -100}, 0, { -1160,  -1014}, {0x00, 0x1a, 0x84, 0xff}}},
    {{{    58,    205,   -100}, 0, {  -230,  -1014}, {0x00, 0x1a, 0x84, 0xff}}},
    {{{    83,      0,    144}, 0, {     0,    990}, {0x00, 0x1a, 0x7c, 0xff}}},
    {{{   -57,    205,    101}, 0, { -1160,   -682}, {0x00, 0x1a, 0x7c, 0xff}}},
    {{{   -82,      0,    144}, 0, { -1358,    990}, {0x00, 0x1a, 0x7c, 0xff}}},
    {{{    58,    205,    101}, 0, {  -230,   -682}, {0x00, 0x1a, 0x7c, 0xff}}},
    {{{   166,      0,      0}, 0, {  1296,    990}, {0x6b, 0x1a, 0x3d, 0xff}}},
    {{{   116,    205,      0}, 0, {  1098,   -682}, {0x6b, 0x1a, 0x3d, 0xff}}},
    {{{    58,    205,    101}, 0, {   168,   -682}, {0x6b, 0x1a, 0x3d, 0xff}}},
    {{{   166,      0,      0}, 0, {  1296,    990}, {0x6b, 0x1a, 0x3e, 0xff}}},
    {{{    58,    205,    101}, 0, {   168,   -682}, {0x6b, 0x1a, 0x3e, 0xff}}},
    {{{    83,      0,    144}, 0, {     0,    990}, {0x6b, 0x1a, 0x3e, 0xff}}},
    {{{    83,      0,   -143}, 0, {  -138,   1162}, {0x6b, 0x1a, 0xc2, 0xff}}},
    {{{    58,    205,   -100}, 0, {    60,   -364}, {0x6b, 0x1a, 0xc2, 0xff}}},
    {{{   116,    205,      0}, 0, {   990,   -364}, {0x6b, 0x1a, 0xc2, 0xff}}},
};

// 0x07018908 - 0x07018968
static const Vtx lll_seg7_vertex_07018908[] = {
    {{{    83,      0,   -143}, 0, {  -138,   1162}, {0x6b, 0x1a, 0xc2, 0xff}}},
    {{{   116,    205,      0}, 0, {   990,   -364}, {0x6b, 0x1a, 0xc2, 0xff}}},
    {{{   166,      0,      0}, 0, {  1190,   1162}, {0x6b, 0x1a, 0xc2, 0xff}}},
    {{{   -82,      0,   -143}, 0, { -1358,    514}, {0x00, 0x1a, 0x84, 0xff}}},
    {{{    58,    205,   -100}, 0, {  -230,  -1014}, {0x00, 0x1a, 0x84, 0xff}}},
    {{{    83,      0,   -143}, 0, {     0,    514}, {0x00, 0x1a, 0x84, 0xff}}},
};

// 0x07018968 - 0x07018A30
static const Gfx lll_seg7_dl_07018968[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_07004000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&lll_seg7_lights_0700FC00.l, 1),
    gsSPLight(&lll_seg7_lights_0700FC00.a, 2),
    gsSPVertex(lll_seg7_vertex_07018708, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 4,  7,  8, 0x0,  4,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(lll_seg7_vertex_07018808, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(lll_seg7_vertex_07018908, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07018A30 - 0x07018AA0
const Gfx lll_seg7_dl_07018A30[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lll_seg7_dl_07018968),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
