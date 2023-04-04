#include <stdio.h>
#include <stdlib.h>
#include <strings.h>

#define STBI_NO_LINEAR
#define STBI_NO_HDR
#define STBI_NO_TGA
#define STB_IMAGE_IMPLEMENTATION
#include <stb/stb_image.h>
#define STB_IMAGE_WRITE_IMPLEMENTATION
#include <stb/stb_image_write.h>

#include "n64graphics.h"
#include "utils.h"

// SCALE_M_N: upscale/downscale M-bit integer to N-bit
#define SCALE_5_8(VAL_) (((VAL_) * 0xFF) / 0x1F)
#define SCALE_8_5(VAL_) ((((VAL_) + 4) * 0x1F) / 0xFF)
#define SCALE_4_8(VAL_) ((VAL_) * 0x11)
#define SCALE_8_4(VAL_) ((VAL_) / 0x11)
#define SCALE_3_8(VAL_) ((VAL_) * 0x24)
#define SCALE_8_3(VAL_) ((VAL_) / 0x24)


typedef struct
{
    enum
    {
       IMG_FORMAT_RGBA,
       IMG_FORMAT_IA,
       IMG_FORMAT_I,
       IMG_FORMAT_CI,
    } format;
    int depth;
} img_format;

//---------------------------------------------------------
// N64 RGBA/IA/I/CI -> internal RGBA/IA
//---------------------------------------------------------

rgba *raw2rgba(const uint8_t *raw, int width, int height, int depth)
{
   rgba *img;
   int img_size;

   img_size = width * height * sizeof(*img);
   img = malloc(img_size);
   if (!img) {
      ERROR("Error allocating %d bytes\n", img_size);
      return NULL;
   }

   if (depth == 16) {
      for (int i = 0; i < width * height; i++) {
         img[i].red   = SCALE_5_8((raw[i*2] & 0xF8) >> 3);
         img[i].green = SCALE_5_8(((raw[i*2] & 0x07) << 2) | ((raw[i*2+1] & 0xC0) >> 6));
         img[i].blue  = SCALE_5_8((raw[i*2+1] & 0x3E) >> 1);
         img[i].alpha = (raw[i*2+1] & 0x01) ? 0xFF : 0x00;
      }
   } else if (depth == 32) {
      for (int i = 0; i < width * height; i++) {
         img[i].red   = raw[i*4];
         img[i].green = raw[i*4+1];
         img[i].blue  = raw[i*4+2];
         img[i].alpha = raw[i*4+3];
      }
   }

   return img;
}

ia *raw2ia(const uint8_t *raw, int width, int height, int depth)
{
   ia *img;
   int img_size;

   img_size = width * height * sizeof(*img);
   img = malloc(img_size);
   if (!img) {
      ERROR("Error allocating %u bytes\n", img_size);
      return NULL;
   }

   switch (depth) {
      case 16:
         for (int i = 0; i < width * height; i++) {
            img[i].intensity = raw[i*2];
            img[i].alpha     = raw[i*2+1];
         }
         break;
      case 8:
         for (int i = 0; i < width * height; i++) {
            img[i].intensity = SCALE_4_8((raw[i] & 0xF0) >> 4);
            img[i].alpha     = SCALE_4_8(raw[i] & 0x0F);
         }
         break;
      case 4:
         for (int i = 0; i < width * height; i++) {
            uint8_t bits;
            bits = raw[i/2];
            if (i % 2) {
               bits &= 0xF;
            } else {
               bits >>= 4;
            }
            img[i].intensity = SCALE_3_8((bits >> 1) & 0x07);
            img[i].alpha     = (bits & 0x01) ? 0xFF : 0x00;
         }
         break;
      case 1:
         for (int i = 0; i < width * height; i++) {
            uint8_t bits;
            uint8_t mask;
            bits = raw[i/8];
            mask = 1 << (7 - (i % 8)); // MSb->LSb
            bits = (bits & mask) ? 0xFF : 0x00;
            img[i].intensity = bits;
            img[i].alpha     = bits;
         }
         break;
      default:
         ERROR("Error invalid depth %d\n", depth);
         break;
   }

   return img;
}

