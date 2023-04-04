// Peach

// 0x050009F8
static const Lights1 peach_seg5_lights_050009F8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05000A10
static const Lights1 peach_seg5_lights_05000A10 = gdSPDefLights1(
    0x7f, 0x5f, 0x0c,
    0xff, 0xbf, 0x18, 0x28, 0x28, 0x28
);

// 0x05000A28
ALIGNED8 static const Texture peach_seg5_texture_05000A28[] = {
#include "actors/peach/peach_eye_open.rgba16.inc.c"
};

// 0x05001228
ALIGNED8 static const Texture peach_seg5_texture_05001228[] = {
#include "actors/peach/peach_eye_mostly_open.rgba16.inc.c"
};

// 0x05001A28
ALIGNED8 static const Texture peach_seg5_texture_05001A28[] = {
#include "actors/peach/peach_eye_mostly_closed.rgba16.inc.c"
};

// 0x05002228
ALIGNED8 static const Texture peach_seg5_texture_05002228[] = {
#include "actors/peach/peach_eye_closed.rgba16.inc.c"
};

// 0x05002A28
ALIGNED8 static const Texture peach_seg5_texture_05002A28[] = {
#include "actors/peach/peach_crown_jewel.rgba16.inc.c"
};

// 0x05002C28
ALIGNED8 static const Texture peach_seg5_texture_05002C28[] = {
#include "actors/peach/peach_chest_jewel.rgba16.inc.c"
};

// 0x05002E28
ALIGNED8 static const Texture peach_seg5_texture_05002E28[] = {
#include "actors/peach/peach_lips_scrunched.rgba16.inc.c"
};

// 0x05003628
ALIGNED8 static const Texture peach_seg5_texture_05003628[] = {
#include "actors/peach/peach_lips.rgba16.inc.c"
};

// 0x05003E28
ALIGNED8 static const Texture peach_seg5_texture_05003E28[] = {
#include "actors/peach/peach_nostril.rgba16.inc.c"
};

// 0x05004028
ALIGNED8 static const Texture peach_seg5_texture_05004028[] = {
#include "actors/peach/peach_dress.rgba16.inc.c"
};

// 0x05004828
static const Vtx peach_seg5_vertex_05004828[] = {
    {{{   205,     -4,    -48}, 0, {   265,   1338}, {0x7a, 0xf9, 0xe1, 0xff}}},
    {{{   163,      0,      0}, 0, {   244,   1052}, {0x7e, 0x03, 0x00, 0xff}}},
    {{{   187,    -34,    -19}, 0, {   375,   1162}, {0x2d, 0xac, 0xad, 0xff}}},
    {{{   187,     35,    -23}, 0, {   110,   1197}, {0x2d, 0x54, 0xad, 0xff}}},
    {{{   205,     -4,     49}, 0, {   254,    765}, {0x7a, 0xf9, 0x20, 0xff}}},
    {{{   187,     35,     24}, 0, {   105,    919}, {0x2e, 0x55, 0x52, 0xff}}},
    {{{   214,     46,      0}, 0, {    67,   1060}, {0x71, 0x37, 0xfe, 0xff}}},
    {{{   214,    -40,      0}, 0, {   395,   1046}, {0x74, 0xcd, 0xfd, 0xff}}},
    {{{   187,    -34,     20}, 0, {   371,    931}, {0x2d, 0xab, 0x52, 0xff}}},
    {{{   187,    -34,    -19}, 0, {  -103,    145}, {0x2d, 0xac, 0xad, 0xff}}},
    {{{   214,    -40,      0}, 0, {   223,   -306}, {0x74, 0xcd, 0xfd, 0xff}}},
    {{{   165,    -43,      0}, 0, {   224,    496}, {0x4b, 0x9a, 0x00, 0xff}}},
    {{{   167,    -34,    -19}, 0, {  -103,    463}, {0x4d, 0xad, 0xc8, 0xff}}},
    {{{   214,     46,      0}, 0, {   223,   -304}, {0x71, 0x37, 0xfe, 0xff}}},
    {{{   156,     40,      0}, 0, {   224,    648}, {0x4e, 0x64, 0x00, 0xff}}},
    {{{   187,     35,     24}, 0, {   618,    141}, {0x2e, 0x55, 0x52, 0xff}}},
};

// 0x05004928
static const Vtx peach_seg5_vertex_05004928[] = {
    {{{   165,    -43,      0}, 0, {   224,    496}, {0x4b, 0x9a, 0x00, 0xff}}},
    {{{   214,    -40,      0}, 0, {   223,   -306}, {0x74, 0xcd, 0xfd, 0xff}}},
    {{{   187,    -34,     20}, 0, {   551,    145}, {0x2d, 0xab, 0x52, 0xff}}},
    {{{   167,    -34,     20}, 0, {   551,    463}, {0x4c, 0xac, 0x38, 0xff}}},
    {{{   169,     -4,    -42}, 0, {   224,    511}, {0x4a, 0xf1, 0x9b, 0xff}}},
    {{{   187,     35,    -23}, 0, {  -581,    154}, {0x2d, 0x54, 0xad, 0xff}}},
    {{{   205,     -4,    -48}, 0, {   225,   -213}, {0x7a, 0xf9, 0xe1, 0xff}}},
    {{{   187,    -34,    -19}, 0, {   829,    159}, {0x2d, 0xac, 0xad, 0xff}}},
    {{{   168,     35,    -23}, 0, {  -586,    534}, {0x6c, 0x3d, 0xe9, 0xff}}},
    {{{   167,    -34,    -19}, 0, {   824,    539}, {0x4d, 0xad, 0xc8, 0xff}}},
    {{{   156,     40,      0}, 0, {   224,    648}, {0x4e, 0x64, 0x00, 0xff}}},
    {{{   214,     46,      0}, 0, {   223,   -304}, {0x71, 0x37, 0xfe, 0xff}}},
    {{{   187,     35,    -23}, 0, {  -170,    141}, {0x2d, 0x54, 0xad, 0xff}}},
    {{{   168,     35,     24}, 0, {   618,    459}, {0x6c, 0x3d, 0x17, 0xff}}},
    {{{   187,     35,     24}, 0, {   618,    141}, {0x2e, 0x55, 0x52, 0xff}}},
};

// 0x05004A18
static const Vtx peach_seg5_vertex_05004A18[] = {
    {{{   169,     -4,     43}, 0, {   224,    511}, {0x4a, 0xf1, 0x65, 0xff}}},
    {{{   167,    -34,     20}, 0, {   824,    539}, {0x4c, 0xac, 0x38, 0xff}}},
    {{{   187,    -34,     20}, 0, {   829,    159}, {0x2d, 0xab, 0x52, 0xff}}},
    {{{   168,     35,    -23}, 0, {  -170,    459}, {0x6c, 0x3d, 0xe9, 0xff}}},
    {{{   156,     40,      0}, 0, {   224,    648}, {0x4e, 0x64, 0x00, 0xff}}},
    {{{   187,     35,    -23}, 0, {  -170,    141}, {0x2d, 0x54, 0xad, 0xff}}},
    {{{   168,     35,     24}, 0, {  -586,    534}, {0x6c, 0x3d, 0x17, 0xff}}},
    {{{   187,     35,     24}, 0, {  -581,    154}, {0x2e, 0x55, 0x52, 0xff}}},
    {{{   205,     -4,     49}, 0, {   225,   -213}, {0x7a, 0xf9, 0x20, 0xff}}},
};

// 0x05004AA8
static const Vtx peach_seg5_vertex_05004AA8[] = {
    {{{    16,     89,     -9}, 0, {  -141,    397}, {0xe1, 0x72, 0xd4, 0xff}}},
    {{{    16,     89,     10}, 0, {   547,    428}, {0xe2, 0x73, 0x2b, 0xff}}},
    {{{    26,    102,      0}, 0, {   230,   -159}, {0xf4, 0x7e, 0xfe, 0xff}}},
};

// 0x05004AD8
static const Vtx peach_seg5_vertex_05004AD8[] = {
    {{{    59,     -8,     86}, 0, {  1146,  -1042}, {0xcd, 0x1d, 0x6f, 0xff}}},
    {{{    20,     28,     56}, 0, {   774,   -582}, {0xa7, 0x05, 0x59, 0xff}}},
    {{{    17,     11,     58}, 0, {   740,   -552}, {0xa5, 0x15, 0x55, 0xff}}},
    {{{    32,    -11,     82}, 0, {  1028,   -674}, {0xc8, 0x17, 0x6f, 0xff}}},
    {{{     1,     38,     35}, 0, {   536,   -374}, {0x9a, 0x03, 0x4a, 0xff}}},
    {{{    16,     69,     48}, 0, {   606,   -580}, {0xbd, 0x39, 0x5a, 0xff}}},
    {{{   -10,     -6,      0}, 0, {    40,   -324}, {0x82, 0xf1, 0x00, 0xff}}},
    {{{    59,     -8,    -85}, 0, {  -676,  -1632}, {0xcd, 0x1d, 0x91, 0xff}}},
    {{{    32,    -11,    -81}, 0, {  -706,  -1234}, {0xc8, 0x17, 0x91, 0xff}}},
    {{{    17,     11,    -57}, 0, {  -494,   -950}, {0xa5, 0x15, 0xab, 0xff}}},
    {{{    20,     28,    -55}, 0, {  -514,   -998}, {0xa7, 0x05, 0xa7, 0xff}}},
    {{{     1,     38,    -34}, 0, {  -380,   -670}, {0x9a, 0x03, 0xb6, 0xff}}},
    {{{    16,     69,    -47}, 0, {  -366,   -894}, {0xbe, 0x39, 0xa5, 0xff}}},
    {{{    44,     56,    -57}, 0, {  1116,    782}, {0xf0, 0x5b, 0xaa, 0xff}}},
    {{{    17,     81,    -32}, 0, {   618,   1170}, {0xe7, 0x68, 0xbd, 0xff}}},
    {{{    45,     79,    -36}, 0, {   714,    746}, {0xf0, 0x6f, 0xc6, 0xff}}},
};

// 0x05004BD8
static const Vtx peach_seg5_vertex_05004BD8[] = {
    {{{     1,     38,    -34}, 0, {  -380,   -670}, {0x9a, 0x03, 0xb6, 0xff}}},
    {{{   -10,     -6,      0}, 0, {    40,   -324}, {0x82, 0xf1, 0x00, 0xff}}},
    {{{   -20,     72,      0}, 0, {    18,   -210}, {0x96, 0x45, 0x00, 0xff}}},
    {{{    17,     81,     33}, 0, {   464,   -628}, {0xe6, 0x68, 0x43, 0xff}}},
    {{{    16,     69,     48}, 0, {   606,   -580}, {0xbd, 0x39, 0x5a, 0xff}}},
    {{{     1,     38,     35}, 0, {   536,   -374}, {0x9a, 0x03, 0x4a, 0xff}}},
    {{{    16,     69,    -47}, 0, {  -366,   -894}, {0xbe, 0x39, 0xa5, 0xff}}},
    {{{    17,     81,    -32}, 0, {  -224,   -850}, {0xe7, 0x68, 0xbd, 0xff}}},
    {{{   102,     92,    -30}, 0, {   588,   -144}, {0xcf, 0x63, 0xc4, 0xff}}},
    {{{    73,     65,    -65}, 0, {  1286,    354}, {0xd5, 0x50, 0xa9, 0xff}}},
    {{{    45,     79,    -36}, 0, {   714,    746}, {0xf0, 0x6f, 0xc6, 0xff}}},
    {{{    85,     99,      0}, 0, {     0,     64}, {0xcd, 0x74, 0x00, 0xff}}},
    {{{    17,     81,    -32}, 0, {   618,   1170}, {0xe7, 0x68, 0xbd, 0xff}}},
    {{{    16,     89,     -9}, 0, {   160,   1136}, {0xe1, 0x72, 0xd4, 0xff}}},
    {{{    44,     56,    -57}, 0, {  1116,    782}, {0xf0, 0x5b, 0xaa, 0xff}}},
};

// 0x05004CC8
static const Vtx peach_seg5_vertex_05004CC8[] = {
    {{{    45,     79,     37}, 0, {   714,    746}, {0xf1, 0x6f, 0x3a, 0xff}}},
    {{{   102,     92,     31}, 0, {   588,   -144}, {0xce, 0x64, 0x3b, 0xff}}},
    {{{    85,     99,      0}, 0, {     0,     64}, {0xcd, 0x74, 0x00, 0xff}}},
    {{{    45,     79,    -36}, 0, {   714,    746}, {0xf0, 0x6f, 0xc6, 0xff}}},
    {{{    41,     89,      0}, 0, {     0,    738}, {0x0b, 0x7e, 0xff, 0xff}}},
    {{{    16,     89,     -9}, 0, {    16,   -772}, {0xe1, 0x72, 0xd4, 0xff}}},
    {{{    26,    102,      0}, 0, {   144,   -870}, {0xf4, 0x7e, 0xfe, 0xff}}},
    {{{    41,     89,      0}, 0, {   188,  -1094}, {0x0b, 0x7e, 0xff, 0xff}}},
    {{{    16,     89,     10}, 0, {   222,   -704}, {0xe2, 0x73, 0x2b, 0xff}}},
    {{{    16,     89,     -9}, 0, {   160,   1136}, {0xe1, 0x72, 0xd4, 0xff}}},
    {{{    16,     69,    -47}, 0, {   882,   1192}, {0xbe, 0x39, 0xa5, 0xff}}},
    {{{    17,     81,    -32}, 0, {   618,   1170}, {0xe7, 0x68, 0xbd, 0xff}}},
    {{{    44,     56,    -57}, 0, {  1116,    782}, {0xf0, 0x5b, 0xaa, 0xff}}},
    {{{    16,     89,     10}, 0, {   160,   1136}, {0xe2, 0x73, 0x2b, 0xff}}},
    {{{    17,     81,     33}, 0, {   618,   1170}, {0xe6, 0x68, 0x43, 0xff}}},
};

// 0x05004DB8
static const Vtx peach_seg5_vertex_05004DB8[] = {
    {{{    45,     79,     37}, 0, {   714,    746}, {0xf1, 0x6f, 0x3a, 0xff}}},
    {{{    44,     56,     58}, 0, {  1116,    782}, {0xf0, 0x5b, 0x56, 0xff}}},
    {{{    73,     65,     66}, 0, {  1286,    354}, {0xd5, 0x50, 0x57, 0xff}}},
    {{{    17,     81,     33}, 0, {   618,   1170}, {0xe6, 0x68, 0x43, 0xff}}},
    {{{   102,     92,     31}, 0, {   588,   -144}, {0xce, 0x64, 0x3b, 0xff}}},
    {{{    17,     81,     33}, 0, {   608,   1114}, {0xe6, 0x68, 0x43, 0xff}}},
    {{{    16,     69,     48}, 0, {   960,   1118}, {0xbd, 0x39, 0x5a, 0xff}}},
    {{{    44,     56,     58}, 0, {  1290,    720}, {0xf0, 0x5b, 0x56, 0xff}}},
};

// 0x05004E38
static const Vtx peach_seg5_vertex_05004E38[] = {
    {{{   -20,     72,      0}, 0, {   480,   2010}, {0x96, 0x45, 0x00, 0xff}}},
    {{{    17,     81,     33}, 0, {  2102,    128}, {0xe6, 0x68, 0x43, 0xff}}},
    {{{    16,     89,     10}, 0, {   962,    -24}, {0xe2, 0x73, 0x2b, 0xff}}},
    {{{    17,     81,    -32}, 0, { -1142,    128}, {0xe7, 0x68, 0xbd, 0xff}}},
    {{{    16,     89,     -9}, 0, {    -4,    -24}, {0xe1, 0x72, 0xd4, 0xff}}},
};

// 0x05004E88
static const Vtx peach_seg5_vertex_05004E88[] = {
    {{{    18,    -87,    -62}, 0, {     0,      0}, {0xa9, 0xc8, 0xb7, 0xff}}},
    {{{    17,     11,    -57}, 0, {     0,      0}, {0xa5, 0x15, 0xab, 0xff}}},
    {{{    32,    -11,    -81}, 0, {     0,      0}, {0xc8, 0x17, 0x91, 0xff}}},
    {{{    98,    -89,    -74}, 0, {     0,      0}, {0x31, 0x98, 0xcc, 0xff}}},
    {{{    61,    -56,   -115}, 0, {     0,      0}, {0xc4, 0xd9, 0x98, 0xff}}},
    {{{    39,   -109,      0}, 0, {     0,      0}, {0xf0, 0x83, 0x00, 0xff}}},
    {{{    59,     -8,    -85}, 0, {     0,      0}, {0xcd, 0x1d, 0x91, 0xff}}},
    {{{   -10,     -6,      0}, 0, {     0,      0}, {0x82, 0xf1, 0x00, 0xff}}},
    {{{    10,    -93,      0}, 0, {     0,      0}, {0x9b, 0xb4, 0x00, 0xff}}},
    {{{    18,    -87,     63}, 0, {     0,      0}, {0xa9, 0xc8, 0x48, 0xff}}},
    {{{    98,    -89,     75}, 0, {     0,      0}, {0x31, 0x97, 0x33, 0xff}}},
    {{{    61,    -56,    116}, 0, {     0,      0}, {0xc4, 0xd9, 0x68, 0xff}}},
    {{{    59,     -8,     86}, 0, {     0,      0}, {0xcd, 0x1d, 0x6f, 0xff}}},
    {{{    17,     11,     58}, 0, {     0,      0}, {0xa5, 0x15, 0x55, 0xff}}},
    {{{    32,    -11,     82}, 0, {     0,      0}, {0xc8, 0x17, 0x6f, 0xff}}},
};

// 0x05004F78
static const Vtx peach_seg5_vertex_05004F78[] = {
    {{{    59,     -8,     86}, 0, {     0,      0}, {0xcd, 0x1d, 0x6f, 0xff}}},
    {{{   100,    -62,    128}, 0, {     0,      0}, {0x1b, 0x17, 0x79, 0xff}}},
    {{{    92,    -29,    104}, 0, {     0,      0}, {0x36, 0x08, 0x72, 0xff}}},
    {{{    61,    -56,    116}, 0, {     0,      0}, {0xc4, 0xd9, 0x68, 0xff}}},
    {{{    20,     28,     56}, 0, {     0,      0}, {0xa7, 0x05, 0x59, 0xff}}},
    {{{    35,     37,     77}, 0, {     0,      0}, {0xd0, 0x29, 0x6e, 0xff}}},
    {{{    66,     12,     84}, 0, {     0,      0}, {0xdd, 0x22, 0x74, 0xff}}},
    {{{    97,      3,    107}, 0, {     0,      0}, {0xe4, 0x06, 0x7b, 0xff}}},
    {{{    97,      3,   -106}, 0, {     0,      0}, {0xe4, 0x06, 0x85, 0xff}}},
    {{{    59,     -8,    -85}, 0, {     0,      0}, {0xcd, 0x1d, 0x91, 0xff}}},
    {{{    66,     12,    -83}, 0, {     0,      0}, {0xdd, 0x22, 0x8c, 0xff}}},
    {{{    35,     37,    -76}, 0, {     0,      0}, {0xd0, 0x29, 0x92, 0xff}}},
    {{{    73,     65,    -65}, 0, {     0,      0}, {0xd5, 0x50, 0xa9, 0xff}}},
    {{{   100,    -62,   -127}, 0, {     0,      0}, {0x1b, 0x17, 0x87, 0xff}}},
    {{{    61,    -56,   -115}, 0, {     0,      0}, {0xc4, 0xd9, 0x98, 0xff}}},
    {{{    92,    -29,   -103}, 0, {     0,      0}, {0x36, 0x08, 0x8e, 0xff}}},
};

