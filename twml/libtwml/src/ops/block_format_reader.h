#pragma once

#include "tensorflow/core/framework/common_shape_fns.h"
#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"
#include "tensorflow/core/platform/env.h"
#include "tensorflow/core/lib/io/random_inputstream.h"

#include <twml.h>

#include <string>

using tensorflow::int64;
using tensorflow::Status;
using std::string;

class BlockFormatReader : twml::BlockFormatReader {
 public:
  explicit BlockFormatReader(tensorflow::io::InputStreamInterface *stream)
      : twml::BlockFormatReader() , stream_(stream) {
  }

  // Read the next record.
  // Returns OK on success,
  // Returns OUT_OF_RANGE for end of file, or something else for an error.
  Status ReadNext(string* record) {
    if (this->next()) {
      return stream_->ReadNBytes(this->current_size(), record);
    }
    return tensorflow::errors::OutOfRange("eof");
  }

  uint64_t read_bytes(void *dest, int size, int count) {
    uint64_t bytesToRead = size * count;
    std::string current;
    // TODO: Try to merge ReadNBytes and the memcpy below
    // ReadNBytes performs a memory copy already.
    Status status = stream_->ReadNBytes(bytesToRead, &current);
    if (!status.ok()) {
      return 0;
    }
    memcpy(dest, current.c_str(), bytesToRead);
    return count;
  }

 private:
  tensorflow::io::InputStreamInterface *stream_;
  TF_DISALLOW_COPY_AND_ASSIGN(BlockFormatReader);
};
