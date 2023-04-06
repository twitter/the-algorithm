// 0x0700F4C0 - 0x0700F500
static const Vtx ccm_seg7_vertex_0700F4C0[] = {
    {{{   233,   -275,     91}, 0, {     0,      0}, {0x99, 0x99, 0x99, 0xff}}},
    {{{   233,   -378,     91}, 0, {     0,    990}, {0x99, 0x99, 0x99, 0xff}}},
    {{{   308,   -275,     21}, 0, {   990,      0}, {0x99, 0x99, 0x99, 0xff}}},
    {{{   308,   -378,     21}, 0, {   990,    990}, {0x99, 0x99, 0x99, 0xff}}},
};

// 0x0700F500 - 0x0700F5C0
static const Vtx ccm_seg7_vertex_0700F500[] = {
    {{{  -116,    100,    541}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   573,   -104,    -99}, 0, {  6232,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   573,    100,    -99}, 0, {  6236,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -116,   -104,    541}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -611,    307,      9}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -116,   -104,    541}, 0, {  4992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -116,    100,    541}, 0, {  4996,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -611,    102,      9}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   573,    100,    -99}, 0, {  4996,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    44,    102,   -669}, 0, {  -384,   -104}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    44,    307,   -669}, 0, {  -384,    918}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   573,   -104,    -99}, 0, {  4992,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0700F5C0 - 0x0700F5F8
static const Gfx ccm_seg7_dl_0700F5C0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snow_09004000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ccm_seg7_vertex_0700F4C0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700F5F8 - 0x0700F650
static const Gfx ccm_seg7_dl_0700F5F8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snow_09007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ccm_seg7_vertex_0700F500, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700F650 - 0x0700F6E0
const Gfx ccm_seg7_dl_0700F650[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ccm_seg7_dl_0700F5C0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ccm_seg7_dl_0700F5F8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