// 0x05005078
static const Vtx peach_seg5_vertex_05005078[] = {
    {{{    35,     37,    -76}, 0, {     0,      0}, {0xd0, 0x29, 0x92, 0xff}}},
    {{{    59,     -8,    -85}, 0, {     0,      0}, {0xcd, 0x1d, 0x91, 0xff}}},
    {{{    20,     28,    -55}, 0, {     0,      0}, {0xa7, 0x05, 0xa7, 0xff}}},
    {{{    97,      3,   -106}, 0, {     0,      0}, {0xe4, 0x06, 0x85, 0xff}}},
    {{{    92,    -29,   -103}, 0, {     0,      0}, {0x36, 0x08, 0x8e, 0xff}}},
    {{{    16,     69,    -47}, 0, {     0,      0}, {0xbe, 0x39, 0xa5, 0xff}}},
    {{{    44,     56,    -57}, 0, {     0,      0}, {0xf0, 0x5b, 0xaa, 0xff}}},
    {{{    73,     65,    -65}, 0, {     0,      0}, {0xd5, 0x50, 0xa9, 0xff}}},
    {{{   137,     48,   -115}, 0, {     0,      0}, {0x39, 0x1c, 0x93, 0xff}}},
    {{{   102,     92,    -30}, 0, {     0,      0}, {0xcf, 0x63, 0xc4, 0xff}}},
    {{{   138,    104,    -46}, 0, {     0,      0}, {0x0b, 0x6f, 0xc5, 0xff}}},
    {{{   134,    -13,    -79}, 0, {     0,      0}, {0x4d, 0xd6, 0xa5, 0xff}}},
    {{{    20,     28,     56}, 0, {     0,      0}, {0xa7, 0x05, 0x59, 0xff}}},
    {{{    35,     37,     77}, 0, {     0,      0}, {0xd0, 0x29, 0x6e, 0xff}}},
    {{{    16,     69,     48}, 0, {     0,      0}, {0xbd, 0x39, 0x5a, 0xff}}},
};

// 0x05005168
static const Vtx peach_seg5_vertex_05005168[] = {
    {{{    44,     56,     58}, 0, {     0,      0}, {0xf0, 0x5b, 0x56, 0xff}}},
    {{{    35,     37,     77}, 0, {     0,      0}, {0xd0, 0x29, 0x6e, 0xff}}},
    {{{    73,     65,     66}, 0, {     0,      0}, {0xd5, 0x50, 0x57, 0xff}}},
    {{{    66,     12,     84}, 0, {     0,      0}, {0xdd, 0x22, 0x74, 0xff}}},
    {{{    16,     69,     48}, 0, {     0,      0}, {0xbd, 0x39, 0x5a, 0xff}}},
    {{{    97,      3,    107}, 0, {     0,      0}, {0xe4, 0x06, 0x7b, 0xff}}},
    {{{   134,    -13,     80}, 0, {     0,      0}, {0x4d, 0xd6, 0x5b, 0xff}}},
    {{{   137,     48,    116}, 0, {     0,      0}, {0x39, 0x1c, 0x6d, 0xff}}},
    {{{    92,    -29,    104}, 0, {     0,      0}, {0x36, 0x08, 0x72, 0xff}}},
    {{{   138,    104,     47}, 0, {     0,      0}, {0x0b, 0x6f, 0x3b, 0xff}}},
    {{{   102,     92,     31}, 0, {     0,      0}, {0xce, 0x64, 0x3b, 0xff}}},
    {{{   134,    -13,    -79}, 0, {     0,      0}, {0x4d, 0xd6, 0xa5, 0xff}}},
    {{{   165,    -32,    -46}, 0, {     0,      0}, {0x6e, 0xd7, 0xd1, 0xff}}},
    {{{   131,    -79,    -38}, 0, {     0,      0}, {0x57, 0xb2, 0xd1, 0xff}}},
    {{{   165,    -43,      0}, 0, {     0,      0}, {0x4b, 0x9a, 0x00, 0xff}}},
    {{{   131,    -87,      0}, 0, {     0,      0}, {0x44, 0x96, 0x00, 0xff}}},
};

// 0x05005268
static const Vtx peach_seg5_vertex_05005268[] = {
    {{{   131,    -87,      0}, 0, {     0,      0}, {0x44, 0x96, 0x00, 0xff}}},
    {{{    98,    -89,    -74}, 0, {     0,      0}, {0x31, 0x98, 0xcc, 0xff}}},
    {{{   131,    -79,    -38}, 0, {     0,      0}, {0x57, 0xb2, 0xd1, 0xff}}},
    {{{   134,    -13,    -79}, 0, {     0,      0}, {0x4d, 0xd6, 0xa5, 0xff}}},
    {{{    61,    -56,   -115}, 0, {     0,      0}, {0xc4, 0xd9, 0x98, 0xff}}},
    {{{   100,    -62,   -127}, 0, {     0,      0}, {0x1b, 0x17, 0x87, 0xff}}},
    {{{    98,    -98,      0}, 0, {     0,      0}, {0x1f, 0x86, 0x00, 0xff}}},
    {{{    39,   -109,      0}, 0, {     0,      0}, {0xf0, 0x83, 0x00, 0xff}}},
    {{{    92,    -29,   -103}, 0, {     0,      0}, {0x36, 0x08, 0x8e, 0xff}}},
    {{{   169,     -4,    -42}, 0, {     0,      0}, {0x4a, 0xf1, 0x9b, 0xff}}},
    {{{   169,     23,    -51}, 0, {     0,      0}, {0x77, 0xf8, 0xd6, 0xff}}},
    {{{   168,     35,    -23}, 0, {     0,      0}, {0x6c, 0x3d, 0xe9, 0xff}}},
    {{{   165,    -32,    -46}, 0, {     0,      0}, {0x6e, 0xd7, 0xd1, 0xff}}},
    {{{   137,     48,   -115}, 0, {     0,      0}, {0x39, 0x1c, 0x93, 0xff}}},
    {{{   138,    104,    -46}, 0, {     0,      0}, {0x0b, 0x6f, 0xc5, 0xff}}},
    {{{   167,     79,    -39}, 0, {     0,      0}, {0x77, 0x29, 0xfa, 0xff}}},
};

// 0x05005368
static const Vtx peach_seg5_vertex_05005368[] = {
    {{{   148,    108,      0}, 0, {     0,      0}, {0x5e, 0x55, 0x00, 0xff}}},
    {{{   138,    104,    -46}, 0, {     0,      0}, {0x0b, 0x6f, 0xc5, 0xff}}},
    {{{   105,    124,      0}, 0, {     0,      0}, {0xd3, 0x76, 0x00, 0xff}}},
    {{{   102,     92,    -30}, 0, {     0,      0}, {0xcf, 0x63, 0xc4, 0xff}}},
    {{{   167,     79,    -39}, 0, {     0,      0}, {0x77, 0x29, 0xfa, 0xff}}},
    {{{   169,     23,    -51}, 0, {     0,      0}, {0x77, 0xf8, 0xd6, 0xff}}},
    {{{   137,     48,   -115}, 0, {     0,      0}, {0x39, 0x1c, 0x93, 0xff}}},
    {{{    85,     99,      0}, 0, {     0,      0}, {0xcd, 0x74, 0x00, 0xff}}},
    {{{   168,     35,    -23}, 0, {     0,      0}, {0x6c, 0x3d, 0xe9, 0xff}}},
    {{{   156,     40,      0}, 0, {     0,      0}, {0x4e, 0x64, 0x00, 0xff}}},
    {{{   169,     -4,    -42}, 0, {     0,      0}, {0x4a, 0xf1, 0x9b, 0xff}}},
    {{{   165,    -32,    -46}, 0, {     0,      0}, {0x6e, 0xd7, 0xd1, 0xff}}},
    {{{   138,    104,     47}, 0, {     0,      0}, {0x0b, 0x6f, 0x3b, 0xff}}},
    {{{   167,     79,     40}, 0, {     0,      0}, {0x77, 0x29, 0x07, 0xff}}},
    {{{   167,    -34,    -19}, 0, {     0,      0}, {0x4d, 0xad, 0xc8, 0xff}}},
    {{{   165,    -43,      0}, 0, {     0,      0}, {0x4b, 0x9a, 0x00, 0xff}}},
};

// 0x05005468
static const Vtx peach_seg5_vertex_05005468[] = {
    {{{    98,    -89,     75}, 0, {     0,      0}, {0x31, 0x97, 0x33, 0xff}}},
    {{{    98,    -98,      0}, 0, {     0,      0}, {0x1f, 0x86, 0x00, 0xff}}},
    {{{   131,    -87,      0}, 0, {     0,      0}, {0x44, 0x96, 0x00, 0xff}}},
    {{{   165,    -43,      0}, 0, {     0,      0}, {0x4b, 0x9a, 0x00, 0xff}}},
    {{{   131,    -79,     39}, 0, {     0,      0}, {0x57, 0xb1, 0x2e, 0xff}}},
    {{{   168,     35,     24}, 0, {     0,      0}, {0x6c, 0x3d, 0x17, 0xff}}},
    {{{   156,     40,      0}, 0, {     0,      0}, {0x4e, 0x64, 0x00, 0xff}}},
    {{{   167,     79,     40}, 0, {     0,      0}, {0x77, 0x29, 0x07, 0xff}}},
    {{{   148,    108,      0}, 0, {     0,      0}, {0x5e, 0x55, 0x00, 0xff}}},
    {{{   138,    104,     47}, 0, {     0,      0}, {0x0b, 0x6f, 0x3b, 0xff}}},
    {{{   105,    124,      0}, 0, {     0,      0}, {0xd3, 0x76, 0x00, 0xff}}},
    {{{   102,     92,     31}, 0, {     0,      0}, {0xce, 0x64, 0x3b, 0xff}}},
    {{{    85,     99,      0}, 0, {     0,      0}, {0xcd, 0x74, 0x00, 0xff}}},
    {{{    39,   -109,      0}, 0, {     0,      0}, {0xf0, 0x83, 0x00, 0xff}}},
    {{{   165,    -32,     47}, 0, {     0,      0}, {0x6e, 0xd7, 0x2f, 0xff}}},
    {{{   167,    -34,     20}, 0, {     0,      0}, {0x4c, 0xac, 0x38, 0xff}}},
};

// 0x05005568
static const Vtx peach_seg5_vertex_05005568[] = {
    {{{    98,    -89,     75}, 0, {     0,      0}, {0x31, 0x97, 0x33, 0xff}}},
    {{{   134,    -13,     80}, 0, {     0,      0}, {0x4d, 0xd6, 0x5b, 0xff}}},
    {{{    92,    -29,    104}, 0, {     0,      0}, {0x36, 0x08, 0x72, 0xff}}},
    {{{   169,     23,     52}, 0, {     0,      0}, {0x77, 0xf8, 0x2a, 0xff}}},
    {{{   137,     48,    116}, 0, {     0,      0}, {0x39, 0x1c, 0x6d, 0xff}}},
    {{{   165,    -32,     47}, 0, {     0,      0}, {0x6e, 0xd7, 0x2f, 0xff}}},
    {{{   131,    -79,     39}, 0, {     0,      0}, {0x57, 0xb1, 0x2e, 0xff}}},
    {{{   100,    -62,    128}, 0, {     0,      0}, {0x1b, 0x17, 0x79, 0xff}}},
    {{{   169,     -4,     43}, 0, {     0,      0}, {0x4a, 0xf1, 0x65, 0xff}}},
    {{{   168,     35,     24}, 0, {     0,      0}, {0x6c, 0x3d, 0x17, 0xff}}},
    {{{   167,     79,     40}, 0, {     0,      0}, {0x77, 0x29, 0x07, 0xff}}},
    {{{   138,    104,     47}, 0, {     0,      0}, {0x0b, 0x6f, 0x3b, 0xff}}},
    {{{    61,    -56,    116}, 0, {     0,      0}, {0xc4, 0xd9, 0x68, 0xff}}},
    {{{   167,    -34,     20}, 0, {     0,      0}, {0x4c, 0xac, 0x38, 0xff}}},
};

// 0x05005648 - 0x05005750
const Gfx peach_seg5_dl_05005648[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05002A28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 16 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&peach_seg5_lights_050009F8.l, 1),
    gsSPLight(&peach_seg5_lights_050009F8.a, 2),
    gsSPVertex(peach_seg5_vertex_05004828, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  1,  5, 0x0,  5,  1,  6, 0x0),
    gsSP2Triangles( 7,  1,  8, 0x0,  6,  1,  3, 0x0),
    gsSP2Triangles( 2,  1,  7, 0x0,  8,  1,  4, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0, 11, 12,  9, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(peach_seg5_vertex_05004928, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  7,  4, 0x0),
    gsSP2Triangles( 8,  5,  4, 0x0,  9,  4,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 10, 0x0),
    gsSPVertex(peach_seg5_vertex_05004A18, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  0,  7, 0x0,  7,  0,  8, 0x0),
    gsSP1Triangle( 2,  8,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005750 - 0x05005780
const Gfx peach_seg5_dl_05005750[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05003E28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 16 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(peach_seg5_vertex_05004AA8, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005780 - 0x050058B8
const Gfx peach_seg5_dl_05005780[] = {
    gsSPVertex(peach_seg5_vertex_05004AD8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  5,  4,  1, 0x0),
    gsSP2Triangles( 6,  2,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0,  9, 11, 10, 0x0),
    gsSP2Triangles( 9,  6, 11, 0x0, 10, 11, 12, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(peach_seg5_vertex_05004BD8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  4, 0x0),
    gsSP2Triangles( 4,  2,  5, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 0,  2,  6, 0x0,  2,  7,  6, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(10, 12, 13, 0x0, 14, 10,  9, 0x0),
    gsSPVertex(peach_seg5_vertex_05004CC8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  6,  8,  7, 0x0),
    gsSP2Triangles( 3,  9,  4, 0x0, 10, 11, 12, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  0,  4, 13, 0x0),
    gsSP1Triangle(14,  0, 13, 0x0),
    gsSPVertex(peach_seg5_vertex_05004DB8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  5,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x050058B8 - 0x050058E0
const Gfx peach_seg5_dl_050058B8[] = {
    gsSPVertex(peach_seg5_vertex_05004E38, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP1Triangle( 0,  2,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x050058E0 - 0x05005C48
const Gfx peach_seg5_dl_050058E0[] = {
    gsSPLight(&peach_seg5_lights_05000A10.l, 1),
    gsSPLight(&peach_seg5_lights_05000A10.a, 2),
    gsSPVertex(peach_seg5_vertex_05004E88, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 3,  5,  0, 0x0,  6,  4,  0, 0x0),
    gsSP2Triangles( 7,  1,  0, 0x0,  2,  6,  0, 0x0),
    gsSP2Triangles( 7,  0,  8, 0x0,  8,  0,  5, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0,  5, 10,  9, 0x0),
    gsSP2Triangles( 9, 11, 12, 0x0, 13,  7,  9, 0x0),
    gsSP2Triangles( 8,  9,  7, 0x0,  9, 12, 14, 0x0),
    gsSP2Triangles( 5,  9,  8, 0x0, 14, 13,  9, 0x0),
    gsSPVertex(peach_seg5_vertex_05004F78, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  0,  5, 0x0,  5,  0,  6, 0x0),
    gsSP2Triangles( 2,  7,  0, 0x0,  6,  0,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 12,  8, 0x0, 11, 12, 10, 0x0),
    gsSP2Triangles( 9, 13, 14, 0x0, 15, 13,  9, 0x0),
    gsSPVertex(peach_seg5_vertex_05005078, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 5,  0,  2, 0x0,  0,  5,  6, 0x0),
    gsSP2Triangles( 6,  7,  0, 0x0,  8,  3,  7, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 10,  8,  7, 0x0),
    gsSP2Triangles( 8, 11,  3, 0x0,  3, 11,  4, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(peach_seg5_vertex_05005168, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  1, 0x0),
    gsSP2Triangles( 4,  1,  0, 0x0,  5,  2,  3, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  2,  5,  7, 0x0),
    gsSP2Triangles( 6,  5,  8, 0x0,  7,  9,  2, 0x0),
    gsSP2Triangles(10,  2,  9, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(12, 14, 13, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(peach_seg5_vertex_05005268, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 1,  4,  5, 0x0,  1,  6,  7, 0x0),
    gsSP2Triangles( 3,  1,  8, 0x0,  8,  1,  5, 0x0),
    gsSP2Triangles( 6,  1,  0, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(10, 12,  3, 0x0, 13, 10,  3, 0x0),
    gsSP1Triangle(14, 15, 13, 0x0),
    gsSPVertex(peach_seg5_vertex_05005368, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  1,  0, 0x0,  5,  6,  4, 0x0),
    gsSP2Triangles( 7,  2,  3, 0x0,  5,  4,  8, 0x0),
    gsSP2Triangles( 9,  4,  0, 0x0,  8,  4,  9, 0x0),
    gsSP2Triangles(10, 11,  5, 0x0, 12, 13,  0, 0x0),
    gsSP2Triangles(14, 15, 11, 0x0, 14, 11, 10, 0x0),
    gsSPVertex(peach_seg5_vertex_05005468, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 7,  6,  8, 0x0,  9,  8, 10, 0x0),
    gsSP2Triangles(11,  9, 10, 0x0, 10, 12, 11, 0x0),
    gsSP2Triangles(13,  1,  0, 0x0, 14,  4,  3, 0x0),
    gsSP1Triangle( 3, 15, 14, 0x0),
    gsSPVertex(peach_seg5_vertex_05005568, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 5,  1,  6, 0x0,  0,  6,  1, 0x0),
    gsSP2Triangles( 1,  5,  3, 0x0,  2,  7,  0, 0x0),
    gsSP2Triangles( 3,  8,  9, 0x0,  5,  8,  3, 0x0),
    gsSP2Triangles( 3,  9, 10, 0x0,  3, 10,  4, 0x0),
    gsSP2Triangles(11,  4, 10, 0x0, 12,  0,  7, 0x0),
    gsSP1Triangle( 5, 13,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005C48 - 0x05005CB0
const Gfx peach_seg5_dl_05005C48[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 4, G_TX_NOLOD, G_TX_CLAMP, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(peach_seg5_dl_05005648),
    gsSPDisplayList(peach_seg5_dl_05005750),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x05005CB0 - 0x05005CE0
const Gfx peach_seg5_dl_05005CB0[] = {
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(peach_seg5_dl_050058E0),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x05005CE0 - 0x05005D38
const Gfx peach_seg5_dl_05005CE0[] = {
    gsSPDisplayList(peach_seg5_dl_05005C48),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05000A28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_05005780),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05002E28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_050058B8),
    gsSPDisplayList(peach_seg5_dl_05005CB0),
    gsSPEndDisplayList(),
};

// 0x05005D38 - 0x05005D90
const Gfx peach_seg5_dl_05005D38[] = {
    gsSPDisplayList(peach_seg5_dl_05005C48),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05001228),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_05005780),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05002E28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_050058B8),
    gsSPDisplayList(peach_seg5_dl_05005CB0),
    gsSPEndDisplayList(),
};

// 0x05005D90 - 0x05005DE8
const Gfx peach_seg5_dl_05005D90[] = {
    gsSPDisplayList(peach_seg5_dl_05005C48),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05001A28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_05005780),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05002E28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_050058B8),
    gsSPDisplayList(peach_seg5_dl_05005CB0),
    gsSPEndDisplayList(),
};

// 0x05005DE8 - 0x05005E40
const Gfx peach_seg5_dl_05005DE8[] = {
    gsSPDisplayList(peach_seg5_dl_05005C48),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05002228),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_05005780),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05002E28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_050058B8),
    gsSPDisplayList(peach_seg5_dl_05005CB0),
    gsSPEndDisplayList(),
};

// 0x05005E40 - 0x05005E98
const Gfx peach_seg5_dl_05005E40[] = {
    gsSPDisplayList(peach_seg5_dl_05005C48),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05000A28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_05005780),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05003628),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_050058B8),
    gsSPDisplayList(peach_seg5_dl_05005CB0),
    gsSPEndDisplayList(),
};

// 0x05005E98 - 0x05005EF0
const Gfx peach_seg5_dl_05005E98[] = {
    gsSPDisplayList(peach_seg5_dl_05005C48),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05001228),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_05005780),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05003628),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_050058B8),
    gsSPDisplayList(peach_seg5_dl_05005CB0),
    gsSPEndDisplayList(),
};

// 0x05005EF0 - 0x05005F48
const Gfx peach_seg5_dl_05005EF0[] = {
    gsSPDisplayList(peach_seg5_dl_05005C48),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05001A28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_05005780),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05003628),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_050058B8),
    gsSPDisplayList(peach_seg5_dl_05005CB0),
    gsSPEndDisplayList(),
};

