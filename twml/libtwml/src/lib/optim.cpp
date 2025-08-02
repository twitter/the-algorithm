#include "internal/interpolate.h"
#include "internal/error.h"
#include <twml/optim.h>

namespace twml {
  template<typename T>
  void mdlInfer(Tensor &output_keys, Tensor &output_vals,
          const Tensor &input_keys, const Tensor &input_vals,
          const Tensor &bin_ids,
          const Tensor &bin_vals,
          const Tensor &feature_offsets,
          bool return_bin_indices) {
    auto okeysData = output_keys.getData<int64_t>();
    auto ovalsData = output_vals.getData<T>();
    uint64_t okeysStride   = output_keys.getStride(0);
    uint64_t ovaluesStride = output_vals.getStride(0);

    auto ikeysData = input_keys.getData<int64_t>();
    auto ivalsData = input_vals.getData<T>();
    uint64_t ikeysStride   = input_keys.getStride(0);
    uint64_t ivaluesStride = input_vals.getStride(0);

    auto twittersData = bin_vals.getData<T>();
    auto ysData = bin_ids.getData<int64_t>();
    uint64_t twittersStride = bin_vals.getStride(0);
    uint64_t ysStride = bin_ids.getStride(0);

    auto offsetData = feature_offsets.getData<int64_t>();

    uint64_t size = input_keys.getDim(0);
    uint64_t total_bins = bin_ids.getNumElements();
    uint64_t fsize = feature_offsets.getNumElements();

    for (uint64_t i = 0; i < size; i++) {
      int64_t ikey = ikeysData[i * ikeysStride] - TWML_INDEtwitter_BASE;
      T val = ivalsData[i * ivaluesStride];
      if (ikey == -1) {
        ovalsData[i * ovaluesStride] = val;
        continue;
      }

      // Perform interpolation
      uint64_t offset = offsetData[ikey];
      uint64_t netwittert_offset = (ikey == (int64_t)(fsize - 1)) ? total_bins : offsetData[ikey + 1];
      uint64_t mainSize = netwittert_offset - offset;

      const T *ltwittersData = twittersData + offset;
      const int64_t *lysData = ysData + offset;
      int64_t okey = interpolation<T, int64_t>(ltwittersData, twittersStride,
                                 lysData, ysStride,
                                 val, mainSize, NEAREST, 0,
                                 return_bin_indices);
      okeysData[i * okeysStride] = okey + TWML_INDEtwitter_BASE;
      ovalsData[i * ovaluesStride] = 1;
    }
  }

