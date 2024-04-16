#pragma once
# nclude <twml/def nes.h>
# nclude <twml/Tensor.h>

# fdef __cplusplus
na space twml {

    // Add ng t se as an easy way to test t  wrappers
    TWMLAP  vo d add1(Tensor &output, const Tensor  nput);
    TWMLAP  vo d copy(Tensor &output, const Tensor  nput);
    TWMLAP   nt64_t feature d(const std::str ng &feature);
}
#end f

# fdef __cplusplus
extern "C" {
#end f

    // Add ng t se as an easy way to test t  wrappers
    TWMLAP  twml_err twml_add1(twml_tensor output, const twml_tensor  nput);
    TWMLAP  twml_err twml_copy(twml_tensor output, const twml_tensor  nput);
    TWMLAP  twml_err twml_get_feature_ d( nt64_t *result, const u nt64_t len, const char *str);

# fdef __cplusplus
}
#end f
