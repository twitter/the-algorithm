// 0x07008BC8 - 0x07008CC8
static const Vtx bbh_seg7_vertex_07008BC8[] = {
    {{{  1408,      0,   1603}, 0, {     0,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1408,    435,   2038}, 0, {     0,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1664,    435,   2038}, 0, {   990,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1408,   1357,   1935}, 0, {   734,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1664,   1459,   2038}, 0, {   990,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1664,   1357,   1935}, 0, {   734,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1408,   1459,   2038}, 0, {   990,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1408,   1101,   1935}, 0, {    96,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1408,   1203,   2038}, 0, {   352,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1664,   1203,   2038}, 0, {   352,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1664,   1101,   1935}, 0, {    96,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1408,      0,   1859}, 0, {     0,    268}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1664,      0,   1603}, 0, {   990,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{   -76,    819,   1654}, 0, {   990,    172}, {0xff, 0xec, 0x40, 0x50}}},
    {{{   -76,   1203,   2038}, 0, {   990,    786}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -332,   1203,   2038}, 0, {     0,    786}, {0xff, 0xec, 0x40, 0x50}}},
};

// 0x07008CC8 - 0x07008DB8
static const Vtx bbh_seg7_vertex_07008CC8[] = {
    {{{  1408,      0,   1859}, 0, {     0,    268}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1408,    179,   2038}, 0, {     0,    690}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1408,    435,   2038}, 0, {     0,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1664,      0,   1603}, 0, {   990,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1664,    435,   2038}, 0, {   990,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1664,    179,   2038}, 0, {   990,    690}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  1664,      0,   1859}, 0, {   990,    268}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -332,    819,   1654}, 0, {     0,    172}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -332,   1459,   2038}, 0, {     0,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -332,    819,   1398}, 0, {     0,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -332,   1203,   2038}, 0, {     0,    786}, {0xff, 0xec, 0x40, 0x50}}},
    {{{   -76,    819,   1654}, 0, {   990,    172}, {0xff, 0xec, 0x40, 0x50}}},
    {{{   -76,   1459,   2038}, 0, {   990,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{   -76,    819,   1398}, 0, {   990,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{   -76,   1203,   2038}, 0, {   990,    786}, {0xff, 0xec, 0x40, 0x50}}},
};

// 0x07008DB8 - 0x07008EA8
static const Gfx bbh_seg7_dl_07008DB8[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, spooky_0900B000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bbh_seg7_vertex_07008BC8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  6,  3, 0x0),
    gsSP2Triangles( 7,  8,  6, 0x0,  5,  4,  9, 0x0),
    gsSP2Triangles( 5,  9, 10, 0x0, 10,  9,  8, 0x0),
    gsSP2Triangles(10,  8,  7, 0x0, 11,  1,  0, 0x0),
    gsSP2Triangles( 0,  2, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bbh_seg7_vertex_07008CC8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  6,  5,  1, 0x0),
    gsSP2Triangles( 6,  1,  0, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 10,  7, 0x0),
    gsSP2Triangles( 9,  8, 12, 0x0,  9, 12, 13, 0x0),
    gsSP2Triangles(13, 14, 11, 0x0, 13, 12, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x07008EA8 - 0x07008F18
const Gfx bbh_seg7_dl_07008EA8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_07008DB8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
