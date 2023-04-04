// Hoot

// 0x05000900
static const Lights1 hoot_seg5_lights_05000900 = gdSPDefLights1(
    0x30, 0x1b, 0x0f,
    0xc0, 0x6e, 0x3d, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 hoot_lights_unused1 = gdSPDefLights1(
    0x37, 0x27, 0x0b,
    0xdd, 0x9d, 0x2d, 0x28, 0x28, 0x28
);

// 0x05000930
static const Lights1 hoot_seg5_lights_05000930 = gdSPDefLights1(
    0x36, 0x26, 0x11,
    0xdb, 0x99, 0x46, 0x28, 0x28, 0x28
);

// 0x05000948
static const Lights1 hoot_seg5_lights_05000948 = gdSPDefLights1(
    0x06, 0x06, 0x06,
    0x19, 0x19, 0x19, 0x28, 0x28, 0x28
);

// 0x05000960
static const Lights1 hoot_seg5_lights_05000960 = gdSPDefLights1(
    0x1d, 0x0a, 0x05,
    0x77, 0x2a, 0x16, 0x28, 0x28, 0x28
);

// 0x05000978
static const Lights1 hoot_seg5_lights_05000978 = gdSPDefLights1(
    0x06, 0x06, 0x06,
    0x19, 0x19, 0x19, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 hoot_lights_unused2 = gdSPDefLights1(
    0x39, 0x27, 0x0a,
    0xe6, 0x9d, 0x29, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 hoot_lights_unused3 = gdSPDefLights1(
    0x1d, 0x06, 0x3b,
    0x75, 0x18, 0xef, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 hoot_lights_unused4 = gdSPDefLights1(
    0x3a, 0x29, 0x09,
    0xeb, 0xa6, 0x27, 0x28, 0x28, 0x28
);

// 0x050009D8
static const Lights1 hoot_seg5_lights_050009D8 = gdSPDefLights1(
    0x18, 0x07, 0x03,
    0x63, 0x1e, 0x0f, 0x28, 0x28, 0x28
);

// 0x050009F0
static const Lights1 hoot_seg5_lights_050009F0 = gdSPDefLights1(
    0x3f, 0x3a, 0x09,
    0xfe, 0xea, 0x26, 0x28, 0x28, 0x28
);

// 0x05000A08
static const Lights1 hoot_seg5_lights_05000A08 = gdSPDefLights1(
    0x13, 0x0d, 0x0b,
    0x4d, 0x35, 0x2e, 0x28, 0x28, 0x28
);

// 0x05000A20
ALIGNED8 static const Texture hoot_seg5_texture_05000A20[] = {
#include "actors/hoot/hoot_eyes.rgba16.inc.c"
};

// 0x05001220
static const Vtx hoot_seg5_vertex_05001220[] = {
    {{{     8,     86,     22}, 0, {  1292,   1620}, {0xc8, 0x5e, 0x3f, 0x00}}},
    {{{     1,     77,     65}, 0, {  1984,   1676}, {0xb2, 0x4d, 0x3e, 0x00}}},
    {{{    89,     76,     57}, 0, {  1892,    256}, {0x0f, 0x6e, 0x3b, 0x00}}},
    {{{    89,     76,    -56}, 0, {    72,    280}, {0x19, 0x68, 0xbd, 0x00}}},
    {{{    40,     94,      0}, 0, {   956,   1164}, {0x37, 0x72, 0x00, 0x00}}},
    {{{    89,     76,     57}, 0, {  1892,    256}, {0x0f, 0x6e, 0x3b, 0x00}}},
    {{{     8,     86,    -21}, 0, {   596,   1628}, {0xd7, 0x5b, 0xb3, 0x00}}},
    {{{    40,     94,      0}, 0, {   956,   1164}, {0x37, 0x72, 0x00, 0x00}}},
    {{{    89,     76,    -56}, 0, {    72,    280}, {0x19, 0x68, 0xbd, 0x00}}},
    {{{    89,     76,    -56}, 0, {    72,    280}, {0x19, 0x68, 0xbd, 0x00}}},
    {{{     1,     77,    -64}, 0, {  -100,   1704}, {0xa1, 0x46, 0xd4, 0x00}}},
    {{{     8,     86,    -21}, 0, {   596,   1628}, {0xd7, 0x5b, 0xb3, 0x00}}},
    {{{     8,     86,     22}, 0, {  1292,   1620}, {0xc8, 0x5e, 0x3f, 0x00}}},
    {{{    89,     76,     57}, 0, {  1892,    256}, {0x0f, 0x6e, 0x3b, 0x00}}},
    {{{    40,     94,      0}, 0, {   956,   1164}, {0x37, 0x72, 0x00, 0x00}}},
};

// 0x05001310
static const Vtx hoot_seg5_vertex_05001310[] = {
    {{{    39,    120,      0}, 0, {     0,      0}, {0x49, 0x67, 0xfe, 0x00}}},
    {{{   -10,    111,      0}, 0, {     0,      0}, {0x94, 0x41, 0xff, 0x00}}},
    {{{     8,     86,     22}, 0, {     0,      0}, {0xc8, 0x5e, 0x3f, 0x00}}},
    {{{     8,     86,     22}, 0, {     0,      0}, {0xc8, 0x5e, 0x3f, 0x00}}},
    {{{    40,     94,      0}, 0, {     0,      0}, {0x37, 0x72, 0x00, 0x00}}},
    {{{    39,    120,      0}, 0, {     0,      0}, {0x49, 0x67, 0xfe, 0x00}}},
    {{{     8,     86,    -21}, 0, {     0,      0}, {0xd7, 0x5b, 0xb3, 0x00}}},
    {{{    39,    120,      0}, 0, {     0,      0}, {0x49, 0x67, 0xfe, 0x00}}},
    {{{    40,     94,      0}, 0, {     0,      0}, {0x37, 0x72, 0x00, 0x00}}},
    {{{     8,     86,    -21}, 0, {     0,      0}, {0xd7, 0x5b, 0xb3, 0x00}}},
    {{{   -10,    111,      0}, 0, {     0,      0}, {0x94, 0x41, 0xff, 0x00}}},
    {{{    39,    120,      0}, 0, {     0,      0}, {0x49, 0x67, 0xfe, 0x00}}},
};

// 0x050013D0
static const Vtx hoot_seg5_vertex_050013D0[] = {
    {{{     8,     86,    -21}, 0, {     0,      0}, {0xd7, 0x5b, 0xb3, 0x00}}},
    {{{     8,     86,     22}, 0, {     0,      0}, {0xc8, 0x5e, 0x3f, 0x00}}},
    {{{   -10,    111,      0}, 0, {     0,      0}, {0x94, 0x41, 0xff, 0x00}}},
};

// 0x05001400
static const Vtx hoot_seg5_vertex_05001400[] = {
    {{{    -8,    -82,     44}, 0, {     0,      0}, {0x8d, 0xcf, 0x0e, 0x00}}},
    {{{    -8,    -82,    -43}, 0, {     0,      0}, {0xa4, 0xab, 0xf1, 0x00}}},
    {{{    69,    -83,     35}, 0, {     0,      0}, {0x26, 0x8c, 0x20, 0x00}}},
    {{{    -8,    -82,    -43}, 0, {     0,      0}, {0xa4, 0xab, 0xf1, 0x00}}},
    {{{    69,    -83,    -34}, 0, {     0,      0}, {0x25, 0x90, 0xd3, 0x00}}},
    {{{    69,    -83,     35}, 0, {     0,      0}, {0x26, 0x8c, 0x20, 0x00}}},
    {{{     1,     77,    -64}, 0, {     0,      0}, {0xa1, 0x46, 0xd4, 0x00}}},
    {{{    -8,    -82,    -43}, 0, {     0,      0}, {0xa4, 0xab, 0xf1, 0x00}}},
    {{{    -8,    -82,     44}, 0, {     0,      0}, {0x8d, 0xcf, 0x0e, 0x00}}},
    {{{    69,    -83,    -34}, 0, {     0,      0}, {0x25, 0x90, 0xd3, 0x00}}},
    {{{    -8,    -82,    -43}, 0, {     0,      0}, {0xa4, 0xab, 0xf1, 0x00}}},
    {{{    -3,    -55,    -96}, 0, {     0,      0}, {0xf8, 0xc9, 0x8e, 0x00}}},
    {{{    -3,     33,    -87}, 0, {     0,      0}, {0xb0, 0x1e, 0xa3, 0x00}}},
    {{{    -3,    -55,    -96}, 0, {     0,      0}, {0xf8, 0xc9, 0x8e, 0x00}}},
    {{{    -8,    -82,    -43}, 0, {     0,      0}, {0xa4, 0xab, 0xf1, 0x00}}},
};

// 0x050014F0
static const Vtx hoot_seg5_vertex_050014F0[] = {
    {{{    -8,    -82,    -43}, 0, {     0,      0}, {0xa4, 0xab, 0xf1, 0x00}}},
    {{{     1,     77,    -64}, 0, {     0,      0}, {0xa1, 0x46, 0xd4, 0x00}}},
    {{{    -3,     33,    -87}, 0, {     0,      0}, {0xb0, 0x1e, 0xa3, 0x00}}},
    {{{   122,    -45,    -35}, 0, {     0,      0}, {0x66, 0xbb, 0xe2, 0x00}}},
    {{{    69,    -83,    -34}, 0, {     0,      0}, {0x25, 0x90, 0xd3, 0x00}}},
    {{{    81,    -44,    -72}, 0, {     0,      0}, {0x3b, 0xd3, 0x9a, 0x00}}},
    {{{    81,    -44,    -72}, 0, {     0,      0}, {0x3b, 0xd3, 0x9a, 0x00}}},
    {{{    69,    -83,    -34}, 0, {     0,      0}, {0x25, 0x90, 0xd3, 0x00}}},
    {{{    -3,    -55,    -96}, 0, {     0,      0}, {0xf8, 0xc9, 0x8e, 0x00}}},
    {{{    69,    -83,     35}, 0, {     0,      0}, {0x26, 0x8c, 0x20, 0x00}}},
    {{{    69,    -83,    -34}, 0, {     0,      0}, {0x25, 0x90, 0xd3, 0x00}}},
    {{{   122,    -45,    -35}, 0, {     0,      0}, {0x66, 0xbb, 0xe2, 0x00}}},
    {{{    -3,    -55,     97}, 0, {     0,      0}, {0xf8, 0xc9, 0x72, 0x00}}},
    {{{    69,    -83,     35}, 0, {     0,      0}, {0x26, 0x8c, 0x20, 0x00}}},
    {{{    81,    -44,     73}, 0, {     0,      0}, {0x3b, 0xd3, 0x66, 0x00}}},
};

// 0x050015E0
static const Vtx hoot_seg5_vertex_050015E0[] = {
    {{{    81,    -44,     73}, 0, {     0,      0}, {0x3b, 0xd3, 0x66, 0x00}}},
    {{{    69,    -83,     35}, 0, {     0,      0}, {0x26, 0x8c, 0x20, 0x00}}},
    {{{   122,    -45,     36}, 0, {     0,      0}, {0x60, 0xbd, 0x2d, 0x00}}},
    {{{   122,    -45,     36}, 0, {     0,      0}, {0x60, 0xbd, 0x2d, 0x00}}},
    {{{    69,    -83,     35}, 0, {     0,      0}, {0x26, 0x8c, 0x20, 0x00}}},
    {{{   122,    -45,    -35}, 0, {     0,      0}, {0x66, 0xbb, 0xe2, 0x00}}},
    {{{    -3,    -55,     97}, 0, {     0,      0}, {0xf8, 0xc9, 0x72, 0x00}}},
    {{{    -8,    -82,     44}, 0, {     0,      0}, {0x8d, 0xcf, 0x0e, 0x00}}},
    {{{    69,    -83,     35}, 0, {     0,      0}, {0x26, 0x8c, 0x20, 0x00}}},
    {{{   135,     24,    -43}, 0, {     0,      0}, {0x65, 0x05, 0xb5, 0x00}}},
    {{{   131,     93,    -14}, 0, {     0,      0}, {0x4c, 0x60, 0xe2, 0x00}}},
    {{{   135,     24,     44}, 0, {     0,      0}, {0x71, 0x01, 0x39, 0x00}}},
    {{{   135,     24,    -43}, 0, {     0,      0}, {0x65, 0x05, 0xb5, 0x00}}},
    {{{   135,     24,     44}, 0, {     0,      0}, {0x71, 0x01, 0x39, 0x00}}},
    {{{   122,    -45,    -35}, 0, {     0,      0}, {0x66, 0xbb, 0xe2, 0x00}}},
};

// 0x050016D0
static const Vtx hoot_seg5_vertex_050016D0[] = {
    {{{   135,     24,     44}, 0, {     0,      0}, {0x71, 0x01, 0x39, 0x00}}},
    {{{   122,    -45,     36}, 0, {     0,      0}, {0x60, 0xbd, 0x2d, 0x00}}},
    {{{   122,    -45,    -35}, 0, {     0,      0}, {0x66, 0xbb, 0xe2, 0x00}}},
    {{{    81,    -44,     73}, 0, {     0,      0}, {0x3b, 0xd3, 0x66, 0x00}}},
    {{{   122,    -45,     36}, 0, {     0,      0}, {0x60, 0xbd, 0x2d, 0x00}}},
    {{{   135,     24,     44}, 0, {     0,      0}, {0x71, 0x01, 0x39, 0x00}}},
    {{{   135,     24,    -43}, 0, {     0,      0}, {0x65, 0x05, 0xb5, 0x00}}},
    {{{   122,    -45,    -35}, 0, {     0,      0}, {0x66, 0xbb, 0xe2, 0x00}}},
    {{{    81,    -44,    -72}, 0, {     0,      0}, {0x3b, 0xd3, 0x9a, 0x00}}},
    {{{   131,     93,     15}, 0, {     0,      0}, {0x4f, 0x56, 0x2f, 0x00}}},
    {{{    89,     76,     57}, 0, {     0,      0}, {0x0f, 0x6e, 0x3b, 0x00}}},
    {{{   135,     24,     44}, 0, {     0,      0}, {0x71, 0x01, 0x39, 0x00}}},
    {{{    81,    -44,     73}, 0, {     0,      0}, {0x3b, 0xd3, 0x66, 0x00}}},
    {{{   135,     24,     44}, 0, {     0,      0}, {0x71, 0x01, 0x39, 0x00}}},
    {{{    86,     27,     73}, 0, {     0,      0}, {0x25, 0x16, 0x77, 0x00}}},
};

// 0x050017C0
static const Vtx hoot_seg5_vertex_050017C0[] = {
    {{{    86,     27,     73}, 0, {     0,      0}, {0x25, 0x16, 0x77, 0x00}}},
    {{{     1,     77,     65}, 0, {     0,      0}, {0xb2, 0x4d, 0x3e, 0x00}}},
    {{{    -3,     33,     88}, 0, {     0,      0}, {0xb0, 0x1e, 0x5d, 0x00}}},
    {{{    -3,     33,     88}, 0, {     0,      0}, {0xb0, 0x1e, 0x5d, 0x00}}},
    {{{    -3,    -55,     97}, 0, {     0,      0}, {0xf8, 0xc9, 0x72, 0x00}}},
    {{{    86,     27,     73}, 0, {     0,      0}, {0x25, 0x16, 0x77, 0x00}}},
    {{{   135,     24,     44}, 0, {     0,      0}, {0x71, 0x01, 0x39, 0x00}}},
    {{{    89,     76,     57}, 0, {     0,      0}, {0x0f, 0x6e, 0x3b, 0x00}}},
    {{{    86,     27,     73}, 0, {     0,      0}, {0x25, 0x16, 0x77, 0x00}}},
    {{{    -3,    -55,     97}, 0, {     0,      0}, {0xf8, 0xc9, 0x72, 0x00}}},
    {{{    81,    -44,     73}, 0, {     0,      0}, {0x3b, 0xd3, 0x66, 0x00}}},
    {{{    86,     27,     73}, 0, {     0,      0}, {0x25, 0x16, 0x77, 0x00}}},
    {{{    86,     27,     73}, 0, {     0,      0}, {0x25, 0x16, 0x77, 0x00}}},
    {{{    89,     76,     57}, 0, {     0,      0}, {0x0f, 0x6e, 0x3b, 0x00}}},
    {{{     1,     77,     65}, 0, {     0,      0}, {0xb2, 0x4d, 0x3e, 0x00}}},
};

// 0x050018B0
static const Vtx hoot_seg5_vertex_050018B0[] = {
    {{{    89,     76,     57}, 0, {     0,      0}, {0x0f, 0x6e, 0x3b, 0x00}}},
    {{{   131,     93,    -14}, 0, {     0,      0}, {0x4c, 0x60, 0xe2, 0x00}}},
    {{{    89,     76,    -56}, 0, {     0,      0}, {0x19, 0x68, 0xbd, 0x00}}},
    {{{    89,     76,     57}, 0, {     0,      0}, {0x0f, 0x6e, 0x3b, 0x00}}},
    {{{   131,     93,     15}, 0, {     0,      0}, {0x4f, 0x56, 0x2f, 0x00}}},
    {{{   131,     93,    -14}, 0, {     0,      0}, {0x4c, 0x60, 0xe2, 0x00}}},
    {{{     1,     77,     65}, 0, {     0,      0}, {0xb2, 0x4d, 0x3e, 0x00}}},
    {{{     8,     86,     22}, 0, {     0,      0}, {0xc8, 0x5e, 0x3f, 0x00}}},
    {{{     1,     77,    -64}, 0, {     0,      0}, {0xa1, 0x46, 0xd4, 0x00}}},
    {{{     1,     77,     65}, 0, {     0,      0}, {0xb2, 0x4d, 0x3e, 0x00}}},
    {{{     1,     77,    -64}, 0, {     0,      0}, {0xa1, 0x46, 0xd4, 0x00}}},
    {{{    -8,    -82,     44}, 0, {     0,      0}, {0x8d, 0xcf, 0x0e, 0x00}}},
    {{{    -3,     33,     88}, 0, {     0,      0}, {0xb0, 0x1e, 0x5d, 0x00}}},
    {{{     1,     77,     65}, 0, {     0,      0}, {0xb2, 0x4d, 0x3e, 0x00}}},
    {{{    -8,    -82,     44}, 0, {     0,      0}, {0x8d, 0xcf, 0x0e, 0x00}}},
};

// 0x050019A0
static const Vtx hoot_seg5_vertex_050019A0[] = {
    {{{   131,     93,    -14}, 0, {     0,      0}, {0x4c, 0x60, 0xe2, 0x00}}},
    {{{   131,     93,     15}, 0, {     0,      0}, {0x4f, 0x56, 0x2f, 0x00}}},
    {{{   135,     24,     44}, 0, {     0,      0}, {0x71, 0x01, 0x39, 0x00}}},
    {{{    -8,    -82,     44}, 0, {     0,      0}, {0x8d, 0xcf, 0x0e, 0x00}}},
    {{{    -3,    -55,     97}, 0, {     0,      0}, {0xf8, 0xc9, 0x72, 0x00}}},
    {{{    -3,     33,     88}, 0, {     0,      0}, {0xb0, 0x1e, 0x5d, 0x00}}},
    {{{   135,     24,    -43}, 0, {     0,      0}, {0x65, 0x05, 0xb5, 0x00}}},
    {{{    89,     76,    -56}, 0, {     0,      0}, {0x19, 0x68, 0xbd, 0x00}}},
    {{{   131,     93,    -14}, 0, {     0,      0}, {0x4c, 0x60, 0xe2, 0x00}}},
    {{{    -3,     33,    -87}, 0, {     0,      0}, {0xb0, 0x1e, 0xa3, 0x00}}},
    {{{     1,     77,    -64}, 0, {     0,      0}, {0xa1, 0x46, 0xd4, 0x00}}},
    {{{    86,     27,    -72}, 0, {     0,      0}, {0x25, 0x16, 0x89, 0x00}}},
    {{{     8,     86,     22}, 0, {     0,      0}, {0xc8, 0x5e, 0x3f, 0x00}}},
    {{{     8,     86,    -21}, 0, {     0,      0}, {0xd7, 0x5b, 0xb3, 0x00}}},
    {{{     1,     77,    -64}, 0, {     0,      0}, {0xa1, 0x46, 0xd4, 0x00}}},
};

// 0x05001A90
static const Vtx hoot_seg5_vertex_05001A90[] = {
    {{{     1,     77,    -64}, 0, {     0,      0}, {0xa1, 0x46, 0xd4, 0x00}}},
    {{{    89,     76,    -56}, 0, {     0,      0}, {0x19, 0x68, 0xbd, 0x00}}},
    {{{    86,     27,    -72}, 0, {     0,      0}, {0x25, 0x16, 0x89, 0x00}}},
    {{{    86,     27,    -72}, 0, {     0,      0}, {0x25, 0x16, 0x89, 0x00}}},
    {{{    89,     76,    -56}, 0, {     0,      0}, {0x19, 0x68, 0xbd, 0x00}}},
    {{{   135,     24,    -43}, 0, {     0,      0}, {0x65, 0x05, 0xb5, 0x00}}},
    {{{    -3,    -55,    -96}, 0, {     0,      0}, {0xf8, 0xc9, 0x8e, 0x00}}},
    {{{    86,     27,    -72}, 0, {     0,      0}, {0x25, 0x16, 0x89, 0x00}}},
    {{{    81,    -44,    -72}, 0, {     0,      0}, {0x3b, 0xd3, 0x9a, 0x00}}},
    {{{    86,     27,    -72}, 0, {     0,      0}, {0x25, 0x16, 0x89, 0x00}}},
    {{{   135,     24,    -43}, 0, {     0,      0}, {0x65, 0x05, 0xb5, 0x00}}},
    {{{    81,    -44,    -72}, 0, {     0,      0}, {0x3b, 0xd3, 0x9a, 0x00}}},
    {{{    -3,    -55,    -96}, 0, {     0,      0}, {0xf8, 0xc9, 0x8e, 0x00}}},
    {{{    -3,     33,    -87}, 0, {     0,      0}, {0xb0, 0x1e, 0xa3, 0x00}}},
    {{{    86,     27,    -72}, 0, {     0,      0}, {0x25, 0x16, 0x89, 0x00}}},
};

// 0x05001B80 - 0x05001C00
const Gfx hoot_seg5_dl_05001B80[] = {
    gsDPLoadTextureBlock(hoot_seg5_texture_05000A20, G_IM_FMT_RGBA, G_IM_SIZ_16b, 32, 32, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_WRAP | G_TX_NOMIRROR, 5, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPLight(&hoot_seg5_lights_050009D8.l, 1),
    gsSPLight(&hoot_seg5_lights_050009D8.a, 2),
    gsSPVertex(hoot_seg5_vertex_05001220, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x05001C00 - 0x05001DF0
const Gfx hoot_seg5_dl_05001C00[] = {
    gsSPLight(&hoot_seg5_lights_050009F0.l, 1),
    gsSPLight(&hoot_seg5_lights_050009F0.a, 2),
    gsSPVertex(hoot_seg5_vertex_05001310, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPLight(&hoot_seg5_lights_05000A08.l, 1),
    gsSPLight(&hoot_seg5_lights_05000A08.a, 2),
    gsSPVertex(hoot_seg5_vertex_050013D0, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPLight(&hoot_seg5_lights_050009D8.l, 1),
    gsSPLight(&hoot_seg5_lights_050009D8.a, 2),
    gsSPVertex(hoot_seg5_vertex_05001400, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(hoot_seg5_vertex_050014F0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(hoot_seg5_vertex_050015E0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(hoot_seg5_vertex_050016D0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(hoot_seg5_vertex_050017C0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(hoot_seg5_vertex_050018B0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(hoot_seg5_vertex_050019A0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(hoot_seg5_vertex_05001A90, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x05001DF0 - 0x05001E38
const Gfx hoot_seg5_dl_05001DF0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBA, G_CC_BLENDRGBA),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsSPDisplayList(hoot_seg5_dl_05001B80),
    gsSPTexture(0x8000, 0x8000, 1, G_TX_RENDERTILE + 1, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(hoot_seg5_dl_05001C00),
    gsSPEndDisplayList(),
};

// 0x05001E38
static const Lights1 hoot_seg5_lights_05001E38 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05001E50
ALIGNED8 static const Texture hoot_seg5_texture_05001E50[] = {
#include "actors/hoot/hoot_wing.rgba16.inc.c"
};

// 0x05002650
ALIGNED8 static const Texture hoot_seg5_texture_05002650[] = {
#include "actors/hoot/hoot_wing_tip.rgba16.inc.c"
};

// //! The vertex macro which calls this has too large of a size.
// 0x05002E50
static const Vtx hoot_seg5_vertex_05002E50[] = {
    {{{   126,      0,    -62}, 0, {  1780,    228}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,      0,    -87}, 0, {   296,    -56}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   168,      0,    125}, 0, {  2288,   2352}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,      0,    -87}, 0, {   296,    -56}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,      0,     78}, 0, {   304,   1824}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   168,      0,    125}, 0, {  2288,   2352}, {0x00, 0x7f, 0x00, 0x00}}},
};

// vertex   -752,      0,   1280,  -2800,      0,  0x07, 0x00, 0x00, 0x00
// vertex  -6656,      0,      0,  -3328,      0,  0x07, 0x3F, 0xF1, 0x00
// vertex  -2800,   4096,      0,  -3584,      0,  0x00, 0x07, 0xC0, 0x7C
// vertex    902,     16,   1280,    904,     16,  0x05, 0x00, 0x1E, 0x38
// vertex   1200,    192,   1280, -16640,      0,  0x00, 0x00, 0x0A, 0x14
// vertex -16640,      0,     30, -18432,      0,  0x00, 0x00, 0x00, 0x00

// 0x05002EB0 - 0x05002F10
const Gfx hoot_seg5_dl_05002EB0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, hoot_seg5_texture_05002650),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPLight(&hoot_seg5_lights_05001E38.l, 1),
    gsSPLight(&hoot_seg5_lights_05001E38.a, 2),
    gsSPVertex(hoot_seg5_vertex_05002E50, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x05002F10 - 0x05002F60
const Gfx hoot_seg5_dl_05002F10[] = {
    gsDPPipeSync(),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsSPDisplayList(hoot_seg5_dl_05002EB0),
    gsSPTexture(0x8000, 0x8000, 1, G_TX_RENDERTILE + 1, G_OFF),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x05002F60
static const Lights1 hoot_seg5_lights_05002F60 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// //! The vertex macro which calls this has too large of a size.
// 0x05002F78
static const Vtx hoot_seg5_vertex_05002F78[] = {
    {{{   119,      0,    -85}, 0, {  1972,     68}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,      0,     96}, 0, {   964,   2040}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   119,      0,     77}, 0, {  1976,   1840}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   119,      0,    -85}, 0, {  1972,     68}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,      0,    -47}, 0, {   960,    484}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,      0,     96}, 0, {   964,   2040}, {0x00, 0x7f, 0x00, 0x00}}},
};

// vertex   -752,      0,   1280,  -2800,      0,  0x07, 0x00, 0x00, 0x00
// vertex  -6656,      0,      0,  -3328,      0,  0x07, 0x3F, 0xF1, 0x00
// vertex  -2800,   4096,      0,  -3584,      0,  0x00, 0x07, 0xC0, 0x7C
// vertex    902,     16,   1280,    904,     16,  0x05, 0x00, 0x2F, 0x60
// vertex   1200,    192,   1280, -16640,      0,  0x00, 0x00, 0x0A, 0x14
// vertex -16640,      0,     30, -18432,      0,  0x00, 0x00, 0x00, 0x00

// 0x05002FD8 - 0x05003038
const Gfx hoot_seg5_dl_05002FD8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, hoot_seg5_texture_05001E50),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPLight(&hoot_seg5_lights_05002F60.l, 1),
    gsSPLight(&hoot_seg5_lights_05002F60.a, 2),
    gsSPVertex(hoot_seg5_vertex_05002F78, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003038 - 0x05003088
const Gfx hoot_seg5_dl_05003038[] = {
    gsDPPipeSync(),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsSPDisplayList(hoot_seg5_dl_05002FD8),
    gsSPTexture(0x8000, 0x8000, 1, G_TX_RENDERTILE + 1, G_OFF),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x05003088
static const Lights1 hoot_seg5_lights_05003088 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// //! The vertex macro which calls this has too large of a size.
// 0x050030A0
static const Vtx hoot_seg5_vertex_050030A0[] = {
    {{{    89,      1,    -46}, 0, {   928,    456}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    37,      1,    -66}, 0, {   352,    216}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    89,      1,     95}, 0, {   924,   2076}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    37,      1,    -66}, 0, {   352,    216}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    37,      1,     29}, 0, {   348,   1324}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    89,      1,     95}, 0, {   924,   2076}, {0x00, 0x7f, 0x00, 0x00}}},
};

// vertex   -752,      0,   1280,  -2800,      0,  0x07, 0x00, 0x00, 0x00
// vertex  -6656,      0,      0,  -3328,      0,  0x07, 0x3F, 0xF1, 0x00
// vertex  -2800,   4096,      0,  -3584,      0,  0x00, 0x07, 0xC0, 0x7C
// vertex    902,     16,   1280,    904,     16,  0x05, 0x00, 0x30, 0x88
// vertex   1200,    192,   1280, -16640,      0,  0x00, 0x00, 0x0A, 0x14
// vertex -16640,      0,     30, -18432,      0,  0x00, 0x00, 0x00, 0x00

// 0x05003100 - 0x05003160
const Gfx hoot_seg5_dl_05003100[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, hoot_seg5_texture_05001E50),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPLight(&hoot_seg5_lights_05003088.l, 1),
    gsSPLight(&hoot_seg5_lights_05003088.a, 2),
    gsSPVertex(hoot_seg5_vertex_050030A0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003160 - 0x050031B0
const Gfx hoot_seg5_dl_05003160[] = {
    gsDPPipeSync(),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsSPDisplayList(hoot_seg5_dl_05003100),
    gsSPTexture(0x8000, 0x8000, 1, G_TX_RENDERTILE + 1, G_OFF),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x050031B0
static const Lights1 hoot_seg5_lights_050031B0 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// //! The vertex macro which calls this has too large of a size.
// 0x050031C8
static const Vtx hoot_seg5_vertex_050031C8[] = {
    {{{    37,      1,    -66}, 0, {   380,    184}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     2,      1,    -39}, 0, {     0,    528}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    37,      1,     28}, 0, {   400,   1356}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     2,      1,    -39}, 0, {     0,    528}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     1,      1,     72}, 0, {   -56,   1904}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    37,      1,     28}, 0, {   400,   1356}, {0x00, 0x7f, 0x00, 0x00}}},
};

// vertex   -752,      0,   1280,  -2800,      0,  0x07, 0x00, 0x00, 0x00
// vertex  -6656,      0,      0,  -3328,      0,  0x07, 0x3F, 0xF1, 0x00
// vertex  -2800,   4096,      0,  -3584,      0,  0x00, 0x07, 0xC0, 0x7C
// vertex    902,     16,   1280,    904,     16,  0x05, 0x00, 0x31, 0xB0
// vertex   1200,    192,   1280, -16640,      0,  0x00, 0x00, 0x0A, 0x14
// vertex -16640,      0,     30, -18432,      0,  0x00, 0x00, 0x00, 0x00

// 0x05003228 - 0x05003288
const Gfx hoot_seg5_dl_05003228[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, hoot_seg5_texture_05001E50),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPLight(&hoot_seg5_lights_050031B0.l, 1),
    gsSPLight(&hoot_seg5_lights_050031B0.a, 2),
    gsSPVertex(hoot_seg5_vertex_050031C8, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003288 - 0x050032D8
const Gfx hoot_seg5_dl_05003288[] = {
    gsDPPipeSync(),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsSPDisplayList(hoot_seg5_dl_05003228),
    gsSPTexture(0x8000, 0x8000, 1, G_TX_RENDERTILE + 1, G_OFF),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x050032D8
static const Lights1 hoot_seg5_lights_050032D8 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// //! The vertex macro which calls this has too large of a size.
// 0x050032F0
static const Vtx hoot_seg5_vertex_050032F0[] = {
    {{{   168,      0,   -124}, 0, {  2340,   2408}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,      0,     88}, 0, {   424,     -4}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   126,      0,     63}, 0, {  1920,    276}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   168,      0,   -124}, 0, {  2340,   2408}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,      0,    -77}, 0, {   348,   1884}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,      0,     88}, 0, {   424,     -4}, {0x00, 0x7f, 0x00, 0x00}}},
};

