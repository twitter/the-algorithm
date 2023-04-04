#include <stdio.h>
#include <stdlib.h>

#include "n64cksum.h"
#include "utils.h"

#define N64CKSUM_VERSION "0.1"

// compute N64 ROM checksums
// buf: buffer with extended SM64 data
// cksum: two element array to write CRC1 and CRC2 to
void n64cksum_calc_6102(unsigned char *buf, unsigned int cksum[]) {
   uint32_t t2, t3, t4, t6, t7, t8, s0;
   uint32_t a0, a1, a2, a3;
   uint32_t v0, v1;
   uint32_t seed, end_offset, cur_offset, buf_offset;

   // derived from the SM64 boot code
   seed = 0xF8CA4DDB; // 0x3f * 0x5d588b65;
   end_offset = 0x100000;
   cur_offset = 0;
   buf_offset = 0x1000;
   seed++;
   a3 = seed;
   t2 = seed;
   t3 = seed;
   s0 = seed;
   a2 = seed;
   t4 = seed;

   do {
      v0 = read_u32_be(&buf[buf_offset]);
      v1 = a3 + v0;
      a1 = v1;
      if (v1 < a3) {
         t2++;
      }
      v1 = v0 & 0x1F;
      t7 = 32 - v1;
      t8 = v0 >> t7;
      t6 = v0 << v1;
      a0 = t6 | t8;
      a3 = a1;
      t3 ^= v0;
      s0 += a0;
      if (a2 < v0) {
         a2 ^= a3 ^ v0;
      } else {
         a2 ^= a0;
      }
      cur_offset += 4;
      t7 = v0 ^ s0;
      buf_offset += 4;
      t4 += t7;
   } while (cur_offset != end_offset);

   cksum[0] = (a3 ^ t2) ^ t3;
   cksum[1] = (s0 ^ a2) ^ t4;
}

void n64cksum_update_checksums(uint8_t *buf)
{
   unsigned int cksum_offsets[] = {0x10, 0x14};
   uint32_t read_cksum[2];
   uint32_t calc_cksum[2];
   int i;

   // assume CIC-NUS-6102
   INFO("BootChip: CIC-NUS-6102\n");

   // calculate new N64 header checksum
   n64cksum_calc_6102(buf, calc_cksum);

   // mimic the n64sums output
   for (i = 0; i < 2; i++) {
      read_cksum[i] = read_u32_be(&buf[cksum_offsets[i]]);
      INFO("CRC%d: 0x%08X ", i+1, read_cksum[i]);
      INFO("Calculated: 0x%08X ", calc_cksum[i]);
      if (calc_cksum[i] == read_cksum[i]) {
         INFO("(Good)\n");
      } else {
         INFO("(Bad)\n");
      }
   }

   // write checksums into header
   INFO("Writing back calculated Checksum\n");
   write_u32_be(&buf[cksum_offsets[0]], calc_cksum[0]);
   write_u32_be(&buf[cksum_offsets[1]], calc_cksum[1]);
}

#ifdef N64CKSUM_STANDALONE
static void print_usage(void)
{
   ERROR("Usage: n64cksum ROM [ROM_OUT]\n"
         "\n"
         "n64cksum v" N64CKSUM_VERSION ": N64 ROM checksum calculator\n"
         "\n"
         "File arguments:\n"
         " ROM          input ROM file\n"
         " ROM_OUT      output ROM file (default: overwrites input ROM)\n");
}

int main(int argc, char *argv[])
{
   unsigned char *rom_data;
   char *file_in;
   char *file_out;
   long length;
   long write_length;
   if (argc < 2) {
      print_usage();
      return EXIT_FAILURE;
   }

   file_in = argv[1];
   if (argc > 2) {
      file_out = argv[2];
   } else {
      file_out = argv[1];
   }

   length = read_file(file_in, &rom_data);
   if (length < 0) {
      ERROR("Error reading input file \"%s\"\n", file_in);
      return EXIT_FAILURE;
   }

   n64cksum_update_checksums(rom_data);

   write_length = write_file(file_out, rom_data, length);

   free(rom_data);

   if (write_length != length) {
      ERROR("Error writing to output file \"%s\"\n", file_out);
      return EXIT_FAILURE;
   }

   return EXIT_SUCCESS;
}
#endif // N64CKSUM_STANDALONE
