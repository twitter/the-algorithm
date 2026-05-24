#include "internal/error.h"
#include "internal/thrift.h"

#include <map>
#include <twml/ThriftWriter.h>
#include <twml/DataRecordWriter.h>
#include <twml/io/IOError.h>
#include <unordered_set>

using namespace twml::io;

namespace twml {

void DataRecordWriter::writeBinary(twml::DataRecord &record) {
  const DataRecord::BinaryFeatures bin_features = record.getBinary();

  if (bin_features.size() > 0) {
    m_thrift_writer.writeStructFieldHeader(TTYPE_SET, DR_BINARY);
    m_thrift_writer.writeListHeader(TTYPE_I64, bin_features.size());

    for (const auto &it : bin_features) {
      m_thrift_writer.writeInt64(it);
    }
  }
}

void DataRecordWriter::writeContinuous(twml::DataRecord &record) {
  const DataRecord::ContinuousFeatures cont_features = record.getContinuous();

  if (cont_features.size() > 0) {
    m_thrift_writer.writeStructFieldHeader(TTYPE_MAP, DR_CONTINUOUS);
    m_thrift_writer.writeMapHeader(TTYPE_I64, TTYPE_DOUBLE, cont_features.size());

    for (const auto &it : cont_features) {
      m_thrift_writer.writeInt64(it.first);
      m_thrift_writer.writeDouble(it.second);
    }
  }
}

void DataRecordWriter::writeDiscrete(twml::DataRecord &record) {
  const DataRecord::DiscreteFeatures disc_features = record.getDiscrete();

  if (disc_features.size() > 0) {
    m_thrift_writer.writeStructFieldHeader(TTYPE_MAP, DR_DISCRETE);
    m_thrift_writer.writeMapHeader(TTYPE_I64, TTYPE_I64, disc_features.size());

     for (const auto &it : disc_features) {
      m_thrift_writer.writeInt64(it.first);
      m_thrift_writer.writeInt64(it.second);
    }
  }
}

void DataRecordWriter::writeString(twml::DataRecord &record) {
  const DataRecord::StringFeatures str_features = record.getString();

  if (str_features.size() > 0) {
    m_thrift_writer.writeStructFieldHeader(TTYPE_MAP, DR_STRING);
    m_thrift_writer.writeMapHeader(TTYPE_I64, TTYPE_STRING, str_features.size());


    for (const auto &it : str_features) {
      m_thrift_writer.writeInt64(it.first);
      m_thrift_writer.writeString(it.second);
    }
  }
}

// convert from internal representation list<(i64, string)>
// to Thrift representation map<i64, set<string>>
void DataRecordWriter::writeSparseBinaryFeatures(twml::DataRecord &record) {
  const DataRecord::SparseBinaryFeatures sp_bin_features = record.getSparseBinary();

  // write map<i64, set<string>> as Thrift
  if (sp_bin_features.size() > 0) {
    m_thrift_writer.writeStructFieldHeader(TTYPE_MAP, DR_SPARSE_BINARY);
    m_thrift_writer.writeMapHeader(TTYPE_I64, TTYPE_SET, sp_bin_features.size());

    for (auto key_vals : sp_bin_features) {
      m_thrift_writer.writeInt64(key_vals.first);
      m_thrift_writer.writeListHeader(TTYPE_STRING, key_vals.second.size());

      for (auto name : key_vals.second)
        m_thrift_writer.writeString(name);
    }
  }
}

// convert from internal representation list<(i64, string, double)>
// to Thrift representation map<i64, map<string, double>>
void DataRecordWriter::writeSparseContinuousFeatures(twml::DataRecord &record) {
  const DataRecord::SparseContinuousFeatures sp_cont_features = record.getSparseContinuous();

  // write map<i64, map<string, double>> as Thrift
  if (sp_cont_features.size() > 0) {
    m_thrift_writer.writeStructFieldHeader(TTYPE_MAP, DR_SPARSE_CONTINUOUS);
    m_thrift_writer.writeMapHeader(TTYPE_I64, TTYPE_MAP, sp_cont_features.size());

    for (auto key_vals : sp_cont_features) {
      m_thrift_writer.writeInt64(key_vals.first);

      if (key_vals.second.size() == 0)
        throw IOError(IOError::MALFORMED_MEMORY_RECORD);

      m_thrift_writer.writeMapHeader(TTYPE_STRING, TTYPE_DOUBLE, key_vals.second.size());

      for (auto map_str_double : key_vals.second) {
        m_thrift_writer.writeString(map_str_double.first);
        m_thrift_writer.writeDouble(map_str_double.second);
      }
    }
  }
}

void DataRecordWriter::writeBlobFeatures(twml::DataRecord &record) {
  const DataRecord::BlobFeatures blob_features = record.getBlob();

  if (blob_features.size() > 0) {
    m_thrift_writer.writeStructFieldHeader(TTYPE_MAP, DR_BLOB);
    m_thrift_writer.writeMapHeader(TTYPE_I64, TTYPE_STRING, blob_features.size());

    for (const auto &it : blob_features) {
      m_thrift_writer.writeInt64(it.first);
      std::vector<uint8_t> value = it.second;
      m_thrift_writer.writeBinary(value.data(), value.size());
    }
  }
}

void DataRecordWriter::writeDenseTensors(twml::DataRecord &record) {
  TensorRecord::RawTensors raw_tensors = record.getRawTensors();
  if (raw_tensors.size() > 0) {
    m_thrift_writer.writeStructFieldHeader(TTYPE_MAP, DR_GENERAL_TENSOR);
    m_tensor_writer.write(record);
  }
}

TWMLAPI uint32_t DataRecordWriter::getRecordsWritten() {
  return m_records_written;
}

TWMLAPI uint64_t DataRecordWriter::write(twml::DataRecord &record) {
  uint64_t bytes_written_before = m_thrift_writer.getBytesWritten();

  writeBinary(record);
  writeContinuous(record);
  writeDiscrete(record);
  writeString(record);
  writeSparseBinaryFeatures(record);
  writeSparseContinuousFeatures(record);
  writeBlobFeatures(record);
  writeDenseTensors(record);
  // TODO add sparse tensor field

  m_thrift_writer.writeStructStop();
  m_records_written++;

  return m_thrift_writer.getBytesWritten() - bytes_written_before;
}

}  // namespace twml
