#include <PR/ultratypes.h>

#include "debug_utils.h"
#include "gd_macros.h"
#include "gd_main.h"
#include "gd_math.h"
#include "gd_types.h"
#include "macros.h"
#include "renderer.h"

/**
 * Finds the square root of a float by treating
 * it as a double and finding the square root from there.
 */
f32 gd_sqrt_f(f32 val) {
    return (f32) gd_sqrt_d(val);
}

/**
 * Set mtx to a look-at matrix for the camera. The resulting transformation
 * transforms the world as if there exists a camera at position 'from' pointed
 * at the position 'to'.
 * An effective goddard copy of mtxf_lookat.
 */
void gd_mat4f_lookat(Mat4f *mtx, f32 xFrom, f32 yFrom, f32 zFrom, f32 xTo, f32 yTo, f32 zTo,
                     f32 zColY, f32 yColY, f32 xColY) {
    f32 invLength;

    struct GdVec3f d;
    struct GdVec3f colX;
    struct GdVec3f norm;

    // No reason to do this? mtx is set lower.
    gd_set_identity_mat4(mtx);

    d.z = xTo - xFrom;
    d.y = yTo - yFrom;
    d.x = zTo - zFrom;

    invLength = ABS(d.z) + ABS(d.y) + ABS(d.x);

    // Scales 'd' if smaller than 10 or larger than 10,000 to be
    // of a magnitude of 10,000.
    if (invLength > 10000.0f || invLength < 10.0f) {
        norm.x = d.z;
        norm.y = d.y;
        norm.z = d.x;
        gd_normalize_vec3f(&norm);
        norm.x *= 10000.0f;
        norm.y *= 10000.0f;
        norm.z *= 10000.0f;

        d.z = norm.x;
        d.y = norm.y;
        d.x = norm.z;
    }

    invLength = -1.0 / gd_sqrt_f(SQ(d.z) + SQ(d.y) + SQ(d.x));
    d.z *= invLength;
    d.y *= invLength;
    d.x *= invLength;

    colX.z = yColY * d.x - xColY * d.y;
    colX.y = xColY * d.z - zColY * d.x;
    colX.x = zColY * d.y - yColY * d.z;

    invLength = 1.0 / gd_sqrt_f(SQ(colX.z) + SQ(colX.y) + SQ(colX.x));

    colX.z *= invLength;
    colX.y *= invLength;
    colX.x *= invLength;

    zColY = d.y * colX.x - d.x * colX.y;
    yColY = d.x * colX.z - d.z * colX.x;
    xColY = d.z * colX.y - d.y * colX.z;

    invLength = 1.0 / gd_sqrt_f(SQ(zColY) + SQ(yColY) + SQ(xColY));

    zColY *= invLength;
    yColY *= invLength;
    xColY *= invLength;

    (*mtx)[0][0] = colX.z;
    (*mtx)[1][0] = colX.y;
    (*mtx)[2][0] = colX.x;
    (*mtx)[3][0] = -(xFrom * colX.z + yFrom * colX.y + zFrom * colX.x);

    (*mtx)[0][1] = zColY;
    (*mtx)[1][1] = yColY;
    (*mtx)[2][1] = xColY;
    (*mtx)[3][1] = -(xFrom * zColY + yFrom * yColY + zFrom * xColY);

    (*mtx)[0][2] = d.z;
    (*mtx)[1][2] = d.y;
    (*mtx)[2][2] = d.x;
    (*mtx)[3][2] = -(xFrom * d.z + yFrom * d.y + zFrom * d.x);

    (*mtx)[0][3] = 0.0f;
    (*mtx)[1][3] = 0.0f;
    (*mtx)[2][3] = 0.0f;
    (*mtx)[3][3] = 1.0f;
}

/**
 * Scales a mat4f in each dimension by a vector.
 */
void gd_scale_mat4f_by_vec3f(Mat4f *mtx, struct GdVec3f *vec) {
    (*mtx)[0][0] *= vec->x;
    (*mtx)[0][1] *= vec->x;
    (*mtx)[0][2] *= vec->x;
    (*mtx)[1][0] *= vec->y;
    (*mtx)[1][1] *= vec->y;
    (*mtx)[1][2] *= vec->y;
    (*mtx)[2][0] *= vec->z;
    (*mtx)[2][1] *= vec->z;
    (*mtx)[2][2] *= vec->z;
}

/**
 * Rotates the matrix 'mtx' about the vector given.
 */
void gd_rot_mat_about_vec(Mat4f *mtx, struct GdVec3f *vec) {
    if (vec->x != 0.0f) {
        gd_absrot_mat4(mtx, GD_X_AXIS, vec->x);
    }
    if (vec->y != 0.0f) {
        gd_absrot_mat4(mtx, GD_Y_AXIS, vec->y);
    }
    if (vec->z != 0.0f) {
        gd_absrot_mat4(mtx, GD_Z_AXIS, vec->z);
    }
}

