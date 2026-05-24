#pragma once
#include <twml/defines.h>
#include <cstdlib>
#include <cstdio>
#include <unistd.h>
#include <cinttypes>
#include <cstdint>

#ifndef PATH_MAX
#define PATH_MAX (8096)
#endif

#ifdef __cplusplus
extern "C" {
#endif

  struct block_format_writer__;
  typedef block_format_writer__ * block_format_writer;

#ifdef __cplusplus
}
#endif


#ifdef __cplusplus
namespace twml {
    class BlockFormatWriter {
    private:
        const char *file_name_;
        FILE *outputfile_;
        char temp_file_name_[PATH_MAX];
        int record_index_;
        int records_per_block_;

        int pack_tag_and_wiretype(FILE *file, uint32_t tag, uint32_t wiretype);
        int pack_varint_i32(FILE *file, int value);
        int pack_string(FILE *file, const char *in, size_t in_len);
        int write_int(FILE *file, int value);

    public:
        BlockFormatWriter(const char *file_name, int record_per_block);
        ~BlockFormatWriter();
        int write(const char *class_name, const char *record, int record_len) ;
        int flush();
        block_format_writer getHandle();
      };

      BlockFormatWriter *getBlockFormatWriter(block_format_writer w);
} //twml namespace
#endif

#ifdef __cplusplus
extern "C" {
#endif
twml_err block_format_writer_create(block_format_writer *w, const char *file_name, int records_per_block);
twml_err block_format_write(block_format_writer w, const char *class_name, const char *record, int record_len);
twml_err block_format_flush(block_format_writer w);
twml_err block_format_writer_delete(const block_format_writer w);
#ifdef __cplusplus
}
#endif
