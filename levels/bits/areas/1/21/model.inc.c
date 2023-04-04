// 0x07012B80 - 0x07012C80
static const Vtx bits_seg7_vertex_07012B80[] = {
    {{{   563,    205,   -204}, 0, {   990,   2112}, {0xff, 0x7c, 0x00, 0xff}}},
    {{{  -460,      0,   -204}, 0, {     0,     72}, {0xff, 0x7c, 0x00, 0xff}}},
    {{{  -460,    205,   -204}, 0, {   990,     72}, {0xff, 0x7c, 0x00, 0xff}}},
    {{{   563,      0,   -204}, 0, {     0,   2112}, {0xff, 0x7c, 0x00, 0xff}}},
    {{{  -460,    205,    205}, 0, {   990,     72}, {0xff, 0x7c, 0x00, 0xff}}},
    {{{  -460,      0,    205}, 0, {     0,     72}, {0xff, 0x7c, 0x00, 0xff}}},
    {{{   563,      0,    205}, 0, {     0,   2112}, {0xff, 0x7c, 0x00, 0xff}}},
    {{{   563,    205,    205}, 0, {   990,   2112}, {0xff, 0x7c, 0x00, 0xff}}},
    {{{  -460,    205,   -204}, 0, {   990,     72}, {0xff, 0x8b, 0x18, 0xff}}},
    {{{  -460,      0,   -204}, 0, {     0,     72}, {0xff, 0x8b, 0x18, 0xff}}},
    {{{  -460,      0,    205}, 0, {     0,     72}, {0xff, 0x8b, 0x18, 0xff}}},
    {{{  -460,    205,    205}, 0, {   990,     72}, {0xff, 0x8b, 0x18, 0xff}}},
    {{{  -460,    205,    205}, 0, {   990,     72}, {0xff, 0xb4, 0x4c, 0xff}}},
    {{{   563,    205,    205}, 0, {   990,   2112}, {0xff, 0xb4, 0x4c, 0xff}}},
    {{{   563,    205,   -204}, 0, {   990,   2112}, {0xff, 0xb4, 0x4c, 0xff}}},
    {{{  -460,    205,   -204}, 0, {   990,     72}, {0xff, 0xb4, 0x4c, 0xff}}},
};

// 0x07012C80 - 0x07012CC0
static const Vtx bits_seg7_vertex_07012C80[] = {
    {{{   563,      0,    205}, 0, {     0,   2112}, {0x78, 0x3c, 0x00, 0xff}}},
    {{{  -460,      0,    205}, 0, {     0,     72}, {0x78, 0x3c, 0x00, 0xff}}},
    {{{  -460,      0,   -204}, 0, {     0,     72}, {0x78, 0x3c, 0x00, 0xff}}},
    {{{   563,      0,   -204}, 0, {     0,   2112}, {0x78, 0x3c, 0x00, 0xff}}},
};

// 0x07012CC0 - 0x07012D40
static const Gfx bits_seg7_dl_07012CC0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09002000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_07012B80, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(bits_seg7_vertex_07012C80, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07012D40 - 0x07012DB0
const Gfx bits_seg7_dl_07012D40[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bits_seg7_dl_07012CC0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
