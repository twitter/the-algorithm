// Bullet Bill

// 0x0500BA90
static const Lights1 bullet_bill_seg5_lights_0500BA90 = gdSPDefLights1(
    0x06, 0x07, 0x14,
    0x19, 0x1c, 0x52, 0x28, 0x28, 0x28
);

// 0x0500BAA8
ALIGNED8 static const Texture bullet_bill_seg5_texture_0500BAA8[] = {
#include "actors/bullet_bill/bullet_bill_eye.rgba16.inc.c"
};

// 0x0500CAA8
ALIGNED8 static const Texture bullet_bill_seg5_texture_0500CAA8[] = {
#include "actors/bullet_bill/bullet_bill_mouth.rgba16.inc.c"
};

// 0x0500DAA8
static const Vtx bullet_bill_seg5_vertex_0500DAA8[] = {
    {{{  -195,      0,    272}, 0, {   884,   1044}, {0x9e, 0x02, 0x50, 0xff}}},
    {{{  -138,    139,    272}, 0, {   884,   -180}, {0xbc, 0x47, 0x50, 0xff}}},
    {{{  -176,    177,    161}, 0, {  2304,   -208}, {0x9e, 0x4a, 0x1c, 0xff}}},
    {{{  -195,      0,    272}, 0, {   884,   1044}, {0x9e, 0x02, 0x50, 0xff}}},
    {{{  -176,    177,    161}, 0, {  2304,   -208}, {0x9e, 0x4a, 0x1c, 0xff}}},
    {{{  -249,      0,    161}, 0, {  2304,   1356}, {0x86, 0xef, 0x1c, 0xff}}},
    {{{  -100,      0,    347}, 0, {  -336,    626}, {0xc4, 0x08, 0x6f, 0xff}}},
    {{{  -138,    139,    272}, 0, {   884,   -180}, {0xbc, 0x47, 0x50, 0xff}}},
    {{{  -195,      0,    272}, 0, {   884,   1044}, {0x9e, 0x02, 0x50, 0xff}}},
    {{{  -100,      0,    347}, 0, {  -336,    626}, {0xc4, 0x08, 0x6f, 0xff}}},
    {{{   -70,     71,    347}, 0, {  -336,     -4}, {0xf0, 0x15, 0x7c, 0xff}}},
    {{{  -138,    139,    272}, 0, {   884,   -180}, {0xbc, 0x47, 0x50, 0xff}}},
    {{{   101,      0,    347}, 0, {  -480,    478}, {0x36, 0x00, 0x72, 0xff}}},
    {{{   196,      0,    272}, 0, {   620,    886}, {0x62, 0x00, 0x4f, 0xff}}},
    {{{   139,    139,    272}, 0, {   960,   -306}, {0x48, 0x3e, 0x53, 0xff}}},
};

// 0x0500DB98
static const Vtx bullet_bill_seg5_vertex_0500DB98[] = {
    {{{    71,     71,    347}, 0, {  -304,   -134}, {0x1c, 0x1c, 0x78, 0xff}}},
    {{{   101,      0,    347}, 0, {  -480,    478}, {0x36, 0x00, 0x72, 0xff}}},
    {{{   139,    139,    272}, 0, {   960,   -306}, {0x48, 0x3e, 0x53, 0xff}}},
    {{{   139,    139,    272}, 0, {   960,   -306}, {0x48, 0x3e, 0x53, 0xff}}},
    {{{   250,      0,    161}, 0, {  1956,   1186}, {0x7a, 0x07, 0x20, 0xff}}},
    {{{   177,    177,    161}, 0, {  2388,   -332}, {0x4a, 0x62, 0x1c, 0xff}}},
    {{{   139,    139,    272}, 0, {   960,   -306}, {0x48, 0x3e, 0x53, 0xff}}},
    {{{   196,      0,    272}, 0, {   620,    886}, {0x62, 0x00, 0x4f, 0xff}}},
    {{{   250,      0,    161}, 0, {  1956,   1186}, {0x7a, 0x07, 0x20, 0xff}}},
};

