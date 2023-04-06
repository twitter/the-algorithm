#include "libultra_internal.h"

void guOrthoF(float m[4][4], float left, float right, float bottom, float top, float near, float far,
              float scale) {
    int row;
    int col;
    guMtxIdentF(m);
    m[0][0] = 2 / (right - left);
    m[1][1] = 2 / (top - bottom);
    m[2][2] = -2 / (far - near);
    m[3][0] = -(right + left) / (right - left);
    m[3][1] = -(top + bottom) / (top - bottom);
    m[3][2] = -(far + near) / (far - near);
    m[3][3] = 1;
    for (row = 0; row < 4; row++) {
        for (col = 0; col < 4; col++) {
            m[row][col] *= scale;
        }
    }
}

void guOrtho(Mtx *m, float left, float right, float bottom, float top, float near, float far,
             float scale) {
    float sp28[4][4];
    guOrthoF(sp28, left, right, bottom, top, near, far, scale);
    guMtxF2L(sp28, m);
}
