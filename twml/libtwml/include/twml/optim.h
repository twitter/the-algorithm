#pragma once
#include <twml/defines.h>
#include <twml/Tensor.h>

#ifdef __cplusplus
namespace twml {
    TWMLAPI void linearInterpolation(
        Tensor output,
        const Tensor input,
        const Tensor xs,
        const Tensor ys);

    TWMLAPI void nearestInterpolation(
        Tensor output,
        const Tensor input,
        const Tensor xs,
        const Tensor ys);

    TWMLAPI void mdlInfer(
        Tensor &output_keys,
        Tensor &output_vals,
        const Tensor &input_keys,
        const Tensor &input_vals,
        const Tensor &bin_ids,
        const Tensor &bin_vals,
        const Tensor &feature_offsets,
        bool return_bin_indices = false);
}
#endif

#ifdef __cplusplus
extern "C" {
#endif
    TWMLAPI twml_err twml_optim_nearest_interpolation(
        twml_tensor output,
        const twml_tensor input,
        const twml_tensor xs,
        const twml_tensor ys);

    TWMLAPI twml_err twml_optim_mdl_infer(
        twml_tensor output_keys,
        twml_tensor output_vals,
        const twml_tensor input_keys,
        const twml_tensor input_vals,
        const twml_tensor bin_ids,
        const twml_tensor bin_vals,
        const twml_tensor feature_offsets,
        const bool return_bin_indices = false);
#ifdef __cplusplus
}
#endif
