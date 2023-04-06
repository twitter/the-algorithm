#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

#define STBI_NO_LINEAR
#define STBI_NO_PSD
#define STBI_NO_TGA
#define STBI_NO_HDR
#define STBI_NO_PIC
#define STBI_NO_PNM
#define STB_IMAGE_WRITE_IMPLEMENTATION
#include <stb/stb_image_write.h>
#define STB_IMAGE_IMPLEMENTATION
#include <stb/stb_image.h>

#define GETBIT(buf, idx) ((buf[(idx)/8] >> (7-((idx)%8))) & 1)
#define SETBIT(buf, idx) buf[(idx)/8] |= (1 << (7-((idx)%8)))

#define IPL3_FONT_NCHARS       50
#define IPL3_FONT_CHAR_W       13
#define IPL3_FONT_CHAR_H       14
#define IPL3_FONT_CHAR_NPIXELS (IPL3_FONT_CHAR_W * IPL3_FONT_CHAR_H)
#define IPL3_FONT_CHAR_NBITS   (IPL3_FONT_CHAR_NPIXELS + 2)
#define IPL3_FONT_CHAR_NBYTES  (IPL3_FONT_CHAR_NBITS / 8)

#define IPL3_FONT_FILE_SIZE ((IPL3_FONT_NCHARS * IPL3_FONT_CHAR_NBYTES) + 0x12)

int ipl3font_decode(const char *binPath, const char *imgPath)
{
    FILE *binfp = fopen(binPath, "rb");

    if(binfp == NULL)
    {
        printf("error: could not open %s for input\n", binPath);
        return EXIT_FAILURE;
    }

    fseek(binfp, 0, SEEK_END);
    size_t binSize = ftell(binfp);

    if(binSize != IPL3_FONT_FILE_SIZE)
    {
        printf("error: font bin size invalid (must be 0x%X bytes)\n", IPL3_FONT_FILE_SIZE);
        fclose(binfp);
        return EXIT_FAILURE;
    }

    rewind(binfp);

    char *binBuf = (char *) malloc(binSize);
    if(fread(binBuf, 1, binSize, binfp) != binSize)
    {
        printf("error: failed to read from %s\n", binPath);
        fclose(binfp);
        return EXIT_FAILURE;
    }
    fclose(binfp);

    uint32_t outSize = IPL3_FONT_NCHARS * IPL3_FONT_CHAR_NPIXELS * sizeof(uint32_t);
    uint32_t *outRgba32 = (uint32_t *) malloc(outSize);
    int outIdx = 0;

    for(int nChar = 0; nChar < IPL3_FONT_NCHARS; nChar++)
    {
        for(int nRow = 0; nRow < IPL3_FONT_CHAR_H; nRow++)
        {
            for(int nCol = 0; nCol < IPL3_FONT_CHAR_W; nCol++)
            {
                int idx = (nChar * IPL3_FONT_CHAR_NBITS) + (nRow * IPL3_FONT_CHAR_W) + nCol;
                int bit = GETBIT(binBuf, idx);
                outRgba32[outIdx++] = (bit == 1) ? 0xFFFFFFFF : 0xFF000000;
            }
        }
    }

    int stbres = stbi_write_png(imgPath,
        IPL3_FONT_CHAR_W,
        IPL3_FONT_NCHARS * IPL3_FONT_CHAR_H,
        4,
        outRgba32,
        IPL3_FONT_CHAR_W * sizeof(uint32_t));

    if(stbres == 0)
    {
        printf("error: failed to write %s\n", imgPath);
        free(outRgba32);
        free(binBuf);
        return EXIT_FAILURE;
    }

    free(outRgba32);
    free(binBuf);
    return EXIT_SUCCESS;
}

int ipl3font_encode(const char *imgPath, const char *binPath)
{
    int x, y, channels_in_file;
    uint32_t *inRgba32 = (uint32_t *) stbi_load(imgPath, &x, &y, &channels_in_file, 4);

    if(inRgba32 == NULL)
    {
        printf("error: failed to load %s\n", imgPath);
        return EXIT_FAILURE;
    }

    if(x != IPL3_FONT_CHAR_W || y != IPL3_FONT_NCHARS * IPL3_FONT_CHAR_H)
    {
        printf("error: invalid ipl3 font image dimensions (must be %dx%d)\n",
            IPL3_FONT_CHAR_W, IPL3_FONT_NCHARS * IPL3_FONT_CHAR_H);
        stbi_image_free(inRgba32);
        return EXIT_FAILURE;
    }

    char *out = calloc(IPL3_FONT_FILE_SIZE, 1);

    int inIdx = 0;

    for(int nChar = 0; nChar < IPL3_FONT_NCHARS; nChar++)
    {
        for(int nRow = 0; nRow < IPL3_FONT_CHAR_H; nRow++)
        {
            for(int nCol = 0; nCol < IPL3_FONT_CHAR_W; nCol++)
            {
                // source pixels that are not 0xFFFFFFFF are ignored
                if(inRgba32[inIdx++] == 0xFFFFFFFF)
                {
                    int idx = (nChar * IPL3_FONT_CHAR_NBITS) + (nRow * IPL3_FONT_CHAR_W) + nCol;
                    SETBIT(out, idx);
                }
            }
        }
    }

    FILE * outfp = fopen(binPath, "wb");

    if(outfp == NULL)
    {
        printf("error: failed to write to %s\n", binPath);
        stbi_image_free(inRgba32);
        free(out);
        return EXIT_FAILURE;
    }

    fwrite(out, 1, IPL3_FONT_FILE_SIZE, outfp);
    fclose(outfp);

    stbi_image_free(inRgba32);
    free(out);

    return EXIT_SUCCESS;
}

int main(int argc, const char *argv[])
{
    if(argc < 4)
    {
        printf("error: no paths\n");
        printf("iplfontutil e <input_img> <output_bin>\n");
        printf("iplfontutil d <input_bin> <output_img>\n");
        return EXIT_FAILURE;
    }

    const char *mode = argv[1];

    if(strcmp(mode, "e") == 0)
    {
        return ipl3font_encode(argv[2], argv[3]);
    }
    else if(strcmp(mode, "d") == 0)
    {
        return ipl3font_decode(argv[2], argv[3]);
    }
    else
    {
        printf("error: unknown mode\n");
        return EXIT_FAILURE;
    }
}
