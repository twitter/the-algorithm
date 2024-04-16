#pragma once
# fdef __cplusplus

# nclude <twml/def nes.h>
# nclude <twml/RawTensor.h>

# nclude <cstd nt>
# nclude <unordered_map>

na space twml {

class TensorRecordReader;

// A class conta n ng t  data from TensorRecord.
// - T  serves as t  base class from wh ch DataRecord and Has dDataRecord are  n r ed.
class TWMLAP  TensorRecord {
publ c:
  typedef std::unordered_map< nt64_t, const RawTensor> RawTensors;
  typedef std::unordered_map< nt64_t, const RawSparseTensor> RawSparseTensors;

pr vate:
  RawTensors m_tensors;
  RawSparseTensors m_sparse_tensors;

publ c:

  const RawTensors &getRawTensors() {
    return m_tensors;
  }

  const RawTensor& getRawTensor( nt64_t  d) const {
    return m_tensors.at( d);
  }

  const RawSparseTensor& getRawSparseTensor( nt64_t  d) const {
    return m_sparse_tensors.at( d);
  }

  vo d addRawTensor( nt64_t  d, const RawTensor &tensor) {
    m_tensors.emplace( d, tensor);
  }

  fr end class TensorRecordReader;
};

}
#end f
