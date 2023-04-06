#include <ultra64.h>

#include "sm64.h"
#include "behavior_data.h"
#include "engine/behavior_script.h"
#include "area.h"
#include "camera.h"
#include "engine/graph_node.h"
#include "interaction.h"
#include "debug.h"
#include "profiler.h"
#include "spawn_object.h"
#include "engine/surface_collision.h"
#include "memory.h"
#include "level_update.h"
#include "object_collision.h"
#include "object_helpers.h"
#include "platform_displacement.h"
#include "engine/surface_load.h"
#include "object_list_processor.h"
#include "mario.h"


/**
 * Flags controlling what debug info is displayed.
 */
s32 gDebugInfoFlags;

/**
 * The number of times per frame find_floor found no floor beneath an
 * object, and therefore either returned a dynamic floor or NULL.
 */
s32 gNumFindFloorMisses;

UNUSED s32 unused_8033BEF8;

/**
 * An unused debug counter with the label "WALL".
 */
s32 gUnknownWallCount;

/**
 * Roughly the number of objects that have been processed this frame so far.
 * A bug in update_terrain_objects makes this count inaccurate.
 */
u32 gObjectCounter;

/**
 * The number of times find_floor, find_ceil, and find_wall_collisions have been called respectively.
 */
struct NumTimesCalled gNumCalls;

/**
 * An array of debug controls that could be used to tweak in-game parameters.
 * The only used rows are [4] and [5] (effectinfo and enemyinfo).
 */
s16 gDebugInfo[16][8];
s16 gDebugInfoOverwrite[16][8];

/**
 * A set of flags to control which objects are updated on a given frame.
 * This is used during dialog and cutscenes to freeze most objects in place.
 */
u32 gTimeStopState;

/**
 * The pool that objects are allocated from.
 */
struct Object gObjectPool[OBJECT_POOL_CAPACITY];

/**
 * A special object whose purpose is to act as a parent for macro objects.
 */
struct Object gMacroObjectDefaultParent;

/**
 * A pointer to gObjectListArray.
 * Given an object list index idx, gObjectLists[idx] is the head of a doubly
 * linked list of all currently spawned objects in the list.
 */
struct ObjectNode *gObjectLists;

/**
 * A singly linked list of available slots in the object pool.
 */
struct ObjectNode gFreeObjectList;

/**
 * The object representing mario.
 */
struct Object *gMarioObject;

/**
 * An object variable that may have been used to represent the planned
 * second player. This is speculation, based on its position and its usage in
 * shadow.c.
 */
struct Object *gLuigiObject;

/**
 * The object whose behavior script is currently being updated.
 * This object is used frequently in object behavior code, and so is often
 * aliased as "o".
 */
struct Object *gCurrentObject;

/**
 * The next object behavior command to be executed.
 */
const BehaviorScript *gCurBhvCommand;

/**
 * The number of objects that were processed last frame, which may miss some
 * objects that were spawned last frame and all objects that were spawned this
 * frame. It also includes objects that were unloaded last frame.
 * Besides this, a bug in update_terrain_objects makes this count inaccurate.
 */
s16 gPrevFrameObjectCount;

/**
 * The total number of surface nodes allocated (a node is allocated for each
 * spatial partition cell that a surface intersects).
 */
s32 gSurfaceNodesAllocated;

/**
 * The total number of surfaces allocated.
 */
s32 gSurfacesAllocated;

/**
 * The number of nodes that have been created for surfaces.
 */
s32 gNumStaticSurfaceNodes;

/**
 * The number of surfaces in the pool.
 */
s32 gNumStaticSurfaces;

/**
 * A pool used by chain chomp and wiggler to allocate their body parts.
 */
struct MemoryPool *gObjectMemoryPool;


s16 gCheckingSurfaceCollisionsForCamera;
s16 gFindFloorIncludeSurfaceIntangible;
s16 *gEnvironmentRegions;
s32 gEnvironmentLevels[20];
s8 gDoorAdjacentRooms[60][2];
s16 gMarioCurrentRoom;
s16 D_8035FEE2;
s16 D_8035FEE4;
s16 gTHIWaterDrained;
s16 gTTCSpeedSetting;
s16 gMarioShotFromCannon;
s16 gCCMEnteredSlide;
s16 gNumRoomedObjectsInMarioRoom;
s16 gNumRoomedObjectsNotInMarioRoom;
s16 gWDWWaterLevelChanging;
s16 gMarioOnMerryGoRound;