// vertex   -752,      0,   1280,  -2800,      0,  0x07, 0x00, 0x00, 0x00
// vertex  -6656,      0,      0,  -3328,      0,  0x07, 0x3F, 0xF1, 0x00
// vertex  -2800,   4096,      0,  -3584,      0,  0x00, 0x07, 0xC0, 0x7C
// vertex    902,     16,   1280,    904,     16,  0x05, 0x00, 0x32, 0xD8
// vertex   1200,    192,   1280, -16640,      0,  0x00, 0x00, 0x0A, 0x14
// vertex -16640,      0,     30, -18432,      0,  0x00, 0x00, 0x00, 0x00

// 0x05003350 - 0x050033B0
const Gfx hoot_seg5_dl_05003350[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, hoot_seg5_texture_05002650),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPLight(&hoot_seg5_lights_050032D8.l, 1),
    gsSPLight(&hoot_seg5_lights_050032D8.a, 2),
    gsSPVertex(hoot_seg5_vertex_050032F0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x050033B0 - 0x05003400
const Gfx hoot_seg5_dl_050033B0[] = {
    gsDPPipeSync(),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsSPDisplayList(hoot_seg5_dl_05003350),
    gsSPTexture(0x8000, 0x8000, 1, G_TX_RENDERTILE + 1, G_OFF),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x05003400
static const Lights1 hoot_seg5_lights_05003400 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// //! The vertex macro which calls this has too large of a size.
// 0x05003418
static const Vtx hoot_seg5_vertex_05003418[] = {
    {{{   119,      0,    -76}, 0, {  1988,   1892}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,      0,    -95}, 0, {  1000,   2096}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   119,      0,     86}, 0, {  1984,     36}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,      0,    -95}, 0, {  1000,   2096}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,      0,     48}, 0, {  1000,    468}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   119,      0,     86}, 0, {  1984,     36}, {0x00, 0x7f, 0x00, 0x00}}},
};

