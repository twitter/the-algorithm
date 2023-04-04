/* skybox generator */

#define _GNU_SOURCE
#include <assert.h>
#include <string.h>
#include <stdint.h>
#include <stdlib.h>
#include <limits.h>
#include <stdio.h>
#include <stdbool.h>
#include <math.h>

#include "n64graphics.h"
#include "utils.h"

#define SKYCONV_ENCODING ENCODING_U8

typedef struct {
    rgba *px;
    bool useless;
    unsigned int pos;
} TextureTile;

typedef enum {
    InvalidType = -1,
    Skybox,
    Cake,
    CakeEU,
    ImageType_MAX
} ImageType;

typedef enum {
    InvalidMode = -1,
    Combine,
    Split
} OperationMode;

typedef struct {
    int imageWidth, imageHeight;
    int tileWidth, tileHeight;
    int numCols, numRows;
    bool wrapX;
    bool optimizePositions;
} ImageProps;

static const ImageProps IMAGE_PROPERTIES[ImageType_MAX][2] = {
    [Skybox] = {
        {248, 248, 31, 31, 8, 8, true, true},
        {256, 256, 32, 32, 8, 8, true, true},
    },
    [Cake] = {
        {316, 228, 79, 19, 4, 12, false, false},
        {320, 240, 80, 20, 4, 12, false, false},
    },
    [CakeEU] = {
        {320, 224, 64, 32, 5, 7, false, false},
        {320, 224, 64, 32, 5, 7, false, false},
    },
};

typedef struct {
    int cols, rows;
} TableDimension;

static const TableDimension TABLE_DIMENSIONS[ImageType_MAX] = {
    [Skybox]   = {8, 10},
    [Cake]     = {4, 12},
    [CakeEU]   = {5,  7},
};

TextureTile *tiles;
ImageType type = InvalidType;
OperationMode mode = InvalidMode;
char *programName;
char *input, *output;
char *writeDir;
char skyboxName[256];
bool expanded = false;
bool writeTiles;

static void allocate_tiles() {
    const ImageProps props = IMAGE_PROPERTIES[type][true];
    int tileWidth = props.tileWidth;
    int tileHeight = props.tileHeight;
    int numRows = props.numRows;
    int numCols = props.numCols;

    int tileSize = tileWidth * tileHeight * sizeof(rgba);
    int totalSize = numRows * numCols * tileSize;
    tiles = calloc(1, numRows * numCols * sizeof(TextureTile));
    rgba *tileData = calloc(1, totalSize);
    for (int row = 0; row < numRows; ++row) {
        for (int col = 0; col < numCols; ++col) {
            tiles[row * numCols + col].px = (tileData + (row * numCols + col) * (tileWidth * tileHeight));
        }
    }
}

static void free_tiles() {
    free(tiles->px);
    free(tiles);
}

static void split_tile(int col, int row, rgba *image, bool expanded) {
    const ImageProps props = IMAGE_PROPERTIES[type][expanded];
    int tileWidth = props.tileWidth;
    int tileHeight = props.tileHeight;
    int imageWidth = props.imageWidth;
    int numCols = props.numCols;

    int expandedWidth = IMAGE_PROPERTIES[type][true].tileWidth;

    for (int y = 0; y < tileHeight; y++) {
        for (int x = 0; x < tileWidth; x++) {
            int ny = row * tileHeight + y;
            int nx = col * tileWidth + x;
            tiles[row * numCols + col].px[y * expandedWidth + x] = image[(ny * imageWidth + nx)];
        }
    }
}

