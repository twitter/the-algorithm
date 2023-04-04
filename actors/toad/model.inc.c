// Toad

// 0x06005908
static const Lights1 toad_seg6_lights_06005908 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06005920
ALIGNED8 static const Texture toad_seg6_texture_06005920[] = {
#include "actors/toad/toad_face.rgba16.inc.c"
};

// 0x06006120
ALIGNED8 static const Texture toad_seg6_texture_06006120[] = {
#include "actors/toad/toad_head.rgba16.inc.c"
};

// 0x06006920
static const Vtx toad_seg6_vertex_06006920[] = {
    {{{   -43,   -102,    106}, 0, {    36,    848}, {0xba, 0xcc, 0x5b, 0xff}}},
    {{{   -56,    -68,    103}, 0, {  -108,    448}, {0xa9, 0xfb, 0x5b, 0xff}}},
    {{{   -77,    -96,     50}, 0, {  -314,    702}, {0x94, 0xc1, 0x13, 0xff}}},
    {{{     0,   -135,     97}, 0, {   486,   1044}, {0x00, 0x8d, 0x34, 0xff}}},
    {{{   -42,   -128,     69}, 0, {    46,    990}, {0xbb, 0x9b, 0x1e, 0xff}}},
    {{{     0,    -99,    124}, 0, {   480,    722}, {0x00, 0xd4, 0x76, 0xff}}},
    {{{     0,    -55,    127}, 0, {   472,    332}, {0x00, 0xfe, 0x7e, 0xff}}},
    {{{    44,   -102,    106}, 0, {   930,    840}, {0x46, 0xcc, 0x5b, 0xff}}},
    {{{     0,    -29,    125}, 0, {   466,     92}, {0x00, 0x13, 0x7d, 0xff}}},
    {{{    57,    -68,    103}, 0, {  1058,    436}, {0x57, 0xfb, 0x5b, 0xff}}},
    {{{    78,    -96,     50}, 0, {  1274,    688}, {0x72, 0xcc, 0x0e, 0xff}}},
    {{{    43,   -128,     69}, 0, {   926,    980}, {0x47, 0x9a, 0x18, 0xff}}},
    {{{   -56,    -20,    101}, 0, {  -118,     16}, {0xb5, 0x11, 0x64, 0xff}}},
    {{{    57,    -20,    101}, 0, {  1052,      4}, {0x4a, 0x11, 0x65, 0xff}}},
    {{{     0,      4,    113}, 0, {   460,   -208}, {0x00, 0x2b, 0x77, 0xff}}},
};

// 0x06006A10
static const Vtx toad_seg6_vertex_06006A10[] = {
    {{{     0,   -143,      8}, 0, {  -546,    672}, {0xf8, 0x85, 0xe4, 0xff}}},
    {{{   -29,   -135,     34}, 0, {  -802,    592}, {0xc8, 0x8f, 0xfd, 0xff}}},
    {{{   -56,   -113,      7}, 0, {  -560,    370}, {0xab, 0xae, 0xd4, 0xff}}},
    {{{   -56,    -68,    103}, 0, {  -108,    448}, {0xa9, 0xfb, 0x5b, 0xff}}},
    {{{   -56,    -20,    101}, 0, {  -118,     16}, {0xb5, 0x11, 0x64, 0xff}}},
    {{{   -86,    -52,     32}, 0, {  -420,    314}, {0x84, 0xea, 0x06, 0xff}}},
    {{{   -77,    -96,     50}, 0, {  -314,    702}, {0x94, 0xc1, 0x13, 0xff}}},
    {{{    87,    -52,     32}, 0, {  1364,    296}, {0x7c, 0xf2, 0x16, 0xff}}},
    {{{    57,    -20,    101}, 0, {  1052,      4}, {0x4a, 0x11, 0x65, 0xff}}},
    {{{    57,    -68,    103}, 0, {  1058,    436}, {0x57, 0xfb, 0x5b, 0xff}}},
    {{{    78,    -96,     50}, 0, {  1274,    688}, {0x72, 0xcc, 0x0e, 0xff}}},
    {{{   -42,   -128,     69}, 0, { -1146,    510}, {0xbb, 0x9b, 0x1e, 0xff}}},
    {{{   -77,    -96,     50}, 0, {  -998,    182}, {0x94, 0xc1, 0x13, 0xff}}},
    {{{     0,   -135,     97}, 0, { -1412,    574}, {0x00, 0x8d, 0x34, 0xff}}},
    {{{   -86,    -52,     32}, 0, {  -858,   -260}, {0x84, 0xea, 0x06, 0xff}}},
    {{{   -61,    -80,    -18}, 0, {  -338,     40}, {0xb0, 0xc1, 0xb5, 0xff}}},
};

// 0x06006B10
static const Vtx toad_seg6_vertex_06006B10[] = {
    {{{     0,   -143,      8}, 0, {  -546,    672}, {0xf8, 0x85, 0xe4, 0xff}}},
    {{{   -56,   -113,      7}, 0, {  -560,    370}, {0xab, 0xae, 0xd4, 0xff}}},
    {{{     0,   -124,    -23}, 0, {  -254,    492}, {0x02, 0xa1, 0xad, 0xff}}},
    {{{   -61,    -80,    -18}, 0, {  -338,     40}, {0xb0, 0xc1, 0xb5, 0xff}}},
    {{{     0,   -135,     97}, 0, { -1412,    574}, {0x00, 0x8d, 0x34, 0xff}}},
    {{{    30,   -135,     34}, 0, {  -802,    592}, {0x33, 0x8e, 0xef, 0xff}}},
    {{{    43,   -128,     69}, 0, { -1146,    510}, {0x47, 0x9a, 0x18, 0xff}}},
    {{{     0,    -94,    -43}, 0, {   -92,    182}, {0x15, 0xb7, 0x9b, 0xff}}},
    {{{    57,   -113,      7}, 0, {  -560,    370}, {0x4d, 0xa9, 0xcf, 0xff}}},
    {{{    62,    -80,    -18}, 0, {  -338,     40}, {0x62, 0xc9, 0xc8, 0xff}}},
    {{{    78,    -96,     50}, 0, {  -998,    182}, {0x72, 0xcc, 0x0e, 0xff}}},
    {{{    87,    -52,     32}, 0, {  -858,   -260}, {0x7c, 0xf2, 0x16, 0xff}}},
};

// 0x06006BD0
static const Vtx toad_seg6_vertex_06006BD0[] = {
    {{{    34,    -28,     97}, 0, {   -26,    942}, {0x12, 0x9f, 0x4e, 0xff}}},
    {{{   142,    -44,     36}, 0, {  1040,   1000}, {0x45, 0xa0, 0x2c, 0xff}}},
    {{{    71,     13,    144}, 0, {    98,    396}, {0x28, 0xbb, 0x62, 0xff}}},
    {{{   -33,    -28,     97}, 0, {  -532,   1064}, {0xfb, 0x9e, 0x50, 0xff}}},
    {{{   176,      0,     13}, 0, {  1482,    566}, {0x78, 0xdd, 0x10, 0xff}}},
    {{{    89,     71,    148}, 0, {   306,   -192}, {0x3e, 0x0d, 0x6d, 0xff}}},
    {{{   -70,     13,    145}, 0, {  -966,    654}, {0xe6, 0xc0, 0x69, 0xff}}},
    {{{    34,    -28,     97}, 0, {   734,   1682}, {0x12, 0x9f, 0x4e, 0xff}}},
    {{{    76,    -57,     47}, 0, {   622,   1826}, {0x1f, 0x93, 0x38, 0xff}}},
    {{{   142,    -44,     36}, 0, {   640,   1996}, {0x45, 0xa0, 0x2c, 0xff}}},
    {{{    71,   -102,    -71}, 0, {   322,   1896}, {0x1d, 0x85, 0xfa, 0xff}}},
    {{{    34,    -88,     -5}, 0, {   458,   1758}, {0x0a, 0x86, 0x20, 0xff}}},
    {{{  -175,     48,    -12}, 0, {  -402,    498}, {0x88, 0x21, 0xed, 0xff}}},
    {{{   -70,    149,     72}, 0, {   194,     88}, {0xd3, 0x75, 0x12, 0xff}}},
    {{{  -141,     91,    -35}, 0, {  -264,    168}, {0xad, 0x52, 0xd1, 0xff}}},
};

