// Koopa (Small Koopa, Big Koopa [Koopa the Quick])


// Unreferenced light group
UNUSED static const Lights1 koopa_lights_unused1 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x060025A0
static const Lights1 koopa_seg6_lights_060025A0 = gdSPDefLights1(
    0x00, 0x59, 0x00,
    0x00, 0xb2, 0x00, 0x28, 0x28, 0x28
);

// 0x060025B8
static const Lights1 koopa_seg6_lights_060025B8 = gdSPDefLights1(
    0x70, 0x57, 0x00,
    0xe0, 0xae, 0x00, 0x28, 0x28, 0x28
);

// 0x060025D0
static const Lights1 koopa_seg6_lights_060025D0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 koopa_lights_unused2 = gdSPDefLights1(
    0x59, 0x59, 0x59,
    0xb2, 0xb2, 0xb2, 0x28, 0x28, 0x28
);

// 0x06002600
static const Lights1 koopa_seg6_lights_06002600 = gdSPDefLights1(
    0x00, 0x54, 0x00,
    0x00, 0xa9, 0x00, 0x28, 0x28, 0x28
);

// 0x06002618
static const Lights1 koopa_seg6_lights_06002618 = gdSPDefLights1(
    0x59, 0x59, 0x59,
    0xb2, 0xb2, 0xb2, 0x28, 0x28, 0x28
);

// 0x06002630
static const Lights1 koopa_seg6_lights_06002630 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// //! There is a malformed light entry here pointing to this texture + 0x18.
//     It results in the koopa actor 'wearing' what appears to be pink shorts
//     beneath its shell, despite the fact it was intended to be white like
//     the rest of its body. This is evident because once the mistake is corrected
//     it turns back to being white like the other polygons.
// 0x06002648
ALIGNED8 static const Texture koopa_seg6_texture_06002648[] = {
#include "actors/koopa/koopa_shell_front.rgba16.inc.c"
};

// 0x06002E48
ALIGNED8 static const Texture koopa_seg6_texture_06002E48[] = {
#include "actors/koopa/koopa_shell_back.rgba16.inc.c"
};

// 0x06003648
ALIGNED8 static const Texture koopa_seg6_texture_06003648[] = {
#include "actors/koopa/koopa_shoe.rgba16.inc.c"
};

// 0x06003E48
ALIGNED8 static const Texture koopa_seg6_texture_06003E48[] = {
#include "actors/koopa/koopa_shell_front_top.rgba16.inc.c"
};

// 0x06004648
ALIGNED8 static const Texture koopa_seg6_texture_06004648[] = {
#include "actors/koopa/koopa_eyes_open.rgba16.inc.c"
};

// 0x06004E48
ALIGNED8 static const Texture koopa_seg6_texture_06004E48[] = {
#include "actors/koopa/koopa_eyes_closed.rgba16.inc.c"
};

// 0x06005648
ALIGNED8 static const Texture koopa_seg6_texture_06005648[] = {
#include "actors/koopa/koopa_eye_border.rgba16.inc.c"
};

// 0x06005E48
ALIGNED8 static const Texture koopa_seg6_texture_06005E48[] = {
#include "actors/koopa/koopa_nostrils.rgba16.inc.c"
};

// 0x06006E48
static const Lights1 koopa_seg6_lights_06006E48 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06006E60
static const Lights1 koopa_seg6_lights_06006E60 = gdSPDefLights1(
    0x67, 0x0b, 0x1a,
    0xce, 0x16, 0x35, 0x28, 0x28, 0x28
);

// 0x06006E78
static const Vtx koopa_seg6_vertex_06006E78[] = {
    {{{   139,     -4,      0}, 0, {   486,    684}, {0x76, 0x2d, 0x00, 0xff}}},
    {{{   133,    -11,    -56}, 0, {     8,    694}, {0x38, 0x4e, 0xae, 0xff}}},
    {{{   111,      2,    -51}, 0, {    40,    976}, {0x39, 0x0d, 0x90, 0xff}}},
    {{{   155,    -27,     49}, 0, {   908,    324}, {0x57, 0x36, 0x49, 0xff}}},
    {{{   184,    -56,     23}, 0, {   700,    -64}, {0x75, 0x18, 0x29, 0xff}}},
    {{{   133,    -11,     56}, 0, {   962,    696}, {0x39, 0x41, 0x5c, 0xff}}},
    {{{   184,    -56,    -23}, 0, {   302,    -66}, {0x6f, 0x23, 0xcf, 0xff}}},
    {{{   111,      2,     52}, 0, {   918,    980}, {0x39, 0x0e, 0x70, 0xff}}},
    {{{   155,    -27,    -48}, 0, {    78,    322}, {0x51, 0x2e, 0xab, 0xff}}},
};

// 0x06006F08
static const Vtx koopa_seg6_vertex_06006F08[] = {
    {{{    10,     50,    -36}, 0, {   796,   3890}, {0xad, 0x48, 0xc2, 0xff}}},
    {{{    15,     22,    -64}, 0, {  1060,   3604}, {0xae, 0x1d, 0xa5, 0xff}}},
    {{{   -12,     -4,      0}, 0, {   458,   4320}, {0x82, 0xf8, 0x00, 0xff}}},
    {{{    16,    -63,      0}, 0, {   458,   3226}, {0xbd, 0x95, 0x00, 0xff}}},
    {{{    43,    -44,    -54}, 0, {   964,   2536}, {0xe5, 0xa4, 0xae, 0xff}}},
    {{{    69,    -65,    -18}, 0, {   626,   1742}, {0xe6, 0x8b, 0xd7, 0xff}}},
    {{{    99,    -87,      0}, 0, {   458,    814}, {0xdc, 0x87, 0x00, 0xff}}},
    {{{   102,    -44,    -54}, 0, {   960,    882}, {0xf5, 0xaf, 0x9f, 0xff}}},
    {{{    69,    -65,     18}, 0, {   288,   1766}, {0xe6, 0x8b, 0x29, 0xff}}},
    {{{   102,    -44,     54}, 0, {   -42,    956}, {0xf4, 0xae, 0x60, 0xff}}},
    {{{   147,    -93,      0}, 0, {   458,   -570}, {0xf9, 0x82, 0x00, 0xff}}},
    {{{    43,    -44,     54}, 0, {   -46,   2612}, {0xe6, 0xa4, 0x52, 0xff}}},
    {{{    23,    -24,     60}, 0, {   -92,   3240}, {0xad, 0xcb, 0x4f, 0xff}}},
    {{{    23,    -24,    -59}, 0, {  1012,   3160}, {0xac, 0xcc, 0xb2, 0xff}}},
    {{{     0,     52,      0}, 0, {   460,   4212}, {0x99, 0x49, 0x09, 0xff}}},
};

// 0x06006FF8
static const Vtx koopa_seg6_vertex_06006FF8[] = {
    {{{     0,     52,      0}, 0, {   460,   4212}, {0x99, 0x49, 0x09, 0xff}}},
    {{{    45,     75,     32}, 0, {   164,   3076}, {0xe3, 0x6a, 0x3e, 0xff}}},
    {{{    36,     84,      0}, 0, {   462,   3348}, {0xd1, 0x75, 0xfa, 0xff}}},
    {{{   -12,     -4,      0}, 0, {   458,   4320}, {0x82, 0xf8, 0x00, 0xff}}},
    {{{    10,     50,     36}, 0, {   124,   3938}, {0xac, 0x44, 0x41, 0xff}}},
    {{{    15,     22,     65}, 0, {  -140,   3692}, {0xae, 0x1e, 0x5b, 0xff}}},
    {{{    23,    -24,     60}, 0, {   -92,   3240}, {0xad, 0xcb, 0x4f, 0xff}}},
    {{{    54,      2,     87}, 0, {  -344,   2534}, {0xf3, 0x01, 0x7e, 0xff}}},
    {{{    64,     41,     62}, 0, {  -112,   2410}, {0xf3, 0x4e, 0x62, 0xff}}},
    {{{    10,     50,    -36}, 0, {   796,   3890}, {0xad, 0x48, 0xc2, 0xff}}},
    {{{    45,     75,    -31}, 0, {   758,   3032}, {0xf3, 0x69, 0xbb, 0xff}}},
    {{{    64,     41,    -62}, 0, {  1034,   2326}, {0x03, 0x47, 0x98, 0xff}}},
    {{{    15,     22,    -64}, 0, {  1060,   3604}, {0xae, 0x1d, 0xa5, 0xff}}},
    {{{    54,      2,    -87}, 0, {  1264,   2416}, {0xf1, 0x01, 0x82, 0xff}}},
    {{{    77,    -16,    -82}, 0, {  1220,   1700}, {0x2d, 0xe3, 0x8e, 0xff}}},
    {{{    43,    -44,    -54}, 0, {   964,   2536}, {0xe5, 0xa4, 0xae, 0xff}}},
};

// 0x060070F8
static const Vtx koopa_seg6_vertex_060070F8[] = {
    {{{    54,      2,    -87}, 0, {  1264,   2416}, {0xf1, 0x01, 0x82, 0xff}}},
    {{{    23,    -24,    -59}, 0, {  1012,   3160}, {0xac, 0xcc, 0xb2, 0xff}}},
    {{{    15,     22,    -64}, 0, {  1060,   3604}, {0xae, 0x1d, 0xa5, 0xff}}},
    {{{    92,     84,    -51}, 0, {   940,   1738}, {0xb8, 0x43, 0xb1, 0xff}}},
    {{{    92,     84,     51}, 0, {   -16,   1808}, {0xca, 0x3d, 0x60, 0xff}}},
    {{{    87,    114,      0}, 0, {   460,   2068}, {0xfc, 0x7e, 0x00, 0xff}}},
    {{{    64,     41,     62}, 0, {  -112,   2410}, {0xf3, 0x4e, 0x62, 0xff}}},
    {{{    64,     41,    -62}, 0, {  1034,   2326}, {0x03, 0x47, 0x98, 0xff}}},
    {{{    84,     81,      0}, 0, {   462,   2006}, {0x40, 0x6d, 0x00, 0xff}}},
    {{{    45,     75,     32}, 0, {   164,   3076}, {0xe3, 0x6a, 0x3e, 0xff}}},
    {{{    45,     75,    -31}, 0, {   758,   3032}, {0xf3, 0x69, 0xbb, 0xff}}},
    {{{    43,    -44,    -54}, 0, {   964,   2536}, {0xe5, 0xa4, 0xae, 0xff}}},
    {{{    77,    -16,    -82}, 0, {  1220,   1700}, {0x2d, 0xe3, 0x8e, 0xff}}},
    {{{   102,    -44,    -54}, 0, {   960,    882}, {0xf5, 0xaf, 0x9f, 0xff}}},
    {{{    36,     84,      0}, 0, {   462,   3348}, {0xd1, 0x75, 0xfa, 0xff}}},
};

// 0x060071E8
static const Vtx koopa_seg6_vertex_060071E8[] = {
    {{{    43,    -44,     54}, 0, {   -46,   2612}, {0xe6, 0xa4, 0x52, 0xff}}},
    {{{    77,    -16,     82}, 0, {  -300,   1812}, {0x2c, 0xe3, 0x73, 0xff}}},
    {{{    54,      2,     87}, 0, {  -344,   2534}, {0xf3, 0x01, 0x7e, 0xff}}},
    {{{    23,    -24,     60}, 0, {   -92,   3240}, {0xad, 0xcb, 0x4f, 0xff}}},
    {{{   102,    -44,     54}, 0, {   -42,    956}, {0xf4, 0xae, 0x60, 0xff}}},
    {{{    36,     84,      0}, 0, {   462,   3348}, {0xd1, 0x75, 0xfa, 0xff}}},
    {{{    45,     75,     32}, 0, {   164,   3076}, {0xe3, 0x6a, 0x3e, 0xff}}},
    {{{    84,     81,      0}, 0, {   462,   2006}, {0x40, 0x6d, 0x00, 0xff}}},
};

// 0x06007268
static const Vtx koopa_seg6_vertex_06007268[] = {
    {{{    92,     84,    -51}, 0, {    48,   1042}, {0xb8, 0x43, 0xb1, 0xff}}},
    {{{   140,     72,    -49}, 0, {    80,    596}, {0x45, 0x33, 0xa3, 0xff}}},
    {{{    99,     22,    -60}, 0, {  -128,    110}, {0x30, 0x0e, 0x8c, 0xff}}},
    {{{   153,     27,    -31}, 0, {   388,   -146}, {0x5a, 0xd7, 0xb2, 0xff}}},
    {{{   149,     99,      0}, 0, {   988,    924}, {0x56, 0x5c, 0x00, 0xff}}},
    {{{   170,     42,      0}, 0, {   968,    -30}, {0x7e, 0xf8, 0x00, 0xff}}},
    {{{    87,    114,      0}, 0, {  1000,   1504}, {0xfc, 0x7e, 0x00, 0xff}}},
    {{{   140,     72,     49}, 0, {  1884,    574}, {0x45, 0x33, 0x5d, 0xff}}},
    {{{   153,     27,     31}, 0, {  1544,   -162}, {0x5a, 0xd8, 0x4e, 0xff}}},
    {{{   139,     -4,      0}, 0, {   960,   -544}, {0x76, 0x2d, 0x00, 0xff}}},
    {{{    92,     84,     51}, 0, {  1932,   1018}, {0xca, 0x3d, 0x60, 0xff}}},
    {{{    99,     22,     60}, 0, {  2076,     82}, {0x2f, 0x10, 0x74, 0xff}}},
    {{{   111,      2,     52}, 0, {  1908,   -278}, {0x39, 0x0e, 0x70, 0xff}}},
    {{{    77,    -16,    -82}, 0, {  -536,   -332}, {0x2d, 0xe3, 0x8e, 0xff}}},
    {{{   111,      2,    -51}, 0, {    20,   -254}, {0x39, 0x0d, 0x90, 0xff}}},
};

// 0x06007358
static const Vtx koopa_seg6_vertex_06007358[] = {
    {{{   111,      2,     52}, 0, {  1908,   -278}, {0x39, 0x0e, 0x70, 0xff}}},
    {{{    77,    -16,     82}, 0, {  2464,   -370}, {0x2c, 0xe3, 0x73, 0xff}}},
    {{{   102,    -44,     54}, 0, {  1944,   -924}, {0xf4, 0xae, 0x60, 0xff}}},
    {{{    99,     22,     60}, 0, {  2076,     82}, {0x2f, 0x10, 0x74, 0xff}}},
    {{{   111,      2,    -51}, 0, {    20,   -254}, {0x39, 0x0d, 0x90, 0xff}}},
    {{{   153,     27,    -31}, 0, {   388,   -146}, {0x5a, 0xd7, 0xb2, 0xff}}},
    {{{   139,     -4,      0}, 0, {   960,   -544}, {0x76, 0x2d, 0x00, 0xff}}},
    {{{    92,     84,     51}, 0, {  1932,   1018}, {0xca, 0x3d, 0x60, 0xff}}},
    {{{    64,     41,     62}, 0, {  2116,    556}, {0xf3, 0x4e, 0x62, 0xff}}},
    {{{    54,      2,     87}, 0, {  2560,     40}, {0xf3, 0x01, 0x7e, 0xff}}},
    {{{   102,    -44,    -54}, 0, {   -36,   -900}, {0xf5, 0xaf, 0x9f, 0xff}}},
    {{{    77,    -16,    -82}, 0, {  -536,   -332}, {0x2d, 0xe3, 0x8e, 0xff}}},
    {{{    99,     22,    -60}, 0, {  -128,    110}, {0x30, 0x0e, 0x8c, 0xff}}},
    {{{    54,      2,    -87}, 0, {  -616,     80}, {0xf1, 0x01, 0x82, 0xff}}},
    {{{    64,     41,    -62}, 0, {  -148,    586}, {0x03, 0x47, 0x98, 0xff}}},
    {{{    92,     84,    -51}, 0, {    48,   1042}, {0xb8, 0x43, 0xb1, 0xff}}},
};

// 0x06007458
static const Vtx koopa_seg6_vertex_06007458[] = {
    {{{    15,     22,    -64}, 0, {  -200,    590}, {0xae, 0x1d, 0xa5, 0xff}}},
    {{{    64,     41,    -62}, 0, {  -148,    586}, {0x03, 0x47, 0x98, 0xff}}},
    {{{    54,      2,    -87}, 0, {  -616,     80}, {0xf1, 0x01, 0x82, 0xff}}},
};

// 0x06007488
static const Vtx koopa_seg6_vertex_06007488[] = {
    {{{    64,     41,    -62}, 0, {     0,      0}, {0x03, 0x47, 0x98, 0xff}}},
    {{{    84,     81,      0}, 0, {     0,      0}, {0x40, 0x6d, 0x00, 0xff}}},
    {{{    64,     41,     62}, 0, {     0,      0}, {0xf3, 0x4e, 0x62, 0xff}}},
};

// 0x060074B8
static const Vtx koopa_seg6_vertex_060074B8[] = {
    {{{   111,      2,     52}, 0, {     0,      0}, {0x39, 0x0e, 0x70, 0xff}}},
    {{{   102,    -44,     54}, 0, {     0,      0}, {0xf4, 0xae, 0x60, 0xff}}},
    {{{   120,    -30,     64}, 0, {     0,      0}, {0xf6, 0x07, 0x7e, 0xff}}},
    {{{   184,    -56,    -23}, 0, {     0,      0}, {0x6f, 0x23, 0xcf, 0xff}}},
    {{{   180,    -81,    -29}, 0, {     0,      0}, {0x4c, 0xac, 0xc8, 0xff}}},
    {{{   139,    -51,    -58}, 0, {     0,      0}, {0x24, 0xe1, 0x8b, 0xff}}},
    {{{   102,    -44,    -54}, 0, {     0,      0}, {0xf5, 0xaf, 0x9f, 0xff}}},
    {{{   147,    -93,      0}, 0, {     0,      0}, {0xf9, 0x82, 0x00, 0xff}}},
    {{{   120,    -30,    -63}, 0, {     0,      0}, {0x01, 0x09, 0x82, 0xff}}},
    {{{   111,      2,    -51}, 0, {     0,      0}, {0x39, 0x0d, 0x90, 0xff}}},
    {{{   155,    -27,    -48}, 0, {     0,      0}, {0x51, 0x2e, 0xab, 0xff}}},
    {{{   180,    -81,     29}, 0, {     0,      0}, {0x36, 0xa3, 0x42, 0xff}}},
    {{{   139,    -51,     58}, 0, {     0,      0}, {0x27, 0xe7, 0x76, 0xff}}},
    {{{   184,    -56,     23}, 0, {     0,      0}, {0x75, 0x18, 0x29, 0xff}}},
    {{{   155,    -27,     49}, 0, {     0,      0}, {0x57, 0x36, 0x49, 0xff}}},
    {{{   133,    -11,     56}, 0, {     0,      0}, {0x39, 0x41, 0x5c, 0xff}}},
};

