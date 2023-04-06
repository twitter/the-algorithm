#ifndef GD_OLD_MENU_H
#define GD_OLD_MENU_H

#include <ultra64.h>
#include "gd_types.h"

extern void get_objvalue(union ObjVarVal *, enum ValPtrType, void *, size_t);
extern struct ObjGadget *make_gadget(s32, s32);
extern void reset_gadget(struct ObjGadget *);
extern void reset_gadgets_in_grp(struct ObjGroup *);

// see bad_declarations.h
#ifndef GD_USE_BAD_DECLARATIONS
extern struct ObjLabel *make_label(struct ObjValPtrs *, char *, s32, f32, f32, f32);
#endif

#endif /* GD_OLD_MENU_H */
