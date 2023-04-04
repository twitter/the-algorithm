#ifndef GD_SKIN_MOVEMENT_H
#define GD_SKIN_MOVEMENT_H

#include "gd_types.h"

void scale_verts(struct ObjGroup *a0);
void move_skin(struct ObjNet *net);
void func_80181894(struct ObjJoint *joint);
void reset_joint_weights(struct ObjJoint *joint);

#endif // GD_SKIN_MOVEMENT_H
