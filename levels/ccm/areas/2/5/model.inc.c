// 0x0701FCD8 - 0x0701FCF0
static const Lights1 ccm_seg7_lights_0701FCD8 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0701FCF0 - 0x0701FD30
static const Vtx ccm_seg7_vertex_0701FCF0[] = {
    {{{ -7587,  -5600,  -6716}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{ -7587,  -5703,  -6716}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{ -7587,  -5703,  -6819}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{ -7587,  -5600,  -6819}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x0701FD30 - 0x0701FD78
static const Gfx ccm_seg7_dl_0701FD30[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snow_09004000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ccm_seg7_lights_0701FCD8.l, 1),
    gsSPLight(&ccm_seg7_lights_0701FCD8.a, 2),
    gsSPVertex(ccm_seg7_vertex_0701FCF0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701FD78 - 0x0701FDE8
const Gfx ccm_seg7_dl_0701FD78[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ccm_seg7_dl_0701FD30),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
