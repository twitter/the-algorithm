#include "internal/linear_search.h"
#include "internal/error.h"
#include <twml/hashing_discretizer_impl.h>
#include <twml/optim.h>
#include <algorithm>

namespace twml {
  template<typename Tx>
  static int64_t lower_bound_search(const Tx *data, const Tx val, const int64_t buf_size) {
    auto index_temp = std::lower_bound(data, data + buf_size, val);
    return static_cast<int64_t>(index_temp - data);
  }

  template<typename Tx>
  static int64_t upper_bound_search(const Tx *data, const Tx val, const int64_t buf_size) {
    auto index_temp = std::upper_bound(data, data + buf_size, val);
    return static_cast<int64_t>(index_temp - data);
  }

  template<typename Tx>
  using search_method = int64_t (*)(const Tx *, const Tx, const int64_t);

  typedef uint64_t (*hash_signature)(uint64_t, int64_t, uint64_t);

  // uint64_t integer_multiplicative_hashing()
  //
  // A function to hash discretized feature_ids into one of 2**output_bits buckets.
  // This function hashes the feature_ids to achieve a uniform distribution of
  //   IDs, so the hashed IDs are with high probability far apart
  // Then, bucket_indices can simply be added, resulting in unique new IDs with high probability
  // We integer hash again to again spread out the new IDs
  // Finally we take the upper
  // Required args:
  //   feature_id:
  //     The feature id of the feature to be hashed.
  //   bucket_index:
  //     The bucket index of the discretized feature value
  //   output_bits:
  //     The number of bits of output space for the features to be hashed into.
  //
  // Note - feature_ids may have arbitrary distribution within int32s
  // Note - 64 bit feature_ids can be processed with this, but the upper
  //          32 bits have no effect on the output
  // e.g. all feature ids 0 through 255 exist in movie-lens.
  // this hashing constant is good for 32 LSBs. will use N=32. (can use N<32 also)
  // this hashing constant is co-prime with 2**32, therefore we have that
  //   a != b, a and b in [0,2**32)
  //    implies
  //   f(a) != f(b) where f(x) = (hashing_constant * x) % (2**32)
  // note that we are mostly ignoring the upper 32 bits, using modulo 2**32 arithmetic
  uint64_t integer_multiplicative_hashing(uint64_t feature_id,
                                          int64_t bucket_index,
                                          uint64_t output_bits) {
    // possibly use 14695981039346656037 for 64 bit unsigned??
    //  = 20921 * 465383 * 1509404459
    // alternatively, 14695981039346656039 is prime
    // We would also need to use N = 64
    const uint64_t hashing_constant = 2654435761;
    const uint64_t N = 32;
    // hash once to prevent problems from anomalous input id distributions
    feature_id *= hashing_constant;
    feature_id += bucket_index;
    // this hash enables the following right shift operation
    //  without losing the bucket information (lower bits)
    feature_id *= hashing_constant;
    // output size is a power of 2
    feature_id >>= N - output_bits;
    uint64_t mask = (1 << output_bits) - 1;
    return mask & feature_id;
  }

  uint64_t integer64_multiplicative_hashing(uint64_t feature_id,
                                            int64_t bucket_index,
                                            uint64_t output_bits) {
    const uint64_t hashing_constant = 14695981039346656039UL;
    const uint64_t N = 64;
    // hash once to prevent problems from anomalous input id distributions
    feature_id *= hashing_constant;
    feature_id += bucket_index;
    // this hash enables the following right shift operation
    //  without losing the bucket information (lower bits)
    feature_id *= hashing_constant;
    // output size is a power of 2
    feature_id >>= N - output_bits;
    uint64_t mask = (1 << output_bits) - 1;
    return mask & feature_id;
  }

  int64_t option_bits(int64_t options, int64_t high, int64_t low) {
    options >>= low;
    options &= (1 << (high - low + 1)) - 1;
    return options;
  }

