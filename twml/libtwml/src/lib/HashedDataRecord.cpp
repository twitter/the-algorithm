#include "internal/thrift.h"
#include "internal/error.h"

#include <twml/HashedDataRecord.h>
#include <twml/HashedDataRecordReader.h>
#include <twml/Error.h>

#include <algorithm>
#include <cstring>
#include <cstdint>

namespace twml {

void HashedDataRecord::decode(HashedDataRecordReader &reader) {
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
        throw ThriftInvalidField(field_id, "HashedDataRecord::readThrift");
    }
    feature_type = reader.readByte();
  }
}

void HashedDataRecord::addKey(int64_t key, int64_t transformed_key,
                              int64_t code, uint8_t type, double value) {
  m_keys.push_back(key);
  m_transformed_keys.push_back(transformed_key);
  m_values.push_back(value);
  m_codes.push_back(code);
  m_types.push_back(type);
}

void HashedDataRecord::addLabel(int64_t id, double label) {
  m_labels[id] = label;
}

void HashedDataRecord::addWeight(int64_t id, double val) {
  m_weights[id] = val;
}

void HashedDataRecord::clear() {
  std::fill(m_labels.begin(), m_labels.end(), std::nanf(""));
  std::fill(m_weights.begin(), m_weights.end(), 0.0);
  m_keys.clear();
  m_transformed_keys.clear();
  m_values.clear();
  m_codes.clear();
  m_types.clear();
}

}  // namespace twml