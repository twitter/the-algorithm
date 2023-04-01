#pragma once
#ifdef __cplusplus

#include <twml/defines.h>
#include <twml/RawTensor.h>

#include <cstdint>
#include <unordered_map>

namespace twml {

class TensorRecordReader;

// A class containing the data from TensorRecord.
// - This serves as the base class from which DataRecord and HashedDataRecord are inherited.
class TWMLAPI TensorRecord {
public:
  typedef std::unordered_map<int64_t, const RawTensor> RawTensors;
  typedef std::unordered_map<int64_t, const RawSparseTensor> RawSparseTensors;

private:
  RawTensors m_tensors;
  RawSparseTensors m_sparse_tensors;

public:

  const RawTensors &getRawTensors() {
    return m_tensors;
  }

  const RawTensor& getRawTensor(int64_t id) const {
    return m_tensors.at(id);
  }

  const RawSparseTensor& getRawSparseTensor(int64_t id) const {
    return m_sparse_tensors.at(id);
  }

  void addRawTensor(int64_t id, const RawTensor &tensor) {
    m_tensors.emplace(id, tensor);
  }

  friend class TensorRecordReader;
};

}
#endif
