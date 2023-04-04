#ifndef GD_JOINTS_H
#define GD_JOINTS_H

#include <PR/ultratypes.h>

#include "gd_types.h"

// bss
extern s32 sResetWeightVtxNum;

// functions
void eye_joint_update_func(struct ObjJoint *self);
struct ObjJoint *make_joint(s32 flags, f32 x, f32 y, f32 z);
struct ObjJoint *make_grabber_joint(struct ObjShape *shape, s32 flags, f32 x, f32 y, f32 z);
void func_8018F328(struct ObjBone *b);
void func_8018FA68(struct ObjBone *b);
s32 set_skin_weight(struct ObjJoint *j, s32 id, struct ObjVertex *vtx, f32 weight);
void func_8018FB58(struct ObjBone *b);
void add_joint2bone(struct ObjBone *b, struct ObjJoint *j);
struct ObjBone *make_bone(s32 a0, struct ObjJoint *j1, struct ObjJoint *j2, s32 a3);
void func_801911A8(struct ObjJoint *j);
void func_80191220(struct ObjJoint *j);
void func_801913C0(struct ObjJoint *j);
void func_801913F0(struct ObjJoint *j);
void stub_joints_2(struct ObjJoint *j);
void reset_joint(struct ObjJoint *j);
void func_80191824(struct ObjJoint *j);
void func_80191E88(struct ObjGroup *grp);
void reset_joint_counts(void);

#endif // GD_JOINTS_H