// 0x0500DC28
static const Vtx bullet_bill_seg5_vertex_0500DC28[] = {
    {{{   -70,     71,    347}, 0, {   508,   -448}, {0xf0, 0x15, 0x7c, 0xff}}},
    {{{     0,   -100,    347}, 0, {   996,    556}, {0xfa, 0xd0, 0x75, 0xff}}},
    {{{    71,    -70,    347}, 0, {  1480,    384}, {0x1d, 0xd9, 0x74, 0xff}}},
    {{{   -70,     71,    347}, 0, {   508,   -448}, {0xf0, 0x15, 0x7c, 0xff}}},
    {{{    71,    -70,    347}, 0, {  1480,    384}, {0x1d, 0xd9, 0x74, 0xff}}},
    {{{   101,      0,    347}, 0, {  1680,      0}, {0x36, 0x00, 0x72, 0xff}}},
    {{{    71,    -70,    347}, 0, {  1480,    384}, {0x1d, 0xd9, 0x74, 0xff}}},
    {{{     0,   -100,    347}, 0, {   996,    556}, {0xfa, 0xd0, 0x75, 0xff}}},
    {{{     0,   -195,    272}, 0, {   996,   1110}, {0xfe, 0x9e, 0x50, 0xff}}},
    {{{   101,      0,    347}, 0, {  1680,      0}, {0x36, 0x00, 0x72, 0xff}}},
    {{{    71,    -70,    347}, 0, {  1480,    384}, {0x1d, 0xd9, 0x74, 0xff}}},
    {{{   139,   -138,    272}, 0, {  1936,    776}, {0x4a, 0xbc, 0x4c, 0xff}}},
    {{{    71,    -70,    347}, 0, {  1480,    384}, {0x1d, 0xd9, 0x74, 0xff}}},
    {{{     0,   -195,    272}, 0, {   996,   1110}, {0xfe, 0x9e, 0x50, 0xff}}},
    {{{   139,   -138,    272}, 0, {  1936,    776}, {0x4a, 0xbc, 0x4c, 0xff}}},
};

// 0x0500DD18
static const Vtx bullet_bill_seg5_vertex_0500DD18[] = {
    {{{   -70,     71,    347}, 0, {   508,   -448}, {0xf0, 0x15, 0x7c, 0xff}}},
    {{{   -70,    -70,    347}, 0, {   508,    384}, {0xd9, 0xe3, 0x74, 0xff}}},
    {{{     0,   -100,    347}, 0, {   996,    556}, {0xfa, 0xd0, 0x75, 0xff}}},
    {{{     0,   -100,    347}, 0, {   996,    556}, {0xfa, 0xd0, 0x75, 0xff}}},
    {{{  -138,   -138,    272}, 0, {    52,    776}, {0xb9, 0xbd, 0x50, 0xff}}},
    {{{     0,   -195,    272}, 0, {   996,   1110}, {0xfe, 0x9e, 0x50, 0xff}}},
    {{{     0,   -100,    347}, 0, {   996,    556}, {0xfa, 0xd0, 0x75, 0xff}}},
    {{{   -70,    -70,    347}, 0, {   508,    384}, {0xd9, 0xe3, 0x74, 0xff}}},
    {{{  -138,   -138,    272}, 0, {    52,    776}, {0xb9, 0xbd, 0x50, 0xff}}},
    {{{   101,      0,    347}, 0, {  1680,      0}, {0x36, 0x00, 0x72, 0xff}}},
    {{{   139,   -138,    272}, 0, {  1936,    776}, {0x4a, 0xbc, 0x4c, 0xff}}},
    {{{   196,      0,    272}, 0, {  2328,      0}, {0x62, 0x00, 0x4f, 0xff}}},
    {{{   -70,     71,    347}, 0, {   508,   -448}, {0xf0, 0x15, 0x7c, 0xff}}},
    {{{  -100,      0,    347}, 0, {   308,      0}, {0xc4, 0x08, 0x6f, 0xff}}},
    {{{   -70,    -70,    347}, 0, {   508,    384}, {0xd9, 0xe3, 0x74, 0xff}}},
};

