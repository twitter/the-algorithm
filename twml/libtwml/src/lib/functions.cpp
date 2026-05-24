#include "internal/error.h"
#include "internal/murmur_hash3.h"
#include "internal/utf_converter.h"
#include <twml/functions.h>
#include <cstring>
#include <algorithm>

namespace twml {

  template<typename T>
  void add1(Tensor &output, const Tensor input) {
    T *odata = output.getData<T>();
    const T *idata = input.getData<T>();
    const uint64_t num_elements = input.getNumElements();

    for (uint64_t i = 0; i < num_elements; i++) {
      odata[i] = idata[i] + 1;
    }
  }

  template<typename T>
  void copy(Tensor &output, const Tensor input) {
    T *odata = output.getData<T>();
    const T *idata = input.getData<T>();
    const uint64_t num_elements = input.getNumElements();

    for (uint64_t i = 0; i < num_elements; i++) {
      odata[i] = idata[i];
    }
  }

  void add1(Tensor &output, const Tensor input) {
    auto type =  input.getType();
    if (output.getType() != type) {
      throw twml::Error(TWML_ERR_TYPE, "Output type does not match input type");
    }

    if (output.getNumElements() != input.getNumElements()) {
      throw twml::Error(TWML_ERR_SIZE, "Output size does not match input size");
    }

    // TODO: Implement an easier dispatch function
    switch (type) {
    case TWML_TYPE_FLOAT:
      twml::add1<float>(output, input);
      break;
    case TWML_TYPE_DOUBLE:
      twml::add1<double>(output, input);
      break;
    default:
      throw twml::Error(TWML_ERR_TYPE, "add1 only supports float and double tensors");
    }
  }

  void copy(Tensor &output, const Tensor input) {
    auto type =  input.getType();
    if (output.getType() != type) {
      throw twml::Error(TWML_ERR_TYPE, "Output type does not match input type");
    }

    if (output.getNumElements() != input.getNumElements()) {
      throw twml::Error(TWML_ERR_SIZE, "Output size does not match input size");
    }

    // TODO: Implement an easier dispatch function
    switch (type) {
    case TWML_TYPE_FLOAT:
      twml::copy<float>(output, input);
      break;
    case TWML_TYPE_DOUBLE:
      twml::copy<double>(output, input);
      break;
    default:
      throw twml::Error(TWML_ERR_TYPE, "copy only supports float and double tensors");
    }
  }

  int64_t featureId(const std::string &feature) {
    const char *str = feature.c_str();
    uint64_t len = feature.size();
    int64_t id = 0;
    TWML_CHECK(twml_get_feature_id(&id, len, str), "Error getting featureId");
    return id;
  }
}  // namespace twml

twml_err twml_add1(twml_tensor output, const twml_tensor input) {
  HANDLE_EXCEPTIONS(
    auto out = twml::getTensor(output);
    auto in = twml::getConstTensor(input);
    twml::add1(*out, *in););
  return TWML_ERR_NONE;
}

twml_err twml_copy(twml_tensor output, const twml_tensor input) {
  HANDLE_EXCEPTIONS(
    auto out = twml::getTensor(output);
    auto in = twml::getConstTensor(input);
    twml::copy(*out, *in););
  return TWML_ERR_NONE;
}

inline twml_err twml_get_feature_id_internal(int64_t *result,
                                             uint64_t out_size, uint16_t *out,
                                             uint64_t out2_size, uint16_t *out2,
                                             const uint64_t len, const char *str) {
  uint64_t k = 0;
  for (uint64_t i = 0; i < len; i++) {
    if (str[i] == '#') {
      k = i;
      break;
    }
  }

  uint8_t hash[16];
  if (k != 0) {
    ssize_t n = utf8_to_utf16((const uint8_t *) str, k, out, out_size);
    if (n < 0) throw std::invalid_argument("error while converting from utf8 to utf16");

    MurmurHash3_x64_128(out, n * sizeof(uint16_t), 0, out2);
    n = utf8_to_utf16((const uint8_t *) (str + k + 1), len - k - 1, &out2[4], out2_size - 8);
    if (n < 0) throw std::invalid_argument("error while converting from utf8 to utf16");

    MurmurHash3_x64_128(out2, (n * sizeof(uint16_t)) + 8, 0, hash);
  } else {
    ssize_t n = utf8_to_utf16((const uint8_t *)str, len, out, out_size);
    if (n < 0) throw std::invalid_argument("error while converting from utf8 to utf16");
    MurmurHash3_x64_128(out, n * sizeof(uint16_t), 0, hash);
  }
  int64_t id;
  memcpy(&id, hash, sizeof(int64_t));
  *result = id;

  return TWML_ERR_NONE;
}

static const int UTF16_STR_MAX_SIZE = 1024;

twml_err twml_get_feature_id(int64_t *result, const uint64_t len, const char *str) {
  try {
    uint16_t out[UTF16_STR_MAX_SIZE];
    uint16_t out2[UTF16_STR_MAX_SIZE];
    return twml_get_feature_id_internal(result,
                                        UTF16_STR_MAX_SIZE, out,
                                        UTF16_STR_MAX_SIZE, out2,
                                        len, str);
  } catch(const std::invalid_argument &ex) {
    // If the space on the stack is not enough, try using the heap.
    // len + 1 is needed because a null terminating character is added at the end.
    std::vector<uint16_t> out(len + 1);
    std::vector<uint16_t> out2(len + 1);
    return twml_get_feature_id_internal(result,
                                        len + 1, out.data(),
                                        len + 1, out2.data(),
                                        len, str);

  }
}
