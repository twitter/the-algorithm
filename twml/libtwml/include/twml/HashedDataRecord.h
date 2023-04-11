#pragma once
#ifdef __cplusplus

#include <twml/defines.h>
#include <twml/TensorRecord.h>

#include <cstdint>
#include <cmath>
#include <vector>

namespace twml {

class HashedDataRecordReader;

class TWMLAPI HashedDataRecord : public TensorRecord {
 public:
  typedef HashedDataRecordReader Reader;

  HashedDataRecord(int num_labels=0, int num_weights=0):
      m_keys(),
      m_transformed_keys(),
      m_values(),
      m_codes(),
      m_types(),
      m_labels(num_labels, std::nanf("")),
      m_weights(num_weights) {}

  void decode(HashedDataRecordReader &reader);

  const std::vector<int64_t> &keys() const { return m_keys; }
  const std::vector<int64_t> &transformed_keys() const { return m_transformed_keys; }
  const std::vector<double> &values() const { return m_values; }
  const std::vector<int64_t> &codes() const { return m_codes; }
  const std::vector<uint8_t> &types() const { return m_types; }

  const std::vector<float> &labels() const { return m_labels; }
  const std::vector<float> &weights() const { return m_weights; }

  void clear();

  uint64_t totalSize() const { return m_keys.size(); }

  void extendSize(int delta_size) {
    int count = m_keys.size() + delta_size;
    m_keys.reserve(count);
    m_transformed_keys.reserve(count);
    m_values.reserve(count);
    m_codes.reserve(count);
    m_types.reserve(count);
  }

 private:
  std::vector<int64_t> m_keys;
  std::vector<int64_t> m_transformed_keys;
  std::vector<double> m_values;
  std::vector<int64_t> m_codes;
  std::vector<uint8_t> m_types;

  std::vector<float> m_labels;
  std::vector<float> m_weights;

  void addKey(int64_t key, int64_t transformed_key, int64_t code, uint8_t type, double value=1);
  void addLabel(int64_t id, double value = 1);
  void addWeight(int64_t id, double value);

  friend class HashedDataRecordReader;
};

}
#endif