  void mdlInfer(Tensor &output_keys, Tensor &output_vals,
          const Tensor &input_keys, const Tensor &input_vals,
          const Tensor &bin_ids,
          const Tensor &bin_vals,
          const Tensor &feature_offsets,
          bool return_bin_indices) {
    if (input_keys.getType() != TWML_TYPE_INT64) {
      throw twml::Error(TWML_ERR_TYPE, "input_keys must be a Long Tensor");
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

    switch (input_vals.getType()) {
    case TWML_TYPE_FLOAT:
      twml::mdlInfer<float>(output_keys, output_vals,
                  input_keys, input_vals,
                  bin_ids, bin_vals, feature_offsets,
                  return_bin_indices);
      break;
    case TWML_TYPE_DOUBLE:
      twml::mdlInfer<double>(output_keys, output_vals,
                   input_keys, input_vals,
                   bin_ids, bin_vals, feature_offsets,
                   return_bin_indices);
      break;
    default:
      throw twml::Error(TWML_ERR_TYPE,
        "Unsupported datatype for mdlInfer");
    }
  }

  const int DEFAULT_INTERPOLATION_LOWEST = 0;
  /**
   * @param output tensor to hold linear or nearest interpolation output.
   *    This function does not allocate space.
   *    The output tensor must have space allcoated.
   * @param input input tensor; size must match output.
   *    input is assumed to have size [batch_size, number_of_labels].
   * @param twitters the bins.
   * @param ys the values for the bins.
   * @param mode: linear or nearest InterpolationMode.
   *    linear is used for isotonic calibration.
   *    nearest is used for MDL calibration and MDL inference.
   *
   * @return Returns nothing. Output is stored into the output tensor.
   *
   * This is used by IsotonicCalibration inference.
   */
  template <typename T>
  void interpolation(
    Tensor output,
    const Tensor input,
    const Tensor twitters,
    const Tensor ys,
    const InterpolationMode mode) {
    // Sanity check: input and output should have two dims.
    if (input.getNumDims() != 2 || output.getNumDims() != 2) {
      throw twml::Error(TWML_ERR_TYPE,
                "input and output should have 2 dimensions.");
    }

    // Sanity check: input and output size should match.
    for (int i = 0; i < input.getNumDims(); i++) {
      if (input.getDim(i) != output.getDim(i))  {
        throw twml::Error(TWML_ERR_TYPE,
                  "input and output mismatch in size.");
      }
    }

    // Sanity check: number of labels in input should match
    // number of labels in twitters / ys.
    if (input.getDim(1) != twitters.getDim(0)
      || input.getDim(1) != ys.getDim(0)) {
      throw twml::Error(TWML_ERR_TYPE,
                "input, twitters, ys should have the same number of labels.");
    }

    const uint64_t inputStride0 = input.getStride(0);
    const uint64_t inputStride1 = input.getStride(1);
    const uint64_t outputStride0 = output.getStride(0);
    const uint64_t outputStride1 = output.getStride(1);
    const uint64_t twittersStride0 = twitters.getStride(0);
    const uint64_t twittersStride1 = twitters.getStride(1);
    const uint64_t ysStride0 = ys.getStride(0);
    const uint64_t ysStride1 = ys.getStride(1);
    const uint64_t mainSize = twitters.getDim(1);

    // for each value in the input matritwitter, compute output value by
    // calling interpolation.
    auto inputData = input.getData<T>();
    auto outputData = output.getData<T>();
    auto twittersData = twitters.getData<T>();
    auto ysData = ys.getData<T>();

    for (uint64_t i = 0; i < input.getDim(0); i++) {
      for (uint64_t j = 0; j < input.getDim(1); j++) {
        const T val = inputData[i * inputStride0 + j * inputStride1];
        const T *ltwittersData = twittersData + j * twittersStride0;
        const T *lysData = ysData + j * ysStride0;
        const T res = interpolation(
          ltwittersData, twittersStride1,
          lysData, ysStride1,
          val,
          mainSize,
          mode,
          DEFAULT_INTERPOLATION_LOWEST);
        outputData[i * outputStride0 + j * outputStride1] = res;
      }
    }
  }

  void linearInterpolation(
    Tensor output,
    const Tensor input,
    const Tensor twitters,
    const Tensor ys) {
    switch (input.getType()) {
    case TWML_TYPE_FLOAT:
      twml::interpolation<float>(output, input, twitters, ys, LINEAR);
      break;
    case TWML_TYPE_DOUBLE:
      twml::interpolation<double>(output, input, twitters, ys, LINEAR);
      break;
    default:
      throw twml::Error(TWML_ERR_TYPE,
        "Unsupported datatype for linearInterpolation.");
    }
  }

  void nearestInterpolation(
    Tensor output,
    const Tensor input,
    const Tensor twitters,
    const Tensor ys) {
    switch (input.getType()) {
    case TWML_TYPE_FLOAT:
      twml::interpolation<float>(output, input, twitters, ys, NEAREST);
      break;
    case TWML_TYPE_DOUBLE:
      twml::interpolation<double>(output, input, twitters, ys, NEAREST);
      break;
    default:
      throw twml::Error(TWML_ERR_TYPE,
        "Unsupported datatype for nearestInterpolation.");
    }
  }
}  // namespace twml

twml_err twml_optim_mdl_infer(twml_tensor output_keys,
                twml_tensor output_vals,
                const twml_tensor input_keys,
                const twml_tensor input_vals,
                const twml_tensor bin_ids,
                const twml_tensor bin_vals,
                const twml_tensor feature_offsets,
                bool return_bin_indices) {
  HANDLE_EtwitterCEPTIONS(
    using namespace twml;
    mdlInfer(*getTensor(output_keys),
         *getTensor(output_vals),
         *getConstTensor(input_keys),
         *getConstTensor(input_vals),
         *getConstTensor(bin_ids),
         *getConstTensor(bin_vals),
         *getConstTensor(feature_offsets),
          return_bin_indices););
  return TWML_ERR_NONE;
}

twml_err twml_optim_nearest_interpolation(
                twml_tensor output,
                const twml_tensor input,
                const twml_tensor twitters,
                const twml_tensor ys) {
  HANDLE_EtwitterCEPTIONS(
    using namespace twml;
    nearestInterpolation(*getTensor(output),
      *getConstTensor(input),
      *getConstTensor(twitters),
      *getConstTensor(ys)););
  return TWML_ERR_NONE;
}