ia *raw2i(const uint8_t *raw, int width, int height, int depth)
{
   ia *img = NULL;
   int img_size;

   img_size = width * height * sizeof(*img);
   img = malloc(img_size);
   if (!img) {
      ERROR("Error allocating %u bytes\n", img_size);
      return NULL;
   }

   switch (depth) {
      case 8:
         for (int i = 0; i < width * height; i++) {
            img[i].intensity = raw[i];
            img[i].alpha     = 0xFF;
         }
         break;
      case 4:
         for (int i = 0; i < width * height; i++) {
            uint8_t bits;
            bits = raw[i/2];
            if (i % 2) {
               bits &= 0xF;
            } else {
               bits >>= 4;
            }
            img[i].intensity = SCALE_4_8(bits);
            img[i].alpha     = img[i].intensity; // alpha copy intensity
            // TODO: modes
            // img[i].alpha     = 0xFF; // alpha = 1
            // img[i].alpha     = img[i].intensity ? 0xFF : 0x00; // binary
         }
         break;
      default:
         ERROR("Error invalid depth %d\n", depth);
         break;
   }

   return img;
}

// convert CI raw data and palette to raw data (either RGBA16 or IA16)
uint8_t *ci2raw(const uint8_t *rawci, const uint8_t *palette, int width, int height, int ci_depth)
{
   uint8_t *raw;
   int raw_size;

   // first convert to raw RGBA
   raw_size = sizeof(uint16_t) * width * height;
   raw = malloc(raw_size);
   if (!raw) {
      ERROR("Error allocating %u bytes\n", raw_size);
      return NULL;
   }

   for (int i = 0; i < width * height; i++) {
      int pal_idx = rawci[i];
      if (ci_depth == 4) {
         int byte_idx = i / 2;
         int nibble = 1 - (i % 2);
         int shift = 4 * nibble;
         pal_idx = (rawci[byte_idx] >> shift) & 0xF;
      }
      raw[2*i]   = palette[2*pal_idx];
      raw[2*i+1] = palette[2*pal_idx+1];
   }

   return raw;
}


//---------------------------------------------------------
// internal RGBA/IA -> N64 RGBA/IA/I/CI
// returns length written to 'raw' used or -1 on error
//---------------------------------------------------------

int rgba2raw(uint8_t *raw, const rgba *img, int width, int height, int depth)
{
   int size = (width * height * depth + 7) / 8;
   INFO("Converting RGBA%d %dx%d to raw\n", depth, width, height);

   if (depth == 16) {
      for (int i = 0; i < width * height; i++) {
         uint8_t r, g, b, a;
         r = SCALE_8_5(img[i].red);
         g = SCALE_8_5(img[i].green);
         b = SCALE_8_5(img[i].blue);
         a = img[i].alpha ? 0x1 : 0x0;
         raw[i*2]   = (r << 3) | (g >> 2);
         raw[i*2+1] = ((g & 0x3) << 6) | (b << 1) | a;
      }
   } else if (depth == 32) {
      for (int i = 0; i < width * height; i++) {
         raw[i*4]   = img[i].red;
         raw[i*4+1] = img[i].green;
         raw[i*4+2] = img[i].blue;
         raw[i*4+3] = img[i].alpha;
      }
   } else {
      ERROR("Error invalid depth %d\n", depth);
      size = -1;
   }

   return size;
}

int ia2raw(uint8_t *raw, const ia *img, int width, int height, int depth)
{
   int size = (width * height * depth + 7) / 8;
   INFO("Converting IA%d %dx%d to raw\n", depth, width, height);
   memset(raw, 0, size);

   switch (depth) {
      case 16:
         for (int i = 0; i < width * height; i++) {
            raw[i*2]   = img[i].intensity;
            raw[i*2+1] = img[i].alpha;
         }
         break;
      case 8:
         for (int i = 0; i < width * height; i++) {
            uint8_t val = SCALE_8_4(img[i].intensity);
            uint8_t alpha = SCALE_8_4(img[i].alpha);
            raw[i] = (val << 4) | alpha;
         }
         break;
      case 4:
         for (int i = 0; i < width * height; i++) {
            uint8_t val = SCALE_8_3(img[i].intensity);
            uint8_t alpha = img[i].alpha ? 0x01 : 0x00;
            uint8_t old = raw[i/2];
            if (i % 2) {
               raw[i/2] = (old & 0xF0) | (val << 1) | alpha;
            } else {
               raw[i/2] = (old & 0x0F) | (((val << 1) | alpha) << 4);
            }
         }
         break;
      case 1:
         for (int i = 0; i < width * height; i++) {
            uint8_t val = img[i].intensity;
            uint8_t old = raw[i/8];
            uint8_t bit = 1 << (7 - (i % 8));
            if (val) {
               raw[i/8] = old | bit;
            } else {
               raw[i/8] = old & (~bit);
            }
         }
         break;
      default:
         ERROR("Error invalid depth %d\n", depth);
         size = -1;
         break;
   }

   return size;
}

