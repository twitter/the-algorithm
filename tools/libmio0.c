#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#if defined(_WIN32) || defined(_WIN64)
#include <io.h>
#include <fcntl.h>
#endif

#include "libmio0.h"
#include "utils.h"

// defines

#define MIO0_VERSION "0.1"

#define GET_BIT(buf, bit) ((buf)[(bit) / 8] & (1 << (7 - ((bit) % 8))))

// types
typedef struct
{
   int *indexes;
   int allocated;
   int count;
   int start;
} lookback;

// functions
#define LOOKBACK_COUNT 256
#define LOOKBACK_INIT_SIZE 128
static lookback *lookback_init(void)
{
   lookback *lb = malloc(LOOKBACK_COUNT * sizeof(*lb));
   for (int i = 0; i < LOOKBACK_COUNT; i++) {
      lb[i].allocated = LOOKBACK_INIT_SIZE;
      lb[i].indexes = malloc(lb[i].allocated * sizeof(*lb[i].indexes));
      lb[i].count = 0;
      lb[i].start = 0;
   }
   return lb;
}

static void lookback_free(lookback *lb)
{
   for (int i = 0; i < LOOKBACK_COUNT; i++) {
      free(lb[i].indexes);
   }
   free(lb);
}

static inline void lookback_push(lookback *lkbk, unsigned char val, int index)
{
   lookback *lb = &lkbk[val];
   if (lb->count == lb->allocated) {
      lb->allocated *= 4;
      lb->indexes = realloc(lb->indexes, lb->allocated * sizeof(*lb->indexes));
   }
   lb->indexes[lb->count++] = index;
}

static void PUT_BIT(unsigned char *buf, int bit, int val)
{
   unsigned char mask = 1 << (7 - (bit % 8));
   unsigned int offset = bit / 8;
   buf[offset] = (buf[offset] & ~(mask)) | (val ? mask : 0);
}

// used to find longest matching stream in buffer
// buf: buffer
// start_offset: offset in buf to look back from
// max_search: max number of bytes to find
// found_offset: returned offset found (0 if none found)
// returns max length of matching stream (0 if none found)
static int find_longest(const unsigned char *buf, int start_offset, int max_search, int *found_offset, lookback *lkbk)
{
   int best_length = 0;
   int best_offset = 0;
   int cur_length;
   int search_len;
   int farthest, off, i;
   int lb_idx;
   const unsigned char first = buf[start_offset];
   lookback *lb = &lkbk[first];

   // buf
   //  |    off        start                  max
   //  V     |+i->       |+i->                 |
   //  |--------------raw-data-----------------|
   //        |+i->       |      |+i->
   //                       +cur_length

   // check at most the past 4096 values
   farthest = MAX(start_offset - 4096, 0);
   // find starting index
   for (lb_idx = lb->start; lb_idx < lb->count && lb->indexes[lb_idx] < farthest; lb_idx++) {}
   lb->start = lb_idx;
   for ( ; lb_idx < lb->count && lb->indexes[lb_idx] < start_offset; lb_idx++) {
      off = lb->indexes[lb_idx];
      // check at most requested max or up until start
      search_len = MIN(max_search, start_offset - off);
      for (i = 0; i < search_len; i++) {
         if (buf[start_offset + i] != buf[off + i]) {
            break;
         }
      }
      cur_length = i;
      // if matched up until start, continue matching in already matched parts
      if (cur_length == search_len) {
         // check at most requested max less current length
         search_len = max_search - cur_length;
         for (i = 0; i < search_len; i++) {
            if (buf[start_offset + cur_length + i] != buf[off + i]) {
               break;
            }
         }
         cur_length += i;
      }
      if (cur_length > best_length) {
         best_offset = start_offset - off;
         best_length = cur_length;
      }
   }

   // return best reverse offset and length (may be 0)
   *found_offset = best_offset;
   return best_length;
}

// decode MIO0 header
// returns 1 if valid header, 0 otherwise
int mio0_decode_header(const unsigned char *buf, mio0_header_t *head)
{
   if (!memcmp(buf, "MIO0", 4)) {
      head->dest_size = read_u32_be(&buf[4]);
      head->comp_offset = read_u32_be(&buf[8]);
      head->uncomp_offset = read_u32_be(&buf[12]);
      return 1;
   }
   return 0;
}

