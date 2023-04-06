#include "libultra_internal.h"

void guRotateF(float m[4][4], float a, float x, float y, float z) {
    float sin_a;
    float cos_a;
    float sp2c;
    float sp28;
    float sp24;
    float xx, yy, zz;
    static float D_80365D70 = GU_PI / 180;

    guNormalize(&x, &y, &z);

    a = a * D_80365D70;

    sin_a = sinf(a);
    cos_a = cosf(a);

    sp2c = x * y * (1 - cos_a);
    sp28 = y * z * (1 - cos_a);
    sp24 = z * x * (1 - cos_a);

    guMtxIdentF(m);
    xx = x * x;
    m[0][0] = (1 - xx) * cos_a + xx;
    m[2][1] = sp28 - x * sin_a;
    m[1][2] = sp28 + x * sin_a;
    yy = y * y;
    m[1][1] = (1 - yy) * cos_a + yy;
    m[2][0] = sp24 + y * sin_a;
    m[0][2] = sp24 - y * sin_a;
    zz = z * z;
    m[2][2] = (1 - zz) * cos_a + zz;
    m[1][0] = sp2c - z * sin_a;
    m[0][1] = sp2c + z * sin_a;
}

void guRotate(Mtx *m, float a, float x, float y, float z) {
    float mf[4][4];
    guRotateF(mf, a, x, y, z);
    guMtxF2L(mf, m);
}
