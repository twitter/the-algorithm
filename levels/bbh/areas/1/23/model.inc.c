// 0x07013AA0 - 0x07013B80
static const Vtx bbh_seg7_vertex_07013AA0[] = {
    {{{  2140,   1126,   -306}, 0, {   990,    990}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{  2140,   1536,   -716}, 0, {     0,      0}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{  2140,   1126,   -716}, 0, {     0,    990}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{  1432,   1280,  -1519}, 0, {   990,      0}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{  1142,   1280,  -1229}, 0, {     0,      0}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{  1142,   1075,  -1229}, 0, {     0,    480}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{  1432,   1075,  -1519}, 0, {   990,    480}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{  1243,    928,  -1129}, 0, {     0,    990}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{  1533,    928,  -1418}, 0, {   990,    990}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{  2140,   1126,   -921}, 0, {   990,    990}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{  2140,   1536,  -1330}, 0, {     0,      0}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{  2140,   1126,  -1330}, 0, {     0,    990}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{  2140,   1536,   -921}, 0, {   990,      0}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{  2140,   1536,   -306}, 0, {   990,      0}, {0xff, 0x00, 0x00, 0x9a}}},
};

// 0x07013B80 - 0x07013BE8
static const Gfx bbh_seg7_dl_07013B80[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, spooky_0900A800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bbh_seg7_vertex_07013AA0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  5,  7,  8, 0x0),
    gsSP2Triangles( 5,  8,  6, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 9, 12, 10, 0x0,  0, 13,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07013BE8 - 0x07013C58
const Gfx bbh_seg7_dl_07013BE8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_07013B80),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