int i2raw(uint8_t *raw, const ia *img, int width, int height, int depth)
{
   int size = (width * height * depth + 7) / 8;
   INFO("Converting I%d %dx%d to raw\n", depth, width, height);
   memset(raw, 0, size);

   switch (depth) {
      case 8:
         for (int i = 0; i < width * height; i++) {
            raw[i] = img[i].intensity;
         }
         break;
      case 4:
         for (int i = 0; i < width * height; i++) {
            uint8_t val = SCALE_8_4(img[i].intensity);
            uint8_t old = raw[i/2];
            if (i % 2) {
               raw[i/2] = (old & 0xF0) | val;
            } else {
               raw[i/2] = (old & 0x0F) | (val << 4);
            }
         }
         break;
      default:
         ERROR("Error invalid depth %d\n", depth);
         size = -1;
         break;
   }

   return size;
}


//---------------------------------------------------------
// internal RGBA/IA -> PNG
//---------------------------------------------------------

int rgba2png(const char *png_filename, const rgba *img, int width, int height)
{
   int ret = 0;
   INFO("Saving RGBA %dx%d to \"%s\"\n", width, height, png_filename);

   // convert to format stb_image_write expects
   uint8_t *data = malloc(4*width*height);
   if (data) {
      for (int j = 0; j < height; j++) {
         for (int i = 0; i < width; i++) {
            int idx = j*width + i;
            data[4*idx]     = img[idx].red;
            data[4*idx + 1] = img[idx].green;
            data[4*idx + 2] = img[idx].blue;
            data[4*idx + 3] = img[idx].alpha;
         }
      }

      ret = stbi_write_png(png_filename, width, height, 4, data, 0);

      free(data);
   }

   return ret;
}

int ia2png(const char *png_filename, const ia *img, int width, int height)
{
   int ret = 0;
   INFO("Saving IA %dx%d to \"%s\"\n", width, height, png_filename);

   // convert to format stb_image_write expects
   uint8_t *data = malloc(2*width*height);
   if (data) {
      for (int j = 0; j < height; j++) {
         for (int i = 0; i < width; i++) {
            int idx = j*width + i;
            data[2*idx]     = img[idx].intensity;
            data[2*idx + 1] = img[idx].alpha;
         }
      }

      ret = stbi_write_png(png_filename, width, height, 2, data, 0);

      free(data);
   }

   return ret;
}

//---------------------------------------------------------
// PNG -> internal RGBA/IA
//---------------------------------------------------------

rgba *png2rgba(const char *png_filename, int *width, int *height)
{
   rgba *img = NULL;
   int w = 0;
   int h = 0;
   int channels = 0;
   int img_size;

   stbi_uc *data = stbi_load(png_filename, &w, &h, &channels, STBI_default);
   if (!data || w <= 0 || h <= 0) {
      ERROR("Error loading \"%s\"\n", png_filename);
      return NULL;
   }
   INFO("Read \"%s\" %dx%d channels: %d\n", png_filename, w, h, channels);

   img_size = w * h * sizeof(*img);
   img = malloc(img_size);
   if (!img) {
      ERROR("Error allocating %u bytes\n", img_size);
      return NULL;
   }

   switch (channels) {
      case 3: // red, green, blue
      case 4: // red, green, blue, alpha
         for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
               int idx = j*w + i;
               img[idx].red   = data[channels*idx];
               img[idx].green = data[channels*idx + 1];
               img[idx].blue  = data[channels*idx + 2];
               if (channels == 4) {
                  img[idx].alpha = data[channels*idx + 3];
               } else {
                  img[idx].alpha = 0xFF;
               }
            }
         }
         break;
      case 2: // grey, alpha
         for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
               int idx = j*w + i;
               img[idx].red   = data[2*idx];
               img[idx].green = data[2*idx];
               img[idx].blue  = data[2*idx];
               img[idx].alpha = data[2*idx + 1];
            }
         }
         break;
      default:
         ERROR("Don't know how to read channels: %d\n", channels);
         free(img);
         img = NULL;
   }

   // cleanup
   stbi_image_free(data);

   *width = w;
   *height = h;
   return img;
}