// vertex   -752,      0,   1280,  -2800,      0,  0x07, 0x00, 0x00, 0x00
// vertex  -6656,      0,      0,  -3328,      0,  0x07, 0x3F, 0xF1, 0x00
// vertex  -2800,   4096,      0,  -3584,      0,  0x00, 0x07, 0xC0, 0x7C
// vertex    902,     16,   1280,    904,     16,  0x05, 0x00, 0x34, 0x00
// vertex   1200,    192,   1280, -16640,      0,  0x00, 0x00, 0x0A, 0x14
// vertex -16640,      0,     30, -18432,      0,  0x00, 0x00, 0x00, 0x00

// 0x05003478 - 0x050034D8
const Gfx hoot_seg5_dl_05003478[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, hoot_seg5_texture_05001E50),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPLight(&hoot_seg5_lights_05003400.l, 1),
    gsSPLight(&hoot_seg5_lights_05003400.a, 2),
    gsSPVertex(hoot_seg5_vertex_05003418, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x050034D8 - 0x05003528
const Gfx hoot_seg5_dl_050034D8[] = {
    gsDPPipeSync(),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsSPDisplayList(hoot_seg5_dl_05003478),
    gsSPTexture(0x8000, 0x8000, 1, G_TX_RENDERTILE + 1, G_OFF),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x05003528
static const Lights1 hoot_seg5_lights_05003528 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// //! The vertex macro which calls this has too large of a size.
// 0x05003540
static const Vtx hoot_seg5_vertex_05003540[] = {
    {{{    89,      1,    -94}, 0, {   984,   2096}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    37,      1,     67}, 0, {   368,    192}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    89,      1,     47}, 0, {   992,    436}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    89,      1,    -94}, 0, {   984,   2096}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    37,      1,    -28}, 0, {   360,   1324}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    37,      1,     67}, 0, {   368,    192}, {0x00, 0x7f, 0x00, 0x00}}},
};

