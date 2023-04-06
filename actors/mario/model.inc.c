// Mario

// 0x04000000 # solid color blue - butt, left thigh, right thigh - all poly types
static const Lights1 mario_blue_lights_group = gdSPDefLights1(
    0x00, 0x00, 0x7f,
    0x00, 0x00, 0xff, 0x28, 0x28, 0x28
);

// 0x04000018 # solid color red - left & right arm, torso (tshirt part), caps - all poly types
static const Lights1 mario_red_lights_group = gdSPDefLights1(
    0x7f, 0x00, 0x00,
    0xff, 0x00, 0x00, 0x28, 0x28, 0x28
);

// 0x04000030 # solid color white - metal butt & left thigh - normal left & right hand closed & open (with cap too) and all wings - all poly types
static const Lights1 mario_white_lights_group = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x04000048 # solid color brown 1 - foot - all poly types
static const Lights1 mario_brown1_lights_group = gdSPDefLights1(
    0x39, 0x0e, 0x07,
    0x72, 0x1c, 0x0e, 0x28, 0x28, 0x28
);

// 0x04000060 # solid color beige skin - face (cap on and off dls) - all poly types
static const Lights1 mario_beige_lights_group = gdSPDefLights1(
    0x7f, 0x60, 0x3c,
    0xfe, 0xc1, 0x79, 0x28, 0x28, 0x28
);

// 0x04000078  # solid color brown 2 - hair - all poly types
static const Lights1 mario_brown2_lights_group = gdSPDefLights1(
    0x39, 0x03, 0x00,
    0x73, 0x06, 0x00, 0x28, 0x28, 0x28
);

// 0x04000090
ALIGNED8 static const u8 mario_texture_metal[] = {
#include "actors/mario/mario_metal.rgba16.inc.c"
};

// 0x04001090
ALIGNED8 static const u8 mario_texture_yellow_button[] = {
#include "actors/mario/mario_overalls_button.rgba16.inc.c"
};

// 0x04001890
ALIGNED8 static const u8 mario_texture_m_logo[] = {
#include "actors/mario/mario_logo.rgba16.inc.c"
};

// 0x04002090
ALIGNED8 static const u8 mario_texture_hair_sideburn[] = {
#include "actors/mario/mario_sideburn.rgba16.inc.c"
};

// 0x04002890
ALIGNED8 static const u8 mario_texture_mustache[] = {
#include "actors/mario/mario_mustache.rgba16.inc.c"
};

// 0x04003090
ALIGNED8 static const u8 mario_texture_eyes_front[] = {
#include "actors/mario/mario_eyes_center.rgba16.inc.c"
};

// 0x04003890
ALIGNED8 static const u8 mario_texture_eyes_half_closed[] = {
#include "actors/mario/mario_eyes_half_closed.rgba16.inc.c"
};

// 0x04004090
ALIGNED8 static const u8 mario_texture_eyes_closed[] = {
#include "actors/mario/mario_eyes_closed.rgba16.inc.c"
};

// Unreferenced
// 0x04004890
ALIGNED8 static const u8 mario_texture_eyes_closed_unused1[] = {
#include "actors/mario/mario_eyes_closed_unused_0.rgba16.inc.c"
};

// Unreferenced
// 0x04005090
ALIGNED8 static const u8 mario_texture_eyes_closed_unused2[] = {
#include "actors/mario/mario_eyes_closed_unused_1.rgba16.inc.c"
};

// 0x04005890
ALIGNED8 static const u8 mario_texture_eyes_right[] = {
#include "actors/mario/mario_eyes_left_unused.rgba16.inc.c"
};

// 0x04006090
ALIGNED8 static const u8 mario_texture_eyes_left[] = {
#include "actors/mario/mario_eyes_right_unused.rgba16.inc.c"
};

// 0x04006890
ALIGNED8 static const u8 mario_texture_eyes_up[] = {
#include "actors/mario/mario_eyes_up_unused.rgba16.inc.c"
};

// 0x04007090
ALIGNED8 static const u8 mario_texture_eyes_down[] = {
#include "actors/mario/mario_eyes_down_unused.rgba16.inc.c"
};

// 0x04007890
ALIGNED8 static const u8 mario_texture_eyes_dead[] = {
#include "actors/mario/mario_eyes_dead.rgba16.inc.c"
};

// 0x04008090
ALIGNED8 static const u8 mario_texture_wings_half_1[] = {
#include "actors/mario/mario_wing.rgba16.inc.c"
};

// 0x04009090
ALIGNED8 static const u8 mario_texture_wings_half_2[] = {
#include "actors/mario/mario_wing_tip.rgba16.inc.c"
};

// 0x0400A090
ALIGNED8 static const u8 mario_texture_metal_wings_half_1[] = {
#include "actors/mario/mario_metal_wing_unused.rgba16.inc.c"
};

// 0x0400B090
ALIGNED8 static const u8 mario_texture_metal_wings_half_2[] = {
#include "actors/mario/mario_metal_wing_tip_unused.rgba16.inc.c"
};

// 0x0400C090
static const Vtx mario_butt_dl_vertex_group1[] = {
    {{{    -9,     73,    -34}, 0, {     0,      0}, {0xc2, 0x6b, 0xe6, 0x00}}},
    {{{    -9,     73,     34}, 0, {     0,      0}, {0xbe, 0x6a, 0x12, 0x00}}},
    {{{    22,     82,    -26}, 0, {     0,      0}, {0x07, 0x7e, 0xf5, 0x00}}},
    {{{    73,     11,      0}, 0, {     0,      0}, {0x7e, 0x04, 0x00, 0x00}}},
    {{{    55,    -61,    -36}, 0, {     0,      0}, {0x67, 0xbe, 0xe1, 0x00}}},
    {{{    44,    -10,    -92}, 0, {     0,      0}, {0x4f, 0xf2, 0x9e, 0x00}}},
    {{{     7,     33,     95}, 0, {     0,      0}, {0xe6, 0x26, 0x75, 0x00}}},
    {{{    44,     -9,     92}, 0, {     0,      0}, {0x4f, 0xf2, 0x62, 0x00}}},
    {{{    43,     40,     91}, 0, {     0,      0}, {0x4e, 0x30, 0x57, 0x00}}},
    {{{     8,     -8,    -99}, 0, {     0,      0}, {0xf3, 0xee, 0x84, 0x00}}},
    {{{    36,    -51,    -79}, 0, {     0,      0}, {0x30, 0xb8, 0xa4, 0x00}}},
    {{{    26,    -74,    -46}, 0, {     0,      0}, {0x18, 0x88, 0xe2, 0x00}}},
    {{{    36,    -52,     79}, 0, {     0,      0}, {0x30, 0xb8, 0x5c, 0x00}}},
    {{{    26,    -74,     45}, 0, {     0,      0}, {0x12, 0x88, 0x22, 0x00}}},
    {{{    55,    -61,     36}, 0, {     0,      0}, {0x60, 0xb3, 0x1a, 0x00}}},
};

// 0x0400C180
static const Vtx mario_butt_dl_vertex_group2[] = {
    {{{    -5,     59,     72}, 0, {     0,      0}, {0xc0, 0x52, 0x47, 0x00}}},
    {{{    -9,     73,     34}, 0, {     0,      0}, {0xbe, 0x6a, 0x12, 0x00}}},
    {{{   -32,     40,     39}, 0, {     0,      0}, {0x8b, 0x25, 0x1d, 0x00}}},
    {{{    52,     70,     38}, 0, {     0,      0}, {0x53, 0x5d, 0x16, 0x00}}},
    {{{    22,     82,     26}, 0, {     0,      0}, {0x07, 0x7d, 0x11, 0x00}}},
    {{{    27,     72,     63}, 0, {     0,      0}, {0x06, 0x6c, 0x42, 0x00}}},
    {{{    43,     40,    -91}, 0, {     0,      0}, {0x4e, 0x30, 0xa9, 0x00}}},
    {{{    27,     72,    -63}, 0, {     0,      0}, {0x07, 0x6b, 0xbe, 0x00}}},
    {{{    52,     70,    -39}, 0, {     0,      0}, {0x59, 0x56, 0xe5, 0x00}}},
    {{{    -5,     59,    -72}, 0, {     0,      0}, {0xc0, 0x52, 0xb9, 0x00}}},
    {{{   -17,     32,    -79}, 0, {     0,      0}, {0xa4, 0x23, 0xb1, 0x00}}},
    {{{   -32,     40,    -39}, 0, {     0,      0}, {0x89, 0x25, 0xeb, 0x00}}},
    {{{    73,     11,      0}, 0, {     0,      0}, {0x7e, 0x04, 0x00, 0x00}}},
    {{{    43,     40,     91}, 0, {     0,      0}, {0x4e, 0x30, 0x57, 0x00}}},
};

// 0x0400C260
static const Vtx mario_butt_dl_vertex_group3[] = {
    {{{   -14,    -74,      0}, 0, {     0,      0}, {0xcb, 0x8d, 0x00, 0x00}}},
    {{{    -9,    -69,     55}, 0, {     0,      0}, {0xcd, 0x97, 0x30, 0x00}}},
    {{{   -31,    -51,     42}, 0, {     0,      0}, {0x8d, 0xd7, 0x1f, 0x00}}},
    {{{    26,    -74,    -46}, 0, {     0,      0}, {0x18, 0x88, 0xe2, 0x00}}},
    {{{    -9,    -69,    -55}, 0, {     0,      0}, {0xcd, 0x97, 0xd0, 0x00}}},
    {{{     4,    -48,    -86}, 0, {     0,      0}, {0xd8, 0xba, 0x9f, 0x00}}},
    {{{     4,    -49,     86}, 0, {     0,      0}, {0xd8, 0xb9, 0x61, 0x00}}},
    {{{    26,    -74,     45}, 0, {     0,      0}, {0x12, 0x88, 0x22, 0x00}}},
    {{{   -32,     40,     39}, 0, {     0,      0}, {0x8b, 0x25, 0x1d, 0x00}}},
    {{{    -9,     73,     34}, 0, {     0,      0}, {0xbe, 0x6a, 0x12, 0x00}}},
    {{{   -32,     40,    -39}, 0, {     0,      0}, {0x89, 0x25, 0xeb, 0x00}}},
    {{{    27,     72,     63}, 0, {     0,      0}, {0x06, 0x6c, 0x42, 0x00}}},
    {{{    -5,     59,     72}, 0, {     0,      0}, {0xc0, 0x52, 0x47, 0x00}}},
    {{{    52,     70,    -39}, 0, {     0,      0}, {0x59, 0x56, 0xe5, 0x00}}},
    {{{    22,     82,    -26}, 0, {     0,      0}, {0x07, 0x7e, 0xf5, 0x00}}},
    {{{    52,     70,     38}, 0, {     0,      0}, {0x53, 0x5d, 0x16, 0x00}}},
};

// 0x0400C360
static const Vtx mario_butt_dl_vertex_group4[] = {
    {{{     6,     33,    -95}, 0, {     0,      0}, {0xe6, 0x26, 0x8b, 0x00}}},
    {{{    27,     72,    -63}, 0, {     0,      0}, {0x07, 0x6b, 0xbe, 0x00}}},
    {{{    43,     40,    -91}, 0, {     0,      0}, {0x4e, 0x30, 0xa9, 0x00}}},
    {{{   -31,    -51,     42}, 0, {     0,      0}, {0x8d, 0xd7, 0x1f, 0x00}}},
    {{{   -31,    -51,    -42}, 0, {     0,      0}, {0x91, 0xd0, 0xdc, 0x00}}},
    {{{   -14,    -74,      0}, 0, {     0,      0}, {0xcb, 0x8d, 0x00, 0x00}}},
    {{{    -9,    -69,    -55}, 0, {     0,      0}, {0xcd, 0x97, 0xd0, 0x00}}},
    {{{    73,     11,      0}, 0, {     0,      0}, {0x7e, 0x04, 0x00, 0x00}}},
    {{{    44,    -10,    -92}, 0, {     0,      0}, {0x4f, 0xf2, 0x9e, 0x00}}},
    {{{    52,     70,     38}, 0, {     0,      0}, {0x53, 0x5d, 0x16, 0x00}}},
    {{{    52,     70,    -39}, 0, {     0,      0}, {0x59, 0x56, 0xe5, 0x00}}},
    {{{    43,     40,     91}, 0, {     0,      0}, {0x4e, 0x30, 0x57, 0x00}}},
    {{{    44,     -9,     92}, 0, {     0,      0}, {0x4f, 0xf2, 0x62, 0x00}}},
    {{{     7,     33,     95}, 0, {     0,      0}, {0xe6, 0x26, 0x75, 0x00}}},
    {{{    27,     72,     63}, 0, {     0,      0}, {0x06, 0x6c, 0x42, 0x00}}},
};

// 0x0400C450
static const Vtx mario_butt_dl_vertex_group5[] = {
    {{{    -5,     59,    -72}, 0, {     0,      0}, {0xc0, 0x52, 0xb9, 0x00}}},
    {{{    -9,     73,    -34}, 0, {     0,      0}, {0xc2, 0x6b, 0xe6, 0x00}}},
    {{{    27,     72,    -63}, 0, {     0,      0}, {0x07, 0x6b, 0xbe, 0x00}}},
    {{{   -32,     40,    -39}, 0, {     0,      0}, {0x89, 0x25, 0xeb, 0x00}}},
    {{{   -32,     40,     39}, 0, {     0,      0}, {0x8b, 0x25, 0x1d, 0x00}}},
    {{{   -18,     32,     79}, 0, {     0,      0}, {0xa4, 0x23, 0x4f, 0x00}}},
    {{{    -5,     59,     72}, 0, {     0,      0}, {0xc0, 0x52, 0x47, 0x00}}},
    {{{    22,     82,    -26}, 0, {     0,      0}, {0x07, 0x7e, 0xf5, 0x00}}},
    {{{    52,     70,    -39}, 0, {     0,      0}, {0x59, 0x56, 0xe5, 0x00}}},
    {{{    27,     72,     63}, 0, {     0,      0}, {0x06, 0x6c, 0x42, 0x00}}},
    {{{    43,     40,     91}, 0, {     0,      0}, {0x4e, 0x30, 0x57, 0x00}}},
    {{{    52,     70,     38}, 0, {     0,      0}, {0x53, 0x5d, 0x16, 0x00}}},
    {{{     8,     -8,     99}, 0, {     0,      0}, {0xf3, 0xef, 0x7c, 0x00}}},
    {{{    36,    -52,     79}, 0, {     0,      0}, {0x30, 0xb8, 0x5c, 0x00}}},
    {{{    44,     -9,     92}, 0, {     0,      0}, {0x4f, 0xf2, 0x62, 0x00}}},
};

// 0x0400C540
static const Vtx mario_butt_dl_vertex_group6[] = {
    {{{     6,     33,    -95}, 0, {     0,      0}, {0xe6, 0x26, 0x8b, 0x00}}},
    {{{    43,     40,    -91}, 0, {     0,      0}, {0x4e, 0x30, 0xa9, 0x00}}},
    {{{    44,    -10,    -92}, 0, {     0,      0}, {0x4f, 0xf2, 0x9e, 0x00}}},
    {{{    44,     -9,     92}, 0, {     0,      0}, {0x4f, 0xf2, 0x62, 0x00}}},
    {{{    36,    -52,     79}, 0, {     0,      0}, {0x30, 0xb8, 0x5c, 0x00}}},
    {{{    55,    -61,     36}, 0, {     0,      0}, {0x60, 0xb3, 0x1a, 0x00}}},
    {{{   -18,     32,     79}, 0, {     0,      0}, {0xa4, 0x23, 0x4f, 0x00}}},
    {{{     7,     33,     95}, 0, {     0,      0}, {0xe6, 0x26, 0x75, 0x00}}},
    {{{    -5,     59,     72}, 0, {     0,      0}, {0xc0, 0x52, 0x47, 0x00}}},
    {{{   -20,     -7,     81}, 0, {     0,      0}, {0xa3, 0xf9, 0x55, 0x00}}},
    {{{     8,     -8,     99}, 0, {     0,      0}, {0xf3, 0xef, 0x7c, 0x00}}},
    {{{     4,    -49,     86}, 0, {     0,      0}, {0xd8, 0xb9, 0x61, 0x00}}},
    {{{   -20,     -8,    -81}, 0, {     0,      0}, {0xa3, 0xf8, 0xab, 0x00}}},
    {{{     8,     -8,    -99}, 0, {     0,      0}, {0xf3, 0xee, 0x84, 0x00}}},
    {{{     4,    -48,    -86}, 0, {     0,      0}, {0xd8, 0xba, 0x9f, 0x00}}},
};

// 0x0400C630
static const Vtx mario_butt_dl_vertex_group7[] = {
    {{{    -5,     59,    -72}, 0, {     0,      0}, {0xc0, 0x52, 0xb9, 0x00}}},
    {{{     6,     33,    -95}, 0, {     0,      0}, {0xe6, 0x26, 0x8b, 0x00}}},
    {{{   -17,     32,    -79}, 0, {     0,      0}, {0xa4, 0x23, 0xb1, 0x00}}},
    {{{   -20,     -8,    -81}, 0, {     0,      0}, {0xa3, 0xf8, 0xab, 0x00}}},
    {{{     7,     33,     95}, 0, {     0,      0}, {0xe6, 0x26, 0x75, 0x00}}},
    {{{   -18,     32,     79}, 0, {     0,      0}, {0xa4, 0x23, 0x4f, 0x00}}},
    {{{   -20,     -7,     81}, 0, {     0,      0}, {0xa3, 0xf9, 0x55, 0x00}}},
    {{{   -32,     40,    -39}, 0, {     0,      0}, {0x89, 0x25, 0xeb, 0x00}}},
    {{{    55,    -61,     36}, 0, {     0,      0}, {0x60, 0xb3, 0x1a, 0x00}}},
    {{{    73,     11,      0}, 0, {     0,      0}, {0x7e, 0x04, 0x00, 0x00}}},
    {{{    44,     -9,     92}, 0, {     0,      0}, {0x4f, 0xf2, 0x62, 0x00}}},
    {{{    55,    -61,    -36}, 0, {     0,      0}, {0x67, 0xbe, 0xe1, 0x00}}},
    {{{    36,    -51,    -79}, 0, {     0,      0}, {0x30, 0xb8, 0xa4, 0x00}}},
    {{{    44,    -10,    -92}, 0, {     0,      0}, {0x4f, 0xf2, 0x9e, 0x00}}},
    {{{     8,     -8,    -99}, 0, {     0,      0}, {0xf3, 0xee, 0x84, 0x00}}},
};

// 0x0400C720
static const Vtx mario_butt_dl_vertex_group8[] = {
    {{{     8,     -8,     99}, 0, {     0,      0}, {0xf3, 0xef, 0x7c, 0x00}}},
    {{{     4,    -49,     86}, 0, {     0,      0}, {0xd8, 0xb9, 0x61, 0x00}}},
    {{{    36,    -52,     79}, 0, {     0,      0}, {0x30, 0xb8, 0x5c, 0x00}}},
    {{{    -9,     73,    -34}, 0, {     0,      0}, {0xc2, 0x6b, 0xe6, 0x00}}},
    {{{    22,     82,    -26}, 0, {     0,      0}, {0x07, 0x7e, 0xf5, 0x00}}},
    {{{    27,     72,    -63}, 0, {     0,      0}, {0x07, 0x6b, 0xbe, 0x00}}},
    {{{    27,     72,     63}, 0, {     0,      0}, {0x06, 0x6c, 0x42, 0x00}}},
    {{{    -5,     59,     72}, 0, {     0,      0}, {0xc0, 0x52, 0x47, 0x00}}},
    {{{     7,     33,     95}, 0, {     0,      0}, {0xe6, 0x26, 0x75, 0x00}}},
    {{{     6,     33,    -95}, 0, {     0,      0}, {0xe6, 0x26, 0x8b, 0x00}}},
    {{{    -5,     59,    -72}, 0, {     0,      0}, {0xc0, 0x52, 0xb9, 0x00}}},
    {{{    22,     82,     26}, 0, {     0,      0}, {0x07, 0x7d, 0x11, 0x00}}},
    {{{    52,     70,     38}, 0, {     0,      0}, {0x53, 0x5d, 0x16, 0x00}}},
    {{{    -9,     73,     34}, 0, {     0,      0}, {0xbe, 0x6a, 0x12, 0x00}}},
    {{{   -32,     40,    -39}, 0, {     0,      0}, {0x89, 0x25, 0xeb, 0x00}}},
    {{{    26,    -74,     45}, 0, {     0,      0}, {0x12, 0x88, 0x22, 0x00}}},
};

// 0x0400C820
static const Vtx mario_butt_dl_vertex_group9[] = {
    {{{     4,    -48,    -86}, 0, {     0,      0}, {0xd8, 0xba, 0x9f, 0x00}}},
    {{{    36,    -51,    -79}, 0, {     0,      0}, {0x30, 0xb8, 0xa4, 0x00}}},
    {{{    26,    -74,    -46}, 0, {     0,      0}, {0x18, 0x88, 0xe2, 0x00}}},
    {{{   -20,     -7,     81}, 0, {     0,      0}, {0xa3, 0xf9, 0x55, 0x00}}},
    {{{   -18,     32,     79}, 0, {     0,      0}, {0xa4, 0x23, 0x4f, 0x00}}},
    {{{   -32,     40,     39}, 0, {     0,      0}, {0x8b, 0x25, 0x1d, 0x00}}},
    {{{     8,     -8,    -99}, 0, {     0,      0}, {0xf3, 0xee, 0x84, 0x00}}},
    {{{     7,     33,     95}, 0, {     0,      0}, {0xe6, 0x26, 0x75, 0x00}}},
    {{{     8,     -8,     99}, 0, {     0,      0}, {0xf3, 0xef, 0x7c, 0x00}}},
    {{{    44,     -9,     92}, 0, {     0,      0}, {0x4f, 0xf2, 0x62, 0x00}}},
    {{{    -9,     73,     34}, 0, {     0,      0}, {0xbe, 0x6a, 0x12, 0x00}}},
    {{{    22,     82,     26}, 0, {     0,      0}, {0x07, 0x7d, 0x11, 0x00}}},
    {{{    22,     82,    -26}, 0, {     0,      0}, {0x07, 0x7e, 0xf5, 0x00}}},
    {{{    -9,    -69,    -55}, 0, {     0,      0}, {0xcd, 0x97, 0xd0, 0x00}}},
    {{{   -31,    -51,    -42}, 0, {     0,      0}, {0x91, 0xd0, 0xdc, 0x00}}},
    {{{   -20,     -8,    -81}, 0, {     0,      0}, {0xa3, 0xf8, 0xab, 0x00}}},
};

// 0x0400C920
static const Vtx mario_butt_dl_vertex_group10[] = {
    {{{   -31,    -51,     42}, 0, {     0,      0}, {0x8d, 0xd7, 0x1f, 0x00}}},
    {{{    -9,    -69,     55}, 0, {     0,      0}, {0xcd, 0x97, 0x30, 0x00}}},
    {{{     4,    -49,     86}, 0, {     0,      0}, {0xd8, 0xb9, 0x61, 0x00}}},
    {{{   -20,     -7,     81}, 0, {     0,      0}, {0xa3, 0xf9, 0x55, 0x00}}},
    {{{   -32,     40,     39}, 0, {     0,      0}, {0x8b, 0x25, 0x1d, 0x00}}},
    {{{   -32,     40,    -39}, 0, {     0,      0}, {0x89, 0x25, 0xeb, 0x00}}},
    {{{   -20,     -8,    -81}, 0, {     0,      0}, {0xa3, 0xf8, 0xab, 0x00}}},
    {{{   -31,    -51,    -42}, 0, {     0,      0}, {0x91, 0xd0, 0xdc, 0x00}}},
    {{{    55,    -61,     36}, 0, {     0,      0}, {0x60, 0xb3, 0x1a, 0x00}}},
    {{{    26,    -74,     45}, 0, {     0,      0}, {0x12, 0x88, 0x22, 0x00}}},
    {{{    26,    -74,    -46}, 0, {     0,      0}, {0x18, 0x88, 0xe2, 0x00}}},
    {{{   -14,    -74,      0}, 0, {     0,      0}, {0xcb, 0x8d, 0x00, 0x00}}},
    {{{    -9,    -69,    -55}, 0, {     0,      0}, {0xcd, 0x97, 0xd0, 0x00}}},
    {{{    55,    -61,    -36}, 0, {     0,      0}, {0x67, 0xbe, 0xe1, 0x00}}},
};

// 0x0400CA00 - 0x0400CC98
const Gfx mario_butt_dl[] = {
    gsSPVertex(mario_butt_dl_vertex_group1, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9,  5, 10, 0x0),
    gsSP2Triangles( 4, 11, 10, 0x0, 12, 13, 14, 0x0),
    gsSPVertex(mario_butt_dl_vertex_group2, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12,  3, 13, 0x0,  6,  8, 12, 0x0),
    gsSPVertex(mario_butt_dl_vertex_group3, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  1,  7, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles(11,  9, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(mario_butt_dl_vertex_group4, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  2, 0x0),
    gsSP2Triangles( 9,  7, 10, 0x0, 11, 12,  7, 0x0),
    gsSP1Triangle(13, 11, 14, 0x0),
    gsSPVertex(mario_butt_dl_vertex_group5, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  2,  7,  8, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0, 12, 13, 14, 0x0),
    gsSPVertex(mario_butt_dl_vertex_group6, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10,  7, 0x0),
    gsSP2Triangles(11, 10,  9, 0x0, 12, 13, 14, 0x0),
    gsSP1Triangle( 0, 13, 12, 0x0),
    gsSPVertex(mario_butt_dl_vertex_group7, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  2,  3, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  9,  8, 11, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0, 13, 14,  1, 0x0),
    gsSPVertex(mario_butt_dl_vertex_group8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10,  5, 0x0),
    gsSP2Triangles( 4, 11, 12, 0x0,  6, 11, 13, 0x0),
    gsSP2Triangles(13,  3, 14, 0x0, 15,  2,  1, 0x0),
    gsSPVertex(mario_butt_dl_vertex_group9, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 1,  0,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0,  0, 13, 14, 0x0),
    gsSP1Triangle(15,  0, 14, 0x0),
    gsSPVertex(mario_butt_dl_vertex_group10, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 3,  4,  0, 0x0,  0,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  5,  7,  0, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  9, 11, 10, 0x0),
    gsSP2Triangles( 9,  1, 11, 0x0, 11, 12, 10, 0x0),
    gsSP1Triangle(10, 13,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0400CC98 - 0x0400CCC8
const Gfx mario_butt[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPLight(&mario_blue_lights_group.l, 1),
    gsSPLight(&mario_blue_lights_group.a, 2),
    gsSPDisplayList(mario_butt_dl),
    gsSPEndDisplayList(),
};

// 0x0400CCC8 - 0x0400CD40
const Gfx mario_metal_butt[] = {
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_DECALFADE, G_CC_DECALFADE),
    gsDPLoadTextureBlock(mario_texture_metal, G_IM_FMT_RGBA, G_IM_SIZ_16b, 64, 32, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_WRAP | G_TX_NOMIRROR, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_ON),
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_butt_dl),
    gsSPEndDisplayList(),
};

// 0x0400CD40
static const Vtx mario_left_arm_shared_dl_vertex_group1[] = {
    {{{     7,     27,     20}, 0, {     0,      0}, {0xda, 0x61, 0x47, 0x00}}},
    {{{    59,     20,     15}, 0, {     0,      0}, {0x44, 0x4a, 0x4c, 0x00}}},
    {{{    59,     21,    -21}, 0, {     0,      0}, {0x37, 0x67, 0xd0, 0x00}}},
    {{{     6,    -16,     32}, 0, {     0,      0}, {0xd5, 0xdc, 0x71, 0x00}}},
    {{{    58,    -15,     25}, 0, {     0,      0}, {0x32, 0xc6, 0x64, 0x00}}},
    {{{     5,    -42,     -5}, 0, {     0,      0}, {0xd3, 0x8a, 0xff, 0x00}}},
    {{{    57,    -36,     -5}, 0, {     0,      0}, {0x3e, 0x93, 0xef, 0x00}}},
    {{{     6,    -13,    -42}, 0, {     0,      0}, {0xd6, 0xde, 0x8e, 0x00}}},
    {{{    58,    -13,    -34}, 0, {     0,      0}, {0x3b, 0xfb, 0x90, 0x00}}},
    {{{     7,     29,    -26}, 0, {     0,      0}, {0xda, 0x62, 0xbb, 0x00}}},
    {{{    -8,     17,    -17}, 0, {     0,      0}, {0x90, 0x2a, 0xd6, 0x00}}},
    {{{    -8,     16,     10}, 0, {     0,      0}, {0x9a, 0x44, 0x1d, 0x00}}},
    {{{    -8,     -9,    -27}, 0, {     0,      0}, {0x8e, 0xe8, 0xce, 0x00}}},
    {{{    -9,    -26,     -5}, 0, {     0,      0}, {0x96, 0xbd, 0x0e, 0x00}}},
    {{{    -8,    -11,     18}, 0, {     0,      0}, {0x8b, 0xff, 0x2f, 0x00}}},
};

// 0x0400CE30
static const Vtx mario_left_arm_shared_dl_vertex_group2[] = {
    {{{    68,     13,    -16}, 0, {     0,      0}, {0x6a, 0x38, 0xd9, 0x00}}},
    {{{    68,    -10,    -25}, 0, {     0,      0}, {0x75, 0xe7, 0xd9, 0x00}}},
    {{{    58,    -13,    -34}, 0, {     0,      0}, {0x3b, 0xfb, 0x90, 0x00}}},
    {{{    59,     20,     15}, 0, {     0,      0}, {0x44, 0x4a, 0x4c, 0x00}}},
    {{{    68,     12,      9}, 0, {     0,      0}, {0x78, 0x1f, 0x1b, 0x00}}},
    {{{    67,    -12,     16}, 0, {     0,      0}, {0x6f, 0xe8, 0x37, 0x00}}},
    {{{    57,    -36,     -5}, 0, {     0,      0}, {0x3e, 0x93, 0xef, 0x00}}},
    {{{    67,    -26,     -5}, 0, {     0,      0}, {0x6e, 0xc2, 0xfe, 0x00}}},
    {{{    58,    -15,     25}, 0, {     0,      0}, {0x32, 0xc6, 0x64, 0x00}}},
    {{{    59,     21,    -21}, 0, {     0,      0}, {0x37, 0x67, 0xd0, 0x00}}},
    {{{     7,     27,     20}, 0, {     0,      0}, {0xda, 0x61, 0x47, 0x00}}},
    {{{    -8,    -11,     18}, 0, {     0,      0}, {0x8b, 0xff, 0x2f, 0x00}}},
    {{{     6,    -16,     32}, 0, {     0,      0}, {0xd5, 0xdc, 0x71, 0x00}}},
    {{{    -9,    -26,     -5}, 0, {     0,      0}, {0x96, 0xbd, 0x0e, 0x00}}},
    {{{     5,    -42,     -5}, 0, {     0,      0}, {0xd3, 0x8a, 0xff, 0x00}}},
};

// 0x0400CF20
static const Vtx mario_left_arm_shared_dl_vertex_group3[] = {
    {{{     5,    -42,     -5}, 0, {     0,      0}, {0xd3, 0x8a, 0xff, 0x00}}},
    {{{    -8,     -9,    -27}, 0, {     0,      0}, {0x8e, 0xe8, 0xce, 0x00}}},
    {{{     6,    -13,    -42}, 0, {     0,      0}, {0xd6, 0xde, 0x8e, 0x00}}},
    {{{    -8,     17,    -17}, 0, {     0,      0}, {0x90, 0x2a, 0xd6, 0x00}}},
    {{{     7,     29,    -26}, 0, {     0,      0}, {0xda, 0x62, 0xbb, 0x00}}},
    {{{    -8,     16,     10}, 0, {     0,      0}, {0x9a, 0x44, 0x1d, 0x00}}},
    {{{     7,     27,     20}, 0, {     0,      0}, {0xda, 0x61, 0x47, 0x00}}},
    {{{    58,    -13,    -34}, 0, {     0,      0}, {0x3b, 0xfb, 0x90, 0x00}}},
    {{{    57,    -36,     -5}, 0, {     0,      0}, {0x3e, 0x93, 0xef, 0x00}}},
    {{{     6,    -16,     32}, 0, {     0,      0}, {0xd5, 0xdc, 0x71, 0x00}}},
    {{{    58,    -15,     25}, 0, {     0,      0}, {0x32, 0xc6, 0x64, 0x00}}},
    {{{    59,     20,     15}, 0, {     0,      0}, {0x44, 0x4a, 0x4c, 0x00}}},
    {{{    59,     21,    -21}, 0, {     0,      0}, {0x37, 0x67, 0xd0, 0x00}}},
    {{{    -8,    -11,     18}, 0, {     0,      0}, {0x8b, 0xff, 0x2f, 0x00}}},
};

// 0x0400D000
static const Vtx mario_left_arm_shared_dl_vertex_group4[] = {
    {{{    68,     12,      9}, 0, {     0,      0}, {0x78, 0x1f, 0x1b, 0x00}}},
    {{{    68,    -10,    -25}, 0, {     0,      0}, {0x75, 0xe7, 0xd9, 0x00}}},
    {{{    68,     13,    -16}, 0, {     0,      0}, {0x6a, 0x38, 0xd9, 0x00}}},
    {{{    67,    -12,     16}, 0, {     0,      0}, {0x6f, 0xe8, 0x37, 0x00}}},
    {{{    67,    -26,     -5}, 0, {     0,      0}, {0x6e, 0xc2, 0xfe, 0x00}}},
    {{{    -8,     17,    -17}, 0, {     0,      0}, {0x90, 0x2a, 0xd6, 0x00}}},
    {{{    -8,     -9,    -27}, 0, {     0,      0}, {0x8e, 0xe8, 0xce, 0x00}}},
    {{{    -8,    -11,     18}, 0, {     0,      0}, {0x8b, 0xff, 0x2f, 0x00}}},
    {{{    -9,    -26,     -5}, 0, {     0,      0}, {0x96, 0xbd, 0x0e, 0x00}}},
};

// 0x0400D090 - 0x0400D1D8
const Gfx mario_left_arm_shared_dl[] = {
    gsSPVertex(mario_left_arm_shared_dl_vertex_group1, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 5,  6,  4, 0x0,  7,  8,  6, 0x0),
    gsSP2Triangles( 9,  2,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 7, 12, 10, 0x0,  5, 13, 12, 0x0),
    gsSP2Triangles( 3, 14, 13, 0x0,  0, 11, 14, 0x0),
    gsSPVertex(mario_left_arm_shared_dl_vertex_group2, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 5,  4,  3, 0x0,  6,  7,  5, 0x0),
    gsSP2Triangles( 1,  7,  6, 0x0,  2,  1,  6, 0x0),
    gsSP2Triangles( 6,  5,  8, 0x0,  8,  5,  3, 0x0),
    gsSP2Triangles( 3,  0,  9, 0x0,  9,  0,  2, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 13, 14, 0x0),
    gsSPVertex(mario_left_arm_shared_dl_vertex_group3, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  2,  4,  7, 0x0),
    gsSP2Triangles( 0,  2,  8, 0x0,  9,  0, 10, 0x0),
    gsSP2Triangles( 6,  9, 11, 0x0,  4,  6, 12, 0x0),
    gsSP1Triangle(13,  5,  3, 0x0),
    gsSPVertex(mario_left_arm_shared_dl_vertex_group4, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 3,  4,  1, 0x0,  5,  6,  7, 0x0),
    gsSP1Triangle( 6,  8,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0400D1D8 - 0x0400D1F8
const Gfx mario_left_arm[] = {
    gsSPLight(&mario_red_lights_group.l, 1),
    gsSPLight(&mario_red_lights_group.a, 2),
    gsSPDisplayList(mario_left_arm_shared_dl),
    gsSPEndDisplayList(),
};

// 0x0400D1F8
static const Vtx mario_left_forearm_shared_dl_vertex[] = {
    {{{    -2,     19,     16}, 0, {     0,      0}, {0xdb, 0x61, 0x48, 0x00}}},
    {{{    47,     14,     13}, 0, {     0,      0}, {0x44, 0x44, 0x51, 0x00}}},
    {{{    47,     16,    -19}, 0, {     0,      0}, {0x45, 0x61, 0xd6, 0x00}}},
    {{{    -3,    -17,     26}, 0, {     0,      0}, {0xd7, 0xdb, 0x71, 0x00}}},
    {{{    46,    -17,     22}, 0, {     0,      0}, {0x41, 0xc7, 0x5c, 0x00}}},
    {{{    -3,    -38,     -4}, 0, {     0,      0}, {0xd4, 0x89, 0xfe, 0x00}}},
    {{{    45,    -34,     -4}, 0, {     0,      0}, {0x3f, 0x95, 0xe8, 0x00}}},
    {{{    -3,    -14,    -34}, 0, {     0,      0}, {0xd7, 0xde, 0x8e, 0x00}}},
    {{{    46,    -14,    -30}, 0, {     0,      0}, {0x42, 0xf4, 0x95, 0x00}}},
    {{{    -2,     21,    -21}, 0, {     0,      0}, {0xdb, 0x63, 0xbb, 0x00}}},
    {{{   -14,     13,    -15}, 0, {     0,      0}, {0x93, 0x2c, 0xd3, 0x00}}},
    {{{   -14,     11,     10}, 0, {     0,      0}, {0x93, 0x3b, 0x19, 0x00}}},
    {{{   -15,    -11,    -24}, 0, {     0,      0}, {0x9a, 0xde, 0xbe, 0x00}}},
    {{{   -15,    -27,     -4}, 0, {     0,      0}, {0x8b, 0xd2, 0x0a, 0x00}}},
    {{{   -15,    -13,     17}, 0, {     0,      0}, {0x9b, 0xfb, 0x4c, 0x00}}},
    {{{    58,     -7,     -3}, 0, {     0,      0}, {0x7e, 0xfc, 0x00, 0x00}}},
};

// 0x0400D2F8 - 0x0400D3E8
const Gfx mario_left_forearm_shared_dl[] = {
    gsSPVertex(mario_left_forearm_shared_dl_vertex, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 5,  6,  4, 0x0,  7,  8,  6, 0x0),
    gsSP2Triangles( 9,  2,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 7, 12, 10, 0x0,  5, 13, 12, 0x0),
    gsSP2Triangles( 3, 14, 13, 0x0,  0, 11, 14, 0x0),
    gsSP2Triangles( 8, 15,  6, 0x0,  6, 15,  4, 0x0),
    gsSP2Triangles( 4, 15,  1, 0x0,  1, 15,  2, 0x0),
    gsSP2Triangles( 2, 15,  8, 0x0,  0, 14,  3, 0x0),
    gsSP2Triangles( 3, 13,  5, 0x0,  5, 12,  7, 0x0),
    gsSP2Triangles( 7, 10,  9, 0x0,  9, 11,  0, 0x0),
    gsSP2Triangles( 7,  9,  8, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 3,  5,  4, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 9,  0,  2, 0x0, 12, 13, 10, 0x0),
    gsSP2Triangles(13, 11, 10, 0x0, 13, 14, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x0400D3E8
static const Vtx mario_left_hand_closed_shared_dl_vertex_group1[] = {
    {{{    36,      6,     42}, 0, {     0,      0}, {0xe1, 0xf0, 0x79, 0x00}}},
    {{{    17,     -1,     27}, 0, {     0,      0}, {0x00, 0xfd, 0x7f, 0x00}}},
    {{{    34,    -41,      3}, 0, {     0,      0}, {0x42, 0xa3, 0x36, 0x00}}},
    {{{    26,    -25,    -32}, 0, {     0,      0}, {0x09, 0xc7, 0x8f, 0x00}}},
    {{{     4,     24,    -31}, 0, {     0,      0}, {0xd5, 0x3f, 0x9c, 0x00}}},
    {{{    33,      7,    -44}, 0, {     0,      0}, {0xc6, 0xfe, 0x90, 0x00}}},
    {{{    -1,     39,      5}, 0, {     0,      0}, {0xea, 0x6e, 0x3a, 0x00}}},
    {{{     5,     -6,     36}, 0, {     0,      0}, {0x44, 0x1b, 0x67, 0x00}}},
    {{{   -10,     26,    -38}, 0, {     0,      0}, {0xf0, 0x41, 0x95, 0x00}}},
    {{{    17,    -38,    -39}, 0, {     0,      0}, {0x1a, 0xb7, 0x9d, 0x00}}},
    {{{    86,     29,     41}, 0, {     0,      0}, {0x4b, 0x20, 0x60, 0x00}}},
    {{{    59,     81,      8}, 0, {     0,      0}, {0x11, 0x7a, 0x1d, 0x00}}},
    {{{   107,    -26,      4}, 0, {     0,      0}, {0x67, 0xc5, 0x2b, 0x00}}},
    {{{    61,     12,    -60}, 0, {     0,      0}, {0x00, 0x21, 0x86, 0x00}}},
    {{{    90,     17,    -46}, 0, {     0,      0}, {0x43, 0x35, 0xa3, 0x00}}},
    {{{   100,     -6,    -46}, 0, {     0,      0}, {0x63, 0x0a, 0xb3, 0x00}}},
};

// 0x0400D4E8
static const Vtx mario_left_hand_closed_shared_dl_vertex_group2[] = {
    {{{    90,     17,    -46}, 0, {     0,      0}, {0x43, 0x35, 0xa3, 0x00}}},
    {{{    57,     55,    -37}, 0, {     0,      0}, {0x22, 0x54, 0xa9, 0x00}}},
    {{{    99,     40,    -16}, 0, {     0,      0}, {0x6c, 0x3c, 0xe5, 0x00}}},
    {{{    32,     63,    -28}, 0, {     0,      0}, {0xd0, 0x4f, 0xaa, 0x00}}},
    {{{    12,     59,     10}, 0, {     0,      0}, {0xaa, 0x58, 0x1b, 0x00}}},
    {{{    59,     81,      8}, 0, {     0,      0}, {0x11, 0x7a, 0x1d, 0x00}}},
    {{{    80,    -31,    -61}, 0, {     0,      0}, {0x0c, 0xb2, 0x9d, 0x00}}},
    {{{   100,     -6,    -46}, 0, {     0,      0}, {0x63, 0x0a, 0xb3, 0x00}}},
    {{{   107,    -26,      4}, 0, {     0,      0}, {0x67, 0xc5, 0x2b, 0x00}}},
    {{{    61,     12,    -60}, 0, {     0,      0}, {0x00, 0x21, 0x86, 0x00}}},
    {{{    33,      7,    -44}, 0, {     0,      0}, {0xc6, 0xfe, 0x90, 0x00}}},
    {{{    86,     29,     41}, 0, {     0,      0}, {0x4b, 0x20, 0x60, 0x00}}},
    {{{    58,    -45,      7}, 0, {     0,      0}, {0x01, 0x85, 0x1c, 0x00}}},
    {{{    36,      6,     42}, 0, {     0,      0}, {0xe1, 0xf0, 0x79, 0x00}}},
};

// 0x0400D5C8
static const Vtx mario_left_hand_closed_shared_dl_vertex_group3[] = {
    {{{    27,    -58,      6}, 0, {     0,      0}, {0xa3, 0xad, 0x12, 0x00}}},
    {{{   -14,    -14,     -4}, 0, {     0,      0}, {0x8c, 0xcd, 0x02, 0x00}}},
    {{{    17,    -38,    -39}, 0, {     0,      0}, {0x1a, 0xb7, 0x9d, 0x00}}},
    {{{   -10,     26,    -38}, 0, {     0,      0}, {0xf0, 0x41, 0x95, 0x00}}},
    {{{   -18,     45,      8}, 0, {     0,      0}, {0x84, 0xf3, 0x13, 0x00}}},
    {{{     5,     -6,     36}, 0, {     0,      0}, {0x99, 0xd2, 0x38, 0x00}}},
    {{{    34,    -41,      3}, 0, {     0,      0}, {0x42, 0xa3, 0x36, 0x00}}},
    {{{    27,    -58,      6}, 0, {     0,      0}, {0x73, 0xd5, 0x1d, 0x00}}},
    {{{    26,    -25,    -32}, 0, {     0,      0}, {0x09, 0xc7, 0x8f, 0x00}}},
    {{{   -18,     45,      8}, 0, {     0,      0}, {0x2d, 0x71, 0x21, 0x00}}},
    {{{    -1,     39,      5}, 0, {     0,      0}, {0xea, 0x6e, 0x3a, 0x00}}},
    {{{     5,     -6,     36}, 0, {     0,      0}, {0x44, 0x1b, 0x67, 0x00}}},
    {{{    36,      6,     42}, 0, {     0,      0}, {0xe1, 0xf0, 0x79, 0x00}}},
    {{{    12,     59,     10}, 0, {     0,      0}, {0xaa, 0x58, 0x1b, 0x00}}},
    {{{    58,    -45,      7}, 0, {     0,      0}, {0x01, 0x85, 0x1c, 0x00}}},
};

// 0x0400D6B8
static const Vtx mario_left_hand_closed_shared_dl_vertex_group4[] = {
    {{{    26,    -25,    -32}, 0, {     0,      0}, {0x09, 0xc7, 0x8f, 0x00}}},
    {{{    33,      7,    -44}, 0, {     0,      0}, {0xc6, 0xfe, 0x90, 0x00}}},
    {{{    80,    -31,    -61}, 0, {     0,      0}, {0x0c, 0xb2, 0x9d, 0x00}}},
    {{{    58,    -45,      7}, 0, {     0,      0}, {0x01, 0x85, 0x1c, 0x00}}},
    {{{    34,    -41,      3}, 0, {     0,      0}, {0x42, 0xa3, 0x36, 0x00}}},
    {{{    61,     12,    -60}, 0, {     0,      0}, {0x00, 0x21, 0x86, 0x00}}},
    {{{    12,     59,     10}, 0, {     0,      0}, {0xaa, 0x58, 0x1b, 0x00}}},
    {{{    32,     63,    -28}, 0, {     0,      0}, {0xd0, 0x4f, 0xaa, 0x00}}},
    {{{     4,     24,    -31}, 0, {     0,      0}, {0xd5, 0x3f, 0x9c, 0x00}}},
    {{{    -1,     39,      5}, 0, {     0,      0}, {0xea, 0x6e, 0x3a, 0x00}}},
};

// 0x0400D758 - 0x0400D8F0
const Gfx mario_left_hand_closed_shared_dl[] = {
    gsSPVertex(mario_left_hand_closed_shared_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  1,  0, 0x0,  2,  1,  7, 0x0),
    gsSP2Triangles( 7,  1,  6, 0x0,  6,  4,  8, 0x0),
    gsSP2Triangles( 8,  4,  3, 0x0,  9,  3,  2, 0x0),
    gsSP2Triangles( 0, 10, 11, 0x0, 12, 10,  0, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(mario_left_hand_closed_shared_dl_vertex_group2, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  1,  3, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 9, 10,  3, 0x0,  1,  0,  9, 0x0),
    gsSP2Triangles( 9,  3,  1, 0x0,  5,  2,  1, 0x0),
    gsSP2Triangles( 0,  2,  7, 0x0,  7,  2,  8, 0x0),
    gsSP2Triangles( 8,  2, 11, 0x0, 11,  2,  5, 0x0),
    gsSP2Triangles( 9,  7,  6, 0x0,  6,  8, 12, 0x0),
    gsSP2Triangles(12,  8, 13, 0x0, 13,  5,  4, 0x0),
    gsSPVertex(mario_left_hand_closed_shared_dl_vertex_group3, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 3,  1,  4, 0x0,  4,  1,  5, 0x0),
    gsSP2Triangles( 5,  1,  0, 0x0,  6,  7,  2, 0x0),
    gsSP2Triangles( 8,  2,  3, 0x0,  3,  9, 10, 0x0),
    gsSP2Triangles(10,  9, 11, 0x0, 11,  7,  6, 0x0),
    gsSP2Triangles(10, 12, 13, 0x0,  6, 14, 12, 0x0),
    gsSPVertex(mario_left_hand_closed_shared_dl_vertex_group4, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 1,  5,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  9,  6, 0x0),
    gsSP1Triangle( 7,  1,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0400D8F0 - 0x0400D910
const Gfx mario_left_hand_closed[] = {
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_left_hand_closed_shared_dl),
    gsSPEndDisplayList(),
};

// 0x0400D910
static const Vtx mario_right_arm_shared_dl_vertex_group1[] = {
    {{{    57,    -14,    -22}, 0, {     0,      0}, {0x33, 0xcf, 0x97, 0x00}}},
    {{{     5,    -44,      5}, 0, {     0,      0}, {0xd3, 0x8a, 0xf7, 0x00}}},
    {{{     6,    -15,    -30}, 0, {     0,      0}, {0xd6, 0xe6, 0x8c, 0x00}}},
    {{{    57,    -38,      6}, 0, {     0,      0}, {0x3e, 0x93, 0x07, 0x00}}},
    {{{     6,    -19,     44}, 0, {     0,      0}, {0xd5, 0xd4, 0x6e, 0x00}}},
    {{{    57,    -17,     37}, 0, {     0,      0}, {0x33, 0xe8, 0x71, 0x00}}},
    {{{     7,     25,     32}, 0, {     0,      0}, {0xd9, 0x5c, 0x4d, 0x00}}},
    {{{    58,     17,     27}, 0, {     0,      0}, {0x44, 0x59, 0x39, 0x00}}},
    {{{     7,     27,    -13}, 0, {     0,      0}, {0xda, 0x67, 0xc2, 0x00}}},
    {{{    58,     19,     -9}, 0, {     0,      0}, {0x3e, 0x4a, 0xaf, 0x00}}},
    {{{    -9,    -28,      6}, 0, {     0,      0}, {0x96, 0xbf, 0xec, 0x00}}},
    {{{    -9,    -10,    -15}, 0, {     0,      0}, {0x8f, 0x01, 0xc7, 0x00}}},
    {{{    -8,     15,     -5}, 0, {     0,      0}, {0x91, 0x39, 0xed, 0x00}}},
    {{{    -8,     14,     22}, 0, {     0,      0}, {0x99, 0x2e, 0x38, 0x00}}},
    {{{    -9,    -13,     30}, 0, {     0,      0}, {0x8a, 0xe9, 0x26, 0x00}}},
    {{{    67,    -12,    -13}, 0, {     0,      0}, {0x68, 0xeb, 0xbc, 0x00}}},
};

// 0x0400DA10
static const Vtx mario_right_arm_shared_dl_vertex_group2[] = {
    {{{    57,    -38,      6}, 0, {     0,      0}, {0x3e, 0x93, 0x07, 0x00}}},
    {{{    67,    -14,     28}, 0, {     0,      0}, {0x6f, 0xe8, 0x37, 0x00}}},
    {{{    57,    -17,     37}, 0, {     0,      0}, {0x33, 0xe8, 0x71, 0x00}}},
    {{{    58,     17,     27}, 0, {     0,      0}, {0x44, 0x59, 0x39, 0x00}}},
    {{{    67,     11,     -4}, 0, {     0,      0}, {0x78, 0x26, 0xf1, 0x00}}},
    {{{    58,     19,     -9}, 0, {     0,      0}, {0x3e, 0x4a, 0xaf, 0x00}}},
    {{{    67,     10,     21}, 0, {     0,      0}, {0x71, 0x2a, 0x25, 0x00}}},
    {{{    66,    -28,      6}, 0, {     0,      0}, {0x75, 0xd1, 0xfe, 0x00}}},
    {{{    67,    -12,    -13}, 0, {     0,      0}, {0x68, 0xeb, 0xbc, 0x00}}},
    {{{    -9,    -13,     30}, 0, {     0,      0}, {0x8a, 0xe9, 0x26, 0x00}}},
    {{{    -9,    -28,      6}, 0, {     0,      0}, {0x96, 0xbf, 0xec, 0x00}}},
    {{{     5,    -44,      5}, 0, {     0,      0}, {0xd3, 0x8a, 0xf7, 0x00}}},
    {{{    -8,     14,     22}, 0, {     0,      0}, {0x99, 0x2e, 0x38, 0x00}}},
    {{{     6,    -19,     44}, 0, {     0,      0}, {0xd5, 0xd4, 0x6e, 0x00}}},
    {{{    -8,     15,     -5}, 0, {     0,      0}, {0x91, 0x39, 0xed, 0x00}}},
    {{{     7,     25,     32}, 0, {     0,      0}, {0xd9, 0x5c, 0x4d, 0x00}}},
};

// 0x0400DB10
static const Vtx mario_right_arm_shared_dl_vertex_group3[] = {
    {{{    -9,    -10,    -15}, 0, {     0,      0}, {0x8f, 0x01, 0xc7, 0x00}}},
    {{{    -8,     15,     -5}, 0, {     0,      0}, {0x91, 0x39, 0xed, 0x00}}},
    {{{     7,     27,    -13}, 0, {     0,      0}, {0xda, 0x67, 0xc2, 0x00}}},
    {{{    -9,    -28,      6}, 0, {     0,      0}, {0x96, 0xbf, 0xec, 0x00}}},
    {{{     6,    -15,    -30}, 0, {     0,      0}, {0xd6, 0xe6, 0x8c, 0x00}}},
    {{{    58,     19,     -9}, 0, {     0,      0}, {0x3e, 0x4a, 0xaf, 0x00}}},
    {{{    57,    -14,    -22}, 0, {     0,      0}, {0x33, 0xcf, 0x97, 0x00}}},
    {{{    58,     17,     27}, 0, {     0,      0}, {0x44, 0x59, 0x39, 0x00}}},
    {{{    57,    -17,     37}, 0, {     0,      0}, {0x33, 0xe8, 0x71, 0x00}}},
    {{{     7,     25,     32}, 0, {     0,      0}, {0xd9, 0x5c, 0x4d, 0x00}}},
    {{{    57,    -38,      6}, 0, {     0,      0}, {0x3e, 0x93, 0x07, 0x00}}},
    {{{     6,    -19,     44}, 0, {     0,      0}, {0xd5, 0xd4, 0x6e, 0x00}}},
    {{{     5,    -44,      5}, 0, {     0,      0}, {0xd3, 0x8a, 0xf7, 0x00}}},
    {{{    67,    -12,    -13}, 0, {     0,      0}, {0x68, 0xeb, 0xbc, 0x00}}},
    {{{    67,     11,     -4}, 0, {     0,      0}, {0x78, 0x26, 0xf1, 0x00}}},
    {{{    66,    -28,      6}, 0, {     0,      0}, {0x75, 0xd1, 0xfe, 0x00}}},
};

// 0x0400DC10
static const Vtx mario_right_arm_shared_dl_vertex_group4[] = {
    {{{    -9,    -10,    -15}, 0, {     0,      0}, {0x8f, 0x01, 0xc7, 0x00}}},
    {{{    -9,    -28,      6}, 0, {     0,      0}, {0x96, 0xbf, 0xec, 0x00}}},
    {{{    -9,    -13,     30}, 0, {     0,      0}, {0x8a, 0xe9, 0x26, 0x00}}},
    {{{    -8,     15,     -5}, 0, {     0,      0}, {0x91, 0x39, 0xed, 0x00}}},
    {{{    -8,     14,     22}, 0, {     0,      0}, {0x99, 0x2e, 0x38, 0x00}}},
    {{{    67,     11,     -4}, 0, {     0,      0}, {0x78, 0x26, 0xf1, 0x00}}},
    {{{    67,    -14,     28}, 0, {     0,      0}, {0x6f, 0xe8, 0x37, 0x00}}},
    {{{    66,    -28,      6}, 0, {     0,      0}, {0x75, 0xd1, 0xfe, 0x00}}},
    {{{    67,     10,     21}, 0, {     0,      0}, {0x71, 0x2a, 0x25, 0x00}}},
};

// 0x0400DCA0 - 0x0400DDE8
const Gfx mario_right_arm_shared_dl[] = {
    gsSPVertex(mario_right_arm_shared_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 5,  6,  4, 0x0,  7,  8,  6, 0x0),
    gsSP2Triangles( 9,  2,  8, 0x0,  1, 10,  2, 0x0),
    gsSP2Triangles( 2, 11,  8, 0x0,  8, 12,  6, 0x0),
    gsSP2Triangles( 6, 13,  4, 0x0,  4, 14,  1, 0x0),
    gsSP2Triangles( 9, 15,  0, 0x0,  0, 15,  3, 0x0),
    gsSPVertex(mario_right_arm_shared_dl_vertex_group2, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 3,  4,  5, 0x0,  3,  6,  4, 0x0),
    gsSP2Triangles( 1,  6,  3, 0x0,  0,  7,  1, 0x0),
    gsSP2Triangles( 8,  7,  0, 0x0,  5,  4,  8, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0, 12,  9, 13, 0x0),
    gsSP1Triangle(14, 12, 15, 0x0),
    gsSPVertex(mario_right_arm_shared_dl_vertex_group3, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 5,  6,  4, 0x0,  7,  5,  2, 0x0),
    gsSP2Triangles( 8,  7,  9, 0x0, 10,  8, 11, 0x0),
    gsSP2Triangles( 6, 10, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(mario_right_arm_shared_dl_vertex_group4, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 2,  4,  3, 0x0,  5,  6,  7, 0x0),
    gsSP1Triangle( 5,  8,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x0400DDE8 - 0x0400DE08
const Gfx mario_right_arm[] = {
    gsSPLight(&mario_red_lights_group.l, 1),
    gsSPLight(&mario_red_lights_group.a, 2),
    gsSPDisplayList(mario_right_arm_shared_dl),
    gsSPEndDisplayList(),
};

// 0x0400DE08
static const Vtx mario_right_forearm_shared_dl_vertex[] = {
    {{{    47,    -15,    -19}, 0, {     0,      0}, {0x41, 0xcf, 0x9f, 0x00}}},
    {{{    -3,    -39,      6}, 0, {     0,      0}, {0xd4, 0x8a, 0xf8, 0x00}}},
    {{{    -2,    -15,    -23}, 0, {     0,      0}, {0xd7, 0xe5, 0x8c, 0x00}}},
    {{{    46,    -36,      6}, 0, {     0,      0}, {0x3f, 0x94, 0x0f, 0x00}}},
    {{{    -2,    -18,     38}, 0, {     0,      0}, {0xd6, 0xd5, 0x6f, 0x00}}},
    {{{    47,    -17,     34}, 0, {     0,      0}, {0x42, 0xec, 0x6a, 0x00}}},
    {{{    -1,     18,     28}, 0, {     0,      0}, {0xdb, 0x5d, 0x4d, 0x00}}},
    {{{    48,     13,     25}, 0, {     0,      0}, {0x45, 0x5d, 0x32, 0x00}}},
    {{{    -1,     20,     -9}, 0, {     0,      0}, {0xdb, 0x67, 0xc1, 0x00}}},
    {{{    48,     15,     -7}, 0, {     0,      0}, {0x45, 0x4c, 0xb6, 0x00}}},
    {{{   -14,    -28,      6}, 0, {     0,      0}, {0x90, 0xc9, 0xef, 0x00}}},
    {{{   -14,    -12,    -13}, 0, {     0,      0}, {0x91, 0x01, 0xc3, 0x00}}},
    {{{   -13,     12,     -4}, 0, {     0,      0}, {0x9e, 0x4b, 0xe7, 0x00}}},
    {{{   -13,     10,     21}, 0, {     0,      0}, {0x8d, 0x22, 0x28, 0x00}}},
    {{{   -14,    -14,     28}, 0, {     0,      0}, {0x9a, 0xd8, 0x3f, 0x00}}},
    {{{    59,     -8,      7}, 0, {     0,      0}, {0x7f, 0xfd, 0x00, 0x00}}},
};

// 0x0400DF08 - 0x0400DFF8
const Gfx mario_right_forearm_shared_dl[] = {
    gsSPVertex(mario_right_forearm_shared_dl_vertex, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 5,  6,  4, 0x0,  7,  8,  6, 0x0),
    gsSP2Triangles( 9,  2,  8, 0x0,  1, 10,  2, 0x0),
    gsSP2Triangles( 2, 11,  8, 0x0,  8, 12,  6, 0x0),
    gsSP2Triangles( 6, 13,  4, 0x0,  4, 14,  1, 0x0),
    gsSP2Triangles( 9, 15,  0, 0x0,  0, 15,  3, 0x0),
    gsSP2Triangles( 3, 15,  5, 0x0,  5, 15,  7, 0x0),
    gsSP2Triangles( 7, 15,  9, 0x0, 14, 10,  1, 0x0),
    gsSP2Triangles(13, 14,  4, 0x0, 12, 13,  6, 0x0),
    gsSP2Triangles(11, 12,  8, 0x0, 10, 11,  2, 0x0),
    gsSP2Triangles( 9,  0,  2, 0x0,  7,  9,  8, 0x0),
    gsSP2Triangles( 5,  7,  6, 0x0,  3,  5,  4, 0x0),
    gsSP2Triangles( 0,  3,  1, 0x0, 11, 13, 12, 0x0),
    gsSP2Triangles(11, 10, 13, 0x0, 10, 14, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x0400DFF8
static const Vtx mario_right_hand_closed_dl_vertex_group1[] = {
    {{{    35,      9,    -37}, 0, {     0,      0}, {0xe5, 0xf5, 0x85, 0x00}}},
    {{{    55,    -44,     -4}, 0, {     0,      0}, {0x00, 0x86, 0xdf, 0x00}}},
    {{{    31,    -40,     -1}, 0, {     0,      0}, {0x43, 0xa4, 0xc9, 0x00}}},
    {{{    10,     60,     -4}, 0, {     0,      0}, {0xac, 0x5a, 0xe5, 0x00}}},
    {{{    -2,     41,     -1}, 0, {     0,      0}, {0xed, 0x70, 0xc9, 0x00}}},
    {{{    24,    -57,     -4}, 0, {     0,      0}, {0x73, 0xd4, 0xe6, 0x00}}},
    {{{     4,     -3,    -33}, 0, {     0,      0}, {0x48, 0x1e, 0x9d, 0x00}}},
    {{{   -19,     47,     -4}, 0, {     0,      0}, {0x30, 0x72, 0xe5, 0x00}}},
    {{{   -14,     26,     41}, 0, {     0,      0}, {0xed, 0x3d, 0x6d, 0x00}}},
    {{{    13,    -38,     41}, 0, {     0,      0}, {0x15, 0xb3, 0x61, 0x00}}},
    {{{    23,    -26,     35}, 0, {     0,      0}, {0x04, 0xc3, 0x6e, 0x00}}},
    {{{    24,    -57,     -4}, 0, {     0,      0}, {0xa2, 0xaf, 0xe7, 0x00}}},
    {{{   -16,    -12,      6}, 0, {     0,      0}, {0x8c, 0xcf, 0xf8, 0x00}}},
    {{{     4,     -3,    -33}, 0, {     0,      0}, {0x9b, 0xd5, 0xc2, 0x00}}},
    {{{   -19,     47,     -4}, 0, {     0,      0}, {0x85, 0xf5, 0xe7, 0x00}}},
    {{{    58,     81,      0}, 0, {     0,      0}, {0x14, 0x7b, 0xe9, 0x00}}},
};

// 0x0400E0F8
static const Vtx mario_right_hand_closed_dl_vertex_group2[] = {
    {{{    35,      9,    -37}, 0, {     0,      0}, {0xe5, 0xf5, 0x85, 0x00}}},
    {{{   104,    -26,      1}, 0, {     0,      0}, {0x68, 0xc5, 0xd7, 0x00}}},
    {{{    55,    -44,     -4}, 0, {     0,      0}, {0x00, 0x86, 0xdf, 0x00}}},
    {{{    75,    -33,     66}, 0, {     0,      0}, {0x08, 0xaf, 0x60, 0x00}}},
    {{{    96,     -8,     52}, 0, {     0,      0}, {0x60, 0x06, 0x51, 0x00}}},
    {{{    57,     10,     66}, 0, {     0,      0}, {0xfd, 0x1c, 0x7b, 0x00}}},
    {{{    58,     81,      0}, 0, {     0,      0}, {0x14, 0x7b, 0xe9, 0x00}}},
    {{{    97,     39,     24}, 0, {     0,      0}, {0x6b, 0x3a, 0x21, 0x00}}},
    {{{    86,     30,    -33}, 0, {     0,      0}, {0x4f, 0x22, 0xa4, 0x00}}},
    {{{    86,     15,     52}, 0, {     0,      0}, {0x40, 0x30, 0x61, 0x00}}},
    {{{    54,     54,     44}, 0, {     0,      0}, {0x20, 0x51, 0x5c, 0x00}}},
    {{{    29,     62,     34}, 0, {     0,      0}, {0xce, 0x4c, 0x57, 0x00}}},
    {{{    29,      6,     49}, 0, {     0,      0}, {0xc2, 0xfa, 0x6e, 0x00}}},
    {{{    10,     60,     -4}, 0, {     0,      0}, {0xac, 0x5a, 0xe5, 0x00}}},
};

// 0x0400E1D8
static const Vtx mario_right_hand_closed_dl_vertex_group3[] = {
    {{{    31,    -40,     -1}, 0, {     0,      0}, {0x43, 0xa4, 0xc9, 0x00}}},
    {{{    23,    -26,     35}, 0, {     0,      0}, {0x04, 0xc3, 0x6e, 0x00}}},
    {{{    13,    -38,     41}, 0, {     0,      0}, {0x15, 0xb3, 0x61, 0x00}}},
    {{{     1,     24,     35}, 0, {     0,      0}, {0xd2, 0x3c, 0x65, 0x00}}},
    {{{   -14,     26,     41}, 0, {     0,      0}, {0xed, 0x3d, 0x6d, 0x00}}},
    {{{    -2,     41,     -1}, 0, {     0,      0}, {0xed, 0x70, 0xc9, 0x00}}},
    {{{    15,      0,    -23}, 0, {     0,      0}, {0x04, 0x01, 0x82, 0x00}}},
    {{{     4,     -3,    -33}, 0, {     0,      0}, {0x48, 0x1e, 0x9d, 0x00}}},
    {{{    35,      9,    -37}, 0, {     0,      0}, {0xe5, 0xf5, 0x85, 0x00}}},
    {{{    29,      6,     49}, 0, {     0,      0}, {0xc2, 0xfa, 0x6e, 0x00}}},
    {{{    55,    -44,     -4}, 0, {     0,      0}, {0x00, 0x86, 0xdf, 0x00}}},
    {{{    75,    -33,     66}, 0, {     0,      0}, {0x08, 0xaf, 0x60, 0x00}}},
    {{{    57,     10,     66}, 0, {     0,      0}, {0xfd, 0x1c, 0x7b, 0x00}}},
    {{{    10,     60,     -4}, 0, {     0,      0}, {0xac, 0x5a, 0xe5, 0x00}}},
    {{{    29,     62,     34}, 0, {     0,      0}, {0xce, 0x4c, 0x57, 0x00}}},
};

// 0x0400E2C8 - 0x0400E458
const Gfx mario_right_hand_closed_dl[] = {
    gsSPVertex(mario_right_hand_closed_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 2,  5,  6, 0x0,  6,  7,  4, 0x0),
    gsSP2Triangles( 4,  7,  8, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles( 9,  5,  2, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(13, 12, 14, 0x0, 14, 12,  8, 0x0),
    gsSP2Triangles( 8, 12,  9, 0x0,  9, 12, 11, 0x0),
    gsSP1Triangle( 3, 15,  0, 0x0),
    gsSPVertex(mario_right_hand_closed_dl_vertex_group2, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 3,  4,  5, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 8,  7,  1, 0x0,  1,  7,  4, 0x0),
    gsSP2Triangles( 4,  7,  9, 0x0, 10,  7,  6, 0x0),
    gsSP2Triangles( 9, 10,  5, 0x0, 10, 11,  5, 0x0),
    gsSP2Triangles(11, 12,  5, 0x0,  1,  4,  3, 0x0),
    gsSP2Triangles(11, 10,  6, 0x0,  6, 13, 11, 0x0),
    gsSP2Triangles( 7, 10,  9, 0x0,  4,  9,  5, 0x0),
    gsSP2Triangles( 0,  8,  1, 0x0,  6,  8,  0, 0x0),
    gsSPVertex(mario_right_hand_closed_dl_vertex_group3, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 4,  3,  5, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 7,  6,  0, 0x0,  8,  6,  5, 0x0),
    gsSP2Triangles( 9,  3,  1, 0x0,  0,  6,  8, 0x0),
    gsSP2Triangles(10, 11,  1, 0x0, 11,  9,  1, 0x0),
    gsSP2Triangles(11, 12,  9, 0x0,  1,  0, 10, 0x0),
    gsSP2Triangles(13,  5,  3, 0x0,  3,  9, 14, 0x0),
    gsSP1Triangle(14, 13,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0400E458 - 0x0400E478
const Gfx mario_right_hand_closed[] = {
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_right_hand_closed_dl),
    gsSPEndDisplayList(),
};

// 0x0400E478 - 0x0400E4A8
const Gfx mario_metal_right_hand_closed[] = {
    gsSPDisplayList(mario_right_hand_closed_dl),
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_OFF),
    gsSPEndDisplayList(),
};

// 0x0400E4A8
static const Vtx mario_left_thigh_dl_vertex_group1[] = {
    {{{     1,    -44,      8}, 0, {     0,      0}, {0xab, 0xa2, 0x00, 0x00}}},
    {{{   -15,      0,      7}, 0, {     0,      0}, {0x82, 0xf7, 0xff, 0x00}}},
    {{{     0,    -13,    -32}, 0, {     0,      0}, {0x9f, 0xe1, 0xb5, 0x00}}},
    {{{    -4,     37,    -17}, 0, {     0,      0}, {0x9e, 0x30, 0xc0, 0x00}}},
    {{{    -5,     37,     31}, 0, {     0,      0}, {0xa0, 0x3f, 0x34, 0x00}}},
    {{{    -1,    -12,     47}, 0, {     0,      0}, {0x9e, 0xe3, 0x49, 0x00}}},
    {{{    95,     -5,     48}, 0, {     0,      0}, {0x4e, 0xe9, 0x60, 0x00}}},
    {{{   116,     10,      8}, 0, {     0,      0}, {0x7e, 0x09, 0x01, 0x00}}},
    {{{    91,     45,     32}, 0, {     0,      0}, {0x3c, 0x4e, 0x4f, 0x00}}},
    {{{    92,     45,    -16}, 0, {     0,      0}, {0x35, 0x5d, 0xbd, 0x00}}},
    {{{    96,     -5,    -31}, 0, {     0,      0}, {0x50, 0xe8, 0xa1, 0x00}}},
    {{{    98,    -37,      8}, 0, {     0,      0}, {0x46, 0x97, 0x01, 0x00}}},
    {{{    19,    -15,    -42}, 0, {     0,      0}, {0xec, 0xd9, 0x8a, 0x00}}},
    {{{    14,     48,    -23}, 0, {     0,      0}, {0xd7, 0x67, 0xc5, 0x00}}},
    {{{    13,     48,     38}, 0, {     0,      0}, {0xe7, 0x6d, 0x3a, 0x00}}},
    {{{    18,    -15,     58}, 0, {     0,      0}, {0xea, 0xda, 0x76, 0x00}}},
};

// 0x0400E5A8
static const Vtx mario_left_thigh_dl_vertex_group2[] = {
    {{{    18,    -15,     58}, 0, {     0,      0}, {0xea, 0xda, 0x76, 0x00}}},
    {{{     1,    -44,      8}, 0, {     0,      0}, {0xab, 0xa2, 0x00, 0x00}}},
    {{{    21,    -54,      8}, 0, {     0,      0}, {0xf0, 0x83, 0x00, 0x00}}},
    {{{    19,    -15,    -42}, 0, {     0,      0}, {0xec, 0xd9, 0x8a, 0x00}}},
    {{{    -1,    -12,     47}, 0, {     0,      0}, {0x9e, 0xe3, 0x49, 0x00}}},
    {{{   -15,      0,      7}, 0, {     0,      0}, {0x82, 0xf7, 0xff, 0x00}}},
    {{{    98,    -37,      8}, 0, {     0,      0}, {0x46, 0x97, 0x01, 0x00}}},
    {{{    13,     48,     38}, 0, {     0,      0}, {0xe7, 0x6d, 0x3a, 0x00}}},
    {{{    91,     45,     32}, 0, {     0,      0}, {0x3c, 0x4e, 0x4f, 0x00}}},
    {{{    14,     48,    -23}, 0, {     0,      0}, {0xd7, 0x67, 0xc5, 0x00}}},
    {{{    92,     45,    -16}, 0, {     0,      0}, {0x35, 0x5d, 0xbd, 0x00}}},
    {{{    96,     -5,    -31}, 0, {     0,      0}, {0x50, 0xe8, 0xa1, 0x00}}},
    {{{    95,     -5,     48}, 0, {     0,      0}, {0x4e, 0xe9, 0x60, 0x00}}},
    {{{     0,    -13,    -32}, 0, {     0,      0}, {0x9f, 0xe1, 0xb5, 0x00}}},
    {{{    -5,     37,     31}, 0, {     0,      0}, {0xa0, 0x3f, 0x34, 0x00}}},
    {{{    -4,     37,    -17}, 0, {     0,      0}, {0x9e, 0x30, 0xc0, 0x00}}},
};

// 0x0400E6A8 - 0x0400E7B0
const Gfx mario_left_thigh_dl[] = {
    gsSPVertex(mario_left_thigh_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 3,  1,  4, 0x0,  4,  1,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 9,  7, 10, 0x0, 10,  7, 11, 0x0),
    gsSP2Triangles(11,  7,  6, 0x0, 12,  3, 13, 0x0),
    gsSP2Triangles(13,  4, 14, 0x0, 14,  4, 15, 0x0),
    gsSPVertex(mario_left_thigh_dl_vertex_group2, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  5,  1, 0x0,  0,  2,  6, 0x0),
    gsSP2Triangles( 7,  0,  8, 0x0,  9,  7, 10, 0x0),
    gsSP2Triangles( 3,  9, 10, 0x0,  2,  3,  6, 0x0),
    gsSP2Triangles( 3, 11,  6, 0x0, 10, 11,  3, 0x0),
    gsSP2Triangles( 7,  8, 10, 0x0,  0, 12,  8, 0x0),
    gsSP2Triangles( 6, 12,  0, 0x0,  1, 13,  3, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0, 14,  4,  0, 0x0),
    gsSP2Triangles( 9, 15, 14, 0x0,  3, 13, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x0400E7B0 - 0x0400E7E0
const Gfx mario_left_thigh[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPLight(&mario_blue_lights_group.l, 1),
    gsSPLight(&mario_blue_lights_group.a, 2),
    gsSPDisplayList(mario_left_thigh_dl),
    gsSPEndDisplayList(),
};

// 0x0400E7E0 - 0x0400E858
const Gfx mario_metal_left_thigh[] = {
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_DECALFADE, G_CC_DECALFADE),
    gsDPLoadTextureBlock(mario_texture_metal, G_IM_FMT_RGBA, G_IM_SIZ_16b, 64, 32, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_WRAP | G_TX_NOMIRROR, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_ON),
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_left_thigh_dl),
    gsSPEndDisplayList(),
};

// 0x0400E858
static const Vtx mario_left_leg_shared_dl_vertex[] = {
    {{{     4,    -37,      9}, 0, {     0,      0}, {0xc1, 0x93, 0x00, 0x00}}},
    {{{    64,    -46,      9}, 0, {     0,      0}, {0x4b, 0x9a, 0x01, 0x00}}},
    {{{    65,    -11,     54}, 0, {     0,      0}, {0x2d, 0xdb, 0x70, 0x00}}},
    {{{     8,     44,     33}, 0, {     0,      0}, {0xcc, 0x5e, 0x43, 0x00}}},
    {{{    68,     45,     37}, 0, {     0,      0}, {0x52, 0x4b, 0x3c, 0x00}}},
    {{{    69,     45,    -18}, 0, {     0,      0}, {0x42, 0x61, 0xd1, 0x00}}},
    {{{     9,     44,    -16}, 0, {     0,      0}, {0xc4, 0x4d, 0xaf, 0x00}}},
    {{{    66,    -11,    -36}, 0, {     0,      0}, {0x2e, 0xda, 0x91, 0x00}}},
    {{{     6,     -6,    -31}, 0, {     0,      0}, {0xb4, 0xe5, 0x9f, 0x00}}},
    {{{     6,     -6,     49}, 0, {     0,      0}, {0xb3, 0xe7, 0x60, 0x00}}},
    {{{   -14,      8,      8}, 0, {     0,      0}, {0x82, 0x06, 0xff, 0x00}}},
    {{{    81,      3,      9}, 0, {     0,      0}, {0x7e, 0xfa, 0x01, 0x00}}},
};

// 0x0400E918 - 0x0400E9C8
const Gfx mario_left_leg_shared_dl[] = {
    gsSPVertex(mario_left_leg_shared_dl_vertex, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  4, 0x0),
    gsSP2Triangles( 3,  4,  5, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 0,  7,  1, 0x0,  0,  8,  7, 0x0),
    gsSP2Triangles( 7,  8,  6, 0x0,  5,  6,  3, 0x0),
    gsSP2Triangles( 3,  9,  2, 0x0,  2,  9,  0, 0x0),
    gsSP2Triangles( 3, 10,  9, 0x0,  5, 11,  7, 0x0),
    gsSP2Triangles( 7, 11,  1, 0x0,  1, 11,  2, 0x0),
    gsSP2Triangles( 2, 11,  4, 0x0,  4, 11,  5, 0x0),
    gsSP2Triangles( 9, 10,  0, 0x0,  0, 10,  8, 0x0),
    gsSP2Triangles( 8, 10,  6, 0x0,  6, 10,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0400E9C8
static const Vtx mario_left_foot_shared_dl_vertex_group1[] = {
    {{{   110,      9,     35}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{   110,      8,    -20}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{    76,     20,    -41}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{    78,     21,     61}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{   -12,     53,    -32}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{   -11,     54,     53}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{   -39,     64,     -8}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{   -41,     65,     26}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{    43,    -18,     54}, 0, {     0,      0}, {0x07, 0xb3, 0x64, 0x00}}},
    {{{    78,     21,     61}, 0, {     0,      0}, {0x15, 0xdb, 0x77, 0x00}}},
    {{{   -11,     54,     53}, 0, {     0,      0}, {0xd9, 0x00, 0x78, 0x00}}},
    {{{    44,    -12,    -33}, 0, {     0,      0}, {0x01, 0xb7, 0x99, 0x00}}},
    {{{   -35,     11,    -21}, 0, {     0,      0}, {0xb5, 0xd2, 0xa5, 0x00}}},
    {{{   -12,     53,    -32}, 0, {     0,      0}, {0xd7, 0xfd, 0x89, 0x00}}},
    {{{   -34,     12,     44}, 0, {     0,      0}, {0xb2, 0xd2, 0x58, 0x00}}},
    {{{    76,     20,    -41}, 0, {     0,      0}, {0x0c, 0xd8, 0x89, 0x00}}},
};

// 0x0400EAC8
static const Vtx mario_left_foot_shared_dl_vertex_group2[] = {
    {{{    44,    -12,    -33}, 0, {     0,      0}, {0x01, 0xb7, 0x99, 0x00}}},
    {{{    76,     20,    -41}, 0, {     0,      0}, {0x0c, 0xd8, 0x89, 0x00}}},
    {{{   110,      8,    -20}, 0, {     0,      0}, {0x44, 0xad, 0xbe, 0x00}}},
    {{{    80,    -31,      7}, 0, {     0,      0}, {0x34, 0x8d, 0xfb, 0x00}}},
    {{{   110,      9,     35}, 0, {     0,      0}, {0x4c, 0xaf, 0x3c, 0x00}}},
    {{{    78,     21,     61}, 0, {     0,      0}, {0x15, 0xdb, 0x77, 0x00}}},
    {{{    43,    -18,     54}, 0, {     0,      0}, {0x07, 0xb3, 0x64, 0x00}}},
    {{{   -34,     12,     44}, 0, {     0,      0}, {0xb2, 0xd2, 0x58, 0x00}}},
    {{{   -11,     54,     53}, 0, {     0,      0}, {0xd9, 0x00, 0x78, 0x00}}},
    {{{   -41,     65,     26}, 0, {     0,      0}, {0x90, 0x17, 0x35, 0x00}}},
    {{{   -39,     64,     -8}, 0, {     0,      0}, {0x93, 0x16, 0xc4, 0x00}}},
    {{{   -35,     11,    -21}, 0, {     0,      0}, {0xb5, 0xd2, 0xa5, 0x00}}},
    {{{   -54,     15,      8}, 0, {     0,      0}, {0x88, 0xd8, 0xfd, 0x00}}},
    {{{   -12,     53,    -32}, 0, {     0,      0}, {0xd7, 0xfd, 0x89, 0x00}}},
    {{{    27,    -35,     11}, 0, {     0,      0}, {0xdb, 0x87, 0xfc, 0x00}}},
};

// 0x0400EBB8 - 0x0400ECA0
const Gfx mario_left_foot_shared_dl[] = {
    gsSPVertex(mario_left_foot_shared_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 3,  2,  4, 0x0,  5,  3,  4, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(14,  8, 10, 0x0, 11, 13, 15, 0x0),
    gsSPVertex(mario_left_foot_shared_dl_vertex_group2, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  4, 0x0),
    gsSP2Triangles( 2,  3,  0, 0x0,  4,  5,  6, 0x0),
    gsSP2Triangles( 6,  3,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSP2Triangles(12,  9, 10, 0x0,  9, 12,  7, 0x0),
    gsSP2Triangles(14,  0,  3, 0x0, 11,  0, 14, 0x0),
    gsSP2Triangles( 3,  6, 14, 0x0, 14,  6,  7, 0x0),
    gsSP2Triangles(14,  7, 12, 0x0, 12, 11, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x0400ECA0 - 0x0400ECC0
const Gfx mario_left_foot[] = {
    gsSPLight(&mario_brown1_lights_group.l, 1),
    gsSPLight(&mario_brown1_lights_group.a, 2),
    gsSPDisplayList(mario_left_foot_shared_dl),
    gsSPEndDisplayList(),
};

// 0x0400ECC0
static const Vtx mario_right_thigh_shared_dl_vertex_group1[] = {
    {{{    98,    -35,    -10}, 0, {     0,      0}, {0x47, 0x97, 0xfc, 0x00}}},
    {{{    19,    -16,     41}, 0, {     0,      0}, {0xee, 0xd6, 0x76, 0x00}}},
    {{{    22,    -54,     -9}, 0, {     0,      0}, {0xf2, 0x82, 0xfd, 0x00}}},
    {{{    91,     46,     16}, 0, {     0,      0}, {0x35, 0x5c, 0x44, 0x00}}},
    {{{    13,     48,     24}, 0, {     0,      0}, {0xd6, 0x66, 0x3e, 0x00}}},
    {{{    12,     49,    -37}, 0, {     0,      0}, {0xe5, 0x6e, 0xc9, 0x00}}},
    {{{    90,     47,    -32}, 0, {     0,      0}, {0x3b, 0x50, 0xb2, 0x00}}},
    {{{    18,    -13,    -58}, 0, {     0,      0}, {0xea, 0xdc, 0x89, 0x00}}},
    {{{     1,    -44,     -9}, 0, {     0,      0}, {0xad, 0xa1, 0xff, 0x00}}},
    {{{   -15,      0,     -7}, 0, {     0,      0}, {0x82, 0xf5, 0x02, 0x00}}},
    {{{    -1,    -11,    -47}, 0, {     0,      0}, {0x9d, 0xe3, 0xb7, 0x00}}},
    {{{    -6,     38,    -31}, 0, {     0,      0}, {0x9e, 0x3f, 0xcf, 0x00}}},
    {{{    -5,     36,     18}, 0, {     0,      0}, {0x9e, 0x2d, 0x42, 0x00}}},
    {{{    95,     -3,    -49}, 0, {     0,      0}, {0x4d, 0xec, 0x9e, 0x00}}},
    {{{   116,     11,     -9}, 0, {     0,      0}, {0x7e, 0x0b, 0xfe, 0x00}}},
    {{{    96,     -5,     30}, 0, {     0,      0}, {0x51, 0xe7, 0x5d, 0x00}}},
};

// 0x0400EDC0
static const Vtx mario_right_thigh_shared_dl_vertex_group2[] = {
    {{{    -5,     36,     18}, 0, {     0,      0}, {0x9e, 0x2d, 0x42, 0x00}}},
    {{{   -15,      0,     -7}, 0, {     0,      0}, {0x82, 0xf5, 0x02, 0x00}}},
    {{{     0,    -14,     31}, 0, {     0,      0}, {0xa0, 0xdf, 0x4b, 0x00}}},
    {{{     1,    -44,     -9}, 0, {     0,      0}, {0xad, 0xa1, 0xff, 0x00}}},
    {{{    19,    -16,     41}, 0, {     0,      0}, {0xee, 0xd6, 0x76, 0x00}}},
    {{{    -6,     38,    -31}, 0, {     0,      0}, {0x9e, 0x3f, 0xcf, 0x00}}},
    {{{    13,     48,     24}, 0, {     0,      0}, {0xd6, 0x66, 0x3e, 0x00}}},
    {{{    18,    -13,    -58}, 0, {     0,      0}, {0xea, 0xdc, 0x89, 0x00}}},
    {{{    -1,    -11,    -47}, 0, {     0,      0}, {0x9d, 0xe3, 0xb7, 0x00}}},
    {{{    95,     -3,    -49}, 0, {     0,      0}, {0x4d, 0xec, 0x9e, 0x00}}},
    {{{    98,    -35,    -10}, 0, {     0,      0}, {0x47, 0x97, 0xfc, 0x00}}},
    {{{    90,     47,    -32}, 0, {     0,      0}, {0x3b, 0x50, 0xb2, 0x00}}},
    {{{    91,     46,     16}, 0, {     0,      0}, {0x35, 0x5c, 0x44, 0x00}}},
    {{{    12,     49,    -37}, 0, {     0,      0}, {0xe5, 0x6e, 0xc9, 0x00}}},
    {{{    96,     -5,     30}, 0, {     0,      0}, {0x51, 0xe7, 0x5d, 0x00}}},
};

// 0x0400EEB0 - 0x0400EFB8
const Gfx mario_right_thigh_shared_dl[] = {
    gsSPVertex(mario_right_thigh_shared_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 3,  5,  4, 0x0,  6,  7,  5, 0x0),
    gsSP2Triangles( 0,  2,  7, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles( 1,  8,  2, 0x0,  2,  8,  7, 0x0),
    gsSP2Triangles( 7, 11,  5, 0x0,  5, 11,  4, 0x0),
    gsSP2Triangles( 4, 12,  1, 0x0, 13, 14,  0, 0x0),
    gsSP2Triangles( 0, 14, 15, 0x0, 15, 14,  3, 0x0),
    gsSP2Triangles( 3, 14,  6, 0x0,  6, 14, 13, 0x0),
    gsSP2Triangles(10,  9, 11, 0x0, 11,  9, 12, 0x0),
    gsSPVertex(mario_right_thigh_shared_dl_vertex_group2, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  5,  0,  6, 0x0),
    gsSP2Triangles( 7,  8,  5, 0x0,  3,  8,  7, 0x0),
    gsSP2Triangles( 4,  2,  3, 0x0,  7,  9, 10, 0x0),
    gsSP2Triangles(11,  9,  7, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles( 4, 14, 12, 0x0, 10, 14,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x0400EFB8 - 0x0400EFD8
const Gfx mario_right_thigh[] = {
    gsSPLight(&mario_blue_lights_group.l, 1),
    gsSPLight(&mario_blue_lights_group.a, 2),
    gsSPDisplayList(mario_right_thigh_shared_dl),
    gsSPEndDisplayList(),
};

// 0x0400EFD8
static const Vtx mario_right_leg_shared_dl_vertex_group1[] = {
    {{{    63,    -45,    -12}, 0, {     0,      0}, {0xf2, 0x9b, 0x4a, 0x00}}},
    {{{    66,    -11,     34}, 0, {     0,      0}, {0xf2, 0x9b, 0x4a, 0x00}}},
    {{{     4,    -36,    -10}, 0, {     0,      0}, {0xc0, 0x93, 0xfe, 0x00}}},
    {{{    66,    -11,     34}, 0, {     0,      0}, {0xfb, 0x22, 0x7a, 0x00}}},
    {{{    69,     45,     18}, 0, {     0,      0}, {0xfc, 0x22, 0x7a, 0x00}}},
    {{{     9,     44,     16}, 0, {     0,      0}, {0xc6, 0x4b, 0x53, 0x00}}},
    {{{    69,     45,     18}, 0, {     0,      0}, {0xff, 0x7f, 0x03, 0x00}}},
    {{{    69,     46,    -37}, 0, {     0,      0}, {0xff, 0x7f, 0x03, 0x00}}},
    {{{     9,     46,    -33}, 0, {     0,      0}, {0xcd, 0x60, 0xc0, 0x00}}},
    {{{    69,     46,    -37}, 0, {     0,      0}, {0xf7, 0x27, 0x88, 0x00}}},
    {{{    65,     -9,    -56}, 0, {     0,      0}, {0xf8, 0x28, 0x88, 0x00}}},
    {{{    65,     -9,    -56}, 0, {     0,      0}, {0xef, 0xa0, 0xb0, 0x00}}},
    {{{    63,    -45,    -12}, 0, {     0,      0}, {0xef, 0xa0, 0xb0, 0x00}}},
    {{{     6,     -6,     30}, 0, {     0,      0}, {0xb4, 0xe4, 0x61, 0x00}}},
    {{{   -13,     10,     -9}, 0, {     0,      0}, {0x82, 0x08, 0x02, 0x00}}},
    {{{     5,     -3,    -50}, 0, {     0,      0}, {0xb2, 0xea, 0xa0, 0x00}}},
};

// 0x0400F0D8
static const Vtx mario_right_leg_shared_dl_vertex_group2[] = {
    {{{    65,     -9,    -56}, 0, {     0,      0}, {0x75, 0xee, 0xd5, 0x00}}},
    {{{    83,      4,    -10}, 0, {     0,      0}, {0x7e, 0xf9, 0xfe, 0x00}}},
    {{{    63,    -45,    -12}, 0, {     0,      0}, {0x75, 0xd1, 0xfd, 0x00}}},
    {{{    66,    -11,     34}, 0, {     0,      0}, {0x77, 0xec, 0x26, 0x00}}},
    {{{    69,     45,     18}, 0, {     0,      0}, {0x7a, 0x18, 0x17, 0x00}}},
    {{{    69,     46,    -37}, 0, {     0,      0}, {0x79, 0x19, 0xe6, 0x00}}},
    {{{     4,    -36,    -10}, 0, {     0,      0}, {0xc0, 0x93, 0xfe, 0x00}}},
    {{{     5,     -3,    -50}, 0, {     0,      0}, {0xb2, 0xea, 0xa0, 0x00}}},
    {{{    65,     -9,    -56}, 0, {     0,      0}, {0xef, 0xa0, 0xb0, 0x00}}},
    {{{    65,     -9,    -56}, 0, {     0,      0}, {0xf8, 0x28, 0x88, 0x00}}},
    {{{     9,     46,    -33}, 0, {     0,      0}, {0xcd, 0x60, 0xc0, 0x00}}},
    {{{     9,     44,     16}, 0, {     0,      0}, {0xc6, 0x4b, 0x53, 0x00}}},
    {{{    69,     45,     18}, 0, {     0,      0}, {0xff, 0x7f, 0x03, 0x00}}},
    {{{     6,     -6,     30}, 0, {     0,      0}, {0xb4, 0xe4, 0x61, 0x00}}},
    {{{    66,    -11,     34}, 0, {     0,      0}, {0xfb, 0x22, 0x7a, 0x00}}},
    {{{    66,    -11,     34}, 0, {     0,      0}, {0xf2, 0x9b, 0x4a, 0x00}}},
};

// 0x0400F1D8 - 0x0400F290
const Gfx mario_right_leg_shared_dl[] = {
    gsSPVertex(mario_right_leg_shared_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10,  8, 0x0),
    gsSP2Triangles(11, 12,  2, 0x0, 13, 14,  2, 0x0),
    gsSP2Triangles( 2, 14, 15, 0x0, 15, 14,  8, 0x0),
    gsSP2Triangles( 8, 14,  5, 0x0,  5, 14, 13, 0x0),
    gsSPVertex(mario_right_leg_shared_dl_vertex_group2, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 3,  1,  4, 0x0,  4,  1,  5, 0x0),
    gsSP2Triangles( 5,  1,  0, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 9,  7, 10, 0x0, 10, 11, 12, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0, 15, 13,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x0400F290
static const Vtx mario_right_foot_dl_vertex_group1[] = {
    {{{    27,    -35,    -11}, 0, {     0,      0}, {0xdc, 0x87, 0x06, 0x00}}},
    {{{   -36,     11,     19}, 0, {     0,      0}, {0xb5, 0xd3, 0x5b, 0x00}}},
    {{{   -54,     14,    -10}, 0, {     0,      0}, {0x89, 0xd6, 0x03, 0x00}}},
    {{{   -34,     11,    -46}, 0, {     0,      0}, {0xb4, 0xd0, 0xa8, 0x00}}},
    {{{    43,    -18,    -55}, 0, {     0,      0}, {0x0a, 0xb2, 0x9d, 0x00}}},
    {{{    80,    -29,     -7}, 0, {     0,      0}, {0x36, 0x8e, 0x08, 0x00}}},
    {{{    44,    -11,     32}, 0, {     0,      0}, {0x01, 0xb8, 0x68, 0x00}}},
    {{{   -42,     64,    -29}, 0, {     0,      0}, {0x90, 0x14, 0xc9, 0x00}}},
    {{{   -40,     63,      5}, 0, {     0,      0}, {0x92, 0x15, 0x3b, 0x00}}},
    {{{   -14,     54,     29}, 0, {     0,      0}, {0xd5, 0xfe, 0x77, 0x00}}},
    {{{   -11,     53,    -56}, 0, {     0,      0}, {0xda, 0xfe, 0x87, 0x00}}},
    {{{   110,     11,    -36}, 0, {     0,      0}, {0x4e, 0xb0, 0xc6, 0x00}}},
    {{{    78,     22,    -61}, 0, {     0,      0}, {0x17, 0xd9, 0x8a, 0x00}}},
    {{{   109,     11,     20}, 0, {     0,      0}, {0x45, 0xaf, 0x44, 0x00}}},
    {{{    75,     23,     40}, 0, {     0,      0}, {0x0b, 0xda, 0x78, 0x00}}},
};

// 0x0400F380
static const Vtx mario_right_foot_dl_vertex_group2[] = {
    {{{   -42,     64,    -29}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{   -40,     63,      5}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{   -11,     53,    -56}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{   -14,     54,     29}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{    78,     22,    -61}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{    75,     23,     40}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{   110,     11,    -36}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{   109,     11,     20}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
};

// 0x0400F400 - 0x0400F4E8
const Gfx mario_right_foot_dl[] = {
    gsSPVertex(mario_right_foot_dl_vertex_group1, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 3,  4,  0, 0x0,  0,  4,  5, 0x0),
    gsSP2Triangles( 0,  6,  1, 0x0,  5,  6,  0, 0x0),
    gsSP2Triangles( 3,  2,  7, 0x0,  8,  7,  2, 0x0),
    gsSP2Triangles( 1,  9,  8, 0x0,  2,  1,  8, 0x0),
    gsSP2Triangles( 7, 10,  3, 0x0, 11,  5,  4, 0x0),
    gsSP2Triangles( 4, 12, 11, 0x0,  6,  5, 13, 0x0),
    gsSP2Triangles(11, 13,  5, 0x0, 13, 14,  6, 0x0),
    gsSP2Triangles(14,  9,  6, 0x0, 10,  4,  3, 0x0),
    gsSP2Triangles( 9,  1,  6, 0x0, 10, 12,  4, 0x0),
    gsSPVertex(mario_right_foot_dl_vertex_group2, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 3,  4,  2, 0x0,  3,  5,  4, 0x0),
    gsSP2Triangles( 5,  6,  4, 0x0,  5,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x0400F4E8 - 0x0400F528
const Gfx mario_right_foot[] = {
    gsSPLight(&mario_brown1_lights_group.l, 1),
    gsSPLight(&mario_brown1_lights_group.a, 2),
    gsSPDisplayList(mario_right_foot_dl),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsSPEndDisplayList(),
};

// 0x0400F528 - 0x0400F568
const Gfx mario_metal_right_foot[] = {
    gsSPDisplayList(mario_right_foot_dl),
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsSPEndDisplayList(),
};

// 0x0400F568
static const Vtx mario_yellow_button_dl_vertex[] = {
    {{{     9,     89,     41}, 0, {   180,    962}, {0x14, 0x78, 0x23, 0xff}}},
    {{{     3,     77,     62}, 0, {   682,    966}, {0x04, 0x6a, 0x44, 0xff}}},
    {{{    47,     75,     35}, 0, {    34,    106}, {0x39, 0x6a, 0x26, 0xff}}},
    {{{    28,     85,     30}, 0, {   -60,    572}, {0x2c, 0x76, 0x08, 0xff}}},
    {{{    17,     66,     76}, 0, {   966,    590}, {0x19, 0x58, 0x57, 0xff}}},
    {{{    32,     62,     74}, 0, {   902,    252}, {0x29, 0x53, 0x56, 0xff}}},
    {{{    48,     64,     59}, 0, {   548,      0}, {0x4d, 0x51, 0x3a, 0xff}}},
    {{{     9,     89,    -40}, 0, {   776,    920}, {0x10, 0x7b, 0xe7, 0xff}}},
    {{{    28,     85,    -29}, 0, {   990,    512}, {0x2d, 0x75, 0xf2, 0xff}}},
    {{{    47,     75,    -34}, 0, {   850,     72}, {0x3e, 0x6a, 0xe2, 0xff}}},
    {{{     3,     77,    -61}, 0, {   264,    972}, {0x03, 0x6a, 0xbb, 0xff}}},
    {{{    48,     64,    -58}, 0, {   312,      0}, {0x4d, 0x50, 0xc5, 0xff}}},
    {{{    32,     62,    -73}, 0, {   -24,    310}, {0x29, 0x52, 0xa9, 0xff}}},
    {{{    17,     66,    -75}, 0, {   -58,    642}, {0x17, 0x58, 0xa8, 0xff}}},
};

// 0x0400F648
static const Vtx mario_tshirt_shared_dl_vertex_group1[] = {
    {{{    47,     75,    -34}, 0, {     0,      0}, {0x3e, 0x6a, 0xe2, 0xff}}},
    {{{    47,     75,     35}, 0, {     0,      0}, {0x39, 0x6a, 0x26, 0xff}}},
    {{{    82,     37,     27}, 0, {     0,      0}, {0x66, 0x48, 0x14, 0xff}}},
    {{{    82,     37,    -26}, 0, {     0,      0}, {0x69, 0x43, 0xec, 0xff}}},
    {{{   100,      1,     24}, 0, {     0,      0}, {0x7a, 0x17, 0x16, 0xff}}},
    {{{   100,      1,    -23}, 0, {     0,      0}, {0x7c, 0x09, 0xea, 0xff}}},
    {{{     6,    -10,    -92}, 0, {     0,      0}, {0x00, 0xeb, 0x83, 0xff}}},
    {{{    47,      3,    -88}, 0, {     0,      0}, {0x30, 0x09, 0x8b, 0xff}}},
    {{{    46,    -34,    -83}, 0, {     0,      0}, {0x24, 0xc8, 0x95, 0xff}}},
    {{{    12,     33,    -90}, 0, {     0,      0}, {0x11, 0x20, 0x87, 0xff}}},
    {{{    20,    -61,    -67}, 0, {     0,      0}, {0x0a, 0x9a, 0xb7, 0xff}}},
    {{{    66,    -52,    -50}, 0, {     0,      0}, {0x3b, 0x9a, 0xd3, 0xff}}},
    {{{    84,    -29,    -62}, 0, {     0,      0}, {0x5f, 0xde, 0xb4, 0xff}}},
    {{{    20,    -61,     68}, 0, {     0,      0}, {0x0a, 0x99, 0x49, 0xff}}},
    {{{    66,    -52,     51}, 0, {     0,      0}, {0x3b, 0x9a, 0x2d, 0xff}}},
    {{{    46,    -34,     84}, 0, {     0,      0}, {0x24, 0xc8, 0x6b, 0xff}}},
};

// 0x0400F748
static const Vtx mario_tshirt_shared_dl_vertex_group2[] = {
    {{{    20,    -61,     68}, 0, {     0,      0}, {0x0a, 0x99, 0x49, 0xff}}},
    {{{    46,    -34,     84}, 0, {     0,      0}, {0x24, 0xc8, 0x6b, 0xff}}},
    {{{     6,    -11,     93}, 0, {     0,      0}, {0x00, 0xea, 0x7c, 0xff}}},
    {{{    47,      3,     89}, 0, {     0,      0}, {0x30, 0x08, 0x75, 0xff}}},
    {{{    84,    -29,     63}, 0, {     0,      0}, {0x5f, 0xde, 0x4c, 0xff}}},
    {{{    66,    -52,     51}, 0, {     0,      0}, {0x3b, 0x9a, 0x2d, 0xff}}},
    {{{    13,     33,     91}, 0, {     0,      0}, {0x12, 0x20, 0x79, 0xff}}},
    {{{   100,      1,    -23}, 0, {     0,      0}, {0x7c, 0x09, 0xea, 0xff}}},
    {{{   100,      1,     24}, 0, {     0,      0}, {0x7a, 0x17, 0x16, 0xff}}},
    {{{    93,    -36,     26}, 0, {     0,      0}, {0x6b, 0xbf, 0x0d, 0xff}}},
    {{{    93,    -36,    -25}, 0, {     0,      0}, {0x66, 0xb7, 0xf0, 0xff}}},
    {{{    54,    -63,      0}, 0, {     0,      0}, {0x30, 0x8b, 0x00, 0xff}}},
    {{{    80,     14,     62}, 0, {     0,      0}, {0x60, 0x28, 0x47, 0xff}}},
    {{{    44,     51,     75}, 0, {     0,      0}, {0x3c, 0x34, 0x62, 0xff}}},
};

// 0x0400F828
static const Vtx mario_tshirt_shared_dl_vertex_group3[] = {
    {{{    80,     14,    -61}, 0, {     0,      0}, {0x60, 0x28, 0xb8, 0xff}}},
    {{{    47,      3,    -88}, 0, {     0,      0}, {0x30, 0x09, 0x8b, 0xff}}},
    {{{    43,     51,    -74}, 0, {     0,      0}, {0x3c, 0x32, 0x9d, 0xff}}},
    {{{    84,    -29,    -62}, 0, {     0,      0}, {0x5f, 0xde, 0xb4, 0xff}}},
    {{{    12,     33,    -90}, 0, {     0,      0}, {0x11, 0x20, 0x87, 0xff}}},
};

// 0x0400F878
static const Vtx mario_pants_overalls_shared_dl_vertex_group1[] = {
    {{{    47,     75,    -34}, 0, {     0,      0}, {0x3e, 0x6a, 0xe2, 0xff}}},
    {{{    28,     85,     30}, 0, {     0,      0}, {0x2c, 0x76, 0x08, 0xff}}},
    {{{    47,     75,     35}, 0, {     0,      0}, {0x39, 0x6a, 0x26, 0xff}}},
    {{{    28,     85,    -29}, 0, {     0,      0}, {0x2d, 0x75, 0xf2, 0xff}}},
    {{{     9,     89,     41}, 0, {     0,      0}, {0x14, 0x78, 0x23, 0xff}}},
    {{{     9,     89,    -40}, 0, {     0,      0}, {0x10, 0x7b, 0xe7, 0xff}}},
    {{{   -15,     88,     35}, 0, {     0,      0}, {0xec, 0x7a, 0x1c, 0xff}}},
    {{{   -15,     87,    -35}, 0, {     0,      0}, {0xea, 0x76, 0xda, 0xff}}},
    {{{     3,     77,    -61}, 0, {     0,      0}, {0x03, 0x6a, 0xbb, 0xff}}},
    {{{   -43,     83,    -24}, 0, {     0,      0}, {0xcc, 0x72, 0xee, 0xff}}},
    {{{   -28,     63,    -74}, 0, {     0,      0}, {0xd1, 0x4f, 0xaa, 0xff}}},
    {{{   -40,     72,    -54}, 0, {     0,      0}, {0xc5, 0x5f, 0xc6, 0xff}}},
    {{{   -40,     72,     55}, 0, {     0,      0}, {0xc4, 0x5e, 0x3a, 0xff}}},
    {{{   -43,     83,     25}, 0, {     0,      0}, {0xbc, 0x69, 0x11, 0xff}}},
    {{{   -28,     63,     75}, 0, {     0,      0}, {0xd0, 0x50, 0x55, 0xff}}},
};

// 0x0400F968
static const Vtx mario_pants_overalls_shared_dl_vertex_group2[] = {
    {{{    80,     14,    -61}, 0, {     0,      0}, {0x60, 0x28, 0xb8, 0xff}}},
    {{{    43,     51,    -74}, 0, {     0,      0}, {0x3c, 0x32, 0x9d, 0xff}}},
    {{{    48,     64,    -58}, 0, {     0,      0}, {0x4d, 0x50, 0xc5, 0xff}}},
    {{{     3,     77,     62}, 0, {     0,      0}, {0x04, 0x6a, 0x44, 0xff}}},
    {{{     9,     89,     41}, 0, {     0,      0}, {0x14, 0x78, 0x23, 0xff}}},
    {{{   -15,     88,     35}, 0, {     0,      0}, {0xec, 0x7a, 0x1c, 0xff}}},
    {{{    47,     75,     35}, 0, {     0,      0}, {0x39, 0x6a, 0x26, 0xff}}},
    {{{    48,     64,     59}, 0, {     0,      0}, {0x4d, 0x51, 0x3a, 0xff}}},
    {{{    82,     37,     27}, 0, {     0,      0}, {0x66, 0x48, 0x14, 0xff}}},
    {{{    32,     62,     74}, 0, {     0,      0}, {0x29, 0x53, 0x56, 0xff}}},
    {{{    44,     51,     75}, 0, {     0,      0}, {0x3c, 0x34, 0x62, 0xff}}},
    {{{    80,     14,     62}, 0, {     0,      0}, {0x60, 0x28, 0x47, 0xff}}},
    {{{   100,      1,     24}, 0, {     0,      0}, {0x7a, 0x17, 0x16, 0xff}}},
    {{{    82,     37,    -26}, 0, {     0,      0}, {0x69, 0x43, 0xec, 0xff}}},
    {{{    32,     62,    -73}, 0, {     0,      0}, {0x29, 0x52, 0xa9, 0xff}}},
    {{{    47,     75,    -34}, 0, {     0,      0}, {0x3e, 0x6a, 0xe2, 0xff}}},
};

// 0x0400FA68
static const Vtx mario_pants_overalls_shared_dl_vertex_group3[] = {
    {{{    80,     14,    -61}, 0, {     0,      0}, {0x60, 0x28, 0xb8, 0xff}}},
    {{{    82,     37,    -26}, 0, {     0,      0}, {0x69, 0x43, 0xec, 0xff}}},
    {{{   100,      1,    -23}, 0, {     0,      0}, {0x7c, 0x09, 0xea, 0xff}}},
    {{{    20,    -61,    -67}, 0, {     0,      0}, {0x0a, 0x9a, 0xb7, 0xff}}},
    {{{    66,    -52,    -50}, 0, {     0,      0}, {0x3b, 0x9a, 0xd3, 0xff}}},
    {{{    54,    -63,      0}, 0, {     0,      0}, {0x30, 0x8b, 0x00, 0xff}}},
    {{{   -27,    -25,    -86}, 0, {     0,      0}, {0xcb, 0xd5, 0x96, 0xff}}},
    {{{     6,    -10,    -92}, 0, {     0,      0}, {0x00, 0xeb, 0x83, 0xff}}},
    {{{   -29,    -58,    -59}, 0, {     0,      0}, {0xc9, 0x9a, 0xcf, 0xff}}},
    {{{     8,    -74,      0}, 0, {     0,      0}, {0xfd, 0x82, 0x00, 0xff}}},
    {{{   -59,    -28,    -37}, 0, {     0,      0}, {0x90, 0xd5, 0xd9, 0xff}}},
    {{{   -30,    -66,      0}, 0, {     0,      0}, {0xb8, 0x98, 0x00, 0xff}}},
    {{{   -27,     26,    -89}, 0, {     0,      0}, {0xc7, 0x0f, 0x90, 0xff}}},
    {{{    12,     33,    -90}, 0, {     0,      0}, {0x11, 0x20, 0x87, 0xff}}},
    {{{    20,    -61,     68}, 0, {     0,      0}, {0x0a, 0x99, 0x49, 0xff}}},
    {{{    66,    -52,     51}, 0, {     0,      0}, {0x3b, 0x9a, 0x2d, 0xff}}},
};

// 0x0400FB68
static const Vtx mario_pants_overalls_shared_dl_vertex_group4[] = {
    {{{     8,    -74,      0}, 0, {     0,      0}, {0xfd, 0x82, 0x00, 0xff}}},
    {{{    20,    -61,     68}, 0, {     0,      0}, {0x0a, 0x99, 0x49, 0xff}}},
    {{{   -29,    -58,     59}, 0, {     0,      0}, {0xc8, 0x99, 0x30, 0xff}}},
    {{{   -27,    -25,     87}, 0, {     0,      0}, {0xcb, 0xd3, 0x69, 0xff}}},
    {{{     6,    -11,     93}, 0, {     0,      0}, {0x00, 0xea, 0x7c, 0xff}}},
    {{{   -27,     26,     91}, 0, {     0,      0}, {0xc8, 0x0e, 0x70, 0xff}}},
    {{{    13,     33,     91}, 0, {     0,      0}, {0x12, 0x20, 0x79, 0xff}}},
    {{{   -59,    -28,     38}, 0, {     0,      0}, {0x8d, 0xd8, 0x21, 0xff}}},
    {{{   -30,    -66,      0}, 0, {     0,      0}, {0xb8, 0x98, 0x00, 0xff}}},
    {{{   -28,     63,    -74}, 0, {     0,      0}, {0xd1, 0x4f, 0xaa, 0xff}}},
    {{{   -27,     26,    -89}, 0, {     0,      0}, {0xc7, 0x0f, 0x90, 0xff}}},
    {{{   -71,     54,    -29}, 0, {     0,      0}, {0x8f, 0x2a, 0xda, 0xff}}},
    {{{   -59,    -28,    -37}, 0, {     0,      0}, {0x90, 0xd5, 0xd9, 0xff}}},
    {{{   -71,     54,     30}, 0, {     0,      0}, {0x94, 0x2a, 0x31, 0xff}}},
    {{{   -43,     83,     25}, 0, {     0,      0}, {0xbc, 0x69, 0x11, 0xff}}},
    {{{   -43,     83,    -24}, 0, {     0,      0}, {0xcc, 0x72, 0xee, 0xff}}},
};

// 0x0400FC68
static const Vtx mario_pants_overalls_shared_dl_vertex_group5[] = {
    {{{   -71,     54,    -29}, 0, {     0,      0}, {0x8f, 0x2a, 0xda, 0xff}}},
    {{{   -43,     83,    -24}, 0, {     0,      0}, {0xcc, 0x72, 0xee, 0xff}}},
    {{{   -40,     72,    -54}, 0, {     0,      0}, {0xc5, 0x5f, 0xc6, 0xff}}},
    {{{   -59,    -28,    -37}, 0, {     0,      0}, {0x90, 0xd5, 0xd9, 0xff}}},
    {{{   -59,    -28,     38}, 0, {     0,      0}, {0x8d, 0xd8, 0x21, 0xff}}},
    {{{   -28,     63,    -74}, 0, {     0,      0}, {0xd1, 0x4f, 0xaa, 0xff}}},
    {{{   -43,     83,     25}, 0, {     0,      0}, {0xbc, 0x69, 0x11, 0xff}}},
    {{{   -71,     54,     30}, 0, {     0,      0}, {0x94, 0x2a, 0x31, 0xff}}},
    {{{   -30,    -66,      0}, 0, {     0,      0}, {0xb8, 0x98, 0x00, 0xff}}},
    {{{   -27,     26,     91}, 0, {     0,      0}, {0xc8, 0x0e, 0x70, 0xff}}},
    {{{   -28,     63,     75}, 0, {     0,      0}, {0xd0, 0x50, 0x55, 0xff}}},
    {{{   -40,     72,     55}, 0, {     0,      0}, {0xc4, 0x5e, 0x3a, 0xff}}},
    {{{    13,     33,     91}, 0, {     0,      0}, {0x12, 0x20, 0x79, 0xff}}},
    {{{    -4,     58,     84}, 0, {     0,      0}, {0xfb, 0x43, 0x6b, 0xff}}},
};

// 0x0400FD48
static const Vtx mario_pants_overalls_shared_dl_vertex_group6[] = {
    {{{   -27,     26,    -89}, 0, {     0,      0}, {0xc7, 0x0f, 0x90, 0xff}}},
    {{{    -4,     58,    -82}, 0, {     0,      0}, {0xf9, 0x42, 0x94, 0xff}}},
    {{{    12,     33,    -90}, 0, {     0,      0}, {0x11, 0x20, 0x87, 0xff}}},
    {{{   -28,     63,    -74}, 0, {     0,      0}, {0xd1, 0x4f, 0xaa, 0xff}}},
    {{{    80,     14,    -61}, 0, {     0,      0}, {0x60, 0x28, 0xb8, 0xff}}},
    {{{   100,      1,    -23}, 0, {     0,      0}, {0x7c, 0x09, 0xea, 0xff}}},
    {{{    84,    -29,    -62}, 0, {     0,      0}, {0x5f, 0xde, 0xb4, 0xff}}},
    {{{    93,    -36,    -25}, 0, {     0,      0}, {0x66, 0xb7, 0xf0, 0xff}}},
    {{{    66,    -52,    -50}, 0, {     0,      0}, {0x3b, 0x9a, 0xd3, 0xff}}},
    {{{    54,    -63,      0}, 0, {     0,      0}, {0x30, 0x8b, 0x00, 0xff}}},
    {{{    66,    -52,     51}, 0, {     0,      0}, {0x3b, 0x9a, 0x2d, 0xff}}},
    {{{    93,    -36,     26}, 0, {     0,      0}, {0x6b, 0xbf, 0x0d, 0xff}}},
    {{{    84,    -29,     63}, 0, {     0,      0}, {0x5f, 0xde, 0x4c, 0xff}}},
    {{{   100,      1,     24}, 0, {     0,      0}, {0x7a, 0x17, 0x16, 0xff}}},
    {{{    80,     14,     62}, 0, {     0,      0}, {0x60, 0x28, 0x47, 0xff}}},
};

// 0x0400FE38
static const Vtx mario_pants_overalls_shared_dl_vertex_group7[] = {
    {{{    17,     66,    -75}, 0, {     0,      0}, {0x17, 0x58, 0xa8, 0xff}}},
    {{{    -4,     58,    -82}, 0, {     0,      0}, {0xf9, 0x42, 0x94, 0xff}}},
    {{{     3,     77,    -61}, 0, {     0,      0}, {0x03, 0x6a, 0xbb, 0xff}}},
    {{{   -28,     63,    -74}, 0, {     0,      0}, {0xd1, 0x4f, 0xaa, 0xff}}},
    {{{    32,     62,     74}, 0, {     0,      0}, {0x29, 0x53, 0x56, 0xff}}},
    {{{    17,     66,     76}, 0, {     0,      0}, {0x19, 0x58, 0x57, 0xff}}},
    {{{    13,     33,     91}, 0, {     0,      0}, {0x12, 0x20, 0x79, 0xff}}},
    {{{   -28,     63,     75}, 0, {     0,      0}, {0xd0, 0x50, 0x55, 0xff}}},
    {{{    -4,     58,     84}, 0, {     0,      0}, {0xfb, 0x43, 0x6b, 0xff}}},
    {{{     3,     77,     62}, 0, {     0,      0}, {0x04, 0x6a, 0x44, 0xff}}},
    {{{   -15,     88,     35}, 0, {     0,      0}, {0xec, 0x7a, 0x1c, 0xff}}},
    {{{    12,     33,    -90}, 0, {     0,      0}, {0x11, 0x20, 0x87, 0xff}}},
    {{{    32,     62,    -73}, 0, {     0,      0}, {0x29, 0x52, 0xa9, 0xff}}},
    {{{    43,     51,    -74}, 0, {     0,      0}, {0x3c, 0x32, 0x9d, 0xff}}},
    {{{    44,     51,     75}, 0, {     0,      0}, {0x3c, 0x34, 0x62, 0xff}}},
};

// 0x0400FF28 - 0x0400FF88
const Gfx mario_yellow_button_dl[] = {
    gsSPVertex(mario_yellow_button_dl_vertex, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  5,  6,  2, 0x0),
    gsSP2Triangles( 4,  5,  2, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  7,  9, 0x0,  9, 11, 12, 0x0),
    gsSP2Triangles(12, 13,  9, 0x0, 13, 10,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0400FF88 - 0x04010260
const Gfx mario_pants_overalls_shared_dl[] = {
    gsSPVertex(mario_pants_overalls_shared_dl_vertex_group1, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  1,  5, 0x0,  1,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 7,  5,  8, 0x0,  9,  6,  7, 0x0),
    gsSP2Triangles( 7,  8, 10, 0x0, 10, 11,  7, 0x0),
    gsSP2Triangles( 9,  7, 11, 0x0, 12,  6, 13, 0x0),
    gsSP2Triangles( 9, 13,  6, 0x0,  6, 12, 14, 0x0),
    gsSPVertex(mario_pants_overalls_shared_dl_vertex_group2, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10,  7, 0x0),
    gsSP2Triangles( 7, 10, 11, 0x0, 11,  8,  7, 0x0),
    gsSP2Triangles(12,  8, 11, 0x0, 13,  0,  2, 0x0),
    gsSP2Triangles( 2,  1, 14, 0x0,  2, 15, 13, 0x0),
    gsSPVertex(mario_pants_overalls_shared_dl_vertex_group3, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  7, 0x0,  3,  8,  6, 0x0),
    gsSP2Triangles( 5,  9,  3, 0x0,  8,  3,  9, 0x0),
    gsSP2Triangles( 6,  8, 10, 0x0, 11, 10,  8, 0x0),
    gsSP2Triangles( 9, 11,  8, 0x0,  6, 12,  7, 0x0),
    gsSP2Triangles(12,  6, 10, 0x0,  7, 12, 13, 0x0),
    gsSP2Triangles(14,  5, 15, 0x0, 14,  9,  5, 0x0),
    gsSPVertex(mario_pants_overalls_shared_dl_vertex_group4, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 1,  4,  3, 0x0,  5,  4,  6, 0x0),
    gsSP2Triangles( 4,  5,  3, 0x0,  2,  3,  7, 0x0),
    gsSP2Triangles( 7,  3,  5, 0x0,  8,  2,  7, 0x0),
    gsSP2Triangles( 2,  8,  0, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12, 11, 10, 0x0, 11,  7, 13, 0x0),
    gsSP1Triangle(14, 15, 11, 0x0),
    gsSPVertex(mario_pants_overalls_shared_dl_vertex_group5, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 2,  5,  0, 0x0,  6,  0,  7, 0x0),
    gsSP2Triangles( 4,  3,  8, 0x0,  9,  7,  4, 0x0),
    gsSP2Triangles( 7, 10, 11, 0x0, 11,  6,  7, 0x0),
    gsSP2Triangles( 9, 12, 13, 0x0, 10,  9, 13, 0x0),
    gsSP1Triangle( 7,  9, 10, 0x0),
    gsSPVertex(mario_pants_overalls_shared_dl_vertex_group6, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9,  8,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(14, 12, 13, 0x0, 11, 10,  9, 0x0),
    gsSPVertex(mario_pants_overalls_shared_dl_vertex_group7, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  7,  9, 0x0,  9,  8,  5, 0x0),
    gsSP2Triangles( 5,  8,  6, 0x0, 11,  1,  0, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0,  6, 14,  4, 0x0),
    gsSP1Triangle(11,  0, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x04010260 - 0x04010348
const Gfx mario_tshirt_shared_dl[] = {
    gsSPVertex(mario_tshirt_shared_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  3,  2, 0x0,  4,  5,  3, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles( 6,  8, 10, 0x0, 11,  8, 12, 0x0),
    gsSP2Triangles(12,  8,  7, 0x0,  8, 11, 10, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(mario_tshirt_shared_dl_vertex_group2, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 3,  1,  4, 0x0,  4,  1,  5, 0x0),
    gsSP2Triangles( 6,  2,  3, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 10,  9, 0x0),
    gsSP2Triangles(12,  3,  4, 0x0, 13,  3, 12, 0x0),
    gsSP1Triangle( 6,  3, 13, 0x0),
    gsSPVertex(mario_tshirt_shared_dl_vertex_group3, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP1Triangle( 1,  4,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x04010348 - 0x04010370
const Gfx mario_torso_dl[] = {
    gsSPDisplayList(mario_pants_overalls_shared_dl),
    gsSPLight(&mario_red_lights_group.l, 1),
    gsSPLight(&mario_red_lights_group.a, 2),
    gsSPDisplayList(mario_tshirt_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04010370 - 0x040103F0
const Gfx mario_torso[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_yellow_button),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_yellow_button_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_torso_dl),
    gsSPEndDisplayList(),
};

// 0x040103F0 - 0x04010410
const Gfx mario_metal_torso_shared_dl[] = {
    gsSPDisplayList(mario_yellow_button_dl),
    gsSPDisplayList(mario_pants_overalls_shared_dl),
    gsSPDisplayList(mario_tshirt_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04010410
static const Vtx mario_m_logo_dl_vertex[] = {
    {{{   195,     93,     32}, 0, {   744,    732}, {0x51, 0x54, 0x30, 0xff}}},
    {{{   153,     33,     95}, 0, {  1250,   1066}, {0x04, 0x46, 0x69, 0xff}}},
    {{{   252,     62,     70}, 0, {  1032,    152}, {0x39, 0x4a, 0x55, 0xff}}},
    {{{   272,     85,      0}, 0, {   472,    -16}, {0x5f, 0x54, 0x00, 0xff}}},
    {{{   252,     62,    -69}, 0, {   -78,    158}, {0x39, 0x4a, 0xab, 0xff}}},
    {{{   195,     93,    -31}, 0, {   232,    736}, {0x51, 0x54, 0xcf, 0xff}}},
    {{{   153,     33,    -94}, 0, {  -258,   1076}, {0x03, 0x46, 0x97, 0xff}}},
};

// 0x04010480
static const Vtx mario_eyes_cap_on_dl_vertex[] = {
    {{{   145,    101,      0}, 0, {   480,    446}, {0x13, 0x7d, 0x00, 0xff}}},
    {{{   108,    105,      0}, 0, {   488,    854}, {0x69, 0x46, 0x00, 0xff}}},
    {{{    97,    103,     25}, 0, {   724,    958}, {0x40, 0x5a, 0x3d, 0xff}}},
    {{{    97,    103,    -24}, 0, {   256,    970}, {0x40, 0x5a, 0xc3, 0xff}}},
    {{{   175,     91,    -38}, 0, {   106,    130}, {0xc6, 0x6d, 0xe7, 0xff}}},
    {{{   144,     83,    -66}, 0, {  -146,    460}, {0x19, 0x5b, 0xad, 0xff}}},
    {{{   104,     87,    -69}, 0, {  -160,    898}, {0x30, 0x5c, 0xb7, 0xff}}},
    {{{   175,     91,     39}, 0, {   842,    112}, {0xc6, 0x6d, 0x19, 0xff}}},
    {{{   153,     33,    -94}, 0, {  -404,    342}, {0x03, 0x46, 0x97, 0xff}}},
    {{{   104,     87,     70}, 0, {  1138,    864}, {0x30, 0x5c, 0x49, 0xff}}},
    {{{   144,     83,     67}, 0, {  1108,    428}, {0x19, 0x5b, 0x53, 0xff}}},
    {{{   153,     33,     95}, 0, {  1362,    296}, {0x04, 0x46, 0x69, 0xff}}},
};

// 0x04010540
static const Vtx mario_hair_sideburn_cap_on_dl_vertex[] = {
    {{{   120,    -12,   -109}, 0, {   916,    184}, {0xed, 0x24, 0x88, 0xff}}},
    {{{    96,     49,    -93}, 0, {   -70,    700}, {0x11, 0x27, 0x89, 0xff}}},
    {{{   153,     33,    -94}, 0, {    90,    -58}, {0x03, 0x46, 0x97, 0xff}}},
    {{{    85,    -23,   -110}, 0, {  1150,    582}, {0x06, 0x59, 0xa6, 0xff}}},
    {{{    42,      0,   -102}, 0, {   842,   1210}, {0xc3, 0x05, 0x92, 0xff}}},
    {{{    44,     44,    -96}, 0, {   118,   1334}, {0xda, 0x21, 0x8c, 0xff}}},
    {{{    96,     49,     94}, 0, {   -74,    726}, {0x10, 0x28, 0x77, 0xff}}},
    {{{    44,     44,     97}, 0, {   196,   1366}, {0xda, 0x21, 0x74, 0xff}}},
    {{{    42,      0,    103}, 0, {   968,   1192}, {0xc3, 0x05, 0x6e, 0xff}}},
    {{{    85,    -23,    111}, 0, {  1238,    518}, {0x04, 0x58, 0x5a, 0xff}}},
    {{{   120,    -11,    111}, 0, {   922,    122}, {0xed, 0x24, 0x78, 0xff}}},
    {{{   153,     33,     95}, 0, {    22,    -68}, {0x04, 0x46, 0x69, 0xff}}},
};

// 0x04010600
static const Vtx mario_mustache_cap_on_dl_vertex_group1[] = {
    {{{    77,    108,     34}, 0, {   342,    368}, {0x0e, 0x16, 0x7c, 0xff}}},
    {{{    92,     88,     84}, 0, {   898,    -18}, {0x23, 0x59, 0x53, 0xff}}},
    {{{    97,    103,     25}, 0, {   224,    146}, {0x40, 0x5a, 0x3d, 0xff}}},
    {{{    48,     94,     76}, 0, {   888,    564}, {0xd7, 0x60, 0x47, 0xff}}},
    {{{    52,    111,     31}, 0, {   354,    690}, {0xa8, 0x33, 0x4b, 0xff}}},
    {{{    27,    100,     30}, 0, {   442,    982}, {0xb1, 0x60, 0x16, 0xff}}},
    {{{    41,    109,      0}, 0, {    70,    924}, {0x95, 0x44, 0x00, 0xff}}},
    {{{    44,     44,     97}, 0, {  1296,    438}, {0xda, 0x21, 0x74, 0xff}}},
    {{{     3,     54,     52}, 0, {   892,   1090}, {0x95, 0x1d, 0x3c, 0xff}}},
    {{{    52,    111,    -30}, 0, {   340,    684}, {0xa8, 0x32, 0xb5, 0xff}}},
    {{{    48,     94,    -75}, 0, {   856,    608}, {0xd7, 0x60, 0xb9, 0xff}}},
    {{{    27,    100,    -29}, 0, {   394,    998}, {0xb1, 0x60, 0xea, 0xff}}},
    {{{     3,     54,    -51}, 0, {   806,   1162}, {0x95, 0x1d, 0xc4, 0xff}}},
    {{{    44,     44,    -96}, 0, {  1250,    518}, {0xda, 0x21, 0x8c, 0xff}}},
    {{{    77,    108,    -33}, 0, {   362,    344}, {0x0e, 0x16, 0x84, 0xff}}},
    {{{    41,    109,      0}, 0, {    50,    900}, {0x95, 0x44, 0x00, 0xff}}},
};

// 0x04010700
static const Vtx mario_mustache_cap_on_dl_vertex_group2[] = {
    {{{    48,     94,    -75}, 0, {   856,    608}, {0xd7, 0x60, 0xb9, 0xff}}},
    {{{    77,    108,    -33}, 0, {   362,    344}, {0x0e, 0x16, 0x84, 0xff}}},
    {{{    92,     88,    -83}, 0, {   922,     -4}, {0x23, 0x59, 0xad, 0xff}}},
    {{{    97,    103,    -24}, 0, {   274,    100}, {0x40, 0x5a, 0xc3, 0xff}}},
    {{{    44,     44,    -96}, 0, {  1250,    518}, {0xda, 0x21, 0x8c, 0xff}}},
};

// 0x04010750
static const Vtx mario_face_cap_dl_vertex_group1[] = {
    {{{   164,    -89,    -74}, 0, {     0,      0}, {0x51, 0xa9, 0xd5, 0xff}}},
    {{{   122,   -139,    -48}, 0, {     0,      0}, {0x28, 0x94, 0xcd, 0xff}}},
    {{{   131,    -56,   -123}, 0, {     0,      0}, {0xde, 0xd4, 0x8f, 0xff}}},
    {{{   187,     -6,   -133}, 0, {     0,      0}, {0x1e, 0x04, 0x85, 0xff}}},
    {{{   120,    -12,   -109}, 0, {     0,      0}, {0xed, 0x24, 0x88, 0xff}}},
    {{{    91,    -54,    -85}, 0, {     0,      0}, {0xd9, 0xb0, 0xa7, 0xff}}},
    {{{    77,   -138,    -50}, 0, {     0,      0}, {0xbc, 0x9e, 0xd8, 0xff}}},
    {{{    64,   -103,    -51}, 0, {     0,      0}, {0xde, 0x94, 0xc7, 0xff}}},
    {{{   122,   -139,     49}, 0, {     0,      0}, {0x2d, 0x8f, 0x22, 0xff}}},
    {{{    77,   -138,     51}, 0, {     0,      0}, {0xc1, 0xa4, 0x3c, 0xff}}},
    {{{    64,   -103,     52}, 0, {     0,      0}, {0xc9, 0x9a, 0x31, 0xff}}},
    {{{   131,    -56,    124}, 0, {     0,      0}, {0xdf, 0xd3, 0x71, 0xff}}},
    {{{   164,    -89,     75}, 0, {     0,      0}, {0x4e, 0xa6, 0x2b, 0xff}}},
    {{{    91,    -54,     86}, 0, {     0,      0}, {0xd9, 0xaf, 0x59, 0xff}}},
    {{{   187,     -6,    134}, 0, {     0,      0}, {0x1f, 0x04, 0x7a, 0xff}}},
    {{{   120,    -11,    111}, 0, {     0,      0}, {0xed, 0x24, 0x78, 0xff}}},
};

// 0x04010850
static const Vtx mario_face_cap_dl_vertex_group2[] = {
    {{{   153,     33,    -94}, 0, {     0,      0}, {0x03, 0x46, 0x97, 0xff}}},
    {{{   187,     -6,   -133}, 0, {     0,      0}, {0x1e, 0x04, 0x85, 0xff}}},
    {{{   120,    -12,   -109}, 0, {     0,      0}, {0xed, 0x24, 0x88, 0xff}}},
    {{{   175,     91,    -38}, 0, {     0,      0}, {0xc6, 0x6d, 0xe7, 0xff}}},
    {{{   173,    132,    -65}, 0, {     0,      0}, {0xda, 0x5a, 0xb0, 0xff}}},
    {{{   195,     93,    -31}, 0, {     0,      0}, {0x51, 0x54, 0xcf, 0xff}}},
    {{{   252,     62,    -69}, 0, {     0,      0}, {0x39, 0x4a, 0xab, 0xff}}},
    {{{   164,    -89,    -74}, 0, {     0,      0}, {0x51, 0xa9, 0xd5, 0xff}}},
    {{{   271,      0,    -41}, 0, {     0,      0}, {0x6e, 0xcf, 0xdc, 0xff}}},
    {{{   187,     -6,    134}, 0, {     0,      0}, {0x1f, 0x04, 0x7a, 0xff}}},
    {{{   153,     33,     95}, 0, {     0,      0}, {0x04, 0x46, 0x69, 0xff}}},
    {{{   120,    -11,    111}, 0, {     0,      0}, {0xed, 0x24, 0x78, 0xff}}},
    {{{   252,     62,     70}, 0, {     0,      0}, {0x39, 0x4a, 0x55, 0xff}}},
    {{{   271,      0,     42}, 0, {     0,      0}, {0x71, 0xdc, 0x2b, 0xff}}},
    {{{   164,    -89,     75}, 0, {     0,      0}, {0x4e, 0xa6, 0x2b, 0xff}}},
};

// 0x04010940
static const Vtx mario_face_cap_dl_vertex_group3[] = {
    {{{   195,     93,     32}, 0, {     0,      0}, {0x51, 0x54, 0x30, 0xff}}},
    {{{   173,    132,     66}, 0, {     0,      0}, {0xda, 0x5a, 0x50, 0xff}}},
    {{{   153,     33,     95}, 0, {     0,      0}, {0x04, 0x46, 0x69, 0xff}}},
    {{{   175,     91,     39}, 0, {     0,      0}, {0xc6, 0x6d, 0x19, 0xff}}},
    {{{   164,    -89,    -74}, 0, {     0,      0}, {0x51, 0xa9, 0xd5, 0xff}}},
    {{{   271,      0,    -41}, 0, {     0,      0}, {0x6e, 0xcf, 0xdc, 0xff}}},
    {{{   164,    -89,     75}, 0, {     0,      0}, {0x4e, 0xa6, 0x2b, 0xff}}},
    {{{   271,      0,     42}, 0, {     0,      0}, {0x71, 0xdc, 0x2b, 0xff}}},
    {{{   252,     62,    -69}, 0, {     0,      0}, {0x39, 0x4a, 0xab, 0xff}}},
    {{{   272,     85,      0}, 0, {     0,      0}, {0x5f, 0x54, 0x00, 0xff}}},
    {{{   182,    155,      0}, 0, {     0,      0}, {0xed, 0x7d, 0x00, 0xff}}},
    {{{   195,     93,    -31}, 0, {     0,      0}, {0x51, 0x54, 0xcf, 0xff}}},
    {{{   175,     91,    -38}, 0, {     0,      0}, {0xc6, 0x6d, 0xe7, 0xff}}},
    {{{   173,    132,    -65}, 0, {     0,      0}, {0xda, 0x5a, 0xb0, 0xff}}},
    {{{   252,     62,     70}, 0, {     0,      0}, {0x39, 0x4a, 0x55, 0xff}}},
};

// 0x04010A30
static const Vtx mario_face_part_cap_on_dl_vertex_group1[] = {
    {{{    41,    109,      0}, 0, {     0,      0}, {0x95, 0x44, 0x00, 0xff}}},
    {{{    27,    100,    -29}, 0, {     0,      0}, {0xb1, 0x60, 0xea, 0xff}}},
    {{{    27,    100,     30}, 0, {     0,      0}, {0xb1, 0x60, 0x16, 0xff}}},
    {{{    -7,     62,      0}, 0, {     0,      0}, {0x85, 0x1d, 0x00, 0xff}}},
    {{{     3,     54,     52}, 0, {     0,      0}, {0x95, 0x1d, 0x3c, 0xff}}},
    {{{     3,     54,    -51}, 0, {     0,      0}, {0x95, 0x1d, 0xc4, 0xff}}},
    {{{    17,    -52,    -46}, 0, {     0,      0}, {0x89, 0xf8, 0xd7, 0xff}}},
    {{{    17,    -52,     47}, 0, {     0,      0}, {0x89, 0xf8, 0x29, 0xff}}},
    {{{    42,      0,    103}, 0, {     0,      0}, {0xc3, 0x05, 0x6e, 0xff}}},
    {{{    44,     44,     97}, 0, {     0,      0}, {0xda, 0x21, 0x74, 0xff}}},
    {{{    42,      0,   -102}, 0, {     0,      0}, {0xc3, 0x05, 0x92, 0xff}}},
    {{{    44,     44,    -96}, 0, {     0,      0}, {0xda, 0x21, 0x8c, 0xff}}},
    {{{    64,    -25,    135}, 0, {     0,      0}, {0xcc, 0xe5, 0x70, 0xff}}},
    {{{    91,    -54,     86}, 0, {     0,      0}, {0xd9, 0xaf, 0x59, 0xff}}},
    {{{   102,    -33,    135}, 0, {     0,      0}, {0x30, 0x18, 0x72, 0xff}}},
};

// 0x04010B20
static const Vtx mario_face_part_cap_on_dl_vertex_group2[] = {
    {{{   102,    -33,   -134}, 0, {     0,      0}, {0x32, 0x19, 0x8f, 0xff}}},
    {{{   120,    -12,   -109}, 0, {     0,      0}, {0xed, 0x24, 0x88, 0xff}}},
    {{{    91,    -54,    -85}, 0, {     0,      0}, {0xd9, 0xb0, 0xa7, 0xff}}},
    {{{    64,    -25,    135}, 0, {     0,      0}, {0xcc, 0xe5, 0x70, 0xff}}},
    {{{    56,    -45,     77}, 0, {     0,      0}, {0xe3, 0xca, 0x6e, 0xff}}},
    {{{    91,    -54,     86}, 0, {     0,      0}, {0xd9, 0xaf, 0x59, 0xff}}},
    {{{   120,    -11,    111}, 0, {     0,      0}, {0xed, 0x24, 0x78, 0xff}}},
    {{{    85,    -23,    111}, 0, {     0,      0}, {0x04, 0x58, 0x5a, 0xff}}},
    {{{   102,    -33,    135}, 0, {     0,      0}, {0x30, 0x18, 0x72, 0xff}}},
    {{{    42,      0,    103}, 0, {     0,      0}, {0xc3, 0x05, 0x6e, 0xff}}},
    {{{    85,    -23,   -110}, 0, {     0,      0}, {0x06, 0x59, 0xa6, 0xff}}},
    {{{    64,    -25,   -134}, 0, {     0,      0}, {0xcc, 0xe5, 0x90, 0xff}}},
    {{{    42,      0,   -102}, 0, {     0,      0}, {0xc3, 0x05, 0x92, 0xff}}},
    {{{    33,    165,      0}, 0, {     0,      0}, {0x8f, 0x38, 0x09, 0xff}}},
    {{{    52,    164,    -40}, 0, {     0,      0}, {0xb8, 0x34, 0xa6, 0xff}}},
    {{{    32,    132,      0}, 0, {     0,      0}, {0x84, 0xe8, 0xf8, 0xff}}},
};

// 0x04010C20
static const Vtx mario_face_part_cap_on_dl_vertex_group3[] = {
    {{{    56,    -45,    -76}, 0, {     0,      0}, {0xe3, 0xca, 0x92, 0xff}}},
    {{{    64,    -25,   -134}, 0, {     0,      0}, {0xcc, 0xe5, 0x90, 0xff}}},
    {{{    91,    -54,    -85}, 0, {     0,      0}, {0xd9, 0xb0, 0xa7, 0xff}}},
    {{{    42,      0,   -102}, 0, {     0,      0}, {0xc3, 0x05, 0x92, 0xff}}},
    {{{    17,    -52,     47}, 0, {     0,      0}, {0x89, 0xf8, 0x29, 0xff}}},
    {{{    56,    -45,     77}, 0, {     0,      0}, {0xe3, 0xca, 0x6e, 0xff}}},
    {{{    42,      0,    103}, 0, {     0,      0}, {0xc3, 0x05, 0x6e, 0xff}}},
    {{{    17,    -52,    -46}, 0, {     0,      0}, {0x89, 0xf8, 0xd7, 0xff}}},
    {{{    32,    132,      0}, 0, {     0,      0}, {0x84, 0xe8, 0xf8, 0xff}}},
    {{{    47,    131,     41}, 0, {     0,      0}, {0xb0, 0xe4, 0x5e, 0xff}}},
    {{{    33,    165,      0}, 0, {     0,      0}, {0x8f, 0x38, 0x09, 0xff}}},
    {{{    52,    111,     31}, 0, {     0,      0}, {0xa8, 0x33, 0x4b, 0xff}}},
    {{{    52,    111,    -30}, 0, {     0,      0}, {0xa8, 0x32, 0xb5, 0xff}}},
    {{{    41,    109,      0}, 0, {     0,      0}, {0x95, 0x44, 0x00, 0xff}}},
    {{{    77,    108,     34}, 0, {     0,      0}, {0x0e, 0x16, 0x7c, 0xff}}},
};

// 0x04010D10
static const Vtx mario_face_part_cap_on_dl_vertex_group4[] = {
    {{{    92,     88,     84}, 0, {     0,      0}, {0x23, 0x59, 0x53, 0xff}}},
    {{{   104,     87,     70}, 0, {     0,      0}, {0x30, 0x5c, 0x49, 0xff}}},
    {{{    97,    103,     25}, 0, {     0,      0}, {0x40, 0x5a, 0x3d, 0xff}}},
    {{{   117,    128,      0}, 0, {     0,      0}, {0x7a, 0xe1, 0x06, 0xff}}},
    {{{   102,    129,     41}, 0, {     0,      0}, {0x45, 0xd6, 0x61, 0xff}}},
    {{{    77,    108,     34}, 0, {     0,      0}, {0x0e, 0x16, 0x7c, 0xff}}},
    {{{   108,    105,      0}, 0, {     0,      0}, {0x69, 0x46, 0x00, 0xff}}},
    {{{    97,    103,    -24}, 0, {     0,      0}, {0x40, 0x5a, 0xc3, 0xff}}},
    {{{   119,    161,      0}, 0, {     0,      0}, {0x79, 0x23, 0xf6, 0xff}}},
    {{{   104,    162,     41}, 0, {     0,      0}, {0x52, 0x27, 0x58, 0xff}}},
    {{{   102,    129,    -40}, 0, {     0,      0}, {0x50, 0xdc, 0xa6, 0xff}}},
    {{{    75,    130,     46}, 0, {     0,      0}, {0x00, 0xec, 0x7d, 0xff}}},
    {{{    78,    163,     46}, 0, {     0,      0}, {0x03, 0x55, 0x5d, 0xff}}},
    {{{   101,    179,      0}, 0, {     0,      0}, {0x27, 0x78, 0x00, 0xff}}},
    {{{    52,    164,     41}, 0, {     0,      0}, {0xc8, 0x3d, 0x5f, 0xff}}},
    {{{    47,    131,     41}, 0, {     0,      0}, {0xb0, 0xe4, 0x5e, 0xff}}},
};

// 0x04010E10
static const Vtx mario_face_part_cap_on_dl_vertex_group5[] = {
    {{{    47,    131,     41}, 0, {     0,      0}, {0xb0, 0xe4, 0x5e, 0xff}}},
    {{{    52,    164,     41}, 0, {     0,      0}, {0xc8, 0x3d, 0x5f, 0xff}}},
    {{{    33,    165,      0}, 0, {     0,      0}, {0x8f, 0x38, 0x09, 0xff}}},
    {{{    75,    130,     46}, 0, {     0,      0}, {0x00, 0xec, 0x7d, 0xff}}},
    {{{    77,    108,     34}, 0, {     0,      0}, {0x0e, 0x16, 0x7c, 0xff}}},
    {{{    78,    163,     46}, 0, {     0,      0}, {0x03, 0x55, 0x5d, 0xff}}},
    {{{    60,    181,      0}, 0, {     0,      0}, {0xea, 0x7c, 0x00, 0xff}}},
    {{{    52,    164,    -40}, 0, {     0,      0}, {0xb8, 0x34, 0xa6, 0xff}}},
    {{{    96,     49,     94}, 0, {     0,      0}, {0x10, 0x28, 0x77, 0xff}}},
    {{{   153,     33,     95}, 0, {     0,      0}, {0x04, 0x46, 0x69, 0xff}}},
    {{{   144,     83,     67}, 0, {     0,      0}, {0x19, 0x5b, 0x53, 0xff}}},
    {{{   101,    179,      0}, 0, {     0,      0}, {0x27, 0x78, 0x00, 0xff}}},
    {{{   104,     87,     70}, 0, {     0,      0}, {0x30, 0x5c, 0x49, 0xff}}},
    {{{    92,     88,     84}, 0, {     0,      0}, {0x23, 0x59, 0x53, 0xff}}},
    {{{    44,     44,     97}, 0, {     0,      0}, {0xda, 0x21, 0x74, 0xff}}},
};

// 0x04010F00
static const Vtx mario_face_part_cap_on_dl_vertex_group6[] = {
    {{{    17,    -52,    -46}, 0, {     0,      0}, {0x89, 0xf8, 0xd7, 0xff}}},
    {{{    31,    -84,      0}, 0, {     0,      0}, {0xa0, 0xae, 0x00, 0xff}}},
    {{{    17,    -52,     47}, 0, {     0,      0}, {0x89, 0xf8, 0x29, 0xff}}},
    {{{   144,     83,    -66}, 0, {     0,      0}, {0x19, 0x5b, 0xad, 0xff}}},
    {{{   153,     33,    -94}, 0, {     0,      0}, {0x03, 0x46, 0x97, 0xff}}},
    {{{    96,     49,    -93}, 0, {     0,      0}, {0x11, 0x27, 0x89, 0xff}}},
    {{{    92,     88,    -83}, 0, {     0,      0}, {0x23, 0x59, 0xad, 0xff}}},
    {{{   104,     87,    -69}, 0, {     0,      0}, {0x30, 0x5c, 0xb7, 0xff}}},
    {{{    44,     44,    -96}, 0, {     0,      0}, {0xda, 0x21, 0x8c, 0xff}}},
    {{{    75,    130,    -45}, 0, {     0,      0}, {0x00, 0xec, 0x83, 0xff}}},
    {{{    78,    163,    -45}, 0, {     0,      0}, {0x03, 0x54, 0xa2, 0xff}}},
    {{{   104,    162,    -40}, 0, {     0,      0}, {0x44, 0x31, 0xa1, 0xff}}},
    {{{    97,    103,    -24}, 0, {     0,      0}, {0x40, 0x5a, 0xc3, 0xff}}},
    {{{    52,    164,    -40}, 0, {     0,      0}, {0xb8, 0x34, 0xa6, 0xff}}},
    {{{    47,    131,    -40}, 0, {     0,      0}, {0xbc, 0xde, 0x9c, 0xff}}},
    {{{    60,    181,      0}, 0, {     0,      0}, {0xea, 0x7c, 0x00, 0xff}}},
};

// 0x04011000
static const Vtx mario_face_part_cap_on_dl_vertex_group7[] = {
    {{{    52,    164,    -40}, 0, {     0,      0}, {0xb8, 0x34, 0xa6, 0xff}}},
    {{{    47,    131,    -40}, 0, {     0,      0}, {0xbc, 0xde, 0x9c, 0xff}}},
    {{{    32,    132,      0}, 0, {     0,      0}, {0x84, 0xe8, 0xf8, 0xff}}},
    {{{    77,    108,    -33}, 0, {     0,      0}, {0x0e, 0x16, 0x84, 0xff}}},
    {{{    52,    111,    -30}, 0, {     0,      0}, {0xa8, 0x32, 0xb5, 0xff}}},
    {{{    75,    130,    -45}, 0, {     0,      0}, {0x00, 0xec, 0x83, 0xff}}},
    {{{   104,    162,    -40}, 0, {     0,      0}, {0x44, 0x31, 0xa1, 0xff}}},
    {{{   102,    129,    -40}, 0, {     0,      0}, {0x50, 0xdc, 0xa6, 0xff}}},
    {{{   119,    161,      0}, 0, {     0,      0}, {0x79, 0x23, 0xf6, 0xff}}},
    {{{    97,    103,    -24}, 0, {     0,      0}, {0x40, 0x5a, 0xc3, 0xff}}},
    {{{    78,    163,    -45}, 0, {     0,      0}, {0x03, 0x54, 0xa2, 0xff}}},
    {{{    60,    181,      0}, 0, {     0,      0}, {0xea, 0x7c, 0x00, 0xff}}},
    {{{   101,    179,      0}, 0, {     0,      0}, {0x27, 0x78, 0x00, 0xff}}},
};

// 0x040110D0
static const Vtx mario_face_back_hair_cap_on_dl_vertex_group1[] = {
    {{{    31,    -84,      0}, 0, {     0,      0}, {0xa0, 0xae, 0x00, 0xff}}},
    {{{    36,   -116,    -35}, 0, {     0,      0}, {0xfb, 0x8e, 0x37, 0xff}}},
    {{{    64,   -103,    -51}, 0, {     0,      0}, {0xde, 0x94, 0xc7, 0xff}}},
    {{{    31,    -85,     87}, 0, {     0,      0}, {0xdb, 0xfd, 0x79, 0xff}}},
    {{{    35,   -120,     68}, 0, {     0,      0}, {0xda, 0x94, 0x35, 0xff}}},
    {{{    58,   -114,     60}, 0, {     0,      0}, {0x57, 0xb4, 0x32, 0xff}}},
    {{{    64,   -103,     52}, 0, {     0,      0}, {0xc9, 0x9a, 0x31, 0xff}}},
    {{{    56,    -45,     77}, 0, {     0,      0}, {0xe3, 0xca, 0x6e, 0xff}}},
    {{{    36,   -116,     36}, 0, {     0,      0}, {0xfb, 0x8e, 0xc9, 0xff}}},
    {{{    91,    -54,     86}, 0, {     0,      0}, {0xd9, 0xaf, 0x59, 0xff}}},
    {{{    17,    -52,     47}, 0, {     0,      0}, {0x89, 0xf8, 0x29, 0xff}}},
    {{{    35,   -120,    -67}, 0, {     0,      0}, {0xda, 0x94, 0xcb, 0xff}}},
    {{{    58,   -114,    -59}, 0, {     0,      0}, {0x57, 0xb4, 0xce, 0xff}}},
    {{{    31,    -85,    -86}, 0, {     0,      0}, {0xdb, 0xfd, 0x87, 0xff}}},
    {{{    56,    -45,    -76}, 0, {     0,      0}, {0xe3, 0xca, 0x92, 0xff}}},
    {{{    17,    -52,    -46}, 0, {     0,      0}, {0x89, 0xf8, 0xd7, 0xff}}},
};

// 0x040111D0
static const Vtx mario_face_back_hair_cap_on_dl_vertex_group2[] = {
    {{{    31,    -85,    -86}, 0, {     0,      0}, {0xdb, 0xfd, 0x87, 0xff}}},
    {{{    35,   -120,    -67}, 0, {     0,      0}, {0xda, 0x94, 0xcb, 0xff}}},
    {{{     9,    -88,    -58}, 0, {     0,      0}, {0x87, 0xdf, 0xf5, 0xff}}},
    {{{    17,    -52,    -46}, 0, {     0,      0}, {0x89, 0xf8, 0xd7, 0xff}}},
    {{{    56,    -45,    -76}, 0, {     0,      0}, {0xe3, 0xca, 0x92, 0xff}}},
    {{{    91,    -54,    -85}, 0, {     0,      0}, {0xd9, 0xb0, 0xa7, 0xff}}},
    {{{    64,   -103,    -51}, 0, {     0,      0}, {0xde, 0x94, 0xc7, 0xff}}},
    {{{    31,    -84,      0}, 0, {     0,      0}, {0xa0, 0xae, 0x00, 0xff}}},
    {{{    36,   -116,     36}, 0, {     0,      0}, {0xfb, 0x8e, 0xc9, 0xff}}},
    {{{     9,    -88,     59}, 0, {     0,      0}, {0x87, 0xdf, 0x0b, 0xff}}},
    {{{    17,    -52,     47}, 0, {     0,      0}, {0x89, 0xf8, 0x29, 0xff}}},
    {{{    36,   -116,    -35}, 0, {     0,      0}, {0xfb, 0x8e, 0x37, 0xff}}},
    {{{    35,   -120,     68}, 0, {     0,      0}, {0xda, 0x94, 0x35, 0xff}}},
    {{{    31,    -85,     87}, 0, {     0,      0}, {0xdb, 0xfd, 0x79, 0xff}}},
};

// 0x040112B0 - 0x040112E8
const Gfx mario_m_logo_dl[] = {
    gsSPVertex(mario_m_logo_dl_vertex, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 2,  3,  0, 0x0,  3,  5,  0, 0x0),
    gsSP1Triangle( 4,  6,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x040112E8 - 0x04011350
const Gfx mario_eyes_cap_on_dl[] = {
    gsSPVertex(mario_eyes_cap_on_dl_vertex, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  0,  4, 0x0,  6,  3,  5, 0x0),
    gsSP2Triangles( 3,  1,  0, 0x0,  4,  0,  7, 0x0),
    gsSP2Triangles( 5,  4,  8, 0x0,  2,  9, 10, 0x0),
    gsSP2Triangles(10,  7,  2, 0x0, 11,  7, 10, 0x0),
    gsSP1Triangle( 7,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x04011350 - 0x040113A0
const Gfx mario_hair_sideburn_cap_on_dl[] = {
    gsSPVertex(mario_hair_sideburn_cap_on_dl_vertex, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 4,  5,  1, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  9,  6, 0x0),
    gsSP2Triangles( 9, 10,  6, 0x0, 11,  6, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x040113A0 - 0x04011438
const Gfx mario_mustache_cap_on_dl[] = {
    gsSPVertex(mario_mustache_cap_on_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 0,  4,  3, 0x0,  5,  3,  4, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  7,  3,  8, 0x0),
    gsSP2Triangles( 8,  3,  5, 0x0,  3,  7,  1, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0, 12, 10, 13, 0x0),
    gsSP2Triangles(11, 10, 12, 0x0, 10,  9, 14, 0x0),
    gsSP1Triangle(15,  9, 11, 0x0),
    gsSPVertex(mario_mustache_cap_on_dl_vertex_group2, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP1Triangle( 2,  4,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x04011438 - 0x040116F8
const Gfx mario_face_part_cap_on_dl[] = {
    gsSPVertex(mario_face_part_cap_on_dl_vertex_group1, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 3,  4,  2, 0x0,  1,  5,  3, 0x0),
    gsSP2Triangles( 6,  7,  3, 0x0,  7,  4,  3, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  4,  7,  8, 0x0),
    gsSP2Triangles( 9,  4,  8, 0x0, 10,  6,  5, 0x0),
    gsSP2Triangles(10,  5, 11, 0x0, 12, 13, 14, 0x0),
    gsSPVertex(mario_face_part_cap_on_dl_vertex_group2, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  5,  6,  8, 0x0),
    gsSP2Triangles( 3,  8,  7, 0x0,  9,  3,  7, 0x0),
    gsSP2Triangles( 3,  9,  4, 0x0, 10, 11, 12, 0x0),
    gsSP2Triangles( 0, 10,  1, 0x0, 10,  0, 11, 0x0),
    gsSP2Triangles(11,  0,  2, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(mario_face_part_cap_on_dl_vertex_group3, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  3,  0,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11,  9,  8, 0x0),
    gsSP2Triangles(12, 13,  8, 0x0,  8, 13, 11, 0x0),
    gsSP1Triangle( 9, 11, 14, 0x0),
    gsSPVertex(mario_face_part_cap_on_dl_vertex_group4, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 5,  2,  4, 0x0,  2,  6,  3, 0x0),
    gsSP2Triangles( 3,  6,  7, 0x0,  8,  9,  3, 0x0),
    gsSP2Triangles( 9,  4,  3, 0x0,  7, 10,  3, 0x0),
    gsSP2Triangles( 3, 10,  8, 0x0, 11,  4,  9, 0x0),
    gsSP2Triangles(12, 11,  9, 0x0, 13,  9,  8, 0x0),
    gsSP2Triangles( 9, 13, 12, 0x0,  4, 11,  5, 0x0),
    gsSP1Triangle(14, 15, 11, 0x0),
    gsSPVertex(mario_face_part_cap_on_dl_vertex_group5, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 1,  5,  6, 0x0,  1,  3,  5, 0x0),
    gsSP2Triangles( 2,  1,  6, 0x0,  2,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  6,  5, 11, 0x0),
    gsSP2Triangles(12, 13,  8, 0x0, 10, 12,  8, 0x0),
    gsSP1Triangle(14,  8, 13, 0x0),
    gsSPVertex(mario_face_part_cap_on_dl_vertex_group6, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  6,  5,  8, 0x0),
    gsSP2Triangles( 5,  7,  3, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 6, 12,  7, 0x0, 13, 10,  9, 0x0),
    gsSP2Triangles(14, 13,  9, 0x0, 15, 10, 13, 0x0),
    gsSPVertex(mario_face_part_cap_on_dl_vertex_group7, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 1,  5,  3, 0x0,  2,  1,  4, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  7,  6,  8, 0x0),
    gsSP2Triangles( 7,  9,  3, 0x0,  5,  7,  3, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12,  6, 10, 0x0),
    gsSP1Triangle(12,  8,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x040116F8 - 0x04011870
const Gfx mario_face_cap_dl[] = {
    gsSPVertex(mario_face_cap_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  4, 0x0),
    gsSP2Triangles( 4,  2,  5, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 2,  1,  6, 0x0,  6,  7,  2, 0x0),
    gsSP2Triangles( 5,  2,  7, 0x0,  6,  1,  8, 0x0),
    gsSP2Triangles( 8,  1,  0, 0x0,  6,  8,  9, 0x0),
    gsSP2Triangles(10,  7,  6, 0x0,  9, 10,  6, 0x0),
    gsSP2Triangles(11, 10,  9, 0x0,  9,  8, 11, 0x0),
    gsSP2Triangles(11,  8, 12, 0x0,  0, 12,  8, 0x0),
    gsSP2Triangles(13, 10, 11, 0x0, 11, 12, 14, 0x0),
    gsSP2Triangles(15, 11, 14, 0x0, 13, 11, 15, 0x0),
    gsSPVertex(mario_face_cap_dl_vertex_group2, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 4,  5,  0, 0x0,  0,  6,  1, 0x0),
    gsSP2Triangles( 7,  1,  8, 0x0,  8,  1,  6, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0,  9, 12, 10, 0x0),
    gsSP2Triangles(12,  9, 13, 0x0, 13,  9, 14, 0x0),
    gsSPVertex(mario_face_cap_dl_vertex_group3, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 8,  9,  5, 0x0,  0, 10,  1, 0x0),
    gsSP2Triangles( 1, 10,  3, 0x0, 11, 10,  0, 0x0),
    gsSP2Triangles(12,  3, 10, 0x0, 12, 10, 13, 0x0),
    gsSP2Triangles(13, 10, 11, 0x0,  5,  9,  7, 0x0),
    gsSP1Triangle( 7,  9, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x04011870 - 0x04011960
const Gfx mario_face_back_hair_cap_on_dl[] = {
    gsSPVertex(mario_face_back_hair_cap_on_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  5,  8,  6, 0x0),
    gsSP2Triangles( 5,  4,  8, 0x0,  7,  3,  5, 0x0),
    gsSP2Triangles( 0,  2,  6, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles( 0,  6,  8, 0x0,  3,  7, 10, 0x0),
    gsSP2Triangles( 1, 11, 12, 0x0, 12, 13, 14, 0x0),
    gsSP2Triangles(11, 13, 12, 0x0,  1, 12,  2, 0x0),
    gsSP2Triangles(14,  2, 12, 0x0, 15, 14, 13, 0x0),
    gsSPVertex(mario_face_back_hair_cap_on_dl_vertex_group2, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  3,  2, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  9, 10,  7, 0x0),
    gsSP2Triangles( 2,  1, 11, 0x0,  2, 11,  7, 0x0),
    gsSP2Triangles( 9, 12, 13, 0x0, 10,  9, 13, 0x0),
    gsSP1Triangle( 8, 12,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x04011960 - 0x040119A0
const Gfx mario_face_cap_on_dl[] = {
    gsSPDisplayList(mario_face_part_cap_on_dl),
    gsSPLight(&mario_red_lights_group.l, 1),
    gsSPLight(&mario_red_lights_group.a, 2),
    gsSPDisplayList(mario_face_cap_dl),
    gsSPLight(&mario_brown2_lights_group.l, 1),
    gsSPLight(&mario_brown2_lights_group.a, 2),
    gsSPDisplayList(mario_face_back_hair_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x040119A0 - 0x04011A90
const Gfx mario_cap_on_eyes_front[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_front),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04011A90 - 0x04011B80
const Gfx mario_cap_on_eyes_half_closed[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_half_closed),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04011B80 - 0x04011C70
const Gfx mario_cap_on_eyes_closed[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_closed),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04011C70 - 0x04011D60
const Gfx mario_cap_on_eyes_right[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_right),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04011D60 - 0x04011E50
const Gfx mario_cap_on_eyes_left[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_left),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04011E50 - 0x04011F40
const Gfx mario_cap_on_eyes_up[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_up),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04011F40 - 0x04012030
const Gfx mario_cap_on_eyes_down[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_down),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04012030 - 0x04012120
const Gfx mario_cap_on_eyes_dead[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_dead),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04012120 - 0x04012160
const Gfx mario_metal_cap_on_shared_dl[] = {
    gsSPDisplayList(mario_m_logo_dl),
    gsSPDisplayList(mario_eyes_cap_on_dl),
    gsSPDisplayList(mario_hair_sideburn_cap_on_dl),
    gsSPDisplayList(mario_mustache_cap_on_dl),
    gsSPDisplayList(mario_face_part_cap_on_dl),
    gsSPDisplayList(mario_face_cap_dl),
    gsSPDisplayList(mario_face_back_hair_cap_on_dl),
    gsSPEndDisplayList(),
};

// Unreferenced light group
UNUSED static const Lights1 mario_lights_unused1 = gdSPDefLights1(
    0x3f, 0x30, 0x1e,
    0xfe, 0xc1, 0x79, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 mario_lights_unused2 = gdSPDefLights1(
    0x1c, 0x01, 0x00,
    0x73, 0x06, 0x00, 0x28, 0x28, 0x28
);

// 0x04012190
static const Vtx mario_eyes_cap_off_dl_vertex[] = {
    {{{   104,     87,    -69}, 0, {  -160,    898}, {0x30, 0x5c, 0xb7, 0xff}}},
    {{{    97,    103,    -24}, 0, {   256,    970}, {0x40, 0x5a, 0xc3, 0xff}}},
    {{{   144,     83,    -66}, 0, {  -146,    460}, {0x19, 0x5b, 0xad, 0xff}}},
    {{{   153,     33,     95}, 0, {  1362,    296}, {0x05, 0x38, 0x71, 0xff}}},
    {{{   175,     91,     39}, 0, {   842,    112}, {0x01, 0x75, 0x2f, 0xff}}},
    {{{   144,     83,     67}, 0, {  1108,    428}, {0x19, 0x5b, 0x53, 0xff}}},
    {{{   145,    101,      0}, 0, {   480,    446}, {0x13, 0x7d, 0x00, 0xff}}},
    {{{    97,    103,     25}, 0, {   724,    958}, {0x40, 0x5a, 0x3d, 0xff}}},
    {{{   175,     91,    -38}, 0, {   106,    130}, {0xf5, 0x73, 0xcc, 0xff}}},
    {{{   104,     87,     70}, 0, {  1138,    864}, {0x30, 0x5c, 0x49, 0xff}}},
    {{{   153,     33,    -94}, 0, {  -404,    342}, {0xfe, 0x36, 0x8e, 0xff}}},
    {{{   108,    105,      0}, 0, {   488,    854}, {0x69, 0x46, 0x00, 0xff}}},
};

// 0x04012250
static const Vtx mario_hair_sideburn_cap_off_dl_vertex[] = {
    {{{    42,      0,    103}, 0, {   968,   1192}, {0xc3, 0x05, 0x6e, 0xff}}},
    {{{    85,    -23,    111}, 0, {  1238,    518}, {0x04, 0x58, 0x5a, 0xff}}},
    {{{    96,     49,     94}, 0, {   -74,    726}, {0x10, 0x28, 0x77, 0xff}}},
    {{{    44,     44,     97}, 0, {   196,   1366}, {0xda, 0x21, 0x74, 0xff}}},
    {{{   153,     33,     95}, 0, {    22,    -68}, {0x05, 0x38, 0x71, 0xff}}},
    {{{   120,    -11,    111}, 0, {   922,    122}, {0xff, 0x13, 0x7d, 0xff}}},
    {{{    42,      0,   -102}, 0, {   842,   1210}, {0xc3, 0x05, 0x92, 0xff}}},
    {{{    44,     44,    -96}, 0, {   118,   1334}, {0xda, 0x21, 0x8c, 0xff}}},
    {{{    96,     49,    -93}, 0, {   -70,    700}, {0x11, 0x27, 0x89, 0xff}}},
    {{{    85,    -23,   -110}, 0, {  1150,    582}, {0x06, 0x59, 0xa6, 0xff}}},
    {{{   120,    -12,   -109}, 0, {   916,    184}, {0xff, 0x13, 0x83, 0xff}}},
    {{{   153,     33,    -94}, 0, {    90,    -58}, {0xfe, 0x36, 0x8e, 0xff}}},
};

// 0x04012310
static const Vtx mario_mustache_cap_off_dl_vertex_group1[] = {
    {{{    92,     88,    -83}, 0, {   992,    -10}, {0x23, 0x59, 0xad, 0xff}}},
    {{{    77,    108,    -33}, 0, {   360,    296}, {0x0e, 0x16, 0x84, 0xff}}},
    {{{    97,    103,    -24}, 0, {   298,     20}, {0x40, 0x5a, 0xc3, 0xff}}},
    {{{    48,     94,    -75}, 0, {   844,    640}, {0xd7, 0x60, 0xb9, 0xff}}},
    {{{    44,     44,    -96}, 0, {  1272,    592}, {0xda, 0x21, 0x8c, 0xff}}},
    {{{    52,    111,    -30}, 0, {   294,    656}, {0xa8, 0x32, 0xb5, 0xff}}},
    {{{    92,     88,     84}, 0, {   948,     -8}, {0x23, 0x59, 0x53, 0xff}}},
    {{{    77,    108,     34}, 0, {   330,    316}, {0x0e, 0x16, 0x7c, 0xff}}},
    {{{    48,     94,     76}, 0, {   848,    630}, {0xd7, 0x60, 0x47, 0xff}}},
    {{{    97,    103,     25}, 0, {   248,     50}, {0x40, 0x5a, 0x3d, 0xff}}},
    {{{    52,    111,     31}, 0, {   292,    670}, {0xa8, 0x33, 0x4b, 0xff}}},
    {{{    44,     44,     97}, 0, {  1276,    566}, {0xda, 0x21, 0x74, 0xff}}},
    {{{     3,     54,     52}, 0, {   768,   1210}, {0x95, 0x1d, 0x3c, 0xff}}},
    {{{    27,    100,     30}, 0, {   336,   1006}, {0xb1, 0x60, 0x16, 0xff}}},
    {{{     3,     54,    -51}, 0, {   720,   1228}, {0x95, 0x1d, 0xc4, 0xff}}},
    {{{    41,    109,      0}, 0, {   -30,    876}, {0x95, 0x44, 0x00, 0xff}}},
};

// 0x04012410
static const Vtx mario_mustache_cap_off_dl_vertex_group2[] = {
    {{{    41,    109,      0}, 0, {   -38,    852}, {0x95, 0x44, 0x00, 0xff}}},
    {{{    52,    111,    -30}, 0, {   294,    656}, {0xa8, 0x32, 0xb5, 0xff}}},
    {{{    27,    100,    -29}, 0, {   310,   1002}, {0xb1, 0x60, 0xea, 0xff}}},
    {{{    48,     94,    -75}, 0, {   844,    640}, {0xd7, 0x60, 0xb9, 0xff}}},
    {{{     3,     54,    -51}, 0, {   720,   1228}, {0x95, 0x1d, 0xc4, 0xff}}},
};

// 0x04012460
static const Vtx mario_face_part_cap_off_dl_vertex_group1[] = {
    {{{    97,    103,     25}, 0, {     0,      0}, {0x40, 0x5a, 0x3d, 0xff}}},
    {{{   108,    105,      0}, 0, {     0,      0}, {0x69, 0x46, 0x00, 0xff}}},
    {{{   117,    128,      0}, 0, {     0,      0}, {0x7a, 0xe1, 0x06, 0xff}}},
    {{{    92,     88,    -83}, 0, {     0,      0}, {0x23, 0x59, 0xad, 0xff}}},
    {{{    97,    103,    -24}, 0, {     0,      0}, {0x40, 0x5a, 0xc3, 0xff}}},
    {{{   104,     87,    -69}, 0, {     0,      0}, {0x30, 0x5c, 0xb7, 0xff}}},
    {{{    96,     49,    -93}, 0, {     0,      0}, {0x11, 0x27, 0x89, 0xff}}},
    {{{    44,     44,    -96}, 0, {     0,      0}, {0xda, 0x21, 0x8c, 0xff}}},
    {{{   102,    129,    -40}, 0, {     0,      0}, {0x50, 0xdc, 0xa6, 0xff}}},
    {{{    77,    108,    -33}, 0, {     0,      0}, {0x0e, 0x16, 0x84, 0xff}}},
    {{{   144,     83,    -66}, 0, {     0,      0}, {0x19, 0x5b, 0xad, 0xff}}},
    {{{    44,     44,     97}, 0, {     0,      0}, {0xda, 0x21, 0x74, 0xff}}},
    {{{    96,     49,     94}, 0, {     0,      0}, {0x10, 0x28, 0x77, 0xff}}},
    {{{    92,     88,     84}, 0, {     0,      0}, {0x23, 0x59, 0x53, 0xff}}},
    {{{   104,     87,     70}, 0, {     0,      0}, {0x30, 0x5c, 0x49, 0xff}}},
};

// 0x04012550
static const Vtx mario_face_part_cap_off_dl_vertex_group2[] = {
    {{{    -7,     62,      0}, 0, {     0,      0}, {0x85, 0x1d, 0x00, 0xff}}},
    {{{     3,     54,     52}, 0, {     0,      0}, {0x95, 0x1d, 0x3c, 0xff}}},
    {{{    27,    100,     30}, 0, {     0,      0}, {0xb1, 0x60, 0x16, 0xff}}},
    {{{   144,     83,     67}, 0, {     0,      0}, {0x19, 0x5b, 0x53, 0xff}}},
    {{{   104,     87,     70}, 0, {     0,      0}, {0x30, 0x5c, 0x49, 0xff}}},
    {{{    96,     49,     94}, 0, {     0,      0}, {0x10, 0x28, 0x77, 0xff}}},
    {{{   117,    128,      0}, 0, {     0,      0}, {0x7a, 0xe1, 0x06, 0xff}}},
    {{{   102,    129,     41}, 0, {     0,      0}, {0x45, 0xd6, 0x61, 0xff}}},
    {{{    97,    103,     25}, 0, {     0,      0}, {0x40, 0x5a, 0x3d, 0xff}}},
    {{{    77,    108,     34}, 0, {     0,      0}, {0x0e, 0x16, 0x7c, 0xff}}},
    {{{    44,     44,     97}, 0, {     0,      0}, {0xda, 0x21, 0x74, 0xff}}},
    {{{    42,      0,    103}, 0, {     0,      0}, {0xc3, 0x05, 0x6e, 0xff}}},
    {{{    17,    -52,     47}, 0, {     0,      0}, {0x89, 0xf8, 0x29, 0xff}}},
    {{{    64,    -25,    135}, 0, {     0,      0}, {0xcc, 0xe5, 0x70, 0xff}}},
    {{{    56,    -45,     77}, 0, {     0,      0}, {0xe3, 0xca, 0x6e, 0xff}}},
    {{{    85,    -23,    111}, 0, {     0,      0}, {0x04, 0x58, 0x5a, 0xff}}},
};

// 0x04012650
static const Vtx mario_face_part_cap_off_dl_vertex_group3[] = {
    {{{    96,     49,     94}, 0, {     0,      0}, {0x10, 0x28, 0x77, 0xff}}},
    {{{   153,     33,     95}, 0, {     0,      0}, {0x05, 0x38, 0x71, 0xff}}},
    {{{   144,     83,     67}, 0, {     0,      0}, {0x19, 0x5b, 0x53, 0xff}}},
    {{{    64,    -25,    135}, 0, {     0,      0}, {0xcc, 0xe5, 0x70, 0xff}}},
    {{{    91,    -54,     86}, 0, {     0,      0}, {0xe9, 0xa9, 0x59, 0xff}}},
    {{{   102,    -33,    135}, 0, {     0,      0}, {0x30, 0x18, 0x72, 0xff}}},
    {{{    56,    -45,     77}, 0, {     0,      0}, {0xe3, 0xca, 0x6e, 0xff}}},
    {{{    85,    -23,    111}, 0, {     0,      0}, {0x04, 0x58, 0x5a, 0xff}}},
    {{{   120,    -11,    111}, 0, {     0,      0}, {0xff, 0x13, 0x7d, 0xff}}},
    {{{    52,    111,    -30}, 0, {     0,      0}, {0xa8, 0x32, 0xb5, 0xff}}},
    {{{    41,    109,      0}, 0, {     0,      0}, {0x95, 0x44, 0x00, 0xff}}},
    {{{    32,    132,      0}, 0, {     0,      0}, {0x84, 0xe8, 0xf8, 0xff}}},
    {{{    85,    -23,   -110}, 0, {     0,      0}, {0x06, 0x59, 0xa6, 0xff}}},
    {{{    64,    -25,   -134}, 0, {     0,      0}, {0xcc, 0xe5, 0x90, 0xff}}},
    {{{    42,      0,   -102}, 0, {     0,      0}, {0xc3, 0x05, 0x92, 0xff}}},
};

// 0x04012740
static const Vtx mario_face_part_cap_off_dl_vertex_group4[] = {
    {{{    27,    100,     30}, 0, {     0,      0}, {0xb1, 0x60, 0x16, 0xff}}},
    {{{    27,    100,    -29}, 0, {     0,      0}, {0xb1, 0x60, 0xea, 0xff}}},
    {{{    -7,     62,      0}, 0, {     0,      0}, {0x85, 0x1d, 0x00, 0xff}}},
    {{{     3,     54,    -51}, 0, {     0,      0}, {0x95, 0x1d, 0xc4, 0xff}}},
    {{{    41,    109,      0}, 0, {     0,      0}, {0x95, 0x44, 0x00, 0xff}}},
    {{{   144,     83,    -66}, 0, {     0,      0}, {0x19, 0x5b, 0xad, 0xff}}},
    {{{   153,     33,    -94}, 0, {     0,      0}, {0xfe, 0x36, 0x8e, 0xff}}},
    {{{    96,     49,    -93}, 0, {     0,      0}, {0x11, 0x27, 0x89, 0xff}}},
    {{{    42,      0,   -102}, 0, {     0,      0}, {0xc3, 0x05, 0x92, 0xff}}},
    {{{    44,     44,    -96}, 0, {     0,      0}, {0xda, 0x21, 0x8c, 0xff}}},
    {{{    64,    -25,   -134}, 0, {     0,      0}, {0xcc, 0xe5, 0x90, 0xff}}},
    {{{   102,    -33,   -134}, 0, {     0,      0}, {0x32, 0x19, 0x8f, 0xff}}},
    {{{    91,    -54,    -85}, 0, {     0,      0}, {0xe8, 0xaa, 0xa7, 0xff}}},
    {{{    85,    -23,   -110}, 0, {     0,      0}, {0x06, 0x59, 0xa6, 0xff}}},
    {{{   120,    -12,   -109}, 0, {     0,      0}, {0xff, 0x13, 0x83, 0xff}}},
};

// 0x04012830
static const Vtx mario_face_part_cap_off_dl_vertex_group5[] = {
    {{{    33,    165,      0}, 0, {     0,      0}, {0x8f, 0x38, 0x09, 0xff}}},
    {{{    60,    181,      0}, 0, {     0,      0}, {0xea, 0x7c, 0x00, 0xff}}},
    {{{    52,    164,    -40}, 0, {     0,      0}, {0xb8, 0x34, 0xa6, 0xff}}},
    {{{   102,    -33,   -134}, 0, {     0,      0}, {0x32, 0x19, 0x8f, 0xff}}},
    {{{   120,    -12,   -109}, 0, {     0,      0}, {0xff, 0x13, 0x83, 0xff}}},
    {{{    91,    -54,    -85}, 0, {     0,      0}, {0xe8, 0xaa, 0xa7, 0xff}}},
    {{{    56,    -45,    -76}, 0, {     0,      0}, {0xe3, 0xca, 0x92, 0xff}}},
    {{{    64,    -25,   -134}, 0, {     0,      0}, {0xcc, 0xe5, 0x90, 0xff}}},
    {{{    42,      0,   -102}, 0, {     0,      0}, {0xc3, 0x05, 0x92, 0xff}}},
    {{{    17,    -52,    -46}, 0, {     0,      0}, {0x89, 0xf8, 0xd7, 0xff}}},
    {{{     3,     54,    -51}, 0, {     0,      0}, {0x95, 0x1d, 0xc4, 0xff}}},
    {{{    -7,     62,      0}, 0, {     0,      0}, {0x85, 0x1d, 0x00, 0xff}}},
    {{{    17,    -52,     47}, 0, {     0,      0}, {0x89, 0xf8, 0x29, 0xff}}},
    {{{    52,    164,     41}, 0, {     0,      0}, {0xc8, 0x3d, 0x5f, 0xff}}},
    {{{    78,    163,     46}, 0, {     0,      0}, {0x03, 0x55, 0x5d, 0xff}}},
    {{{   101,    179,      0}, 0, {     0,      0}, {0x27, 0x78, 0x00, 0xff}}},
};

// 0x04012930
static const Vtx mario_face_part_cap_off_dl_vertex_group6[] = {
    {{{   104,    162,     41}, 0, {     0,      0}, {0x52, 0x27, 0x58, 0xff}}},
    {{{   101,    179,      0}, 0, {     0,      0}, {0x27, 0x78, 0x00, 0xff}}},
    {{{    78,    163,     46}, 0, {     0,      0}, {0x03, 0x55, 0x5d, 0xff}}},
    {{{    52,    164,     41}, 0, {     0,      0}, {0xc8, 0x3d, 0x5f, 0xff}}},
    {{{    75,    130,     46}, 0, {     0,      0}, {0x00, 0xec, 0x7d, 0xff}}},
    {{{   119,    161,      0}, 0, {     0,      0}, {0x79, 0x23, 0xf6, 0xff}}},
    {{{   104,    162,    -40}, 0, {     0,      0}, {0x44, 0x31, 0xa1, 0xff}}},
    {{{    78,    163,    -45}, 0, {     0,      0}, {0x03, 0x54, 0xa2, 0xff}}},
    {{{    60,    181,      0}, 0, {     0,      0}, {0xea, 0x7c, 0x00, 0xff}}},
    {{{    47,    131,     41}, 0, {     0,      0}, {0xb0, 0xe4, 0x5e, 0xff}}},
    {{{    33,    165,      0}, 0, {     0,      0}, {0x8f, 0x38, 0x09, 0xff}}},
    {{{   102,    129,     41}, 0, {     0,      0}, {0x45, 0xd6, 0x61, 0xff}}},
    {{{    77,    108,     34}, 0, {     0,      0}, {0x0e, 0x16, 0x7c, 0xff}}},
    {{{    52,    164,    -40}, 0, {     0,      0}, {0xb8, 0x34, 0xa6, 0xff}}},
    {{{    32,    132,      0}, 0, {     0,      0}, {0x84, 0xe8, 0xf8, 0xff}}},
    {{{   117,    128,      0}, 0, {     0,      0}, {0x7a, 0xe1, 0x06, 0xff}}},
};

// 0x04012A30
static const Vtx mario_face_part_cap_off_dl_vertex_group7[] = {
    {{{    52,    111,     31}, 0, {     0,      0}, {0xa8, 0x33, 0x4b, 0xff}}},
    {{{    47,    131,     41}, 0, {     0,      0}, {0xb0, 0xe4, 0x5e, 0xff}}},
    {{{    32,    132,      0}, 0, {     0,      0}, {0x84, 0xe8, 0xf8, 0xff}}},
    {{{    77,    108,     34}, 0, {     0,      0}, {0x0e, 0x16, 0x7c, 0xff}}},
    {{{    52,    164,    -40}, 0, {     0,      0}, {0xb8, 0x34, 0xa6, 0xff}}},
    {{{    78,    163,    -45}, 0, {     0,      0}, {0x03, 0x54, 0xa2, 0xff}}},
    {{{    75,    130,    -45}, 0, {     0,      0}, {0x00, 0xec, 0x83, 0xff}}},
    {{{   104,    162,    -40}, 0, {     0,      0}, {0x44, 0x31, 0xa1, 0xff}}},
    {{{    60,    181,      0}, 0, {     0,      0}, {0xea, 0x7c, 0x00, 0xff}}},
    {{{    17,    -52,    -46}, 0, {     0,      0}, {0x89, 0xf8, 0xd7, 0xff}}},
    {{{    31,    -84,      0}, 0, {     0,      0}, {0xa0, 0xae, 0x00, 0xff}}},
    {{{    17,    -52,     47}, 0, {     0,      0}, {0x89, 0xf8, 0x29, 0xff}}},
    {{{    41,    109,      0}, 0, {     0,      0}, {0x95, 0x44, 0x00, 0xff}}},
    {{{    47,    131,    -40}, 0, {     0,      0}, {0xbc, 0xde, 0x9c, 0xff}}},
    {{{   102,    129,    -40}, 0, {     0,      0}, {0x50, 0xdc, 0xa6, 0xff}}},
    {{{    77,    108,    -33}, 0, {     0,      0}, {0x0e, 0x16, 0x84, 0xff}}},
};

// 0x04012B30
static const Vtx mario_face_part_cap_off_dl_vertex_group8[] = {
    {{{   102,    129,    -40}, 0, {     0,      0}, {0x50, 0xdc, 0xa6, 0xff}}},
    {{{   104,    162,    -40}, 0, {     0,      0}, {0x44, 0x31, 0xa1, 0xff}}},
    {{{   119,    161,      0}, 0, {     0,      0}, {0x79, 0x23, 0xf6, 0xff}}},
    {{{   117,    128,      0}, 0, {     0,      0}, {0x7a, 0xe1, 0x06, 0xff}}},
    {{{    32,    132,      0}, 0, {     0,      0}, {0x84, 0xe8, 0xf8, 0xff}}},
    {{{    47,    131,    -40}, 0, {     0,      0}, {0xbc, 0xde, 0x9c, 0xff}}},
    {{{    52,    111,    -30}, 0, {     0,      0}, {0xa8, 0x32, 0xb5, 0xff}}},
    {{{    77,    108,    -33}, 0, {     0,      0}, {0x0e, 0x16, 0x84, 0xff}}},
    {{{    52,    164,    -40}, 0, {     0,      0}, {0xb8, 0x34, 0xa6, 0xff}}},
};

// 0x04012BC0
static const Vtx mario_face_hair_cap_off_dl_vertex_group1[] = {
    {{{   153,     33,    -94}, 0, {     0,      0}, {0xfe, 0x36, 0x8e, 0xff}}},
    {{{   200,     70,    -97}, 0, {     0,      0}, {0x07, 0x48, 0x99, 0xff}}},
    {{{   182,     -9,   -115}, 0, {     0,      0}, {0x2f, 0xfe, 0x8b, 0xff}}},
    {{{   153,     33,     95}, 0, {     0,      0}, {0x05, 0x38, 0x71, 0xff}}},
    {{{   200,     75,     84}, 0, {     0,      0}, {0x10, 0x52, 0x5f, 0xff}}},
    {{{   151,     72,     70}, 0, {     0,      0}, {0xe4, 0x5c, 0x51, 0xff}}},
    {{{   120,    -11,    111}, 0, {     0,      0}, {0xff, 0x13, 0x7d, 0xff}}},
    {{{   181,     -9,    116}, 0, {     0,      0}, {0x2d, 0x01, 0x76, 0xff}}},
    {{{   175,     91,     39}, 0, {     0,      0}, {0x01, 0x75, 0x2f, 0xff}}},
    {{{   209,     86,     60}, 0, {     0,      0}, {0x38, 0x60, 0x3c, 0xff}}},
    {{{   227,     35,     75}, 0, {     0,      0}, {0x6d, 0x12, 0x3d, 0xff}}},
    {{{   175,     91,    -38}, 0, {     0,      0}, {0xf5, 0x73, 0xcc, 0xff}}},
    {{{   151,     72,    -68}, 0, {     0,      0}, {0xd6, 0x5b, 0xb3, 0xff}}},
    {{{   221,     35,    -73}, 0, {     0,      0}, {0x71, 0x03, 0xc8, 0xff}}},
    {{{   211,     91,    -71}, 0, {     0,      0}, {0x31, 0x46, 0xa3, 0xff}}},
    {{{   227,    -14,    -58}, 0, {     0,      0}, {0x72, 0xf1, 0xcc, 0xff}}},
};

// 0x04012CC0
static const Vtx mario_face_hair_cap_off_dl_vertex_group2[] = {
    {{{   120,    -12,   -109}, 0, {     0,      0}, {0xff, 0x13, 0x83, 0xff}}},
    {{{   153,     33,    -94}, 0, {     0,      0}, {0xfe, 0x36, 0x8e, 0xff}}},
    {{{   182,     -9,   -115}, 0, {     0,      0}, {0x2f, 0xfe, 0x8b, 0xff}}},
    {{{   227,    -14,     59}, 0, {     0,      0}, {0x72, 0xec, 0x32, 0xff}}},
    {{{   241,    -34,      0}, 0, {     0,      0}, {0x7a, 0xe0, 0x00, 0xff}}},
    {{{   237,     64,      0}, 0, {     0,      0}, {0x7e, 0xf9, 0xff, 0xff}}},
    {{{   189,   -102,     73}, 0, {     0,      0}, {0x50, 0xbb, 0x44, 0xff}}},
    {{{   181,     -9,    116}, 0, {     0,      0}, {0x2d, 0x01, 0x76, 0xff}}},
    {{{   227,     35,     75}, 0, {     0,      0}, {0x6d, 0x12, 0x3d, 0xff}}},
    {{{   189,   -102,    -71}, 0, {     0,      0}, {0x50, 0xb9, 0xbd, 0xff}}},
    {{{   227,    -14,    -58}, 0, {     0,      0}, {0x72, 0xf1, 0xcc, 0xff}}},
    {{{   200,   -117,      0}, 0, {     0,      0}, {0x3f, 0x93, 0x00, 0xff}}},
    {{{   221,     35,    -73}, 0, {     0,      0}, {0x71, 0x03, 0xc8, 0xff}}},
    {{{   248,    104,    -43}, 0, {     0,      0}, {0x76, 0x2c, 0xf3, 0xff}}},
    {{{   231,     81,     33}, 0, {     0,      0}, {0x71, 0x27, 0x2a, 0xff}}},
};

// 0x04012DB0
static const Vtx mario_face_hair_cap_off_dl_vertex_group3[] = {
    {{{   221,     35,    -73}, 0, {     0,      0}, {0x71, 0x03, 0xc8, 0xff}}},
    {{{   211,     91,    -71}, 0, {     0,      0}, {0x31, 0x46, 0xa3, 0xff}}},
    {{{   232,     69,    -34}, 0, {     0,      0}, {0x72, 0xe4, 0xd1, 0xff}}},
    {{{   237,     64,      0}, 0, {     0,      0}, {0x7e, 0xf9, 0xff, 0xff}}},
    {{{   175,     91,    -38}, 0, {     0,      0}, {0xf5, 0x73, 0xcc, 0xff}}},
    {{{   201,    125,    -13}, 0, {     0,      0}, {0xe4, 0x7b, 0xfb, 0xff}}},
    {{{   160,     96,      0}, 0, {     0,      0}, {0xb7, 0x67, 0xfe, 0xff}}},
    {{{   189,   -102,    -71}, 0, {     0,      0}, {0x50, 0xb9, 0xbd, 0xff}}},
    {{{   116,   -124,    -67}, 0, {     0,      0}, {0xfc, 0x8f, 0xc8, 0xff}}},
    {{{   145,    -84,   -104}, 0, {     0,      0}, {0xef, 0xcf, 0x8d, 0xff}}},
    {{{   182,     -9,   -115}, 0, {     0,      0}, {0x2f, 0xfe, 0x8b, 0xff}}},
    {{{   200,   -117,      0}, 0, {     0,      0}, {0x3f, 0x93, 0x00, 0xff}}},
    {{{   189,   -102,     73}, 0, {     0,      0}, {0x50, 0xbb, 0x44, 0xff}}},
    {{{   116,   -126,     68}, 0, {     0,      0}, {0xfb, 0x8f, 0x38, 0xff}}},
    {{{   119,   -134,      0}, 0, {     0,      0}, {0xe8, 0x84, 0xff, 0xff}}},
    {{{   145,    -83,    105}, 0, {     0,      0}, {0xef, 0xd0, 0x73, 0xff}}},
};

// 0x04012EB0
static const Vtx mario_face_hair_cap_off_dl_vertex_group4[] = {
    {{{   145,    -83,    105}, 0, {     0,      0}, {0xef, 0xd0, 0x73, 0xff}}},
    {{{   189,   -102,     73}, 0, {     0,      0}, {0x50, 0xbb, 0x44, 0xff}}},
    {{{   181,     -9,    116}, 0, {     0,      0}, {0x2d, 0x01, 0x76, 0xff}}},
    {{{   201,    125,    -13}, 0, {     0,      0}, {0xe4, 0x7b, 0xfb, 0xff}}},
    {{{   209,     86,     60}, 0, {     0,      0}, {0x38, 0x60, 0x3c, 0xff}}},
    {{{   248,    104,    -43}, 0, {     0,      0}, {0x76, 0x2c, 0xf3, 0xff}}},
    {{{   231,     81,     33}, 0, {     0,      0}, {0x71, 0x27, 0x2a, 0xff}}},
    {{{   232,     69,    -34}, 0, {     0,      0}, {0x72, 0xe4, 0xd1, 0xff}}},
    {{{   211,     91,    -71}, 0, {     0,      0}, {0x31, 0x46, 0xa3, 0xff}}},
    {{{   237,     64,      0}, 0, {     0,      0}, {0x7e, 0xf9, 0xff, 0xff}}},
    {{{   227,     35,     75}, 0, {     0,      0}, {0x6d, 0x12, 0x3d, 0xff}}},
    {{{   175,     91,     39}, 0, {     0,      0}, {0x01, 0x75, 0x2f, 0xff}}},
    {{{   160,     96,      0}, 0, {     0,      0}, {0xb7, 0x67, 0xfe, 0xff}}},
    {{{    64,   -103,     52}, 0, {     0,      0}, {0xf3, 0x8b, 0x2e, 0xff}}},
    {{{    64,   -103,    -51}, 0, {     0,      0}, {0xf3, 0x8b, 0xd2, 0xff}}},
    {{{   119,   -134,      0}, 0, {     0,      0}, {0xe8, 0x84, 0xff, 0xff}}},
};

// 0x04012FB0
static const Vtx mario_face_hair_cap_off_dl_vertex_group5[] = {
    {{{   145,    -83,    105}, 0, {     0,      0}, {0xef, 0xd0, 0x73, 0xff}}},
    {{{   181,     -9,    116}, 0, {     0,      0}, {0x2d, 0x01, 0x76, 0xff}}},
    {{{   120,    -11,    111}, 0, {     0,      0}, {0xff, 0x13, 0x7d, 0xff}}},
    {{{    64,   -103,    -51}, 0, {     0,      0}, {0xf3, 0x8b, 0xd2, 0xff}}},
    {{{   116,   -124,    -67}, 0, {     0,      0}, {0xfc, 0x8f, 0xc8, 0xff}}},
    {{{   119,   -134,      0}, 0, {     0,      0}, {0xe8, 0x84, 0xff, 0xff}}},
    {{{   145,    -84,   -104}, 0, {     0,      0}, {0xef, 0xcf, 0x8d, 0xff}}},
    {{{   182,     -9,   -115}, 0, {     0,      0}, {0x2f, 0xfe, 0x8b, 0xff}}},
    {{{   120,    -12,   -109}, 0, {     0,      0}, {0xff, 0x13, 0x83, 0xff}}},
    {{{    91,    -54,    -85}, 0, {     0,      0}, {0xe8, 0xaa, 0xa7, 0xff}}},
    {{{    64,   -103,     52}, 0, {     0,      0}, {0xf3, 0x8b, 0x2e, 0xff}}},
    {{{   116,   -126,     68}, 0, {     0,      0}, {0xfb, 0x8f, 0x38, 0xff}}},
    {{{    91,    -54,     86}, 0, {     0,      0}, {0xe9, 0xa9, 0x59, 0xff}}},
    {{{    31,    -84,      0}, 0, {     0,      0}, {0xa0, 0xae, 0x00, 0xff}}},
    {{{    36,   -116,     36}, 0, {     0,      0}, {0xfb, 0x8e, 0xc9, 0xff}}},
    {{{     9,    -88,     59}, 0, {     0,      0}, {0x87, 0xdf, 0x0b, 0xff}}},
};

// 0x040130B0
static const Vtx mario_face_hair_cap_off_dl_vertex_group6[] = {
    {{{    64,   -103,     52}, 0, {     0,      0}, {0xf3, 0x8b, 0x2e, 0xff}}},
    {{{    91,    -54,     86}, 0, {     0,      0}, {0xe9, 0xa9, 0x59, 0xff}}},
    {{{    56,    -45,     77}, 0, {     0,      0}, {0xe3, 0xca, 0x6e, 0xff}}},
    {{{    31,    -84,      0}, 0, {     0,      0}, {0xa0, 0xae, 0x00, 0xff}}},
    {{{    36,   -116,     36}, 0, {     0,      0}, {0xfb, 0x8e, 0xc9, 0xff}}},
    {{{    58,   -114,     60}, 0, {     0,      0}, {0x57, 0xb4, 0x32, 0xff}}},
    {{{    36,   -116,    -35}, 0, {     0,      0}, {0xfb, 0x8e, 0x37, 0xff}}},
    {{{    64,   -103,    -51}, 0, {     0,      0}, {0xf3, 0x8b, 0xd2, 0xff}}},
    {{{    56,    -45,    -76}, 0, {     0,      0}, {0xe3, 0xca, 0x92, 0xff}}},
    {{{    91,    -54,    -85}, 0, {     0,      0}, {0xe8, 0xaa, 0xa7, 0xff}}},
    {{{    58,   -114,    -59}, 0, {     0,      0}, {0x57, 0xb4, 0xce, 0xff}}},
    {{{    17,    -52,    -46}, 0, {     0,      0}, {0x89, 0xf8, 0xd7, 0xff}}},
    {{{     9,    -88,    -58}, 0, {     0,      0}, {0x87, 0xdf, 0xf5, 0xff}}},
    {{{     9,    -88,     59}, 0, {     0,      0}, {0x87, 0xdf, 0x0b, 0xff}}},
    {{{    17,    -52,     47}, 0, {     0,      0}, {0x89, 0xf8, 0x29, 0xff}}},
    {{{    35,   -120,    -67}, 0, {     0,      0}, {0xda, 0x94, 0xcb, 0xff}}},
};

// 0x040131B0
static const Vtx mario_face_hair_cap_off_dl_vertex_group7[] = {
    {{{    36,   -116,     36}, 0, {     0,      0}, {0xfb, 0x8e, 0xc9, 0xff}}},
    {{{    35,   -120,     68}, 0, {     0,      0}, {0xda, 0x94, 0x35, 0xff}}},
    {{{     9,    -88,     59}, 0, {     0,      0}, {0x87, 0xdf, 0x0b, 0xff}}},
    {{{    58,   -114,     60}, 0, {     0,      0}, {0x57, 0xb4, 0x32, 0xff}}},
    {{{    35,   -120,    -67}, 0, {     0,      0}, {0xda, 0x94, 0xcb, 0xff}}},
    {{{    31,    -85,    -86}, 0, {     0,      0}, {0xdb, 0xfd, 0x87, 0xff}}},
    {{{    58,   -114,    -59}, 0, {     0,      0}, {0x57, 0xb4, 0xce, 0xff}}},
    {{{     9,    -88,    -58}, 0, {     0,      0}, {0x87, 0xdf, 0xf5, 0xff}}},
    {{{    56,    -45,    -76}, 0, {     0,      0}, {0xe3, 0xca, 0x92, 0xff}}},
    {{{    17,    -52,    -46}, 0, {     0,      0}, {0x89, 0xf8, 0xd7, 0xff}}},
    {{{    31,    -85,     87}, 0, {     0,      0}, {0xdb, 0xfd, 0x79, 0xff}}},
    {{{    56,    -45,     77}, 0, {     0,      0}, {0xe3, 0xca, 0x6e, 0xff}}},
    {{{    17,    -52,     47}, 0, {     0,      0}, {0x89, 0xf8, 0x29, 0xff}}},
};

// 0x04013280
static const Vtx mario_face_part_cap_off_dl_vertex_group9[] = {
    {{{    44,     44,     97}, 0, {     0,      0}, {0xda, 0x21, 0x74, 0xff}}},
    {{{    48,     94,     76}, 0, {     0,      0}, {0xd7, 0x60, 0x47, 0xff}}},
    {{{     3,     54,     52}, 0, {     0,      0}, {0x95, 0x1d, 0x3c, 0xff}}},
};

// 0x040132B0 - 0x04013318
const Gfx mario_eyes_cap_off_dl[] = {
    gsSPVertex(mario_eyes_cap_off_dl_vertex, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  7, 0x0,  8,  6,  4, 0x0),
    gsSP2Triangles( 5,  4,  7, 0x0,  7,  9,  5, 0x0),
    gsSP2Triangles( 2,  8, 10, 0x0,  1,  6,  8, 0x0),
    gsSP2Triangles( 1,  8,  2, 0x0,  6, 11,  7, 0x0),
    gsSP1Triangle( 1, 11,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x04013318 - 0x040133A8
const Gfx mario_mustache_cap_off_dl[] = {
    gsSPVertex(mario_mustache_cap_off_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 0,  4,  3, 0x0,  3,  5,  1, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  7,  6,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0,  8, 11,  6, 0x0),
    gsSP2Triangles(12,  8, 13, 0x0, 13,  8, 10, 0x0),
    gsSP2Triangles(14,  3,  4, 0x0, 13, 10, 15, 0x0),
    gsSPVertex(mario_mustache_cap_off_dl_vertex_group2, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP1Triangle( 2,  3,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x040133A8 - 0x040133F8
const Gfx mario_hair_sideburn_cap_off_dl[] = {
    gsSPVertex(mario_hair_sideburn_cap_off_dl_vertex, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  2,  5, 0x0,  1,  5,  2, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  9,  6, 0x0),
    gsSP2Triangles( 9,  8, 10, 0x0, 10,  8, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x040133F8 - 0x040136D0
const Gfx mario_face_part_cap_off_dl[] = {
    gsSPVertex(mario_face_part_cap_off_dl_vertex_group1, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  3,  6,  7, 0x0),
    gsSP2Triangles( 4,  8,  2, 0x0,  8,  4,  9, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  6,  5, 10, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0, 14, 13, 12, 0x0),
    gsSP1Triangle(13, 14,  0, 0x0),
    gsSPVertex(mario_face_part_cap_off_dl_vertex_group2, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9,  8,  7, 0x0),
    gsSP2Triangles(10,  1, 11, 0x0,  1, 12, 11, 0x0),
    gsSP2Triangles(12,  1,  0, 0x0, 13, 11, 14, 0x0),
    gsSP2Triangles(12, 14, 11, 0x0, 11, 13, 15, 0x0),
    gsSPVertex(mario_face_part_cap_off_dl_vertex_group3, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  3,  5,  7, 0x0),
    gsSP2Triangles( 8,  7,  5, 0x0,  4,  8,  5, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0, 12, 13, 14, 0x0),
    gsSPVertex(mario_face_part_cap_off_dl_vertex_group4, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  1,  0, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 8,  3,  9, 0x0, 10, 11, 12, 0x0),
    gsSP2Triangles(13, 11, 10, 0x0, 11, 13, 14, 0x0),
    gsSPVertex(mario_face_part_cap_off_dl_vertex_group5, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  5, 0x0,  8,  7,  6, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8,  6,  9, 0x0),
    gsSP2Triangles( 9, 11, 10, 0x0,  9, 12, 11, 0x0),
    gsSP2Triangles( 0, 13,  1, 0x0, 13, 14,  1, 0x0),
    gsSP1Triangle( 1, 14, 15, 0x0),
    gsSPVertex(mario_face_part_cap_off_dl_vertex_group6, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  1,  0,  5, 0x0),
    gsSP2Triangles( 1,  6,  7, 0x0,  1,  5,  6, 0x0),
    gsSP2Triangles( 7,  8,  1, 0x0,  3,  9,  4, 0x0),
    gsSP2Triangles( 9,  3, 10, 0x0,  4, 11,  0, 0x0),
    gsSP2Triangles(11,  4, 12, 0x0,  4,  9, 12, 0x0),
    gsSP2Triangles(10, 13, 14, 0x0,  0, 11, 15, 0x0),
    gsSP2Triangles( 5,  0, 15, 0x0, 14,  9, 10, 0x0),
    gsSPVertex(mario_face_part_cap_off_dl_vertex_group7, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 8,  5,  4, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 2, 12,  0, 0x0, 13,  4,  6, 0x0),
    gsSP2Triangles( 6, 14, 15, 0x0,  6,  7, 14, 0x0),
    gsSP1Triangle(13,  6, 15, 0x0),
    gsSPVertex(mario_face_part_cap_off_dl_vertex_group8, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  6,  5, 0x0),
    gsSP1Triangle( 8,  5,  4, 0x0),
    gsSPVertex(mario_face_part_cap_off_dl_vertex_group9, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x040136D0 - 0x040139C0
const Gfx mario_face_hair_cap_off_dl[] = {
    gsSPVertex(mario_face_hair_cap_off_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  7, 0x0,  3,  7,  4, 0x0),
    gsSP2Triangles( 8,  4,  9, 0x0,  7, 10,  4, 0x0),
    gsSP2Triangles( 4, 10,  9, 0x0,  8,  5,  4, 0x0),
    gsSP2Triangles(11,  1, 12, 0x0,  1,  0, 12, 0x0),
    gsSP2Triangles( 1, 13,  2, 0x0,  1, 11, 14, 0x0),
    gsSP2Triangles(13,  1, 14, 0x0, 15,  2, 13, 0x0),
    gsSPVertex(mario_face_hair_cap_off_dl_vertex_group2, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  3,  7,  6, 0x0),
    gsSP2Triangles( 8,  7,  3, 0x0,  5,  8,  3, 0x0),
    gsSP2Triangles( 9, 10,  4, 0x0, 11,  4,  6, 0x0),
    gsSP2Triangles(11,  9,  4, 0x0, 10,  5,  4, 0x0),
    gsSP2Triangles(10, 12,  5, 0x0,  5, 13, 14, 0x0),
    gsSP2Triangles( 8,  5, 14, 0x0,  9,  2, 10, 0x0),
    gsSPVertex(mario_face_hair_cap_off_dl_vertex_group3, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 1,  4,  5, 0x0,  4,  6,  5, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0, 10,  7,  9, 0x0),
    gsSP2Triangles( 8,  7, 11, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(14, 11, 13, 0x0, 14,  8, 11, 0x0),
    gsSP1Triangle(15, 13, 12, 0x0),
    gsSPVertex(mario_face_hair_cap_off_dl_vertex_group4, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  5, 0x0),
    gsSP2Triangles( 8,  3,  5, 0x0,  9,  7,  5, 0x0),
    gsSP2Triangles(10,  6,  4, 0x0,  4,  3, 11, 0x0),
    gsSP2Triangles( 3, 12, 11, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(mario_face_hair_cap_off_dl_vertex_group5, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  4,  3, 0x0,  7,  6,  8, 0x0),
    gsSP2Triangles( 9,  6,  3, 0x0,  6,  9,  8, 0x0),
    gsSP2Triangles(10, 11,  0, 0x0, 12,  0,  2, 0x0),
    gsSP2Triangles(10,  0, 12, 0x0, 10,  5, 11, 0x0),
    gsSP2Triangles(13, 14, 15, 0x0, 13,  3, 10, 0x0),
    gsSPVertex(mario_face_hair_cap_off_dl_vertex_group6, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 5,  0,  2, 0x0,  5,  4,  0, 0x0),
    gsSP2Triangles( 3,  6,  7, 0x0,  8,  9,  7, 0x0),
    gsSP2Triangles( 6, 10,  7, 0x0,  8,  7, 10, 0x0),
    gsSP2Triangles( 3, 11, 12, 0x0, 13, 14,  3, 0x0),
    gsSP2Triangles(12,  6,  3, 0x0,  6, 15, 10, 0x0),
    gsSP1Triangle(12, 15,  6, 0x0),
    gsSPVertex(mario_face_hair_cap_off_dl_vertex_group7, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  4,  7, 0x0),
    gsSP2Triangles( 6,  5,  8, 0x0,  9,  8,  5, 0x0),
    gsSP2Triangles( 5,  7,  9, 0x0, 10, 11, 12, 0x0),
    gsSP2Triangles(11, 10,  3, 0x0, 12,  2, 10, 0x0),
    gsSP2Triangles( 2,  1, 10, 0x0, 10,  1,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x040139C0 - 0x040139E8
const Gfx mario_face_cap_off_dl[] = {
    gsSPDisplayList(mario_face_part_cap_off_dl),
    gsSPLight(&mario_brown2_lights_group.l, 1),
    gsSPLight(&mario_brown2_lights_group.a, 2),
    gsSPDisplayList(mario_face_hair_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x040139E8 - 0x04013AB8
const Gfx mario_cap_off_eyes_front[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_front),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04013AB8 - 0x04013B88
const Gfx mario_cap_off_eyes_half_closed[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_half_closed),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04013B88 - 0x04013C58
const Gfx mario_cap_off_eyes_closed[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_closed),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04013C58 - 0x04013D28
const Gfx mario_cap_off_eyes_right[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_right),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04013D28 - 0x04013DF8
const Gfx mario_cap_off_eyes_left[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_left),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04013DF8 - 0x04013EC8
const Gfx mario_cap_off_eyes_up[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_up),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04013EC8 - 0x04013F98
const Gfx mario_cap_off_eyes_down[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_down),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04013F98 - 0x04014068
const Gfx mario_cap_off_eyes_dead[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_dead),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_mustache_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_hair_sideburn),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_hair_sideburn_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04014068 - 0x04014098
const Gfx mario_metal_cap_off_shared_dl[] = {
    gsSPDisplayList(mario_eyes_cap_off_dl),
    gsSPDisplayList(mario_mustache_cap_off_dl),
    gsSPDisplayList(mario_hair_sideburn_cap_off_dl),
    gsSPDisplayList(mario_face_part_cap_off_dl),
    gsSPDisplayList(mario_face_hair_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04014098
static const Vtx mario_medium_poly_butt_dl_vertex_group1[] = {
    {{{     0,     78,     37}, 0, {     0,      0}, {0xc7, 0x6f, 0x14, 0x00}}},
    {{{    44,     80,     46}, 0, {     0,      0}, {0x36, 0x68, 0x2e, 0x00}}},
    {{{    43,     80,    -45}, 0, {     0,      0}, {0x2c, 0x70, 0xdb, 0x00}}},
    {{{    -6,    -84,     50}, 0, {     0,      0}, {0xd5, 0x90, 0x26, 0x00}}},
    {{{    41,    -86,    -45}, 0, {     0,      0}, {0x35, 0x90, 0xe7, 0x00}}},
    {{{    41,    -86,     45}, 0, {     0,      0}, {0x44, 0x9b, 0x1f, 0x00}}},
    {{{   -33,    -45,    -31}, 0, {     0,      0}, {0x87, 0xe9, 0xe3, 0x00}}},
    {{{   -33,    -45,     31}, 0, {     0,      0}, {0x86, 0xe5, 0x15, 0x00}}},
    {{{   -32,     43,    -22}, 0, {     0,      0}, {0x8b, 0x2d, 0xf0, 0x00}}},
    {{{    -2,     41,     95}, 0, {     0,      0}, {0xba, 0x3b, 0x57, 0x00}}},
    {{{    -5,    -45,     97}, 0, {     0,      0}, {0xc7, 0xde, 0x6b, 0x00}}},
    {{{    44,     36,     95}, 0, {     0,      0}, {0x3d, 0x22, 0x69, 0x00}}},
    {{{    -5,    -45,    -97}, 0, {     0,      0}, {0xc7, 0xdd, 0x95, 0x00}}},
    {{{    -2,     41,    -95}, 0, {     0,      0}, {0xba, 0x3b, 0xaa, 0x00}}},
    {{{    44,     35,    -96}, 0, {     0,      0}, {0x3c, 0x22, 0x96, 0x00}}},
    {{{    -6,    -84,    -50}, 0, {     0,      0}, {0xc1, 0x9a, 0xd9, 0x00}}},
};

// 0x04014198
static const Vtx mario_medium_poly_butt_dl_vertex_group2[] = {
    {{{    -2,     41,     95}, 0, {     0,      0}, {0xba, 0x3b, 0x57, 0x00}}},
    {{{     0,     78,     37}, 0, {     0,      0}, {0xc7, 0x6f, 0x14, 0x00}}},
    {{{   -32,     43,     22}, 0, {     0,      0}, {0x8c, 0x2c, 0x18, 0x00}}},
    {{{     0,     78,    -37}, 0, {     0,      0}, {0xc5, 0x6c, 0xe2, 0x00}}},
    {{{    -2,     41,    -95}, 0, {     0,      0}, {0xba, 0x3b, 0xaa, 0x00}}},
    {{{   -32,     43,    -22}, 0, {     0,      0}, {0x8b, 0x2d, 0xf0, 0x00}}},
    {{{   -33,    -45,     31}, 0, {     0,      0}, {0x86, 0xe5, 0x15, 0x00}}},
    {{{   -33,    -45,    -31}, 0, {     0,      0}, {0x87, 0xe9, 0xe3, 0x00}}},
    {{{    -6,    -84,    -50}, 0, {     0,      0}, {0xc1, 0x9a, 0xd9, 0x00}}},
    {{{    43,    -48,    -97}, 0, {     0,      0}, {0x3b, 0xc1, 0xa4, 0x00}}},
    {{{    41,    -86,    -45}, 0, {     0,      0}, {0x35, 0x90, 0xe7, 0x00}}},
    {{{    -6,    -84,     50}, 0, {     0,      0}, {0xd5, 0x90, 0x26, 0x00}}},
    {{{    41,    -86,     45}, 0, {     0,      0}, {0x44, 0x9b, 0x1f, 0x00}}},
    {{{    43,    -48,     97}, 0, {     0,      0}, {0x3b, 0xc2, 0x5c, 0x00}}},
    {{{    44,     36,     95}, 0, {     0,      0}, {0x3d, 0x22, 0x69, 0x00}}},
    {{{    44,     80,     46}, 0, {     0,      0}, {0x36, 0x68, 0x2e, 0x00}}},
};

// 0x04014298
static const Vtx mario_medium_poly_butt_dl_vertex_group3[] = {
    {{{    -2,     41,    -95}, 0, {     0,      0}, {0xba, 0x3b, 0xaa, 0x00}}},
    {{{    43,     80,    -45}, 0, {     0,      0}, {0x2c, 0x70, 0xdb, 0x00}}},
    {{{    44,     35,    -96}, 0, {     0,      0}, {0x3c, 0x22, 0x96, 0x00}}},
    {{{     0,     78,    -37}, 0, {     0,      0}, {0xc5, 0x6c, 0xe2, 0x00}}},
    {{{    44,     80,     46}, 0, {     0,      0}, {0x36, 0x68, 0x2e, 0x00}}},
    {{{     0,     78,     37}, 0, {     0,      0}, {0xc7, 0x6f, 0x14, 0x00}}},
    {{{    -2,     41,     95}, 0, {     0,      0}, {0xba, 0x3b, 0x57, 0x00}}},
    {{{   -32,     43,    -22}, 0, {     0,      0}, {0x8b, 0x2d, 0xf0, 0x00}}},
    {{{    43,    -48,     97}, 0, {     0,      0}, {0x3b, 0xc2, 0x5c, 0x00}}},
    {{{    -5,    -45,     97}, 0, {     0,      0}, {0xc7, 0xde, 0x6b, 0x00}}},
    {{{    -6,    -84,     50}, 0, {     0,      0}, {0xd5, 0x90, 0x26, 0x00}}},
    {{{    -6,    -84,    -50}, 0, {     0,      0}, {0xc1, 0x9a, 0xd9, 0x00}}},
    {{{    -5,    -45,    -97}, 0, {     0,      0}, {0xc7, 0xdd, 0x95, 0x00}}},
    {{{    43,    -48,    -97}, 0, {     0,      0}, {0x3b, 0xc1, 0xa4, 0x00}}},
    {{{   -33,    -45,     31}, 0, {     0,      0}, {0x86, 0xe5, 0x15, 0x00}}},
    {{{   -33,    -45,    -31}, 0, {     0,      0}, {0x87, 0xe9, 0xe3, 0x00}}},
};

// 0x04014398
static const Vtx mario_medium_poly_butt_dl_vertex_group4[] = {
    {{{    -5,    -45,     97}, 0, {     0,      0}, {0xc7, 0xde, 0x6b, 0x00}}},
    {{{    43,    -48,     97}, 0, {     0,      0}, {0x3b, 0xc2, 0x5c, 0x00}}},
    {{{    44,     36,     95}, 0, {     0,      0}, {0x3d, 0x22, 0x69, 0x00}}},
    {{{   -33,    -45,     31}, 0, {     0,      0}, {0x86, 0xe5, 0x15, 0x00}}},
    {{{   -32,     43,     22}, 0, {     0,      0}, {0x8c, 0x2c, 0x18, 0x00}}},
    {{{   -32,     43,    -22}, 0, {     0,      0}, {0x8b, 0x2d, 0xf0, 0x00}}},
    {{{    -6,    -84,     50}, 0, {     0,      0}, {0xd5, 0x90, 0x26, 0x00}}},
    {{{    -6,    -84,    -50}, 0, {     0,      0}, {0xc1, 0x9a, 0xd9, 0x00}}},
    {{{    41,    -86,    -45}, 0, {     0,      0}, {0x35, 0x90, 0xe7, 0x00}}},
    {{{    43,     80,    -45}, 0, {     0,      0}, {0x2c, 0x70, 0xdb, 0x00}}},
    {{{     0,     78,    -37}, 0, {     0,      0}, {0xc5, 0x6c, 0xe2, 0x00}}},
    {{{     0,     78,     37}, 0, {     0,      0}, {0xc7, 0x6f, 0x14, 0x00}}},
    {{{    85,     -5,      0}, 0, {     0,      0}, {0x7f, 0xfe, 0x00, 0x00}}},
    {{{    44,     35,    -96}, 0, {     0,      0}, {0x3c, 0x22, 0x96, 0x00}}},
    {{{    43,    -48,    -97}, 0, {     0,      0}, {0x3b, 0xc1, 0xa4, 0x00}}},
    {{{    41,    -86,     45}, 0, {     0,      0}, {0x44, 0x9b, 0x1f, 0x00}}},
};

// 0x04014498
static const Vtx mario_medium_poly_butt_dl_vertex_group5[] = {
    {{{    44,     36,     95}, 0, {     0,      0}, {0x3d, 0x22, 0x69, 0x00}}},
    {{{    85,     -5,      0}, 0, {     0,      0}, {0x7f, 0xfe, 0x00, 0x00}}},
    {{{    44,     80,     46}, 0, {     0,      0}, {0x36, 0x68, 0x2e, 0x00}}},
    {{{    43,     80,    -45}, 0, {     0,      0}, {0x2c, 0x70, 0xdb, 0x00}}},
};

// 0x040144D8 - 0x04014638
const Gfx mario_medium_poly_butt_dl[] = {
    gsSPVertex(mario_medium_poly_butt_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15,  6, 0x0),
    gsSP1Triangle( 3, 10,  7, 0x0),
    gsSPVertex(mario_medium_poly_butt_dl_vertex_group2, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 0,  2,  6, 0x0,  7,  5,  4, 0x0),
    gsSP2Triangles( 7,  8,  6, 0x0,  9, 10,  8, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0,  2,  1,  5, 0x0),
    gsSP1Triangle(14, 15,  0, 0x0),
    gsSPVertex(mario_medium_poly_butt_dl_vertex_group3, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  3,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 10, 14, 0x0,  0, 12, 15, 0x0),
    gsSP2Triangles(14,  9,  6, 0x0,  2, 13, 12, 0x0),
    gsSPVertex(mario_medium_poly_butt_dl_vertex_group4, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 9, 12, 13, 0x0, 13, 12, 14, 0x0),
    gsSP2Triangles(14, 12,  8, 0x0,  8, 12, 15, 0x0),
    gsSP2Triangles(15, 12,  1, 0x0,  1, 12,  2, 0x0),
    gsSPVertex(mario_medium_poly_butt_dl_vertex_group5, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x04014638 - 0x04014668
const Gfx mario_medium_poly_butt[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPLight(&mario_blue_lights_group.l, 1),
    gsSPLight(&mario_blue_lights_group.a, 2),
    gsSPDisplayList(mario_medium_poly_butt_dl),
    gsSPEndDisplayList(),
};

// 0x04014668 - 0x040146E0
const Gfx mario_metal_medium_poly_butt[] = {
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_DECALFADE, G_CC_DECALFADE),
    gsDPLoadTextureBlock(mario_texture_metal, G_IM_FMT_RGBA, G_IM_SIZ_16b, 64, 32, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_WRAP | G_TX_NOMIRROR, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_ON),
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_medium_poly_butt_dl),
    gsSPEndDisplayList(),
};

// 0x040146E0
static const Vtx mario_medium_poly_left_arm_shared_dl_vertex[] = {
    {{{    -6,    -32,    -22}, 0, {     0,      0}, {0x08, 0x84, 0x18, 0x00}}},
    {{{    60,    -27,    -18}, 0, {     0,      0}, {0x3d, 0xc5, 0xa3, 0x00}}},
    {{{    60,    -18,     26}, 0, {     0,      0}, {0x5c, 0xbe, 0x36, 0x00}}},
    {{{    -6,    -32,    -22}, 0, {     0,      0}, {0x07, 0xf5, 0x82, 0x00}}},
    {{{    -7,     21,    -27}, 0, {     0,      0}, {0x07, 0xf5, 0x82, 0x00}}},
    {{{    59,     16,    -22}, 0, {     0,      0}, {0x5b, 0x4f, 0xda, 0x00}}},
    {{{    -7,     20,     18}, 0, {     0,      0}, {0x0c, 0x73, 0x32, 0x00}}},
    {{{    59,     14,     14}, 0, {     0,      0}, {0x38, 0x4b, 0x55, 0x00}}},
    {{{    -6,    -21,     33}, 0, {     0,      0}, {0x0b, 0x2a, 0x77, 0x00}}},
    {{{    -7,     21,    -27}, 0, {     0,      0}, {0x0a, 0x7e, 0x04, 0x00}}},
    {{{    -6,    -21,     33}, 0, {     0,      0}, {0x08, 0x84, 0x18, 0x00}}},
    {{{    -7,     21,    -27}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{    -6,    -32,    -22}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{    -6,    -21,     33}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{    -7,     20,     18}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
};

// 0x040147D0 - 0x04014840
const Gfx mario_medium_poly_left_arm_shared_dl[] = {
    gsSPVertex(mario_medium_poly_left_arm_shared_dl_vertex, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 4,  5,  1, 0x0,  6,  7,  5, 0x0),
    gsSP2Triangles( 8,  2,  7, 0x0,  6,  8,  7, 0x0),
    gsSP2Triangles( 9,  6,  5, 0x0, 10,  0,  2, 0x0),
    gsSP2Triangles( 5,  2,  1, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(13, 14, 11, 0x0,  5,  7,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x04014840 - 0x04014860
const Gfx mario_medium_poly_left_arm[] = {
    gsSPLight(&mario_red_lights_group.l, 1),
    gsSPLight(&mario_red_lights_group.a, 2),
    gsSPDisplayList(mario_medium_poly_left_arm_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04014860
static const Vtx mario_medium_poly_left_forearm_shared_dl_vertex[] = {
    {{{    -9,    -29,    -18}, 0, {     0,      0}, {0x05, 0x84, 0x19, 0x00}}},
    {{{    53,    -26,    -17}, 0, {     0,      0}, {0x59, 0xd0, 0xb5, 0x00}}},
    {{{    53,    -17,     24}, 0, {     0,      0}, {0x3d, 0xab, 0x47, 0x00}}},
    {{{    -9,    -29,    -18}, 0, {     0,      0}, {0x04, 0xf3, 0x82, 0x00}}},
    {{{    -9,     16,    -24}, 0, {     0,      0}, {0x04, 0xf4, 0x82, 0x00}}},
    {{{    53,     13,    -21}, 0, {     0,      0}, {0x3b, 0x64, 0xce, 0x00}}},
    {{{    -9,     15,     15}, 0, {     0,      0}, {0x07, 0x75, 0x30, 0x00}}},
    {{{    53,     13,     12}, 0, {     0,      0}, {0x52, 0x41, 0x47, 0x00}}},
    {{{    -9,    -19,     28}, 0, {     0,      0}, {0x07, 0x2c, 0x76, 0x00}}},
    {{{    -9,     16,    -24}, 0, {     0,      0}, {0x06, 0x7e, 0x02, 0x00}}},
    {{{    -9,    -19,     28}, 0, {     0,      0}, {0x05, 0x84, 0x1a, 0x00}}},
    {{{    -9,     16,    -24}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{    -9,    -29,    -18}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{    -9,    -19,     28}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{    -9,     15,     15}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
};

// 0x04014950 - 0x040149C0
const Gfx mario_medium_poly_left_forearm_shared_dl[] = {
    gsSPVertex(mario_medium_poly_left_forearm_shared_dl_vertex, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 4,  5,  1, 0x0,  6,  7,  5, 0x0),
    gsSP2Triangles( 8,  2,  7, 0x0,  6,  8,  7, 0x0),
    gsSP2Triangles( 9,  6,  5, 0x0, 10,  0,  2, 0x0),
    gsSP2Triangles( 1,  7,  2, 0x0,  1,  5,  7, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0, 13, 14, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x040149C0
static const Vtx mario_medium_poly_left_hand_closed_shared_dl_vertex_group1[] = {
    {{{    31,     10,     40}, 0, {     0,      0}, {0xd8, 0xed, 0x76, 0x00}}},
    {{{    12,      1,     26}, 0, {     0,      0}, {0xf1, 0xf7, 0x7d, 0x00}}},
    {{{    27,    -32,     -3}, 0, {     0,      0}, {0x06, 0x82, 0x0b, 0x00}}},
    {{{    11,      2,    -28}, 0, {     0,      0}, {0xca, 0xf6, 0x8e, 0x00}}},
    {{{    44,    -22,    -46}, 0, {     0,      0}, {0xde, 0xb9, 0x9d, 0x00}}},
    {{{    18,     37,    -31}, 0, {     0,      0}, {0xc7, 0x28, 0x97, 0x00}}},
    {{{    -2,     36,     -2}, 0, {     0,      0}, {0xb0, 0x61, 0x03, 0x00}}},
    {{{    -5,     -5,     34}, 0, {     0,      0}, {0x2f, 0x13, 0x74, 0x00}}},
    {{{    -6,     -5,    -32}, 0, {     0,      0}, {0x1c, 0x0e, 0x86, 0x00}}},
    {{{    81,     32,     40}, 0, {     0,      0}, {0x48, 0x1f, 0x63, 0x00}}},
    {{{    55,     84,      9}, 0, {     0,      0}, {0x14, 0x74, 0x2c, 0x00}}},
    {{{   102,    -23,      6}, 0, {     0,      0}, {0x64, 0xc4, 0x31, 0x00}}},
    {{{    95,     43,    -12}, 0, {     0,      0}, {0x6e, 0x36, 0xe0, 0x00}}},
    {{{    54,     58,    -32}, 0, {     0,      0}, {0x02, 0x4e, 0x9d, 0x00}}},
    {{{    84,    -10,    -41}, 0, {     0,      0}, {0x39, 0xe2, 0x93, 0x00}}},
    {{{    53,    -42,      9}, 0, {     0,      0}, {0x04, 0x83, 0x0f, 0x00}}},
};

// 0x04014AC0
static const Vtx mario_medium_poly_left_hand_closed_shared_dl_vertex_group2[] = {
    {{{    31,     10,     40}, 0, {     0,      0}, {0xd8, 0xed, 0x76, 0x00}}},
    {{{    55,     84,      9}, 0, {     0,      0}, {0x14, 0x74, 0x2c, 0x00}}},
    {{{     7,     62,     10}, 0, {     0,      0}, {0xa7, 0x5a, 0x00, 0x00}}},
    {{{    27,    -32,     -3}, 0, {     0,      0}, {0x06, 0x82, 0x0b, 0x00}}},
    {{{    14,    -51,     -2}, 0, {     0,      0}, {0x37, 0xd5, 0x97, 0x00}}},
    {{{    -6,     -5,    -32}, 0, {     0,      0}, {0x1c, 0x0e, 0x86, 0x00}}},
    {{{   -26,     40,     -1}, 0, {     0,      0}, {0x05, 0x48, 0x98, 0x00}}},
    {{{    -2,     36,     -2}, 0, {     0,      0}, {0xb0, 0x61, 0x03, 0x00}}},
    {{{   -26,     40,     -1}, 0, {     0,      0}, {0x11, 0x50, 0x60, 0x00}}},
    {{{    -5,     -5,     34}, 0, {     0,      0}, {0x2f, 0x13, 0x74, 0x00}}},
    {{{    14,    -51,     -2}, 0, {     0,      0}, {0x49, 0xd5, 0x5e, 0x00}}},
    {{{    18,     37,    -31}, 0, {     0,      0}, {0xc7, 0x28, 0x97, 0x00}}},
    {{{    44,    -22,    -46}, 0, {     0,      0}, {0xde, 0xb9, 0x9d, 0x00}}},
    {{{    53,    -42,      9}, 0, {     0,      0}, {0x04, 0x83, 0x0f, 0x00}}},
    {{{    54,     58,    -32}, 0, {     0,      0}, {0x02, 0x4e, 0x9d, 0x00}}},
};

// 0x04014BB0
static const Vtx mario_medium_poly_left_hand_closed_shared_dl_vertex_group3[] = {
    {{{    54,     58,    -32}, 0, {     0,      0}, {0x02, 0x4e, 0x9d, 0x00}}},
    {{{    95,     43,    -12}, 0, {     0,      0}, {0x6e, 0x36, 0xe0, 0x00}}},
    {{{    84,    -10,    -41}, 0, {     0,      0}, {0x39, 0xe2, 0x93, 0x00}}},
    {{{     7,     62,     10}, 0, {     0,      0}, {0xa7, 0x5a, 0x00, 0x00}}},
    {{{    55,     84,      9}, 0, {     0,      0}, {0x14, 0x74, 0x2c, 0x00}}},
    {{{    53,    -42,      9}, 0, {     0,      0}, {0x04, 0x83, 0x0f, 0x00}}},
    {{{   102,    -23,      6}, 0, {     0,      0}, {0x64, 0xc4, 0x31, 0x00}}},
    {{{    18,     37,    -31}, 0, {     0,      0}, {0xc7, 0x28, 0x97, 0x00}}},
    {{{    44,    -22,    -46}, 0, {     0,      0}, {0xde, 0xb9, 0x9d, 0x00}}},
    {{{    11,      2,    -28}, 0, {     0,      0}, {0xca, 0xf6, 0x8e, 0x00}}},
    {{{    -5,     -5,     34}, 0, {     0,      0}, {0x8c, 0xce, 0x01, 0x00}}},
    {{{    -6,     -5,    -32}, 0, {     0,      0}, {0x8c, 0xce, 0x01, 0x00}}},
    {{{    14,    -51,     -2}, 0, {     0,      0}, {0x8c, 0xce, 0x01, 0x00}}},
    {{{   -26,     40,     -1}, 0, {     0,      0}, {0x8c, 0xce, 0x01, 0x00}}},
};

// 0x04014C90 - 0x04014DC0
const Gfx mario_medium_poly_left_hand_closed_shared_dl[] = {
    gsSPVertex(mario_medium_poly_left_hand_closed_shared_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 5,  3,  6, 0x0,  6,  1,  0, 0x0),
    gsSP2Triangles( 2,  1,  7, 0x0,  7,  1,  6, 0x0),
    gsSP2Triangles( 6,  3,  8, 0x0,  3,  2,  8, 0x0),
    gsSP2Triangles( 0,  9, 10, 0x0, 11,  9,  0, 0x0),
    gsSP2Triangles(10, 12, 13, 0x0, 14, 12, 11, 0x0),
    gsSP2Triangles(11, 12,  9, 0x0,  9, 12, 10, 0x0),
    gsSP1Triangle(15, 11,  0, 0x0),
    gsSPVertex(mario_medium_poly_left_hand_closed_shared_dl_vertex_group2, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9, 10,  3, 0x0,  7,  0,  2, 0x0),
    gsSP2Triangles( 7,  2, 11, 0x0,  3, 12, 13, 0x0),
    gsSP2Triangles( 3, 13,  0, 0x0, 11,  2, 14, 0x0),
    gsSPVertex(mario_medium_poly_left_hand_closed_shared_dl_vertex_group3, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 5,  2,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  2,  8, 0x0,  7,  0,  2, 0x0),
    gsSP2Triangles( 5,  8,  2, 0x0, 10, 11, 12, 0x0),
    gsSP1Triangle(10, 13, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x04014DC0 - 0x04014DE0
const Gfx mario_medium_poly_left_hand_closed[] = {
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_medium_poly_left_hand_closed_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04014DE0
static const Vtx mario_medium_poly_right_arm_shared_dl_vertex[] = {
    {{{    60,    -28,     22}, 0, {     0,      0}, {0x3b, 0xc7, 0x60, 0x00}}},
    {{{    60,    -19,    -19}, 0, {     0,      0}, {0x5a, 0xbc, 0xc7, 0x00}}},
    {{{    60,     14,     24}, 0, {     0,      0}, {0x5b, 0x4f, 0x26, 0x00}}},
    {{{    -7,    -31,     24}, 0, {     0,      0}, {0x04, 0x84, 0xe6, 0x00}}},
    {{{    -7,    -20,    -27}, 0, {     0,      0}, {0x04, 0x84, 0xe6, 0x00}}},
    {{{    -8,     21,    -15}, 0, {     0,      0}, {0x11, 0x73, 0xce, 0x00}}},
    {{{    -8,     22,     27}, 0, {     0,      0}, {0x0e, 0x7e, 0xfd, 0x00}}},
    {{{    60,     13,     -9}, 0, {     0,      0}, {0x3c, 0x46, 0xaa, 0x00}}},
    {{{    -7,    -20,    -27}, 0, {     0,      0}, {0x0e, 0x23, 0x88, 0x00}}},
    {{{    -8,     22,     27}, 0, {     0,      0}, {0x03, 0xfa, 0x7e, 0x00}}},
    {{{    -7,    -31,     24}, 0, {     0,      0}, {0x03, 0xfa, 0x7e, 0x00}}},
    {{{    -7,    -20,    -27}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{    -7,    -31,     24}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{    -8,     22,     27}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{    -8,     21,    -15}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
};

// 0x04014ED0 - 0x04014F40
const Gfx mario_medium_poly_right_arm_shared_dl[] = {
    gsSPVertex(mario_medium_poly_right_arm_shared_dl_vertex, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 2,  5,  6, 0x0,  7,  8,  5, 0x0),
    gsSP2Triangles( 7,  1,  8, 0x0,  2,  7,  5, 0x0),
    gsSP2Triangles( 0,  2,  9, 0x0,  0,  9, 10, 0x0),
    gsSP2Triangles( 1,  0,  3, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(13, 14, 11, 0x0,  1,  7,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x04014F40 - 0x04014F60
const Gfx mario_medium_poly_right_arm[] = {
    gsSPLight(&mario_red_lights_group.l, 1),
    gsSPLight(&mario_red_lights_group.a, 2),
    gsSPDisplayList(mario_medium_poly_right_arm_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04014F60
static const Vtx mario_medium_poly_right_forearm_shared_dl_vertex[] = {
    {{{    -5,    -18,    -24}, 0, {     0,      0}, {0x81, 0xfe, 0x03, 0x00}}},
    {{{    -3,    -29,     21}, 0, {     0,      0}, {0x81, 0xfe, 0x03, 0x00}}},
    {{{    -4,     17,     26}, 0, {     0,      0}, {0x81, 0xfe, 0x03, 0x00}}},
    {{{    52,     13,    -10}, 0, {     0,      0}, {0x51, 0x40, 0xb7, 0x00}}},
    {{{    53,     13,     22}, 0, {     0,      0}, {0x3d, 0x63, 0x31, 0x00}}},
    {{{    53,    -26,     19}, 0, {     0,      0}, {0x5b, 0xd0, 0x49, 0x00}}},
    {{{    52,    -17,    -21}, 0, {     0,      0}, {0x3a, 0xac, 0xb6, 0x00}}},
    {{{    -3,    -29,     21}, 0, {     0,      0}, {0x04, 0x85, 0xe4, 0x00}}},
    {{{    -5,    -18,    -24}, 0, {     0,      0}, {0x03, 0x85, 0xe3, 0x00}}},
    {{{    -5,     17,    -12}, 0, {     0,      0}, {0x09, 0x75, 0xd1, 0x00}}},
    {{{    -4,     17,     26}, 0, {     0,      0}, {0x08, 0x7e, 0x00, 0x00}}},
    {{{    -5,    -18,    -24}, 0, {     0,      0}, {0x06, 0x2a, 0x89, 0x00}}},
    {{{    -4,     17,     26}, 0, {     0,      0}, {0x06, 0xf5, 0x7e, 0x00}}},
    {{{    -3,    -29,     21}, 0, {     0,      0}, {0x06, 0xf4, 0x7e, 0x00}}},
    {{{    -5,     17,    -12}, 0, {     0,      0}, {0x81, 0xfe, 0x03, 0x00}}},
};

// 0x04015050 - 0x040150C0
const Gfx mario_medium_poly_right_forearm_shared_dl[] = {
    gsSPVertex(mario_medium_poly_right_forearm_shared_dl_vertex, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 4,  9, 10, 0x0,  3, 11,  9, 0x0),
    gsSP2Triangles( 3,  6, 11, 0x0,  4,  3,  9, 0x0),
    gsSP2Triangles( 5,  4, 12, 0x0,  5, 12, 13, 0x0),
    gsSP2Triangles( 6,  5,  7, 0x0,  2, 14,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x040150C0
static const Vtx mario_medium_poly_right_hand_closed_dl_vertex_group1[] = {
    {{{    -7,     -4,     36}, 0, {     0,      0}, {0x8c, 0xcd, 0xfd, 0x00}}},
    {{{   -26,     40,      0}, 0, {     0,      0}, {0x8c, 0xcd, 0xfd, 0x00}}},
    {{{    -4,     -7,    -34}, 0, {     0,      0}, {0x8c, 0xcd, 0xfd, 0x00}}},
    {{{    14,    -51,      7}, 0, {     0,      0}, {0x8c, 0xcd, 0xfd, 0x00}}},
    {{{    81,     -8,     49}, 0, {     0,      0}, {0x36, 0xe6, 0x6f, 0x00}}},
    {{{    42,    -20,     54}, 0, {     0,      0}, {0xd9, 0xbb, 0x62, 0x00}}},
    {{{    54,    -42,     -4}, 0, {     0,      0}, {0x04, 0x82, 0xf6, 0x00}}},
    {{{    52,     59,     37}, 0, {     0,      0}, {0xff, 0x55, 0x5e, 0x00}}},
    {{{    16,     38,     35}, 0, {     0,      0}, {0xc1, 0x2d, 0x63, 0x00}}},
    {{{    10,      3,     33}, 0, {     0,      0}, {0xc3, 0xfa, 0x6e, 0x00}}},
    {{{   102,    -23,      0}, 0, {     0,      0}, {0x65, 0xc1, 0xd7, 0x00}}},
    {{{    55,     84,     -8}, 0, {     0,      0}, {0x15, 0x73, 0xd1, 0x00}}},
    {{{     7,     62,    -11}, 0, {     0,      0}, {0xa7, 0x5a, 0xf9, 0x00}}},
    {{{    94,     43,     18}, 0, {     0,      0}, {0x6d, 0x38, 0x21, 0x00}}},
    {{{    32,      8,    -40}, 0, {     0,      0}, {0xda, 0xe8, 0x8a, 0x00}}},
    {{{    26,    -32,      8}, 0, {     0,      0}, {0x08, 0x82, 0xfa, 0x00}}},
};

// 0x040151C0
static const Vtx mario_medium_poly_right_hand_closed_dl_vertex_group2[] = {
    {{{    16,     38,     35}, 0, {     0,      0}, {0xc1, 0x2d, 0x63, 0x00}}},
    {{{     7,     62,    -11}, 0, {     0,      0}, {0xa7, 0x5a, 0xf9, 0x00}}},
    {{{    -3,     37,      3}, 0, {     0,      0}, {0xb0, 0x62, 0xf7, 0x00}}},
    {{{    32,      8,    -40}, 0, {     0,      0}, {0xda, 0xe8, 0x8a, 0x00}}},
    {{{    26,    -32,      8}, 0, {     0,      0}, {0x08, 0x82, 0xfa, 0x00}}},
    {{{    14,    -51,      7}, 0, {     0,      0}, {0x4f, 0xd0, 0xaa, 0x00}}},
    {{{    -4,     -7,    -34}, 0, {     0,      0}, {0x37, 0x10, 0x8f, 0x00}}},
    {{{   -26,     40,      0}, 0, {     0,      0}, {0x15, 0x51, 0xa1, 0x00}}},
    {{{   -26,     40,      0}, 0, {     0,      0}, {0x02, 0x4f, 0x62, 0x00}}},
    {{{    -7,     -4,     36}, 0, {     0,      0}, {0x19, 0x13, 0x7a, 0x00}}},
    {{{    14,    -51,      7}, 0, {     0,      0}, {0x36, 0xd7, 0x6b, 0x00}}},
    {{{    55,     84,     -8}, 0, {     0,      0}, {0x15, 0x73, 0xd1, 0x00}}},
    {{{   102,    -23,      0}, 0, {     0,      0}, {0x65, 0xc1, 0xd7, 0x00}}},
    {{{    54,    -42,     -4}, 0, {     0,      0}, {0x04, 0x82, 0xf6, 0x00}}},
    {{{    94,     43,     18}, 0, {     0,      0}, {0x6d, 0x38, 0x21, 0x00}}},
    {{{    83,     30,    -39}, 0, {     0,      0}, {0x4e, 0x1c, 0xa1, 0x00}}},
};

// 0x040152C0
static const Vtx mario_medium_poly_right_hand_closed_dl_vertex_group3[] = {
    {{{   102,    -23,      0}, 0, {     0,      0}, {0x65, 0xc1, 0xd7, 0x00}}},
    {{{    94,     43,     18}, 0, {     0,      0}, {0x6d, 0x38, 0x21, 0x00}}},
    {{{    81,     -8,     49}, 0, {     0,      0}, {0x36, 0xe6, 0x6f, 0x00}}},
    {{{    52,     59,     37}, 0, {     0,      0}, {0xff, 0x55, 0x5e, 0x00}}},
    {{{    55,     84,     -8}, 0, {     0,      0}, {0x15, 0x73, 0xd1, 0x00}}},
    {{{    32,      8,    -40}, 0, {     0,      0}, {0xda, 0xe8, 0x8a, 0x00}}},
    {{{    83,     30,    -39}, 0, {     0,      0}, {0x4e, 0x1c, 0xa1, 0x00}}},
    {{{    -7,     -4,     36}, 0, {     0,      0}, {0x19, 0x13, 0x7a, 0x00}}},
    {{{    26,    -32,      8}, 0, {     0,      0}, {0x08, 0x82, 0xfa, 0x00}}},
    {{{    10,      3,     33}, 0, {     0,      0}, {0xc3, 0xfa, 0x6e, 0x00}}},
    {{{    -3,     37,      3}, 0, {     0,      0}, {0xb0, 0x62, 0xf7, 0x00}}},
    {{{    13,      0,    -26}, 0, {     0,      0}, {0xf5, 0xf2, 0x83, 0x00}}},
    {{{    -4,     -7,    -34}, 0, {     0,      0}, {0x37, 0x10, 0x8f, 0x00}}},
    {{{    16,     38,     35}, 0, {     0,      0}, {0xc1, 0x2d, 0x63, 0x00}}},
    {{{    42,    -20,     54}, 0, {     0,      0}, {0xd9, 0xbb, 0x62, 0x00}}},
};

// 0x040153B0 - 0x040154E0
const Gfx mario_medium_poly_right_hand_closed_dl[] = {
    gsSPVertex(mario_medium_poly_right_hand_closed_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  8, 0x0),
    gsSP2Triangles( 5,  4,  8, 0x0,  9,  5,  8, 0x0),
    gsSP2Triangles(10,  4,  6, 0x0,  7, 11, 12, 0x0),
    gsSP2Triangles( 4, 13,  7, 0x0,  7, 12,  8, 0x0),
    gsSP2Triangles(14,  6, 15, 0x0,  6,  5, 15, 0x0),
    gsSPVertex(mario_medium_poly_right_hand_closed_dl_vertex_group2, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  7,  2, 0x0),
    gsSP2Triangles( 2,  8,  9, 0x0,  9, 10,  4, 0x0),
    gsSP2Triangles( 1, 11,  3, 0x0,  3, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 15, 0x0, 15, 14, 12, 0x0),
    gsSPVertex(mario_medium_poly_right_hand_closed_dl_vertex_group3, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  4, 0x0),
    gsSP2Triangles( 5,  6,  0, 0x0,  4,  6,  5, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  7,  9, 10, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11,  8, 0x0),
    gsSP2Triangles( 5, 11, 10, 0x0, 10,  9, 13, 0x0),
    gsSP2Triangles(14,  9,  8, 0x0,  8, 11,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x040154E0 - 0x04015500
const Gfx mario_medium_poly_right_hand_closed[] = {
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_medium_poly_right_hand_closed_dl),
    gsSPEndDisplayList(),
};

// 0x04015500 - 0x04015530
const Gfx mario_metal_medium_poly_right_hand_closed[] = {
    gsSPDisplayList(mario_medium_poly_right_hand_closed_dl),
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_OFF),
    gsSPEndDisplayList(),
};

// 0x04015530
static const Vtx mario_medium_poly_left_thigh_dl_vertex[] = {
    {{{    18,    -12,     58}, 0, {     0,      0}, {0x13, 0xdd, 0x78, 0x00}}},
    {{{    22,    -52,      8}, 0, {     0,      0}, {0x1c, 0x85, 0x01, 0x00}}},
    {{{    99,    -35,      8}, 0, {     0,      0}, {0x3e, 0x92, 0x01, 0x00}}},
    {{{    14,     51,     38}, 0, {     0,      0}, {0x08, 0x75, 0x30, 0x00}}},
    {{{    92,     47,     33}, 0, {     0,      0}, {0x36, 0x4b, 0x56, 0x00}}},
    {{{    14,     50,    -23}, 0, {     0,      0}, {0x0a, 0x65, 0xb5, 0x00}}},
    {{{    92,     47,    -16}, 0, {     0,      0}, {0x47, 0x55, 0xc5, 0x00}}},
    {{{    19,    -13,    -42}, 0, {     0,      0}, {0x15, 0xdb, 0x89, 0x00}}},
    {{{    96,     -3,    -31}, 0, {     0,      0}, {0x68, 0xf1, 0xba, 0x00}}},
    {{{    96,     -3,     48}, 0, {     0,      0}, {0x71, 0xf8, 0x38, 0x00}}},
    {{{    19,    -13,    -42}, 0, {     0,      0}, {0x82, 0xf7, 0xff, 0x00}}},
    {{{    22,    -52,      8}, 0, {     0,      0}, {0x82, 0xf7, 0xff, 0x00}}},
    {{{    18,    -12,     58}, 0, {     0,      0}, {0x82, 0xf7, 0xff, 0x00}}},
    {{{    14,     51,     38}, 0, {     0,      0}, {0x82, 0xf7, 0xff, 0x00}}},
    {{{    14,     50,    -23}, 0, {     0,      0}, {0x82, 0xf7, 0xff, 0x00}}},
};

// 0x04015620 - 0x040156B0
const Gfx mario_medium_poly_left_thigh_dl[] = {
    gsSPVertex(mario_medium_poly_left_thigh_dl_vertex, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 5,  3,  6, 0x0,  7,  5,  6, 0x0),
    gsSP2Triangles( 1,  7,  2, 0x0,  7,  8,  2, 0x0),
    gsSP2Triangles( 6,  8,  7, 0x0,  3,  4,  6, 0x0),
    gsSP2Triangles( 0,  9,  4, 0x0,  2,  9,  0, 0x0),
    gsSP2Triangles( 9,  2,  8, 0x0, 10, 11, 12, 0x0),
    gsSP2Triangles(12, 13, 10, 0x0, 13, 14, 10, 0x0),
    gsSP2Triangles( 8,  6,  9, 0x0,  6,  4,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x040156B0 - 0x040156E0
const Gfx mario_medium_poly_left_thigh[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPLight(&mario_blue_lights_group.l, 1),
    gsSPLight(&mario_blue_lights_group.a, 2),
    gsSPDisplayList(mario_medium_poly_left_thigh_dl),
    gsSPEndDisplayList(),
};

// 0x040156E0 - 0x04015758
const Gfx mario_metal_medium_poly_left_thigh[] = {
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_DECALFADE, G_CC_DECALFADE),
    gsDPLoadTextureBlock(mario_texture_metal, G_IM_FMT_RGBA, G_IM_SIZ_16b, 64, 32, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_WRAP | G_TX_NOMIRROR, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_ON),
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_medium_poly_left_thigh_dl),
    gsSPEndDisplayList(),
};

// 0x04015758
static const Vtx mario_medium_poly_left_leg_shared_dl_vertex[] = {
    {{{     5,    -35,      9}, 0, {     0,      0}, {0xca, 0x8e, 0x00, 0x00}}},
    {{{    65,    -44,     10}, 0, {     0,      0}, {0xee, 0x83, 0x01, 0x00}}},
    {{{    66,     -9,     54}, 0, {     0,      0}, {0xf2, 0xdc, 0x78, 0x00}}},
    {{{     9,     46,     33}, 0, {     0,      0}, {0xd5, 0x61, 0x45, 0x00}}},
    {{{    69,     47,     37}, 0, {     0,      0}, {0xf9, 0x66, 0x4b, 0x00}}},
    {{{    70,     47,    -18}, 0, {     0,      0}, {0xfb, 0x74, 0xcf, 0x00}}},
    {{{    10,     46,    -16}, 0, {     0,      0}, {0xaf, 0x40, 0xb7, 0x00}}},
    {{{    67,     -9,    -36}, 0, {     0,      0}, {0xf4, 0xda, 0x88, 0x00}}},
    {{{     7,     -4,    -31}, 0, {     0,      0}, {0x9b, 0xee, 0xb6, 0x00}}},
    {{{     7,     -4,     49}, 0, {     0,      0}, {0x8f, 0xf5, 0x37, 0x00}}},
    {{{    66,     -9,     54}, 0, {     0,      0}, {0x7e, 0xfa, 0x01, 0x00}}},
    {{{    65,    -44,     10}, 0, {     0,      0}, {0x7e, 0xfa, 0x01, 0x00}}},
    {{{    67,     -9,    -36}, 0, {     0,      0}, {0x7e, 0xfa, 0x01, 0x00}}},
    {{{    70,     47,    -18}, 0, {     0,      0}, {0x7e, 0xfa, 0x01, 0x00}}},
    {{{    69,     47,     37}, 0, {     0,      0}, {0x7e, 0xfa, 0x01, 0x00}}},
};

// 0x04015848 - 0x040158D8
const Gfx mario_medium_poly_left_leg_shared_dl[] = {
    gsSPVertex(mario_medium_poly_left_leg_shared_dl_vertex, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  4, 0x0),
    gsSP2Triangles( 3,  4,  5, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 0,  7,  1, 0x0,  0,  8,  7, 0x0),
    gsSP2Triangles( 7,  8,  6, 0x0,  5,  6,  3, 0x0),
    gsSP2Triangles( 3,  9,  2, 0x0,  2,  9,  0, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0,  8,  0,  9, 0x0),
    gsSP2Triangles( 9,  6,  8, 0x0,  9,  3,  6, 0x0),
    gsSP2Triangles(12, 13, 10, 0x0, 13, 14, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x040158D8
static const Vtx mario_medium_poly_left_foot_shared_dl_vertex_group1[] = {
    {{{   108,     10,     36}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{   108,      9,    -20}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{    75,     21,    -41}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{    76,     22,     61}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{   -14,     55,    -27}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{   -13,     55,     49}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{   -41,     65,      9}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{    41,    -18,     54}, 0, {     0,      0}, {0x04, 0xb4, 0x65, 0x00}}},
    {{{    76,     22,     61}, 0, {     0,      0}, {0x12, 0xdd, 0x78, 0x00}}},
    {{{   -13,     55,     49}, 0, {     0,      0}, {0xce, 0x07, 0x74, 0x00}}},
    {{{    42,    -11,    -33}, 0, {     0,      0}, {0xff, 0xb8, 0x98, 0x00}}},
    {{{   -37,     12,    -16}, 0, {     0,      0}, {0xa1, 0xcf, 0xbd, 0x00}}},
    {{{   -14,     55,    -27}, 0, {     0,      0}, {0xcc, 0x02, 0x8d, 0x00}}},
    {{{   -36,     13,     39}, 0, {     0,      0}, {0xa0, 0xd0, 0x42, 0x00}}},
    {{{    75,     21,    -41}, 0, {     0,      0}, {0x09, 0xda, 0x88, 0x00}}},
    {{{   108,      9,    -20}, 0, {     0,      0}, {0x44, 0xad, 0xbe, 0x00}}},
};

// 0x040159D8
static const Vtx mario_medium_poly_left_foot_shared_dl_vertex_group2[] = {
    {{{    78,    -30,      7}, 0, {     0,      0}, {0x34, 0x8d, 0xfb, 0x00}}},
    {{{   108,      9,    -20}, 0, {     0,      0}, {0x44, 0xad, 0xbe, 0x00}}},
    {{{   108,     10,     36}, 0, {     0,      0}, {0x4c, 0xaf, 0x3c, 0x00}}},
    {{{    42,    -11,    -33}, 0, {     0,      0}, {0xff, 0xb8, 0x98, 0x00}}},
    {{{    76,     22,     61}, 0, {     0,      0}, {0x12, 0xdd, 0x78, 0x00}}},
    {{{    41,    -18,     54}, 0, {     0,      0}, {0x04, 0xb4, 0x65, 0x00}}},
    {{{   -36,     13,     39}, 0, {     0,      0}, {0xa0, 0xd0, 0x42, 0x00}}},
    {{{   -13,     55,     49}, 0, {     0,      0}, {0xce, 0x07, 0x74, 0x00}}},
    {{{   -41,     65,      9}, 0, {     0,      0}, {0x83, 0x15, 0x00, 0x00}}},
    {{{   -14,     55,    -27}, 0, {     0,      0}, {0xcc, 0x02, 0x8d, 0x00}}},
    {{{   -37,     12,    -16}, 0, {     0,      0}, {0xa1, 0xcf, 0xbd, 0x00}}},
    {{{    25,    -34,     11}, 0, {     0,      0}, {0xdd, 0x87, 0xfb, 0x00}}},
};

// 0x04015A98 - 0x04015B60
const Gfx mario_medium_poly_left_foot_shared_dl[] = {
    gsSPVertex(mario_medium_poly_left_foot_shared_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 3,  2,  4, 0x0,  5,  3,  4, 0x0),
    gsSP2Triangles( 6,  5,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13,  7,  9, 0x0),
    gsSP2Triangles(10, 12, 14, 0x0, 10, 14, 15, 0x0),
    gsSPVertex(mario_medium_poly_left_foot_shared_dl_vertex_group2, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  5,  0,  2, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles(11,  3,  0, 0x0, 10,  3, 11, 0x0),
    gsSP2Triangles( 0,  5, 11, 0x0, 11,  5,  6, 0x0),
    gsSP2Triangles(11,  6, 10, 0x0,  6,  8, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x04015B60 - 0x04015B80
const Gfx mario_medium_poly_left_foot[] = {
    gsSPLight(&mario_brown1_lights_group.l, 1),
    gsSPLight(&mario_brown1_lights_group.a, 2),
    gsSPDisplayList(mario_medium_poly_left_foot_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04015B80
static const Vtx mario_medium_poly_right_thigh_shared_dl_vertex[] = {
    {{{    18,    -11,    -58}, 0, {     0,      0}, {0x82, 0xf5, 0x02, 0x00}}},
    {{{    23,    -52,     -9}, 0, {     0,      0}, {0x82, 0xf5, 0x02, 0x00}}},
    {{{    20,    -14,     41}, 0, {     0,      0}, {0x82, 0xf5, 0x02, 0x00}}},
    {{{    97,     -3,     30}, 0, {     0,      0}, {0x69, 0xf1, 0x45, 0x00}}},
    {{{    99,    -33,    -10}, 0, {     0,      0}, {0x40, 0x93, 0xfc, 0x00}}},
    {{{    95,     -1,    -49}, 0, {     0,      0}, {0x71, 0xfb, 0xc7, 0x00}}},
    {{{    18,    -11,    -58}, 0, {     0,      0}, {0x12, 0xe0, 0x87, 0x00}}},
    {{{    91,     49,    -33}, 0, {     0,      0}, {0x34, 0x4e, 0xab, 0x00}}},
    {{{    92,     48,     16}, 0, {     0,      0}, {0x47, 0x55, 0x3d, 0x00}}},
    {{{    13,     52,    -37}, 0, {     0,      0}, {0x06, 0x76, 0xd3, 0x00}}},
    {{{    20,    -14,     41}, 0, {     0,      0}, {0x16, 0xd9, 0x76, 0x00}}},
    {{{    23,    -52,     -9}, 0, {     0,      0}, {0x1d, 0x85, 0xfd, 0x00}}},
    {{{    14,     50,     24}, 0, {     0,      0}, {0x0a, 0x63, 0x4d, 0x00}}},
    {{{    13,     52,    -37}, 0, {     0,      0}, {0x82, 0xf5, 0x02, 0x00}}},
    {{{    14,     50,     24}, 0, {     0,      0}, {0x82, 0xf5, 0x02, 0x00}}},
};

// 0x04015C70 - 0x04015D00
const Gfx mario_medium_poly_right_thigh_shared_dl[] = {
    gsSPVertex(mario_medium_poly_right_thigh_shared_dl_vertex, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  5,  4, 0x0,  7,  5,  6, 0x0),
    gsSP2Triangles( 8,  7,  9, 0x0, 10,  3,  8, 0x0),
    gsSP2Triangles( 4,  3, 10, 0x0,  4, 10, 11, 0x0),
    gsSP2Triangles( 8, 12, 10, 0x0,  8,  9, 12, 0x0),
    gsSP2Triangles( 7,  6,  9, 0x0,  4, 11,  6, 0x0),
    gsSP2Triangles( 5,  8,  3, 0x0,  5,  7,  8, 0x0),
    gsSP2Triangles( 2, 13,  0, 0x0,  2, 14, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x04015D00 - 0x04015D20
const Gfx mario_medium_poly_right_thigh[] = {
    gsSPLight(&mario_blue_lights_group.l, 1),
    gsSPLight(&mario_blue_lights_group.a, 2),
    gsSPDisplayList(mario_medium_poly_right_thigh_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04015D20
static const Vtx mario_medium_poly_right_leg_shared_dl_vertex[] = {
    {{{     6,     -1,    -50}, 0, {     0,      0}, {0x8e, 0xf7, 0xca, 0x00}}},
    {{{     5,    -34,    -11}, 0, {     0,      0}, {0xc9, 0x8e, 0xfe, 0x00}}},
    {{{     7,     -4,     30}, 0, {     0,      0}, {0x9b, 0xed, 0x4a, 0x00}}},
    {{{    67,     -9,     34}, 0, {     0,      0}, {0x7e, 0xf9, 0xfe, 0x00}}},
    {{{    64,    -43,    -12}, 0, {     0,      0}, {0x7e, 0xf9, 0xfe, 0x00}}},
    {{{    66,     -7,    -56}, 0, {     0,      0}, {0x7e, 0xf9, 0xfe, 0x00}}},
    {{{    66,     -7,    -56}, 0, {     0,      0}, {0xf1, 0xde, 0x87, 0x00}}},
    {{{    10,     48,    -33}, 0, {     0,      0}, {0xd6, 0x63, 0xbe, 0x00}}},
    {{{    10,     46,     16}, 0, {     0,      0}, {0xb1, 0x3f, 0x4b, 0x00}}},
    {{{    71,     47,     18}, 0, {     0,      0}, {0xfd, 0x73, 0x33, 0x00}}},
    {{{    67,     -9,     34}, 0, {     0,      0}, {0xf4, 0xd8, 0x77, 0x00}}},
    {{{    64,    -43,    -12}, 0, {     0,      0}, {0xec, 0x83, 0xfd, 0x00}}},
    {{{    70,     49,    -37}, 0, {     0,      0}, {0xfa, 0x67, 0xb8, 0x00}}},
    {{{    71,     47,     18}, 0, {     0,      0}, {0x7e, 0xf9, 0xfe, 0x00}}},
    {{{    70,     49,    -37}, 0, {     0,      0}, {0x7e, 0xf9, 0xfe, 0x00}}},
};

// 0x04015E10 - 0x04015EA0
const Gfx mario_medium_poly_right_leg_shared_dl[] = {
    gsSPVertex(mario_medium_poly_right_leg_shared_dl_vertex, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 1,  0,  6, 0x0,  6,  0,  7, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  8,  2, 10, 0x0),
    gsSP2Triangles(10,  2,  1, 0x0, 11, 10,  1, 0x0),
    gsSP2Triangles(10,  9,  8, 0x0,  9, 12,  7, 0x0),
    gsSP2Triangles(12,  6,  7, 0x0,  6, 11,  1, 0x0),
    gsSP2Triangles( 5, 13,  3, 0x0,  5, 14, 13, 0x0),
    gsSP2Triangles( 2,  8,  0, 0x0,  8,  7,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x04015EA0
static const Vtx mario_medium_poly_right_foot_dl_vertex_group1[] = {
    {{{   -38,     12,     14}, 0, {     0,      0}, {0xa1, 0xcf, 0x43, 0x00}}},
    {{{   -43,     65,    -12}, 0, {     0,      0}, {0x83, 0x13, 0xff, 0x00}}},
    {{{   -36,     12,    -41}, 0, {     0,      0}, {0xa1, 0xce, 0xbe, 0x00}}},
    {{{    25,    -34,    -11}, 0, {     0,      0}, {0xdf, 0x86, 0x07, 0x00}}},
    {{{    41,    -18,    -55}, 0, {     0,      0}, {0x07, 0xb3, 0x9c, 0x00}}},
    {{{    79,    -29,     -7}, 0, {     0,      0}, {0x36, 0x8e, 0x08, 0x00}}},
    {{{    42,    -10,     32}, 0, {     0,      0}, {0xff, 0xba, 0x69, 0x00}}},
    {{{   -15,     55,     24}, 0, {     0,      0}, {0xcb, 0x04, 0x73, 0x00}}},
    {{{   -14,     54,    -51}, 0, {     0,      0}, {0xcf, 0x04, 0x8c, 0x00}}},
    {{{   108,     11,    -36}, 0, {     0,      0}, {0x4e, 0xb0, 0xc6, 0x00}}},
    {{{    76,     23,    -62}, 0, {     0,      0}, {0x14, 0xdb, 0x89, 0x00}}},
    {{{   107,     12,     20}, 0, {     0,      0}, {0x45, 0xaf, 0x44, 0x00}}},
    {{{    74,     24,     40}, 0, {     0,      0}, {0x08, 0xdc, 0x79, 0x00}}},
    {{{   -15,     55,     24}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{   -14,     54,    -51}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{   -43,     65,    -12}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
};

// 0x04015FA0
static const Vtx mario_medium_poly_right_foot_dl_vertex_group2[] = {
    {{{   -15,     55,     24}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{    76,     23,    -62}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{   -14,     54,    -51}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{    74,     24,     40}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{   108,     11,    -36}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{   107,     12,     20}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
};

// 0x04016000 - 0x040160C8
const Gfx mario_medium_poly_right_foot_dl[] = {
    gsSPVertex(mario_medium_poly_right_foot_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 2,  4,  3, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  0, 0x0,  5,  6,  3, 0x0),
    gsSP2Triangles( 0,  7,  1, 0x0,  1,  8,  2, 0x0),
    gsSP2Triangles( 9,  5,  4, 0x0,  4, 10,  9, 0x0),
    gsSP2Triangles( 6,  5, 11, 0x0,  9, 11,  5, 0x0),
    gsSP2Triangles(11, 12,  6, 0x0, 12,  7,  6, 0x0),
    gsSP2Triangles( 8,  4,  2, 0x0,  7,  0,  6, 0x0),
    gsSP2Triangles( 8, 10,  4, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(mario_medium_poly_right_foot_dl_vertex_group2, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 3,  4,  1, 0x0,  3,  5,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x040160C8 - 0x04016108
const Gfx mario_medium_poly_right_foot[] = {
    gsSPLight(&mario_brown1_lights_group.l, 1),
    gsSPLight(&mario_brown1_lights_group.a, 2),
    gsSPDisplayList(mario_medium_poly_right_foot_dl),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsSPEndDisplayList(),
};

// 0x04016108 - 0x04016148
const Gfx mario_metal_medium_poly_right_foot[] = {
    gsSPDisplayList(mario_medium_poly_right_foot_dl),
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsSPEndDisplayList(),
};

// 0x04016148
static const Vtx mario_seg4_vertex_04016148[] = {
    {{{    75,     24,    -60}, 0, {   -74,   -930}, {0x60, 0x27, 0xb8, 0xff}}},
    {{{    36,     47,    -76}, 0, {  -174,    168}, {0x2e, 0x36, 0x98, 0xff}}},
    {{{    44,     78,    -35}, 0, {   988,     92}, {0x45, 0x65, 0xe2, 0xff}}},
    {{{   -26,     49,    -87}, 0, {  -538,   1590}, {0xc4, 0x29, 0x99, 0xff}}},
    {{{     1,     87,    -53}, 0, {   638,   1164}, {0x0e, 0x74, 0xd1, 0xff}}},
    {{{     1,     87,     54}, 0, {  2522,    702}, {0x11, 0x74, 0x2f, 0xff}}},
    {{{     1,     87,     54}, 0, {   272,   1122}, {0x11, 0x74, 0x2f, 0xff}}},
    {{{    44,     78,     37}, 0, {   -92,    108}, {0x47, 0x5f, 0x2a, 0xff}}},
    {{{    44,     78,    -35}, 0, { -1398,   -128}, {0x45, 0x65, 0xe2, 0xff}}},
    {{{   -26,     49,     88}, 0, {  1514,   1490}, {0xc4, 0x29, 0x67, 0xff}}},
    {{{    36,     47,     77}, 0, {  1110,    130}, {0x2f, 0x37, 0x68, 0xff}}},
    {{{    75,     24,     60}, 0, {   968,   -926}, {0x62, 0x27, 0x46, 0xff}}},
};

// 0x04016208
static const Vtx mario_seg4_vertex_04016208[] = {
    {{{    82,     37,     28}, 0, {     0,      0}, {0x71, 0x36, 0x0e, 0xff}}},
    {{{    82,     37,    -28}, 0, {     0,      0}, {0x71, 0x35, 0xea, 0xff}}},
    {{{    44,     78,    -35}, 0, {     0,      0}, {0x45, 0x65, 0xe2, 0xff}}},
    {{{    44,     78,     37}, 0, {     0,      0}, {0x47, 0x5f, 0x2a, 0xff}}},
    {{{    95,    -39,    -25}, 0, {     0,      0}, {0x79, 0xe6, 0xe8, 0xff}}},
    {{{    95,    -39,     25}, 0, {     0,      0}, {0x75, 0xdb, 0x1d, 0xff}}},
    {{{    54,    -68,      0}, 0, {     0,      0}, {0x2d, 0x8a, 0x00, 0xff}}},
    {{{    56,     -4,     79}, 0, {     0,      0}, {0x39, 0x04, 0x71, 0xff}}},
    {{{    75,     24,     60}, 0, {     0,      0}, {0x62, 0x27, 0x46, 0xff}}},
    {{{    36,     47,     77}, 0, {     0,      0}, {0x2f, 0x37, 0x68, 0xff}}},
    {{{     9,      0,     94}, 0, {     0,      0}, {0x0e, 0x03, 0x7e, 0xff}}},
    {{{    22,    -41,     87}, 0, {     0,      0}, {0x1f, 0xc9, 0x6d, 0xff}}},
    {{{    83,    -29,     58}, 0, {     0,      0}, {0x54, 0xc8, 0x4c, 0xff}}},
    {{{    83,    -29,    -58}, 0, {     0,      0}, {0x52, 0xc9, 0xb2, 0xff}}},
    {{{    40,    -66,    -47}, 0, {     0,      0}, {0x1d, 0x91, 0xcb, 0xff}}},
    {{{    22,    -41,    -86}, 0, {     0,      0}, {0x1e, 0xca, 0x92, 0xff}}},
};

// 0x04016308
static const Vtx mario_seg4_vertex_04016308[] = {
    {{{    36,     47,    -76}, 0, {     0,      0}, {0x2e, 0x36, 0x98, 0xff}}},
    {{{    75,     24,    -60}, 0, {     0,      0}, {0x60, 0x27, 0xb8, 0xff}}},
    {{{    56,     -4,    -77}, 0, {     0,      0}, {0x38, 0x04, 0x8f, 0xff}}},
    {{{     9,      0,    -93}, 0, {     0,      0}, {0x0e, 0x03, 0x82, 0xff}}},
    {{{    83,    -29,    -58}, 0, {     0,      0}, {0x52, 0xc9, 0xb2, 0xff}}},
    {{{    22,    -41,    -86}, 0, {     0,      0}, {0x1e, 0xca, 0x92, 0xff}}},
    {{{    22,    -41,     87}, 0, {     0,      0}, {0x1f, 0xc9, 0x6d, 0xff}}},
    {{{    40,    -66,     48}, 0, {     0,      0}, {0x1d, 0x91, 0x35, 0xff}}},
    {{{    83,    -29,     58}, 0, {     0,      0}, {0x54, 0xc8, 0x4c, 0xff}}},
};

// 0x04016398
static const Vtx mario_seg4_vertex_04016398[] = {
    {{{    83,    -29,    -58}, 0, {     0,      0}, {0x52, 0xc9, 0xb2, 0xff}}},
    {{{    95,    -39,    -25}, 0, {     0,      0}, {0x79, 0xe6, 0xe8, 0xff}}},
    {{{    54,    -68,      0}, 0, {     0,      0}, {0x2d, 0x8a, 0x00, 0xff}}},
    {{{    75,     24,    -60}, 0, {     0,      0}, {0x60, 0x27, 0xb8, 0xff}}},
    {{{    82,     37,    -28}, 0, {     0,      0}, {0x71, 0x35, 0xea, 0xff}}},
    {{{    40,    -66,    -47}, 0, {     0,      0}, {0x1d, 0x91, 0xcb, 0xff}}},
    {{{    44,     78,    -35}, 0, {     0,      0}, {0x45, 0x65, 0xe2, 0xff}}},
    {{{    75,     24,     60}, 0, {     0,      0}, {0x62, 0x27, 0x46, 0xff}}},
    {{{    82,     37,     28}, 0, {     0,      0}, {0x71, 0x36, 0x0e, 0xff}}},
    {{{    44,     78,     37}, 0, {     0,      0}, {0x47, 0x5f, 0x2a, 0xff}}},
    {{{    83,    -29,     58}, 0, {     0,      0}, {0x54, 0xc8, 0x4c, 0xff}}},
    {{{    95,    -39,     25}, 0, {     0,      0}, {0x75, 0xdb, 0x1d, 0xff}}},
    {{{    40,    -66,     48}, 0, {     0,      0}, {0x1d, 0x91, 0x35, 0xff}}},
    {{{     1,     87,    -53}, 0, {     0,      0}, {0x0e, 0x74, 0xd1, 0xff}}},
    {{{   -30,     88,     42}, 0, {     0,      0}, {0xd3, 0x72, 0x1f, 0xff}}},
    {{{     1,     87,     54}, 0, {     0,      0}, {0x11, 0x74, 0x2f, 0xff}}},
};

// 0x04016498
static const Vtx mario_seg4_vertex_04016498[] = {
    {{{     1,     87,    -53}, 0, {     0,      0}, {0x0e, 0x74, 0xd1, 0xff}}},
    {{{   -30,     88,    -41}, 0, {     0,      0}, {0xc7, 0x6a, 0xda, 0xff}}},
    {{{   -30,     88,     42}, 0, {     0,      0}, {0xd3, 0x72, 0x1f, 0xff}}},
    {{{   -26,     49,    -87}, 0, {     0,      0}, {0xc4, 0x29, 0x99, 0xff}}},
    {{{   -71,     56,      0}, 0, {     0,      0}, {0x89, 0x2b, 0x00, 0xff}}},
    {{{   -26,     49,     88}, 0, {     0,      0}, {0xc4, 0x29, 0x67, 0xff}}},
    {{{     1,     87,     54}, 0, {     0,      0}, {0x11, 0x74, 0x2f, 0xff}}},
    {{{   -59,    -44,      0}, 0, {     0,      0}, {0x88, 0xd8, 0x00, 0xff}}},
    {{{   -26,    -72,    -38}, 0, {     0,      0}, {0xda, 0x8b, 0xe5, 0xff}}},
    {{{   -26,    -72,     39}, 0, {     0,      0}, {0xda, 0x8b, 0x1b, 0xff}}},
    {{{   -26,    -41,     85}, 0, {     0,      0}, {0xcc, 0xc2, 0x61, 0xff}}},
    {{{   -26,    -41,    -84}, 0, {     0,      0}, {0xcc, 0xc2, 0x9f, 0xff}}},
    {{{    40,    -66,     48}, 0, {     0,      0}, {0x1d, 0x91, 0x35, 0xff}}},
    {{{    22,    -41,     87}, 0, {     0,      0}, {0x1f, 0xc9, 0x6d, 0xff}}},
    {{{     9,      0,     94}, 0, {     0,      0}, {0x0e, 0x03, 0x7e, 0xff}}},
    {{{    54,    -68,      0}, 0, {     0,      0}, {0x2d, 0x8a, 0x00, 0xff}}},
};

// 0x04016598
static const Vtx mario_seg4_vertex_04016598[] = {
    {{{   -26,     49,     88}, 0, {     0,      0}, {0xc4, 0x29, 0x67, 0xff}}},
    {{{     9,      0,     94}, 0, {     0,      0}, {0x0e, 0x03, 0x7e, 0xff}}},
    {{{    36,     47,     77}, 0, {     0,      0}, {0x2f, 0x37, 0x68, 0xff}}},
    {{{     9,      0,    -93}, 0, {     0,      0}, {0x0e, 0x03, 0x82, 0xff}}},
    {{{   -26,     49,    -87}, 0, {     0,      0}, {0xc4, 0x29, 0x99, 0xff}}},
    {{{    36,     47,    -76}, 0, {     0,      0}, {0x2e, 0x36, 0x98, 0xff}}},
    {{{   -26,    -41,    -84}, 0, {     0,      0}, {0xcc, 0xc2, 0x9f, 0xff}}},
    {{{    22,    -41,    -86}, 0, {     0,      0}, {0x1e, 0xca, 0x92, 0xff}}},
    {{{    40,    -66,    -47}, 0, {     0,      0}, {0x1d, 0x91, 0xcb, 0xff}}},
    {{{   -26,    -72,    -38}, 0, {     0,      0}, {0xda, 0x8b, 0xe5, 0xff}}},
    {{{    54,    -68,      0}, 0, {     0,      0}, {0x2d, 0x8a, 0x00, 0xff}}},
    {{{   -26,    -72,     39}, 0, {     0,      0}, {0xda, 0x8b, 0x1b, 0xff}}},
    {{{    40,    -66,     48}, 0, {     0,      0}, {0x1d, 0x91, 0x35, 0xff}}},
};

// 0x04016668 - 0x040166B8
const Gfx mario_medium_poly_yellow_button_dl[] = {
    gsSPVertex(mario_seg4_vertex_04016148, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  4,  5,  2, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10,  6, 0x0),
    gsSP2Triangles( 6, 10,  7, 0x0,  7, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x040166B8 - 0x04016800
const Gfx mario_medium_poly_pants_overalls_shared_dl[] = {
    gsSPVertex(mario_seg4_vertex_04016398, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 3,  4,  1, 0x0,  2,  5,  0, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10, 11, 0x0,  8,  7, 11, 0x0),
    gsSP2Triangles(11, 10,  2, 0x0, 10, 12,  2, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(mario_seg4_vertex_04016498, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  2,  1, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 5,  2,  4, 0x0,  6,  2,  5, 0x0),
    gsSP2Triangles( 4,  3,  7, 0x0,  8,  9,  7, 0x0),
    gsSP2Triangles( 5,  4,  7, 0x0,  9, 10,  7, 0x0),
    gsSP2Triangles(11,  8,  7, 0x0,  3, 11,  7, 0x0),
    gsSP2Triangles( 7, 10,  5, 0x0, 10,  9, 12, 0x0),
    gsSP2Triangles(10, 13, 14, 0x0, 12, 13, 10, 0x0),
    gsSP2Triangles( 5, 10, 14, 0x0,  9,  8, 15, 0x0),
    gsSPVertex(mario_seg4_vertex_04016598, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  4,  3, 0x0,  6,  3,  7, 0x0),
    gsSP2Triangles( 8,  9,  6, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 9,  8, 10, 0x0, 11, 10, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x04016800 - 0x040168A0
const Gfx mario_medium_poly_tshirt_shared_dl[] = {
    gsSPVertex(mario_seg4_vertex_04016208, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 1,  0,  4, 0x0,  0,  5,  4, 0x0),
    gsSP2Triangles( 6,  4,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0,  7, 10, 11, 0x0),
    gsSP2Triangles( 7, 12,  8, 0x0, 11, 12,  7, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(mario_seg4_vertex_04016308, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  2,  4,  5, 0x0),
    gsSP2Triangles( 5,  3,  2, 0x0,  6,  7,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x040168A0 - 0x040168C8
const Gfx mario_medium_poly_torso_dl[] = {
    gsSPDisplayList(mario_medium_poly_pants_overalls_shared_dl),
    gsSPLight(&mario_red_lights_group.l, 1),
    gsSPLight(&mario_red_lights_group.a, 2),
    gsSPDisplayList(mario_medium_poly_tshirt_shared_dl),
    gsSPEndDisplayList(),
};

// 0x040168C8 - 0x04016948
const Gfx mario_medium_poly_torso[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_yellow_button),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_medium_poly_yellow_button_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_medium_poly_torso_dl),
    gsSPEndDisplayList(),
};

// 0x04016948 - 0x04016968
const Gfx mario_metal_medium_poly_torso[] = {
    gsSPDisplayList(mario_medium_poly_yellow_button_dl),
    gsSPDisplayList(mario_medium_poly_pants_overalls_shared_dl),
    gsSPDisplayList(mario_medium_poly_tshirt_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04016968
static const Vtx mario_low_poly_butt_dl_vertex[] = {
    {{{   -37,    -37,      0}, 0, {     0,      0}, {0x82, 0xf5, 0x00, 0x00}}},
    {{{    -2,     52,     92}, 0, {     0,      0}, {0xb1, 0x3c, 0x4e, 0x00}}},
    {{{   -35,     55,      0}, 0, {     0,      0}, {0x97, 0x45, 0x00, 0x00}}},
    {{{    -7,    -86,     85}, 0, {     0,      0}, {0xbf, 0xc4, 0x5a, 0x00}}},
    {{{    49,     46,     96}, 0, {     0,      0}, {0x4b, 0x1d, 0x61, 0x00}}},
    {{{    -7,    -86,    -85}, 0, {     0,      0}, {0xcf, 0xaa, 0xb2, 0x00}}},
    {{{    -2,     52,    -92}, 0, {     0,      0}, {0xb0, 0x3b, 0xb2, 0x00}}},
    {{{    48,     44,    -98}, 0, {     0,      0}, {0x29, 0x22, 0x8e, 0x00}}},
    {{{    46,    -71,    -89}, 0, {     0,      0}, {0x73, 0xd9, 0xdd, 0x00}}},
    {{{    46,    -71,     89}, 0, {     0,      0}, {0x44, 0xa1, 0x2f, 0x00}}},
    {{{    49,     96,      0}, 0, {     0,      0}, {0x2b, 0x77, 0x00, 0x00}}},
};

// 0x04016A18 - 0x04016AB8
const Gfx mario_low_poly_butt_dl[] = {
    gsSPVertex(mario_low_poly_butt_dl_vertex, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  0,  2,  6, 0x0),
    gsSP2Triangles( 6,  5,  0, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 7,  8,  5, 0x0,  3,  9,  4, 0x0),
    gsSP2Triangles( 8,  7, 10, 0x0,  4,  9,  8, 0x0),
    gsSP2Triangles( 5,  9,  3, 0x0,  3,  0,  5, 0x0),
    gsSP2Triangles( 5,  8,  9, 0x0, 10,  7,  6, 0x0),
    gsSP2Triangles(10,  4,  8, 0x0,  2,  1, 10, 0x0),
    gsSP2Triangles( 6,  2, 10, 0x0,  1,  4, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x04016AB8 - 0x04016AE8
const Gfx mario_low_poly_butt[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPLight(&mario_blue_lights_group.l, 1),
    gsSPLight(&mario_blue_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_butt_dl),
    gsSPEndDisplayList(),
};

// 0x04016AE8 - 0x04016B60
const Gfx mario_metal_low_poly_butt[] = {
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_DECALFADE, G_CC_DECALFADE),
    gsDPLoadTextureBlock(mario_texture_metal, G_IM_FMT_RGBA, G_IM_SIZ_16b, 64, 32, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_WRAP | G_TX_NOMIRROR, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_ON),
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_butt_dl),
    gsSPEndDisplayList(),
};

// 0x04016B60
static const Vtx mario_low_poly_left_arm_shared_dl_vertex[] = {
    {{{    -6,     28,     10}, 0, {     0,      0}, {0x09, 0x65, 0xb5, 0x00}}},
    {{{    72,     21,      8}, 0, {     0,      0}, {0x44, 0x4d, 0x48, 0x00}}},
    {{{    71,    -11,    -34}, 0, {     0,      0}, {0x3f, 0x57, 0xbe, 0x00}}},
    {{{    -8,    -30,     31}, 0, {     0,      0}, {0x09, 0x26, 0x78, 0x00}}},
    {{{    71,    -30,     23}, 0, {     0,      0}, {0x5e, 0x15, 0x51, 0x00}}},
    {{{    -6,     28,     10}, 0, {     0,      0}, {0x07, 0x29, 0x77, 0x00}}},
    {{{    -8,     -7,    -41}, 0, {     0,      0}, {0x0b, 0x67, 0xb8, 0x00}}},
    {{{    -8,    -30,     31}, 0, {     0,      0}, {0xa4, 0xad, 0xe5, 0x00}}},
    {{{    -6,     28,     10}, 0, {     0,      0}, {0x81, 0x02, 0x00, 0x00}}},
    {{{    -8,     -7,    -41}, 0, {     0,      0}, {0xa4, 0xad, 0xe5, 0x00}}},
    {{{    71,    -11,    -34}, 0, {     0,      0}, {0xfd, 0x88, 0xd9, 0x00}}},
    {{{    71,    -30,     23}, 0, {     0,      0}, {0xfd, 0x88, 0xd9, 0x00}}},
};

// 0x04016C20 - 0x04016C70
const Gfx mario_low_poly_left_arm_shared_dl[] = {
    gsSPVertex(mario_low_poly_left_arm_shared_dl_vertex, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 5,  3,  1, 0x0,  6,  0,  2, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  2,  1,  4, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0,  7, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x04016C70 - 0x04016C90
const Gfx mario_low_poly_left_arm[] = {
    gsSPLight(&mario_red_lights_group.l, 1),
    gsSPLight(&mario_red_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_left_arm_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04016C90
static const Vtx mario_low_poly_left_forearm_shared_dl_vertex[] = {
    {{{    -5,     20,      6}, 0, {     0,      0}, {0x05, 0x62, 0xb0, 0x00}}},
    {{{    67,     16,      5}, 0, {     0,      0}, {0x43, 0x4b, 0x4c, 0x00}}},
    {{{    67,    -14,    -31}, 0, {     0,      0}, {0x3c, 0x55, 0xba, 0x00}}},
    {{{    -5,    -30,     21}, 0, {     0,      0}, {0x05, 0x20, 0x7a, 0x00}}},
    {{{    67,    -30,     17}, 0, {     0,      0}, {0x5c, 0x14, 0x54, 0x00}}},
    {{{    -5,     20,      6}, 0, {     0,      0}, {0x04, 0x22, 0x7a, 0x00}}},
    {{{    -5,    -12,    -35}, 0, {     0,      0}, {0x07, 0x63, 0xb2, 0x00}}},
    {{{    -5,    -30,     21}, 0, {     0,      0}, {0xa5, 0xad, 0xe5, 0x00}}},
    {{{    -5,     20,      6}, 0, {     0,      0}, {0x81, 0x01, 0x00, 0x00}}},
    {{{    -5,    -12,    -35}, 0, {     0,      0}, {0xa5, 0xad, 0xe5, 0x00}}},
    {{{    67,    -30,     17}, 0, {     0,      0}, {0xfe, 0x88, 0xd9, 0x00}}},
    {{{    67,    -14,    -31}, 0, {     0,      0}, {0xfe, 0x88, 0xd9, 0x00}}},
};

// 0x04016D50 - 0x04016DA0
const Gfx mario_low_poly_left_forearm_shared_dl[] = {
    gsSPVertex(mario_low_poly_left_forearm_shared_dl_vertex, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 5,  3,  1, 0x0,  6,  0,  2, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  2,  1,  4, 0x0),
    gsSP2Triangles(10,  7,  9, 0x0, 10,  9, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x04016DA0
static const Vtx mario_low_poly_left_hand_closed_shared_dl_vertex[] = {
    {{{    -6,     34,    -25}, 0, {     0,      0}, {0xa0, 0x13, 0xb0, 0x00}}},
    {{{    -9,     39,     24}, 0, {     0,      0}, {0xb3, 0x43, 0x4a, 0x00}}},
    {{{    91,     80,    -34}, 0, {     0,      0}, {0x13, 0x7c, 0xee, 0x00}}},
    {{{    90,     35,     33}, 0, {     0,      0}, {0x41, 0x14, 0x6b, 0x00}}},
    {{{    20,    -34,     21}, 0, {     0,      0}, {0xd4, 0x9a, 0x3c, 0x00}}},
    {{{   122,      4,    -45}, 0, {     0,      0}, {0x63, 0xbb, 0x25, 0x00}}},
    {{{   122,      4,    -45}, 0, {     0,      0}, {0xf2, 0x15, 0x84, 0x00}}},
    {{{    20,    -31,    -44}, 0, {     0,      0}, {0xcb, 0xae, 0xb1, 0x00}}},
};

// 0x04016E20 - 0x04016E80
const Gfx mario_low_poly_left_hand_closed_shared_dl[] = {
    gsSPVertex(mario_low_poly_left_hand_closed_shared_dl_vertex, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  3, 0x0,  0,  6,  7, 0x0),
    gsSP2Triangles( 0,  2,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 3,  1,  4, 0x0,  2,  3,  5, 0x0),
    gsSP2Triangles( 0,  7,  4, 0x0,  4,  1,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x04016E80 - 0x04016EA0
const Gfx mario_low_poly_left_hand_closed[] = {
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_left_hand_closed_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04016EA0
static const Vtx mario_low_poly_right_arm_shared_dl_vertex[] = {
    {{{    65,     30,     23}, 0, {     0,      0}, {0x5e, 0xeb, 0x51, 0x00}}},
    {{{    66,    -21,      8}, 0, {     0,      0}, {0x45, 0xb3, 0x49, 0x00}}},
    {{{    65,     11,    -34}, 0, {     0,      0}, {0x40, 0xa9, 0xbf, 0x00}}},
    {{{   -10,      7,    -41}, 0, {     0,      0}, {0xa4, 0x52, 0xe5, 0x00}}},
    {{{    -9,    -28,     10}, 0, {     0,      0}, {0x81, 0xfe, 0x00, 0x00}}},
    {{{   -10,     30,     30}, 0, {     0,      0}, {0xa4, 0x52, 0xe5, 0x00}}},
    {{{    -9,    -28,     10}, 0, {     0,      0}, {0x09, 0x9a, 0xb6, 0x00}}},
    {{{   -10,      7,    -41}, 0, {     0,      0}, {0x0c, 0x99, 0xb8, 0x00}}},
    {{{   -10,     30,     30}, 0, {     0,      0}, {0x0a, 0xdb, 0x78, 0x00}}},
    {{{    -9,    -28,     10}, 0, {     0,      0}, {0x07, 0xd8, 0x78, 0x00}}},
    {{{    65,     30,     23}, 0, {     0,      0}, {0xfc, 0x78, 0xd9, 0x00}}},
    {{{    65,     11,    -34}, 0, {     0,      0}, {0xfc, 0x78, 0xd9, 0x00}}},
};

// 0x04016F60 - 0x04016FB0
const Gfx mario_low_poly_right_arm_shared_dl[] = {
    gsSPVertex(mario_low_poly_right_arm_shared_dl_vertex, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 2,  6,  7, 0x0,  1,  8,  9, 0x0),
    gsSP2Triangles( 1,  0,  8, 0x0,  2,  1,  6, 0x0),
    gsSP2Triangles( 5, 10, 11, 0x0,  5, 11,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x04016FB0 - 0x04016FD0
const Gfx mario_low_poly_right_arm[] = {
    gsSPLight(&mario_red_lights_group.l, 1),
    gsSPLight(&mario_red_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_right_arm_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04016FD0
static const Vtx mario_low_poly_right_forearm_shared_dl_vertex[] = {
    {{{   -13,     12,    -35}, 0, {     0,      0}, {0xa5, 0x53, 0xe5, 0x00}}},
    {{{   -13,    -20,      6}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{   -13,     30,     21}, 0, {     0,      0}, {0xa5, 0x53, 0xe5, 0x00}}},
    {{{    54,     14,    -31}, 0, {     0,      0}, {0x3d, 0xaa, 0xba, 0x00}}},
    {{{   -13,    -20,      6}, 0, {     0,      0}, {0x05, 0x9e, 0xb1, 0x00}}},
    {{{   -13,     12,    -35}, 0, {     0,      0}, {0x07, 0x9c, 0xb3, 0x00}}},
    {{{    55,    -16,      5}, 0, {     0,      0}, {0x43, 0xb5, 0x4c, 0x00}}},
    {{{   -13,     30,     21}, 0, {     0,      0}, {0x05, 0xe0, 0x7a, 0x00}}},
    {{{   -13,    -20,      6}, 0, {     0,      0}, {0x04, 0xde, 0x7a, 0x00}}},
    {{{    54,     30,     17}, 0, {     0,      0}, {0x5c, 0xed, 0x54, 0x00}}},
    {{{    54,     30,     17}, 0, {     0,      0}, {0xfe, 0x78, 0xd8, 0x00}}},
    {{{    54,     14,    -31}, 0, {     0,      0}, {0xfe, 0x78, 0xd8, 0x00}}},
};

// 0x04017090 - 0x040170E0
const Gfx mario_low_poly_right_forearm_shared_dl[] = {
    gsSPVertex(mario_low_poly_right_forearm_shared_dl_vertex, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  2, 10, 11, 0x0),
    gsSP2Triangles( 2, 11,  0, 0x0,  9,  6,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x040170E0
static const Vtx mario_low_poly_right_hand_closed_dl_vertex[] = {
    {{{    85,    -38,    -32}, 0, {     0,      0}, {0x3d, 0xe3, 0x96, 0x00}}},
    {{{   -15,    -35,    -22}, 0, {     0,      0}, {0x99, 0xdc, 0xc1, 0x00}}},
    {{{    17,     37,    -22}, 0, {     0,      0}, {0xfe, 0x69, 0xb9, 0x00}}},
    {{{   116,    -10,     36}, 0, {     0,      0}, {0x66, 0x47, 0x16, 0x00}}},
    {{{    82,    -85,     44}, 0, {     0,      0}, {0x13, 0x8a, 0xd8, 0x00}}},
    {{{   -15,    -35,     43}, 0, {     0,      0}, {0x9d, 0xec, 0x4c, 0x00}}},
    {{{    82,    -85,     44}, 0, {     0,      0}, {0x02, 0x15, 0x7d, 0x00}}},
    {{{    14,     30,     26}, 0, {     0,      0}, {0xca, 0x50, 0x51, 0x00}}},
};

// 0x04017160 - 0x040171C0
const Gfx mario_low_poly_right_hand_closed_dl[] = {
    gsSPVertex(mario_low_poly_right_hand_closed_dl_vertex, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  0,  3, 0x0,  4,  5,  1, 0x0),
    gsSP2Triangles( 6,  3,  7, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  3,  2,  7, 0x0),
    gsSP2Triangles( 1,  5,  7, 0x0,  7,  2,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x040171C0 - 0x040171E0
const Gfx mario_low_poly_right_hand_closed[] = {
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_right_hand_closed_dl),
    gsSPEndDisplayList(),
};

// 0x040171E0 - 0x04017210
const Gfx mario_metal_low_poly_right_hand_closed[] = {
    gsSPDisplayList(mario_low_poly_right_hand_closed_dl),
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_OFF),
    gsSPEndDisplayList(),
};

// 0x04017210
static const Vtx mario_low_poly_left_thigh_dl_vertex[] = {
    {{{    10,     51,     45}, 0, {     0,      0}, {0x08, 0x73, 0x34, 0x00}}},
    {{{    17,    -44,     58}, 0, {     0,      0}, {0x0a, 0x11, 0x7d, 0x00}}},
    {{{    94,     48,     38}, 0, {     0,      0}, {0x38, 0x40, 0x5d, 0x00}}},
    {{{    11,     51,    -27}, 0, {     0,      0}, {0x0b, 0x5e, 0xad, 0x00}}},
    {{{    95,     48,    -19}, 0, {     0,      0}, {0x4a, 0x4f, 0xbf, 0x00}}},
    {{{    18,    -45,    -38}, 0, {     0,      0}, {0x0c, 0x0f, 0x83, 0x00}}},
    {{{   101,    -28,    -28}, 0, {     0,      0}, {0x5b, 0xca, 0xbc, 0x00}}},
    {{{   100,    -28,     48}, 0, {     0,      0}, {0x63, 0xbd, 0x29, 0x00}}},
    {{{    17,    -44,     58}, 0, {     0,      0}, {0x82, 0xf7, 0xff, 0x00}}},
    {{{    10,     51,     45}, 0, {     0,      0}, {0x82, 0xf7, 0xff, 0x00}}},
    {{{    18,    -45,    -38}, 0, {     0,      0}, {0x82, 0xf7, 0xff, 0x00}}},
    {{{    11,     51,    -27}, 0, {     0,      0}, {0x82, 0xf7, 0xff, 0x00}}},
    {{{    18,    -45,    -38}, 0, {     0,      0}, {0x18, 0x84, 0x01, 0x00}}},
    {{{    17,    -44,     58}, 0, {     0,      0}, {0x18, 0x84, 0x01, 0x00}}},
};

// 0x040172F0 - 0x04017360
const Gfx mario_low_poly_left_thigh_dl[] = {
    gsSPVertex(mario_low_poly_left_thigh_dl_vertex, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 5,  3,  4, 0x0,  4,  6,  5, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  1,  7,  2, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  9, 11, 10, 0x0),
    gsSP2Triangles( 6,  4,  7, 0x0,  4,  2,  7, 0x0),
    gsSP2Triangles(12,  7, 13, 0x0, 12,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x04017360 - 0x04017390
const Gfx mario_low_poly_left_thigh[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPLight(&mario_blue_lights_group.l, 1),
    gsSPLight(&mario_blue_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_left_thigh_dl),
    gsSPEndDisplayList(),
};

// 0x04017390 - 0x04017408
const Gfx mario_metal_low_poly_left_thigh[] = {
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_DECALFADE, G_CC_DECALFADE),
    gsDPLoadTextureBlock(mario_texture_metal, G_IM_FMT_RGBA, G_IM_SIZ_16b, 64, 32, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_WRAP | G_TX_NOMIRROR, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_ON),
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_left_thigh_dl),
    gsSPEndDisplayList(),
};

// 0x04017408
static const Vtx mario_low_poly_left_leg_shared_dl_vertex[] = {
    {{{     7,     47,     38}, 0, {     0,      0}, {0xcf, 0x59, 0x4b, 0x00}}},
    {{{    70,    -39,     53}, 0, {     0,      0}, {0xf8, 0x10, 0x7d, 0x00}}},
    {{{    75,     52,     42}, 0, {     0,      0}, {0xf4, 0x5e, 0x53, 0x00}}},
    {{{    75,     52,    -23}, 0, {     0,      0}, {0xf5, 0x72, 0xca, 0x00}}},
    {{{     7,     47,    -20}, 0, {     0,      0}, {0xab, 0x34, 0xb2, 0x00}}},
    {{{    71,    -39,    -34}, 0, {     0,      0}, {0xfa, 0x0f, 0x83, 0x00}}},
    {{{     3,    -29,    -29}, 0, {     0,      0}, {0xbb, 0xa4, 0xcd, 0x00}}},
    {{{     2,    -29,     48}, 0, {     0,      0}, {0x92, 0xdb, 0x31, 0x00}}},
    {{{    71,    -39,    -34}, 0, {     0,      0}, {0x7e, 0xfa, 0x01, 0x00}}},
    {{{    75,     52,    -23}, 0, {     0,      0}, {0x7e, 0xfa, 0x01, 0x00}}},
    {{{    70,    -39,     53}, 0, {     0,      0}, {0x7e, 0xfa, 0x01, 0x00}}},
    {{{    75,     52,     42}, 0, {     0,      0}, {0x7e, 0xfa, 0x01, 0x00}}},
    {{{    71,    -39,    -34}, 0, {     0,      0}, {0xed, 0x83, 0x00, 0x00}}},
    {{{    70,    -39,     53}, 0, {     0,      0}, {0xed, 0x83, 0x00, 0x00}}},
};

// 0x040174E8 - 0x04017558
const Gfx mario_low_poly_left_leg_shared_dl[] = {
    gsSPVertex(mario_low_poly_left_leg_shared_dl_vertex, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  3,  5, 0x0,  5,  6,  4, 0x0),
    gsSP2Triangles( 3,  4,  0, 0x0,  0,  7,  1, 0x0),
    gsSP2Triangles( 7,  4,  6, 0x0,  7,  0,  4, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  9, 11, 10, 0x0),
    gsSP2Triangles( 6, 12, 13, 0x0, 13,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x04017558
static const Vtx mario_low_poly_left_foot_shared_dl_vertex[] = {
    {{{   110,     11,     56}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{   109,     10,    -33}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{   -50,     69,    -36}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{   -49,     70,     63}, 0, {     0,      0}, {0x2b, 0x77, 0xff, 0x00}}},
    {{{    74,    -39,     51}, 0, {     0,      0}, {0x13, 0xb0, 0x60, 0x00}}},
    {{{   110,     11,     56}, 0, {     0,      0}, {0x5b, 0xbb, 0x36, 0x00}}},
    {{{   -49,     70,     63}, 0, {     0,      0}, {0x00, 0xf4, 0x7e, 0x00}}},
    {{{    73,    -40,    -25}, 0, {     0,      0}, {0x18, 0x96, 0xc0, 0x00}}},
    {{{   -56,     20,    -30}, 0, {     0,      0}, {0xa4, 0xc5, 0xc2, 0x00}}},
    {{{   -50,     69,    -36}, 0, {     0,      0}, {0xfe, 0xf0, 0x83, 0x00}}},
    {{{   -54,     20,     58}, 0, {     0,      0}, {0x99, 0xc2, 0x26, 0x00}}},
    {{{   109,     10,    -33}, 0, {     0,      0}, {0x44, 0xc2, 0xaa, 0x00}}},
    {{{   -50,     69,    -36}, 0, {     0,      0}, {0x82, 0x0e, 0x01, 0x00}}},
    {{{   -49,     70,     63}, 0, {     0,      0}, {0x82, 0x0e, 0x01, 0x00}}},
};

// 0x04017638 - 0x040176A8
const Gfx mario_low_poly_left_foot_shared_dl[] = {
    gsSPVertex(mario_low_poly_left_foot_shared_dl_vertex, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  4,  6, 0x0,  7,  9, 11, 0x0),
    gsSP2Triangles(12, 10, 13, 0x0,  7, 10,  8, 0x0),
    gsSP2Triangles( 7, 11,  5, 0x0,  5,  4,  7, 0x0),
    gsSP2Triangles( 7,  4, 10, 0x0, 12,  8, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x040176A8 - 0x040176C8
const Gfx mario_low_poly_left_foot[] = {
    gsSPLight(&mario_brown1_lights_group.l, 1),
    gsSPLight(&mario_brown1_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_left_foot_shared_dl),
    gsSPEndDisplayList(),
};

// 0x040176C8
static const Vtx mario_low_poly_right_thigh_shared_dl_vertex[] = {
    {{{   102,    -25,    -48}, 0, {     0,      0}, {0x64, 0xbf, 0xd5, 0x00}}},
    {{{   103,    -27,     25}, 0, {     0,      0}, {0x5c, 0xc9, 0x42, 0x00}}},
    {{{    25,    -45,     35}, 0, {     0,      0}, {0x1b, 0x85, 0xfd, 0x00}}},
    {{{    24,    -42,    -56}, 0, {     0,      0}, {0x1b, 0x85, 0xfd, 0x00}}},
    {{{    95,     50,    -36}, 0, {     0,      0}, {0x37, 0x42, 0xa3, 0x00}}},
    {{{    96,     49,     18}, 0, {     0,      0}, {0x4a, 0x4e, 0x41, 0x00}}},
    {{{    25,    -45,     35}, 0, {     0,      0}, {0x82, 0xf5, 0x02, 0x00}}},
    {{{    17,     51,     27}, 0, {     0,      0}, {0x82, 0xf5, 0x02, 0x00}}},
    {{{    15,     53,    -42}, 0, {     0,      0}, {0x82, 0xf5, 0x02, 0x00}}},
    {{{    24,    -42,    -56}, 0, {     0,      0}, {0x82, 0xf5, 0x02, 0x00}}},
    {{{    24,    -42,    -56}, 0, {     0,      0}, {0x09, 0x13, 0x83, 0x00}}},
    {{{    15,     53,    -42}, 0, {     0,      0}, {0x07, 0x74, 0xce, 0x00}}},
    {{{    25,    -45,     35}, 0, {     0,      0}, {0x0d, 0x0c, 0x7d, 0x00}}},
    {{{    17,     51,     27}, 0, {     0,      0}, {0x0b, 0x5c, 0x55, 0x00}}},
};

// 0x040177A8 - 0x04017818
const Gfx mario_low_poly_right_thigh_shared_dl[] = {
    gsSPVertex(mario_low_poly_right_thigh_shared_dl_vertex, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 0,  4,  5, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP2Triangles( 4,  0, 10, 0x0,  5,  4, 11, 0x0),
    gsSP2Triangles(12,  1,  5, 0x0,  5, 13, 12, 0x0),
    gsSP2Triangles( 5, 11, 13, 0x0,  4, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x04017818 - 0x04017838
const Gfx mario_low_poly_right_thigh[] = {
    gsSPLight(&mario_blue_lights_group.l, 1),
    gsSPLight(&mario_blue_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_right_thigh_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04017838
static const Vtx mario_low_poly_right_leg_shared_dl_vertex[] = {
    {{{     2,    -29,     27}, 0, {     0,      0}, {0xba, 0xa3, 0x31, 0x00}}},
    {{{     1,    -26,    -50}, 0, {     0,      0}, {0x91, 0xdd, 0xcf, 0x00}}},
    {{{    68,    -37,    -56}, 0, {     0,      0}, {0xec, 0x83, 0xfd, 0x00}}},
    {{{    70,    -40,     30}, 0, {     0,      0}, {0xec, 0x83, 0xfd, 0x00}}},
    {{{    68,    -37,    -56}, 0, {     0,      0}, {0x7e, 0xf9, 0xfe, 0x00}}},
    {{{    74,     54,    -43}, 0, {     0,      0}, {0x7e, 0xf9, 0xfe, 0x00}}},
    {{{    75,     52,     21}, 0, {     0,      0}, {0x7e, 0xf9, 0xfe, 0x00}}},
    {{{    70,    -40,     30}, 0, {     0,      0}, {0x7e, 0xf9, 0xfe, 0x00}}},
    {{{     6,     47,     19}, 0, {     0,      0}, {0xad, 0x33, 0x50, 0x00}}},
    {{{     6,     49,    -38}, 0, {     0,      0}, {0xcf, 0x5b, 0xb7, 0x00}}},
    {{{    68,    -37,    -56}, 0, {     0,      0}, {0xf8, 0x13, 0x83, 0x00}}},
    {{{    75,     52,     21}, 0, {     0,      0}, {0xf7, 0x71, 0x38, 0x00}}},
    {{{    70,    -40,     30}, 0, {     0,      0}, {0xfb, 0x0c, 0x7e, 0x00}}},
    {{{    74,     54,    -43}, 0, {     0,      0}, {0xf4, 0x60, 0xaf, 0x00}}},
};

// 0x04017918 - 0x04017988
const Gfx mario_low_poly_right_leg_shared_dl[] = {
    gsSPVertex(mario_low_poly_right_leg_shared_dl_vertex, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9,  1, 0x0,  0,  8,  1, 0x0),
    gsSP2Triangles(10,  1,  9, 0x0,  9,  8, 11, 0x0),
    gsSP2Triangles( 8,  0, 12, 0x0, 12, 11,  8, 0x0),
    gsSP2Triangles(11, 13,  9, 0x0, 13, 10,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x04017988
static const Vtx mario_low_poly_right_foot_dl_vertex[] = {
    {{{   -55,     19,    -59}, 0, {     0,      0}, {0x9a, 0xc0, 0xda, 0x00}}},
    {{{   -57,     20,     28}, 0, {     0,      0}, {0xa4, 0xc5, 0x3e, 0x00}}},
    {{{   -52,     69,     34}, 0, {     0,      0}, {0x82, 0x0c, 0xfd, 0x00}}},
    {{{    73,    -38,    -50}, 0, {     0,      0}, {0x15, 0xae, 0xa2, 0x00}}},
    {{{    71,    -37,     25}, 0, {     0,      0}, {0x19, 0x98, 0x42, 0x00}}},
    {{{   108,     12,    -55}, 0, {     0,      0}, {0x5c, 0xbc, 0xcc, 0x00}}},
    {{{   106,     13,     33}, 0, {     0,      0}, {0x44, 0xc5, 0x58, 0x00}}},
    {{{   -50,     68,    -65}, 0, {     0,      0}, {0x82, 0x0c, 0xfd, 0x00}}},
    {{{   -52,     69,     34}, 0, {     0,      0}, {0xfd, 0xf2, 0x7e, 0x00}}},
    {{{   -50,     68,    -65}, 0, {     0,      0}, {0x02, 0xf2, 0x82, 0x00}}},
    {{{   -52,     69,     34}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{   108,     12,    -55}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{   -50,     68,    -65}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{   106,     13,     33}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
};

// 0x04017A68 - 0x04017AD8
const Gfx mario_low_poly_right_foot_dl[] = {
    gsSPVertex(mario_low_poly_right_foot_dl_vertex, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 4,  3,  5, 0x0,  5,  6,  4, 0x0),
    gsSP2Triangles( 1,  0,  4, 0x0,  7,  0,  2, 0x0),
    gsSP2Triangles( 6,  8,  4, 0x0,  9,  3,  0, 0x0),
    gsSP2Triangles( 8,  1,  4, 0x0,  9,  5,  3, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x04017AD8 - 0x04017B18
const Gfx mario_low_poly_right_foot[] = {
    gsSPLight(&mario_brown1_lights_group.l, 1),
    gsSPLight(&mario_brown1_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_right_foot_dl),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsSPEndDisplayList(),
};

// 0x04017B18 - 0x04017B58
const Gfx mario_metal_low_poly_right_foot[] = {
    gsSPDisplayList(mario_low_poly_right_foot_dl),
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsSPEndDisplayList(),
};

// 0x04017B58
static const Vtx mario_seg4_vertex_04017B58[] = {
    {{{   -28,     41,    -88}, 0, {  -396,   1646}, {0xb7, 0x31, 0xa5, 0xff}}},
    {{{     0,     98,      0}, 0, {  1968,    722}, {0xf0, 0x7d, 0x00, 0xff}}},
    {{{    35,     64,    -82}, 0, {  -390,    132}, {0x29, 0x4b, 0xa3, 0xff}}},
    {{{    39,     90,      0}, 0, {  1698,   -280}, {0x4a, 0x67, 0x00, 0xff}}},
    {{{   -28,     41,     89}, 0, {  1610,   1512}, {0xd1, 0x37, 0x67, 0xff}}},
    {{{    35,     64,     83}, 0, {  1070,    -68}, {0x29, 0x4b, 0x5d, 0xff}}},
    {{{     0,     98,      0}, 0, { -1064,    968}, {0xf0, 0x7d, 0x00, 0xff}}},
    {{{    39,     90,      0}, 0, { -1146,   -124}, {0x4a, 0x67, 0x00, 0xff}}},
};

// 0x04017BD8
static const Vtx mario_seg4_vertex_04017BD8[] = {
    {{{   -28,    -71,     81}, 0, {     0,      0}, {0x9a, 0xbd, 0x20, 0xff}}},
    {{{   -28,     41,    -88}, 0, {     0,      0}, {0xb7, 0x31, 0xa5, 0xff}}},
    {{{   -28,    -71,    -80}, 0, {     0,      0}, {0xb7, 0xb5, 0xba, 0xff}}},
    {{{    39,    -79,    -73}, 0, {     0,      0}, {0x27, 0xb3, 0xa4, 0xff}}},
    {{{   -28,     41,     89}, 0, {     0,      0}, {0xd1, 0x37, 0x67, 0xff}}},
    {{{   -28,     89,      0}, 0, {     0,      0}, {0x97, 0x46, 0x00, 0xff}}},
    {{{    39,    -79,     74}, 0, {     0,      0}, {0x37, 0xba, 0x59, 0xff}}},
    {{{    35,     64,     83}, 0, {     0,      0}, {0x29, 0x4b, 0x5d, 0xff}}},
    {{{     0,     98,      0}, 0, {     0,      0}, {0xf0, 0x7d, 0x00, 0xff}}},
    {{{    35,     64,    -82}, 0, {     0,      0}, {0x29, 0x4b, 0xa3, 0xff}}},
    {{{    89,    -45,    -42}, 0, {     0,      0}, {0x70, 0xd0, 0xdf, 0xff}}},
    {{{    89,    -45,     43}, 0, {     0,      0}, {0x75, 0xe4, 0x28, 0xff}}},
    {{{    74,     26,    -59}, 0, {     0,      0}, {0x61, 0x25, 0xb9, 0xff}}},
    {{{    39,     90,      0}, 0, {     0,      0}, {0x4a, 0x67, 0x00, 0xff}}},
    {{{    74,     26,     60}, 0, {     0,      0}, {0x61, 0x25, 0x47, 0xff}}},
};

// 0x04017CC8
static const Vtx mario_seg4_vertex_04017CC8[] = {
    {{{    74,     26,     60}, 0, {     0,      0}, {0x61, 0x25, 0x47, 0xff}}},
    {{{    87,     42,      0}, 0, {     0,      0}, {0x76, 0x2d, 0x00, 0xff}}},
    {{{    39,     90,      0}, 0, {     0,      0}, {0x4a, 0x67, 0x00, 0xff}}},
    {{{    39,    -79,    -73}, 0, {     0,      0}, {0x27, 0xb3, 0xa4, 0xff}}},
    {{{    35,     64,    -82}, 0, {     0,      0}, {0x29, 0x4b, 0xa3, 0xff}}},
    {{{    74,     26,    -59}, 0, {     0,      0}, {0x61, 0x25, 0xb9, 0xff}}},
    {{{    89,    -45,    -42}, 0, {     0,      0}, {0x70, 0xd0, 0xdf, 0xff}}},
    {{{    39,    -79,     74}, 0, {     0,      0}, {0x37, 0xba, 0x59, 0xff}}},
    {{{    89,    -45,     43}, 0, {     0,      0}, {0x75, 0xe4, 0x28, 0xff}}},
    {{{    35,     64,     83}, 0, {     0,      0}, {0x29, 0x4b, 0x5d, 0xff}}},
};

// 0x04017D68 - 0x04017D98
const Gfx mario_low_poly_yellow_button_dl[] = {
    gsSPVertex(mario_seg4_vertex_04017B58, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x04017D98 - 0x04017E20
const Gfx mario_low_poly_pants_overalls_shared_dl[] = {
    gsSPVertex(mario_seg4_vertex_04017BD8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 0,  4,  5, 0x0,  3,  6,  0, 0x0),
    gsSP2Triangles( 4,  0,  6, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 7,  4,  6, 0x0,  5,  4,  8, 0x0),
    gsSP2Triangles( 8,  1,  5, 0x0,  3,  1,  9, 0x0),
    gsSP2Triangles( 3,  2,  1, 0x0,  6,  3, 10, 0x0),
    gsSP2Triangles(10, 11,  6, 0x0, 12,  9, 13, 0x0),
    gsSP1Triangle(13,  7, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x04017E20 - 0x04017E78
const Gfx mario_low_poly_tshirt_shared_dl[] = {
    gsSPVertex(mario_seg4_vertex_04017CC8, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 6,  5,  1, 0x0,  7,  8,  0, 0x0),
    gsSP2Triangles( 7,  0,  9, 0x0,  8,  6,  1, 0x0),
    gsSP1Triangle( 1,  0,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x04017E78 - 0x04017EA0
const Gfx mario_low_poly_torso_dl[] = {
    gsSPDisplayList(mario_low_poly_pants_overalls_shared_dl),
    gsSPLight(&mario_red_lights_group.l, 1),
    gsSPLight(&mario_red_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_tshirt_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04017EA0 - 0x04017F20
const Gfx mario_low_poly_torso[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_yellow_button),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_yellow_button_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_torso_dl),
    gsSPEndDisplayList(),
};

// 0x04017F20 - 0x04017F40
const Gfx mario_metal_low_poly_torso[] = {
    gsSPDisplayList(mario_low_poly_yellow_button_dl),
    gsSPDisplayList(mario_low_poly_pants_overalls_shared_dl),
    gsSPDisplayList(mario_low_poly_tshirt_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04017F40
static const Vtx mario_low_poly_mario_m_logo_dl_vertex[] = {
    {{{   272,     48,     53}, 0, {   904,      0}, {0x42, 0x51, 0x47, 0xff}}},
    {{{   174,     79,     -9}, 0, {   452,    816}, {0x51, 0x61, 0xfa, 0xff}}},
    {{{   146,     27,     92}, 0, {  1238,    962}, {0x03, 0x3a, 0x70, 0xff}}},
    {{{   272,     48,    -52}, 0, {    -2,      0}, {0x62, 0x27, 0xba, 0xff}}},
    {{{   146,     27,    -91}, 0, {  -334,    962}, {0x03, 0x39, 0x8f, 0xff}}},
};

// 0x04017F90
static const Vtx mario_low_poly_eyes_cap_on_dl_vertex[] = {
    {{{   146,     27,     92}, 0, {  1316,    212}, {0x03, 0x3a, 0x70, 0xff}}},
    {{{   168,     79,      0}, 0, {   458,     68}, {0xd9, 0x78, 0x00, 0xff}}},
    {{{    88,    101,     33}, 0, {   762,    990}, {0x38, 0x55, 0x4b, 0xff}}},
    {{{    88,    101,    -32}, 0, {   154,    990}, {0x1e, 0x5d, 0xb1, 0xff}}},
    {{{   146,     27,    -91}, 0, {  -398,    212}, {0x03, 0x39, 0x8f, 0xff}}},
};

// 0x04017FE0
static const Vtx mario_low_poly_mustache_cap_on_dl_vertex[] = {
    {{{    88,    101,    -32}, 0, {   398,    214}, {0x1e, 0x5d, 0xb1, 0xff}}},
    {{{   146,     27,    -91}, 0, {  1408,   -550}, {0x03, 0x39, 0x8f, 0xff}}},
    {{{    34,     39,    -98}, 0, {  1306,    732}, {0xcd, 0x40, 0xa0, 0xff}}},
    {{{    44,    113,      0}, 0, {    10,    800}, {0x9c, 0x4d, 0x00, 0xff}}},
    {{{    -5,     58,      0}, 0, {   300,   1456}, {0x88, 0x28, 0x00, 0xff}}},
    {{{    88,    101,     33}, 0, {   294,    258}, {0x38, 0x55, 0x4b, 0xff}}},
    {{{    44,    113,      0}, 0, {    34,    840}, {0x9c, 0x4d, 0x00, 0xff}}},
    {{{    34,     39,     99}, 0, {  1314,    634}, {0xcd, 0x40, 0x60, 0xff}}},
    {{{   146,     27,     92}, 0, {  1132,   -556}, {0x03, 0x3a, 0x70, 0xff}}},
    {{{    -5,     58,      0}, 0, {   470,   1412}, {0x88, 0x28, 0x00, 0xff}}},
};

// 0x04018080
static const Vtx mario_low_poly_face_cap_dl_vertex[] = {
    {{{   272,     48,    -52}, 0, {     0,      0}, {0x62, 0x27, 0xba, 0xff}}},
    {{{   272,     48,     53}, 0, {     0,      0}, {0x42, 0x51, 0x47, 0xff}}},
    {{{   149,    -87,    138}, 0, {     0,      0}, {0x43, 0xb6, 0x4d, 0xff}}},
    {{{   146,     27,    -91}, 0, {     0,      0}, {0x03, 0x39, 0x8f, 0xff}}},
    {{{   149,    -87,   -137}, 0, {     0,      0}, {0x33, 0xc2, 0x9f, 0xff}}},
    {{{   146,     27,     92}, 0, {     0,      0}, {0x03, 0x3a, 0x70, 0xff}}},
    {{{    58,   -114,     81}, 0, {     0,      0}, {0xba, 0xad, 0x40, 0xff}}},
    {{{    98,   -165,      0}, 0, {     0,      0}, {0xfd, 0x82, 0x00, 0xff}}},
    {{{    58,   -114,    -80}, 0, {     0,      0}, {0xbc, 0xb8, 0xb2, 0xff}}},
    {{{   168,     79,      0}, 0, {     0,      0}, {0xd9, 0x78, 0x00, 0xff}}},
    {{{   175,    154,      0}, 0, {     0,      0}, {0xe1, 0x6c, 0xc6, 0xff}}},
    {{{   174,     79,     -9}, 0, {     0,      0}, {0x51, 0x61, 0xfa, 0xff}}},
};

// 0x04018140
static const Vtx mario_low_poly_face_part_cap_on_dl_vertex[] = {
    {{{    94,    146,     33}, 0, {     0,      0}, {0xff, 0x44, 0x6b, 0xff}}},
    {{{    50,    153,      0}, 0, {     0,      0}, {0xad, 0x5f, 0x00, 0xff}}},
    {{{    44,    113,      0}, 0, {     0,      0}, {0x9c, 0x4d, 0x00, 0xff}}},
    {{{    88,    101,     33}, 0, {     0,      0}, {0x38, 0x55, 0x4b, 0xff}}},
    {{{    94,    146,    -32}, 0, {     0,      0}, {0x3b, 0x35, 0x9e, 0xff}}},
    {{{    88,    101,    -32}, 0, {     0,      0}, {0x1e, 0x5d, 0xb1, 0xff}}},
    {{{    34,     39,     99}, 0, {     0,      0}, {0xcd, 0x40, 0x60, 0xff}}},
    {{{    -5,     58,      0}, 0, {     0,      0}, {0x88, 0x28, 0x00, 0xff}}},
    {{{     7,    -85,     79}, 0, {     0,      0}, {0xaa, 0xdc, 0x54, 0xff}}},
    {{{   146,     27,     92}, 0, {     0,      0}, {0x03, 0x3a, 0x70, 0xff}}},
    {{{     7,    -85,    -78}, 0, {     0,      0}, {0xa9, 0xc7, 0xb9, 0xff}}},
    {{{    34,     39,    -98}, 0, {     0,      0}, {0xcd, 0x40, 0xa0, 0xff}}},
    {{{   146,     27,    -91}, 0, {     0,      0}, {0x03, 0x39, 0x8f, 0xff}}},
};

// 0x04018210
static const Vtx mario_low_poly_face_back_hair_cap_on_dl_vertex[] = {
    {{{    58,   -114,     81}, 0, {     0,      0}, {0xba, 0xad, 0x40, 0xff}}},
    {{{     7,    -85,     79}, 0, {     0,      0}, {0xaa, 0xdc, 0x54, 0xff}}},
    {{{     7,    -85,    -78}, 0, {     0,      0}, {0xa9, 0xc7, 0xb9, 0xff}}},
    {{{    58,   -114,    -80}, 0, {     0,      0}, {0xbc, 0xb8, 0xb2, 0xff}}},
    {{{   146,     27,     92}, 0, {     0,      0}, {0x03, 0x3a, 0x70, 0xff}}},
    {{{   146,     27,    -91}, 0, {     0,      0}, {0x03, 0x39, 0x8f, 0xff}}},
};

// 0x04018270 - 0x04018298
const Gfx mario_low_poly_mario_m_logo_dl[] = {
    gsSPVertex(mario_low_poly_mario_m_logo_dl_vertex, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP1Triangle( 3,  1,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x04018298 - 0x040182C0
const Gfx mario_low_poly_eyes_cap_on_dl[] = {
    gsSPVertex(mario_low_poly_eyes_cap_on_dl_vertex, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  4, 0x0),
    gsSP1Triangle( 3,  2,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x040182C0 - 0x04018300
const Gfx mario_low_poly_mustache_cap_on_dl[] = {
    gsSPVertex(mario_low_poly_mustache_cap_on_dl_vertex, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 2,  4,  3, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 7,  8,  5, 0x0,  6,  9,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x04018300 - 0x04018370
const Gfx mario_low_poly_face_part_cap_on_dl[] = {
    gsSPVertex(mario_low_poly_face_part_cap_on_dl_vertex, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  2,  4,  5, 0x0),
    gsSP2Triangles( 4,  1,  0, 0x0,  4,  0,  3, 0x0),
    gsSP2Triangles( 5,  4,  3, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 9,  6,  8, 0x0,  8,  7, 10, 0x0),
    gsSP2Triangles(10,  7, 11, 0x0, 10, 11, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x04018370 - 0x040183F0
const Gfx mario_low_poly_face_cap_dl[] = {
    gsSPVertex(mario_low_poly_face_cap_dl_vertex, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 6,  2,  5, 0x0,  6,  7,  2, 0x0),
    gsSP2Triangles( 7,  4,  2, 0x0,  3,  4,  8, 0x0),
    gsSP2Triangles( 4,  7,  8, 0x0,  9, 10,  3, 0x0),
    gsSP2Triangles(10, 11,  3, 0x0,  8,  7,  6, 0x0),
    gsSP2Triangles(10,  5, 11, 0x0, 10,  9,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x040183F0 - 0x04018420
const Gfx mario_low_poly_face_back_hair_cap_on_dl[] = {
    gsSPVertex(mario_low_poly_face_back_hair_cap_on_dl_vertex, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  1,  0, 0x0,  3,  2,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x04018420 - 0x04018460
const Gfx mario_low_poly_face_cap_on_dl[] = {
    gsSPDisplayList(mario_low_poly_face_part_cap_on_dl),
    gsSPLight(&mario_red_lights_group.l, 1),
    gsSPLight(&mario_red_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_face_cap_dl),
    gsSPLight(&mario_brown2_lights_group.l, 1),
    gsSPLight(&mario_brown2_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_face_back_hair_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04018460 - 0x04018530
const Gfx mario_low_poly_cap_on_eyes_front[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_front),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04018530 - 0x04018600
const Gfx mario_low_poly_cap_on_eyes_half_closed[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_half_closed),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04018600 - 0x040186D0
const Gfx mario_low_poly_cap_on_eyes_closed[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_closed),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x040186D0 - 0x040187A0
const Gfx mario_low_poly_cap_on_eyes_right[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_right),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x040187A0 - 0x04018870
const Gfx mario_low_poly_cap_on_eyes_left[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_left),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04018870 - 0x04018940
const Gfx mario_low_poly_cap_on_eyes_up[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_up),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04018940 - 0x04018A10
const Gfx mario_low_poly_cap_on_eyes_down[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_down),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04018A10 - 0x04018AE0
const Gfx mario_low_poly_cap_on_eyes_dead[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mario_m_logo_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_dead),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_eyes_cap_on_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_on_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04018AE0 - 0x04018B18
const Gfx mario_metal_low_poly_cap_on[] = {
    gsSPDisplayList(mario_low_poly_mario_m_logo_dl),
    gsSPDisplayList(mario_low_poly_eyes_cap_on_dl),
    gsSPDisplayList(mario_low_poly_mustache_cap_on_dl),
    gsSPDisplayList(mario_low_poly_face_part_cap_on_dl),
    gsSPDisplayList(mario_low_poly_face_cap_dl),
    gsSPDisplayList(mario_low_poly_face_back_hair_cap_on_dl),
    gsSPEndDisplayList(),
};

// 0x04018B18
static const Vtx mario_low_poly_mario_eyes_cap_off_dl_vertex[] = {
    {{{   146,     27,     92}, 0, {  1316,    212}, {0x16, 0x25, 0x77, 0xff}}},
    {{{   168,     79,      0}, 0, {   458,     68}, {0x27, 0x78, 0x00, 0xff}}},
    {{{    88,    101,     33}, 0, {   762,    990}, {0x38, 0x55, 0x4b, 0xff}}},
    {{{    88,    101,    -32}, 0, {   154,    990}, {0x1e, 0x5d, 0xb1, 0xff}}},
    {{{   146,     27,    -91}, 0, {  -398,    212}, {0x14, 0x26, 0x89, 0xff}}},
};

// 0x04018B68
static const Vtx mario_low_poly_mustache_cap_off_dl_vertex[] = {
    {{{    34,     39,    -98}, 0, {  1306,    732}, {0xcd, 0x40, 0xa0, 0xff}}},
    {{{    44,    113,      0}, 0, {    10,    800}, {0x9c, 0x4d, 0x00, 0xff}}},
    {{{    88,    101,    -32}, 0, {   398,    214}, {0x1e, 0x5d, 0xb1, 0xff}}},
    {{{    -5,     58,      0}, 0, {   300,   1456}, {0x88, 0x28, 0x00, 0xff}}},
    {{{   146,     27,    -91}, 0, {  1408,   -550}, {0x14, 0x26, 0x89, 0xff}}},
    {{{    88,    101,     33}, 0, {   294,    258}, {0x38, 0x55, 0x4b, 0xff}}},
    {{{    44,    113,      0}, 0, {    34,    840}, {0x9c, 0x4d, 0x00, 0xff}}},
    {{{    34,     39,     99}, 0, {  1314,    634}, {0xcd, 0x40, 0x60, 0xff}}},
    {{{    -5,     58,      0}, 0, {   470,   1412}, {0x88, 0x28, 0x00, 0xff}}},
    {{{   146,     27,     92}, 0, {  1132,   -556}, {0x16, 0x25, 0x77, 0xff}}},
};

// 0x04018C08
static const Vtx mario_low_poly_face_part_cap_off_dl_vertex[] = {
    {{{    44,    113,      0}, 0, {     0,      0}, {0x9c, 0x4d, 0x00, 0xff}}},
    {{{    94,    146,    -32}, 0, {     0,      0}, {0x3b, 0x35, 0x9e, 0xff}}},
    {{{    88,    101,    -32}, 0, {     0,      0}, {0x1e, 0x5d, 0xb1, 0xff}}},
    {{{    50,    153,      0}, 0, {     0,      0}, {0xad, 0x5f, 0x00, 0xff}}},
    {{{    88,    101,     33}, 0, {     0,      0}, {0x38, 0x55, 0x4b, 0xff}}},
    {{{    94,    146,     33}, 0, {     0,      0}, {0xff, 0x44, 0x6b, 0xff}}},
    {{{   146,     27,     92}, 0, {     0,      0}, {0x16, 0x25, 0x77, 0xff}}},
    {{{    34,     39,     99}, 0, {     0,      0}, {0xcd, 0x40, 0x60, 0xff}}},
    {{{     7,    -85,     79}, 0, {     0,      0}, {0xaa, 0xdc, 0x54, 0xff}}},
    {{{    -5,     58,      0}, 0, {     0,      0}, {0x88, 0x28, 0x00, 0xff}}},
    {{{     7,    -85,    -78}, 0, {     0,      0}, {0xa9, 0xc7, 0xb9, 0xff}}},
    {{{    34,     39,    -98}, 0, {     0,      0}, {0xcd, 0x40, 0xa0, 0xff}}},
    {{{   146,     27,    -91}, 0, {     0,      0}, {0x14, 0x26, 0x89, 0xff}}},
};

// 0x04018CD8
static const Vtx mario_low_poly_face_hair_cap_off_dl_vertex[] = {
    {{{    58,   -114,     81}, 0, {     0,      0}, {0xd3, 0x9b, 0x3d, 0xff}}},
    {{{     7,    -85,     79}, 0, {     0,      0}, {0xaa, 0xdc, 0x54, 0xff}}},
    {{{     7,    -85,    -78}, 0, {     0,      0}, {0xa9, 0xc7, 0xb9, 0xff}}},
    {{{   146,     27,     92}, 0, {     0,      0}, {0x16, 0x25, 0x77, 0xff}}},
    {{{   167,   -121,     77}, 0, {     0,      0}, {0x3c, 0xad, 0x4a, 0xff}}},
    {{{    58,   -114,    -80}, 0, {     0,      0}, {0xd8, 0xa3, 0xb5, 0xff}}},
    {{{    90,   -136,      0}, 0, {     0,      0}, {0xf0, 0x83, 0x00, 0xff}}},
    {{{   146,     27,    -91}, 0, {     0,      0}, {0x14, 0x26, 0x89, 0xff}}},
    {{{   167,   -121,    -81}, 0, {     0,      0}, {0x3a, 0xad, 0xb5, 0xff}}},
    {{{   218,     48,    -52}, 0, {     0,      0}, {0x5f, 0x3b, 0xc6, 0xff}}},
    {{{   168,     79,      0}, 0, {     0,      0}, {0x27, 0x78, 0x00, 0xff}}},
    {{{   192,    -28,    -80}, 0, {     0,      0}, {0x55, 0xf7, 0xa3, 0xff}}},
    {{{   223,    -38,     -1}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0xff}}},
    {{{   218,     48,     53}, 0, {     0,      0}, {0x60, 0x39, 0x3a, 0xff}}},
    {{{   192,    -28,     78}, 0, {     0,      0}, {0x56, 0xf4, 0x5c, 0xff}}},
};

// 0x04018DC8 - 0x04018DF0
const Gfx mario_low_poly_mario_eyes_cap_off_dl[] = {
    gsSPVertex(mario_low_poly_mario_eyes_cap_off_dl_vertex, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  4, 0x0),
    gsSP1Triangle( 3,  2,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x04018DF0 - 0x04018E30
const Gfx mario_low_poly_mustache_cap_off_dl[] = {
    gsSPVertex(mario_low_poly_mustache_cap_off_dl_vertex, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 6,  8,  7, 0x0,  7,  9,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x04018E30 - 0x04018EA0
const Gfx mario_low_poly_face_part_cap_off_dl[] = {
    gsSPVertex(mario_low_poly_face_part_cap_off_dl_vertex, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  0, 0x0,  5,  3,  0, 0x0),
    gsSP2Triangles( 1,  3,  5, 0x0,  2,  1,  4, 0x0),
    gsSP2Triangles( 1,  5,  4, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 7,  9,  8, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles(10,  9, 11, 0x0, 10, 11, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x04018EA0 - 0x04018F68
const Gfx mario_low_poly_face_hair_cap_off_dl[] = {
    gsSPVertex(mario_low_poly_face_hair_cap_off_dl_vertex, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 0,  4,  3, 0x0,  0,  2,  5, 0x0),
    gsSP2Triangles( 5,  6,  0, 0x0,  0,  6,  4, 0x0),
    gsSP2Triangles( 5,  2,  7, 0x0,  7,  8,  5, 0x0),
    gsSP2Triangles( 8,  6,  5, 0x0,  9,  7, 10, 0x0),
    gsSP2Triangles( 7,  9, 11, 0x0,  7, 11,  8, 0x0),
    gsSP2Triangles( 6,  8,  4, 0x0,  8, 11, 12, 0x0),
    gsSP2Triangles( 4,  8, 12, 0x0, 11,  9, 12, 0x0),
    gsSP2Triangles(13, 14, 12, 0x0, 14,  4, 12, 0x0),
    gsSP2Triangles( 4, 14,  3, 0x0, 13, 10,  3, 0x0),
    gsSP2Triangles(14, 13,  3, 0x0,  9, 10, 13, 0x0),
    gsSP1Triangle( 9, 13, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x04018F68 - 0x04018F90
const Gfx mario_low_poly_face_cap_off_dl[] = {
    gsSPDisplayList(mario_low_poly_face_part_cap_off_dl),
    gsSPLight(&mario_brown2_lights_group.l, 1),
    gsSPLight(&mario_brown2_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_face_hair_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04018F90 - 0x04019040
const Gfx mario_low_poly_cap_off_eyes_front[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_front),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04019040 - 0x040190F0
const Gfx mario_low_poly_cap_off_eyes_half_closed[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_half_closed),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x040190F0 - 0x040191A0
const Gfx mario_low_poly_cap_off_eyes_closed[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_closed),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x040191A0 - 0x04019250
const Gfx mario_low_poly_cap_off_eyes_right[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_right),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04019250 - 0x04019300
const Gfx mario_low_poly_cap_off_eyes_left[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_left),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04019300 - 0x040193B0
const Gfx mario_low_poly_cap_off_eyes_up[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_up),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x040193B0 - 0x04019460
const Gfx mario_low_poly_cap_off_eyes_down[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_down),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04019460 - 0x04019510
const Gfx mario_low_poly_cap_off_eyes_dead[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_eyes_dead),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_beige_lights_group.l, 1),
    gsSPLight(&mario_beige_lights_group.a, 2),
    gsSPDisplayList(mario_low_poly_mario_eyes_cap_off_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_mustache),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_low_poly_mustache_cap_off_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_low_poly_face_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04019510 - 0x04019538
const Gfx mario_metal_low_poly_cap_off[] = {
    gsSPDisplayList(mario_low_poly_mario_eyes_cap_off_dl),
    gsSPDisplayList(mario_low_poly_mustache_cap_off_dl),
    gsSPDisplayList(mario_low_poly_face_part_cap_off_dl),
    gsSPDisplayList(mario_low_poly_face_hair_cap_off_dl),
    gsSPEndDisplayList(),
};

// 0x04019538
static const Vtx mario_left_hand_open_shared_dl_vertex_group1[] = {
    {{{   121,     73,    -21}, 0, {     0,      0}, {0x47, 0x60, 0x2b, 0x00}}},
    {{{    70,     89,    -20}, 0, {     0,      0}, {0xec, 0x7c, 0xf6, 0x00}}},
    {{{    77,     41,     21}, 0, {     0,      0}, {0x1d, 0x2a, 0x74, 0x00}}},
    {{{   141,     34,     -7}, 0, {     0,      0}, {0x77, 0x0c, 0x29, 0x00}}},
    {{{    78,     59,    -34}, 0, {     0,      0}, {0xfe, 0x0b, 0x82, 0x00}}},
    {{{   119,     70,    -42}, 0, {     0,      0}, {0x3d, 0x44, 0xa9, 0x00}}},
    {{{   133,     28,    -40}, 0, {     0,      0}, {0x45, 0xc9, 0xa6, 0x00}}},
    {{{    83,    -17,     10}, 0, {     0,      0}, {0x4d, 0xbd, 0x4a, 0x00}}},
    {{{    26,    -32,      6}, 0, {     0,      0}, {0x34, 0xa6, 0x47, 0x00}}},
    {{{    45,    -34,     10}, 0, {     0,      0}, {0xfe, 0x9a, 0x4a, 0x00}}},
    {{{    26,     16,     28}, 0, {     0,      0}, {0xda, 0x0c, 0x78, 0x00}}},
    {{{    41,    -37,    -36}, 0, {     0,      0}, {0xd8, 0x9b, 0xbf, 0x00}}},
    {{{    20,    -27,    -27}, 0, {     0,      0}, {0x09, 0xbf, 0x94, 0x00}}},
    {{{    25,     -1,    -44}, 0, {     0,      0}, {0xe4, 0xe0, 0x89, 0x00}}},
};

// 0x04019618
static const Vtx mario_left_hand_open_shared_dl_vertex_group2[] = {
    {{{    -3,     35,     -9}, 0, {     0,      0}, {0xcd, 0x72, 0x13, 0x00}}},
    {{{     6,     53,     -9}, 0, {     0,      0}, {0xaf, 0x61, 0x00, 0x00}}},
    {{{    11,     29,    -52}, 0, {     0,      0}, {0xba, 0x2a, 0xa0, 0x00}}},
    {{{    26,     16,     28}, 0, {     0,      0}, {0xda, 0x0c, 0x78, 0x00}}},
    {{{     1,    -33,    -23}, 0, {     0,      0}, {0x9c, 0xbb, 0xe0, 0x00}}},
    {{{     6,    -38,      7}, 0, {     0,      0}, {0xae, 0xab, 0x2b, 0x00}}},
    {{{    -6,     -3,     17}, 0, {     0,      0}, {0x90, 0xda, 0x2d, 0x00}}},
    {{{     2,      3,     26}, 0, {     0,      0}, {0xf5, 0x17, 0x7c, 0x00}}},
    {{{    20,    -46,     12}, 0, {     0,      0}, {0x26, 0x9e, 0x45, 0x00}}},
    {{{    26,    -32,      6}, 0, {     0,      0}, {0x34, 0xa6, 0x47, 0x00}}},
    {{{   -17,     40,     -8}, 0, {     0,      0}, {0xbc, 0x68, 0x16, 0x00}}},
    {{{   -10,     14,    -43}, 0, {     0,      0}, {0xd3, 0x19, 0x8d, 0x00}}},
    {{{    20,    -27,    -27}, 0, {     0,      0}, {0x09, 0xbf, 0x94, 0x00}}},
    {{{    13,    -39,    -31}, 0, {     0,      0}, {0x18, 0x9a, 0xba, 0x00}}},
    {{{   -20,     22,     -7}, 0, {     0,      0}, {0x83, 0x06, 0x14, 0x00}}},
    {{{    70,     89,    -20}, 0, {     0,      0}, {0xec, 0x7c, 0xf6, 0x00}}},
};

// 0x04019718
static const Vtx mario_left_hand_open_shared_dl_vertex_group3[] = {
    {{{    45,    -34,     10}, 0, {     0,      0}, {0xfe, 0x9a, 0x4a, 0x00}}},
    {{{    83,    -17,     10}, 0, {     0,      0}, {0x4d, 0xbd, 0x4a, 0x00}}},
    {{{    26,     16,     28}, 0, {     0,      0}, {0xda, 0x0c, 0x78, 0x00}}},
    {{{    89,    -47,    -37}, 0, {     0,      0}, {0x56, 0xaa, 0xdf, 0x00}}},
    {{{    76,    -18,    -48}, 0, {     0,      0}, {0x27, 0x0a, 0x88, 0x00}}},
    {{{    89,     -6,    -27}, 0, {     0,      0}, {0x5c, 0xf5, 0xab, 0x00}}},
    {{{     6,     53,     -9}, 0, {     0,      0}, {0xaf, 0x61, 0x00, 0x00}}},
    {{{    26,     41,    -41}, 0, {     0,      0}, {0x05, 0x34, 0x8d, 0x00}}},
    {{{    11,     29,    -52}, 0, {     0,      0}, {0xba, 0x2a, 0xa0, 0x00}}},
    {{{   141,     34,     -7}, 0, {     0,      0}, {0x77, 0x0c, 0x29, 0x00}}},
    {{{    77,     41,     21}, 0, {     0,      0}, {0x1d, 0x2a, 0x74, 0x00}}},
    {{{   133,     28,    -40}, 0, {     0,      0}, {0x45, 0xc9, 0xa6, 0x00}}},
    {{{    69,     10,    -36}, 0, {     0,      0}, {0x35, 0x1c, 0x91, 0x00}}},
    {{{    78,     59,    -34}, 0, {     0,      0}, {0xfe, 0x0b, 0x82, 0x00}}},
    {{{   121,     73,    -21}, 0, {     0,      0}, {0x47, 0x60, 0x2b, 0x00}}},
    {{{   119,     70,    -42}, 0, {     0,      0}, {0x3d, 0x44, 0xa9, 0x00}}},
};

// 0x04019818
static const Vtx mario_left_hand_open_shared_dl_vertex_group4[] = {
    {{{    25,     -1,    -44}, 0, {     0,      0}, {0xe4, 0xe0, 0x89, 0x00}}},
    {{{    11,     29,    -52}, 0, {     0,      0}, {0xba, 0x2a, 0xa0, 0x00}}},
    {{{    26,     41,    -41}, 0, {     0,      0}, {0x05, 0x34, 0x8d, 0x00}}},
    {{{    78,     59,    -34}, 0, {     0,      0}, {0xfe, 0x0b, 0x82, 0x00}}},
    {{{    69,     10,    -36}, 0, {     0,      0}, {0x35, 0x1c, 0x91, 0x00}}},
    {{{    89,    -47,    -37}, 0, {     0,      0}, {0x56, 0xaa, 0xdf, 0x00}}},
    {{{    41,    -37,    -36}, 0, {     0,      0}, {0xd8, 0x9b, 0xbf, 0x00}}},
    {{{    76,    -18,    -48}, 0, {     0,      0}, {0x27, 0x0a, 0x88, 0x00}}},
    {{{    45,    -34,     10}, 0, {     0,      0}, {0xfe, 0x9a, 0x4a, 0x00}}},
    {{{    89,     -6,    -27}, 0, {     0,      0}, {0x5c, 0xf5, 0xab, 0x00}}},
    {{{    83,    -17,     10}, 0, {     0,      0}, {0x4d, 0xbd, 0x4a, 0x00}}},
    {{{    70,     89,    -20}, 0, {     0,      0}, {0xec, 0x7c, 0xf6, 0x00}}},
    {{{     6,     53,     -9}, 0, {     0,      0}, {0xaf, 0x61, 0x00, 0x00}}},
    {{{   133,     28,    -40}, 0, {     0,      0}, {0x45, 0xc9, 0xa6, 0x00}}},
    {{{    77,     41,     21}, 0, {     0,      0}, {0x1d, 0x2a, 0x74, 0x00}}},
    {{{    26,     16,     28}, 0, {     0,      0}, {0xda, 0x0c, 0x78, 0x00}}},
};

// 0x04019918
static const Vtx mario_left_hand_open_shared_dl_vertex_group5[] = {
    {{{     6,    -38,      7}, 0, {     0,      0}, {0xae, 0xab, 0x2b, 0x00}}},
    {{{     1,    -33,    -23}, 0, {     0,      0}, {0x9c, 0xbb, 0xe0, 0x00}}},
    {{{    13,    -39,    -31}, 0, {     0,      0}, {0x18, 0x9a, 0xba, 0x00}}},
    {{{   -15,      4,    -32}, 0, {     0,      0}, {0x89, 0xdd, 0xe7, 0x00}}},
    {{{   -10,     14,    -43}, 0, {     0,      0}, {0xd3, 0x19, 0x8d, 0x00}}},
    {{{   -20,     22,     -7}, 0, {     0,      0}, {0x83, 0x06, 0x14, 0x00}}},
    {{{    -6,     -3,     17}, 0, {     0,      0}, {0x90, 0xda, 0x2d, 0x00}}},
    {{{     2,      3,     26}, 0, {     0,      0}, {0xf5, 0x17, 0x7c, 0x00}}},
    {{{    20,    -27,    -27}, 0, {     0,      0}, {0x09, 0xbf, 0x94, 0x00}}},
    {{{    26,    -32,      6}, 0, {     0,      0}, {0x34, 0xa6, 0x47, 0x00}}},
    {{{     1,     15,    -37}, 0, {     0,      0}, {0xd2, 0x09, 0x8b, 0x00}}},
    {{{    -3,     35,     -9}, 0, {     0,      0}, {0xcd, 0x72, 0x13, 0x00}}},
    {{{    11,      5,     17}, 0, {     0,      0}, {0xff, 0x1b, 0x7c, 0x00}}},
    {{{    26,     16,     28}, 0, {     0,      0}, {0xda, 0x0c, 0x78, 0x00}}},
    {{{    11,     29,    -52}, 0, {     0,      0}, {0xba, 0x2a, 0xa0, 0x00}}},
    {{{    25,     -1,    -44}, 0, {     0,      0}, {0xe4, 0xe0, 0x89, 0x00}}},
};

// 0x04019A18
static const Vtx mario_left_hand_open_shared_dl_vertex_group6[] = {
    {{{    26,    -32,      6}, 0, {     0,      0}, {0x34, 0xa6, 0x47, 0x00}}},
    {{{    20,    -27,    -27}, 0, {     0,      0}, {0x09, 0xbf, 0x94, 0x00}}},
    {{{    41,    -37,    -36}, 0, {     0,      0}, {0xd8, 0x9b, 0xbf, 0x00}}},
    {{{    26,     16,     28}, 0, {     0,      0}, {0xda, 0x0c, 0x78, 0x00}}},
    {{{    11,      5,     17}, 0, {     0,      0}, {0xff, 0x1b, 0x7c, 0x00}}},
};

// 0x04019A68 - 0x04019CA0
const Gfx mario_left_hand_open_shared_dl[] = {
    gsSPVertex(mario_left_hand_open_shared_dl_vertex_group1, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  3,  7, 0x0),
    gsSP2Triangles( 1,  0,  5, 0x0,  5,  4,  1, 0x0),
    gsSP2Triangles( 5,  3,  6, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles( 8, 11,  9, 0x0, 12, 13, 11, 0x0),
    gsSPVertex(mario_left_hand_open_shared_dl_vertex_group2, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 0, 10,  7, 0x0, 11, 10,  0, 0x0),
    gsSP2Triangles(12, 13, 11, 0x0,  9,  8, 13, 0x0),
    gsSP2Triangles( 7,  5,  8, 0x0, 10, 14,  7, 0x0),
    gsSP2Triangles(11, 14, 10, 0x0, 13,  4, 11, 0x0),
    gsSP2Triangles( 8,  5, 13, 0x0,  3, 15,  1, 0x0),
    gsSPVertex(mario_left_hand_open_shared_dl_vertex_group3, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  5,  3, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 1,  9, 10, 0x0,  5, 11,  1, 0x0),
    gsSP2Triangles(12, 13,  5, 0x0, 14,  9, 15, 0x0),
    gsSP1Triangle( 4,  7, 12, 0x0),
    gsSPVertex(mario_left_hand_open_shared_dl_vertex_group4, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  7,  0,  2, 0x0),
    gsSP2Triangles( 8,  6,  5, 0x0,  5,  9, 10, 0x0),
    gsSP2Triangles(11,  3,  2, 0x0,  2, 12, 11, 0x0),
    gsSP2Triangles( 3, 13,  9, 0x0,  6,  0,  7, 0x0),
    gsSP2Triangles( 7,  4,  9, 0x0, 10, 14, 15, 0x0),
    gsSP1Triangle(15, 14, 11, 0x0),
    gsSPVertex(mario_left_hand_open_shared_dl_vertex_group5, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 4,  3,  5, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 7,  6,  0, 0x0,  2,  8,  9, 0x0),
    gsSP2Triangles( 4, 10,  8, 0x0, 11, 10,  4, 0x0),
    gsSP2Triangles( 7, 12, 11, 0x0,  9, 12,  7, 0x0),
    gsSP2Triangles( 6,  3,  1, 0x0,  6,  5,  3, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0, 14, 10, 11, 0x0),
    gsSP2Triangles( 8, 10, 15, 0x0, 10, 14, 15, 0x0),
    gsSPVertex(mario_left_hand_open_shared_dl_vertex_group6, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x04019CA0 - 0x04019CC0
const Gfx mario_left_hand_open[] = {
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_left_hand_open_shared_dl),
    gsSPEndDisplayList(),
};

// 0x04019CC0
static const Vtx mario_right_hand_open_dl_vertex_group1[] = {
    {{{    81,     37,    -28}, 0, {     0,      0}, {0x18, 0x20, 0x88, 0x00}}},
    {{{    76,     88,      9}, 0, {     0,      0}, {0xef, 0x7d, 0x00, 0x00}}},
    {{{   125,     71,      9}, 0, {     0,      0}, {0x47, 0x5a, 0xcb, 0x00}}},
    {{{   143,     31,     -1}, 0, {     0,      0}, {0x75, 0x06, 0xd2, 0x00}}},
    {{{   136,     28,     31}, 0, {     0,      0}, {0x4a, 0xcf, 0x5a, 0x00}}},
    {{{   124,     69,     30}, 0, {     0,      0}, {0x44, 0x49, 0x4d, 0x00}}},
    {{{    84,     59,     25}, 0, {     0,      0}, {0x04, 0x15, 0x7c, 0x00}}},
    {{{    86,    -19,    -13}, 0, {     0,      0}, {0x49, 0xb6, 0xb8, 0x00}}},
    {{{    30,     13,    -30}, 0, {     0,      0}, {0xd3, 0x03, 0x8a, 0x00}}},
    {{{    48,    -35,     -9}, 0, {     0,      0}, {0xf7, 0x94, 0xbe, 0x00}}},
    {{{    30,    -32,     -4}, 0, {     0,      0}, {0x2f, 0x9f, 0xbe, 0x00}}},
    {{{    46,    -35,     37}, 0, {     0,      0}, {0xd8, 0xa2, 0x4a, 0x00}}},
    {{{    32,      2,     43}, 0, {     0,      0}, {0xe9, 0xeb, 0x7a, 0x00}}},
    {{{    26,    -24,     28}, 0, {     0,      0}, {0x0d, 0xc8, 0x70, 0x00}}},
};

// 0x04019DA0
static const Vtx mario_right_hand_open_dl_vertex_group2[] = {
    {{{    20,     33,     48}, 0, {     0,      0}, {0xbf, 0x32, 0x60, 0x00}}},
    {{{    13,     53,      5}, 0, {     0,      0}, {0xb0, 0x62, 0xfc, 0x00}}},
    {{{     3,     36,      6}, 0, {     0,      0}, {0xce, 0x71, 0xe6, 0x00}}},
    {{{    30,     13,    -30}, 0, {     0,      0}, {0xd3, 0x03, 0x8a, 0x00}}},
    {{{     0,     -4,    -15}, 0, {     0,      0}, {0x8d, 0xda, 0xdc, 0x00}}},
    {{{    11,    -38,     -3}, 0, {     0,      0}, {0xa9, 0xaa, 0xe0, 0x00}}},
    {{{     7,    -30,     26}, 0, {     0,      0}, {0x9b, 0xc0, 0x29, 0x00}}},
    {{{    30,    -32,     -4}, 0, {     0,      0}, {0x2f, 0x9f, 0xbe, 0x00}}},
    {{{    25,    -46,     -9}, 0, {     0,      0}, {0x21, 0x97, 0xc1, 0x00}}},
    {{{     7,      1,    -26}, 0, {     0,      0}, {0xee, 0x0d, 0x84, 0x00}}},
    {{{    -9,     41,      5}, 0, {     0,      0}, {0xbc, 0x66, 0xe5, 0x00}}},
    {{{    -1,     18,     42}, 0, {     0,      0}, {0xd9, 0x23, 0x73, 0x00}}},
    {{{    19,    -36,     33}, 0, {     0,      0}, {0x1a, 0xa0, 0x4d, 0x00}}},
    {{{    26,    -24,     28}, 0, {     0,      0}, {0x0d, 0xc8, 0x70, 0x00}}},
    {{{   -12,     23,      6}, 0, {     0,      0}, {0x83, 0x07, 0xf1, 0x00}}},
    {{{    76,     88,      9}, 0, {     0,      0}, {0xef, 0x7d, 0x00, 0x00}}},
};

// 0x04019EA0
static const Vtx mario_right_hand_open_dl_vertex_group3[] = {
    {{{    30,     13,    -30}, 0, {     0,      0}, {0xd3, 0x03, 0x8a, 0x00}}},
    {{{    86,    -19,    -13}, 0, {     0,      0}, {0x49, 0xb6, 0xb8, 0x00}}},
    {{{    48,    -35,     -9}, 0, {     0,      0}, {0xf7, 0x94, 0xbe, 0x00}}},
    {{{    93,    -45,     36}, 0, {     0,      0}, {0x57, 0xac, 0x23, 0x00}}},
    {{{    93,     -5,     22}, 0, {     0,      0}, {0x61, 0xfa, 0x50, 0x00}}},
    {{{    81,    -15,     45}, 0, {     0,      0}, {0x2f, 0x13, 0x74, 0x00}}},
    {{{    20,     33,     48}, 0, {     0,      0}, {0xbf, 0x32, 0x60, 0x00}}},
    {{{    34,     43,     36}, 0, {     0,      0}, {0x0c, 0x3d, 0x6e, 0x00}}},
    {{{    13,     53,      5}, 0, {     0,      0}, {0xb0, 0x62, 0xfc, 0x00}}},
    {{{    81,     37,    -28}, 0, {     0,      0}, {0x18, 0x20, 0x88, 0x00}}},
    {{{   143,     31,     -1}, 0, {     0,      0}, {0x75, 0x06, 0xd2, 0x00}}},
    {{{   136,     28,     31}, 0, {     0,      0}, {0x4a, 0xcf, 0x5a, 0x00}}},
    {{{    84,     59,     25}, 0, {     0,      0}, {0x04, 0x15, 0x7c, 0x00}}},
    {{{    75,     11,     32}, 0, {     0,      0}, {0x3c, 0x23, 0x69, 0x00}}},
    {{{   124,     69,     30}, 0, {     0,      0}, {0x44, 0x49, 0x4d, 0x00}}},
    {{{   125,     71,      9}, 0, {     0,      0}, {0x47, 0x5a, 0xcb, 0x00}}},
};

// 0x04019FA0
static const Vtx mario_right_hand_open_dl_vertex_group4[] = {
    {{{    34,     43,     36}, 0, {     0,      0}, {0x0c, 0x3d, 0x6e, 0x00}}},
    {{{    20,     33,     48}, 0, {     0,      0}, {0xbf, 0x32, 0x60, 0x00}}},
    {{{    32,      2,     43}, 0, {     0,      0}, {0xe9, 0xeb, 0x7a, 0x00}}},
    {{{    75,     11,     32}, 0, {     0,      0}, {0x3c, 0x23, 0x69, 0x00}}},
    {{{    84,     59,     25}, 0, {     0,      0}, {0x04, 0x15, 0x7c, 0x00}}},
    {{{    81,    -15,     45}, 0, {     0,      0}, {0x2f, 0x13, 0x74, 0x00}}},
    {{{    46,    -35,     37}, 0, {     0,      0}, {0xd8, 0xa2, 0x4a, 0x00}}},
    {{{    93,    -45,     36}, 0, {     0,      0}, {0x57, 0xac, 0x23, 0x00}}},
    {{{    48,    -35,     -9}, 0, {     0,      0}, {0xf7, 0x94, 0xbe, 0x00}}},
    {{{    86,    -19,    -13}, 0, {     0,      0}, {0x49, 0xb6, 0xb8, 0x00}}},
    {{{    93,     -5,     22}, 0, {     0,      0}, {0x61, 0xfa, 0x50, 0x00}}},
    {{{    76,     88,      9}, 0, {     0,      0}, {0xef, 0x7d, 0x00, 0x00}}},
    {{{    13,     53,      5}, 0, {     0,      0}, {0xb0, 0x62, 0xfc, 0x00}}},
    {{{   136,     28,     31}, 0, {     0,      0}, {0x4a, 0xcf, 0x5a, 0x00}}},
    {{{    30,     13,    -30}, 0, {     0,      0}, {0xd3, 0x03, 0x8a, 0x00}}},
    {{{    81,     37,    -28}, 0, {     0,      0}, {0x18, 0x20, 0x88, 0x00}}},
};

// 0x0401A0A0
static const Vtx mario_right_hand_open_dl_vertex_group5[] = {
    {{{    19,    -36,     33}, 0, {     0,      0}, {0x1a, 0xa0, 0x4d, 0x00}}},
    {{{     7,    -30,     26}, 0, {     0,      0}, {0x9b, 0xc0, 0x29, 0x00}}},
    {{{    11,    -38,     -3}, 0, {     0,      0}, {0xa9, 0xaa, 0xe0, 0x00}}},
    {{{    -1,     18,     42}, 0, {     0,      0}, {0xd9, 0x23, 0x73, 0x00}}},
    {{{    -7,      7,     32}, 0, {     0,      0}, {0x8a, 0xe2, 0x20, 0x00}}},
    {{{   -12,     23,      6}, 0, {     0,      0}, {0x83, 0x07, 0xf1, 0x00}}},
    {{{     7,      1,    -26}, 0, {     0,      0}, {0xee, 0x0d, 0x84, 0x00}}},
    {{{     0,     -4,    -15}, 0, {     0,      0}, {0x8d, 0xda, 0xdc, 0x00}}},
    {{{    30,    -32,     -4}, 0, {     0,      0}, {0x2f, 0x9f, 0xbe, 0x00}}},
    {{{    26,    -24,     28}, 0, {     0,      0}, {0x0d, 0xc8, 0x70, 0x00}}},
    {{{     9,     18,     35}, 0, {     0,      0}, {0xd8, 0x13, 0x76, 0x00}}},
    {{{     3,     36,      6}, 0, {     0,      0}, {0xce, 0x71, 0xe6, 0x00}}},
    {{{    16,      4,    -17}, 0, {     0,      0}, {0xf9, 0x11, 0x83, 0x00}}},
    {{{    30,     13,    -30}, 0, {     0,      0}, {0xd3, 0x03, 0x8a, 0x00}}},
    {{{    20,     33,     48}, 0, {     0,      0}, {0xbf, 0x32, 0x60, 0x00}}},
    {{{    32,      2,     43}, 0, {     0,      0}, {0xe9, 0xeb, 0x7a, 0x00}}},
};

// 0x0401A1A0
static const Vtx mario_right_hand_open_dl_vertex_group6[] = {
    {{{    46,    -35,     37}, 0, {     0,      0}, {0xd8, 0xa2, 0x4a, 0x00}}},
    {{{    26,    -24,     28}, 0, {     0,      0}, {0x0d, 0xc8, 0x70, 0x00}}},
    {{{    30,    -32,     -4}, 0, {     0,      0}, {0x2f, 0x9f, 0xbe, 0x00}}},
    {{{    16,      4,    -17}, 0, {     0,      0}, {0xf9, 0x11, 0x83, 0x00}}},
    {{{    30,     13,    -30}, 0, {     0,      0}, {0xd3, 0x03, 0x8a, 0x00}}},
};

// 0x0401A1F0 - 0x0401A428
const Gfx mario_right_hand_open_dl[] = {
    gsSPVertex(mario_right_hand_open_dl_vertex_group1, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  3,  4, 0x0),
    gsSP2Triangles( 5,  2,  1, 0x0,  1,  6,  5, 0x0),
    gsSP2Triangles( 4,  3,  5, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles( 9, 11, 10, 0x0, 11, 12, 13, 0x0),
    gsSPVertex(mario_right_hand_open_dl_vertex_group2, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9, 10,  2, 0x0,  2, 10, 11, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0, 12,  8,  7, 0x0),
    gsSP2Triangles( 8,  5,  9, 0x0,  9, 14, 10, 0x0),
    gsSP2Triangles(10, 14, 11, 0x0, 11,  6, 12, 0x0),
    gsSP2Triangles(12,  5,  8, 0x0,  1, 15,  3, 0x0),
    gsSPVertex(mario_right_hand_open_dl_vertex_group3, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 3,  4,  5, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 9, 10,  1, 0x0,  1, 11,  4, 0x0),
    gsSP2Triangles( 4, 12, 13, 0x0, 14, 10, 15, 0x0),
    gsSP1Triangle(13,  7,  5, 0x0),
    gsSPVertex(mario_right_hand_open_dl_vertex_group4, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  0,  2,  5, 0x0),
    gsSP2Triangles( 7,  6,  8, 0x0,  9, 10,  7, 0x0),
    gsSP2Triangles( 0,  4, 11, 0x0, 11, 12,  0, 0x0),
    gsSP2Triangles(10, 13,  4, 0x0,  5,  2,  6, 0x0),
    gsSP2Triangles(10,  3,  5, 0x0, 14, 15,  9, 0x0),
    gsSP1Triangle(11, 15, 14, 0x0),
    gsSPVertex(mario_right_hand_open_dl_vertex_group5, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 5,  4,  3, 0x0,  6,  7,  5, 0x0),
    gsSP2Triangles( 2,  7,  6, 0x0,  8,  9,  0, 0x0),
    gsSP2Triangles( 9, 10,  3, 0x0,  3, 10, 11, 0x0),
    gsSP2Triangles(11, 12,  6, 0x0,  6, 12,  8, 0x0),
    gsSP2Triangles( 1,  4,  7, 0x0,  4,  5,  7, 0x0),
    gsSP2Triangles(13, 12, 11, 0x0, 11, 10, 14, 0x0),
    gsSP2Triangles(15, 10,  9, 0x0, 15, 14, 10, 0x0),
    gsSPVertex(mario_right_hand_open_dl_vertex_group6, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401A428 - 0x0401A448
const Gfx mario_right_hand_open[] = {
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_right_hand_open_dl),
    gsSPEndDisplayList(),
};

// 0x0401A448 - 0x0401A478
const Gfx mario_metal_right_hand_open[] = {
    gsSPDisplayList(mario_right_hand_open_dl),
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_OFF),
    gsSPEndDisplayList(),
};

// 0x0401A478
static const Vtx mario_right_hand_cap_m_logo_dl_vertex[] = {
    {{{   131,     47,     -1}, 0, {  1104,    228}, {0xd7, 0x60, 0xb8, 0xff}}},
    {{{   107,    -19,    -33}, 0, {   474,     32}, {0xd1, 0x0e, 0x8c, 0xff}}},
    {{{    78,     11,     40}, 0, {   772,    818}, {0xd0, 0x3e, 0x9d, 0xff}}},
    {{{    70,    -52,     35}, 0, {   196,    800}, {0xc5, 0xde, 0x96, 0xff}}},
    {{{   114,    -92,    -11}, 0, {  -148,    188}, {0xc4, 0xb8, 0xac, 0xff}}},
};

// 0x0401A4C8
static const Vtx mario_right_hand_cap_hand_position_dl_vertex_group1[] = {
    {{{     1,     24,     35}, 0, {     0,      0}, {0xd4, 0x3c, 0x66, 0xff}}},
    {{{    29,      7,     48}, 0, {     0,      0}, {0xc3, 0xfa, 0x6e, 0xff}}},
    {{{    29,     62,     34}, 0, {     0,      0}, {0xcd, 0x4c, 0x57, 0xff}}},
    {{{    11,     60,     -3}, 0, {     0,      0}, {0xab, 0x59, 0xe6, 0xff}}},
    {{{    56,     11,     65}, 0, {     0,      0}, {0xfc, 0x1b, 0x7b, 0xff}}},
    {{{    54,     54,     44}, 0, {     0,      0}, {0x1f, 0x50, 0x5c, 0xff}}},
    {{{    58,     81,      0}, 0, {     0,      0}, {0x13, 0x7a, 0xe8, 0xff}}},
    {{{    -2,     41,      0}, 0, {     0,      0}, {0xee, 0x71, 0xca, 0xff}}},
    {{{    35,      9,    -36}, 0, {     0,      0}, {0xe4, 0xf4, 0x85, 0xff}}},
    {{{   -13,     26,     41}, 0, {     0,      0}, {0xee, 0x3c, 0x6e, 0xff}}},
    {{{    23,    -25,     35}, 0, {     0,      0}, {0x04, 0xc2, 0x6e, 0xff}}},
    {{{    75,    -32,     65}, 0, {     0,      0}, {0x08, 0xae, 0x60, 0xff}}},
    {{{    16,      1,    -22}, 0, {     0,      0}, {0x04, 0x00, 0x82, 0xff}}},
    {{{     4,     -3,    -32}, 0, {     0,      0}, {0x11, 0x06, 0x83, 0xff}}},
    {{{   -19,     47,     -4}, 0, {     0,      0}, {0xad, 0x54, 0xd3, 0xff}}},
};

// 0x0401A5B8
static const Vtx mario_right_hand_cap_hand_position_dl_vertex_group2[] = {
    {{{   103,    -25,      1}, 0, {     0,      0}, {0x67, 0xc4, 0xd6, 0xff}}},
    {{{    95,     -8,     52}, 0, {     0,      0}, {0x60, 0x06, 0x52, 0xff}}},
    {{{    75,    -32,     65}, 0, {     0,      0}, {0x08, 0xae, 0x60, 0xff}}},
    {{{    31,    -39,      0}, 0, {     0,      0}, {0x42, 0xa3, 0xca, 0xff}}},
    {{{    23,    -25,     35}, 0, {     0,      0}, {0x04, 0xc2, 0x6e, 0xff}}},
    {{{    13,    -37,     41}, 0, {     0,      0}, {0x12, 0xb1, 0x61, 0xff}}},
    {{{    55,    -43,     -3}, 0, {     0,      0}, {0x00, 0x86, 0xe0, 0xff}}},
    {{{   -13,     26,     41}, 0, {     0,      0}, {0xee, 0x3c, 0x6e, 0xff}}},
    {{{     4,     -3,    -32}, 0, {     0,      0}, {0x11, 0x06, 0x83, 0xff}}},
    {{{    16,      1,    -22}, 0, {     0,      0}, {0x04, 0x00, 0x82, 0xff}}},
    {{{    35,      9,    -36}, 0, {     0,      0}, {0xe4, 0xf4, 0x85, 0xff}}},
    {{{    24,    -55,     -4}, 0, {     0,      0}, {0x04, 0x8b, 0xd1, 0xff}}},
    {{{    56,     11,     65}, 0, {     0,      0}, {0xfc, 0x1b, 0x7b, 0xff}}},
    {{{   -15,    -12,      7}, 0, {     0,      0}, {0x8c, 0xcf, 0xf8, 0xff}}},
    {{{    85,     15,     52}, 0, {     0,      0}, {0x40, 0x30, 0x62, 0xff}}},
    {{{    54,     54,     44}, 0, {     0,      0}, {0x1f, 0x50, 0x5c, 0xff}}},
};

// 0x0401A6B8
static const Vtx mario_right_hand_cap_hand_position_dl_vertex_group3[] = {
    {{{    35,      9,    -36}, 0, {     0,      0}, {0xe4, 0xf4, 0x85, 0xff}}},
    {{{    85,     30,    -32}, 0, {     0,      0}, {0x4f, 0x21, 0xa3, 0xff}}},
    {{{   103,    -25,      1}, 0, {     0,      0}, {0x67, 0xc4, 0xd6, 0xff}}},
    {{{    58,     81,      0}, 0, {     0,      0}, {0x13, 0x7a, 0xe8, 0xff}}},
    {{{    24,    -55,     -4}, 0, {     0,      0}, {0x04, 0x8b, 0xd1, 0xff}}},
    {{{   -15,    -12,      7}, 0, {     0,      0}, {0x8c, 0xcf, 0xf8, 0xff}}},
    {{{     4,     -3,    -32}, 0, {     0,      0}, {0x11, 0x06, 0x83, 0xff}}},
    {{{   -19,     47,     -4}, 0, {     0,      0}, {0xad, 0x54, 0xd3, 0xff}}},
    {{{   -13,     26,     41}, 0, {     0,      0}, {0xee, 0x3c, 0x6e, 0xff}}},
    {{{    13,    -37,     41}, 0, {     0,      0}, {0x12, 0xb1, 0x61, 0xff}}},
    {{{    96,     39,     24}, 0, {     0,      0}, {0x6b, 0x39, 0x21, 0xff}}},
    {{{    54,     54,     44}, 0, {     0,      0}, {0x1f, 0x50, 0x5c, 0xff}}},
    {{{    95,     -8,     52}, 0, {     0,      0}, {0x60, 0x06, 0x52, 0xff}}},
    {{{    85,     15,     52}, 0, {     0,      0}, {0x40, 0x30, 0x62, 0xff}}},
};

// 0x0401A798
static const Vtx mario_right_hand_cap_top_dl_vertex_group1[] = {
    {{{    29,    -82,     43}, 0, {     0,      0}, {0x95, 0xbd, 0x00, 0xff}}},
    {{{    70,    -52,     35}, 0, {     0,      0}, {0xc5, 0xde, 0x96, 0xff}}},
    {{{   106,   -124,     90}, 0, {     0,      0}, {0x9d, 0xb2, 0x09, 0xff}}},
    {{{    65,    -53,     55}, 0, {     0,      0}, {0xb7, 0x01, 0x67, 0xff}}},
    {{{   151,   -168,     67}, 0, {     0,      0}, {0xf4, 0x87, 0xdc, 0xff}}},
    {{{   137,   -137,    134}, 0, {     0,      0}, {0x9f, 0xdf, 0x49, 0xff}}},
    {{{   114,    -92,    -11}, 0, {     0,      0}, {0xc4, 0xb8, 0xac, 0xff}}},
    {{{   228,   -121,    117}, 0, {     0,      0}, {0x66, 0xcd, 0xcb, 0xff}}},
    {{{   182,    -72,     -8}, 0, {     0,      0}, {0x4d, 0xd9, 0xa4, 0xff}}},
    {{{   181,   -167,    138}, 0, {     0,      0}, {0x06, 0x88, 0x26, 0xff}}},
    {{{   171,   -129,    178}, 0, {     0,      0}, {0xb3, 0xd1, 0x58, 0xff}}},
    {{{   160,     65,    148}, 0, {     0,      0}, {0xb3, 0x47, 0x47, 0xff}}},
    {{{   211,     81,    156}, 0, {     0,      0}, {0x22, 0x6c, 0x37, 0xff}}},
    {{{   183,    100,     86}, 0, {     0,      0}, {0x11, 0x7c, 0xed, 0xff}}},
    {{{   129,     65,    103}, 0, {     0,      0}, {0xa7, 0x50, 0x27, 0xff}}},
};

// 0x0401A888
static const Vtx mario_right_hand_cap_top_dl_vertex_group2[] = {
    {{{    78,     11,     40}, 0, {     0,      0}, {0xd0, 0x3e, 0x9d, 0xff}}},
    {{{   129,     65,    103}, 0, {     0,      0}, {0xa7, 0x50, 0x27, 0xff}}},
    {{{   131,     47,     -1}, 0, {     0,      0}, {0xd7, 0x60, 0xb8, 0xff}}},
    {{{    45,     49,     53}, 0, {     0,      0}, {0xa8, 0x5a, 0x0a, 0xff}}},
    {{{    73,     11,     60}, 0, {     0,      0}, {0xbb, 0xfb, 0x6a, 0xff}}},
    {{{   183,    100,     86}, 0, {     0,      0}, {0x11, 0x7c, 0xed, 0xff}}},
    {{{   192,     42,    190}, 0, {     0,      0}, {0xb3, 0x23, 0x5d, 0xff}}},
    {{{   211,     81,    156}, 0, {     0,      0}, {0x22, 0x6c, 0x37, 0xff}}},
    {{{   160,     65,    148}, 0, {     0,      0}, {0xb3, 0x47, 0x47, 0xff}}},
    {{{    18,    -13,     33}, 0, {     0,      0}, {0x84, 0x10, 0xed, 0xff}}},
    {{{    70,    -52,     35}, 0, {     0,      0}, {0xc5, 0xde, 0x96, 0xff}}},
    {{{   192,     11,     -2}, 0, {     0,      0}, {0x4b, 0x29, 0xa3, 0xff}}},
    {{{   107,    -19,    -33}, 0, {     0,      0}, {0xd1, 0x0e, 0x8c, 0xff}}},
    {{{   228,   -121,    117}, 0, {     0,      0}, {0x66, 0xcd, 0xcb, 0xff}}},
    {{{   246,     29,    128}, 0, {     0,      0}, {0x72, 0x20, 0xd4, 0xff}}},
    {{{   285,     -6,    196}, 0, {     0,      0}, {0x79, 0x23, 0x0b, 0xff}}},
};

// 0x0401A988
static const Vtx mario_right_hand_cap_top_dl_vertex_group3[] = {
    {{{    65,    -53,     55}, 0, {     0,      0}, {0xb7, 0x01, 0x67, 0xff}}},
    {{{    18,    -13,     33}, 0, {     0,      0}, {0x84, 0x10, 0xed, 0xff}}},
    {{{    29,    -82,     43}, 0, {     0,      0}, {0x95, 0xbd, 0x00, 0xff}}},
    {{{    70,    -52,     35}, 0, {     0,      0}, {0xc5, 0xde, 0x96, 0xff}}},
    {{{    73,     11,     60}, 0, {     0,      0}, {0xbb, 0xfb, 0x6a, 0xff}}},
    {{{   114,    -92,    -11}, 0, {     0,      0}, {0xc4, 0xb8, 0xac, 0xff}}},
    {{{   107,    -19,    -33}, 0, {     0,      0}, {0xd1, 0x0e, 0x8c, 0xff}}},
    {{{   182,    -72,     -8}, 0, {     0,      0}, {0x4d, 0xd9, 0xa4, 0xff}}},
    {{{   192,     11,     -2}, 0, {     0,      0}, {0x4b, 0x29, 0xa3, 0xff}}},
    {{{   246,     29,    128}, 0, {     0,      0}, {0x72, 0x20, 0xd4, 0xff}}},
    {{{   228,   -121,    117}, 0, {     0,      0}, {0x66, 0xcd, 0xcb, 0xff}}},
    {{{   211,     81,    156}, 0, {     0,      0}, {0x22, 0x6c, 0x37, 0xff}}},
    {{{   285,     -6,    196}, 0, {     0,      0}, {0x79, 0x23, 0x0b, 0xff}}},
    {{{   273,   -105,    188}, 0, {     0,      0}, {0x5f, 0xbf, 0x34, 0xff}}},
    {{{   181,   -167,    138}, 0, {     0,      0}, {0x06, 0x88, 0x26, 0xff}}},
    {{{   228,      1,    228}, 0, {     0,      0}, {0x14, 0x22, 0x78, 0xff}}},
};

// 0x0401AA88
static const Vtx mario_right_hand_cap_top_dl_vertex_group4[] = {
    {{{   181,   -167,    138}, 0, {     0,      0}, {0x06, 0x88, 0x26, 0xff}}},
    {{{   273,   -105,    188}, 0, {     0,      0}, {0x5f, 0xbf, 0x34, 0xff}}},
    {{{   216,   -104,    220}, 0, {     0,      0}, {0xe5, 0xcc, 0x70, 0xff}}},
    {{{   228,      1,    228}, 0, {     0,      0}, {0x14, 0x22, 0x78, 0xff}}},
    {{{   285,     -6,    196}, 0, {     0,      0}, {0x79, 0x23, 0x0b, 0xff}}},
    {{{   211,     81,    156}, 0, {     0,      0}, {0x22, 0x6c, 0x37, 0xff}}},
    {{{   192,     42,    190}, 0, {     0,      0}, {0xb3, 0x23, 0x5d, 0xff}}},
    {{{   171,   -129,    178}, 0, {     0,      0}, {0xb3, 0xd1, 0x58, 0xff}}},
};

// 0x0401AB08
static const Vtx mario_right_hand_cap_bottom_dl_vertex[] = {
    {{{    65,    -53,     55}, 0, {     0,      0}, {0xb7, 0x01, 0x67, 0xff}}},
    {{{   106,   -124,     90}, 0, {     0,      0}, {0x9d, 0xb2, 0x09, 0xff}}},
    {{{   137,   -137,    134}, 0, {     0,      0}, {0x9f, 0xdf, 0x49, 0xff}}},
    {{{    73,     11,     60}, 0, {     0,      0}, {0xbb, 0xfb, 0x6a, 0xff}}},
    {{{   129,     65,    103}, 0, {     0,      0}, {0xa7, 0x50, 0x27, 0xff}}},
    {{{   160,     65,    148}, 0, {     0,      0}, {0xb3, 0x47, 0x47, 0xff}}},
    {{{   192,     42,    190}, 0, {     0,      0}, {0xb3, 0x23, 0x5d, 0xff}}},
    {{{   171,   -129,    178}, 0, {     0,      0}, {0xb3, 0xd1, 0x58, 0xff}}},
    {{{   216,   -104,    220}, 0, {     0,      0}, {0xe5, 0xcc, 0x70, 0xff}}},
    {{{   228,      1,    228}, 0, {     0,      0}, {0x14, 0x22, 0x78, 0xff}}},
};

// 0x0401ABA8 - 0x0401ABD0
const Gfx mario_right_hand_cap_m_logo_dl[] = {
    gsSPVertex(mario_right_hand_cap_m_logo_dl_vertex, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP1Triangle( 1,  4,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401ABD0 - 0x0401AD40
const Gfx mario_right_hand_cap_top_dl[] = {
    gsSPVertex(mario_right_hand_cap_top_dl_vertex_group1, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  6,  2,  1, 0x0),
    gsSP2Triangles( 2,  6,  4, 0x0,  7,  4,  8, 0x0),
    gsSP2Triangles( 8,  4,  6, 0x0,  4,  9,  5, 0x0),
    gsSP2Triangles( 4,  7,  9, 0x0,  5,  9, 10, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0, 13, 14, 11, 0x0),
    gsSPVertex(mario_right_hand_cap_top_dl_vertex_group2, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 0,  3,  1, 0x0,  5,  2,  1, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  0,  9,  3, 0x0),
    gsSP2Triangles(10,  9,  0, 0x0, 11, 12,  2, 0x0),
    gsSP2Triangles( 2,  5, 11, 0x0,  3,  9,  4, 0x0),
    gsSP2Triangles(13, 14, 15, 0x0, 11,  5, 14, 0x0),
    gsSP1Triangle( 7, 14,  5, 0x0),
    gsSPVertex(mario_right_hand_cap_top_dl_vertex_group3, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  7,  6,  8, 0x0),
    gsSP2Triangles(10,  7,  9, 0x0, 11, 12,  9, 0x0),
    gsSP2Triangles(10, 13, 14, 0x0, 12, 13, 10, 0x0),
    gsSP1Triangle(13, 12, 15, 0x0),
    gsSPVertex(mario_right_hand_cap_top_dl_vertex_group4, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 3,  4,  5, 0x0,  3,  5,  6, 0x0),
    gsSP1Triangle( 7,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401AD40 - 0x0401AED0
const Gfx mario_right_hand_cap_hand_position_dl[] = {
    gsSPVertex(mario_right_hand_cap_hand_position_dl_vertex_group1, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  2,  5,  6, 0x0),
    gsSP2Triangles( 6,  3,  2, 0x0,  5,  2,  4, 0x0),
    gsSP2Triangles( 3,  7,  0, 0x0,  3,  6,  8, 0x0),
    gsSP2Triangles( 3,  8,  7, 0x0,  9,  0,  7, 0x0),
    gsSP2Triangles(10,  0,  9, 0x0,  1,  0, 10, 0x0),
    gsSP2Triangles(11,  1, 10, 0x0, 11,  4,  1, 0x0),
    gsSP2Triangles( 7, 12, 13, 0x0,  8, 12,  7, 0x0),
    gsSP2Triangles(13, 14,  7, 0x0,  7, 14,  9, 0x0),
    gsSPVertex(mario_right_hand_cap_hand_position_dl_vertex_group2, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  6,  2,  4, 0x0),
    gsSP2Triangles( 7,  5,  4, 0x0,  8,  9,  3, 0x0),
    gsSP2Triangles( 3,  9, 10, 0x0, 10,  6,  3, 0x0),
    gsSP2Triangles( 5, 11,  3, 0x0,  3, 11,  8, 0x0),
    gsSP2Triangles(10,  0,  6, 0x0,  6,  0,  2, 0x0),
    gsSP2Triangles( 2,  1, 12, 0x0,  5, 13, 11, 0x0),
    gsSP2Triangles( 1, 14, 12, 0x0, 14, 15, 12, 0x0),
    gsSPVertex(mario_right_hand_cap_hand_position_dl_vertex_group3, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 7,  5,  8, 0x0,  8,  5,  9, 0x0),
    gsSP2Triangles( 3, 10,  1, 0x0, 11, 10,  3, 0x0),
    gsSP2Triangles( 1, 10,  2, 0x0,  2, 10, 12, 0x0),
    gsSP2Triangles(12, 10, 13, 0x0, 10, 11, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401AED0 - 0x0401AF20
const Gfx mario_right_hand_cap_bottom_dl[] = {
    gsSPVertex(mario_right_hand_cap_bottom_dl_vertex, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  3,  2, 0x0,  5,  4,  2, 0x0),
    gsSP2Triangles( 6,  5,  2, 0x0,  6,  2,  7, 0x0),
    gsSP2Triangles( 8,  9,  6, 0x0,  6,  7,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401AF20 - 0x0401AF60
const Gfx mario_right_hand_cap_dl[] = {
    gsSPDisplayList(mario_right_hand_cap_top_dl),
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_right_hand_cap_hand_position_dl),
    gsSPLight(&mario_brown2_lights_group.l, 1),
    gsSPLight(&mario_brown2_lights_group.a, 2),
    gsSPDisplayList(mario_right_hand_cap_bottom_dl),
    gsSPEndDisplayList(),
};

// 0x0401AF60
static const Vtx mario_right_hand_cap_wings_half_1_dl_vertex[] = {
    {{{   368,    146,      7}, 0, {   990,      0}, {0xbe, 0x65, 0xda, 0xff}}},
    {{{   166,     37,     68}, 0, {     0,   2012}, {0xbe, 0x65, 0xda, 0xff}}},
    {{{   212,     96,    143}, 0, {   990,   2012}, {0xbe, 0x65, 0xda, 0xff}}},
    {{{   368,    146,      7}, 0, {   990,      0}, {0xbe, 0x64, 0xd9, 0xff}}},
    {{{   322,     87,    -67}, 0, {     0,      0}, {0xbe, 0x64, 0xd9, 0xff}}},
    {{{   166,     37,     68}, 0, {     0,   2012}, {0xbe, 0x64, 0xd9, 0xff}}},
    {{{   178,   -177,    128}, 0, {   990,   2012}, {0xa8, 0xb4, 0xcf, 0xff}}},
    {{{   149,   -100,     60}, 0, {     0,   2012}, {0xa8, 0xb4, 0xcf, 0xff}}},
    {{{   319,   -248,    -14}, 0, {   990,      0}, {0xa8, 0xb4, 0xcf, 0xff}}},
    {{{   290,   -171,    -81}, 0, {     0,      0}, {0xa8, 0xb4, 0xcf, 0xff}}},
};

// 0x0401B000
static const Vtx mario_right_hand_cap_wings_half_2_dl_vertex[] = {
    {{{   414,    206,     82}, 0, {   990,      0}, {0xbe, 0x64, 0xd9, 0xff}}},
    {{{   212,     96,    143}, 0, {     0,   2012}, {0xbe, 0x64, 0xd9, 0xff}}},
    {{{   258,    156,    218}, 0, {   990,   2012}, {0xbe, 0x64, 0xd9, 0xff}}},
    {{{   368,    146,      7}, 0, {     0,      0}, {0xbe, 0x64, 0xd9, 0xff}}},
    {{{   178,   -177,    128}, 0, {     0,   2012}, {0xa8, 0xb3, 0xcf, 0xff}}},
    {{{   319,   -248,    -14}, 0, {     0,      0}, {0xa8, 0xb3, 0xcf, 0xff}}},
    {{{   349,   -325,     53}, 0, {   990,      0}, {0xa8, 0xb3, 0xcf, 0xff}}},
    {{{   207,   -253,    195}, 0, {   990,   2012}, {0xa8, 0xb3, 0xcf, 0xff}}},
};

// 0x0401B080 - 0x0401B0B0
const Gfx mario_right_hand_cap_wings_half_1_dl[] = {
    gsSPVertex(mario_right_hand_cap_wings_half_1_dl_vertex, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  7,  9,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401B0B0 - 0x0401B0E0
const Gfx mario_right_hand_cap_wings_half_2_dl[] = {
    gsSPVertex(mario_right_hand_cap_wings_half_2_dl_vertex, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  4,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401B0E0 - 0x0401B138
const Gfx mario_right_hand_cap_wings_intial_dl[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADEA, G_CC_MODULATERGBFADEA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x0401B138 - 0x0401B158
const Gfx mario_right_hand_cap_wings_end_dl[] = {
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0401B158 - 0x0401B1D8
const Gfx mario_right_hand_cap[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_right_hand_cap_m_logo_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_right_hand_cap_dl),
    gsSPEndDisplayList(),
};

// 0x0401B1D8 - 0x0401B230
const Gfx mario_right_hand_cap_wings[] = {
    gsSPDisplayList(mario_right_hand_cap_wings_intial_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_wings_half_1),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_right_hand_cap_wings_half_1_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_wings_half_2),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_right_hand_cap_wings_half_2_dl),
    gsSPDisplayList(mario_right_hand_cap_wings_end_dl),
    gsSPEndDisplayList(),
};

// 0x0401B230 - 0x0401B278
const Gfx mario_metal_right_hand_cap_shared_dl[] = {
    gsSPDisplayList(mario_right_hand_cap_m_logo_dl),
    gsSPDisplayList(mario_right_hand_cap_top_dl),
    gsSPDisplayList(mario_right_hand_cap_hand_position_dl),
    gsSPDisplayList(mario_right_hand_cap_bottom_dl),
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_OFF),
    gsSPEndDisplayList(),
};

// 0x0401B278 - 0x0401B2D0
const Gfx mario_metal_right_hand_cap_shared_dl_wings[] = {
    gsSPDisplayList(mario_right_hand_cap_wings_intial_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_metal_wings_half_1),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_right_hand_cap_wings_half_1_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_metal_wings_half_2),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_right_hand_cap_wings_half_2_dl),
    gsSPDisplayList(mario_right_hand_cap_wings_end_dl),
    gsSPEndDisplayList(),
};

// 0x0401B2D0
static const Vtx mario_right_hand_peace_shared_dl_vertex_group1[] = {
    {{{   -21,      9,     33}, 0, {     0,      0}, {0x8a, 0xe1, 0x1f, 0x00}}},
    {{{   -25,     24,      0}, 0, {     0,      0}, {0x84, 0xf8, 0xea, 0x00}}},
    {{{    -8,    -12,    -20}, 0, {     0,      0}, {0x93, 0xcf, 0xd7, 0x00}}},
    {{{     0,    -35,     32}, 0, {     0,      0}, {0x9d, 0xba, 0x22, 0xff}}},
    {{{     6,    -49,      0}, 0, {     0,      0}, {0xa9, 0xa7, 0xec, 0xff}}},
    {{{    88,    -34,     39}, 0, {     0,      0}, {0x38, 0xa1, 0x3d, 0xff}}},
    {{{    90,     -3,     52}, 0, {     0,      0}, {0x50, 0x07, 0x61, 0xff}}},
    {{{    45,    -29,     52}, 0, {     0,      0}, {0xeb, 0xb9, 0x66, 0xff}}},
    {{{    23,    -29,     36}, 0, {     0,      0}, {0x12, 0xec, 0x7b, 0xff}}},
    {{{    18,     30,     41}, 0, {     0,      0}, {0xc1, 0x2b, 0x64, 0xff}}},
    {{{    46,     49,     45}, 0, {     0,      0}, {0xf8, 0x44, 0x6a, 0xff}}},
    {{{     0,     21,     37}, 0, {     0,      0}, {0xfe, 0x44, 0x6a, 0xff}}},
    {{{    56,    -47,     -5}, 0, {     0,      0}, {0x0c, 0x87, 0xde, 0xff}}},
    {{{    56,     80,      1}, 0, {     0,      0}, {0x1a, 0x6a, 0x40, 0xff}}},
    {{{     8,     58,     -2}, 0, {     0,      0}, {0xbc, 0x55, 0x40, 0xff}}},
    {{{    84,     48,     24}, 0, {     0,      0}, {0x32, 0x3d, 0x62, 0xff}}},
};

// 0x0401B3D0
static const Vtx mario_right_hand_peace_shared_dl_vertex_group2[] = {
    {{{    33,      7,    -37}, 0, {     0,      0}, {0xde, 0xf5, 0x87, 0xff}}},
    {{{    56,    -47,     -5}, 0, {     0,      0}, {0x0c, 0x87, 0xde, 0x00}}},
    {{{    32,    -43,      0}, 0, {     0,      0}, {0xdd, 0xba, 0x9d, 0x00}}},
    {{{    45,    -29,     52}, 0, {     0,      0}, {0xeb, 0xb9, 0x66, 0xff}}},
    {{{    32,    -43,      0}, 0, {     0,      0}, {0xeb, 0x89, 0x24, 0xff}}},
    {{{    18,     30,     41}, 0, {     0,      0}, {0xc1, 0x2b, 0x64, 0xff}}},
    {{{     8,     58,     -2}, 0, {     0,      0}, {0xbc, 0x55, 0x40, 0xff}}},
    {{{    -4,     38,      0}, 0, {     0,      0}, {0xd3, 0x66, 0x3c, 0xff}}},
    {{{     8,     58,     -2}, 0, {     0,      0}, {0xd0, 0x30, 0x96, 0xff}}},
    {{{    -4,     38,      0}, 0, {     0,      0}, {0xb3, 0x18, 0x9f, 0xff}}},
    {{{    24,    -59,     -3}, 0, {     0,      0}, {0xc4, 0xb9, 0xab, 0xff}}},
    {{{     6,    -49,      0}, 0, {     0,      0}, {0xa9, 0xa7, 0xec, 0xff}}},
    {{{    -8,    -12,    -20}, 0, {     0,      0}, {0x93, 0xcf, 0xd7, 0xff}}},
    {{{     2,     -5,    -33}, 0, {     0,      0}, {0xad, 0xda, 0xa9, 0xff}}},
};

// 0x0401B4B0
static const Vtx mario_right_hand_peace_shared_dl_vertex_group3[] = {
    {{{   -22,     43,     -3}, 0, {     0,      0}, {0x31, 0x4d, 0xa8, 0xff}}},
    {{{    -4,     38,      0}, 0, {     0,      0}, {0x31, 0x4d, 0xa8, 0x00}}},
    {{{    14,     -1,    -22}, 0, {     0,      0}, {0x47, 0x20, 0x9d, 0x00}}},
    {{{     2,     -5,    -33}, 0, {     0,      0}, {0x47, 0x20, 0x9d, 0xff}}},
    {{{   -22,     43,     -3}, 0, {     0,      0}, {0x86, 0x10, 0xe4, 0xff}}},
    {{{   -25,     24,      0}, 0, {     0,      0}, {0x84, 0xf8, 0xea, 0xff}}},
    {{{   -21,      9,     33}, 0, {     0,      0}, {0x8a, 0xe1, 0x1f, 0xff}}},
    {{{   -15,     23,     43}, 0, {     0,      0}, {0xd6, 0x31, 0x6c, 0xff}}},
    {{{     0,    -35,     32}, 0, {     0,      0}, {0x9d, 0xba, 0x22, 0xff}}},
    {{{    15,    -39,     42}, 0, {     0,      0}, {0x0c, 0xbe, 0x6b, 0xff}}},
    {{{     6,    -49,      0}, 0, {     0,      0}, {0xa9, 0xa7, 0xec, 0xff}}},
    {{{    24,    -59,     -3}, 0, {     0,      0}, {0xce, 0x92, 0x24, 0xff}}},
    {{{     8,     58,     -2}, 0, {     0,      0}, {0xd0, 0x30, 0x96, 0xff}}},
    {{{    56,     80,      1}, 0, {     0,      0}, {0xfe, 0x41, 0x94, 0xff}}},
    {{{    33,      7,    -37}, 0, {     0,      0}, {0xde, 0xf5, 0x87, 0xff}}},
};

// 0x0401B5A0
static const Vtx mario_right_hand_peace_shared_dl_vertex_group4[] = {
    {{{   101,    -29,     15}, 0, {     0,      0}, {0x53, 0xa3, 0x15, 0xff}}},
    {{{    88,    -34,     39}, 0, {     0,      0}, {0x38, 0xa1, 0x3d, 0x00}}},
    {{{    56,    -47,     -5}, 0, {     0,      0}, {0x0c, 0x87, 0xde, 0x00}}},
    {{{    56,     80,      1}, 0, {     0,      0}, {0xfe, 0x41, 0x94, 0xff}}},
    {{{    84,     60,     -7}, 0, {     0,      0}, {0xfb, 0x44, 0x96, 0xff}}},
    {{{    89,     18,    -34}, 0, {     0,      0}, {0x0a, 0x1b, 0x85, 0xff}}},
    {{{   105,     17,      3}, 0, {     0,      0}, {0x0a, 0x7c, 0x14, 0xff}}},
    {{{    98,     10,     29}, 0, {     0,      0}, {0x0a, 0x7a, 0x21, 0xff}}},
    {{{   153,      7,     24}, 0, {     0,      0}, {0x0c, 0x7a, 0x1e, 0xff}}},
    {{{    46,     49,     45}, 0, {     0,      0}, {0xf8, 0x44, 0x6a, 0xff}}},
    {{{    84,     48,     24}, 0, {     0,      0}, {0x32, 0x3d, 0x62, 0xff}}},
    {{{    56,     80,      1}, 0, {     0,      0}, {0x1a, 0x6a, 0x40, 0xff}}},
    {{{    33,      7,    -37}, 0, {     0,      0}, {0xde, 0xf5, 0x87, 0xff}}},
    {{{    94,    -22,    -18}, 0, {     0,      0}, {0x07, 0xcd, 0x8d, 0xff}}},
    {{{    14,     -1,    -22}, 0, {     0,      0}, {0xbe, 0xe5, 0x98, 0xff}}},
    {{{    -4,     38,      0}, 0, {     0,      0}, {0xb3, 0x18, 0x9f, 0xff}}},
};

// 0x0401B6A0
static const Vtx mario_right_hand_peace_shared_dl_vertex_group5[] = {
    {{{    -4,     38,      0}, 0, {     0,      0}, {0xd3, 0x66, 0x3c, 0xff}}},
    {{{     0,     21,     37}, 0, {     0,      0}, {0xfe, 0x44, 0x6a, 0x00}}},
    {{{    18,     30,     41}, 0, {     0,      0}, {0xc1, 0x2b, 0x64, 0x00}}},
    {{{    45,    -29,     52}, 0, {     0,      0}, {0xeb, 0xb9, 0x66, 0xff}}},
    {{{    23,    -29,     36}, 0, {     0,      0}, {0xe3, 0x8b, 0x25, 0xff}}},
    {{{    32,    -43,      0}, 0, {     0,      0}, {0xeb, 0x89, 0x24, 0xff}}},
    {{{    32,    -43,      0}, 0, {     0,      0}, {0xdd, 0xba, 0x9d, 0xff}}},
    {{{    14,     -1,    -22}, 0, {     0,      0}, {0xbe, 0xe5, 0x98, 0xff}}},
    {{{    33,      7,    -37}, 0, {     0,      0}, {0xde, 0xf5, 0x87, 0xff}}},
    {{{   166,    -13,    -17}, 0, {     0,      0}, {0x7a, 0x08, 0x21, 0xff}}},
    {{{   162,     14,    -11}, 0, {     0,      0}, {0x79, 0x01, 0x25, 0xff}}},
    {{{   153,      7,     24}, 0, {     0,      0}, {0x7a, 0x08, 0x21, 0xff}}},
    {{{   155,    -15,     21}, 0, {     0,      0}, {0x79, 0x06, 0x23, 0xff}}},
    {{{    84,     60,     -7}, 0, {     0,      0}, {0xc2, 0x66, 0x28, 0xff}}},
    {{{    84,     48,     24}, 0, {     0,      0}, {0xc2, 0x66, 0x28, 0xff}}},
    {{{   126,     84,     -3}, 0, {     0,      0}, {0xc2, 0x66, 0x28, 0xff}}},
};

// 0x0401B7A0
static const Vtx mario_right_hand_peace_shared_dl_vertex_group6[] = {
    {{{   146,     59,     -8}, 0, {     0,      0}, {0x5d, 0x4d, 0x26, 0xff}}},
    {{{   126,     84,     -3}, 0, {     0,      0}, {0x5f, 0x47, 0x2a, 0x00}}},
    {{{   123,     72,     21}, 0, {     0,      0}, {0x5d, 0x4d, 0x26, 0x00}}},
    {{{   126,     84,     -3}, 0, {     0,      0}, {0xfd, 0x37, 0x8e, 0xff}}},
    {{{    89,     18,    -34}, 0, {     0,      0}, {0x0a, 0x1b, 0x85, 0xff}}},
    {{{    84,     60,     -7}, 0, {     0,      0}, {0xfb, 0x44, 0x96, 0xff}}},
    {{{   146,     59,     -8}, 0, {     0,      0}, {0x15, 0x2a, 0x8b, 0xff}}},
    {{{   162,     14,    -11}, 0, {     0,      0}, {0x0a, 0x7d, 0x0d, 0xff}}},
    {{{    89,     18,    -34}, 0, {     0,      0}, {0x06, 0x7e, 0xff, 0xff}}},
    {{{   105,     17,      3}, 0, {     0,      0}, {0x0a, 0x7c, 0x14, 0xff}}},
    {{{   162,     14,    -11}, 0, {     0,      0}, {0x26, 0x1e, 0x8b, 0xff}}},
    {{{   166,    -13,    -17}, 0, {     0,      0}, {0x18, 0xf9, 0x84, 0xff}}},
    {{{    56,     80,      1}, 0, {     0,      0}, {0x1a, 0x6a, 0x40, 0xff}}},
    {{{    84,     48,     24}, 0, {     0,      0}, {0x32, 0x3d, 0x62, 0xff}}},
    {{{    84,     60,     -7}, 0, {     0,      0}, {0x4d, 0x5d, 0x24, 0xff}}},
};

// 0x0401B890
static const Vtx mario_right_hand_peace_shared_dl_vertex_group7[] = {
    {{{   139,     53,     25}, 0, {     0,      0}, {0x5b, 0xa8, 0x02, 0xff}}},
    {{{   105,     17,      3}, 0, {     0,      0}, {0x59, 0xa6, 0xf7, 0x00}}},
    {{{   146,     59,     -8}, 0, {     0,      0}, {0x57, 0xa6, 0xf0, 0x00}}},
    {{{    33,      7,    -37}, 0, {     0,      0}, {0xde, 0xf5, 0x87, 0xff}}},
    {{{    89,     18,    -34}, 0, {     0,      0}, {0x0a, 0x1b, 0x85, 0xff}}},
    {{{    94,    -22,    -18}, 0, {     0,      0}, {0x07, 0xcd, 0x8d, 0xff}}},
    {{{    88,    -34,     39}, 0, {     0,      0}, {0x38, 0xa1, 0x3d, 0xff}}},
    {{{   101,    -29,     15}, 0, {     0,      0}, {0x53, 0xa3, 0x15, 0xff}}},
    {{{    90,     -3,     52}, 0, {     0,      0}, {0x50, 0x07, 0x61, 0xff}}},
    {{{    94,    -22,    -18}, 0, {     0,      0}, {0x1c, 0x85, 0x00, 0xff}}},
    {{{   155,    -15,     21}, 0, {     0,      0}, {0x19, 0x85, 0xf1, 0xff}}},
    {{{   166,    -13,    -17}, 0, {     0,      0}, {0x10, 0x83, 0x00, 0xff}}},
    {{{   101,    -29,     15}, 0, {     0,      0}, {0xff, 0xe2, 0x7b, 0xff}}},
    {{{   153,      7,     24}, 0, {     0,      0}, {0xff, 0xe2, 0x7b, 0xff}}},
    {{{    98,     10,     29}, 0, {     0,      0}, {0x07, 0xd6, 0x77, 0xff}}},
};

// 0x0401B980
static const Vtx mario_right_hand_peace_shared_dl_vertex_group8[] = {
    {{{   139,     53,     25}, 0, {     0,      0}, {0xfc, 0x13, 0x7d, 0xff}}},
    {{{   123,     72,     21}, 0, {     0,      0}, {0xfc, 0x15, 0x7d, 0x00}}},
    {{{    84,     48,     24}, 0, {     0,      0}, {0x32, 0x3d, 0x62, 0x00}}},
    {{{    98,     10,     29}, 0, {     0,      0}, {0x55, 0x16, 0x5b, 0xff}}},
    {{{   101,    -29,     15}, 0, {     0,      0}, {0xff, 0xe2, 0x7b, 0xff}}},
    {{{   155,    -15,     21}, 0, {     0,      0}, {0xf5, 0xef, 0x7d, 0xff}}},
    {{{   153,      7,     24}, 0, {     0,      0}, {0xff, 0xe2, 0x7b, 0xff}}},
    {{{    90,     -3,     52}, 0, {     0,      0}, {0x50, 0x07, 0x61, 0xff}}},
    {{{   101,    -29,     15}, 0, {     0,      0}, {0x53, 0xa3, 0x15, 0xff}}},
    {{{   146,     59,     -8}, 0, {     0,      0}, {0x57, 0xa6, 0xf0, 0xff}}},
    {{{   105,     17,      3}, 0, {     0,      0}, {0x59, 0xa6, 0xf7, 0xff}}},
    {{{    89,     18,    -34}, 0, {     0,      0}, {0x51, 0xa6, 0xdd, 0xff}}},
    {{{   166,    -13,    -17}, 0, {     0,      0}, {0x18, 0xf9, 0x84, 0xff}}},
    {{{    94,    -22,    -18}, 0, {     0,      0}, {0x07, 0xcd, 0x8d, 0xff}}},
    {{{    89,     18,    -34}, 0, {     0,      0}, {0x0a, 0x1b, 0x85, 0xff}}},
};

// 0x0401BA70
static const Vtx mario_right_hand_peace_shared_dl_vertex_group9[] = {
    {{{    84,     48,     24}, 0, {     0,      0}, {0xc2, 0x66, 0x28, 0xff}}},
    {{{   123,     72,     21}, 0, {     0,      0}, {0xc3, 0x66, 0x29, 0x00}}},
    {{{   126,     84,     -3}, 0, {     0,      0}, {0xc2, 0x66, 0x28, 0x00}}},
    {{{   139,     53,     25}, 0, {     0,      0}, {0x5b, 0xa8, 0x02, 0xff}}},
    {{{    98,     10,     29}, 0, {     0,      0}, {0x5b, 0xa8, 0x02, 0xff}}},
    {{{   105,     17,      3}, 0, {     0,      0}, {0x59, 0xa6, 0xf7, 0xff}}},
    {{{   105,     17,      3}, 0, {     0,      0}, {0x0a, 0x7c, 0x14, 0xff}}},
    {{{   153,      7,     24}, 0, {     0,      0}, {0x0c, 0x7a, 0x1e, 0xff}}},
    {{{   162,     14,    -11}, 0, {     0,      0}, {0x0a, 0x7d, 0x0d, 0xff}}},
    {{{   123,     72,     21}, 0, {     0,      0}, {0x5d, 0x4d, 0x26, 0xff}}},
    {{{   139,     53,     25}, 0, {     0,      0}, {0x5a, 0x52, 0x21, 0xff}}},
    {{{   146,     59,     -8}, 0, {     0,      0}, {0x5d, 0x4d, 0x26, 0xff}}},
    {{{    24,    -59,     -3}, 0, {     0,      0}, {0x68, 0xc7, 0x2c, 0xff}}},
    {{{    32,    -43,      0}, 0, {     0,      0}, {0x68, 0xc7, 0x2c, 0xff}}},
    {{{    23,    -29,     36}, 0, {     0,      0}, {0x12, 0xec, 0x7b, 0xff}}},
    {{{    15,    -39,     42}, 0, {     0,      0}, {0x0c, 0xbe, 0x6b, 0xff}}},
};

// 0x0401BB70
static const Vtx mario_right_hand_peace_shared_dl_vertex_group10[] = {
    {{{     2,     -5,    -33}, 0, {     0,      0}, {0x47, 0x20, 0x9d, 0xff}}},
    {{{    14,     -1,    -22}, 0, {     0,      0}, {0x47, 0x20, 0x9d, 0x00}}},
    {{{    32,    -43,      0}, 0, {     0,      0}, {0x52, 0xee, 0xa2, 0x00}}},
    {{{    24,    -59,     -3}, 0, {     0,      0}, {0x52, 0xee, 0xa2, 0xff}}},
    {{{     2,     -5,    -33}, 0, {     0,      0}, {0xad, 0xda, 0xa9, 0xff}}},
    {{{    -8,    -12,    -20}, 0, {     0,      0}, {0x93, 0xcf, 0xd7, 0xff}}},
    {{{   -25,     24,      0}, 0, {     0,      0}, {0x84, 0xf8, 0xea, 0xff}}},
    {{{   -22,     43,     -3}, 0, {     0,      0}, {0x86, 0x10, 0xe4, 0xff}}},
    {{{   -15,     23,     43}, 0, {     0,      0}, {0xd6, 0x31, 0x6c, 0xff}}},
    {{{     0,     21,     37}, 0, {     0,      0}, {0xfe, 0x44, 0x6a, 0xff}}},
    {{{    -4,     38,      0}, 0, {     0,      0}, {0xd3, 0x66, 0x3c, 0xff}}},
    {{{   -22,     43,     -3}, 0, {     0,      0}, {0x1a, 0x72, 0x2f, 0xff}}},
    {{{    15,    -39,     42}, 0, {     0,      0}, {0x0c, 0xbe, 0x6b, 0xff}}},
    {{{    23,    -29,     36}, 0, {     0,      0}, {0x12, 0xec, 0x7b, 0xff}}},
};

// 0x0401BC50
static const Vtx mario_right_hand_peace_shared_dl_vertex_group11[] = {
    {{{   101,    -29,     15}, 0, {     0,      0}, {0x53, 0xa3, 0x15, 0xff}}},
    {{{    56,    -47,     -5}, 0, {     0,      0}, {0x0c, 0x87, 0xde, 0x00}}},
    {{{    94,    -22,    -18}, 0, {     0,      0}, {0x1c, 0x85, 0x00, 0x00}}},
};

// 0x0401BC80 - 0x0401BF30
const Gfx mario_right_hand_peace_shared_dl[] = {
    gsSPVertex(mario_right_hand_peace_shared_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 2,  4,  3, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 8,  7,  9, 0x0,  7,  6, 10, 0x0),
    gsSP2Triangles( 7, 10,  9, 0x0, 11,  8,  9, 0x0),
    gsSP2Triangles( 7, 12,  5, 0x0, 10, 13, 14, 0x0),
    gsSP2Triangles( 6, 15, 10, 0x0, 10, 14,  9, 0x0),
    gsSPVertex(mario_right_hand_peace_shared_dl_vertex_group2, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  8,  0,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSPVertex(mario_right_hand_peace_shared_dl_vertex_group3, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 7,  6,  8, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9,  8, 10, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(mario_right_hand_peace_shared_dl_vertex_group4, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 2, 12, 13, 0x0,  3,  5, 12, 0x0),
    gsSP1Triangle(12, 14, 15, 0x0),
    gsSPVertex(mario_right_hand_peace_shared_dl_vertex_group5, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 9, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(mario_right_hand_peace_shared_dl_vertex_group6, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 4, 10, 11, 0x0, 12, 13, 14, 0x0),
    gsSPVertex(mario_right_hand_peace_shared_dl_vertex_group7, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10,  7, 0x0),
    gsSP2Triangles(10,  9, 11, 0x0, 12, 13, 14, 0x0),
    gsSPVertex(mario_right_hand_peace_shared_dl_vertex_group8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  3,  2,  7, 0x0),
    gsSP2Triangles( 8,  3,  7, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(mario_right_hand_peace_shared_dl_vertex_group9, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(mario_right_hand_peace_shared_dl_vertex_group10, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(12, 13,  9, 0x0, 12,  9,  8, 0x0),
    gsSPVertex(mario_right_hand_peace_shared_dl_vertex_group11, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401BF30 - 0x0401BF50
const Gfx mario_right_hand_peace[] = {
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_right_hand_peace_shared_dl),
    gsSPEndDisplayList(),
};

// 0x0401BF50
static const Vtx mario_cap_unused_m_logo_dl_vertex[] = {
    {{{    33,     35,    118}, 0, {   728,    758}, {0x30, 0x26, 0x6e, 0xff}}},
    {{{    96,     22,     45}, 0, {  1240,    876}, {0x48, 0xa4, 0x31, 0xff}}},
    {{{    71,    101,    113}, 0, {  1028,    148}, {0x55, 0x16, 0x5b, 0xff}}},
    {{{     0,    110,    143}, 0, {   460,     68}, {0x00, 0x34, 0x73, 0xff}}},
    {{{   -31,     35,    118}, 0, {   206,    762}, {0xd0, 0x26, 0x6f, 0xff}}},
    {{{   -70,    101,    113}, 0, {  -106,    158}, {0xab, 0x16, 0x5b, 0xff}}},
    {{{   -95,     22,     46}, 0, {  -302,    890}, {0xa8, 0xb9, 0x38, 0xff}}},
};

// 0x0401BFC0
static const Vtx mario_cap_unused_base_top_dl_vertex_group1[] = {
    {{{   -66,      2,    139}, 0, {     0,      0}, {0xb0, 0xbb, 0x45, 0xff}}},
    {{{     0,      0,    163}, 0, {     0,      0}, {0x00, 0xba, 0x69, 0xff}}},
    {{{   -31,     35,    118}, 0, {     0,      0}, {0xd0, 0x26, 0x6f, 0xff}}},
    {{{   -32,     17,    109}, 0, {     0,      0}, {0x00, 0x83, 0xf0, 0xff}}},
    {{{    33,     17,    109}, 0, {     0,      0}, {0xfb, 0x84, 0xea, 0xff}}},
    {{{   -95,     22,     46}, 0, {     0,      0}, {0xa8, 0xb9, 0x38, 0xff}}},
    {{{  -101,     10,     -7}, 0, {     0,      0}, {0xd8, 0x89, 0x11, 0xff}}},
    {{{   -70,    101,    113}, 0, {     0,      0}, {0xab, 0x16, 0x5b, 0xff}}},
    {{{  -135,     70,     23}, 0, {     0,      0}, {0x84, 0x15, 0x10, 0xff}}},
    {{{  -125,     38,    -45}, 0, {     0,      0}, {0x8d, 0xec, 0xd1, 0xff}}},
    {{{   -86,      1,    -60}, 0, {     0,      0}, {0xce, 0x8c, 0xf6, 0xff}}},
    {{{   -41,    144,     64}, 0, {     0,      0}, {0xdc, 0x79, 0x00, 0xff}}},
    {{{   -76,     84,    -60}, 0, {     0,      0}, {0xd5, 0x6e, 0xd3, 0xff}}},
    {{{   136,     70,     22}, 0, {     0,      0}, {0x7b, 0x16, 0x10, 0xff}}},
    {{{    71,    101,    113}, 0, {     0,      0}, {0x55, 0x16, 0x5b, 0xff}}},
    {{{    96,     22,     45}, 0, {     0,      0}, {0x48, 0xa4, 0x31, 0xff}}},
};

// 0x0401C0C0
static const Vtx mario_cap_unused_base_top_dl_vertex_group2[] = {
    {{{    42,    144,     64}, 0, {     0,      0}, {0x2b, 0x76, 0x0d, 0xff}}},
    {{{   136,     70,     22}, 0, {     0,      0}, {0x7b, 0x16, 0x10, 0xff}}},
    {{{    76,     84,    -60}, 0, {     0,      0}, {0x2a, 0x6c, 0xcf, 0xff}}},
    {{{   103,     10,     -6}, 0, {     0,      0}, {0x42, 0x96, 0x12, 0xff}}},
    {{{   126,     38,    -46}, 0, {     0,      0}, {0x73, 0xec, 0xd0, 0xff}}},
    {{{    71,    101,    113}, 0, {     0,      0}, {0x55, 0x16, 0x5b, 0xff}}},
    {{{    96,     22,     45}, 0, {     0,      0}, {0x48, 0xa4, 0x31, 0xff}}},
    {{{    67,      2,    139}, 0, {     0,      0}, {0x50, 0xba, 0x44, 0xff}}},
    {{{    33,     17,    109}, 0, {     0,      0}, {0xfb, 0x84, 0xea, 0xff}}},
    {{{    33,     35,    118}, 0, {     0,      0}, {0x30, 0x26, 0x6e, 0xff}}},
    {{{    86,      1,    -60}, 0, {     0,      0}, {0x20, 0x86, 0xfe, 0xff}}},
    {{{     0,      0,    163}, 0, {     0,      0}, {0x00, 0xba, 0x69, 0xff}}},
    {{{   -31,     35,    118}, 0, {     0,      0}, {0xd0, 0x26, 0x6f, 0xff}}},
    {{{    53,      0,   -118}, 0, {     0,      0}, {0x2c, 0xb5, 0xa5, 0xff}}},
    {{{    49,     62,   -139}, 0, {     0,      0}, {0x32, 0x49, 0xa6, 0xff}}},
};

// 0x0401C1B0
static const Vtx mario_cap_unused_base_top_dl_vertex_group3[] = {
    {{{   -76,     84,    -60}, 0, {     0,      0}, {0xd5, 0x6e, 0xd3, 0xff}}},
    {{{   -41,    144,     64}, 0, {     0,      0}, {0xdc, 0x79, 0x00, 0xff}}},
    {{{    76,     84,    -60}, 0, {     0,      0}, {0x2a, 0x6c, 0xcf, 0xff}}},
    {{{     0,    110,    143}, 0, {     0,      0}, {0x00, 0x34, 0x73, 0xff}}},
    {{{    42,    144,     64}, 0, {     0,      0}, {0x2b, 0x76, 0x0d, 0xff}}},
    {{{   -70,    101,    113}, 0, {     0,      0}, {0xab, 0x16, 0x5b, 0xff}}},
    {{{    71,    101,    113}, 0, {     0,      0}, {0x55, 0x16, 0x5b, 0xff}}},
    {{{    49,     62,   -139}, 0, {     0,      0}, {0x32, 0x49, 0xa6, 0xff}}},
    {{{   126,     38,    -46}, 0, {     0,      0}, {0x73, 0xec, 0xd0, 0xff}}},
    {{{   -52,      0,   -118}, 0, {     0,      0}, {0xd2, 0x9d, 0xc1, 0xff}}},
    {{{   -49,     62,   -138}, 0, {     0,      0}, {0xce, 0x1a, 0x8f, 0xff}}},
    {{{    53,      0,   -118}, 0, {     0,      0}, {0x2c, 0xb5, 0xa5, 0xff}}},
    {{{  -125,     38,    -45}, 0, {     0,      0}, {0x8d, 0xec, 0xd1, 0xff}}},
    {{{    86,      1,    -60}, 0, {     0,      0}, {0x20, 0x86, 0xfe, 0xff}}},
    {{{   -86,      1,    -60}, 0, {     0,      0}, {0xce, 0x8c, 0xf6, 0xff}}},
};

// 0x0401C2A0
static const Vtx mario_cap_unused_base_bottom_dl_vertex[] = {
    {{{    86,      1,    -60}, 0, {     0,      0}, {0x20, 0x86, 0xfe, 0xff}}},
    {{{   -86,      1,    -60}, 0, {     0,      0}, {0xce, 0x8c, 0xf6, 0xff}}},
    {{{   -52,      0,   -118}, 0, {     0,      0}, {0xd2, 0x9d, 0xc1, 0xff}}},
    {{{    33,     17,    109}, 0, {     0,      0}, {0xfb, 0x84, 0xea, 0xff}}},
    {{{   -32,     17,    109}, 0, {     0,      0}, {0x00, 0x83, 0xf0, 0xff}}},
    {{{  -101,     10,     -7}, 0, {     0,      0}, {0xd8, 0x89, 0x11, 0xff}}},
    {{{    96,     22,     45}, 0, {     0,      0}, {0x48, 0xa4, 0x31, 0xff}}},
    {{{   103,     10,     -6}, 0, {     0,      0}, {0x42, 0x96, 0x12, 0xff}}},
    {{{    53,      0,   -118}, 0, {     0,      0}, {0x2c, 0xb5, 0xa5, 0xff}}},
};

// 0x0401C330 - 0x0401C368
const Gfx mario_cap_unused_m_logo_dl[] = {
    gsSPVertex(mario_cap_unused_m_logo_dl_vertex, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 3,  5,  4, 0x0,  2,  3,  0, 0x0),
    gsSP1Triangle( 5,  6,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401C368 - 0x0401C4C8
const Gfx mario_cap_unused_base_top_dl[] = {
    gsSPVertex(mario_cap_unused_base_top_dl_vertex_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 3,  1,  0, 0x0,  3,  5,  6, 0x0),
    gsSP2Triangles( 3,  0,  5, 0x0,  5,  7,  8, 0x0),
    gsSP2Triangles( 5,  8,  6, 0x0,  0,  2,  5, 0x0),
    gsSP2Triangles( 6,  9, 10, 0x0,  8,  9,  6, 0x0),
    gsSP2Triangles(11,  8,  7, 0x0,  8, 12,  9, 0x0),
    gsSP2Triangles(12,  8, 11, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(mario_cap_unused_base_top_dl_vertex_group2, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 4,  2,  1, 0x0,  5,  1,  0, 0x0),
    gsSP2Triangles( 1,  6,  3, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 9,  7,  6, 0x0, 10,  4,  3, 0x0),
    gsSP2Triangles( 9, 11,  7, 0x0,  7, 11,  8, 0x0),
    gsSP2Triangles(12, 11,  9, 0x0, 13, 14,  4, 0x0),
    gsSPVertex(mario_cap_unused_base_top_dl_vertex_group3, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 5,  3,  1, 0x0,  1,  4,  2, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  0,  2,  7, 0x0),
    gsSP2Triangles( 8,  7,  2, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12, 10,  9, 0x0,  7, 10,  0, 0x0),
    gsSP2Triangles(10,  7, 11, 0x0,  0, 10, 12, 0x0),
    gsSP2Triangles(11,  8, 13, 0x0, 14, 12,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401C4C8 - 0x0401C510
const Gfx mario_cap_unused_base_bottom_dl[] = {
    gsSPVertex(mario_cap_unused_base_bottom_dl_vertex, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  6,  5, 0x0),
    gsSP2Triangles( 0,  7,  5, 0x0,  0,  5,  1, 0x0),
    gsSP1Triangle( 2,  8,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401C510 - 0x0401C538
const Gfx mario_cap_unused_base_dl[] = {
    gsSPDisplayList(mario_cap_unused_base_top_dl),
    gsSPLight(&mario_brown2_lights_group.l, 1),
    gsSPLight(&mario_brown2_lights_group.a, 2),
    gsSPDisplayList(mario_cap_unused_base_bottom_dl),
    gsSPEndDisplayList(),
};

// 0x0401C538
static const Vtx mario_wings_half_1_unused_dl_vertex[] = {
    {{{   199,    247,    -55}, 0, {   990,      0}, {0x59, 0xed, 0x58, 0xff}}},
    {{{   131,    274,     20}, 0, {     0,      0}, {0x59, 0xed, 0x58, 0xff}}},
    {{{    69,     71,     38}, 0, {     0,   2012}, {0x59, 0xed, 0x58, 0xff}}},
    {{{   199,    247,    -55}, 0, {   990,      0}, {0x58, 0xee, 0x58, 0xff}}},
    {{{    69,     71,     38}, 0, {     0,   2012}, {0x58, 0xee, 0x58, 0xff}}},
    {{{   138,     44,    -37}, 0, {   990,   2012}, {0x58, 0xee, 0x58, 0xff}}},
    {{{  -137,     44,    -37}, 0, {   990,   2012}, {0xa8, 0xee, 0x58, 0xff}}},
    {{{   -68,     71,     38}, 0, {     0,   2012}, {0xa8, 0xee, 0x58, 0xff}}},
    {{{  -198,    247,    -55}, 0, {   990,      0}, {0xa8, 0xee, 0x58, 0xff}}},
    {{{   -68,     71,     38}, 0, {     0,   2012}, {0xa7, 0xed, 0x58, 0xff}}},
    {{{  -130,    274,     20}, 0, {     0,      0}, {0xa7, 0xed, 0x58, 0xff}}},
    {{{  -198,    247,    -55}, 0, {   990,      0}, {0xa7, 0xed, 0x58, 0xff}}},
};

// 0x0401C5F8
static const Vtx mario_wings_half_2_unused_dl_vertex[] = {
    {{{   268,    219,   -132}, 0, {   990,      0}, {0x59, 0xed, 0x57, 0xff}}},
    {{{   199,    247,    -55}, 0, {     0,      0}, {0x59, 0xed, 0x57, 0xff}}},
    {{{   138,     44,    -37}, 0, {     0,   2012}, {0x59, 0xed, 0x57, 0xff}}},
    {{{   207,     16,   -114}, 0, {   990,   2012}, {0x59, 0xed, 0x57, 0xff}}},
    {{{  -206,     16,   -114}, 0, {   990,   2012}, {0xa7, 0xed, 0x57, 0xff}}},
    {{{  -137,     44,    -37}, 0, {     0,   2012}, {0xa7, 0xed, 0x57, 0xff}}},
    {{{  -267,    219,   -132}, 0, {   990,      0}, {0xa7, 0xed, 0x57, 0xff}}},
    {{{  -198,    247,    -55}, 0, {     0,      0}, {0xa7, 0xed, 0x57, 0xff}}},
};

// 0x0401C678 - 0x0401C6A8
const Gfx mario_wings_half_1_unused_dl[] = {
    gsSPVertex(mario_wings_half_1_unused_dl_vertex, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401C6A8 - 0x0401C6D8
const Gfx mario_wings_half_2_unused_dl[] = {
    gsSPVertex(mario_wings_half_2_unused_dl_vertex, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401C6D8 - 0x0401C730
const Gfx mario_cap_wings_unused_intial_dl[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPEndDisplayList(),
};

// 0x0401C730 - 0x0401C758
const Gfx mario_cap_wings_unused_end_dl[] = {
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0401C758 - 0x0401C7E8 # Unreferenced, possibly a early duplicate of the cap object used in segment 03
const Gfx mario_cap_unused_dl[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBA, G_CC_BLENDRGBA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_m_logo),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_red_lights_group.l, 1),
    gsSPLight(&mario_red_lights_group.a, 2),
    gsSPDisplayList(mario_cap_unused_m_logo_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(mario_cap_unused_base_dl),
    gsSPEndDisplayList(),
};

// 0x0401C7E8 - 0x0401C890 # Unreferenced, possibly an early duplicate of the cap object used in segment 03
const Gfx mario_metal_cap_unused_dl[] = {
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPLoadTextureBlock(mario_texture_metal, G_IM_FMT_RGBA, G_IM_SIZ_16b, 64, 32, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_WRAP | G_TX_NOMIRROR, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_ON),
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsSPDisplayList(mario_cap_unused_m_logo_dl),
    gsSPDisplayList(mario_cap_unused_base_top_dl),
    gsSPDisplayList(mario_cap_unused_base_bottom_dl),
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_OFF),
    gsSPEndDisplayList(),
};

// 0x0401C890 - 0x0401C8E8 # Unreferenced, unused like the duplicated cap, same case
const Gfx mario_cap_wings_unused[] = {
    gsSPDisplayList(mario_cap_wings_unused_intial_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_wings_half_1),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_wings_half_1_unused_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_wings_half_2),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_wings_half_2_unused_dl),
    gsSPDisplayList(mario_cap_wings_unused_end_dl),
    gsSPEndDisplayList(),
};

// 0x0401C8E8 - 0x0401C940 # Unreferenced, unused like the duplicated cap, same case
const Gfx mario_metal_cap_wings_unused[] = {
    gsSPDisplayList(mario_cap_wings_unused_intial_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_metal_wings_half_1),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_wings_half_1_unused_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_metal_wings_half_2),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_wings_half_2_unused_dl),
    gsSPDisplayList(mario_cap_wings_unused_end_dl),
    gsSPEndDisplayList(),
};

// 0x0401C940
static const Vtx mario_wings_half_1_dl_vertex[] = {
    {{{  -105,    212,      0}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -105,      0,      0}, 0, {     0,   2012}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,      0,      0}, 0, {   990,   2012}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,    212,      0}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x0401C980
static const Vtx mario_wings_half_2_dl_vertex[] = {
    {{{     0,      0,      0}, 0, {     0,   2012}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   106,    212,      0}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,    212,      0}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   106,      0,      0}, 0, {   990,   2012}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x0401C9C0 - 0x0401C9E0
const Gfx mario_wings_half_1_dl[] = {
    gsSPVertex(mario_wings_half_1_dl_vertex, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401C9E0 - 0x0401CA00
const Gfx mario_wings_half_2_dl[] = {
    gsSPVertex(mario_wings_half_2_dl_vertex, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0401CA00 - 0x0401CAB8
const Gfx mario_cap_wings[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_wings_half_1),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_wings_half_1_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_wings_half_2),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_wings_half_2_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0401CAB8 - 0x0401CB70
const Gfx mario_cap_wings_transparent[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALFADEA, G_CC_DECALFADEA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_wings_half_1),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_wings_half_1_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_wings_half_2),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_wings_half_2_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0401CB70 - 0x0401CC28
const Gfx mario_metal_cap_wings[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPLight(&mario_white_lights_group.l, 1),
    gsSPLight(&mario_white_lights_group.a, 2),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_metal_wings_half_1),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_wings_half_1_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_metal_wings_half_2),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_wings_half_2_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0401CC28 - 0x0401CD20
const Gfx mario_metal_cap_wings_transparent[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_TEXTURE_GEN),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetCombineMode(G_CC_DECALFADEA, G_CC_DECALFADEA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_metal_wings_half_1),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_wings_half_1_dl),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_texture_metal_wings_half_2),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_wings_half_2_dl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_TEXTURE_GEN | G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetCombineMode(G_CC_DECALFADE, G_CC_DECALFADE),
    gsDPLoadTextureBlock(mario_texture_metal, G_IM_FMT_RGBA, G_IM_SIZ_16b, 64, 32, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_WRAP | G_TX_NOMIRROR, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_ON),
    gsSPEndDisplayList(),
};
