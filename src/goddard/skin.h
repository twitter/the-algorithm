#ifndef GD_SKIN_H
#define GD_SKIN_H

#include <PR/ultratypes.h>

#include "gd_types.h"

// bss
extern struct ObjNet* gGdSkinNet;   // @ 801BAAF0

// functions
void reset_net(struct ObjNet *net);
struct ObjNet *make_net(UNUSED s32 a0, struct ObjShape *shapedata, struct ObjGroup *a2,
                        struct ObjGroup *a3, struct ObjGroup *a4);
void convert_net_verts(struct ObjNet *net);
void move_nets(struct ObjGroup *group);
void func_80193848(struct ObjGroup *group);
void reset_net_count(void);

#endif // GD_SKIN_H
