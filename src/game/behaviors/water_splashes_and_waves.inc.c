// water_splashes_and_waves.c.inc

// Water droplets from Mario jumping in a pool of water.
struct WaterDropletParams sWaterSplashDropletParams = {
    /* Flags */ WATER_DROPLET_FLAG_RAND_ANGLE,
    /* Model */ MODEL_WHITE_PARTICLE_SMALL,
    /* Behavior */ bhvWaterDroplet,
    /* Unused (flag-specific) */ 0, 0,
    /* Random fvel offset, scale */ 5.0f, 3.0f,
    /* Random yvel offset, scale */ 30.0f, 20.0f,
    /* Random size offset, scale */ 0.5f, 1.0f
};

// Water droplets from Mario jumping in shallow water.
struct WaterDropletParams sShallowWaterSplashDropletParams = {
    /* Flags */ WATER_DROPLET_FLAG_RAND_ANGLE | WATER_DROPLET_FLAG_SET_Y_TO_WATER_LEVEL,
    /* Model */ MODEL_WHITE_PARTICLE_SMALL,
    /* Behavior */ bhvWaterDroplet,
    /* Unused (flag-specific) */ 0, 0,
    /* Random fvel offset, scale */ 2.0f, 3.0f,
    /* Random yvel offset, scale */ 20.0f, 20.0f,
    /* Random size offset, scale */ 0.5f, 1.0f
};

// The fish particle easter egg from Mario jumping in shallow water.
struct WaterDropletParams sWaterDropletFishParams = {
    /* Flags */ WATER_DROPLET_FLAG_RAND_ANGLE | WATER_DROPLET_FLAG_SET_Y_TO_WATER_LEVEL,
    /* Model */ MODEL_FISH,
    /* Behavior */ bhvWaterDroplet,
    /* Unused (flag-specific) */ 0, 0,
    /* Random fvel offset, scale */ 2.0f, 3.0f,
    /* Random yvel offset, scale */ 20.0f, 20.0f,
    /* Random size offset, scale */ 1.0f, 0.0f
};

// Water droplets from Mario running in shallow water.
struct WaterDropletParams sShallowWaterWaveDropletParams = {
    /* Flags */ WATER_DROPLET_FLAG_RAND_ANGLE_INCR_PLUS_8000 | WATER_DROPLET_FLAG_RAND_ANGLE | WATER_DROPLET_FLAG_SET_Y_TO_WATER_LEVEL,
    /* Model */ MODEL_WHITE_PARTICLE_SMALL, 
    /* Behavior */ bhvWaterDroplet, 
    /* Move angle range */ 0x6000,
    /* Unused (flag-specific) */ 0,
    /* Random fvel offset, scale */ 2.0f, 8.0f,
    /* Random yvel offset, scale */ 10.0f, 10.0f,
    /* Random size offset, scale */ 0.5f, 1.0f
};


void bhv_water_splash_spawn_droplets(void) {
    s32 i;
    if (o->oTimer == 0)
        o->oPosY = find_water_level(o->oPosX, o->oPosZ);
    
    if (o->oPosY > -10000.0f) // Make sure it is not at the default water level
        for (i = 0; i < 3; i++)
            spawn_water_droplet(o, &sWaterSplashDropletParams);
}

void bhv_water_droplet_loop(void) {
    UNUSED u32 unusedVar;
    f32 waterLevel = find_water_level(o->oPosX, o->oPosZ);
    
    if (o->oTimer == 0) {
        if (cur_obj_has_model(MODEL_FISH))
            o->header.gfx.node.flags &= ~GRAPH_RENDER_BILLBOARD;
        else
            o->header.gfx.node.flags |= GRAPH_RENDER_BILLBOARD;
        o->oFaceAngleYaw = random_u16();
    }
    // Apply gravity
    o->oVelY -= 4.0f;
    o->oPosY += o->oVelY;
    // Check if fallen back into the water
    if (o->oVelY < 0.0f) {
        if (waterLevel > o->oPosY) {
            // Create the smaller splash
            try_to_spawn_object(0, 1.0f, o, MODEL_SMALL_WATER_SPLASH, bhvWaterDropletSplash);
            obj_mark_for_deletion(o);
        } else if (o->oTimer > 20)
            obj_mark_for_deletion(o);
    }
    if (waterLevel < -10000.0f)
        obj_mark_for_deletion(o);
}

void bhv_idle_water_wave_loop(void) {
    obj_copy_pos(o, gMarioObject);
    o->oPosY = gMarioStates->waterLevel + 5;
    if (!(gMarioObject->oMarioParticleFlags & ACTIVE_PARTICLE_IDLE_WATER_WAVE)) {
        gMarioObject->oActiveParticleFlags &= (u16)~ACTIVE_PARTICLE_IDLE_WATER_WAVE;
        o->activeFlags = 0;
    }
}

void bhv_water_droplet_splash_init(void) {
    cur_obj_scale(random_float() + 1.5);
}

void bhv_bubble_splash_init(void) {
    f32 waterLevel = find_water_level(o->oPosX, o->oPosZ);
    obj_scale_xyz(o, 0.5f, 1.0f, 0.5f);
    o->oPosY = waterLevel + 5.0f;
}

void bhv_shallow_water_splash_init(void) {
    struct Object *fishObj;
    // Have a 1 in 256 chance to spawn the fish particle easter egg.
    if ((random_u16() & 0xFF) <= 0) // Strange
    {
        fishObj = spawn_water_droplet(o, &sWaterDropletFishParams);
        obj_init_animation_with_sound(fishObj, blue_fish_seg3_anims_0301C2B0, 0);
    }
}

void bhv_wave_trail_shrink(void) {
    f32 waterLevel = find_water_level(o->oPosX, o->oPosZ);
    // Destroy every other water wave to space them out (this is a terrible way of doing it)
    if (o->oTimer == 0)
        if (gGlobalTimer & 1)
            obj_mark_for_deletion(o);
    o->oPosY = waterLevel + 5.0f;
    
    if (o->oTimer == 0)
        o->oWaveTrailSize = o->header.gfx.scale[0];
    
    if (o->oAnimState > 3) {
        o->oWaveTrailSize = o->oWaveTrailSize - 0.1; // Shrink the wave
        if (o->oWaveTrailSize < 0.0f)
            o->oWaveTrailSize = 0.0f;
        o->header.gfx.scale[0] = o->oWaveTrailSize;
        o->header.gfx.scale[2] = o->oWaveTrailSize;
    }
}
