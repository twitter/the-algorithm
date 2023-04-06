// 0x0700AD90 - 0x0700AE90
static const Vtx bitdw_seg7_vertex_0700AD90[] = {
    {{{  -409,    205,   -204}, 0, {   990,   2010}, {0xce, 0x91, 0x16, 0xff}}},
    {{{  -409,      0,   -204}, 0, {   478,   1498}, {0xce, 0x91, 0x16, 0xff}}},
    {{{  -409,      0,    205}, 0, {  1502,    478}, {0xce, 0x91, 0x16, 0xff}}},
    {{{  -409,    205,    205}, 0, {  2012,    990}, {0xce, 0x91, 0x16, 0xff}}},
    {{{   973,      0,   -204}, 0, {   480,   1498}, {0xce, 0x91, 0x16, 0xff}}},
    {{{   973,    205,    205}, 0, {  2012,    990}, {0xce, 0x91, 0x16, 0xff}}},
    {{{   973,      0,    205}, 0, {  1500,    480}, {0xce, 0x91, 0x16, 0xff}}},
    {{{   973,    205,   -204}, 0, {   990,   2010}, {0xce, 0x91, 0x16, 0xff}}},
    {{{  -409,    205,   -204}, 0, {   478,    478}, {0xce, 0xaf, 0x16, 0xff}}},
    {{{   973,      0,   -204}, 0, {  3418,   4438}, {0xce, 0xaf, 0x16, 0xff}}},
    {{{  -409,      0,   -204}, 0, {     0,    990}, {0xce, 0xaf, 0x16, 0xff}}},
    {{{   973,    205,   -204}, 0, {  3928,   3926}, {0xce, 0xaf, 0x16, 0xff}}},
    {{{   973,    205,    205}, 0, {  3928,   3926}, {0xce, 0xaf, 0x16, 0xff}}},
    {{{  -409,      0,    205}, 0, {     0,    990}, {0xce, 0xaf, 0x16, 0xff}}},
    {{{   973,      0,    205}, 0, {  3418,   4438}, {0xce, 0xaf, 0x16, 0xff}}},
    {{{  -409,    205,    205}, 0, {   478,    478}, {0xce, 0xaf, 0x16, 0xff}}},
};

// 0x0700AE90 - 0x0700AF10
static const Vtx bitdw_seg7_vertex_0700AE90[] = {
    {{{  -409,      0,   -204}, 0, {   990,   2010}, {0x91, 0x6d, 0x00, 0xff}}},
    {{{   973,      0,   -204}, 0, {  4440,  -1438}, {0x91, 0x6d, 0x00, 0xff}}},
    {{{   973,      0,    205}, 0, {  3418,  -2460}, {0x91, 0x6d, 0x00, 0xff}}},
    {{{  -409,      0,    205}, 0, {     0,    990}, {0x91, 0x6d, 0x00, 0xff}}},
    {{{  -409,    205,    205}, 0, {     0,    990}, {0xff, 0xe5, 0x65, 0xff}}},
    {{{   973,    205,   -204}, 0, {  4440,  -1438}, {0xff, 0xe5, 0x65, 0xff}}},
    {{{  -409,    205,   -204}, 0, {   990,   2010}, {0xff, 0xe5, 0x65, 0xff}}},
    {{{   973,    205,    205}, 0, {  3418,  -2460}, {0xff, 0xe5, 0x65, 0xff}}},
};

// 0x0700AF10 - 0x0700AFA0
static const Gfx bitdw_seg7_dl_0700AF10[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitdw_seg7_vertex_0700AD90, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15, 13, 0x0),
    gsSPVertex(bitdw_seg7_vertex_0700AE90, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700AFA0 - 0x0700B010
const Gfx bitdw_seg7_dl_0700AFA0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitdw_seg7_dl_0700AF10),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
