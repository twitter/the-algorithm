#ifndef GFX_DIMENSIONS_H
#define GFX_DIMENSIONS_H

/*

This file is for ports that want to enable widescreen.
Change the definitions to the following:

#include <math.h>
#define GFX_DIMENSIONS_FROM_LEFT_EDGE(v) (SCREEN_WIDTH / 2 - SCREEN_HEIGHT / 2 * [current_aspect_ratio] + (v))
#define GFX_DIMENSIONS_FROM_RIGHT_EDGE(v) (SCREEN_WIDTH / 2 + SCREEN_HEIGHT / 2 * [current_aspect_ratio] - (v))
#define GFX_DIMENSIONS_RECT_FROM_LEFT_EDGE(v) ((int)floorf(GFX_DIMENSIONS_FROM_LEFT_EDGE(v)))
#define GFX_DIMENSIONS_RECT_FROM_RIGHT_EDGE(v) ((int)ceilf(GFX_DIMENSIONS_FROM_RIGHT_EDGE(v)))
#define GFX_DIMENSIONS_ASPECT_RATIO [current_aspect_ratio]

The idea is that SCREEN_WIDTH and SCREEN_HEIGHT are still hardcoded to 320 and 240, and that
x=0 lies at where a 4:3 left edge would be. On 16:9 widescreen, the left edge is hence at -53.33.

To get better accuracy, consider using floats instead of shorts for coordinates in Vertex and Matrix structures.

The conversion to integers above is for RECT commands which naturally only accept integer values.
Note that RECT commands must be enhanced to support negative coordinates with this modification.

*/

#ifdef WIDESCREEN
#error "widescreen not implemented"
#else

#define GFX_DIMENSIONS_FROM_LEFT_EDGE(v) (v)
#define GFX_DIMENSIONS_FROM_RIGHT_EDGE(v) (SCREEN_WIDTH - (v))
#define GFX_DIMENSIONS_RECT_FROM_LEFT_EDGE(v) (v)
#define GFX_DIMENSIONS_RECT_FROM_RIGHT_EDGE(v) (SCREEN_WIDTH - (v))
#define GFX_DIMENSIONS_ASPECT_RATIO (4.0f / 3.0f)

#endif

// If screen is taller than it is wide, radius should be equal to SCREEN_HEIGHT since we scale horizontally
#define GFX_DIMENSIONS_FULL_RADIUS (SCREEN_HEIGHT * (GFX_DIMENSIONS_ASPECT_RATIO > 1 ? GFX_DIMENSIONS_ASPECT_RATIO : 1))

#endif // GFX_DIMENSIONS_H
