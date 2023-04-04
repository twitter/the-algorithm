
/*====================================================================
 * os_cont.h
 *
 * Copyright 1995, Silicon Graphics, Inc.
 * All Rights Reserved.
 *
 * This is UNPUBLISHED PROPRIETARY SOURCE CODE of Silicon Graphics,
 * Inc.; the contents of this file may not be disclosed to third
 * parties, copied or duplicated in any form, in whole or in part,
 * without the prior written permission of Silicon Graphics, Inc.
 *
 * RESTRICTED RIGHTS LEGEND:
 * Use, duplication or disclosure by the Government is subject to
 * restrictions as set forth in subdivision (c)(1)(ii) of the Rights
 * in Technical Data and Computer Software clause at DFARS
 * 252.227-7013, and/or in similar or successor clauses in the FAR,
 * DOD or NASA FAR Supplement. Unpublished - rights reserved under the
 * Copyright Laws of the United States.
 *====================================================================*/

/*---------------------------------------------------------------------*
        Copyright (C) 1998 Nintendo. (Originated by SGI)
        
        $RCSfile: os_cont.h,v $
        $Revision: 1.1 $
        $Date: 1998/10/09 08:01:05 $
 *---------------------------------------------------------------------*/

#ifndef _OS_CONT_H_
#define	_OS_CONT_H_

#ifdef _LANGUAGE_C_PLUS_PLUS
extern "C" {
#endif

#include <PR/ultratypes.h>
#include "os_message.h"


#if defined(_LANGUAGE_C) || defined(_LANGUAGE_C_PLUS_PLUS)

/**************************************************************************
 *
 * Type definitions
 *
 */

/*
 * Structure for controllers 
 */

typedef struct {
	u16     type;                   /* Controller Type */
	u8      status;                 /* Controller status */
	u8	errnum;
}OSContStatus;

typedef struct {
	u16     button;
	s8      stick_x;		/* -80 <= stick_x <= 80 */
	s8      stick_y;		/* -80 <= stick_y <= 80 */
	u8	errnum;
} OSContPad;

typedef struct {
	void    *address;               /* Ram pad Address:  11 bits */
	u8      databuffer[32];         /* address of the data buffer */
        u8      addressCrc;             /* CRC code for address */
	u8      dataCrc;                /* CRC code for data */
	u8	errnum;
} OSContRamIo;


#endif /* defined(_LANGUAGE_C) || defined(_LANGUAGE_C_PLUS_PLUS) */

/**************************************************************************
 *
 * Global definitions
 *
 */

/*
 *  Controllers  number
 */

#ifndef _HW_VERSION_1
#define MAXCONTROLLERS  4
#else
#define MAXCONTROLLERS  6
#endif

/* controller errors */
#define CONT_NO_RESPONSE_ERROR          0x8
#define CONT_OVERRUN_ERROR              0x4
#ifdef _HW_VERSION_1
#define CONT_FRAME_ERROR                0x2
#define CONT_COLLISION_ERROR            0x1
#endif 

/* Controller type */

#define CONT_ABSOLUTE           0x0001
#define CONT_RELATIVE           0x0002
#define CONT_JOYPORT            0x0004
#define CONT_EEPROM		0x8000
#define CONT_EEP16K		0x4000
#define	CONT_TYPE_MASK		0x1f07
#define	CONT_TYPE_NORMAL	0x0005
#define	CONT_TYPE_MOUSE		0x0002
#define	CONT_TYPE_VOICE		0x0100

/* Controller status */

#define CONT_CARD_ON            0x01
#define CONT_CARD_PULL          0x02
#define CONT_ADDR_CRC_ER        0x04
#define CONT_EEPROM_BUSY	0x80

/* Buttons */

#define CONT_A      0x8000
#define CONT_B      0x4000
#define CONT_G	    0x2000
#define CONT_START  0x1000
#define CONT_UP     0x0800
#define CONT_DOWN   0x0400
#define CONT_LEFT   0x0200
#define CONT_RIGHT  0x0100
#define CONT_L      0x0020
#define CONT_R      0x0010
#define CONT_E      0x0008
#define CONT_D      0x0004
#define CONT_C      0x0002
#define CONT_F      0x0001

/* Nintendo's official button names */

#define A_BUTTON	CONT_A
#define B_BUTTON	CONT_B
#define L_TRIG		CONT_L
#define R_TRIG		CONT_R
#define Z_TRIG		CONT_G
#define START_BUTTON	CONT_START
#define U_JPAD		CONT_UP
#define L_JPAD		CONT_LEFT
#define R_JPAD		CONT_RIGHT
#define D_JPAD		CONT_DOWN
#define U_CBUTTONS	CONT_E
#define L_CBUTTONS	CONT_C
#define R_CBUTTONS	CONT_F
#define D_CBUTTONS	CONT_D

/* Controller error number */

#define	CONT_ERR_NO_CONTROLLER		PFS_ERR_NOPACK 		/* 1 */
#define	CONT_ERR_CONTRFAIL		CONT_OVERRUN_ERROR	/* 4 */
#define	CONT_ERR_INVALID		PFS_ERR_INVALID		/* 5 */
#define	CONT_ERR_DEVICE			PFS_ERR_DEVICE 		/* 11 */
#define	CONT_ERR_NOT_READY		12
#define	CONT_ERR_VOICE_MEMORY		13
#define	CONT_ERR_VOICE_WORD		14
#define	CONT_ERR_VOICE_NO_RESPONSE	15


#if defined(_LANGUAGE_C) || defined(_LANGUAGE_C_PLUS_PLUS)

/**************************************************************************
 *
 * Macro definitions
 *
 */


/**************************************************************************
 *
 * Extern variables
 *
 */


/**************************************************************************
 *
 * Function prototypes
 *
 */

/* Controller interface */

extern s32		osContInit(OSMesgQueue *, u8 *, OSContStatus *);
extern s32		osContReset(OSMesgQueue *, OSContStatus *);
extern s32		osContStartQuery(OSMesgQueue *);
extern s32		osContStartReadData(OSMesgQueue *);
#ifndef _HW_VERSION_1
extern s32		osContSetCh(u8);
#endif
extern void		osContGetQuery(OSContStatus *);
extern void		osContGetReadData(OSContPad *);


#endif  /* defined(_LANGUAGE_C) || defined(_LANGUAGE_C_PLUS_PLUS) */

#ifdef _LANGUAGE_C_PLUS_PLUS
}
#endif

#endif /* !_OS_CONT_H_ */