// vertex   -752,      0,   1280,  -2800,      0,  0x07, 0x00, 0x00, 0x00
// vertex  -6656,      0,      0,  -3328,      0,  0x07, 0x3F, 0xF1, 0x00
// vertex  -2800,   4096,      0,  -3584,      0,  0x00, 0x07, 0xC0, 0x7C
// vertex    902,     16,   1280,    904,     16,  0x05, 0x00, 0x35, 0x28
// vertex   1200,    192,   1280, -16640,      0,  0x00, 0x00, 0x0A, 0x14
// vertex -16640,      0,     30, -18432,      0,  0x00, 0x00, 0x00, 0x00

// 0x050035A0 - 0x05003600
const Gfx hoot_seg5_dl_050035A0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, hoot_seg5_texture_05001E50),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPLight(&hoot_seg5_lights_05003528.l, 1),
    gsSPLight(&hoot_seg5_lights_05003528.a, 2),
    gsSPVertex(hoot_seg5_vertex_05003540, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003600 - 0x05003650
const Gfx hoot_seg5_dl_05003600[] = {
    gsDPPipeSync(),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsSPDisplayList(hoot_seg5_dl_050035A0),
    gsSPTexture(0x8000, 0x8000, 1, G_TX_RENDERTILE + 1, G_OFF),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x05003650
static const Lights1 hoot_seg5_lights_05003650 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// //! The vertex macro which calls this has too large of a size.
// 0x05003668
static const Vtx hoot_seg5_vertex_05003668[] = {
    {{{    37,      1,    -27}, 0, {   428,   1324}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     2,      1,     40}, 0, {   -24,    512}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    37,      1,     67}, 0, {   432,    180}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    37,      1,    -27}, 0, {   428,   1324}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     1,      1,    -71}, 0, {   -40,   1848}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     2,      1,     40}, 0, {   -24,    512}, {0x00, 0x7f, 0x00, 0x00}}},
};

// vertex   -752,      0,   1280,  -2800,      0,  0x07, 0x00, 0x00, 0x00
// vertex  -6656,      0,      0,  -3328,      0,  0x07, 0x3F, 0xF1, 0x00
// vertex  -2800,   4096,      0,  -3584,      0,  0x00, 0x07, 0xC0, 0x7C
// vertex    902,     16,   1280,    904,     16,  0x05, 0x00, 0x36, 0x50
// vertex   1200,    192,   1280, -16640,      0,  0x00, 0x00, 0x0A, 0x14
// vertex -16640,      0,     30, -18432,      0,  0x00, 0x00, 0x00, 0x00