ia *png2ia(const char *png_filename, int *width, int *height)
{
   ia *img = NULL;
   int w = 0, h = 0;
   int channels = 0;
   int img_size;

   stbi_uc *data = stbi_load(png_filename, &w, &h, &channels, STBI_default);
   if (!data || w <= 0 || h <= 0) {
      ERROR("Error loading \"%s\"\n", png_filename);
      return NULL;
   }
   INFO("Read \"%s\" %dx%d channels: %d\n", png_filename, w, h, channels);

   img_size = w * h * sizeof(*img);
   img = malloc(img_size);
   if (!img) {
      ERROR("Error allocating %d bytes\n", img_size);
      return NULL;
   }

   switch (channels) {
      case 3: // red, green, blue
      case 4: // red, green, blue, alpha
         ERROR("Warning: averaging RGB PNG to create IA\n");
         for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
               int idx = j*w + i;
               int sum = data[channels*idx] + data[channels*idx + 1] + data[channels*idx + 2];
               img[idx].intensity = (sum + 1) / 3; // add 1 to round up where appropriate
               if (channels == 4) {
                  img[idx].alpha = data[channels*idx + 3];
               } else {
                  img[idx].alpha = 0xFF;
               }
            }
         }
         break;
      case 2: // grey, alpha
         for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
               int idx = j*w + i;
               img[idx].intensity = data[2*idx];
               img[idx].alpha     = data[2*idx + 1];
            }
         }
         break;
      default:
         ERROR("Don't know how to read channels: %d\n", channels);
         free(img);
         img = NULL;
   }

   // cleanup
   stbi_image_free(data);

   *width = w;
   *height = h;
   return img;
}

// find index of palette color
// return -1 if not found
static int pal_find_color(const palette_t *pal, uint16_t val)
{
   for (int i = 0; i < pal->used; i++) {
      if (pal->data[i] == val) {
         return i;
      }
   }
   return -1;
}

// find value in palette, or add if not there
// returns palette index entered or -1 if palette full
static int pal_add_color(palette_t *pal, uint16_t val)
{
   int idx;
   idx = pal_find_color(pal, val);
   if (idx < 0) {
      if (pal->used == pal->max) {
         ERROR("Error: trying to use more than %d\n", pal->max);
      } else {
         idx = pal->used;
         pal->data[pal->used] = val;
         pal->used++;
      }
   }
   return idx;
}

// convert from raw (RGBA16 or IA16) format to CI + palette
// returns 1 on success
int raw2ci(uint8_t *rawci, palette_t *pal, const uint8_t *raw, int raw_len, int ci_depth)
{
   // assign colors to palette
   pal->used = 0;
   memset(pal->data, 0, sizeof(pal->data));
   int ci_idx = 0;
   for (int i = 0; i < raw_len; i += sizeof(uint16_t)) {
      uint16_t val = read_u16_be(&raw[i]);
      int pal_idx = pal_add_color(pal, val);
      if (pal_idx < 0) {
         ERROR("Error adding color @ (%d): %d (used: %d/%d)\n", i, pal_idx, pal->used, pal->max);
         return 0;
      } else {
         switch (ci_depth) {
            case 8:
               rawci[ci_idx] = (uint8_t)pal_idx;
               break;
            case 4:
            {
               int byte_idx = ci_idx / 2;
               int nibble = 1 - (ci_idx % 2);
               uint8_t mask = 0xF << (4 * (1 - nibble));
               rawci[byte_idx] = (rawci[byte_idx] & mask) | (pal_idx << (4 * nibble));
               break;
            }
         }
         ci_idx++;
      }
   }
   return 1;
}

const char *n64graphics_get_read_version(void)
{
   return "stb_image 2.19";
}

const char *n64graphics_get_write_version(void)
{
   return "stb_image_write 1.09";
}

#ifdef N64GRAPHICS_STANDALONE
#define N64GRAPHICS_VERSION "0.4"
#include <string.h>

