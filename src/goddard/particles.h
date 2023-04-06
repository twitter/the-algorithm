#ifndef GD_PARTICLES_H
#define GD_PARTICLES_H

#include <ultra64.h>
#include "gd_types.h"

// functions
extern void func_801823A0(struct ObjNet *);
extern struct ObjParticle *make_particle(u32, s32, f32, f32, f32);
extern void move_particles_in_grp(struct ObjGroup *);

#endif /* GD_PARTICLES_H */
