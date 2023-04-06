#ifndef SURFACE_TERRAINS_H
#define SURFACE_TERRAINS_H

// Surface Types
#define SURFACE_DEFAULT                      0x0000 // Environment default
#define SURFACE_BURNING                      0x0001 // Lava / Frostbite (in SL), but is used mostly for Lava
#define SURFACE_0004                         0x0004 // Unused, has no function and has parameters
#define SURFACE_HANGABLE                     0x0005 // Ceiling that Mario can climb on
#define SURFACE_SLOW                         0x0009 // Slow down Mario, unused
#define SURFACE_DEATH_PLANE                  0x000A // Death floor
#define SURFACE_CLOSE_CAMERA                 0x000B // Close camera
#define SURFACE_WATER                        0x000D // Water, has no action, used on some waterboxes below
#define SURFACE_FLOWING_WATER                0x000E // Water (flowing), has parameters
#define SURFACE_INTANGIBLE                   0x0012 // Intangible (Separates BBH mansion from merry-go-round, for room usage)
#define SURFACE_VERY_SLIPPERY                0x0013 // Very slippery, mostly used for slides
#define SURFACE_SLIPPERY                     0x0014 // Slippery
#define SURFACE_NOT_SLIPPERY                 0x0015 // Non-slippery, climbable
#define SURFACE_TTM_VINES                    0x0016 // TTM vines, has no action defined
#define SURFACE_MGR_MUSIC                    0x001A // Plays the Merry go round music, see handle_merry_go_round_music in bbh_merry_go_round.inc.c for more details
#define SURFACE_INSTANT_WARP_1B              0x001B // Instant warp to another area, used to warp between areas in WDW and the endless stairs to warp back
#define SURFACE_INSTANT_WARP_1C              0x001C // Instant warp to another area, used to warp between areas in WDW
#define SURFACE_INSTANT_WARP_1D              0x001D // Instant warp to another area, used to warp between areas in DDD, SSL and TTM
#define SURFACE_INSTANT_WARP_1E              0x001E // Instant warp to another area, used to warp between areas in DDD, SSL and TTM
#define SURFACE_SHALLOW_QUICKSAND            0x0021 // Shallow Quicksand (depth of 10 units)
#define SURFACE_DEEP_QUICKSAND               0x0022 // Quicksand (lethal, slow, depth of 160 units)
#define SURFACE_INSTANT_QUICKSAND            0x0023 // Quicksand (lethal, instant)
#define SURFACE_DEEP_MOVING_QUICKSAND        0x0024 // Moving quicksand (flowing, depth of 160 units)
#define SURFACE_SHALLOW_MOVING_QUICKSAND     0x0025 // Moving quicksand (flowing, depth of 25 units)
#define SURFACE_QUICKSAND                    0x0026 // Moving quicksand (60 units)
#define SURFACE_MOVING_QUICKSAND             0x0027 // Moving quicksand (flowing, depth of 60 units)
#define SURFACE_WALL_MISC                    0x0028 // Used for some walls, Cannon to adjust the camera, and some objects like Warp Pipe
#define SURFACE_NOISE_DEFAULT                0x0029 // Default floor with noise
#define SURFACE_NOISE_SLIPPERY               0x002A // Slippery floor with noise
#define SURFACE_HORIZONTAL_WIND              0x002C // Horizontal wind, has parameters
#define SURFACE_INSTANT_MOVING_QUICKSAND     0x002D // Quicksand (lethal, flowing)
#define SURFACE_ICE                          0x002E // Slippery Ice, in snow levels and THI's water floor
#define SURFACE_LOOK_UP_WARP                 0x002F // Look up and warp (Wing cap entrance)
#define SURFACE_HARD                         0x0030 // Hard floor (Always has fall damage)
#define SURFACE_WARP                         0x0032 // Surface warp
#define SURFACE_TIMER_START                  0x0033 // Timer start (Peach's secret slide)
#define SURFACE_TIMER_END                    0x0034 // Timer stop (Peach's secret slide)
#define SURFACE_HARD_SLIPPERY                0x0035 // Hard and slippery (Always has fall damage)
#define SURFACE_HARD_VERY_SLIPPERY           0x0036 // Hard and very slippery (Always has fall damage)
#define SURFACE_HARD_NOT_SLIPPERY            0x0037 // Hard and Non-slippery (Always has fall damage)
#define SURFACE_VERTICAL_WIND                0x0038 // Death at bottom with vertical wind
#define SURFACE_BOSS_FIGHT_CAMERA            0x0065 // Wide camera for BOB and WF bosses
#define SURFACE_CAMERA_FREE_ROAM             0x0066 // Free roam camera for THI and TTC
#define SURFACE_THI3_WALLKICK                0x0068 // Surface where there's a wall kick section in THI 3rd area, has no action defined
#define SURFACE_CAMERA_8_DIR                 0x0069 // Surface that enables far camera for platforms, used in THI
#define SURFACE_CAMERA_MIDDLE                0x006E // Surface camera that returns to the middle, used on the 4 pillars of SSL
#define SURFACE_CAMERA_ROTATE_RIGHT          0x006F // Surface camera that rotates to the right (Bowser 1 & THI)
#define SURFACE_CAMERA_ROTATE_LEFT           0x0070 // Surface camera that rotates to the left (BOB & TTM)
#define SURFACE_CAMERA_BOUNDARY              0x0072 // Intangible Area, only used to restrict camera movement
#define SURFACE_NOISE_VERY_SLIPPERY_73       0x0073 // Very slippery floor with noise, unused
#define SURFACE_NOISE_VERY_SLIPPERY_74       0x0074 // Very slippery floor with noise, unused
#define SURFACE_NOISE_VERY_SLIPPERY          0x0075 // Very slippery floor with noise, used in CCM
#define SURFACE_NO_CAM_COLLISION             0x0076 // Surface with no cam collision flag
#define SURFACE_NO_CAM_COLLISION_77          0x0077 // Surface with no cam collision flag, unused
#define SURFACE_NO_CAM_COL_VERY_SLIPPERY     0x0078 // Surface with no cam collision flag, very slippery with noise (THI)
#define SURFACE_NO_CAM_COL_SLIPPERY          0x0079 // Surface with no cam collision flag, slippery with noise (CCM, PSS and TTM slides)
#define SURFACE_SWITCH                       0x007A // Surface with no cam collision flag, non-slippery with noise, used by switches and Dorrie
#define SURFACE_VANISH_CAP_WALLS             0x007B // Vanish cap walls, pass through them with Vanish Cap
#define SURFACE_PAINTING_WOBBLE_A6           0x00A6 // Painting wobble (BOB Left)
#define SURFACE_PAINTING_WOBBLE_A7           0x00A7 // Painting wobble (BOB Middle)
#define SURFACE_PAINTING_WOBBLE_A8           0x00A8 // Painting wobble (BOB Right)
#define SURFACE_PAINTING_WOBBLE_A9           0x00A9 // Painting wobble (CCM Left)
#define SURFACE_PAINTING_WOBBLE_AA           0x00AA // Painting wobble (CCM Middle)
#define SURFACE_PAINTING_WOBBLE_AB           0x00AB // Painting wobble (CCM Right)
#define SURFACE_PAINTING_WOBBLE_AC           0x00AC // Painting wobble (WF Left)
#define SURFACE_PAINTING_WOBBLE_AD           0x00AD // Painting wobble (WF Middle)
#define SURFACE_PAINTING_WOBBLE_AE           0x00AE // Painting wobble (WF Right)
#define SURFACE_PAINTING_WOBBLE_AF           0x00AF // Painting wobble (JRB Left)
#define SURFACE_PAINTING_WOBBLE_B0           0x00B0 // Painting wobble (JRB Middle)
#define SURFACE_PAINTING_WOBBLE_B1           0x00B1 // Painting wobble (JRB Right)
#define SURFACE_PAINTING_WOBBLE_B2           0x00B2 // Painting wobble (LLL Left)
#define SURFACE_PAINTING_WOBBLE_B3           0x00B3 // Painting wobble (LLL Middle)
#define SURFACE_PAINTING_WOBBLE_B4           0x00B4 // Painting wobble (LLL Right)
#define SURFACE_PAINTING_WOBBLE_B5           0x00B5 // Painting wobble (SSL Left)
#define SURFACE_PAINTING_WOBBLE_B6           0x00B6 // Painting wobble (SSL Middle)
#define SURFACE_PAINTING_WOBBLE_B7           0x00B7 // Painting wobble (SSL Right)
#define SURFACE_PAINTING_WOBBLE_B8           0x00B8 // Painting wobble (Unused - Left)
#define SURFACE_PAINTING_WOBBLE_B9           0x00B9 // Painting wobble (Unused - Middle)
#define SURFACE_PAINTING_WOBBLE_BA           0x00BA // Painting wobble (Unused - Right)
#define SURFACE_PAINTING_WOBBLE_BB           0x00BB // Painting wobble (DDD - Left), makes the painting wobble if touched
#define SURFACE_PAINTING_WOBBLE_BC           0x00BC // Painting wobble (Unused, DDD - Middle)
#define SURFACE_PAINTING_WOBBLE_BD           0x00BD // Painting wobble (Unused, DDD - Right)
#define SURFACE_PAINTING_WOBBLE_BE           0x00BE // Painting wobble (WDW Left)
#define SURFACE_PAINTING_WOBBLE_BF           0x00BF // Painting wobble (WDW Middle)
#define SURFACE_PAINTING_WOBBLE_C0           0x00C0 // Painting wobble (WDW Right)
#define SURFACE_PAINTING_WOBBLE_C1           0x00C1 // Painting wobble (THI Tiny - Left)
#define SURFACE_PAINTING_WOBBLE_C2           0x00C2 // Painting wobble (THI Tiny - Middle)
#define SURFACE_PAINTING_WOBBLE_C3           0x00C3 // Painting wobble (THI Tiny - Right)
#define SURFACE_PAINTING_WOBBLE_C4           0x00C4 // Painting wobble (TTM Left)
#define SURFACE_PAINTING_WOBBLE_C5           0x00C5 // Painting wobble (TTM Middle)
#define SURFACE_PAINTING_WOBBLE_C6           0x00C6 // Painting wobble (TTM Right)
#define SURFACE_PAINTING_WOBBLE_C7           0x00C7 // Painting wobble (Unused, TTC - Left)
#define SURFACE_PAINTING_WOBBLE_C8           0x00C8 // Painting wobble (Unused, TTC - Middle)
#define SURFACE_PAINTING_WOBBLE_C9           0x00C9 // Painting wobble (Unused, TTC - Right)
#define SURFACE_PAINTING_WOBBLE_CA           0x00CA // Painting wobble (Unused, SL - Left)
#define SURFACE_PAINTING_WOBBLE_CB           0x00CB // Painting wobble (Unused, SL - Middle)
#define SURFACE_PAINTING_WOBBLE_CC           0x00CC // Painting wobble (Unused, SL - Right)
#define SURFACE_PAINTING_WOBBLE_CD           0x00CD // Painting wobble (THI Huge - Left)
#define SURFACE_PAINTING_WOBBLE_CE           0x00CE // Painting wobble (THI Huge - Middle)
#define SURFACE_PAINTING_WOBBLE_CF           0x00CF // Painting wobble (THI Huge - Right)
#define SURFACE_PAINTING_WOBBLE_D0           0x00D0 // Painting wobble (HMC & COTMC - Left), makes the painting wobble if touched
#define SURFACE_PAINTING_WOBBLE_D1           0x00D1 // Painting wobble (Unused, HMC & COTMC - Middle)
#define SURFACE_PAINTING_WOBBLE_D2           0x00D2 // Painting wobble (Unused, HMC & COTMC - Right)
#define SURFACE_PAINTING_WARP_D3             0x00D3 // Painting warp (BOB Left)
#define SURFACE_PAINTING_WARP_D4             0x00D4 // Painting warp (BOB Middle)
#define SURFACE_PAINTING_WARP_D5             0x00D5 // Painting warp (BOB Right)
#define SURFACE_PAINTING_WARP_D6             0x00D6 // Painting warp (CCM Left)
#define SURFACE_PAINTING_WARP_D7             0x00D7 // Painting warp (CCM Middle)
#define SURFACE_PAINTING_WARP_D8             0x00D8 // Painting warp (CCM Right)
#define SURFACE_PAINTING_WARP_D9             0x00D9 // Painting warp (WF Left)
#define SURFACE_PAINTING_WARP_DA             0x00DA // Painting warp (WF Middle)
#define SURFACE_PAINTING_WARP_DB             0x00DB // Painting warp (WF Right)
#define SURFACE_PAINTING_WARP_DC             0x00DC // Painting warp (JRB Left)
#define SURFACE_PAINTING_WARP_DD             0x00DD // Painting warp (JRB Middle)
#define SURFACE_PAINTING_WARP_DE             0x00DE // Painting warp (JRB Right)
#define SURFACE_PAINTING_WARP_DF             0x00DF // Painting warp (LLL Left)
#define SURFACE_PAINTING_WARP_E0             0x00E0 // Painting warp (LLL Middle)
#define SURFACE_PAINTING_WARP_E1             0x00E1 // Painting warp (LLL Right)
#define SURFACE_PAINTING_WARP_E2             0x00E2 // Painting warp (SSL Left)
#define SURFACE_PAINTING_WARP_E3             0x00E3 // Painting warp (SSL Medium)
#define SURFACE_PAINTING_WARP_E4             0x00E4 // Painting warp (SSL Right)
#define SURFACE_PAINTING_WARP_E5             0x00E5 // Painting warp (Unused - Left)
#define SURFACE_PAINTING_WARP_E6             0x00E6 // Painting warp (Unused - Medium)
#define SURFACE_PAINTING_WARP_E7             0x00E7 // Painting warp (Unused - Right)
#define SURFACE_PAINTING_WARP_E8             0x00E8 // Painting warp (DDD - Left)
#define SURFACE_PAINTING_WARP_E9             0x00E9 // Painting warp (DDD - Middle)
#define SURFACE_PAINTING_WARP_EA             0x00EA // Painting warp (DDD - Right)
#define SURFACE_PAINTING_WARP_EB             0x00EB // Painting warp (WDW Left)
#define SURFACE_PAINTING_WARP_EC             0x00EC // Painting warp (WDW Middle)
#define SURFACE_PAINTING_WARP_ED             0x00ED // Painting warp (WDW Right)
#define SURFACE_PAINTING_WARP_EE             0x00EE // Painting warp (THI Tiny - Left)
#define SURFACE_PAINTING_WARP_EF             0x00EF // Painting warp (THI Tiny - Middle)
#define SURFACE_PAINTING_WARP_F0             0x00F0 // Painting warp (THI Tiny - Right)
#define SURFACE_PAINTING_WARP_F1             0x00F1 // Painting warp (TTM Left)
#define SURFACE_PAINTING_WARP_F2             0x00F2 // Painting warp (TTM Middle)
#define SURFACE_PAINTING_WARP_F3             0x00F3 // Painting warp (TTM Right)
#define SURFACE_TTC_PAINTING_1               0x00F4 // Painting warp (TTC Left)
#define SURFACE_TTC_PAINTING_2               0x00F5 // Painting warp (TTC Medium)
#define SURFACE_TTC_PAINTING_3               0x00F6 // Painting warp (TTC Right)
#define SURFACE_PAINTING_WARP_F7             0x00F7 // Painting warp (SL Left)
#define SURFACE_PAINTING_WARP_F8             0x00F8 // Painting warp (SL Middle)
#define SURFACE_PAINTING_WARP_F9             0x00F9 // Painting warp (SL Right)
#define SURFACE_PAINTING_WARP_FA             0x00FA // Painting warp (THI Tiny - Left)
#define SURFACE_PAINTING_WARP_FB             0x00FB // Painting warp (THI Tiny - Middle)
#define SURFACE_PAINTING_WARP_FC             0x00FC // Painting warp (THI Tiny - Right)
#define SURFACE_WOBBLING_WARP                0x00FD // Pool warp (HMC & DDD)
#define SURFACE_TRAPDOOR                     0x00FF // Bowser Left trapdoor, has no action defined

