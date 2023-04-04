#ifndef __BSTRING_H__
#define __BSTRING_H__
#ifdef __cplusplus
extern "C" {
#endif

/*
 * bstring(3C) -- byte string operations
 *
 * Copyright 1990, Silicon Graphics, Inc. 
 * All Rights Reserved.
 *
 * This is UNPUBLISHED PROPRIETARY SOURCE CODE of Silicon Graphics, Inc.;
 * the contents of this file may not be disclosed to third parties, copied or 
 * duplicated in any form, in whole or in part, without the prior written 
 * permission of Silicon Graphics, Inc.
 *
 * RESTRICTED RIGHTS LEGEND:
 * Use, duplication or disclosure by the Government is subject to restrictions 
 * as set forth in subdivision (c)(1)(ii) of the Rights in Technical Data
 * and Computer Software clause at DFARS 252.227-7013, and/or in similar or 
 * successor clauses in the FAR, DOD or NASA FAR Supplement. Unpublished - 
 * rights reserved under the Copyright Laws of the United States.
 */

#ident "$Revision: 1.4 $"

extern void    bcopy(const void *, void *, size_t);
extern int    bcmp(const void *, const void *, int);
extern void    bzero(void *, size_t);
extern void    blkclr(void *, int);

#ifdef __cplusplus
}
#endif
#endif /* !__BSTRING_H__ */
