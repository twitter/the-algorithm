#ifndef GD_DRAW_OBJECTS_H
#define GD_DRAW_OBJECTS_H

#include "gd_types.h"

// data
extern struct ObjCamera *gViewUpdateCamera;

// bss
// this is unused, but it needs to be declared before gGdLightGroup
extern u8 gUnref_801B9B30[0x88];
extern struct ObjGroup *gGdLightGroup;  // ObjGroup* of ObjLights

// functions
extern void draw_light(struct ObjLight *);
extern void draw_material(struct ObjMaterial *);
extern struct GdColour *gd_get_colour(s32);
extern void draw_face(struct ObjFace *);
extern void draw_label(struct ObjLabel *);
extern void draw_net(struct ObjNet *);
extern void draw_gadget(struct ObjGadget *);
extern void draw_camera(struct ObjCamera *);
extern void func_80179B9C(struct GdVec3f *, struct ObjCamera *, struct ObjView *);
extern void nop_obj_draw(struct GdObj *);
extern void draw_particle(struct GdObj *);
extern void draw_bone(struct GdObj *);
extern void draw_joint(struct GdObj *);
extern void draw_group(struct ObjGroup *);
extern void draw_plane(struct GdObj *);
extern void apply_obj_draw_fn(struct GdObj *);
extern void create_gddl_for_shapes(struct ObjGroup *);
extern void map_face_materials(struct ObjGroup *, struct ObjGroup *);
extern void map_vertices(struct ObjGroup *, struct ObjGroup *);
extern void update_view(struct ObjView *);

#endif /* GD_DRAW_OBJECTS_H */