// 0x05005F48 - 0x05005FA0
const Gfx peach_seg5_dl_05005F48[] = {
    gsSPDisplayList(peach_seg5_dl_05005C48),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05002228),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_05005780),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05003628),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(peach_seg5_dl_050058B8),
    gsSPDisplayList(peach_seg5_dl_05005CB0),
    gsSPEndDisplayList(),
};

// 0x05005FA0
static const Lights1 peach_seg5_lights_05005FA0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05005FB8
static const Vtx peach_seg5_vertex_05005FB8[] = {
    {{{    96,     28,      1}, 0, {   246,   2076}, {0x0a, 0x7b, 0x1c, 0xff}}},
    {{{    96,      9,     27}, 0, {    58,   2076}, {0x0b, 0x08, 0x7e, 0xff}}},
    {{{   152,      8,     22}, 0, {    44,   1520}, {0x0b, 0x42, 0x6b, 0xff}}},
    {{{   152,    -16,     14}, 0, {  -202,   1518}, {0x0b, 0xae, 0x5f, 0xff}}},
    {{{    96,    -20,     17}, 0, {  -244,   2074}, {0x0b, 0x8c, 0x31, 0xff}}},
    {{{   152,    -16,    -11}, 0, {  -202,   1518}, {0x0c, 0x8b, 0xd2, 0xff}}},
    {{{   152,     23,      1}, 0, {   196,   1520}, {0x0a, 0x7a, 0xe1, 0xff}}},
    {{{    96,    -21,    -14}, 0, {  -246,   2074}, {0x0c, 0xad, 0xa1, 0xff}}},
    {{{   152,      8,    -19}, 0, {    42,   1520}, {0x0b, 0x08, 0x82, 0xff}}},
    {{{    96,      9,    -24}, 0, {    58,   2076}, {0x0b, 0x40, 0x94, 0xff}}},
};

