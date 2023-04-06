// Mips

// 0x0600FB80
ALIGNED8 static const u8 mips_seg6_texture_0600FB80[] = {
#include "actors/mips/mips_eyes.rgba16.inc.c"
};

// 0x06010380
static const Lights1 mips_seg6_lights_06010380 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06010398
static const Lights1 mips_seg6_lights_06010398 = gdSPDefLights1(
    0x0f, 0x0d, 0x04,
    0x27, 0x21, 0x0b, 0x28, 0x28, 0x28
);

// 0x060103B0
static const Vtx mips_seg6_vertex_060103B0[] = {
    {{{    60,     25,    -62}, 0, {  -146,  -1034}, {0x1d, 0x21, 0x8a, 0xff}}},
    {{{   102,     15,    -32}, 0, {   144,  -1598}, {0x65, 0x0d, 0xb5, 0xff}}},
    {{{    46,    -61,    -46}, 0, {     8,   -848}, {0x23, 0x9a, 0xbf, 0xff}}},
    {{{     4,    -54,    -41}, 0, {    56,   -296}, {0xb2, 0xbf, 0xb5, 0xff}}},
    {{{   100,    -30,      0}, 0, {   458,  -1572}, {0x6b, 0xbd, 0x00, 0xff}}},
    {{{     4,    -54,     42}, 0, {   858,   -296}, {0xb9, 0xac, 0x3e, 0xff}}},
    {{{    46,    -61,     48}, 0, {   906,   -848}, {0x2f, 0xa8, 0x4d, 0xff}}},
    {{{    60,     25,     64}, 0, {  1062,  -1034}, {0x1c, 0x21, 0x77, 0xff}}},
    {{{    -7,     16,      0}, 0, {   458,   -128}, {0x84, 0x17, 0x00, 0xff}}},
    {{{     2,      8,     67}, 0, {  1094,   -262}, {0xae, 0x16, 0x5d, 0xff}}},
    {{{     2,      8,    -66}, 0, {  -178,   -262}, {0xaf, 0x18, 0xa3, 0xff}}},
    {{{   102,     15,     33}, 0, {   770,  -1598}, {0x66, 0x0d, 0x4a, 0xff}}},
    {{{   115,     13,      0}, 0, {   458,  -1770}, {0x74, 0x31, 0x00, 0xff}}},
    {{{    16,     44,     47}, 0, {   902,   -454}, {0xd0, 0x5d, 0x47, 0xff}}},
    {{{    16,     44,    -45}, 0, {    14,   -454}, {0xd1, 0x5c, 0xb8, 0xff}}},
    {{{     9,     60,      0}, 0, {   458,   -352}, {0xa2, 0x54, 0x00, 0xff}}},
};

// 0x060104B0
static const Vtx mips_seg6_vertex_060104B0[] = {
    {{{   115,     13,      0}, 0, {   468,      0}, {0x74, 0x31, 0x00, 0xff}}},
    {{{    64,     56,    -13}, 0, {   308,    632}, {0x45, 0x59, 0xc6, 0xff}}},
    {{{    64,     56,     15}, 0, {   632,    632}, {0x44, 0x5a, 0x38, 0xff}}},
    {{{   102,     15,    -32}, 0, {   104,     80}, {0x65, 0x0d, 0xb5, 0xff}}},
    {{{   102,     15,     33}, 0, {   834,     80}, {0x66, 0x0d, 0x4a, 0xff}}},
    {{{    16,     44,    -45}, 0, {   -46,    922}, {0xd1, 0x5c, 0xb8, 0xff}}},
    {{{    45,     77,      0}, 0, {   468,    910}, {0xfb, 0x7e, 0xfe, 0xff}}},
    {{{    60,     25,    -62}, 0, {  -236,    468}, {0x1d, 0x21, 0x8a, 0xff}}},
    {{{     9,     60,      0}, 0, {   468,   1082}, {0xa2, 0x54, 0x00, 0xff}}},
    {{{    16,     44,     47}, 0, {   988,    922}, {0xd0, 0x5d, 0x47, 0xff}}},
    {{{    60,     25,     64}, 0, {  1176,    468}, {0x1c, 0x21, 0x77, 0xff}}},
    {{{     2,      8,    -66}, 0, {  -272,    800}, {0xaf, 0x18, 0xa3, 0xff}}},
    {{{     2,      8,     67}, 0, {  1212,    800}, {0xae, 0x16, 0x5d, 0xff}}},
};

// 0x06010580
static const Vtx mips_seg6_vertex_06010580[] = {
    {{{    45,     77,      0}, 0, {     0,      0}, {0xfb, 0x7e, 0xfe, 0xff}}},
    {{{    64,     56,     15}, 0, {     0,      0}, {0x44, 0x5a, 0x38, 0xff}}},
    {{{    64,     76,      0}, 0, {     0,      0}, {0x56, 0x5d, 0xfd, 0xff}}},
    {{{    64,     56,    -13}, 0, {     0,      0}, {0x45, 0x59, 0xc6, 0xff}}},
};

// 0x060105C0
static const Vtx mips_seg6_vertex_060105C0[] = {
    {{{    -9,     52,     -5}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    11,     50,    -12}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    11,     50,     13}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    -9,     52,      6}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06010600 - 0x06010748