/**
 * Adds each component of a vector to the
 * translation column of a mat4f matrix.
 */
void gd_add_vec3f_to_mat4f_offset(Mat4f *mtx, struct GdVec3f *vec) {
    UNUSED Mat4f temp;
    f32 z, y, x;

    x = vec->x;
    y = vec->y;
    z = vec->z;

    (*mtx)[3][0] += x;
    (*mtx)[3][1] += y;
    (*mtx)[3][2] += z;
}

/**
 * Creates a lookat matrix, but specifically from the perspective of the origin.
 * Rolls is only ever 0 in practice, and this is really only ever used once.
 *
 * Matrix has form-  | -(cz+sxy)/h sh  (cx-syz)/h 0 |
 *                   |  (sz-cxy)/h ch -(sx+cyz)/h 0 |
 *                   |     -x      -y     -z      0 |
 *                   |      0       0      0      1 |
 */
void gd_create_origin_lookat(Mat4f *mtx, struct GdVec3f *vec, f32 roll) {
    f32 invertedHMag;
    f32 hMag;
    f32 c;
    f32 s;
    f32 radPerDeg = RAD_PER_DEG;
    struct GdVec3f unit;

    unit.x = vec->x;
    unit.y = vec->y;
    unit.z = vec->z;

    gd_normalize_vec3f(&unit);
    hMag = gd_sqrt_f(SQ(unit.x) + SQ(unit.z));

    roll *= radPerDeg; // convert roll from degrees to radians
    s = gd_sin_d(roll);
    c = gd_cos_d(roll);

    gd_set_identity_mat4(mtx);
    if (hMag != 0.0f) {
        invertedHMag = 1.0f / hMag;
        (*mtx)[0][0] = ((-unit.z * c) - (s * unit.y * unit.x)) * invertedHMag;
        (*mtx)[1][0] = ((unit.z * s) - (c * unit.y * unit.x)) * invertedHMag;
        (*mtx)[2][0] = -unit.x;
        (*mtx)[3][0] = 0.0f;

        (*mtx)[0][1] = s * hMag;
        (*mtx)[1][1] = c * hMag;
        (*mtx)[2][1] = -unit.y;
        (*mtx)[3][1] = 0.0f;

        (*mtx)[0][2] = ((c * unit.x) - (s * unit.y * unit.z)) * invertedHMag;
        (*mtx)[1][2] = ((-s * unit.x) - (c * unit.y * unit.z)) * invertedHMag;
        (*mtx)[2][2] = -unit.z;
        (*mtx)[3][2] = 0.0f;

        (*mtx)[0][3] = 0.0f;
        (*mtx)[1][3] = 0.0f;
        (*mtx)[2][3] = 0.0f;
        (*mtx)[3][3] = 1.0f;
    } else {
        (*mtx)[0][0] = 0.0f;
        (*mtx)[1][0] = 1.0f;
        (*mtx)[2][0] = 0.0f;
        (*mtx)[3][0] = 0.0f;

        (*mtx)[0][1] = 0.0f;
        (*mtx)[1][1] = 0.0f;
        (*mtx)[2][1] = 1.0f;
        (*mtx)[3][1] = 0.0f;

        (*mtx)[0][2] = 1.0f;
        (*mtx)[1][2] = 0.0f;
        (*mtx)[2][2] = 0.0f;
        (*mtx)[3][2] = 0.0f;

        (*mtx)[0][3] = 0.0f;
        (*mtx)[1][3] = 0.0f;
        (*mtx)[2][3] = 0.0f;
        (*mtx)[3][3] = 1.0f;
    }
}

/**
 * Clamps a float within a set range about zero.
 */
f32 gd_clamp_f32(f32 a, f32 b) {
    if (b < a) {
        a = b;
    } else if (a < -b) {
        a = -b;
    }

    return a;
}

/**
 * Clamps a vector within a set range about zero.
 */
void gd_clamp_vec3f(struct GdVec3f *vec, f32 limit) {
    if (vec->x > limit) {
        vec->x = limit;
    } else if (vec->x < -limit) {
        vec->x = -limit;
    }

    if (vec->y > limit) {
        vec->y = limit;
    } else if (vec->y < -limit) {
        vec->y = -limit;
    }

    if (vec->z > limit) {
        vec->z = limit;
    } else if (vec->z < -limit) {
        vec->z = -limit;
    }
}

/**
 * Rotates a 2D vector by some angle in degrees.
 */