#define SURFACE_IS_QUICKSAND(cmd)     (cmd >= 0x21 && cmd < 0x28)   // Doesn't include SURFACE_INSTANT_MOVING_QUICKSAND
#define SURFACE_IS_NOT_HARD(cmd)      (cmd != SURFACE_HARD && \
                                     !(cmd >= 0x35 && cmd <= 0x37))
#define SURFACE_IS_PAINTING_WARP(cmd) (cmd >= 0xD3 && cmd < 0xFD)

#define SURFACE_CLASS_DEFAULT       0x0000
#define SURFACE_CLASS_VERY_SLIPPERY 0x0013
#define SURFACE_CLASS_SLIPPERY      0x0014
#define SURFACE_CLASS_NOT_SLIPPERY  0x0015

#define SURFACE_FLAG_DYNAMIC          (1 << 0)
#define SURFACE_FLAG_NO_CAM_COLLISION (1 << 1)
#define SURFACE_FLAG_X_PROJECTION     (1 << 3)

// These are effectively unique "surface" types like those defined higher
// And they are used as collision commands to load certain functions
#define TERRAIN_LOAD_VERTICES    0x0040 // Begins vertices list for collision triangles
#define TERRAIN_LOAD_CONTINUE    0x0041 // Stop loading vertices but continues to load other collision commands
#define TERRAIN_LOAD_END         0x0042 // End the collision list
#define TERRAIN_LOAD_OBJECTS     0x0043 // Loads in certain objects for level start
#define TERRAIN_LOAD_ENVIRONMENT 0x0044 // Loads water/HMC gas

