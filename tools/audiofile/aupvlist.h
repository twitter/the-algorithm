/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	aupvlist.h

	This file contains the interface to the parameter value list data
	structures and routines.
*/

#ifndef AUPVLIST_H
#define AUPVLIST_H

#ifdef __cplusplus
extern "C" {
#endif

#if (defined(__GNUC__) && __GNUC__ >= 4) || defined(__clang__)
#define AFAPI __attribute__((visibility("default")))
#else
#define AFAPI
#endif

enum
{
	AU_PVTYPE_LONG = 1,
	AU_PVTYPE_DOUBLE = 2,
	AU_PVTYPE_PTR = 3
};

typedef struct _AUpvlist *AUpvlist;

#define AU_NULL_PVLIST ((struct _AUpvlist *) 0)

AFAPI AUpvlist AUpvnew (int maxItems);
AFAPI int AUpvgetmaxitems (AUpvlist);
AFAPI int AUpvfree (AUpvlist);
AFAPI int AUpvsetparam (AUpvlist, int item, int param);
AFAPI int AUpvsetvaltype (AUpvlist, int item, int type);
AFAPI int AUpvsetval (AUpvlist, int item, void *val);
AFAPI int AUpvgetparam (AUpvlist, int item, int *param);
AFAPI int AUpvgetvaltype (AUpvlist, int item, int *type);
AFAPI int AUpvgetval (AUpvlist, int item, void *val);

#undef AFAPI

#ifdef __cplusplus
}
#endif

#endif /* AUPVLIST_H */
