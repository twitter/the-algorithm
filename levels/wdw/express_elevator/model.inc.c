// 0x07013500 - 0x07013518
static const Lights1 wdw_seg7_lights_07013500 = gdSPDefLights1(
    0x99, 0x99, 0x99,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07013518 - 0x07013618
static const Vtx wdw_seg7_vertex_07013518[] = {
    {{{  -357,      0,    -64}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -357,     51,    384}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -357,     51,    -64}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   195,     51,    384}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   195,      0,    384}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   195,      0,    -64}, 0, {  2158,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   195,     51,    -64}, 0, {  2158,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   256,     51,    -64}, 0, {  1020,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   256,      0,    -64}, 0, {  1020,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   256,      0,    384}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   256,     51,    384}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   512,     51,    384}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   512,      0,    384}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   512,      0,    -64}, 0, {  1020,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   512,     51,    -64}, 0, {  1020,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -357,      0,    384}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x07013618 - 0x07013708
static const Vtx wdw_seg7_vertex_07013618[] = {
    {{{   256,     51,    384}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   256,      0,    384}, 0, {   224,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   512,      0,    384}, 0, {   224,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   195,      0,    -64}, 0, {   990,    618}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -357,      0,    384}, 0, {  -798,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -357,      0,    -64}, 0, {   990,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   195,      0,    384}, 0, {  -798,    618}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -357,     51,    -64}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   195,     51,    384}, 0, {  -798,    618}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   195,     51,    -64}, 0, {   990,    618}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -357,     51,    384}, 0, {  -798,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   512,      0,    -64}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   256,      0,    384}, 0, {  -798,    690}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   256,      0,    -64}, 0, {   990,    690}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   512,      0,    384}, 0, {  -798,    990}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x07013708 - 0x070137F8
static const Vtx wdw_seg7_vertex_07013708[] = {
    {{{   195,     38,    109}, 0, {   480,    990}, {0x00, 0x6d, 0x40, 0xff}}},
    {{{   256,     51,     87}, 0, {   224,     30}, {0x00, 0x6d, 0x40, 0xff}}},
    {{{   195,     51,     87}, 0, {   224,    990}, {0x00, 0x6d, 0x40, 0xff}}},
    {{{   256,     51,    384}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   512,      0,    384}, 0, {   224,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   512,     51,    384}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   512,     51,    -64}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   256,      0,    -64}, 0, {   224,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   256,     51,    -64}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   512,      0,    -64}, 0, {   224,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   256,     51,    -64}, 0, {   990,    690}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   512,     51,    384}, 0, {  -798,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   512,     51,    -64}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   256,     51,    384}, 0, {  -798,    690}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   256,     38,    109}, 0, {   480,     30}, {0x00, 0x6d, 0x40, 0xff}}},
};

// 0x070137F8 - 0x070138E8
static const Vtx wdw_seg7_vertex_070137F8[] = {
    {{{   195,     38,     65}, 0, {   224,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   256,     38,     65}, 0, {   224,     30}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   256,     13,     65}, 0, {     0,     30}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   195,     13,    109}, 0, {   734,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   256,     38,    109}, 0, {   478,     30}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   195,     38,    109}, 0, {   478,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   256,     13,    109}, 0, {   734,     30}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   195,      0,     87}, 0, {   990,    990}, {0x00, 0x93, 0x40, 0xff}}},
    {{{   256,      0,     87}, 0, {   990,     30}, {0x00, 0x93, 0x40, 0xff}}},
    {{{   256,     13,    109}, 0, {   734,     30}, {0x00, 0x93, 0x40, 0xff}}},
    {{{   195,     13,    109}, 0, {   734,    990}, {0x00, 0x93, 0x40, 0xff}}},
    {{{   195,     13,     65}, 0, {   478,    990}, {0x00, 0x93, 0xc0, 0xff}}},
    {{{   256,      0,     87}, 0, {   224,     30}, {0x00, 0x93, 0xc0, 0xff}}},
    {{{   195,      0,     87}, 0, {   224,    990}, {0x00, 0x93, 0xc0, 0xff}}},
    {{{   256,     13,     65}, 0, {   480,     30}, {0x00, 0x93, 0xc0, 0xff}}},
};

// 0x070138E8 - 0x070139D8
static const Vtx wdw_seg7_vertex_070138E8[] = {
    {{{   195,      0,    -64}, 0, {   224,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -357,      0,    -64}, 0, {   224,    -76}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -357,     51,    -64}, 0, {     0,    -76}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   195,     38,     65}, 0, {   224,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   256,     13,     65}, 0, {     0,     30}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   195,     13,     65}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   195,     51,     87}, 0, {   734,    990}, {0x00, 0x6d, 0xc0, 0xff}}},
    {{{   256,     38,     65}, 0, {   478,     30}, {0x00, 0x6d, 0xc0, 0xff}}},
    {{{   195,     38,     65}, 0, {   478,    990}, {0x00, 0x6d, 0xc0, 0xff}}},
    {{{   256,     51,     87}, 0, {   734,     30}, {0x00, 0x6d, 0xc0, 0xff}}},
    {{{  -357,      0,    384}, 0, {   224,    -76}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   195,     51,    384}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -357,     51,    384}, 0, {     0,    -76}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   195,      0,    384}, 0, {   224,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   195,     51,    -64}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x070139D8 - 0x07013A50
static const Gfx wdw_seg7_dl_070139D8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09006800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wdw_seg7_lights_07013500.l, 1),
    gsSPLight(&wdw_seg7_lights_07013500.a, 2),
    gsSPVertex(wdw_seg7_vertex_07013518, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07013A50 - 0x07013B70
static const Gfx wdw_seg7_dl_07013A50[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, wdw_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(wdw_seg7_vertex_07013618, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 14, 12, 0x0),
    gsSPVertex(wdw_seg7_vertex_07013708, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSP1Triangle( 0, 14,  1, 0x0),
    gsSPVertex(wdw_seg7_vertex_070137F8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 14, 12, 0x0),
    gsSPVertex(wdw_seg7_vertex_070138E8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSP1Triangle( 0,  2, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x07013B70 - 0x07013BE8
const Gfx wdw_seg7_dl_07013B70[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wdw_seg7_dl_070139D8),
    gsSPDisplayList(wdw_seg7_dl_07013A50),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