const Gfx mips_seg6_dl_06010600[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mips_seg6_texture_0600FB80),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mips_seg6_lights_06010380.l, 1),
    gsSPLight(&mips_seg6_lights_06010380.a, 2),
    gsSPVertex(mips_seg6_vertex_060103B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  2,  5,  3, 0x0),
    gsSP2Triangles( 2,  6,  5, 0x0,  2,  4,  6, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  8,  3,  5, 0x0),
    gsSP2Triangles( 5,  9,  8, 0x0,  9,  5,  7, 0x0),
    gsSP2Triangles( 3, 10,  0, 0x0,  8, 10,  3, 0x0),
    gsSP2Triangles( 6, 11,  7, 0x0,  4, 11,  6, 0x0),
    gsSP2Triangles(12, 11,  4, 0x0,  4,  1, 12, 0x0),
    gsSP2Triangles( 8,  9, 13, 0x0, 14, 10,  8, 0x0),
    gsSP2Triangles( 8, 15, 14, 0x0, 13, 15,  8, 0x0),
    gsSPVertex(mips_seg6_vertex_060104B0, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  5,  6,  1, 0x0),
    gsSP2Triangles( 5,  1,  7, 0x0,  7,  1,  3, 0x0),
    gsSP2Triangles( 5,  8,  6, 0x0,  6,  9,  2, 0x0),
    gsSP2Triangles( 2,  9, 10, 0x0,  2, 10,  4, 0x0),
    gsSP2Triangles(11,  5,  7, 0x0,  9, 12, 10, 0x0),
    gsSP1Triangle( 6,  8,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x06010748 - 0x060107B8
const Gfx mips_seg6_dl_06010748[] = {
    gsSPLight(&mips_seg6_lights_06010398.l, 1),
    gsSPLight(&mips_seg6_lights_06010398.a, 2),
    gsSPVertex(mips_seg6_vertex_06010580, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP1Triangle( 3,  2,  1, 0x0),
    gsSPLight(&mips_seg6_lights_06010380.l, 1),
    gsSPLight(&mips_seg6_lights_06010380.a, 2),
    gsSPVertex(mips_seg6_vertex_060105C0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 3,  2,  0, 0x0,  2,  1,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x060107B8 - 0x06010820
const Gfx mips_seg6_dl_060107B8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mips_seg6_dl_06010600),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(mips_seg6_dl_06010748),
    gsSPEndDisplayList(),
};

// 0x06010820
static const Lights1 mips_seg6_lights_06010820 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06010838
static const Vtx mips_seg6_vertex_06010838[] = {
    {{{   -23,    -21,    -59}, 0, { -1764,    666}, {0xde, 0xd3, 0x8f, 0xff}}},
    {{{   -48,    -27,    -16}, 0, { -2202,    640}, {0x94, 0x0a, 0xbf, 0xff}}},
    {{{   -16,     41,    -55}, 0, { -1306,   -124}, {0xe5, 0x4b, 0x9e, 0xff}}},
    {{{   -47,     37,     22}, 0, { -1840,   -188}, {0x90, 0x32, 0x1c, 0xff}}},
    {{{   -47,     37,    -21}, 0, { -1840,   -188}, {0x98, 0x3e, 0xdc, 0xff}}},
    {{{   -34,    -55,      0}, 0, { -2112,   1046}, {0x22, 0x86, 0x00, 0xff}}},
    {{{   -48,    -27,     18}, 0, { -2202,    640}, {0x9f, 0x26, 0x47, 0xff}}},
    {{{   -75,    -36,    -40}, 0, { -2688,    648}, {0xde, 0x30, 0x90, 0xff}}},
    {{{   -16,     41,     56}, 0, { -1306,   -124}, {0xe4, 0x4a, 0x62, 0xff}}},
    {{{   -23,    -21,     60}, 0, { -1764,    666}, {0xde, 0xd2, 0x70, 0xff}}},
    {{{   -75,    -36,     42}, 0, { -2688,    648}, {0xed, 0xfb, 0x7d, 0xff}}},
    {{{   -15,     60,      0}, 0, { -1198,   -360}, {0xfb, 0x7e, 0x00, 0xff}}},
    {{{    12,    -27,     49}, 0, { -1206,    880}, {0x38, 0xb6, 0x55, 0xff}}},
    {{{    17,     16,     52}, 0, {  -886,    330}, {0x39, 0x1e, 0x6c, 0xff}}},
    {{{    65,     12,     15}, 0, {  -142,    578}, {0x6a, 0x1c, 0x3f, 0xff}}},
};

// 0x06010928
static const Vtx mips_seg6_vertex_06010928[] = {
    {{{    12,    -27,     49}, 0, { -1206,    880}, {0x38, 0xb6, 0x55, 0xff}}},
    {{{    33,    -39,      0}, 0, {  -928,   1112}, {0x38, 0x8f, 0x00, 0xff}}},
    {{{    64,    -13,      0}, 0, {  -292,    902}, {0x6b, 0xbd, 0x00, 0xff}}},
    {{{   -34,    -55,      0}, 0, { -2112,   1046}, {0x22, 0x86, 0x00, 0xff}}},
    {{{    65,     12,     15}, 0, {  -142,    578}, {0x6a, 0x1c, 0x3f, 0xff}}},
    {{{    31,     35,     19}, 0, {  -564,    142}, {0x42, 0x67, 0x1f, 0xff}}},
    {{{    17,     16,     52}, 0, {  -886,    330}, {0x39, 0x1e, 0x6c, 0xff}}},
    {{{   -16,     41,     56}, 0, { -1306,   -124}, {0xe4, 0x4a, 0x62, 0xff}}},
    {{{    18,     18,    -49}, 0, {  -870,    316}, {0x3a, 0x21, 0x95, 0xff}}},
    {{{    31,     34,    -18}, 0, {  -570,    156}, {0x40, 0x66, 0xda, 0xff}}},
    {{{    65,     12,    -14}, 0, {  -142,    578}, {0x68, 0x30, 0xcb, 0xff}}},
    {{{   -23,    -21,    -59}, 0, { -1764,    666}, {0xde, 0xd3, 0x8f, 0xff}}},
    {{{   -16,     41,    -55}, 0, { -1306,   -124}, {0xe5, 0x4b, 0x9e, 0xff}}},
    {{{    12,    -27,    -48}, 0, { -1206,    882}, {0x38, 0xb8, 0xa9, 0xff}}},
    {{{   -15,     60,      0}, 0, { -1198,   -360}, {0xfb, 0x7e, 0x00, 0xff}}},
};

// 0x06010A18
static const Vtx mips_seg6_vertex_06010A18[] = {
    {{{   -34,    -55,      0}, 0, { -2112,   1046}, {0x22, 0x86, 0x00, 0xff}}},
    {{{   -53,    -82,      0}, 0, { -2566,   1322}, {0x1f, 0x86, 0xff, 0xff}}},
    {{{   -75,    -36,    -40}, 0, { -2688,    648}, {0xde, 0x30, 0x90, 0xff}}},
    {{{   -75,    -36,     42}, 0, { -2688,    648}, {0xed, 0xfb, 0x7d, 0xff}}},
    {{{   -15,     60,      0}, 0, { -1198,   -360}, {0xfb, 0x7e, 0x00, 0xff}}},
    {{{    31,     35,     19}, 0, {  -564,    142}, {0x42, 0x67, 0x1f, 0xff}}},
    {{{    31,     34,    -18}, 0, {  -570,    156}, {0x40, 0x66, 0xda, 0xff}}},
    {{{   -82,    -71,      0}, 0, { -2974,   1062}, {0x9e, 0xb1, 0xff, 0xff}}},
};

// 0x06010A98 - 0x06010C40
const Gfx mips_seg6_dl_06010A98[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mips_seg6_texture_0600FB80),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mips_seg6_lights_06010820.l, 1),
    gsSPLight(&mips_seg6_lights_06010820.a, 2),
    gsSPVertex(mips_seg6_vertex_06010838, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 0,  5,  1, 0x0,  1,  6,  3, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  7,  1,  5, 0x0),
    gsSP2Triangles( 6,  1,  7, 0x0,  8,  6,  9, 0x0),
    gsSP2Triangles( 5,  9,  6, 0x0,  8,  3,  6, 0x0),
    gsSP2Triangles( 7, 10,  6, 0x0,  5,  6, 10, 0x0),
    gsSP2Triangles( 8, 11,  3, 0x0, 11,  4,  3, 0x0),
    gsSP2Triangles( 9,  5, 12, 0x0,  8,  9, 13, 0x0),
    gsSP2Triangles( 9, 12, 13, 0x0, 11,  2,  4, 0x0),
    gsSP1Triangle(12, 14, 13, 0x0),
    gsSPVertex(mips_seg6_vertex_06010928, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  4,  5,  6, 0x0),
    gsSP2Triangles( 7,  6,  5, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles(11, 12,  8, 0x0,  9,  8, 12, 0x0),
    gsSP2Triangles( 8, 13, 11, 0x0, 13,  8, 10, 0x0),
    gsSP2Triangles(13,  1,  3, 0x0, 10,  2, 13, 0x0),
    gsSP2Triangles( 3, 11, 13, 0x0,  2,  1, 13, 0x0),
    gsSP2Triangles(10,  9,  5, 0x0,  5,  4, 10, 0x0),
    gsSP2Triangles(10,  4,  2, 0x0,  5, 14,  7, 0x0),
    gsSP1Triangle(12, 14,  9, 0x0),
    gsSPVertex(mips_seg6_vertex_06010A18, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  2,  7,  3, 0x0),
    gsSP2Triangles( 1,  7,  2, 0x0,  3,  7,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06010C40 - 0x06010CA0
const Gfx mips_seg6_dl_06010C40[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mips_seg6_dl_06010A98),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06010CA0
static const Lights1 mips_seg6_lights_06010CA0 = gdSPDefLights1(
    0x3c, 0x3c, 0x00,
    0x96, 0x96, 0x00, 0x28, 0x28, 0x28
);

// 0x06010CB8
static const Lights1 mips_seg6_lights_06010CB8 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06010CD0
static const Vtx mips_seg6_vertex_06010CD0[] = {
    {{{     0,      0,    -26}, 0, { -1052,    990}, {0xc7, 0x5d, 0xc1, 0xff}}},
    {{{    76,      0,     41}, 0, {  -290,    990}, {0x18, 0x42, 0x69, 0xff}}},
    {{{    76,      0,    -39}, 0, {  -290,    990}, {0x1c, 0x12, 0x86, 0xff}}},
    {{{   103,      1,      1}, 0, {   -24,   1000}, {0x7e, 0x0b, 0x00, 0xff}}},
    {{{     0,      0,     28}, 0, { -1052,    990}, {0xb3, 0x3c, 0x51, 0xff}}},
    {{{   -19,     -8,      0}, 0, { -1254,    902}, {0x84, 0x19, 0xff, 0xff}}},
};

// 0x06010D30
static const Vtx mips_seg6_vertex_06010D30[] = {
    {{{   -11,    -30,      0}, 0, { -1168,    680}, {0xbc, 0x96, 0xfe, 0xff}}},
    {{{     0,      0,    -26}, 0, { -1052,    990}, {0xc7, 0x5d, 0xc1, 0xff}}},
    {{{    76,      0,    -39}, 0, {  -290,    990}, {0x1c, 0x12, 0x86, 0xff}}},
    {{{   -19,     -8,      0}, 0, { -1254,    902}, {0x84, 0x19, 0xff, 0xff}}},
    {{{    76,    -46,      0}, 0, {  -290,    516}, {0x40, 0x93, 0xff, 0xff}}},
    {{{    76,      0,     41}, 0, {  -290,    990}, {0x18, 0x42, 0x69, 0xff}}},
    {{{     0,      0,     28}, 0, { -1052,    990}, {0xb3, 0x3c, 0x51, 0xff}}},
    {{{   103,      1,      1}, 0, {   -24,   1000}, {0x7e, 0x0b, 0x00, 0xff}}},
};

// 0x06010DB0 - 0x06010E60
const Gfx mips_seg6_dl_06010DB0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mips_seg6_texture_0600FB80),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mips_seg6_lights_06010CA0.l, 1),
    gsSPLight(&mips_seg6_lights_06010CA0.a, 2),
    gsSPVertex(mips_seg6_vertex_06010CD0, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  4,  0,  5, 0x0),
    gsSPLight(&mips_seg6_lights_06010CB8.l, 1),
    gsSPLight(&mips_seg6_lights_06010CB8.a, 2),
    gsSPVertex(mips_seg6_vertex_06010D30, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  5,  6,  0, 0x0),
    gsSP2Triangles( 0,  6,  3, 0x0,  0,  4,  5, 0x0),
    gsSP2Triangles( 2,  7,  4, 0x0,  7,  5,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x06010E60 - 0x06010EC0
const Gfx mips_seg6_dl_06010E60[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mips_seg6_dl_06010DB0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06010EC0
static const Lights1 mips_seg6_lights_06010EC0 = gdSPDefLights1(
    0x3c, 0x3c, 0x00,
    0x96, 0x96, 0x00, 0x28, 0x28, 0x28
);

// 0x06010ED8
static const Lights1 mips_seg6_lights_06010ED8 = gdSPDefLights1(
    0x35, 0x38, 0x00,
    0x85, 0x8e, 0x00, 0x28, 0x28, 0x28
);

// 0x06010EF0
static const Lights1 mips_seg6_lights_06010EF0 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06010F08
static const Vtx mips_seg6_vertex_06010F08[] = {
    {{{     1,      0,    -13}, 0, {   -52,   2012}, {0xbe, 0x5f, 0xcf, 0xff}}},
    {{{     1,      0,     15}, 0, {   -52,   2012}, {0xa0, 0x2f, 0x43, 0xff}}},
    {{{    80,      0,     28}, 0, {   702,   2122}, {0x35, 0x2d, 0x69, 0xff}}},
    {{{    80,      0,    -26}, 0, {   702,   2122}, {0x39, 0x02, 0x8f, 0xff}}},
    {{{    94,    -14,      1}, 0, {   848,   1676}, {0x7d, 0xeb, 0x00, 0xff}}},
};

// 0x06010F58
static const Vtx mips_seg6_vertex_06010F58[] = {
    {{{    -2,    -19,      0}, 0, {   -72,   1382}, {0xbc, 0x96, 0xfe, 0xff}}},
    {{{     1,      0,     15}, 0, {   -52,   2012}, {0xa0, 0x2f, 0x43, 0xff}}},
    {{{     1,      0,    -13}, 0, {   -52,   2012}, {0xbe, 0x5f, 0xcf, 0xff}}},
};

// 0x06010F88
static const Vtx mips_seg6_vertex_06010F88[] = {
    {{{    -2,    -19,      0}, 0, {   -72,   1382}, {0xbc, 0x96, 0xfe, 0xff}}},
    {{{    80,      0,    -26}, 0, {   702,   2122}, {0x39, 0x02, 0x8f, 0xff}}},
    {{{    79,    -32,      0}, 0, {   714,   1108}, {0x33, 0x8d, 0xff, 0xff}}},
    {{{    80,      0,     28}, 0, {   702,   2122}, {0x35, 0x2d, 0x69, 0xff}}},
    {{{    94,    -14,      1}, 0, {   848,   1676}, {0x7d, 0xeb, 0x00, 0xff}}},
    {{{     1,      0,    -13}, 0, {   -52,   2012}, {0xbe, 0x5f, 0xcf, 0xff}}},
    {{{     1,      0,     15}, 0, {   -52,   2012}, {0xa0, 0x2f, 0x43, 0xff}}},
};

// 0x06010FF8 - 0x060110B0
const Gfx mips_seg6_dl_06010FF8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mips_seg6_texture_0600FB80),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mips_seg6_lights_06010EC0.l, 1),
    gsSPLight(&mips_seg6_lights_06010EC0.a, 2),
    gsSPVertex(mips_seg6_vertex_06010F08, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP1Triangle( 3,  2,  4, 0x0),
    gsSPLight(&mips_seg6_lights_06010ED8.l, 1),
    gsSPLight(&mips_seg6_lights_06010ED8.a, 2),
    gsSPVertex(mips_seg6_vertex_06010F58, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPLight(&mips_seg6_lights_06010EF0.l, 1),
    gsSPLight(&mips_seg6_lights_06010EF0.a, 2),
    gsSPVertex(mips_seg6_vertex_06010F88, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 3,  2,  4, 0x0,  2,  1,  4, 0x0),
    gsSP2Triangles( 0,  5,  1, 0x0,  3,  6,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x060110B0 - 0x06011110
const Gfx mips_seg6_dl_060110B0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mips_seg6_dl_06010FF8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06011110
static const Lights1 mips_seg6_lights_06011110 = gdSPDefLights1(
    0x3c, 0x3c, 0x00,
    0x96, 0x96, 0x00, 0x28, 0x28, 0x28
);

// 0x06011128
static const Lights1 mips_seg6_lights_06011128 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06011140
static const Vtx mips_seg6_vertex_06011140[] = {
    {{{    87,      0,     55}, 0, {   732,   2272}, {0x2d, 0x46, 0x5f, 0xff}}},
    {{{   126,      0,      1}, 0, {  1082,   2254}, {0x7c, 0xe7, 0x00, 0xff}}},
    {{{    87,      0,    -52}, 0, {   732,   2272}, {0x1e, 0x66, 0xbc, 0xff}}},
    {{{     0,      0,     41}, 0, {   -58,   2302}, {0xbd, 0x3f, 0x56, 0xff}}},
    {{{     0,      0,    -35}, 0, {   -58,   2302}, {0xb8, 0x17, 0x9b, 0xff}}},
    {{{   -19,    -20,     -3}, 0, {  -238,   1860}, {0x83, 0xf0, 0xfb, 0xff}}},
};

// 0x060111A0
static const Vtx mips_seg6_vertex_060111A0[] = {
    {{{   -19,    -20,     -3}, 0, {  -238,   1860}, {0x83, 0xf0, 0xfb, 0xff}}},
    {{{     0,    -38,     28}, 0, {   -54,   1464}, {0xcc, 0x97, 0x2e, 0xff}}},
    {{{     0,      0,     41}, 0, {   -58,   2302}, {0xbd, 0x3f, 0x56, 0xff}}},
    {{{     0,    -38,    -22}, 0, {   -54,   1464}, {0xc4, 0xa4, 0xc2, 0xff}}},
    {{{     0,      0,    -35}, 0, {   -58,   2302}, {0xb8, 0x17, 0x9b, 0xff}}},
    {{{    89,    -42,    -18}, 0, {   750,   1340}, {0x21, 0x96, 0xc5, 0xff}}},
    {{{    89,    -42,     21}, 0, {   750,   1340}, {0x2b, 0xa1, 0x47, 0xff}}},
    {{{    87,      0,     55}, 0, {   732,   2272}, {0x2d, 0x46, 0x5f, 0xff}}},
    {{{    87,      0,    -52}, 0, {   732,   2272}, {0x1e, 0x66, 0xbc, 0xff}}},
    {{{   126,      0,      1}, 0, {  1082,   2254}, {0x7c, 0xe7, 0x00, 0xff}}},
};

// 0x06011240 - 0x06011310
const Gfx mips_seg6_dl_06011240[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mips_seg6_texture_0600FB80),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mips_seg6_lights_06011110.l, 1),
    gsSPLight(&mips_seg6_lights_06011110.a, 2),
    gsSPVertex(mips_seg6_vertex_06011140, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 2,  4,  3, 0x0,  3,  4,  5, 0x0),
    gsSPLight(&mips_seg6_lights_06011128.l, 1),
    gsSPLight(&mips_seg6_lights_06011128.a, 2),
    gsSPVertex(mips_seg6_vertex_060111A0, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 0,  4,  3, 0x0,  5,  6,  1, 0x0),
    gsSP2Triangles( 1,  3,  5, 0x0,  2,  1,  6, 0x0),
    gsSP2Triangles( 6,  7,  2, 0x0,  5,  3,  4, 0x0),
    gsSP2Triangles( 4,  8,  5, 0x0,  8,  9,  5, 0x0),
    gsSP2Triangles( 9,  6,  5, 0x0,  9,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011310 - 0x06011370
const Gfx mips_seg6_dl_06011310[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mips_seg6_dl_06011240),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06011370
static const Lights1 mips_seg6_lights_06011370 = gdSPDefLights1(
    0x3c, 0x3c, 0x00,
    0x96, 0x96, 0x00, 0x28, 0x28, 0x28
);

// 0x06011388
static const Lights1 mips_seg6_lights_06011388 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x060113A0
static const Vtx mips_seg6_vertex_060113A0[] = {
    {{{    54,      0,    -35}, 0, {  -112,    464}, {0x5e, 0xf7, 0xac, 0xff}}},
    {{{    54,      0,     41}, 0, {  -112,    464}, {0x68, 0x18, 0x43, 0xff}}},
    {{{    49,    -37,      3}, 0, {  -288,     54}, {0x40, 0x93, 0x00, 0xff}}},
    {{{    -2,     52,    -19}, 0, {  -756,   1148}, {0xe0, 0x50, 0xa4, 0xff}}},
    {{{    -2,     52,     24}, 0, {  -756,   1148}, {0xb6, 0x2a, 0x5e, 0xff}}},
};

// 0x060113F0
static const Vtx mips_seg6_vertex_060113F0[] = {
    {{{    -2,     52,     24}, 0, {  -756,   1148}, {0xb6, 0x2a, 0x5e, 0xff}}},
    {{{    -2,     52,    -19}, 0, {  -756,   1148}, {0xe0, 0x50, 0xa4, 0xff}}},
    {{{   -14,     13,      2}, 0, { -1032,    740}, {0x86, 0xdf, 0x00, 0xff}}},
    {{{    27,    -28,    -22}, 0, {  -560,    194}, {0xca, 0xaa, 0xb5, 0xff}}},
    {{{    27,    -28,     28}, 0, {  -560,    194}, {0xc8, 0xaa, 0x4a, 0xff}}},
    {{{    54,      0,    -35}, 0, {  -112,    464}, {0x5e, 0xf7, 0xac, 0xff}}},
    {{{    49,    -37,      3}, 0, {  -288,     54}, {0x40, 0x93, 0x00, 0xff}}},
    {{{    54,      0,     41}, 0, {  -112,    464}, {0x68, 0x18, 0x43, 0xff}}},
};

// 0x06011470 - 0x06011520
const Gfx mips_seg6_dl_06011470[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mips_seg6_texture_0600FB80),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mips_seg6_lights_06011370.l, 1),
    gsSPLight(&mips_seg6_lights_06011370.a, 2),
    gsSPVertex(mips_seg6_vertex_060113A0, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP1Triangle( 3,  4,  1, 0x0),
    gsSPLight(&mips_seg6_lights_06011388.l, 1),
    gsSPLight(&mips_seg6_lights_06011388.a, 2),
    gsSPVertex(mips_seg6_vertex_060113F0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 3,  2,  1, 0x0,  3,  5,  6, 0x0),
    gsSP2Triangles( 6,  4,  3, 0x0,  1,  5,  3, 0x0),
    gsSP2Triangles( 6,  7,  4, 0x0,  4,  7,  0, 0x0),
    gsSP1Triangle( 0,  2,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011520 - 0x06011580
const Gfx mips_seg6_dl_06011520[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mips_seg6_dl_06011470),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06011580
static const Lights1 mips_seg6_lights_06011580 = gdSPDefLights1(
    0x3c, 0x3c, 0x00,
    0x96, 0x96, 0x00, 0x28, 0x28, 0x28
);

// 0x06011598
static const Lights1 mips_seg6_lights_06011598 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x060115B0
static const Vtx mips_seg6_vertex_060115B0[] = {
    {{{    87,      0,    -54}, 0, {  1972,    878}, {0x2d, 0x46, 0xa1, 0xff}}},
    {{{     0,      0,    -40}, 0, {  3460,    788}, {0xbd, 0x3f, 0xaa, 0xff}}},
    {{{    87,      0,     53}, 0, {  1972,    878}, {0x1e, 0x66, 0x44, 0xff}}},
    {{{     0,      0,     36}, 0, {  3460,    788}, {0xb8, 0x17, 0x65, 0xff}}},
    {{{   -19,    -20,      4}, 0, {  3784,    484}, {0x83, 0xf0, 0x05, 0xff}}},
    {{{   126,      0,      0}, 0, {  1316,    914}, {0x7c, 0xe7, 0x00, 0xff}}},
};

// 0x06011610
static const Vtx mips_seg6_vertex_06011610[] = {
    {{{     0,      0,    -40}, 0, {  3460,    788}, {0xbd, 0x3f, 0xaa, 0xff}}},
    {{{    87,      0,    -54}, 0, {  1972,    878}, {0x2d, 0x46, 0xa1, 0xff}}},
    {{{    89,    -42,    -20}, 0, {  1906,    290}, {0x2b, 0xa1, 0xb9, 0xff}}},
    {{{     0,    -38,    -27}, 0, {  3426,    260}, {0xcc, 0x97, 0xd2, 0xff}}},
    {{{   -19,    -20,      4}, 0, {  3784,    484}, {0x83, 0xf0, 0x05, 0xff}}},
    {{{    89,    -42,     19}, 0, {  1906,    290}, {0x21, 0x96, 0x3b, 0xff}}},
    {{{     0,    -38,     23}, 0, {  3426,    260}, {0xc4, 0xa4, 0x3e, 0xff}}},
    {{{     0,      0,     36}, 0, {  3460,    788}, {0xb8, 0x17, 0x65, 0xff}}},
    {{{    87,      0,     53}, 0, {  1972,    878}, {0x1e, 0x66, 0x44, 0xff}}},
    {{{   126,      0,      0}, 0, {  1316,    914}, {0x7c, 0xe7, 0x00, 0xff}}},
};

// 0x060116B0 - 0x06011780
const Gfx mips_seg6_dl_060116B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mips_seg6_texture_0600FB80),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mips_seg6_lights_06011580.l, 1),
    gsSPLight(&mips_seg6_lights_06011580.a, 2),
    gsSPVertex(mips_seg6_vertex_060115B0, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  3,  1, 0x0,  2,  5,  0, 0x0),
    gsSPLight(&mips_seg6_lights_06011598.l, 1),
    gsSPLight(&mips_seg6_lights_06011598.a, 2),
    gsSPVertex(mips_seg6_vertex_06011610, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 2,  3,  0, 0x0,  3,  2,  5, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  5,  6,  3, 0x0),
    gsSP2Triangles( 6,  7,  4, 0x0,  7,  6,  5, 0x0),
    gsSP2Triangles( 5,  8,  7, 0x0,  5,  9,  8, 0x0),
    gsSP2Triangles( 5,  2,  9, 0x0,  2,  1,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011780 - 0x060117E0
const Gfx mips_seg6_dl_06011780[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mips_seg6_dl_060116B0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x060117E0
static const Lights1 mips_seg6_lights_060117E0 = gdSPDefLights1(
    0x3c, 0x3c, 0x00,
    0x96, 0x96, 0x00, 0x28, 0x28, 0x28
);

// 0x060117F8
static const Lights1 mips_seg6_lights_060117F8 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06011810
static const Vtx mips_seg6_vertex_06011810[] = {
    {{{    49,    -37,     -2}, 0, {  -516,    -60}, {0x40, 0x93, 0x00, 0xff}}},
    {{{    54,      0,    -40}, 0, {  -564,   -446}, {0x68, 0x18, 0xbd, 0xff}}},
    {{{    54,      0,     36}, 0, {  -564,    330}, {0x5e, 0xf7, 0x54, 0xff}}},
    {{{    -2,     52,     20}, 0, {     2,    164}, {0xe0, 0x50, 0x5c, 0xff}}},
    {{{    -2,     52,    -23}, 0, {     2,   -268}, {0xb6, 0x2a, 0xa2, 0xff}}},
};

// 0x06011860
static const Vtx mips_seg6_vertex_06011860[] = {
    {{{   -14,     13,     -1}, 0, {   120,    -56}, {0x86, 0xdf, 0x00, 0xff}}},
    {{{    -2,     52,     20}, 0, {     2,    164}, {0xe0, 0x50, 0x5c, 0xff}}},
    {{{    -2,     52,    -23}, 0, {     2,   -268}, {0xb6, 0x2a, 0xa2, 0xff}}},
    {{{    49,    -37,     -2}, 0, {  -516,    -60}, {0x40, 0x93, 0x00, 0xff}}},
    {{{    54,      0,     36}, 0, {  -564,    330}, {0x5e, 0xf7, 0x54, 0xff}}},
    {{{    27,    -28,     23}, 0, {  -302,    200}, {0xca, 0xaa, 0x4b, 0xff}}},
    {{{    27,    -28,    -27}, 0, {  -302,   -312}, {0xc8, 0xaa, 0xb6, 0xff}}},
    {{{    54,      0,    -40}, 0, {  -564,   -446}, {0x68, 0x18, 0xbd, 0xff}}},
};

// 0x060118E0 - 0x06011990
const Gfx mips_seg6_dl_060118E0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mips_seg6_texture_0600FB80),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mips_seg6_lights_060117E0.l, 1),
    gsSPLight(&mips_seg6_lights_060117E0.a, 2),
    gsSPVertex(mips_seg6_vertex_06011810, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  1, 0x0),
    gsSP1Triangle( 1,  4,  3, 0x0),
    gsSPLight(&mips_seg6_lights_060117F8.l, 1),
    gsSPLight(&mips_seg6_lights_060117F8.a, 2),
    gsSPVertex(mips_seg6_vertex_06011860, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  6,  7,  3, 0x0),
    gsSP2Triangles( 5,  4,  1, 0x0,  6,  5,  0, 0x0),
    gsSP2Triangles( 1,  0,  5, 0x0,  2,  7,  6, 0x0),
    gsSP1Triangle( 6,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011990 - 0x060119F0
const Gfx mips_seg6_dl_06011990[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mips_seg6_dl_060118E0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x060119F0
static const Lights1 mips_seg6_lights_060119F0 = gdSPDefLights1(
    0x3c, 0x3c, 0x00,
    0x96, 0x96, 0x00, 0x28, 0x28, 0x28
);

// 0x06011A08
static const Lights1 mips_seg6_lights_06011A08 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06011A20
static const Vtx mips_seg6_vertex_06011A20[] = {
    {{{    76,      0,     40}, 0, {  -628,    934}, {0x1c, 0x11, 0x7a, 0xff}}},
    {{{    76,      0,    -40}, 0, {  -628,    934}, {0x18, 0x42, 0x97, 0xff}}},
    {{{     0,      0,     27}, 0, { -1998,    894}, {0xc6, 0x5d, 0x3f, 0xff}}},
    {{{   103,      1,      0}, 0, {  -142,    966}, {0x7e, 0x0b, 0x00, 0xff}}},
    {{{     0,      0,    -27}, 0, { -1998,    894}, {0xb5, 0x3c, 0xaf, 0xff}}},
    {{{   -19,     -8,      0}, 0, { -2378,    732}, {0x84, 0x19, 0x00, 0xff}}},
};

// 0x06011A80
static const Vtx mips_seg6_vertex_06011A80[] = {
    {{{   -19,     -8,      0}, 0, { -2378,    732}, {0x84, 0x19, 0x00, 0xff}}},
    {{{   -11,    -30,      0}, 0, { -2264,    358}, {0xbc, 0x95, 0x00, 0xff}}},
    {{{     0,      0,     27}, 0, { -1998,    894}, {0xc6, 0x5d, 0x3f, 0xff}}},
    {{{     0,      0,    -27}, 0, { -1998,    894}, {0xb5, 0x3c, 0xaf, 0xff}}},
    {{{    76,    -46,      0}, 0, {  -708,    126}, {0x40, 0x93, 0x00, 0xff}}},
    {{{    76,      0,     40}, 0, {  -628,    934}, {0x1c, 0x11, 0x7a, 0xff}}},
    {{{    76,      0,    -40}, 0, {  -628,    934}, {0x18, 0x42, 0x97, 0xff}}},
    {{{   103,      1,      0}, 0, {  -142,    966}, {0x7e, 0x0b, 0x00, 0xff}}},
};

// 0x06011B00 - 0x06011BB0
const Gfx mips_seg6_dl_06011B00[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mips_seg6_texture_0600FB80),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mips_seg6_lights_060119F0.l, 1),
    gsSPLight(&mips_seg6_lights_060119F0.a, 2),
    gsSPVertex(mips_seg6_vertex_06011A20, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  5,  2,  4, 0x0),
    gsSPLight(&mips_seg6_lights_06011A08.l, 1),
    gsSPLight(&mips_seg6_lights_06011A08.a, 2),
    gsSPVertex(mips_seg6_vertex_06011A80, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  1, 0x0,  1,  3,  6, 0x0),
    gsSP2Triangles( 5,  2,  1, 0x0,  6,  4,  1, 0x0),
    gsSP2Triangles( 4,  7,  5, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011BB0 - 0x06011C10
const Gfx mips_seg6_dl_06011BB0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mips_seg6_dl_06011B00),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06011C10
static const Lights1 mips_seg6_lights_06011C10 = gdSPDefLights1(
    0x3c, 0x3c, 0x00,
    0x96, 0x96, 0x00, 0x28, 0x28, 0x28
);

// 0x06011C28
static const Lights1 mips_seg6_lights_06011C28 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06011C40
static const Vtx mips_seg6_vertex_06011C40[] = {
    {{{    94,    -14,      0}, 0, {   -84,    658}, {0x7d, 0xeb, 0x00, 0xff}}},
    {{{    80,      0,    -27}, 0, {  -338,    952}, {0x35, 0x2f, 0x97, 0xff}}},
    {{{    80,      0,     27}, 0, {  -338,    952}, {0x39, 0xff, 0x71, 0xff}}},
    {{{     1,      0,     14}, 0, { -1644,    926}, {0xbd, 0x5f, 0x30, 0xff}}},
    {{{     1,      0,    -14}, 0, { -1644,    926}, {0xa2, 0x31, 0xbc, 0xff}}},
    {{{    -2,    -19,      0}, 0, { -1674,    522}, {0xbc, 0x96, 0x00, 0xff}}},
};

// 0x06011CA0
static const Vtx mips_seg6_vertex_06011CA0[] = {
    {{{    94,    -14,      0}, 0, {   -84,    658}, {0x7d, 0xeb, 0x00, 0xff}}},
    {{{    79,    -32,      0}, 0, {  -312,    302}, {0x33, 0x8d, 0x00, 0xff}}},
    {{{    80,      0,    -27}, 0, {  -338,    952}, {0x35, 0x2f, 0x97, 0xff}}},
    {{{    80,      0,     27}, 0, {  -338,    952}, {0x39, 0xff, 0x71, 0xff}}},
    {{{     1,      0,     14}, 0, { -1644,    926}, {0xbd, 0x5f, 0x30, 0xff}}},
    {{{    -2,    -19,      0}, 0, { -1674,    522}, {0xbc, 0x96, 0x00, 0xff}}},
    {{{     1,      0,    -14}, 0, { -1644,    926}, {0xa2, 0x31, 0xbc, 0xff}}},
};

// 0x06011D10 - 0x06011DB0
const Gfx mips_seg6_dl_06011D10[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mips_seg6_texture_0600FB80),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mips_seg6_lights_06011C10.l, 1),
    gsSPLight(&mips_seg6_lights_06011C10.a, 2),
    gsSPVertex(mips_seg6_vertex_06011C40, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 1,  4,  3, 0x0,  3,  4,  5, 0x0),
    gsSPLight(&mips_seg6_lights_06011C28.l, 1),
    gsSPLight(&mips_seg6_lights_06011C28.a, 2),
    gsSPVertex(mips_seg6_vertex_06011CA0, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 3,  4,  5, 0x0,  1,  3,  5, 0x0),
    gsSP2Triangles( 2,  1,  5, 0x0,  5,  6,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011DB0 - 0x06011E10
const Gfx mips_seg6_dl_06011DB0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mips_seg6_dl_06011D10),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06011E10
static const Lights1 mips_seg6_lights_06011E10 = gdSPDefLights1(
    0x34, 0x2c, 0x0f,
    0x82, 0x6e, 0x26, 0x28, 0x28, 0x28
);

// 0x06011E28
static const Vtx mips_seg6_vertex_06011E28[] = {
    {{{    51,    -16,    -11}, 0, {     0,      0}, {0xf1, 0xb5, 0x9b, 0xff}}},
    {{{    51,    -16,     13}, 0, {     0,      0}, {0xf2, 0x93, 0x3e, 0xff}}},
    {{{     0,    -12,     -6}, 0, {     0,      0}, {0xf2, 0xb5, 0x9c, 0xff}}},
    {{{     0,    -12,      8}, 0, {     0,      0}, {0xf2, 0xeb, 0x7c, 0xff}}},
    {{{    53,     21,      0}, 0, {     0,      0}, {0xe6, 0x7c, 0xfc, 0xff}}},
    {{{     0,     10,      0}, 0, {     0,      0}, {0xe7, 0x7c, 0xfb, 0xff}}},
};

// 0x06011E88 - 0x06011ED8
const Gfx mips_seg6_dl_06011E88[] = {
    gsSPLight(&mips_seg6_lights_06011E10.l, 1),
    gsSPLight(&mips_seg6_lights_06011E10.a, 2),
    gsSPVertex(mips_seg6_vertex_06011E28, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  2,  5,  4, 0x0),
    gsSP2Triangles( 4,  5,  3, 0x0,  1,  4,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011ED8 - 0x06011EF8
const Gfx mips_seg6_dl_06011ED8[] = {
    gsDPPipeSync(),
    gsSPDisplayList(mips_seg6_dl_06011E88),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x06011EF8
static const Lights1 mips_seg6_lights_06011EF8 = gdSPDefLights1(
    0x34, 0x2c, 0x0f,
    0x82, 0x6e, 0x26, 0x28, 0x28, 0x28
);

// 0x06011F10
static const Vtx mips_seg6_vertex_06011F10[] = {
    {{{     0,    -12,     -7}, 0, {     0,      0}, {0xf3, 0xe8, 0x85, 0xff}}},
    {{{     0,     10,      0}, 0, {     0,      0}, {0xe7, 0x7c, 0x00, 0xff}}},
    {{{    53,     21,      0}, 0, {     0,      0}, {0xe6, 0x7c, 0x00, 0xff}}},
    {{{    51,    -16,    -12}, 0, {     0,      0}, {0xf2, 0x93, 0xc3, 0xff}}},
    {{{     0,    -12,      7}, 0, {     0,      0}, {0xf1, 0xb6, 0x65, 0xff}}},
    {{{    51,    -16,     12}, 0, {     0,      0}, {0xf1, 0xb7, 0x66, 0xff}}},
};

// 0x06011F70 - 0x06011FC0
const Gfx mips_seg6_dl_06011F70[] = {
    gsSPLight(&mips_seg6_lights_06011EF8.l, 1),
    gsSPLight(&mips_seg6_lights_06011EF8.a, 2),
    gsSPVertex(mips_seg6_vertex_06011F10, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  2, 0x0,  2,  1,  4, 0x0),
    gsSP2Triangles( 4,  3,  5, 0x0,  4,  0,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011FC0 - 0x06011FE0
const Gfx mips_seg6_dl_06011FC0[] = {
    gsDPPipeSync(),
    gsSPDisplayList(mips_seg6_dl_06011F70),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};