typedef enum
{
   MODE_EXPORT,
   MODE_IMPORT,
} tool_mode;

typedef struct
{
   char *img_filename;
   char *bin_filename;
   char *pal_filename;
   tool_mode mode;
   write_encoding encoding;
   unsigned int bin_offset;
   unsigned int pal_offset;
   img_format format;
   img_format pal_format;
   int width;
   int height;
   int bin_truncate;
   int pal_truncate;
} graphics_config;

static const graphics_config default_config =
{
   .img_filename = NULL,
   .bin_filename = NULL,
   .pal_filename = NULL,
   .mode = MODE_EXPORT,
   .encoding = ENCODING_RAW,
   .bin_offset = 0,
   .pal_offset = 0,
   .format = {IMG_FORMAT_RGBA, 16},
   .pal_format = {IMG_FORMAT_RGBA, 16},
   .width = 32,
   .height = 32,
   .bin_truncate = 1,
   .pal_truncate = 1,
};

typedef struct
{
   const char *name;
   img_format format;
} format_entry;

static const format_entry format_table[] =
{
   {"rgba16", {IMG_FORMAT_RGBA, 16}},
   {"rgba32", {IMG_FORMAT_RGBA, 32}},
   {"ia1",    {IMG_FORMAT_IA,    1}},
   {"ia4",    {IMG_FORMAT_IA,    4}},
   {"ia8",    {IMG_FORMAT_IA,    8}},
   {"ia16",   {IMG_FORMAT_IA,   16}},
   {"i4",     {IMG_FORMAT_I,     4}},
   {"i8",     {IMG_FORMAT_I,     8}},
   {"ci8",    {IMG_FORMAT_CI,    8}},
   {"ci4",    {IMG_FORMAT_CI,    4}},
};

static const char *format2str(const img_format *format)
{
   for (unsigned i = 0; i < DIM(format_table); i++) {
      if (format->format == format_table[i].format.format && format->depth == format_table[i].format.depth) {
         return format_table[i].name;
      }
   }
   return "unknown";
}

static int parse_format(img_format *format, const char *str)
{
   for (unsigned i = 0; i < DIM(format_table); i++) {
      if (!strcasecmp(str, format_table[i].name)) {
         format->format = format_table[i].format.format;
         format->depth = format_table[i].format.depth;
         return 1;
      }
   }
   return 0;
}

typedef struct
{
   const char *name;
   write_encoding encoding;
} scheme_entry;

static const scheme_entry encoding_table[] =
{
   {"raw", ENCODING_RAW},
   {"u8",  ENCODING_U8},
   {"u16", ENCODING_U16},
   {"u32", ENCODING_U32},
   {"u64", ENCODING_U64},
};

static const char *encoding2str(write_encoding encoding)
{
   for (unsigned i = 0; i < DIM(encoding_table); i++) {
      if (encoding == encoding_table[i].encoding) {
         return encoding_table[i].name;
      }
   }
   return "unknown";
}

static int parse_encoding(write_encoding *encoding, const char *str)
{
   for (unsigned i = 0; i < DIM(encoding_table); i++) {
      if (!strcasecmp(str, encoding_table[i].name)) {
         *encoding = encoding_table[i].encoding;
         return 1;
      }
   }
   return 0;
}

static void print_usage(void)
{
   ERROR("Usage: n64graphics -e/-i BIN_FILE -g IMG_FILE [-p PAL_FILE] [-o BIN_OFFSET] [-P PAL_OFFSET] [-f FORMAT] [-c CI_FORMAT] [-w WIDTH] [-h HEIGHT] [-V]\n"
         "\n"
         "n64graphics v" N64GRAPHICS_VERSION ": N64 graphics manipulator\n"
         "\n"
         "Required arguments:\n"
         " -e BIN_FILE   export from BIN_FILE to PNG_FILE\n"
         " -i BIN_FILE   import from PNG_FILE to BIN_FILE\n"
         " -g IMG_FILE   graphics file to import/export (.png)\n"
         "Optional arguments:\n"
         " -o BIN_OFFSET starting offset in BIN_FILE (prevents truncation during import)\n"
         " -f FORMAT     texture format: rgba16, rgba32, ia1, ia4, ia8, ia16, i4, i8, ci4, ci8 (default: %s)\n"
         " -s SCHEME     output scheme: raw, u8 (hex), u64 (hex) (default: %s)\n"
         " -w WIDTH      export texture width (default: %d)\n"
         " -h HEIGHT     export texture height (default: %d)\n"
         "CI arguments:\n"
         " -c CI_FORMAT  CI palette format: rgba16, ia16 (default: %s)\n"
         " -p PAL_FILE   palette binary file to import/export from/to\n"
         " -P PAL_OFFSET starting offset in PAL_FILE (prevents truncation during import)\n"
         "Other arguments:\n"
         " -v            verbose logging\n"
         " -V            print version information\n",
         format2str(&default_config.format),
         encoding2str(default_config.encoding),
         default_config.width,
         default_config.height,
         format2str(&default_config.pal_format));
}

