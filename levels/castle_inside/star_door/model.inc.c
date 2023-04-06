// 0x0703BD28 - 0x0703BD40
static const Lights1 inside_castle_seg7_lights_0703BD28 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0703BD40 - 0x0703BE40
static const Vtx inside_castle_seg7_vertex_0703BD40[] = {
    {{{   -76,      0,    -15}, 0, {   478,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -76,      0,     15}, 0, {   992,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -76,    256,     15}, 0, {   992,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -76,    256,    -15}, 0, {  1100,    460}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    79,    256,     15}, 0, {  -650,    240}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    79,    256,    -15}, 0, {  -542,    734}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -76,    256,     15}, 0, {   990,    -34}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    79,      0,    -15}, 0, {  1234,   -116}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -76,      0,     15}, 0, {  -174,    868}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -76,      0,    -15}, 0, {  -604,    498}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    79,      0,     15}, 0, {  1664,    254}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    79,      0,     15}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    79,      0,    -15}, 0, {   584,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    79,    256,    -15}, 0, {   584,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    79,    256,     15}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   -76,    256,    -15}, 0, {   480,      0}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x0703BE40 - 0x0703BE80
static const Vtx inside_castle_seg7_vertex_0703BE40[] = {
    {{{   -76,      0,     15}, 0, {     0,   2012}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    79,      0,     15}, 0, {   990,   2012}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    79,    256,     15}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -76,    256,     15}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x0703BE80 - 0x0703BEC0
static const Vtx inside_castle_seg7_vertex_0703BE80[] = {
    {{{    79,      0,    -15}, 0, {     0,   2012}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -76,    256,    -15}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    79,    256,    -15}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -76,      0,    -15}, 0, {   990,   2012}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0703BEC0 - 0x0703BF38
static const Gfx inside_castle_seg7_dl_0703BEC0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_castle_seg7_texture_07005800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&inside_castle_seg7_lights_0703BD28.l, 1),
    gsSPLight(&inside_castle_seg7_lights_0703BD28.a, 2),
    gsSPVertex(inside_castle_seg7_vertex_0703BD40, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0,  2, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x0703BF38 - 0x0703BF70
static const Gfx inside_castle_seg7_dl_0703BF38[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_castle_seg7_texture_07003800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(inside_castle_seg7_vertex_0703BE40, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0703BF70 - 0x0703BFA8
static const Gfx inside_castle_seg7_dl_0703BF70[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_castle_seg7_texture_07004800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(inside_castle_seg7_vertex_0703BE80, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0703BFA8 - 0x0703C040
const Gfx inside_castle_seg7_dl_0703BFA8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_0703BEC0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_0703BF38),
    gsSPDisplayList(inside_castle_seg7_dl_0703BF70),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
