// 0x0700C2A0 - 0x0700C2E0
static const Vtx castle_grounds_seg7_vertex_0700C2A0[] = {
    {{{   268,    803,  -3206}, 0, {   990,    328}, {0xc4, 0xc4, 0xd0, 0xff}}},
    {{{  -243,    803,  -3206}, 0, {     0,    330}, {0xc4, 0xc4, 0xd0, 0xff}}},
    {{{  -245,    803,  -2844}, 0, {     0,    970}, {0xc4, 0xc4, 0xd0, 0xff}}},
    {{{   266,    803,  -2844}, 0, {   990,    966}, {0xc4, 0xc4, 0xd0, 0xff}}},
};

// 0x0700C2E0 - 0x0700C3A0
static const Vtx castle_grounds_seg7_vertex_0700C2E0[] = {
    {{{   205,   1110,  -3104}, 0, { -4534,      0}, {0x9c, 0x9c, 0xae, 0xff}}},
    {{{   154,    803,  -3104}, 0, {  1596,    990}, {0x9c, 0x9c, 0xae, 0xff}}},
    {{{   205,    803,  -3104}, 0, {  1596,      0}, {0x9c, 0x9c, 0xae, 0xff}}},
    {{{   154,   1059,  -3104}, 0, { -3512,    990}, {0x9c, 0x9c, 0xae, 0xff}}},
    {{{   205,   1110,  -3104}, 0, {  6610,      0}, {0x9c, 0x9c, 0xae, 0xff}}},
    {{{  -153,   1059,  -3104}, 0, {  -542,    990}, {0x9c, 0x9c, 0xae, 0xff}}},
    {{{   154,   1059,  -3104}, 0, {  5588,    990}, {0x9c, 0x9c, 0xae, 0xff}}},
    {{{  -204,   1110,  -3104}, 0, { -1564,      0}, {0x9c, 0x9c, 0xae, 0xff}}},
    {{{  -153,   1059,  -3104}, 0, {  4564,    990}, {0x9c, 0x9c, 0xae, 0xff}}},
    {{{  -204,    803,  -3104}, 0, {  -544,      0}, {0x9c, 0x9c, 0xae, 0xff}}},
    {{{  -153,    803,  -3104}, 0, {  -544,    990}, {0x9c, 0x9c, 0xae, 0xff}}},
    {{{  -204,   1110,  -3104}, 0, {  5586,      0}, {0x9c, 0x9c, 0xae, 0xff}}},
};

// 0x0700C3A0 - 0x0700C3D8
static const Gfx castle_grounds_seg7_dl_0700C3A0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, outside_09006000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(castle_grounds_seg7_vertex_0700C2A0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700C3D8 - 0x0700C430
static const Gfx castle_grounds_seg7_dl_0700C3D8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, outside_09003000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(castle_grounds_seg7_vertex_0700C2E0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700C430 - 0x0700C4C0
const Gfx castle_grounds_seg7_dl_0700C430[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(castle_grounds_seg7_dl_0700C3A0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(castle_grounds_seg7_dl_0700C3D8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
