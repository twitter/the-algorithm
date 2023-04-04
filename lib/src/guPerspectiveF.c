#include "libultra_internal.h"

void guPerspectiveF(float mf[4][4], u16 *perspNorm, float fovy, float aspect, float near, float far,
                    float scale) {
    float yscale;
    int row;
    int col;
    guMtxIdentF(mf);
    fovy *= GU_PI / 180.0;
    yscale = cosf(fovy / 2) / sinf(fovy / 2);
    mf[0][0] = yscale / aspect;
    mf[1][1] = yscale;
    mf[2][2] = (near + far) / (near - far);
    mf[2][3] = -1;
    mf[3][2] = 2 * near * far / (near - far);
    mf[3][3] = 0.0f;
    for (row = 0; row < 4; row++) {
        for (col = 0; col < 4; col++) {
            mf[row][col] *= scale;
        }
    }
    if (perspNorm != NULL) {
        if (near + far <= 2.0) {
            *perspNorm = 65535;
        } else {
            *perspNorm = (double) (1 << 17) / (near + far);
            if (*perspNorm <= 0) {
                *perspNorm = 1;
            }
        }
    }
}

void guPerspective(Mtx *m, u16 *perspNorm, float fovy, float aspect, float near, float far,
                   float scale) {
    float mat[4][4];
    guPerspectiveF(mat, perspNorm, fovy, aspect, near, far, scale);
    guMtxF2L(mat, m);
}
