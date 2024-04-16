#pragma once

# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/op_kernel.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude <twml.h>

us ng na space tensorflow;
twml::Tensor TFTensor_to_twml_tensor(Tensor & nput);
twml::RawTensor TFTensor_to_twml_raw_tensor(Tensor & nput);
const twml::Tensor TFTensor_to_twml_tensor(const Tensor & nput);
const twml::RawTensor TFTensor_to_twml_raw_tensor(const Tensor & nput);