static void print_version(void)
{
   ERROR("n64graphics v" N64GRAPHICS_VERSION ", using:\n"
         "  %s\n"
         "  %s\n",
         n64graphics_get_read_version(), n64graphics_get_write_version());
}

// parse command line arguments
static int parse_arguments(int argc, char *argv[], graphics_config *config)
{
   for (int i = 1; i < argc; i++) {
      if (argv[i][0] == '-') {
         switch (argv[i][1]) {
            case 'c':
               if (++i >= argc) return 0;
               if (!parse_format(&config->pal_format, argv[i])) {
                  return 0;
               }
               break;
            case 'e':
               if (++i >= argc) return 0;
               config->bin_filename = argv[i];
               config->mode = MODE_EXPORT;
               break;
            case 'f':
               if (++i >= argc) return 0;
               if (!parse_format(&config->format, argv[i])) {
                  return 0;
               }
               break;
            case 'g':
               if (++i >= argc) return 0;
               config->img_filename = argv[i];
               break;
            case 'h':
               if (++i >= argc) return 0;
               config->height = strtoul(argv[i], NULL, 0);
               break;
            case 'i':
               if (++i >= argc) return 0;
               config->bin_filename = argv[i];
               config->mode = MODE_IMPORT;
               break;
            case 'o':
               if (++i >= argc) return 0;
               config->bin_offset = strtoul(argv[i], NULL, 0);
               config->bin_truncate = 0;
               break;
            case 'p':
               if (++i >= argc) return 0;
               config->pal_filename = argv[i];
               break;
            case 'P':
               if (++i >= argc) return 0;
               config->pal_offset = strtoul(argv[i], NULL, 0);
               config->pal_truncate = 0;
               break;
            case 's':
               if (++i >= argc) return 0;
               if (!parse_encoding(&config->encoding, argv[i])) {
                  return 0;
               }
               break;
            case 'v':
               g_verbosity = 1;
               break;
            case 'V':
               print_version();
               exit(0);
               break;
            case 'w':
               if (++i >= argc) return 0;
               config->width = strtoul(argv[i], NULL, 0);
               break;
            default:
               return 0;
               break;
         }
      } else {
         return 0;
      }
   }
   return 1;
}

// returns 1 if config is valid
static int valid_config(const graphics_config *config)
{
   if (!config->bin_filename || !config->img_filename) {
      return 0;
   }
   if (config->format.format == IMG_FORMAT_CI) {
      if (!config->pal_filename || (config->pal_format.depth != 16) ||
         (config->pal_format.format != IMG_FORMAT_RGBA && config->pal_format.format != IMG_FORMAT_IA)) {
         return 0;
      }
   }
   return 1;
}

