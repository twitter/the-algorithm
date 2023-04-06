#include <stdio.h>
#include <stdlib.h>

#include "libsm64.h"
#include "utils.h"

#define N64CKSUM_VERSION "0.1"

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

   sm64_update_checksums(rom_data);

   write_length = write_file(file_out, rom_data, length);

   free(rom_data);

   if (write_length != length) {
      ERROR("Error writing to output file \"%s\"\n", file_out);
      return EXIT_FAILURE;
   }

   return EXIT_SUCCESS;
}
