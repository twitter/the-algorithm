#include "internal/thrift.h"
#include "internal/error.h"
#include <string>
#include <cmath>

#include <twml/DataRecordReader.h>

namespace twml {

inline std::string bufferToString(int32_t str_len, const uint8_t *str) {
  return std::string(str, str + str_len);
}


bool DataRecordReader::keepKey(const int64_t &key, int64_t &code) {
  auto it = m_keep_map->find(key);
  if (it == m_keep_map->end()) return false;
  code = it->second;
  return true;
}

bool DataRecordReader::isLabel(const int64_t &key, int64_t &code) {
  if (m_labels_map == nullptr) return false;
  auto it = m_labels_map->find(key);
  if (it == m_labels_map->end()) return false;
  code = it->second;
  return true;
}

bool DataRecordReader::isWeight(const int64_t &key, int64_t &code) {
  if (m_weights_map == nullptr) return false;
  auto it = m_weights_map->find(key);
  if (it == m_weights_map->end()) return false;
  code = it->second;
  return true;
}


void DataRecordReader::readBinary(
  const int feature_type,
  DataRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_SET, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  int32_t length = readInt32();
  int64_t id, code;
#ifdef USE_DENSE_HASH
  record->m_binary.resize(2 * length);
#else
  record->m_binary.reserve(2 * length);
#endif
  for (int32_t i = 0; i < length; i++) {
    id = readInt64();
    record->m_binary.insert(id);
    if (isLabel(id, code)) {
      record->addLabel(code);
    }
  }
}

void DataRecordReader::readContinuous(
  const int feature_type,
  DataRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_DOUBLE, "value_type");

  int32_t length = readInt32();
  int64_t id, code;
#ifdef USE_DENSE_HASH
  record->m_continuous.resize(2 * length);
#else
  record->m_continuous.reserve(2 * length);
#endif
  for (int32_t i = 0; i < length; i++) {
    id = readInt64();
    double val = readDouble();
    if (!std::isnan(val)) {
      record->m_continuous[id] = val;
    }
    if (isLabel(id, code)) {
      record->addLabel(code, val);
    } else if (isWeight(id, code)) {
      record->addWeight(code, val);
    }
  }
}

void DataRecordReader::readDiscrete(
  const int feature_type,
  DataRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "value_type");

  int32_t length = readInt32();
  int64_t id;
#ifdef USE_DENSE_HASH
  record->m_discrete.resize(2 * length);
#else
  record->m_discrete.reserve(2 * length);
#endif
  for (int32_t i = 0; i < length; i++) {
    id = readInt64();
    record->m_discrete[id] = readInt64();
  }
}

void DataRecordReader::readString(
  const int feature_type,
  DataRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_STRING, "value_type");
  int32_t length = readInt32();
  int64_t id;

#ifdef USE_DENSE_HASH
  record->m_string.resize(2 * length);
#else
  record->m_string.reserve(2 * length);
#endif

  for (int32_t i = 0; i < length; i++) {
    id = readInt64();
    const uint8_t *begin = nullptr;
    int32_t str_len = getRawBuffer<uint8_t>(&begin);
    record->m_string[id] = bufferToString(str_len, begin);
  }
}

void DataRecordReader::readSparseBinary(
  const int feature_type,
  DataRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_SET, "value_type");

  int32_t length = readInt32();
  int64_t id, code;

#ifdef USE_DENSE_HASH
  record->m_sparsebinary.resize(2 * length);
#else
  record->m_sparsebinary.reserve(2 * length);
#endif

  for (int32_t i = 0; i < length; i++) {
    id = readInt64();
    CHECK_THRIFT_TYPE(readByte(), TTYPE_STRING, "set:key_type");
    int32_t set_length = readInt32();
    if (keepKey(id, code)) {
      record->m_sparsebinary[id].reserve(set_length);
      for (int32_t j = 0; j < set_length; j++) {
        const uint8_t *begin = nullptr;
        int32_t str_len = getRawBuffer<uint8_t>(&begin);
        record->m_sparsebinary[id].push_back(bufferToString(str_len, begin));
      }
    } else {
      for (int32_t j = 0; j < set_length; j++) {
        int32_t str_len = readInt32();
        skipLength(str_len);
      }
    }
  }
}

void DataRecordReader::readSparseContinuous(
  const int feature_type,
  DataRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_MAP, "value_type");

  int32_t length = readInt32();
  int64_t id, code;

#ifdef USE_DENSE_HASH
  record->m_sparsecontinuous.resize(2 * length);
#else
  record->m_sparsecontinuous.reserve(2 * length);
#endif

  for (int32_t i = 0; i < length; i++) {
    id = readInt64();
    CHECK_THRIFT_TYPE(readByte(), TTYPE_STRING, "map::key_type");
    CHECK_THRIFT_TYPE(readByte(), TTYPE_DOUBLE, "map::value_type");
    int32_t map_length = readInt32();
    if (keepKey(id, code)) {
      record->m_sparsecontinuous[id].reserve(map_length);
      for (int32_t j = 0; j < map_length; j++) {
        const uint8_t *begin = nullptr;
        int32_t str_len = getRawBuffer<uint8_t>(&begin);
        double val = readDouble();
        if (!std::isnan(val)) {
          record->m_sparsecontinuous[id].push_back({bufferToString(str_len, begin), val});
        }
      }
    } else {
      for (int32_t j = 0; j < map_length; j++) {
        int32_t str_len = readInt32();
        skipLength(str_len);
        skip<double>();
      }
    }
  }
}

void DataRecordReader::readBlob(
  const int feature_type,
  DataRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_STRING, "value_type");

  int32_t length = readInt32();
  int64_t id, code;
  for (int32_t i = 0; i < length; i++) {
    id = readInt64();
    if (keepKey(id, code)) {
      const uint8_t *begin = nullptr;
      int32_t blob_len = getRawBuffer<uint8_t>(&begin);
      record->m_blob[id] = std::vector<uint8_t>(begin, begin + blob_len);
    } else {
      int32_t str_len = readInt32();
      skipLength(str_len);
    }
  }
}

}  // namespace twml
