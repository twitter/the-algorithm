// 0x0700BE10 - 0x0700BE88
const Gfx cotmc_dl_water_begin[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_XLU_INTER2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(980, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetEnvColor(255, 255, 255, 140),
    gsDPSetCombineMode(G_CC_DECALFADE, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x0700BE88 - 0x0700BED0
const Gfx cotmc_dl_water_end[] = {
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsSPGeometryModeSetFirst(G_FOG, G_LIGHTING | G_CULL_BACK),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetRenderMode(G_RM_AA_ZB_XLU_INTER, G_RM_NOOP2),
    gsSPEndDisplayList(),
};

// 0x0700BED0 - 0x0700BF60
Movtex cotmc_movtex_tris_water[] = {
    MOV_TEX_SPD(    30),
    MOV_TEX_TRIS(  256,    0, -7373, 0, 0),
    MOV_TEX_TRIS(  256, 5120, -7373, 4, 0),
    MOV_TEX_TRIS( -256,    0, -7373, 0, 1),
    MOV_TEX_TRIS( -256, 5120, -7373, 4, 1),
    MOV_TEX_TRIS( 1536, -204,  3584, 0, 0),
    MOV_TEX_TRIS( 1536,    0,  3430, 1, 0),
    MOV_TEX_TRIS( 1536,    0, -7680, 5, 0),
    MOV_TEX_TRIS(-1536, -204,  3584, 0, 2),
    MOV_TEX_TRIS(-1536,    0,  3430, 1, 2),
    MOV_TEX_TRIS(-1536,    0, -7680, 5, 2),
    MOV_TEX_TRIS(-1024, -614,  3584, 0, 0),
    MOV_TEX_TRIS(-1024, 1434,  3584, 1, 0),
    MOV_TEX_TRIS( 1024, -614,  3584, 0, 1),
    MOV_TEX_TRIS( 1024, 1434,  3584, 1, 1),
    MOV_TEX_END(),
};

// 0x0700BF60 - 0x0700BFA8
const Gfx cotmc_dl_water[] = {
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  5,  7, 0x0, 10, 11, 12, 0x0),
    gsSP2Triangles(12, 11, 13, 0x0,  7,  5,  8, 0x0),
    gsSP2Triangles( 5,  6,  8, 0x0,  8,  6,  9, 0x0),
    gsSPEndDisplayList(),
};
