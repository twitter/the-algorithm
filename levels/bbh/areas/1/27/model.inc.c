// 0x07015750 - 0x07015840
static const Vtx bbh_seg7_vertex_07015750[] = {
    {{{  1014,    307,   -818}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1014,    410,   -716}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   963,    410,   -818}, 0, {   478,    478}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1014,    512,   -818}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1014,    410,   -921}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1014,   1126,   -818}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1014,   1229,   -716}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   963,   1229,   -818}, 0, {   478,    478}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1014,   1229,   -921}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1014,   1331,   -818}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -706,   1229,   -716}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -706,   1126,   -818}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -654,   1229,   -818}, 0, {   480,    480}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -706,   1229,   -921}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -706,   1331,   -818}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07015840 - 0x07015930
static const Vtx bbh_seg7_vertex_07015840[] = {
    {{{  -706,    512,   -818}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -706,    410,   -716}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -654,    410,   -818}, 0, {   480,    480}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -706,    307,   -818}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -706,    410,   -921}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   256,    410,  -1525}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   154,    512,  -1525}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   154,    410,  -1474}, 0, {   480,    478}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   154,    307,  -1525}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,    410,  -1525}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,   1229,  -1525}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   154,   1126,  -1525}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   154,   1229,  -1474}, 0, {   480,    478}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   256,   1229,  -1525}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   154,   1331,  -1525}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07015930 - 0x07015A20
static const Gfx bbh_seg7_dl_07015930[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, spooky_09006800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bbh_seg7_vertex_07015750, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 3,  4,  2, 0x0,  4,  0,  2, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  8,  5,  7, 0x0),
    gsSP2Triangles( 6,  9,  7, 0x0,  9,  8,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 11, 13, 12, 0x0),
    gsSP2Triangles(13, 14, 12, 0x0, 14, 10, 12, 0x0),
    gsSPVertex(bbh_seg7_vertex_07015840, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 3,  4,  2, 0x0,  4,  0,  2, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  8,  5,  7, 0x0),
    gsSP2Triangles( 6,  9,  7, 0x0,  9,  8,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 11, 13, 12, 0x0),
    gsSP2Triangles(13, 14, 12, 0x0, 14, 10, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x07015A20 - 0x07015A90
const Gfx bbh_seg7_dl_07015A20[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_07015930),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
