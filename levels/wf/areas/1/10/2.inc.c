// 0x07008838 - 0x07008850
static const Lights1 wf_seg7_lights_07008838 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07008850 - 0x07008950
static const Vtx wf_seg7_vertex_07008850[] = {
    {{{   128,   1792,    128}, 0, {  1244,   1000}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   128,   2048,    128}, 0, {  1244,    -20}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -127,   2048,    128}, 0, {   222,    -20}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -127,   1792,   -127}, 0, {  1236,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   128,   2048,   -127}, 0, {   214,    -30}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   128,   1792,   -127}, 0, {   214,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -127,   2048,   -127}, 0, {  1236,    -30}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -127,   1792,    128}, 0, {  1226,    988}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -127,   2048,    128}, 0, {  1226,    -34}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -127,   2048,   -127}, 0, {   204,    -34}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -127,   1792,   -127}, 0, {   204,    988}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   128,   1792,   -127}, 0, {  1244,    996}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   128,   2048,   -127}, 0, {  1244,    -24}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   128,   2048,    128}, 0, {   222,    -24}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   128,   1792,    128}, 0, {   222,    996}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -127,   1792,    128}, 0, {   222,   1000}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x07008950 - 0x070089F0
static const Vtx wf_seg7_vertex_07008950[] = {
    {{{   256,   2048,    256}, 0, {  4056,   4054}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -255,   2048,    256}, 0, {  4056,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -255,   2048,   -255}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   256,   2048,   -255}, 0, {     0,   4054}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,   1792,   -537}, 0, {  -802,  -1362}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   466,   1792,    269}, 0, {  1056,   1858}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   466,   1792,   -268}, 0, {  1056,   -288}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -465,   1792,   -268}, 0, { -2662,   -288}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -465,   1792,    269}, 0, { -2662,   1858}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,   1792,    538}, 0, {  -802,   2932}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x070089F0 - 0x07008AF0
static const Vtx wf_seg7_vertex_070089F0[] = {
    {{{   466,   1536,   -268}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   466,   1792,   -268}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   466,   1792,    269}, 0, {   -82,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -465,   1536,    269}, 0, {  1042,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -465,   1792,   -268}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -465,   1536,   -268}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -465,   1792,    269}, 0, {  1042,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{     0,   1536,    538}, 0, {  1042,    990}, {0xc1, 0x00, 0x6d, 0xff}}},
    {{{  -465,   1792,    269}, 0, {     0,      0}, {0xc1, 0x00, 0x6d, 0xff}}},
    {{{  -465,   1536,    269}, 0, {     0,    990}, {0xc1, 0x00, 0x6d, 0xff}}},
    {{{     0,   1792,    538}, 0, {  1042,      0}, {0xc1, 0x00, 0x6d, 0xff}}},
    {{{   466,   1536,    269}, 0, {   990,    990}, {0x3f, 0x00, 0x6d, 0xff}}},
    {{{     0,   1792,    538}, 0, {   -82,      0}, {0x3f, 0x00, 0x6d, 0xff}}},
    {{{     0,   1536,    538}, 0, {   -82,    990}, {0x3f, 0x00, 0x6d, 0xff}}},
    {{{   466,   1792,    269}, 0, {   990,      0}, {0x3f, 0x00, 0x6d, 0xff}}},
    {{{   466,   1536,    269}, 0, {   -82,    990}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x07008AF0 - 0x07008BD0
static const Vtx wf_seg7_vertex_07008AF0[] = {
    {{{     0,   1536,   -537}, 0, {   990,    990}, {0x3f, 0x00, 0x93, 0xff}}},
    {{{   466,   1792,   -268}, 0, {   -82,      0}, {0x3f, 0x00, 0x93, 0xff}}},
    {{{   466,   1536,   -268}, 0, {   -82,    990}, {0x3f, 0x00, 0x93, 0xff}}},
    {{{     0,   1792,   -537}, 0, {   990,      0}, {0x3f, 0x00, 0x93, 0xff}}},
    {{{  -465,   1536,   -268}, 0, {  1042,    990}, {0xc1, 0x00, 0x93, 0xff}}},
    {{{     0,   1792,   -537}, 0, {     0,      0}, {0xc1, 0x00, 0x93, 0xff}}},
    {{{     0,   1536,   -537}, 0, {     0,    990}, {0xc1, 0x00, 0x93, 0xff}}},
    {{{  -465,   1792,   -268}, 0, {  1042,      0}, {0xc1, 0x00, 0x93, 0xff}}},
    {{{   256,   2048,   -255}, 0, { -1052,    990}, {0x00, 0x38, 0x8f, 0xff}}},
    {{{  -255,   2048,   -255}, 0, {  3034,    990}, {0x00, 0x38, 0x8f, 0xff}}},
    {{{     0,   2560,      0}, 0, {   990,  -3580}, {0x00, 0x38, 0x8f, 0xff}}},
    {{{  -255,   2048,    256}, 0, { -2414,    982}, {0x00, 0x38, 0x71, 0xff}}},
    {{{   256,   2048,    256}, 0, {  1672,    982}, {0x00, 0x38, 0x71, 0xff}}},
    {{{     0,   2560,      0}, 0, {  -370,  -3588}, {0x00, 0x38, 0x71, 0xff}}},
};

// 0x07008BD0 - 0x07008C30
static const Vtx wf_seg7_vertex_07008BD0[] = {
    {{{  -255,   2048,   -255}, 0, {     0,    990}, {0x8f, 0x38, 0x00, 0xff}}},
    {{{  -255,   2048,    256}, 0, {  4056,    990}, {0x8f, 0x38, 0x00, 0xff}}},
    {{{     0,   2560,      0}, 0, {  2012,  -3580}, {0x8f, 0x38, 0x00, 0xff}}},
    {{{   256,   2048,    256}, 0, {     0,    990}, {0x71, 0x38, 0x00, 0xff}}},
    {{{   256,   2048,   -255}, 0, {  4056,    990}, {0x71, 0x38, 0x00, 0xff}}},
    {{{     0,   2560,      0}, 0, {  2012,  -3580}, {0x71, 0x38, 0x00, 0xff}}},
};

// 0x07008C30 - 0x07008D20
static const Vtx wf_seg7_vertex_07008C30[] = {
    {{{   466,      0,   -268}, 0, {  1246,   7120}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   466,   1536,    269}, 0, {   -94,   -544}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   466,      0,    269}, 0, {   -94,   7120}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -465,      0,    269}, 0, {  1246,   7120}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -465,   1536,    269}, 0, {  1246,   -542}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -465,   1536,   -268}, 0, {   -94,   -544}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -465,      0,   -268}, 0, {   -94,   7120}, {0x81, 0x00, 0x00, 0xff}}},
    {{{     0,      0,    538}, 0, {  1246,   7120}, {0xc1, 0x00, 0x6d, 0xff}}},
    {{{     0,   1536,    538}, 0, {  1246,   -542}, {0xc1, 0x00, 0x6d, 0xff}}},
    {{{  -465,   1536,    269}, 0, {   -94,   -542}, {0xc1, 0x00, 0x6d, 0xff}}},
    {{{  -465,      0,    269}, 0, {   -94,   7120}, {0xc1, 0x00, 0x6d, 0xff}}},
    {{{   466,      0,    269}, 0, {  1246,   7120}, {0x3f, 0x00, 0x6d, 0xff}}},
    {{{   466,   1536,    269}, 0, {  1246,   -544}, {0x3f, 0x00, 0x6d, 0xff}}},
    {{{     0,   1536,    538}, 0, {   -94,   -544}, {0x3f, 0x00, 0x6d, 0xff}}},
    {{{     0,      0,    538}, 0, {   -94,   7120}, {0x3f, 0x00, 0x6d, 0xff}}},
};