// 0x050036C8 - 0x05003728
const Gfx hoot_seg5_dl_050036C8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, hoot_seg5_texture_05001E50),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPLight(&hoot_seg5_lights_05003650.l, 1),
    gsSPLight(&hoot_seg5_lights_05003650.a, 2),
    gsSPVertex(hoot_seg5_vertex_05003668, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003728 - 0x05003778
const Gfx hoot_seg5_dl_05003728[] = {
    gsDPPipeSync(),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsSPDisplayList(hoot_seg5_dl_050036C8),
    gsSPTexture(0x8000, 0x8000, 1, G_TX_RENDERTILE + 1, G_OFF),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x05003778
static const Vtx hoot_seg5_vertex_05003778[] = {
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x02, 0xbe, 0x6c, 0x00}}},
    {{{    22,     13,     15}, 0, {     0,      0}, {0x02, 0xbe, 0x6c, 0x00}}},
    {{{   -14,      3,     11}, 0, {     0,      0}, {0x02, 0xbe, 0x6c, 0x00}}},
    {{{    22,     13,    -15}, 0, {     0,      0}, {0x02, 0xbe, 0x94, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x02, 0xbe, 0x94, 0xff}}},
    {{{   -14,      3,    -10}, 0, {     0,      0}, {0x02, 0xbe, 0x94, 0xff}}},
    {{{   -14,      3,    -10}, 0, {     0,      0}, {0x84, 0xea, 0x00, 0xff}}},
    {{{   -10,    -13,      0}, 0, {     0,      0}, {0x84, 0xea, 0x00, 0xff}}},
    {{{   -14,      3,     11}, 0, {     0,      0}, {0x84, 0xea, 0x00, 0xff}}},
    {{{    22,     13,    -15}, 0, {     0,      0}, {0xe1, 0x7b, 0x00, 0xff}}},
    {{{   -14,      3,     11}, 0, {     0,      0}, {0xe1, 0x7b, 0x00, 0xff}}},
    {{{    22,     13,     15}, 0, {     0,      0}, {0xe1, 0x7b, 0x00, 0xff}}},
    {{{    49,    -27,    -24}, 0, {     0,      0}, {0x37, 0x5f, 0xc2, 0xff}}},
    {{{    38,    -32,    -47}, 0, {     0,      0}, {0x2d, 0x6c, 0xd1, 0xff}}},
    {{{    22,     13,    -15}, 0, {     0,      0}, {0x4e, 0x29, 0xa5, 0xff}}},
};

// 0x05003868
static const Vtx hoot_seg5_vertex_05003868[] = {
    {{{    22,     13,    -15}, 0, {     0,      0}, {0x87, 0xe1, 0xf1, 0xff}}},
    {{{    38,    -32,    -47}, 0, {     0,      0}, {0x87, 0xe1, 0xf1, 0x00}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x87, 0xe1, 0xf1, 0x00}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x35, 0xbb, 0x5b, 0xff}}},
    {{{    41,    -47,    -38}, 0, {     0,      0}, {0x18, 0xb1, 0x5f, 0xff}}},
    {{{    49,    -27,    -24}, 0, {     0,      0}, {0x26, 0xb7, 0x60, 0xff}}},
    {{{    49,    -27,     25}, 0, {     0,      0}, {0x26, 0xb7, 0xa0, 0xff}}},
    {{{    41,    -47,     39}, 0, {     0,      0}, {0x18, 0xb1, 0xa1, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x35, 0xbb, 0xa5, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x87, 0xe1, 0x0f, 0xff}}},
    {{{    38,    -32,     48}, 0, {     0,      0}, {0x87, 0xe1, 0x0f, 0xff}}},
    {{{    22,     13,     15}, 0, {     0,      0}, {0x87, 0xe1, 0x0f, 0xff}}},
    {{{    22,     13,     15}, 0, {     0,      0}, {0x4e, 0x29, 0x5b, 0xff}}},
    {{{    38,    -32,     48}, 0, {     0,      0}, {0x2d, 0x6c, 0x2f, 0xff}}},
    {{{    49,    -27,     25}, 0, {     0,      0}, {0x37, 0x5f, 0x3e, 0xff}}},
};

// 0x05003958
static const Vtx hoot_seg5_vertex_05003958[] = {
    {{{    41,     29,      0}, 0, {     0,      0}, {0x33, 0xf6, 0x73, 0xff}}},
    {{{    26,     49,      9}, 0, {     0,      0}, {0x27, 0xea, 0x76, 0x00}}},
    {{{    22,     13,     15}, 0, {     0,      0}, {0x4e, 0x29, 0x5b, 0x00}}},
    {{{    22,     13,     15}, 0, {     0,      0}, {0x82, 0x0e, 0x00, 0xff}}},
    {{{    26,     49,      9}, 0, {     0,      0}, {0x82, 0x0e, 0x00, 0xff}}},
    {{{    22,     13,    -15}, 0, {     0,      0}, {0x82, 0x0e, 0x00, 0xff}}},
    {{{    22,     13,    -15}, 0, {     0,      0}, {0x4e, 0x29, 0xa5, 0xff}}},
    {{{    26,     49,     -8}, 0, {     0,      0}, {0x27, 0xea, 0x8a, 0xff}}},
    {{{    41,     29,      0}, 0, {     0,      0}, {0x33, 0xf6, 0x8d, 0xff}}},
    {{{    45,      8,      0}, 0, {     0,      0}, {0x48, 0x27, 0xa0, 0xff}}},
    {{{    26,     49,     -8}, 0, {     0,      0}, {0x82, 0x0e, 0x00, 0xff}}},
    {{{    45,      8,      0}, 0, {     0,      0}, {0x48, 0x27, 0x60, 0xff}}},
    {{{    49,    -27,     25}, 0, {     0,      0}, {0x37, 0x5f, 0x3e, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x87, 0xe1, 0x0f, 0xff}}},
    {{{    41,    -47,     39}, 0, {     0,      0}, {0x87, 0xe1, 0x0f, 0xff}}},
    {{{    38,    -32,     48}, 0, {     0,      0}, {0x87, 0xe1, 0x0f, 0xff}}},
};

// 0x05003A58
static const Vtx hoot_seg5_vertex_05003A58[] = {
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x35, 0xbb, 0xa5, 0xff}}},
    {{{    45,      8,      0}, 0, {     0,      0}, {0x3f, 0xc7, 0xa3, 0x00}}},
    {{{    49,    -27,     25}, 0, {     0,      0}, {0x26, 0xb7, 0xa0, 0x00}}},
    {{{    49,    -27,    -24}, 0, {     0,      0}, {0x26, 0xb7, 0x60, 0xff}}},
    {{{    45,      8,      0}, 0, {     0,      0}, {0x3f, 0xc7, 0x5d, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x35, 0xbb, 0x5b, 0xff}}},
    {{{    38,    -32,    -47}, 0, {     0,      0}, {0x87, 0xe1, 0xf1, 0xff}}},
    {{{    41,    -47,    -38}, 0, {     0,      0}, {0x87, 0xe1, 0xf1, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x87, 0xe1, 0xf1, 0xff}}},
    {{{    22,     13,    -15}, 0, {     0,      0}, {0x4e, 0x29, 0xa5, 0xff}}},
    {{{    45,      8,      0}, 0, {     0,      0}, {0x48, 0x27, 0xa0, 0xff}}},
    {{{    49,    -27,    -24}, 0, {     0,      0}, {0x37, 0x5f, 0xc2, 0xff}}},
    {{{    22,     13,    -15}, 0, {     0,      0}, {0xe1, 0x7b, 0x00, 0xff}}},
    {{{   -14,      3,    -10}, 0, {     0,      0}, {0xe1, 0x7b, 0x00, 0xff}}},
    {{{   -14,      3,     11}, 0, {     0,      0}, {0xe1, 0x7b, 0x00, 0xff}}},
};

// 0x05003B48
static const Vtx hoot_seg5_vertex_05003B48[] = {
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x02, 0xbe, 0x94, 0xff}}},
    {{{   -10,    -13,      0}, 0, {     0,      0}, {0x02, 0xbe, 0x94, 0x00}}},
    {{{   -14,      3,    -10}, 0, {     0,      0}, {0x02, 0xbe, 0x94, 0x00}}},
    {{{   -14,      3,     11}, 0, {     0,      0}, {0x02, 0xbe, 0x6c, 0xff}}},
    {{{   -10,    -13,      0}, 0, {     0,      0}, {0x02, 0xbe, 0x6c, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x02, 0xbe, 0x6c, 0xff}}},
};

// 0x05003BA8
static const Vtx hoot_seg5_vertex_05003BA8[] = {
    {{{    41,    -47,    -38}, 0, {     0,      0}, {0x18, 0xb1, 0x5f, 0x00}}},
    {{{    79,    -26,    -25}, 0, {     0,      0}, {0x07, 0xb4, 0x64, 0x00}}},
    {{{    49,    -27,    -24}, 0, {     0,      0}, {0x26, 0xb7, 0x60, 0x00}}},
    {{{    49,    -27,    -24}, 0, {     0,      0}, {0x37, 0x5f, 0xc2, 0xff}}},
    {{{    79,    -26,    -25}, 0, {     0,      0}, {0xfc, 0x7b, 0xe4, 0xff}}},
    {{{    38,    -32,    -47}, 0, {     0,      0}, {0x2d, 0x6c, 0xd1, 0xff}}},
    {{{    38,    -32,     48}, 0, {     0,      0}, {0x2d, 0x6c, 0x2f, 0xff}}},
    {{{    79,    -26,     26}, 0, {     0,      0}, {0xfc, 0x7b, 0x1c, 0xff}}},
    {{{    49,    -27,     25}, 0, {     0,      0}, {0x37, 0x5f, 0x3e, 0xff}}},
    {{{    49,    -27,     25}, 0, {     0,      0}, {0x26, 0xb7, 0xa0, 0xff}}},
    {{{    79,    -26,     26}, 0, {     0,      0}, {0x07, 0xb4, 0x9c, 0xff}}},
    {{{    41,    -47,     39}, 0, {     0,      0}, {0x18, 0xb1, 0xa1, 0xff}}},
    {{{    26,     49,     -8}, 0, {     0,      0}, {0x27, 0xea, 0x8a, 0xff}}},
    {{{    63,     30,      0}, 0, {     0,      0}, {0x00, 0xcc, 0x8d, 0xff}}},
    {{{    41,     29,      0}, 0, {     0,      0}, {0x33, 0xf6, 0x8d, 0xff}}},
};

// 0x05003C98
static const Vtx hoot_seg5_vertex_05003C98[] = {
    {{{    41,     29,      0}, 0, {     0,      0}, {0x33, 0xf6, 0x73, 0xff}}},
    {{{    63,     30,      0}, 0, {     0,      0}, {0x00, 0xcc, 0x73, 0x00}}},
    {{{    26,     49,      9}, 0, {     0,      0}, {0x27, 0xea, 0x76, 0x00}}},
    {{{    41,    -47,     39}, 0, {     0,      0}, {0x3d, 0xcf, 0x63, 0xff}}},
    {{{    79,    -26,     26}, 0, {     0,      0}, {0x3d, 0xcf, 0x63, 0xff}}},
    {{{    38,    -32,     48}, 0, {     0,      0}, {0x3d, 0xcf, 0x63, 0xff}}},
    {{{    26,     49,      9}, 0, {     0,      0}, {0x3a, 0x70, 0x00, 0xff}}},
    {{{    63,     30,      0}, 0, {     0,      0}, {0x3a, 0x70, 0x00, 0xff}}},
    {{{    26,     49,     -8}, 0, {     0,      0}, {0x3a, 0x70, 0x00, 0xff}}},
    {{{    38,    -32,    -47}, 0, {     0,      0}, {0x3d, 0xcf, 0x9d, 0xff}}},
    {{{    79,    -26,    -25}, 0, {     0,      0}, {0x3d, 0xcf, 0x9d, 0xff}}},
    {{{    41,    -47,    -38}, 0, {     0,      0}, {0x3d, 0xcf, 0x9d, 0xff}}},
};

