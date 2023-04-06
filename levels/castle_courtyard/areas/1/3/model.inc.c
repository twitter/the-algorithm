// 0x07005708 - 0x070057F8
static const Vtx castle_courtyard_seg7_vertex_07005708[] = {
    {{{   205,   1741,    502}, 0, {   990,      0}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{   614,   1741,    502}, 0, {     0,      0}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{   614,   1331,    502}, 0, {     0,    990}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{ -2047,   1126,    195}, 0, {   990,      0}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{ -1637,    717,    195}, 0, {     0,    990}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{ -2047,    717,    195}, 0, {   990,    990}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{ -1637,   1126,    195}, 0, {     0,      0}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{ -2047,   1741,    195}, 0, {   990,      0}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{ -1637,   1331,    195}, 0, {     0,    990}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{ -2047,   1331,    195}, 0, {   990,    990}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{ -1637,   1741,    195}, 0, {     0,      0}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{  -613,   1741,    502}, 0, {   990,      0}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{  -204,   1331,    502}, 0, {     0,    990}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{  -613,   1331,    502}, 0, {   990,    990}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{  -204,   1741,    502}, 0, {     0,      0}, {0xa2, 0xa2, 0xc1, 0xff}}},
};

// 0x070057F8 - 0x070058A8
static const Vtx castle_courtyard_seg7_vertex_070057F8[] = {
    {{{  1638,   1741,    195}, 0, {   990,      0}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{  2048,   1741,    195}, 0, {     0,      0}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{  2048,   1331,    195}, 0, {     0,    990}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{   205,   1741,    502}, 0, {   990,      0}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{   614,   1331,    502}, 0, {     0,    990}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{   205,   1331,    502}, 0, {   990,    990}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{  1638,   1126,    195}, 0, {   990,      0}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{  2048,   1126,    195}, 0, {     0,      0}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{  2048,    717,    195}, 0, {     0,    990}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{  1638,    717,    195}, 0, {   990,    990}, {0xa2, 0xa2, 0xc1, 0xff}}},
    {{{  1638,   1331,    195}, 0, {   990,    990}, {0xa2, 0xa2, 0xc1, 0xff}}},
};

// 0x070058A8 - 0x07005938
static const Gfx castle_courtyard_seg7_dl_070058A8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, outside_0900A800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(castle_courtyard_seg7_vertex_07005708, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 14, 12, 0x0),
    gsSPVertex(castle_courtyard_seg7_vertex_070057F8, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP1Triangle( 0,  2, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x07005938 - 0x070059A8
const Gfx castle_courtyard_seg7_dl_07005938[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(castle_courtyard_seg7_dl_070058A8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