int main(int argc, char *argv[])
{
   graphics_config config = default_config;
   rgba *imgr;
   ia   *imgi;
   FILE *bin_fp;
   uint8_t *raw;
   int raw_size;
   int length = 0;
   int flength;
   int res;

   int valid = parse_arguments(argc, argv, &config);
   if (!valid || !valid_config(&config)) {
      print_usage();
      exit(EXIT_FAILURE);
   }

   if (config.mode == MODE_IMPORT) {
      if (0 == strcmp("-", config.bin_filename)) {
         bin_fp = stdout;
      } else {
         if (config.bin_truncate) {
            bin_fp = fopen(config.bin_filename, "wb");
         } else {
            bin_fp = fopen(config.bin_filename, "r+b");
         }
      }
      if (!bin_fp) {
         ERROR("Error opening \"%s\"\n", config.bin_filename);
         return -1;
      }
      if (!config.bin_truncate) {
         fseek(bin_fp, config.bin_offset, SEEK_SET);
      }
      switch (config.format.format) {
         case IMG_FORMAT_RGBA:
            imgr = png2rgba(config.img_filename, &config.width, &config.height);
            raw_size = (config.width * config.height * config.format.depth + 7) / 8;
            raw = malloc(raw_size);
            if (!raw) {
               ERROR("Error allocating %u bytes\n", raw_size);
            }
            length = rgba2raw(raw, imgr, config.width, config.height, config.format.depth);
            break;
         case IMG_FORMAT_IA:
            imgi = png2ia(config.img_filename, &config.width, &config.height);
            raw_size = (config.width * config.height * config.format.depth + 7) / 8;
            raw = malloc(raw_size);
            if (!raw) {
               ERROR("Error allocating %u bytes\n", raw_size);
            }
            length = ia2raw(raw, imgi, config.width, config.height, config.format.depth);
            break;
         case IMG_FORMAT_I:
            imgi = png2ia(config.img_filename, &config.width, &config.height);
            raw_size = (config.width * config.height * config.format.depth + 7) / 8;
            raw = malloc(raw_size);
            if (!raw) {
               ERROR("Error allocating %u bytes\n", raw_size);
            }
            length = i2raw(raw, imgi, config.width, config.height, config.format.depth);
            break;
         case IMG_FORMAT_CI:
         {
            palette_t pal = {0};
            FILE *pal_fp;
            uint8_t *raw16;
            int raw16_size;
            int raw16_length;
            uint8_t *ci;
            int ci_length;
            int pal_success;
            int pal_length;

            if (config.pal_truncate) {
               pal_fp = fopen(config.pal_filename, "wb");
            } else {
               pal_fp = fopen(config.pal_filename, "r+b");
            }
            if (!pal_fp) {
               ERROR("Error opening \"%s\"\n", config.pal_filename);
               return EXIT_FAILURE;
            }
            if (!config.pal_truncate) {
               fseek(pal_fp, config.bin_offset, SEEK_SET);
            }

            raw16_size = config.width * config.height * config.pal_format.depth / 8;
            raw16 = malloc(raw16_size);
            if (!raw16) {
               ERROR("Error allocating %d bytes\n", raw16_size);
               return EXIT_FAILURE;
            }
            switch (config.pal_format.format) {
               case IMG_FORMAT_RGBA:
                  imgr = png2rgba(config.img_filename, &config.width, &config.height);
                  raw16_length = rgba2raw(raw16, imgr, config.width, config.height, config.pal_format.depth);
                  break;
               case IMG_FORMAT_IA:
                  imgi = png2ia(config.img_filename, &config.width, &config.height);
                  raw16_length = ia2raw(raw16, imgi, config.width, config.height, config.pal_format.depth);
                  break;
               default:
                  ERROR("Unsupported palette format: %s\n", format2str(&config.pal_format));
                  exit(EXIT_FAILURE);
            }

            // convert raw to palette
            pal.max = (1 << config.format.depth);
            ci_length = config.width * config.height * config.format.depth / 8;
            ci = malloc(ci_length);
            pal_success = raw2ci(ci, &pal, raw16, raw16_length, config.format.depth);
            if (!pal_success) {
               ERROR("Error converting palette\n");
               exit(EXIT_FAILURE);
            }

            // pack the bytes
            uint8_t raw_pal[sizeof(pal.data)];
            for (int i = 0; i < pal.max; i++) {
               write_u16_be(&raw_pal[2*i], pal.data[i]);
            }
            pal_length = pal.max * sizeof(pal.data[0]);
            INFO("Writing 0x%X bytes to offset 0x%X of \"%s\"\n", pal_length, config.pal_offset, config.pal_filename);
            flength = fprint_write_output(pal_fp, config.encoding, raw_pal, pal_length);
            if (config.encoding == ENCODING_RAW && flength != pal_length) {
               ERROR("Error writing %d bytes to \"%s\"\n", pal_length, config.pal_filename);
            }
            INFO("Wrote 0x%X bytes to \"%s\"\n", flength, config.pal_filename);

            raw = ci;
            length = ci_length;

            free(raw16);
            fclose(pal_fp);
            break;
         }
         default:
            return EXIT_FAILURE;
      }
      if (length <= 0) {
         ERROR("Error converting to raw format\n");
         return EXIT_FAILURE;
      }
      INFO("Writing 0x%X bytes to offset 0x%X of \"%s\"\n", length, config.bin_offset, config.bin_filename);
      flength = fprint_write_output(bin_fp, config.encoding, raw, length);
      if (config.encoding == ENCODING_RAW && flength != length) {
         ERROR("Error writing %d bytes to \"%s\"\n", length, config.bin_filename);
      }
      INFO("Wrote 0x%X bytes to \"%s\"\n", flength, config.bin_filename);
      if (bin_fp != stdout) {
         fclose(bin_fp);
      }

   } else {
      if (config.width <= 0 || config.height <= 0 || config.format.depth <= 0) {
         ERROR("Error: must set position width and height for export\n");
         return EXIT_FAILURE;
      }
      bin_fp = fopen(config.bin_filename, "rb");
      if (!bin_fp) {
         ERROR("Error opening \"%s\"\n", config.bin_filename);
         return -1;
      }
      raw_size = (config.width * config.height * config.format.depth + 7) / 8;
      raw = malloc(raw_size);
      if (config.bin_offset > 0) {
         fseek(bin_fp, config.bin_offset, SEEK_SET);
      }
      flength = fread(raw, 1, raw_size, bin_fp);
      if (flength != raw_size) {
         ERROR("Error reading %d bytes from \"%s\"\n", raw_size, config.bin_filename);
      }
      switch (config.format.format) {
         case IMG_FORMAT_RGBA:
            imgr = raw2rgba(raw, config.width, config.height, config.format.depth);
            res = rgba2png(config.img_filename, imgr, config.width, config.height);
            break;
         case IMG_FORMAT_IA:
            imgi = raw2ia(raw, config.width, config.height, config.format.depth);
            res = ia2png(config.img_filename, imgi, config.width, config.height);
            break;
         case IMG_FORMAT_I:
            imgi = raw2i(raw, config.width, config.height, config.format.depth);
            res = ia2png(config.img_filename, imgi, config.width, config.height);
            break;
         case IMG_FORMAT_CI:
         {
            FILE *pal_fp;
            uint8_t *pal;
            uint8_t *raw_fmt;
            int pal_size;

            INFO("Extracting %s offset 0x%X, pal.offset 0x%0X, pal.format %s\n", format2str(&config.format),
                 config.bin_offset, config.pal_offset, format2str(&config.pal_format));

            pal_fp = fopen(config.pal_filename, "rb");
            if (!pal_fp) {
               ERROR("Error opening \"%s\"\n", config.bin_filename);
               return EXIT_FAILURE;
            }
            if (config.pal_offset > 0) {
               fseek(pal_fp, config.pal_offset, SEEK_SET);
            }

            pal_size = sizeof(uint16_t) * (1 << config.format.depth);
            INFO("Palette size: %d\n", pal_size);
            pal = malloc(pal_size);
            flength = fread(pal, 1, pal_size, pal_fp);
            if (flength != pal_size) {
               ERROR("Error reading %d bytes from \"%s\"\n", pal_size, config.pal_filename);
            }
            raw_fmt = ci2raw(raw, pal, config.width, config.height, config.format.depth);
            switch (config.pal_format.format) {
               case IMG_FORMAT_RGBA:
                  INFO("Converting raw to RGBA16\n");
                  imgr = raw2rgba(raw_fmt, config.width, config.height, config.pal_format.depth);
                  res = rgba2png(config.img_filename, imgr, config.width, config.height);
                  break;
               case IMG_FORMAT_IA:
                  INFO("Converting raw to IA16\n");
                  imgi = raw2ia(raw_fmt, config.width, config.height, config.pal_format.depth);
                  res = ia2png(config.img_filename, imgi, config.width, config.height);
                  break;
               default:
                  ERROR("Unsupported palette format: %s\n", format2str(&config.pal_format));
                  return EXIT_FAILURE;
            }
            free(raw_fmt);
            free(pal);
            break;
         }
         default:
            return EXIT_FAILURE;
      }
      if (!res) {
         ERROR("Error writing to \"%s\"\n", config.img_filename);
         return EXIT_FAILURE;
      }
   }

   return EXIT_SUCCESS;
}
#endif // N64GRAPHICS_STANDALONE
