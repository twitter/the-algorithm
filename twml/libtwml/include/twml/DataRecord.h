#pragma once
#ifdef __cplusplus

#include <twml/common.h>
#include <twml/defines.h>
#include <twml/TensorRecord.h>

#include <cstdint>
#include <cmath>
#include <string>
#include <unordered_map>
#include <unordered_set>
#include <vector>

namespace twml {

class DataRecordReader;

class TWMLAPI DataRecord : public TensorRecord {
public:
  typedef std::vector<std::pair<std::string, double>> SparseContinuousValueType;
  typedef std::vector<std::string> SparseBinaryValueType;
  typedef Set<int64_t> BinaryFeatures;
  typedef Map<int64_t, double> ContinuousFeatures;
  typedef Map<int64_t, int64_t> DiscreteFeatures;
  typedef Map<int64_t, std::string> StringFeatures;
  typedef Map<int64_t, SparseBinaryValueType> SparseBinaryFeatures;
  typedef Map<int64_t, SparseContinuousValueType> SparseContinuousFeatures;
  typedef Map<int64_t, std::vector<uint8_t>> BlobFeatures;

private:
  BinaryFeatures m_binary;
  ContinuousFeatures m_continuous;
  DiscreteFeatures m_discrete;
  StringFeatures m_string;
  SparseBinaryFeatures m_sparsebinary;
  SparseContinuousFeatures m_sparsecontinuous;
  BlobFeatures m_blob;


  std::vector<float> m_labels;
  std::vector<float> m_weights;

  void addLabel(int64_t id, double label = 1);
  void addWeight(int64_t id, double value);

public:
  typedef DataRecordReader Reader;

  DataRecord(int num_labels=0, int num_weights=0):
      m_binary(),
      m_continuous(),
      m_discrete(),
      m_string(),
      m_sparsebinary(),
      m_sparsecontinuous(),
      m_blob(),
      m_labels(num_labels, std::nanf("")),
      m_weights(num_weights) {
#ifdef USE_DENSE_HASH
        m_binary.set_empty_key(0);
        m_continuous.set_empty_key(0);
        m_discrete.set_empty_key(0);
        m_string.set_empty_key(0);
        m_sparsebinary.set_empty_key(0);
        m_sparsecontinuous.set_empty_key(0);
#endif
        m_binary.max_load_factor(0.5);
        m_continuous.max_load_factor(0.5);
        m_discrete.max_load_factor(0.5);
        m_string.max_load_factor(0.5);
        m_sparsebinary.max_load_factor(0.5);
        m_sparsecontinuous.max_load_factor(0.5);
      }

  const BinaryFeatures &getBinary() const { return m_binary; }
  const ContinuousFeatures &getContinuous() const { return m_continuous; }
  const DiscreteFeatures &getDiscrete() const { return m_discrete; }
  const StringFeatures &getString() const { return m_string; }
  const SparseBinaryFeatures &getSparseBinary() const { return m_sparsebinary; }
  const SparseContinuousFeatures &getSparseContinuous() const { return m_sparsecontinuous; }
  const BlobFeatures &getBlob() const { return m_blob; }

  const std::vector<float> &labels() const { return m_labels; }
  const std::vector<float> &weights() const { return m_weights; }

  // used by DataRecordWriter
  template <typename T>
  void addContinuous(std::vector<int64_t> feature_ids, std::vector<T> values) {
    for (size_t i = 0; i < feature_ids.size(); ++i){
      m_continuous[feature_ids[i]] = values[i];
    }
  }

  template <typename T>
  void addContinuous(const int64_t *keys, uint64_t num_keys, T *values) {
    for (size_t i = 0; i < num_keys; ++i){
       m_continuous[keys[i]] = values[i];
     }
  }

  void decode(DataRecordReader &reader);
  void clear();
  friend class DataRecordReader;
};

}
#endif
