#include "internal/thrift.h"
#include "internal/error.h"

#include <twml/HashedDataRecordReader.h>
#include <twml/utilities.h>
#include <twml/functions.h>
#include <cmath>

namespace twml {

bool HashedDataRecordReader::keepId(const int64_t &key, int64_t &code) {
  auto it = m_keep_map->find(key);
  if (it == m_keep_map->end()) return false;
  code = it->second;
  return true;
}

bool HashedDataRecordReader::isLabel(const int64_t &key, int64_t &code) {
  if (m_labels_map == nullptr) return false;
  auto it = m_labels_map->find(key);
  if (it == m_labels_map->end()) return false;
  code = it->second;
  return true;
}

bool HashedDataRecordReader::isWeight(const int64_t &key, int64_t &code) {
  if (m_weights_map == nullptr) return false;
  auto it = m_weights_map->find(key);
  if (it == m_weights_map->end()) return false;
  code = it->second;
  return true;
}

void HashedDataRecordReader::readBinary(
  const int feature_type,
  HashedDataRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_SET, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");

  int32_t length = readInt32();
  record->extendSize(length);
  int64_t id, code;
  for (int32_t i = 0; i < length; i++) {
    id = readInt64();
    if (keepId(id, code)) {
      record->addKey(id, id, code, DR_BINARY);
    } else if (isLabel(id, code)) {
      record->addLabel(code);
    }
  }
}

void HashedDataRecordReader::readContinuous(
  const int feature_type,
  HashedDataRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_DOUBLE, "value_type");

  int32_t length = readInt32();
  record->extendSize(length);
  int64_t id, code;
  for (int32_t i = 0; i < length; i++) {
    id = readInt64();
    if (keepId(id, code)) {
      double value = readDouble();
      if (!std::isnan(value)) {
        record->addKey(id, id, code, DR_CONTINUOUS, value);
      }
    } else if (isLabel(id, code)) {
      record->addLabel(code, readDouble());
    }  else if (isWeight(id, code)) {
      record->addWeight(code, readDouble());
    } else {
      skip<double>();
    }
  }
}

void HashedDataRecordReader::readDiscrete(
  const int feature_type,
  HashedDataRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "value_type");

  int32_t length = readInt32();
  record->extendSize(length);
  int64_t id, code;
  for (int32_t i = 0; i < length; i++) {
    id = readInt64();
    if (keepId(id, code)) {
      int64_t transformed_key = mixDiscreteIdAndValue(id, readInt64());
      record->addKey(id, transformed_key, code, DR_DISCRETE);
    } else {
      skip<int64_t>();
    }
  }
}

void HashedDataRecordReader::readString(
  const int feature_type,
  HashedDataRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_STRING, "value_type");

  int32_t length = readInt32();
  record->extendSize(length);
  int64_t id, code;
  for (int32_t i = 0; i < length; i++) {
    id = readInt64();
    if (keepId(id, code)) {
      const uint8_t *begin = nullptr;
      int32_t str_len = getRawBuffer<uint8_t>(&begin);
      int64_t transformed_key = mixStringIdAndValue(id, str_len, begin);
      record->addKey(id, transformed_key, code, DR_STRING);
    } else {
      int32_t str_len = readInt32();
      skipLength(str_len);
    }
  }
}

void HashedDataRecordReader::readSparseBinary(
  const int feature_type,
  HashedDataRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_SET, "value_type");

  int32_t length = readInt32();
  record->extendSize(length);
  int64_t id, code;
  for (int32_t i = 0; i < length; i++) {
    id = readInt64();
    if (keepId(id, code)) {
      CHECK_THRIFT_TYPE(readByte(), TTYPE_STRING, "set:key_type");
      int32_t set_length = readInt32();
      for (int32_t j = 0; j < set_length; j++) {
        const uint8_t *begin = nullptr;
        int32_t str_len = getRawBuffer<uint8_t>(&begin);
        int64_t transformed_key = mixStringIdAndValue(id, str_len, begin);
        record->addKey(id, transformed_key, code, DR_SPARSE_BINARY);
      }
    } else {
      CHECK_THRIFT_TYPE(readByte(), TTYPE_STRING, "set:key_type");
      int32_t set_length = readInt32();
      for (int32_t j = 0; j < set_length; j++) {
        int32_t str_len = readInt32();
        skipLength(str_len);
      }
    }
  }
}

void HashedDataRecordReader::readSparseContinuous(
  const int feature_type,
  HashedDataRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_MAP, "value_type");

  int32_t length = readInt32();
  record->extendSize(length);
  int64_t id, code;
  for (int32_t i = 0; i < length; i++) {
    id = readInt64();
    if (keepId(id, code)) {
      CHECK_THRIFT_TYPE(readByte(), TTYPE_STRING, "map::key_type");
      CHECK_THRIFT_TYPE(readByte(), TTYPE_DOUBLE, "map::value_type");
      int32_t map_length = readInt32();
      for (int32_t j = 0; j < map_length; j++) {
        const uint8_t *begin = nullptr;
        int32_t str_len = getRawBuffer<uint8_t>(&begin);
        int64_t transformed_key = 0;
        switch(m_decode_mode) {
          case DecodeMode::hash_fname_and_valname:
            transformed_key = mixStringIdAndValue(id, str_len, begin);
            break;
          default:  // m_decode_mode == DecodeMode::hash_valname == 0 is default
            twml_get_feature_id(&transformed_key, str_len, reinterpret_cast<const char *>(begin));
        }
        double value = readDouble();
        if (!std::isnan(value)) {
          record->addKey(id, transformed_key, code, DR_SPARSE_CONTINUOUS, value);
        }
      }
    } else {
      CHECK_THRIFT_TYPE(readByte(), TTYPE_STRING, "map::key_type");
      CHECK_THRIFT_TYPE(readByte(), TTYPE_DOUBLE, "map::value_type");
      int32_t map_length = readInt32();
      for (int32_t j = 0; j < map_length; j++) {
        int32_t str_len = readInt32();
        skipLength(str_len);
        skip<double>();
      }
    }
  }
}

void HashedDataRecordReader::readBlob(
  const int feature_type,
  HashedDataRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_STRING, "value_type");

  int32_t length = readInt32();
  int64_t id;
  for (int32_t i = 0; i < length; i++) {
    // Skips the BlobFeatures if they are defined or not in the FeatureConfig
    id = readInt64();
    int32_t str_len = readInt32();
    skipLength(str_len);
  }
}
}  // namespace twml