void gd_rot_2d_vec(f32 deg, f32 *x, f32 *y) {
    f32 xP;
    f32 yP;
    f32 rad;

    rad = deg / DEG_PER_RAD;
    xP = (*x * gd_cos_d(rad)) - (*y * gd_sin_d(rad));
    yP = (*x * gd_sin_d(rad)) + (*y * gd_cos_d(rad));
    *x = xP;
    *y = yP;
}

/**
 * Rotates a matrix about one of its rows.
 */
void UNUSED gd_rot_mat_about_row(Mat4f *mat, s32 row, f32 ang) {
    Mat4f rot;
    struct GdVec3f vec;

    vec.x = (*mat)[row][0];
    vec.y = (*mat)[row][1];
    vec.z = (*mat)[row][2];

    gd_create_rot_mat_angular(&rot, &vec, ang / 2.0);
    gd_mult_mat4f(mat, &rot, mat);
}

/**
 * Rotates a mat4f matrix about a given axis
 * by a set angle in degrees.
 */
void gd_absrot_mat4(Mat4f *mtx, s32 axisnum, f32 ang) {
    Mat4f rMat;
    struct GdVec3f rot;

    switch (axisnum) {
        case GD_X_AXIS:
            rot.x = 1.0f;
            rot.y = 0.0f;
            rot.z = 0.0f;
            break;
        case GD_Y_AXIS:
            rot.x = 0.0f;
            rot.y = 1.0f;
            rot.z = 0.0f;
            break;
        case GD_Z_AXIS:
            rot.x = 0.0f;
            rot.y = 0.0f;
            rot.z = 1.0f;
            break;
        default:
            fatal_printf("absrot_matrix4(): Bad axis num");
    }

    gd_create_rot_mat_angular(&rMat, &rot, ang / 2.0); //? 2.0f
    gd_mult_mat4f(mtx, &rMat, mtx);
}


f32 gd_vec3f_magnitude(struct GdVec3f *vec) {
    return gd_sqrt_f(SQ(vec->x) + SQ(vec->y) + SQ(vec->z));
}

/**
 * Normalizes a vec3f to have a length of 1.
 */
s32 gd_normalize_vec3f(struct GdVec3f *vec) {
    f32 mag;
    if ((mag = SQ(vec->x) + SQ(vec->y) + SQ(vec->z)) == 0.0f) {
        return FALSE;
    }

    mag = gd_sqrt_f(mag);
    // gd_sqrt_f rounds near 0 numbers to 0, so verify again.
    if (mag == 0.0f) {
        vec->x = 0.0f;
        vec->y = 0.0f;
        vec->z = 0.0f;
        return FALSE;
    }

    vec->x /= mag;
    vec->y /= mag;
    vec->z /= mag;

    return TRUE;
}

/**
 * Stores the cross product of 'a' x 'b' in 'dst'.
 */
void gd_cross_vec3f(struct GdVec3f *a, struct GdVec3f *b, struct GdVec3f *dst) {
    struct GdVec3f result;

    result.x = (a->y * b->z) - (a->z * b->y);
    result.y = (a->z * b->x) - (a->x * b->z);
    result.z = (a->x * b->y) - (a->y * b->x);

    dst->x = result.x;
    dst->y = result.y;
    dst->z = result.z;
}

/**
 * Returns the dot product of 'a' and 'b'.
 */
f32 gd_dot_vec3f(struct GdVec3f *a, struct GdVec3f *b) {
    return (a->x * b->x) + (a->y * b->y) + (a->z * b->z);
}

/**
 * Inverts each element of src into dst.
 */
void UNUSED gd_invert_elements_mat4f(Mat4f *src, Mat4f *dst) {
    s32 i;
    s32 j;

    for (i = 0; i < 4; i++) {
        for (j = 0; j < 4; j++) {
            (*dst)[i][j] = 1.0f / (*src)[i][j];
        }
    }
}

/**
 * Inverts a matrix from src and stores it into dst.
 * Reaches a fatal_print if the determinant is 0.
 */
void gd_inverse_mat4f(Mat4f *src, Mat4f *dst) {
    s32 i;
    s32 j;
    f32 determinant;

    gd_adjunct_mat4f(src, dst);
    determinant = gd_mat4f_det(dst);

    if (ABS(determinant) < 1e-5) { //? 1e-5f
        fatal_print("Non-singular matrix, no inverse!\n");
    }

    for (i = 0; i < 4; i++) {
        for (j = 0; j < 4; j++) {
            (*dst)[i][j] /= determinant;
        }
    }
}

/**
 * Takes a matrix from src and converts it into its adjunct in dst.
 */