void mio0_encode_header(unsigned char *buf, const mio0_header_t *head)
{
   memcpy(buf, "MIO0", 4);
   write_u32_be(&buf[4], head->dest_size);
   write_u32_be(&buf[8], head->comp_offset);
   write_u32_be(&buf[12], head->uncomp_offset);
}

int mio0_decode(const unsigned char *in, unsigned char *out, unsigned int *end)
{
   mio0_header_t head;
   unsigned int bytes_written = 0;
   int bit_idx = 0;
   int comp_idx = 0;
   int uncomp_idx = 0;
   int valid;

   // extract header
   valid = mio0_decode_header(in, &head);
   // verify MIO0 header
   if (!valid) {
      return -2;
   }

   // decode data
   while (bytes_written < head.dest_size) {
      if (GET_BIT(&in[MIO0_HEADER_LENGTH], bit_idx)) {
         // 1 - pull uncompressed data
         out[bytes_written] = in[head.uncomp_offset + uncomp_idx];
         bytes_written++;
         uncomp_idx++;
      } else {
         // 0 - read compressed data
         int idx;
         int length;
         int i;
         const unsigned char *vals = &in[head.comp_offset + comp_idx];
         comp_idx += 2;
         length = ((vals[0] & 0xF0) >> 4) + 3;
         idx = ((vals[0] & 0x0F) << 8) + vals[1] + 1;
         for (i = 0; i < length; i++) {
            out[bytes_written] = out[bytes_written - idx];
            bytes_written++;
         }
      }
      bit_idx++;
   }

   if (end) {
      *end = head.uncomp_offset + uncomp_idx;
   }

   return bytes_written;
}

int mio0_encode(const unsigned char *in, unsigned int length, unsigned char *out)
{
   unsigned char *bit_buf;
   unsigned char *comp_buf;
   unsigned char *uncomp_buf;
   unsigned int bit_length;
   unsigned int comp_offset;
   unsigned int uncomp_offset;
   unsigned int bytes_proc = 0;
   int bytes_written;
   int bit_idx = 0;
   int comp_idx = 0;
   int uncomp_idx = 0;
   lookback *lookbacks;

   // initialize lookback buffer
   lookbacks = lookback_init();

   // allocate some temporary buffers worst case size
   bit_buf = malloc((length + 7) / 8); // 1-bit/byte
   comp_buf = malloc(length); // 16-bits/2bytes
   uncomp_buf = malloc(length); // all uncompressed
   memset(bit_buf, 0, (length + 7) / 8);

   // encode data
   // special case for first byte
   lookback_push(lookbacks, in[0], 0);
   uncomp_buf[uncomp_idx] = in[0];
   uncomp_idx += 1;
   bytes_proc += 1;
   PUT_BIT(bit_buf, bit_idx++, 1);
   while (bytes_proc < length) {
      int offset;
      int max_length = MIN(length - bytes_proc, 18);
      int longest_match = find_longest(in, bytes_proc, max_length, &offset, lookbacks);
      // push current byte before checking next longer match
      lookback_push(lookbacks, in[bytes_proc], bytes_proc);
      if (longest_match > 2) {
         int lookahead_offset;
         // lookahead to next byte to see if longer match
         int lookahead_length = MIN(length - bytes_proc - 1, 18);
         int lookahead_match = find_longest(in, bytes_proc + 1, lookahead_length, &lookahead_offset, lookbacks);
         // better match found, use uncompressed + lookahead compressed
         if ((longest_match + 1) < lookahead_match) {
            // uncompressed byte
            uncomp_buf[uncomp_idx] = in[bytes_proc];
            uncomp_idx++;
            PUT_BIT(bit_buf, bit_idx, 1);
            bytes_proc++;
            longest_match = lookahead_match;
            offset = lookahead_offset;
            bit_idx++;
            lookback_push(lookbacks, in[bytes_proc], bytes_proc);
         }
         // first byte already pushed above
         for (int i = 1; i < longest_match; i++) {
            lookback_push(lookbacks, in[bytes_proc + i], bytes_proc + i);
         }
         // compressed block
         comp_buf[comp_idx] = (((longest_match - 3) & 0x0F) << 4) |
                              (((offset - 1) >> 8) & 0x0F);
         comp_buf[comp_idx + 1] = (offset - 1) & 0xFF;
         comp_idx += 2;
         PUT_BIT(bit_buf, bit_idx, 0);
         bytes_proc += longest_match;
      } else {
         // uncompressed byte
         uncomp_buf[uncomp_idx] = in[bytes_proc];
         uncomp_idx++;
         PUT_BIT(bit_buf, bit_idx, 1);
         bytes_proc++;
      }
      bit_idx++;
   }

   // compute final sizes and offsets
   // +7 so int division accounts for all bits
   bit_length = ((bit_idx + 7) / 8);
   // compressed data after control bits and aligned to 4-byte boundary
   comp_offset = ALIGN(MIO0_HEADER_LENGTH + bit_length, 4);
   uncomp_offset = comp_offset + comp_idx;
   bytes_written = uncomp_offset + uncomp_idx;

   // output header
   memcpy(out, "MIO0", 4);
   write_u32_be(&out[4], length);
   write_u32_be(&out[8], comp_offset);
   write_u32_be(&out[12], uncomp_offset);
   // output data
   memcpy(&out[MIO0_HEADER_LENGTH], bit_buf, bit_length);
   memcpy(&out[comp_offset], comp_buf, comp_idx);
   memcpy(&out[uncomp_offset], uncomp_buf, uncomp_idx);

   // free allocated buffers
   free(bit_buf);
   free(comp_buf);
   free(uncomp_buf);
   lookback_free(lookbacks);

   return bytes_written;
}