/**
 * Nodes used to represent the doubly linked object lists.
 */
struct ObjectNode gObjectListArray[16];

/**
 * The order that object lists are processed in a frame.
 */
s8 sObjectListUpdateOrder[] = { OBJ_LIST_SPAWNER,
                                OBJ_LIST_SURFACE,
                                OBJ_LIST_POLELIKE,
                                OBJ_LIST_PLAYER,
                                OBJ_LIST_PUSHABLE,
                                OBJ_LIST_GENACTOR,
                                OBJ_LIST_DESTRUCTIVE,
                                OBJ_LIST_LEVEL,
                                OBJ_LIST_DEFAULT,
                                OBJ_LIST_UNIMPORTANT,
                                -1 };

/**
 * Info needed to spawn particles and keep track of which have been spawned for
 * an object.
 */
struct ParticleProperties {
    u32 particleFlag;
    u32 activeParticleFlag;
    u8 model;
    const BehaviorScript *behavior;
};

/**
 * A table mapping particle flags to various properties use when spawning a particle.
 */
struct ParticleProperties sParticleTypes[] = {
    { PARTICLE_DUST,                 ACTIVE_PARTICLE_DUST,                 MODEL_MIST,                 bhvMistParticleSpawner },
    { PARTICLE_VERTICAL_STAR,        ACTIVE_PARTICLE_V_STAR,               MODEL_NONE,                 bhvVertStarParticleSpawner },
    { PARTICLE_HORIZONTAL_STAR,      ACTIVE_PARTICLE_H_STAR,               MODEL_NONE,                 bhvHorStarParticleSpawner },
    { PARTICLE_SPARKLES,             ACTIVE_PARTICLE_SPARKLES,             MODEL_SPARKLES,             bhvSparkleParticleSpawner },
    { PARTICLE_BUBBLE,               ACTIVE_PARTICLE_BUBBLE,               MODEL_BUBBLE,               bhvBubbleParticleSpawner },
    { PARTICLE_WATER_SPLASH,         ACTIVE_PARTICLE_WATER_SPLASH,         MODEL_WATER_SPLASH,         bhvWaterSplash },
    { PARTICLE_IDLE_WATER_WAVE,      ACTIVE_PARTICLE_IDLE_WATER_WAVE,      MODEL_IDLE_WATER_WAVE,      bhvIdleWaterWave },
    { PARTICLE_PLUNGE_BUBBLE,        ACTIVE_PARTICLE_PLUNGE_BUBBLE,        MODEL_WHITE_PARTICLE_SMALL, bhvPlungeBubble },
    { PARTICLE_WAVE_TRAIL,           ACTIVE_PARTICLE_WAVE_TRAIL,           MODEL_WAVE_TRAIL,           bhvWaveTrail },
    { PARTICLE_FIRE,                 ACTIVE_PARTICLE_FIRE,                 MODEL_RED_FLAME,            bhvFireParticleSpawner },
    { PARTICLE_SHALLOW_WATER_WAVE,   ACTIVE_PARTICLE_SHALLOW_WATER_WAVE,   MODEL_NONE,                 bhvShallowWaterWave },
    { PARTICLE_SHALLOW_WATER_SPLASH, ACTIVE_PARTICLE_SHALLOW_WATER_SPLASH, MODEL_NONE,                 bhvShallowWaterSplash },
    { PARTICLE_LEAF,                 ACTIVE_PARTICLE_LEAF,                 MODEL_NONE,                 bhvLeafParticleSpawner },
    { PARTICLE_SNOW,                 ACTIVE_PARTICLE_SNOW,                 MODEL_NONE,                 bhvSnowParticleSpawner },
    { PARTICLE_BREATH,               ACTIVE_PARTICLE_BREATH,               MODEL_NONE,                 bhvBreathParticleSpawner },
    { PARTICLE_DIRT,                 ACTIVE_PARTICLE_DIRT,                 MODEL_NONE,                 bhvDirtParticleSpawner },
    { PARTICLE_MIST_CIRCLE,          ACTIVE_PARTICLE_MIST_CIRCLE,          MODEL_NONE,                 bhvMistCircParticleSpawner },
    { PARTICLE_TRIANGLE,             ACTIVE_PARTICLE_TRIANGLE,             MODEL_NONE,                 bhvTriangleParticleSpawner },
    { 0, 0, MODEL_NONE, NULL },
};

