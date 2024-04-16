#pragma once
# nclude <twml/def nes.h>
# nclude <twml/Tensor.h>

# fdef __cplusplus
na space twml {
    TWMLAP  vo d l near nterpolat on(
        Tensor output,
        const Tensor  nput,
        const Tensor xs,
        const Tensor ys);

    TWMLAP  vo d nearest nterpolat on(
        Tensor output,
        const Tensor  nput,
        const Tensor xs,
        const Tensor ys);

    TWMLAP  vo d mdl nfer(
        Tensor &output_keys,
        Tensor &output_vals,
        const Tensor & nput_keys,
        const Tensor & nput_vals,
        const Tensor &b n_ ds,
        const Tensor &b n_vals,
        const Tensor &feature_offsets,
        bool return_b n_ nd ces = false);
}
#end f

# fdef __cplusplus
extern "C" {
#end f
    TWMLAP  twml_err twml_opt m_nearest_ nterpolat on(
        twml_tensor output,
        const twml_tensor  nput,
        const twml_tensor xs,
        const twml_tensor ys);

    TWMLAP  twml_err twml_opt m_mdl_ nfer(
        twml_tensor output_keys,
        twml_tensor output_vals,
        const twml_tensor  nput_keys,
        const twml_tensor  nput_vals,
        const twml_tensor b n_ ds,
        const twml_tensor b n_vals,
        const twml_tensor feature_offsets,
        const bool return_b n_ nd ces = false);
# fdef __cplusplus
}
#end f
