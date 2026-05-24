#include <twml/io/IOError.h>


namespace twml {
namespace io {

namespace {
  std::string messageFromStatus(IOError::Status status) {
    switch (status) {
      case IOError::OUT_OF_RANGE:
        return "failed to read enough input";
      case IOError::WRONG_MAGIC:
        return "wrong magic in stream";
      case IOError::WRONG_HEADER:
        return "wrong header in stream";
      case IOError::ERROR_HEADER_CHECKSUM:
        return "header checksum doesn't match";
      case IOError::INVALID_METHOD:
        return "using invalid method";
      case IOError::USING_RESERVED:
        return "using reserved flag";
      case IOError::ERROR_HEADER_EXTRA_FIELD_CHECKSUM:
        return "extra header field checksum doesn't match";
      case IOError::CANT_FIT_OUTPUT:
        return "can't fit output in the given space";
      case IOError::SPLIT_FILE:
        return "split files aren't supported";
      case IOError::BLOCK_SIZE_TOO_LARGE:
        return "block size is too large";
      case IOError::SOURCE_LARGER_THAN_DESTINATION:
        return "source is larger than destination";
      case IOError::DESTINATION_LARGER_THAN_CAPACITY:
        return "destination buffer is too small to fit uncompressed result";
      case IOError::HEADER_FLAG_MISMATCH:
        return "failed to match flags for compressed and decompressed data";
      case IOError::NOT_ENOUGH_INPUT:
        return "not enough input to proceed with decompression";
      case IOError::ERROR_SOURCE_BLOCK_CHECKSUM:
        return "source block checksum doesn't match";
      case IOError::COMPRESSED_DATA_VIOLATION:
        return "error occurred while decompressing the data";
      case IOError::ERROR_DESTINATION_BLOCK_CHECKSUM:
        return "destination block checksum doesn't match";
      case IOError::EMPTY_RECORD:
        return "can't write an empty record";
      case IOError::MALFORMED_MEMORY_RECORD:
        return "can't write malformed record";
      case IOError::UNSUPPORTED_OUTPUT_TYPE:
        return "output data type is not supported";
      case IOError::OTHER_ERROR:
      default:
        return "unknown error occurred";
    }
  }
}  // namespace

IOError::IOError(Status status): twml::Error(TWML_ERR_IO, "Found error while processing stream: " +
    messageFromStatus(status)), m_status(status) {}

}  // namespace io
}  // namespace twml