static void expand_tiles(ImageType imageType) {
    const ImageProps props = IMAGE_PROPERTIES[imageType][true];
    int numRows = props.numRows;
    int numCols = props.numCols;
    int tileWidth = props.tileWidth;
    int tileHeight = props.tileHeight;

    // If the image type wraps,
    // Copy each tile's left edge to the previous tile's right edge
    // Each tile's height is still tileHeight - 1
    if (props.wrapX) {
        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col) {
                int nextCol = (col + 1) % numCols;
                for (int y = 0; y < (tileHeight - 1); ++y) {
                    tiles[row * numCols + col].px[(tileWidth - 1) + y * tileWidth] = tiles[row * numCols + nextCol].px[y * tileWidth];
                }
            }
        }
    } else {
        // Don't wrap, copy the second to last column instead.
        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols - 1; ++col) {
                int nextCol = (col + 1) % numCols;
                for (int y = 0; y < (tileHeight - 1); ++y) {
                    tiles[row * numCols + col].px[(tileWidth - 1) + y * tileWidth] = tiles[row * numCols + nextCol].px[y * tileWidth];
                }
            }
            for (int y = 0; y < (tileHeight - 1); ++y) {
                tiles[row * numCols + (numCols - 1)].px[(tileWidth - 1) + y * tileWidth] = tiles[row * numCols + (numCols - 1)].px[(tileWidth - 2) + y * tileWidth];
            }

        }
    }

    // Copy each tile's top edge to the previous tile's bottom edge, EXCEPT for the bottom row, which
    // just duplicates its second-to-last row
    for (int row = 0; row < numRows; ++row) {
        if (row < numRows - 1) {
            for (int col = 0; col < numCols; ++col) {
                int nextRow = (row + 1) % numRows;
                for (int x = 0; x < tileWidth; ++x) {
                    tiles[row * numCols + col].px[x + (tileHeight - 1) * tileWidth] = tiles[nextRow * numCols + col].px[x];
                }
            }
        }
        // For the last row of tiles, duplicate each one's second to last row
        else {
            for (int col = 0; col < numCols; ++col) {
                for (int x = 0; x < tileWidth; ++x) {
                    tiles[row * numCols + col].px[x + (tileHeight - 1) * tileWidth] = tiles[row * numCols + col].px[x + (tileHeight - 2) * tileWidth];
                }
            }
        }
    }
}

static void init_tiles(rgba *image, bool expanded) {
    const ImageProps props = IMAGE_PROPERTIES[type][expanded];

    for (int row = 0; row < props.numRows; row++) {
        for (int col = 0; col < props.numCols; col++) {
            split_tile(col, row, image, expanded);
        }
    }

    // Expand the tiles to their full size
    if (!expanded) {
        expand_tiles(type);
    }
}

static void assign_tile_positions() {
    const ImageProps props = IMAGE_PROPERTIES[type][true];
    const size_t TILE_SIZE = props.tileWidth * props.tileHeight * sizeof(rgba);

    unsigned int newPos = 0;
    for (int i = 0; i < props.numRows * props.numCols; i++) {
        if (props.optimizePositions) {
            for (int j = 0; j < i; j++) {
                if (!tiles[j].useless && memcmp(tiles[j].px, tiles[i].px, TILE_SIZE) == 0) {
                    tiles[i].useless = 1;
                    tiles[i].pos = j;
                    break;
                }
            }
        }

        if (!tiles[i].useless) {
            tiles[i].pos = newPos;
            newPos++;
        }
    }
}

// Provide a replacement for realpath on Windows
#ifdef _WIN32
#define realpath(path, resolved_path) _fullpath(resolved_path, path, PATH_MAX)
#endif

/* write pngs to disc */
void write_tiles() {
    const ImageProps props = IMAGE_PROPERTIES[type][true];
    char buffer[PATH_MAX];
    char skyboxName[PATH_MAX];

    if (realpath(writeDir, buffer) == NULL) {
        fprintf(stderr, "err: Could not find find img dir %s", writeDir);
        exit(EXIT_FAILURE);
    }

    strcat(buffer, "/");

    switch(type) {
        case Skybox:
            strcat(buffer, skyboxName);
        break;
        case Cake:
            strcat(buffer, "cake");
        break;
        case CakeEU:
            strcat(buffer, "cake_eu");
        break;
        default:
            exit(EXIT_FAILURE);
        break;
    }

    int dirLength = strlen(buffer);
    char *filename = buffer + dirLength;
    for (int i = 0; i < props.numRows * props.numCols; i++) {
        if (!tiles[i].useless) {
            *filename = 0;
            snprintf(filename, PATH_MAX, ".%d.rgba16.png", tiles[i].pos);
            rgba2png(buffer, tiles[i].px, props.tileWidth, props.tileHeight);
        }
    }
}

