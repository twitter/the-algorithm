// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

// Do not include this file directly. Please include "onnxruntime_cxx_api.h" instead.
// If interested in trying out features of the new experimental C++ API, include "experimental_onnxruntime_cxx_api.h" instead.
//
// These are the inline implementations of the C++ header APIs. They're in this separate file as to not clutter
// the main C++ file with implementation details.

namespace Ort {

namespace detail {
inline void ThrowStatus(const Status& st) {
  std::string error_message = st.GetErrorMessage();
  OrtErrorCode error_code = st.GetErrorCode();
  ORT_CXX_API_THROW(std::move(error_message), error_code);
}
}  // namespace detail

inline void ThrowOnError(OrtStatus* ort_status) {
  if (ort_status) {
    Ort::Status st(ort_status);
    detail::ThrowStatus(st);
  }
}

inline void ThrowOnError(const Status& st) {
  if (st) {
    detail::ThrowStatus(st);
  }
}

inline Status::Status(OrtStatus* status) : Base<OrtStatus>{status} {
}

inline Status::Status(const std::exception& e) {
  p_ = GetApi().CreateStatus(ORT_FAIL, e.what());
}

inline Status::Status(const Exception& e) {
  p_ = GetApi().CreateStatus(e.GetOrtErrorCode(), e.what());
}

inline std::string Status::GetErrorMessage() const {
  std::string message(GetApi().GetErrorMessage(p_));
  return message;
}

inline OrtErrorCode Status::GetErrorCode() const {
  return GetApi().GetErrorCode(p_);
}

// This template converts a C++ type into it's ONNXTensorElementDataType
template <typename T>
struct TypeToTensorType;
template <>
struct TypeToTensorType<float> {
  static constexpr ONNXTensorElementDataType type = ONNX_TENSOR_ELEMENT_DATA_TYPE_FLOAT;
};
template <>
struct TypeToTensorType<Float16_t> {
  static constexpr ONNXTensorElementDataType type = ONNX_TENSOR_ELEMENT_DATA_TYPE_FLOAT16;
};
template <>
struct TypeToTensorType<BFloat16_t> {
  static constexpr ONNXTensorElementDataType type = ONNX_TENSOR_ELEMENT_DATA_TYPE_BFLOAT16;
};
template <>
struct TypeToTensorType<double> {
  static constexpr ONNXTensorElementDataType type = ONNX_TENSOR_ELEMENT_DATA_TYPE_DOUBLE;
};
template <>
struct TypeToTensorType<int8_t> {
  static constexpr ONNXTensorElementDataType type = ONNX_TENSOR_ELEMENT_DATA_TYPE_INT8;
};
template <>
struct TypeToTensorType<int16_t> {
  static constexpr ONNXTensorElementDataType type = ONNX_TENSOR_ELEMENT_DATA_TYPE_INT16;
};
template <>
struct TypeToTensorType<int32_t> {
  static constexpr ONNXTensorElementDataType type = ONNX_TENSOR_ELEMENT_DATA_TYPE_INT32;
};
template <>
struct TypeToTensorType<int64_t> {
  static constexpr ONNXTensorElementDataType type = ONNX_TENSOR_ELEMENT_DATA_TYPE_INT64;
};
template <>
struct TypeToTensorType<uint8_t> {
  static constexpr ONNXTensorElementDataType type = ONNX_TENSOR_ELEMENT_DATA_TYPE_UINT8;
};
template <>
struct TypeToTensorType<uint16_t> {
  static constexpr ONNXTensorElementDataType type = ONNX_TENSOR_ELEMENT_DATA_TYPE_UINT16;
};
template <>
struct TypeToTensorType<uint32_t> {
  static constexpr ONNXTensorElementDataType type = ONNX_TENSOR_ELEMENT_DATA_TYPE_UINT32;
};
template <>
struct TypeToTensorType<uint64_t> {
  static constexpr ONNXTensorElementDataType type = ONNX_TENSOR_ELEMENT_DATA_TYPE_UINT64;
};
template <>
struct TypeToTensorType<bool> {
  static constexpr ONNXTensorElementDataType type = ONNX_TENSOR_ELEMENT_DATA_TYPE_BOOL;
};

inline MemoryAllocation::MemoryAllocation(OrtAllocator* allocator, void* p, size_t size)
    : allocator_(allocator), p_(p), size_(size) {
}

inline MemoryAllocation::~MemoryAllocation() {
  if (p_ != nullptr) {
    // We do not throw out of destructor
    auto ret = GetApi().AllocatorFree(allocator_, p_);
    static_cast<void>(ret);
  }
}

inline MemoryAllocation::MemoryAllocation(MemoryAllocation&& o) noexcept : allocator_(nullptr), p_(nullptr), size_(0) {
  *this = std::move(o);
}

inline MemoryAllocation& MemoryAllocation::operator=(MemoryAllocation&& o) noexcept {
  OrtAllocator* alloc = nullptr;
  void* p = nullptr;
  size_t sz = 0;

  // Swap out this
  std::swap(alloc, allocator_);
  std::swap(p, p_);
  std::swap(sz, size_);

  // Swap with incoming
  std::swap(allocator_, o.allocator_);
  std::swap(p_, o.p_);
  std::swap(size_, o.size_);

  // Destroy this instance if needed
  MemoryAllocation this_alloc(alloc, p, sz);
  return *this;
}

namespace detail {

template <typename T>
inline void* AllocatorImpl<T>::Alloc(size_t size) {
  void* out;
  ThrowOnError(GetApi().AllocatorAlloc(this->p_, size, &out));
  return out;
}

template <typename T>
inline MemoryAllocation AllocatorImpl<T>::GetAllocation(size_t size) {
  void* out;
  ThrowOnError(GetApi().AllocatorAlloc(this->p_, size, &out));
  MemoryAllocation result(this->p_, out, size);
  return result;
}

template <typename T>
inline void AllocatorImpl<T>::Free(void* p) {
  ThrowOnError(GetApi().AllocatorFree(this->p_, p));
}

template <typename T>
inline ConstMemoryInfo AllocatorImpl<T>::GetInfo() const {
  const OrtMemoryInfo* out;
  ThrowOnError(GetApi().AllocatorGetInfo(this->p_, &out));
  return ConstMemoryInfo{out};
}

}  // namespace detail

inline AllocatorWithDefaultOptions::AllocatorWithDefaultOptions() {
  ThrowOnError(GetApi().GetAllocatorWithDefaultOptions(&this->p_));
}

inline Allocator::Allocator(const Session& sess, const OrtMemoryInfo* mem_info) {
  ThrowOnError(GetApi().CreateAllocator(sess, mem_info, &this->p_));
}

namespace detail {

template <typename T>
inline std::string MemoryInfoImpl<T>::GetAllocatorName() const {
  const char* name = nullptr;
  ThrowOnError(GetApi().MemoryInfoGetName(this->p_, &name));
  return std::string(name);
}

template <typename T>
inline OrtAllocatorType MemoryInfoImpl<T>::GetAllocatorType() const {
  OrtAllocatorType type;
  ThrowOnError(GetApi().MemoryInfoGetType(this->p_, &type));
  return type;
}

template <typename T>
inline int MemoryInfoImpl<T>::GetDeviceId() const {
  int id = 0;
  ThrowOnError(GetApi().MemoryInfoGetId(this->p_, &id));
  return id;
}

template <typename T>
inline OrtMemoryInfoDeviceType MemoryInfoImpl<T>::GetDeviceType() const {
  OrtMemoryInfoDeviceType type;
  GetApi().MemoryInfoGetDeviceType(this->p_, &type);
  return type;
}

template <typename T>
inline OrtMemType MemoryInfoImpl<T>::GetMemoryType() const {
  OrtMemType type;
  ThrowOnError(GetApi().MemoryInfoGetMemType(this->p_, &type));
  return type;
}

template <typename T>
template <typename U>
inline bool MemoryInfoImpl<T>::operator==(const MemoryInfoImpl<U>& o) const {
  int comp_result = 0;
  ThrowOnError(Ort::GetApi().CompareMemoryInfo(this->p_, o, &comp_result));
  return comp_result == 0;
}

}  // namespace detail

inline MemoryInfo MemoryInfo::CreateCpu(OrtAllocatorType type, OrtMemType mem_type) {
  OrtMemoryInfo* p;
  ThrowOnError(GetApi().CreateCpuMemoryInfo(type, mem_type, &p));
  return MemoryInfo(p);
}

inline MemoryInfo::MemoryInfo(const char* name, OrtAllocatorType type, int id, OrtMemType mem_type) {
  ThrowOnError(GetApi().CreateMemoryInfo(name, type, id, mem_type, &this->p_));
}

namespace detail {
template <typename T>
inline std::vector<std::string> ConstIoBindingImpl<T>::GetOutputNames() const {
  AllocatorWithDefaultOptions allocator;
  return binding_utils::GetOutputNamesHelper(this->p_, allocator);
}

template <typename T>
inline std::vector<std::string> ConstIoBindingImpl<T>::GetOutputNames(OrtAllocator* allocator) const {
  return binding_utils::GetOutputNamesHelper(this->p_, allocator);
}

template <typename T>
inline std::vector<Value> ConstIoBindingImpl<T>::GetOutputValues() const {
  AllocatorWithDefaultOptions allocator;
  return binding_utils::GetOutputValuesHelper(this->p_, allocator);
}

template <typename T>
inline std::vector<Value> ConstIoBindingImpl<T>::GetOutputValues(OrtAllocator* allocator) const {
  return binding_utils::GetOutputValuesHelper(this->p_, allocator);
}

template <typename T>
inline void IoBindingImpl<T>::BindInput(const char* name, const Value& value) {
  ThrowOnError(GetApi().BindInput(this->p_, name, value));
}

template <typename T>
inline void IoBindingImpl<T>::BindOutput(const char* name, const Value& value) {
  ThrowOnError(GetApi().BindOutput(this->p_, name, value));
}

template <typename T>
inline void IoBindingImpl<T>::BindOutput(const char* name, const OrtMemoryInfo* mem_info) {
  ThrowOnError(GetApi().BindOutputToDevice(this->p_, name, mem_info));
}

template <typename T>
inline void IoBindingImpl<T>::ClearBoundInputs() {
  GetApi().ClearBoundInputs(this->p_);
}

template <typename T>
inline void IoBindingImpl<T>::ClearBoundOutputs() {
  GetApi().ClearBoundOutputs(this->p_);
}

template <typename T>
inline void IoBindingImpl<T>::SynchronizeInputs() {
  ThrowOnError(GetApi().SynchronizeBoundInputs(this->p_));
}

template <typename T>
inline void IoBindingImpl<T>::SynchronizeOutputs() {
  ThrowOnError(GetApi().SynchronizeBoundOutputs(this->p_));
}

namespace binding_utils {
inline std::vector<std::string> GetOutputNamesHelper(const OrtIoBinding* binding, OrtAllocator* allocator) {
  std::vector<std::string> result;
  auto free_fn = detail::AllocatedFree(allocator);
  using Ptr = std::unique_ptr<void, decltype(free_fn)>;

  char* buffer = nullptr;
  size_t* lengths = nullptr;
  size_t count = 0;
  ThrowOnError(GetApi().GetBoundOutputNames(binding, allocator, &buffer, &lengths, &count));

  if (count == 0) {
    return result;
  }

  Ptr buffer_g(buffer, free_fn);
  Ptr lengths_g(lengths, free_fn);

  result.reserve(count);
  for (size_t i = 0; i < count; ++i) {
    auto sz = *lengths;
    result.emplace_back(buffer, sz);
    buffer += sz;
    ++lengths;
  }
  return result;
}

inline std::vector<Value> GetOutputValuesHelper(const OrtIoBinding* binding, OrtAllocator* allocator) {
  std::vector<Value> result;
  size_t owned = 0;
  size_t output_count = 0;
  // Lambda to release the buffer when no longer needed and
  // make sure that we destroy all instances on exception
  auto free_fn = [&owned, &output_count, allocator](OrtValue** buffer) {
    if (buffer) {
      while (owned < output_count) {
        auto* p = buffer + owned++;
        GetApi().ReleaseValue(*p);
      }
      allocator->Free(allocator, buffer);
    }
  };
  using Ptr = std::unique_ptr<OrtValue*, decltype(free_fn)>;

  OrtValue** output_buffer = nullptr;
  ThrowOnError(GetApi().GetBoundOutputValues(binding, allocator, &output_buffer, &output_count));
  if (output_count == 0) {
    return result;
  }

  Ptr buffer_g(output_buffer, free_fn);

  result.reserve(output_count);
  for (size_t i = 0; i < output_count; ++i) {
    result.emplace_back(output_buffer[i]);
    ++owned;
  }
  return result;
}

}  // namespace binding_utils
}  // namespace detail

inline IoBinding::IoBinding(Session& session) {
  ThrowOnError(GetApi().CreateIoBinding(session, &this->p_));
}

inline ArenaCfg::ArenaCfg(size_t max_mem, int arena_extend_strategy, int initial_chunk_size_bytes, int max_dead_bytes_per_chunk) {
  ThrowOnError(GetApi().CreateArenaCfg(max_mem, arena_extend_strategy, initial_chunk_size_bytes, max_dead_bytes_per_chunk, &p_));
}

inline ThreadingOptions::ThreadingOptions() {
  ThrowOnError(GetApi().CreateThreadingOptions(&p_));
}

inline ThreadingOptions& ThreadingOptions::SetGlobalIntraOpNumThreads(int intra_op_num_threads) {
  ThrowOnError(GetApi().SetGlobalIntraOpNumThreads(p_, intra_op_num_threads));
  return *this;
}

inline ThreadingOptions& ThreadingOptions::SetGlobalInterOpNumThreads(int inter_op_num_threads) {
  ThrowOnError(GetApi().SetGlobalInterOpNumThreads(p_, inter_op_num_threads));
  return *this;
}

inline ThreadingOptions& ThreadingOptions::SetGlobalSpinControl(int allow_spinning) {
  ThrowOnError(GetApi().SetGlobalSpinControl(p_, allow_spinning));
  return *this;
}

inline ThreadingOptions& ThreadingOptions::SetGlobalDenormalAsZero() {
  ThrowOnError(GetApi().SetGlobalDenormalAsZero(p_));
  return *this;
}

inline ThreadingOptions& ThreadingOptions::SetGlobalCustomCreateThreadFn(OrtCustomCreateThreadFn ort_custom_create_thread_fn) {
  ThrowOnError(GetApi().SetGlobalCustomCreateThreadFn(p_, ort_custom_create_thread_fn));
  return *this;
}

inline ThreadingOptions& ThreadingOptions::SetGlobalCustomThreadCreationOptions(void* ort_custom_thread_creation_options) {
  ThrowOnError(GetApi().SetGlobalCustomThreadCreationOptions(p_, ort_custom_thread_creation_options));
  return *this;
}

inline ThreadingOptions& ThreadingOptions::SetGlobalCustomJoinThreadFn(OrtCustomJoinThreadFn ort_custom_join_thread_fn) {
  ThrowOnError(GetApi().SetGlobalCustomJoinThreadFn(p_, ort_custom_join_thread_fn));
  return *this;
}

inline Env::Env(OrtLoggingLevel logging_level, _In_ const char* logid) {
  ThrowOnError(GetApi().CreateEnv(logging_level, logid, &p_));
  if (strcmp(logid, "onnxruntime-node") == 0) {
    ThrowOnError(GetApi().SetLanguageProjection(p_, OrtLanguageProjection::ORT_PROJECTION_NODEJS));
  } else {
    ThrowOnError(GetApi().SetLanguageProjection(p_, OrtLanguageProjection::ORT_PROJECTION_CPLUSPLUS));
  }
}

inline Env::Env(OrtLoggingLevel logging_level, const char* logid, OrtLoggingFunction logging_function, void* logger_param) {
  ThrowOnError(GetApi().CreateEnvWithCustomLogger(logging_function, logger_param, logging_level, logid, &p_));
  if (strcmp(logid, "onnxruntime-node") == 0) {
    ThrowOnError(GetApi().SetLanguageProjection(p_, OrtLanguageProjection::ORT_PROJECTION_NODEJS));
  } else {
    ThrowOnError(GetApi().SetLanguageProjection(p_, OrtLanguageProjection::ORT_PROJECTION_CPLUSPLUS));
  }
}

inline Env::Env(const OrtThreadingOptions* tp_options, OrtLoggingLevel logging_level, _In_ const char* logid) {
  ThrowOnError(GetApi().CreateEnvWithGlobalThreadPools(logging_level, logid, tp_options, &p_));
  if (strcmp(logid, "onnxruntime-node") == 0) {
    ThrowOnError(GetApi().SetLanguageProjection(p_, OrtLanguageProjection::ORT_PROJECTION_NODEJS));
  } else {
    ThrowOnError(GetApi().SetLanguageProjection(p_, OrtLanguageProjection::ORT_PROJECTION_CPLUSPLUS));
  }
}

inline Env::Env(const OrtThreadingOptions* tp_options, OrtLoggingFunction logging_function, void* logger_param,
                OrtLoggingLevel logging_level, _In_ const char* logid) {
  ThrowOnError(GetApi().CreateEnvWithCustomLoggerAndGlobalThreadPools(logging_function, logger_param, logging_level, logid, tp_options, &p_));
  if (strcmp(logid, "onnxruntime-node") == 0) {
    ThrowOnError(GetApi().SetLanguageProjection(p_, OrtLanguageProjection::ORT_PROJECTION_NODEJS));
  } else {
    ThrowOnError(GetApi().SetLanguageProjection(p_, OrtLanguageProjection::ORT_PROJECTION_CPLUSPLUS));
  }
}

inline Env& Env::EnableTelemetryEvents() {
  ThrowOnError(GetApi().EnableTelemetryEvents(p_));
  return *this;
}

inline Env& Env::DisableTelemetryEvents() {
  ThrowOnError(GetApi().DisableTelemetryEvents(p_));
  return *this;
}

inline Env& Env::UpdateEnvWithCustomLogLevel(OrtLoggingLevel log_severity_level) {
  ThrowOnError(GetApi().UpdateEnvWithCustomLogLevel(p_, log_severity_level));
  return *this;
}

inline Env& Env::CreateAndRegisterAllocator(const OrtMemoryInfo* mem_info, const OrtArenaCfg* arena_cfg) {
  ThrowOnError(GetApi().CreateAndRegisterAllocator(p_, mem_info, arena_cfg));
  return *this;
}

inline CustomOpDomain::CustomOpDomain(const char* domain) {
  ThrowOnError(GetApi().CreateCustomOpDomain(domain, &p_));
}

inline void CustomOpDomain::Add(const OrtCustomOp* op) {
  ThrowOnError(GetApi().CustomOpDomain_Add(p_, op));
}

inline RunOptions::RunOptions() {
  ThrowOnError(GetApi().CreateRunOptions(&p_));
}

inline RunOptions& RunOptions::SetRunLogVerbosityLevel(int level) {
  ThrowOnError(GetApi().RunOptionsSetRunLogVerbosityLevel(p_, level));
  return *this;
}

inline RunOptions& RunOptions::SetRunLogSeverityLevel(int level) {
  ThrowOnError(GetApi().RunOptionsSetRunLogSeverityLevel(p_, level));
  return *this;
}

inline int RunOptions::GetRunLogVerbosityLevel() const {
  int out;
  ThrowOnError(GetApi().RunOptionsGetRunLogVerbosityLevel(p_, &out));
  return out;
}

inline int RunOptions::GetRunLogSeverityLevel() const {
  int out;
  ThrowOnError(GetApi().RunOptionsGetRunLogSeverityLevel(p_, &out));
  return out;
}

inline RunOptions& RunOptions::SetRunTag(const char* run_tag) {
  ThrowOnError(GetApi().RunOptionsSetRunTag(p_, run_tag));
  return *this;
}

inline const char* RunOptions::GetRunTag() const {
  const char* out;
  ThrowOnError(GetApi().RunOptionsGetRunTag(p_, &out));
  return out;
}

inline RunOptions& RunOptions::AddConfigEntry(const char* config_key, const char* config_value) {
  ThrowOnError(GetApi().AddRunConfigEntry(p_, config_key, config_value));
  return *this;
}

inline RunOptions& RunOptions::SetTerminate() {
  ThrowOnError(GetApi().RunOptionsSetTerminate(p_));
  return *this;
}

inline RunOptions& RunOptions::UnsetTerminate() {
  ThrowOnError(GetApi().RunOptionsUnsetTerminate(p_));
  return *this;
}

namespace detail {

template <typename T>
inline Ort::SessionOptions ConstSessionOptionsImpl<T>::Clone() const {
  OrtSessionOptions* out;
  ThrowOnError(GetApi().CloneSessionOptions(this->p_, &out));
  return SessionOptions{out};
}

template <typename T>
inline std::string ConstSessionOptionsImpl<T>::GetConfigEntry(const char* config_key) const {
  size_t size = 0;
  // Feed nullptr for the data buffer to query the true size of the string value
  Ort::ThrowOnError(GetApi().GetSessionConfigEntry(this->p_, config_key, nullptr, &size));

  std::string out;
  out.resize(size);
  Ort::ThrowOnError(GetApi().GetSessionConfigEntry(this->p_, config_key, &out[0], &size));
  out.resize(size - 1);  // remove the terminating character '\0'

  return out;
}

template <typename T>
inline bool ConstSessionOptionsImpl<T>::HasConfigEntry(const char* config_key) const {
  int out = 0;
  Ort::ThrowOnError(GetApi().HasSessionConfigEntry(this->p_, config_key, &out));
  return static_cast<bool>(out);
}

template <typename T>
inline std::string ConstSessionOptionsImpl<T>::GetConfigEntryOrDefault(const char* config_key, const std::string& def) {
  if (!this->HasConfigEntry(config_key)) {
    return def;
  }

  return this->GetConfigEntry(config_key);
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::SetIntraOpNumThreads(int intra_op_num_threads) {
  ThrowOnError(GetApi().SetIntraOpNumThreads(this->p_, intra_op_num_threads));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::SetInterOpNumThreads(int inter_op_num_threads) {
  ThrowOnError(GetApi().SetInterOpNumThreads(this->p_, inter_op_num_threads));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::SetGraphOptimizationLevel(GraphOptimizationLevel graph_optimization_level) {
  ThrowOnError(GetApi().SetSessionGraphOptimizationLevel(this->p_, graph_optimization_level));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::SetOptimizedModelFilePath(const ORTCHAR_T* optimized_model_filepath) {
  ThrowOnError(GetApi().SetOptimizedModelFilePath(this->p_, optimized_model_filepath));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::EnableProfiling(const ORTCHAR_T* profile_file_prefix) {
  ThrowOnError(GetApi().EnableProfiling(this->p_, profile_file_prefix));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::DisableProfiling() {
  ThrowOnError(GetApi().DisableProfiling(this->p_));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::EnableOrtCustomOps() {
  ThrowOnError(GetApi().EnableOrtCustomOps(this->p_));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::EnableMemPattern() {
  ThrowOnError(GetApi().EnableMemPattern(this->p_));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::DisableMemPattern() {
  ThrowOnError(GetApi().DisableMemPattern(this->p_));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::EnableCpuMemArena() {
  ThrowOnError(GetApi().EnableCpuMemArena(this->p_));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::DisableCpuMemArena() {
  ThrowOnError(GetApi().DisableCpuMemArena(this->p_));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::SetExecutionMode(ExecutionMode execution_mode) {
  ThrowOnError(GetApi().SetSessionExecutionMode(this->p_, execution_mode));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::SetLogId(const char* logid) {
  ThrowOnError(GetApi().SetSessionLogId(this->p_, logid));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::SetLogSeverityLevel(int level) {
  ThrowOnError(GetApi().SetSessionLogSeverityLevel(this->p_, level));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::Add(OrtCustomOpDomain* custom_op_domain) {
  ThrowOnError(GetApi().AddCustomOpDomain(this->p_, custom_op_domain));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::AddConfigEntry(const char* config_key, const char* config_value) {
  ThrowOnError(GetApi().AddSessionConfigEntry(this->p_, config_key, config_value));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::AddInitializer(const char* name, const OrtValue* ort_val) {
  ThrowOnError(GetApi().AddInitializer(this->p_, name, ort_val));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::DisablePerSessionThreads() {
  ThrowOnError(GetApi().DisablePerSessionThreads(this->p_));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::AddExternalInitializers(const std::vector<std::string>& names,
                                                                             const std::vector<Value>& ort_values) {
  const size_t inputs_num = names.size();
  if (inputs_num != ort_values.size()) {
    ORT_CXX_API_THROW("Expecting names and ort_values to have the same length", ORT_INVALID_ARGUMENT);
  }
  std::vector<const char*> names_ptr;
  std::vector<const OrtValue*> ort_values_ptrs;
  names_ptr.reserve(inputs_num);
  ort_values_ptrs.reserve(inputs_num);
  for (size_t i = 0; i < inputs_num; ++i) {
    names_ptr.push_back(names[i].c_str());
    ort_values_ptrs.push_back(ort_values[i]);
  }
  ThrowOnError(GetApi().AddExternalInitializers(this->p_, names_ptr.data(), ort_values_ptrs.data(), inputs_num));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::AppendExecutionProvider_CUDA(const OrtCUDAProviderOptions& provider_options) {
  ThrowOnError(GetApi().SessionOptionsAppendExecutionProvider_CUDA(this->p_, &provider_options));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::AppendExecutionProvider_CUDA_V2(const OrtCUDAProviderOptionsV2& provider_options) {
  ThrowOnError(GetApi().SessionOptionsAppendExecutionProvider_CUDA_V2(this->p_, &provider_options));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::AppendExecutionProvider_ROCM(const OrtROCMProviderOptions& provider_options) {
  ThrowOnError(GetApi().SessionOptionsAppendExecutionProvider_ROCM(this->p_, &provider_options));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::AppendExecutionProvider_TensorRT(const OrtTensorRTProviderOptions& provider_options) {
  ThrowOnError(GetApi().SessionOptionsAppendExecutionProvider_TensorRT(this->p_, &provider_options));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::AppendExecutionProvider_TensorRT_V2(const OrtTensorRTProviderOptionsV2& provider_options) {
  ThrowOnError(GetApi().SessionOptionsAppendExecutionProvider_TensorRT_V2(this->p_, &provider_options));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::AppendExecutionProvider_MIGraphX(const OrtMIGraphXProviderOptions& provider_options) {
  ThrowOnError(GetApi().SessionOptionsAppendExecutionProvider_MIGraphX(this->p_, &provider_options));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::AppendExecutionProvider_CANN(const OrtCANNProviderOptions& provider_options) {
  ThrowOnError(GetApi().SessionOptionsAppendExecutionProvider_CANN(this->p_, &provider_options));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::AppendExecutionProvider(
    const std::string& provider_name,
    const std::unordered_map<std::string, std::string>& provider_options) {
  auto num_entries = provider_options.size();
  std::vector<const char*> keys, values;
  if (num_entries > 0) {
    keys.reserve(num_entries);
    values.reserve(num_entries);

    for (const auto& entry : provider_options) {
      keys.push_back(entry.first.c_str());
      values.push_back(entry.second.c_str());
    }
  }

  ThrowOnError(GetApi().SessionOptionsAppendExecutionProvider(this->p_, provider_name.c_str(),
                                                              keys.data(), values.data(), num_entries));

  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::SetCustomCreateThreadFn(OrtCustomCreateThreadFn ort_custom_create_thread_fn) {
  ThrowOnError(GetApi().SessionOptionsSetCustomCreateThreadFn(this->p_, ort_custom_create_thread_fn));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::SetCustomThreadCreationOptions(void* ort_custom_thread_creation_options) {
  ThrowOnError(GetApi().SessionOptionsSetCustomThreadCreationOptions(this->p_, ort_custom_thread_creation_options));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::SetCustomJoinThreadFn(OrtCustomJoinThreadFn ort_custom_join_thread_fn) {
  ThrowOnError(GetApi().SessionOptionsSetCustomJoinThreadFn(this->p_, ort_custom_join_thread_fn));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::AppendExecutionProvider_OpenVINO(const OrtOpenVINOProviderOptions& provider_options) {
  ThrowOnError(GetApi().SessionOptionsAppendExecutionProvider_OpenVINO(this->p_, &provider_options));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::RegisterCustomOpsLibrary(const ORTCHAR_T* library_name,
                                                                              const CustomOpConfigs& custom_op_configs) {
  // Add custom op config entries before registering the custom op library. Otherwise, the config entries _may_ be ignored by
  // the custom op library.
  for (const auto& config_iter : custom_op_configs.GetFlattenedConfigs()) {
    AddConfigEntry(config_iter.first.c_str(), config_iter.second.c_str());
  }

  ThrowOnError(GetApi().RegisterCustomOpsLibrary_V2(this->p_, library_name));
  return *this;
}

template <typename T>
inline SessionOptionsImpl<T>& SessionOptionsImpl<T>::RegisterCustomOpsUsingFunction(const char* registration_function_name) {
  ThrowOnError(GetApi().RegisterCustomOpsUsingFunction(this->p_, registration_function_name));
  return *this;
}

/// Session
template <typename T>
inline size_t ConstSessionImpl<T>::GetInputCount() const {
  size_t out;
  ThrowOnError(GetApi().SessionGetInputCount(this->p_, &out));
  return out;
}

template <typename T>
inline size_t ConstSessionImpl<T>::GetOutputCount() const {
  size_t out;
  ThrowOnError(GetApi().SessionGetOutputCount(this->p_, &out));
  return out;
}

template <typename T>
inline size_t ConstSessionImpl<T>::GetOverridableInitializerCount() const {
  size_t out;
  ThrowOnError(GetApi().SessionGetOverridableInitializerCount(this->p_, &out));
  return out;
}

template <typename T>
inline AllocatedStringPtr ConstSessionImpl<T>::GetInputNameAllocated(size_t index, OrtAllocator* allocator) const {
  char* out;
  ThrowOnError(GetApi().SessionGetInputName(this->p_, index, allocator, &out));
  return AllocatedStringPtr(out, detail::AllocatedFree(allocator));
}

template <typename T>
inline AllocatedStringPtr ConstSessionImpl<T>::GetOutputNameAllocated(size_t index, OrtAllocator* allocator) const {
  char* out;
  ThrowOnError(GetApi().SessionGetOutputName(this->p_, index, allocator, &out));
  return AllocatedStringPtr(out, detail::AllocatedFree(allocator));
}

template <typename T>
inline AllocatedStringPtr ConstSessionImpl<T>::GetOverridableInitializerNameAllocated(size_t index, OrtAllocator* allocator) const {
  char* out;
  ThrowOnError(GetApi().SessionGetOverridableInitializerName(this->p_, index, allocator, &out));
  return AllocatedStringPtr(out, detail::AllocatedFree(allocator));
}

template <typename T>
inline uint64_t ConstSessionImpl<T>::GetProfilingStartTimeNs() const {
  uint64_t out;
  ThrowOnError(GetApi().SessionGetProfilingStartTimeNs(this->p_, &out));
  return out;
}

template <typename T>
inline ModelMetadata ConstSessionImpl<T>::GetModelMetadata() const {
  OrtModelMetadata* out;
  ThrowOnError(GetApi().SessionGetModelMetadata(this->p_, &out));
  return ModelMetadata{out};
}

template <typename T>
inline TypeInfo ConstSessionImpl<T>::GetInputTypeInfo(size_t index) const {
  OrtTypeInfo* out;
  ThrowOnError(GetApi().SessionGetInputTypeInfo(this->p_, index, &out));
  return TypeInfo{out};
}

template <typename T>
inline TypeInfo ConstSessionImpl<T>::GetOutputTypeInfo(size_t index) const {
  OrtTypeInfo* out;
  ThrowOnError(GetApi().SessionGetOutputTypeInfo(this->p_, index, &out));
  return TypeInfo{out};
}

template <typename T>
inline TypeInfo ConstSessionImpl<T>::GetOverridableInitializerTypeInfo(size_t index) const {
  OrtTypeInfo* out;
  ThrowOnError(GetApi().SessionGetOverridableInitializerTypeInfo(this->p_, index, &out));
  return TypeInfo{out};
}

template <typename T>
inline std::vector<Value> SessionImpl<T>::Run(const RunOptions& run_options, const char* const* input_names, const Value* input_values, size_t input_count,
                                              const char* const* output_names, size_t output_count) {
  std::vector<Value> output_values;
  output_values.reserve(output_count);
  for (size_t i = 0; i < output_count; i++)
    output_values.emplace_back(nullptr);
  Run(run_options, input_names, input_values, input_count, output_names, output_values.data(), output_count);
  return output_values;
}

template <typename T>
inline void SessionImpl<T>::Run(const RunOptions& run_options, const char* const* input_names, const Value* input_values, size_t input_count,
                                const char* const* output_names, Value* output_values, size_t output_count) {
  static_assert(sizeof(Value) == sizeof(OrtValue*), "Value is really just an array of OrtValue* in memory, so we can reinterpret_cast safely");
  auto ort_input_values = reinterpret_cast<const OrtValue* const*>(input_values);
  auto ort_output_values = reinterpret_cast<OrtValue**>(output_values);
  ThrowOnError(GetApi().Run(this->p_, run_options, input_names, ort_input_values, input_count, output_names, output_count, ort_output_values));
}

template <typename T>
inline void SessionImpl<T>::Run(const RunOptions& run_options, const IoBinding& io_binding) {
  ThrowOnError(GetApi().RunWithBinding(this->p_, run_options, io_binding));
}

template <typename T>
inline AllocatedStringPtr SessionImpl<T>::EndProfilingAllocated(OrtAllocator* allocator) {
  char* out = nullptr;
  ThrowOnError(GetApi().SessionEndProfiling(this->p_, allocator, &out));
  return AllocatedStringPtr(out, detail::AllocatedFree(allocator));
}

}  // namespace detail

inline SessionOptions::SessionOptions() {
  ThrowOnError(GetApi().CreateSessionOptions(&this->p_));
}

/// CustomOpConfigs
inline std::string detail::MakeCustomOpConfigEntryKey(const char* custom_op_name, const char* config) {
  std::string config_key = "custom_op.";

  config_key += custom_op_name;
  config_key += ".";
  config_key += config;

  return config_key;
}

inline CustomOpConfigs& CustomOpConfigs::AddConfig(const char* custom_op_name, const char* config_key, const char* config_value) {
  const std::string full_flat_key = detail::MakeCustomOpConfigEntryKey(custom_op_name, config_key);
  flat_configs_[full_flat_key] = config_value;
  return *this;
}

inline const std::unordered_map<std::string, std::string>& CustomOpConfigs::GetFlattenedConfigs() const {
  return flat_configs_;
}

inline Session::Session(const Env& env, const ORTCHAR_T* model_path, const SessionOptions& options) {
  ThrowOnError(GetApi().CreateSession(env, model_path, options, &this->p_));
}

inline Session::Session(const Env& env, const ORTCHAR_T* model_path, const SessionOptions& options,
                        OrtPrepackedWeightsContainer* prepacked_weights_container) {
  ThrowOnError(GetApi().CreateSessionWithPrepackedWeightsContainer(env, model_path, options, prepacked_weights_container, &this->p_));
}

inline Session::Session(const Env& env, const void* model_data, size_t model_data_length, const SessionOptions& options) {
  ThrowOnError(GetApi().CreateSessionFromArray(env, model_data, model_data_length, options, &this->p_));
}

inline Session::Session(const Env& env, const void* model_data, size_t model_data_length,
                        const SessionOptions& options, OrtPrepackedWeightsContainer* prepacked_weights_container) {
  ThrowOnError(GetApi().CreateSessionFromArrayWithPrepackedWeightsContainer(env, model_data, model_data_length, options,
                                                                            prepacked_weights_container, &this->p_));
}

inline AllocatedStringPtr ModelMetadata::GetProducerNameAllocated(OrtAllocator* allocator) const {
  char* out;
  ThrowOnError(GetApi().ModelMetadataGetProducerName(p_, allocator, &out));
  return AllocatedStringPtr(out, detail::AllocatedFree(allocator));
}

inline AllocatedStringPtr ModelMetadata::GetGraphNameAllocated(OrtAllocator* allocator) const {
  char* out;
  ThrowOnError(GetApi().ModelMetadataGetGraphName(p_, allocator, &out));
  return AllocatedStringPtr(out, detail::AllocatedFree(allocator));
}

inline AllocatedStringPtr ModelMetadata::GetDomainAllocated(OrtAllocator* allocator) const {
  char* out;
  ThrowOnError(GetApi().ModelMetadataGetDomain(p_, allocator, &out));
  return AllocatedStringPtr(out, detail::AllocatedFree(allocator));
}

inline AllocatedStringPtr Ort::ModelMetadata::GetDescriptionAllocated(OrtAllocator* allocator) const {
  char* out;
  ThrowOnError(GetApi().ModelMetadataGetDescription(p_, allocator, &out));
  return AllocatedStringPtr(out, detail::AllocatedFree(allocator));
}

inline AllocatedStringPtr ModelMetadata::GetGraphDescriptionAllocated(OrtAllocator* allocator) const {
  char* out;
  ThrowOnError(GetApi().ModelMetadataGetGraphDescription(p_, allocator, &out));
  return AllocatedStringPtr(out, detail::AllocatedFree(allocator));
}

inline AllocatedStringPtr ModelMetadata::LookupCustomMetadataMapAllocated(const char* key, OrtAllocator* allocator) const {
  char* out;
  ThrowOnError(GetApi().ModelMetadataLookupCustomMetadataMap(p_, allocator, key, &out));
  return AllocatedStringPtr(out, detail::AllocatedFree(allocator));
}

inline std::vector<AllocatedStringPtr> ModelMetadata::GetCustomMetadataMapKeysAllocated(OrtAllocator* allocator) const {
  auto deletor = detail::AllocatedFree(allocator);
  std::vector<AllocatedStringPtr> result;

  char** out = nullptr;
  int64_t num_keys = 0;
  ThrowOnError(GetApi().ModelMetadataGetCustomMetadataMapKeys(p_, allocator, &out, &num_keys));
  if (num_keys <= 0) {
    return result;
  }

  // array of pointers will be freed
  std::unique_ptr<void, decltype(deletor)> array_guard(out, deletor);
  // reserve may throw
  auto strings_deletor = [&deletor, num_keys](char** out) { for(int64_t i = 0; i < num_keys; ++i) deletor(out[i]); };
  std::unique_ptr<char*, decltype(strings_deletor)> strings_guard(out, strings_deletor);
  result.reserve(static_cast<size_t>(num_keys));
  strings_guard.release();
  for (int64_t i = 0; i < num_keys; ++i) {
    result.push_back(AllocatedStringPtr(out[i], deletor));
  }

  return result;
}

inline int64_t ModelMetadata::GetVersion() const {
  int64_t out;
  ThrowOnError(GetApi().ModelMetadataGetVersion(p_, &out));
  return out;
}

namespace detail {

template <typename T>
inline ONNXTensorElementDataType TensorTypeAndShapeInfoImpl<T>::GetElementType() const {
  ONNXTensorElementDataType out;
  ThrowOnError(GetApi().GetTensorElementType(this->p_, &out));
  return out;
}

template <typename T>
inline size_t TensorTypeAndShapeInfoImpl<T>::GetElementCount() const {
  size_t out;
  ThrowOnError(GetApi().GetTensorShapeElementCount(this->p_, &out));
  return static_cast<size_t>(out);
}

template <typename T>
inline size_t TensorTypeAndShapeInfoImpl<T>::GetDimensionsCount() const {
  size_t out;
  ThrowOnError(GetApi().GetDimensionsCount(this->p_, &out));
  return out;
}

template <typename T>
inline void TensorTypeAndShapeInfoImpl<T>::GetDimensions(int64_t* values, size_t values_count) const {
  ThrowOnError(GetApi().GetDimensions(this->p_, values, values_count));
}

template <typename T>
inline void TensorTypeAndShapeInfoImpl<T>::GetSymbolicDimensions(const char** values, size_t values_count) const {
  ThrowOnError(GetApi().GetSymbolicDimensions(this->p_, values, values_count));
}

template <typename T>
inline std::vector<int64_t> TensorTypeAndShapeInfoImpl<T>::GetShape() const {
  std::vector<int64_t> out(GetDimensionsCount(), 0);
  ThrowOnError(GetApi().GetDimensions(this->p_, out.data(), out.size()));
  return out;
}

}  // namespace detail

namespace detail {
template <typename T>
inline ConstTensorTypeAndShapeInfo TypeInfoImpl<T>::GetTensorTypeAndShapeInfo() const {
  const OrtTensorTypeAndShapeInfo* out;
  ThrowOnError(GetApi().CastTypeInfoToTensorInfo(this->p_, &out));
  return ConstTensorTypeAndShapeInfo{out};
}

template <typename T>
inline ConstSequenceTypeInfo TypeInfoImpl<T>::GetSequenceTypeInfo() const {
  const OrtSequenceTypeInfo* out;
  ThrowOnError(GetApi().CastTypeInfoToSequenceTypeInfo(this->p_, &out));
  return ConstSequenceTypeInfo{out};
}

template <typename T>
inline ConstMapTypeInfo TypeInfoImpl<T>::GetMapTypeInfo() const {
  const OrtMapTypeInfo* out;
  ThrowOnError(GetApi().CastTypeInfoToMapTypeInfo(this->p_, &out));
  return ConstMapTypeInfo{out};
}

template <typename T>
inline ONNXType TypeInfoImpl<T>::GetONNXType() const {
  ONNXType out;
  ThrowOnError(GetApi().GetOnnxTypeFromTypeInfo(this->p_, &out));
  return out;
}

}  // namespace detail

namespace detail {
template <typename T>
inline TypeInfo SequenceTypeInfoImpl<T>::GetSequenceElementType() const {
  OrtTypeInfo* output;
  ThrowOnError(GetApi().GetSequenceElementType(this->p_, &output));
  return TypeInfo{output};
}

}  // namespace detail

namespace detail {
template <typename T>
inline ONNXTensorElementDataType MapTypeInfoImpl<T>::GetMapKeyType() const {
  ONNXTensorElementDataType out;
  ThrowOnError(GetApi().GetMapKeyType(this->p_, &out));
  return out;
}

template <typename T>
inline TypeInfo MapTypeInfoImpl<T>::GetMapValueType() const {
  OrtTypeInfo* output;
  ThrowOnError(GetApi().GetMapValueType(this->p_, &output));
  return TypeInfo{output};
}
}  // namespace detail

namespace detail {

template <typename T>
template <typename R>
inline void ConstValueImpl<T>::GetOpaqueData(const char* domain, const char* type_name, R& out) const {
  ThrowOnError(GetApi().GetOpaqueValue(domain, type_name, this->p_, &out, sizeof(R)));
}

template <typename T>
inline bool ConstValueImpl<T>::IsTensor() const {
  int out;
  ThrowOnError(GetApi().IsTensor(this->p_, &out));
  return out != 0;
}

template <typename T>
inline bool ConstValueImpl<T>::HasValue() const {
  int out;
  ThrowOnError(GetApi().HasValue(this->p_, &out));
  return out != 0;
}

template <typename T>
inline size_t ConstValueImpl<T>::GetCount() const {
  size_t out;
  ThrowOnError(GetApi().GetValueCount(this->p_, &out));
  return out;
}

template <typename T>
inline Value ConstValueImpl<T>::GetValue(int index, OrtAllocator* allocator) const {
  OrtValue* out;
  ThrowOnError(GetApi().GetValue(this->p_, index, allocator, &out));
  return Value{out};
}

template <typename T>
inline size_t ConstValueImpl<T>::GetStringTensorDataLength() const {
  size_t out;
  ThrowOnError(GetApi().GetStringTensorDataLength(this->p_, &out));
  return out;
}

template <typename T>
inline size_t ConstValueImpl<T>::GetStringTensorElementLength(size_t element_index) const {
  size_t out;
  ThrowOnError(GetApi().GetStringTensorElementLength(this->p_, element_index, &out));
  return out;
}

template <typename T>
template <typename R>
inline const R* ConstValueImpl<T>::GetTensorData() const {
  R* out;
  ThrowOnError(GetApi().GetTensorMutableData(const_cast<OrtValue*>(this->p_), (void**)&out));
  return out;
}

template <typename T>
inline const void* ConstValueImpl<T>::GetTensorRawData() const {
  void* out;
  ThrowOnError(GetApi().GetTensorMutableData(const_cast<OrtValue*>(this->p_), &out));
  return out;
}

template <typename T>
inline TypeInfo ConstValueImpl<T>::GetTypeInfo() const {
  OrtTypeInfo* output;
  ThrowOnError(GetApi().GetTypeInfo(this->p_, &output));
  return TypeInfo{output};
}

template <typename T>
inline TensorTypeAndShapeInfo ConstValueImpl<T>::GetTensorTypeAndShapeInfo() const {
  OrtTensorTypeAndShapeInfo* output;
  ThrowOnError(GetApi().GetTensorTypeAndShape(this->p_, &output));
  return TensorTypeAndShapeInfo{output};
}

template <typename T>
inline ConstMemoryInfo ConstValueImpl<T>::GetTensorMemoryInfo() const {
  const OrtMemoryInfo* mem_info;
  ThrowOnError(GetApi().GetTensorMemoryInfo(this->p_, &mem_info));
  return ConstMemoryInfo(mem_info);
}

template <typename T>
inline void ConstValueImpl<T>::GetStringTensorElement(size_t buffer_length, size_t element_index, void* buffer) const {
  ThrowOnError(GetApi().GetStringTensorElement(this->p_, buffer_length, element_index, buffer));
}

template <typename T>
inline void ConstValueImpl<T>::GetStringTensorContent(void* buffer, size_t buffer_length, size_t* offsets, size_t offsets_count) const {
  ThrowOnError(GetApi().GetStringTensorContent(this->p_, buffer, buffer_length, offsets, offsets_count));
}

#if !defined(DISABLE_SPARSE_TENSORS)
template <typename T>
inline OrtSparseFormat ConstValueImpl<T>::GetSparseFormat() const {
  OrtSparseFormat format;
  ThrowOnError(GetApi().GetSparseTensorFormat(this->p_, &format));
  return format;
}

template <typename T>
inline TensorTypeAndShapeInfo ConstValueImpl<T>::GetSparseTensorValuesTypeAndShapeInfo() const {
  OrtTensorTypeAndShapeInfo* output;
  ThrowOnError(GetApi().GetSparseTensorValuesTypeAndShape(this->p_, &output));
  return TensorTypeAndShapeInfo{output};
}

template <typename T>
inline TensorTypeAndShapeInfo ConstValueImpl<T>::GetSparseTensorIndicesTypeShapeInfo(OrtSparseIndicesFormat indices_format) const {
  OrtTensorTypeAndShapeInfo* output;
  ThrowOnError(GetApi().GetSparseTensorIndicesTypeShape(this->p_, indices_format, &output));
  return TensorTypeAndShapeInfo{output};
}

template <typename T>
template <typename R>
inline const R* ConstValueImpl<T>::GetSparseTensorIndicesData(OrtSparseIndicesFormat indices_format, size_t& num_indices) const {
  const void* out;
  ThrowOnError(GetApi().GetSparseTensorIndices(this->p_, indices_format, &num_indices, &out));
  return reinterpret_cast<const R*>(out);
}

template <typename T>
inline bool ConstValueImpl<T>::IsSparseTensor() const {
  int out;
  ThrowOnError(GetApi().IsSparseTensor(this->p_, &out));
  return out != 0;
}

template <typename T>
template <typename R>
inline const R* ConstValueImpl<T>::GetSparseTensorValues() const {
  const void* out;
  ThrowOnError(GetApi().GetSparseTensorValues(this->p_, &out));
  return reinterpret_cast<const R*>(out);
}

#endif

template <typename T>
void ValueImpl<T>::FillStringTensor(const char* const* s, size_t s_len) {
  ThrowOnError(GetApi().FillStringTensor(this->p_, s, s_len));
}

template <typename T>
void ValueImpl<T>::FillStringTensorElement(const char* s, size_t index) {
  ThrowOnError(GetApi().FillStringTensorElement(this->p_, s, index));
}

template <typename T>
void* ValueImpl<T>::GetTensorMutableRawData() {
  void* out;
  ThrowOnError(GetApi().GetTensorMutableData(this->p_, &out));
  return out;
}

template <typename T>
template <typename R>
R* ValueImpl<T>::GetTensorMutableData() {
  R* out;
  ThrowOnError(GetApi().GetTensorMutableData(this->p_, (void**)&out));
  return out;
}

template <typename T>
template <typename R>
R& ValueImpl<T>::At(const std::vector<int64_t>& location) {
  static_assert(!std::is_same<T, std::string>::value, "this api does not support std::string");
  R* out;
  ThrowOnError(GetApi().TensorAt(this->p_, location.data(), location.size(), (void**)&out));
  return *out;
}

#if !defined(DISABLE_SPARSE_TENSORS)
template <typename T>
void ValueImpl<T>::UseCooIndices(int64_t* indices_data, size_t indices_num) {
  ThrowOnError(GetApi().UseCooIndices(this->p_, indices_data, indices_num));
}

template <typename T>
void ValueImpl<T>::UseCsrIndices(int64_t* inner_data, size_t inner_num, int64_t* outer_data, size_t outer_num) {
  ThrowOnError(GetApi().UseCsrIndices(this->p_, inner_data, inner_num, outer_data, outer_num));
}

template <typename T>
void ValueImpl<T>::UseBlockSparseIndices(const Shape& indices_shape, int32_t* indices_data) {
  ThrowOnError(GetApi().UseBlockSparseIndices(this->p_, indices_shape.shape, indices_shape.shape_len, indices_data));
}

template <typename T>
void ValueImpl<T>::FillSparseTensorCoo(const OrtMemoryInfo* mem_info, const OrtSparseValuesParam& values_param,
                                       const int64_t* indices_data, size_t indices_num) {
  ThrowOnError(GetApi().FillSparseTensorCoo(this->p_, mem_info, values_param.values_shape,
                                            values_param.values_shape_len, values_param.data.p_data,
                                            indices_data, indices_num));
}

template <typename T>
void ValueImpl<T>::FillSparseTensorCsr(const OrtMemoryInfo* data_mem_info,
                                       const OrtSparseValuesParam& values,
                                       const int64_t* inner_indices_data, size_t inner_indices_num,
                                       const int64_t* outer_indices_data, size_t outer_indices_num) {
  ThrowOnError(GetApi().FillSparseTensorCsr(this->p_, data_mem_info, values.values_shape, values.values_shape_len, values.data.p_data,
                                            inner_indices_data, inner_indices_num,
                                            outer_indices_data, outer_indices_num));
}

template <typename T>
void ValueImpl<T>::FillSparseTensorBlockSparse(const OrtMemoryInfo* data_mem_info,
                                               const OrtSparseValuesParam& values,
                                               const Shape& indices_shape,
                                               const int32_t* indices_data) {
  ThrowOnError(GetApi().FillSparseTensorBlockSparse(this->p_, data_mem_info, values.values_shape, values.values_shape_len, values.data.p_data,
                                                    indices_shape.shape, indices_shape.shape_len,
                                                    indices_data));
}

#endif  // !defined(DISABLE_SPARSE_TENSORS)

}  // namespace detail

template <typename T>
inline Value Value::CreateTensor(const OrtMemoryInfo* info, T* p_data, size_t p_data_element_count, const int64_t* shape, size_t shape_len) {
  return CreateTensor(info, p_data, p_data_element_count * sizeof(T), shape, shape_len, TypeToTensorType<T>::type);
}

inline Value Value::CreateTensor(const OrtMemoryInfo* info, void* p_data, size_t p_data_byte_count, const int64_t* shape, size_t shape_len,
                                 ONNXTensorElementDataType type) {
  OrtValue* out;
  ThrowOnError(GetApi().CreateTensorWithDataAsOrtValue(info, p_data, p_data_byte_count, shape, shape_len, type, &out));
  return Value{out};
}

template <typename T>
inline Value Value::CreateTensor(OrtAllocator* allocator, const int64_t* shape, size_t shape_len) {
  return CreateTensor(allocator, shape, shape_len, TypeToTensorType<T>::type);
}

inline Value Value::CreateTensor(OrtAllocator* allocator, const int64_t* shape, size_t shape_len, ONNXTensorElementDataType type) {
  OrtValue* out;
  ThrowOnError(GetApi().CreateTensorAsOrtValue(allocator, shape, shape_len, type, &out));
  return Value{out};
}

#if !defined(DISABLE_SPARSE_TENSORS)

template <typename T>
inline Value Value::CreateSparseTensor(const OrtMemoryInfo* info, T* p_data, const Shape& dense_shape,
                                       const Shape& values_shape) {
  return CreateSparseTensor(info, p_data, dense_shape, values_shape, TypeToTensorType<T>::type);
}

inline Value Value::CreateSparseTensor(const OrtMemoryInfo* info, void* p_data, const Shape& dense_shape,
                                       const Shape& values_shape, ONNXTensorElementDataType type) {
  OrtValue* out;
  ThrowOnError(GetApi().CreateSparseTensorWithValuesAsOrtValue(info, p_data, dense_shape.shape, dense_shape.shape_len,
                                                               values_shape.shape, values_shape.shape_len, type, &out));
  return Value{out};
}

template <typename T>
inline Value Value::CreateSparseTensor(OrtAllocator* allocator, const Shape& dense_shape) {
  return CreateSparseTensor(allocator, dense_shape, TypeToTensorType<T>::type);
}

inline Value Value::CreateSparseTensor(OrtAllocator* allocator, const Shape& dense_shape,
                                       ONNXTensorElementDataType type) {
  OrtValue* out;
  ThrowOnError(GetApi().CreateSparseTensorAsOrtValue(allocator, dense_shape.shape, dense_shape.shape_len, type, &out));
  return Value{out};
}
#endif  // !defined(DISABLE_SPARSE_TENSORS)

inline Value Value::CreateMap(Value& keys, Value& values) {
  OrtValue* out;
  OrtValue* inputs[2] = {keys, values};
  ThrowOnError(GetApi().CreateValue(inputs, 2, ONNX_TYPE_MAP, &out));
  return Value{out};
}

inline Value Value::CreateSequence(std::vector<Value>& values) {
  OrtValue* out;
  std::vector<OrtValue*> values_ort{values.data(), values.data() + values.size()};
  ThrowOnError(GetApi().CreateValue(values_ort.data(), values_ort.size(), ONNX_TYPE_SEQUENCE, &out));
  return Value{out};
}

template <typename T>
inline Value Value::CreateOpaque(const char* domain, const char* type_name, const T& data_container) {
  OrtValue* out;
  ThrowOnError(GetApi().CreateOpaqueValue(domain, type_name, &data_container, sizeof(T), &out));
  return Value{out};
}

//
// Custom OP Inlines
//
inline KernelContext::KernelContext(OrtKernelContext* context) : ctx_(context) {
}

inline size_t KernelContext::GetInputCount() const {
  size_t out = 0;
  Ort::ThrowOnError(GetApi().KernelContext_GetInputCount(ctx_, &out));
  return out;
}

inline size_t KernelContext::GetOutputCount() const {
  size_t out = 0;
  Ort::ThrowOnError(GetApi().KernelContext_GetOutputCount(ctx_, &out));
  return out;
}

inline ConstValue KernelContext::GetInput(size_t index) const {
  const OrtValue* out = nullptr;
  Ort::ThrowOnError(GetApi().KernelContext_GetInput(ctx_, index, &out));
  return ConstValue{out};
}

inline UnownedValue KernelContext::GetOutput(size_t index, const int64_t* dim_values, size_t dim_count) const {
  OrtValue* out = nullptr;
  Ort::ThrowOnError(GetApi().KernelContext_GetOutput(ctx_, index, dim_values, dim_count, &out));
  return UnownedValue(out);
}

inline UnownedValue KernelContext::GetOutput(size_t index, const std::vector<int64_t>& dims) const {
  OrtValue* out = nullptr;
  Ort::ThrowOnError(GetApi().KernelContext_GetOutput(ctx_, index, dims.data(), dims.size(), &out));
  return UnownedValue(out);
}

inline void* KernelContext::GetGPUComputeStream() const {
  void* out = nullptr;
  Ort::ThrowOnError(GetApi().KernelContext_GetGPUComputeStream(ctx_, &out));
  return out;
}

inline OpAttr::OpAttr(const char* name, const void* data, int len, OrtOpAttrType type) {
  Ort::ThrowOnError(GetApi().CreateOpAttr(name, data, len, type, &p_));
}

namespace detail {
template <typename T>
inline KernelInfo KernelInfoImpl<T>::Copy() const {
  OrtKernelInfo* info_copy = nullptr;
  Ort::ThrowOnError(GetApi().CopyKernelInfo(this->p_, &info_copy));
  return KernelInfo{info_copy};
}

template <typename T>
inline size_t KernelInfoImpl<T>::GetInputCount() const {
  size_t out = 0;
  ThrowOnError(GetApi().KernelInfo_GetInputCount(this->p_, &out));
  return out;
}

template <typename T>
inline size_t KernelInfoImpl<T>::GetOutputCount() const {
  size_t out = 0;
  ThrowOnError(GetApi().KernelInfo_GetOutputCount(this->p_, &out));
  return out;
}

template <typename T>
inline std::string KernelInfoImpl<T>::GetInputName(size_t index) const {
  size_t size = 0;

  // Feed nullptr for the data buffer to query the true size of the string value
  Ort::ThrowOnError(GetApi().KernelInfo_GetInputName(this->p_, index, nullptr, &size));

  std::string out;
  out.resize(size);
  Ort::ThrowOnError(GetApi().KernelInfo_GetInputName(this->p_, index, &out[0], &size));
  out.resize(size - 1);  // remove the terminating character '\0'

  return out;
}

template <typename T>
inline std::string KernelInfoImpl<T>::GetOutputName(size_t index) const {
  size_t size = 0;

  // Feed nullptr for the data buffer to query the true size of the string value
  Ort::ThrowOnError(GetApi().KernelInfo_GetOutputName(this->p_, index, nullptr, &size));

  std::string out;
  out.resize(size);
  Ort::ThrowOnError(GetApi().KernelInfo_GetOutputName(this->p_, index, &out[0], &size));
  out.resize(size - 1);  // remove the terminating character '\0'

  return out;
}

template <typename T>
inline TypeInfo KernelInfoImpl<T>::GetInputTypeInfo(size_t index) const {
  OrtTypeInfo* out = nullptr;
  ThrowOnError(GetApi().KernelInfo_GetInputTypeInfo(this->p_, index, &out));
  return TypeInfo{out};
}

template <typename T>
inline TypeInfo KernelInfoImpl<T>::GetOutputTypeInfo(size_t index) const {
  OrtTypeInfo* out = nullptr;
  ThrowOnError(GetApi().KernelInfo_GetOutputTypeInfo(this->p_, index, &out));
  return TypeInfo{out};
}

template <typename T>
inline Value KernelInfoImpl<T>::GetTensorAttribute(const char* name, OrtAllocator* allocator) const {
  OrtValue* out = nullptr;
  ThrowOnError(GetApi().KernelInfoGetAttribute_tensor(this->p_, name, allocator, &out));
  return Value{out};
}

inline void attr_utils::GetAttr(const OrtKernelInfo* p, const char* name, float& out) {
  Ort::ThrowOnError(GetApi().KernelInfoGetAttribute_float(p, name, &out));
}

inline void attr_utils::GetAttr(const OrtKernelInfo* p, const char* name, int64_t& out) {
  Ort::ThrowOnError(GetApi().KernelInfoGetAttribute_int64(p, name, &out));
}

inline void attr_utils::GetAttr(const OrtKernelInfo* p, const char* name, std::string& result) {
  size_t size = 0;
  // Feed nullptr for the data buffer to query the true size of the string attribute
  Ort::ThrowOnError(GetApi().KernelInfoGetAttribute_string(p, name, nullptr, &size));

  std::string out;
  out.resize(size);
  Ort::ThrowOnError(GetApi().KernelInfoGetAttribute_string(p, name, &out[0], &size));
  out.resize(size - 1);  // remove the terminating character '\0'
  out.swap(result);
}

inline void attr_utils::GetAttrs(const OrtKernelInfo* p, const char* name, std::vector<float>& result) {
  size_t size = 0;
  // Feed nullptr for the data buffer to query the true size of the attribute
  Ort::ThrowOnError(GetApi().KernelInfoGetAttributeArray_float(p, name, nullptr, &size));

  std::vector<float> out;
  out.resize(size);
  Ort::ThrowOnError(GetApi().KernelInfoGetAttributeArray_float(p, name, out.data(), &size));
  out.swap(result);
}

inline void attr_utils::GetAttrs(const OrtKernelInfo* p, const char* name, std::vector<int64_t>& result) {
  size_t size = 0;

  // Feed nullptr for the data buffer to query the true size of the attribute
  Ort::ThrowOnError(GetApi().KernelInfoGetAttributeArray_int64(p, name, nullptr, &size));

  std::vector<int64_t> out;
  out.resize(size);
  Ort::ThrowOnError(GetApi().KernelInfoGetAttributeArray_int64(p, name, out.data(), &size));
  out.swap(result);
}
}  // namespace detail

inline KernelInfo::KernelInfo(OrtKernelInfo* info) : detail::KernelInfoImpl<OrtKernelInfo>{info} {}

inline Op::Op(OrtOp* p) : Base<OrtOp>(p) {}

inline Op Op::Create(const OrtKernelInfo* info, const char* op_name, const char* domain, int version,
                     const char** type_constraint_names,
                     const ONNXTensorElementDataType* type_constraint_values,
                     size_t type_constraint_count,
                     const OpAttr* attr_values, size_t attr_count,
                     size_t input_count, size_t output_count) {
  static_assert(sizeof(OpAttr) == sizeof(OrtOpAttr*),
                "OpAttr's is expected to be just an array of OrtOpAttr in memory so we can reinterpret safely");
  auto attr_input_values = reinterpret_cast<const OrtOpAttr* const*>(attr_values);
  OrtOp* op;
  Ort::ThrowOnError(GetApi().CreateOp(info, op_name, domain, version, type_constraint_names, type_constraint_values,
                                      static_cast<int>(type_constraint_count),
                                      attr_input_values,
                                      static_cast<int>(attr_count),
                                      static_cast<int>(input_count),
                                      static_cast<int>(output_count), &op));
  return Op{op};
}

inline void Op::Invoke(const OrtKernelContext* context,
                       const Value* input_values,
                       size_t input_count,
                       Value* output_values,
                       size_t output_count) {
  static_assert(sizeof(Value) == sizeof(OrtValue*),
                "Value is really just an array of OrtValue* in memory, so we can reinterpret_cast safely");
  auto ort_input_values = reinterpret_cast<const OrtValue* const*>(input_values);
  auto ort_output_values = reinterpret_cast<OrtValue**>(output_values);
  Ort::ThrowOnError(GetApi().InvokeOp(context, p_, ort_input_values, static_cast<int>(input_count),
                                      ort_output_values, static_cast<int>(output_count)));
}

inline void Op::Invoke(const OrtKernelContext* context,
                       const OrtValue* const* input_values,
                       size_t input_count,
                       OrtValue* const* output_values,
                       size_t output_count) {
  Ort::ThrowOnError(GetApi().InvokeOp(context, p_, input_values, static_cast<int>(input_count),
                                      output_values, static_cast<int>(output_count)));
}

inline void CustomOpApi::ThrowOnError(OrtStatus* status) {
  Ort::ThrowOnError(status);
}

template <>
inline float CustomOpApi::KernelInfoGetAttribute<float>(_In_ const OrtKernelInfo* info, _In_ const char* name) {
  float out;
  Ort::ThrowOnError(api_.KernelInfoGetAttribute_float(info, name, &out));
  return out;
}

template <>
inline int64_t CustomOpApi::KernelInfoGetAttribute<int64_t>(_In_ const OrtKernelInfo* info, _In_ const char* name) {
  int64_t out;
  Ort::ThrowOnError(api_.KernelInfoGetAttribute_int64(info, name, &out));
  return out;
}

template <>
inline std::string CustomOpApi::KernelInfoGetAttribute<std::string>(_In_ const OrtKernelInfo* info, _In_ const char* name) {
  size_t size = 0;
  std::string out;

  // Feed nullptr for the data buffer to query the true size of the string attribute
  OrtStatus* status = api_.KernelInfoGetAttribute_string(info, name, nullptr, &size);

  if (status == nullptr) {
    out.resize(size);
    Ort::ThrowOnError(api_.KernelInfoGetAttribute_string(info, name, &out[0], &size));
    out.resize(size - 1);  // remove the terminating character '\0'
  } else {
    Ort::ThrowOnError(status);
  }
  return out;
}

template <>
inline std::vector<float> CustomOpApi::KernelInfoGetAttribute(_In_ const OrtKernelInfo* info, _In_ const char* name) {
  size_t size = 0;
  std::vector<float> out;

  // Feed nullptr for the data buffer to query the true size of the attribute
  OrtStatus* status = api_.KernelInfoGetAttributeArray_float(info, name, nullptr, &size);

  if (status == nullptr) {
    out.resize(size);
    Ort::ThrowOnError(api_.KernelInfoGetAttributeArray_float(info, name, out.data(), &size));
  } else {
    Ort::ThrowOnError(status);
  }
  return out;
}

template <>
inline std::vector<int64_t> CustomOpApi::KernelInfoGetAttribute(_In_ const OrtKernelInfo* info, _In_ const char* name) {
  size_t size = 0;
  std::vector<int64_t> out;

  // Feed nullptr for the data buffer to query the true size of the attribute
  OrtStatus* status = api_.KernelInfoGetAttributeArray_int64(info, name, nullptr, &size);

  if (status == nullptr) {
    out.resize(size);
    Ort::ThrowOnError(api_.KernelInfoGetAttributeArray_int64(info, name, out.data(), &size));
  } else {
    Ort::ThrowOnError(status);
  }
  return out;
}
inline OrtTensorTypeAndShapeInfo* CustomOpApi::GetTensorTypeAndShape(_In_ const OrtValue* value) {
  OrtTensorTypeAndShapeInfo* out;
  Ort::ThrowOnError(api_.GetTensorTypeAndShape(value, &out));
  return out;
}

inline size_t CustomOpApi::GetTensorShapeElementCount(_In_ const OrtTensorTypeAndShapeInfo* info) {
  size_t out;
  Ort::ThrowOnError(api_.GetTensorShapeElementCount(info, &out));
  return out;
}

inline ONNXTensorElementDataType CustomOpApi::GetTensorElementType(const OrtTensorTypeAndShapeInfo* info) {
  ONNXTensorElementDataType out;
  Ort::ThrowOnError(api_.GetTensorElementType(info, &out));
  return out;
}

inline size_t CustomOpApi::GetDimensionsCount(_In_ const OrtTensorTypeAndShapeInfo* info) {
  size_t out;
  Ort::ThrowOnError(api_.GetDimensionsCount(info, &out));
  return out;
}

inline void CustomOpApi::GetDimensions(_In_ const OrtTensorTypeAndShapeInfo* info, _Out_ int64_t* dim_values, size_t dim_values_length) {
  Ort::ThrowOnError(api_.GetDimensions(info, dim_values, dim_values_length));
}

inline void CustomOpApi::SetDimensions(OrtTensorTypeAndShapeInfo* info, _In_ const int64_t* dim_values, size_t dim_count) {
  Ort::ThrowOnError(api_.SetDimensions(info, dim_values, dim_count));
}

template <typename T>
inline T* CustomOpApi::GetTensorMutableData(_Inout_ OrtValue* value) {
  T* data;
  Ort::ThrowOnError(api_.GetTensorMutableData(value, reinterpret_cast<void**>(&data)));
  return data;
}

inline const OrtMemoryInfo* CustomOpApi::GetTensorMemoryInfo(_In_ const OrtValue* value) {
  const OrtMemoryInfo* mem_info;
  Ort::ThrowOnError(api_.GetTensorMemoryInfo(value, &mem_info));
  return mem_info;
}

template <typename T>
inline const T* CustomOpApi::GetTensorData(_Inout_ const OrtValue* value) {
  T* data = nullptr;
  Ort::ThrowOnError(api_.GetTensorMutableData(const_cast<OrtValue*>(value), reinterpret_cast<void**>(&data)));
  return data;
}

inline std::vector<int64_t> CustomOpApi::GetTensorShape(const OrtTensorTypeAndShapeInfo* info) {
  size_t out;
  Ort::ThrowOnError(api_.GetDimensionsCount(info, &out));
  std::vector<int64_t> output(out);
  Ort::ThrowOnError(api_.GetDimensions(info, output.data(), out));
  return output;
}

inline void CustomOpApi::ReleaseTensorTypeAndShapeInfo(OrtTensorTypeAndShapeInfo* input) {
  api_.ReleaseTensorTypeAndShapeInfo(input);
}

inline size_t CustomOpApi::KernelContext_GetInputCount(const OrtKernelContext* context) {
  size_t out;
  Ort::ThrowOnError(api_.KernelContext_GetInputCount(context, &out));
  return out;
}

inline const OrtValue* CustomOpApi::KernelContext_GetInput(const OrtKernelContext* context, _In_ size_t index) {
  const OrtValue* out;
  Ort::ThrowOnError(api_.KernelContext_GetInput(context, index, &out));
  return out;
}

inline size_t CustomOpApi::KernelContext_GetOutputCount(const OrtKernelContext* context) {
  size_t out;
  Ort::ThrowOnError(api_.KernelContext_GetOutputCount(context, &out));
  return out;
}

inline OrtValue* CustomOpApi::KernelContext_GetOutput(OrtKernelContext* context, _In_ size_t index,
                                                      _In_ const int64_t* dim_values, size_t dim_count) {
  OrtValue* out;
  Ort::ThrowOnError(api_.KernelContext_GetOutput(context, index, dim_values, dim_count, &out));
  return out;
}

inline void* CustomOpApi::KernelContext_GetGPUComputeStream(const OrtKernelContext* context) {
  void* out;
  Ort::ThrowOnError(api_.KernelContext_GetGPUComputeStream(context, &out));
  return out;
}

inline OrtOpAttr* CustomOpApi::CreateOpAttr(_In_ const char* name,
                                            _In_ const void* data,
                                            _In_ int len,
                                            _In_ OrtOpAttrType type) {
  OrtOpAttr* op_attr{};
  Ort::ThrowOnError(api_.CreateOpAttr(name, data, len, type, &op_attr));
  return op_attr;
}

inline void CustomOpApi::ReleaseOpAttr(_Frees_ptr_opt_ OrtOpAttr* op_attr) {
  api_.ReleaseOpAttr(op_attr);
}

inline OrtOp* CustomOpApi::CreateOp(_In_ const OrtKernelInfo* info,
                                    _In_ const char* op_name,
                                    _In_ const char* domain,
                                    _In_ int version,
                                    _In_opt_ const char** type_constraint_names,
                                    _In_opt_ const ONNXTensorElementDataType* type_constraint_values,
                                    _In_opt_ int type_constraint_count,
                                    _In_opt_ const OrtOpAttr* const* attr_values,
                                    _In_opt_ int attr_count,
                                    _In_ int input_count,
                                    _In_ int output_count) {
  OrtOp* ort_op{};
  Ort::ThrowOnError(api_.CreateOp(info, op_name, domain, version, type_constraint_names, type_constraint_values,
                                  type_constraint_count, attr_values, attr_count, input_count, output_count, &ort_op));
  return ort_op;
}

inline void CustomOpApi::InvokeOp(_In_ const OrtKernelContext* context,
                                  _In_ const OrtOp* ort_op,
                                  _In_ const OrtValue* const* input_values,
                                  _In_ int input_count,
                                  _Inout_ OrtValue* const* output_values,
                                  _In_ int output_count) {
  Ort::ThrowOnError(api_.InvokeOp(context, ort_op, input_values, input_count, output_values, output_count));
}

inline void CustomOpApi::ReleaseOp(_Frees_ptr_opt_ OrtOp* ort_op) {
  api_.ReleaseOp(ort_op);
}

inline OrtKernelInfo* CustomOpApi::CopyKernelInfo(_In_ const OrtKernelInfo* info) {
  OrtKernelInfo* info_copy{};
  Ort::ThrowOnError(api_.CopyKernelInfo(info, &info_copy));
  return info_copy;
}

inline void CustomOpApi::ReleaseKernelInfo(_Frees_ptr_opt_ OrtKernelInfo* info_copy) {
  api_.ReleaseKernelInfo(info_copy);
}

inline std::vector<std::string> GetAvailableProviders() {
  int len;
  char** providers;
  ThrowOnError(GetApi().GetAvailableProviders(&providers, &len));
  std::vector<std::string> available_providers(providers, providers + len);
  ThrowOnError(GetApi().ReleaseAvailableProviders(providers, len));
  return available_providers;
}

SessionOptions& AddInitializer(const char* name, const OrtValue* ort_val);

template <typename TOp, typename TKernel>
void CustomOpBase<TOp, TKernel>::GetSessionConfigs(std::unordered_map<std::string, std::string>& out,
                                                   ConstSessionOptions options) const {
  const TOp* derived = static_cast<const TOp*>(this);
  std::vector<std::string> keys = derived->GetSessionConfigKeys();

  out.reserve(keys.size());

  std::string config_entry_key = detail::MakeCustomOpConfigEntryKey(derived->GetName(), "");
  const size_t prefix_size = config_entry_key.length();

  for (const auto& key : keys) {
    config_entry_key.resize(prefix_size);
    config_entry_key.append(key);
    out[key] = options.GetConfigEntryOrDefault(config_entry_key.c_str(), "");
  }
}

}  // namespace Ort
