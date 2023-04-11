#pragma once
#include <twml/defines.h>

#include <cstddef>
#include <vector>
#include <string>

#ifdef __cplusplus
extern "C" {
#endif

  struct twml_tensor__;
  typedef twml_tensor__ * twml_tensor;

#ifdef __cplusplus
}
#endif

#ifdef __cplusplus
namespace twml {

class TWMLAPI Tensor
{
private:
  twml_type m_type;
  void *m_data;
  std::vector<uint64_t> m_dims;
  std::vector<uint64_t> m_strides;

public:
  Tensor() {}
  Tensor(void *data, int ndims, const uint64_t *dims, const uint64_t *strides, twml_type type);
  Tensor(void *data, const std::vector<uint64_t> &dims, const std::vector<uint64_t> &strides, twml_type type);

  const std::vector<uint64_t>& getDims() const {
    return m_dims;
  }

  int getNumDims() const;
  uint64_t getDim(int dim) const;
  uint64_t getStride(int dim) const;
  uint64_t getNumElements() const;
  twml_type getType() const;

  twml_tensor getHandle();
  const twml_tensor getHandle() const;

  template<typename T> T *getData();
  template<typename T> const T *getData() const;
};

TWMLAPI std::string getTypeName(twml_type type);
TWMLAPI const Tensor *getConstTensor(const twml_tensor t);
TWMLAPI Tensor *getTensor(twml_tensor t);
TWMLAPI uint64_t getSizeOf(twml_type type);

}
#endif

#ifdef __cplusplus
extern "C" {
#endif
    TWMLAPI twml_err twml_tensor_create(twml_tensor *tensor, void *data,
                                        int ndims, uint64_t *dims,
                                        uint64_t *strides, twml_type type);

    TWMLAPI twml_err twml_tensor_delete(const twml_tensor tensor);

    TWMLAPI twml_err twml_tensor_get_type(twml_type *type, const twml_tensor tensor);

    TWMLAPI twml_err twml_tensor_get_data(void **data, const twml_tensor tensor);

    TWMLAPI twml_err twml_tensor_get_dim(uint64_t *dim, const twml_tensor tensor, int id);

    TWMLAPI twml_err twml_tensor_get_num_dims(int *ndims, const twml_tensor tensor);

    TWMLAPI twml_err twml_tensor_get_num_elements(uint64_t *nelements, const twml_tensor tensor);

    TWMLAPI twml_err twml_tensor_get_stride(uint64_t *stride, const twml_tensor tensor, int id);
#ifdef __cplusplus
}
#endif
