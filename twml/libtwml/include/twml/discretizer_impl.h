#pragma once
#include <twml/common.h>
#include <twml/defines.h>
#include <twml/Tensor.h>

#ifdef __cplusplus
namespace twml {
    TWMLAPI void discretizerInfer(
        Tensor &output_keys,
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
        int output_start);
}  // namespace twml
#endif
