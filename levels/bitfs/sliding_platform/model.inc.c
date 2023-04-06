// 0x070113A8 - 0x070114A8
static const Vtx bitfs_seg7_vertex_070113A8[] = {
    {{{   355,    109,    179}, 0, {   990,      0}, {0xce, 0x35, 0x16, 0xff}}},
    {{{   355,      7,    179}, 0, {   990,    480}, {0xce, 0x35, 0x16, 0xff}}},
    {{{   355,      7,   -188}, 0, {     0,    480}, {0xce, 0x35, 0x16, 0xff}}},
    {{{   355,    109,   -188}, 0, {     0,      0}, {0xce, 0x35, 0x16, 0xff}}},
    {{{  -361,    109,   -188}, 0, {   734,    480}, {0xce, 0x35, 0x16, 0xff}}},
    {{{  -361,      7,   -188}, 0, {   734,    990}, {0xce, 0x35, 0x16, 0xff}}},
    {{{  -361,      7,    179}, 0, {  -286,    990}, {0xce, 0x35, 0x16, 0xff}}},
    {{{  -361,    109,    179}, 0, {  -286,    480}, {0xce, 0x35, 0x16, 0xff}}},
    {{{  -361,    109,    179}, 0, {   224,    650}, {0xdf, 0x3f, 0x1f, 0xff}}},
    {{{   355,      7,    179}, 0, {  2268,    820}, {0xdf, 0x3f, 0x1f, 0xff}}},
    {{{   355,    109,    179}, 0, {  2268,    650}, {0xdf, 0x3f, 0x1f, 0xff}}},
    {{{  -361,      7,    179}, 0, {   224,    820}, {0xdf, 0x3f, 0x1f, 0xff}}},
    {{{   355,    109,   -188}, 0, {     0,    478}, {0xdf, 0x3f, 0x1f, 0xff}}},
    {{{  -361,      7,   -188}, 0, {  -322,   2522}, {0xdf, 0x3f, 0x1f, 0xff}}},
    {{{  -361,    109,   -188}, 0, {     0,   2522}, {0xdf, 0x3f, 0x1f, 0xff}}},
    {{{   355,      7,   -188}, 0, {  -322,    478}, {0xdf, 0x3f, 0x1f, 0xff}}},
};

// 0x070114A8 - 0x070114E8
static const Vtx bitfs_seg7_vertex_070114A8[] = {
    {{{  -361,    109,    179}, 0, {   990,    990}, {0xff, 0xe5, 0x65, 0xff}}},
    {{{   355,    109,   -188}, 0, {     0,   3032}, {0xff, 0xe5, 0x65, 0xff}}},
    {{{  -361,    109,   -188}, 0, {     0,    990}, {0xff, 0xe5, 0x65, 0xff}}},
    {{{   355,    109,    179}, 0, {   990,   3032}, {0xff, 0xe5, 0x65, 0xff}}},
};

// 0x070114E8 - 0x07011568
static const Gfx bitfs_seg7_dl_070114E8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09003000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_070113A8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15, 13, 0x0),
    gsSPVertex(bitfs_seg7_vertex_070114A8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07011568 - 0x070115D8
const Gfx bitfs_seg7_dl_07011568[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitfs_seg7_dl_070114E8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