/**
 * Copy position, velocity, and angle variables from MarioState to the mario
 * object.
 */
void copy_mario_state_to_object(void) {
    s32 i = 0;
    // L is real
    if (gCurrentObject != gMarioObject) {
        i += 1;
    }

    gCurrentObject->oVelX = gMarioStates[i].vel[0];
    gCurrentObject->oVelY = gMarioStates[i].vel[1];
    gCurrentObject->oVelZ = gMarioStates[i].vel[2];

    gCurrentObject->oPosX = gMarioStates[i].pos[0];
    gCurrentObject->oPosY = gMarioStates[i].pos[1];
    gCurrentObject->oPosZ = gMarioStates[i].pos[2];

    gCurrentObject->oMoveAnglePitch = gCurrentObject->header.gfx.angle[0];
    gCurrentObject->oMoveAngleYaw = gCurrentObject->header.gfx.angle[1];
    gCurrentObject->oMoveAngleRoll = gCurrentObject->header.gfx.angle[2];

    gCurrentObject->oFaceAnglePitch = gCurrentObject->header.gfx.angle[0];
    gCurrentObject->oFaceAngleYaw = gCurrentObject->header.gfx.angle[1];
    gCurrentObject->oFaceAngleRoll = gCurrentObject->header.gfx.angle[2];

    gCurrentObject->oAngleVelPitch = gMarioStates[i].angleVel[0];
    gCurrentObject->oAngleVelYaw = gMarioStates[i].angleVel[1];
    gCurrentObject->oAngleVelRoll = gMarioStates[i].angleVel[2];
}

/**
 * Spawn a particle at gCurrentObject's location.
 */
void spawn_particle(u32 activeParticleFlag, s16 model, const BehaviorScript *behavior) {
    if (!(gCurrentObject->oActiveParticleFlags & activeParticleFlag)) {
        struct Object *particle;
        gCurrentObject->oActiveParticleFlags |= activeParticleFlag;
        particle = spawn_object_at_origin(gCurrentObject, 0, model, behavior);
        obj_copy_pos_and_angle(particle, gCurrentObject);
    }
}

/**
 * Mario's primary behavior update function.
 */
void bhv_mario_update(void) {
    u32 particleFlags = 0;
    s32 i;

    particleFlags = execute_mario_action(gCurrentObject);
    gCurrentObject->oMarioParticleFlags = particleFlags;

    // Mario code updates MarioState's versions of position etc, so we need
    // to sync it with the mario object
    copy_mario_state_to_object();

    i = 0;
    while (sParticleTypes[i].particleFlag != 0) {
        if (particleFlags & sParticleTypes[i].particleFlag) {
            spawn_particle(sParticleTypes[i].activeParticleFlag, sParticleTypes[i].model,
                           sParticleTypes[i].behavior);
        }

        i++;
    }
}

/**
 * Update every object that occurs after firstObj in the given object list,
 * including firstObj itself. Return the number of objects that were updated.
 */
s32 update_objects_starting_at(struct ObjectNode *objList, struct ObjectNode *firstObj) {
    s32 count = 0;

    while (objList != firstObj) {
        gCurrentObject = (struct Object *) firstObj;

        gCurrentObject->header.gfx.node.flags |= GRAPH_RENDER_HAS_ANIMATION;
        cur_obj_update();

        firstObj = firstObj->next;
        count += 1;
    }

    return count;
}