// 0x06006CC0
static const Vtx toad_seg6_vertex_06006CC0[] = {
    {{{   -78,    122,    -51}, 0, {   490,    500}, {0xd3, 0x65, 0xc3, 0xff}}},
    {{{   -38,    154,      8}, 0, {  1016,    282}, {0xe4, 0x79, 0xe8, 0xff}}},
    {{{     0,    133,    -57}, 0, {   506,    -88}, {0x00, 0x70, 0xc5, 0xff}}},
    {{{   -70,    149,     72}, 0, {   194,     88}, {0xd3, 0x75, 0x12, 0xff}}},
    {{{    71,    149,     72}, 0, {  1052,    -96}, {0x26, 0x77, 0x15, 0xff}}},
    {{{    39,    154,      7}, 0, {   796,   -256}, {0x10, 0x7c, 0xee, 0xff}}},
    {{{   -38,    154,      8}, 0, {   320,   -154}, {0xe4, 0x79, 0xe8, 0xff}}},
    {{{   -87,    119,    122}, 0, {    22,    468}, {0xc3, 0x53, 0x49, 0xff}}},
    {{{    71,    149,     72}, 0, {  1066,     -2}, {0x26, 0x77, 0x15, 0xff}}},
    {{{   -70,    149,     72}, 0, {   120,     78}, {0xd3, 0x75, 0x12, 0xff}}},
    {{{    89,    119,    122}, 0, {  1202,    368}, {0x3a, 0x54, 0x4a, 0xff}}},
    {{{   -87,     71,    148}, 0, {    50,    828}, {0xc5, 0x10, 0x6f, 0xff}}},
    {{{    89,     71,    148}, 0, {  1230,    728}, {0x3e, 0x0d, 0x6d, 0xff}}},
    {{{   -70,     13,    145}, 0, {   200,   1082}, {0xe6, 0xc0, 0x69, 0xff}}},
    {{{  -141,     91,    -35}, 0, {   472,   1082}, {0xad, 0x52, 0xd1, 0xff}}},
    {{{   -70,     34,   -143}, 0, {  -446,    512}, {0xda, 0x31, 0x92, 0xff}}},
};

// 0x06006DC0
static const Vtx toad_seg6_vertex_06006DC0[] = {
    {{{  -141,     91,    -35}, 0, {   472,   1082}, {0xad, 0x52, 0xd1, 0xff}}},
    {{{   -70,    149,     72}, 0, {  1420,    692}, {0xd3, 0x75, 0x12, 0xff}}},
    {{{   -38,    154,      8}, 0, {  1016,    282}, {0xe4, 0x79, 0xe8, 0xff}}},
    {{{   -38,     90,   -112}, 0, {   -22,    184}, {0xf0, 0x54, 0xa3, 0xff}}},
    {{{   -78,    122,    -51}, 0, {   490,    500}, {0xd3, 0x65, 0xc3, 0xff}}},
    {{{     0,    133,    -57}, 0, {   506,    -88}, {0x00, 0x70, 0xc5, 0xff}}},
    {{{  -175,     48,    -12}, 0, {  -354,    -42}, {0x88, 0x21, 0xed, 0xff}}},
    {{{   -87,    119,    122}, 0, {  1274,     12}, {0xc3, 0x53, 0x49, 0xff}}},
    {{{   -70,    149,     72}, 0, {  1116,   -304}, {0xd3, 0x75, 0x12, 0xff}}},
    {{{  -175,      0,     13}, 0, {  -346,    408}, {0x87, 0xe0, 0x13, 0xff}}},
    {{{   -87,     71,    148}, 0, {  1280,    464}, {0xc5, 0x10, 0x6f, 0xff}}},
    {{{  -141,    -44,     37}, 0, {  -168,    914}, {0xba, 0xa7, 0x37, 0xff}}},
    {{{   -70,     34,   -143}, 0, {  -446,    512}, {0xda, 0x31, 0x92, 0xff}}},
    {{{    89,     71,    148}, 0, {   -76,    968}, {0x3e, 0x0d, 0x6d, 0xff}}},
    {{{   176,      0,     13}, 0, {  1032,    922}, {0x78, 0xdd, 0x10, 0xff}}},
    {{{   176,     47,    -12}, 0, {  1026,    610}, {0x79, 0x22, 0xf0, 0xff}}},
};

// 0x06006EC0
static const Vtx toad_seg6_vertex_06006EC0[] = {
    {{{  -141,    -44,     37}, 0, {  -168,    914}, {0xba, 0xa7, 0x37, 0xff}}},
    {{{   -70,     13,    145}, 0, {  1136,    958}, {0xe6, 0xc0, 0x69, 0xff}}},
    {{{   -87,     71,    148}, 0, {  1280,    464}, {0xc5, 0x10, 0x6f, 0xff}}},
    {{{   -66,    -61,     45}, 0, {   554,   1460}, {0xe7, 0x96, 0x3f, 0xff}}},
    {{{   -33,    -28,     97}, 0, {   706,   1508}, {0xfb, 0x9e, 0x50, 0xff}}},
    {{{   -70,     13,    145}, 0, {   842,   1372}, {0xe6, 0xc0, 0x69, 0xff}}},
    {{{  -141,    -44,     37}, 0, {   524,   1270}, {0xba, 0xa7, 0x37, 0xff}}},
    {{{   -33,    -88,     -5}, 0, {   432,   1586}, {0xec, 0x89, 0x25, 0xff}}},
    {{{   -70,   -102,    -71}, 0, {   266,   1534}, {0xd7, 0x88, 0x00, 0xff}}},
    {{{    89,     71,    148}, 0, {   -76,    968}, {0x3e, 0x0d, 0x6d, 0xff}}},
    {{{   176,     47,    -12}, 0, {  1026,    610}, {0x79, 0x22, 0xf0, 0xff}}},
    {{{    89,    119,    122}, 0, {   -82,    656}, {0x3a, 0x54, 0x4a, 0xff}}},
    {{{    34,    -88,     -5}, 0, {   458,   1758}, {0x0a, 0x86, 0x20, 0xff}}},
    {{{   142,     91,    -36}, 0, {   908,    228}, {0x54, 0x55, 0xd7, 0xff}}},
    {{{    71,    149,     72}, 0, {    18,    266}, {0x26, 0x77, 0x15, 0xff}}},
};

// 0x06006FB0
static const Vtx toad_seg6_vertex_06006FB0[] = {
    {{{   -88,    -72,   -121}, 0, {  -182,    430}, {0xc2, 0xae, 0xb7, 0xff}}},
    {{{  -175,     48,    -12}, 0, {  1388,    114}, {0x88, 0x21, 0xed, 0xff}}},
    {{{   -88,    -23,   -147}, 0, {  -130,    -66}, {0xc6, 0xf2, 0x91, 0xff}}},
    {{{  -175,      0,     13}, 0, {  1336,    612}, {0x87, 0xe0, 0x13, 0xff}}},
    {{{   -70,   -102,    -71}, 0, {   -80,    898}, {0xd7, 0x88, 0x00, 0xff}}},
    {{{  -141,    -44,     37}, 0, {  1138,   1040}, {0xba, 0xa7, 0x37, 0xff}}},
    {{{    34,    -88,     -5}, 0, {   458,   1758}, {0x0a, 0x86, 0x20, 0xff}}},
    {{{   -70,   -102,    -71}, 0, {   266,   1534}, {0xd7, 0x88, 0x00, 0xff}}},
    {{{    71,   -102,    -71}, 0, {   322,   1896}, {0x1d, 0x85, 0xfa, 0xff}}},
    {{{    71,    149,     72}, 0, {    18,    266}, {0x26, 0x77, 0x15, 0xff}}},
    {{{   142,     91,    -36}, 0, {   908,    228}, {0x54, 0x55, 0xd7, 0xff}}},
    {{{    79,    122,    -52}, 0, {   704,   -146}, {0x2c, 0x6b, 0xcf, 0xff}}},
    {{{    39,    154,      7}, 0, {   210,   -124}, {0x10, 0x7c, 0xee, 0xff}}},
    {{{    79,    122,    -52}, 0, {   -82,    342}, {0x2c, 0x6b, 0xcf, 0xff}}},
    {{{    39,     90,   -112}, 0, {   670,    514}, {0x1c, 0x58, 0xaa, 0xff}}},
    {{{     0,    133,    -57}, 0, {   430,   -262}, {0x00, 0x70, 0xc5, 0xff}}},
};

