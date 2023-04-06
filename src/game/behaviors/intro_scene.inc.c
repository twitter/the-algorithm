// intro_scene.inc.c

void spawn_child_obj_relative(struct Object *parent, s16 xOffset, s16 yOffset, s16 zOffset, s16 pitchOffset,
                   s16 yawOffset, s16 rollOffset, s16 forwardVel,
                   s32 model, const BehaviorScript *behavior) {
    struct Object *sp1C = spawn_object(parent, model, behavior);

    sp1C->header.gfx.unk38.animFrame = random_float() * 6.f;
    sp1C->oEndBirdUnk104 = sCutsceneVars[9].point[0];
    sCutsceneVars[9].point[0] += 1.f;
    sp1C->oPosX += xOffset;
    sp1C->oPosY += yOffset;
    if (gCutsceneTimer > 700)
        sp1C->oPosY += -150.f;
    sp1C->oPosZ += zOffset;
    sp1C->oMoveAnglePitch += pitchOffset;
    sp1C->oMoveAngleYaw += yawOffset;
    sp1C->oMoveAngleRoll += rollOffset;
    sp1C->oForwardVel = forwardVel;
}

void bhv_intro_scene_loop(void) {
    UNUSED struct Object *sp34;

    if (gCutsceneObjSpawn != 0) {
        gCurrentObject->oPosX = gCamera->pos[0];
        gCurrentObject->oPosY = gCamera->pos[1];
        gCurrentObject->oPosZ = gCamera->pos[2];
        gCurrentObject->oMoveAnglePitch = 0;
        gCurrentObject->oMoveAngleYaw = 0;

        switch (gCutsceneObjSpawn) {
            case 6:
                sp34 = spawn_object(gCurrentObject, MODEL_LAKITU, bhvBeginningLakitu);
                break;
            case 5:
                sp34 = spawn_object(gCurrentObject, MODEL_PEACH, bhvBeginningPeach);
                break;
            case 7:
                spawn_child_obj_relative(gCurrentObject, 0, 205, 500, 0x1000, 0x6000, -0x1E00, 25, MODEL_BIRDS,
                              bhvEndBirds1);
                spawn_child_obj_relative(gCurrentObject, 0, 205, 800, 0x1800, 0x6000, -0x1400, 35, MODEL_BIRDS,
                              bhvEndBirds1);
                spawn_child_obj_relative(gCurrentObject, -100, 300, 500, 0x800, 0x6000, 0, 25, MODEL_BIRDS,
                              bhvEndBirds1);
                spawn_child_obj_relative(gCurrentObject, 100, -200, 800, 0, 0x4000, 0x1400, 45, MODEL_BIRDS,
                              bhvEndBirds1);
                spawn_child_obj_relative(gCurrentObject, -80, 300, 350, 0x1800, 0x5000, 0xA00, 35, MODEL_BIRDS,
                              bhvEndBirds1);
                spawn_child_obj_relative(gCurrentObject, -300, 300, 500, 0x800, 0x6000, 0x2800, 25, MODEL_BIRDS,
                              bhvEndBirds1);
                spawn_child_obj_relative(gCurrentObject, -400, -200, 800, 0, 0x4000, -0x1400, 45, MODEL_BIRDS,
                              bhvEndBirds1);
                break;
            case 9:
                spawn_child_obj_relative(gCurrentObject, 50, 205, 500, 0x1000, 0x6000, 0, 35, MODEL_BIRDS,
                              bhvEndBirds1);
                spawn_child_obj_relative(gCurrentObject, 0, 285, 800, 0x1800, 0x6000, 0, 35, MODEL_BIRDS,
                              bhvEndBirds1);
                break;
            case 8:
                spawn_child_obj_relative(gCurrentObject, -100, -100, -700, 0, 0, -0xF00, 25, MODEL_BIRDS,
                              bhvEndBirds2);
                spawn_child_obj_relative(gCurrentObject, -250, 255, -200, 0, 0, -0x1400, 25, MODEL_BIRDS,
                              bhvEndBirds2);
                spawn_child_obj_relative(gCurrentObject, -100, 155, -600, 0, 0, -0x500, 35, MODEL_BIRDS,
                              bhvEndBirds2);
                spawn_child_obj_relative(gCurrentObject, 250, 200, -1200, 0, 0, -0x700, 25, MODEL_BIRDS,
                              bhvEndBirds2);
                spawn_child_obj_relative(gCurrentObject, -250, 255, -700, 0, 0, 0, 25, MODEL_BIRDS, bhvEndBirds2);
                break;
        }

        gCutsceneObjSpawn = 0;
    }
}
