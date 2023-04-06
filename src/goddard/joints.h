#ifndef GD_JOINTS_H_
#define GD_JOINTS_H_

#include <ultra64.h>
#include "gd_types.h"

// bss
extern s32 sTargetWeightID;

// functions
extern void Proc8018EBE8(struct ObjJoint *);
extern struct ObjJoint * make_joint(s32, f32, f32, f32);
extern struct ObjJoint * make_joint_withshape(struct ObjShape *, s32, f32, f32, f32);
extern void func_8018F328(struct ObjBone *);
extern void Unknown8018FA68(struct ObjBone *);
extern s32 set_skin_weight(struct ObjJoint *, s32, struct ObjVertex *, f32);
extern void func_8018FB58(struct ObjBone *);
extern void add_joint2bone(struct ObjBone *, struct ObjJoint *);
extern struct ObjBone * make_bone(s32, struct ObjJoint *, struct ObjJoint *, s32);
extern void Unknown801911A8(struct ObjJoint *);
extern void Unknown80191220(struct ObjJoint *);
extern void Unknown801913C0(struct ObjJoint *);
extern void Unknown801913F0(struct ObjJoint *);
extern void Unknown801914F8(struct ObjJoint *);
extern void func_80191604(struct ObjJoint *);
extern void Unknown80191824(struct ObjJoint *);
extern void func_80191E88(struct ObjGroup *);
extern void reset_joint_counts(void);

#endif /* GD_JOINTS_H_ */
