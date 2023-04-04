#ifndef GD_RENDERER_H
#define GD_RENDERER_H

#include <PR/ultratypes.h>
#include <PR/os_cont.h>

#include "gd_types.h"
#include "macros.h"

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

enum GdSceneId {
    GD_SCENE_YOSHI,  // create yoshi?
    GD_SCENE_YOSHI1,  // destroy yoshi?
    GD_SCENE_REGULAR_MARIO,
    GD_SCENE_DIZZY_MARIO,
    GD_SCENE_CAR,   // create car?
    GD_SCENE_CAR5  // destroy car?
};

// data
extern s32 gGdFrameBufNum;

// functions
u32 get_alloc_mem_amt(void);
s32 gd_get_ostime(void);
f32 get_time_scale(void);
f64 gd_sin_d(f64 x);
f64 gd_cos_d(f64 x);
f64 gd_sqrt_d(f64 x);
void gd_printf(const char *format, ...);
void gd_exit(UNUSED s32 code) NORETURN;
void gd_free(void *ptr);
void *gd_allocblock(u32 size);
void *gd_malloc(u32 size, u8 perm);
void *gd_malloc_perm(u32 size);
void *gd_malloc_temp(u32 size);
void draw_indexed_dl(s32 dlNum, s32 gfxIdx);
void gd_add_to_heap(void *addr, u32 size);
void gdm_init(void *blockpool, u32 size);
void gdm_setup(void);
void gdm_maketestdl(s32 id);
void gd_vblank(void);
void gd_copy_p1_contpad(OSContPad *p1cont);
s32 gd_sfx_to_play(void);
Gfx *gdm_gettestdl(s32 id);
void gd_draw_rect(f32 ulx, f32 uly, f32 lrx, f32 lry);
void gd_draw_border_rect(f32 ulx, f32 uly, f32 lrx, f32 lry);
void gd_dl_set_fill(struct GdColour *colour);
void stash_current_gddl(void);
void pop_gddl_stash(void);
s32 gd_startdisplist(s32 memarea);
s32 gd_enddlsplist_parent(void);
void gd_dl_load_matrix(Mat4f *mtx);
void gd_dl_push_matrix(void);
void gd_dl_pop_matrix(void);
void gd_dl_mul_trans_matrix(f32 x, f32 y, f32 z);
void gd_dl_load_trans_matrix(f32 x, f32 y, f32 z);
void gd_dl_scale(f32 x, f32 y, f32 z);
void func_8019F2C4(f32 arg0, s8 arg1);
void gd_dl_lookat(struct ObjCamera *cam, f32 arg1, f32 arg2, f32 arg3, f32 arg4, f32 arg5, f32 arg6, f32 arg7);
void check_tri_display(s32 vtxcount);
Vtx *gd_dl_make_vertex(f32 x, f32 y, f32 z, f32 alpha);
void func_8019FEF0(void);
void gd_dl_make_triangle(f32 x1, f32 y1, f32 z1, f32 x2, f32 y2, f32 z2, f32 x3, f32 y3, f32 z3);
void func_801A0038(void);
void gd_dl_flush_vertices(void);
void set_render_alpha(f32 arg0);
void set_light_id(s32 index);
void set_light_num(s32 n);
s32 create_mtl_gddl(s32 mtlType);
void branch_to_gddl(s32 dlNum);
void gd_dl_hilite(s32, struct ObjCamera *, struct GdVec3f *, struct GdVec3f *, struct GdVec3f *, struct GdColour *);
void gd_dl_hilite(s32 idx, struct ObjCamera *cam, UNUSED struct GdVec3f *arg2, UNUSED struct GdVec3f *arg3,
                   struct GdVec3f *arg4, struct GdColour *colour);
s32 gd_dl_material_lighting(s32 id, struct GdColour *colour, s32 material);
void set_Vtx_norm_buf_1(struct GdVec3f *norm);
void set_Vtx_norm_buf_2(struct GdVec3f *norm);
void set_gd_mtx_parameters(s32 params);
void gd_set_one_cycle(void);
void gddl_is_loading_stub_dl(UNUSED s32 dlLoad);
void start_view_dl(struct ObjView *view);
void border_active_view(void);
void gd_shading(s32 model);
s32 gd_getproperty(s32 prop, UNUSED void *arg1);
void gd_setproperty(enum GdProperty prop, f32 f1, f32 f2, f32 f3);
void gd_create_ortho_matrix(f32 l, f32 r, f32 b, f32 t, f32 n, f32 f);
void gd_create_perspective_matrix(f32 fovy, f32 aspect, f32 near, f32 far);
s32 setup_view_buffers(const char *name, struct ObjView *view, UNUSED s32 ulx, UNUSED s32 uly,
                       UNUSED s32 lrx, UNUSED s32 lry);
void gd_init_controllers(void);
void stub_renderer_6(struct GdObj *obj); //apply to OBJ_TYPE_VIEWS
long defpup(UNUSED const char *menufmt, ...);
void menu_cb_control_type(u32);
void menu_cb_recalibrate_controller(u32);
void func_801A4438(f32 x, f32 y, f32 z);
void stub_renderer_10(u32 arg0);
void stub_draw_label_text(UNUSED char *s);
void set_active_view(struct ObjView *v);
void func_801A520C(void);
void gd_init(void);
void stub_renderer_12(s8 *arg0);    /* convert LE bytes to BE word? */
void stub_renderer_13(UNUSED void *arg0);
void stub_renderer_14(UNUSED s8 *arg0);    /* convert LE bytes to BE f32? */
void init_pick_buf(s16 *buf, s32 len);
void store_in_pickbuf(s16 data);
s32 get_cur_pickbuf_offset(UNUSED s16 *arg0);
void set_vtx_tc_buf(f32 tcS, f32 tcT);
struct GdObj *load_dynlist(struct DynList *dynlist);

#endif // GD_RENDERER_H
