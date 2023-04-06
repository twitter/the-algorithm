#ifndef GD_SKIN_H
#define GD_SKIN_H

#include <ultra64.h>
#include "gd_types.h"

// bss
extern struct ObjNet* gGdSkinNet;   // @ 801BAAF0

// functions
extern void reset_net(struct ObjNet *);
extern struct ObjNet * make_net(s32, struct ObjShape *, struct ObjGroup *, struct ObjGroup *, struct ObjGroup *);
extern void convert_net_verts(struct ObjNet *);
extern void move_nets(struct ObjGroup *);
extern void func_80193848(struct ObjGroup *);
extern void reset_net_count(void);

#endif /* GD_SKIN_H */
