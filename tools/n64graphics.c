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

typedef enum
{
   IMG_FORMAT_RGBA,
   IMG_FORMAT_IA,
   IMG_FORMAT_I,
   IMG_FORMAT_CI,
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
            img[i].alpha     = 0xFF;
         }
         break;
      default:
         ERROR("Error invalid depth %d\n", depth);
         break;
   }

   return img;
}

// extract RGBA from CI raw data and palette
// TODO: different palette depths
rgba *rawci2rgba(const uint8_t *rawci, const uint8_t *palette, int width, int height, int depth)
{
   uint8_t *raw_rgba;
   rgba *img = NULL;
   int raw_size;

   // first convert to raw RGBA
   raw_size = 2 * width * height;
   raw_rgba = malloc(raw_size);
   if (!raw_rgba) {
      ERROR("Error allocating %u bytes\n", raw_size);
      return NULL;
   }

   for (int i = 0; i < width * height; i++) {
      raw_rgba[2*i]   = palette[2*rawci[i]];
      raw_rgba[2*i+1] = palette[2*rawci[i]+1];
   }

   // then convert to RGBA image data
   img = raw2rgba(raw_rgba, width, height, depth);

   free(raw_rgba);

   return img;
}


//---------------------------------------------------------
// internal RGBA/IA -> N64 RGBA/IA/I/CI
// returns length written to 'raw' used or -1 on error
//---------------------------------------------------------

int rgba2raw(uint8_t *raw, const rgba *img, int width, int height, int depth)
{
   int size = width * height * depth / 8;
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
   int size = width * height * depth / 8;
   INFO("Converting IA%d %dx%d to raw\n", depth, width, height);

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
   int size = width * height * depth / 8;
   INFO("Converting I%d %dx%d to raw\n", depth, width, height);

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

const char *n64graphics_get_read_version(void)
{
   return "stb_image 2.19";
}

const char *n64graphics_get_write_version(void)
{
   return "stb_image_write 1.09";
}

#ifdef N64GRAPHICS_STANDALONE
#define N64GRAPHICS_VERSION "0.3"
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
   tool_mode mode;
   unsigned int offset;
   img_format format;
   int depth;
   int width;
   int height;
   int truncate;
} graphics_config;

static const graphics_config default_config =
{
   .img_filename = NULL,
   .bin_filename = NULL,
   .mode = MODE_EXPORT,
   .offset = 0,
   .format = IMG_FORMAT_RGBA,
   .depth = 16,
   .width = 32,
   .height = 32,
   .truncate = 1,
};

typedef struct
{
   const char *name;
   img_format format;
   int depth;
} format_entry;

static const format_entry format_table[] =
{
   {"rgba16", IMG_FORMAT_RGBA, 16},
   {"rgba32", IMG_FORMAT_RGBA, 32},
   {"ia1",    IMG_FORMAT_IA,    1},
   {"ia4",    IMG_FORMAT_IA,    4},
   {"ia8",    IMG_FORMAT_IA,    8},
   {"ia16",   IMG_FORMAT_IA,   16},
   {"i4",     IMG_FORMAT_I,     4},
   {"i8",     IMG_FORMAT_I,     8},
   {"ci8",    IMG_FORMAT_CI,    8},
   {"ci16",   IMG_FORMAT_CI,   16},
};

static const char *format2str(img_format format, int depth)
{
   for (unsigned i = 0; i < DIM(format_table); i++) {
      if (format == format_table[i].format && depth == format_table[i].depth) {
         return format_table[i].name;
      }
   }
   return "unknown";
}

static int parse_format(graphics_config *config, const char *str)
{
   for (unsigned i = 0; i < DIM(format_table); i++) {
      if (!strcasecmp(str, format_table[i].name)) {
         config->format = format_table[i].format;
         config->depth = format_table[i].depth;
         return 1;
      }
   }
   return 0;
}

