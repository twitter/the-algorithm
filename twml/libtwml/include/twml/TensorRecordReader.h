#pragma once
# fdef __cplusplus

# nclude <twml/def nes.h>
# nclude <twml/TensorRecord.h>
# nclude <twml/Thr ftReader.h>

# nclude <cstd nt>

# nclude <vector>
# nclude <str ng>
# nclude <unordered_map>

na space twml {

// Class that parses t  thr ft objects as def ned  n tensor.thr ft
class TWMLAP  TensorRecordReader : publ c Thr ftReader {

  std::vector<u nt64_t> readShape();
  template<typena  T> RawTensor readTypedTensor();
  RawTensor readRawTypedTensor();
  RawTensor readStr ngTensor();
  RawTensor readGeneralTensor();
  RawSparseTensor readCOOSparseTensor();

publ c:
  vo d readTensor(const  nt feature_type, TensorRecord *record);
  vo d readSparseTensor(const  nt feature_type, TensorRecord *record);

  TensorRecordReader(const u nt8_t *buffer) : Thr ftReader(buffer) {}
};

}
#end f