// 0x060075B8
static const Vtx koopa_seg6_vertex_060075B8[] = {
    {{{   111,      2,    -51}, 0, {     0,      0}, {0x39, 0x0d, 0x90, 0xff}}},
    {{{   133,    -11,    -56}, 0, {     0,      0}, {0x38, 0x4e, 0xae, 0xff}}},
    {{{   120,    -30,    -63}, 0, {     0,      0}, {0x01, 0x09, 0x82, 0xff}}},
    {{{   155,    -27,    -48}, 0, {     0,      0}, {0x51, 0x2e, 0xab, 0xff}}},
    {{{   184,    -56,     23}, 0, {     0,      0}, {0x75, 0x18, 0x29, 0xff}}},
    {{{   180,    -81,     29}, 0, {     0,      0}, {0x36, 0xa3, 0x42, 0xff}}},
    {{{   180,    -81,    -29}, 0, {     0,      0}, {0x4c, 0xac, 0xc8, 0xff}}},
    {{{   139,    -51,     58}, 0, {     0,      0}, {0x27, 0xe7, 0x76, 0xff}}},
    {{{   184,    -56,    -23}, 0, {     0,      0}, {0x6f, 0x23, 0xcf, 0xff}}},
    {{{   147,    -93,      0}, 0, {     0,      0}, {0xf9, 0x82, 0x00, 0xff}}},
};