static unsigned int get_index(TextureTile *t, unsigned int i) {
    if (t[i].useless) {
        i = t[i].pos;
    }
    return t[i].pos;
}

static void print_raw_data(FILE *cFile, TextureTile *tile) {
    ImageProps props = IMAGE_PROPERTIES[type][true];
    uint8_t *raw = malloc(props.tileWidth * props.tileHeight * 2);
    int size = rgba2raw(raw, tile->px, props.tileWidth, props.tileHeight, 16);
    fprint_write_output(cFile, SKYCONV_ENCODING, raw, size);
    free(raw);
}

static void write_skybox_c() { /* write c data to disc */
    const ImageProps props = IMAGE_PROPERTIES[type][true];

    char fBuffer[PATH_MAX] = "";
    FILE *cFile;

    if (realpath(output, fBuffer) == NULL) {
        fprintf(stderr, "err: Could not find find src dir %s", output);
        exit(EXIT_FAILURE);
    }

    sprintf(fBuffer, "%s/%s_skybox.c", output, skyboxName);
    cFile = fopen(fBuffer, "w"); /* reset file */

    /* setup C file */

    if (cFile == NULL) {
        fprintf(stderr, "err: Could not open %s\n", fBuffer);
    }

    fprintf(cFile, "#include \"types.h\"\n\n#include \"make_const_nonconst.h\"\n\n");

    for (int i = 0; i < props.numRows * props.numCols; i++) {
        if (!tiles[i].useless) {
            fprintf(cFile, "ALIGNED8 static const Texture %s_skybox_texture_%05X[] = {\n", skyboxName, tiles[i].pos);

            print_raw_data(cFile, &tiles[i]);

            fputs("};\n\n", cFile);
        }
    }

    fprintf(cFile, "const Texture *const %s_skybox_ptrlist[] = {\n", skyboxName);

    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 10; col++) {
            fprintf(cFile, "%s_skybox_texture_%05X,\n", skyboxName, get_index(tiles, row * 8 + (col % 8)));
        }
    }

    fputs("};\n\n", cFile);
    fclose(cFile);
}

static void write_cake_c() {
    char buffer[PATH_MAX] = "";
    if (realpath(output, buffer) == NULL) {
        fprintf(stderr, "err: Could not find find src dir %s", output);
        exit(EXIT_FAILURE);
    }

    if (type == CakeEU) {
        strcat(buffer, "/cake_eu.inc.c");
    }
    else {
        strcat(buffer, "/cake.inc.c");
    }

    FILE *cFile = fopen(buffer, "w");

    const char *euSuffx = "";
    if (type == CakeEU) {
        euSuffx = "eu_";
    }

    int numTiles = TABLE_DIMENSIONS[type].cols * TABLE_DIMENSIONS[type].rows;
    for (int i = 0; i < numTiles; ++i) {
        fprintf(cFile, "ALIGNED8 static const Texture cake_end_texture_%s%d[] = {\n", euSuffx, i);
        print_raw_data(cFile, &tiles[i]);
        fputs("};\n\n", cFile);
    }
    fclose(cFile);
}

