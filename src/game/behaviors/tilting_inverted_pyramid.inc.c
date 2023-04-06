/**
 * This is the behavior file for the tilting inverted pyramids in BitFS/LLL.
 * The object essentially just tilts and moves Mario with it.
 */

/**
 * Creates a transform matrix on a variable passed in from given normals
 * and the object's position.
 */
void create_transform_from_normals(Mat4 transform, f32 xNorm, f32 yNorm, f32 zNorm) {
    Vec3f normal;
    Vec3f pos;

    pos[0] = o->oPosX;
    pos[1] = o->oPosY;
    pos[2] = o->oPosZ;

    normal[0] = xNorm;
    normal[1] = yNorm;
    normal[2] = zNorm;

    mtxf_align_terrain_normal(transform, normal, pos, 0);
}

/**
 * Initialize the object's transform matrix with Y being up.
 */
void bhv_platform_normals_init(void) {
    Mat4 *transform = &o->transform;

    o->oTiltingPyramidNormalX = 0.0f;
    o->oTiltingPyramidNormalY = 1.0f;
    o->oTiltingPyramidNormalZ = 0.0f;

    create_transform_from_normals(*transform, 0.0f, 1.0f, 0.0f);
}

/**
 * Returns a value that is src incremented/decremented by inc towards goal
 * until goal is reached. Does not overshoot.
 */
f32 approach_by_increment(f32 goal, f32 src, f32 inc) {
    f32 newVal;

    if (src <= goal) {
        if (goal - src < inc) {
            newVal = goal;
        } else {
            newVal = src + inc;
        }
    } else if (goal - src > -inc) {
        newVal = goal;
    } else {
        newVal = src - inc;
    }

    return newVal;
}

/**
 * Main behavior for the tilting pyramids in LLL/BitFS. These platforms calculate rough normals from Mario's position,
 * then gradually tilt back moving Mario with them.
 */
void bhv_tilting_inverted_pyramid_loop(void) {
    f32 dx;
    f32 dy;
    f32 dz;
    f32 d;

    Vec3f dist;
    Vec3f posBeforeRotation;
    Vec3f posAfterRotation;

    // Mario's position
    f32 mx;
    f32 my;
    f32 mz;

    s32 marioOnPlatform = FALSE;
    UNUSED s32 unused;
    Mat4 *transform = &o->transform;
    UNUSED s32 unused2[7];

    if (gMarioObject->platform == o) {
        get_mario_pos(&mx, &my, &mz);

        dist[0] = gMarioObject->oPosX - o->oPosX;
        dist[1] = gMarioObject->oPosY - o->oPosY;
        dist[2] = gMarioObject->oPosZ - o->oPosZ;
        linear_mtxf_mul_vec3f(*transform, posBeforeRotation, dist);

        dx = gMarioObject->oPosX - o->oPosX;
        dy = 500.0f;
        dz = gMarioObject->oPosZ - o->oPosZ;
        d = sqrtf(dx * dx + dy * dy + dz * dz);
    
        //! Always true since dy = 500, making d >= 500.
        if (d != 0.0f) {
            // Normalizing
            d = 1.0 / d;
            dx *= d;
            dy *= d;
            dz *= d;
        } else {
            dx = 0.0f;
            dy = 1.0f;
            dz = 0.0f;
        }

        if (o->oTiltingPyramidMarioOnPlatform == TRUE)
            marioOnPlatform++;

        o->oTiltingPyramidMarioOnPlatform = TRUE;
    } else {
        dx = 0.0f;
        dy = 1.0f;
        dz = 0.0f;
        o->oTiltingPyramidMarioOnPlatform = FALSE;
    }

    // Approach the normals by 0.01f towards the new goal, then create a transform matrix and orient the object. 
    // Outside of the other conditionals since it needs to tilt regardless of whether Mario is on.
    o->oTiltingPyramidNormalX = approach_by_increment(dx, o->oTiltingPyramidNormalX, 0.01f);
    o->oTiltingPyramidNormalY = approach_by_increment(dy, o->oTiltingPyramidNormalY, 0.01f);
    o->oTiltingPyramidNormalZ = approach_by_increment(dz, o->oTiltingPyramidNormalZ, 0.01f);
    create_transform_from_normals(*transform, o->oTiltingPyramidNormalX, o->oTiltingPyramidNormalY, o->oTiltingPyramidNormalZ);

    // If Mario is on the platform, adjust his position for the platform tilt.
    if (marioOnPlatform != FALSE) {
        linear_mtxf_mul_vec3f(*transform, posAfterRotation, dist);
        mx += posAfterRotation[0] - posBeforeRotation[0];
        my += posAfterRotation[1] - posBeforeRotation[1];
        mz += posAfterRotation[2] - posBeforeRotation[2];
        set_mario_pos(mx, my, mz);
    }

    o->header.gfx.throwMatrix = transform;
}