/**
 * Update objects in objList starting with firstObj while time stop is active.
 * This means that only certain select objects will be updated, such as mario,
 * doors, unimportant objects, and the object that initiated time stop.
 * The exact set of objects that are updated depends on which flags are set
 * in gTimeStopState.
 * Return the total number of objects in the list (including those that weren't
 * updated)
 */
s32 update_objects_during_time_stop(struct ObjectNode *objList, struct ObjectNode *firstObj) {
    s32 count = 0;
    s32 unfrozen;

    while (objList != firstObj) {
        gCurrentObject = (struct Object *) firstObj;

        unfrozen = FALSE;

        // Selectively unfreeze certain objects
        if (!(gTimeStopState & TIME_STOP_ALL_OBJECTS)) {
            if (gCurrentObject == gMarioObject && !(gTimeStopState & TIME_STOP_MARIO_AND_DOORS)) {
                unfrozen = TRUE;
            }

            if ((gCurrentObject->oInteractType & (INTERACT_DOOR | INTERACT_WARP_DOOR))
                && !(gTimeStopState & TIME_STOP_MARIO_AND_DOORS)) {
                unfrozen = TRUE;
            }

            if (gCurrentObject->activeFlags
                & (ACTIVE_FLAG_UNIMPORTANT | ACTIVE_FLAG_INITIATED_TIME_STOP)) {
                unfrozen = TRUE;
            }
        }

        // Only update if unfrozen
        if (unfrozen) {
            gCurrentObject->header.gfx.node.flags |= GRAPH_RENDER_HAS_ANIMATION;
            cur_obj_update();
        } else {
            gCurrentObject->header.gfx.node.flags &= ~GRAPH_RENDER_HAS_ANIMATION;
        }

        firstObj = firstObj->next;
        count++;
    }

    return count;
}

/**
 * Update every object in the given list. Return the total number of objects in
 * the list.
 */
s32 update_objects_in_list(struct ObjectNode *objList) {
    s32 count;
    struct ObjectNode *firstObj = objList->next;

    if (!(gTimeStopState & TIME_STOP_ACTIVE)) {
        count = update_objects_starting_at(objList, firstObj);
    } else {
        count = update_objects_during_time_stop(objList, firstObj);
    }

    return count;
}

/**
 * Unload any objects in the list that have been deactivated.
 */
s32 unload_deactivated_objects_in_list(struct ObjectNode *objList) {
    struct ObjectNode *obj = objList->next;

    while (objList != obj) {
        gCurrentObject = (struct Object *) obj;

        obj = obj->next;

        if ((gCurrentObject->activeFlags & ACTIVE_FLAG_ACTIVE) != ACTIVE_FLAG_ACTIVE) {
            // Prevent object from respawning after exiting and re-entering the
            // area
            if (!(gCurrentObject->oFlags & OBJ_FLAG_PERSISTENT_RESPAWN)) {
                set_object_respawn_info_bits(gCurrentObject, RESPAWN_INFO_DONT_RESPAWN);
            }

            unload_object(gCurrentObject);
        }
    }

    return 0;
}

/**
 * OR the object's respawn info with bits << 8. If bits = 0xFF, this prevents
 * the object from respawning after leaving and re-entering the area.
 * For macro objects, respawnInfo points to the 16 bit entry in the macro object
 * list. For other objects, it points to the 32 bit behaviorArg in the
 * SpawnInfo.
 */
void set_object_respawn_info_bits(struct Object *obj, u8 bits) {
    u32 *info32;
    u16 *info16;

    switch (obj->respawnInfoType) {
        case RESPAWN_INFO_TYPE_32:
            info32 = (u32 *) obj->respawnInfo;
            *info32 |= bits << 8;
            break;

        case RESPAWN_INFO_TYPE_16:
            info16 = (u16 *) obj->respawnInfo;
            *info16 |= bits << 8;
            break;
    }
}

/**
 * Unload all objects whose activeAreaIndex is areaIndex.
 */
void unload_objects_from_area(UNUSED s32 unused, s32 areaIndex) {
    struct Object *obj;
    struct ObjectNode *node;
    struct ObjectNode *list;
    s32 i;
    gObjectLists = gObjectListArray;

    for (i = 0; i < NUM_OBJ_LISTS; i++) {
        list = gObjectLists + i;
        node = list->next;

        while (node != list) {
            obj = (struct Object *) node;
            node = node->next;

            if (obj->header.gfx.unk19 == areaIndex) {
                unload_object(obj);
            }
        }
    }
}