#define TERRAIN_LOAD_IS_SURFACE_TYPE_LOW(cmd)  (cmd < 0x40)
#define TERRAIN_LOAD_IS_SURFACE_TYPE_HIGH(cmd) (cmd >= 0x65)

// Terrain types defined by the level script command terrain_type (cmd_31)
#define TERRAIN_GRASS  0x0000
#define TERRAIN_STONE  0x0001
#define TERRAIN_SNOW   0x0002
#define TERRAIN_SAND   0x0003
#define TERRAIN_SPOOKY 0x0004
#define TERRAIN_WATER  0x0005
#define TERRAIN_SLIDE  0x0006
#define TERRAIN_MASK   0x0007

// These collision commands are unique "surface" types like those defined higher

// Collision Data Routine Initiate
#define COL_INIT() TERRAIN_LOAD_VERTICES

// Collision Vertices Read Initiate
#define COL_VERTEX_INIT(vtxNum) vtxNum

// Collision Vertex
#define COL_VERTEX(x, y, z) x, y, z

// Collision Tris Initiate
#define COL_TRI_INIT(surfType, triNum) surfType, triNum

// Collision Tri
#define COL_TRI(v1, v2, v3) v1, v2, v3

// Collision Tri With Special Params
#define COL_TRI_SPECIAL(v1, v2, v3, param) v1, v2, v3, param

// Collision Tris Stop Loading
#define COL_TRI_STOP() TERRAIN_LOAD_CONTINUE

// End Collision Data
#define COL_END() TERRAIN_LOAD_END

// Special Object Initiate
#define COL_SPECIAL_INIT(num) TERRAIN_LOAD_OBJECTS, num

// Water Boxes Initiate
#define COL_WATER_BOX_INIT(num) TERRAIN_LOAD_ENVIRONMENT, num

// Water Box
#define COL_WATER_BOX(id, x1, z1, x2, z2, y) id, x1, z1, x2, z2, y

#endif