// 0x06007658 - 0x060076B0
const Gfx koopa_seg6_dl_06007658[] = {
    gsSPLight(&koopa_seg6_lights_06006E48.l, 1),
    gsSPLight(&koopa_seg6_lights_06006E48.a, 2),
    gsSPVertex(koopa_seg6_vertex_06006E78, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 0,  5,  3, 0x0,  4,  6,  0, 0x0),
    gsSP2Triangles( 7,  5,  0, 0x0,  8,  1,  0, 0x0),
    gsSP1Triangle( 0,  6,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x060076B0 - 0x06007850
const Gfx koopa_seg6_dl_060076B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, koopa_seg6_texture_06005648),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(koopa_seg6_vertex_06006F08, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  5,  7, 0x0,  5,  6,  8, 0x0),
    gsSP2Triangles( 3,  5,  8, 0x0,  7,  5,  4, 0x0),
    gsSP2Triangles( 9,  6, 10, 0x0,  7, 10,  6, 0x0),
    gsSP2Triangles( 9,  8,  6, 0x0,  8, 11,  3, 0x0),
    gsSP2Triangles(11,  8,  9, 0x0,  3, 11, 12, 0x0),
    gsSP2Triangles( 2,  3, 12, 0x0,  3, 13,  4, 0x0),
    gsSP2Triangles(13,  3,  2, 0x0,  2,  1, 13, 0x0),
    gsSP1Triangle(14,  0,  2, 0x0),
    gsSPVertex(koopa_seg6_vertex_06006FF8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 3,  5,  4, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 7,  8,  5, 0x0,  4,  5,  8, 0x0),
    gsSP2Triangles( 6,  5,  3, 0x0,  0,  4,  1, 0x0),
    gsSP2Triangles( 8,  1,  4, 0x0,  2,  9,  0, 0x0),
    gsSP2Triangles( 2, 10,  9, 0x0,  9, 11, 12, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(koopa_seg6_vertex_060070F8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  3,  7,  6, 0x0),
    gsSP2Triangles( 6,  8,  9, 0x0, 10,  8,  7, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0,  0, 11,  1, 0x0),
    gsSP1Triangle(14,  8, 10, 0x0),
    gsSPVertex(koopa_seg6_vertex_060071E8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 1,  0,  4, 0x0,  5,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x06007850 - 0x06007970
const Gfx koopa_seg6_dl_06007850[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, koopa_seg6_texture_06005E48),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(koopa_seg6_vertex_06007268, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  1, 0x0),
    gsSP2Triangles( 1,  4,  5, 0x0,  1,  0,  6, 0x0),
    gsSP2Triangles( 6,  4,  1, 0x0,  5,  3,  1, 0x0),
    gsSP2Triangles( 7,  4,  6, 0x0,  7,  5,  4, 0x0),
    gsSP2Triangles( 7,  8,  5, 0x0,  3,  5,  9, 0x0),
    gsSP2Triangles( 9,  5,  8, 0x0,  7,  6, 10, 0x0),
    gsSP2Triangles(11,  7, 10, 0x0,  8,  7, 11, 0x0),
    gsSP2Triangles(12,  9,  8, 0x0,  8, 11, 12, 0x0),
    gsSP1Triangle(13,  2, 14, 0x0),
    gsSPVertex(koopa_seg6_vertex_06007358, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  3, 0x0),
    gsSP2Triangles( 9,  1,  3, 0x0,  9,  3,  8, 0x0),
    gsSP2Triangles( 4, 10, 11, 0x0,  4, 12,  5, 0x0),
    gsSP2Triangles(13, 14, 12, 0x0, 14, 15, 12, 0x0),
    gsSP1Triangle(12, 11, 13, 0x0),
    gsSPVertex(koopa_seg6_vertex_06007458, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06007970 - 0x06007A60
const Gfx koopa_seg6_dl_06007970[] = {
    gsSPLight(&koopa_seg6_lights_06006E60.l, 1),
    gsSPLight(&koopa_seg6_lights_06006E60.a, 2),
    gsSPVertex(koopa_seg6_vertex_06007488, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPLight(&koopa_seg6_lights_06006E48.l, 1),
    gsSPLight(&koopa_seg6_lights_06006E48.a, 2),
    gsSPVertex(koopa_seg6_vertex_060074B8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  4,  7, 0x0,  4,  6,  5, 0x0),
    gsSP2Triangles( 6,  8,  5, 0x0,  8,  6,  9, 0x0),
    gsSP2Triangles( 5, 10,  3, 0x0,  8, 10,  5, 0x0),
    gsSP2Triangles(11, 12,  1, 0x0, 12,  2,  1, 0x0),
    gsSP2Triangles( 1,  7, 11, 0x0, 13, 14, 12, 0x0),
    gsSP2Triangles(12, 15,  2, 0x0, 12, 14, 15, 0x0),
    gsSP1Triangle( 0,  2, 15, 0x0),
    gsSPVertex(koopa_seg6_vertex_060075B8, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  5,  4, 0x0),
    gsSP2Triangles( 6,  8,  4, 0x0,  9,  6,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x06007A60 - 0x06007AA0
const Gfx koopa_seg6_dl_06007A60[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x06007AA0 - 0x06007AF8
const Gfx koopa_seg6_dl_06007AA0[] = {
    gsSPDisplayList(koopa_seg6_dl_06007658),
    gsSPDisplayList(koopa_seg6_dl_060076B0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(koopa_seg6_dl_06007850),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(koopa_seg6_dl_06007970),
    gsSPEndDisplayList(),
};

// 0x06007AF8 - 0x06007B20
const Gfx koopa_seg6_dl_06007AF8[] = {
    gsSPDisplayList(koopa_seg6_dl_06007A60),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, koopa_seg6_texture_06004648),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPBranchList(koopa_seg6_dl_06007AA0),
};

// 0x06007B20 - 0x06007B48
const Gfx koopa_seg6_dl_06007B20[] = {
    gsSPDisplayList(koopa_seg6_dl_06007A60),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, koopa_seg6_texture_06004E48),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPBranchList(koopa_seg6_dl_06007AA0),
};

// 0x06007B48
static const Lights1 koopa_seg6_lights_06007B48 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06007B60
static const Lights1 koopa_seg6_lights_06007B60 = gdSPDefLights1(
    0x70, 0x57, 0x00,
    0xe0, 0xae, 0x00, 0x28, 0x28, 0x28
);

// 0x06007B78
static const Vtx koopa_seg6_vertex_06007B78[] = {
    {{{   -28,    -79,    -33}, 0, {   774,    814}, {0xc7, 0x96, 0xda, 0xff}}},
    {{{   -84,    -15,      0}, 0, {   468,   1346}, {0xa0, 0xae, 0x00, 0xff}}},
    {{{   -56,      0,    -71}, 0, {  1132,   1060}, {0xce, 0xb0, 0xac, 0xff}}},
    {{{    35,    -89,    -33}, 0, {   770,    152}, {0x12, 0x97, 0xbd, 0xff}}},
    {{{   -28,    -79,     33}, 0, {   158,    814}, {0xbe, 0x9f, 0x2f, 0xff}}},
    {{{    35,    -89,     33}, 0, {   156,    152}, {0x0b, 0x8f, 0x36, 0xff}}},
    {{{   -56,      0,     73}, 0, {  -196,   1060}, {0xce, 0xaf, 0x53, 0xff}}},
    {{{    63,    -28,    -76}, 0, {  1164,   -156}, {0x13, 0xae, 0xa2, 0xff}}},
    {{{    91,    -63,      0}, 0, {   462,   -438}, {0x39, 0x8f, 0x00, 0xff}}},
    {{{    63,    -28,     79}, 0, {  -236,   -154}, {0x15, 0xaf, 0x5e, 0xff}}},
};

// 0x06007C18
static const Vtx koopa_seg6_vertex_06007C18[] = {
    {{{    68,     63,     40}, 0, {   826,     98}, {0x2c, 0x72, 0x1f, 0xff}}},
    {{{   109,     38,      0}, 0, {   524,   -122}, {0x6d, 0x40, 0x00, 0xff}}},
    {{{    68,     63,    -38}, 0, {   212,    100}, {0x33, 0x6d, 0xda, 0xff}}},
    {{{   -40,     71,    -38}, 0, {   196,    712}, {0xdd, 0x74, 0xdd, 0xff}}},
    {{{   -40,     71,     40}, 0, {   810,    710}, {0xe3, 0x71, 0x31, 0xff}}},
    {{{    91,     28,     61}, 0, {   990,    -24}, {0x5c, 0x3f, 0x3a, 0xff}}},
    {{{    51,     43,     79}, 0, {  1120,    194}, {0x1b, 0x5f, 0x4e, 0xff}}},
    {{{    51,     43,    -76}, 0, {   -88,    190}, {0x1b, 0x61, 0xb4, 0xff}}},
    {{{    91,     28,    -58}, 0, {    52,    -26}, {0x5c, 0x41, 0xc7, 0xff}}},
    {{{   -68,     38,     56}, 0, {   932,    864}, {0xab, 0x53, 0x2a, 0xff}}},
    {{{   -68,     38,    -56}, 0, {    58,    862}, {0xaf, 0x54, 0xcf, 0xff}}},
    {{{   -25,     43,    -76}, 0, {   -98,    628}, {0xe7, 0x5d, 0xaf, 0xff}}},
    {{{   -81,     25,      0}, 0, {   492,    932}, {0x99, 0x49, 0x00, 0xff}}},
    {{{   -25,     43,     79}, 0, {  1104,    632}, {0xe4, 0x5a, 0x54, 0xff}}},
};

// 0x06007CF8
static const Vtx koopa_seg6_vertex_06007CF8[] = {
    {{{   122,    -43,    -30}, 0, {     0,      0}, {0x6a, 0xd5, 0xcc, 0xff}}},
    {{{    91,     28,    -58}, 0, {     0,      0}, {0x5c, 0x41, 0xc7, 0xff}}},
    {{{   109,     38,      0}, 0, {     0,      0}, {0x6d, 0x40, 0x00, 0xff}}},
    {{{   122,    -43,     30}, 0, {     0,      0}, {0x6b, 0xd5, 0x34, 0xff}}},
    {{{    91,     28,     61}, 0, {     0,      0}, {0x5c, 0x3f, 0x3a, 0xff}}},
};

// 0x06007D48
static const Vtx koopa_seg6_vertex_06007D48[] = {
    {{{   -56,      0,    -71}, 0, {     0,      0}, {0xce, 0xb0, 0xac, 0xff}}},
    {{{   -68,     17,    -84}, 0, {     0,      0}, {0x9d, 0x10, 0xb4, 0xff}}},
    {{{     0,     22,   -107}, 0, {     0,      0}, {0xee, 0x19, 0x85, 0xff}}},
    {{{  -107,      5,      0}, 0, {     0,      0}, {0x88, 0xd8, 0x00, 0xff}}},
    {{{   -81,     25,      0}, 0, {     0,      0}, {0x99, 0x49, 0x00, 0xff}}},
    {{{   -68,     38,    -56}, 0, {     0,      0}, {0xaf, 0x54, 0xcf, 0xff}}},
    {{{   -68,     17,     86}, 0, {     0,      0}, {0x9d, 0x12, 0x4c, 0xff}}},
    {{{   -68,     38,     56}, 0, {     0,      0}, {0xab, 0x53, 0x2a, 0xff}}},
    {{{   -25,     43,    -76}, 0, {     0,      0}, {0xe7, 0x5d, 0xaf, 0xff}}},
    {{{    63,    -28,    -76}, 0, {     0,      0}, {0x13, 0xae, 0xa2, 0xff}}},
    {{{    79,     10,    -94}, 0, {     0,      0}, {0x43, 0x11, 0x96, 0xff}}},
    {{{   122,    -43,    -30}, 0, {     0,      0}, {0x6a, 0xd5, 0xcc, 0xff}}},
    {{{    91,    -63,      0}, 0, {     0,      0}, {0x39, 0x8f, 0x00, 0xff}}},
    {{{   122,    -43,     30}, 0, {     0,      0}, {0x6b, 0xd5, 0x34, 0xff}}},
    {{{    91,     28,    -58}, 0, {     0,      0}, {0x5c, 0x41, 0xc7, 0xff}}},
};

// 0x06007E38
static const Vtx koopa_seg6_vertex_06007E38[] = {
    {{{  -107,      5,      0}, 0, {     0,      0}, {0x88, 0xd8, 0x00, 0xff}}},
    {{{   -84,    -15,      0}, 0, {     0,      0}, {0xa0, 0xae, 0x00, 0xff}}},
    {{{   -56,      0,     73}, 0, {     0,      0}, {0xce, 0xaf, 0x53, 0xff}}},
    {{{    51,     43,    -76}, 0, {     0,      0}, {0x1b, 0x61, 0xb4, 0xff}}},
    {{{    91,     28,    -58}, 0, {     0,      0}, {0x5c, 0x41, 0xc7, 0xff}}},
    {{{    79,     10,    -94}, 0, {     0,      0}, {0x43, 0x11, 0x96, 0xff}}},
    {{{     0,     22,   -107}, 0, {     0,      0}, {0xee, 0x19, 0x85, 0xff}}},
    {{{   -25,     43,    -76}, 0, {     0,      0}, {0xe7, 0x5d, 0xaf, 0xff}}},
    {{{   -56,      0,    -71}, 0, {     0,      0}, {0xce, 0xb0, 0xac, 0xff}}},
    {{{    63,    -28,     79}, 0, {     0,      0}, {0x15, 0xaf, 0x5e, 0xff}}},
    {{{    91,    -63,      0}, 0, {     0,      0}, {0x39, 0x8f, 0x00, 0xff}}},
    {{{   122,    -43,     30}, 0, {     0,      0}, {0x6b, 0xd5, 0x34, 0xff}}},
    {{{   -68,     17,     86}, 0, {     0,      0}, {0x9d, 0x12, 0x4c, 0xff}}},
    {{{    91,     28,     61}, 0, {     0,      0}, {0x5c, 0x3f, 0x3a, 0xff}}},
    {{{    79,     10,     94}, 0, {     0,      0}, {0x42, 0x10, 0x6a, 0xff}}},
};

// 0x06007F28
static const Vtx koopa_seg6_vertex_06007F28[] = {
    {{{   -68,     38,     56}, 0, {     0,      0}, {0xab, 0x53, 0x2a, 0xff}}},
    {{{   -68,     17,     86}, 0, {     0,      0}, {0x9d, 0x12, 0x4c, 0xff}}},
    {{{     0,     22,    107}, 0, {     0,      0}, {0xef, 0x17, 0x7b, 0xff}}},
    {{{   -25,     43,     79}, 0, {     0,      0}, {0xe4, 0x5a, 0x54, 0xff}}},
    {{{   -56,      0,     73}, 0, {     0,      0}, {0xce, 0xaf, 0x53, 0xff}}},
    {{{    63,    -28,     79}, 0, {     0,      0}, {0x15, 0xaf, 0x5e, 0xff}}},
    {{{    51,     43,     79}, 0, {     0,      0}, {0x1b, 0x5f, 0x4e, 0xff}}},
    {{{    79,     10,     94}, 0, {     0,      0}, {0x42, 0x10, 0x6a, 0xff}}},
    {{{    91,     28,     61}, 0, {     0,      0}, {0x5c, 0x3f, 0x3a, 0xff}}},
};

// 0x06007FB8 - 0x06008050
const Gfx koopa_seg6_dl_06007FB8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, koopa_seg6_texture_06002E48),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&koopa_seg6_lights_06007B48.l, 1),
    gsSPLight(&koopa_seg6_lights_06007B48.a, 2),
    gsSPVertex(koopa_seg6_vertex_06007B78, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  0,  5,  4, 0x0),
    gsSP2Triangles( 0,  3,  5, 0x0,  1,  4,  6, 0x0),
    gsSP2Triangles( 6,  4,  5, 0x0,  3,  2,  7, 0x0),
    gsSP2Triangles( 3,  8,  5, 0x0,  8,  3,  7, 0x0),
    gsSP2Triangles( 9,  5,  8, 0x0,  9,  6,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x06008050 - 0x060080F8
const Gfx koopa_seg6_dl_06008050[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, koopa_seg6_texture_06002648),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(koopa_seg6_vertex_06007C18, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 0,  2,  3, 0x0,  1,  0,  5, 0x0),
    gsSP2Triangles( 4,  6,  0, 0x0,  6,  5,  0, 0x0),
    gsSP2Triangles( 3,  2,  7, 0x0,  8,  2,  1, 0x0),
    gsSP2Triangles( 2,  8,  7, 0x0,  9,  4,  3, 0x0),
    gsSP2Triangles( 3, 10,  9, 0x0,  7, 11,  3, 0x0),
    gsSP2Triangles(11, 10,  3, 0x0, 10, 12,  9, 0x0),
    gsSP2Triangles( 4,  9, 13, 0x0,  4, 13,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x060080F8 - 0x06008250
const Gfx koopa_seg6_dl_060080F8[] = {
    gsSPLight(&koopa_seg6_lights_06007B60.l, 1),
    gsSPLight(&koopa_seg6_lights_06007B60.a, 2),
    gsSPVertex(koopa_seg6_vertex_06007CF8, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP1Triangle( 2,  4,  3, 0x0),
    gsSPLight(&koopa_seg6_lights_06007B48.l, 1),
    gsSPLight(&koopa_seg6_lights_06007B48.a, 2),
    gsSPVertex(koopa_seg6_vertex_06007D48, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  1,  3, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 1,  4,  5, 0x0,  4,  3,  6, 0x0),
    gsSP2Triangles( 7,  4,  6, 0x0,  2,  5,  8, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0, 11, 12,  9, 0x0),
    gsSP2Triangles(12, 11, 13, 0x0, 11, 10, 14, 0x0),
    gsSP2Triangles( 9,  2, 10, 0x0,  2,  9,  0, 0x0),
    gsSPVertex(koopa_seg6_vertex_06007E38, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  6,  7,  3, 0x0),
    gsSP2Triangles( 0,  8,  1, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 2, 12,  0, 0x0, 13, 14, 11, 0x0),
    gsSP1Triangle(11, 14,  9, 0x0),
    gsSPVertex(koopa_seg6_vertex_06007F28, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  4,  5,  2, 0x0),
    gsSP2Triangles( 6,  2,  7, 0x0,  6,  3,  2, 0x0),
    gsSP2Triangles( 7,  2,  5, 0x0,  6,  7,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x06008250 - 0x060082C0
const Gfx koopa_seg6_dl_06008250[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(koopa_seg6_dl_06007FB8),
    gsSPDisplayList(koopa_seg6_dl_06008050),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(koopa_seg6_dl_060080F8),
    gsSPEndDisplayList(),
};

// 0x060082C0
static const Lights1 koopa_seg6_lights_060082C0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x060082D8
static const Lights1 koopa_seg6_lights_060082D8 = gdSPDefLights1(
    0x00, 0x64, 0x00,
    0x00, 0xc8, 0x00, 0x28, 0x28, 0x28
);

// 0x060082F0
static const Vtx koopa_seg6_vertex_060082F0[] = {
    {{{     2,      5,     30}, 0, {  2880,    262}, {0xc7, 0x1e, 0x6d, 0xff}}},
    {{{     0,    -45,     43}, 0, {  1052,    144}, {0xd7, 0xd7, 0x70, 0xff}}},
    {{{    28,    -45,     43}, 0, {  1040,    916}, {0x59, 0xf7, 0x59, 0xff}}},
    {{{    28,    -73,     20}, 0, {   140,    922}, {0x2b, 0x9e, 0x43, 0xff}}},
    {{{     2,    -73,     20}, 0, {   134,    272}, {0xd2, 0x8f, 0x20, 0xff}}},
    {{{    28,    -73,    -25}, 0, {   140,    922}, {0x4a, 0x9e, 0xe4, 0xff}}},
    {{{    28,      7,     38}, 0, {  2916,    906}, {0x4e, 0x2b, 0x59, 0xff}}},
    {{{     2,    -73,    -25}, 0, {   134,    272}, {0xd9, 0x9a, 0xc0, 0xff}}},
    {{{    28,    -45,    -48}, 0, {  1040,    916}, {0x5a, 0xdd, 0xaf, 0xff}}},
    {{{     0,    -45,    -45}, 0, {  1052,    144}, {0xc5, 0xf4, 0x91, 0xff}}},
    {{{    28,      7,    -43}, 0, {  2916,    906}, {0x4e, 0x21, 0xa3, 0xff}}},
    {{{     2,      5,    -33}, 0, {  2880,    262}, {0xc8, 0x33, 0x9b, 0xff}}},
    {{{     0,     40,    -10}, 0, {  4032,    248}, {0xc8, 0x67, 0xd1, 0xff}}},
    {{{    28,     40,    -15}, 0, {  4040,    898}, {0x17, 0x64, 0xb7, 0xff}}},
    {{{    28,     40,     12}, 0, {  4040,    898}, {0x45, 0x65, 0x1e, 0xff}}},
    {{{     0,     40,      5}, 0, {  4032,    248}, {0xb1, 0x52, 0x37, 0xff}}},
};

// 0x060083F0
static const Vtx koopa_seg6_vertex_060083F0[] = {
    {{{    28,      7,     38}, 0, {   938,    710}, {0x4e, 0x2b, 0x59, 0xff}}},
    {{{    28,    -45,    -48}, 0, {   -52,    240}, {0x5a, 0xdd, 0xaf, 0xff}}},
    {{{    28,      7,    -43}, 0, {    14,    710}, {0x4e, 0x21, 0xa3, 0xff}}},
    {{{    28,     40,    -15}, 0, {   304,    990}, {0x17, 0x64, 0xb7, 0xff}}},
    {{{    28,     40,     12}, 0, {   650,    990}, {0x45, 0x65, 0x1e, 0xff}}},
    {{{    28,    -45,     43}, 0, {  1008,    240}, {0x59, 0xf7, 0x59, 0xff}}},
    {{{    28,    -73,    -25}, 0, {   212,     16}, {0x4a, 0x9e, 0xe4, 0xff}}},
    {{{    28,    -73,     20}, 0, {   742,     16}, {0x2b, 0x9e, 0x43, 0xff}}},
};

// 0x06008470
static const Vtx koopa_seg6_vertex_06008470[] = {
    {{{     0,     40,      5}, 0, {     0,      0}, {0xb1, 0x52, 0x37, 0xff}}},
    {{{     0,     40,    -10}, 0, {     0,      0}, {0xc8, 0x67, 0xd1, 0xff}}},
    {{{   -22,    -20,    -20}, 0, {     0,      0}, {0x90, 0x22, 0xd1, 0xff}}},
    {{{   -22,    -20,     15}, 0, {     0,      0}, {0x8c, 0x1c, 0x2a, 0xff}}},
    {{{     2,      5,     30}, 0, {     0,      0}, {0xc7, 0x1e, 0x6d, 0xff}}},
    {{{     2,      5,    -33}, 0, {     0,      0}, {0xc8, 0x33, 0x9b, 0xff}}},
    {{{   -28,    -48,    -15}, 0, {     0,      0}, {0x8d, 0xd7, 0xe0, 0xff}}},
    {{{     0,    -45,    -45}, 0, {     0,      0}, {0xc5, 0xf4, 0x91, 0xff}}},
    {{{   -28,    -48,     10}, 0, {     0,      0}, {0x97, 0xe2, 0x3f, 0xff}}},
    {{{     0,    -45,     43}, 0, {     0,      0}, {0xd7, 0xd7, 0x70, 0xff}}},
    {{{     2,    -73,    -25}, 0, {     0,      0}, {0xd9, 0x9a, 0xc0, 0xff}}},
    {{{     2,    -73,     20}, 0, {     0,      0}, {0xd2, 0x8f, 0x20, 0xff}}},
};

// 0x06008530 - 0x060085E8
const Gfx koopa_seg6_dl_06008530[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, koopa_seg6_texture_06003648),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&koopa_seg6_lights_060082C0.l, 1),
    gsSPLight(&koopa_seg6_lights_060082C0.a, 2),
    gsSPVertex(koopa_seg6_vertex_060082F0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 1,  4,  3, 0x0,  4,  5,  3, 0x0),
    gsSP2Triangles( 0,  2,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  5,  7, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 9, 10,  8, 0x0,  9, 11, 10, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0, 11, 13, 10, 0x0),
    gsSP2Triangles(12, 14, 13, 0x0, 12, 15, 14, 0x0),
    gsSP2Triangles( 6, 14, 15, 0x0,  6, 15,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x060085E8 - 0x06008640
const Gfx koopa_seg6_dl_060085E8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, koopa_seg6_texture_06003E48),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(koopa_seg6_vertex_060083F0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 5,  6,  1, 0x0,  5,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x06008640 - 0x060086D0
const Gfx koopa_seg6_dl_06008640[] = {
    gsSPLight(&koopa_seg6_lights_060082D8.l, 1),
    gsSPLight(&koopa_seg6_lights_060082D8.a, 2),
    gsSPVertex(koopa_seg6_vertex_06008470, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 3,  4,  0, 0x0,  1,  5,  2, 0x0),
    gsSP2Triangles( 3,  2,  6, 0x0,  7,  6,  2, 0x0),
    gsSP2Triangles( 7,  2,  5, 0x0,  3,  6,  8, 0x0),
    gsSP2Triangles( 4,  3,  8, 0x0,  4,  8,  9, 0x0),
    gsSP2Triangles( 6, 10, 11, 0x0,  7, 10,  6, 0x0),
    gsSP2Triangles( 6, 11,  8, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x060086D0 - 0x06008740
const Gfx koopa_seg6_dl_060086D0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(koopa_seg6_dl_06008530),
    gsSPDisplayList(koopa_seg6_dl_060085E8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(koopa_seg6_dl_06008640),
    gsSPEndDisplayList(),
};

// 0x06008740
static const Lights1 koopa_seg6_lights_06008740 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06008758
static const Lights1 koopa_seg6_lights_06008758 = gdSPDefLights1(
    0x00, 0x64, 0x00,
    0x00, 0xc8, 0x00, 0x28, 0x28, 0x28
);

// 0x06008770
static const Vtx koopa_seg6_vertex_06008770[] = {
    {{{    28,      7,    -35}, 0, {   648,    906}, {0x50, 0x21, 0xa4, 0xff}}},
    {{{    28,    -45,    -43}, 0, {   216,    916}, {0x5b, 0xdb, 0xb0, 0xff}}},
    {{{     0,    -45,    -40}, 0, {   218,    144}, {0xc6, 0xf4, 0x91, 0xff}}},
    {{{     2,    -73,    -17}, 0, {     6,    272}, {0xdb, 0x98, 0xc4, 0xff}}},
    {{{    28,    -73,    -17}, 0, {     8,    922}, {0x4a, 0x9d, 0xe5, 0xff}}},
    {{{     2,    -73,     25}, 0, {     6,    272}, {0xd3, 0x8f, 0x21, 0xff}}},
    {{{     2,      5,    -28}, 0, {   640,    262}, {0xc9, 0x32, 0x9a, 0xff}}},
    {{{    28,    -73,     25}, 0, {     8,    922}, {0x28, 0x9c, 0x42, 0xff}}},
    {{{     0,    -45,     48}, 0, {   218,    144}, {0xd4, 0xd7, 0x6f, 0xff}}},
    {{{    28,    -45,     51}, 0, {   216,    916}, {0x57, 0xf6, 0x5b, 0xff}}},
    {{{     2,      5,     35}, 0, {   640,    262}, {0xc6, 0x20, 0x6b, 0xff}}},
    {{{    28,      7,     43}, 0, {   648,    906}, {0x4f, 0x2b, 0x59, 0xff}}},
    {{{    28,     40,     17}, 0, {   908,    898}, {0x47, 0x64, 0x1e, 0xff}}},
    {{{     0,     40,     12}, 0, {   906,    248}, {0xb2, 0x51, 0x39, 0xff}}},
    {{{     0,     40,     -5}, 0, {   906,    248}, {0xc7, 0x67, 0xd2, 0xff}}},
    {{{    28,     40,    -10}, 0, {   908,    898}, {0x1a, 0x63, 0xb6, 0xff}}},
};

// 0x06008870
static const Vtx koopa_seg6_vertex_06008870[] = {
    {{{    28,      7,     43}, 0, {   846,    710}, {0x4f, 0x2b, 0x59, 0xff}}},
    {{{    28,    -45,    -43}, 0, {   -46,    240}, {0x5b, 0xdb, 0xb0, 0xff}}},
    {{{    28,      7,    -35}, 0, {    14,    710}, {0x50, 0x21, 0xa4, 0xff}}},
    {{{    28,     40,    -10}, 0, {   274,    990}, {0x1a, 0x63, 0xb6, 0xff}}},
    {{{    28,     40,     17}, 0, {   586,    990}, {0x47, 0x64, 0x1e, 0xff}}},
    {{{    28,    -45,     51}, 0, {   908,    240}, {0x57, 0xf6, 0x5b, 0xff}}},
    {{{    28,    -73,    -17}, 0, {   192,     16}, {0x4a, 0x9d, 0xe5, 0xff}}},
    {{{    28,    -73,     25}, 0, {   670,     16}, {0x28, 0x9c, 0x42, 0xff}}},
};

// 0x060088F0
static const Vtx koopa_seg6_vertex_060088F0[] = {
    {{{   -22,    -20,    -12}, 0, {     0,      0}, {0x8f, 0x22, 0xd3, 0xff}}},
    {{{   -22,    -20,     20}, 0, {     0,      0}, {0x8c, 0x1e, 0x29, 0xff}}},
    {{{     0,     40,     12}, 0, {     0,      0}, {0xb2, 0x51, 0x39, 0xff}}},
    {{{   -28,    -48,    -10}, 0, {     0,      0}, {0x8d, 0xd7, 0xe1, 0xff}}},
    {{{     0,     40,     -5}, 0, {     0,      0}, {0xc7, 0x67, 0xd2, 0xff}}},
    {{{     0,    -45,    -40}, 0, {     0,      0}, {0xc6, 0xf4, 0x91, 0xff}}},
    {{{     2,      5,    -28}, 0, {     0,      0}, {0xc9, 0x32, 0x9a, 0xff}}},
    {{{     2,      5,     35}, 0, {     0,      0}, {0xc6, 0x20, 0x6b, 0xff}}},
    {{{   -28,    -48,     17}, 0, {     0,      0}, {0x96, 0xe5, 0x3f, 0xff}}},
    {{{     0,    -45,     48}, 0, {     0,      0}, {0xd4, 0xd7, 0x6f, 0xff}}},
    {{{     2,    -73,    -17}, 0, {     0,      0}, {0xdb, 0x98, 0xc4, 0xff}}},
    {{{     2,    -73,     25}, 0, {     0,      0}, {0xd3, 0x8f, 0x21, 0xff}}},
};

// 0x060089B0 - 0x06008A68
const Gfx koopa_seg6_dl_060089B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, koopa_seg6_texture_06003648),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&koopa_seg6_lights_06008740.l, 1),
    gsSPLight(&koopa_seg6_lights_06008740.a, 2),
    gsSPVertex(koopa_seg6_vertex_06008770, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 1,  4,  3, 0x0,  4,  5,  3, 0x0),
    gsSP2Triangles( 0,  2,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  5,  7, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 9, 10,  8, 0x0,  9, 11, 10, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0, 11, 13, 10, 0x0),
    gsSP2Triangles(12, 14, 13, 0x0, 12, 15, 14, 0x0),
    gsSP2Triangles( 6, 14, 15, 0x0,  6, 15,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x06008A68 - 0x06008AC0
const Gfx koopa_seg6_dl_06008A68[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, koopa_seg6_texture_06003E48),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(koopa_seg6_vertex_06008870, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 5,  6,  1, 0x0,  5,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x06008AC0 - 0x06008B50
const Gfx koopa_seg6_dl_06008AC0[] = {
    gsSPLight(&koopa_seg6_lights_06008758.l, 1),
    gsSPLight(&koopa_seg6_lights_06008758.a, 2),
    gsSPVertex(koopa_seg6_vertex_060088F0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  5,  0,  6, 0x0),
    gsSP2Triangles( 5,  3,  0, 0x0,  4,  6,  0, 0x0),
    gsSP2Triangles( 1,  7,  2, 0x0,  3,  8,  1, 0x0),
    gsSP2Triangles( 7,  1,  8, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 3, 10, 11, 0x0,  3, 11,  8, 0x0),
    gsSP2Triangles( 5, 10,  3, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x06008B50 - 0x06008BC0
const Gfx koopa_seg6_dl_06008B50[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(koopa_seg6_dl_060089B0),
    gsSPDisplayList(koopa_seg6_dl_06008A68),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(koopa_seg6_dl_06008AC0),
    gsSPEndDisplayList(),
};

// 0x06008BC0
static const Vtx koopa_seg6_vertex_06008BC0[] = {
    {{{    27,    -76,    -21}, 0, {     0,      0}, {0xff, 0x8d, 0xcb, 0x00}}},
    {{{    27,    -76,     26}, 0, {     0,      0}, {0xff, 0x8d, 0x35, 0x00}}},
    {{{     1,    -76,     26}, 0, {     0,      0}, {0xd2, 0x95, 0x2f, 0x00}}},
    {{{     1,    -76,    -21}, 0, {     0,      0}, {0xd2, 0x95, 0xd1, 0x00}}},
    {{{    26,     40,     17}, 0, {     0,      0}, {0xef, 0x70, 0x38, 0x00}}},
    {{{    26,     40,    -13}, 0, {     0,      0}, {0xef, 0x70, 0xc8, 0x00}}},
    {{{     1,     39,     -7}, 0, {     0,      0}, {0xb8, 0x5a, 0xcc, 0x00}}},
    {{{     1,     39,     11}, 0, {     0,      0}, {0xb8, 0x5a, 0x34, 0x00}}},
    {{{   -30,    -49,    -11}, 0, {     0,      0}, {0x91, 0xdb, 0xd0, 0x00}}},
    {{{   -30,    -49,     16}, 0, {     0,      0}, {0x91, 0xdb, 0x30, 0x00}}},
    {{{   -25,    -23,     21}, 0, {     0,      0}, {0x8f, 0x25, 0x2c, 0x00}}},
    {{{   -25,    -23,    -16}, 0, {     0,      0}, {0x8f, 0x25, 0xd4, 0x00}}},
    {{{    27,    -49,     49}, 0, {     0,      0}, {0xf2, 0xdf, 0x79, 0x00}}},
    {{{    27,      6,     43}, 0, {     0,      0}, {0xe6, 0x30, 0x72, 0x00}}},
    {{{     1,      5,     35}, 0, {     0,      0}, {0xc3, 0x2b, 0x66, 0x00}}},
    {{{    -3,    -48,     48}, 0, {     0,      0}, {0xc9, 0xe0, 0x6d, 0x00}}},
};

// 0x06008CC0
static const Vtx koopa_seg6_vertex_06008CC0[] = {
    {{{    27,      6,    -38}, 0, {     0,      0}, {0xe6, 0x30, 0x8e, 0x00}}},
    {{{    27,    -49,    -45}, 0, {     0,      0}, {0xf2, 0xdf, 0x87, 0x00}}},
    {{{    -3,    -48,    -43}, 0, {     0,      0}, {0xc9, 0xe0, 0x93, 0x00}}},
    {{{     1,      5,    -30}, 0, {     0,      0}, {0xc3, 0x2b, 0x9a, 0x00}}},
    {{{   -30,    -49,     16}, 0, {     0,      0}, {0x91, 0xdb, 0x30, 0x00}}},
    {{{     1,    -76,     26}, 0, {     0,      0}, {0xd2, 0x95, 0x2f, 0x00}}},
    {{{    -3,    -48,     48}, 0, {     0,      0}, {0xc9, 0xe0, 0x6d, 0x00}}},
    {{{     1,    -76,    -21}, 0, {     0,      0}, {0xd2, 0x95, 0xd1, 0x00}}},
    {{{   -30,    -49,    -11}, 0, {     0,      0}, {0x91, 0xdb, 0xd0, 0x00}}},
    {{{     1,      5,     35}, 0, {     0,      0}, {0xc3, 0x2b, 0x66, 0x00}}},
    {{{   -25,    -23,     21}, 0, {     0,      0}, {0x8f, 0x25, 0x2c, 0x00}}},
    {{{   -25,    -23,    -16}, 0, {     0,      0}, {0x8f, 0x25, 0xd4, 0x00}}},
    {{{     1,     39,     -7}, 0, {     0,      0}, {0xb8, 0x5a, 0xcc, 0x00}}},
    {{{    26,     40,    -13}, 0, {     0,      0}, {0xef, 0x70, 0xc8, 0x00}}},
};

// 0x06008DA0
static const Vtx koopa_seg6_vertex_06008DA0[] = {
    {{{    27,      6,     43}, 0, {     0,      0}, {0xe6, 0x30, 0x72, 0x00}}},
    {{{    26,     40,     17}, 0, {     0,      0}, {0xef, 0x70, 0x38, 0x00}}},
    {{{     1,     39,     11}, 0, {     0,      0}, {0xb8, 0x5a, 0x34, 0x00}}},
    {{{     1,      5,     35}, 0, {     0,      0}, {0xc3, 0x2b, 0x66, 0x00}}},
    {{{    -3,    -48,     48}, 0, {     0,      0}, {0xc9, 0xe0, 0x6d, 0x00}}},
    {{{     1,    -76,     26}, 0, {     0,      0}, {0xd2, 0x95, 0x2f, 0x00}}},
    {{{    27,    -76,     26}, 0, {     0,      0}, {0xff, 0x8d, 0x35, 0x00}}},
    {{{    27,    -49,     49}, 0, {     0,      0}, {0xf2, 0xdf, 0x79, 0x00}}},
    {{{   -30,    -49,    -11}, 0, {     0,      0}, {0x91, 0xdb, 0xd0, 0x00}}},
    {{{     1,    -76,    -21}, 0, {     0,      0}, {0xd2, 0x95, 0xd1, 0x00}}},
    {{{   -30,    -49,     16}, 0, {     0,      0}, {0x91, 0xdb, 0x30, 0x00}}},
    {{{    27,    -49,    -45}, 0, {     0,      0}, {0xf2, 0xdf, 0x87, 0x00}}},
    {{{    27,    -76,    -21}, 0, {     0,      0}, {0xff, 0x8d, 0xcb, 0x00}}},
    {{{    -3,    -48,    -43}, 0, {     0,      0}, {0xc9, 0xe0, 0x93, 0x00}}},
};

// 0x06008E80
static const Vtx koopa_seg6_vertex_06008E80[] = {
    {{{    27,    -49,     49}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    27,    -76,     26}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    27,    -76,    -21}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    27,    -49,    -45}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{     1,     39,     -7}, 0, {     0,      0}, {0xb8, 0x5a, 0xcc, 0x00}}},
    {{{     1,      5,    -30}, 0, {     0,      0}, {0xc3, 0x2b, 0x9a, 0x00}}},
    {{{   -25,    -23,    -16}, 0, {     0,      0}, {0x8f, 0x25, 0xd4, 0x00}}},
    {{{   -25,    -23,     21}, 0, {     0,      0}, {0x8f, 0x25, 0x2c, 0x00}}},
    {{{     1,      5,     35}, 0, {     0,      0}, {0xc3, 0x2b, 0x66, 0x00}}},
    {{{     1,     39,     11}, 0, {     0,      0}, {0xb8, 0x5a, 0x34, 0x00}}},
    {{{    27,      6,     43}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    27,      6,    -38}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    26,     40,    -13}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    26,     40,     17}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
};

// 0x06008F60
static const Vtx koopa_seg6_vertex_06008F60[] = {
    {{{    61,     -6,    -30}, 0, {     0,      0}, {0xe3, 0xe0, 0x8a, 0x00}}},
    {{{    61,    -25,      0}, 0, {     0,      0}, {0xf0, 0x83, 0x00, 0x00}}},
    {{{    -1,    -17,      0}, 0, {     0,      0}, {0xf0, 0x83, 0x00, 0x00}}},
    {{{    -1,     -6,    -16}, 0, {     0,      0}, {0xe3, 0xe0, 0x8a, 0x00}}},
    {{{    60,     34,     11}, 0, {     0,      0}, {0xdb, 0x68, 0x3e, 0x00}}},
    {{{    60,     34,    -11}, 0, {     0,      0}, {0xdb, 0x68, 0xc2, 0x00}}},
    {{{    -1,     16,      0}, 0, {     0,      0}, {0xd2, 0x76, 0x00, 0x00}}},
    {{{    61,     -6,    -30}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    60,     34,    -11}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    60,     34,     11}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    61,     -6,     29}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    61,    -25,      0}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    61,     -6,     29}, 0, {     0,      0}, {0xe3, 0xe0, 0x76, 0x00}}},
    {{{    -1,     -6,     16}, 0, {     0,      0}, {0xe3, 0xe0, 0x76, 0x00}}},
};

// 0x06009040
static const Vtx koopa_seg6_vertex_06009040[] = {
    {{{     1,    -76,     20}, 0, {     0,      0}, {0xd2, 0x95, 0x2f, 0x00}}},
    {{{     1,    -76,    -27}, 0, {     0,      0}, {0xd2, 0x95, 0xd1, 0x00}}},
    {{{    27,    -76,    -27}, 0, {     0,      0}, {0xff, 0x8d, 0xcb, 0x00}}},
    {{{    27,    -76,     20}, 0, {     0,      0}, {0xff, 0x8d, 0x35, 0x00}}},
    {{{     1,     39,    -12}, 0, {     0,      0}, {0xb8, 0x5a, 0xcc, 0x00}}},
    {{{     1,     39,      6}, 0, {     0,      0}, {0xb8, 0x5a, 0x34, 0x00}}},
    {{{    26,     40,     12}, 0, {     0,      0}, {0xef, 0x70, 0x38, 0x00}}},
    {{{    26,     40,    -18}, 0, {     0,      0}, {0xef, 0x70, 0xc8, 0x00}}},
    {{{   -25,    -23,     15}, 0, {     0,      0}, {0x8f, 0x25, 0x2c, 0x00}}},
    {{{   -25,    -23,    -22}, 0, {     0,      0}, {0x8f, 0x25, 0xd4, 0x00}}},
    {{{   -30,    -49,    -17}, 0, {     0,      0}, {0x91, 0xdb, 0xd0, 0x00}}},
    {{{   -30,    -49,     11}, 0, {     0,      0}, {0x91, 0xdb, 0x30, 0x00}}},
    {{{    -3,    -48,    -48}, 0, {     0,      0}, {0xc9, 0xe0, 0x93, 0x00}}},
    {{{     1,      5,    -36}, 0, {     0,      0}, {0xc3, 0x2b, 0x9a, 0x00}}},
    {{{    27,      6,    -44}, 0, {     0,      0}, {0xe6, 0x30, 0x8e, 0x00}}},
    {{{    27,    -49,    -50}, 0, {     0,      0}, {0xf2, 0xdf, 0x87, 0x00}}},
};

// 0x06009140
static const Vtx koopa_seg6_vertex_06009140[] = {
    {{{     1,      5,     29}, 0, {     0,      0}, {0xc3, 0x2b, 0x66, 0x00}}},
    {{{    -3,    -48,     42}, 0, {     0,      0}, {0xc9, 0xe0, 0x6d, 0x00}}},
    {{{    27,    -49,     44}, 0, {     0,      0}, {0xf2, 0xdf, 0x79, 0x00}}},
    {{{    27,      6,     38}, 0, {     0,      0}, {0xe6, 0x30, 0x72, 0x00}}},
    {{{    -3,    -48,    -48}, 0, {     0,      0}, {0xc9, 0xe0, 0x93, 0x00}}},
    {{{     1,    -76,    -27}, 0, {     0,      0}, {0xd2, 0x95, 0xd1, 0x00}}},
    {{{   -30,    -49,    -17}, 0, {     0,      0}, {0x91, 0xdb, 0xd0, 0x00}}},
    {{{   -30,    -49,     11}, 0, {     0,      0}, {0x91, 0xdb, 0x30, 0x00}}},
    {{{     1,    -76,     20}, 0, {     0,      0}, {0xd2, 0x95, 0x2f, 0x00}}},
    {{{   -25,    -23,    -22}, 0, {     0,      0}, {0x8f, 0x25, 0xd4, 0x00}}},
    {{{     1,      5,    -36}, 0, {     0,      0}, {0xc3, 0x2b, 0x9a, 0x00}}},
    {{{   -25,    -23,     15}, 0, {     0,      0}, {0x8f, 0x25, 0x2c, 0x00}}},
    {{{    26,     40,     12}, 0, {     0,      0}, {0xef, 0x70, 0x38, 0x00}}},
    {{{     1,     39,      6}, 0, {     0,      0}, {0xb8, 0x5a, 0x34, 0x00}}},
    {{{     1,     39,    -12}, 0, {     0,      0}, {0xb8, 0x5a, 0xcc, 0x00}}},
    {{{    26,     40,    -18}, 0, {     0,      0}, {0xef, 0x70, 0xc8, 0x00}}},
};

// 0x06009240
static const Vtx koopa_seg6_vertex_06009240[] = {
    {{{     1,      5,    -36}, 0, {     0,      0}, {0xc3, 0x2b, 0x9a, 0x00}}},
    {{{    26,     40,    -18}, 0, {     0,      0}, {0xef, 0x70, 0xc8, 0x00}}},
    {{{    27,      6,    -44}, 0, {     0,      0}, {0xe6, 0x30, 0x8e, 0x00}}},
    {{{    27,    -49,    -50}, 0, {     0,      0}, {0xf2, 0xdf, 0x87, 0x00}}},
    {{{    27,    -76,    -27}, 0, {     0,      0}, {0xff, 0x8d, 0xcb, 0x00}}},
    {{{     1,    -76,    -27}, 0, {     0,      0}, {0xd2, 0x95, 0xd1, 0x00}}},
    {{{    -3,    -48,    -48}, 0, {     0,      0}, {0xc9, 0xe0, 0x93, 0x00}}},
    {{{   -30,    -49,    -17}, 0, {     0,      0}, {0x91, 0xdb, 0xd0, 0x00}}},
    {{{     1,    -76,     20}, 0, {     0,      0}, {0xd2, 0x95, 0x2f, 0x00}}},
    {{{   -30,    -49,     11}, 0, {     0,      0}, {0x91, 0xdb, 0x30, 0x00}}},
    {{{    -3,    -48,     42}, 0, {     0,      0}, {0xc9, 0xe0, 0x6d, 0x00}}},
    {{{    27,    -76,     20}, 0, {     0,      0}, {0xff, 0x8d, 0x35, 0x00}}},
    {{{    27,    -49,     44}, 0, {     0,      0}, {0xf2, 0xdf, 0x79, 0x00}}},
    {{{    27,    -49,     44}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    27,    -76,     20}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    27,    -76,    -27}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
};

// 0x06009340
static const Vtx koopa_seg6_vertex_06009340[] = {
    {{{    27,    -49,     44}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    27,    -76,    -27}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    27,    -49,    -50}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{   -25,    -23,     15}, 0, {     0,      0}, {0x8f, 0x25, 0x2c, 0x00}}},
    {{{     1,      5,     29}, 0, {     0,      0}, {0xc3, 0x2b, 0x66, 0x00}}},
    {{{     1,     39,      6}, 0, {     0,      0}, {0xb8, 0x5a, 0x34, 0x00}}},
    {{{     1,     39,    -12}, 0, {     0,      0}, {0xb8, 0x5a, 0xcc, 0x00}}},
    {{{     1,      5,    -36}, 0, {     0,      0}, {0xc3, 0x2b, 0x9a, 0x00}}},
    {{{   -25,    -23,    -22}, 0, {     0,      0}, {0x8f, 0x25, 0xd4, 0x00}}},
    {{{    27,      6,     38}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    27,      6,    -44}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    26,     40,    -18}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    26,     40,     12}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
};

// 0x06009410
static const Vtx koopa_seg6_vertex_06009410[] = {
    {{{    -1,     -6,     16}, 0, {     0,      0}, {0xe3, 0xe0, 0x76, 0x00}}},
    {{{    -1,    -17,      0}, 0, {     0,      0}, {0xf0, 0x83, 0x00, 0x00}}},
    {{{    61,    -25,      0}, 0, {     0,      0}, {0xf0, 0x83, 0x00, 0x00}}},
    {{{    61,     -6,     29}, 0, {     0,      0}, {0xe3, 0xe0, 0x76, 0x00}}},
    {{{    -1,     16,      0}, 0, {     0,      0}, {0xd2, 0x76, 0x00, 0x00}}},
    {{{    60,     34,     10}, 0, {     0,      0}, {0xdb, 0x68, 0x3e, 0x00}}},
    {{{    60,     34,    -11}, 0, {     0,      0}, {0xdb, 0x68, 0xc2, 0x00}}},
    {{{    61,    -25,      0}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    61,     -6,    -30}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    60,     34,    -11}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    60,     34,     10}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    61,     -6,     29}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0x00}}},
    {{{    -1,     -6,    -17}, 0, {     0,      0}, {0xe3, 0xe0, 0x8a, 0x00}}},
    {{{    61,     -6,    -30}, 0, {     0,      0}, {0xe3, 0xe0, 0x8a, 0x00}}},
};