// 0x05003D58
static const Vtx hoot_seg5_vertex_05003D58[] = {
    {{{    45,    -48,      1}, 0, {     0,      0}, {0xde, 0xb4, 0x5f, 0x00}}},
    {{{    51,     -7,     36}, 0, {     0,      0}, {0xde, 0xb4, 0x5f, 0x00}}},
    {{{   -10,    -23,      1}, 0, {     0,      0}, {0xde, 0xb4, 0x5f, 0x00}}},
    {{{    58,     33,      1}, 0, {     0,      0}, {0xf8, 0x53, 0xa1, 0xff}}},
    {{{    51,     -7,    -34}, 0, {     0,      0}, {0xf8, 0x53, 0xa1, 0xff}}},
    {{{    -2,     26,      1}, 0, {     0,      0}, {0xf8, 0x53, 0xa1, 0xff}}},
    {{{   -10,    -23,      1}, 0, {     0,      0}, {0x83, 0x14, 0x00, 0xff}}},
    {{{    -6,      1,     23}, 0, {     0,      0}, {0x83, 0x14, 0x00, 0xff}}},
    {{{    -6,      1,    -20}, 0, {     0,      0}, {0x83, 0x14, 0x00, 0xff}}},
    {{{    51,     -7,     36}, 0, {     0,      0}, {0xf8, 0x53, 0x5f, 0xff}}},
    {{{    58,     33,      1}, 0, {     0,      0}, {0xf8, 0x53, 0x5f, 0xff}}},
    {{{    -2,     26,      1}, 0, {     0,      0}, {0xf8, 0x53, 0x5f, 0xff}}},
    {{{    51,     -7,    -34}, 0, {     0,      0}, {0xde, 0xb4, 0xa1, 0xff}}},
    {{{    45,    -48,      1}, 0, {     0,      0}, {0xde, 0xb4, 0xa1, 0xff}}},
    {{{   -10,    -23,      1}, 0, {     0,      0}, {0xde, 0xb4, 0xa1, 0xff}}},
    {{{    -6,      1,    -20}, 0, {     0,      0}, {0xde, 0xb4, 0xa1, 0xff}}},
};

// 0x05003E58
static const Vtx hoot_seg5_vertex_05003E58[] = {
    {{{    -2,     26,      1}, 0, {     0,      0}, {0xf8, 0x53, 0x5f, 0xff}}},
    {{{    -6,      1,     23}, 0, {     0,      0}, {0xf8, 0x53, 0x5f, 0x00}}},
    {{{    51,     -7,     36}, 0, {     0,      0}, {0xf8, 0x53, 0x5f, 0x00}}},
    {{{    -6,      1,     23}, 0, {     0,      0}, {0x83, 0x14, 0x00, 0xff}}},
    {{{    -2,     26,      1}, 0, {     0,      0}, {0x83, 0x14, 0x00, 0xff}}},
    {{{    -6,      1,    -20}, 0, {     0,      0}, {0x83, 0x14, 0x00, 0xff}}},
    {{{    51,     -7,    -34}, 0, {     0,      0}, {0xf8, 0x53, 0xa1, 0xff}}},
    {{{    -6,      1,    -20}, 0, {     0,      0}, {0xf8, 0x53, 0xa1, 0xff}}},
    {{{    -2,     26,      1}, 0, {     0,      0}, {0xf8, 0x53, 0xa1, 0xff}}},
    {{{    51,     -7,     36}, 0, {     0,      0}, {0xde, 0xb4, 0x5f, 0xff}}},
    {{{    -6,      1,     23}, 0, {     0,      0}, {0xde, 0xb4, 0x5f, 0xff}}},
    {{{   -10,    -23,      1}, 0, {     0,      0}, {0xde, 0xb4, 0x5f, 0xff}}},
};

// 0x05003F18
static const Vtx hoot_seg5_vertex_05003F18[] = {
    {{{    51,     -7,    -34}, 0, {     0,      0}, {0x7d, 0xec, 0x00, 0x00}}},
    {{{    58,     33,      1}, 0, {     0,      0}, {0x7d, 0xec, 0x00, 0x00}}},
    {{{    51,     -7,     36}, 0, {     0,      0}, {0x7d, 0xec, 0x00, 0x00}}},
    {{{    45,    -48,      1}, 0, {     0,      0}, {0x7d, 0xec, 0x00, 0xff}}},
};

// 0x05003F58
static const Vtx hoot_seg5_vertex_05003F58[] = {
    {{{    26,     49,     -9}, 0, {     0,      0}, {0x27, 0xea, 0x8a, 0x00}}},
    {{{    63,     30,      0}, 0, {     0,      0}, {0x00, 0xcc, 0x8d, 0x00}}},
    {{{    41,     29,      0}, 0, {     0,      0}, {0x33, 0xf6, 0x8d, 0x00}}},
    {{{    41,     29,      0}, 0, {     0,      0}, {0x33, 0xf6, 0x73, 0xff}}},
    {{{    63,     30,      0}, 0, {     0,      0}, {0x00, 0xcc, 0x73, 0xff}}},
    {{{    26,     49,      8}, 0, {     0,      0}, {0x27, 0xea, 0x76, 0xff}}},
    {{{    41,    -47,    -39}, 0, {     0,      0}, {0x18, 0xb1, 0x5f, 0xff}}},
    {{{    79,    -26,    -26}, 0, {     0,      0}, {0x07, 0xb4, 0x64, 0xff}}},
    {{{    49,    -27,    -25}, 0, {     0,      0}, {0x26, 0xb7, 0x60, 0xff}}},
    {{{    49,    -27,    -25}, 0, {     0,      0}, {0x37, 0x5f, 0xc2, 0xff}}},
    {{{    79,    -26,    -26}, 0, {     0,      0}, {0xfc, 0x7b, 0xe4, 0xff}}},
    {{{    38,    -32,    -48}, 0, {     0,      0}, {0x2d, 0x6c, 0xd1, 0xff}}},
    {{{    38,    -32,     47}, 0, {     0,      0}, {0x2d, 0x6c, 0x2f, 0xff}}},
    {{{    79,    -26,     25}, 0, {     0,      0}, {0xfc, 0x7b, 0x1c, 0xff}}},
    {{{    49,    -27,     24}, 0, {     0,      0}, {0x37, 0x5f, 0x3e, 0xff}}},
};

// 0x05004048
static const Vtx hoot_seg5_vertex_05004048[] = {
    {{{    49,    -27,     24}, 0, {     0,      0}, {0x26, 0xb7, 0xa0, 0xff}}},
    {{{    79,    -26,     25}, 0, {     0,      0}, {0x07, 0xb4, 0x9c, 0x00}}},
    {{{    41,    -47,     38}, 0, {     0,      0}, {0x18, 0xb1, 0xa1, 0x00}}},
    {{{    41,    -47,     38}, 0, {     0,      0}, {0x3d, 0xcf, 0x63, 0xff}}},
    {{{    79,    -26,     25}, 0, {     0,      0}, {0x3d, 0xcf, 0x63, 0xff}}},
    {{{    38,    -32,     47}, 0, {     0,      0}, {0x3d, 0xcf, 0x63, 0xff}}},
    {{{    26,     49,      8}, 0, {     0,      0}, {0x3a, 0x70, 0x00, 0xff}}},
    {{{    63,     30,      0}, 0, {     0,      0}, {0x3a, 0x70, 0x00, 0xff}}},
    {{{    26,     49,     -9}, 0, {     0,      0}, {0x3a, 0x70, 0x00, 0xff}}},
    {{{    38,    -32,    -48}, 0, {     0,      0}, {0x3d, 0xcf, 0x9d, 0xff}}},
    {{{    79,    -26,    -26}, 0, {     0,      0}, {0x3d, 0xcf, 0x9d, 0xff}}},
    {{{    41,    -47,    -39}, 0, {     0,      0}, {0x3d, 0xcf, 0x9d, 0xff}}},
};

// 0x05004108
static const Vtx hoot_seg5_vertex_05004108[] = {
    {{{    49,    -27,    -25}, 0, {     0,      0}, {0x37, 0x5f, 0xc2, 0x00}}},
    {{{    38,    -32,    -48}, 0, {     0,      0}, {0x2d, 0x6c, 0xd1, 0x00}}},
    {{{    22,     13,    -15}, 0, {     0,      0}, {0x4e, 0x29, 0xa5, 0x00}}},
    {{{    22,     13,    -15}, 0, {     0,      0}, {0x87, 0xe1, 0xf1, 0xff}}},
    {{{    38,    -32,    -48}, 0, {     0,      0}, {0x87, 0xe1, 0xf1, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x87, 0xe1, 0xf1, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x35, 0xbb, 0x5b, 0xff}}},
    {{{    41,    -47,    -39}, 0, {     0,      0}, {0x18, 0xb1, 0x5f, 0xff}}},
    {{{    49,    -27,    -25}, 0, {     0,      0}, {0x26, 0xb7, 0x60, 0xff}}},
    {{{    49,    -27,     24}, 0, {     0,      0}, {0x26, 0xb7, 0xa0, 0xff}}},
    {{{    41,    -47,     38}, 0, {     0,      0}, {0x18, 0xb1, 0xa1, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x35, 0xbb, 0xa5, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x87, 0xe1, 0x0f, 0xff}}},
    {{{    38,    -32,     47}, 0, {     0,      0}, {0x87, 0xe1, 0x0f, 0xff}}},
    {{{    22,     13,     15}, 0, {     0,      0}, {0x87, 0xe1, 0x0f, 0xff}}},
};

