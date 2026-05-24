#include "internal/error.h"
#include <twml/Tensor.h>
#include <twml/Type.h>
#include <type_traits>
#include <algorithm>
#include <numeric>

namespace twml {

using std::vector;

Tensor::Tensor(void *data, int ndims, const uint64_t *dims, const uint64_t *strides, twml_type type) :
    m_type(type), m_data(data),
    m_dims(dims, dims + ndims),
    m_strides(strides, strides + ndims) {
}

Tensor::Tensor(void *data,
               const vector<uint64_t> &dims,
               const vector<uint64_t> &strides,
               twml_type type) :
    m_type(type), m_data(data),
    m_dims(dims.begin(), dims.end()),
    m_strides(strides.begin(), strides.end()) {
  if (dims.size() != strides.size()) {
    throw twml::Error(TWML_ERR_SIZE, "The number size of dims and strides don't match");
  }
}

int Tensor::getNumDims() const {
  return static_cast<int>(m_dims.size());
}

uint64_t Tensor::getDim(int id) const {
  if (id >= this->getNumDims()) {
    throw twml::Error(TWML_ERR_SIZE, "Requested dimension exceeds tensor dimension");
  }
  return m_dims[id];
}

uint64_t Tensor::getStride(int id) const {
  if (id >= this->getNumDims()) {
    throw twml::Error(TWML_ERR_SIZE, "Requested dimension exceeds tensor dimension");
  }
  return m_strides[id];
}

uint64_t Tensor::getNumElements() const {
  return std::accumulate(m_dims.begin(), m_dims.end(), 1, std::multiplies<int>());
}

twml_type Tensor::getType() const {
  return m_type;
}

twml_tensor Tensor::getHandle() {
  return reinterpret_cast<twml_tensor>(this);
}

const twml_tensor Tensor::getHandle() const {
  return reinterpret_cast<const twml_tensor>(const_cast<Tensor *>(this));
}

const Tensor *getConstTensor(const twml_tensor t) {
  return reinterpret_cast<const Tensor *>(t);
}

Tensor *getTensor(twml_tensor t) {
  return reinterpret_cast<Tensor *>(t);
}

#define INSTANTIATE(T)                                  \
  template<> TWMLAPI T *Tensor::getData() {             \
    if ((twml_type)Type<T>::type != m_type) {           \
      throw twml::Error(TWML_ERR_TYPE,                  \
                        "Requested invalid type");      \
    }                                                   \
    return reinterpret_cast<T *>(m_data);               \
  }                                                     \
  template<> TWMLAPI const T *Tensor::getData() const { \
    if ((twml_type)Type<T>::type != m_type) {           \
      throw twml::Error(TWML_ERR_TYPE,                  \
                        "Requested invalid type");      \
    }                                                   \
    return (const T *)m_data;                           \
  }                                                     \

INSTANTIATE(int32_t)
INSTANTIATE(int64_t)
INSTANTIATE(int8_t)
INSTANTIATE(uint8_t)
INSTANTIATE(float)
INSTANTIATE(double)
INSTANTIATE(bool)
INSTANTIATE(std::string)

// This is used for the C api. No checks needed for void.
template<> TWMLAPI void *Tensor::getData() {
  return m_data;
}
template<> TWMLAPI const void *Tensor::getData() const {
  return (const void *)m_data;
}

std::string getTypeName(twml_type type) {
  switch (type) {
    case TWML_TYPE_FLOAT32 : return "float32";
    case TWML_TYPE_FLOAT64 : return "float64";
    case TWML_TYPE_INT32   : return "int32";
    case TWML_TYPE_INT64   : return "int64";
    case TWML_TYPE_INT8    : return "int8";
    case TWML_TYPE_UINT8   : return "uint8";
    case TWML_TYPE_BOOL    : return "bool";
    case TWML_TYPE_STRING  : return "string";
    case TWML_TYPE_UNKNOWN : return "Unknown type";
  }
  throw twml::Error(TWML_ERR_TYPE, "Uknown type");
}

uint64_t getSizeOf(twml_type dtype) {
  switch (dtype) {
    case TWML_TYPE_FLOAT  : return 4;
    case TWML_TYPE_DOUBLE : return 8;
    case TWML_TYPE_INT64  : return 8;
    case TWML_TYPE_INT32  : return 4;
    case TWML_TYPE_UINT8  : return 1;
    case TWML_TYPE_BOOL   : return 1;
    case TWML_TYPE_INT8   : return 1;
    case TWML_TYPE_STRING :
      throw twml::Error(TWML_ERR_THRIFT, "getSizeOf not supported for strings");
    case TWML_TYPE_UNKNOWN:
      throw twml::Error(TWML_ERR_THRIFT, "Can't get size of unknown types");
  }
  throw twml::Error(TWML_ERR_THRIFT, "Invalid twml_type");
}

}  // namespace twml

twml_err twml_tensor_create(twml_tensor *t, void *data, int ndims, uint64_t *dims,
              uint64_t *strides, twml_type type) {
  HANDLE_EXCEPTIONS(
    twml::Tensor *res =  new twml::Tensor(data, ndims, dims, strides, type);
    *t = reinterpret_cast<twml_tensor>(res););
  return TWML_ERR_NONE;
}

twml_err twml_tensor_delete(const twml_tensor t) {
  HANDLE_EXCEPTIONS(
    delete twml::getConstTensor(t););
  return TWML_ERR_NONE;
}

twml_err twml_tensor_get_type(twml_type *type, const twml_tensor t) {
  HANDLE_EXCEPTIONS(
    *type = twml::getConstTensor(t)->getType(););
  return TWML_ERR_NONE;
}

twml_err twml_tensor_get_data(void **data, const twml_tensor t) {
  HANDLE_EXCEPTIONS(
    *data = twml::getTensor(t)->getData<void>(););
  return TWML_ERR_NONE;
}

twml_err twml_tensor_get_dim(uint64_t *dim, const twml_tensor t, int id) {
  HANDLE_EXCEPTIONS(
    const twml::Tensor *tensor = twml::getConstTensor(t);
    *dim = tensor->getDim(id););
  return TWML_ERR_NONE;
}

twml_err twml_tensor_get_stride(uint64_t *stride, const twml_tensor t, int id) {
  HANDLE_EXCEPTIONS(
    const twml::Tensor *tensor = twml::getConstTensor(t);
    *stride = tensor->getStride(id););
  return TWML_ERR_NONE;
}

twml_err twml_tensor_get_num_dims(int *ndim, const twml_tensor t) {
  HANDLE_EXCEPTIONS(
    const twml::Tensor *tensor = twml::getConstTensor(t);
    *ndim = tensor->getNumDims(););
  return TWML_ERR_NONE;
}

twml_err twml_tensor_get_num_elements(uint64_t *nelements, const twml_tensor t) {
  HANDLE_EXCEPTIONS(
    const twml::Tensor *tensor = twml::getConstTensor(t);
    *nelements = tensor->getNumElements(););
  return TWML_ERR_NONE;
}