/**
 * Spawn objects given a list of SpawnInfos. Called when loading an area.
 */
void spawn_objects_from_info(UNUSED s32 unused, struct SpawnInfo *spawnInfo) {
    gObjectLists = gObjectListArray;
    gTimeStopState = 0;

    gWDWWaterLevelChanging = FALSE;
    gMarioOnMerryGoRound = 0;

    //! (Spawning Displacement) On the Japanese version, mario's platform object
    //  isn't cleared when transitioning between areas. This can cause mario to
    //  receive displacement after spawning.
#ifndef VERSION_JP
    clear_mario_platform();
#endif

    if (gCurrAreaIndex == 2) {
        gCCMEnteredSlide |= 1;
    }

    while (spawnInfo != NULL) {
        struct Object *object;
        UNUSED s32 unused;
        const BehaviorScript *script;
        UNUSED s16 arg16 = (s16)(spawnInfo->behaviorArg & 0xFFFF);

        script = segmented_to_virtual(spawnInfo->behaviorScript);

        // If the object was previously killed/collected, don't respawn it
        if ((spawnInfo->behaviorArg & (RESPAWN_INFO_DONT_RESPAWN << 8))
            != (RESPAWN_INFO_DONT_RESPAWN << 8)) {
            object = create_object(script);

            // Behavior parameters are often treated as four separate bytes, but
            // are stored as an s32.
            object->oBehParams = spawnInfo->behaviorArg;
            // The second byte of the behavior parameters is copied over to a special field
            // as it is the most frequently used by objects.
            object->oBehParams2ndByte = ((spawnInfo->behaviorArg) >> 16) & 0xFF;

            object->behavior = script;
            object->unused1 = 0;

            // Record death/collection in the SpawnInfo
            object->respawnInfoType = RESPAWN_INFO_TYPE_32;
            object->respawnInfo = &spawnInfo->behaviorArg;

            if (spawnInfo->behaviorArg & 0x01) {
                gMarioObject = object;
                geo_make_first_child(&object->header.gfx.node);
            }

            geo_obj_init_spawninfo(&object->header.gfx, spawnInfo);

            object->oPosX = spawnInfo->startPos[0];
            object->oPosY = spawnInfo->startPos[1];
            object->oPosZ = spawnInfo->startPos[2];

            object->oFaceAnglePitch = spawnInfo->startAngle[0];
            object->oFaceAngleYaw = spawnInfo->startAngle[1];
            object->oFaceAngleRoll = spawnInfo->startAngle[2];

            object->oMoveAnglePitch = spawnInfo->startAngle[0];
            object->oMoveAngleYaw = spawnInfo->startAngle[1];
            object->oMoveAngleRoll = spawnInfo->startAngle[2];
        }

        spawnInfo = spawnInfo->next;
    }
}

void stub_obj_list_processor_1() {
}

/**
 * Clear objects, dynamic surfaces, and some miscellaneous level data used by
 * objects.
 */
void clear_objects(void) {
    s32 i;

    gTHIWaterDrained = 0;
    gTimeStopState = 0;
    gMarioObject = NULL;
    gMarioCurrentRoom = 0;

    for (i = 0; i < 60; i++) {
        gDoorAdjacentRooms[i][0] = 0;
        gDoorAdjacentRooms[i][1] = 0;
    }

    debug_unknown_level_select_check();

    init_free_object_list();
    clear_object_lists(gObjectListArray);

    stub_behavior_script_2();
    stub_obj_list_processor_1();

    for (i = 0; i < OBJECT_POOL_CAPACITY; i++) {
        gObjectPool[i].activeFlags = ACTIVE_FLAGS_DEACTIVATED;
        geo_reset_object_node(&gObjectPool[i].header.gfx);
    }

    gObjectMemoryPool = mem_pool_init(0x800, MEMORY_POOL_LEFT);
    gObjectLists = gObjectListArray;

    clear_dynamic_surfaces();
}

