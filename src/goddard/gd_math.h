#ifndef GD_MATH_H
#define GD_MATH_H

#include <PR/ultratypes.h>

#include "gd_types.h"
#include "macros.h"

struct Row4 {
    f32 c0, c1, c2, c3;
};

struct InvMat4 {
    struct Row4 r0, r1, r2, r3;
};

enum GdRotAxis {
    GD_X_AXIS,
    GD_Y_AXIS,
    GD_Z_AXIS
};

// Needed for gd_math.c itself.
void gd_adjunct_mat4f(Mat4f *src, Mat4f *dst);
f32 gd_mat4f_det(Mat4f *mtx);
f32 gd_3x3_det(f32 r0c0, f32 r0c1, f32 r0c2,
               f32 r1c0, f32 r1c1, f32 r1c2, 
               f32 r2c0, f32 r2c1, f32 r2c2);
f32 gd_2x2_det(f32 a, f32 b, f32 c, f32 d);

void gd_mat4f_lookat(Mat4f *mtx, f32 xFrom, f32 yFrom, f32 zFrom, f32 xTo, f32 yTo, f32 zTo,
                     f32 zColY, f32 yColY, f32 xColY);
void gd_scale_mat4f_by_vec3f(Mat4f *mtx, struct GdVec3f *vec);
void gd_rot_mat_about_vec(Mat4f *mtx, struct GdVec3f *vec);
void gd_add_vec3f_to_mat4f_offset(Mat4f *mtx, struct GdVec3f *vec);
void gd_create_origin_lookat(Mat4f *mtx, struct GdVec3f *vec, f32 roll);
f32 gd_clamp_f32(f32 a, f32 b);
void gd_clamp_vec3f(struct GdVec3f *vec, f32 limit);
void gd_rot_2d_vec(f32 deg, f32 *x, f32 *y);
void gd_absrot_mat4(Mat4f *mtx, s32 axisnum, f32 ang);
f32 gd_vec3f_magnitude(struct GdVec3f *vec);
s32 gd_normalize_vec3f(struct GdVec3f *vec);
void gd_cross_vec3f(struct GdVec3f *a, struct GdVec3f *b, struct GdVec3f *dst);
f32 gd_dot_vec3f(struct GdVec3f *a, struct GdVec3f *b);
void gd_inverse_mat4f(Mat4f *src, Mat4f *dst);
void gd_create_rot_mat_angular(Mat4f *mtx, struct GdVec3f *vec, f32 ang);
void gd_set_identity_mat4(Mat4f *mtx);
void gd_copy_mat4f(const Mat4f *src, Mat4f *dst);
void gd_rotate_and_translate_vec3f(struct GdVec3f *vec, const Mat4f *mtx);
void gd_mat4f_mult_vec3f(struct GdVec3f *vec, const Mat4f *mtx);
void gd_mult_mat4f(const Mat4f *mA, const Mat4f *mB, Mat4f *dst);
void gd_print_vec(UNUSED const char *prefix, const struct GdVec3f *vec);
void gd_print_bounding_box(UNUSED const char *prefix, const struct GdBoundingBox *p);
void gd_print_mtx(UNUSED const char *prefix, const Mat4f *mtx);

#endif // GD_MATH_H
