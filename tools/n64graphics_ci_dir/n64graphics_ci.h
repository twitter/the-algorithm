#ifndef N64GRAPHICS_CI_H_
#define N64GRAPHICS_CI_H_

#include <stdint.h>

// intermediate formats
typedef struct _rgba
{
    uint8_t red;
    uint8_t green;
    uint8_t blue;
    uint8_t alpha;
} rgba;

typedef struct _ia
{
    uint8_t intensity;
    uint8_t alpha;
} ia;

// N64 raw RGBA16/RGBA32 -> intermediate RGBA
rgba *raw2rgba(const uint8_t *raw, int width, int height, int depth);

// N64 raw CI + palette -> intermediate RGBA
rgba *rawci2rgba(const uint8_t *rawci, const uint8_t *palette, int width, int height, int depth);

// intermediate RGBA -> N64 raw CI + palette
int rgba2rawci(uint8_t *raw, uint8_t *out_palette, int *pal_len, const rgba *img, int width, int height, int depth);

// PNG file -> intermediate RGBA
rgba *png2rgba(const char *png_filename, int *width, int *height);

// intermediate RGBA write to PNG file
int rgba2png(const char *png_filename, const rgba *img, int width, int height);

// get version of underlying graphics reading library
const char *n64graphics_get_read_version(void);

// get version of underlying graphics writing library
const char *n64graphics_get_write_version(void);

#endif
