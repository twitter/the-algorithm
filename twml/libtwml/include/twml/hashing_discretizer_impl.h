#pragma once
#include <twml/common.h>
#include <twml/defines.h>
#include <twml/Tensor.h>
#include <unordered_map>

#ifdef __cplusplus
namespace twml {
    TWMLAPI void hashDiscretizerInfer(
        Tensor &output_keys,
        Tensor &output_vals,
        const Tensor &input_ids,
        const Tensor &input_vals,
        int n_bin,
        const Tensor &bin_vals,
        int output_bits,
        const Map<int64_t, int64_t> &ID_to_index,
        int start_compute,
        int end_compute,
        int64_t options);
}  // namespace twml
#endif
