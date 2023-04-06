#ifndef GD_DYNLIST_PROCESSOR_H
#define GD_DYNLIST_PROCESSOR_H

#include <ultra64.h>
#include "gd_types.h"

// types
/// @name DynId Type
/// @{
/// A new type for identification of `GdObj`s in the dynamic object list.
typedef void *DynId;
/// Macros for casting between types of ids,
/// as the id can be either a number or a string.
/// @{
#define DynIdAsStr(id) ((char *)(id))
#define DynIdAsInt(id) ((u32)(uintptr_t)(id))
#define AsDynId(unk)   ((DynId)(unk))
/// @}
/// @}

/// parameters types for `d_set_parm_ptr()`
enum DParmPtr {
    PARM_PTR_OBJ_VTX = 1, ///< parameter is an `ObjVertex` to add to an `ObjFace`
    PARM_PTR_CHAR    = 5  ///< parameter is a `char *`
};

/// parameters for `d_set_parm_f()`
enum DParmF {
    PARM_F_ALPHA = 1,       ///< Set the alpha value for an `ObjShape` or `ObjVertex`
    PARM_F_RANGE_LEFT = 2,  ///< Set the left range for an `ObjGadget`
    PARM_F_RANGE_RIGHT = 3, ///< Set the right range for an `ObjGadget`
    PARM_F_VARVAL = 6       ///< Set the float variable value union in an `ObjGadget`
};

/// `d_makeobj()` object types
enum DObjTypes {
    D_CAR_DYNAMICS  = 0,
    D_NET           = 1,
    D_JOINT         = 2,
    D_ANOTHER_JOINT = 3,
    D_CAMERA        = 4,
    D_VERTEX        = 5,
    D_FACE          = 6,
    D_PLANE         = 7,
    D_BONE          = 8,
    D_MATERIAL      = 9,
    D_SHAPE         = 10,
    D_GADGET        = 11,
    D_LABEL         = 12,
    D_VIEW          = 13,
    D_ANIMATOR      = 14,
    D_DATA_GRP      = 15, ///< An `ObjGroup` that links to raw vertex or face data
    D_PARTICLE      = 16,
    D_LIGHT         = 17,
    D_GROUP         = 18
};

// functions
extern void push_dynobj_stash(void);
extern void pop_dynobj_stash(void);
extern void reset_dynlist(void);
extern struct GdObj *proc_dynlist(struct DynList *dylist);
extern void d_copystr_to_idbuf(char *);
extern struct GdObj *d_makeobj(enum DObjTypes type, DynId id);
extern void d_set_shapeptrptr(struct ObjShape **);
extern struct GdObj *d_use_obj(DynId);
extern void set_cur_dynobj();        //set_cur_dynobj(struct GdObj *);
extern void d_start_group(DynId);
extern void d_end_group(DynId);
extern void dynid_is_int(s32);
extern void d_set_init_pos(f32, f32, f32);
extern void d_get_init_pos(struct GdVec3f*);
extern void d_get_init_rot(struct GdVec3f*);
extern void d_set_rel_pos(f32, f32, f32);
extern void d_get_rel_pos(struct GdVec3f*);
extern struct ObjGroup* d_get_att_objgroup(void);
extern void d_get_scale(struct GdVec3f*);
extern void d_set_world_pos(f32, f32, f32);
extern void d_get_world_pos(struct GdVec3f *);
extern void d_set_scale(f32, f32, f32);
extern void d_add_valptr(DynId, u32, s32, size_t);
extern void d_add_valproc(union ObjVarVal * (*)(union ObjVarVal *, union ObjVarVal));
extern void d_set_flags(s32);
extern void d_set_parm_f(enum DParmF, f32);
extern void d_set_parm_ptr(enum DParmPtr, void *);
extern void d_set_obj_draw_flag(enum ObjDrawingFlags);
extern void d_set_type(s32);
extern void d_set_colour_num(s32);
extern void d_set_diffuse(f32, f32, f32);
extern struct GdPlaneF* d_get_plane(void);
extern void d_get_matrix(Mat4f*);
extern Mat4f* d_get_rot_mtx_ptr(void);
extern void d_set_idn_mtx(Mat4f*);
extern Mat4f* d_get_matrix_ptr(void);
extern Mat4f* d_get_idn_mtx_ptr(void);
extern f32 d_calc_world_dist_btwn(struct GdObj *, struct GdObj *);

#endif /* GD_DYNLIST_PROCESSOR_H */