/**
 * Update spawner and surface objects.
 */
void update_terrain_objects(void) {
    gObjectCounter = update_objects_in_list(&gObjectLists[OBJ_LIST_SPAWNER]);
    //! This was meant to be +=
    gObjectCounter = update_objects_in_list(&gObjectLists[OBJ_LIST_SURFACE]);
}

/**
 * Update all other object lists besides spawner and surface objects, using
 * the order specified by sObjectListUpdateOrder.
 */
void update_non_terrain_objects(void) {
    UNUSED s32 unused;
    s32 listIndex;

    s32 i = 2;
    while ((listIndex = sObjectListUpdateOrder[i]) != -1) {
        gObjectCounter += update_objects_in_list(&gObjectLists[listIndex]);
        i += 1;
    }
}

/**
 * Unload deactivated objects in any object list.
 */
void unload_deactivated_objects(void) {
    UNUSED s32 unused;
    s32 listIndex;

    s32 i = 0;
    while ((listIndex = sObjectListUpdateOrder[i]) != -1) {
        unload_deactivated_objects_in_list(&gObjectLists[listIndex]);
        i += 1;
    }

    // TIME_STOP_UNKNOWN_0 was most likely intended to be used to track whether
    // any objects had been deactivated
    gTimeStopState &= ~TIME_STOP_UNKNOWN_0;
}

/**
 * Unused profiling function.
 */
static u16 unused_get_elapsed_time(u64 *cycleCounts, s32 index) {
    u16 time;
    f64 cycles;

    cycles = cycleCounts[index] - cycleCounts[index - 1];
    if (cycles < 0) {
        cycles = 0;
    }

    time = (u16)(((u64) cycles * 1000000 / osClockRate) / 16667.0 * 1000.0);
    if (time > 999) {
        time = 999;
    }

    return time;
}

/**
 * Update all objects. This includes script execution, object collision detection,
 * and object surface management.
 */
void update_objects(UNUSED s32 unused) {
    s64 cycleCounts[30];

    cycleCounts[0] = get_current_clock();

    gTimeStopState &= ~TIME_STOP_MARIO_OPENED_DOOR;

    gNumRoomedObjectsInMarioRoom = 0;
    gNumRoomedObjectsNotInMarioRoom = 0;
    gCheckingSurfaceCollisionsForCamera = FALSE;

    reset_debug_objectinfo();
    stub_debug_5();

    gObjectLists = gObjectListArray;

    // If time stop is not active, unload object surfaces
    cycleCounts[1] = get_clock_difference(cycleCounts[0]);
    clear_dynamic_surfaces();

    // Update spawners and objects with surfaces
    cycleCounts[2] = get_clock_difference(cycleCounts[0]);
    update_terrain_objects();

    // If mario was touching a moving platform at the end of last frame, apply
    // displacement now
    //! If the platform object unloaded and a different object took its place,
    //  displacement could be applied incorrectly
    apply_mario_platform_displacement();

    // Detect which objects are intersecting
    cycleCounts[3] = get_clock_difference(cycleCounts[0]);
    detect_object_collisions();

    // Update all other objects that haven't been updated yet
    cycleCounts[4] = get_clock_difference(cycleCounts[0]);
    update_non_terrain_objects();

    // Unload any objects that have been deactivated
    cycleCounts[5] = get_clock_difference(cycleCounts[0]);
    unload_deactivated_objects();

    // Check if mario is on a platform object and save this object
    cycleCounts[6] = get_clock_difference(cycleCounts[0]);
    update_mario_platform();

    cycleCounts[7] = get_clock_difference(cycleCounts[0]);

    cycleCounts[0] = 0;
    try_print_debug_mario_object_info();

    // If time stop was enabled this frame, activate it now so that it will
    // take effect next frame
    if (gTimeStopState & TIME_STOP_ENABLED) {
        gTimeStopState |= TIME_STOP_ACTIVE;
    } else {
        gTimeStopState &= ~TIME_STOP_ACTIVE;
    }

    gPrevFrameObjectCount = gObjectCounter;
}
