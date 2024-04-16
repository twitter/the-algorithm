#pragma once
# fdef __cplusplus

# nclude <twml/def nes.h>
# nclude <twml/DataRecord.h>
# nclude <twml/TensorRecordWr er.h>

na space twml {

// Encodes DataRecords as b nary Thr ft. BatchPred ct onResponse
// uses t  class to encode pred ct on responses through  
// TensorFlow response wr er operator.
class TWMLAP  DataRecordWr er {
  pr vate:
    u nt32_t m_records_wr ten;
    twml::Thr ftWr er &m_thr ft_wr er;
    twml::TensorRecordWr er m_tensor_wr er;

    vo d wr eB nary(twml::DataRecord &record);
    vo d wr eCont nuous(twml::DataRecord &record);
    vo d wr eD screte(twml::DataRecord &record);
    vo d wr eStr ng(twml::DataRecord &record);
    vo d wr eSparseB naryFeatures(twml::DataRecord &record);
    vo d wr eSparseCont nuousFeatures(twml::DataRecord &record);
    vo d wr eBlobFeatures(twml::DataRecord &record);
    vo d wr eDenseTensors(twml::DataRecord &record);

  publ c:
    DataRecordWr er(twml::Thr ftWr er &thr ft_wr er):
      m_records_wr ten(0),
      m_thr ft_wr er(thr ft_wr er),
      m_tensor_wr er(twml::TensorRecordWr er(thr ft_wr er)) { }

    u nt32_t getRecordsWr ten();
    u nt64_t wr e(twml::DataRecord &record);
};

}
#end f
