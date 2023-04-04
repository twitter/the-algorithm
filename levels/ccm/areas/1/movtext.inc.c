// 0x070166E8
static Movtex ccm_movtex_penguin_puddle_water_data[] = {
    MOV_TEX_INIT_LOAD(   1),
    MOV_TEX_ROT_SPEED(   5),
    MOV_TEX_ROT_SCALE(   2),
    MOV_TEX_4_BOX_TRIS(3137, 4228),
    MOV_TEX_4_BOX_TRIS(3137, 4945),
    MOV_TEX_4_BOX_TRIS(3925, 4945),
    MOV_TEX_4_BOX_TRIS(3925, 4228),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(   0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x07016708 - 0x07016718
const struct MovtexQuadCollection ccm_movtex_penguin_puddle_water[] = {
    {0, ccm_movtex_penguin_puddle_water_data},
    {-1, NULL},
};
