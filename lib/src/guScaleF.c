#include "libultra_internal.h"

void guScaleF(float mf[4][4], float x, float y, float z) {
    guMtxIdentF(mf);
    mf[0][0] = x;
    mf[1][1] = y;
    mf[2][2] = z;
    mf[3][3] = 1.0;
}

void guScale(Mtx *m, float x, float y, float z) {
    float mf[4][4];
    guScaleF(mf, x, y, z);
    guMtxF2L(mf, m);
}