void gd_adjunct_mat4f(Mat4f *src, Mat4f *dst) {
    struct InvMat4 inv;

    inv.r3.c3 = (*src)[0][0];
    inv.r2.c3 = (*src)[0][1];
    inv.r1.c3 = (*src)[0][2];
    inv.r0.c3 = (*src)[0][3];
    inv.r3.c2 = (*src)[1][0];
    inv.r2.c2 = (*src)[1][1];
    inv.r1.c2 = (*src)[1][2];
    inv.r0.c2 = (*src)[1][3];
    inv.r3.c1 = (*src)[2][0];
    inv.r2.c1 = (*src)[2][1];
    inv.r1.c1 = (*src)[2][2];
    inv.r0.c1 = (*src)[2][3];
    inv.r3.c0 = (*src)[3][0];
    inv.r2.c0 = (*src)[3][1];
    inv.r1.c0 = (*src)[3][2];
    inv.r0.c0 = (*src)[3][3];

    (*dst)[0][0] = gd_3x3_det(inv.r2.c2, inv.r2.c1, inv.r2.c0, inv.r1.c2, inv.r1.c1, inv.r1.c0,
                                 inv.r0.c2, inv.r0.c1, inv.r0.c0);
    (*dst)[1][0] = -gd_3x3_det(inv.r3.c2, inv.r3.c1, inv.r3.c0, inv.r1.c2, inv.r1.c1, inv.r1.c0,
                                  inv.r0.c2, inv.r0.c1, inv.r0.c0);
    (*dst)[2][0] = gd_3x3_det(inv.r3.c2, inv.r3.c1, inv.r3.c0, inv.r2.c2, inv.r2.c1, inv.r2.c0,
                                 inv.r0.c2, inv.r0.c1, inv.r0.c0);
    (*dst)[3][0] = -gd_3x3_det(inv.r3.c2, inv.r3.c1, inv.r3.c0, inv.r2.c2, inv.r2.c1, inv.r2.c0,
                                  inv.r1.c2, inv.r1.c1, inv.r1.c0);
    (*dst)[0][1] = -gd_3x3_det(inv.r2.c3, inv.r2.c1, inv.r2.c0, inv.r1.c3, inv.r1.c1, inv.r1.c0,
                                  inv.r0.c3, inv.r0.c1, inv.r0.c0);
    (*dst)[1][1] = gd_3x3_det(inv.r3.c3, inv.r3.c1, inv.r3.c0, inv.r1.c3, inv.r1.c1, inv.r1.c0,
                                 inv.r0.c3, inv.r0.c1, inv.r0.c0);
    (*dst)[2][1] = -gd_3x3_det(inv.r3.c3, inv.r3.c1, inv.r3.c0, inv.r2.c3, inv.r2.c1, inv.r2.c0,
                                  inv.r0.c3, inv.r0.c1, inv.r0.c0);
    (*dst)[3][1] = gd_3x3_det(inv.r3.c3, inv.r3.c1, inv.r3.c0, inv.r2.c3, inv.r2.c1, inv.r2.c0,
                                 inv.r1.c3, inv.r1.c1, inv.r1.c0);
    (*dst)[0][2] = gd_3x3_det(inv.r2.c3, inv.r2.c2, inv.r2.c0, inv.r1.c3, inv.r1.c2, inv.r1.c0,
                                 inv.r0.c3, inv.r0.c2, inv.r0.c0);
    (*dst)[1][2] = -gd_3x3_det(inv.r3.c3, inv.r3.c2, inv.r3.c0, inv.r1.c3, inv.r1.c2, inv.r1.c0,
                                  inv.r0.c3, inv.r0.c2, inv.r0.c0);
    (*dst)[2][2] = gd_3x3_det(inv.r3.c3, inv.r3.c2, inv.r3.c0, inv.r2.c3, inv.r2.c2, inv.r2.c0,
                                 inv.r0.c3, inv.r0.c2, inv.r0.c0);
    (*dst)[3][2] = -gd_3x3_det(inv.r3.c3, inv.r3.c2, inv.r3.c0, inv.r2.c3, inv.r2.c2, inv.r2.c0,
                                  inv.r1.c3, inv.r1.c2, inv.r1.c0);
    (*dst)[0][3] = -gd_3x3_det(inv.r2.c3, inv.r2.c2, inv.r2.c1, inv.r1.c3, inv.r1.c2, inv.r1.c1,
                                  inv.r0.c3, inv.r0.c2, inv.r0.c1);
    (*dst)[1][3] = gd_3x3_det(inv.r3.c3, inv.r3.c2, inv.r3.c1, inv.r1.c3, inv.r1.c2, inv.r1.c1,
                                 inv.r0.c3, inv.r0.c2, inv.r0.c1);
    (*dst)[2][3] = -gd_3x3_det(inv.r3.c3, inv.r3.c2, inv.r3.c1, inv.r2.c3, inv.r2.c2, inv.r2.c1,
                                  inv.r0.c3, inv.r0.c2, inv.r0.c1);
    (*dst)[3][3] = gd_3x3_det(inv.r3.c3, inv.r3.c2, inv.r3.c1, inv.r2.c3, inv.r2.c2, inv.r2.c1,
                                 inv.r1.c3, inv.r1.c2, inv.r1.c1);
}

