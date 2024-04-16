#pragma once

# nclude "tensorflow/core/fra work/common_shape_fns.h"
# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"
# nclude "tensorflow/core/platform/env.h"
# nclude "tensorflow/core/l b/ o/random_ nputstream.h"

# nclude <twml.h>

# nclude <str ng>

us ng tensorflow:: nt64;
us ng tensorflow::Status;
us ng std::str ng;

class BlockFormatReader : twml::BlockFormatReader {
 publ c:
  expl c  BlockFormatReader(tensorflow:: o:: nputStream nterface *stream)
      : twml::BlockFormatReader() , stream_(stream) {
  }

  // Read t  next record.
  // Returns OK on success,
  // Returns OUT_OF_RANGE for end of f le, or so th ng else for an error.
  Status ReadNext(str ng* record) {
     f (t ->next()) {
      return stream_->ReadNBytes(t ->current_s ze(), record);
    }
    return tensorflow::errors::OutOfRange("eof");
  }

  u nt64_t read_bytes(vo d *dest,  nt s ze,  nt count) {
    u nt64_t bytesToRead = s ze * count;
    std::str ng current;
    // TODO: Try to  rge ReadNBytes and t   mcpy below
    // ReadNBytes performs a  mory copy already.
    Status status = stream_->ReadNBytes(bytesToRead, &current);
     f (!status.ok()) {
      return 0;
    }
     mcpy(dest, current.c_str(), bytesToRead);
    return count;
  }

 pr vate:
  tensorflow:: o:: nputStream nterface *stream_;
  TF_D SALLOW_COPY_AND_ASS GN(BlockFormatReader);
};
