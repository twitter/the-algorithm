#ifndef GD_RENDERER_H
#define GD_RENDERER_H

#include <ultra64.h>
#include "gd_types.h"

// types
/// Properties types used in [gd_setproperty](@ref gd_setproperty); most are stubbed out.
enum GdProperty {
    GD_PROP_OVERLAY       = 4,
    GD_PROP_LIGHTING      = 11,
    GD_PROP_AMB_COLOUR    = 12,
    GD_PROP_DIFUSE_COLOUR = 13,
    GD_PROP_LIGHT_DIR     = 15,
    GD_PROP_CULLING       = 16,
    GD_PROP_STUB17        = 17,
    GD_PROP_STUB18        = 18,
    GD_PROP_STUB19        = 19,
    GD_PROP_STUB20        = 20,
    GD_PROP_STUB21        = 21,
    GD_PROP_ZBUF_FN       = 22
};

// data
extern s32 gGdFrameBuf;

// functions
extern u32 get_alloc_mem_amt(void);
extern s32 gd_get_ostime(void);
extern f32 get_time_scale(void);
extern f64 gd_sin_d(f64);
extern f64 gd_cos_d(f64);
extern f64 gd_sqrt_d(f64);
extern void gd_printf(const char *, ...);
extern void gd_exit(s32);
extern void gd_free(void *);
extern void *gd_allocblock(u32);
extern void *gd_malloc(u32, u8);
extern void *gd_malloc_perm(u32);
extern void *gd_malloc_temp(u32);
extern void func_8019BD0C(s32, s32);
extern void gd_add_to_heap(void *, u32);
extern void gdm_init(void *, u32);
extern void gdm_setup(void);
extern void gdm_maketestdl(s32);
extern void gd_vblank(void);
extern void gd_copy_p1_contpad(OSContPad *);
extern s32 gd_sfx_to_play(void);
extern void *gdm_gettestdl(s32);
extern void gd_draw_rect(f32, f32, f32, f32);
extern void gd_draw_border_rect(f32, f32, f32, f32);
extern void gd_set_fill(struct GdColour *);
extern void stash_current_gddl(void);
extern void pop_gddl_stash(void);
extern s32 gd_startdisplist(s32);
extern s32 gd_enddlsplist_parent();
extern void add_mat4_load_to_dl(Mat4f *);
extern void idn_mtx_push_gddl(void);
extern void pop_mtx_gddl(void);
extern void translate_mtx_gddl(f32, f32, f32);
extern void translate_load_mtx_gddl(f32, f32, f32);
extern void func_8019F258(f32, f32, f32);
extern void func_8019F2C4(f32, s8);
extern void func_8019F318(struct ObjCamera *a, f32, f32, f32, f32, f32, f32, f32);
extern void check_tri_display(s32);
extern Vtx *make_Vtx_if_new(f32, f32, f32, f32);
extern void func_8019FEF0(void);
extern void add_tri_to_dl(f32, f32, f32, f32, f32, f32, f32, f32, f32);
extern void func_801A0038(void);
extern void func_801A0070(void);
extern void func_801A02B8(f32);
extern void set_light_id(s32);
extern void set_light_num(s32);
extern s32 create_mtl_gddl(s32);
extern void branch_to_gddl(s32);
extern void func_801A0478(s32, struct ObjCamera *, struct GdVec3f *, struct GdVec3f *, struct GdVec3f *, struct GdColour *);
extern s32 func_801A086C(s32, struct GdColour *, s32);
extern void set_Vtx_norm_buf_1(struct GdVec3f *);
extern void set_Vtx_norm_buf_2(struct GdVec3f *);
extern void set_gd_mtx_parameters(s32);
extern void gd_set_one_cycle(void);
extern void gddl_is_loading_stub_dl(s32);
extern void start_view_dl(struct ObjView *);
extern void border_active_view(void);
extern void gd_shading(s32);
extern s32 gd_getproperty(s32, void *);
extern void gd_setproperty(enum GdProperty, f32, f32, f32);
extern void gd_create_ortho_matrix(f32, f32, f32, f32, f32, f32);
extern void gd_create_perspective_matrix(f32, f32, f32, f32);
extern s32 setup_view_buffers(const char *, struct ObjView *, s32, s32, s32, s32);
extern void gd_init_controllers(void);
extern void Proc801A43DC(struct GdObj *); //apply to OBJ_TYPE_VIEWS
extern void *func_801A43F0(const char *, ...);   // TUI code..? query_user? doesn't actually return anything... maybe it returned a "menu *"?
extern void Proc801A4410(void *);  // function looks like it got various controller/input attributes..?
extern void Proc801A4424(void *);  // TUI stuff?
extern void func_801A4438(f32, f32, f32);
extern void func_801A48C4(u32);
extern void func_801A48D8(char *);
extern void set_active_view(struct ObjView *);
extern void func_801A520C(void);
extern void gd_init(void);
extern void func_801A5998(s8 *);    /* convert LE bytes to BE word? */
extern void func_801A59AC(void *);
extern void func_801A59C0(s8 *);    /* convert LE bytes to BE f32? */
extern void init_pick_buf(s16 *, s32);
extern void store_in_pickbuf(s16);
extern s32 get_cur_pickbuf_offset(s16 *);
extern void set_Vtx_tc_buf(f32, f32);
extern struct GdObj *load_dynlist(struct DynList *);

#endif /* GD_RENDERER_H */