/**
 * Returns the determinant of a mat4f matrix.
 */
f32 gd_mat4f_det(Mat4f *mtx) {
    f32 det;
    struct InvMat4 inv;

    inv.r3.c3 = (*mtx)[0][0];
    inv.r2.c3 = (*mtx)[0][1];
    inv.r1.c3 = (*mtx)[0][2];
    inv.r0.c3 = (*mtx)[0][3];
    inv.r3.c2 = (*mtx)[1][0];
    inv.r2.c2 = (*mtx)[1][1];
    inv.r1.c2 = (*mtx)[1][2];
    inv.r0.c2 = (*mtx)[1][3];
    inv.r3.c1 = (*mtx)[2][0];
    inv.r2.c1 = (*mtx)[2][1];
    inv.r1.c1 = (*mtx)[2][2];
    inv.r0.c1 = (*mtx)[2][3];
    inv.r3.c0 = (*mtx)[3][0];
    inv.r2.c0 = (*mtx)[3][1];
    inv.r1.c0 = (*mtx)[3][2];
    inv.r0.c0 = (*mtx)[3][3];

    det = (inv.r3.c3
                * gd_3x3_det(inv.r2.c2, inv.r2.c1, inv.r2.c0,
                             inv.r1.c2, inv.r1.c1, inv.r1.c0,
                             inv.r0.c2, inv.r0.c1, inv.r0.c0)
           - inv.r2.c3
                * gd_3x3_det(inv.r3.c2, inv.r3.c1, inv.r3.c0,
                             inv.r1.c2, inv.r1.c1, inv.r1.c0,
                             inv.r0.c2, inv.r0.c1, inv.r0.c0))
          + inv.r1.c3
                * gd_3x3_det(inv.r3.c2, inv.r3.c1, inv.r3.c0,
                             inv.r2.c2, inv.r2.c1, inv.r2.c0,
                             inv.r0.c2, inv.r0.c1, inv.r0.c0)
          - inv.r0.c3
                * gd_3x3_det(inv.r3.c2, inv.r3.c1, inv.r3.c0,
                             inv.r2.c2, inv.r2.c1, inv.r2.c0,
                             inv.r1.c2, inv.r1.c1, inv.r1.c0);

    return det;
}

/**
 * Takes the individual values of a 3 by 3 matrix and
 * returns the determinant.
 */
f32 gd_3x3_det(f32 r0c0, f32 r0c1, f32 r0c2,
               f32 r1c0, f32 r1c1, f32 r1c2, 
               f32 r2c0, f32 r2c1, f32 r2c2) {
    f32 det;

    det = r0c0 * gd_2x2_det(r1c1, r1c2, r2c1, r2c2) - r1c0 * gd_2x2_det(r0c1, r0c2, r2c1, r2c2)
          + r2c0 * gd_2x2_det(r0c1, r0c2, r1c1, r1c2);

    return det;
}

/**
 * Takes the individual values of a 2 by 2 matrix and
 * returns the determinant.
 */
f32 gd_2x2_det(f32 a, f32 b, f32 c, f32 d) {
    f32 det = a * d - b * c;

    return det;
}

/**
 * Creates a vector negative to what was passed in. Also sets the first row of a mat4f
 * to 1 0 0 0. Perhaps meant to be used at the end of gd_create_quat_rot_mat? Not
 * sure of the purpose of the vector portion, though.
 */
void UNUSED gd_create_neg_vec_zero_first_mat_row(Mat4f *mtx, struct GdVec3f *vec, f32 x, f32 y, f32 z) {
    s32 i;

    vec->x = -x;
    vec->y = -y;
    vec->z = -z;

    (*mtx)[0][0] = 1.0f;

    for (i = 1; i < 4; i++) {
        (*mtx)[0][i] = 0.0f;
    }
}

/**
 * This function quite literally does nothing.
 * Seems to have been meant to create a vector from a quaternion?
 */
void UNUSED gd_broken_quat_to_vec3f(f32 quat[4], struct GdVec3f *vec, f32 zHalf, s32 i, s32 run) {
    s32 j;
    s32 k;
    UNUSED f32 jVal;
    UNUSED f32 kVal;
    UNUSED struct GdVec3f uVec;
    struct GdVec3f tVec;

    tVec.x = vec->x;
    tVec.y = vec->y;
    tVec.z = vec->z;

    if (run < 0) {
        goto end;
    }

    if ((j = i + 1) >= 4) {
        j = 1;
    }

    if ((k = j + 1) >= 4) {
        k = 1;
    }

    jVal = quat[j];
    kVal = quat[k];
    uVec.x = quat[0];
    uVec.y = quat[i];
    uVec.z = zHalf + zHalf;

end:
    vec->x = tVec.x;
    vec->y = tVec.y;
    vec->z = tVec.z;
}

