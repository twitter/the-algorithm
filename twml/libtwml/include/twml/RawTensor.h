#pragma once
# nclude <twml/Tensor.h>
# nclude <type_tra s>

# fdef __cplusplus
na space twml {

// T  class conta ns t  raw po nters to tensors com ng from thr ft object.
class TWMLAP  RawTensor : publ c Tensor
{
pr vate:
  bool m_ s_b g_end an;
  u nt64_t m_raw_length;
publ c:

  RawTensor() {}

  RawTensor(vo d *data, const std::vector<u nt64_t> &d ms,
            const std::vector<u nt64_t> &str des, twml_type type, bool  s_b g_end an, u nt64_t length)
      :  Tensor(data, d ms, str des, type), m_ s_b g_end an( s_b g_end an), m_raw_length(length) {}

  bool  s_b g_end an() const {
    return m_ s_b g_end an;
  }

  u nt64_t getRawLength() const {
    return m_raw_length;
  }

  // Extracts a sl ce from a tensor at  dx0 along d  ns on 0
  // Used  n BatchPred ct onResponse to wr e each sl ce  n separate records
  RawTensor getSl ce(u nt64_t  dx0) const {
    vo d *sl ce = nullptr;
    u nt64_t raw_length = 0;

     f (getType() == TWML_TYPE_STR NG) {
      raw_length = getStr de(0);
      std::str ng *data = const_cast<std::str ng *>(stat c_cast<const std::str ng*>(getData<vo d>()));
      sl ce = stat c_cast<vo d *>(data + raw_length *  dx0);
    } else {
      raw_length = getStr de(0) * getS zeOf(getType());
      char *data = const_cast<char *>(stat c_cast<const char*>(getData<vo d>()));
      sl ce = stat c_cast<vo d *>(data + raw_length *  dx0);
    }

    std::vector<u nt64_t> d ms, str des;
    for ( nt   = 1;   < getNumD ms();  ++) {
      d ms.push_back(getD m( ));
      str des.push_back(getStr de( ));
    }

    return RawTensor(sl ce, d ms, str des, getType(), m_ s_b g_end an, raw_length);
  }
};

// Wrapper class around RawTensor to hold sparse tensors.
class TWMLAP  RawSparseTensor
{
pr vate:
  RawTensor m_ nd ces;
  RawTensor m_values;
  std::vector<u nt64_t> m_dense_shape;

publ c:

  RawSparseTensor() {
  }

  RawSparseTensor(const RawTensor & nd ces_, const RawTensor &values_,
                  const std::vector<u nt64_t> &dense_shape_) :
      m_ nd ces( nd ces_), m_values(values_), m_dense_shape(dense_shape_)
  {
     f (m_ nd ces.getType() != TWML_TYPE_ NT64) {
      throw twml::Error(TWML_ERR_TYPE, " nd ces of Sparse Tensor must be of type  nt64");
    }
  }

  const RawTensor & nd ces() const {
    return m_ nd ces;
  }

  const RawTensor &values() const {
    return m_values;
  }

  const std::vector<u nt64_t>& denseShape() const {
    return m_dense_shape;
  }
};

}
#end f