// input: the skybox tiles + the table = up to 64 32x32 images (rgba16) + 80 pointers (u32)
// some pointers point to duplicate entries
void combine_skybox(const char *input, const char *output) {
    enum { W = 10, H = 8, W2 = 8 };

    FILE *file = fopen(input, "rb");
    if (!file) goto fail;
    if (fseek(file, 0, SEEK_END)) goto fail;

    ssize_t fileSize = ftell(file);
    if (fileSize < 8*10*4) goto fail;
    rewind(file);

    size_t tableIndex = fileSize - 8*10*4;
    if (tableIndex % (32*32*2) != 0) goto fail;

    // there are at most 64 tiles before the table
    rgba *tiles[8*8];
    size_t tileIndex = 0;
    for (size_t pos = 0; pos < tableIndex; pos += 32*32*2) {
        uint8_t buf[32*32*2];
        if (fread(buf, sizeof(buf), 1, file) != 1) goto fail;
        tiles[tileIndex] = raw2rgba(buf, 32, 32, 16);
        tileIndex++;
    }

    uint32_t table[W*H];
    if (fread(table, sizeof(table), 1, file) != 1) goto fail;

    reverse_endian((unsigned char *) table, W*H*4);

    uint32_t base = table[0];
    for (int i = 0; i < W*H; i++) {
        table[i] -= base;
    }

    // Convert the 256x256 skybox to an editable 248x248 image by skipping the duplicated rows and columns
    // every 32nd column is a repeat of the 33rd, and
    // every 32nd row is a repeat of the 33rd, EXCEPT for the last row, but that only matters when
    // expanding the tiles
    rgba combined[31*H * 31*W2];
    for (int i = 0; i < H; i++) {
        for (int j = 0; j < W2; j++) {
            int index = table[i*W+j] / 0x800;
            for (int y = 0; y < 31; y++) {
                for (int x = 0; x < 31; x++) {
                    combined[(i*31 + y) * (31*W2) + (j*31 + x)] = tiles[index][y*32 + x];
                }
            }
        }
    }
    if (!rgba2png(output, combined, 31*W2, 31*H)) {
        fprintf(stderr, "Failed to write skybox image.\n");
        exit(1);
    }
    return;
fail:
    fprintf(stderr, "Failed to read skybox binary.\n");
    exit(1);
}

void combine_cakeimg(const char *input, const char *output, bool eu) {
    int W, H, SMALLH, SMALLW;
    if (eu) {
        W = 5;
        H = 7;
        SMALLH = 32;
        SMALLW = 64;
    } else {
        W = 4;
        H = 12;
        SMALLH = 20;
        SMALLW = 80;
    }

    FILE *file = fopen(input, "rb");
    if (!file) goto fail;

    rgba *combined;
    if (!eu) {
        combined = malloc((SMALLH-1)*H * (SMALLW-1)*W * sizeof(rgba));
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                //Read the full tile
                uint8_t buf[SMALLH * SMALLW * 2];
                if (fread(buf, sizeof(buf), 1, file) != 1) goto fail;
                rgba *tile = raw2rgba(buf, SMALLH, SMALLW, 16);

                //Only write the unique parts of each tile
                for (int y = 0; y < SMALLH - 1; y++) {
                    for (int x = 0; x < SMALLW - 1; x++) {
                        combined[(i*(SMALLH-1) + y) * (SMALLW-1)*W + (j*(SMALLW-1) + x)] = tile[y*(SMALLW) + x];
                    }
                }
            }
        }
        if (!rgba2png(output, combined, (SMALLW-1)*W, (SMALLH-1)*H)) {
            fprintf(stderr, "Failed to write cake image.\n");
            exit(1);
        }
    }
    else {
        combined = malloc(SMALLH*H * SMALLW*W * sizeof(rgba));
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                uint8_t buf[SMALLH * SMALLW * 2];
                if (fread(buf, sizeof(buf), 1, file) != 1) goto fail;
                rgba *tile = raw2rgba(buf, SMALLH, SMALLW, 16);
                for (int y = 0; y < SMALLH; y++) {
                    for (int x = 0; x < SMALLW; x++) {
                        combined[(i*SMALLH + y) * SMALLW*W + (j*SMALLW + x)] = tile[y*SMALLW + x];
                    }
                }
            }
        }
        if (!rgba2png(output, combined, SMALLW*W, SMALLH*H)) {
            fprintf(stderr, "Failed to write cake image.\n");
            exit(1);
        }
    }
    return;
fail:
    fprintf(stderr, "Failed to read cake binary.\n");
    exit(1);
}

// Modified from n64split
static void usage() {
    fprintf(stderr,
            "Usage: %s --type sky|cake|cake_eu {--combine INPUT OUTPUT | --split INPUT OUTPUT}\n"
            "\n"
            "Optional arguments:\n"
            " --write-tiles OUTDIR      Also create the individual tiles' PNG files\n", programName);
}

