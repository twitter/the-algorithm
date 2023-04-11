#include "tensorflow_utils.h"
#include <string>
#include <vector>

twml::Tensor TFTensor_to_twml_tensor(Tensor &input) {
  int ndims = input.dims();
  std::vector<uint64_t> dims(ndims);
  std::vector<uint64_t> strides(ndims);
  for (int i = 0; i < ndims; i++) {
    dims[i] = input.dim_size(i);
  }
  uint64_t stride = 1;
  for (int i = ndims-1; i >= 0; i--) {
    strides[i] = stride;
    stride *= dims[i];
  }

  switch (input.dtype()) {
    case DT_INT8:
      return twml::Tensor(input.flat<int8>().data(), dims, strides, TWML_TYPE_INT8);
    case DT_UINT8:
      return twml::Tensor(input.flat<uint8>().data(), dims, strides, TWML_TYPE_UINT8);
    case DT_INT32:
      return twml::Tensor(input.flat<int32>().data(), dims, strides, TWML_TYPE_INT32);
    case DT_INT64:
      return twml::Tensor(input.flat<int64>().data(), dims, strides, TWML_TYPE_INT64);
    case DT_FLOAT:
      return twml::Tensor(input.flat<float>().data(), dims, strides, TWML_TYPE_FLOAT);
    case DT_DOUBLE:
      return twml::Tensor(input.flat<double>().data(), dims, strides, TWML_TYPE_DOUBLE);
    case DT_BOOL:
      return twml::Tensor(input.flat<bool>().data(), dims, strides, TWML_TYPE_BOOL);
    case DT_STRING:
      return twml::Tensor(input.flat<string>().data(), dims, strides, TWML_TYPE_STRING);
    default:
      throw twml::Error(TWML_ERR_TYPE, "Unknown tensor data type.");
      break;
  }
}

const twml::Tensor TFTensor_to_twml_tensor(const Tensor &input) {
  // TODO: define some type of constant tensor, which should be used for inputs to force not
  // changing
  return TFTensor_to_twml_tensor(const_cast<Tensor&>(input));
}

twml::RawTensor TFTensor_to_twml_raw_tensor(Tensor &input) {
  int ndims = input.dims();
  std::vector<uint64_t> dims(ndims);
  std::vector<uint64_t> strides(ndims);
  for (int i = 0; i < ndims; i++) {
    dims[i] = input.dim_size(i);
  }
  uint64_t stride = 1;
  for (int i = ndims-1; i >= 0; i--) {
    strides[i] = stride;
    stride *= dims[i];
  }

  switch (input.dtype()) {
    case DT_INT8:
      return twml::RawTensor(input.flat<int8>().data(), dims, strides, TWML_TYPE_INT8, false, input.flat<int8>().size());
    case DT_UINT8:
      return twml::RawTensor(input.flat<uint8>().data(), dims, strides, TWML_TYPE_UINT8, false, input.flat<uint8>().size());
    case DT_INT32:
      return twml::RawTensor(input.flat<int32>().data(), dims, strides, TWML_TYPE_INT32, false, input.flat<int32>().size());
    case DT_INT64:
      return twml::RawTensor(input.flat<int64>().data(), dims, strides, TWML_TYPE_INT64, false, input.flat<int64>().size());
    case DT_FLOAT:
      return twml::RawTensor(input.flat<float>().data(), dims, strides, TWML_TYPE_FLOAT, false, input.flat<float>().size());
    case DT_DOUBLE:
      return twml::RawTensor(input.flat<double>().data(), dims, strides, TWML_TYPE_DOUBLE, false, input.flat<double>().size());
    case DT_BOOL:
      return twml::RawTensor(input.flat<bool>().data(), dims, strides, TWML_TYPE_BOOL, false, input.flat<bool>().size());
    case DT_STRING:
      return twml::RawTensor(input.flat<string>().data(), dims, strides, TWML_TYPE_STRING, false, input.flat<string>().size());
    default:
      throw twml::Error(TWML_ERR_TYPE, "Unknown tensor data type.");
      break;
  }
}

const twml::RawTensor TFTensor_to_twml_raw_tensor(const Tensor &input) {
  // TODO: define some type of constant tensor, which should be used for inputs to force not
  // changing
  return TFTensor_to_twml_raw_tensor(const_cast<Tensor&>(input));
}