static void print_usage(void)
{
   ERROR("Usage: n64graphics -e/-i BIN_FILE -g PNG_FILE [-o offset] [-f FORMAT] [-w WIDTH] [-h HEIGHT] [-V]\n"
         "\n"
         "n64graphics v" N64GRAPHICS_VERSION ": N64 graphics manipulator\n"
         "\n"
         "Required arguments:\n"
         " -e BIN_FILE  export from BIN_FILE to PNG_FILE\n"
         " -i BIN_FILE  import from PNG_FILE to BIN_FILE\n"
         " -g PNG_FILE  graphics file to import/export (.png)\n"
         "Optional arguments:\n"
         " -o OFFSET    starting offset in BIN_FILE (prevents truncation during import)\n"
         " -f FORMAT    texture format: rgba16, rgba32, ia1, ia4, ia8, ia16, i4, i8, ci8, ci16 (default: %s)\n"
         " -w WIDTH     export texture width (default: %d)\n"
         " -h HEIGHT    export texture height (default: %d)\n"
         " -v           verbose logging\n"
         " -V           print version information\n",
         format2str(default_config.format, default_config.depth),
         default_config.width,
         default_config.height);
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
            case 'e':
               if (++i >= argc) return 0;
               config->bin_filename = argv[i];
               config->mode = MODE_EXPORT;
               break;
            case 'f':
               if (++i >= argc) return 0;
               if (!parse_format(config, argv[i])) {
                  return 0;
               }
               break;
            case 'i':
               if (++i >= argc) return 0;
               config->bin_filename = argv[i];
               config->mode = MODE_IMPORT;
               break;
            case 'g':
               if (++i >= argc) return 0;
               config->img_filename = argv[i];
               break;
            case 'h':
               if (++i >= argc) return 0;
               config->height = strtoul(argv[i], NULL, 0);
               break;
            case 'o':
               if (++i >= argc) return 0;
               config->offset = strtoul(argv[i], NULL, 0);
               config->truncate = 0;
               break;
            case 'w':
               if (++i >= argc) return 0;
               config->width = strtoul(argv[i], NULL, 0);
               break;
            case 'v':
               g_verbosity = 1;
               break;
            case 'V':
               print_version();
               exit(0);
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

int main(int argc, char *argv[])
{
   graphics_config config = default_config;
   rgba *imgr;
   ia   *imgi;
   FILE *fp;
   uint8_t *raw;
   int raw_size;
   int length = 0;
   int flength;
   int res;

   int valid = parse_arguments(argc, argv, &config);
   if (!valid || !config.bin_filename || !config.bin_filename) {
      print_usage();
      exit(EXIT_FAILURE);
   }

   if (config.mode == MODE_IMPORT) {
      if (config.truncate) {
         fp = fopen(config.bin_filename, "wb");
      } else {
         fp = fopen(config.bin_filename, "r+b");
      }
      if (!fp) {
         ERROR("Error opening \"%s\"\n", config.bin_filename);
         return -1;
      }
      if (!config.truncate) {
         fseek(fp, config.offset, SEEK_SET);
      }
      switch (config.format) {
         case IMG_FORMAT_RGBA:
            imgr = png2rgba(config.img_filename, &config.width, &config.height);
            raw_size = config.width * config.height * config.depth / 8;
            raw = malloc(raw_size);
            if (!raw) {
               ERROR("Error allocating %u bytes\n", raw_size);
            }
            length = rgba2raw(raw, imgr, config.width, config.height, config.depth);
            break;
         case IMG_FORMAT_IA:
            imgi = png2ia(config.img_filename, &config.width, &config.height);
            raw_size = config.width * config.height * config.depth / 8;
            raw = malloc(raw_size);
            if (!raw) {
               ERROR("Error allocating %u bytes\n", raw_size);
            }
            length = ia2raw(raw, imgi, config.width, config.height, config.depth);
            break;
         case IMG_FORMAT_I:
            imgi = png2ia(config.img_filename, &config.width, &config.height);
            raw_size = config.width * config.height * config.depth / 8;
            raw = malloc(raw_size);
            if (!raw) {
               ERROR("Error allocating %u bytes\n", raw_size);
            }
            length = i2raw(raw, imgi, config.width, config.height, config.depth);
            break;
         default:
            return EXIT_FAILURE;
      }
      if (length <= 0) {
         ERROR("Error converting to raw format\n");
         return EXIT_FAILURE;
      }
      INFO("Writing 0x%X bytes to offset 0x%X of \"%s\"\n", length, config.offset, config.bin_filename);
      flength = fwrite(raw, 1, length, fp);
      if (flength != length) {
         ERROR("Error writing %d bytes to \"%s\"\n", length, config.bin_filename);
      }
      fclose(fp);

   } else {
      if (config.width <= 0 || config.height <= 0 || config.depth <= 0) {
         ERROR("Error: must set position width and height for export\n");
         return EXIT_FAILURE;
      }
      fp = fopen(config.bin_filename, "rb");
      if (!fp) {
         ERROR("Error opening \"%s\"\n", config.bin_filename);
         return -1;
      }
      raw_size = config.width * config.height * config.depth / 8;
      raw = malloc(raw_size);
      if (config.offset > 0) {
         fseek(fp, config.offset, SEEK_SET);
      }
      flength = fread(raw, 1, raw_size, fp);
      if (flength != raw_size) {
         ERROR("Error reading %d bytes from \"%s\"\n", raw_size, config.bin_filename);
      }
      switch (config.format) {
         case IMG_FORMAT_RGBA:
            imgr = raw2rgba(raw, config.width, config.height, config.depth);
            res = rgba2png(config.img_filename, imgr, config.width, config.height);
            break;
         case IMG_FORMAT_IA:
            imgi = raw2ia(raw, config.width, config.height, config.depth);
            res = ia2png(config.img_filename, imgi, config.width, config.height);
            break;
         case IMG_FORMAT_I:
            imgi = raw2i(raw, config.width, config.height, config.depth);
            res = ia2png(config.img_filename, imgi, config.width, config.height);
            break;
         default:
            return EXIT_FAILURE;
      }
      if (!res) {
         ERROR("Error writing to \"%s\"\n", config.img_filename);
      }
   }

   return EXIT_SUCCESS;
}
#endif // N64GRAPHICS_STANDALONE
