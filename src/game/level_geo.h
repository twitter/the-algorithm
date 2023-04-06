#ifndef _LEVEL_GEO_H
#define _LEVEL_GEO_H

struct Struct802761D0
{
    u16 unk0;
    s16 unk2;
    u8 filler4[0x18-0x4];
};

extern Gfx *geo_envfx_main(s32 a, struct GraphNode *b, f32 c[4][4]);
extern Gfx *geo_skybox_main(s32 a, struct GraphNode *b, UNUSED Mat4 *c);

#endif /* _LEVEL_GEO_H */
