#pragma once
# nclude <twml/def nes.h>

# nclude <cstddef>
# nclude <vector>
# nclude <str ng>

# fdef __cplusplus
extern "C" {
#end f

  struct twml_tensor__;
  typedef twml_tensor__ * twml_tensor;

# fdef __cplusplus
}
#end f

# fdef __cplusplus
na space twml {

class TWMLAP  Tensor
{
pr vate:
  twml_type m_type;
  vo d *m_data;
  std::vector<u nt64_t> m_d ms;
  std::vector<u nt64_t> m_str des;

publ c:
  Tensor() {}
  Tensor(vo d *data,  nt nd ms, const u nt64_t *d ms, const u nt64_t *str des, twml_type type);
  Tensor(vo d *data, const std::vector<u nt64_t> &d ms, const std::vector<u nt64_t> &str des, twml_type type);

  const std::vector<u nt64_t>& getD ms() const {
    return m_d ms;
  }

   nt getNumD ms() const;
  u nt64_t getD m( nt d m) const;
  u nt64_t getStr de( nt d m) const;
  u nt64_t getNumEle nts() const;
  twml_type getType() const;

  twml_tensor getHandle();
  const twml_tensor getHandle() const;

  template<typena  T> T *getData();
  template<typena  T> const T *getData() const;
};

TWMLAP  std::str ng getTypeNa (twml_type type);
TWMLAP  const Tensor *getConstTensor(const twml_tensor t);
TWMLAP  Tensor *getTensor(twml_tensor t);
TWMLAP  u nt64_t getS zeOf(twml_type type);

}
#end f

# fdef __cplusplus
extern "C" {
#end f
    TWMLAP  twml_err twml_tensor_create(twml_tensor *tensor, vo d *data,
                                         nt nd ms, u nt64_t *d ms,
                                        u nt64_t *str des, twml_type type);

    TWMLAP  twml_err twml_tensor_delete(const twml_tensor tensor);

    TWMLAP  twml_err twml_tensor_get_type(twml_type *type, const twml_tensor tensor);

    TWMLAP  twml_err twml_tensor_get_data(vo d **data, const twml_tensor tensor);

    TWMLAP  twml_err twml_tensor_get_d m(u nt64_t *d m, const twml_tensor tensor,  nt  d);

    TWMLAP  twml_err twml_tensor_get_num_d ms( nt *nd ms, const twml_tensor tensor);

    TWMLAP  twml_err twml_tensor_get_num_ele nts(u nt64_t *nele nts, const twml_tensor tensor);

    TWMLAP  twml_err twml_tensor_get_str de(u nt64_t *str de, const twml_tensor tensor,  nt  d);
# fdef __cplusplus
}
#end f
