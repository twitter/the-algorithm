#pragma once
#ifdef __cplusplus

#include <twml/common.h>
#include <twml/defines.h>
#include <twml/DataRecord.h>
#include <twml/TensorRecordReader.h>

#include <cstdint>

#include <vector>
#include <string>
#include <unordered_map>

namespace twml {

class TWMLAPI DataRecordReader : public TensorRecordReader {

private:
  typedef Map<int64_t, int64_t> KeyMap_t;
  KeyMap_t *m_keep_map;
  KeyMap_t *m_labels_map;
  KeyMap_t *m_weights_map;

public:
  bool keepKey              (const int64_t &key, int64_t &code);
  bool isLabel              (const int64_t &key, int64_t &code);
  bool isWeight             (const int64_t &key, int64_t &code);
  void readBinary           (const int feature_type , DataRecord *record);
  void readContinuous       (const int feature_type , DataRecord *record);
  void readDiscrete         (const int feature_type , DataRecord *record);
  void readString           (const int feature_type , DataRecord *record);
  void readSparseBinary     (const int feature_type , DataRecord *record);
  void readSparseContinuous (const int feature_type , DataRecord *record);
  void readBlob             (const int feature_type , DataRecord *record);

  DataRecordReader() :
      TensorRecordReader(nullptr),
      m_keep_map(nullptr),
      m_labels_map(nullptr),
      m_weights_map(nullptr)
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

  void setDecodeMode(int64_t mode) {}
};

}
#endif