// 0x060070B0
static const Vtx toad_seg6_vertex_060070B0[] = {
    {{{    71,     34,   -144}, 0, {   848,   1202}, {0x2d, 0x32, 0x95, 0xff}}},
    {{{   -38,     90,   -112}, 0, {  1178,    -54}, {0xf0, 0x54, 0xa3, 0xff}}},
    {{{    39,     90,   -112}, 0, {   670,    514}, {0x1c, 0x58, 0xaa, 0xff}}},
    {{{   142,     91,    -36}, 0, {  -508,    890}, {0x54, 0x55, 0xd7, 0xff}}},
    {{{    79,    122,    -52}, 0, {   -82,    342}, {0x2c, 0x6b, 0xcf, 0xff}}},
    {{{     0,    133,    -57}, 0, {   430,   -262}, {0x00, 0x70, 0xc5, 0xff}}},
    {{{   142,    -44,     36}, 0, {   -38,    932}, {0x45, 0xa0, 0x2c, 0xff}}},
    {{{    71,   -102,    -71}, 0, {  1100,    968}, {0x1d, 0x85, 0xfa, 0xff}}},
    {{{    88,    -72,   -121}, 0, {  1238,    498}, {0x3a, 0xad, 0xb5, 0xff}}},
    {{{    88,    -24,   -147}, 0, {   -90,    608}, {0x3d, 0xf2, 0x93, 0xff}}},
    {{{   -70,     34,   -143}, 0, {   904,    182}, {0xda, 0x31, 0x92, 0xff}}},
    {{{    71,     34,   -144}, 0, {   -10,    212}, {0x2d, 0x32, 0x95, 0xff}}},
    {{{   -38,     90,   -112}, 0, {   662,   -172}, {0xf0, 0x54, 0xa3, 0xff}}},
    {{{   -88,    -23,   -147}, 0, {  1048,    572}, {0xc6, 0xf2, 0x91, 0xff}}},
    {{{    88,    -72,   -121}, 0, {   -72,    958}, {0x3a, 0xad, 0xb5, 0xff}}},
    {{{   -88,    -72,   -121}, 0, {  1066,    922}, {0xc2, 0xae, 0xb7, 0xff}}},
};

// 0x060071B0
static const Vtx toad_seg6_vertex_060071B0[] = {
    {{{   176,      0,     13}, 0, {  -180,    454}, {0x78, 0xdd, 0x10, 0xff}}},
    {{{    88,    -24,   -147}, 0, {  1236,     52}, {0x3d, 0xf2, 0x93, 0xff}}},
    {{{   176,     47,    -12}, 0, {  -182,      6}, {0x79, 0x22, 0xf0, 0xff}}},
    {{{    88,    -72,   -121}, 0, {  1238,    498}, {0x3a, 0xad, 0xb5, 0xff}}},
    {{{   142,    -44,     36}, 0, {   -38,    932}, {0x45, 0xa0, 0x2c, 0xff}}},
};

// 0x06007200
static const Vtx toad_seg6_vertex_06007200[] = {
    {{{   176,     47,    -12}, 0, {     0,      0}, {0x79, 0x22, 0xf0, 0xff}}},
    {{{    71,     34,   -144}, 0, {     0,      0}, {0x2d, 0x32, 0x95, 0xff}}},
    {{{   142,     91,    -36}, 0, {     0,      0}, {0x54, 0x55, 0xd7, 0xff}}},
    {{{   -38,    154,      8}, 0, {     0,      0}, {0xe4, 0x79, 0xe8, 0xff}}},
    {{{    39,    154,      7}, 0, {     0,      0}, {0x10, 0x7c, 0xee, 0xff}}},
    {{{     0,    133,    -57}, 0, {     0,      0}, {0x00, 0x70, 0xc5, 0xff}}},
    {{{    79,    122,    -52}, 0, {     0,      0}, {0x2c, 0x6b, 0xcf, 0xff}}},
    {{{   -88,    -23,   -147}, 0, {     0,      0}, {0xc6, 0xf2, 0x91, 0xff}}},
    {{{  -141,     91,    -35}, 0, {     0,      0}, {0xad, 0x52, 0xd1, 0xff}}},
    {{{   -70,     34,   -143}, 0, {     0,      0}, {0xda, 0x31, 0x92, 0xff}}},
    {{{  -175,     48,    -12}, 0, {     0,      0}, {0x88, 0x21, 0xed, 0xff}}},
    {{{    71,   -102,    -71}, 0, {     0,      0}, {0x1d, 0x85, 0xfa, 0xff}}},
    {{{   -88,    -72,   -121}, 0, {     0,      0}, {0xc2, 0xae, 0xb7, 0xff}}},
    {{{    88,    -72,   -121}, 0, {     0,      0}, {0x3a, 0xad, 0xb5, 0xff}}},
    {{{   -70,   -102,    -71}, 0, {     0,      0}, {0xd7, 0x88, 0x00, 0xff}}},
    {{{    88,    -24,   -147}, 0, {     0,      0}, {0x3d, 0xf2, 0x93, 0xff}}},
};

