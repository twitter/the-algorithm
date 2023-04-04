// 0x0700F238 - 0x0700F278
static const Vtx bitfs_seg7_vertex_0700F238[] = {
    {{{  -204,    154,    205}, 0, {   650,    990}, {0xbc, 0xca, 0xbf, 0xff}}},
    {{{   205,    154,   -204}, 0, {  2012,    990}, {0xbc, 0xca, 0xbf, 0xff}}},
    {{{  -204,    154,   -204}, 0, {  1330,   1670}, {0xbc, 0xca, 0xbf, 0xff}}},
    {{{   205,    154,    205}, 0, {  1330,    308}, {0xbc, 0xca, 0xbf, 0xff}}},
};

// 0x0700F278 - 0x0700F378
static const Vtx bitfs_seg7_vertex_0700F278[] = {
    {{{   184,      0,   -183}, 0, {   938,    480}, {0x67, 0x76, 0x64, 0xff}}},
    {{{   205,    154,   -204}, 0, {   990,      0}, {0x67, 0x76, 0x64, 0xff}}},
    {{{   205,    154,    205}, 0, {     0,      0}, {0x67, 0x76, 0x64, 0xff}}},
    {{{   184,      0,    184}, 0, {    20,    480}, {0x67, 0x76, 0x64, 0xff}}},
    {{{  -183,      0,    184}, 0, {   938,    480}, {0x67, 0x76, 0x64, 0xff}}},
    {{{  -204,    154,    205}, 0, {   990,      0}, {0x67, 0x76, 0x64, 0xff}}},
    {{{  -204,    154,   -204}, 0, {     0,      0}, {0x67, 0x76, 0x64, 0xff}}},
    {{{  -183,      0,   -183}, 0, {    20,    480}, {0x67, 0x76, 0x64, 0xff}}},
    {{{  -204,    154,   -204}, 0, {   990,      0}, {0x74, 0x86, 0x75, 0xff}}},
    {{{   184,      0,   -183}, 0, {    20,    480}, {0x74, 0x86, 0x75, 0xff}}},
    {{{  -183,      0,   -183}, 0, {   938,    480}, {0x74, 0x86, 0x75, 0xff}}},
    {{{   205,    154,   -204}, 0, {     0,      0}, {0x74, 0x86, 0x75, 0xff}}},
    {{{   205,    154,    205}, 0, {   990,      0}, {0x74, 0x86, 0x75, 0xff}}},
    {{{  -204,    154,    205}, 0, {     0,      0}, {0x74, 0x86, 0x75, 0xff}}},
    {{{  -183,      0,    184}, 0, {    20,    478}, {0x74, 0x86, 0x75, 0xff}}},
    {{{   184,      0,    184}, 0, {   938,    478}, {0x74, 0x86, 0x75, 0xff}}},
};

// 0x0700F378 - 0x0700F3B8
static const Vtx bitfs_seg7_vertex_0700F378[] = {
    {{{  -183,      0,   -183}, 0, {     0,    990}, {0x43, 0x56, 0x37, 0xff}}},
    {{{   184,      0,    184}, 0, {  2176,    254}, {0x43, 0x56, 0x37, 0xff}}},
    {{{  -183,      0,    184}, 0, {   704,   -482}, {0x43, 0x56, 0x37, 0xff}}},
    {{{   184,      0,   -183}, 0, {  1440,   1724}, {0x43, 0x56, 0x37, 0xff}}},
};

// 0x0700F3B8 - 0x0700F3F8
static const Vtx bitfs_seg7_vertex_0700F3B8[] = {
    {{{   205,    154,   -204}, 0, {   990,      0}, {0x79, 0xb2, 0xeb, 0xff}}},
    {{{  -183,      0,   -183}, 0, {    20,     20}, {0x79, 0xb2, 0xeb, 0xff}}},
    {{{   184,      0,   -183}, 0, {   938,     20}, {0x79, 0xb2, 0xeb, 0xff}}},
    {{{  -204,    154,   -204}, 0, {     0,      0}, {0x79, 0xb2, 0xeb, 0xff}}},
};

// 0x0700F3F8 - 0x0700F430
static const Gfx bitfs_seg7_dl_0700F3F8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_0700F238, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700F430 - 0x0700F498
static const Gfx bitfs_seg7_dl_0700F430[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_0700F278, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700F498 - 0x0700F4D0
static const Gfx bitfs_seg7_dl_0700F498[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_0700F378, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700F4D0 - 0x0700F508
static const Gfx bitfs_seg7_dl_0700F4D0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bitfs_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_0700F3B8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700F508 - 0x0700F590
const Gfx bitfs_seg7_dl_0700F508[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitfs_seg7_dl_0700F3F8),
    gsSPDisplayList(bitfs_seg7_dl_0700F430),
    gsSPDisplayList(bitfs_seg7_dl_0700F498),
    gsSPDisplayList(bitfs_seg7_dl_0700F4D0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
