#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>

#include <mutex>

using namespace tensorflow;

REGISTER_OP("Hashmap")
.Input("keys: int64")
.Input("hash_keys: int64")
.Input("hash_values: int64")
.Output("values: int64")
.Output("mask: int8")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    // TODO: check if the sizes are different in the input
    c->set_output(0, c->input(0));
    c->set_output(1, c->input(0));
    return Status::OK();
  });


class Hashmap : public OpKernel {
 private:
  twml::HashMap hmap;
  std::once_flag flag;

 public:
  explicit Hashmap(OpKernelConstruction* context) : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    try {
      // Quick hack
      const Tensor& keys = context->input(0);

      std::call_once(this->flag, [this, context](){
          const Tensor& hash_keys = context->input(1);
          const Tensor& hash_values = context->input(2);
          const auto hash_keys_flat = hash_keys.flat<int64>();
          const auto hash_values_flat = hash_values.flat<int64>();
          const int64 N = hash_keys_flat.size();

          for (int64 i = 0; i < N; i++) {
            hmap.insert(hash_keys_flat(i), hash_values_flat(i));
          }
        });

      Tensor* values = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(0, keys.shape(),
                                                       &values));

      Tensor* mask = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(1, keys.shape(),
                                                       &mask));

      // copy the values without sharing a storage
      values->flat<int64>() = keys.flat<int64>();

      auto keys_flat = keys.flat<int64>();
      auto values_flat = values->flat<int64>();
      auto mask_flat = mask->flat<int8>();

      // TODO: use twml tensor
      const int64 N = keys_flat.size();
      for (int64 i = 0; i < N; i++) {
        // values_flat(i), keys_flat(i) return references to tensorflow::int64.
        // Using them in hmap.get() was causing issues because of automatic casting.
        int64_t val = values_flat(i);
        int64_t key = keys_flat(i);
        mask_flat(i) = hmap.get(val, key);
        values_flat(i) = val;
      }
    }  catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_KERNEL_BUILDER(
  Name("Hashmap")
  .Device(DEVICE_CPU),
  Hashmap);
