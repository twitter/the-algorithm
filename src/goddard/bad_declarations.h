#ifndef GD_BAD_DECLARATIONS_H
#define GD_BAD_DECLARATIONS_H

#include "gd_types.h"

/**
 * @file bad_declarations.h
 *
 * Match incorrect type promotion for two declared functions.
 *
 * There is an issue with the compiled code for these function calls in files
 * outside of the files in which they were defined: instead of passing f32's,
 * the caller passes f64's.
 *
 * The only possible reason I can come up with for this behavior is that
 * goddard only declared (not prototyped) his functions in the headers,
 * and didn't include the header in the function's defining .c file.
 * (Even IDO 5.3 cares about illegal promotion of types!) This results in
 * default argument promotion, which is incorrect in this case.
 *
 * Since that's an awful practice to emulate, include this file (first!) to prevent
 * the proper prototypes of these functions from being seen by files with the
 * the incorrectly compiled calls.
*/

#ifndef AVOID_UB

#define GD_USE_BAD_DECLARATIONS

/* shape_helper.h */
extern struct ObjFace *make_face_with_colour();
/* should be: make_face_with_colour(f32, f32, f32) */

/* old_menu.h */
extern struct ObjLabel *make_label();
/* should be: make_label(struct ObjValPtr *, char *, s32, f32, f32, f32) */

#endif /* !AVOID_UB */

#endif // GD_BAD_DECLARATIONS_H
