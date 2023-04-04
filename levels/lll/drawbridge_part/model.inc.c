// 0x070183F0 - 0x07018470
static const Vtx lll_seg7_vertex_070183F0[] = {
    {{{     0,   -101,   -191}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{     0,      0,    192}, 0, {  3800,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{     0,   -101,    192}, 0, {  3800,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{     0,      0,   -191}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -639,   -101,    192}, 0, {  3800,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -639,      0,   -191}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -639,   -101,   -191}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -639,      0,    192}, 0, {  3800,      0}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x07018470 - 0x070184B0
static const Vtx lll_seg7_vertex_07018470[] = {
    {{{     0,      0,   -191}, 0, { -5140,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -639,      0,   -191}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -639,      0,    192}, 0, {     0,  -2076}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,      0,    192}, 0, { -5140,  -2076}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x070184B0 - 0x070184F0
static const Vtx lll_seg7_vertex_070184B0[] = {
    {{{     0,   -101,    192}, 0, { -3352,    862}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -639,   -101,    192}, 0, {  -158,    862}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -639,   -101,   -191}, 0, {  -158,  -1054}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,   -101,   -191}, 0, { -3352,  -1054}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x070184F0 - 0x07018570
static const Vtx lll_seg7_vertex_070184F0[] = {
    {{{     0,   -101,    192}, 0, {  5334,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,      0,    192}, 0, {  5334,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -639,      0,    192}, 0, { -1052,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -639,   -101,    192}, 0, { -1052,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -639,   -101,   -191}, 0, {  2268,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -639,      0,   -191}, 0, {  2268,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,      0,   -191}, 0, { -4118,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,   -101,   -191}, 0, { -4118,    990}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x07018570 - 0x070185C8
static const Gfx lll_seg7_dl_07018570[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_07005000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&lll_seg7_lights_0700FC00.l, 1),
    gsSPLight(&lll_seg7_lights_0700FC00.a, 2),
    gsSPVertex(lll_seg7_vertex_070183F0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x070185C8 - 0x07018600
static const Gfx lll_seg7_dl_070185C8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, fire_0900A000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(lll_seg7_vertex_07018470, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07018600 - 0x07018638
static const Gfx lll_seg7_dl_07018600[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, fire_09001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(lll_seg7_vertex_070184B0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07018638 - 0x07018680
static const Gfx lll_seg7_dl_07018638[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(lll_seg7_vertex_070184F0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x07018680 - 0x07018708
const Gfx lll_seg7_dl_07018680[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lll_seg7_dl_07018570),
    gsSPDisplayList(lll_seg7_dl_070185C8),
    gsSPDisplayList(lll_seg7_dl_07018600),
    gsSPDisplayList(lll_seg7_dl_07018638),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
