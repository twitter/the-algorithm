#pragma once

#include <string>
#include <cstdlib>
#include <unistd.h>
#include <stdexcept>
#include <inttypes.h>
#include <stdint.h>

namespace twml {
class BlockFormatReader {
 private:
  int record_size_;
  long block_pos_;
  long block_end_;
  char classname_[1024];

  int read_one_record_size();
  int read_int();
  int consume_marker(int scan);
  int unpack_varint_i32();
  int unpack_tag_and_wiretype(uint32_t *tag, uint32_t *wiretype);
  int unpack_string(char *out, uint64_t max_out_len);

 public:
  BlockFormatReader();
  bool next();
  uint64_t current_size() const { return record_size_; }

  virtual uint64_t read_bytes(void *dest, int size, int count) = 0;
};
}
