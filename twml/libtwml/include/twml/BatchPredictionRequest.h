#pragma once

# fdef __cplusplus

# nclude <twml/DataRecord.h>
# nclude <twml/Has dDataRecord.h>
# nclude <twml/Tensor.h>

na space twml {

template<class RecordType>
class Gener cBatchPred ct onRequest {
 stat c_assert(std:: s_sa <RecordType, Has dDataRecord>::value ||
               std:: s_sa <RecordType, DataRecord>::value,
               "RecordType has to be Has dDatarecord or DataRecord");
 publ c:
  typedef typena  RecordType::Reader Reader;
  Gener cBatchPred ct onRequest( nt numOfLabels=0,  nt numOf  ghts=0):
      m_common_features(), m_requests(),
      num_labels(numOfLabels), num_  ghts(numOf  ghts)
  {}

  vo d decode(Reader &reader);

  std::vector<RecordType>& requests() {
    return m_requests;
  }

  RecordType& common() {
    return m_common_features;
  }

 pr vate:
  RecordType m_common_features;
  std::vector<RecordType> m_requests;
   nt num_labels;
   nt num_  ghts;
};

us ng Has dBatchPred ct onRequest = Gener cBatchPred ct onRequest<Has dDataRecord>;
us ng BatchPred ct onRequest = Gener cBatchPred ct onRequest<DataRecord>;

}

#end f