/**
 * This function is a pitch rotation of a quaternion, with the sign allowing both regular
 * and inverse multiplication.
 */
void UNUSED gd_quat_rotation(f32 quat[4], UNUSED s32 unused, f32 c, f32 s, s32 i, s32 sign) {
    s32 j;
    s32 k;
    f32 quatVal;
    UNUSED u8 filler[8];

    if ((j = i + 1) >= 4) {
        j = 1;
    }
    if ((k = j + 1) >= 4) {
        k = 1;
    }

    quatVal = quat[i];
    quat[i] = sign * s * quat[0] + quatVal * c;
    quat[0] = quat[0] * c - sign * s * quatVal;

    quatVal = quat[j];
    quat[j] = quat[k] * s + quatVal * c;
    quat[k] = quat[k] * c - s * quatVal;
}

/**
 * Shifts a matrix up by one row, putting the top row on bottom.
 */
void gd_shift_mat_up(Mat4f *mtx) {
    s32 i;
    s32 j;
    f32 temp[3];

    for (i = 0; i < 3; i++) {
        temp[i] = (*mtx)[0][i + 1];
    }
    for (i = 1; i < 4; i++) {
        for (j = 1; j < 4; j++) {
            (*mtx)[i - 1][j - 1] = (*mtx)[i][j];
        }
    }

    (*mtx)[0][3] = 0.0f;
    (*mtx)[1][3] = 0.0f;
    (*mtx)[2][3] = 0.0f;
    (*mtx)[3][3] = 1.0f;

    for (i = 0; i < 3; i++) {
        (*mtx)[3][i] = temp[i];
    }
}

/**
 * Creates a rotation matrix from a quaternion.
 *
 * Has form-
 * | 1        -               -               -        |
 * | 0 w^2+i^2-j^2-k^2     2ij+2wk         2ik+2wj     |
 * | 0     2ij-2wk     w^2+j^2-i^2-k^2     2jk+2wi     |
 * | 0     2ik+2wj         2jk-2wi     w^2+k^2-i^2-j^2 |
 * 
 * Potentially broken if 'mtx' is not an identity matrix/zero'ed.
 */
void UNUSED gd_create_quat_rot_mat(f32 quat[4], UNUSED s32 unused, Mat4f *mtx) {
    f32 twoIJ;
    f32 two0K;
    f32 sqQuat[4];
    s32 i;
    s32 j;
    s32 k;

    for (i = 0; i < 4; i++) {
        sqQuat[i] = SQ(quat[i]);
    }

    for (i = 1; i < 4; i++) {
        if ((j = i + 1) >= 4) {
            j = 1;
        }

        if ((k = j + 1) >= 4) {
            k = 1;
        }

        twoIJ = 2.0 * quat[i] * quat[j];
        two0K = 2.0 * quat[k] * quat[0];

        (*mtx)[j][i] = twoIJ - two0K;
        (*mtx)[i][j] = twoIJ + two0K;
        (*mtx)[i][i] = sqQuat[i] + sqQuat[0] - sqQuat[j] - sqQuat[k];
        (*mtx)[i][0] = 0.0f;
    }

    //! The first row only ever has the first value set to 1, but the
    //! latter portions remain what they were originally. Perhaps this was meant
    //! to call gd_create_neg_vec_zero_first_mat_row?
    (*mtx)[0][0] = 1.0f;
    gd_shift_mat_up(mtx);
}

/**
 * Creates a rotation matrix to multiply the primary matrix by.
 * s/c are sin(angle)/cos(angle). That angular rotation is about vector
 * 'vec'.
 * 
 * Matrix has form-
 *
 * | (1-c)z^2+c (1-c)zy-sx (1-c)xz-sy 0 | 
 * | (1-c)zy-sx (1-c)y^2+c (1-c)xy-sz 0 |
 * | (1-c)xz-sy (1-c)xy-sz (1-c)x^2+c 0 |
 * |      0          0          0     1 |
 */