// 0x060094F0
static const Vtx koopa_seg6_vertex_060094F0[] = {
    {{{    14,    -41,     15}, 0, {     0,      0}, {0xdc, 0x98, 0x3e, 0x00}}},
    {{{    14,    -41,    -19}, 0, {     0,      0}, {0xdc, 0x98, 0xc2, 0x00}}},
    {{{    48,    -41,    -22}, 0, {     0,      0}, {0x23, 0x99, 0xc0, 0x00}}},
    {{{    48,    -41,     18}, 0, {     0,      0}, {0x23, 0x99, 0x40, 0x00}}},
    {{{    44,      5,    -22}, 0, {     0,      0}, {0x1d, 0x74, 0xd8, 0x00}}},
    {{{    20,     35,     -2}, 0, {     0,      0}, {0x15, 0x7d, 0x00, 0x00}}},
    {{{    44,      5,     18}, 0, {     0,      0}, {0x1d, 0x74, 0x28, 0x00}}},
    {{{    -6,     24,     -2}, 0, {     0,      0}, {0xa1, 0x53, 0x00, 0x00}}},
    {{{    -6,    -18,    -23}, 0, {     0,      0}, {0x96, 0xe3, 0xc2, 0x00}}},
    {{{    -6,    -18,     19}, 0, {     0,      0}, {0x96, 0xe3, 0x3e, 0x00}}},
    {{{    67,    -22,     20}, 0, {     0,      0}, {0x68, 0xdf, 0x3f, 0x00}}},
    {{{    67,    -22,    -24}, 0, {     0,      0}, {0x68, 0xdf, 0xc1, 0x00}}},
    {{{    67,     15,    -11}, 0, {     0,      0}, {0x3a, 0x5e, 0xc3, 0x00}}},
    {{{    67,     15,      6}, 0, {     0,      0}, {0x3a, 0x5e, 0x3d, 0x00}}},
};

// 0x060095D0
static const Vtx koopa_seg6_vertex_060095D0[] = {
    {{{    14,     15,    -37}, 0, {     0,      0}, {0xdb, 0x2b, 0x8f, 0x00}}},
    {{{    44,     -8,    -42}, 0, {     0,      0}, {0x26, 0x15, 0x89, 0x00}}},
    {{{    30,    -22,    -37}, 0, {     0,      0}, {0x05, 0xcc, 0x8d, 0x00}}},
    {{{    14,     15,     33}, 0, {     0,      0}, {0xdb, 0x2b, 0x71, 0x00}}},
    {{{    14,    -22,     33}, 0, {     0,      0}, {0xdc, 0xd5, 0x71, 0x00}}},
    {{{    30,    -22,     33}, 0, {     0,      0}, {0x05, 0xcc, 0x73, 0x00}}},
    {{{    67,     15,      6}, 0, {     0,      0}, {0x3a, 0x5e, 0x3d, 0x00}}},
    {{{    44,      5,     18}, 0, {     0,      0}, {0x1d, 0x74, 0x28, 0x00}}},
    {{{    44,     -8,     38}, 0, {     0,      0}, {0x26, 0x15, 0x77, 0x00}}},
    {{{    20,     35,     -2}, 0, {     0,      0}, {0x15, 0x7d, 0x00, 0x00}}},
    {{{    -6,     24,     -2}, 0, {     0,      0}, {0xa1, 0x53, 0x00, 0x00}}},
    {{{    67,     15,    -11}, 0, {     0,      0}, {0x3a, 0x5e, 0xc3, 0x00}}},
    {{{    44,      5,    -22}, 0, {     0,      0}, {0x1d, 0x74, 0xd8, 0x00}}},
    {{{    67,    -22,    -24}, 0, {     0,      0}, {0x68, 0xdf, 0xc1, 0x00}}},
    {{{    48,    -41,    -22}, 0, {     0,      0}, {0x23, 0x99, 0xc0, 0x00}}},
};

// 0x060096C0
static const Vtx koopa_seg6_vertex_060096C0[] = {
    {{{    14,    -22,    -37}, 0, {     0,      0}, {0xdc, 0xd5, 0x8f, 0x00}}},
    {{{    14,    -41,    -19}, 0, {     0,      0}, {0xdc, 0x98, 0xc2, 0x00}}},
    {{{    -6,    -18,    -23}, 0, {     0,      0}, {0x96, 0xe3, 0xc2, 0x00}}},
    {{{    -6,    -18,     19}, 0, {     0,      0}, {0x96, 0xe3, 0x3e, 0x00}}},
    {{{    14,    -41,     15}, 0, {     0,      0}, {0xdc, 0x98, 0x3e, 0x00}}},
    {{{    14,    -22,     33}, 0, {     0,      0}, {0xdc, 0xd5, 0x71, 0x00}}},
    {{{    48,    -41,     18}, 0, {     0,      0}, {0x23, 0x99, 0x40, 0x00}}},
    {{{    67,    -22,     20}, 0, {     0,      0}, {0x68, 0xdf, 0x3f, 0x00}}},
    {{{    44,     -8,     38}, 0, {     0,      0}, {0x26, 0x15, 0x77, 0x00}}},
    {{{    67,     15,      6}, 0, {     0,      0}, {0x3a, 0x5e, 0x3d, 0x00}}},
    {{{    -6,     24,     -2}, 0, {     0,      0}, {0xa1, 0x53, 0x00, 0x00}}},
    {{{    14,     15,    -37}, 0, {     0,      0}, {0xdb, 0x2b, 0x8f, 0x00}}},
    {{{    14,     15,     33}, 0, {     0,      0}, {0xdb, 0x2b, 0x71, 0x00}}},
    {{{    44,      5,     18}, 0, {     0,      0}, {0x1d, 0x74, 0x28, 0x00}}},
    {{{    30,    -22,    -37}, 0, {     0,      0}, {0x05, 0xcc, 0x8d, 0x00}}},
};

// 0x060097B0
static const Vtx koopa_seg6_vertex_060097B0[] = {
    {{{    14,     15,    -37}, 0, {     0,      0}, {0xdb, 0x2b, 0x8f, 0x00}}},
    {{{    20,     35,     -2}, 0, {     0,      0}, {0x15, 0x7d, 0x00, 0x00}}},
    {{{    44,      5,    -22}, 0, {     0,      0}, {0x1d, 0x74, 0xd8, 0x00}}},
    {{{    67,    -22,     20}, 0, {     0,      0}, {0x68, 0xdf, 0x3f, 0x00}}},
    {{{    48,    -41,     18}, 0, {     0,      0}, {0x23, 0x99, 0x40, 0x00}}},
    {{{    48,    -41,    -22}, 0, {     0,      0}, {0x23, 0x99, 0xc0, 0x00}}},
    {{{    67,    -22,    -24}, 0, {     0,      0}, {0x68, 0xdf, 0xc1, 0x00}}},
    {{{    30,    -22,    -37}, 0, {     0,      0}, {0x05, 0xcc, 0x8d, 0x00}}},
    {{{    14,    -41,    -19}, 0, {     0,      0}, {0xdc, 0x98, 0xc2, 0x00}}},
    {{{    -6,    -18,    -23}, 0, {     0,      0}, {0x96, 0xe3, 0xc2, 0x00}}},
    {{{    14,    -41,     15}, 0, {     0,      0}, {0xdc, 0x98, 0x3e, 0x00}}},
    {{{    -6,    -18,     19}, 0, {     0,      0}, {0x96, 0xe3, 0x3e, 0x00}}},
    {{{    30,    -22,     33}, 0, {     0,      0}, {0x05, 0xcc, 0x73, 0x00}}},
    {{{    14,    -22,     33}, 0, {     0,      0}, {0xdc, 0xd5, 0x71, 0x00}}},
    {{{    14,    -22,    -37}, 0, {     0,      0}, {0xdc, 0xd5, 0x8f, 0x00}}},
};