// 0x07008D20 - 0x07008DD0
static const Vtx wf_seg7_vertex_07008D20[] = {
    {{{  -465,      0,   -268}, 0, {  1246,   7120}, {0xc1, 0x00, 0x93, 0xff}}},
    {{{  -465,   1536,   -268}, 0, {  1246,   -544}, {0xc1, 0x00, 0x93, 0xff}}},
    {{{     0,   1536,   -537}, 0, {   -94,   -544}, {0xc1, 0x00, 0x93, 0xff}}},
    {{{   466,      0,   -268}, 0, {  1246,   7120}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   466,   1536,   -268}, 0, {  1246,   -544}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   466,   1536,    269}, 0, {   -94,   -544}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{     0,      0,   -537}, 0, {  1246,   7120}, {0x3f, 0x00, 0x93, 0xff}}},
    {{{     0,   1536,   -537}, 0, {  1246,   -542}, {0x3f, 0x00, 0x93, 0xff}}},
    {{{   466,   1536,   -268}, 0, {   -94,   -544}, {0x3f, 0x00, 0x93, 0xff}}},
    {{{   466,      0,   -268}, 0, {   -94,   7120}, {0x3f, 0x00, 0x93, 0xff}}},
    {{{     0,      0,   -537}, 0, {   -94,   7120}, {0xc1, 0x00, 0x93, 0xff}}},
};

// 0x07008DD0 - 0x07008E48
static const Gfx wf_seg7_dl_07008DD0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, wf_seg7_texture_07001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_07008838.l, 1),
    gsSPLight(&wf_seg7_lights_07008838.a, 2),
    gsSPVertex(wf_seg7_vertex_07008850, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0,  2, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x07008E48 - 0x07008EA0
static const Gfx wf_seg7_dl_07008E48[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09007800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(wf_seg7_vertex_07008950, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  8, 0x0),
    gsSP2Triangles( 4,  8,  9, 0x0,  4,  9,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07008EA0 - 0x07008F58
static const Gfx wf_seg7_dl_07008EA0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09009000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(wf_seg7_vertex_070089F0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0,  2, 15, 0x0),
    gsSPVertex(wf_seg7_vertex_07008AF0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSPVertex(wf_seg7_vertex_07008BD0, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07008F58 - 0x07008FE8
static const Gfx wf_seg7_dl_07008F58[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(wf_seg7_vertex_07008C30, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(wf_seg7_vertex_07008D20, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP1Triangle( 0,  2, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x07008FE8 - 0x07009070
const Gfx wf_seg7_dl_07008FE8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_07008DD0),
    gsSPDisplayList(wf_seg7_dl_07008E48),
    gsSPDisplayList(wf_seg7_dl_07008EA0),
    gsSPDisplayList(wf_seg7_dl_07008F58),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
