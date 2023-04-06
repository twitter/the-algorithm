#include <stdio.h>
#include <stdlib.h>
#include <strings.h>

#define STBI_NO_LINEAR
#define STBI_NO_HDR
#define STBI_NO_TGA
#define STB_IMAGE_IMPLEMENTATION
#include "../stb/stb_image.h"
#define STB_IMAGE_WRITE_IMPLEMENTATION
#include "../stb/stb_image_write.h"

#include "exoquant/exoquant.h"

#include "n64graphics_ci.h"
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
    IMG_FORMAT_CI
} img_format;

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
            img[i].red = SCALE_5_8((raw[i * 2] & 0xF8) >> 3);
            img[i].green = SCALE_5_8(((raw[i * 2] & 0x07) << 2) | ((raw[i * 2 + 1] & 0xC0) >> 6));
            img[i].blue = SCALE_5_8((raw[i * 2 + 1] & 0x3E) >> 1);
            img[i].alpha = (raw[i * 2 + 1] & 0x01) ? 0xFF : 0x00;
        }
    }
    else if (depth == 32) {
        for (int i = 0; i < width * height; i++) {
            img[i].red = raw[i * 4];
            img[i].green = raw[i * 4 + 1];
            img[i].blue = raw[i * 4 + 2];
            img[i].alpha = raw[i * 4 + 3];
        }
    }

    return img;
}

// extract RGBA from CI raw data and palette
rgba *rawci2rgba(const uint8_t *rawci, const uint8_t *palette, int width, int height, int depth)
{
    uint8_t *raw_rgba;
    rgba *img = NULL;
    int raw_size;
    int pal_index_1;
    int pal_index_2;

    // first convert to raw RGBA
    raw_size = 2 * width * height;
    raw_rgba = malloc(raw_size);
    if (!raw_rgba) {
        ERROR("Error allocating %u bytes\n", raw_size);
        return NULL;
    }

    if (depth == 4) { // CI4
        for (int i = 0; i < (width * height) / 2; i++) {
            pal_index_1 = rawci[i] >> 4;
            pal_index_2 = rawci[i] & 0xF;
            raw_rgba[i * 4 + 0] = palette[pal_index_1 * 2 + 0];
            raw_rgba[i * 4 + 1] = palette[pal_index_1 * 2 + 1];
            raw_rgba[i * 4 + 2] = palette[pal_index_2 * 2 + 0];
            raw_rgba[i * 4 + 3] = palette[pal_index_2 * 2 + 1];
        }
    } else { // CI8
        for (int i = 0; i < width * height; i++) {
            pal_index_1 = rawci[i];
            raw_rgba[i * 2 + 0] = palette[pal_index_1 * 2 + 0];
            raw_rgba[i * 2 + 1] = palette[pal_index_1 * 2 + 1];
        }
    }

    // then convert to RGBA image data
    img = raw2rgba(raw_rgba, width, height, 16);

    free(raw_rgba);

    return img;
}

int rgba2rawci(uint8_t *raw, uint8_t *out_palette, int *pal_len, const rgba *img, int width, int height, int depth)
{
    int size = width * height * depth / 8;
    int num_colors = *pal_len / 2;

    INFO("Converting RGBA %dx%d to raw CI%d\n", width, height, depth);

    uint8_t *rgba32_palette = malloc(num_colors * 4);
    uint8_t *ci8_raw = malloc(width * height);

    // Use ExoQuant to convert the RGBA32 data buffer to an CI8 output
    exq_data *pExq = exq_init();
    exq_feed(pExq, (uint8_t*)img, width * height);
    exq_quantize_hq(pExq, num_colors);
    exq_get_palette(pExq, rgba32_palette, num_colors);
    exq_map_image_ordered(pExq, width, height, (uint8_t*)img, ci8_raw);
    exq_free(pExq);

    if (depth == 4) {
        // Convert CI8 image to CI4 image
        for (int i = 0; i < size; i++) {
            raw[i] = (ci8_raw[i * 2 + 0] << 4) | ci8_raw[i * 2 + 1];
        }
    }
    else {
        memcpy(raw, ci8_raw, size);
    }

    // Convert RGBA32 palette to RGBA16
    for (int i = 0; i < num_colors; i++) {
        unsigned char red = (rgba32_palette[i * 4 + 0] / 8) & 0x1F;
        unsigned char green = (rgba32_palette[i * 4 + 1] / 8) & 0x1F;
        unsigned char blue = (rgba32_palette[i * 4 + 2] / 8) & 0x1F;
        unsigned char alpha = rgba32_palette[i * 4 + 3] > 0 ? 1 : 0; // 1 bit alpha

        out_palette[i * 2 + 0] = (red << 3) | (green >> 2);
        out_palette[i * 2 + 1] = ((green & 3) << 6) | (blue << 1) | alpha;
    }

    free(rgba32_palette);
    free(ci8_raw);
    return size;
}