static FILE *mio0_open_out_file(const char *out_file) {
   if (strcmp(out_file, "-") == 0) {
#if defined(_WIN32) || defined(_WIN64)
      _setmode(_fileno(stdout), _O_BINARY);
#endif
      return stdout;
   } else {
      return fopen(out_file, "wb");
   }
}

int mio0_decode_file(const char *in_file, unsigned long offset, const char *out_file)
{
   mio0_header_t head;
   FILE *in;
   FILE *out;
   unsigned char *in_buf = NULL;
   unsigned char *out_buf = NULL;
   long file_size;
   int ret_val = 0;
   size_t bytes_read;
   int bytes_decoded;
   int bytes_written;
   int valid;

   in = fopen(in_file, "rb");
   if (in == NULL) {
      return 1;
   }

   // allocate buffer to read from offset to end of file
   fseek(in, 0, SEEK_END);
   file_size = ftell(in);
   in_buf = malloc(file_size - offset);
   fseek(in, offset, SEEK_SET);

   // read bytes
   bytes_read = fread(in_buf, 1, file_size - offset, in);
   if (bytes_read != file_size - offset) {
      ret_val = 2;
      goto free_all;
   }

   // verify header
   valid = mio0_decode_header(in_buf, &head);
   if (!valid) {
      ret_val = 3;
      goto free_all;
   }
   out_buf = malloc(head.dest_size);

   // decompress MIO0 encoded data
   bytes_decoded = mio0_decode(in_buf, out_buf, NULL);
   if (bytes_decoded < 0) {
      ret_val = 3;
      goto free_all;
   }

   // open output file
   out = mio0_open_out_file(out_file);
   if (out == NULL) {
      ret_val = 4;
      goto free_all;
   }

   // write data to file
   bytes_written = fwrite(out_buf, 1, bytes_decoded, out);
   if (bytes_written != bytes_decoded) {
      ret_val = 5;
   }

   // clean up
   if (out != stdout) {
      fclose(out);
   }
free_all:
   if (out_buf) {
      free(out_buf);
   }
   if (in_buf) {
      free(in_buf);
   }
   fclose(in);

   return ret_val;
}

