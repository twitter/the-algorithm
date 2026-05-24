#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"

using namespace tensorflow;

REGISTER_OP("DataRecordTensorWriter")
.Attr("T: list({string, int32, int64, float, double, bool})")
.Input("keys: int64")
.Input("values: T")
.Output("result: uint8")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
  return Status::OK();
  }).Doc(R"doc(

A tensorflow OP that packages keys and dense tensors into a DataRecord.

values: list of tensors
keys: feature ids from the original DataRecord (int64)

Outputs
  bytes: output DataRecord serialized using Thrift into a uint8 tensor.
)doc");

class DataRecordTensorWriter : public OpKernel {
 public:
  explicit DataRecordTensorWriter(OpKernelConstruction* context)
  : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    const Tensor& keys = context->input(0);

    try {
      // set keys as twml::Tensor
      const twml::Tensor in_keys_ = TFTensor_to_twml_tensor(keys);

      // check sizes
      uint64_t num_keys = in_keys_.getNumElements();
      uint64_t num_values = context->num_inputs() - 1;

      OP_REQUIRES(context, num_keys == num_values,
        errors::InvalidArgument("Number of dense keys and dense tensors do not match"));

      // populate DataRecord object
      const int64_t *keys = in_keys_.getData<int64_t>();
      twml::DataRecord record = twml::DataRecord();

      for (int i = 1; i < context->num_inputs(); i++) {
        const twml::RawTensor& value = TFTensor_to_twml_raw_tensor(context->input(i));
        record.addRawTensor(keys[i-1], value);
      }

      // determine the length of the encoded result (no memory is copied)
      twml::ThriftWriter thrift_dry_writer = twml::ThriftWriter(nullptr, 0, true);
      twml::DataRecordWriter record_dry_writer = twml::DataRecordWriter(thrift_dry_writer);
      record_dry_writer.write(record);
      int len = thrift_dry_writer.getBytesWritten();
      TensorShape result_shape = {1, len};

      // allocate output tensor
      Tensor* result = NULL;
      OP_REQUIRES_OK(context, context->allocate_output(0, result_shape, &result));
      twml::Tensor out_result = TFTensor_to_twml_tensor(*result);

      // write to output tensor
      uint8_t *buffer = out_result.getData<uint8_t>();
      twml::ThriftWriter thrift_writer = twml::ThriftWriter(buffer, len, false);
      twml::DataRecordWriter record_writer = twml::DataRecordWriter(thrift_writer);
      record_writer.write(record);
    } catch(const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_KERNEL_BUILDER(
    Name("DataRecordTensorWriter").Device(DEVICE_CPU),
    DataRecordTensorWriter);
