#pragma once

#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/op_kernel.h"
#include "tensorflow/core/framework/shape_inference.h"
#include <twml.h>

using namespace tensorflow;
twml::Tensor TFTensor_to_twml_tensor(Tensor &input);
twml::RawTensor TFTensor_to_twml_raw_tensor(Tensor &input);
const twml::Tensor TFTensor_to_twml_tensor(const Tensor &input);
const twml::RawTensor TFTensor_to_twml_raw_tensor(const Tensor &input);

