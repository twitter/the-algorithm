#ifndef SURFACE_COLLISION_H
#define SURFACE_COLLISION_H

#include <PR/ultratypes.h>

#include "types.h"

// Range level area is 16384x16384 (-8192 to +8192 in x and z)
#define LEVEL_BOUNDARY_MAX  0x2000 // 8192

#define CELL_SIZE           (1 << 10) // 0x400

#define CELL_HEIGHT_LIMIT           20000
#define FLOOR_LOWER_LIMIT           -11000
#define FLOOR_LOWER_LIMIT_MISC      (FLOOR_LOWER_LIMIT + 1000)
// same as FLOOR_LOWER_LIMIT_MISC, explicitly for shadow.c 
// It doesn't match if ".0" is removed or ".f" is added
#define FLOOR_LOWER_LIMIT_SHADOW    (FLOOR_LOWER_LIMIT + 1000.0)

struct WallCollisionData {
    /*0x00*/ f32 x, y, z;
    /*0x0C*/ f32 offsetY;
    /*0x10*/ f32 radius;
    /*0x14*/ u8 filler[2];
    /*0x16*/ s16 numWalls;
    /*0x18*/ struct Surface *walls[4];
};

struct FloorGeometry {
    u8 filler[16]; // possibly position data?
    f32 normalX;
    f32 normalY;
    f32 normalZ;
    f32 originOffset;
};

s32 f32_find_wall_collision(f32 *xPtr, f32 *yPtr, f32 *zPtr, f32 offsetY, f32 radius);
s32 find_wall_collisions(struct WallCollisionData *colData);
f32 find_ceil(f32 posX, f32 posY, f32 posZ, struct Surface **pceil);
f32 find_floor_height_and_data(f32 xPos, f32 yPos, f32 zPos, struct FloorGeometry **floorGeo);
f32 find_floor_height(f32 x, f32 y, f32 z);
f32 find_floor(f32 xPos, f32 yPos, f32 zPos, struct Surface **pfloor);
f32 find_water_level(f32 x, f32 z);
f32 find_poison_gas_level(f32 x, f32 z);
void debug_surface_list_info(f32 xPos, f32 zPos);

#endif // SURFACE_COLLISION_H