void gd_create_rot_matrix(Mat4f *mtx, struct GdVec3f *vec, f32 s, f32 c) {
    f32 oneMinusCos;
    struct GdVec3f rev;

    rev.z = vec->x;
    rev.y = vec->y;
    rev.x = vec->z;

    oneMinusCos = 1.0 - c;

    (*mtx)[0][0] = oneMinusCos * rev.z * rev.z + c;
    (*mtx)[0][1] = oneMinusCos * rev.z * rev.y + s * rev.x;
    (*mtx)[0][2] = oneMinusCos * rev.z * rev.x - s * rev.y;
    (*mtx)[0][3] = 0.0f;

    (*mtx)[1][0] = oneMinusCos * rev.z * rev.y - s * rev.x;
    (*mtx)[1][1] = oneMinusCos * rev.y * rev.y + c;
    (*mtx)[1][2] = oneMinusCos * rev.y * rev.x + s * rev.z;
    (*mtx)[1][3] = 0.0f;

    (*mtx)[2][0] = oneMinusCos * rev.z * rev.x + s * rev.y;
    (*mtx)[2][1] = oneMinusCos * rev.y * rev.x - s * rev.z;
    (*mtx)[2][2] = oneMinusCos * rev.x * rev.x + c;
    (*mtx)[2][3] = 0.0f;

    (*mtx)[3][0] = 0.0f;
    (*mtx)[3][1] = 0.0f;
    (*mtx)[3][2] = 0.0f;
    (*mtx)[3][3] = 1.0f;
}

/**
 * Creates a rotation matrix about vector 'vec' with ang in degrees.
 */
void gd_create_rot_mat_angular(Mat4f *mtx, struct GdVec3f *vec, f32 ang) {
    f32 s;
    f32 c;

    s = gd_sin_d(ang / (DEG_PER_RAD / 2.0));
    c = gd_cos_d(ang / (DEG_PER_RAD / 2.0));

    gd_create_rot_matrix(mtx, vec, s, c);
}

/**
 * Sets a mat4f matrix to an identity matrix.
 */
void gd_set_identity_mat4(Mat4f *mtx) {
    (*mtx)[0][0] = 1.0f;
    (*mtx)[0][1] = 0.0f;
    (*mtx)[0][2] = 0.0f;
    (*mtx)[0][3] = 0.0f;
    (*mtx)[1][0] = 0.0f;
    (*mtx)[1][1] = 1.0f;
    (*mtx)[1][2] = 0.0f;
    (*mtx)[1][3] = 0.0f;
    (*mtx)[2][0] = 0.0f;
    (*mtx)[2][1] = 0.0f;
    (*mtx)[2][2] = 1.0f;
    (*mtx)[2][3] = 0.0f;
    (*mtx)[3][0] = 0.0f;
    (*mtx)[3][1] = 0.0f;
    (*mtx)[3][2] = 0.0f;
    (*mtx)[3][3] = 1.0f;
}

/**
 * Copies a mat4f from src to dst.
 */
void gd_copy_mat4f(const Mat4f *src, Mat4f *dst) {
    (*dst)[0][0] = (*src)[0][0];
    (*dst)[0][1] = (*src)[0][1];
    (*dst)[0][2] = (*src)[0][2];
    (*dst)[0][3] = (*src)[0][3];
    (*dst)[1][0] = (*src)[1][0];
    (*dst)[1][1] = (*src)[1][1];
    (*dst)[1][2] = (*src)[1][2];
    (*dst)[1][3] = (*src)[1][3];
    (*dst)[2][0] = (*src)[2][0];
    (*dst)[2][1] = (*src)[2][1];
    (*dst)[2][2] = (*src)[2][2];
    (*dst)[2][3] = (*src)[2][3];
    (*dst)[3][0] = (*src)[3][0];
    (*dst)[3][1] = (*src)[3][1];
    (*dst)[3][2] = (*src)[3][2];
    (*dst)[3][3] = (*src)[3][3];
}

/**
 * Transforms a vec3f, rotating with the main 3x3 portion of the mat4f
 * and translating with the 4th column.
 */
void gd_rotate_and_translate_vec3f(struct GdVec3f *vec, const Mat4f *mtx) {
    struct GdVec3f out;

    out.x = (*mtx)[0][0] * vec->x + (*mtx)[1][0] * vec->y + (*mtx)[2][0] * vec->z;
    out.y = (*mtx)[0][1] * vec->x + (*mtx)[1][1] * vec->y + (*mtx)[2][1] * vec->z;
    out.z = (*mtx)[0][2] * vec->x + (*mtx)[1][2] * vec->y + (*mtx)[2][2] * vec->z;
    out.x += (*mtx)[3][0];
    out.y += (*mtx)[3][1];
    out.z += (*mtx)[3][2];

    vec->x = out.x;
    vec->y = out.y;
    vec->z = out.z;
}

/**
 * Multiples a vec3f by the main 3x3 portion of a mat4f matrix.
 */
