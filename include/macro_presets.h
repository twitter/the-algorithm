#ifndef _MACRO_PRESETS_H
#define _MACRO_PRESETS_H

#include "macro_preset_names.h"
#include "behavior_data.h"
#include "model_ids.h"

struct MacroPreset
{
    /*0x00*/ const BehaviorScript *behavior;
    /*0x04*/ s16 model;
    /*0x06*/ s16 param;
};

struct MacroPreset MacroObjectPresets[] = {
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvOneCoin, MODEL_YELLOW_COIN, 0},
    {bhvMovingBlueCoin, MODEL_BLUE_COIN, 0},
    {bhvBlueCoinSliding, MODEL_BLUE_COIN, 0}, // unused
    {bhvRedCoin, MODEL_RED_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvCoinFormation, MODEL_NONE, 0},
    {bhvCoinFormation, MODEL_NONE, COIN_FORMATION_FLAG_RING},
    {bhvCoinFormation, MODEL_NONE, COIN_FORMATION_FLAG_ARROW},
    {bhvCoinFormation, MODEL_NONE, COIN_FORMATION_FLAG_FLYING},
    {bhvCoinFormation, MODEL_NONE, COIN_FORMATION_FLAG_FLYING | COIN_FORMATION_FLAG_VERTICAL},
    {bhvCoinFormation, MODEL_NONE, COIN_FORMATION_FLAG_FLYING | COIN_FORMATION_FLAG_RING},
    {bhvCoinFormation, MODEL_NONE, COIN_FORMATION_FLAG_FLYING | COIN_FORMATION_FLAG_RING | COIN_FORMATION_FLAG_VERTICAL},
    {bhvCoinFormation, MODEL_NONE, COIN_FORMATION_FLAG_FLYING | COIN_FORMATION_FLAG_ARROW}, // unused
    {bhvHiddenStarTrigger, MODEL_NONE, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvUnusedFakeStar, MODEL_STAR, 0}, // unused
    {bhvMessagePanel, MODEL_WOODEN_SIGNPOST, 0},
    {bhvCannonClosed, MODEL_DL_CANNON_LID, 0},
    {bhvBobombBuddyOpensCannon, MODEL_BOBOMB_BUDDY, 0},
    {bhvButterfly, MODEL_BUTTERFLY, 0}, // unused
    {bhvBouncingFireball, MODEL_NONE, 0}, // unused
    {bhvLargeFishGroup, MODEL_NONE, 0}, // unused
    {bhvLargeFishGroup, MODEL_NONE, 1},
    {bhvBetaFishSplashSpawner, MODEL_NONE, 0},
    {bhvHidden1upInPoleSpawner, MODEL_NONE, 0},
    {bhvGoomba, MODEL_GOOMBA, 1},
    {bhvGoomba, MODEL_GOOMBA, 2},
    {bhvGoombaTripletSpawner, MODEL_NONE, 0},
    {bhvGoombaTripletSpawner, MODEL_NONE, 8}, // unused
    {bhvSignOnWall, MODEL_NONE, 0},
    {bhvChuckya, MODEL_CHUCKYA, 0},
    {bhvCannon, MODEL_CANNON_BASE, 0},
    {bhvGoomba, MODEL_GOOMBA, 0},
    {bhvHomingAmp, MODEL_AMP, 0},
    {bhvCirclingAmp, MODEL_AMP, 0},
    {bhvCarrySomething1, MODEL_UNKNOWN_7D, 0}, // unused
    {bhvBetaTrampolineTop, MODEL_TRAMPOLINE, 0}, // unused
    {bhvFreeBowlingBall, MODEL_BOWLING_BALL, 0}, // unused
    {bhvSnufit, MODEL_SNUFIT, 0},
    {bhvRecoveryHeart, MODEL_HEART, 0},
    {bhv1upSliding, MODEL_1UP, 0},
    {bhv1Up, MODEL_1UP, 0},
    {bhv1upJumpOnApproach, MODEL_1UP, 0}, // unused
    {bhvHidden1up, MODEL_1UP, 0},
    {bhvHidden1upTrigger, MODEL_NONE, 0},
    {bhv1Up, MODEL_1UP, 1},
    {bhv1Up, MODEL_1UP, 2},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvBlueCoinSwitch, MODEL_BLUE_COIN_SWITCH, 0},
    {bhvHiddenBlueCoin, MODEL_BLUE_COIN, 0},
    {bhvCapSwitch, MODEL_CAP_SWITCH, 0}, // unused
    {bhvCapSwitch, MODEL_CAP_SWITCH, 1}, // unused
    {bhvCapSwitch, MODEL_CAP_SWITCH, 2}, // unused
    {bhvCapSwitch, MODEL_CAP_SWITCH, 3}, // unused
    {bhvWaterLevelDiamond, MODEL_BREAKABLE_BOX, 0}, // unused
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 0},
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 1},
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 2},
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 3},
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 4}, // unused
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 5},
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 6},
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 7},
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 8},
    {bhvBreakableBox, MODEL_BREAKABLE_BOX, 0},
    {bhvBreakableBox, MODEL_BREAKABLE_BOX, 1},
    {bhvPushableMetalBox, MODEL_METAL_BOX, 0},
    {bhvBreakableBoxSmall, MODEL_BREAKABLE_BOX_SMALL, 0},
    {bhvFloorSwitchHiddenObjects, MODEL_PURPLE_SWITCH, 0},
    {bhvHiddenObject, MODEL_BREAKABLE_BOX, 0},
    {bhvHiddenObject, MODEL_BREAKABLE_BOX, 1}, // unused
    {bhvHiddenObject, MODEL_BREAKABLE_BOX, 2}, // unused
    {bhvBreakableBox, MODEL_BREAKABLE_BOX, 3},
    {bhvKoopaShellUnderwater, MODEL_KOOPA_SHELL, 0},
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 9},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvBulletBill, MODEL_BULLET_BILL, 0}, // unused
    {bhvHeaveHo, MODEL_HEAVE_HO, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvThwomp2, MODEL_THWOMP, 0}, // unused
    {bhvFireSpitter, MODEL_BOWLING_BALL, 0},
    {bhvFlyGuy, MODEL_FLYGUY, 1},
    {bhvJumpingBox, MODEL_BREAKABLE_BOX, 0},
    {bhvTripletButterfly, MODEL_BUTTERFLY, 0},
    {bhvTripletButterfly, MODEL_BUTTERFLY, 4},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvSmallBully, MODEL_BULLY, 0},
    {bhvSmallBully, MODEL_BULLY_BOSS, 0}, // unused
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvStub1D0C, MODEL_UNKNOWN_58, 0}, // unused
    {bhvBouncingFireball, MODEL_NONE, 0},
    {bhvFlamethrower, MODEL_NONE, 4},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvWoodenPost, MODEL_WOODEN_POST, 0},
    {bhvWaterBombSpawner, MODEL_NONE, 0},
    {bhvEnemyLakitu, MODEL_ENEMY_LAKITU, 0},
    {bhvKoopa, MODEL_KOOPA_WITH_SHELL, 2}, // unused
    {bhvKoopaRaceEndpoint, MODEL_NONE, 0}, // unused
    {bhvBobomb, MODEL_BLACK_BOBOMB, 0},
    {bhvWaterBombCannon, MODEL_CANNON_BASE, 0}, // unused
    {bhvBobombBuddyOpensCannon, MODEL_BOBOMB_BUDDY, 0}, // unused
    {bhvWaterBombCannon, MODEL_CANNON_BASE, 0},
    {bhvBobomb, MODEL_BLACK_BOBOMB, 1},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvUnusedFakeStar, MODEL_UNKNOWN_54, 0}, // unused
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvUnagi, MODEL_UNAGI, 0}, // unused
    {bhvSushiShark, MODEL_SUSHI, 0}, // unused
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvStaticObject, MODEL_KLEPTO, 0}, // unused
    {bhvTweester, MODEL_TWEESTER, 0}, // unused
    {bhvPokey, MODEL_NONE, 0},
    {bhvPokey, MODEL_NONE, 0}, // unused
    {bhvToxBox, MODEL_SSL_TOX_BOX, 0}, // unused
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvMontyMole, MODEL_MONTY_MOLE, 0}, // unused
    {bhvMontyMole, MODEL_MONTY_MOLE, 1},
    {bhvMontyMoleHole, MODEL_DL_MONTY_MOLE_HOLE, 0},
    {bhvFlyGuy, MODEL_FLYGUY, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvWigglerHead, MODEL_WIGGLER_HEAD, 0}, // unused
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvSpindrift, MODEL_SPINDRIFT, 0},
    {bhvMrBlizzard, MODEL_MR_BLIZZARD_HIDDEN, 0},
    {bhvMrBlizzard, MODEL_MR_BLIZZARD_HIDDEN, 0}, // unused
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvSmallPenguin, MODEL_PENGUIN, 0}, // unused
    {bhvTuxiesMother, MODEL_PENGUIN, 0}, // unused
    {bhvTuxiesMother, MODEL_PENGUIN, 0}, // unused
    {bhvMrBlizzard, MODEL_MR_BLIZZARD_HIDDEN, 1}, // unused
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvHauntedChair, MODEL_HAUNTED_CHAIR, 0}, // unused
    {bhvHauntedChair, MODEL_HAUNTED_CHAIR, 0},
    {bhvHauntedChair, MODEL_HAUNTED_CHAIR, 0}, // unused
    {bhvGhostHuntBoo, MODEL_BOO, 0}, // unused
    {bhvGhostHuntBoo, MODEL_BOO, 0}, // unused
    {bhvCourtyardBooTriplet, MODEL_BOO, 0}, // unused
    {bhvBooWithCage, MODEL_BOO, 0}, // unused
    {bhvAlphaBooKey, MODEL_BETA_BOO_KEY, 0}, // unused
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvChirpChirp, MODEL_NONE, 0},
    {bhvSeaweedBundle, MODEL_NONE, 0},
    {bhvBetaChestBottom, MODEL_TREASURE_CHEST_BASE, 0}, // unused
    {bhvBowserBomb, MODEL_WATER_MINE, 0}, // unused
    {bhvLargeFishGroup, MODEL_NONE, 2}, // unused
    {bhvLargeFishGroup, MODEL_NONE, 3},
    {bhvJetStreamRingSpawner, MODEL_WATER_RING, 0}, // unused
    {bhvJetStreamRingSpawner, MODEL_WATER_RING, 0}, // unused
    {bhvSkeeter, MODEL_SKEETER, 0},
    {bhvClamShell, MODEL_CLAM_SHELL, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvMacroUkiki, MODEL_UKIKI, 0}, // unused
    {bhvMacroUkiki, MODEL_UKIKI, 1}, // unused
    {bhvPiranhaPlant, MODEL_PIRANHA_PLANT, 0}, // unused
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvSmallWhomp, MODEL_WHOMP, 0},
    {bhvChainChomp, MODEL_CHAIN_CHOMP, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvKoopa, MODEL_KOOPA_WITH_SHELL, 1},
    {bhvKoopa, MODEL_KOOPA_WITHOUT_SHELL, 0}, // unused
    {bhvWoodenPost, MODEL_WOODEN_POST, 0}, // unused
    {bhvFirePiranhaPlant, MODEL_PIRANHA_PLANT, 0},
    {bhvFirePiranhaPlant, MODEL_PIRANHA_PLANT, 1}, // unused
    {bhvKoopa, MODEL_KOOPA_WITH_SHELL, 4},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvMoneybagHidden, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvSwoop, MODEL_SWOOP, 0},
    {bhvSwoop, MODEL_SWOOP, 1},
    {bhvMrI, MODEL_NONE, 0},
    {bhvScuttlebugSpawn, MODEL_NONE, 0},
    {bhvScuttlebug, MODEL_SCUTTLEBUG, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_UNKNOWN_54, 0}, // unused
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvTTCRotatingSolid, MODEL_TTC_ROTATING_CUBE, 0},
    {bhvTTCRotatingSolid, MODEL_TTC_ROTATING_PRISM, 1},
    {bhvTTCPendulum, MODEL_TTC_PENDULUM, 0},
    {bhvTTCTreadmill, MODEL_TTC_LARGE_TREADMILL, 0},
    {bhvTTCTreadmill, MODEL_TTC_SMALL_TREADMILL, 1},
    {bhvTTCMovingBar, MODEL_TTC_PUSH_BLOCK, 0},
    {bhvTTCCog, MODEL_TTC_ROTATING_HEXAGON, 0},
    {bhvTTCCog, MODEL_TTC_ROTATING_TRIANGLE, 2},
    {bhvTTCPitBlock, MODEL_TTC_PIT_BLOCK, 0},
    {bhvTTCPitBlock, MODEL_TTC_PIT_BLOCK_UNUSED, 1}, // unused
    {bhvTTCElevator, MODEL_TTC_ELEVATOR_PLATFORM, 0},
    {bhvTTC2DRotator, MODEL_TTC_CLOCK_HAND, 0},
    {bhvTTCSpinner, MODEL_TTC_SPINNER, 0},
    {bhvTTC2DRotator, MODEL_TTC_SMALL_GEAR, 1},
    {bhvTTC2DRotator, MODEL_TTC_LARGE_GEAR, 1},
    {bhvTTCTreadmill, MODEL_TTC_LARGE_TREADMILL, 2},
    {bhvTTCTreadmill, MODEL_TTC_SMALL_TREADMILL, 3},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 10},
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 11},
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 12},
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 13}, // unused
    {bhvExclamationBox, MODEL_EXCLAMATION_BOX, 14},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvSlidingPlatform2, MODEL_BITS_SLIDING_PLATFORM, 0}, // unused
    {bhvSlidingPlatform2, MODEL_BITS_TWIN_SLIDING_PLATFORMS, 0}, // unused
    {bhvAnotherTiltingPlatform, MODEL_BITDW_SLIDING_PLATFORM, 0}, // unused
    {bhvOctagonalPlatformRotating, MODEL_BITS_OCTAGONAL_PLATFORM, 0}, // unused
    {bhvAnimatesOnFloorSwitchPress, MODEL_BITS_STAIRCASE, 0}, // unused
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvFerrisWheelAxle, MODEL_BITS_FERRIS_WHEEL_AXLE, 0}, // unused
    {bhvActivatedBackAndForthPlatform, MODEL_BITS_ARROW_PLATFORM, 0}, // unused
    {bhvSeesawPlatform, MODEL_BITS_SEESAW_PLATFORM, 0}, // unused
    {bhvSeesawPlatform, MODEL_BITS_TILTING_W_PLATFORM, 0}, // unused
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0},
    {bhvYellowCoin, MODEL_YELLOW_COIN, 0}
};

#endif // _MACRO_PRESETS_H