// 0x0500DE08
static const Vtx bullet_bill_seg5_vertex_0500DE08[] = {
    {{{   -70,    -70,    347}, 0, {   508,    384}, {0xd9, 0xe3, 0x74, 0xff}}},
    {{{  -195,      0,    272}, 0, {  -336,      0}, {0x9e, 0x02, 0x50, 0xff}}},
    {{{  -138,   -138,    272}, 0, {    52,    776}, {0xb9, 0xbd, 0x50, 0xff}}},
    {{{   -70,    -70,    347}, 0, {   508,    384}, {0xd9, 0xe3, 0x74, 0xff}}},
    {{{  -100,      0,    347}, 0, {   308,      0}, {0xc4, 0x08, 0x6f, 0xff}}},
    {{{  -195,      0,    272}, 0, {  -336,      0}, {0x9e, 0x02, 0x50, 0xff}}},
    {{{   -70,     71,    347}, 0, {   508,   -448}, {0xf0, 0x15, 0x7c, 0xff}}},
    {{{   101,      0,    347}, 0, {  1680,      0}, {0x36, 0x00, 0x72, 0xff}}},
    {{{    71,     71,    347}, 0, {  1480,   -448}, {0x1c, 0x1c, 0x78, 0xff}}},
    {{{   -70,     71,    347}, 0, {   508,   -448}, {0xf0, 0x15, 0x7c, 0xff}}},
    {{{    71,     71,    347}, 0, {  1480,   -448}, {0x1c, 0x1c, 0x78, 0xff}}},
    {{{     0,    101,    347}, 0, {   996,   -620}, {0x08, 0x3c, 0x6f, 0xff}}},
};

// 0x0500DEC8
static const Vtx bullet_bill_seg5_vertex_0500DEC8[] = {
    {{{  -176,   -176,   -192}, 0, {     0,      0}, {0xd6, 0xe0, 0x8d, 0xff}}},
    {{{   250,      0,   -192}, 0, {     0,      0}, {0x66, 0xf2, 0xb7, 0xff}}},
    {{{   177,   -176,   -192}, 0, {     0,      0}, {0x3e, 0xae, 0xb7, 0xff}}},
    {{{  -176,   -176,   -192}, 0, {     0,      0}, {0xd6, 0xe0, 0x8d, 0xff}}},
    {{{   177,   -176,   -192}, 0, {     0,      0}, {0x3e, 0xae, 0xb7, 0xff}}},
    {{{     0,   -249,   -192}, 0, {     0,      0}, {0xf0, 0x8a, 0xd6, 0xff}}},
    {{{   250,      0,   -192}, 0, {     0,      0}, {0x66, 0xf2, 0xb7, 0xff}}},
    {{{   177,   -176,    161}, 0, {     0,      0}, {0x5f, 0xb0, 0x16, 0xff}}},
    {{{   177,   -176,   -192}, 0, {     0,      0}, {0x3e, 0xae, 0xb7, 0xff}}},
    {{{   177,   -176,   -192}, 0, {     0,      0}, {0x3e, 0xae, 0xb7, 0xff}}},
    {{{   177,   -176,    161}, 0, {     0,      0}, {0x5f, 0xb0, 0x16, 0xff}}},
    {{{     0,   -249,    161}, 0, {     0,      0}, {0x10, 0x86, 0x1c, 0xff}}},
    {{{   177,   -176,   -192}, 0, {     0,      0}, {0x3e, 0xae, 0xb7, 0xff}}},
    {{{     0,   -249,    161}, 0, {     0,      0}, {0x10, 0x86, 0x1c, 0xff}}},
    {{{     0,   -249,   -192}, 0, {     0,      0}, {0xf0, 0x8a, 0xd6, 0xff}}},
};