// 0x060098A0
static const Vtx koopa_seg6_vertex_060098A0[] = {
    {{{    14,     15,     33}, 0, {     0,      0}, {0xdb, 0x2b, 0x71, 0x00}}},
    {{{    -6,     24,     -2}, 0, {     0,      0}, {0xa1, 0x53, 0x00, 0x00}}},
    {{{    -6,    -18,     19}, 0, {     0,      0}, {0x96, 0xe3, 0x3e, 0x00}}},
    {{{    14,     15,    -37}, 0, {     0,      0}, {0xdb, 0x2b, 0x8f, 0x00}}},
    {{{    14,    -22,    -37}, 0, {     0,      0}, {0xdc, 0xd5, 0x8f, 0x00}}},
    {{{    -6,    -18,    -23}, 0, {     0,      0}, {0x96, 0xe3, 0xc2, 0x00}}},
    {{{    44,     -8,    -42}, 0, {     0,      0}, {0x26, 0x15, 0x89, 0x00}}},
    {{{    67,     15,    -11}, 0, {     0,      0}, {0x3a, 0x5e, 0xc3, 0x00}}},
    {{{    67,    -22,    -24}, 0, {     0,      0}, {0x68, 0xdf, 0xc1, 0x00}}},
    {{{    30,    -22,     33}, 0, {     0,      0}, {0x05, 0xcc, 0x73, 0x00}}},
    {{{    44,     -8,     38}, 0, {     0,      0}, {0x26, 0x15, 0x77, 0x00}}},
    {{{    44,      5,     18}, 0, {     0,      0}, {0x1d, 0x74, 0x28, 0x00}}},
    {{{    67,     15,      6}, 0, {     0,      0}, {0x3a, 0x5e, 0x3d, 0x00}}},
    {{{    44,      5,    -22}, 0, {     0,      0}, {0x1d, 0x74, 0xd8, 0x00}}},
    {{{    48,    -41,    -22}, 0, {     0,      0}, {0x23, 0x99, 0xc0, 0x00}}},
    {{{    30,    -22,    -37}, 0, {     0,      0}, {0x05, 0xcc, 0x8d, 0x00}}},
};

// 0x060099A0
static const Vtx koopa_seg6_vertex_060099A0[] = {
    {{{    44,     -8,     38}, 0, {     0,      0}, {0x26, 0x15, 0x77, 0x00}}},
    {{{    30,    -22,     33}, 0, {     0,      0}, {0x05, 0xcc, 0x73, 0x00}}},
    {{{    48,    -41,     18}, 0, {     0,      0}, {0x23, 0x99, 0x40, 0x00}}},
    {{{    44,      5,     18}, 0, {     0,      0}, {0x1d, 0x74, 0x28, 0x00}}},
    {{{    20,     35,     -2}, 0, {     0,      0}, {0x15, 0x7d, 0x00, 0x00}}},
    {{{    14,     15,     33}, 0, {     0,      0}, {0xdb, 0x2b, 0x71, 0x00}}},
    {{{    44,      5,    -22}, 0, {     0,      0}, {0x1d, 0x74, 0xd8, 0x00}}},
    {{{    44,     -8,    -42}, 0, {     0,      0}, {0x26, 0x15, 0x89, 0x00}}},
    {{{    14,     15,    -37}, 0, {     0,      0}, {0xdb, 0x2b, 0x8f, 0x00}}},
};

// 0x06009A30
static const Vtx koopa_seg6_vertex_06009A30[] = {
    {{{     0,     -2,    -10}, 0, {     0,      0}, {0xef, 0x00, 0x83, 0x00}}},
    {{{    -1,      8,     -2}, 0, {     0,      0}, {0xeb, 0x76, 0xda, 0x00}}},
    {{{    52,     17,     -4}, 0, {     0,      0}, {0xeb, 0x76, 0xda, 0x00}}},
    {{{    53,      0,    -17}, 0, {     0,      0}, {0xef, 0x00, 0x83, 0x00}}},
    {{{    -1,      4,     10}, 0, {     0,      0}, {0xed, 0x49, 0x65, 0x00}}},
    {{{    52,     10,     16}, 0, {     0,      0}, {0xed, 0x49, 0x65, 0x00}}},
    {{{     0,     -8,     10}, 0, {     0,      0}, {0xf1, 0xb6, 0x65, 0x00}}},
    {{{    53,    -11,     16}, 0, {     0,      0}, {0xf1, 0xb6, 0x65, 0x00}}},
    {{{     0,    -13,     -2}, 0, {     0,      0}, {0xf2, 0x88, 0xda, 0x00}}},
    {{{    53,    -18,     -4}, 0, {     0,      0}, {0xf2, 0x88, 0xda, 0x00}}},
};

// 0x06009AD0
static const Vtx koopa_seg6_vertex_06009AD0[] = {
    {{{    48,    -41,    -18}, 0, {     0,      0}, {0x23, 0x99, 0xc0, 0x00}}},
    {{{    48,    -41,     22}, 0, {     0,      0}, {0x23, 0x99, 0x40, 0x00}}},
    {{{    14,    -41,     19}, 0, {     0,      0}, {0xdc, 0x98, 0x3e, 0x00}}},
    {{{    14,    -41,    -15}, 0, {     0,      0}, {0xdc, 0x98, 0xc2, 0x00}}},
    {{{    44,      5,    -18}, 0, {     0,      0}, {0x1d, 0x74, 0xd8, 0x00}}},
    {{{    20,     35,      2}, 0, {     0,      0}, {0x15, 0x7d, 0x00, 0x00}}},
    {{{    44,      5,     22}, 0, {     0,      0}, {0x1d, 0x74, 0x28, 0x00}}},
    {{{    -6,    -18,    -19}, 0, {     0,      0}, {0x96, 0xe3, 0xc2, 0x00}}},
    {{{    -6,    -18,     23}, 0, {     0,      0}, {0x96, 0xe3, 0x3e, 0x00}}},
    {{{    -6,     24,      2}, 0, {     0,      0}, {0xa1, 0x53, 0x00, 0x00}}},
    {{{    67,     15,     -6}, 0, {     0,      0}, {0x3a, 0x5e, 0xc3, 0x00}}},
    {{{    67,     15,     11}, 0, {     0,      0}, {0x3a, 0x5e, 0x3d, 0x00}}},
    {{{    67,    -22,     24}, 0, {     0,      0}, {0x68, 0xdf, 0x3f, 0x00}}},
    {{{    67,    -22,    -20}, 0, {     0,      0}, {0x68, 0xdf, 0xc1, 0x00}}},
};

// 0x06009BB0
static const Vtx koopa_seg6_vertex_06009BB0[] = {
    {{{    30,    -22,     37}, 0, {     0,      0}, {0x05, 0xcc, 0x73, 0x00}}},
    {{{    44,     -8,     42}, 0, {     0,      0}, {0x26, 0x15, 0x77, 0x00}}},
    {{{    14,     15,     37}, 0, {     0,      0}, {0xdb, 0x2b, 0x71, 0x00}}},
    {{{    30,    -22,    -33}, 0, {     0,      0}, {0x05, 0xcc, 0x8d, 0x00}}},
    {{{    14,    -22,    -33}, 0, {     0,      0}, {0xdc, 0xd5, 0x8f, 0x00}}},
    {{{    14,     15,    -33}, 0, {     0,      0}, {0xdb, 0x2b, 0x8f, 0x00}}},
    {{{    44,     -8,    -38}, 0, {     0,      0}, {0x26, 0x15, 0x89, 0x00}}},
    {{{    44,      5,    -18}, 0, {     0,      0}, {0x1d, 0x74, 0xd8, 0x00}}},
    {{{    67,     15,     -6}, 0, {     0,      0}, {0x3a, 0x5e, 0xc3, 0x00}}},
    {{{    -6,     24,      2}, 0, {     0,      0}, {0xa1, 0x53, 0x00, 0x00}}},
    {{{    20,     35,      2}, 0, {     0,      0}, {0x15, 0x7d, 0x00, 0x00}}},
    {{{    44,      5,     22}, 0, {     0,      0}, {0x1d, 0x74, 0x28, 0x00}}},
    {{{    67,     15,     11}, 0, {     0,      0}, {0x3a, 0x5e, 0x3d, 0x00}}},
    {{{    48,    -41,     22}, 0, {     0,      0}, {0x23, 0x99, 0x40, 0x00}}},
    {{{    67,    -22,     24}, 0, {     0,      0}, {0x68, 0xdf, 0x3f, 0x00}}},
};

// 0x06009CA0
static const Vtx koopa_seg6_vertex_06009CA0[] = {
    {{{    -6,    -18,     23}, 0, {     0,      0}, {0x96, 0xe3, 0x3e, 0x00}}},
    {{{    14,    -41,     19}, 0, {     0,      0}, {0xdc, 0x98, 0x3e, 0x00}}},
    {{{    14,    -22,     37}, 0, {     0,      0}, {0xdc, 0xd5, 0x71, 0x00}}},
    {{{    14,    -22,    -33}, 0, {     0,      0}, {0xdc, 0xd5, 0x8f, 0x00}}},
    {{{    14,    -41,    -15}, 0, {     0,      0}, {0xdc, 0x98, 0xc2, 0x00}}},
    {{{    -6,    -18,    -19}, 0, {     0,      0}, {0x96, 0xe3, 0xc2, 0x00}}},
    {{{    44,     -8,    -38}, 0, {     0,      0}, {0x26, 0x15, 0x89, 0x00}}},
    {{{    67,    -22,    -20}, 0, {     0,      0}, {0x68, 0xdf, 0xc1, 0x00}}},
    {{{    48,    -41,    -18}, 0, {     0,      0}, {0x23, 0x99, 0xc0, 0x00}}},
    {{{    67,     15,     -6}, 0, {     0,      0}, {0x3a, 0x5e, 0xc3, 0x00}}},
    {{{    14,     15,     37}, 0, {     0,      0}, {0xdb, 0x2b, 0x71, 0x00}}},
    {{{    -6,     24,      2}, 0, {     0,      0}, {0xa1, 0x53, 0x00, 0x00}}},
    {{{    14,     15,    -33}, 0, {     0,      0}, {0xdb, 0x2b, 0x8f, 0x00}}},
    {{{    44,      5,    -18}, 0, {     0,      0}, {0x1d, 0x74, 0xd8, 0x00}}},
    {{{    30,    -22,     37}, 0, {     0,      0}, {0x05, 0xcc, 0x73, 0x00}}},
};

// 0x06009D90
static const Vtx koopa_seg6_vertex_06009D90[] = {
    {{{    44,      5,     22}, 0, {     0,      0}, {0x1d, 0x74, 0x28, 0x00}}},
    {{{    20,     35,      2}, 0, {     0,      0}, {0x15, 0x7d, 0x00, 0x00}}},
    {{{    14,     15,     37}, 0, {     0,      0}, {0xdb, 0x2b, 0x71, 0x00}}},
    {{{    67,    -22,     24}, 0, {     0,      0}, {0x68, 0xdf, 0x3f, 0x00}}},
    {{{    48,    -41,     22}, 0, {     0,      0}, {0x23, 0x99, 0x40, 0x00}}},
    {{{    48,    -41,    -18}, 0, {     0,      0}, {0x23, 0x99, 0xc0, 0x00}}},
    {{{    67,    -22,    -20}, 0, {     0,      0}, {0x68, 0xdf, 0xc1, 0x00}}},
    {{{    14,    -41,     19}, 0, {     0,      0}, {0xdc, 0x98, 0x3e, 0x00}}},
    {{{    30,    -22,     37}, 0, {     0,      0}, {0x05, 0xcc, 0x73, 0x00}}},
    {{{    -6,    -18,    -19}, 0, {     0,      0}, {0x96, 0xe3, 0xc2, 0x00}}},
    {{{    14,    -41,    -15}, 0, {     0,      0}, {0xdc, 0x98, 0xc2, 0x00}}},
    {{{    -6,    -18,     23}, 0, {     0,      0}, {0x96, 0xe3, 0x3e, 0x00}}},
    {{{    30,    -22,    -33}, 0, {     0,      0}, {0x05, 0xcc, 0x8d, 0x00}}},
    {{{    14,    -22,    -33}, 0, {     0,      0}, {0xdc, 0xd5, 0x8f, 0x00}}},
    {{{    14,    -22,     37}, 0, {     0,      0}, {0xdc, 0xd5, 0x71, 0x00}}},
};

// 0x06009E80
static const Vtx koopa_seg6_vertex_06009E80[] = {
    {{{    -6,    -18,    -19}, 0, {     0,      0}, {0x96, 0xe3, 0xc2, 0x00}}},
    {{{    -6,     24,      2}, 0, {     0,      0}, {0xa1, 0x53, 0x00, 0x00}}},
    {{{    14,     15,    -33}, 0, {     0,      0}, {0xdb, 0x2b, 0x8f, 0x00}}},
    {{{    -6,    -18,     23}, 0, {     0,      0}, {0x96, 0xe3, 0x3e, 0x00}}},
    {{{    14,    -22,     37}, 0, {     0,      0}, {0xdc, 0xd5, 0x71, 0x00}}},
    {{{    14,     15,     37}, 0, {     0,      0}, {0xdb, 0x2b, 0x71, 0x00}}},
    {{{    67,    -22,     24}, 0, {     0,      0}, {0x68, 0xdf, 0x3f, 0x00}}},
    {{{    67,     15,     11}, 0, {     0,      0}, {0x3a, 0x5e, 0x3d, 0x00}}},
    {{{    44,     -8,     42}, 0, {     0,      0}, {0x26, 0x15, 0x77, 0x00}}},
    {{{    44,     -8,    -38}, 0, {     0,      0}, {0x26, 0x15, 0x89, 0x00}}},
    {{{    30,    -22,    -33}, 0, {     0,      0}, {0x05, 0xcc, 0x8d, 0x00}}},
    {{{    44,      5,     22}, 0, {     0,      0}, {0x1d, 0x74, 0x28, 0x00}}},
    {{{    67,     15,     -6}, 0, {     0,      0}, {0x3a, 0x5e, 0xc3, 0x00}}},
    {{{    44,      5,    -18}, 0, {     0,      0}, {0x1d, 0x74, 0xd8, 0x00}}},
    {{{    30,    -22,     37}, 0, {     0,      0}, {0x05, 0xcc, 0x73, 0x00}}},
    {{{    48,    -41,     22}, 0, {     0,      0}, {0x23, 0x99, 0x40, 0x00}}},
};

// 0x06009F80
static const Vtx koopa_seg6_vertex_06009F80[] = {
    {{{    48,    -41,    -18}, 0, {     0,      0}, {0x23, 0x99, 0xc0, 0x00}}},
    {{{    30,    -22,    -33}, 0, {     0,      0}, {0x05, 0xcc, 0x8d, 0x00}}},
    {{{    44,     -8,    -38}, 0, {     0,      0}, {0x26, 0x15, 0x89, 0x00}}},
    {{{    14,     15,    -33}, 0, {     0,      0}, {0xdb, 0x2b, 0x8f, 0x00}}},
    {{{    20,     35,      2}, 0, {     0,      0}, {0x15, 0x7d, 0x00, 0x00}}},
    {{{    44,      5,    -18}, 0, {     0,      0}, {0x1d, 0x74, 0xd8, 0x00}}},
    {{{    14,     15,     37}, 0, {     0,      0}, {0xdb, 0x2b, 0x71, 0x00}}},
    {{{    44,     -8,     42}, 0, {     0,      0}, {0x26, 0x15, 0x77, 0x00}}},
    {{{    44,      5,     22}, 0, {     0,      0}, {0x1d, 0x74, 0x28, 0x00}}},
};

// 0x0600A010
static const Vtx koopa_seg6_vertex_0600A010[] = {
    {{{    53,      0,     17}, 0, {     0,      0}, {0xef, 0x00, 0x7d, 0x00}}},
    {{{    52,     17,      3}, 0, {     0,      0}, {0xeb, 0x76, 0x26, 0x00}}},
    {{{    -1,      8,      1}, 0, {     0,      0}, {0xeb, 0x76, 0x26, 0x00}}},
    {{{     0,     -2,      9}, 0, {     0,      0}, {0xef, 0x00, 0x7d, 0x00}}},
    {{{    52,     10,    -17}, 0, {     0,      0}, {0xed, 0x49, 0x9b, 0x00}}},
    {{{    -1,      4,    -11}, 0, {     0,      0}, {0xed, 0x49, 0x9b, 0x00}}},
    {{{    53,    -11,    -17}, 0, {     0,      0}, {0xf1, 0xb6, 0x9b, 0x00}}},
    {{{     0,     -8,    -11}, 0, {     0,      0}, {0xf1, 0xb6, 0x9b, 0x00}}},
    {{{    53,    -18,      3}, 0, {     0,      0}, {0xf2, 0x88, 0x26, 0x00}}},
    {{{     0,    -13,      1}, 0, {     0,      0}, {0xf2, 0x88, 0x26, 0x00}}},
};

// 0x0600A0B0
static const Vtx koopa_seg6_vertex_0600A0B0[] = {
    {{{     0,     51,      0}, 0, {     0,      0}, {0x95, 0x44, 0x00, 0x00}}},
    {{{    10,     49,     35}, 0, {     0,      0}, {0xac, 0x42, 0x43, 0x00}}},
    {{{    44,     74,     31}, 0, {     0,      0}, {0xf1, 0x69, 0x44, 0x00}}},
    {{{    35,     83,      0}, 0, {     0,      0}, {0xdc, 0x79, 0x00, 0x00}}},
    {{{    14,     21,     64}, 0, {     0,      0}, {0xaf, 0x1e, 0x5c, 0x00}}},
    {{{    63,     40,     61}, 0, {     0,      0}, {0x17, 0x3d, 0x6c, 0x00}}},
    {{{   139,     71,     48}, 0, {     0,      0}, {0x46, 0x33, 0x5c, 0x00}}},
    {{{    86,    112,      0}, 0, {     0,      0}, {0x1f, 0x7b, 0x00, 0x00}}},
    {{{    91,     82,     51}, 0, {     0,      0}, {0x14, 0x35, 0x71, 0x00}}},
    {{{    82,     80,      0}, 0, {     0,      0}, {0x42, 0x6c, 0x00, 0x00}}},
    {{{    98,     22,     59}, 0, {     0,      0}, {0x2f, 0x0e, 0x74, 0x00}}},
    {{{    53,      2,     86}, 0, {     0,      0}, {0xf2, 0x02, 0x7e, 0x00}}},
    {{{   -13,     -4,      0}, 0, {     0,      0}, {0x82, 0xf8, 0x00, 0x00}}},
    {{{    16,    -63,      0}, 0, {     0,      0}, {0xbc, 0x95, 0x00, 0x00}}},
    {{{    23,    -24,     59}, 0, {     0,      0}, {0xad, 0xcb, 0x4f, 0x00}}},
};

