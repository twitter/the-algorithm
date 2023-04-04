// 0x07009258 - 0x07009358
static const Vtx bitfs_seg7_vertex_07009258[] = {
    {{{   819,    205,    307}, 0, {  1414,    308}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   512,      0,    614}, 0, {     0,    990}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   819,      0,    307}, 0, {  1414,    990}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   819,      0,   -307}, 0, {     0,    990}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   512,    205,   -615}, 0, {  1414,    308}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   819,    205,   -307}, 0, {     0,    308}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   512,      0,   -615}, 0, {  1414,    990}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  -511,    205,   -615}, 0, {     0,    308}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  -511,      0,   -615}, 0, {     0,    990}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  -818,      0,   -307}, 0, {  1414,    990}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  -818,    205,   -307}, 0, {  1414,    308}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  -818,      0,    307}, 0, {  -454,    990}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  -511,    205,    614}, 0, {   990,    308}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  -818,    205,    307}, 0, {  -454,    308}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  -511,      0,    614}, 0, {   990,    990}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   512,    205,    614}, 0, {     0,    308}, {0x7d, 0x7d, 0x7d, 0xff}}},
};

// 0x07009358 - 0x07009458
static const Vtx bitfs_seg7_vertex_07009358[] = {
    {{{  -818,      0,    307}, 0, {  2016,    990}, {0x64, 0x64, 0x64, 0xff}}},
    {{{  -818,    205,   -307}, 0, {     0,    308}, {0x64, 0x64, 0x64, 0xff}}},
    {{{  -818,      0,   -307}, 0, {     0,    990}, {0x64, 0x64, 0x64, 0xff}}},
    {{{  -818,    205,    307}, 0, {  2016,    308}, {0x64, 0x64, 0x64, 0xff}}},
    {{{   819,      0,   -307}, 0, {  2016,    990}, {0x64, 0x64, 0x64, 0xff}}},
    {{{   819,    205,   -307}, 0, {  2016,    308}, {0x64, 0x64, 0x64, 0xff}}},
    {{{   819,    205,    307}, 0, {     0,    308}, {0x64, 0x64, 0x64, 0xff}}},
    {{{   819,      0,    307}, 0, {     0,    990}, {0x64, 0x64, 0x64, 0xff}}},
    {{{  -511,    205,   -615}, 0, {  3374,    308}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{   512,    205,   -615}, 0, {     0,    308}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{   512,      0,   -615}, 0, {     0,    990}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{  -511,      0,   -615}, 0, {  3374,    990}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{   512,    205,    614}, 0, {  3374,    308}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{  -511,      0,    614}, 0, {     0,    990}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{   512,      0,    614}, 0, {  3374,    990}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{  -511,    205,    614}, 0, {     0,    308}, {0x8c, 0x8c, 0x8c, 0xff}}},
};

// 0x07009458 - 0x070094D8
static const Vtx bitfs_seg7_vertex_07009458[] = {
    {{{  -818,    205,   -307}, 0, {   736,   2522}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   512,    205,    614}, 0, {  1246,   -288}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   819,    205,    307}, 0, {  2012,   -288}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   819,    205,   -307}, 0, {  2780,    480}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   512,    205,   -615}, 0, {  2780,   1244}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -511,    205,    614}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -511,    205,   -615}, 0, {  1502,   2522}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -818,    205,    307}, 0, {     0,   1754}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x070094D8 - 0x07009588
static const Gfx bitfs_seg7_dl_070094D8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bitfs_seg7_texture_07001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_07009258, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  7,  9, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0, 15,  1, 0x0),
    gsSPVertex(bitfs_seg7_vertex_07009358, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x07009588 - 0x070095E0
static const Gfx bitfs_seg7_dl_07009588[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_07009458, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  2,  3, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 0,  4,  6, 0x0,  0,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x070095E0 - 0x07009670
const Gfx bitfs_seg7_dl_070095E0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitfs_seg7_dl_070094D8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitfs_seg7_dl_07009588),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
