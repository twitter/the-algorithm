#include "libultra_internal.h"

void guTranslateF(float m[4][4], float x, float y, float z) {
    guMtxIdentF(m);
    m[3][0] = x;
    m[3][1] = y;
    m[3][2] = z;
}

void guTranslate(Mtx *m, float x, float y, float z) {
    float mf[4][4];
    guTranslateF(mf, x, y, z);
    guMtxF2L(mf, m);
}