// 0x0600A1A0
static const Vtx koopa_seg6_vertex_0600A1A0[] = {
    {{{    68,    -65,     17}, 0, {     0,      0}, {0xe6, 0x8b, 0x28, 0x00}}},
    {{{    42,    -44,     54}, 0, {     0,      0}, {0xe4, 0xa5, 0x52, 0x00}}},
    {{{    16,    -63,      0}, 0, {     0,      0}, {0xbc, 0x95, 0x00, 0x00}}},
    {{{    23,    -24,     59}, 0, {     0,      0}, {0xad, 0xcb, 0x4f, 0x00}}},
    {{{   101,    -44,     53}, 0, {     0,      0}, {0xf4, 0xae, 0x60, 0x00}}},
    {{{    97,    -86,      0}, 0, {     0,      0}, {0xdc, 0x87, 0x00, 0x00}}},
    {{{   146,    -93,      0}, 0, {     0,      0}, {0xf9, 0x82, 0x00, 0x00}}},
    {{{    14,     21,     64}, 0, {     0,      0}, {0xaf, 0x1e, 0x5c, 0x00}}},
    {{{    53,      2,     86}, 0, {     0,      0}, {0xf2, 0x02, 0x7e, 0x00}}},
    {{{    75,    -16,     81}, 0, {     0,      0}, {0x2c, 0xe3, 0x73, 0x00}}},
    {{{    98,     22,     59}, 0, {     0,      0}, {0x2f, 0x0e, 0x74, 0x00}}},
    {{{    63,     40,     61}, 0, {     0,      0}, {0x17, 0x3d, 0x6c, 0x00}}},
    {{{   -13,     -4,      0}, 0, {     0,      0}, {0x82, 0xf8, 0x00, 0x00}}},
    {{{    14,     21,    -64}, 0, {     0,      0}, {0xaf, 0x1e, 0xa4, 0x00}}},
    {{{    23,    -24,    -59}, 0, {     0,      0}, {0xad, 0xcb, 0xb1, 0x00}}},
    {{{    42,    -44,    -54}, 0, {     0,      0}, {0xe4, 0xa5, 0xae, 0x00}}},
};

// 0x0600A2A0
static const Vtx koopa_seg6_vertex_0600A2A0[] = {
    {{{    91,     82,    -51}, 0, {     0,      0}, {0x84, 0x19, 0x00, 0x00}}},
    {{{    91,     82,     51}, 0, {     0,      0}, {0x84, 0x19, 0x00, 0x00}}},
    {{{    86,    112,      0}, 0, {     0,      0}, {0x84, 0xe9, 0x00, 0x00}}},
    {{{    35,     83,      0}, 0, {     0,      0}, {0xdc, 0x79, 0x00, 0x00}}},
    {{{    44,     74,    -32}, 0, {     0,      0}, {0xf1, 0x69, 0xbc, 0x00}}},
    {{{    10,     49,    -36}, 0, {     0,      0}, {0xac, 0x42, 0xbd, 0x00}}},
    {{{     0,     51,      0}, 0, {     0,      0}, {0x95, 0x44, 0x00, 0x00}}},
    {{{    63,     40,    -62}, 0, {     0,      0}, {0x17, 0x3d, 0x94, 0x00}}},
    {{{    82,     80,      0}, 0, {     0,      0}, {0x42, 0x6c, 0x00, 0x00}}},
    {{{    63,     40,     61}, 0, {     0,      0}, {0x17, 0x3d, 0x6c, 0x00}}},
    {{{    98,     22,    -60}, 0, {     0,      0}, {0x2f, 0x0e, 0x8c, 0x00}}},
    {{{    75,    -16,    -82}, 0, {     0,      0}, {0x2c, 0xe3, 0x8d, 0x00}}},
    {{{    53,      2,    -86}, 0, {     0,      0}, {0xf2, 0x02, 0x82, 0x00}}},
    {{{    42,    -44,    -54}, 0, {     0,      0}, {0xe4, 0xa5, 0xae, 0x00}}},
    {{{    23,    -24,    -59}, 0, {     0,      0}, {0xad, 0xcb, 0xb1, 0x00}}},
    {{{    14,     21,    -64}, 0, {     0,      0}, {0xaf, 0x1e, 0xa4, 0x00}}},
};

// 0x0600A3A0
static const Vtx koopa_seg6_vertex_0600A3A0[] = {
    {{{   101,    -44,    -54}, 0, {     0,      0}, {0xf4, 0xae, 0xa0, 0x00}}},
    {{{    68,    -65,    -18}, 0, {     0,      0}, {0xe6, 0x8b, 0xd8, 0x00}}},
    {{{    42,    -44,    -54}, 0, {     0,      0}, {0xe4, 0xa5, 0xae, 0x00}}},
    {{{   146,    -93,      0}, 0, {     0,      0}, {0xf9, 0x82, 0x00, 0x00}}},
    {{{    97,    -86,      0}, 0, {     0,      0}, {0xdc, 0x87, 0x00, 0x00}}},
    {{{   151,     26,    -31}, 0, {     0,      0}, {0x5a, 0xd7, 0xb1, 0x00}}},
    {{{    98,     22,    -60}, 0, {     0,      0}, {0x2f, 0x0e, 0x8c, 0x00}}},
    {{{   139,     71,    -49}, 0, {     0,      0}, {0x46, 0x33, 0xa4, 0x00}}},
    {{{    14,     21,    -64}, 0, {     0,      0}, {0xaf, 0x1e, 0xa4, 0x00}}},
    {{{    63,     40,    -62}, 0, {     0,      0}, {0x17, 0x3d, 0x94, 0x00}}},
    {{{    53,      2,    -86}, 0, {     0,      0}, {0xf2, 0x02, 0x82, 0x00}}},
    {{{    91,     82,    -51}, 0, {     0,      0}, {0x14, 0x35, 0x8f, 0x00}}},
    {{{    35,     83,      0}, 0, {     0,      0}, {0xdc, 0x79, 0x00, 0x00}}},
    {{{    82,     80,      0}, 0, {     0,      0}, {0x42, 0x6c, 0x00, 0x00}}},
    {{{    44,     74,    -32}, 0, {     0,      0}, {0xf1, 0x69, 0xbc, 0x00}}},
    {{{    86,    112,      0}, 0, {     0,      0}, {0x1f, 0x7b, 0x00, 0x00}}},
};

// 0x0600A4A0
static const Vtx koopa_seg6_vertex_0600A4A0[] = {
    {{{    10,     49,    -36}, 0, {     0,      0}, {0xac, 0x42, 0xbd, 0x00}}},
    {{{    63,     40,    -62}, 0, {     0,      0}, {0x17, 0x3d, 0x94, 0x00}}},
    {{{    14,     21,    -64}, 0, {     0,      0}, {0xaf, 0x1e, 0xa4, 0x00}}},
    {{{    75,    -16,     81}, 0, {     0,      0}, {0x2c, 0xe3, 0x73, 0x00}}},
    {{{    42,    -44,     54}, 0, {     0,      0}, {0xe4, 0xa5, 0x52, 0x00}}},
    {{{   101,    -44,     53}, 0, {     0,      0}, {0xf4, 0xae, 0x60, 0x00}}},
    {{{    44,     74,    -32}, 0, {     0,      0}, {0xf1, 0x69, 0xbc, 0x00}}},
    {{{    63,     40,     61}, 0, {     0,      0}, {0x17, 0x3d, 0x6c, 0x00}}},
    {{{    44,     74,     31}, 0, {     0,      0}, {0xf1, 0x69, 0x44, 0x00}}},
    {{{    10,     49,     35}, 0, {     0,      0}, {0xac, 0x42, 0x43, 0x00}}},
    {{{    68,    -65,     17}, 0, {     0,      0}, {0xe6, 0x8b, 0x28, 0x00}}},
    {{{    97,    -86,      0}, 0, {     0,      0}, {0xdc, 0x87, 0x00, 0x00}}},
    {{{   151,     26,     31}, 0, {     0,      0}, {0x5a, 0xd7, 0x4f, 0x00}}},
    {{{   139,     71,     48}, 0, {     0,      0}, {0x46, 0x33, 0x5c, 0x00}}},
    {{{    98,     22,     59}, 0, {     0,      0}, {0x2f, 0x0e, 0x74, 0x00}}},
    {{{    91,     82,     51}, 0, {     0,      0}, {0x14, 0x35, 0x71, 0x00}}},
};

// 0x0600A5A0
static const Vtx koopa_seg6_vertex_0600A5A0[] = {
    {{{    91,     82,    -51}, 0, {     0,      0}, {0x14, 0x35, 0x8f, 0x00}}},
    {{{   139,     71,    -49}, 0, {     0,      0}, {0x46, 0x33, 0xa4, 0x00}}},
    {{{    98,     22,    -60}, 0, {     0,      0}, {0x2f, 0x0e, 0x8c, 0x00}}},
    {{{    53,      2,    -86}, 0, {     0,      0}, {0xf2, 0x02, 0x82, 0x00}}},
    {{{    63,     40,    -62}, 0, {     0,      0}, {0x17, 0x3d, 0x94, 0x00}}},
    {{{    53,      2,     86}, 0, {     0,      0}, {0xf2, 0x02, 0x7e, 0x00}}},
    {{{    75,    -16,     81}, 0, {     0,      0}, {0x2c, 0xe3, 0x73, 0x00}}},
    {{{    98,     22,     59}, 0, {     0,      0}, {0x2f, 0x0e, 0x74, 0x00}}},
    {{{    23,    -24,     59}, 0, {     0,      0}, {0xad, 0xcb, 0x4f, 0x00}}},
    {{{    14,     21,     64}, 0, {     0,      0}, {0xaf, 0x1e, 0x5c, 0x00}}},
    {{{   -13,     -4,      0}, 0, {     0,      0}, {0x82, 0xf8, 0x00, 0x00}}},
    {{{    23,    -24,    -59}, 0, {     0,      0}, {0xad, 0xcb, 0xb1, 0x00}}},
    {{{    16,    -63,      0}, 0, {     0,      0}, {0xbc, 0x95, 0x00, 0x00}}},
    {{{    42,    -44,    -54}, 0, {     0,      0}, {0xe4, 0xa5, 0xae, 0x00}}},
    {{{    68,    -65,    -18}, 0, {     0,      0}, {0xe6, 0x8b, 0xd8, 0x00}}},
};

// 0x0600A690
static const Vtx koopa_seg6_vertex_0600A690[] = {
    {{{    42,    -44,     54}, 0, {     0,      0}, {0xe4, 0xa5, 0x52, 0x00}}},
    {{{    68,    -65,     17}, 0, {     0,      0}, {0xe6, 0x8b, 0x28, 0x00}}},
    {{{   101,    -44,     53}, 0, {     0,      0}, {0xf4, 0xae, 0x60, 0x00}}},
    {{{    53,      2,     86}, 0, {     0,      0}, {0xf2, 0x02, 0x7e, 0x00}}},
    {{{    23,    -24,     59}, 0, {     0,      0}, {0xad, 0xcb, 0x4f, 0x00}}},
    {{{    42,    -44,    -54}, 0, {     0,      0}, {0xe4, 0xa5, 0xae, 0x00}}},
    {{{    75,    -16,    -82}, 0, {     0,      0}, {0x2c, 0xe3, 0x8d, 0x00}}},
    {{{   101,    -44,    -54}, 0, {     0,      0}, {0xf4, 0xae, 0xa0, 0x00}}},
    {{{    53,      2,    -86}, 0, {     0,      0}, {0xf2, 0x02, 0x82, 0x00}}},
    {{{     0,     51,      0}, 0, {     0,      0}, {0x95, 0x44, 0x00, 0x00}}},
    {{{   -13,     -4,      0}, 0, {     0,      0}, {0x82, 0xf8, 0x00, 0x00}}},
    {{{    10,     49,     35}, 0, {     0,      0}, {0xac, 0x42, 0x43, 0x00}}},
    {{{    10,     49,    -36}, 0, {     0,      0}, {0xac, 0x42, 0xbd, 0x00}}},
    {{{   109,      2,     51}, 0, {     0,      0}, {0x39, 0x0c, 0x70, 0x00}}},
    {{{    75,    -16,     81}, 0, {     0,      0}, {0x2c, 0xe3, 0x73, 0x00}}},
    {{{    98,     22,     59}, 0, {     0,      0}, {0x2f, 0x0e, 0x74, 0x00}}},
};

// 0x0600A790
static const Vtx koopa_seg6_vertex_0600A790[] = {
    {{{    75,    -16,    -82}, 0, {     0,      0}, {0x2c, 0xe3, 0x8d, 0x00}}},
    {{{    98,     22,    -60}, 0, {     0,      0}, {0x2f, 0x0e, 0x8c, 0x00}}},
    {{{   109,      2,    -51}, 0, {     0,      0}, {0x39, 0x0c, 0x90, 0x00}}},
    {{{   101,    -44,    -54}, 0, {     0,      0}, {0xf4, 0xae, 0xa0, 0x00}}},
    {{{    97,    -86,      0}, 0, {     0,      0}, {0xdc, 0x87, 0x00, 0x00}}},
    {{{    68,    -65,    -18}, 0, {     0,      0}, {0xe6, 0x8b, 0xd8, 0x00}}},
    {{{    86,    112,      0}, 0, {     0,      0}, {0x1f, 0x7b, 0x00, 0x00}}},
    {{{   147,     97,      0}, 0, {     0,      0}, {0x56, 0x5d, 0x00, 0x00}}},
    {{{   139,     71,    -49}, 0, {     0,      0}, {0x46, 0x33, 0xa4, 0x00}}},
    {{{   168,     41,      0}, 0, {     0,      0}, {0x7e, 0xf8, 0x00, 0x00}}},
    {{{   151,     26,    -31}, 0, {     0,      0}, {0x5a, 0xd7, 0xb1, 0x00}}},
    {{{   139,     71,     48}, 0, {     0,      0}, {0x46, 0x33, 0x5c, 0x00}}},
    {{{   137,     -5,      0}, 0, {     0,      0}, {0x60, 0xae, 0x00, 0x00}}},
    {{{   151,     26,     31}, 0, {     0,      0}, {0x5a, 0xd7, 0x4f, 0x00}}},
    {{{    98,     22,     59}, 0, {     0,      0}, {0x2f, 0x0e, 0x74, 0x00}}},
    {{{   109,      2,     51}, 0, {     0,      0}, {0x39, 0x0c, 0x70, 0x00}}},
};

// 0x0600A890
static const Vtx koopa_seg6_vertex_0600A890[] = {
    {{{    91,     82,    -51}, 0, {     0,      0}, {0x84, 0x19, 0x00, 0x00}}},
    {{{    63,     40,    -62}, 0, {     0,      0}, {0x97, 0x46, 0x00, 0x00}}},
    {{{    63,     40,     61}, 0, {     0,      0}, {0x97, 0x46, 0x00, 0x00}}},
    {{{    91,     82,     51}, 0, {     0,      0}, {0x84, 0x19, 0x00, 0x00}}},
    {{{    10,     49,    -36}, 0, {     0,      0}, {0xac, 0x42, 0xbd, 0x00}}},
    {{{    14,     21,    -64}, 0, {     0,      0}, {0xaf, 0x1e, 0xa4, 0x00}}},
    {{{   -13,     -4,      0}, 0, {     0,      0}, {0x82, 0xf8, 0x00, 0x00}}},
    {{{    14,     21,     64}, 0, {     0,      0}, {0xaf, 0x1e, 0x5c, 0x00}}},
    {{{    10,     49,     35}, 0, {     0,      0}, {0xac, 0x42, 0x43, 0x00}}},
    {{{    16,    -63,      0}, 0, {     0,      0}, {0xbc, 0x95, 0x00, 0x00}}},
    {{{    68,    -65,    -18}, 0, {     0,      0}, {0xe6, 0x8b, 0xd8, 0x00}}},
    {{{    68,    -65,     17}, 0, {     0,      0}, {0xe6, 0x8b, 0x28, 0x00}}},
    {{{    97,    -86,      0}, 0, {     0,      0}, {0xdc, 0x87, 0x00, 0x00}}},
    {{{   139,     71,     48}, 0, {     0,      0}, {0x46, 0x33, 0x5c, 0x00}}},
    {{{   151,     26,     31}, 0, {     0,      0}, {0x5a, 0xd7, 0x4f, 0x00}}},
    {{{   168,     41,      0}, 0, {     0,      0}, {0x7e, 0xf8, 0x00, 0x00}}},
};

// 0x0600A990
static const Vtx koopa_seg6_vertex_0600A990[] = {
    {{{   139,     71,    -49}, 0, {     0,      0}, {0x46, 0x33, 0xa4, 0x00}}},
    {{{   147,     97,      0}, 0, {     0,      0}, {0x56, 0x5d, 0x00, 0x00}}},
    {{{   168,     41,      0}, 0, {     0,      0}, {0x7e, 0xf8, 0x00, 0x00}}},
};

// 0x0600A9C0
static const Vtx koopa_seg6_vertex_0600A9C0[] = {
    {{{   178,    -81,    -29}, 0, {     0,      0}, {0x4b, 0xab, 0xc8, 0x00}}},
    {{{   182,    -56,    -23}, 0, {     0,      0}, {0x6d, 0x22, 0xc9, 0x00}}},
    {{{   182,    -56,     23}, 0, {     0,      0}, {0x73, 0x18, 0x2d, 0x00}}},
    {{{   119,    -30,    -63}, 0, {     0,      0}, {0xf6, 0x07, 0x82, 0x00}}},
    {{{   101,    -44,    -54}, 0, {     0,      0}, {0xf4, 0xae, 0xa0, 0x00}}},
    {{{   109,      2,    -51}, 0, {     0,      0}, {0x39, 0x0c, 0x90, 0x00}}},
    {{{   109,      2,     51}, 0, {     0,      0}, {0x39, 0x0c, 0x70, 0x00}}},
    {{{   119,    -30,     63}, 0, {     0,      0}, {0xf6, 0x07, 0x7e, 0x00}}},
    {{{   131,    -11,     55}, 0, {     0,      0}, {0x41, 0x48, 0x51, 0x00}}},
    {{{   153,    -37,     48}, 0, {     0,      0}, {0x59, 0x2f, 0x4c, 0x00}}},
    {{{   137,    -52,     57}, 0, {     0,      0}, {0x24, 0xde, 0x74, 0x00}}},
    {{{   146,    -93,      0}, 0, {     0,      0}, {0xf9, 0x82, 0x00, 0x00}}},
    {{{   137,     -5,      0}, 0, {     0,      0}, {0x56, 0x5c, 0x00, 0x00}}},
    {{{   131,    -11,    -56}, 0, {     0,      0}, {0x41, 0x48, 0xaf, 0x00}}},
    {{{   178,    -81,     28}, 0, {     0,      0}, {0x36, 0xa2, 0x41, 0x00}}},
    {{{   101,    -44,     53}, 0, {     0,      0}, {0xf4, 0xae, 0x60, 0x00}}},
};

// 0x0600AAC0
static const Vtx koopa_seg6_vertex_0600AAC0[] = {
    {{{   119,    -30,    -63}, 0, {     0,      0}, {0xf6, 0x07, 0x82, 0x00}}},
    {{{   131,    -11,    -56}, 0, {     0,      0}, {0x41, 0x48, 0xaf, 0x00}}},
    {{{   153,    -37,    -49}, 0, {     0,      0}, {0x59, 0x2f, 0xb4, 0x00}}},
    {{{   137,    -52,     57}, 0, {     0,      0}, {0x24, 0xde, 0x74, 0x00}}},
    {{{   119,    -30,     63}, 0, {     0,      0}, {0xf6, 0x07, 0x7e, 0x00}}},
    {{{   101,    -44,     53}, 0, {     0,      0}, {0xf4, 0xae, 0x60, 0x00}}},
    {{{   146,    -93,      0}, 0, {     0,      0}, {0xf9, 0x82, 0x00, 0x00}}},
    {{{   178,    -81,     28}, 0, {     0,      0}, {0x36, 0xa2, 0x41, 0x00}}},
    {{{   178,    -81,    -29}, 0, {     0,      0}, {0x4b, 0xab, 0xc8, 0x00}}},
    {{{   101,    -44,    -54}, 0, {     0,      0}, {0xf4, 0xae, 0xa0, 0x00}}},
    {{{   137,    -52,    -58}, 0, {     0,      0}, {0x24, 0xde, 0x8c, 0x00}}},
    {{{   109,      2,    -51}, 0, {     0,      0}, {0x39, 0x0c, 0x90, 0x00}}},
    {{{   182,    -56,    -23}, 0, {     0,      0}, {0x6d, 0x22, 0xc9, 0x00}}},
    {{{   153,    -37,     48}, 0, {     0,      0}, {0x59, 0x2f, 0x4c, 0x00}}},
    {{{   131,    -11,     55}, 0, {     0,      0}, {0x41, 0x48, 0x51, 0x00}}},
};