  // it is assumed that start_compute and end_compute are valid
  template<typename T>
  void hashDiscretizerInfer(Tensor &output_keys,
                            Tensor &output_vals,
                            const Tensor &input_ids,
                            const Tensor &input_vals,
                            const Tensor &bin_vals,
                            int output_bits,
                            const Map<int64_t, int64_t> &ID_to_index,
                            int64_t start_compute,
                            int64_t end_compute,
                            int64_t n_bin,
                            int64_t options) {
    auto output_keys_data = output_keys.getData<int64_t>();
    auto output_vals_data = output_vals.getData<T>();

    auto input_ids_data = input_ids.getData<int64_t>();
    auto input_vals_data = input_vals.getData<T>();

    auto bin_vals_data = bin_vals.getData<T>();

    // The function pointer implementation removes the option_bits
    // function call (might be inlined) and corresponding branch from
    // the hot loop, but it prevents inlining these functions, so
    // there will be function call overhead. Uncertain which would
    // be faster, testing needed. Also, code optimizers do weird things...
    hash_signature hash_fn = integer_multiplicative_hashing;
    switch (option_bits(options, 4, 2)) {
      case 0:
      hash_fn = integer_multiplicative_hashing;
      break;
      case 1:
      hash_fn = integer64_multiplicative_hashing;
      break;
      default:
      hash_fn = integer_multiplicative_hashing;
    }

    search_method<T> search_fn = lower_bound_search;
    switch (option_bits(options, 1, 0)) {
      case 0:
      search_fn = lower_bound_search<T>;
      break;
      case 1:
      search_fn = linear_search<T>;
      break;
      case 2:
      search_fn = upper_bound_search<T>;
      break;
      default:
      search_fn = lower_bound_search<T>;
    }

    for (uint64_t i = start_compute; i < end_compute; i++) {
      int64_t id = input_ids_data[i];
      T val = input_vals_data[i];

      auto iter = ID_to_index.find(id);
      if (iter != ID_to_index.end()) {
        int64_t feature_idx = iter->second;
        const T *bin_vals_start = bin_vals_data + feature_idx * n_bin;
        int64_t out_bin_idx = search_fn(bin_vals_start, val, n_bin);
        output_keys_data[i] = hash_fn(id, out_bin_idx, output_bits);
        output_vals_data[i] = 1;
      } else {
        // feature not calibrated
        output_keys_data[i] = id & ((1 << output_bits) - 1);
        output_vals_data[i] = val;
      }
    }
  }

  void hashDiscretizerInfer(Tensor &output_keys,
                            Tensor &output_vals,
                            const Tensor &input_ids,
                            const Tensor &input_vals,
                            int n_bin,
                            const Tensor &bin_vals,
                            int output_bits,
                            const Map<int64_t, int64_t> &ID_to_index,
                            int start_compute,
                            int end_compute,
                            int64_t options) {
    if (input_ids.getType() != TWML_TYPE_INT64) {
      throw twml::Error(TWML_ERR_TYPE, "input_ids must be a Long Tensor");
    }

    if (output_keys.getType() != TWML_TYPE_INT64) {
      throw twml::Error(TWML_ERR_TYPE, "output_keys must be a Long Tensor");
    }

    if (input_vals.getType() != bin_vals.getType()) {
      throw twml::Error(TWML_ERR_TYPE,
                "Data type of input_vals does not match type of bin_vals");
    }

    if (bin_vals.getNumDims() != 1) {
      throw twml::Error(TWML_ERR_SIZE,
                "bin_vals must be 1 Dimensional");
    }

    uint64_t size = input_ids.getDim(0);
    if (end_compute == -1) {
      end_compute = size;
    }

    if (start_compute < 0 || start_compute >= size) {
      throw twml::Error(TWML_ERR_SIZE,
                "start_compute out of range");
    }

    if (end_compute < -1 || end_compute > size) {
      throw twml::Error(TWML_ERR_SIZE,
                "end_compute out of range");
    }

    if (start_compute > end_compute && end_compute != -1) {
      throw twml::Error(TWML_ERR_SIZE,
                "must have start_compute <= end_compute, or end_compute==-1");
    }

    if (output_keys.getStride(0) != 1 || output_vals.getStride(0) != 1 ||
        input_ids.getStride(0) != 1 || input_vals.getStride(0) != 1 ||
        bin_vals.getStride(0) != 1) {
      throw twml::Error(TWML_ERR_SIZE,
                "All Strides must be 1.");
    }

    switch (input_vals.getType()) {
    case TWML_TYPE_FLOAT:
      twml::hashDiscretizerInfer<float>(output_keys, output_vals,
                  input_ids, input_vals,
                  bin_vals, output_bits, ID_to_index,
                  start_compute, end_compute, n_bin, options);
      break;
    case TWML_TYPE_DOUBLE:
      twml::hashDiscretizerInfer<double>(output_keys, output_vals,
                   input_ids, input_vals,
                   bin_vals, output_bits, ID_to_index,
                   start_compute, end_compute, n_bin, options);
      break;
    default:
      throw twml::Error(TWML_ERR_TYPE,
        "Unsupported datatype for hashDiscretizerInfer");
    }
  }
}  // namespace twml