int mio0_encode_file(const char *in_file, const char *out_file)
{
   FILE *in;
   FILE *out;
   unsigned char *in_buf = NULL;
   unsigned char *out_buf = NULL;
   size_t file_size;
   size_t bytes_read;
   int bytes_encoded;
   int bytes_written;
   int ret_val = 0;

   in = fopen(in_file, "rb");
   if (in == NULL) {
      return 1;
   }

   // allocate buffer to read entire contents of files
   fseek(in, 0, SEEK_END);
   file_size = ftell(in);
   fseek(in, 0, SEEK_SET);
   in_buf = malloc(file_size);

   // read bytes
   bytes_read = fread(in_buf, 1, file_size, in);
   if (bytes_read != file_size) {
      ret_val = 2;
      goto free_all;
   }

   // allocate worst case length
   out_buf = malloc(MIO0_HEADER_LENGTH + ((file_size+7)/8) + file_size);

   // compress data in MIO0 format
   bytes_encoded = mio0_encode(in_buf, file_size, out_buf);

   // open output file
   out = mio0_open_out_file(out_file);
   if (out == NULL) {
      ret_val = 4;
      goto free_all;
   }

   // write data to file
   bytes_written = fwrite(out_buf, 1, bytes_encoded, out);
   if (bytes_written != bytes_encoded) {
      ret_val = 5;
   }

   // clean up
   if (out != stdout) {
      fclose(out);
   }
free_all:
   if (out_buf) {
      free(out_buf);
   }
   if (in_buf) {
      free(in_buf);
   }
   fclose(in);

   return ret_val;
}

// mio0 standalone executable
#ifdef MIO0_STANDALONE
typedef struct
{
   char *in_filename;
   char *out_filename;
   unsigned int offset;
   int compress;
} arg_config;

static arg_config default_config =
{
   NULL,
   NULL,
   0,
   1
};

static void print_usage(void)
{
   ERROR("Usage: mio0 [-c / -d] [-o OFFSET] FILE [OUTPUT]\n"
         "\n"
         "mio0 v" MIO0_VERSION ": MIO0 compression and decompression tool\n"
         "\n"
         "Optional arguments:\n"
         " -c           compress raw data into MIO0 (default: compress)\n"
         " -d           decompress MIO0 into raw data\n"
         " -o OFFSET    starting offset in FILE (default: 0)\n"
         "\n"
         "File arguments:\n"
         " FILE        input file\n"
         " [OUTPUT]    output file (default: FILE.out), \"-\" for stdout\n");
   exit(1);
}

// parse command line arguments
static void parse_arguments(int argc, char *argv[], arg_config *config)
{
   int i;
   int file_count = 0;
   if (argc < 2) {
      print_usage();
      exit(1);
   }
   for (i = 1; i < argc; i++) {
      if (argv[i][0] == '-' && argv[i][1] != '\0') {
         switch (argv[i][1]) {
            case 'c':
               config->compress = 1;
               break;
            case 'd':
               config->compress = 0;
               break;
            case 'o':
               if (++i >= argc) {
                  print_usage();
               }
               config->offset = strtoul(argv[i], NULL, 0);
               break;
            default:
               print_usage();
               break;
         }
      } else {
         switch (file_count) {
            case 0:
               config->in_filename = argv[i];
               break;
            case 1:
               config->out_filename = argv[i];
               break;
            default: // too many
               print_usage();
               break;
         }
         file_count++;
      }
   }
   if (file_count < 1) {
      print_usage();
   }
}

int main(int argc, char *argv[])
{
   char out_filename[FILENAME_MAX];
   arg_config config;
   int ret_val;

   // get configuration from arguments
   config = default_config;
   parse_arguments(argc, argv, &config);
   if (config.out_filename == NULL) {
      config.out_filename = out_filename;
      sprintf(config.out_filename, "%s.out", config.in_filename);
   }

   // operation
   if (config.compress) {
      ret_val = mio0_encode_file(config.in_filename, config.out_filename);
   } else {
      ret_val = mio0_decode_file(config.in_filename, config.offset, config.out_filename);
   }

   switch (ret_val) {
      case 1:
         ERROR("Error opening input file \"%s\"\n", config.in_filename);
         break;
      case 2:
         ERROR("Error reading from input file \"%s\"\n", config.in_filename);
         break;
      case 3:
         ERROR("Error decoding MIO0 data. Wrong offset (0x%X)?\n", config.offset);
         break;
      case 4:
         ERROR("Error opening output file \"%s\"\n", config.out_filename);
         break;
      case 5:
         ERROR("Error writing bytes to output file \"%s\"\n", config.out_filename);
         break;
   }

   return ret_val;
}
#endif // MIO0_STANDALONE