// 0x0600ABB0
static const Vtx koopa_seg6_vertex_0600ABB0[] = {
    {{{   182,    -56,     23}, 0, {     0,      0}, {0x73, 0x18, 0x2d, 0x00}}},
    {{{   182,    -56,    -23}, 0, {     0,      0}, {0x6d, 0x22, 0xc9, 0x00}}},
    {{{   137,     -5,      0}, 0, {     0,      0}, {0x56, 0x5c, 0x00, 0x00}}},
    {{{   131,    -11,     55}, 0, {     0,      0}, {0x41, 0x48, 0x51, 0x00}}},
    {{{   153,    -37,     48}, 0, {     0,      0}, {0x59, 0x2f, 0x4c, 0x00}}},
    {{{   153,    -37,    -49}, 0, {     0,      0}, {0x59, 0x2f, 0xb4, 0x00}}},
    {{{   131,    -11,    -56}, 0, {     0,      0}, {0x41, 0x48, 0xaf, 0x00}}},
    {{{   101,    -44,    -54}, 0, {     0,      0}, {0xf4, 0xae, 0xa0, 0x00}}},
    {{{   119,    -30,    -63}, 0, {     0,      0}, {0xf6, 0x07, 0x82, 0x00}}},
    {{{   137,    -52,    -58}, 0, {     0,      0}, {0x24, 0xde, 0x8c, 0x00}}},
    {{{   137,    -52,     57}, 0, {     0,      0}, {0x24, 0xde, 0x74, 0x00}}},
    {{{   119,    -30,     63}, 0, {     0,      0}, {0xf6, 0x07, 0x7e, 0x00}}},
    {{{   178,    -81,     28}, 0, {     0,      0}, {0x36, 0xa2, 0x41, 0x00}}},
    {{{   178,    -81,    -29}, 0, {     0,      0}, {0x4b, 0xab, 0xc8, 0x00}}},
};

// 0x0600AC90
static const Vtx koopa_seg6_vertex_0600AC90[] = {
    {{{   -84,     25,      0}, 0, {     0,      0}, {0x96, 0x44, 0x00, 0x00}}},
    {{{  -109,      4,      0}, 0, {     0,      0}, {0xaf, 0x61, 0x00, 0x00}}},
    {{{   -71,     18,     85}, 0, {     0,      0}, {0xa7, 0x50, 0x27, 0x00}}},
    {{{   -71,     38,     56}, 0, {     0,      0}, {0xaa, 0x53, 0x29, 0x00}}},
    {{{    91,     27,     60}, 0, {     0,      0}, {0x5f, 0x3d, 0x38, 0x00}}},
    {{{    79,      8,     94}, 0, {     0,      0}, {0x43, 0x0c, 0x6a, 0x00}}},
    {{{   121,    -46,     31}, 0, {     0,      0}, {0x6d, 0xdb, 0x33, 0x00}}},
    {{{    51,     43,     78}, 0, {     0,      0}, {0x1d, 0x5f, 0x4e, 0x00}}},
    {{{   -28,     43,     78}, 0, {     0,      0}, {0xe4, 0x5b, 0x53, 0x00}}},
    {{{    -3,     23,    107}, 0, {     0,      0}, {0xee, 0x16, 0x7b, 0x00}}},
    {{{  -109,      4,      0}, 0, {     0,      0}, {0xa8, 0xa6, 0x00, 0x00}}},
    {{{   -85,    -18,      0}, 0, {     0,      0}, {0x98, 0xb9, 0x00, 0x00}}},
    {{{   -57,     -2,     73}, 0, {     0,      0}, {0xcc, 0xb7, 0x59, 0x00}}},
    {{{   -71,     18,     85}, 0, {     0,      0}, {0xc9, 0xad, 0x4d, 0x00}}},
    {{{    63,    -31,     77}, 0, {     0,      0}, {0x16, 0xb3, 0x61, 0x00}}},
    {{{    91,    -65,      0}, 0, {     0,      0}, {0x41, 0x94, 0x00, 0x00}}},
};

// 0x0600AD90
static const Vtx koopa_seg6_vertex_0600AD90[] = {
    {{{  -109,      4,      0}, 0, {     0,      0}, {0xa8, 0xa6, 0x00, 0x00}}},
    {{{   -57,     -2,    -74}, 0, {     0,      0}, {0xcc, 0xb7, 0xa7, 0x00}}},
    {{{   -85,    -18,      0}, 0, {     0,      0}, {0x98, 0xb9, 0x00, 0x00}}},
    {{{    91,    -65,      0}, 0, {     0,      0}, {0x41, 0x94, 0x00, 0x00}}},
    {{{   121,    -46,    -32}, 0, {     0,      0}, {0x33, 0x9b, 0xc8, 0x00}}},
    {{{   121,    -46,     31}, 0, {     0,      0}, {0x33, 0x9b, 0x38, 0x00}}},
    {{{   -84,     25,      0}, 0, {     0,      0}, {0x96, 0x44, 0x00, 0x00}}},
    {{{   -71,     18,    -86}, 0, {     0,      0}, {0xa7, 0x50, 0xd9, 0x00}}},
    {{{  -109,      4,      0}, 0, {     0,      0}, {0xaf, 0x61, 0x00, 0x00}}},
    {{{    -3,     23,   -108}, 0, {     0,      0}, {0xee, 0x16, 0x85, 0x00}}},
    {{{   -71,     38,    -57}, 0, {     0,      0}, {0xad, 0x52, 0xd0, 0x00}}},
    {{{    51,     43,    -79}, 0, {     0,      0}, {0x1d, 0x60, 0xb3, 0x00}}},
    {{{    91,     27,    -62}, 0, {     0,      0}, {0x5f, 0x3d, 0xc8, 0x00}}},
    {{{    79,      8,    -96}, 0, {     0,      0}, {0x43, 0x0c, 0x96, 0x00}}},
    {{{   -71,     18,    -86}, 0, {     0,      0}, {0xc9, 0xad, 0xb3, 0x00}}},
    {{{    63,    -31,    -78}, 0, {     0,      0}, {0x16, 0xb3, 0x9f, 0x00}}},
};

// 0x0600AE90
static const Vtx koopa_seg6_vertex_0600AE90[] = {
    {{{   121,    -46,    -32}, 0, {     0,      0}, {0x6d, 0xdb, 0xcd, 0x00}}},
    {{{    91,    -65,      0}, 0, {     0,      0}, {0x41, 0x94, 0x00, 0x00}}},
    {{{    63,    -31,    -78}, 0, {     0,      0}, {0x16, 0xb3, 0x9f, 0x00}}},
    {{{    79,      8,    -96}, 0, {     0,      0}, {0x43, 0x0c, 0x96, 0x00}}},
    {{{    -3,     23,   -108}, 0, {     0,      0}, {0xee, 0x16, 0x85, 0x00}}},
    {{{   -57,     -2,    -74}, 0, {     0,      0}, {0xcc, 0xb7, 0xa7, 0x00}}},
    {{{   -71,     18,    -86}, 0, {     0,      0}, {0xc9, 0xad, 0xb3, 0x00}}},
    {{{   -71,     38,    -57}, 0, {     0,      0}, {0xad, 0x52, 0xd0, 0x00}}},
    {{{   -28,     43,    -79}, 0, {     0,      0}, {0xe4, 0x5b, 0xae, 0x00}}},
    {{{    51,     43,    -79}, 0, {     0,      0}, {0x1d, 0x60, 0xb3, 0x00}}},
    {{{    91,     27,    -62}, 0, {     0,      0}, {0x5f, 0x3d, 0xc8, 0x00}}},
    {{{   -71,     18,    -86}, 0, {     0,      0}, {0xa7, 0x50, 0xd9, 0x00}}},
    {{{   -84,     25,      0}, 0, {     0,      0}, {0x96, 0x44, 0x00, 0x00}}},
};

// 0x0600AF60
static const Vtx koopa_seg6_vertex_0600AF60[] = {
    {{{   -85,    -18,      0}, 0, {     0,      0}, {0x98, 0xb9, 0x00, 0x00}}},
    {{{   -32,    -96,     33}, 0, {     0,      0}, {0xb7, 0xa9, 0x37, 0x00}}},
    {{{   -57,     -2,     73}, 0, {     0,      0}, {0xcc, 0xb7, 0x59, 0x00}}},
    {{{    32,   -106,    -34}, 0, {     0,      0}, {0x1c, 0x9f, 0xb4, 0x00}}},
    {{{    91,    -65,      0}, 0, {     0,      0}, {0x41, 0x94, 0x00, 0x00}}},
    {{{    32,   -106,     33}, 0, {     0,      0}, {0x1c, 0x9f, 0x4c, 0x00}}},
    {{{   -32,    -96,    -34}, 0, {     0,      0}, {0xb7, 0xa9, 0xc9, 0x00}}},
    {{{    63,    -31,     77}, 0, {     0,      0}, {0x16, 0xb3, 0x61, 0x00}}},
    {{{   -57,     -2,    -74}, 0, {     0,      0}, {0xcc, 0xb7, 0xa7, 0x00}}},
    {{{    63,    -31,    -78}, 0, {     0,      0}, {0x16, 0xb3, 0x9f, 0x00}}},
};

// 0x0600B000
static const Vtx koopa_seg6_vertex_0600B000[] = {
    {{{   -43,     70,     40}, 0, {     0,      0}, {0xe1, 0x71, 0x30, 0x00}}},
    {{{    51,     43,     78}, 0, {     0,      0}, {0x1d, 0x5f, 0x4e, 0x00}}},
    {{{    68,     63,     39}, 0, {     0,      0}, {0x2d, 0x71, 0x20, 0x00}}},
    {{{    91,     27,    -62}, 0, {     0,      0}, {0x5f, 0x3d, 0xc8, 0x00}}},
    {{{    68,     63,    -40}, 0, {     0,      0}, {0x34, 0x6d, 0xdb, 0x00}}},
    {{{   109,     39,      0}, 0, {     0,      0}, {0x6f, 0x3c, 0x00, 0x00}}},
    {{{    51,     43,    -79}, 0, {     0,      0}, {0x1d, 0x60, 0xb3, 0x00}}},
    {{{   -28,     43,    -79}, 0, {     0,      0}, {0xe4, 0x5b, 0xae, 0x00}}},
    {{{   -71,     38,    -57}, 0, {     0,      0}, {0xad, 0x52, 0xd0, 0x00}}},
    {{{   -43,     70,    -40}, 0, {     0,      0}, {0xda, 0x73, 0xde, 0x00}}},
    {{{    91,     27,     60}, 0, {     0,      0}, {0x5f, 0x3d, 0x38, 0x00}}},
    {{{   -71,     38,     56}, 0, {     0,      0}, {0xaa, 0x53, 0x29, 0x00}}},
    {{{   -28,     43,     78}, 0, {     0,      0}, {0xe4, 0x5b, 0x53, 0x00}}},
    {{{   -84,     25,      0}, 0, {     0,      0}, {0x96, 0x44, 0x00, 0x00}}},
};

// 0x0600B0E0
static const Vtx koopa_seg6_vertex_0600B0E0[] = {
    {{{   109,     39,      0}, 0, {     0,      0}, {0x64, 0x4d, 0x00, 0x00}}},
    {{{    91,     27,     60}, 0, {     0,      0}, {0x63, 0x3d, 0x30, 0x00}}},
    {{{   121,    -46,     31}, 0, {     0,      0}, {0x79, 0x1a, 0x1b, 0x00}}},
    {{{   121,    -46,    -32}, 0, {     0,      0}, {0x79, 0x1a, 0xe5, 0x00}}},
    {{{    91,     27,    -62}, 0, {     0,      0}, {0x63, 0x3d, 0xd0, 0x00}}},
    {{{   -71,     38,     56}, 0, {     0,      0}, {0xaa, 0x53, 0x29, 0x00}}},
    {{{   -43,     70,     40}, 0, {     0,      0}, {0xe1, 0x71, 0x30, 0x00}}},
    {{{   -43,     70,    -40}, 0, {     0,      0}, {0xda, 0x73, 0xde, 0x00}}},
    {{{   -71,     38,    -57}, 0, {     0,      0}, {0xad, 0x52, 0xd0, 0x00}}},
    {{{    68,     63,     39}, 0, {     0,      0}, {0x2d, 0x71, 0x20, 0x00}}},
    {{{    68,     63,    -40}, 0, {     0,      0}, {0x34, 0x6d, 0xdb, 0x00}}},
};

// 0x0600B190
static const Vtx koopa_seg6_vertex_0600B190[] = {
    {{{    38,     -9,    -69}, 0, {     0,      0}, {0x1f, 0x1d, 0x89, 0x00}}},
    {{{   -16,     -3,    -77}, 0, {     0,      0}, {0xe7, 0xb7, 0x9c, 0x00}}},
    {{{   -42,     55,    -74}, 0, {     0,      0}, {0xd1, 0x2d, 0x94, 0x00}}},
    {{{    29,     49,    -50}, 0, {     0,      0}, {0x1f, 0x62, 0xb7, 0xff}}},
    {{{   116,      0,      0}, 0, {     0,      0}, {0x76, 0x2a, 0xf2, 0xff}}},
    {{{   115,    -22,     17}, 0, {     0,      0}, {0x5e, 0xc3, 0x39, 0xff}}},
    {{{   115,    -22,    -17}, 0, {     0,      0}, {0x5e, 0xbb, 0xcf, 0xff}}},
    {{{    38,     -9,     69}, 0, {     0,      0}, {0x15, 0x97, 0x43, 0xff}}},
    {{{    69,     -7,     58}, 0, {     0,      0}, {0x21, 0xa7, 0x54, 0xff}}},
    {{{    61,     25,     88}, 0, {     0,      0}, {0x57, 0x12, 0x5a, 0xff}}},
    {{{   -32,     71,     39}, 0, {     0,      0}, {0x16, 0x7b, 0x13, 0xff}}},
    {{{    71,     55,     19}, 0, {     0,      0}, {0x35, 0x6c, 0x27, 0xff}}},
    {{{    71,     55,    -19}, 0, {     0,      0}, {0x33, 0x70, 0xe5, 0xff}}},
    {{{   -32,     71,    -39}, 0, {     0,      0}, {0x1a, 0x75, 0xd8, 0xff}}},
};

// 0x0600B270
static const Vtx koopa_seg6_vertex_0600B270[] = {
    {{{    13,    -27,    -42}, 0, {     0,      0}, {0xff, 0x88, 0xda, 0xff}}},
    {{{    65,    -37,    -31}, 0, {     0,      0}, {0x15, 0x90, 0xca, 0x00}}},
    {{{    65,    -37,     31}, 0, {     0,      0}, {0x0c, 0x87, 0x21, 0x00}}},
    {{{    13,    -27,     35}, 0, {     0,      0}, {0x0d, 0x89, 0x29, 0xff}}},
    {{{    29,     49,    -50}, 0, {     0,      0}, {0x1f, 0x62, 0xb7, 0xff}}},
    {{{   -42,     55,    -74}, 0, {     0,      0}, {0xd1, 0x2d, 0x94, 0xff}}},
    {{{   -32,     71,    -39}, 0, {     0,      0}, {0x1a, 0x75, 0xd8, 0xff}}},
    {{{    71,     55,    -19}, 0, {     0,      0}, {0x33, 0x70, 0xe5, 0xff}}},
    {{{   116,      0,      0}, 0, {     0,      0}, {0x76, 0x2a, 0xf2, 0xff}}},
    {{{   115,    -22,    -17}, 0, {     0,      0}, {0x5e, 0xbb, 0xcf, 0xff}}},
    {{{    69,     -7,    -58}, 0, {     0,      0}, {0x44, 0xcd, 0xa3, 0xff}}},
    {{{    62,     26,    -88}, 0, {     0,      0}, {0x57, 0x15, 0xa7, 0xff}}},
    {{{   115,    -22,     17}, 0, {     0,      0}, {0x5e, 0xc3, 0x39, 0xff}}},
    {{{    71,     55,     19}, 0, {     0,      0}, {0x35, 0x6c, 0x27, 0xff}}},
    {{{    61,     25,     88}, 0, {     0,      0}, {0x57, 0x12, 0x5a, 0xff}}},
    {{{    69,     -7,     58}, 0, {     0,      0}, {0x21, 0xa7, 0x54, 0xff}}},
};

// 0x0600B370
static const Vtx koopa_seg6_vertex_0600B370[] = {
    {{{    13,    -27,     35}, 0, {     0,      0}, {0x0d, 0x89, 0x29, 0xff}}},
    {{{    69,     -7,     58}, 0, {     0,      0}, {0x21, 0xa7, 0x54, 0x00}}},
    {{{    38,     -9,     69}, 0, {     0,      0}, {0x15, 0x97, 0x43, 0x00}}},
    {{{    29,     49,     50}, 0, {     0,      0}, {0x06, 0x46, 0x69, 0xff}}},
    {{{    61,     25,     88}, 0, {     0,      0}, {0x57, 0x12, 0x5a, 0xff}}},
    {{{    71,     55,     19}, 0, {     0,      0}, {0x35, 0x6c, 0x27, 0xff}}},
    {{{   116,      0,      0}, 0, {     0,      0}, {0x76, 0x2a, 0xf2, 0xff}}},
    {{{    62,     26,    -88}, 0, {     0,      0}, {0x57, 0x15, 0xa7, 0xff}}},
    {{{    71,     55,    -19}, 0, {     0,      0}, {0x33, 0x70, 0xe5, 0xff}}},
    {{{    69,     -7,    -58}, 0, {     0,      0}, {0x44, 0xcd, 0xa3, 0xff}}},
    {{{   115,    -22,    -17}, 0, {     0,      0}, {0x5e, 0xbb, 0xcf, 0xff}}},
    {{{    65,    -37,    -31}, 0, {     0,      0}, {0x15, 0x90, 0xca, 0xff}}},
    {{{   115,    -22,     17}, 0, {     0,      0}, {0x5e, 0xc3, 0x39, 0xff}}},
    {{{    65,    -37,     31}, 0, {     0,      0}, {0x0c, 0x87, 0x21, 0xff}}},
    {{{    38,     -9,    -69}, 0, {     0,      0}, {0x13, 0xa3, 0xad, 0xff}}},
    {{{    13,    -27,    -42}, 0, {     0,      0}, {0xff, 0x88, 0xda, 0xff}}},
};

