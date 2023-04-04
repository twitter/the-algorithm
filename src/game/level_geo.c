#include <ultra64.h>

#include "sm64.h"
#include "rendering_graph_node.h"
#include "mario_misc.h"
#include "skybox.h"
#include "engine/math_util.h"
#include "camera.h"
#include "envfx_snow.h"
#include "level_geo.h"

/**
 * Geo function that generates a displaylist for environment effects such as
 * snow or jet stream bubbles.
 */
Gfx *geo_envfx_main(s32 callContext, struct GraphNode *node, Mat4 mtxf) {
    Vec3s marioPos;
    Vec3s camFrom;
    Vec3s camTo;
    void *particleList;
    Gfx *gfx = NULL;

    if (callContext == GEO_CONTEXT_RENDER && gCurGraphNodeCamera != NULL) {
        struct GraphNodeGenerated *execNode = (struct GraphNodeGenerated *) node;
        u32 *params = &execNode->parameter; // accessed a s32 as 2 u16s by pointing to the variable and
                                            // casting to a local struct as necessary.

        if (GET_HIGH_U16_OF_32(*params) != gAreaUpdateCounter) {
            UNUSED struct Camera *sp2C = gCurGraphNodeCamera->config.camera;
            s32 snowMode = GET_LOW_U16_OF_32(*params);

            vec3f_to_vec3s(camTo, gCurGraphNodeCamera->focus);
            vec3f_to_vec3s(camFrom, gCurGraphNodeCamera->pos);
            vec3f_to_vec3s(marioPos, gPlayerCameraState->pos);
            particleList = envfx_update_particles(snowMode, marioPos, camTo, camFrom);
            if (particleList != NULL) {
                Mtx *mtx = alloc_display_list(sizeof(*mtx));

                gfx = alloc_display_list(2 * sizeof(*gfx));
                mtxf_to_mtx(mtx, mtxf);
                gSPMatrix(&gfx[0], VIRTUAL_TO_PHYSICAL(mtx), G_MTX_MODELVIEW | G_MTX_LOAD | G_MTX_NOPUSH);
                gSPBranchList(&gfx[1], VIRTUAL_TO_PHYSICAL(particleList));
                execNode->fnNode.node.flags = (execNode->fnNode.node.flags & 0xFF) | 0x400;
            }
            SET_HIGH_U16_OF_32(*params, gAreaUpdateCounter);
        }
    } else if (callContext == GEO_CONTEXT_AREA_INIT) {
        // Give these arguments some dummy values. Not used in ENVFX_MODE_NONE
        vec3s_copy(camTo, gVec3sZero);
        vec3s_copy(camFrom, gVec3sZero);
        vec3s_copy(marioPos, gVec3sZero);
        envfx_update_particles(ENVFX_MODE_NONE, marioPos, camTo, camFrom);
    }

    return gfx;
}

/**
 * Geo function that generates a displaylist for the skybox. Can be assigned
 * as the function of a GraphNodeBackground.
 */
Gfx *geo_skybox_main(s32 callContext, struct GraphNode *node, UNUSED Mat4 *mtx) {
    Gfx *gfx = NULL;
    struct GraphNodeBackground *backgroundNode = (struct GraphNodeBackground *) node;

    if (callContext == GEO_CONTEXT_AREA_LOAD) {
        backgroundNode->unused = 0;
    } else if (callContext == GEO_CONTEXT_RENDER) {
        struct GraphNodeCamera *camNode = (struct GraphNodeCamera *) gCurGraphNodeRoot->views[0];
        struct GraphNodePerspective *camFrustum =
            (struct GraphNodePerspective *) camNode->fnNode.node.parent;

        gfx = create_skybox_facing_camera(0, backgroundNode->background, camFrustum->fov, gLakituState.pos[0],
                            gLakituState.pos[1], gLakituState.pos[2], gLakituState.focus[0],
                            gLakituState.focus[1], gLakituState.focus[2]);
    }

    return gfx;
}
