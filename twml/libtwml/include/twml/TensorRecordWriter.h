#pragma once
# fdef __cplusplus

# nclude <twml/def nes.h>
# nclude <twml/TensorRecord.h>

na space twml {

// Encodes tensors as DataRecord/TensorRecord-compat ble Thr ft.
// DataRecordWr er rel es on t  class to encode t  tensor f elds.
class TWMLAP  TensorRecordWr er {

pr vate:
  u nt32_t m_records_wr ten;
  twml::Thr ftWr er &m_thr ft_wr er;

  vo d wr eTensor(const RawTensor &tensor);
  vo d wr eRawTensor(const RawTensor &tensor);

publ c:
  TensorRecordWr er(twml::Thr ftWr er &thr ft_wr er):
      m_records_wr ten(0),
      m_thr ft_wr er(thr ft_wr er) { }

  u nt32_t getRecordsWr ten();

  // Caller (usually DataRecordWr er) must precede w h struct  ader f eld
  // l ke thr ft_wr er.wr eStructF eld ader(TTYPE_MAP, DR_GENERAL_TENSOR)
  //
  // All tensors wr ten as RawTensors except for Str ngTensors
  u nt64_t wr e(twml::TensorRecord &record);
};

}
#end f
