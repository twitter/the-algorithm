// 0x0700D28C - 0x0700D2AC
static Movtex jrb_movtex_water_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(   20),
    MOV_TEX_ROT_SCALE(    6),
    MOV_TEX_4_BOX_TRIS(-6304,  -669),
    MOV_TEX_4_BOX_TRIS(-6304,  7814),
    MOV_TEX_4_BOX_TRIS( 7992,  7814),
    MOV_TEX_4_BOX_TRIS( 7992,  -669),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0xB4),
    MOV_TEX_DEFINE(  TEXTURE_JRB_WATER),
    MOV_TEX_END(),
};

// 0x0700D2AC - 0x0700D2CC
static Movtex jrb_movtex_ocean_cave_water_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(   10),
    MOV_TEX_ROT_SCALE(    2),
    MOV_TEX_4_BOX_TRIS( 4433, -4253),
    MOV_TEX_4_BOX_TRIS( 4433,  -669),
    MOV_TEX_4_BOX_TRIS( 5969,  -669),
    MOV_TEX_4_BOX_TRIS( 5969, -4253),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0xB4),
    MOV_TEX_DEFINE(  TEXTURE_JRB_WATER),
    MOV_TEX_END(),
};

// 0x0700D2CC
const struct MovtexQuadCollection jrb_movtex_water[] = {
    {0, jrb_movtex_water_data},
    {1, jrb_movtex_ocean_cave_water_data},
    {-1, NULL},
};

// 0x0700D2E4, appears if you enter the course the first time, then it disappears when you collect the "Plunder in the Sunken Ship" star
static Movtex jrb_movtex_initial_mist_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(   20),
    MOV_TEX_ROT_SCALE(   10),
    MOV_TEX_4_BOX_TRIS(-7818, -1125),
    MOV_TEX_4_BOX_TRIS(-7818,  7814),
    MOV_TEX_4_BOX_TRIS( 9055,  7814),
    MOV_TEX_4_BOX_TRIS( 9055, -1125),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0x32),
    MOV_TEX_DEFINE(  TEXTURE_MIST),
    MOV_TEX_END(),
};

// 0x0700D304
const struct MovtexQuadCollection jrb_movtex_initial_mist[] = {
    {51, jrb_movtex_initial_mist_data},
    {-1, NULL},
};
