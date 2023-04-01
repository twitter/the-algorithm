#pragma once
#ifdef __cplusplus

#include <twml/common.h>
#include <twml/defines.h>
#include <twml/HashedDataRecord.h>
#include <twml/TensorRecordReader.h>

#include <cstdint>

#include <vector>
#include <string>
#include <unordered_map>

namespace twml {

enum class DecodeMode: int64_t
{
  hash_valname = 0,
  hash_fname_and_valname = 1,
};

class TWMLAPI HashedDataRecordReader : public TensorRecordReader {
private:
  typedef Map<int64_t, int64_t> KeyMap_t;
  KeyMap_t *m_keep_map;
  KeyMap_t *m_labels_map;
  KeyMap_t *m_weights_map;
  DecodeMode m_decode_mode;

public:
  bool keepId               (const int64_t &key, int64_t &code);
  bool isLabel              (const int64_t &key, int64_t &code);
  bool isWeight             (const int64_t &key, int64_t &code);
  void readBinary           (const int feature_type , HashedDataRecord *record);
  void readContinuous       (const int feature_type , HashedDataRecord *record);
  void readDiscrete         (const int feature_type , HashedDataRecord *record);
  void readString           (const int feature_type , HashedDataRecord *record);
  void readSparseBinary     (const int feature_type , HashedDataRecord *record);
  void readSparseContinuous (const int feature_type , HashedDataRecord *record);
  void readBlob             (const int feature_type , HashedDataRecord *record);

  HashedDataRecordReader() :
      TensorRecordReader(nullptr),
      m_keep_map(nullptr),
      m_labels_map(nullptr),
      m_weights_map(nullptr),
      m_decode_mode(DecodeMode::hash_valname)
      {}

  // Using a template instead of int64_t because tensorflow implements int64 based on compiler.
  void setKeepMap(KeyMap_t *keep_map) {
    m_keep_map = keep_map;
  }

  void setLabelsMap(KeyMap_t *labels_map) {
    m_labels_map = labels_map;
  }

  void setWeightsMap(KeyMap_t *weights_map) {
    m_weights_map = weights_map;
  }

  void setDecodeMode(int64_t mode) {
    m_decode_mode = static_cast<DecodeMode>(mode);
  }
};

}
#endif
