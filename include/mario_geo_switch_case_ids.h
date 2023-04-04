#ifndef MARIO_GEO_SWITCH_CASE_IDS_H
#define MARIO_GEO_SWITCH_CASE_IDS_H

/* Mario Geo-Switch-Case IDs */

enum MarioEyesGSCId {
    /*0x00*/ MARIO_EYES_BLINK,
    /*0x01*/ MARIO_EYES_OPEN,
    /*0x02*/ MARIO_EYES_HALF_CLOSED,
    /*0x03*/ MARIO_EYES_CLOSED,
    /*0x04*/ MARIO_EYES_LOOK_LEFT,  // unused
    /*0x05*/ MARIO_EYES_LOOK_RIGHT, // unused
    /*0x06*/ MARIO_EYES_LOOK_UP,    // unused
    /*0x07*/ MARIO_EYES_LOOK_DOWN,  // unused
    /*0x08*/ MARIO_EYES_DEAD
};

enum MarioHandGSCId {
    /*0x00*/ MARIO_HAND_FISTS,
    /*0x01*/ MARIO_HAND_OPEN,
    /*0x02*/ MARIO_HAND_PEACE_SIGN,
    /*0x03*/ MARIO_HAND_HOLDING_CAP,
    /*0x04*/ MARIO_HAND_HOLDING_WING_CAP,
    /*0x05*/ MARIO_HAND_RIGHT_OPEN
};

enum MarioCapGSCId {
    /*0x00*/ MARIO_HAS_DEFAULT_CAP_ON,
    /*0x01*/ MARIO_HAS_DEFAULT_CAP_OFF,
    /*0x02*/ MARIO_HAS_WING_CAP_ON,
    /*0x03*/ MARIO_HAS_WING_CAP_OFF // unused
};

enum MarioGrabPosGSCId {
    /*0x00*/ GRAB_POS_NULL,
    /*0x01*/ GRAB_POS_LIGHT_OBJ,
    /*0x02*/ GRAB_POS_HEAVY_OBJ,
    /*0x03*/ GRAB_POS_BOWSER
};

#endif // MARIO_GEO_SWITCH_CASE_IDS_H
