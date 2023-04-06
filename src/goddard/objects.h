#ifndef GD_OBJECTS_H
#define GD_OBJECTS_H

#include <ultra64.h>
#include "gd_types.h"

// types
// Type to erase for func arg to `apply_to_obj_types_in_group`. Maybe one day this
// can be the proper type of 'void (*)(struct GdObj *)...
typedef void (*applyproc_t)(void *);

// structs
struct DebugCounters {
    u32 ctr0;
    s32 ctr1;
};

// bss
extern struct GdPlaneF D_801B9DA0;
extern struct ObjCamera *sCurrentMoveCamera;
extern struct ObjView *sCurrentMoveView;
extern struct DebugCounters gGdCounter;
extern Mat4f D_801B9DC8;
extern struct GdVec3f D_801B9E08;
extern struct ObjGroup* sCurrentMoveGrp;
extern struct GdVec3f D_801B9E18;
extern struct GdVec3f D_801B9E28;
extern f32 D_801B9E34;
extern Mat4f* D_801B9E38;                /* never read from */
extern struct ObjParticle *D_801B9E3C;  /* never read from */
extern s32 D_801B9E40;                  /* always 0 */
extern s32 D_801B9E44;
extern Mat4f* D_801B9E48;
extern struct ObjCamera* gGdCameraList;
extern void* D_801B9E50;
extern struct ObjGroup* gGdGroupList;
extern s32 gGdObjCount;
extern s32 gGdGroupCount;
extern s32 gGdPlaneCount;
extern s32 gGdCameraCount;
extern struct Unk801B9E68 sGdViewInfo;   /* count in first member? */
extern void* D_801B9E80;
extern struct ObjJoint* gGdJointList;
extern struct ObjBone* gGdBoneList;
extern struct GdObj* gGdObjectList;
extern struct ObjGroup* gGdViewsGroup;

// functions
extern void func_8017BCB0(void);
extern void func_8017BD20(void*);
extern void func_8017BE60(struct GdPlaneF*);
extern void func_8017BED0(struct ObjGroup *, struct GdObj *);
extern const char* get_obj_name_str(enum ObjTypeFlag);
extern struct GdObj * make_object(enum ObjTypeFlag);
extern struct ObjZone * make_zone(struct ObjGroup *, struct GdPlaneF *, struct ObjGroup *);
extern struct ObjUnk200000 * Unknown8017C7A0(struct ObjVertex *, struct ObjFace *);
extern struct Links * make_link_to_obj(struct Links*, struct GdObj*);
extern struct VtxLink * make_vtx_link(struct VtxLink *, Vtx *);
extern struct ObjValPtrs* make_valptrs(struct GdObj *, s32, enum ValPtrType, size_t);
extern void reset_plane(struct ObjPlane*);
extern struct ObjPlane* make_plane(s32, struct ObjFace*);
extern struct ObjCamera* make_camera(s32, struct GdObj*);
extern struct ObjMaterial* make_material(UNUSED s32, char*, s32);
extern struct ObjLight* make_light(s32, char*, s32);
extern struct ObjView* make_view(const char *, s32, s32, s32, s32, s32, s32, struct ObjGroup*);
extern struct ObjAnimator* make_animator(void);
extern struct ObjWeight* make_weight(UNUSED s32, s32, struct ObjVertex*, f32);
extern struct ObjGroup* make_group_of_type(enum ObjTypeFlag, struct GdObj*, struct GdObj*);
extern void sprint_obj_id(char*, struct GdObj*);
extern struct ObjGroup* make_group(s32 count, ...);
extern void addto_group(struct ObjGroup*, struct GdObj*);
extern void addto_groupfirst(struct ObjGroup*, struct GdObj*);
extern s32 group_contains_obj(struct ObjGroup*, struct GdObj*);
extern void show_details(enum ObjTypeFlag);
extern s32 Unknown8017E1E8(void);
extern s32 func_8017E20C(void);
extern void gd_loadtexture(struct GdObj*);
extern void func_8017E2B8(void);
extern struct GdObj* UnknownRecursive8017E2F0(struct GdObj*, enum ObjTypeFlag);
extern s32 apply_to_obj_types_in_group(s32, applyproc_t, struct ObjGroup *);
extern void func_8017E584(struct ObjNet*, struct GdVec3f*, struct GdVec3f*);
extern void func_8017E838(struct ObjNet*, struct GdVec3f*, struct GdVec3f*);
extern void func_8017E9EC(struct ObjNet*);
extern s32 Unknown8017EA94(struct GdVec3f*, Mat4f);
extern s32 Unknown8017EB24(struct GdObj*, struct GdObj*);
extern s32 Unknown8017ED00(struct GdObj*, struct GdPlaneF*);
extern s32 Unknown8017EDCC(struct GdVec3f*, struct GdPlaneF*);
extern s32 gd_plane_point_within(struct GdPlaneF*, struct GdPlaneF*);
extern s32 func_8017F054(struct GdObj*, struct GdObj*);
extern s32 UnknownRecursive8017F210(struct GdObj*, struct GdObj*);
extern void func_8017F404(f32, struct GdObj*, struct GdObj*);
extern void func_8017F424(struct GdTriangleF*, struct GdTriangleF*, f32);
extern void move_animator(struct ObjAnimator*);
extern void drag_picked_object(struct GdObj*);
extern void move_animators(struct ObjGroup *);
extern void find_and_drag_picked_object(struct ObjGroup *);
extern void move_camera(struct ObjCamera*);
extern void move_cameras_in_grp(struct ObjGroup*);
extern void Unknown8018100C(struct ObjLight*);
extern void move_lights_in_grp(struct ObjGroup*);
extern void move_lights_in_grp(struct ObjGroup*);
extern void proc_view_movement(struct ObjView *);
extern void reset_nets_and_gadgets(struct ObjGroup *);
extern void null_obj_lists(void);

#endif /* GD_OBJECTS_H */
