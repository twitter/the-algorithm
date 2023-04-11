#include "internal/thrift.h"
#include "internal/error.h"

#include <twml/utilities.h>
#include <twml/DataRecord.h>
#include <twml/DataRecordReader.h>
#include <twml/Error.h>

#include <cstring>
#include <cstdint>

namespace twml {

void DataRecord::decode(DataRecordReader &reader) {
  uint8_t feature_type = reader.readByte();
  while (feature_type != TTYPE_STOP) {
    int16_t field_id = reader.readInt16();
    switch (field_id) {
      case DR_BINARY:
        reader.readBinary(feature_type, this);
        break;
      case DR_CONTINUOUS:
        reader.readContinuous(feature_type, this);
        break;
      case DR_DISCRETE:
        reader.readDiscrete(feature_type, this);
        break;
      case DR_STRING:
        reader.readString(feature_type, this);
        break;
      case DR_SPARSE_BINARY:
        reader.readSparseBinary(feature_type, this);
        break;
      case DR_SPARSE_CONTINUOUS:
        reader.readSparseContinuous(feature_type, this);
        break;
      case DR_BLOB:
        reader.readBlob(feature_type, this);
        break;
      case DR_GENERAL_TENSOR:
        reader.readTensor(feature_type, dynamic_cast<TensorRecord *>(this));
        break;
      case DR_SPARSE_TENSOR:
        reader.readSparseTensor(feature_type, dynamic_cast<TensorRecord *>(this));
        break;
      default:
        throw ThriftInvalidField(field_id, "DataRecord::decode");
    }
    feature_type = reader.readByte();
  }
}

void DataRecord::addLabel(int64_t id, double label) {
  m_labels[id] = label;
}

void DataRecord::addWeight(int64_t id, double val) {
  m_weights[id] = val;
}

void DataRecord::clear() {
  std::fill(m_labels.begin(), m_labels.end(), std::nanf(""));
  std::fill(m_weights.begin(), m_weights.end(), 0.0);
  m_binary.clear();
  m_continuous.clear();
  m_discrete.clear();
  m_string.clear();
  m_sparsebinary.clear();
  m_sparsecontinuous.clear();
}

}  // namespace twml
