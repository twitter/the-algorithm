#include "internal/error.h"
#include <cstring>
#include <iostream>
#include <twml/BlockFormatWriter.h>

#define WIRE_TYPE_LENGTH_PREFIXED   (2)
#define WIRE_TYPE_VARINT            (0)

#ifndef PATH_MAX
#define PATH_MAX (8096)
#endif

#define MARKER_SIZE (16)
static uint8_t _marker[MARKER_SIZE] = {
        0x29, 0xd8, 0xd5, 0x06, 0x58, 0xcd, 0x4c, 0x29,
        0xb2, 0xbc, 0x57, 0x99, 0x21, 0x71, 0xbd, 0xff
};
namespace twml {

    BlockFormatWriter::BlockFormatWriter(const char *file_name, int record_per_block) :
      file_name_(file_name), record_index_(0), records_per_block_(record_per_block) {
      snprintf(temp_file_name_, PATH_MAX, "%s.block", file_name);
      std::fstream outputfile_(file_name_, std::ios::app);
    }

    int BlockFormatWriter::pack_tag_and_wiretype(std::fstream& fs, uint32_t tag, uint32_t wiretype) {
      uint8_t x = ((tag & 0x0f) << 3) | (wiretype & 0x7);
      fs.write(reinterpret_cast<char*>(&x), sizeof(x));
      if (fs.fail()) {
        return -1;
      }
      return 0;
    }

    int BlockFormatWriter::pack_varint_i32(std::fstream& fs, int value) {
      for (int i = 0; i < 10; i++) {
        uint8_t x = value & 0x7F;
        value = value >> 7;
        if (value != 0) x |= 0x80;
        fs.write(reinterpret_cast<char*>(&x), sizeof(x));
        if (fs.fail()) {
          return -1;
        }
        if (value == 0) break;
      }
      return 0;
    }

    int BlockFormatWriter::pack_string(std::fstream& fs, const char *in, size_t in_len) {
      if (pack_varint_i32(fs, in_len)) return -1;
      fs.write(in, in_len);
      if (fs.fail()) {
        return -1;
      }
      return 0;
    }

    int BlockFormatWriter::write_int(std::fstream& fs, int value) {
      uint8_t buff[4];
      buff[0] = value & 0xff;
      buff[1] = (value >> 8) & 0xff;
      buff[2] = (value >> 16) & 0xff;
      buff[3] = (value >> 24) & 0xff;
      fs.write(reinterpret_cast<char*>(buff), sizeof(buff));
      if (fs.fail()) {
        return -1;
      }
      return 0;
    }

    int BlockFormatWriter::write(const char *class_name, const char *record, int record_len) {
      if (record) {
        record_index_++;
        // The buffer holds max records_per_block_ of records (block).
        std::fstream buffer(temp_file_name_, std::ios::app);
        if (!buffer) return -1;
        if (buffer.tellg() == 0) {
          if (pack_tag_and_wiretype(buffer, 1, WIRE_TYPE_VARINT))
            throw std::invalid_argument("Error writting tag and wiretype");
          if (pack_varint_i32(buffer, 1))
            throw std::invalid_argument("Error writting varint_i32");
          if (pack_tag_and_wiretype(buffer, 2, WIRE_TYPE_LENGTH_PREFIXED))
            throw std::invalid_argument("Error writting tag and wiretype");
          if (pack_string(buffer, class_name, strlen(class_name)))
            throw std::invalid_argument("Error writting class name");
        }
        if (pack_tag_and_wiretype(buffer, 3, WIRE_TYPE_LENGTH_PREFIXED))
          throw std::invalid_argument("Error writtig tag and wiretype");
        if (pack_string(buffer, record, record_len))
          throw std::invalid_argument("Error writting record");
      }

      if ((record_index_ % records_per_block_) == 0) {
        flush();
      }
      return 0;
    }

    int BlockFormatWriter::flush() {
      // Flush the records in the buffer to outputfile
      std::fstream buffer(temp_file_name_, std::ios::in);
      if (buffer) {
        buffer.seekg(0, buffer.end);
        int64_t block_size = buffer.tellg();
        buffer.seekg(0, buffer.beg);

        if (outputfile_.write(reinterpret_cast<const char*>(_marker), sizeof(_marker)).fail() != 1) return 1;
        if (write_int(outputfile_, block_size)) return 1;
        uint8_t buff[4096];
        while (1) {
          buffer.read(reinterpret_cast<char*>(buff), sizeof(buff));
          std::streamsize n = buffer.gcount();
          if (n) {
            outputfile_.write(reinterpret_cast<char*>(buff), n);
            if (outputfile_.fail()) return 1;
          }
          if (n != sizeof(buff)) break;
        }
        // Remove the buffer
        if (remove(temp_file_name_)) return 1;
      }
      return 0;
    }

    block_format_writer BlockFormatWriter::getHandle() {
        return reinterpret_cast<block_format_writer>(this);
      }

    BlockFormatWriter *getBlockFormatWriter(block_format_writer w) {
       return reinterpret_cast<BlockFormatWriter *>(w);
    }

}  // namespace twml

twml_err block_format_writer_create(block_format_writer *w, const char *file_name, int records_per_block) {
  HANDLE_EXCEPTIONS(
    twml::BlockFormatWriter *writer =  new twml::BlockFormatWriter(file_name, records_per_block);
    *w = reinterpret_cast<block_format_writer>(writer););
  return TWML_ERR_NONE;
}

twml_err block_format_write(block_format_writer w, const char *class_name, const char *record, int record_len) {
  HANDLE_EXCEPTIONS(
    twml::BlockFormatWriter *writer = twml::getBlockFormatWriter(w);
    writer->write(class_name, record, record_len););
  return TWML_ERR_NONE;
}

twml_err block_format_flush(block_format_writer w) {
  HANDLE_EXCEPTIONS(
    twml::BlockFormatWriter *writer = twml::getBlockFormatWriter(w);
    writer->flush(););
  return TWML_ERR_NONE;
}

twml_err block_format_writer_delete(const block_format_writer w) {
  HANDLE_EXCEPTIONS(
    delete twml::getBlockFormatWriter(w););
  return TWML_ERR_NONE;
}
