#ifndef SHAPE_HELPER_H
#define SHAPE_HELPER_H

#include <ultra64.h>
#include "gd_types.h"

// data
extern struct ObjGroup *gMarioFaceGrp;
extern struct ObjShape *D_801A82E4;
extern struct ObjShape *gShapeRedSpark;
extern struct ObjShape *gShapeSilverSpark;
extern struct ObjShape *gShapeRedStar;
extern struct ObjShape *gShapeSilverStar;

// functions
extern void calc_face_normal(struct ObjFace *);
extern struct ObjVertex *gd_make_vertex(f32, f32, f32);
extern void add_3_vtx_to_face(struct ObjFace *, struct ObjVertex *, struct ObjVertex *, struct ObjVertex *);
extern struct ObjShape *make_shape(s32, const char *);
extern void scale_verts_in_shape(struct ObjShape *, f32, f32, f32);
extern struct ObjNet *make_netfromshape(struct ObjShape *);
extern void animate_mario_head_gameover(struct ObjAnimator *);
extern void animate_mario_head_normal(struct ObjAnimator *);
extern s32 load_mario_head(void (*aniFn)(struct ObjAnimator *));
extern void load_shapes2(void);

// see bad_declarations.h
#ifndef GD_USE_BAD_DECLARATIONS
extern struct ObjFace* make_face_with_colour(f32, f32, f32);
#endif

#endif /* SHAPE_HELPER_H */
