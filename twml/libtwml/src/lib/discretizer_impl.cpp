#include "internal/interpolate.h"
#include "internal/error.h"
#include <twml/discretizer_impl.h>
#include <twml/optim.h>

namespace twml {
  // it is assumed that start_compute and end_compute are valid
  template<typename T>
  void discretizerInfer(Tensor &output_keys,
          Tensor &output_vals,
          const Tensor &input_ids,
          const Tensor &input_vals,
          const Tensor &bin_ids,
          const Tensor &bin_vals,
          const Tensor &feature_offsets,
          int output_bits,
          const Map<int64_t, int64_t> &ID_to_index,
          int64_t start_compute,
          int64_t end_compute,
          int64_t output_start) {
    auto out_keysData = output_keys.getData<int64_t>();
    auto out_valsData = output_vals.getData<T>();
    uint64_t out_keysStride = output_keys.getStride(0);
    uint64_t out_valsStride = output_vals.getStride(0);

    auto in_idsData = input_ids.getData<int64_t>();
    auto in_valsData = input_vals.getData<T>();
    uint64_t in_idsStride = input_ids.getStride(0);
    uint64_t in_valsStride = input_vals.getStride(0);

    auto xsData = bin_vals.getData<T>();
    auto ysData = bin_ids.getData<int64_t>();
    uint64_t xsStride = bin_vals.getStride(0);
    uint64_t ysStride = bin_ids.getStride(0);

    auto offsetData = feature_offsets.getData<int64_t>();

    uint64_t total_bins = bin_ids.getNumElements();
    uint64_t fsize = feature_offsets.getNumElements();

    uint64_t output_size = (1 << output_bits);

    for (uint64_t i = start_compute; i < end_compute; i++) {
      int64_t feature_ID = in_idsData[i * in_idsStride];
      T val = in_valsData[i * in_valsStride];

      auto iter = ID_to_index.find(feature_ID);
      if (iter == ID_to_index.end()) {
        // feature not calibrated
        // modulo add operation for new key from feature ID
        int64_t ikey = feature_ID % (output_size - total_bins) + total_bins;
        out_keysData[(i + output_start - start_compute) * out_keysStride] = ikey;
        out_valsData[(i + output_start - start_compute) * out_valsStride] = val;
        continue;
      }

      int64_t ikey = iter->second;

      // Perform interpolation
      uint64_t offset = offsetData[ikey];
      uint64_t next_offset = (ikey == (int64_t)(fsize - 1)) ? total_bins : offsetData[ikey + 1];
      uint64_t mainSize = next_offset - offset;

      const T *lxsData = xsData + offset;
      const int64_t *lysData = ysData + offset;
      int64_t okey;
      okey = interpolation<T, int64_t>(lxsData, xsStride,
                                       lysData, ysStride,
                                       val, mainSize,
                                       NEAREST, 0);
      out_keysData[(i + output_start - start_compute) * out_keysStride] = okey;
      out_valsData[(i + output_start - start_compute) * out_valsStride] = 1;
    }
  }

  void discretizerInfer(Tensor &output_keys,
          Tensor &output_vals,
          const Tensor &input_ids,
          const Tensor &input_vals,
          const Tensor &bin_ids,
          const Tensor &bin_vals,
          const Tensor &feature_offsets,
          int output_bits,
          const Map<int64_t, int64_t> &ID_to_index,
          int start_compute,
          int end_compute,
          int output_start) {
    if (input_ids.getType() != TWML_TYPE_INT64) {
      throw twml::Error(TWML_ERR_TYPE, "input_ids must be a Long Tensor");
    }

    if (output_keys.getType() != TWML_TYPE_INT64) {
      throw twml::Error(TWML_ERR_TYPE, "output_keys must be a Long Tensor");
    }

    if (bin_ids.getType() != TWML_TYPE_INT64) {
      throw twml::Error(TWML_ERR_TYPE, "bin_ids must be a Long Tensor");
    }

    if (feature_offsets.getType() != TWML_TYPE_INT64) {
      throw twml::Error(TWML_ERR_TYPE, "bin_ids must be a Long Tensor");
    }

    if (input_vals.getType() != bin_vals.getType()) {
      throw twml::Error(TWML_ERR_TYPE,
                "Data type of input_vals does not match type of bin_vals");
    }

    if (bin_vals.getNumDims() != 1) {
      throw twml::Error(TWML_ERR_SIZE,
                "bin_vals must be 1 Dimensional");
    }

    if (bin_ids.getNumDims() != 1) {
      throw twml::Error(TWML_ERR_SIZE,
                "bin_ids must be 1 Dimensional");
    }

    if (bin_vals.getNumElements() != bin_ids.getNumElements()) {
      throw twml::Error(TWML_ERR_SIZE,
                "Dimensions of bin_vals and bin_ids do not match");
    }

    if (feature_offsets.getStride(0) != 1) {
      throw twml::Error(TWML_ERR_SIZE,
                "feature_offsets must be contiguous");
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

    switch (input_vals.getType()) {
    case TWML_TYPE_FLOAT:
      twml::discretizerInfer<float>(output_keys, output_vals,
                  input_ids, input_vals,
                  bin_ids, bin_vals, feature_offsets, output_bits, ID_to_index,
                  start_compute, end_compute, output_start);
      break;
    case TWML_TYPE_DOUBLE:
      twml::discretizerInfer<double>(output_keys, output_vals,
                   input_ids, input_vals,
                   bin_ids, bin_vals, feature_offsets, output_bits, ID_to_index,
                   start_compute, end_compute, output_start);
      break;
    default:
      throw twml::Error(TWML_ERR_TYPE,
        "Unsupported datatype for discretizerInfer");
    }
  }
}  // namespace twml
