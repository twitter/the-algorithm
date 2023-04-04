// 0x07017938 - 0x070179F8
static const Vtx lll_seg7_vertex_07017938[] = {
    {{{  -127,      0,   -127}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -127,    307,   -127}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   128,    307,   -127}, 0, {   138,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   128,      0,   -127}, 0, {   138,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -127,      0,    128}, 0, {   820,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -127,    307,    128}, 0, {   820,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -127,    307,   -127}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -127,      0,   -127}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   128,      0,   -127}, 0, {   820,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   128,    307,    128}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   128,      0,    128}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   128,    307,   -127}, 0, {   820,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x070179F8 - 0x07017A38
static const Vtx lll_seg7_vertex_070179F8[] = {
    {{{  -127,    307,   -127}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   128,    307,    128}, 0, {   820,    820}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   128,    307,   -127}, 0, {   820,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -127,    307,    128}, 0, {     0,    820}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07017A38 - 0x07017A78
static const Vtx lll_seg7_vertex_07017A38[] = {
    {{{   128,      0,    128}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   128,    307,    128}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -127,    307,    128}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -127,      0,    128}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x07017A78 - 0x07017AE0
static const Gfx lll_seg7_dl_07017A78[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, fire_09006800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&lll_seg7_lights_0700FC00.l, 1),
    gsSPLight(&lll_seg7_lights_0700FC00.a, 2),
    gsSPVertex(lll_seg7_vertex_07017938, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x07017AE0 - 0x07017B18
static const Gfx lll_seg7_dl_07017AE0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, fire_09006000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(lll_seg7_vertex_070179F8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07017B18 - 0x07017B50
static const Gfx lll_seg7_dl_07017B18[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, fire_09008800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(lll_seg7_vertex_07017A38, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07017B50 - 0x07017BE8
const Gfx lll_seg7_dl_07017B50[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lll_seg7_dl_07017A78),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lll_seg7_dl_07017AE0),
    gsSPDisplayList(lll_seg7_dl_07017B18),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