// 0x0500DFB8
static const Vtx bullet_bill_seg5_vertex_0500DFB8[] = {
    {{{   139,   -138,    272}, 0, {     0,      0}, {0x4a, 0xbc, 0x4c, 0xff}}},
    {{{   177,   -176,    161}, 0, {     0,      0}, {0x5f, 0xb0, 0x16, 0xff}}},
    {{{   250,      0,    161}, 0, {     0,      0}, {0x7a, 0x07, 0x20, 0xff}}},
    {{{   250,      0,   -192}, 0, {     0,      0}, {0x66, 0xf2, 0xb7, 0xff}}},
    {{{   250,      0,    161}, 0, {     0,      0}, {0x7a, 0x07, 0x20, 0xff}}},
    {{{   177,   -176,    161}, 0, {     0,      0}, {0x5f, 0xb0, 0x16, 0xff}}},
    {{{   139,   -138,    272}, 0, {     0,      0}, {0x4a, 0xbc, 0x4c, 0xff}}},
    {{{     0,   -249,    161}, 0, {     0,      0}, {0x10, 0x86, 0x1c, 0xff}}},
    {{{   177,   -176,    161}, 0, {     0,      0}, {0x5f, 0xb0, 0x16, 0xff}}},
    {{{     0,   -195,    272}, 0, {     0,      0}, {0xfe, 0x9e, 0x50, 0xff}}},
    {{{  -176,   -176,    161}, 0, {     0,      0}, {0xb6, 0x9e, 0x1c, 0xff}}},
    {{{     0,   -249,    161}, 0, {     0,      0}, {0x10, 0x86, 0x1c, 0xff}}},
    {{{   139,   -138,    272}, 0, {     0,      0}, {0x4a, 0xbc, 0x4c, 0xff}}},
    {{{     0,   -195,    272}, 0, {     0,      0}, {0xfe, 0x9e, 0x50, 0xff}}},
    {{{     0,   -249,    161}, 0, {     0,      0}, {0x10, 0x86, 0x1c, 0xff}}},
};

// 0x0500E0A8
static const Vtx bullet_bill_seg5_vertex_0500E0A8[] = {
    {{{     0,   -249,   -192}, 0, {     0,      0}, {0xf0, 0x8a, 0xd6, 0xff}}},
    {{{     0,   -249,    161}, 0, {     0,      0}, {0x10, 0x86, 0x1c, 0xff}}},
    {{{  -176,   -176,    161}, 0, {     0,      0}, {0xb6, 0x9e, 0x1c, 0xff}}},
    {{{     0,   -249,   -192}, 0, {     0,      0}, {0xf0, 0x8a, 0xd6, 0xff}}},
    {{{  -176,   -176,    161}, 0, {     0,      0}, {0xb6, 0x9e, 0x1c, 0xff}}},
    {{{  -176,   -176,   -192}, 0, {     0,      0}, {0xd6, 0xe0, 0x8d, 0xff}}},
    {{{  -176,   -176,   -192}, 0, {     0,      0}, {0xd6, 0xe0, 0x8d, 0xff}}},
    {{{   177,    177,   -192}, 0, {     0,      0}, {0x52, 0x3e, 0xb7, 0xff}}},
    {{{   250,      0,   -192}, 0, {     0,      0}, {0x66, 0xf2, 0xb7, 0xff}}},
    {{{   177,    177,   -192}, 0, {     0,      0}, {0x52, 0x3e, 0xb7, 0xff}}},
    {{{   250,      0,    161}, 0, {     0,      0}, {0x7a, 0x07, 0x20, 0xff}}},
    {{{   250,      0,   -192}, 0, {     0,      0}, {0x66, 0xf2, 0xb7, 0xff}}},
    {{{   196,      0,    272}, 0, {     0,      0}, {0x62, 0x00, 0x4f, 0xff}}},
    {{{   139,   -138,    272}, 0, {     0,      0}, {0x4a, 0xbc, 0x4c, 0xff}}},
    {{{   250,      0,    161}, 0, {     0,      0}, {0x7a, 0x07, 0x20, 0xff}}},
};

