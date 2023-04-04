#ifndef SEGMENT2_H
#define SEGMENT2_H

#include <PR/ultratypes.h>
#include <PR/gbi.h>

extern u8 seg2_course_name_table[];
extern u8 seg2_act_name_table[];
extern Gfx dl_rgba16_text_begin[];
extern Gfx dl_rgba16_text_end[];
extern Gfx dl_ia_text_begin[];
extern Gfx dl_ia_text_end[];
extern u8 texture_transition_star_half[];
extern u8 texture_transition_circle_half[];
extern u8 texture_transition_mario[];
extern u8 texture_transition_bowser_half[];
extern u8 texture_waterbox_water[];
extern u8 texture_waterbox_mist[];
extern u8 texture_waterbox_jrb_water[];
extern u8 texture_waterbox_unknown_water[];
extern u8 texture_waterbox_lava[];
extern Gfx dl_proj_mtx_fullscreen[];
extern Gfx dl_draw_quad_verts_0123[];
extern Gfx dl_screen_transition_end[];
extern Gfx dl_transition_draw_filled_region[];
extern Gfx dl_shadow_circle[];
extern Gfx dl_shadow_square[];
extern Gfx dl_shadow_9_verts[];
extern Gfx dl_shadow_4_verts[];
extern Gfx dl_shadow_end[];
extern Gfx dl_skybox_begin[];
extern Gfx dl_skybox_tile_tex_settings[];
extern Gfx dl_skybox_end[];
extern Gfx dl_waterbox_ia16_begin[];
extern Gfx dl_waterbox_rgba16_begin[];
extern Gfx dl_waterbox_end[];
extern Gfx dl_paintings_draw_ripples[];
extern Gfx dl_paintings_rippling_begin[];
extern Gfx dl_paintings_rippling_end[];
extern Gfx dl_paintings_env_mapped_begin[];
extern Gfx dl_paintings_env_mapped_end[];
extern u8 seg2_painting_triangle_mesh[];
extern u8 seg2_painting_mesh_neighbor_tris[];
extern u8* main_hud_lut[58];
extern Gfx dl_hud_img_load_tex_block[];
extern Gfx dl_hud_img_begin[];
extern Gfx dl_hud_img_end[];
extern void *main_font_lut[];
extern Gfx dl_ia_text_tex_settings[];
extern Gfx dl_rgba16_load_tex_block[];
extern void *main_credits_font_lut[];
extern u8* main_hud_camera_lut[6];
extern Gfx dl_draw_text_bg_box[];
extern Gfx dl_draw_triangle[];
extern void *seg2_dialog_table[];
extern Gfx dl_billboard_num_0[];
extern Gfx dl_billboard_num_1[];
extern Gfx dl_billboard_num_2[];
extern Gfx dl_billboard_num_3[];
extern Gfx dl_billboard_num_4[];
extern Gfx dl_billboard_num_5[];
extern Gfx dl_billboard_num_6[];
extern Gfx dl_billboard_num_7[];
extern Gfx dl_billboard_num_8[];
extern Gfx dl_billboard_num_9[];

#endif // SEGMENT2_H
