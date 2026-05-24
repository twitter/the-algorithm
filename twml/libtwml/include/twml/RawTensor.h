#pragma once
#include <twml/Tensor.h>
#include <type_traits>

#ifdef __cplusplus
namespace twml {

// This class contains the raw pointers to tensors coming from thrift object.
class TWMLAPI RawTensor : public Tensor
{
private:
  bool m_is_big_endian;
  uint64_t m_raw_length;
public:

  RawTensor() {}

  RawTensor(void *data, const std::vector<uint64_t> &dims,
            const std::vector<uint64_t> &strides, twml_type type, bool is_big_endian, uint64_t length)
      :  Tensor(data, dims, strides, type), m_is_big_endian(is_big_endian), m_raw_length(length) {}

  bool is_big_endian() const {
    return m_is_big_endian;
  }

  uint64_t getRawLength() const {
    return m_raw_length;
  }

  // Extracts a slice from a tensor at idx0 along dimension 0
  // Used in BatchPredictionResponse to write each slice in separate records
  RawTensor getSlice(uint64_t idx0) const {
    void *slice = nullptr;
    uint64_t raw_length = 0;

    if (getType() == TWML_TYPE_STRING) {
      raw_length = getStride(0);
      std::string *data = const_cast<std::string *>(static_cast<const std::string*>(getData<void>()));
      slice = static_cast<void *>(data + raw_length * idx0);
    } else {
      raw_length = getStride(0) * getSizeOf(getType());
      char *data = const_cast<char *>(static_cast<const char*>(getData<void>()));
      slice = static_cast<void *>(data + raw_length * idx0);
    }

    std::vector<uint64_t> dims, strides;
    for (int i = 1; i < getNumDims(); i++) {
      dims.push_back(getDim(i));
      strides.push_back(getStride(i));
    }

    return RawTensor(slice, dims, strides, getType(), m_is_big_endian, raw_length);
  }
};

// Wrapper class around RawTensor to hold sparse tensors.
class TWMLAPI RawSparseTensor
{
private:
  RawTensor m_indices;
  RawTensor m_values;
  std::vector<uint64_t> m_dense_shape;

public:

  RawSparseTensor() {
  }

  RawSparseTensor(const RawTensor &indices_, const RawTensor &values_,
                  const std::vector<uint64_t> &dense_shape_) :
      m_indices(indices_), m_values(values_), m_dense_shape(dense_shape_)
  {
    if (m_indices.getType() != TWML_TYPE_INT64) {
      throw twml::Error(TWML_ERR_TYPE, "Indices of Sparse Tensor must be of type int64");
    }
  }

  const RawTensor &indices() const {
    return m_indices;
  }

  const RawTensor &values() const {
    return m_values;
  }

  const std::vector<uint64_t>& denseShape() const {
    return m_dense_shape;
  }
};

}
#endif
