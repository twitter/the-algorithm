#include <twml/BlockFormatReader.h>
#include <cstring>
#include <stdexcept>

#define OFFSET_CHUNK                (32768)
#define RECORDS_PER_BLOCK           (100)

#define WIRE_TYPE_VARINT            (0)
#define WIRE_TYPE_64BIT             (1)
#define WIRE_TYPE_LENGTH_PREFIXED   (2)

/*
   This was all extracted from the ancient elephant bird scrolls
   https://github.com/twitter/elephant-bird/blob/master/core/src/main/java/com/twitter/elephantbird/mapreduce/io/BinaryBlockReader.java
*/

#define MARKER_SIZE (16)
static uint8_t _marker[MARKER_SIZE] = {
  0x29, 0xd8, 0xd5, 0x06, 0x58, 0xcd, 0x4c, 0x29,
  0xb2, 0xbc, 0x57, 0x99, 0x21, 0x71, 0xbd, 0xff
};


namespace twml {
BlockFormatReader::BlockFormatReader():
    record_size_(0), block_pos_(0), block_end_(0) {
  memset(classname_, 0, sizeof(classname_));
}


bool BlockFormatReader::next() {
  record_size_ = read_one_record_size();
  if (record_size_ < 0) {
    record_size_ = 0;
    return false;
  }
  return true;
}

int BlockFormatReader::read_int() {
  uint8_t buff[4];
  if (read_bytes(buff, 1, 4) != 4)
    return -1;
  return static_cast<int>(buff[0])
      | (static_cast<int>(buff[1] << 8))
      | (static_cast<int>(buff[2] << 16))
      | (static_cast<int>(buff[3] << 24));
}

int BlockFormatReader::consume_marker(int scan) {
  uint8_t buff[MARKER_SIZE];
  if (read_bytes(buff, 1, MARKER_SIZE) != MARKER_SIZE)
    return 0;

  while (memcmp(buff, _marker, MARKER_SIZE) != 0) {
    if (!scan) return 0;
    memmove(buff, buff + 1, MARKER_SIZE - 1);
    if (read_bytes(buff + MARKER_SIZE - 1, 1, 1) != 1)
      return 0;
  }
  return 1;
}

int BlockFormatReader::unpack_varint_i32() {
  int value = 0;
  for (int i = 0; i < 10; i++) {
    uint8_t x;
    if (read_bytes(&x, 1, 1) != 1)
      return -1;
    block_pos_++;
    value |= (static_cast<int>(x & 0x7F)) << (i * 7);
    if ((x & 0x80) == 0) break;
  }
  return value;
}


int BlockFormatReader::unpack_tag_and_wiretype(uint32_t *tag, uint32_t *wiretype) {
  uint8_t x;
  if (read_bytes(&x, 1, 1) != 1)
    return -1;

  block_pos_++;
  *tag = (x & 0x7f) >> 3;
  *wiretype = x & 7;
  if ((x & 0x80) == 0)
    return 0;

  return -1;
}

int BlockFormatReader::unpack_string(char *out, uint64_t max_out_len) {
  int len = unpack_varint_i32();
  if (len < 0) return -1;
  uint64_t slen = len;
  if (slen + 1 > max_out_len) return -1;
  uint64_t n = read_bytes(out, 1, slen);
  if (n != slen) return -1;
  block_pos_ += n;
  out[n] = 0;
  return 0;
}

int BlockFormatReader::read_one_record_size() {
  for (int i = 0; i < 2; i++) {
    if (block_end_ == 0) {
      while (consume_marker(1)) {
        int block_size = read_int();
        if (block_size > 0) {
          block_pos_ = 0;
          block_end_ = block_size;
          uint32_t tag, wiretype;
          if (unpack_tag_and_wiretype(&tag, &wiretype))
            throw std::invalid_argument("unsupported tag and wiretype");
          if (tag != 1 && wiretype != WIRE_TYPE_VARINT)
            throw std::invalid_argument("unexpected tag and wiretype");
          int version = unpack_varint_i32();
          if (version != 1)
            throw std::invalid_argument("unsupported version");
          if (unpack_tag_and_wiretype(&tag, &wiretype))
            throw std::invalid_argument("unsupported tag and wiretype");
          if (tag != 2 && wiretype != WIRE_TYPE_LENGTH_PREFIXED)
            throw std::invalid_argument("unexpected tag and wiretype");
          if (unpack_string(classname_, sizeof(classname_)-1))
            throw std::invalid_argument("unsupported class name");
          break;
        }
      }
    }
    if (block_pos_ < block_end_) {
      uint32_t tag, wiretype;
      if (unpack_tag_and_wiretype(&tag, &wiretype))
        throw std::invalid_argument("unsupported tag and wiretype");
      if (tag != 3 && wiretype != WIRE_TYPE_LENGTH_PREFIXED)
        throw std::invalid_argument("unexpected tag and wiretype");
      int record_size = unpack_varint_i32();
      block_pos_ += record_size;
      return record_size;
    } else {
      block_end_ = 0;
    }
  }
  return -1;
}
}  // namespace twml
