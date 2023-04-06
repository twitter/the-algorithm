
/*====================================================================
 * os_tlb.h
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
        
        $RCSfile: os_tlb.h,v $
        $Revision: 1.1 $
        $Date: 1998/10/09 08:01:20 $
 *---------------------------------------------------------------------*/

#ifndef _OS_TLB_H_
#define	_OS_TLB_H_

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

typedef u32 OSPageMask;


#endif /* defined(_LANGUAGE_C) || defined(_LANGUAGE_C_PLUS_PLUS) */

/**************************************************************************
 *
 * Global definitions
 *
 */

/*
 * Page size argument for TLB routines
 */
#define OS_PM_4K	0x0000000
#define OS_PM_16K	0x0006000
#define OS_PM_64K	0x001e000
#define OS_PM_256K	0x007e000
#define OS_PM_1M	0x01fe000
#define OS_PM_4M	0x07fe000
#define OS_PM_16M	0x1ffe000


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

/* TLB management routines */

extern void		osMapTLB(s32, OSPageMask, void *, u32, u32, s32);
extern void		osMapTLBRdb(void);
extern void		osUnmapTLB(s32);
extern void		osUnmapTLBAll(void);
extern void		osSetTLBASID(s32);


#endif  /* defined(_LANGUAGE_C) || defined(_LANGUAGE_C_PLUS_PLUS) */

#ifdef _LANGUAGE_C_PLUS_PLUS
}
#endif

#endif /* !_OS_TLB_H_ */
