// 0x0700FA30 - 0x0700FA50
static Movtex sl_movtex_snowman_water_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(    8),
    MOV_TEX_ROT_SCALE(   10),
    MOV_TEX_4_BOX_TRIS(-6194,  -409),
    MOV_TEX_4_BOX_TRIS(-6194,  4198),
    MOV_TEX_4_BOX_TRIS(  154,  4198),
    MOV_TEX_4_BOX_TRIS(  154,  -409),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(   0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x0700FA50 - 0x0700FA70
static Movtex sl_movtex_ice_bully_water_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(    8),
    MOV_TEX_ROT_SCALE(   10),
    MOV_TEX_4_BOX_TRIS(-1279, -6143),
    MOV_TEX_4_BOX_TRIS(-1279, -3071),
    MOV_TEX_4_BOX_TRIS( 1792, -3071),
    MOV_TEX_4_BOX_TRIS( 1792, -6143),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(   0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x0700FA70 - 0x0700FA88
const struct MovtexQuadCollection sl_movtex_water[] = {
    {0, sl_movtex_snowman_water_data},
    {1, sl_movtex_ice_bully_water_data},
    {-1, NULL},
};