// Modified from n64split
static int parse_arguments(int argc, char *argv[]) {
    programName = argv[0];
    for (int i = 1; i < argc; ++i) {
        if (strcmp(argv[i], "--combine") == 0) {
            if (++i >= argc || mode != InvalidMode) {
                goto invalid;
            }

            mode = Combine;
            input = argv[i];
            if (++i >= argc) {
                goto invalid;
            }

            output = argv[i];
        }

        if (strcmp(argv[i], "--split") == 0) {
            if (++i >= argc || mode != InvalidMode) {
                goto invalid;
            }

            mode = Split;
            input = argv[i];
            if (++i >= argc) {
                goto invalid;
            }

            output = argv[i];
        }
        
        if (strcmp(argv[i], "--type") == 0) {
            if (++i >= argc || type != InvalidType) {
                goto invalid;
            }

            if (strcmp(argv[i], "sky") == 0) {
                type = Skybox;
            } else if(strcmp(argv[i], "cake-eu") == 0) {
                type = CakeEU;
            } else if(strcmp(argv[i], "cake") == 0) {
                type = Cake;
            }
        }

        if (strcmp(argv[i], "--write-tiles") == 0) {
            if (++i >= argc || argv[i][0] == '-') {
                goto invalid;
            }

            writeTiles = true;
            writeDir = argv[i];
        }
    }

    return 1;
invalid:
    usage();
    return 0;
}

bool imageMatchesDimensions(int width, int height) {
    bool matchesDimensions = false;
    for (int expand = false; expand <= true; ++expand) {
        if (width  == IMAGE_PROPERTIES[type][expand].imageWidth &&
            height == IMAGE_PROPERTIES[type][expand].imageHeight) {
            matchesDimensions = true;
            expanded = expand;
            break;
        }
    }
    if (!matchesDimensions) {
        if (type != CakeEU) {
            fprintf(stderr, "err: That type of image must be either %d x %d or %d x %d. Yours is %d x %d.\n",
                    IMAGE_PROPERTIES[type][false].imageWidth, IMAGE_PROPERTIES[type][false].imageHeight,
                    IMAGE_PROPERTIES[type][true].imageWidth, IMAGE_PROPERTIES[type][true].imageHeight,
                    width, height);
        }
        else {
            fprintf(stderr, "err: That type of image must be %d x %d. Yours is %d x %d.\n",
                    IMAGE_PROPERTIES[type][true].imageWidth, IMAGE_PROPERTIES[type][true].imageHeight,
                    width, height);

        }
        return false;
    }

    if (type == CakeEU) {
        expanded = true;
    }

    return true;
}

int main(int argc, char *argv[]) {
    if (parse_arguments(argc, argv) == false) {
        return EXIT_FAILURE;
    }

    if (type == Skybox && mode == Split) {
        // Extract the skybox's name (ie: bbh, bidw) from the input png
        char *base = basename(input);
        strcpy(skyboxName, base);
        char *extension = strrchr(skyboxName, '.');
        if (extension) *extension = '\0';
    }

    switch (mode) {
        case Combine:
            switch (type) {
                case Skybox:
                    combine_skybox(input, output);
                break;
                case Cake:
                    combine_cakeimg(input, output, 0);
                break;
                case CakeEU:
                    combine_cakeimg(input, output, 1);
                break;
                default:
                    usage();
                    return EXIT_FAILURE;
                break;
            }
        break;

        case Split: {
            int width, height;
            rgba *image = png2rgba(input, &width, &height);
            if (image == NULL) {
                fprintf(stderr, "err: Could not load image %s\n", argv[1]);
                return EXIT_FAILURE;
            }

            if (!imageMatchesDimensions(width, height)) {
                return EXIT_FAILURE;
            }

            allocate_tiles();
            
            init_tiles(image, expanded);
            switch (type) {
                case Skybox:
                    assign_tile_positions();
                    write_skybox_c();
                    break;
                case Cake:
                case CakeEU:
                    assign_tile_positions();
                    write_cake_c();
                    break;
                default:
                    fprintf(stderr, "err: Unknown image type.\n");
                    return EXIT_FAILURE;
                    break;
            }

            if (writeTiles) {
                write_tiles();
            }
            free_tiles();
            free(image);
        } break;
        default:
            usage();
            return EXIT_FAILURE;
        break;
    }


    return EXIT_SUCCESS;
}
