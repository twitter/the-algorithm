#pragma once
# nclude <twml/common.h>
# nclude <twml/def nes.h>
# nclude <twml/Tensor.h>

# fdef __cplusplus
na space twml {
    TWMLAP  vo d d scret zer nfer(
        Tensor &output_keys,
        Tensor &output_vals,
        const Tensor & nput_ ds,
        const Tensor & nput_vals,
        const Tensor &b n_ ds,
        const Tensor &b n_vals,
        const Tensor &feature_offsets,
         nt output_b s,
        const Map< nt64_t,  nt64_t> & D_to_ ndex,
         nt start_compute,
         nt end_compute,
         nt output_start);
}  // na space twml
#end f
