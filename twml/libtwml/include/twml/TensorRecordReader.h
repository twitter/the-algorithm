#pragma once
#ifdef __cplusplus

#include <twml/defines.h>
#include <twml/TensorRecord.h>
#include <twml/ThriftReader.h>

#include <cstdint>

#include <vector>
#include <string>
#include <unordered_map>

namespace twml {

// Class that parses the thrift objects as defined in tensor.thrift
class TWMLAPI TensorRecordReader : public ThriftReader {

  std::vector<uint64_t> readShape();
  template<typename T> RawTensor readTypedTensor();
  RawTensor readRawTypedTensor();
  RawTensor readStringTensor();
  RawTensor readGeneralTensor();
  RawSparseTensor readCOOSparseTensor();

public:
  void readTensor(const int feature_type, TensorRecord *record);
  void readSparseTensor(const int feature_type, TensorRecord *record);

  TensorRecordReader(const uint8_t *buffer) : ThriftReader(buffer) {}
};

}
#endif