// 0x0500E198
static const Vtx bullet_bill_seg5_vertex_0500E198[] = {
    {{{   177,    177,   -192}, 0, {     0,      0}, {0x52, 0x3e, 0xb7, 0xff}}},
    {{{   177,    177,    161}, 0, {     0,      0}, {0x4a, 0x62, 0x1c, 0xff}}},
    {{{   250,      0,    161}, 0, {     0,      0}, {0x7a, 0x07, 0x20, 0xff}}},
    {{{  -176,   -176,   -192}, 0, {     0,      0}, {0xd6, 0xe0, 0x8d, 0xff}}},
    {{{  -176,   -176,    161}, 0, {     0,      0}, {0xb6, 0x9e, 0x1c, 0xff}}},
    {{{  -249,      0,    161}, 0, {     0,      0}, {0x86, 0xef, 0x1c, 0xff}}},
    {{{  -176,   -176,   -192}, 0, {     0,      0}, {0xd6, 0xe0, 0x8d, 0xff}}},
    {{{     0,    250,   -192}, 0, {     0,      0}, {0x0e, 0x66, 0xb7, 0xff}}},
    {{{   177,    177,   -192}, 0, {     0,      0}, {0x52, 0x3e, 0xb7, 0xff}}},
    {{{     0,    250,   -192}, 0, {     0,      0}, {0x0e, 0x66, 0xb7, 0xff}}},
    {{{   177,    177,    161}, 0, {     0,      0}, {0x4a, 0x62, 0x1c, 0xff}}},
    {{{   177,    177,   -192}, 0, {     0,      0}, {0x52, 0x3e, 0xb7, 0xff}}},
    {{{     0,    196,    272}, 0, {     0,      0}, {0x02, 0x62, 0x50, 0xff}}},
    {{{   177,    177,    161}, 0, {     0,      0}, {0x4a, 0x62, 0x1c, 0xff}}},
    {{{     0,    250,    161}, 0, {     0,      0}, {0xef, 0x7a, 0x1c, 0xff}}},
};

// 0x0500E288
static const Vtx bullet_bill_seg5_vertex_0500E288[] = {
    {{{     0,    196,    272}, 0, {     0,      0}, {0x02, 0x62, 0x50, 0xff}}},
    {{{   139,    139,    272}, 0, {     0,      0}, {0x48, 0x3e, 0x53, 0xff}}},
    {{{   177,    177,    161}, 0, {     0,      0}, {0x4a, 0x62, 0x1c, 0xff}}},
    {{{     0,    250,   -192}, 0, {     0,      0}, {0x0e, 0x66, 0xb7, 0xff}}},
    {{{     0,    250,    161}, 0, {     0,      0}, {0xef, 0x7a, 0x1c, 0xff}}},
    {{{   177,    177,    161}, 0, {     0,      0}, {0x4a, 0x62, 0x1c, 0xff}}},
    {{{  -176,   -176,   -192}, 0, {     0,      0}, {0xd6, 0xe0, 0x8d, 0xff}}},
    {{{  -176,    177,   -192}, 0, {     0,      0}, {0xc2, 0x52, 0xb7, 0xff}}},
    {{{     0,    250,   -192}, 0, {     0,      0}, {0x0e, 0x66, 0xb7, 0xff}}},
    {{{  -176,    177,   -192}, 0, {     0,      0}, {0xc2, 0x52, 0xb7, 0xff}}},
    {{{     0,    250,    161}, 0, {     0,      0}, {0xef, 0x7a, 0x1c, 0xff}}},
    {{{     0,    250,   -192}, 0, {     0,      0}, {0x0e, 0x66, 0xb7, 0xff}}},
    {{{  -138,    139,    272}, 0, {     0,      0}, {0xbc, 0x47, 0x50, 0xff}}},
    {{{     0,    250,    161}, 0, {     0,      0}, {0xef, 0x7a, 0x1c, 0xff}}},
    {{{  -176,    177,    161}, 0, {     0,      0}, {0x9e, 0x4a, 0x1c, 0xff}}},
};

