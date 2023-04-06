#ifndef _ULTRA64_GU_H_
#define _ULTRA64_GU_H_

#include <PR/mbi.h>
#include <PR/ultratypes.h>

#define GU_PI 3.1415926
/* Functions */

void guPerspectiveF(float mf[4][4], u16 *perspNorm, float fovy, float aspect,
                    float near, float far, float scale);
void guPerspective(Mtx *m, u16 *perspNorm, float fovy, float aspect, float near,
                   float far, float scale);
void guOrtho(Mtx *m, float left, float right, float bottom, float top,
             float near, float far, float scale);
void guTranslate(Mtx *m, float x, float y, float z);
void guRotate(Mtx *m, float a, float x, float y, float z);
void guScale(Mtx *m, float x, float y, float z);
void guMtxF2L(float mf[4][4], Mtx *m);
void guMtxIdent(Mtx *m);
void guMtxIdentF(float mf[4][4]);
void guMtxL2F(float mf[4][4], Mtx *m);
void guNormalize(float *, float *, float *);

/* Used only in Fast3DEX2 */
void guLookAtReflect (Mtx *m, LookAt *l, float xEye, float yEye, float zEye,
                    float xAt,  float yAt,  float zAt,
                    float xUp,  float yUp,  float zUp);
#endif