// 0x0600B470
static const Vtx koopa_seg6_vertex_0600B470[] = {
    {{{    29,     49,     50}, 0, {     0,      0}, {0x06, 0x46, 0x69, 0xff}}},
    {{{   -42,     55,     74}, 0, {     0,      0}, {0x24, 0x55, 0x56, 0x00}}},
    {{{   -16,     -3,     77}, 0, {     0,      0}, {0x20, 0x21, 0x76, 0x00}}},
    {{{    38,     -9,     69}, 0, {     0,      0}, {0xd9, 0x1e, 0x74, 0xff}}},
    {{{    38,     -9,    -69}, 0, {     0,      0}, {0xa5, 0x0c, 0xaa, 0xff}}},
    {{{    29,     49,    -50}, 0, {     0,      0}, {0xa5, 0x0c, 0xaa, 0xff}}},
    {{{    62,     26,    -88}, 0, {     0,      0}, {0xa5, 0x0c, 0xaa, 0xff}}},
    {{{    71,     55,     19}, 0, {     0,      0}, {0x35, 0x6c, 0x27, 0xff}}},
    {{{   -32,     71,     39}, 0, {     0,      0}, {0x16, 0x7b, 0x13, 0xff}}},
    {{{    71,     55,    -19}, 0, {     0,      0}, {0x33, 0x70, 0xe5, 0xff}}},
    {{{    62,     26,    -88}, 0, {     0,      0}, {0x57, 0x15, 0xa7, 0xff}}},
    {{{    29,     49,    -50}, 0, {     0,      0}, {0x1f, 0x62, 0xb7, 0xff}}},
    {{{   -16,     -3,    -77}, 0, {     0,      0}, {0xe7, 0xb7, 0x9c, 0xff}}},
    {{{    38,     -9,    -69}, 0, {     0,      0}, {0x13, 0xa3, 0xad, 0xff}}},
    {{{    13,    -27,    -42}, 0, {     0,      0}, {0xff, 0x88, 0xda, 0xff}}},
};

// 0x0600B560
static const Vtx koopa_seg6_vertex_0600B560[] = {
    {{{    13,    -27,     35}, 0, {     0,      0}, {0x0d, 0x89, 0x29, 0xff}}},
    {{{    38,     -9,     69}, 0, {     0,      0}, {0x15, 0x97, 0x43, 0x00}}},
    {{{   -16,     -3,     77}, 0, {     0,      0}, {0xd0, 0xb0, 0x55, 0x00}}},
    {{{    61,     25,     88}, 0, {     0,      0}, {0xa4, 0x0c, 0x55, 0xff}}},
    {{{    29,     49,     50}, 0, {     0,      0}, {0x06, 0x46, 0x69, 0xff}}},
    {{{    38,     -9,     69}, 0, {     0,      0}, {0xd9, 0x1e, 0x74, 0xff}}},
    {{{    62,     26,    -88}, 0, {     0,      0}, {0x57, 0x15, 0xa7, 0xff}}},
    {{{    69,     -7,    -58}, 0, {     0,      0}, {0x44, 0xcd, 0xa3, 0xff}}},
    {{{    38,     -9,    -69}, 0, {     0,      0}, {0x13, 0xa3, 0xad, 0xff}}},
};

// 0x0600B5F0
static const Vtx koopa_seg6_vertex_0600B5F0[] = {
    {{{   -63,    -17,    -28}, 0, {     0,      0}, {0x95, 0xce, 0xd3, 0x00}}},
    {{{   -42,     55,    -74}, 0, {     0,      0}, {0xd1, 0x2d, 0x94, 0x00}}},
    {{{   -16,     -3,    -77}, 0, {     0,      0}, {0xe7, 0xb7, 0x9c, 0x00}}},
    {{{   -16,     -3,     77}, 0, {     0,      0}, {0xd0, 0xb0, 0x55, 0xff}}},
    {{{   -63,    -17,     28}, 0, {     0,      0}, {0xa5, 0xb2, 0x26, 0xff}}},
    {{{   -24,    -39,     28}, 0, {     0,      0}, {0xf1, 0x89, 0x28, 0xff}}},
    {{{   -65,     24,     35}, 0, {     0,      0}, {0x87, 0x0f, 0x21, 0xff}}},
    {{{   -65,     24,    -34}, 0, {     0,      0}, {0x8a, 0x2a, 0xf0, 0xff}}},
    {{{    13,    -27,     35}, 0, {     0,      0}, {0x0d, 0x89, 0x29, 0xff}}},
    {{{   -24,    -39,    -28}, 0, {     0,      0}, {0xef, 0x86, 0xe5, 0xff}}},
    {{{    13,    -27,    -42}, 0, {     0,      0}, {0xff, 0x88, 0xda, 0xff}}},
    {{{   -42,     55,     74}, 0, {     0,      0}, {0xa5, 0xdc, 0x50, 0xff}}},
    {{{   -32,     71,     39}, 0, {     0,      0}, {0x98, 0x48, 0x01, 0xff}}},
    {{{   -32,     71,    -39}, 0, {     0,      0}, {0x98, 0x48, 0xfe, 0xff}}},
    {{{   -42,     55,     74}, 0, {     0,      0}, {0x98, 0x48, 0x04, 0xff}}},
};

// 0x0600B6E0 - 0x0600B838
const Gfx koopa_seg6_dl_0600B6E0[] = {
    gsSPLight(&koopa_seg6_lights_060025A0.l, 1),
    gsSPLight(&koopa_seg6_lights_060025A0.a, 2),
    gsSPVertex(koopa_seg6_vertex_06008BC0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(koopa_seg6_vertex_06008CC0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  2,  7,  8, 0x0),
    gsSP2Triangles( 9, 10,  4, 0x0,  9,  4,  6, 0x0),
    gsSP2Triangles( 2,  8, 11, 0x0,  2, 11,  3, 0x0),
    gsSP2Triangles( 3, 12, 13, 0x0,  3, 13,  0, 0x0),
    gsSPVertex(koopa_seg6_vertex_06008DA0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9,  5, 0x0,  8,  5, 10, 0x0),
    gsSP2Triangles(11, 12,  9, 0x0, 11,  9, 13, 0x0),
    gsSPVertex(koopa_seg6_vertex_06008E80, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 6,  7,  9, 0x0,  6,  9,  4, 0x0),
    gsSP2Triangles(10,  0,  3, 0x0, 10,  3, 11, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0, 11, 13, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600B838 - 0x0600B8B8
const Gfx koopa_seg6_dl_0600B838[] = {
    gsSPLight(&koopa_seg6_lights_060025B8.l, 1),
    gsSPLight(&koopa_seg6_lights_060025B8.a, 2),
    gsSPVertex(koopa_seg6_vertex_06008F60, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0,  7, 10, 11, 0x0),
    gsSP2Triangles(12,  4,  6, 0x0, 12,  6, 13, 0x0),
    gsSP2Triangles( 5,  0,  3, 0x0,  5,  3,  6, 0x0),
    gsSP2Triangles( 1, 12, 13, 0x0,  1, 13,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600B8B8 - 0x0600BA10
const Gfx koopa_seg6_dl_0600B8B8[] = {
    gsSPLight(&koopa_seg6_lights_060025A0.l, 1),
    gsSPLight(&koopa_seg6_lights_060025A0.a, 2),
    gsSPVertex(koopa_seg6_vertex_06009040, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(koopa_seg6_vertex_06009140, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  1, 0x0),
    gsSP2Triangles( 4,  6,  9, 0x0,  4,  9, 10, 0x0),
    gsSP2Triangles( 0, 11,  7, 0x0,  0,  7,  1, 0x0),
    gsSP2Triangles( 3, 12, 13, 0x0,  3, 13,  0, 0x0),
    gsSP1Triangle(10, 14, 15, 0x0),
    gsSPVertex(koopa_seg6_vertex_06009240, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  5,  8, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0, 10,  8, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(koopa_seg6_vertex_06009340, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  5,  6,  8, 0x0),
    gsSP2Triangles( 5,  8,  3, 0x0,  9,  0,  2, 0x0),
    gsSP2Triangles( 9,  2, 10, 0x0, 10, 11, 12, 0x0),
    gsSP1Triangle(10, 12,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600BA10 - 0x0600BA90
const Gfx koopa_seg6_dl_0600BA10[] = {
    gsSPLight(&koopa_seg6_lights_060025B8.l, 1),
    gsSPLight(&koopa_seg6_lights_060025B8.a, 2),
    gsSPVertex(koopa_seg6_vertex_06009410, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0,  7, 10, 11, 0x0),
    gsSP2Triangles(12,  4,  6, 0x0, 12,  6, 13, 0x0),
    gsSP2Triangles( 4,  0,  3, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 1, 12, 13, 0x0,  1, 13,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600BA90 - 0x0600BC18
const Gfx koopa_seg6_dl_0600BA90[] = {
    gsSPLight(&koopa_seg6_lights_060025B8.l, 1),
    gsSPLight(&koopa_seg6_lights_060025B8.a, 2),
    gsSPVertex(koopa_seg6_vertex_060094F0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSPVertex(koopa_seg6_vertex_060095D0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  3,  9, 10, 0x0),
    gsSP2Triangles(10,  9,  0, 0x0, 11,  1, 12, 0x0),
    gsSP1Triangle( 1, 13, 14, 0x0),
    gsSPVertex(koopa_seg6_vertex_060096C0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  7,  9,  8, 0x0),
    gsSP2Triangles( 2, 10, 11, 0x0, 12,  3,  5, 0x0),
    gsSP2Triangles(12,  8, 13, 0x0, 14,  0, 11, 0x0),
    gsSPVertex(koopa_seg6_vertex_060097B0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  5,  8, 0x0),
    gsSP2Triangles( 9,  8, 10, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(10,  4, 12, 0x0, 12, 13, 10, 0x0),
    gsSP1Triangle( 8, 14,  7, 0x0),
    gsSPVertex(koopa_seg6_vertex_060098A0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10,  0, 0x0),
    gsSP2Triangles(11, 12,  7, 0x0, 11,  7, 13, 0x0),
    gsSP1Triangle(14, 15,  6, 0x0),
    gsSPVertex(koopa_seg6_vertex_060099A0, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  7,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600BC18 - 0x0600BC88
const Gfx koopa_seg6_dl_0600BC18[] = {
    gsSPLight(&koopa_seg6_lights_060025B8.l, 1),
    gsSPLight(&koopa_seg6_lights_060025B8.a, 2),
    gsSPVertex(koopa_seg6_vertex_06009A30, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 1,  4,  5, 0x0,  1,  5,  2, 0x0),
    gsSP2Triangles( 4,  6,  7, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 6,  8,  9, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles( 8,  0,  3, 0x0,  8,  3,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600BC88 - 0x0600BE10
const Gfx koopa_seg6_dl_0600BC88[] = {
    gsSPLight(&koopa_seg6_lights_060025B8.l, 1),
    gsSPLight(&koopa_seg6_lights_060025B8.a, 2),
    gsSPVertex(koopa_seg6_vertex_06009AD0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSPVertex(koopa_seg6_vertex_06009BB0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10,  5, 0x0),
    gsSP2Triangles( 2, 10,  9, 0x0, 11,  1, 12, 0x0),
    gsSP1Triangle(13, 14,  1, 0x0),
    gsSPVertex(koopa_seg6_vertex_06009CA0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles(10, 11,  0, 0x0,  3,  5, 12, 0x0),
    gsSP2Triangles(13,  6, 12, 0x0, 10,  2, 14, 0x0),
    gsSPVertex(koopa_seg6_vertex_06009D90, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  4,  8, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0,  9,  7, 11, 0x0),
    gsSP2Triangles(12,  5, 10, 0x0, 10, 13, 12, 0x0),
    gsSP1Triangle( 8, 14,  7, 0x0),
    gsSPVertex(koopa_seg6_vertex_06009E80, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  2,  9, 10, 0x0),
    gsSP2Triangles(11,  7, 12, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle( 8, 14, 15, 0x0),
    gsSPVertex(koopa_seg6_vertex_06009F80, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  7,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600BE10 - 0x0600BE80
const Gfx koopa_seg6_dl_0600BE10[] = {
    gsSPLight(&koopa_seg6_lights_060025B8.l, 1),
    gsSPLight(&koopa_seg6_lights_060025B8.a, 2),
    gsSPVertex(koopa_seg6_vertex_0600A010, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 1,  4,  5, 0x0,  1,  5,  2, 0x0),
    gsSP2Triangles( 4,  6,  7, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 6,  8,  9, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles( 8,  0,  3, 0x0,  8,  3,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600BE80 - 0x0600C240
const Gfx koopa_seg6_dl_0600BE80[] = {
    gsSPLight(&koopa_seg6_lights_060025B8.l, 1),
    gsSPLight(&koopa_seg6_lights_060025B8.a, 2),
    gsSPVertex(koopa_seg6_vertex_0600A0B0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 1,  4,  5, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 5,  9,  2, 0x0,  3,  2,  9, 0x0),
    gsSP2Triangles(10,  6,  8, 0x0, 11,  5,  4, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600A1A0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  3,  8, 0x0),
    gsSP2Triangles( 1,  9,  8, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0,  2, 14, 15, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600A2A0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 13, 14, 0x0),
    gsSP1Triangle(12, 14, 15, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600A3A0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles( 9, 11,  6, 0x0, 12, 13, 14, 0x0),
    gsSP2Triangles(14, 13,  9, 0x0,  7, 11, 15, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600A4A0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 0,  6,  1, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 5, 10, 11, 0x0, 12, 13, 14, 0x0),
    gsSP1Triangle(15,  7, 14, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600A5A0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles(11, 12, 10, 0x0, 12, 13, 14, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600A690, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  8,  6,  5, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0,  9, 12, 10, 0x0),
    gsSP2Triangles(13, 14,  2, 0x0, 13, 15, 14, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600A790, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  5,  3, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 9, 10,  8, 0x0, 11,  7,  6, 0x0),
    gsSP2Triangles(11,  9,  7, 0x0, 10,  9, 12, 0x0),
    gsSP2Triangles(12,  9, 13, 0x0,  2, 10, 12, 0x0),
    gsSP2Triangles( 2,  1, 10, 0x0, 13, 14, 15, 0x0),
    gsSP1Triangle(15, 12, 13, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600A890, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0, 10, 12, 11, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600A990, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPLight(&koopa_seg6_lights_060025D0.l, 1),
    gsSPLight(&koopa_seg6_lights_060025D0.a, 2),
    gsSPVertex(koopa_seg6_vertex_0600A9C0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  2,  9, 10, 0x0),
    gsSP2Triangles( 4,  0, 11, 0x0, 12, 13,  5, 0x0),
    gsSP2Triangles( 6,  8, 12, 0x0, 11,  0, 14, 0x0),
    gsSP2Triangles( 6, 15,  7, 0x0, 10, 14,  2, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600AAC0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles( 0,  2, 10, 0x0, 11,  1,  0, 0x0),
    gsSP2Triangles(12,  8, 10, 0x0,  3, 13, 14, 0x0),
    gsSP2Triangles(10,  2, 12, 0x0,  7,  3,  5, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600ABB0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 5,  6,  2, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  3, 11, 0x0,  0, 12, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600C240 - 0x0600C498
const Gfx koopa_seg6_dl_0600C240[] = {
    gsSPLight(&koopa_seg6_lights_06002618.l, 1),
    gsSPLight(&koopa_seg6_lights_06002618.a, 2),
    gsSPVertex(koopa_seg6_vertex_0600AC90, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  5,  4, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  8,  3,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0,  9, 13, 12, 0x0),
    gsSP2Triangles( 5,  9, 14, 0x0,  6,  5, 14, 0x0),
    gsSP2Triangles(14, 15,  6, 0x0, 12, 14,  9, 0x0),
    gsSP2Triangles(12, 13, 10, 0x0,  7,  9,  5, 0x0),
    gsSP1Triangle( 3,  2,  9, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600AD90, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9,  7, 10, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0,  0, 14,  1, 0x0),
    gsSP1Triangle( 9, 15,  1, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600AE90, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 2,  4,  3, 0x0,  5,  6,  4, 0x0),
    gsSP2Triangles( 4,  7,  8, 0x0,  4,  8,  9, 0x0),
    gsSP2Triangles( 3,  4,  9, 0x0,  0,  3, 10, 0x0),
    gsSP1Triangle(11, 12,  7, 0x0),
    gsSPLight(&koopa_seg6_lights_06002600.l, 1),
    gsSPLight(&koopa_seg6_lights_06002600.a, 2),
    gsSPVertex(koopa_seg6_vertex_0600AF60, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  6,  5,  1, 0x0),
    gsSP2Triangles( 7,  5,  4, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 3,  8,  9, 0x0,  4,  3,  9, 0x0),
    gsSP2Triangles( 7,  2,  5, 0x0,  6,  0,  8, 0x0),
    gsSP2Triangles( 8,  3,  6, 0x0,  6,  1,  0, 0x0),
    gsSPLight(&koopa_seg6_lights_060025B8.l, 1),
    gsSPLight(&koopa_seg6_lights_060025B8.a, 2),
    gsSPVertex(koopa_seg6_vertex_0600B000, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 1, 10,  2, 0x0,  0, 11, 12, 0x0),
    gsSP2Triangles( 6,  7,  9, 0x0,  0, 12,  1, 0x0),
    gsSP2Triangles( 2,  5,  4, 0x0,  9,  4,  6, 0x0),
    gsSP2Triangles( 5,  2, 10, 0x0,  9,  0,  2, 0x0),
    gsSP1Triangle( 8, 13, 11, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600B0E0, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 2,  3,  0, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 7,  8,  5, 0x0,  9, 10,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600C498 - 0x0600C6A0
const Gfx koopa_seg6_dl_0600C498[] = {
    gsSPLight(&koopa_seg6_lights_06002630.l, 1),
    gsSPLight(&koopa_seg6_lights_06002630.a, 2),
    gsSPVertex(koopa_seg6_vertex_0600B190, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600B270, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles( 2,  1,  9, 0x0,  2,  9, 12, 0x0),
    gsSP2Triangles( 7, 13,  8, 0x0, 14, 15, 12, 0x0),
    gsSP2Triangles(14, 12,  8, 0x0,  3,  2, 15, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600B370, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0, 12,  1, 13, 0x0),
    gsSP2Triangles(14,  9, 11, 0x0, 14, 11, 15, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600B470, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  1, 0x0),
    gsSP2Triangles( 7,  1,  0, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(koopa_seg6_vertex_0600B560, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  7,  8, 0x0),
    gsSPLight((u8*)koopa_seg6_texture_06002648 + 0x20, 1), //! this malformed light results in a
    gsSPLight((u8*)koopa_seg6_texture_06002648 + 0x18, 2), //! koopa appearing to wear pink shorts.
    gsSPVertex(koopa_seg6_vertex_0600B5F0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  0, 0x0,  8,  5,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  3,  5,  8, 0x0),
    gsSP2Triangles( 6,  0,  4, 0x0,  6,  4,  3, 0x0),
    gsSP2Triangles( 6,  3, 11, 0x0,  9,  5,  4, 0x0),
    gsSP2Triangles( 9,  4,  0, 0x0, 12, 13,  7, 0x0),
    gsSP2Triangles(12,  7,  6, 0x0,  1,  7, 13, 0x0),
    gsSP2Triangles(10,  9,  2, 0x0,  0,  2,  9, 0x0),
    gsSP2Triangles( 0,  7,  1, 0x0,  6, 14, 12, 0x0),
    gsSPEndDisplayList(),
};
