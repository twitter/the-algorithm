// 0x07008DB8 - 0x07008EA8
static const Vtx bits_seg7_vertex_07008DB8[] = {
    {{{ -1121,    -50,    147}, 0, {  -788,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -388,     51,   -613}, 0, {  8758,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{ -1121,     51,    147}, 0, {  -788,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  1178,    -50,   -255}, 0, {  8598,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  1178,     51,   -255}, 0, {  8598,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   465,     51,    761}, 0, {   582,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   465,    -50,    761}, 0, {   582,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{ -1121,    -50,    761}, 0, {  -644,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   465,    -50,    761}, 0, { 10596,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   465,     51,    761}, 0, { 10596,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{ -1121,     51,    761}, 0, {  -644,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -388,    -50,   -613}, 0, {  -644,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  1178,    -50,   -613}, 0, { 10596,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  1178,     51,   -613}, 0, { 10596,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -388,     51,   -613}, 0, {  -644,      0}, {0xff, 0xd4, 0x00, 0xff}}},
};

// 0x07008EA8 - 0x07008F58
static const Vtx bits_seg7_vertex_07008EA8[] = {
    {{{     4,  -1177,    607}, 0, {     0,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   315,  -1177,    607}, 0, {  3034,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   315,  -1074,    607}, 0, {  3034,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{ -1121,    -50,    147}, 0, {  -788,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -388,    -50,   -613}, 0, {  8758,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -388,     51,   -613}, 0, {  8758,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{     4,  -1177,    299}, 0, {     0,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   315,  -1074,    299}, 0, {  3034,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{     4,  -1074,    299}, 0, {     0,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   315,  -1177,    299}, 0, {  3034,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{     4,  -1074,    607}, 0, {     0,      0}, {0xff, 0xd4, 0x00, 0xff}}},
};

// 0x07008F58 - 0x07008FE8
static const Gfx bits_seg7_dl_07008F58[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09005000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_07008DB8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(bits_seg7_vertex_07008EA8, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP1Triangle( 0,  2, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x07008FE8 - 0x07009058
const Gfx bits_seg7_dl_07008FE8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bits_seg7_dl_07008F58),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
