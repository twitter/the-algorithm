#pragma once
#ifdef __cplusplus

#include <twml/defines.h>
#include <twml/DataRecord.h>
#include <twml/TensorRecordWriter.h>

namespace twml {

// Encodes DataRecords as binary Thrift. BatchPredictionResponse
// uses this class to encode prediction responses through our
// TensorFlow response writer operator.
class TWMLAPI DataRecordWriter {
  private:
    uint32_t m_records_written;
    twml::ThriftWriter &m_thrift_writer;
    twml::TensorRecordWriter m_tensor_writer;

    void writeBinary(twml::DataRecord &record);
    void writeContinuous(twml::DataRecord &record);
    void writeDiscrete(twml::DataRecord &record);
    void writeString(twml::DataRecord &record);
    void writeSparseBinaryFeatures(twml::DataRecord &record);
    void writeSparseContinuousFeatures(twml::DataRecord &record);
    void writeBlobFeatures(twml::DataRecord &record);
    void writeDenseTensors(twml::DataRecord &record);

  public:
    DataRecordWriter(twml::ThriftWriter &thrift_writer):
      m_records_written(0),
      m_thrift_writer(thrift_writer),
      m_tensor_writer(twml::TensorRecordWriter(thrift_writer)) { }

    uint32_t getRecordsWritten();
    uint64_t write(twml::DataRecord &record);
};

}
#endif