// 0x050041F8
static const Vtx hoot_seg5_vertex_050041F8[] = {
    {{{    22,     13,     15}, 0, {     0,      0}, {0x4e, 0x29, 0x5b, 0xff}}},
    {{{    38,    -32,     47}, 0, {     0,      0}, {0x2d, 0x6c, 0x2f, 0x00}}},
    {{{    49,    -27,     24}, 0, {     0,      0}, {0x37, 0x5f, 0x3e, 0x00}}},
    {{{    41,     29,      0}, 0, {     0,      0}, {0x33, 0xf6, 0x73, 0xff}}},
    {{{    26,     49,      8}, 0, {     0,      0}, {0x27, 0xea, 0x76, 0xff}}},
    {{{    22,     13,     15}, 0, {     0,      0}, {0x82, 0x0e, 0x00, 0xff}}},
    {{{    26,     49,     -9}, 0, {     0,      0}, {0x82, 0x0e, 0x00, 0xff}}},
    {{{    22,     13,    -15}, 0, {     0,      0}, {0x82, 0x0e, 0x00, 0xff}}},
    {{{    22,     13,    -15}, 0, {     0,      0}, {0xe1, 0x7b, 0x00, 0xff}}},
    {{{   -14,      3,    -11}, 0, {     0,      0}, {0xe1, 0x7b, 0x00, 0xff}}},
    {{{    22,     13,     15}, 0, {     0,      0}, {0xe1, 0x7b, 0x00, 0xff}}},
    {{{   -14,      3,    -11}, 0, {     0,      0}, {0x84, 0xea, 0x00, 0xff}}},
    {{{   -10,    -13,      0}, 0, {     0,      0}, {0x84, 0xea, 0x00, 0xff}}},
    {{{   -14,      3,     10}, 0, {     0,      0}, {0x84, 0xea, 0x00, 0xff}}},
};

// 0x050042D8
static const Vtx hoot_seg5_vertex_050042D8[] = {
    {{{   -14,      3,     10}, 0, {     0,      0}, {0x02, 0xbe, 0x6c, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x02, 0xbe, 0x6c, 0x00}}},
    {{{    22,     13,     15}, 0, {     0,      0}, {0x02, 0xbe, 0x6c, 0x00}}},
    {{{    22,     13,    -15}, 0, {     0,      0}, {0x4e, 0x29, 0xa5, 0xff}}},
    {{{    26,     49,     -9}, 0, {     0,      0}, {0x27, 0xea, 0x8a, 0xff}}},
    {{{    41,     29,      0}, 0, {     0,      0}, {0x33, 0xf6, 0x8d, 0xff}}},
    {{{   -14,      3,    -11}, 0, {     0,      0}, {0x02, 0xbe, 0x94, 0xff}}},
    {{{    22,     13,    -15}, 0, {     0,      0}, {0x02, 0xbe, 0x94, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x02, 0xbe, 0x94, 0xff}}},
    {{{   -10,    -13,      0}, 0, {     0,      0}, {0x02, 0xbe, 0x94, 0xff}}},
    {{{   -10,    -13,      0}, 0, {     0,      0}, {0x02, 0xbe, 0x6c, 0xff}}},
    {{{   -14,      3,    -11}, 0, {     0,      0}, {0xe1, 0x7b, 0x00, 0xff}}},
    {{{   -14,      3,     10}, 0, {     0,      0}, {0xe1, 0x7b, 0x00, 0xff}}},
    {{{    22,     13,     15}, 0, {     0,      0}, {0xe1, 0x7b, 0x00, 0xff}}},
};

// 0x050043B8
static const Vtx hoot_seg5_vertex_050043B8[] = {
    {{{    49,    -27,     24}, 0, {     0,      0}, {0x37, 0x5f, 0x3e, 0xff}}},
    {{{    45,      8,      0}, 0, {     0,      0}, {0x48, 0x27, 0x60, 0x00}}},
    {{{    22,     13,     15}, 0, {     0,      0}, {0x4e, 0x29, 0x5b, 0x00}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x87, 0xe1, 0x0f, 0xff}}},
    {{{    41,    -47,     38}, 0, {     0,      0}, {0x87, 0xe1, 0x0f, 0xff}}},
    {{{    38,    -32,     47}, 0, {     0,      0}, {0x87, 0xe1, 0x0f, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x35, 0xbb, 0xa5, 0xff}}},
    {{{    45,      8,      0}, 0, {     0,      0}, {0x3f, 0xc7, 0xa3, 0xff}}},
    {{{    49,    -27,     24}, 0, {     0,      0}, {0x26, 0xb7, 0xa0, 0xff}}},
    {{{    49,    -27,    -25}, 0, {     0,      0}, {0x26, 0xb7, 0x60, 0xff}}},
    {{{    45,      8,      0}, 0, {     0,      0}, {0x3f, 0xc7, 0x5d, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x35, 0xbb, 0x5b, 0xff}}},
    {{{    38,    -32,    -48}, 0, {     0,      0}, {0x87, 0xe1, 0xf1, 0xff}}},
    {{{    41,    -47,    -39}, 0, {     0,      0}, {0x87, 0xe1, 0xf1, 0xff}}},
    {{{    26,    -12,      0}, 0, {     0,      0}, {0x87, 0xe1, 0xf1, 0xff}}},
};

// 0x050044A8
static const Vtx hoot_seg5_vertex_050044A8[] = {
    {{{    22,     13,    -15}, 0, {     0,      0}, {0x4e, 0x29, 0xa5, 0xff}}},
    {{{    45,      8,      0}, 0, {     0,      0}, {0x48, 0x27, 0xa0, 0x00}}},
    {{{    49,    -27,    -25}, 0, {     0,      0}, {0x37, 0x5f, 0xc2, 0x00}}},
    {{{    41,     29,      0}, 0, {     0,      0}, {0x33, 0xf6, 0x8d, 0xff}}},
    {{{    22,     13,     15}, 0, {     0,      0}, {0x82, 0x0e, 0x00, 0xff}}},
    {{{    26,     49,      8}, 0, {     0,      0}, {0x82, 0x0e, 0x00, 0xff}}},
    {{{    26,     49,     -9}, 0, {     0,      0}, {0x82, 0x0e, 0x00, 0xff}}},
    {{{    22,     13,     15}, 0, {     0,      0}, {0x4e, 0x29, 0x5b, 0xff}}},
    {{{    45,      8,      0}, 0, {     0,      0}, {0x48, 0x27, 0x60, 0xff}}},
    {{{    41,     29,      0}, 0, {     0,      0}, {0x33, 0xf6, 0x73, 0xff}}},
};

// 0x05004548
static const Vtx hoot_seg5_vertex_05004548[] = {
    {{{   -10,    -23,     -1}, 0, {     0,      0}, {0xde, 0xb4, 0x5f, 0x00}}},
    {{{    45,    -48,     -1}, 0, {     0,      0}, {0xde, 0xb4, 0x5f, 0x00}}},
    {{{    51,     -7,     34}, 0, {     0,      0}, {0xde, 0xb4, 0x5f, 0x00}}},
    {{{    -2,     26,     -1}, 0, {     0,      0}, {0xf8, 0x53, 0xa1, 0xff}}},
    {{{    58,     33,     -1}, 0, {     0,      0}, {0xf8, 0x53, 0xa1, 0xff}}},
    {{{    51,     -7,    -36}, 0, {     0,      0}, {0xf8, 0x53, 0xa1, 0xff}}},
    {{{    -6,      1,     20}, 0, {     0,      0}, {0x83, 0x14, 0x00, 0xff}}},
    {{{    -6,      1,    -23}, 0, {     0,      0}, {0x83, 0x14, 0x00, 0xff}}},
    {{{   -10,    -23,     -1}, 0, {     0,      0}, {0x83, 0x14, 0x00, 0xff}}},
    {{{    -2,     26,     -1}, 0, {     0,      0}, {0xf8, 0x53, 0x5f, 0xff}}},
    {{{    51,     -7,     34}, 0, {     0,      0}, {0xf8, 0x53, 0x5f, 0xff}}},
    {{{    58,     33,     -1}, 0, {     0,      0}, {0xf8, 0x53, 0x5f, 0xff}}},
    {{{   -10,    -23,     -1}, 0, {     0,      0}, {0xde, 0xb4, 0xa1, 0xff}}},
    {{{    51,     -7,    -36}, 0, {     0,      0}, {0xde, 0xb4, 0xa1, 0xff}}},
    {{{    45,    -48,     -1}, 0, {     0,      0}, {0xde, 0xb4, 0xa1, 0xff}}},
    {{{    -6,      1,    -23}, 0, {     0,      0}, {0xde, 0xb4, 0xa1, 0xff}}},
};

// 0x05004648
static const Vtx hoot_seg5_vertex_05004648[] = {
    {{{    -2,     26,     -1}, 0, {     0,      0}, {0xf8, 0x53, 0x5f, 0xff}}},
    {{{    -6,      1,     20}, 0, {     0,      0}, {0xf8, 0x53, 0x5f, 0x00}}},
    {{{    51,     -7,     34}, 0, {     0,      0}, {0xf8, 0x53, 0x5f, 0x00}}},
    {{{    -6,      1,     20}, 0, {     0,      0}, {0x83, 0x14, 0x00, 0xff}}},
    {{{    -2,     26,     -1}, 0, {     0,      0}, {0x83, 0x14, 0x00, 0xff}}},
    {{{    -6,      1,    -23}, 0, {     0,      0}, {0x83, 0x14, 0x00, 0xff}}},
    {{{    51,     -7,    -36}, 0, {     0,      0}, {0xf8, 0x53, 0xa1, 0xff}}},
    {{{    -6,      1,    -23}, 0, {     0,      0}, {0xf8, 0x53, 0xa1, 0xff}}},
    {{{    -2,     26,     -1}, 0, {     0,      0}, {0xf8, 0x53, 0xa1, 0xff}}},
    {{{    51,     -7,     34}, 0, {     0,      0}, {0xde, 0xb4, 0x5f, 0xff}}},
    {{{    -6,      1,     20}, 0, {     0,      0}, {0xde, 0xb4, 0x5f, 0xff}}},
    {{{   -10,    -23,     -1}, 0, {     0,      0}, {0xde, 0xb4, 0x5f, 0xff}}},
};

