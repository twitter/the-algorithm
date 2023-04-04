
/*====================================================================
 * os_exception.h
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
        
        $RCSfile: os_exception.h,v $
        $Revision: 1.1 $
        $Date: 1998/10/09 08:01:07 $
 *---------------------------------------------------------------------*/

#ifndef _OS_EXCEPTION_H_
#define	_OS_EXCEPTION_H_

#ifdef _LANGUAGE_C_PLUS_PLUS
extern "C" {
#endif

#include <PR/ultratypes.h>

#if defined(_LANGUAGE_C) || defined(_LANGUAGE_C_PLUS_PLUS)

/**************************************************************************
 *
 * Type definitions
 *
 */

typedef u32 OSIntMask;
typedef u32 OSHWIntr;

#endif /* defined(_LANGUAGE_C) || defined(_LANGUAGE_C_PLUS_PLUS) */

/**************************************************************************
 *
 * Global definitions
 *
 */

/* Flags for debugging purpose */

#define	OS_FLAG_CPU_BREAK	1	/* Break exception has occurred */
#define	OS_FLAG_FAULT		2	/* CPU fault has occurred */

/* Interrupt masks */

#define	OS_IM_NONE	0x00000001
#define	OS_IM_SW1	0x00000501
#define	OS_IM_SW2	0x00000601
#define	OS_IM_CART	0x00000c01
#define	OS_IM_PRENMI	0x00001401
#define OS_IM_RDBWRITE	0x00002401
#define OS_IM_RDBREAD	0x00004401
#define	OS_IM_COUNTER	0x00008401
#define	OS_IM_CPU	0x0000ff01
#define	OS_IM_SP	0x00010401
#define	OS_IM_SI	0x00020401
#define	OS_IM_AI	0x00040401
#define	OS_IM_VI	0x00080401
#define	OS_IM_PI	0x00100401
#define	OS_IM_DP	0x00200401
#define	OS_IM_ALL	0x003fff01
#define	RCP_IMASK	0x003f0000
#define	RCP_IMASKSHIFT	16


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

/* Interrupt operations */

extern OSIntMask	osGetIntMask(void);
extern OSIntMask	osSetIntMask(OSIntMask);


#endif  /* defined(_LANGUAGE_C) || defined(_LANGUAGE_C_PLUS_PLUS) */

#ifdef _LANGUAGE_C_PLUS_PLUS
}
#endif

#endif /* !_OS_EXCEPTION_H_ */