// 0x0500E378
static const Vtx bullet_bill_seg5_vertex_0500E378[] = {
    {{{  -138,    139,    272}, 0, {     0,      0}, {0xbc, 0x47, 0x50, 0xff}}},
    {{{     0,    196,    272}, 0, {     0,      0}, {0x02, 0x62, 0x50, 0xff}}},
    {{{     0,    250,    161}, 0, {     0,      0}, {0xef, 0x7a, 0x1c, 0xff}}},
    {{{  -176,    177,   -192}, 0, {     0,      0}, {0xc2, 0x52, 0xb7, 0xff}}},
    {{{  -176,    177,    161}, 0, {     0,      0}, {0x9e, 0x4a, 0x1c, 0xff}}},
    {{{     0,    250,    161}, 0, {     0,      0}, {0xef, 0x7a, 0x1c, 0xff}}},
    {{{  -176,   -176,   -192}, 0, {     0,      0}, {0xd6, 0xe0, 0x8d, 0xff}}},
    {{{  -249,      0,   -192}, 0, {     0,      0}, {0x8a, 0x10, 0xd6, 0xff}}},
    {{{  -176,    177,   -192}, 0, {     0,      0}, {0xc2, 0x52, 0xb7, 0xff}}},
    {{{  -249,      0,   -192}, 0, {     0,      0}, {0x8a, 0x10, 0xd6, 0xff}}},
    {{{  -176,    177,    161}, 0, {     0,      0}, {0x9e, 0x4a, 0x1c, 0xff}}},
    {{{  -176,    177,   -192}, 0, {     0,      0}, {0xc2, 0x52, 0xb7, 0xff}}},
    {{{  -249,      0,   -192}, 0, {     0,      0}, {0x8a, 0x10, 0xd6, 0xff}}},
    {{{  -249,      0,    161}, 0, {     0,      0}, {0x86, 0xef, 0x1c, 0xff}}},
    {{{  -176,    177,    161}, 0, {     0,      0}, {0x9e, 0x4a, 0x1c, 0xff}}},
};

// 0x0500E468
static const Vtx bullet_bill_seg5_vertex_0500E468[] = {
    {{{  -176,   -176,   -192}, 0, {     0,      0}, {0xd6, 0xe0, 0x8d, 0xff}}},
    {{{  -249,      0,    161}, 0, {     0,      0}, {0x86, 0xef, 0x1c, 0xff}}},
    {{{  -249,      0,   -192}, 0, {     0,      0}, {0x8a, 0x10, 0xd6, 0xff}}},
    {{{  -138,   -138,    272}, 0, {     0,      0}, {0xb9, 0xbd, 0x50, 0xff}}},
    {{{  -195,      0,    272}, 0, {     0,      0}, {0x9e, 0x02, 0x50, 0xff}}},
    {{{  -249,      0,    161}, 0, {     0,      0}, {0x86, 0xef, 0x1c, 0xff}}},
    {{{  -138,   -138,    272}, 0, {     0,      0}, {0xb9, 0xbd, 0x50, 0xff}}},
    {{{  -249,      0,    161}, 0, {     0,      0}, {0x86, 0xef, 0x1c, 0xff}}},
    {{{  -176,   -176,    161}, 0, {     0,      0}, {0xb6, 0x9e, 0x1c, 0xff}}},
    {{{     0,    101,    347}, 0, {     0,      0}, {0x08, 0x3c, 0x6f, 0xff}}},
    {{{    71,     71,    347}, 0, {     0,      0}, {0x1c, 0x1c, 0x78, 0xff}}},
    {{{   139,    139,    272}, 0, {     0,      0}, {0x48, 0x3e, 0x53, 0xff}}},
    {{{     0,   -195,    272}, 0, {     0,      0}, {0xfe, 0x9e, 0x50, 0xff}}},
    {{{  -138,   -138,    272}, 0, {     0,      0}, {0xb9, 0xbd, 0x50, 0xff}}},
    {{{  -176,   -176,    161}, 0, {     0,      0}, {0xb6, 0x9e, 0x1c, 0xff}}},
};

