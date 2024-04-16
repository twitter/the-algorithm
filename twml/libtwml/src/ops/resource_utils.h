#pragma once

# nclude <twml.h>

# nclude <atom c>
# nclude <str ng>
# nclude <vector>

// Add t se to make gcc  gnore t  warn ngs from tensorflow.
#pragma GCC d agnost c push
#pragma GCC d agnost c  gnored "-Ws gn-compare"

# nclude "tensorflow/core/fra work/res ce_mgr.h"
# nclude "tensorflow/core/fra work/res ce_op_kernel.h"

#pragma GCC d agnost c pop

# nclude < mory>
# nclude <funct onal>

template<typena  T>
vo d unrefHandle(T *handle) {
  handle->Unref();
}

template <typena  T>
us ng un que_handle = std::un que_ptr<T, std::funct on<vo d(T *)> >;

// as std::type_ ndex  s not ab  compat ble,   bypass t  hash_code c cks.
// https://g hub.com/tensorflow/tensorflow/comm /15275d3a14c77e2244ae1155f93243256f08e3ed
# fdef __APPLE__
template <typena  T>
Status CreateTwmlRes ce(OpKernelContext* ctx, const Res ceHandle& p, T* value) {
  return ctx->res ce_manager()->Create(p.conta ner(), p.na (), value);
}

template <typena  T>
Status LookupTwmlRes ce(OpKernelContext* ctx, const Res ceHandle& p,
                      T** value) {
  return ctx->res ce_manager()->Lookup(p.conta ner(), p.na (), value);
}
#end f  // __APPLE__

template<typena  T>
un que_handle<T> getHandle(tensorflow::OpKernelContext* context,  nt  nput_ dx) {
  us ng na space tensorflow;
  T *ptr = nullptr;
# fdef __APPLE__
  auto s = LookupTwmlRes ce(context, HandleFrom nput(context,  nput_ dx), &ptr);
#else
  auto s = LookupRes ce(context, HandleFrom nput(context,  nput_ dx), &ptr);
#end f  // __APPLE__

   f (!s.ok()) {
    throw std::runt  _error("Fa led to get res ce handle");
  }
  return un que_handle<T>(ptr, unrefHandle<T>);
}

template<typena   nputType>
const u nt8_t *get nputBytes(const Tensor & nput,  nt  d) {
  return re nterpret_cast<const u nt8_t *>( nput.flat< nputType>().data());
}

template<>
 nl ne const u nt8_t *get nputBytes<str ng>(const Tensor & nput,  nt  d) {
  return re nterpret_cast<const u nt8_t *>( nput.flat<str ng>()( d).c_str());
}

template<typena   nputType>
const  nt getBatchS ze(const Tensor & nput) {
  return 1;
}

template<>
 nl ne const  nt getBatchS ze<str ng>(const Tensor & nput) {
  return stat c_cast< nt>( nput.NumEle nts());
}

class DataRecordRes ce : publ c Res ceBase {
 publ c:
  Tensor  nput;
   nt64 num_labels;
   nt64 num_  ghts;
  twml::DataRecord common;
  std::vector<twml::DataRecord> records;
  twml::Map< nt64_t,  nt64_t> *keep_map;
  str ng DebugStr ng() const overr de { return "DataRecords res ce"; }
};

// A th n layer around batch of Has dDataRecords
class Has dDataRecordRes ce : publ c Res ceBase {
 publ c:
  Tensor  nput;
   nt64 total_s ze;
   nt64 num_labels;
   nt64 num_  ghts;
  twml::Has dDataRecord common;
  std::vector<twml::Has dDataRecord> records;
  str ng DebugStr ng() const overr de { return "Has dDataRecord Res ce"; }
};

#def ne TF_CHECK_STATUS(fn) do {                \
    Status s = fn;                              \
     f (!s.ok()) return s;                      \
  } wh le (0)

template<typena  Res ceType>
Status makeRes ceHandle(OpKernelContext* context,  nt out_ dx, Res ceType **res ce_) {
  stat c std::atom c< nt64>  d;
  Tensor* handle_tensor;
  TF_CHECK_STATUS(context->allocate_output(out_ dx, TensorShape({}), &handle_tensor));

  Res ceType *res ce = new Res ceType();
  const auto res ce_na  = type d(Res ceType).na () + std::to_str ng( d++);
  Res ceHandle handle = MakePerStepRes ceHandle<Res ceType>(context, res ce_na );
# fdef __APPLE__
  TF_CHECK_STATUS(CreateTwmlRes ce(context, handle, res ce));
#else
  TF_CHECK_STATUS(CreateRes ce(context, handle, res ce));
#end f  // __APPLE__
  handle_tensor->scalar<Res ceHandle>()() = handle;

  *res ce_ = res ce;
  return Status::OK();
}