rgba *png2rgba(const char *png_filename, int *width, int *height)
{
    rgba *img = NULL;
    int w = 0;
    int h = 0;
    int channels = 0;
    int img_size;

    stbi_uc *data = stbi_load(png_filename, &w, &h, &channels, STBI_default);
    if (!data || w <= 0 || h <= 0) {
        ERROR("Error loading png file \"%s\"\n", png_filename);
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
                img[idx].red = data[channels*idx];
                img[idx].green = data[channels*idx + 1];
                img[idx].blue = data[channels*idx + 2];
                if (channels == 4) {
                    img[idx].alpha = data[channels*idx + 3];
                }
                else {
                    img[idx].alpha = 0xFF;
                }
            }
        }
        break;
    case 2: // grey, alpha
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                int idx = j*w + i;
                img[idx].red = data[2 * idx];
                img[idx].green = data[2 * idx];
                img[idx].blue = data[2 * idx];
                img[idx].alpha = data[2 * idx + 1];
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

int rgba2png(const char *png_filename, const rgba *img, int width, int height)
{
    int ret = 0;
    INFO("Saving RGBA %dx%d to \"%s\"\n", width, height, png_filename);

    // convert to format stb_image_write expects
    uint8_t *data = malloc(4 * width*height);
    if (data) {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int idx = j*width + i;
                data[4 * idx] = img[idx].red;
                data[4 * idx + 1] = img[idx].green;
                data[4 * idx + 2] = img[idx].blue;
                data[4 * idx + 3] = img[idx].alpha;
            }
        }

        ret = stbi_write_png(png_filename, width, height, 4, data, 0);

        free(data);
    }

    return ret;
}

const char *n64graphics_get_read_version(void)
{
    return "stb_image 2.19";
}

const char *n64graphics_get_write_version(void)
{
    return "stb_image_write 1.09";
}

/***************************************************************/

#define N64GRAPHICS_VERSION "0.4 - CI Only Branch"
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
    .format = IMG_FORMAT_CI,
    .depth = 8,
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
    { "ci4",    IMG_FORMAT_CI,   4 },
    { "ci8",    IMG_FORMAT_CI,   8 },
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
    ERROR("Usage: n64graphics_ci -e/-i BIN_FILE -g PNG_FILE [-o offset] [-f FORMAT] [-w WIDTH] [-h HEIGHT] [-V]\n"
        "\n"
        "n64graphics v" N64GRAPHICS_VERSION ": N64 graphics manipulator\n"
        "\n"
        "Required arguments:\n"
        " -e BIN_FILE  export from BIN_FILE to PNG_FILE\n"
        " -i BIN_FILE  import from PNG_FILE to BIN_FILE\n"
        " -g PNG_FILE  graphics file to import/export (.png)\n"
        "Optional arguments:\n"
        " -o OFFSET    starting offset in BIN_FILE (prevents truncation during import)\n"
        " -f FORMAT    texture format: ci4, ci8 (default: %s)\n"
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
        }
        else {
            return 0;
        }
    }
    return 1;
}

