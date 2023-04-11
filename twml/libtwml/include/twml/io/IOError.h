#pragma once

#include <twml/Error.h>

namespace twml {
namespace io {

class IOError : public twml::Error {
  public:
    enum Status {
      OUT_OF_RANGE = 1,
      WRONG_MAGIC = 2,
      WRONG_HEADER = 3,
      ERROR_HEADER_CHECKSUM = 4,
      INVALID_METHOD = 5,
      USING_RESERVED = 6,
      ERROR_HEADER_EXTRA_FIELD_CHECKSUM = 7,
      CANT_FIT_OUTPUT = 8,
      SPLIT_FILE = 9,
      BLOCK_SIZE_TOO_LARGE = 10,
      SOURCE_LARGER_THAN_DESTINATION = 11,
      DESTINATION_LARGER_THAN_CAPACITY = 12,
      HEADER_FLAG_MISMATCH = 13,
      NOT_ENOUGH_INPUT = 14,
      ERROR_SOURCE_BLOCK_CHECKSUM = 15,
      COMPRESSED_DATA_VIOLATION = 16,
      ERROR_DESTINATION_BLOCK_CHECKSUM = 17,
      EMPTY_RECORD = 18,
      MALFORMED_MEMORY_RECORD = 19,
      UNSUPPORTED_OUTPUT_TYPE = 20,
      OTHER_ERROR
    };

    IOError(Status status);

    Status status() const {
      return m_status;
    }

  private:
    Status m_status;
};

}
}
