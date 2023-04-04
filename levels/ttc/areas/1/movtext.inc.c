// 0x07015F78 - 0x07015F90
static const Lights1 ttc_lights_surface_treadmill = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07015F90 - 0x07016790
ALIGNED8 const Texture ttc_yellow_triangle[] = {
#include "levels/ttc/2.rgba16.inc.c"
};

// 0x07016790 - 0x07016808
const Gfx ttc_dl_surface_treadmill_begin[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(200, 255, 255, 255),
    gsSPFogPosition(900, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsSPLight(&ttc_lights_surface_treadmill.l, 1),
    gsSPLight(&ttc_lights_surface_treadmill.a, 2),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x07016808 - 0x07016840
const Gfx ttc_dl_surface_treadmill_end[] = {
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x07016840 - 0x07016904
Movtex ttc_movtex_tris_big_surface_treadmill[] = {
    MOV_TEX_SPD(     120),
    MOV_TEX_ROT_TRIS( 230, -86,  549,  0,   0,  127,  0, 0),
    MOV_TEX_ROT_TRIS( 230, -35,  549,  0,  33,  122,  1, 0),
    MOV_TEX_ROT_TRIS( 230,   0,  512,  0, 110,   62,  2, 0),
    MOV_TEX_ROT_TRIS( 230,   0, -511,  0, 123,  -31, 14, 0),
    MOV_TEX_ROT_TRIS( 230, -35, -547,  0,  65, -108, 15, 0),
    MOV_TEX_ROT_TRIS( 230, -86, -547,  0,   0, -127, 16, 0),
    MOV_TEX_ROT_TRIS(-229, -86,  549,  0,   0,  127,  0, 1),
    MOV_TEX_ROT_TRIS(-229, -35,  549,  0,  66,  108,  1, 1),
    MOV_TEX_ROT_TRIS(-229,   0,  512,  0, 123,   31,  2, 1),
    MOV_TEX_ROT_TRIS(-229,   0, -511,  0, 110,  -63, 14, 1),
    MOV_TEX_ROT_TRIS(-229, -35, -547,  0,  32, -122, 15, 1),
    MOV_TEX_ROT_TRIS(-229, -86, -547,  0,   0, -127, 16, 1),
    MOV_TEX_END(),
};

// 0x07016904 - 0x070169C8
Movtex ttc_movtex_tris_small_surface_treadmill[] = {
    MOV_TEX_SPD(     120),
    MOV_TEX_ROT_TRIS( 230, -86,  344,  0,   0,  127,  0, 0),
    MOV_TEX_ROT_TRIS( 230, -35,  344,  0,  32,  122,  1, 0),
    MOV_TEX_ROT_TRIS( 230,   0,  308,  0, 110,   63,  2, 0),
    MOV_TEX_ROT_TRIS( 230,   0, -306,  0, 123,  -31,  9, 0),
    MOV_TEX_ROT_TRIS( 230, -35, -342,  0,  65, -108, 10, 0),
    MOV_TEX_ROT_TRIS( 230, -86, -342,  0,   0, -127, 11, 0),
    MOV_TEX_ROT_TRIS(-229, -86,  344,  0,   0,  127,  0, 1),
    MOV_TEX_ROT_TRIS(-229, -35,  344,  0,  65,  108,  1, 1),
    MOV_TEX_ROT_TRIS(-229,   0,  308,  0, 123,   31,  2, 1),
    MOV_TEX_ROT_TRIS(-229,   0, -306,  0, 110,  -63,  9, 1),
    MOV_TEX_ROT_TRIS(-229, -35, -342,  0,  32, -122, 10, 1),
    MOV_TEX_ROT_TRIS(-229, -86, -342,  0,   0, -127, 11, 1),
    MOV_TEX_END(),
};

// 0x070169C8 - 0x07016A20
const Gfx ttc_dl_surface_treadmill[] = {
    gsSP2Triangles( 0,  1,  6, 0x0,  1,  7,  6, 0x0),
    gsSP2Triangles( 1,  2,  7, 0x0,  2,  8,  7, 0x0),
    gsSP2Triangles( 2,  3,  8, 0x0,  3,  9,  8, 0x0),
    gsSP2Triangles( 3,  4,  9, 0x0,  4, 10,  9, 0x0),
    gsSP2Triangles( 4,  5, 10, 0x0,  5, 11, 10, 0x0),
    gsSPEndDisplayList(),
};