// 0x0500E558
static const Vtx bullet_bill_seg5_vertex_0500E558[] = {
    {{{   -70,     71,    347}, 0, {     0,      0}, {0xf0, 0x15, 0x7c, 0xff}}},
    {{{     0,    196,    272}, 0, {     0,      0}, {0x02, 0x62, 0x50, 0xff}}},
    {{{  -138,    139,    272}, 0, {     0,      0}, {0xbc, 0x47, 0x50, 0xff}}},
    {{{   -70,     71,    347}, 0, {     0,      0}, {0xf0, 0x15, 0x7c, 0xff}}},
    {{{     0,    101,    347}, 0, {     0,      0}, {0x08, 0x3c, 0x6f, 0xff}}},
    {{{     0,    196,    272}, 0, {     0,      0}, {0x02, 0x62, 0x50, 0xff}}},
    {{{     0,    101,    347}, 0, {     0,      0}, {0x08, 0x3c, 0x6f, 0xff}}},
    {{{   139,    139,    272}, 0, {     0,      0}, {0x48, 0x3e, 0x53, 0xff}}},
    {{{     0,    196,    272}, 0, {     0,      0}, {0x02, 0x62, 0x50, 0xff}}},
};

// 0x0500E5E8 - 0x0500E678
const Gfx bullet_bill_seg5_dl_0500E5E8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bullet_bill_seg5_texture_0500BAA8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bullet_bill_seg5_lights_0500BA90.l, 1),
    gsSPLight(&bullet_bill_seg5_lights_0500BA90.a, 2),
    gsSPVertex(bullet_bill_seg5_vertex_0500DAA8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bullet_bill_seg5_vertex_0500DB98, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  7,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500E678 - 0x0500E730
const Gfx bullet_bill_seg5_dl_0500E678[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bullet_bill_seg5_texture_0500CAA8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bullet_bill_seg5_vertex_0500DC28, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bullet_bill_seg5_vertex_0500DD18, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bullet_bill_seg5_vertex_0500DE08, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500E730 - 0x0500E8A8
const Gfx bullet_bill_seg5_dl_0500E730[] = {
    gsSPVertex(bullet_bill_seg5_vertex_0500DEC8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bullet_bill_seg5_vertex_0500DFB8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bullet_bill_seg5_vertex_0500E0A8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bullet_bill_seg5_vertex_0500E198, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bullet_bill_seg5_vertex_0500E288, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bullet_bill_seg5_vertex_0500E378, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bullet_bill_seg5_vertex_0500E468, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bullet_bill_seg5_vertex_0500E558, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  7,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500E8A8 - 0x0500E918
const Gfx bullet_bill_seg5_dl_0500E8A8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBA, G_CC_BLENDRGBA),
    gsSPNumLights(NUMLIGHTS_1),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bullet_bill_seg5_dl_0500E5E8),
    gsSPDisplayList(bullet_bill_seg5_dl_0500E678),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(bullet_bill_seg5_dl_0500E730),
    gsSPEndDisplayList(),
};