// 0x05006058 - 0x050060E0
const Gfx peach_seg5_dl_05006058[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05000A28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&peach_seg5_lights_05005FA0.l, 1),
    gsSPLight(&peach_seg5_lights_05005FA0.a, 2),
    gsSPVertex(peach_seg5_vertex_05005FB8, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 1,  4,  3, 0x0,  4,  5,  3, 0x0),
    gsSP2Triangles( 0,  2,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 7,  8,  5, 0x0,  7,  9,  8, 0x0),
    gsSP2Triangles( 9,  0,  6, 0x0,  9,  6,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x050060E0 - 0x05006138
const Gfx peach_seg5_dl_050060E0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(peach_seg5_dl_05006058),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x05006138
static const Lights1 peach_seg5_lights_05006138 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05006150
static const Lights1 peach_seg5_lights_05006150 = gdSPDefLights1(
    0x6c, 0x54, 0x5f,
    0xd9, 0xa9, 0xbe, 0x28, 0x28, 0x28
);

// 0x05006168
static const Vtx peach_seg5_vertex_05006168[] = {
    {{{    54,     70,    -23}, 0, {  -179,    143}, {0x23, 0x6c, 0xca, 0xff}}},
    {{{    52,     85,      0}, 0, {   224,    223}, {0x17, 0x7c, 0x00, 0xff}}},
    {{{    70,     67,     -7}, 0, {    96,   -135}, {0x55, 0x5b, 0xeb, 0xff}}},
    {{{    36,     73,     19}, 0, {   542,    450}, {0xf5, 0x78, 0x26, 0xff}}},
    {{{    28,     75,      0}, 0, {   224,    584}, {0xd9, 0x78, 0x00, 0xff}}},
    {{{    36,     73,    -18}, 0, {   -94,    450}, {0xf5, 0x78, 0xd9, 0xff}}},
    {{{    54,     70,     24}, 0, {   627,    143}, {0x23, 0x6d, 0x36, 0xff}}},
    {{{    70,     67,      8}, 0, {   351,   -135}, {0x51, 0x5d, 0x19, 0xff}}},
};

// 0x050061E8
static const Vtx peach_seg5_vertex_050061E8[] = {
    {{{   116,     24,     43}, 0, {   692,   -612}, {0x36, 0x3d, 0x60, 0xff}}},
    {{{   119,    -30,     36}, 0, {   658,   -626}, {0x30, 0xac, 0x51, 0xff}}},
    {{{   119,    -30,    -35}, 0, {   300,   -626}, {0x40, 0x9f, 0xce, 0xff}}},
    {{{   116,     24,    -42}, 0, {   266,   -612}, {0x2f, 0x2b, 0x93, 0xff}}},
    {{{   111,     45,      0}, 0, {   478,   -586}, {0xef, 0x7b, 0xea, 0xff}}},
};

// 0x05006238
static const Vtx peach_seg5_vertex_05006238[] = {
    {{{    85,     34,    -65}, 0, {   150,   -458}, {0x4b, 0x3c, 0xae, 0xff}}},
    {{{    98,     18,    -34}, 0, {   302,   -522}, {0x2e, 0x52, 0xac, 0xff}}},
    {{{    98,    -23,    -34}, 0, {   302,   -520}, {0x59, 0xcd, 0xb7, 0xff}}},
    {{{    84,     50,    -21}, 0, {   368,   -452}, {0x66, 0x4a, 0xf2, 0xff}}},
    {{{    23,    -22,    -70}, 0, {   126,   -146}, {0xdf, 0xd1, 0x90, 0xff}}},
    {{{    24,     37,    -70}, 0, {   126,   -150}, {0xc5, 0x23, 0x96, 0xff}}},
    {{{    54,     70,    -23}, 0, {   358,   -300}, {0x23, 0x6c, 0xca, 0xff}}},
    {{{    70,     67,     -7}, 0, {   440,   -382}, {0x55, 0x5b, 0xeb, 0xff}}},
    {{{    20,     69,    -39}, 0, {   278,   -132}, {0xdd, 0x70, 0xd2, 0xff}}},
    {{{    85,    -21,    -65}, 0, {   150,   -456}, {0x3a, 0xc5, 0xa1, 0xff}}},
    {{{    36,     73,    -18}, 0, {   384,   -210}, {0xf5, 0x78, 0xd9, 0xff}}},
    {{{    84,     50,     22}, 0, {   590,   -452}, {0x65, 0x4b, 0x0b, 0xff}}},
    {{{    70,     67,      8}, 0, {   518,   -382}, {0x51, 0x5d, 0x19, 0xff}}},
    {{{    -7,     37,    -34}, 0, {   302,      8}, {0x8c, 0x2a, 0xe4, 0xff}}},
};

// 0x05006318
static const Vtx peach_seg5_vertex_05006318[] = {
    {{{    54,     70,     24}, 0, {   600,   -300}, {0x23, 0x6d, 0x36, 0xff}}},
    {{{    20,     69,     40}, 0, {   680,   -132}, {0xd3, 0x6f, 0x28, 0xff}}},
    {{{    85,     34,     66}, 0, {   808,   -458}, {0x4b, 0x3c, 0x52, 0xff}}},
    {{{    24,     37,     71}, 0, {   832,   -150}, {0xd2, 0x2c, 0x6d, 0xff}}},
    {{{    23,    -22,     71}, 0, {   832,   -146}, {0xd1, 0xd9, 0x6f, 0xff}}},
    {{{    98,    -23,     35}, 0, {   656,   -520}, {0x42, 0xb8, 0x50, 0xff}}},
    {{{    98,     18,     35}, 0, {   656,   -522}, {0x48, 0x42, 0x50, 0xff}}},
    {{{    84,     50,     22}, 0, {   590,   -452}, {0x65, 0x4b, 0x0b, 0xff}}},
    {{{    70,     67,      8}, 0, {   518,   -382}, {0x51, 0x5d, 0x19, 0xff}}},
    {{{    85,    -21,     66}, 0, {   808,   -454}, {0x3a, 0xc5, 0x5f, 0xff}}},
    {{{    95,     36,      0}, 0, {   478,   -506}, {0x25, 0x78, 0x0d, 0xff}}},
    {{{    84,     50,    -21}, 0, {   368,   -452}, {0x66, 0x4a, 0xf2, 0xff}}},
    {{{    20,     69,    -39}, 0, {   278,   -132}, {0xdd, 0x70, 0xd2, 0xff}}},
    {{{    -7,     37,    -34}, 0, {   302,      8}, {0x8c, 0x2a, 0xe4, 0xff}}},
    {{{    28,     75,      0}, 0, {   478,   -170}, {0xd9, 0x78, 0x00, 0xff}}},
};

// 0x05006408
static const Vtx peach_seg5_vertex_05006408[] = {
    {{{   111,     45,      0}, 0, {   478,   -586}, {0xef, 0x7b, 0xea, 0xff}}},
    {{{    98,     18,    -34}, 0, {   302,   -522}, {0x2e, 0x52, 0xac, 0xff}}},
    {{{    95,     36,      0}, 0, {   478,   -506}, {0x25, 0x78, 0x0d, 0xff}}},
    {{{    84,     50,    -21}, 0, {   368,   -452}, {0x66, 0x4a, 0xf2, 0xff}}},
    {{{   116,     24,     43}, 0, {   692,   -612}, {0x36, 0x3d, 0x60, 0xff}}},
    {{{    98,     18,     35}, 0, {   656,   -522}, {0x48, 0x42, 0x50, 0xff}}},
    {{{    98,    -23,     35}, 0, {   656,   -520}, {0x42, 0xb8, 0x50, 0xff}}},
    {{{   119,    -30,     36}, 0, {   658,   -626}, {0x30, 0xac, 0x51, 0xff}}},
    {{{   116,     24,    -42}, 0, {   266,   -612}, {0x2f, 0x2b, 0x93, 0xff}}},
    {{{    24,     37,     71}, 0, {   832,   -150}, {0xd2, 0x2c, 0x6d, 0xff}}},
    {{{    20,     69,     40}, 0, {   680,   -132}, {0xd3, 0x6f, 0x28, 0xff}}},
    {{{    -7,     37,     35}, 0, {   656,      8}, {0x92, 0x20, 0x34, 0xff}}},
    {{{    36,     73,     19}, 0, {   574,   -210}, {0xf5, 0x78, 0x26, 0xff}}},
    {{{    28,     75,      0}, 0, {   478,   -170}, {0xd9, 0x78, 0x00, 0xff}}},
};

// 0x050064E8
static const Vtx peach_seg5_vertex_050064E8[] = {
    {{{    23,    -45,    -34}, 0, {   302,   -146}, {0xd9, 0x8d, 0xdf, 0xff}}},
    {{{    86,    -53,    -34}, 0, {   302,   -460}, {0x40, 0x9d, 0xd3, 0xff}}},
    {{{    86,    -53,     35}, 0, {   656,   -460}, {0x41, 0x98, 0x1e, 0xff}}},
    {{{    54,     70,     24}, 0, {   600,   -300}, {0x23, 0x6d, 0x36, 0xff}}},
    {{{    36,     73,     19}, 0, {   574,   -210}, {0xf5, 0x78, 0x26, 0xff}}},
    {{{    20,     69,     40}, 0, {   680,   -132}, {0xd3, 0x6f, 0x28, 0xff}}},
    {{{    28,     75,      0}, 0, {   478,   -170}, {0xd9, 0x78, 0x00, 0xff}}},
    {{{    36,     73,    -18}, 0, {   384,   -210}, {0xf5, 0x78, 0xd9, 0xff}}},
    {{{    20,     69,    -39}, 0, {   278,   -132}, {0xdd, 0x70, 0xd2, 0xff}}},
    {{{    85,    -21,    -65}, 0, {   150,   -456}, {0x3a, 0xc5, 0xa1, 0xff}}},
    {{{    -8,    -22,    -34}, 0, {   302,     10}, {0x95, 0xd5, 0xcd, 0xff}}},
    {{{    -8,    -22,     35}, 0, {   656,     10}, {0x92, 0xc8, 0x1b, 0xff}}},
    {{{    23,    -22,    -70}, 0, {   126,   -146}, {0xdf, 0xd1, 0x90, 0xff}}},
    {{{    23,    -45,     35}, 0, {   656,   -146}, {0xdd, 0x90, 0x2f, 0xff}}},
    {{{    98,    -23,    -34}, 0, {   302,   -520}, {0x59, 0xcd, 0xb7, 0xff}}},
    {{{    85,    -21,     66}, 0, {   808,   -454}, {0x3a, 0xc5, 0x5f, 0xff}}},
};

// 0x050065E8
static const Vtx peach_seg5_vertex_050065E8[] = {
    {{{    98,    -23,     35}, 0, {   656,   -520}, {0x42, 0xb8, 0x50, 0xff}}},
    {{{    86,    -53,     35}, 0, {   656,   -460}, {0x41, 0x98, 0x1e, 0xff}}},
    {{{    98,    -23,    -34}, 0, {   302,   -520}, {0x59, 0xcd, 0xb7, 0xff}}},
    {{{    85,    -21,     66}, 0, {   808,   -454}, {0x3a, 0xc5, 0x5f, 0xff}}},
    {{{    -8,    -22,    -34}, 0, {   302,     10}, {0x95, 0xd5, 0xcd, 0xff}}},
    {{{    -8,    -22,     35}, 0, {   656,     10}, {0x92, 0xc8, 0x1b, 0xff}}},
    {{{    -7,     37,    -34}, 0, {   302,      8}, {0x8c, 0x2a, 0xe4, 0xff}}},
    {{{    23,    -45,     35}, 0, {   656,   -146}, {0xdd, 0x90, 0x2f, 0xff}}},
    {{{    23,    -22,     71}, 0, {   832,   -146}, {0xd1, 0xd9, 0x6f, 0xff}}},
    {{{    -7,     37,     35}, 0, {   656,      8}, {0x92, 0x20, 0x34, 0xff}}},
    {{{    24,     37,     71}, 0, {   832,   -150}, {0xd2, 0x2c, 0x6d, 0xff}}},
    {{{    20,     69,     40}, 0, {   680,   -132}, {0xd3, 0x6f, 0x28, 0xff}}},
    {{{    24,     37,    -70}, 0, {   126,   -150}, {0xc5, 0x23, 0x96, 0xff}}},
    {{{    23,    -22,    -70}, 0, {   126,   -146}, {0xdf, 0xd1, 0x90, 0xff}}},
    {{{   119,    -30,    -35}, 0, {   300,   -626}, {0x40, 0x9f, 0xce, 0xff}}},
    {{{   119,    -30,     36}, 0, {   658,   -626}, {0x30, 0xac, 0x51, 0xff}}},
};

// 0x050066E8
static const Vtx peach_seg5_vertex_050066E8[] = {
    {{{   116,     24,    -42}, 0, {   266,   -612}, {0x2f, 0x2b, 0x93, 0xff}}},
    {{{   119,    -30,    -35}, 0, {   300,   -626}, {0x40, 0x9f, 0xce, 0xff}}},
    {{{    98,    -23,    -34}, 0, {   302,   -520}, {0x59, 0xcd, 0xb7, 0xff}}},
    {{{    98,     18,    -34}, 0, {   302,   -522}, {0x2e, 0x52, 0xac, 0xff}}},
};

// 0x05006728 - 0x05006798
const Gfx peach_seg5_dl_05006728[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05002C28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 16 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&peach_seg5_lights_05006138.l, 1),
    gsSPLight(&peach_seg5_lights_05006138.a, 2),
    gsSPVertex(peach_seg5_vertex_05006168, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  4, 0x0),
    gsSP2Triangles( 4,  1,  5, 0x0,  6,  1,  3, 0x0),
    gsSP2Triangles( 7,  1,  6, 0x0,  2,  1,  7, 0x0),
    gsSP1Triangle( 5,  1,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05006798 - 0x05006A18
const Gfx peach_seg5_dl_05006798[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05004028),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&peach_seg5_lights_05006150.l, 1),
    gsSPLight(&peach_seg5_lights_05006150.a, 2),
    gsSPVertex(peach_seg5_vertex_050061E8, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP1Triangle( 3,  0,  2, 0x0),
    gsSPLight(&peach_seg5_lights_05006138.l, 1),
    gsSPLight(&peach_seg5_lights_05006138.a, 2),
    gsSPVertex(peach_seg5_vertex_05006238, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  0, 0x0,  0,  6,  7, 0x0),
    gsSP2Triangles( 7,  3,  0, 0x0,  0,  8,  6, 0x0),
    gsSP2Triangles( 0,  5,  8, 0x0,  0,  9,  4, 0x0),
    gsSP2Triangles( 2,  9,  0, 0x0,  8, 10,  6, 0x0),
    gsSP2Triangles( 3,  7, 11, 0x0,  7, 12, 11, 0x0),
    gsSP1Triangle( 8,  5, 13, 0x0),
    gsSPVertex(peach_seg5_vertex_05006318, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 5,  6,  2, 0x0,  6,  7,  2, 0x0),
    gsSP2Triangles( 8,  0,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 2,  7,  8, 0x0,  4,  9,  2, 0x0),
    gsSP2Triangles( 2,  9,  5, 0x0, 10, 11,  7, 0x0),
    gsSP2Triangles( 7,  6, 10, 0x0,  1, 12, 13, 0x0),
    gsSP1Triangle(12,  1, 14, 0x0),
    gsSPVertex(peach_seg5_vertex_05006408, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  2,  5,  4, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  7,  4, 0x0),
    gsSP2Triangles( 0,  8,  1, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(10, 12, 13, 0x0),
    gsSPVertex(peach_seg5_vertex_050064E8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9,  1,  0, 0x0),
    gsSP2Triangles(10,  0, 11, 0x0, 12,  0, 10, 0x0),
    gsSP2Triangles(13,  0,  2, 0x0,  0, 12,  9, 0x0),
    gsSP2Triangles( 0, 13, 11, 0x0, 14,  1,  9, 0x0),
    gsSP2Triangles( 2,  1, 14, 0x0, 13,  2, 15, 0x0),
    gsSPVertex(peach_seg5_vertex_050065E8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  5, 0x0),
    gsSP2Triangles( 5,  9,  6, 0x0,  9,  5,  8, 0x0),
    gsSP2Triangles(10,  9,  8, 0x0,  6,  9, 11, 0x0),
    gsSP2Triangles( 3,  8,  7, 0x0,  4,  6, 12, 0x0),
    gsSP2Triangles(13,  4, 12, 0x0, 14, 15,  0, 0x0),
    gsSP1Triangle( 0,  2, 14, 0x0),
    gsSPVertex(peach_seg5_vertex_050066E8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05006A18 - 0x05006A90
const Gfx peach_seg5_dl_05006A18[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 4, G_TX_NOLOD, G_TX_CLAMP, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(peach_seg5_dl_05006728),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(peach_seg5_dl_05006798),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x05006A90
static const Lights1 peach_seg5_lights_05006A90 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05006AA8
static const Vtx peach_seg5_vertex_05006AA8[] = {
    {{{    93,     12,     39}, 0, {   676,   1080}, {0x2c, 0x13, 0x75, 0xff}}},
    {{{    92,     36,     28}, 0, {   618,   1092}, {0x46, 0x58, 0x38, 0xff}}},
    {{{    54,     35,     28}, 0, {   618,   1468}, {0x3e, 0x53, 0x48, 0xff}}},
    {{{    33,     62,    -56}, 0, {   196,  -1380}, {0x5a, 0x4a, 0xd0, 0xff}}},
    {{{    32,     71,      0}, 0, {   478,  -1376}, {0x5a, 0x59, 0x00, 0xff}}},
    {{{    54,     35,    -27}, 0, {   340,  -1596}, {0x3c, 0x5f, 0xc7, 0xff}}},
    {{{    35,     12,    -80}, 0, {    76,  -1404}, {0x5f, 0x0a, 0xae, 0xff}}},
    {{{    55,     11,    -39}, 0, {   280,  -1606}, {0x46, 0x13, 0x99, 0xff}}},
    {{{   -18,     76,    -80}, 0, {    72,   -868}, {0x21, 0x52, 0xa6, 0xff}}},
    {{{   -19,     99,      0}, 0, {   480,   -856}, {0x1c, 0x7b, 0x00, 0xff}}},
    {{{    54,     44,      0}, 0, {   480,  -1590}, {0x28, 0x77, 0x09, 0xff}}},
    {{{    57,    -12,    -27}, 0, {   338,  -1618}, {0x48, 0xc0, 0xaf, 0xff}}},
    {{{    33,     62,     57}, 0, {   766,  -1380}, {0x5a, 0x4a, 0x30, 0xff}}},
    {{{    54,     35,     28}, 0, {   618,  -1596}, {0x3e, 0x53, 0x48, 0xff}}},
    {{{    54,     44,      0}, 0, {   480,   1474}, {0x28, 0x77, 0x09, 0xff}}},
    {{{    94,    -10,     28}, 0, {   618,   1070}, {0x5f, 0xcc, 0x40, 0xff}}},
};

// 0x05006BA8
static const Vtx peach_seg5_vertex_05006BA8[] = {
    {{{    91,     46,      0}, 0, {   480,   1096}, {0x63, 0x4e, 0xf8, 0xff}}},
    {{{    92,     36,     28}, 0, {   618,   1092}, {0x46, 0x58, 0x38, 0xff}}},
    {{{    94,    -10,     28}, 0, {   618,   1070}, {0x5f, 0xcc, 0x40, 0xff}}},
    {{{    55,     10,     40}, 0, {   678,   1458}, {0x47, 0xfc, 0x68, 0xff}}},
    {{{    93,     12,     39}, 0, {   676,   1080}, {0x2c, 0x13, 0x75, 0xff}}},
    {{{    54,     35,     28}, 0, {   618,   1468}, {0x3e, 0x53, 0x48, 0xff}}},
    {{{    57,    -12,     28}, 0, {   620,   1446}, {0x49, 0xb1, 0x42, 0xff}}},
    {{{    95,    -20,      0}, 0, {   480,   1064}, {0x4e, 0x9e, 0x0b, 0xff}}},
    {{{    54,     44,      0}, 0, {   480,   1474}, {0x28, 0x77, 0x09, 0xff}}},
    {{{    54,     35,    -27}, 0, {   340,   1468}, {0x3c, 0x5f, 0xc7, 0xff}}},
    {{{    92,     36,    -27}, 0, {   340,   1092}, {0x48, 0x45, 0xb2, 0xff}}},
    {{{    94,    -10,    -27}, 0, {   340,   1070}, {0x60, 0xbe, 0xd0, 0xff}}},
    {{{    57,    -22,      0}, 0, {   478,   1440}, {0x54, 0xa2, 0xf8, 0xff}}},
    {{{    57,    -12,    -27}, 0, {   338,   1446}, {0x48, 0xc0, 0xaf, 0xff}}},
    {{{    93,     12,    -38}, 0, {   284,   1080}, {0x2e, 0xed, 0x8c, 0xff}}},
    {{{    55,     11,    -39}, 0, {   280,   1458}, {0x46, 0x13, 0x99, 0xff}}},
};

// 0x05006CA8
static const Vtx peach_seg5_vertex_05006CA8[] = {
    {{{    35,     12,    -80}, 0, {    76,  -1404}, {0x5f, 0x0a, 0xae, 0xff}}},
    {{{   -14,      8,   -114}, 0, {   -94,   -900}, {0x2c, 0xfd, 0x89, 0xff}}},
    {{{   -18,     76,    -80}, 0, {    72,   -868}, {0x21, 0x52, 0xa6, 0xff}}},
    {{{    38,    -47,    -56}, 0, {   194,  -1434}, {0x60, 0xc5, 0xc7, 0xff}}},
    {{{    57,    -12,    -27}, 0, {   338,  -1618}, {0x48, 0xc0, 0xaf, 0xff}}},
    {{{    57,    -22,      0}, 0, {   478,  -1624}, {0x54, 0xa2, 0xf8, 0xff}}},
    {{{    55,     11,    -39}, 0, {   280,   1458}, {0x46, 0x13, 0x99, 0xff}}},
    {{{    93,     12,    -38}, 0, {   284,   1080}, {0x2e, 0xed, 0x8c, 0xff}}},
    {{{    57,    -12,    -27}, 0, {   338,   1446}, {0x48, 0xc0, 0xaf, 0xff}}},
    {{{    55,     10,     40}, 0, {   678,  -1606}, {0x47, 0xfc, 0x68, 0xff}}},
    {{{    54,     35,     28}, 0, {   618,  -1596}, {0x3e, 0x53, 0x48, 0xff}}},
    {{{    33,     62,     57}, 0, {   766,  -1380}, {0x5a, 0x4a, 0x30, 0xff}}},
    {{{    39,    -70,      0}, 0, {   478,  -1444}, {0x64, 0xb2, 0x00, 0xff}}},
    {{{   -11,    -72,    -80}, 0, {    72,   -938}, {0x21, 0xb1, 0xa3, 0xff}}},
    {{{    -9,   -102,      0}, 0, {   480,   -954}, {0x29, 0x89, 0x01, 0xff}}},
    {{{    38,    -47,     57}, 0, {   764,  -1434}, {0x62, 0xc2, 0x32, 0xff}}},
};

// 0x05006DA8
static const Vtx peach_seg5_vertex_05006DA8[] = {
    {{{    57,    -22,      0}, 0, {   478,  -1624}, {0x54, 0xa2, 0xf8, 0xff}}},
    {{{    57,    -12,     28}, 0, {   620,  -1618}, {0x49, 0xb1, 0x42, 0xff}}},
    {{{    38,    -47,     57}, 0, {   764,  -1434}, {0x62, 0xc2, 0x32, 0xff}}},
    {{{    35,     12,     81}, 0, {   882,  -1404}, {0x5c, 0x02, 0x56, 0xff}}},
    {{{    55,     10,     40}, 0, {   678,  -1606}, {0x47, 0xfc, 0x68, 0xff}}},
    {{{   -11,    -68,     81}, 0, {   886,   -938}, {0x27, 0xb8, 0x60, 0xff}}},
    {{{    -9,   -102,      0}, 0, {   480,   -954}, {0x29, 0x89, 0x01, 0xff}}},
    {{{    39,    -70,      0}, 0, {   478,  -1444}, {0x64, 0xb2, 0x00, 0xff}}},
    {{{    33,     62,     57}, 0, {   766,  -1380}, {0x5a, 0x4a, 0x30, 0xff}}},
    {{{   -43,      6,    114}, 0, {  1050,   -610}, {0x00, 0x01, 0x7e, 0xff}}},
    {{{   -40,    -71,     81}, 0, {   882,   -648}, {0x09, 0x9b, 0x4c, 0xff}}},
    {{{   -14,      8,    115}, 0, {  1054,   -900}, {0x24, 0x04, 0x79, 0xff}}},
    {{{   -18,     76,     81}, 0, {   886,   -868}, {0x21, 0x52, 0x5a, 0xff}}},
    {{{   -19,     99,      0}, 0, {   480,   -856}, {0x1c, 0x7b, 0x00, 0xff}}},
    {{{   -18,     76,    -80}, 0, {    72,   -868}, {0x21, 0x52, 0xa6, 0xff}}},
    {{{   -47,     79,    -80}, 0, {    76,   -574}, {0x01, 0x6d, 0xc0, 0xff}}},
};

// 0x05006EA8
static const Vtx peach_seg5_vertex_05006EA8[] = {
    {{{   -19,     99,      0}, 0, {   480,   -856}, {0x1c, 0x7b, 0x00, 0xff}}},
    {{{   -47,     79,     81}, 0, {   882,   -574}, {0x01, 0x6d, 0x40, 0xff}}},
    {{{   -18,     76,     81}, 0, {   886,   -868}, {0x21, 0x52, 0x5a, 0xff}}},
    {{{   -47,     79,    -80}, 0, {    76,   -574}, {0x01, 0x6d, 0xc0, 0xff}}},
    {{{   -48,     96,      0}, 0, {   480,   -566}, {0xf3, 0x7e, 0x00, 0xff}}},
    {{{   -43,      6,    114}, 0, {  1050,   -610}, {0x00, 0x01, 0x7e, 0xff}}},
    {{{   -14,      8,    115}, 0, {  1054,   -900}, {0x24, 0x04, 0x79, 0xff}}},
    {{{   -11,    -68,     81}, 0, {   886,   -938}, {0x27, 0xb8, 0x60, 0xff}}},
    {{{   -40,    -71,     81}, 0, {   882,   -648}, {0x09, 0x9b, 0x4c, 0xff}}},
    {{{    -9,   -102,      0}, 0, {   480,   -954}, {0x29, 0x89, 0x01, 0xff}}},
    {{{   -11,    -72,    -80}, 0, {    72,   -938}, {0x21, 0xb1, 0xa3, 0xff}}},
    {{{   -40,    -74,    -80}, 0, {    76,   -650}, {0x07, 0x9a, 0xb6, 0xff}}},
    {{{   -43,      6,   -113}, 0, {   -90,   -610}, {0x00, 0x02, 0x82, 0xff}}},
    {{{   -38,   -104,      0}, 0, {   480,   -664}, {0x08, 0x82, 0x01, 0xff}}},
    {{{   -14,      8,   -114}, 0, {   -94,   -900}, {0x2c, 0xfd, 0x89, 0xff}}},
    {{{   -18,     76,    -80}, 0, {    72,   -868}, {0x21, 0x52, 0xa6, 0xff}}},
};

// 0x05006FA8 - 0x05007230
const Gfx peach_seg5_dl_05006FA8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05004028),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&peach_seg5_lights_05006A90.l, 1),
    gsSPLight(&peach_seg5_lights_05006A90.a, 2),
    gsSPVertex(peach_seg5_vertex_05006AA8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  7, 0x0,  3,  8,  9, 0x0),
    gsSP2Triangles( 9,  4,  3, 0x0,  3,  5,  7, 0x0),
    gsSP2Triangles( 8,  3,  6, 0x0,  4, 10,  5, 0x0),
    gsSP2Triangles( 6,  7, 11, 0x0,  4, 12, 13, 0x0),
    gsSP2Triangles(13, 10,  4, 0x0, 12,  4,  9, 0x0),
    gsSP2Triangles( 2,  1, 14, 0x0,  1,  0, 15, 0x0),
    gsSPVertex(peach_seg5_vertex_05006BA8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 2,  4,  3, 0x0,  6,  2,  3, 0x0),
    gsSP2Triangles( 0,  2,  7, 0x0,  7,  2,  6, 0x0),
    gsSP2Triangles( 8,  0,  9, 0x0, 10,  0, 11, 0x0),
    gsSP2Triangles( 0,  7, 11, 0x0,  1,  0,  8, 0x0),
    gsSP2Triangles( 0, 10,  9, 0x0, 12,  7,  6, 0x0),
    gsSP2Triangles(11,  7, 12, 0x0, 13, 11, 12, 0x0),
    gsSP2Triangles(11, 14, 10, 0x0, 14, 11, 13, 0x0),
    gsSP2Triangles( 9, 10, 15, 0x0, 10, 14, 15, 0x0),
    gsSPVertex(peach_seg5_vertex_05006CA8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 1,  0,  3, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12,  3,  5, 0x0,  3, 13,  1, 0x0),
    gsSP2Triangles(14, 13,  3, 0x0,  3, 12, 14, 0x0),
    gsSP1Triangle(15, 12,  5, 0x0),
    gsSPVertex(peach_seg5_vertex_05006DA8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  1, 0x0),
    gsSP2Triangles( 1,  4,  3, 0x0,  2,  5,  6, 0x0),
    gsSP2Triangles( 6,  7,  2, 0x0,  5,  2,  3, 0x0),
    gsSP2Triangles( 8,  3,  4, 0x0,  9, 10,  5, 0x0),
    gsSP2Triangles( 3, 11,  5, 0x0, 12, 11,  3, 0x0),
    gsSP2Triangles( 3,  8, 12, 0x0, 13, 12,  8, 0x0),
    gsSP1Triangle(14, 15, 13, 0x0),
    gsSPVertex(peach_seg5_vertex_05006EA8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  2,  5,  6, 0x0),
    gsSP2Triangles( 2,  1,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 6,  5,  7, 0x0, 10, 11, 12, 0x0),
    gsSP2Triangles( 9, 11, 10, 0x0,  9, 13, 11, 0x0),
    gsSP2Triangles( 8, 13,  9, 0x0, 14, 12, 15, 0x0),
    gsSP2Triangles(10, 12, 14, 0x0, 12,  3, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x05007230 - 0x05007288
const Gfx peach_seg5_dl_05007230[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(peach_seg5_dl_05006FA8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

#ifndef VERSION_JP
// 0x05007288 - 0x050072E8
const Gfx peach_seg5_us_dl_05007288[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetRenderMode(G_RM_CUSTOM_AA_ZB_XLU_SURF, G_RM_NOOP2),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(peach_seg5_dl_05006FA8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};
#endif

// 0x05007288
static const Lights1 peach_seg5_lights_05007288 = gdSPDefLights1(
    0x7f, 0x5f, 0x0c,
    0xff, 0xbf, 0x18, 0x28, 0x28, 0x28
);

// 0x050072A0
static const Vtx peach_seg5_vertex_050072A0[] = {
    {{{    89,    -13,     96}, 0, {     0,      0}, {0x12, 0x64, 0x4b, 0xff}}},
    {{{    77,     -2,    169}, 0, {     0,      0}, {0xbc, 0x59, 0x3a, 0xff}}},
    {{{   113,    -57,    148}, 0, {     0,      0}, {0x3c, 0xde, 0x69, 0xff}}},
    {{{   134,    -77,    114}, 0, {     0,      0}, {0x04, 0xc7, 0x71, 0xff}}},
    {{{    64,    -31,    124}, 0, {     0,      0}, {0x9d, 0xf3, 0x4d, 0xff}}},
    {{{   136,   -112,     40}, 0, {     0,      0}, {0xf1, 0x84, 0x16, 0xff}}},
    {{{   182,   -105,     94}, 0, {     0,      0}, {0x24, 0xb7, 0x60, 0xff}}},
    {{{    10,    -41,    110}, 0, {     0,      0}, {0xd0, 0xee, 0x74, 0xff}}},
    {{{   188,    -40,     85}, 0, {     0,      0}, {0x39, 0x28, 0x6a, 0xff}}},
    {{{    72,     37,     49}, 0, {     0,      0}, {0x09, 0x6d, 0x40, 0xff}}},
    {{{   -37,     23,     46}, 0, {     0,      0}, {0xd9, 0x6c, 0x33, 0xff}}},
    {{{   184,      6,     56}, 0, {     0,      0}, {0x35, 0x56, 0x4b, 0xff}}},
    {{{   -27,    -67,     43}, 0, {     0,      0}, {0xbd, 0x98, 0x1b, 0xff}}},
    {{{   -72,    -14,     66}, 0, {     0,      0}, {0xa7, 0x00, 0x59, 0xff}}},
};

// 0x05007380
static const Vtx peach_seg5_vertex_05007380[] = {
    {{{    64,    -31,   -123}, 0, {     0,      0}, {0x9d, 0xf3, 0xb3, 0xff}}},
    {{{    77,     -2,   -168}, 0, {     0,      0}, {0xbc, 0x59, 0xc6, 0xff}}},
    {{{   113,    -57,   -147}, 0, {     0,      0}, {0x3d, 0xdd, 0x97, 0xff}}},
    {{{   134,    -76,   -113}, 0, {     0,      0}, {0x04, 0xc8, 0x8f, 0xff}}},
    {{{    89,    -13,    -95}, 0, {     0,      0}, {0x12, 0x64, 0xb6, 0xff}}},
    {{{   182,   -105,    -93}, 0, {     0,      0}, {0x24, 0xb7, 0xa0, 0xff}}},
    {{{   136,   -112,    -39}, 0, {     0,      0}, {0xfb, 0x84, 0xea, 0xff}}},
    {{{   188,    -40,    -84}, 0, {     0,      0}, {0x39, 0x28, 0x97, 0xff}}},
    {{{    10,    -41,   -109}, 0, {     0,      0}, {0xd1, 0xee, 0x8c, 0xff}}},
    {{{   -27,    -67,    -43}, 0, {     0,      0}, {0xc6, 0x93, 0xe4, 0xff}}},
    {{{   -72,    -14,    -65}, 0, {     0,      0}, {0xa7, 0x00, 0xa6, 0xff}}},
    {{{   -37,     23,    -45}, 0, {     0,      0}, {0xdc, 0x6a, 0xc6, 0xff}}},
    {{{    72,     37,    -47}, 0, {     0,      0}, {0x08, 0x6d, 0xc0, 0xff}}},
    {{{   184,      6,    -55}, 0, {     0,      0}, {0x35, 0x56, 0xb5, 0xff}}},
    {{{   -27,    -67,     43}, 0, {     0,      0}, {0xbd, 0x98, 0x1b, 0xff}}},
    {{{   -95,     -7,    -21}, 0, {     0,      0}, {0x84, 0x0b, 0xe9, 0xff}}},
};

// 0x05007480
static const Vtx peach_seg5_vertex_05007480[] = {
    {{{   136,   -112,     40}, 0, {     0,      0}, {0xf1, 0x84, 0x16, 0xff}}},
    {{{   -27,    -67,     43}, 0, {     0,      0}, {0xbd, 0x98, 0x1b, 0xff}}},
    {{{   -27,    -67,    -43}, 0, {     0,      0}, {0xc6, 0x93, 0xe4, 0xff}}},
    {{{   136,   -112,    -39}, 0, {     0,      0}, {0xfb, 0x84, 0xea, 0xff}}},
    {{{   239,    -84,     59}, 0, {     0,      0}, {0x51, 0xa0, 0x0c, 0xff}}},
    {{{   239,    -84,    -58}, 0, {     0,      0}, {0x5a, 0xa9, 0xf1, 0xff}}},
    {{{   182,   -105,    -93}, 0, {     0,      0}, {0x24, 0xb7, 0xa0, 0xff}}},
    {{{   182,   -105,     94}, 0, {     0,      0}, {0x24, 0xb7, 0x60, 0xff}}},
    {{{   229,    -45,    -47}, 0, {     0,      0}, {0x43, 0x4f, 0xb8, 0xff}}},
    {{{   189,     27,      0}, 0, {     0,      0}, {0x3e, 0x6e, 0x00, 0xff}}},
    {{{   243,    -24,      0}, 0, {     0,      0}, {0x20, 0x7a, 0xff, 0xff}}},
    {{{   229,    -45,     49}, 0, {     0,      0}, {0x43, 0x4f, 0x47, 0xff}}},
    {{{   255,    -74,    120}, 0, {     0,      0}, {0x79, 0xfe, 0x24, 0xff}}},
    {{{    72,     37,    -47}, 0, {     0,      0}, {0x08, 0x6d, 0xc0, 0xff}}},
    {{{    59,     54,      0}, 0, {     0,      0}, {0xfc, 0x7e, 0x00, 0xff}}},
    {{{   184,      6,    -55}, 0, {     0,      0}, {0x35, 0x56, 0xb5, 0xff}}},
};

// 0x05007580
static const Vtx peach_seg5_vertex_05007580[] = {
    {{{   229,    -45,    -47}, 0, {     0,      0}, {0x43, 0x4f, 0xb8, 0xff}}},
    {{{   188,    -40,    -84}, 0, {     0,      0}, {0x39, 0x28, 0x97, 0xff}}},
    {{{   184,      6,    -55}, 0, {     0,      0}, {0x35, 0x56, 0xb5, 0xff}}},
    {{{   189,     27,      0}, 0, {     0,      0}, {0x3e, 0x6e, 0x00, 0xff}}},
    {{{    59,     54,      0}, 0, {     0,      0}, {0xfc, 0x7e, 0x00, 0xff}}},
    {{{    72,     37,    -47}, 0, {     0,      0}, {0x08, 0x6d, 0xc0, 0xff}}},
    {{{   -37,     23,    -45}, 0, {     0,      0}, {0xdc, 0x6a, 0xc6, 0xff}}},
    {{{   182,   -105,    -93}, 0, {     0,      0}, {0x24, 0xb7, 0xa0, 0xff}}},
    {{{   -72,    -14,    -65}, 0, {     0,      0}, {0xa7, 0x00, 0xa6, 0xff}}},
    {{{   -95,     -7,    -21}, 0, {     0,      0}, {0x84, 0x0b, 0xe9, 0xff}}},
    {{{   -37,     23,     46}, 0, {     0,      0}, {0xd9, 0x6c, 0x33, 0xff}}},
    {{{   229,    -45,     49}, 0, {     0,      0}, {0x43, 0x4f, 0x47, 0xff}}},
    {{{   239,    -84,     59}, 0, {     0,      0}, {0x51, 0xa0, 0x0c, 0xff}}},
    {{{   298,    -12,      0}, 0, {     0,      0}, {0x45, 0x6a, 0xff, 0xff}}},
    {{{   -95,     -7,     22}, 0, {     0,      0}, {0x86, 0x09, 0x21, 0xff}}},
    {{{   -27,    -67,     43}, 0, {     0,      0}, {0xbd, 0x98, 0x1b, 0xff}}},
};

// 0x05007680
static const Vtx peach_seg5_vertex_05007680[] = {
    {{{   -95,     -7,     22}, 0, {     0,      0}, {0x86, 0x09, 0x21, 0xff}}},
    {{{   -72,    -14,     66}, 0, {     0,      0}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{   -37,     23,     46}, 0, {     0,      0}, {0xd9, 0x6c, 0x33, 0xff}}},
    {{{   -27,    -67,     43}, 0, {     0,      0}, {0xbd, 0x98, 0x1b, 0xff}}},
    {{{    72,     37,     49}, 0, {     0,      0}, {0x09, 0x6d, 0x40, 0xff}}},
    {{{    59,     54,      0}, 0, {     0,      0}, {0xfc, 0x7e, 0x00, 0xff}}},
    {{{   229,    -45,    -47}, 0, {     0,      0}, {0x43, 0x4f, 0xb8, 0xff}}},
    {{{   255,    -74,   -119}, 0, {     0,      0}, {0x79, 0xfd, 0xdc, 0xff}}},
    {{{   182,   -105,    -93}, 0, {     0,      0}, {0x24, 0xb7, 0xa0, 0xff}}},
    {{{   298,    -12,      0}, 0, {     0,      0}, {0x45, 0x6a, 0xff, 0xff}}},
    {{{   239,    -84,    -58}, 0, {     0,      0}, {0x5a, 0xa9, 0xf1, 0xff}}},
    {{{   243,    -24,      0}, 0, {     0,      0}, {0x20, 0x7a, 0xff, 0xff}}},
    {{{   189,     27,      0}, 0, {     0,      0}, {0x3e, 0x6e, 0x00, 0xff}}},
    {{{   239,    -84,     59}, 0, {     0,      0}, {0x51, 0xa0, 0x0c, 0xff}}},
    {{{   255,    -74,    120}, 0, {     0,      0}, {0x79, 0xfe, 0x24, 0xff}}},
    {{{   182,   -105,     94}, 0, {     0,      0}, {0x24, 0xb7, 0x60, 0xff}}},
};

// 0x05007780
static const Vtx peach_seg5_vertex_05007780[] = {
    {{{   184,      6,     56}, 0, {     0,      0}, {0x35, 0x56, 0x4b, 0xff}}},
    {{{   188,    -40,     85}, 0, {     0,      0}, {0x39, 0x28, 0x6a, 0xff}}},
    {{{   229,    -45,     49}, 0, {     0,      0}, {0x43, 0x4f, 0x47, 0xff}}},
    {{{   189,     27,      0}, 0, {     0,      0}, {0x3e, 0x6e, 0x00, 0xff}}},
    {{{    72,     37,     49}, 0, {     0,      0}, {0x09, 0x6d, 0x40, 0xff}}},
    {{{   182,   -105,     94}, 0, {     0,      0}, {0x24, 0xb7, 0x60, 0xff}}},
    {{{   243,    -24,      0}, 0, {     0,      0}, {0x20, 0x7a, 0xff, 0xff}}},
    {{{   298,    -12,      0}, 0, {     0,      0}, {0x45, 0x6a, 0xff, 0xff}}},
    {{{   255,    -74,    120}, 0, {     0,      0}, {0x79, 0xfe, 0x24, 0xff}}},
};

// 0x05007810 - 0x05007AB8
const Gfx peach_seg5_dl_05007810[] = {
    gsSPLight(&peach_seg5_lights_05007288.l, 1),
    gsSPLight(&peach_seg5_lights_05007288.a, 2),
    gsSPVertex(peach_seg5_vertex_050072A0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  4, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  3,  4,  7, 0x0),
    gsSP2Triangles( 3,  6,  8, 0x0,  3,  8,  0, 0x0),
    gsSP2Triangles( 5,  3,  7, 0x0,  0,  9, 10, 0x0),
    gsSP2Triangles( 0,  7,  4, 0x0,  4,  1,  0, 0x0),
    gsSP2Triangles( 0, 10,  7, 0x0, 11,  9,  0, 0x0),
    gsSP2Triangles( 0,  8, 11, 0x0,  7, 12,  5, 0x0),
    gsSP2Triangles(13, 12,  7, 0x0, 10, 13,  7, 0x0),
    gsSPVertex(peach_seg5_vertex_05007380, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 3,  2,  4, 0x0,  2,  1,  4, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  4,  7,  3, 0x0),
    gsSP2Triangles( 7,  5,  3, 0x0,  8,  3,  6, 0x0),
    gsSP2Triangles( 3,  8,  0, 0x0,  4,  1,  0, 0x0),
    gsSP2Triangles( 0,  8,  4, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles( 8, 11,  4, 0x0,  6,  9,  8, 0x0),
    gsSP2Triangles( 8, 10, 11, 0x0, 11, 12,  4, 0x0),
    gsSP2Triangles( 4, 12, 13, 0x0, 13,  7,  4, 0x0),
    gsSP2Triangles( 9, 14, 15, 0x0, 15, 10,  9, 0x0),
    gsSPVertex(peach_seg5_vertex_05007480, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  3,  5, 0x0,  5,  3,  6, 0x0),
    gsSP2Triangles( 4,  0,  3, 0x0,  7,  0,  4, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(12,  4, 11, 0x0,  9, 13, 14, 0x0),
    gsSP1Triangle( 9, 15, 13, 0x0),
    gsSPVertex(peach_seg5_vertex_05007580, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  1,  0, 0x0),
    gsSP2Triangles( 6,  8,  9, 0x0,  9, 10,  6, 0x0),
    gsSP2Triangles(10,  4,  6, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles( 9, 14, 10, 0x0, 15, 14,  9, 0x0),
    gsSPVertex(peach_seg5_vertex_05007680, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 9, 10,  6, 0x0,  9,  6, 11, 0x0),
    gsSP2Triangles( 6, 10,  7, 0x0,  8,  7, 10, 0x0),
    gsSP2Triangles( 5,  4, 12, 0x0, 13, 14, 15, 0x0),
    gsSP1Triangle( 9, 13, 10, 0x0),
    gsSPVertex(peach_seg5_vertex_05007780, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  0,  3, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 6,  2,  7, 0x0,  5,  8,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05007AB8 - 0x05007AE0
const Gfx peach_seg5_dl_05007AB8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(peach_seg5_dl_05007810),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x05007AE0
static const Lights1 peach_seg5_lights_05007AE0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05007AF8
static const Vtx peach_seg5_vertex_05007AF8[] = {
    {{{   -32,     28,     30}, 0, {   312,  -1330}, {0x96, 0x36, 0x2a, 0xff}}},
    {{{   -12,     51,    -18}, 0, {   412,  -1562}, {0xea, 0x75, 0xd7, 0xff}}},
    {{{   -35,     28,    -17}, 0, {   300,  -1330}, {0x91, 0x27, 0xd2, 0xff}}},
    {{{   -33,    -19,     30}, 0, {   308,   -856}, {0x95, 0xd6, 0x33, 0xff}}},
    {{{    -9,     27,     52}, 0, {   428,  -1324}, {0xe6, 0x20, 0x77, 0xff}}},
    {{{   -42,      4,      6}, 0, {   264,  -1096}, {0x82, 0x02, 0x07, 0xff}}},
    {{{   -10,     51,     28}, 0, {   426,  -1560}, {0xf5, 0x6f, 0x3c, 0xff}}},
    {{{   -15,    -19,    -42}, 0, {   398,   -852}, {0xc9, 0xdb, 0x94, 0xff}}},
    {{{   -36,    -19,    -17}, 0, {   294,   -858}, {0x8e, 0xd7, 0xdb, 0xff}}},
    {{{   -14,     27,    -42}, 0, {   402,  -1326}, {0xe9, 0x27, 0x8a, 0xff}}},
    {{{   -15,    -43,    -18}, 0, {   400,   -616}, {0xe4, 0x8c, 0xd7, 0xff}}},
    {{{   -10,    -19,     52}, 0, {   424,   -850}, {0xe5, 0xd0, 0x72, 0xff}}},
    {{{   -12,    -43,     28}, 0, {   414,   -614}, {0xef, 0x92, 0x3c, 0xff}}},
    {{{    31,    -32,    -15}, 0, {   636,   -720}, {0x4c, 0xa4, 0xd8, 0xff}}},
    {{{    33,    -33,     20}, 0, {   646,   -720}, {0x6d, 0xc0, 0x09, 0xff}}},
};

// 0x05007BE8
static const Vtx peach_seg5_vertex_05007BE8[] = {
    {{{    33,     38,    -15}, 0, {   644,  -1434}, {0x75, 0x28, 0xe8, 0xff}}},
    {{{    35,    -15,     38}, 0, {   652,   -898}, {0x55, 0xd8, 0x54, 0xff}}},
    {{{    33,    -33,     20}, 0, {   646,   -720}, {0x6d, 0xc0, 0x09, 0xff}}},
    {{{    31,    -32,    -15}, 0, {   636,   -720}, {0x4c, 0xa4, 0xd8, 0xff}}},
    {{{    31,    -15,    -33}, 0, {   632,   -900}, {0x4b, 0xd8, 0xa3, 0xff}}},
    {{{    32,     20,    -33}, 0, {   638,  -1256}, {0x57, 0x2a, 0xb0, 0xff}}},
    {{{   -12,    -43,     28}, 0, {   414,   -614}, {0xef, 0x92, 0x3c, 0xff}}},
    {{{   -14,     27,    -42}, 0, {   402,  -1326}, {0xe9, 0x27, 0x8a, 0xff}}},
    {{{   -12,     51,    -18}, 0, {   412,  -1562}, {0xea, 0x75, 0xd7, 0xff}}},
    {{{    35,     38,     20}, 0, {   654,  -1434}, {0x48, 0x65, 0x16, 0xff}}},
    {{{    36,     20,     38}, 0, {   656,  -1254}, {0x5f, 0x2b, 0x47, 0xff}}},
    {{{   -15,    -43,    -18}, 0, {   400,   -616}, {0xe4, 0x8c, 0xd7, 0xff}}},
    {{{   -15,    -19,    -42}, 0, {   398,   -852}, {0xc9, 0xdb, 0x94, 0xff}}},
    {{{   -10,    -19,     52}, 0, {   424,   -850}, {0xe5, 0xd0, 0x72, 0xff}}},
    {{{    -9,     27,     52}, 0, {   428,  -1324}, {0xe6, 0x20, 0x77, 0xff}}},
    {{{   -10,     51,     28}, 0, {   426,  -1560}, {0xf5, 0x6f, 0x3c, 0xff}}},
};

// 0x05007CE8 - 0x05007E58
const Gfx peach_seg5_dl_05007CE8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05004028),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&peach_seg5_lights_05007AE0.l, 1),
    gsSPLight(&peach_seg5_lights_05007AE0.a, 2),
    gsSPVertex(peach_seg5_vertex_05007AF8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 5,  3,  0, 0x0,  4,  6,  0, 0x0),
    gsSP2Triangles( 0,  2,  5, 0x0,  0,  6,  1, 0x0),
    gsSP2Triangles( 2,  7,  8, 0x0,  2,  1,  9, 0x0),
    gsSP2Triangles( 2,  9,  7, 0x0,  5,  2,  8, 0x0),
    gsSP2Triangles( 8,  3,  5, 0x0,  8, 10,  3, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11,  3, 12, 0x0),
    gsSP2Triangles( 3, 11,  4, 0x0, 10, 12,  3, 0x0),
    gsSP2Triangles(10, 13, 14, 0x0, 10, 14, 12, 0x0),
    gsSPVertex(peach_seg5_vertex_05007BE8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 2,  5,  0, 0x0,  2,  4,  5, 0x0),
    gsSP2Triangles( 6,  2,  1, 0x0,  7,  8,  5, 0x0),
    gsSP2Triangles( 7,  5,  4, 0x0,  8,  0,  5, 0x0),
    gsSP2Triangles( 9,  0,  8, 0x0,  0, 10,  1, 0x0),
    gsSP2Triangles( 0,  9, 10, 0x0,  4, 11, 12, 0x0),
    gsSP2Triangles( 7,  4, 12, 0x0,  4,  3, 11, 0x0),
    gsSP2Triangles(13,  6,  1, 0x0, 13,  1, 14, 0x0),
    gsSP2Triangles( 1, 10, 14, 0x0, 10, 15, 14, 0x0),
    gsSP2Triangles(10,  9, 15, 0x0,  8, 15,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x05007E58 - 0x05007EB0
const Gfx peach_seg5_dl_05007E58[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(peach_seg5_dl_05007CE8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x05007EB0
static const Lights1 peach_seg5_lights_05007EB0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05007EC8
static const Vtx peach_seg5_vertex_05007EC8[] = {
    {{{   -32,     28,    -29}, 0, {   312,  -1330}, {0x96, 0x36, 0xd6, 0xff}}},
    {{{   -33,    -19,    -29}, 0, {   308,   -856}, {0x95, 0xd6, 0xcd, 0xff}}},
    {{{   -42,      4,     -5}, 0, {   264,  -1096}, {0x82, 0x02, 0xf9, 0xff}}},
    {{{   -35,     28,     18}, 0, {   300,  -1330}, {0x91, 0x27, 0x2e, 0xff}}},
    {{{   -36,    -19,     18}, 0, {   294,   -858}, {0x8e, 0xd7, 0x25, 0xff}}},
    {{{   -15,    -19,     43}, 0, {   398,   -852}, {0xc9, 0xdb, 0x6c, 0xff}}},
    {{{   -12,     51,     19}, 0, {   412,  -1562}, {0xea, 0x75, 0x29, 0xff}}},
    {{{   -14,     27,     43}, 0, {   402,  -1326}, {0xe9, 0x27, 0x76, 0xff}}},
    {{{    -9,     27,    -51}, 0, {   428,  -1324}, {0xe6, 0x20, 0x89, 0xff}}},
    {{{   -10,     51,    -27}, 0, {   426,  -1562}, {0xf5, 0x6f, 0xc4, 0xff}}},
    {{{   -15,    -43,     19}, 0, {   400,   -616}, {0xe4, 0x8c, 0x29, 0xff}}},
    {{{   -12,    -43,    -27}, 0, {   414,   -614}, {0xef, 0x92, 0xc4, 0xff}}},
    {{{   -10,    -19,    -51}, 0, {   424,   -852}, {0xe5, 0xd0, 0x8e, 0xff}}},
    {{{    33,     38,     16}, 0, {   644,  -1434}, {0x75, 0x28, 0x18, 0xff}}},
    {{{    35,     38,    -19}, 0, {   654,  -1434}, {0x48, 0x65, 0xea, 0xff}}},
};

// 0x05007FB8
static const Vtx peach_seg5_vertex_05007FB8[] = {
    {{{    33,    -33,    -19}, 0, {   646,   -720}, {0x6d, 0xc0, 0xf7, 0xff}}},
    {{{    35,    -15,    -37}, 0, {   652,   -898}, {0x55, 0xd8, 0xac, 0xff}}},
    {{{    33,     38,     16}, 0, {   644,  -1434}, {0x75, 0x28, 0x18, 0xff}}},
    {{{    36,     20,    -37}, 0, {   656,  -1256}, {0x5f, 0x2b, 0xb9, 0xff}}},
    {{{    35,     38,    -19}, 0, {   654,  -1434}, {0x48, 0x65, 0xea, 0xff}}},
    {{{    32,     20,     34}, 0, {   638,  -1256}, {0x57, 0x2a, 0x50, 0xff}}},
    {{{   -12,     51,     19}, 0, {   412,  -1562}, {0xea, 0x75, 0x29, 0xff}}},
    {{{   -14,     27,     43}, 0, {   402,  -1326}, {0xe9, 0x27, 0x76, 0xff}}},
    {{{    31,    -15,     34}, 0, {   634,   -898}, {0x4b, 0xd8, 0x5d, 0xff}}},
    {{{    31,    -32,     16}, 0, {   636,   -720}, {0x4c, 0xa4, 0x28, 0xff}}},
    {{{   -15,    -43,     19}, 0, {   400,   -616}, {0xe4, 0x8c, 0x29, 0xff}}},
    {{{   -12,    -43,    -27}, 0, {   414,   -614}, {0xef, 0x92, 0xc4, 0xff}}},
    {{{   -15,    -19,     43}, 0, {   398,   -852}, {0xc9, 0xdb, 0x6c, 0xff}}},
    {{{   -10,    -19,    -51}, 0, {   424,   -852}, {0xe5, 0xd0, 0x8e, 0xff}}},
    {{{    -9,     27,    -51}, 0, {   428,  -1324}, {0xe6, 0x20, 0x89, 0xff}}},
    {{{   -10,     51,    -27}, 0, {   426,  -1562}, {0xf5, 0x6f, 0xc4, 0xff}}},
};

// 0x050080B8 - 0x05008228
const Gfx peach_seg5_dl_050080B8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05004028),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&peach_seg5_lights_05007EB0.l, 1),
    gsSPLight(&peach_seg5_lights_05007EB0.a, 2),
    gsSPVertex(peach_seg5_vertex_05007EC8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  4,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  3, 0x0,  3,  6,  0, 0x0),
    gsSP2Triangles( 7,  6,  3, 0x0,  5,  7,  3, 0x0),
    gsSP2Triangles( 0,  8,  1, 0x0,  0,  9,  8, 0x0),
    gsSP2Triangles( 6,  9,  0, 0x0,  1, 10,  4, 0x0),
    gsSP2Triangles(11,  1, 12, 0x0,  8, 12,  1, 0x0),
    gsSP2Triangles( 1, 11, 10, 0x0,  4, 10,  5, 0x0),
    gsSP1Triangle( 6, 13, 14, 0x0),
    gsSPVertex(peach_seg5_vertex_05007FB8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 3,  4,  2, 0x0,  2,  5,  0, 0x0),
    gsSP2Triangles( 5,  2,  6, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 5,  8,  0, 0x0,  8,  5,  7, 0x0),
    gsSP2Triangles( 0,  9, 10, 0x0, 11,  0, 10, 0x0),
    gsSP2Triangles( 8,  9,  0, 0x0,  1,  0, 11, 0x0),
    gsSP2Triangles(12, 10,  8, 0x0, 12,  8,  7, 0x0),
    gsSP2Triangles(10,  9,  8, 0x0,  1, 11, 13, 0x0),
    gsSP2Triangles(14,  1, 13, 0x0, 14,  3,  1, 0x0),
    gsSP2Triangles(14, 15,  3, 0x0, 15,  4,  3, 0x0),
    gsSP1Triangle( 4, 15,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x05008228 - 0x05008280
const Gfx peach_seg5_dl_05008228[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(peach_seg5_dl_050080B8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x05008280
static const Lights1 peach_seg5_lights_05008280 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05008298
static const Vtx peach_seg5_vertex_05008298[] = {
    {{{    52,     26,      0}, 0, {  1068,  -1682}, {0x07, 0x7e, 0x00, 0xff}}},
    {{{    23,     13,     18}, 0, {   380,  -1426}, {0x00, 0x3e, 0x6e, 0xff}}},
    {{{    52,     12,     18}, 0, {  1050,  -1376}, {0x07, 0x40, 0x6d, 0xff}}},
    {{{    23,     27,      0}, 0, {   394,  -1730}, {0x05, 0x7a, 0xdf, 0xff}}},
    {{{    52,     12,    -17}, 0, {  1046,  -1380}, {0x06, 0x3b, 0x91, 0xff}}},
    {{{    23,    -10,     20}, 0, {   350,   -878}, {0xff, 0xc5, 0x6f, 0xff}}},
    {{{    51,     -9,     21}, 0, {  1022,   -876}, {0x04, 0xc6, 0x70, 0xff}}},
    {{{    23,     13,    -18}, 0, {   376,  -1430}, {0x05, 0x2c, 0x8a, 0xff}}},
    {{{    51,     -9,    -19}, 0, {  1018,   -880}, {0x06, 0xc6, 0x90, 0xff}}},
    {{{    23,    -10,    -20}, 0, {   348,   -882}, {0x05, 0xaf, 0x9f, 0xff}}},
    {{{    22,    -21,      0}, 0, {   336,   -634}, {0x03, 0x83, 0x16, 0xff}}},
    {{{    51,    -20,      0}, 0, {  1012,   -624}, {0x05, 0x82, 0xff, 0xff}}},
};

// 0x05008358
static const Vtx peach_seg5_vertex_05008358[] = {
    {{{   115,     -2,      0}, 0, {     0,      0}, {0x7e, 0xfa, 0x00, 0xff}}},
    {{{   101,     -9,     16}, 0, {     0,      0}, {0x2d, 0xc8, 0x68, 0xff}}},
    {{{   101,    -18,      0}, 0, {     0,      0}, {0x2e, 0x8b, 0xf1, 0xff}}},
    {{{    51,     -9,    -19}, 0, {     0,      0}, {0x06, 0xc6, 0x90, 0xff}}},
    {{{    51,    -20,      0}, 0, {     0,      0}, {0x05, 0x82, 0xff, 0xff}}},
    {{{   101,     -9,    -16}, 0, {     0,      0}, {0x32, 0xd8, 0x93, 0xff}}},
    {{{    52,     12,    -17}, 0, {     0,      0}, {0x06, 0x3b, 0x91, 0xff}}},
    {{{   102,     12,    -14}, 0, {     0,      0}, {0x34, 0x42, 0xa2, 0xff}}},
    {{{   102,     21,      0}, 0, {     0,      0}, {0x3c, 0x6e, 0x13, 0xff}}},
    {{{   102,     12,     14}, 0, {     0,      0}, {0x40, 0x36, 0x5e, 0xff}}},
    {{{    51,     -9,     21}, 0, {     0,      0}, {0x04, 0xc6, 0x70, 0xff}}},
    {{{    52,     12,     18}, 0, {     0,      0}, {0x07, 0x40, 0x6d, 0xff}}},
    {{{    52,     26,      0}, 0, {     0,      0}, {0x07, 0x7e, 0x00, 0xff}}},
};

// 0x05008428 - 0x050084C0
const Gfx peach_seg5_dl_05008428[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05000A28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&peach_seg5_lights_05008280.l, 1),
    gsSPLight(&peach_seg5_lights_05008280.a, 2),
    gsSPVertex(peach_seg5_vertex_05008298, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  3,  0, 0x0,  1,  5,  6, 0x0),
    gsSP2Triangles( 6,  2,  1, 0x0,  4,  7,  3, 0x0),
    gsSP2Triangles( 7,  4,  8, 0x0,  8,  9,  7, 0x0),
    gsSP2Triangles( 6, 10, 11, 0x0, 11, 10,  9, 0x0),
    gsSP2Triangles(11,  9,  8, 0x0,  6,  5, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x050084C0 - 0x05008560
const Gfx peach_seg5_dl_050084C0[] = {
    gsSPVertex(peach_seg5_vertex_05008358, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  4, 0x0),
    gsSP2Triangles( 3,  5,  2, 0x0,  2,  5,  0, 0x0),
    gsSP2Triangles( 4,  2,  1, 0x0,  6,  7,  5, 0x0),
    gsSP2Triangles( 7,  0,  5, 0x0,  3,  6,  5, 0x0),
    gsSP2Triangles( 8,  9,  0, 0x0,  1,  0,  9, 0x0),
    gsSP2Triangles( 0,  7,  8, 0x0,  4,  1, 10, 0x0),
    gsSP2Triangles( 1,  9, 11, 0x0, 11, 10,  1, 0x0),
    gsSP2Triangles(12,  8,  7, 0x0, 12,  7,  6, 0x0),
    gsSP2Triangles(11,  8, 12, 0x0, 11,  9,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x05008560 - 0x050085D0
const Gfx peach_seg5_dl_05008560[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(peach_seg5_dl_05008428),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(peach_seg5_dl_050084C0),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x050085D0
static const Lights1 peach_seg5_lights_050085D0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x050085E8
static const Vtx peach_seg5_vertex_050085E8[] = {
    {{{   -18,      2,      0}, 0, {     0,      0}, {0x82, 0x01, 0x03, 0xff}}},
    {{{     0,     -9,    -19}, 0, {     0,      0}, {0xcb, 0xcd, 0x99, 0xff}}},
    {{{    -5,    -20,      0}, 0, {     0,      0}, {0xd6, 0x89, 0xf5, 0xff}}},
    {{{    77,    -12,      0}, 0, {     0,      0}, {0x25, 0x88, 0x11, 0xff}}},
    {{{     1,     -9,     18}, 0, {     0,      0}, {0xe3, 0xc5, 0x6c, 0xff}}},
    {{{    77,     -6,    -14}, 0, {     0,      0}, {0x25, 0xbf, 0x9a, 0xff}}},
    {{{    77,     -6,     12}, 0, {     0,      0}, {0x2f, 0xc2, 0x63, 0xff}}},
    {{{    77,      7,     12}, 0, {     0,      0}, {0x26, 0x39, 0x6a, 0xff}}},
    {{{     1,     13,     19}, 0, {     0,      0}, {0xcf, 0x32, 0x69, 0xff}}},
    {{{    -4,     24,      0}, 0, {     0,      0}, {0xd8, 0x76, 0x13, 0xff}}},
    {{{     0,     13,    -19}, 0, {     0,      0}, {0xe2, 0x3a, 0x94, 0xff}}},
    {{{    77,     15,      0}, 0, {     0,      0}, {0x2c, 0x76, 0xf5, 0xff}}},
    {{{    77,      7,    -14}, 0, {     0,      0}, {0x34, 0x3a, 0x9d, 0xff}}},
    {{{    94,      0,      0}, 0, {     0,      0}, {0x7e, 0xfa, 0x05, 0xff}}},
};

// 0x050086C8 - 0x050087A8
const Gfx peach_seg5_dl_050086C8[] = {
    gsSPLight(&peach_seg5_lights_050085D0.l, 1),
    gsSPLight(&peach_seg5_lights_050085D0.a, 2),
    gsSPVertex(peach_seg5_vertex_050085E8, 14, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSP2Triangles( 3,  4,  2, 0x0,  2,  5,  3, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 4,  7,  8, 0x0,  8,  0,  4, 0x0),
    gsSP2Triangles( 9, 10,  0, 0x0,  0,  8,  9, 0x0),
    gsSP2Triangles( 1,  0, 10, 0x0, 11, 10,  9, 0x0),
    gsSP2Triangles( 9,  8,  7, 0x0,  9,  7, 11, 0x0),
    gsSP2Triangles(11, 12, 10, 0x0, 10, 12,  5, 0x0),
    gsSP2Triangles(10,  5,  1, 0x0,  6, 13,  7, 0x0),
    gsSP2Triangles( 7, 13, 11, 0x0, 11, 13, 12, 0x0),
    gsSP2Triangles(13,  3,  5, 0x0, 12, 13,  5, 0x0),
    gsSP1Triangle( 6,  3, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x050087A8 - 0x050087D0
const Gfx peach_seg5_dl_050087A8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(peach_seg5_dl_050086C8),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x050087D0
static const Lights1 peach_seg5_lights_050087D0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x050087E8
static const Vtx peach_seg5_vertex_050087E8[] = {
    {{{    23,     13,    -17}, 0, {   568,   -670}, {0x00, 0x3c, 0x91, 0xff}}},
    {{{    52,     12,    -17}, 0, {   990,   -670}, {0x07, 0x3f, 0x93, 0xff}}},
    {{{    51,     -9,    -20}, 0, {   982,   -348}, {0x03, 0xc7, 0x8f, 0xff}}},
    {{{    52,     26,      0}, 0, {   996,   -866}, {0x07, 0x7e, 0xfd, 0xff}}},
    {{{    23,    -10,    -19}, 0, {   560,   -318}, {0xff, 0xc6, 0x90, 0xff}}},
    {{{    23,     27,      0}, 0, {   572,   -864}, {0x05, 0x7b, 0x1d, 0xff}}},
    {{{    52,     12,     18}, 0, {   988,   -670}, {0x06, 0x3d, 0x6e, 0xff}}},
    {{{    23,     13,     19}, 0, {   566,   -670}, {0x05, 0x2d, 0x76, 0xff}}},
    {{{    51,     -9,     20}, 0, {   978,   -350}, {0x05, 0xc5, 0x70, 0xff}}},
    {{{    23,    -10,     21}, 0, {   558,   -320}, {0x05, 0xae, 0x60, 0xff}}},
    {{{    51,    -20,      0}, 0, {   980,   -186}, {0x05, 0x82, 0x00, 0xff}}},
    {{{    22,    -21,      0}, 0, {   556,   -160}, {0x03, 0x84, 0xe8, 0xff}}},
};

// 0x050088A8
static const Vtx peach_seg5_vertex_050088A8[] = {
    {{{   101,    -18,      1}, 0, {     0,      0}, {0x2e, 0x8b, 0x0e, 0xff}}},
    {{{   101,     -9,    -15}, 0, {     0,      0}, {0x2d, 0xc9, 0x98, 0xff}}},
    {{{   115,     -2,      1}, 0, {     0,      0}, {0x7e, 0xfa, 0x00, 0xff}}},
    {{{   101,     -9,     17}, 0, {     0,      0}, {0x32, 0xd8, 0x6d, 0xff}}},
    {{{   102,     12,     15}, 0, {     0,      0}, {0x34, 0x42, 0x5e, 0xff}}},
    {{{   102,     12,    -13}, 0, {     0,      0}, {0x40, 0x36, 0xa2, 0xff}}},
    {{{   102,     21,      1}, 0, {     0,      0}, {0x3c, 0x6d, 0xec, 0xff}}},
    {{{    52,     12,     18}, 0, {     0,      0}, {0x06, 0x3d, 0x6e, 0xff}}},
    {{{    51,     -9,     20}, 0, {     0,      0}, {0x05, 0xc5, 0x70, 0xff}}},
    {{{    51,    -20,      0}, 0, {     0,      0}, {0x05, 0x82, 0x00, 0xff}}},
    {{{    51,     -9,    -20}, 0, {     0,      0}, {0x03, 0xc7, 0x8f, 0xff}}},
    {{{    52,     12,    -17}, 0, {     0,      0}, {0x07, 0x3f, 0x93, 0xff}}},
    {{{    52,     26,      0}, 0, {     0,      0}, {0x07, 0x7e, 0xfd, 0xff}}},
};

// 0x05008978 - 0x05008A10
const Gfx peach_seg5_dl_05008978[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05000A28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&peach_seg5_lights_050087D0.l, 1),
    gsSPLight(&peach_seg5_lights_050087D0.a, 2),
    gsSPVertex(peach_seg5_vertex_050087E8, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  0,  5,  3, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 8,  6,  7, 0x0,  7,  9,  8, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  9, 11, 10, 0x0),
    gsSP2Triangles(10, 11,  2, 0x0, 11,  4,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05008A10 - 0x05008AB0
const Gfx peach_seg5_dl_05008A10[] = {
    gsSPVertex(peach_seg5_vertex_050088A8, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  4, 0x0),
    gsSP2Triangles( 2,  5,  6, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 5,  2,  1, 0x0,  6,  4,  2, 0x0),
    gsSP2Triangles( 3,  4,  7, 0x0,  3,  7,  8, 0x0),
    gsSP2Triangles( 0,  3,  8, 0x0,  9,  0,  8, 0x0),
    gsSP2Triangles( 1,  0,  9, 0x0,  1, 10, 11, 0x0),
    gsSP2Triangles(10,  1,  9, 0x0, 11,  5,  1, 0x0),
    gsSP2Triangles( 7,  4, 12, 0x0,  4,  6, 12, 0x0),
    gsSP2Triangles(12,  6, 11, 0x0,  6,  5, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x05008AB0 - 0x05008B20
const Gfx peach_seg5_dl_05008AB0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(peach_seg5_dl_05008978),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(peach_seg5_dl_05008A10),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x05008B20
static const Lights1 peach_seg5_lights_05008B20 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05008B38
static const Vtx peach_seg5_vertex_05008B38[] = {
    {{{    -5,    -20,      1}, 0, {     0,      0}, {0xd6, 0x89, 0x0a, 0xff}}},
    {{{     0,     -9,     20}, 0, {     0,      0}, {0xcb, 0xcd, 0x67, 0xff}}},
    {{{   -18,      2,      1}, 0, {     0,      0}, {0x82, 0x01, 0xfd, 0xff}}},
    {{{     0,     13,     20}, 0, {     0,      0}, {0xe2, 0x3b, 0x6c, 0xff}}},
    {{{    -4,     24,      1}, 0, {     0,      0}, {0xd8, 0x76, 0xec, 0xff}}},
    {{{     1,     -9,    -17}, 0, {     0,      0}, {0xe3, 0xc6, 0x94, 0xff}}},
    {{{     1,     13,    -18}, 0, {     0,      0}, {0xcf, 0x32, 0x97, 0xff}}},
    {{{    77,    -12,      0}, 0, {     0,      0}, {0x25, 0x89, 0xec, 0xff}}},
    {{{    77,     -6,    -11}, 0, {     0,      0}, {0x2d, 0xc4, 0x9b, 0xff}}},
    {{{    77,      7,    -11}, 0, {     0,      0}, {0x24, 0x38, 0x95, 0xff}}},
    {{{    77,     -6,     15}, 0, {     0,      0}, {0x26, 0xbe, 0x65, 0xff}}},
    {{{    77,      7,     15}, 0, {     0,      0}, {0x36, 0x3b, 0x61, 0xff}}},
    {{{    77,     15,      0}, 0, {     0,      0}, {0x2c, 0x76, 0x07, 0xff}}},
    {{{    94,      0,      0}, 0, {     0,      0}, {0x7e, 0xfa, 0xf6, 0xff}}},
};

// 0x05008C18 - 0x05008CF8
const Gfx peach_seg5_dl_05008C18[] = {
    gsSPLight(&peach_seg5_lights_05008B20.l, 1),
    gsSPLight(&peach_seg5_lights_05008B20.a, 2),
    gsSPVertex(peach_seg5_vertex_05008B38, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 2,  5,  0, 0x0,  5,  2,  6, 0x0),
    gsSP2Triangles( 4,  6,  2, 0x0,  3,  2,  1, 0x0),
    gsSP2Triangles( 0,  5,  7, 0x0,  5,  8,  7, 0x0),
    gsSP2Triangles( 9,  8,  5, 0x0,  6,  9,  5, 0x0),
    gsSP2Triangles( 7, 10,  0, 0x0, 10,  1,  0, 0x0),
    gsSP2Triangles( 3, 11, 12, 0x0,  4,  3, 12, 0x0),
    gsSP2Triangles(10, 11,  3, 0x0,  1, 10,  3, 0x0),
    gsSP2Triangles( 9,  6,  4, 0x0, 12,  9,  4, 0x0),
    gsSP2Triangles(13,  9, 12, 0x0, 11, 13, 12, 0x0),
    gsSP2Triangles( 9, 13,  8, 0x0, 10,  7, 13, 0x0),
    gsSP2Triangles( 7,  8, 13, 0x0, 10, 13, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x05008CF8 - 0x05008D20
const Gfx peach_seg5_dl_05008CF8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(peach_seg5_dl_05008C18),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x05008D20
static const Lights1 peach_seg5_lights_05008D20 = gdSPDefLights1(
    0x0b, 0x1a, 0x67,
    0x16, 0x35, 0xce, 0x28, 0x28, 0x28
);

// 0x05008D38
static const Vtx peach_seg5_vertex_05008D38[] = {
    {{{     6,     -2,     75}, 0, {     0,      0}, {0xa8, 0xae, 0x26, 0xff}}},
    {{{    -1,      8,     67}, 0, {     0,      0}, {0x82, 0xf9, 0x02, 0xff}}},
    {{{     1,      8,     52}, 0, {     0,      0}, {0x93, 0xec, 0xc3, 0xff}}},
    {{{     3,     10,     80}, 0, {     0,      0}, {0x9f, 0xf6, 0x50, 0xff}}},
    {{{    15,      4,     86}, 0, {     0,      0}, {0xe0, 0xc7, 0x6c, 0xff}}},
    {{{    13,     20,     85}, 0, {     0,      0}, {0xe1, 0x25, 0x75, 0xff}}},
    {{{     2,     23,     73}, 0, {     0,      0}, {0xa2, 0x45, 0x30, 0xff}}},
    {{{    25,     27,     82}, 0, {     0,      0}, {0x16, 0x51, 0x5e, 0xff}}},
    {{{    29,      1,     84}, 0, {     0,      0}, {0x28, 0xc4, 0x67, 0xff}}},
    {{{     8,     31,     62}, 0, {     0,      0}, {0xb9, 0x68, 0xf8, 0xff}}},
    {{{    36,     28,     72}, 0, {     0,      0}, {0x25, 0x6c, 0x35, 0xff}}},
    {{{    40,      2,     75}, 0, {     0,      0}, {0x51, 0xe3, 0x5c, 0xff}}},
    {{{    19,     32,     53}, 0, {     0,      0}, {0xc8, 0x61, 0xc6, 0xff}}},
    {{{    12,      9,     43}, 0, {     0,      0}, {0xb8, 0xcd, 0xa6, 0xff}}},
    {{{    26,     -9,     56}, 0, {     0,      0}, {0x0b, 0x82, 0x01, 0xff}}},
    {{{    15,    -10,     66}, 0, {     0,      0}, {0xdb, 0x87, 0x04, 0xff}}},
};

// 0x05008E38 - 0x05008F20
const Gfx peach_seg5_dl_05008E38[] = {
    gsSPLight(&peach_seg5_lights_05008D20.l, 1),
    gsSPLight(&peach_seg5_lights_05008D20.a, 2),
    gsSPVertex(peach_seg5_vertex_05008D38, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 3,  4,  5, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  3,  6,  1, 0x0),
    gsSP2Triangles( 6,  5,  7, 0x0,  5,  4,  8, 0x0),
    gsSP2Triangles( 5,  8,  7, 0x0,  6,  7,  9, 0x0),
    gsSP2Triangles( 1,  6,  9, 0x0,  9,  7, 10, 0x0),
    gsSP2Triangles( 7, 11, 10, 0x0,  7,  8, 11, 0x0),
    gsSP2Triangles( 2,  9, 12, 0x0,  9, 10, 12, 0x0),
    gsSP2Triangles( 1,  9,  2, 0x0,  2, 12, 13, 0x0),
    gsSP2Triangles( 8, 14, 11, 0x0,  8, 15, 14, 0x0),
    gsSP2Triangles( 4, 15,  8, 0x0,  4,  0, 15, 0x0),
    gsSP2Triangles( 0,  2, 15, 0x0, 15,  2, 13, 0x0),
    gsSP1Triangle(15, 13, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x05008F20 - 0x05008F48
const Gfx peach_seg5_dl_05008F20[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(peach_seg5_dl_05008E38),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x05008F48
static const Lights1 peach_seg5_lights_05008F48 = gdSPDefLights1(
    0x0b, 0x1a, 0x67,
    0x16, 0x35, 0xce, 0x28, 0x28, 0x28
);

// 0x05008F60
static const Vtx peach_seg5_vertex_05008F60[] = {
    {{{    29,      1,    -83}, 0, {     0,      0}, {0x29, 0xca, 0x96, 0xff}}},
    {{{     6,     -3,    -74}, 0, {     0,      0}, {0xbb, 0xa8, 0xc5, 0xff}}},
    {{{    15,      4,    -85}, 0, {     0,      0}, {0xeb, 0xe2, 0x87, 0xff}}},
    {{{     8,     31,    -62}, 0, {     0,      0}, {0xb5, 0x65, 0x0a, 0xff}}},
    {{{    13,     20,    -84}, 0, {     0,      0}, {0xd0, 0x39, 0x9a, 0xff}}},
    {{{     2,     22,    -72}, 0, {     0,      0}, {0x91, 0x34, 0xe2, 0xff}}},
    {{{     3,      9,    -79}, 0, {     0,      0}, {0x9f, 0xf6, 0xb0, 0xff}}},
    {{{     1,      7,    -52}, 0, {     0,      0}, {0x93, 0xe5, 0x3a, 0xff}}},
    {{{    -1,      8,    -66}, 0, {     0,      0}, {0x87, 0xdd, 0xfc, 0xff}}},
    {{{    25,     27,    -81}, 0, {     0,      0}, {0x13, 0x53, 0xa2, 0xff}}},
    {{{    12,      8,    -42}, 0, {     0,      0}, {0xab, 0x07, 0x5d, 0xff}}},
    {{{    19,     32,    -52}, 0, {     0,      0}, {0xe6, 0x7b, 0x10, 0xff}}},
    {{{    36,     28,    -72}, 0, {     0,      0}, {0x41, 0x41, 0xaa, 0xff}}},
    {{{    40,      2,    -74}, 0, {     0,      0}, {0x44, 0xae, 0xbd, 0xff}}},
    {{{    15,    -11,    -65}, 0, {     0,      0}, {0xde, 0x87, 0xf8, 0xff}}},
    {{{    26,     -9,    -55}, 0, {     0,      0}, {0xeb, 0x8b, 0x2b, 0xff}}},
};

// 0x05009060 - 0x05009148
const Gfx peach_seg5_dl_05009060[] = {
    gsSPLight(&peach_seg5_lights_05008F48.l, 1),
    gsSPLight(&peach_seg5_lights_05008F48.a, 2),
    gsSPVertex(peach_seg5_vertex_05008F60, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  7,  3,  5, 0x0),
    gsSP2Triangles( 7,  5,  8, 0x0,  8,  5,  6, 0x0),
    gsSP2Triangles( 4,  2,  6, 0x0,  3,  9,  4, 0x0),
    gsSP2Triangles( 9,  2,  4, 0x0,  1,  8,  6, 0x0),
    gsSP2Triangles( 2,  1,  6, 0x0, 10, 11,  3, 0x0),
    gsSP2Triangles(10,  3,  7, 0x0, 11,  9,  3, 0x0),
    gsSP2Triangles(11, 12,  9, 0x0, 12,  0,  9, 0x0),
    gsSP2Triangles( 9,  0,  2, 0x0, 12, 13,  0, 0x0),
    gsSP2Triangles(13, 14,  0, 0x0,  0, 14,  1, 0x0),
    gsSP2Triangles(13, 15, 14, 0x0, 14,  8,  1, 0x0),
    gsSP2Triangles(14,  7,  8, 0x0, 15, 10,  7, 0x0),
    gsSP1Triangle(15,  7, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x05009148 - 0x05009170
const Gfx peach_seg5_dl_05009148[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(peach_seg5_dl_05009060),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x05009170
static const Lights1 peach_seg5_lights_05009170 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05009188
static const Vtx peach_seg5_vertex_05009188[] = {
    {{{     7,    -19,    -19}, 0, {     0,      0}, {0xbe, 0xb1, 0xb8, 0xff}}},
    {{{    44,    -29,    -34}, 0, {     0,      0}, {0xf6, 0xb3, 0x9c, 0xff}}},
    {{{    30,    -23,    -10}, 0, {     0,      0}, {0x32, 0x8c, 0x08, 0xff}}},
    {{{     7,    -15,     16}, 0, {     0,      0}, {0xdd, 0xb2, 0x5d, 0xff}}},
    {{{   -10,     -7,      9}, 0, {     0,      0}, {0x99, 0xce, 0x34, 0xff}}},
    {{{   -10,     -7,    -11}, 0, {     0,      0}, {0x95, 0xd1, 0xd1, 0xff}}},
    {{{   -11,     12,    -11}, 0, {     0,      0}, {0x9e, 0x31, 0xc2, 0xff}}},
    {{{    17,     -3,    -23}, 0, {     0,      0}, {0xee, 0x30, 0x8d, 0xff}}},
    {{{    43,    -15,    -34}, 0, {     0,      0}, {0xfe, 0x2a, 0x89, 0xff}}},
    {{{    45,     21,     19}, 0, {     0,      0}, {0x14, 0x4a, 0x64, 0xff}}},
    {{{    47,    -17,     19}, 0, {     0,      0}, {0x1f, 0xab, 0x58, 0xff}}},
    {{{     5,     21,     16}, 0, {     0,      0}, {0xcf, 0x54, 0x50, 0xff}}},
    {{{   -11,     12,      9}, 0, {     0,      0}, {0x96, 0x2d, 0x34, 0xff}}},
    {{{    26,      9,    -12}, 0, {     0,      0}, {0x3b, 0x4c, 0xaf, 0xff}}},
    {{{     5,     24,     -6}, 0, {     0,      0}, {0xf2, 0x69, 0xbb, 0xff}}},
    {{{    87,     10,      6}, 0, {     0,      0}, {0x6a, 0x36, 0x2a, 0xff}}},
};

// 0x05009288
static const Vtx peach_seg5_vertex_05009288[] = {
    {{{    51,    -16,      0}, 0, {     0,      0}, {0x27, 0xaf, 0xa8, 0xff}}},
    {{{    87,    -10,      0}, 0, {     0,      0}, {0x48, 0xd3, 0xa2, 0xff}}},
    {{{    47,    -17,     19}, 0, {     0,      0}, {0x1f, 0xab, 0x58, 0xff}}},
    {{{    90,     -4,      5}, 0, {     0,      0}, {0x67, 0xdc, 0x3f, 0xff}}},
    {{{    30,    -23,    -10}, 0, {     0,      0}, {0x32, 0x8c, 0x08, 0xff}}},
    {{{    87,     10,      6}, 0, {     0,      0}, {0x6a, 0x36, 0x2a, 0xff}}},
    {{{    52,    -19,    -32}, 0, {     0,      0}, {0x70, 0x22, 0xd1, 0xff}}},
    {{{    26,      9,    -12}, 0, {     0,      0}, {0x3b, 0x4c, 0xaf, 0xff}}},
    {{{    42,     26,      2}, 0, {     0,      0}, {0x1e, 0x54, 0xa6, 0xff}}},
    {{{    50,    -30,    -32}, 0, {     0,      0}, {0x48, 0x99, 0xf4, 0xff}}},
    {{{    44,    -29,    -34}, 0, {     0,      0}, {0xf6, 0xb3, 0x9c, 0xff}}},
    {{{    45,     21,     19}, 0, {     0,      0}, {0x14, 0x4a, 0x64, 0xff}}},
    {{{    82,     14,      0}, 0, {     0,      0}, {0x42, 0x5f, 0xcf, 0xff}}},
    {{{     5,     24,     -6}, 0, {     0,      0}, {0xf2, 0x69, 0xbb, 0xff}}},
    {{{     5,     21,     16}, 0, {     0,      0}, {0xcf, 0x54, 0x50, 0xff}}},
    {{{    43,    -15,    -34}, 0, {     0,      0}, {0xfe, 0x2a, 0x89, 0xff}}},
};

// 0x05009388 - 0x05009500
const Gfx peach_seg5_dl_05009388[] = {
    gsSPLight(&peach_seg5_lights_05009170.l, 1),
    gsSPLight(&peach_seg5_lights_05009170.a, 2),
    gsSPVertex(peach_seg5_vertex_05009188, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  5,  0, 0x0,  0,  5,  6, 0x0),
    gsSP2Triangles( 7,  8,  0, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 7,  0,  6, 0x0,  8,  1,  0, 0x0),
    gsSP2Triangles( 9,  3, 10, 0x0, 10,  3,  2, 0x0),
    gsSP2Triangles(11,  4,  3, 0x0,  9, 11,  3, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  4, 12,  6, 0x0),
    gsSP2Triangles(11, 12,  4, 0x0,  6, 12, 11, 0x0),
    gsSP2Triangles(13,  8,  7, 0x0,  6, 14,  7, 0x0),
    gsSP2Triangles( 7, 14, 13, 0x0, 14,  6, 11, 0x0),
    gsSP1Triangle(15,  9, 10, 0x0),
    gsSPVertex(peach_seg5_vertex_05009288, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  2,  3,  5, 0x0),
    gsSP2Triangles( 5,  3,  1, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 0,  4,  8, 0x0,  4,  7,  8, 0x0),
    gsSP2Triangles( 4,  9,  6, 0x0, 10,  9,  4, 0x0),
    gsSP2Triangles( 8,  1,  0, 0x0, 11, 12,  8, 0x0),
    gsSP2Triangles( 7, 13,  8, 0x0, 14,  8, 13, 0x0),
    gsSP2Triangles(14, 11,  8, 0x0,  8, 12,  1, 0x0),
    gsSP2Triangles( 5,  1, 12, 0x0, 11,  5, 12, 0x0),
    gsSP2Triangles( 6,  9, 10, 0x0,  7,  6, 15, 0x0),
    gsSP1Triangle( 6, 10, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x05009500 - 0x05009528
const Gfx peach_seg5_dl_05009500[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(peach_seg5_dl_05009388),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x05009528
static const Lights1 peach_seg5_lights_05009528 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05009540
static const Vtx peach_seg5_vertex_05009540[] = {
    {{{   -10,     -7,     12}, 0, {     0,      0}, {0x95, 0xd1, 0x2f, 0xff}}},
    {{{   -11,     12,     12}, 0, {     0,      0}, {0x9e, 0x31, 0x3e, 0xff}}},
    {{{   -10,     -7,     -8}, 0, {     0,      0}, {0x99, 0xce, 0xcc, 0xff}}},
    {{{     7,    -15,    -15}, 0, {     0,      0}, {0xdd, 0xb2, 0xa3, 0xff}}},
    {{{     5,     21,    -15}, 0, {     0,      0}, {0xcf, 0x54, 0xb0, 0xff}}},
    {{{     7,    -19,     20}, 0, {     0,      0}, {0xbe, 0xb1, 0x48, 0xff}}},
    {{{   -11,     12,     -8}, 0, {     0,      0}, {0x96, 0x2d, 0xcc, 0xff}}},
    {{{    47,    -17,    -18}, 0, {     0,      0}, {0x1f, 0xab, 0xa8, 0xff}}},
    {{{    45,     21,    -18}, 0, {     0,      0}, {0x14, 0x4a, 0x9c, 0xff}}},
    {{{    30,    -23,     11}, 0, {     0,      0}, {0x32, 0x8c, 0xf8, 0xff}}},
    {{{    44,    -29,     35}, 0, {     0,      0}, {0xf6, 0xb3, 0x64, 0xff}}},
    {{{    43,    -15,     35}, 0, {     0,      0}, {0xfe, 0x2a, 0x77, 0xff}}},
    {{{    17,     -3,     24}, 0, {     0,      0}, {0xee, 0x30, 0x73, 0xff}}},
    {{{    50,    -30,     33}, 0, {     0,      0}, {0x48, 0x99, 0x0c, 0xff}}},
    {{{    52,    -19,     33}, 0, {     0,      0}, {0x70, 0x22, 0x2f, 0xff}}},
    {{{     5,     24,      7}, 0, {     0,      0}, {0xf2, 0x69, 0x45, 0xff}}},
};

// 0x05009640
static const Vtx peach_seg5_vertex_05009640[] = {
    {{{    17,     -3,     24}, 0, {     0,      0}, {0xee, 0x30, 0x73, 0xff}}},
    {{{    43,    -15,     35}, 0, {     0,      0}, {0xfe, 0x2a, 0x77, 0xff}}},
    {{{    26,      9,     13}, 0, {     0,      0}, {0x3b, 0x4c, 0x51, 0xff}}},
    {{{     5,     24,      7}, 0, {     0,      0}, {0xf2, 0x69, 0x45, 0xff}}},
    {{{    42,     26,     -1}, 0, {     0,      0}, {0x1e, 0x54, 0x5a, 0xff}}},
    {{{    30,    -23,     11}, 0, {     0,      0}, {0x32, 0x8c, 0xf8, 0xff}}},
    {{{    51,    -16,      1}, 0, {     0,      0}, {0x27, 0xaf, 0x58, 0xff}}},
    {{{    47,    -17,    -18}, 0, {     0,      0}, {0x1f, 0xab, 0xa8, 0xff}}},
    {{{    87,    -10,      1}, 0, {     0,      0}, {0x48, 0xd3, 0x5e, 0xff}}},
    {{{    52,    -19,     33}, 0, {     0,      0}, {0x70, 0x22, 0x2f, 0xff}}},
    {{{    50,    -30,     33}, 0, {     0,      0}, {0x48, 0x99, 0x0c, 0xff}}},
    {{{    44,    -29,     35}, 0, {     0,      0}, {0xf6, 0xb3, 0x64, 0xff}}},
    {{{     5,     21,    -15}, 0, {     0,      0}, {0xcf, 0x54, 0xb0, 0xff}}},
    {{{    82,     14,      1}, 0, {     0,      0}, {0x42, 0x5f, 0x31, 0xff}}},
    {{{    87,     10,     -5}, 0, {     0,      0}, {0x6a, 0x36, 0xd6, 0xff}}},
    {{{    45,     21,    -18}, 0, {     0,      0}, {0x14, 0x4a, 0x9c, 0xff}}},
};

// 0x05009740
static const Vtx peach_seg5_vertex_05009740[] = {
    {{{    47,    -17,    -18}, 0, {     0,      0}, {0x1f, 0xab, 0xa8, 0xff}}},
    {{{    90,     -4,     -4}, 0, {     0,      0}, {0x67, 0xdc, 0xc1, 0xff}}},
    {{{    87,    -10,      1}, 0, {     0,      0}, {0x48, 0xd3, 0x5e, 0xff}}},
    {{{    87,     10,     -5}, 0, {     0,      0}, {0x6a, 0x36, 0xd6, 0xff}}},
    {{{    82,     14,      1}, 0, {     0,      0}, {0x42, 0x5f, 0x31, 0xff}}},
    {{{    42,     26,     -1}, 0, {     0,      0}, {0x1e, 0x54, 0x5a, 0xff}}},
    {{{    45,     21,    -18}, 0, {     0,      0}, {0x14, 0x4a, 0x9c, 0xff}}},
    {{{     5,     21,    -15}, 0, {     0,      0}, {0xcf, 0x54, 0xb0, 0xff}}},
};

// 0x050097C0 - 0x05009940
const Gfx peach_seg5_dl_050097C0[] = {
    gsSPLight(&peach_seg5_lights_05009528.l, 1),
    gsSPLight(&peach_seg5_lights_05009528.a, 2),
    gsSPVertex(peach_seg5_vertex_05009540, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  4, 0x0),
    gsSP2Triangles( 5,  0,  2, 0x0,  2,  3,  5, 0x0),
    gsSP2Triangles( 1,  6,  2, 0x0,  2,  6,  4, 0x0),
    gsSP2Triangles( 7,  3,  8, 0x0,  9,  5,  3, 0x0),
    gsSP2Triangles( 9,  3,  7, 0x0,  3,  4,  8, 0x0),
    gsSP2Triangles( 9, 10,  5, 0x0,  1,  0,  5, 0x0),
    gsSP2Triangles( 5, 11, 12, 0x0,  1,  5, 12, 0x0),
    gsSP2Triangles( 5, 10, 11, 0x0,  4,  6,  1, 0x0),
    gsSP2Triangles(10, 13, 14, 0x0,  4,  1, 15, 0x0),
    gsSP1Triangle(12, 15,  1, 0x0),
    gsSPVertex(peach_seg5_vertex_05009640, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  6, 0x0),
    gsSP2Triangles( 6,  5,  7, 0x0,  6,  8,  4, 0x0),
    gsSP2Triangles( 2,  9,  5, 0x0,  4,  2,  5, 0x0),
    gsSP2Triangles( 9, 10,  5, 0x0,  5, 10, 11, 0x0),
    gsSP2Triangles( 4,  3,  2, 0x0,  3,  4, 12, 0x0),
    gsSP2Triangles( 1,  9,  2, 0x0,  1, 11,  9, 0x0),
    gsSP2Triangles(13, 14, 15, 0x0,  7, 15, 14, 0x0),
    gsSPVertex(peach_seg5_vertex_05009740, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  2,  3, 0x0,  2,  4,  5, 0x0),
    gsSP2Triangles( 2,  1,  3, 0x0,  5,  4,  6, 0x0),
    gsSP1Triangle( 5,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x05009940 - 0x05009968
const Gfx peach_seg5_dl_05009940[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(peach_seg5_dl_050097C0),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x05009968
static const Lights1 peach_seg5_lights_05009968 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05009980
static const Vtx peach_seg5_vertex_05009980[] = {
    {{{   324,    171,    145}, 0, {  1954,   1582}, {0xe1, 0x5d, 0x4f, 0xff}}},
    {{{   322,    239,     -3}, 0, {  2536,   1574}, {0xe1, 0x79, 0xef, 0xff}}},
    {{{   184,    205,     -3}, 0, {  2538,    886}, {0xde, 0x79, 0x0d, 0xff}}},
    {{{   179,    141,    121}, 0, {  1948,    862}, {0xde, 0x4a, 0x60, 0xff}}},
    {{{   179,    141,   -140}, 0, {  3160,    860}, {0xdb, 0x56, 0xab, 0xff}}},
    {{{   110,    182,     -1}, 0, {  2534,    516}, {0xd0, 0x75, 0x03, 0xff}}},
    {{{    52,    106,     99}, 0, {  1914,    230}, {0xcd, 0x4a, 0x59, 0xff}}},
    {{{   328,      9,    204}, 0, {  1280,   1604}, {0xe5, 0x00, 0x7b, 0xff}}},
    {{{   189,      5,    173}, 0, {  1268,    912}, {0xe1, 0x00, 0x7a, 0xff}}},
    {{{   124,      3,    154}, 0, {  1262,    586}, {0xda, 0x00, 0x79, 0xff}}},
    {{{   324,    171,   -166}, 0, {  3150,   1582}, {0xdf, 0x58, 0xac, 0xff}}},
    {{{   328,      9,   -245}, 0, {  3772,   1604}, {0xdf, 0x00, 0x86, 0xff}}},
    {{{    52,    106,   -109}, 0, {  3178,    228}, {0xc2, 0x4c, 0xb1, 0xff}}},
    {{{   124,      3,   -182}, 0, {  3788,    584}, {0xcd, 0xff, 0x8d, 0xff}}},
    {{{   189,      5,   -207}, 0, {  3782,    912}, {0xd9, 0xff, 0x88, 0xff}}},
};

// 0x05009A70
static const Vtx peach_seg5_vertex_05009A70[] = {
    {{{   333,   -153,    145}, 0, {   580,   1626}, {0xe7, 0xa1, 0x4f, 0xff}}},
    {{{   328,      9,    204}, 0, {  1280,   1604}, {0xe5, 0x00, 0x7b, 0xff}}},
    {{{   186,   -131,    121}, 0, {   570,    898}, {0xe3, 0xb4, 0x60, 0xff}}},
    {{{   328,      9,   -245}, 0, {  3772,   1604}, {0xdf, 0x00, 0x86, 0xff}}},
    {{{   332,   -153,   -166}, 0, {  4408,   1624}, {0xe4, 0xa6, 0xac, 0xff}}},
    {{{   186,   -131,   -140}, 0, {  4414,    896}, {0xe0, 0xa8, 0xab, 0xff}}},
    {{{   189,      5,   -207}, 0, {  3782,    912}, {0xd9, 0xff, 0x88, 0xff}}},
    {{{   124,      3,   -182}, 0, {  3788,    584}, {0xcd, 0xff, 0x8d, 0xff}}},
    {{{   334,   -220,     -3}, 0, {  5064,   1634}, {0xe9, 0x85, 0xef, 0xff}}},
    {{{    59,   -104,   -109}, 0, {  4420,    262}, {0xc6, 0xb1, 0xb1, 0xff}}},
    {{{   119,   -176,     -1}, 0, {  5066,    564}, {0xd6, 0x89, 0x02, 0xff}}},
    {{{   194,   -195,     -3}, 0, {  5062,    938}, {0xe6, 0x85, 0x0d, 0xff}}},
    {{{   333,   -153,    145}, 0, {  5690,   1626}, {0xe7, 0xa1, 0x4f, 0xff}}},
    {{{   186,   -131,    121}, 0, {  5680,    898}, {0xe3, 0xb4, 0x60, 0xff}}},
    {{{    59,   -104,    100}, 0, {  5698,    262}, {0xd1, 0xb4, 0x59, 0xff}}},
};

// 0x05009B60
static const Vtx peach_seg5_vertex_05009B60[] = {
    {{{   -10,      0,   -101}, 0, {  3806,    -88}, {0xaf, 0xfe, 0x9f, 0xff}}},
    {{{   -44,      0,    -67}, 0, {  3818,   -254}, {0x8b, 0xfd, 0xd1, 0xff}}},
    {{{   -45,     44,    -48}, 0, {  3206,   -260}, {0x8d, 0x1d, 0xd5, 0xff}}},
    {{{   186,   -131,    121}, 0, {   570,    898}, {0xe3, 0xb4, 0x60, 0xff}}},
    {{{   189,      5,    173}, 0, {  1268,    912}, {0xe1, 0x00, 0x7a, 0xff}}},
    {{{   124,      3,    154}, 0, {  1262,    586}, {0xda, 0x00, 0x79, 0xff}}},
    {{{   328,      9,    204}, 0, {  1280,   1604}, {0xe5, 0x00, 0x7b, 0xff}}},
    {{{    59,   -104,    100}, 0, {   588,    262}, {0xd1, 0xb4, 0x59, 0xff}}},
    {{{    -8,    -81,    -67}, 0, {  4516,    -78}, {0xa9, 0xbb, 0xc4, 0xff}}},
    {{{   -42,    -46,    -48}, 0, {  4422,   -248}, {0x8f, 0xde, 0xd4, 0xff}}},
    {{{   -42,    -46,     42}, 0, {  5672,   -248}, {0x87, 0xe7, 0x19, 0xff}}},
    {{{   -42,    -65,     -2}, 0, {  5034,   -246}, {0x90, 0xc6, 0xf7, 0xff}}},
    {{{    -7,   -114,      7}, 0, {  5124,    -74}, {0xb0, 0x9f, 0x06, 0xff}}},
    {{{   -45,     63,     -2}, 0, {  2568,   -264}, {0x86, 0x22, 0xfa, 0xff}}},
    {{{   -12,     81,    -67}, 0, {  3092,    -98}, {0xa6, 0x41, 0xc4, 0xff}}},
    {{{   -13,    114,      7}, 0, {  2476,   -104}, {0xab, 0x5d, 0x06, 0xff}}},
};

// 0x05009C60
static const Vtx peach_seg5_vertex_05009C60[] = {
    {{{   -44,      0,     61}, 0, {  1226,   -254}, {0x89, 0xfd, 0x2a, 0xff}}},
    {{{   -45,     44,     42}, 0, {  1904,   -260}, {0x90, 0x25, 0x2d, 0xff}}},
    {{{   -45,     63,     -2}, 0, {  2568,   -264}, {0x86, 0x22, 0xfa, 0xff}}},
    {{{   -13,    114,      7}, 0, {  2476,   -104}, {0xab, 0x5d, 0x06, 0xff}}},
    {{{   -42,    -46,     42}, 0, {   562,   -248}, {0x87, 0xe7, 0x19, 0xff}}},
    {{{   -10,      0,    100}, 0, {  1240,    -88}, {0xb3, 0xff, 0x64, 0xff}}},
    {{{    -8,    -81,     75}, 0, {   572,    -78}, {0xad, 0xc1, 0x48, 0xff}}},
    {{{    -7,   -114,      7}, 0, {    14,    -74}, {0xb0, 0x9f, 0x06, 0xff}}},
    {{{   124,      3,    154}, 0, {  1262,    586}, {0xda, 0x00, 0x79, 0xff}}},
    {{{    59,   -104,    100}, 0, {   588,    262}, {0xd1, 0xb4, 0x59, 0xff}}},
    {{{    52,    106,     99}, 0, {  1914,    230}, {0xcd, 0x4a, 0x59, 0xff}}},
    {{{   119,   -176,     -1}, 0, {  5066,    564}, {0xd6, 0x89, 0x02, 0xff}}},
    {{{    -7,   -114,      7}, 0, {  5124,    -74}, {0xb0, 0x9f, 0x06, 0xff}}},
    {{{    59,   -104,   -109}, 0, {  4420,    262}, {0xc6, 0xb1, 0xb1, 0xff}}},
    {{{    59,   -104,    100}, 0, {  5698,    262}, {0xd1, 0xb4, 0x59, 0xff}}},
};

// 0x05009D50
static const Vtx peach_seg5_vertex_05009D50[] = {
    {{{   124,      3,   -182}, 0, {  3788,    584}, {0xcd, 0xff, 0x8d, 0xff}}},
    {{{   -10,      0,   -101}, 0, {  3806,    -88}, {0xaf, 0xfe, 0x9f, 0xff}}},
    {{{    52,    106,   -109}, 0, {  3178,    228}, {0xc2, 0x4c, 0xb1, 0xff}}},
    {{{    59,   -104,   -109}, 0, {  4420,    262}, {0xc6, 0xb1, 0xb1, 0xff}}},
    {{{   -13,    114,      7}, 0, {  2476,   -104}, {0xab, 0x5d, 0x06, 0xff}}},
    {{{   110,    182,     -1}, 0, {  2534,    516}, {0xd0, 0x75, 0x03, 0xff}}},
    {{{    52,    106,     99}, 0, {  1914,    230}, {0xcd, 0x4a, 0x59, 0xff}}},
    {{{   -12,     81,     75}, 0, {  1914,    -98}, {0xab, 0x3b, 0x48, 0xff}}},
    {{{   -10,      0,    100}, 0, {  1240,    -88}, {0xb3, 0xff, 0x64, 0xff}}},
    {{{   -45,     44,     42}, 0, {  1904,   -260}, {0x90, 0x25, 0x2d, 0xff}}},
    {{{    -7,   -114,      7}, 0, {  5124,    -74}, {0xb0, 0x9f, 0x06, 0xff}}},
    {{{    -8,    -81,    -67}, 0, {  4516,    -78}, {0xa9, 0xbb, 0xc4, 0xff}}},
    {{{   -12,     81,    -67}, 0, {  3092,    -98}, {0xa6, 0x41, 0xc4, 0xff}}},
};

// 0x05009E20 - 0x0500A0A8
const Gfx peach_seg5_dl_05009E20[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, peach_seg5_texture_05004028),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&peach_seg5_lights_05009968.l, 1),
    gsSPLight(&peach_seg5_lights_05009968.a, 2),
    gsSPVertex(peach_seg5_vertex_05009980, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  2,  1, 0x0,  5,  2,  4, 0x0),
    gsSP2Triangles( 3,  2,  5, 0x0,  5,  6,  3, 0x0),
    gsSP2Triangles( 7,  0,  3, 0x0,  3,  8,  7, 0x0),
    gsSP2Triangles( 3,  6,  9, 0x0,  9,  8,  3, 0x0),
    gsSP2Triangles( 1, 10,  4, 0x0, 10, 11,  4, 0x0),
    gsSP2Triangles( 4, 12,  5, 0x0, 13, 12,  4, 0x0),
    gsSP2Triangles(11, 14,  4, 0x0,  4, 14, 13, 0x0),
    gsSPVertex(peach_seg5_vertex_05009A70, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  7,  6,  5, 0x0),
    gsSP2Triangles( 4,  8,  5, 0x0,  5,  9,  7, 0x0),
    gsSP2Triangles(10,  9,  5, 0x0,  5, 11, 10, 0x0),
    gsSP2Triangles( 8, 11,  5, 0x0,  8, 12, 11, 0x0),
    gsSP2Triangles(10, 11, 13, 0x0, 12, 13, 11, 0x0),
    gsSP1Triangle(13, 14, 10, 0x0),
    gsSPVertex(peach_seg5_vertex_05009B60, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  4,  3, 0x0,  5,  7,  3, 0x0),
    gsSP2Triangles( 8,  9,  0, 0x0,  1,  9, 10, 0x0),
    gsSP2Triangles( 9, 11, 10, 0x0,  9,  1,  0, 0x0),
    gsSP2Triangles(11,  9,  8, 0x0, 12, 11,  8, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0,  2,  1, 10, 0x0),
    gsSP2Triangles(13,  2, 10, 0x0,  0,  2, 14, 0x0),
    gsSP2Triangles(14,  2, 13, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(peach_seg5_vertex_05009C60, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  1, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  5,  4,  6, 0x0),
    gsSP2Triangles( 6,  4,  7, 0x0,  5,  0,  4, 0x0),
    gsSP2Triangles( 1,  0,  5, 0x0,  8,  5,  9, 0x0),
    gsSP2Triangles(10,  5,  8, 0x0,  5,  6,  9, 0x0),
    gsSP2Triangles( 9,  6,  7, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(14, 12, 11, 0x0),
    gsSPVertex(peach_seg5_vertex_05009D50, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  5,  4,  6, 0x0),
    gsSP2Triangles( 4,  7,  6, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 4,  9,  7, 0x0,  7,  9,  8, 0x0),
    gsSP2Triangles(10, 11,  3, 0x0,  3, 11,  1, 0x0),
    gsSP2Triangles( 1, 12,  2, 0x0,  2, 12,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500A0A8 - 0x0500A128
const Gfx peach_seg5_dl_0500A0A8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(peach_seg5_dl_05009E20),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsSPEndDisplayList(),
};

#ifndef VERSION_JP
// 0x0500A188 - 0x0500A210
const Gfx peach_seg5_us_dl_0500A188[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(peach_seg5_dl_05009E20),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetRenderMode(G_RM_AA_ZB_XLU_SURF, G_RM_NOOP2),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsSPEndDisplayList(),
};
#endif
