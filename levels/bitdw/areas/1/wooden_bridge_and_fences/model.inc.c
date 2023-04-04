// 0x07003370 - 0x07003460
static const Vtx bitdw_seg7_vertex_07003370[] = {
    {{{    51,    410,  -1228}, 0, {   912,    -30}, {0xaf, 0xce, 0x16, 0xff}}},
    {{{ -1021,    492,  -1393}, 0, {-10012,    -24}, {0xaf, 0xce, 0x16, 0xff}}},
    {{{ -1021,    390,  -1393}, 0, { -9878,    990}, {0xaf, 0xce, 0x16, 0xff}}},
    {{{    51,    410,  -1228}, 0, {   990,      0}, {0xaf, 0xce, 0x16, 0xff}}},
    {{{   256,    307,   -818}, 0, { -3578,    990}, {0xaf, 0xce, 0x16, 0xff}}},
    {{{   264,    409,   -818}, 0, { -3614,    -28}, {0xaf, 0xce, 0x16, 0xff}}},
    {{{    51,    307,  -1228}, 0, {   990,    990}, {0xaf, 0xce, 0x16, 0xff}}},
    {{{   264,    409,   -818}, 0, {   786,      0}, {0xaf, 0xce, 0x16, 0xff}}},
    {{{   162,    417,   -818}, 0, {  -234,      0}, {0xaf, 0xce, 0x16, 0xff}}},
    {{{   154,    315,   -818}, 0, {  -234,    990}, {0xaf, 0xce, 0x16, 0xff}}},
    {{{   256,    307,   -818}, 0, {   786,    990}, {0xaf, 0xce, 0x16, 0xff}}},
    {{{  -146,    441,   -818}, 0, {   800,    -30}, {0xaf, 0xce, 0x16, 0xff}}},
    {{{ -1074,    512,   -818}, 0, { -8488,    -28}, {0xaf, 0xce, 0x16, 0xff}}},
    {{{ -1074,    410,   -818}, 0, { -8410,    990}, {0xaf, 0xce, 0x16, 0xff}}},
    {{{  -146,    338,   -818}, 0, {   878,    990}, {0xaf, 0xce, 0x16, 0xff}}},
};

// 0x07003460 - 0x070034E0
static const Vtx bitdw_seg7_vertex_07003460[] = {
    {{{  -153,    205,   -818}, 0, {   990,    990}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{  -153,   -442,   1090}, 0, { -9072,    990}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{  -153,   -215,   1058}, 0, { -8900,    -86}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{  -146,    441,   -818}, 0, {   990,      0}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{   154,   -215,   1058}, 0, { -8900,    -86}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{   154,   -442,   1090}, 0, { -9070,    990}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{   162,    417,   -818}, 0, {   990,      0}, {0xdf, 0xbf, 0x1f, 0xff}}},
    {{{   154,    205,   -818}, 0, {   990,    990}, {0xdf, 0xbf, 0x1f, 0xff}}},
};

// 0x070034E0 - 0x07003520
static const Vtx bitdw_seg7_vertex_070034E0[] = {
    {{{   154,   -306,   1024}, 0, {     0,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   154,    315,   -818}, 0, {  6440,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -146,    338,   -818}, 0, {  6464,    972}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -153,   -306,   1024}, 0, {     0,    988}, {0xff, 0xd4, 0x00, 0xff}}},
};

// 0x07003520 - 0x07003588
static const Gfx bitdw_seg7_dl_07003520[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09005000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitdw_seg7_vertex_07003370, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0,  2,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x07003588 - 0x070035D0
static const Gfx bitdw_seg7_dl_07003588[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09006000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitdw_seg7_vertex_07003460, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x070035D0 - 0x07003608
static const Gfx bitdw_seg7_dl_070035D0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitdw_seg7_vertex_070034E0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x07003608 - 0x07003688
const Gfx bitdw_seg7_dl_07003608[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitdw_seg7_dl_07003520),
    gsSPDisplayList(bitdw_seg7_dl_07003588),
    gsSPDisplayList(bitdw_seg7_dl_070035D0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
