#pragma once
#ifdef __cplusplus

#include <twml/defines.h>
#include <twml/TensorRecord.h>

namespace twml {

// Encodes tensors as DataRecord/TensorRecord-compatible Thrift.
// DataRecordWriter relies on this class to encode the tensor fields.
class TWMLAPI TensorRecordWriter {

private:
  uint32_t m_records_written;
  twml::ThriftWriter &m_thrift_writer;

  void writeTensor(const RawTensor &tensor);
  void writeRawTensor(const RawTensor &tensor);

public:
  TensorRecordWriter(twml::ThriftWriter &thrift_writer):
      m_records_written(0),
      m_thrift_writer(thrift_writer) { }

  uint32_t getRecordsWritten();

  // Caller (usually DataRecordWriter) must precede with struct header field
  // like thrift_writer.writeStructFieldHeader(TTYPE_MAP, DR_GENERAL_TENSOR)
  //
  // All tensors written as RawTensors except for StringTensors
  uint64_t write(twml::TensorRecord &record);
};

}
#endif
