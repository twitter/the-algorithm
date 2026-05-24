#pragma once

#ifdef __cplusplus

#include <twml/DataRecord.h>
#include <twml/HashedDataRecord.h>
#include <twml/Tensor.h>

namespace twml {

template<class RecordType>
class GenericBatchPredictionRequest {
 static_assert(std::is_same<RecordType, HashedDataRecord>::value ||
               std::is_same<RecordType, DataRecord>::value,
               "RecordType has to be HashedDatarecord or DataRecord");
 public:
  typedef typename RecordType::Reader Reader;
  GenericBatchPredictionRequest(int numOfLabels=0, int numOfWeights=0):
      m_common_features(), m_requests(),
      num_labels(numOfLabels), num_weights(numOfWeights)
  {}

  void decode(Reader &reader);

  std::vector<RecordType>& requests() {
    return m_requests;
  }

  RecordType& common() {
    return m_common_features;
  }

 private:
  RecordType m_common_features;
  std::vector<RecordType> m_requests;
  int num_labels;
  int num_weights;
};

using HashedBatchPredictionRequest = GenericBatchPredictionRequest<HashedDataRecord>;
using BatchPredictionRequest = GenericBatchPredictionRequest<DataRecord>;

}

#endif
