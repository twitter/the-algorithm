#include <dirent.h>
#include <fcntl.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#if defined(_MSC_VER) || defined(__MINGW32__)
  #include <io.h>
  #include <sys/utime.h>
#else
  #include <unistd.h>
  #include <utime.h>
#endif

#include "utils.h"

// global verbosity setting
int g_verbosity = 0;

int read_s16_be(unsigned char *buf)
{
   unsigned tmp = read_u16_be(buf);
   int ret;
   if (tmp > 0x7FFF) {
      ret = -((int)0x10000 - (int)tmp);
   } else {
      ret = (int)tmp;
   }
   return ret;
}

float read_f32_be(unsigned char *buf)
{
   union {uint32_t i; float f;} ret;
   ret.i = read_u32_be(buf);
   return ret.f;
}

int is_power2(unsigned int val)
{
   while (((val & 1) == 0) && (val > 1)) {
      val >>= 1;
   }
   return (val == 1);
}

void fprint_hex(FILE *fp, const unsigned char *buf, int length)
{
   int i;
   for (i = 0; i < length; i++) {
      fprint_byte(fp, buf[i]);
      fputc(' ', fp);
   }
}

void fprint_hex_source(FILE *fp, const unsigned char *buf, int length)
{
   int i;
   for (i = 0; i < length; i++) {
      if (i > 0) fputs(", ", fp);
      fputs("0x", fp);
      fprint_byte(fp, buf[i]);
   }
}

void print_hex(const unsigned char *buf, int length)
{
   fprint_hex(stdout, buf, length);
}

void swap_bytes(unsigned char *data, long length)
{
   long i;
   unsigned char tmp;
   for (i = 0; i < length; i += 2) {
      tmp = data[i];
      data[i] = data[i+1];
      data[i+1] = tmp;
   }
}

void reverse_endian(unsigned char *data, long length)
{
   long i;
   unsigned char tmp;
   for (i = 0; i < length; i += 4) {
      tmp = data[i];
      data[i] = data[i+3];
      data[i+3] = tmp;
      tmp = data[i+1];
      data[i+1] = data[i+2];
      data[i+2] = tmp;
   }
}

long filesize(const char *filename)
{
   struct stat st;

   if (stat(filename, &st) == 0) {
      return st.st_size;
   }

   return -1;
}

void touch_file(const char *filename)
{
   int fd;
   //fd = open(filename, O_WRONLY|O_CREAT|O_NOCTTY|O_NONBLOCK, 0666);
   fd = open(filename, O_WRONLY|O_CREAT, 0666);
   if (fd >= 0) {
      utime(filename, NULL);
      close(fd);
   }
}

long read_file(const char *file_name, unsigned char **data)
{
   FILE *in;
   unsigned char *in_buf = NULL;
   long file_size;
   long bytes_read;
   in = fopen(file_name, "rb");
   if (in == NULL) {
      return -1;
   }

   // allocate buffer to read from offset to end of file
   fseek(in, 0, SEEK_END);
   file_size = ftell(in);

   // sanity check
   if (file_size > 256*MB) {
      return -2;
   }

   in_buf = malloc(file_size);
   fseek(in, 0, SEEK_SET);

   // read bytes
   bytes_read = fread(in_buf, 1, file_size, in);
   if (bytes_read != file_size) {
      return -3;
   }

   fclose(in);
   *data = in_buf;
   return bytes_read;
}

long write_file(const char *file_name, unsigned char *data, long length)
{
   FILE *out;
   long bytes_written;
   // open output file
   out = fopen(file_name, "wb");
   if (out == NULL) {
      perror(file_name);
      return -1;
   }
   bytes_written = fwrite(data, 1, length, out);
   fclose(out);
   return bytes_written;
}

void generate_filename(const char *in_name, char *out_name, char *extension)
{
   char tmp_name[FILENAME_MAX];
   int len;
   int i;
   strcpy(tmp_name, in_name);
   len = strlen(tmp_name);
   for (i = len - 1; i > 0; i--) {
      if (tmp_name[i] == '.') {
         break;
      }
   }
   if (i <= 0) {
      i = len;
   }
   tmp_name[i] = '\0';
   sprintf(out_name, "%s.%s", tmp_name, extension);
}

char *basename(const char *name)
{
   const char *base = name;
   while (*name) {
      if (*name++ == '/') {
         base = name;
      }
   }
   return (char *)base;
}

void make_dir(const char *dir_name)
{
   struct stat st = {0};
   if (stat(dir_name, &st) == -1) {
      mkdir(dir_name, 0755);
   }
}

long copy_file(const char *src_name, const char *dst_name)
{
   unsigned char *buf;
   long bytes_written;
   long bytes_read;

   bytes_read = read_file(src_name, &buf);

   if (bytes_read > 0) {
      bytes_written = write_file(dst_name, buf, bytes_read);
      if (bytes_written != bytes_read) {
         bytes_read = -1;
      }
      free(buf);
   }

   return bytes_read;
}

void dir_list_ext(const char *dir, const char *extension, dir_list *list)
{
   char *pool;
   char *pool_ptr;
   struct dirent *entry;
   DIR *dfd;
   int idx;

   dfd = opendir(dir);
   if (dfd == NULL) {
      ERROR("Can't open '%s'\n", dir);
      exit(1);
   }

   pool = malloc(FILENAME_MAX * MAX_DIR_FILES);
   pool_ptr = pool;

   idx = 0;
   while ((entry = readdir(dfd)) != NULL && idx < MAX_DIR_FILES) {
      if (!extension || str_ends_with(entry->d_name, extension)) {
         sprintf(pool_ptr, "%s/%s", dir, entry->d_name);
         list->files[idx] = pool_ptr;
         pool_ptr += strlen(pool_ptr) + 1;
         idx++;
      }
   }
   list->count = idx;

   closedir(dfd);
}

void dir_list_free(dir_list *list)
{
   // assume first entry in array is allocated
   if (list->files[0]) {
      free(list->files[0]);
      list->files[0] = NULL;
   }
}

int str_ends_with(const char *str, const char *suffix)
{
   if (!str || !suffix) {
      return 0;
   }
   size_t len_str = strlen(str);
   size_t len_suffix = strlen(suffix);
   if (len_suffix > len_str) {
      return 0;
   }
   return (0 == strncmp(str + len_str - len_suffix, suffix, len_suffix));
}