char* getPaletteFilename(char* ci_filename)
{
    int bin_filename_len;
    int extension_len;
    char* pal_bin_filename;
    char* extension_loc;
    char* extension;

    // Write Palette file
    bin_filename_len = strlen(ci_filename);
    extension_loc = strrchr(ci_filename, '.');
    extension_len = (int)(extension_loc - ci_filename);
    extension = malloc(extension_len);
    memcpy(extension, extension_loc, extension_len);
    pal_bin_filename = malloc(bin_filename_len + 4); // +4 to include ".pal"
    if (!pal_bin_filename) {
        ERROR("Error allocating bytes for palette filename\n");
    }
    memcpy(pal_bin_filename, ci_filename, bin_filename_len);
    strcpy(pal_bin_filename + bin_filename_len, ".pal");
    //strcpy(pal_bin_filename + bin_filename_len, extension);

    free(extension);

    return pal_bin_filename;
}

int main(int argc, char* argv[])
{
    graphics_config config = default_config;
    rgba *imgr;
    FILE *fp;
    uint8_t *raw;
    int raw_size;
    int length = 0;
    int flength;
    int res;
    FILE *fp_pal; // for ci palette
    uint8_t *pal;
    int pal_len;
    char* pal_bin_filename;

    int valid = parse_arguments(argc, argv, &config);
    if (!valid || !config.bin_filename || !config.bin_filename || !config.img_filename) {
        print_usage();
        exit(EXIT_FAILURE);
    }

    if (config.mode == MODE_IMPORT) {
        printf("%s\n", config.bin_filename);
        if (config.truncate) {
            fp = fopen(config.bin_filename, "wb");
        }
        else {
            fp = fopen(config.bin_filename, "r+b");
        }
        if (!fp) {
            ERROR("Error opening binary file \"%s\"\n", config.bin_filename);
            return -1;
        }
        if (!config.truncate) {
            fseek(fp, config.offset, SEEK_SET);
        }
        switch (config.format) {
        case IMG_FORMAT_CI:
            imgr = png2rgba(config.img_filename, &config.width, &config.height);
            raw_size = config.width * config.height * config.depth / 8;
            raw = malloc(raw_size);
            if (!raw) {
                ERROR("Error allocating %u bytes\n", raw_size);
            }
            if (config.depth == 4) {
                pal_len = 16 * 2; // CI4
            }
            else {
                pal_len = 256 * 2; // CI8
            }
            pal = malloc(pal_len);
            if (!pal) {
                ERROR("Error allocating %u bytes for palette\n", pal_len);
            }
            length = rgba2rawci(raw, pal, &pal_len, imgr, config.width, config.height, config.depth);
            //length = rgba2raw(raw, imgr, config.width, config.height, config.depth);
            break;
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

        if (config.format == IMG_FORMAT_CI) {
            pal_bin_filename = getPaletteFilename(config.bin_filename);

            fp_pal = fopen(pal_bin_filename, "wb");
            INFO("Writing 0x%X bytes to palette file \"%s\"\n", pal_len, pal_bin_filename);
            flength = fwrite(pal, 1, pal_len, fp_pal);
            if (flength != pal_len) {
                ERROR("Error writing %d bytes to \"%s\"\n", pal_len, pal_bin_filename);
            }
            fclose(fp_pal);
            free(pal_bin_filename);
        }
    } else { // Export
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
        case IMG_FORMAT_CI:
            // Read Palette file
            pal_bin_filename = getPaletteFilename(config.bin_filename);
            fp_pal = fopen(pal_bin_filename, "rb");
            if (!fp_pal) {
                ERROR("Error opening \"%s\"\n", pal_bin_filename);
                return -1;
            }
            if (config.depth == 4) {
                pal_len = 16 * 2; // CI4
            }
            else {
                pal_len = 256 * 2; // CI8
            }

            pal = malloc(pal_len);
            if (config.offset > 0) {
                fseek(fp, config.offset, SEEK_SET);
            }
            flength = fread(pal, 1, pal_len, fp_pal);

            imgr = rawci2rgba(raw, pal, config.width, config.height, config.depth);
            res = rgba2png(config.img_filename, imgr, config.width, config.height);

            free(pal_bin_filename);
            break;
        default:
            return EXIT_FAILURE;
        }
        if (!res) {
            ERROR("Error writing to \"%s\"\n", config.img_filename);
        }
    }

    return 0;
}
