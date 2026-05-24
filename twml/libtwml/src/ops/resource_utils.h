#pragma once

#include <twml.h>

#include <atomic>
#include <string>
#include <vector>

// Add these to make gcc ignore the warnings from tensorflow.
#pragma GCC diagnostic push
#pragma GCC diagnostic ignored "-Wsign-compare"

#include "tensorflow/core/framework/resource_mgr.h"
#include "tensorflow/core/framework/resource_op_kernel.h"

#pragma GCC diagnostic pop

#include <memory>
#include <functional>

template<typename T>
void unrefHandle(T *handle) {
  handle->Unref();
}

template <typename T>
using unique_handle = std::unique_ptr<T, std::function<void(T *)> >;

// as std::type_index is not abi compatible, we bypass the hash_code checks.
// https://github.com/tensorflow/tensorflow/commit/15275d3a14c77e2244ae1155f93243256f08e3ed
#ifdef __APPLE__
template <typename T>
Status CreateTwmlResource(OpKernelContext* ctx, const ResourceHandle& p, T* value) {
  return ctx->resource_manager()->Create(p.container(), p.name(), value);
}

template <typename T>
Status LookupTwmlResource(OpKernelContext* ctx, const ResourceHandle& p,
                      T** value) {
  return ctx->resource_manager()->Lookup(p.container(), p.name(), value);
}
#endif  // __APPLE__

template<typename T>
unique_handle<T> getHandle(tensorflow::OpKernelContext* context, int input_idx) {
  using namespace tensorflow;
  T *ptr = nullptr;
#ifdef __APPLE__
  auto s = LookupTwmlResource(context, HandleFromInput(context, input_idx), &ptr);
#else
  auto s = LookupResource(context, HandleFromInput(context, input_idx), &ptr);
#endif  // __APPLE__

  if (!s.ok()) {
    throw std::runtime_error("Failed to get resource handle");
  }
  return unique_handle<T>(ptr, unrefHandle<T>);
}

template<typename InputType>
const uint8_t *getInputBytes(const Tensor &input, int id) {
  return reinterpret_cast<const uint8_t *>(input.flat<InputType>().data());
}

template<>
inline const uint8_t *getInputBytes<string>(const Tensor &input, int id) {
  return reinterpret_cast<const uint8_t *>(input.flat<string>()(id).c_str());
}

template<typename InputType>
const int getBatchSize(const Tensor &input) {
  return 1;
}

template<>
inline const int getBatchSize<string>(const Tensor &input) {
  return static_cast<int>(input.NumElements());
}

class DataRecordResource : public ResourceBase {
 public:
  Tensor input;
  int64 num_labels;
  int64 num_weights;
  twml::DataRecord common;
  std::vector<twml::DataRecord> records;
  twml::Map<int64_t, int64_t> *keep_map;
  string DebugString() const override { return "DataRecords resource"; }
};

// A thin layer around batch of HashedDataRecords
class HashedDataRecordResource : public ResourceBase {
 public:
  Tensor input;
  int64 total_size;
  int64 num_labels;
  int64 num_weights;
  twml::HashedDataRecord common;
  std::vector<twml::HashedDataRecord> records;
  string DebugString() const override { return "HashedDataRecord Resource"; }
};

#define TF_CHECK_STATUS(fn) do {                \
    Status s = fn;                              \
    if (!s.ok()) return s;                      \
  } while (0)

template<typename ResourceType>
Status makeResourceHandle(OpKernelContext* context, int out_idx, ResourceType **resource_) {
  static std::atomic<int64> id;
  Tensor* handle_tensor;
  TF_CHECK_STATUS(context->allocate_output(out_idx, TensorShape({}), &handle_tensor));

  ResourceType *resource = new ResourceType();
  const auto resource_name = typeid(ResourceType).name() + std::to_string(id++);
  ResourceHandle handle = MakePerStepResourceHandle<ResourceType>(context, resource_name);
#ifdef __APPLE__
  TF_CHECK_STATUS(CreateTwmlResource(context, handle, resource));
#else
  TF_CHECK_STATUS(CreateResource(context, handle, resource));
#endif  // __APPLE__
  handle_tensor->scalar<ResourceHandle>()() = handle;

  *resource_ = resource;
  return Status::OK();
}