void gd_mat4f_mult_vec3f(struct GdVec3f *vec, const Mat4f *mtx) {
    struct GdVec3f out;

    out.x = (*mtx)[0][0] * vec->x + (*mtx)[1][0] * vec->y + (*mtx)[2][0] * vec->z;
    out.y = (*mtx)[0][1] * vec->x + (*mtx)[1][1] * vec->y + (*mtx)[2][1] * vec->z;
    out.z = (*mtx)[0][2] * vec->x + (*mtx)[1][2] * vec->y + (*mtx)[2][2] * vec->z;

    vec->x = out.x;
    vec->y = out.y;
    vec->z = out.z;
}

#define MAT4_DOT_PROD(A, B, R, row, col)                                                               \
    {                                                                                                  \
        (R)[(row)][(col)] = (A)[(row)][0] * (B)[0][(col)];                                             \
        (R)[(row)][(col)] += (A)[(row)][1] * (B)[1][(col)];                                            \
        (R)[(row)][(col)] += (A)[(row)][2] * (B)[2][(col)];                                            \
        (R)[(row)][(col)] += (A)[(row)][3] * (B)[3][(col)];                                            \
    }

#define MAT4_MULTIPLY(A, B, R)                                                                         \
    {                                                                                                  \
        MAT4_DOT_PROD((A), (B), (R), 0, 0);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 0, 1);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 0, 2);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 0, 3);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 1, 0);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 1, 1);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 1, 2);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 1, 3);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 2, 0);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 2, 1);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 2, 2);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 2, 3);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 3, 0);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 3, 1);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 3, 2);                                                            \
        MAT4_DOT_PROD((A), (B), (R), 3, 3);                                                            \
    }

/**
 * Multiplies two Mat4f matrices and puts it in dst.
 */
void gd_mult_mat4f(const Mat4f *mA, const Mat4f *mB, Mat4f *dst) {
    Mat4f res;

    MAT4_MULTIPLY((*mA), (*mB), res);
    gd_copy_mat4f(&res, dst);
}

#undef MAT4_MULTIPLY
#undef MAT4_DOT_PROD

/**
 * Prints a vec3f vector.
 *
 * Printed the prefix at some point, as shown by how the function is used.
 */
void gd_print_vec(UNUSED const char *prefix, const struct GdVec3f *vec) {
    UNUSED u8 filler[8];

    printf("%f,%f,%f\n", vec->x, vec->y, vec->z);
    printf("\n");
}

/**
 * Prints a plane's boundaries.
 *
 * Printed a prefix at some point, as shone by how the function is used.
 */
void gd_print_bounding_box(UNUSED const char *prefix, UNUSED const struct GdBoundingBox *p) {
    UNUSED u8 filler[8];

    printf("Min X = %f, Max X = %f \n", p->minX, p->maxX);
    printf("Min Y = %f, Max Y = %f \n", p->minY, p->maxY);
    printf("Min Z = %f, Max Z = %f \n", p->minZ, p->maxZ);
    printf("\n");
}

/**
 * Prints a Mat4f.
 *
 * Although the prefix input is unused, the one usage of this function
 * does have a "Matrix:" prefix, so it was definitely used at one point.
 */
void gd_print_mtx(UNUSED const char *prefix, const Mat4f *mtx) {
    s32 i;
    s32 j;

    for (i = 0; i < 4; i++) {
        for (j = 0; j < 4; j++) {
            gd_printf("%f ", (*mtx)[i][j]);
        }
        gd_printf("\n");
    }
}

/**
 * Prints a quaternion along with a prefix.
 */
void UNUSED gd_print_quat(const char *prefix, const f32 f[4]) {
    s32 i;

    gd_printf(prefix);
    for (i = 0; i < 4; i++) {
        gd_printf("%f ", f[i]);
    }
    gd_printf("\n");
}

/**
 * Rotates a matrix or creates a rotation matrix about a vector made from an offset
 * of 100 and the passed in x, y, and z values.
 */
void UNUSED gd_rot_mat_offset(Mat4f *dst, f32 x, f32 y, f32 z, s32 copy) {
    f32 adj = 100.0f;
    Mat4f rot;
    f32 c;
    f32 s;
    f32 opp;
    f32 mag;
    struct GdVec3f vec;

    opp = gd_sqrt_f(SQ(x) + SQ(y) + SQ(z));

    if (opp == 0.0f) {
        if (copy) {
            gd_set_identity_mat4(dst);
        }
        return;
    }

    mag = gd_sqrt_f(SQ(adj) + SQ(opp));
    c = adj / mag;
    s = opp / mag;

    vec.x = -y / opp;
    vec.y = -x / opp;
    vec.z = -z / opp;

    gd_create_rot_matrix(&rot, &vec, s, c);
    if (!copy) {
        gd_mult_mat4f(dst, &rot, dst);
    } else {
        gd_copy_mat4f(&rot, dst);
    }
}