// 0x05004708
static const Vtx hoot_seg5_vertex_05004708[] = {
    {{{    45,    -48,     -1}, 0, {     0,      0}, {0x7d, 0xec, 0x00, 0x00}}},
    {{{    51,     -7,    -36}, 0, {     0,      0}, {0x7d, 0xec, 0x00, 0x00}}},
    {{{    51,     -7,     34}, 0, {     0,      0}, {0x7d, 0xec, 0x00, 0x00}}},
    {{{    58,     33,     -1}, 0, {     0,      0}, {0x7d, 0xec, 0x00, 0xff}}},
};

// 0x05004748
static const Vtx hoot_seg5_vertex_05004748[] = {
    {{{     0,     71,    -47}, 0, {     0,      0}, {0xd6, 0x6a, 0xca, 0x00}}},
    {{{     0,     71,     47}, 0, {     0,      0}, {0xd4, 0x71, 0x23, 0x00}}},
    {{{    64,     81,    -43}, 0, {     0,      0}, {0x18, 0x71, 0xcd, 0x00}}},
    {{{  -105,    -59,      0}, 0, {     0,      0}, {0x82, 0x0c, 0x00, 0xff}}},
    {{{   -39,     46,     39}, 0, {     0,      0}, {0xa5, 0x4c, 0x2b, 0xff}}},
    {{{   -39,     46,    -39}, 0, {     0,      0}, {0xa9, 0x54, 0xde, 0xff}}},
    {{{   -16,     11,     85}, 0, {     0,      0}, {0xce, 0x1d, 0x70, 0xff}}},
    {{{   112,     35,     48}, 0, {     0,      0}, {0x75, 0x1a, 0x27, 0xff}}},
    {{{    64,     81,     43}, 0, {     0,      0}, {0x1f, 0x6b, 0x3b, 0xff}}},
    {{{    65,     30,     89}, 0, {     0,      0}, {0x2e, 0x22, 0x71, 0xff}}},
    {{{    65,     30,    -89}, 0, {     0,      0}, {0x2e, 0x22, 0x8f, 0xff}}},
    {{{   112,     35,    -48}, 0, {     0,      0}, {0x6f, 0x24, 0xd1, 0xff}}},
    {{{   -16,     11,    -85}, 0, {     0,      0}, {0xce, 0x1d, 0x90, 0xff}}},
    {{{   109,     54,      0}, 0, {     0,      0}, {0x5f, 0x54, 0x00, 0xff}}},
};

// 0x05004828
static const Vtx hoot_seg5_vertex_05004828[] = {
    {{{    48,    -41,    -96}, 0, {     0,      0}, {0x09, 0xca, 0x8e, 0x00}}},
    {{{    36,    -84,    -48}, 0, {     0,      0}, {0x0a, 0x87, 0xde, 0x00}}},
    {{{   -36,    -75,    -34}, 0, {     0,      0}, {0xd6, 0x9d, 0xbe, 0x00}}},
    {{{    99,    -52,     48}, 0, {     0,      0}, {0x5e, 0xc5, 0x3d, 0xff}}},
    {{{    36,    -84,     48}, 0, {     0,      0}, {0x1a, 0x89, 0x23, 0xff}}},
    {{{    99,    -52,    -48}, 0, {     0,      0}, {0x63, 0xbf, 0xd4, 0xff}}},
    {{{   -16,     11,     85}, 0, {     0,      0}, {0xce, 0x1d, 0x70, 0xff}}},
    {{{   -36,    -75,     34}, 0, {     0,      0}, {0xda, 0x95, 0x37, 0xff}}},
    {{{    48,    -41,     96}, 0, {     0,      0}, {0x09, 0xca, 0x72, 0xff}}},
    {{{  -105,    -59,      0}, 0, {     0,      0}, {0x82, 0x0c, 0x00, 0xff}}},
    {{{   112,     35,    -48}, 0, {     0,      0}, {0x6f, 0x24, 0xd1, 0xff}}},
    {{{   112,     35,     48}, 0, {     0,      0}, {0x75, 0x1a, 0x27, 0xff}}},
    {{{    65,     30,     89}, 0, {     0,      0}, {0x2e, 0x22, 0x71, 0xff}}},
    {{{   -16,     11,    -85}, 0, {     0,      0}, {0xce, 0x1d, 0x90, 0xff}}},
    {{{    65,     30,    -89}, 0, {     0,      0}, {0x2e, 0x22, 0x8f, 0xff}}},
    {{{  -105,    -59,      0}, 0, {     0,      0}, {0xe4, 0x85, 0x00, 0xff}}},
};

// 0x05004928 - 0x05004A98
const Gfx hoot_seg5_dl_05004928[] = {
    gsSPLight(&hoot_seg5_lights_05000930.l, 1),
    gsSPLight(&hoot_seg5_lights_05000930.a, 2),
    gsSPVertex(hoot_seg5_vertex_05003778, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(hoot_seg5_vertex_05003868, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(hoot_seg5_vertex_05003958, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  9,  6, 0x0),
    gsSP2Triangles( 4, 10,  5, 0x0,  2, 11,  0, 0x0),
    gsSP2Triangles(12, 11,  2, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(hoot_seg5_vertex_05003A58, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(hoot_seg5_vertex_05003B48, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPLight(&hoot_seg5_lights_05000948.l, 1),
    gsSPLight(&hoot_seg5_lights_05000948.a, 2),
    gsSPVertex(hoot_seg5_vertex_05003BA8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(hoot_seg5_vertex_05003C98, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004A98 - 0x05004B38
const Gfx hoot_seg5_dl_05004A98[] = {
    gsSPLight(&hoot_seg5_lights_05000960.l, 1),
    gsSPLight(&hoot_seg5_lights_05000960.a, 2),
    gsSPVertex(hoot_seg5_vertex_05003D58, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 15, 12, 0x0),
    gsSPVertex(hoot_seg5_vertex_05003E58, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPLight(&hoot_seg5_lights_05000978.l, 1),
    gsSPLight(&hoot_seg5_lights_05000978.a, 2),
    gsSPVertex(hoot_seg5_vertex_05003F18, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004B38 - 0x05004CA8
const Gfx hoot_seg5_dl_05004B38[] = {
    gsSPLight(&hoot_seg5_lights_05000948.l, 1),
    gsSPLight(&hoot_seg5_lights_05000948.a, 2),
    gsSPVertex(hoot_seg5_vertex_05003F58, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(hoot_seg5_vertex_05004048, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPLight(&hoot_seg5_lights_05000930.l, 1),
    gsSPLight(&hoot_seg5_lights_05000930.a, 2),
    gsSPVertex(hoot_seg5_vertex_05004108, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(hoot_seg5_vertex_050041F8, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  8,  9, 10, 0x0),
    gsSP1Triangle(11, 12, 13, 0x0),
    gsSPVertex(hoot_seg5_vertex_050042D8, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  9,  6, 0x0),
    gsSP2Triangles( 0, 10,  1, 0x0, 11, 12, 13, 0x0),
    gsSPVertex(hoot_seg5_vertex_050043B8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(hoot_seg5_vertex_050044A8, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004CA8 - 0x05004D48
const Gfx hoot_seg5_dl_05004CA8[] = {
    gsSPLight(&hoot_seg5_lights_05000960.l, 1),
    gsSPLight(&hoot_seg5_lights_05000960.a, 2),
    gsSPVertex(hoot_seg5_vertex_05004548, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15, 13, 0x0),
    gsSPVertex(hoot_seg5_vertex_05004648, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPLight(&hoot_seg5_lights_05000978.l, 1),
    gsSPLight(&hoot_seg5_lights_05000978.a, 2),
    gsSPVertex(hoot_seg5_vertex_05004708, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004D48 - 0x05004EC0
const Gfx hoot_seg5_dl_05004D48[] = {
    gsSPLight(&hoot_seg5_lights_05000900.l, 1),
    gsSPLight(&hoot_seg5_lights_05000900.a, 2),
    gsSPVertex(hoot_seg5_vertex_05004748, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  1,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  2, 11, 0x0,  0, 12,  5, 0x0),
    gsSP2Triangles( 6,  4,  3, 0x0, 12,  3,  5, 0x0),
    gsSP2Triangles( 1,  0,  5, 0x0,  8,  1,  6, 0x0),
    gsSP2Triangles(11,  2, 13, 0x0, 12,  0,  2, 0x0),
    gsSP2Triangles( 8,  7, 13, 0x0,  2,  8, 13, 0x0),
    gsSP2Triangles( 2, 10, 12, 0x0,  6,  9,  8, 0x0),
    gsSP2Triangles(11, 13,  7, 0x0,  5,  4,  1, 0x0),
    gsSP1Triangle( 1,  8,  2, 0x0),
    gsSPLight(&hoot_seg5_lights_05000960.l, 1),
    gsSPLight(&hoot_seg5_lights_05000960.a, 2),
    gsSPVertex(hoot_seg5_vertex_05004828, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  4,  3, 0x0),
    gsSP2Triangles( 7,  6,  9, 0x0, 10, 11,  5, 0x0),
    gsSP2Triangles( 7,  1,  4, 0x0,  6,  8, 12, 0x0),
    gsSP2Triangles(13, 14,  0, 0x0,  7,  4,  8, 0x0),
    gsSP2Triangles( 2, 13,  0, 0x0, 13,  2,  9, 0x0),
    gsSP2Triangles( 5,  1,  0, 0x0,  3, 11, 12, 0x0),
    gsSP2Triangles(14, 10,  5, 0x0,  4,  1,  5, 0x0),
    gsSP2Triangles( 5,  0, 14, 0x0, 12,  8,  3, 0x0),
    gsSP2Triangles(11,  3,  5, 0x0,  7,  2,  1, 0x0),
    gsSP1Triangle(15,  2,  7, 0x0),
    gsSPEndDisplayList(),
};