// 0x06007300 - 0x06007498
const Gfx toad_seg6_dl_06007300[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, toad_seg6_texture_06005920),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&toad_seg6_lights_06005908.l, 1),
    gsSPLight(&toad_seg6_lights_06005908.a, 2),
    gsSPVertex(toad_seg6_vertex_06006920, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  0,  5,  6, 0x0),
    gsSP2Triangles( 3,  5,  0, 0x0,  6,  1,  0, 0x0),
    gsSP2Triangles( 6,  5,  7, 0x0,  3,  7,  5, 0x0),
    gsSP2Triangles( 1,  6,  8, 0x0,  8,  6,  9, 0x0),
    gsSP2Triangles( 7,  9,  6, 0x0, 10,  9,  7, 0x0),
    gsSP2Triangles( 3, 11,  7, 0x0,  7, 11, 10, 0x0),
    gsSP2Triangles( 8, 12,  1, 0x0, 13, 14,  8, 0x0),
    gsSP2Triangles( 9, 13,  8, 0x0, 12,  8, 14, 0x0),
    gsSPVertex(toad_seg6_vertex_06006A10, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0,  1, 11, 12, 0x0),
    gsSP2Triangles(13, 11,  1, 0x0,  1, 12,  2, 0x0),
    gsSP2Triangles(13,  1,  0, 0x0,  2, 12, 14, 0x0),
    gsSP1Triangle( 2, 14, 15, 0x0),
    gsSPVertex(toad_seg6_vertex_06006B10, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  0,  5, 0x0,  4,  5,  6, 0x0),
    gsSP2Triangles( 2,  3,  7, 0x0,  5,  0,  2, 0x0),
    gsSP2Triangles( 5,  2,  8, 0x0,  8,  2,  7, 0x0),
    gsSP2Triangles( 8,  7,  9, 0x0,  6,  5,  8, 0x0),
    gsSP2Triangles( 6,  8, 10, 0x0, 10,  8,  9, 0x0),
    gsSP1Triangle(10,  9, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x06007498 - 0x060076C0
const Gfx toad_seg6_dl_06007498[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, toad_seg6_texture_06006120),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(toad_seg6_vertex_06006BD0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  2,  4,  5, 0x0),
    gsSP2Triangles( 3,  2,  6, 0x0,  6,  2,  5, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  8, 10,  9, 0x0),
    gsSP2Triangles( 8, 11, 10, 0x0, 12, 13, 14, 0x0),
    gsSPVertex(toad_seg6_vertex_06006CC0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 10,  7, 0x0),
    gsSP2Triangles(11, 12, 10, 0x0, 13, 12, 11, 0x0),
    gsSP2Triangles(14,  1,  0, 0x0, 15, 14,  0, 0x0),
    gsSPVertex(toad_seg6_vertex_06006DC0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9,  7,  6, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0, 11, 10,  9, 0x0),
    gsSP2Triangles(12,  4,  3, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(toad_seg6_vertex_06006EC0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  3,  6, 0x0),
    gsSP2Triangles( 7,  6,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12,  7,  8, 0x0, 11, 10, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(toad_seg6_vertex_06006FB0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  3,  0, 0x0,  4,  5,  3, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 9, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(toad_seg6_vertex_060070B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 3,  2,  4, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(11, 10, 12, 0x0,  9, 13, 10, 0x0),
    gsSP2Triangles(14, 13,  9, 0x0, 14, 15, 13, 0x0),
    gsSPVertex(toad_seg6_vertex_060071B0, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP1Triangle( 4,  3,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x060076C0 - 0x06007710
const Gfx toad_seg6_dl_060076C0[] = {
    gsSPVertex(toad_seg6_vertex_06007200, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06007710 - 0x06007788
const Gfx toad_seg6_dl_06007710[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(toad_seg6_dl_06007300),
    gsSPDisplayList(toad_seg6_dl_06007498),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(toad_seg6_dl_060076C0),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

#ifndef VERSION_JP
// 0x06007788 - 0x06007808
const Gfx toad_seg6_us_dl_06007788[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetRenderMode(G_RM_CUSTOM_AA_ZB_XLU_SURF, G_RM_NOOP2),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(toad_seg6_dl_06007300),
    gsSPDisplayList(toad_seg6_dl_06007498),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(toad_seg6_dl_060076C0),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};
#endif

// 0x06007788
static const Lights1 toad_seg6_lights_06007788 = gdSPDefLights1(
    0x21, 0x13, 0x5a,
    0x42, 0x27, 0xb5, 0x28, 0x28, 0x28
);

// 0x060077A0
static const Vtx toad_seg6_vertex_060077A0[] = {
    {{{   -80,    -27,    -24}, 0, {     0,      0}, {0x90, 0x28, 0xd5, 0xff}}},
    {{{   -61,     26,     28}, 0, {     0,      0}, {0x9c, 0x3a, 0x32, 0xff}}},
    {{{   -61,     26,    -27}, 0, {     0,      0}, {0x93, 0x37, 0xdf, 0xff}}},
    {{{   -35,     68,    -34}, 0, {     0,      0}, {0xc6, 0x48, 0xaa, 0xff}}},
    {{{    36,     68,    -34}, 0, {     0,      0}, {0x57, 0x4b, 0xcb, 0xff}}},
    {{{    31,     26,    -60}, 0, {     0,      0}, {0x26, 0x35, 0x94, 0xff}}},
    {{{    36,     60,     20}, 0, {     0,      0}, {0x54, 0x4d, 0x35, 0xff}}},
    {{{    62,     26,     28}, 0, {     0,      0}, {0x63, 0x3f, 0x2d, 0xff}}},
    {{{    62,     26,    -27}, 0, {     0,      0}, {0x6d, 0x2e, 0xd5, 0xff}}},
    {{{    26,     29,     50}, 0, {     0,      0}, {0x45, 0x31, 0x5e, 0xff}}},
    {{{    81,    -23,     30}, 0, {     0,      0}, {0x6a, 0x2e, 0x33, 0xff}}},
    {{{    81,    -27,    -24}, 0, {     0,      0}, {0x71, 0x26, 0xd6, 0xff}}},
    {{{    40,    -30,    -76}, 0, {     0,      0}, {0x32, 0x25, 0x92, 0xff}}},
    {{{    40,    -18,     65}, 0, {     0,      0}, {0x4f, 0x32, 0x55, 0xff}}},
    {{{   -80,    -23,     30}, 0, {     0,      0}, {0x93, 0x2c, 0x2f, 0xff}}},
    {{{   -30,     26,    -60}, 0, {     0,      0}, {0xcf, 0x2b, 0x94, 0xff}}},
};

// 0x060078A0
static const Vtx toad_seg6_vertex_060078A0[] = {
    {{{   -35,     60,     20}, 0, {     0,      0}, {0xa1, 0x4b, 0x23, 0xff}}},
    {{{   -61,     26,    -27}, 0, {     0,      0}, {0x93, 0x37, 0xdf, 0xff}}},
    {{{   -61,     26,     28}, 0, {     0,      0}, {0x9c, 0x3a, 0x32, 0xff}}},
    {{{   -25,     29,     50}, 0, {     0,      0}, {0xc2, 0x3a, 0x5d, 0xff}}},
    {{{   -39,    -18,     65}, 0, {     0,      0}, {0xb7, 0x2a, 0x5e, 0xff}}},
    {{{   -35,     68,    -34}, 0, {     0,      0}, {0xc6, 0x48, 0xaa, 0xff}}},
    {{{    40,    -30,    -76}, 0, {     0,      0}, {0x32, 0x25, 0x92, 0xff}}},
    {{{   -39,    -30,    -76}, 0, {     0,      0}, {0xcd, 0x27, 0x93, 0xff}}},
    {{{   -30,     26,    -60}, 0, {     0,      0}, {0xcf, 0x2b, 0x94, 0xff}}},
    {{{    31,     26,    -60}, 0, {     0,      0}, {0x26, 0x35, 0x94, 0xff}}},
    {{{   -80,    -23,     30}, 0, {     0,      0}, {0x93, 0x2c, 0x2f, 0xff}}},
    {{{   -80,    -27,    -24}, 0, {     0,      0}, {0x90, 0x28, 0xd5, 0xff}}},
};

// 0x06007960 - 0x06007A48
const Gfx toad_seg6_dl_06007960[] = {
    gsSPLight(&toad_seg6_lights_06007788.l, 1),
    gsSPLight(&toad_seg6_lights_06007788.a, 2),
    gsSPVertex(toad_seg6_vertex_060077A0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  7, 0x0,  4,  7,  8, 0x0),
    gsSP2Triangles( 8,  5,  4, 0x0,  9,  7,  6, 0x0),
    gsSP2Triangles(10,  8,  7, 0x0, 10,  7,  9, 0x0),
    gsSP2Triangles(10, 11,  8, 0x0, 12,  8, 11, 0x0),
    gsSP2Triangles(12,  5,  8, 0x0, 10,  9, 13, 0x0),
    gsSP2Triangles( 0, 14,  1, 0x0, 15,  0,  2, 0x0),
    gsSPVertex(toad_seg6_vertex_060078A0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  3,  2, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP2Triangles( 5,  9,  8, 0x0,  4,  2, 10, 0x0),
    gsSP2Triangles( 8,  1,  5, 0x0,  8,  7, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x06007A48 - 0x06007A80
const Gfx toad_seg6_dl_06007A48[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsSPDisplayList(toad_seg6_dl_06007960),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

#ifndef VERSION_JP
// 0x06007B00 - 0x06007B28
const Gfx toad_seg6_us_dl_06007B00[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(toad_seg6_dl_06007960),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};
#endif

// 0x06007A80
static const Lights1 toad_seg6_lights_06007A80 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06007A98
static const Lights1 toad_seg6_lights_06007A98 = gdSPDefLights1(
    0x7f, 0x6a, 0x50,
    0xfe, 0xd5, 0xa1, 0x28, 0x28, 0x28
);

// 0x06007AB0
static const Vtx toad_seg6_vertex_06007AB0[] = {
    {{{    44,    -69,    -33}, 0, {     0,      0}, {0x21, 0x90, 0xd1, 0xff}}},
    {{{   -34,    -45,    -60}, 0, {     0,      0}, {0xdb, 0xd0, 0x91, 0xff}}},
    {{{    35,    -45,    -60}, 0, {     0,      0}, {0x2e, 0xf5, 0x8b, 0xff}}},
    {{{    71,    -33,     25}, 0, {     0,      0}, {0x74, 0x05, 0x32, 0xff}}},
    {{{    57,      9,     22}, 0, {     0,      0}, {0x67, 0x41, 0x23, 0xff}}},
    {{{    29,      9,     49}, 0, {     0,      0}, {0x2b, 0x33, 0x6b, 0xff}}},
    {{{    35,    -33,     61}, 0, {     0,      0}, {0x28, 0xe3, 0x74, 0xff}}},
    {{{    44,    -69,     34}, 0, {     0,      0}, {0x43, 0xa0, 0x2e, 0xff}}},
    {{{    71,    -38,    -29}, 0, {     0,      0}, {0x72, 0xdc, 0xd7, 0xff}}},
    {{{    57,      9,    -26}, 0, {     0,      0}, {0x69, 0x30, 0xce, 0xff}}},
    {{{   -43,    -69,     34}, 0, {     0,      0}, {0xdb, 0x96, 0x3a, 0xff}}},
    {{{   -34,    -33,     61}, 0, {     0,      0}, {0xcf, 0x04, 0x74, 0xff}}},
    {{{   -28,      9,    -51}, 0, {     0,      0}, {0xd6, 0x25, 0x8f, 0xff}}},
    {{{    29,      9,    -51}, 0, {     0,      0}, {0x21, 0x2d, 0x8f, 0xff}}},
};

// 0x06007B90
static const Vtx toad_seg6_vertex_06007B90[] = {
    {{{   -70,    -38,    -29}, 0, {     0,      0}, {0x8d, 0xfe, 0xcc, 0xff}}},
    {{{   -56,      9,     22}, 0, {     0,      0}, {0x96, 0x35, 0x2c, 0xff}}},
    {{{   -56,      9,    -26}, 0, {     0,      0}, {0x98, 0x3c, 0xda, 0xff}}},
    {{{    44,    -69,    -33}, 0, {     0,      0}, {0x21, 0x90, 0xd1, 0xff}}},
    {{{   -43,    -69,     34}, 0, {     0,      0}, {0xdb, 0x96, 0x3a, 0xff}}},
    {{{   -43,    -69,    -33}, 0, {     0,      0}, {0xc0, 0x99, 0xdc, 0xff}}},
    {{{   -34,    -45,    -60}, 0, {     0,      0}, {0xdb, 0xd0, 0x91, 0xff}}},
    {{{   -34,    -33,     61}, 0, {     0,      0}, {0xcf, 0x04, 0x74, 0xff}}},
    {{{   -28,      9,     49}, 0, {     0,      0}, {0xdf, 0x3f, 0x68, 0xff}}},
    {{{   -70,    -33,     25}, 0, {     0,      0}, {0x8c, 0xe2, 0x29, 0xff}}},
    {{{    29,      9,     49}, 0, {     0,      0}, {0x2b, 0x33, 0x6b, 0xff}}},
    {{{   -28,      9,    -51}, 0, {     0,      0}, {0xd6, 0x25, 0x8f, 0xff}}},
};

// 0x06007C50
static const Vtx toad_seg6_vertex_06007C50[] = {
    {{{    29,      9,     49}, 0, {     0,      0}, {0x2b, 0x33, 0x6b, 0xff}}},
    {{{    57,      9,     22}, 0, {     0,      0}, {0x67, 0x41, 0x23, 0xff}}},
    {{{    21,     55,     14}, 0, {     0,      0}, {0x22, 0x6d, 0x36, 0xff}}},
    {{{   -28,      9,     49}, 0, {     0,      0}, {0xdf, 0x3f, 0x68, 0xff}}},
    {{{    21,     55,    -28}, 0, {     0,      0}, {0x43, 0x60, 0xd0, 0xff}}},
    {{{    57,      9,    -26}, 0, {     0,      0}, {0x69, 0x30, 0xce, 0xff}}},
    {{{   -20,     55,    -28}, 0, {     0,      0}, {0xdc, 0x67, 0xc0, 0xff}}},
    {{{   -20,     55,     14}, 0, {     0,      0}, {0xc0, 0x65, 0x29, 0xff}}},
    {{{    29,      9,    -51}, 0, {     0,      0}, {0x21, 0x2d, 0x8f, 0xff}}},
    {{{   -28,      9,    -51}, 0, {     0,      0}, {0xd6, 0x25, 0x8f, 0xff}}},
    {{{   -56,      9,     22}, 0, {     0,      0}, {0x96, 0x35, 0x2c, 0xff}}},
    {{{   -56,      9,    -26}, 0, {     0,      0}, {0x98, 0x3c, 0xda, 0xff}}},
};

// 0x06007D10 - 0x06007EB0
const Gfx toad_seg6_dl_06007D10[] = {
    gsSPLight(&toad_seg6_lights_06007A80.l, 1),
    gsSPLight(&toad_seg6_lights_06007A80.a, 2),
    gsSPVertex(toad_seg6_vertex_06007AB0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  3,  6,  7, 0x0),
    gsSP2Triangles( 7,  8,  3, 0x0,  3,  9,  4, 0x0),
    gsSP2Triangles( 3,  8,  9, 0x0, 10,  7,  6, 0x0),
    gsSP2Triangles(10,  6, 11, 0x0, 11,  6,  5, 0x0),
    gsSP2Triangles( 7,  0,  8, 0x0,  0,  7, 10, 0x0),
    gsSP2Triangles( 2, 12, 13, 0x0,  2,  1, 12, 0x0),
    gsSP2Triangles( 2,  8,  0, 0x0,  2, 13,  9, 0x0),
    gsSP1Triangle( 2,  9,  8, 0x0),
    gsSPVertex(toad_seg6_vertex_06007B90, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  1, 0x0),
    gsSP2Triangles( 7,  1,  9, 0x0,  7, 10,  8, 0x0),
    gsSP2Triangles( 7,  9,  4, 0x0,  5,  4,  9, 0x0),
    gsSP2Triangles( 5,  9,  0, 0x0,  0,  9,  1, 0x0),
    gsSP2Triangles(11,  6,  0, 0x0, 11,  0,  2, 0x0),
    gsSP1Triangle( 0,  6,  5, 0x0),
    gsSPLight(&toad_seg6_lights_06007A98.l, 1),
    gsSPLight(&toad_seg6_lights_06007A98.a, 2),
    gsSPVertex(toad_seg6_vertex_06007C50, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  2,  1, 0x0,  4,  1,  5, 0x0),
    gsSP2Triangles( 6,  7,  2, 0x0,  6,  2,  4, 0x0),
    gsSP2Triangles( 2,  7,  3, 0x0,  5,  8,  4, 0x0),
    gsSP2Triangles( 6,  8,  9, 0x0,  6,  4,  8, 0x0),
    gsSP2Triangles(10,  3,  7, 0x0,  7, 11, 10, 0x0),
    gsSP2Triangles( 7,  6, 11, 0x0,  9, 11,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x06007EB0 - 0x06007ED8
const Gfx toad_seg6_dl_06007EB0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(toad_seg6_dl_06007D10),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x06007ED8
static const Lights1 toad_seg6_lights_06007ED8 = gdSPDefLights1(
    0x7f, 0x6a, 0x50,
    0xfe, 0xd5, 0xa1, 0x28, 0x28, 0x28
);

// 0x06007EF0
static const Vtx toad_seg6_vertex_06007EF0[] = {
    {{{     7,     14,    -12}, 0, {     0,      0}, {0x4b, 0x1f, 0x9f, 0xff}}},
    {{{    -1,     14,    -12}, 0, {     0,      0}, {0xcf, 0x36, 0x99, 0xff}}},
    {{{    11,     34,      0}, 0, {     0,      0}, {0x24, 0x79, 0xfc, 0xff}}},
    {{{     3,    -43,    -27}, 0, {     0,      0}, {0x35, 0x1e, 0x91, 0xff}}},
    {{{    11,    -43,     -2}, 0, {     0,      0}, {0x7e, 0xf5, 0x0a, 0xff}}},
    {{{   -11,     14,      0}, 0, {     0,      0}, {0x97, 0x46, 0x0d, 0xff}}},
    {{{    -1,     14,     13}, 0, {     0,      0}, {0xd2, 0x3f, 0x63, 0xff}}},
    {{{     7,     14,     13}, 0, {     0,      0}, {0x3d, 0x21, 0x69, 0xff}}},
    {{{     3,    -43,     28}, 0, {     0,      0}, {0x50, 0xee, 0x60, 0xff}}},
    {{{    20,    -51,    -15}, 0, {     0,      0}, {0x73, 0xf7, 0xcd, 0xff}}},
    {{{     4,    -57,     18}, 0, {     0,      0}, {0x62, 0xd9, 0x45, 0xff}}},
    {{{     4,    -57,    -25}, 0, {     0,      0}, {0x40, 0xbe, 0xa9, 0xff}}},
    {{{   -17,    -43,    -27}, 0, {     0,      0}, {0xcb, 0xf8, 0x8d, 0xff}}},
    {{{   -17,    -43,     28}, 0, {     0,      0}, {0xcd, 0xf9, 0x73, 0xff}}},
    {{{    12,    -76,      3}, 0, {     0,      0}, {0x42, 0xa7, 0x3b, 0xff}}},
    {{{    12,    -76,     -9}, 0, {     0,      0}, {0x50, 0xa6, 0xdb, 0xff}}},
};

// 0x06007FF0
static const Vtx toad_seg6_vertex_06007FF0[] = {
    {{{     4,    -57,     18}, 0, {     0,      0}, {0x62, 0xd9, 0x45, 0xff}}},
    {{{   -17,    -43,     28}, 0, {     0,      0}, {0xcd, 0xf9, 0x73, 0xff}}},
    {{{   -15,    -73,      8}, 0, {     0,      0}, {0xd4, 0x97, 0x37, 0xff}}},
    {{{     3,    -43,     28}, 0, {     0,      0}, {0x50, 0xee, 0x60, 0xff}}},
    {{{    12,    -76,      3}, 0, {     0,      0}, {0x42, 0xa7, 0x3b, 0xff}}},
    {{{   -15,    -73,    -12}, 0, {     0,      0}, {0xc5, 0xa6, 0xbe, 0xff}}},
    {{{   -17,    -43,    -27}, 0, {     0,      0}, {0xcb, 0xf8, 0x8d, 0xff}}},
    {{{     4,    -57,    -25}, 0, {     0,      0}, {0x40, 0xbe, 0xa9, 0xff}}},
    {{{    12,    -76,     -9}, 0, {     0,      0}, {0x50, 0xa6, 0xdb, 0xff}}},
    {{{   -35,    -43,      0}, 0, {     0,      0}, {0x83, 0xfa, 0xef, 0xff}}},
    {{{   -11,     14,      0}, 0, {     0,      0}, {0x97, 0x46, 0x0d, 0xff}}},
    {{{    -1,     14,     13}, 0, {     0,      0}, {0xd2, 0x3f, 0x63, 0xff}}},
    {{{    -1,     14,    -12}, 0, {     0,      0}, {0xcf, 0x36, 0x99, 0xff}}},
};

// 0x060080C0 - 0x060081F8
const Gfx toad_seg6_dl_060080C0[] = {
    gsSPLight(&toad_seg6_lights_06007ED8.l, 1),
    gsSPLight(&toad_seg6_lights_06007ED8.a, 2),
    gsSPVertex(toad_seg6_vertex_06007EF0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  4,  3,  0, 0x0),
    gsSP2Triangles( 2,  5,  6, 0x0,  6,  7,  2, 0x0),
    gsSP2Triangles( 4,  2,  7, 0x0,  5,  2,  1, 0x0),
    gsSP2Triangles( 7,  8,  4, 0x0,  4,  9,  3, 0x0),
    gsSP2Triangles( 4,  8, 10, 0x0, 11,  9,  4, 0x0),
    gsSP2Triangles( 4, 10, 11, 0x0,  1,  3, 12, 0x0),
    gsSP2Triangles(11,  3,  9, 0x0, 12,  3, 11, 0x0),
    gsSP2Triangles( 7, 13,  8, 0x0,  7,  6, 13, 0x0),
    gsSP2Triangles(10, 14, 15, 0x0, 10, 15, 11, 0x0),
    gsSPVertex(toad_seg6_vertex_06007FF0, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 7,  8,  5, 0x0,  2,  8,  4, 0x0),
    gsSP2Triangles( 2,  5,  8, 0x0,  5,  2,  9, 0x0),
    gsSP2Triangles( 5,  9,  6, 0x0,  9,  2,  1, 0x0),
    gsSP2Triangles(10,  9,  1, 0x0, 10,  1, 11, 0x0),
    gsSP2Triangles( 9, 12,  6, 0x0,  9, 10, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x060081F8 - 0x06008220
const Gfx toad_seg6_dl_060081F8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(toad_seg6_dl_060080C0),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x06008220
static const Lights1 toad_seg6_lights_06008220 = gdSPDefLights1(
    0x7f, 0x6a, 0x50,
    0xfe, 0xd5, 0xa1, 0x28, 0x28, 0x28
);

// 0x06008238
static const Vtx toad_seg6_vertex_06008238[] = {
    {{{    11,    -43,      3}, 0, {     0,      0}, {0x7d, 0xf5, 0xf5, 0xff}}},
    {{{     3,    -43,    -27}, 0, {     0,      0}, {0x40, 0xfa, 0x93, 0xff}}},
    {{{     7,     15,    -12}, 0, {     0,      0}, {0x4d, 0x1f, 0xa1, 0xff}}},
    {{{     3,    -43,     28}, 0, {     0,      0}, {0x41, 0x1c, 0x69, 0xff}}},
    {{{    20,    -51,     16}, 0, {     0,      0}, {0x73, 0xf7, 0x33, 0xff}}},
    {{{     4,    -57,    -17}, 0, {     0,      0}, {0x53, 0xc9, 0xb2, 0xff}}},
    {{{     4,    -57,     26}, 0, {     0,      0}, {0x54, 0xce, 0x50, 0xff}}},
    {{{    11,     34,      0}, 0, {     0,      0}, {0x25, 0x79, 0xff, 0xff}}},
    {{{     7,     15,     13}, 0, {     0,      0}, {0x3c, 0x22, 0x6a, 0xff}}},
    {{{    -1,     15,    -12}, 0, {     0,      0}, {0xcf, 0x37, 0x99, 0xff}}},
    {{{   -11,     15,      0}, 0, {     0,      0}, {0x98, 0x46, 0x0d, 0xff}}},
    {{{    -1,     15,     13}, 0, {     0,      0}, {0xd2, 0x40, 0x62, 0xff}}},
    {{{   -17,    -43,     28}, 0, {     0,      0}, {0xca, 0x05, 0x72, 0xff}}},
    {{{   -15,    -73,     13}, 0, {     0,      0}, {0xcc, 0x9b, 0x37, 0xff}}},
    {{{    12,    -76,     10}, 0, {     0,      0}, {0x3e, 0xa2, 0x3a, 0xff}}},
    {{{    12,    -76,     -2}, 0, {     0,      0}, {0x52, 0xa9, 0xda, 0xff}}},
};

// 0x06008338
static const Vtx toad_seg6_vertex_06008338[] = {
    {{{   -15,    -73,     -7}, 0, {     0,      0}, {0xcf, 0xa0, 0xbe, 0xff}}},
    {{{   -17,    -43,    -27}, 0, {     0,      0}, {0xd1, 0xe4, 0x8e, 0xff}}},
    {{{     4,    -57,    -17}, 0, {     0,      0}, {0x53, 0xc9, 0xb2, 0xff}}},
    {{{     3,    -43,    -27}, 0, {     0,      0}, {0x40, 0xfa, 0x93, 0xff}}},
    {{{    12,    -76,     -2}, 0, {     0,      0}, {0x52, 0xa9, 0xda, 0xff}}},
    {{{   -15,    -73,     13}, 0, {     0,      0}, {0xcc, 0x9b, 0x37, 0xff}}},
    {{{    12,    -76,     10}, 0, {     0,      0}, {0x3e, 0xa2, 0x3a, 0xff}}},
    {{{   -35,    -43,      0}, 0, {     0,      0}, {0x83, 0xf9, 0xf0, 0xff}}},
    {{{   -17,    -43,     28}, 0, {     0,      0}, {0xca, 0x05, 0x72, 0xff}}},
    {{{    -1,     15,    -12}, 0, {     0,      0}, {0xcf, 0x37, 0x99, 0xff}}},
    {{{   -11,     15,      0}, 0, {     0,      0}, {0x98, 0x46, 0x0d, 0xff}}},
};

// 0x060083E8 - 0x06008520
const Gfx toad_seg6_dl_060083E8[] = {
    gsSPLight(&toad_seg6_lights_06008220.l, 1),
    gsSPLight(&toad_seg6_lights_06008220.a, 2),
    gsSPVertex(toad_seg6_vertex_06008238, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 5,  1,  0, 0x0,  0,  4,  6, 0x0),
    gsSP2Triangles( 0,  7,  8, 0x0,  8,  3,  0, 0x0),
    gsSP2Triangles( 2,  7,  0, 0x0,  6,  5,  0, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0,  7, 11,  8, 0x0),
    gsSP2Triangles( 7,  2,  9, 0x0, 11,  7, 10, 0x0),
    gsSP2Triangles(12,  3,  8, 0x0, 12,  8, 11, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  6,  3, 12, 0x0),
    gsSP2Triangles( 1,  9,  2, 0x0, 12, 11, 10, 0x0),
    gsSP2Triangles( 6, 12, 13, 0x0,  6, 14, 15, 0x0),
    gsSP2Triangles( 6, 15,  5, 0x0, 13, 14,  6, 0x0),
    gsSPVertex(toad_seg6_vertex_06008338, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  4,  5,  0, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  0,  5, 0x0),
    gsSP2Triangles( 8,  7,  5, 0x0,  3,  1,  9, 0x0),
    gsSP2Triangles( 1,  0,  7, 0x0,  9,  1,  7, 0x0),
    gsSP2Triangles( 8, 10,  7, 0x0,  9,  7, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x06008520 - 0x06008560
const Gfx toad_seg6_dl_06008520[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(toad_seg6_dl_060083E8),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};

// US: 8608
#ifndef VERSION_JP
// 0x06008608 - 0x06008650
const Gfx toad_seg6_us_dl_06008608[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(toad_seg6_dl_060083E8),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetRenderMode(G_RM_AA_ZB_XLU_SURF, G_RM_NOOP2),
    gsSPEndDisplayList(),
};
#endif

// 0x06008560
static const Lights1 toad_seg6_lights_06008560 = gdSPDefLights1(
    0x34, 0x20, 0x0d,
    0x68, 0x40, 0x1b, 0x28, 0x28, 0x28
);

// 0x06008578
static const Vtx toad_seg6_vertex_06008578[] = {
    {{{   -17,     17,    -45}, 0, {     0,      0}, {0xce, 0x3b, 0x9d, 0xff}}},
    {{{    18,     17,    -45}, 0, {     0,      0}, {0x28, 0x51, 0xa8, 0xff}}},
    {{{    23,    -15,    -51}, 0, {     0,      0}, {0x1e, 0x98, 0xbf, 0xff}}},
    {{{   -22,    -15,    -51}, 0, {     0,      0}, {0xcf, 0xd9, 0x92, 0xff}}},
    {{{    46,    -15,    -25}, 0, {     0,      0}, {0x77, 0xe2, 0xe3, 0xff}}},
    {{{    34,    -15,     26}, 0, {     0,      0}, {0x70, 0xdf, 0x30, 0xff}}},
    {{{    17,    -15,     52}, 0, {     0,      0}, {0x2c, 0xab, 0x52, 0xff}}},
    {{{   -45,    -15,    -25}, 0, {     0,      0}, {0x89, 0xe0, 0xe6, 0xff}}},
    {{{   -33,    -15,     26}, 0, {     0,      0}, {0xa5, 0xb1, 0x27, 0xff}}},
    {{{   -16,    -15,     52}, 0, {     0,      0}, {0xc7, 0xda, 0x6a, 0xff}}},
    {{{    36,     17,    -20}, 0, {     0,      0}, {0x6f, 0x39, 0xea, 0xff}}},
    {{{   -35,     17,    -20}, 0, {     0,      0}, {0x9b, 0x4b, 0xf9, 0xff}}},
    {{{   -26,     11,     26}, 0, {     0,      0}, {0x99, 0x36, 0x31, 0xff}}},
    {{{   -13,      7,     47}, 0, {     0,      0}, {0xda, 0x51, 0x59, 0xff}}},
    {{{    14,      7,     47}, 0, {     0,      0}, {0x34, 0x3b, 0x63, 0xff}}},
    {{{    27,     11,     26}, 0, {     0,      0}, {0x5f, 0x45, 0x2e, 0xff}}},
};

// 0x06008678
static const Vtx toad_seg6_vertex_06008678[] = {
    {{{   -13,      7,     47}, 0, {     0,      0}, {0xda, 0x51, 0x59, 0xff}}},
    {{{    14,      7,     47}, 0, {     0,      0}, {0x34, 0x3b, 0x63, 0xff}}},
    {{{    14,     21,     26}, 0, {     0,      0}, {0x18, 0x6f, 0x37, 0xff}}},
    {{{   -13,     21,     26}, 0, {     0,      0}, {0xcf, 0x69, 0x31, 0xff}}},
    {{{   -26,     11,     26}, 0, {     0,      0}, {0x99, 0x36, 0x31, 0xff}}},
    {{{    18,     35,    -16}, 0, {     0,      0}, {0x37, 0x72, 0xfd, 0xff}}},
    {{{   -17,     35,    -16}, 0, {     0,      0}, {0xe0, 0x7a, 0xf3, 0xff}}},
    {{{    27,     11,     26}, 0, {     0,      0}, {0x5f, 0x45, 0x2e, 0xff}}},
    {{{   -35,     17,    -20}, 0, {     0,      0}, {0x9b, 0x4b, 0xf9, 0xff}}},
    {{{    34,    -15,     26}, 0, {     0,      0}, {0x70, 0xdf, 0x30, 0xff}}},
    {{{    36,     17,    -20}, 0, {     0,      0}, {0x6f, 0x39, 0xea, 0xff}}},
    {{{    18,     17,    -45}, 0, {     0,      0}, {0x28, 0x51, 0xa8, 0xff}}},
    {{{   -17,     17,    -45}, 0, {     0,      0}, {0xce, 0x3b, 0x9d, 0xff}}},
};

// 0x06008748 - 0x06008890
const Gfx toad_seg6_dl_06008748[] = {
    gsSPLight(&toad_seg6_lights_06008560.l, 1),
    gsSPLight(&toad_seg6_lights_06008560.a, 2),
    gsSPVertex(toad_seg6_vertex_06008578, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  2,  5,  6, 0x0),
    gsSP2Triangles( 2,  7,  3, 0x0,  2,  8,  7, 0x0),
    gsSP2Triangles( 2,  9,  8, 0x0,  2,  6,  9, 0x0),
    gsSP2Triangles( 2,  1, 10, 0x0,  2, 10,  4, 0x0),
    gsSP2Triangles( 7, 11,  0, 0x0, 12, 11,  7, 0x0),
    gsSP2Triangles(12,  7,  8, 0x0,  7,  0,  3, 0x0),
    gsSP2Triangles(12,  8,  9, 0x0, 10,  5,  4, 0x0),
    gsSP2Triangles(12,  9, 13, 0x0, 14,  9,  6, 0x0),
    gsSP2Triangles(14, 13,  9, 0x0,  5, 14,  6, 0x0),
    gsSP1Triangle(10, 15,  5, 0x0),
    gsSPVertex(toad_seg6_vertex_06008678, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 3,  4,  0, 0x0,  2,  5,  6, 0x0),
    gsSP2Triangles( 2,  6,  3, 0x0,  2,  1,  7, 0x0),
    gsSP2Triangles( 7,  5,  2, 0x0,  8,  4,  3, 0x0),
    gsSP2Triangles( 8,  3,  6, 0x0,  9,  7,  1, 0x0),
    gsSP2Triangles( 7, 10,  5, 0x0,  5, 10, 11, 0x0),
    gsSP2Triangles(11,  6,  5, 0x0, 11, 12,  6, 0x0),
    gsSP1Triangle( 6, 12,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x06008890 - 0x060088B8
const Gfx toad_seg6_dl_06008890[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(toad_seg6_dl_06008748),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x060088B8
static const Lights1 toad_seg6_lights_060088B8 = gdSPDefLights1(
    0x34, 0x20, 0x0d,
    0x68, 0x40, 0x1b, 0x28, 0x28, 0x28
);

// 0x060088D0
static const Vtx toad_seg6_vertex_060088D0[] = {
    {{{   -22,    -15,     52}, 0, {     0,      0}, {0xe2, 0x98, 0x41, 0xff}}},
    {{{   -17,     17,     46}, 0, {     0,      0}, {0xd8, 0x51, 0x58, 0xff}}},
    {{{   -35,     17,     21}, 0, {     0,      0}, {0x91, 0x39, 0x16, 0xff}}},
    {{{    23,    -15,     52}, 0, {     0,      0}, {0x31, 0xd9, 0x6e, 0xff}}},
    {{{    18,     17,     46}, 0, {     0,      0}, {0x32, 0x3b, 0x63, 0xff}}},
    {{{   -45,    -15,     26}, 0, {     0,      0}, {0x89, 0xe2, 0x1d, 0xff}}},
    {{{   -33,    -15,    -25}, 0, {     0,      0}, {0x90, 0xdf, 0xd0, 0xff}}},
    {{{   -16,    -15,    -51}, 0, {     0,      0}, {0xd4, 0xab, 0xae, 0xff}}},
    {{{    46,    -15,     26}, 0, {     0,      0}, {0x77, 0xe0, 0x1a, 0xff}}},
    {{{    34,    -15,    -25}, 0, {     0,      0}, {0x5b, 0xb1, 0xd9, 0xff}}},
    {{{    17,    -15,    -51}, 0, {     0,      0}, {0x39, 0xda, 0x96, 0xff}}},
    {{{    27,     11,    -25}, 0, {     0,      0}, {0x67, 0x36, 0xcf, 0xff}}},
    {{{    36,     17,     21}, 0, {     0,      0}, {0x65, 0x4b, 0x07, 0xff}}},
    {{{   -13,      7,    -46}, 0, {     0,      0}, {0xcc, 0x3b, 0x9d, 0xff}}},
    {{{    14,      7,    -46}, 0, {     0,      0}, {0x26, 0x51, 0xa7, 0xff}}},
    {{{   -26,     11,    -25}, 0, {     0,      0}, {0xa1, 0x45, 0xd2, 0xff}}},
};

// 0x060089D0
static const Vtx toad_seg6_vertex_060089D0[] = {
    {{{   -13,     21,    -25}, 0, {     0,      0}, {0xe8, 0x6f, 0xc9, 0xff}}},
    {{{   -17,     35,     17}, 0, {     0,      0}, {0xc9, 0x72, 0x03, 0xff}}},
    {{{    18,     35,     17}, 0, {     0,      0}, {0x20, 0x7a, 0x0d, 0xff}}},
    {{{    14,     21,    -25}, 0, {     0,      0}, {0x31, 0x69, 0xcf, 0xff}}},
    {{{   -26,     11,    -25}, 0, {     0,      0}, {0xa1, 0x45, 0xd2, 0xff}}},
    {{{    14,      7,    -46}, 0, {     0,      0}, {0x26, 0x51, 0xa7, 0xff}}},
    {{{   -13,      7,    -46}, 0, {     0,      0}, {0xcc, 0x3b, 0x9d, 0xff}}},
    {{{    27,     11,    -25}, 0, {     0,      0}, {0x67, 0x36, 0xcf, 0xff}}},
    {{{    36,     17,     21}, 0, {     0,      0}, {0x65, 0x4b, 0x07, 0xff}}},
    {{{   -35,     17,     21}, 0, {     0,      0}, {0x91, 0x39, 0x16, 0xff}}},
    {{{    18,     17,     46}, 0, {     0,      0}, {0x32, 0x3b, 0x63, 0xff}}},
    {{{   -17,     17,     46}, 0, {     0,      0}, {0xd8, 0x51, 0x58, 0xff}}},
};

// 0x06008A90 - 0x06008BD8
const Gfx toad_seg6_dl_06008A90[] = {
    gsSPLight(&toad_seg6_lights_060088B8.l, 1),
    gsSPLight(&toad_seg6_lights_060088B8.a, 2),
    gsSPVertex(toad_seg6_vertex_060088D0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  0,  2,  5, 0x0),
    gsSP2Triangles( 0,  5,  6, 0x0,  0,  6,  7, 0x0),
    gsSP2Triangles( 0,  8,  3, 0x0,  0,  9,  8, 0x0),
    gsSP2Triangles( 0, 10,  9, 0x0,  0,  7, 10, 0x0),
    gsSP2Triangles( 8, 11, 12, 0x0,  8,  9, 11, 0x0),
    gsSP2Triangles( 8, 12,  4, 0x0,  8,  4,  3, 0x0),
    gsSP2Triangles(10, 11,  9, 0x0,  6,  5,  2, 0x0),
    gsSP2Triangles(10, 13, 14, 0x0, 10,  7, 13, 0x0),
    gsSP2Triangles(10, 14, 11, 0x0, 13,  7,  6, 0x0),
    gsSP2Triangles(13,  6, 15, 0x0,  6,  2, 15, 0x0),
    gsSPVertex(toad_seg6_vertex_060089D0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 1,  0,  4, 0x0,  0,  3,  5, 0x0),
    gsSP2Triangles( 0,  5,  6, 0x0,  6,  4,  0, 0x0),
    gsSP2Triangles( 7,  5,  3, 0x0,  3,  8,  7, 0x0),
    gsSP2Triangles( 3,  2,  8, 0x0,  1,  4,  9, 0x0),
    gsSP2Triangles(10,  8,  2, 0x0,  2, 11, 10, 0x0),
    gsSP2Triangles( 2,  1, 11, 0x0,  9, 11,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06008BD8 - 0x06008C00
const Gfx toad_seg6_dl_06008BD8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(toad_seg6_dl_06008A90),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};
