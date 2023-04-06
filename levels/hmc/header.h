#ifndef HMC_HEADER_H
#define HMC_HEADER_H

#include "types.h"
#include "game/moving_texture.h"

// geo
extern const GeoLayout hmc_geo_000530[];
extern const GeoLayout hmc_geo_000548[];
extern const GeoLayout hmc_geo_000570[];
extern const GeoLayout hmc_geo_000588[];
extern const GeoLayout hmc_geo_0005A0[];
extern const GeoLayout hmc_geo_0005B8[];
extern const GeoLayout hmc_geo_0005D0[];
extern const GeoLayout hmc_geo_0005E8[];
extern const GeoLayout hmc_geo_000618[];
extern const GeoLayout hmc_geo_000658[];
extern const GeoLayout hmc_geo_0006A8[];
extern const GeoLayout hmc_geo_0006E0[];
extern const GeoLayout hmc_geo_000700[];
extern const GeoLayout hmc_geo_000748[];
extern const GeoLayout hmc_geo_000770[];
extern const GeoLayout hmc_geo_000798[];
extern const GeoLayout hmc_geo_0007F8[];
extern const GeoLayout hmc_geo_000850[];
extern const GeoLayout hmc_geo_0008D0[];
extern const GeoLayout hmc_geo_000938[];
extern const GeoLayout hmc_geo_000998[];
extern const GeoLayout hmc_geo_000A18[];
extern const GeoLayout hmc_geo_000A88[];
extern const GeoLayout hmc_geo_000AE8[];
extern const GeoLayout hmc_geo_000B48[];
extern const GeoLayout hmc_geo_000B90[];

// leveldata
extern const Gfx hmc_seg7_dl_070078B0[];
extern const Gfx hmc_seg7_dl_07007B50[];
extern const Gfx hmc_seg7_dl_070080E8[];
extern const Gfx hmc_seg7_dl_070093F0[];
extern const Gfx hmc_seg7_dl_0700E448[];
extern const Gfx hmc_seg7_dl_0700EF00[];
extern const Gfx hmc_seg7_dl_0700F3E8[];
extern const Gfx hmc_seg7_dl_0700FA40[];
extern const Gfx hmc_seg7_dl_0700FEF0[];
extern const Gfx hmc_seg7_dl_07010070[];
extern const Gfx hmc_seg7_dl_07013CA8[];
extern const Gfx hmc_seg7_dl_07013E80[];
extern const Gfx hmc_seg7_dl_07014300[];
extern const Gfx hmc_seg7_dl_07014B08[];
extern const Gfx hmc_seg7_dl_07014C00[];
extern const Gfx hmc_seg7_dl_07014E48[];
extern const Gfx hmc_seg7_dl_070173A8[];
extern const Gfx hmc_seg7_dl_07017C98[];
extern const Gfx hmc_seg7_dl_07018200[];
extern const Gfx hmc_seg7_dl_07019248[];
extern const Gfx hmc_seg7_dl_07019368[];
extern const Gfx hmc_seg7_dl_0701A080[];
extern const Gfx hmc_seg7_dl_0701A400[];
extern const Gfx hmc_seg7_dl_0701E820[];
extern const Gfx hmc_seg7_dl_0701F1B0[];
extern const Gfx hmc_seg7_dl_0701F690[];
extern const Gfx hmc_seg7_dl_0701F818[];
extern const Gfx hmc_seg7_dl_0701FD58[];
extern const Gfx hmc_seg7_dl_0701FFF8[];
extern const Gfx hmc_seg7_dl_07020FD0[];
extern const Gfx hmc_seg7_dl_07021760[];
extern const Gfx hmc_seg7_dl_07021BA0[];
extern const Gfx hmc_seg7_dl_070228A0[];
extern const Gfx hmc_seg7_dl_07022AA0[];
extern const Gfx hmc_seg7_dl_07022DA0[];
extern const Gfx hmc_seg7_dl_07023090[];
extern const Gfx hmc_seg7_dl_07023BC8[];
extern const Gfx hmc_seg7_dl_07023E10[];
extern const Gfx hmc_seg7_dl_07024110[];
extern const Gfx hmc_seg7_dl_07024268[];
extern const Gfx hmc_seg7_dl_070242A0[];
extern const u8 *const hmc_seg7_painting_textures_07025518[];
extern struct Painting cotmc_painting;
extern const Collision hmc_seg7_collision_level[];
extern const MacroObject hmc_seg7_macro_objs[];
extern const u8 hmc_seg7_rooms[];
extern const Collision hmc_seg7_collision_elevator[];
extern const Collision hmc_seg7_collision_0702B65C[];
extern const Collision hmc_seg7_collision_controllable_platform[];
extern const Collision hmc_seg7_collision_controllable_platform_sub[];
extern const Trajectory hmc_seg7_trajectory_0702B86C[];
extern const struct MovtexQuadCollection hmc_movtex_dorrie_pool_water[];
extern const struct MovtexQuadCollection hmc_movtex_toxic_maze_mist[];

// script
extern const LevelScript level_hmc_entry[];

#endif
