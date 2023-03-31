#pragma once
#include <twml/defines.h>
#include <twml/Tensor.h>

#ifdef __cplusplus
namespace twml {

    // Adding these as an easy way to test the wrappers
    TWMLAPI void add1(Tensor &output, const Tensor input);
    TWMLAPI void copy(Tensor &output, const Tensor input);
    TWMLAPI int64_t featureId(const std::string &feature);
}
#endif

#ifdef __cplusplus
extern "C" {
#endif

    // Adding these as an easy way to test the wrappers
    TWMLAPI twml_err twml_add1(twml_tensor output, const twml_tensor input);
    TWMLAPI twml_err twml_copy(twml_tensor output, const twml_tensor input);
    TWMLAPI twml_err twml_get_feature_id(int64_t *result, const uint64_t len, const char *str);

#ifdef __cplusplus
}
#endif
