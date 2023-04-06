#ifndef GD_MATH_H
#define GD_MATH_H

#include <ultra64.h>
#include "gd_types.h"

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
extern void gd_adjunct_mat4f(Mat4f *, Mat4f *);
extern f32 gd_mat4f_det(Mat4f *);
extern f32 gd_3x3_det(f32, f32, f32, f32, f32, f32, f32, f32, f32);
extern f32 gd_2x2_det(f32, f32, f32, f32);

extern void gd_mat4f_lookat(Mat4f *, f32, f32, f32, f32, f32, f32, f32, f32, f32);
extern void gd_scale_mat4f_by_vec3f(Mat4f *, struct GdVec3f *);
extern void gd_rot_mat_about_vec(Mat4f *, struct GdVec3f *);
extern void gd_add_vec3f_to_mat4f_offset(Mat4f *, struct GdVec3f *);
extern void gd_create_origin_lookat(Mat4f *, struct GdVec3f *, f32);
extern f32 gd_clamp_f32(f32, f32);
extern void gd_clamp_vec3f(struct GdVec3f *, f32);
extern void gd_rot_2d_vec(f32, f32 *, f32 *);
extern void gd_absrot_mat4(Mat4f *, s32, f32);
extern f32 gd_vec3f_magnitude(struct GdVec3f *);
extern s32 gd_normalize_vec3f(struct GdVec3f *);
extern void gd_cross_vec3f(struct GdVec3f *a, struct GdVec3f *b, struct GdVec3f *dst);
extern f32 gd_dot_vec3f(struct GdVec3f *, struct GdVec3f *);
extern void gd_inverse_mat4f(Mat4f *, Mat4f *);
extern void gd_create_rot_mat_angular(Mat4f *, struct GdVec3f *, f32);
extern void gd_set_identity_mat4(Mat4f *);
extern void gd_copy_mat4f(const Mat4f *, Mat4f *);
extern void gd_rotate_and_translate_vec3f(struct GdVec3f *, const Mat4f *);
extern void gd_mat4f_mult_vec3f(struct GdVec3f *, const Mat4f *);
extern void gd_mult_mat4f(const Mat4f *, const Mat4f *, Mat4f *);
extern void gd_print_vec(const char *, const struct GdVec3f *);
extern void gd_print_plane(const char *, const struct GdPlaneF *);
extern void gd_print_mtx(const char *, const Mat4f *);

#endif /* GD_MATH_H */
