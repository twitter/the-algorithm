#ifndef GEO_LAYOUT_H
#define GEO_LAYOUT_H

#include <PR/ultratypes.h>

#include "game/memory.h"
#include "macros.h"
#include "types.h"

#define GEO_CMD_FLAGS_RESET 0
#define GEO_CMD_FLAGS_SET   1
#define GEO_CMD_FLAGS_CLEAR 2

#define CMD_SIZE_SHIFT (sizeof(void *) >> 3)
#define CMD_PROCESS_OFFSET(offset) (((offset) & 3) | (((offset) & ~3) << CMD_SIZE_SHIFT))

#define cur_geo_cmd_u8(offset) \
    (gGeoLayoutCommand[CMD_PROCESS_OFFSET(offset)])

#define cur_geo_cmd_s16(offset) \
    (*(s16 *) &gGeoLayoutCommand[CMD_PROCESS_OFFSET(offset)])

#define cur_geo_cmd_s32(offset) \
    (*(s32 *) &gGeoLayoutCommand[CMD_PROCESS_OFFSET(offset)])

#define cur_geo_cmd_u32(offset) \
    (*(u32 *) &gGeoLayoutCommand[CMD_PROCESS_OFFSET(offset)])

#define cur_geo_cmd_ptr(offset) \
    (*(void **) &gGeoLayoutCommand[CMD_PROCESS_OFFSET(offset)])

extern struct AllocOnlyPool *gGraphNodePool;
extern struct GraphNode *gCurRootGraphNode;
extern UNUSED s32 D_8038BCA8;
extern struct GraphNode **gGeoViews;
extern u16 gGeoNumViews;
extern uintptr_t gGeoLayoutStack[];
extern struct GraphNode *gCurGraphNodeList[];
extern s16 gCurGraphNodeIndex;
extern s16 gGeoLayoutStackIndex;
extern UNUSED s16 D_8038BD7C;
extern s16 gGeoLayoutReturnIndex;
extern u8 *gGeoLayoutCommand;
extern struct GraphNode gObjParentGraphNode;

extern struct AllocOnlyPool *D_8038BCA0;
extern struct GraphNode *D_8038BCA4;
extern s16 D_8038BD78;
extern struct GraphNode *D_8038BCF8[];

void geo_layout_cmd_branch_and_link(void);
void geo_layout_cmd_end(void);
void geo_layout_cmd_branch(void);
void geo_layout_cmd_return(void);
void geo_layout_cmd_open_node(void);
void geo_layout_cmd_close_node(void);
void geo_layout_cmd_assign_as_view(void);
void geo_layout_cmd_update_node_flags(void);
void geo_layout_cmd_node_root(void);
void geo_layout_cmd_node_ortho_projection(void);
void geo_layout_cmd_node_perspective(void);
void geo_layout_cmd_node_start(void);
void geo_layout_cmd_nop3(void);
void geo_layout_cmd_node_master_list(void);
void geo_layout_cmd_node_level_of_detail(void);
void geo_layout_cmd_node_switch_case(void);
void geo_layout_cmd_node_camera(void);
void geo_layout_cmd_node_translation_rotation(void);
void geo_layout_cmd_node_translation(void);
void geo_layout_cmd_node_rotation(void);
void geo_layout_cmd_node_scale(void);
void geo_layout_cmd_nop2(void);
void geo_layout_cmd_node_animated_part(void);
void geo_layout_cmd_node_billboard(void);
void geo_layout_cmd_node_display_list(void);
void geo_layout_cmd_node_shadow(void);
void geo_layout_cmd_node_object_parent(void);
void geo_layout_cmd_node_generated(void);
void geo_layout_cmd_node_background(void);
void geo_layout_cmd_nop(void);
void geo_layout_cmd_copy_view(void);
void geo_layout_cmd_node_held_obj(void);
void geo_layout_cmd_node_culling_radius(void);

struct GraphNode *process_geo_layout(struct AllocOnlyPool *a0, void *segptr);

#endif // GEO_LAYOUT_H
