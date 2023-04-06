// 0x070382B0 - 0x070382C8
static const Lights1 inside_castle_seg7_lights_070382B0 = gdSPDefLights1(
    0x5f, 0x5f, 0x5f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x070382C8 - 0x07038308
static const Vtx inside_castle_seg7_vertex_070382C8[] = {
    {{{  1616,    512,  -1089}, 0, {     0,      0}, {0x59, 0x00, 0x5a, 0xff}}},
    {{{  1616,    358,  -1089}, 0, {     0,    990}, {0x59, 0x00, 0x5a, 0xff}}},
    {{{  1725,    358,  -1197}, 0, {   990,    990}, {0x59, 0x00, 0x5a, 0xff}}},
    {{{  1725,    512,  -1197}, 0, {   990,      0}, {0x59, 0x00, 0x5a, 0xff}}},
};

// 0x07038308 - 0x07038350
static const Gfx inside_castle_seg7_dl_07038308[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_castle_seg7_texture_07003000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&inside_castle_seg7_lights_070382B0.l, 1),
    gsSPLight(&inside_castle_seg7_lights_070382B0.a, 2),
    gsSPVertex(inside_castle_seg7_vertex_070382C8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x07038350 - 0x070383C0
const Gfx inside_castle_seg7_dl_07038350[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_07038308),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
