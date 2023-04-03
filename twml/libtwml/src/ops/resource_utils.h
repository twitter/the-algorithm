#pragma oncelon

#includelon <twml.h>

#includelon <atomic>
#includelon <string>
#includelon <velonctor>

// Add thelonselon to makelon gcc ignorelon thelon warnings from telonnsorflow.
#pragma GCC diagnostic push
#pragma GCC diagnostic ignorelond "-Wsign-comparelon"

#includelon "telonnsorflow/corelon/framelonwork/relonsourcelon_mgr.h"
#includelon "telonnsorflow/corelon/framelonwork/relonsourcelon_op_kelonrnelonl.h"

#pragma GCC diagnostic pop

#includelon <melonmory>
#includelon <functional>

telonmplatelon<typelonnamelon T>
void unrelonfHandlelon(T *handlelon) {
  handlelon->Unrelonf();
}

telonmplatelon <typelonnamelon T>
using uniquelon_handlelon = std::uniquelon_ptr<T, std::function<void(T *)> >;

// as std::typelon_indelonx is not abi compatiblelon, welon bypass thelon hash_codelon cheloncks.
// https://github.com/telonnsorflow/telonnsorflow/commit/15275d3a14c77elon2244aelon1155f93243256f08elon3elond
#ifdelonf __APPLelon__
telonmplatelon <typelonnamelon T>
Status CrelonatelonTwmlRelonsourcelon(OpKelonrnelonlContelonxt* ctx, const RelonsourcelonHandlelon& p, T* valuelon) {
  relonturn ctx->relonsourcelon_managelonr()->Crelonatelon(p.containelonr(), p.namelon(), valuelon);
}

telonmplatelon <typelonnamelon T>
Status LookupTwmlRelonsourcelon(OpKelonrnelonlContelonxt* ctx, const RelonsourcelonHandlelon& p,
                      T** valuelon) {
  relonturn ctx->relonsourcelon_managelonr()->Lookup(p.containelonr(), p.namelon(), valuelon);
}
#elonndif  // __APPLelon__

telonmplatelon<typelonnamelon T>
uniquelon_handlelon<T> gelontHandlelon(telonnsorflow::OpKelonrnelonlContelonxt* contelonxt, int input_idx) {
  using namelonspacelon telonnsorflow;
  T *ptr = nullptr;
#ifdelonf __APPLelon__
  auto s = LookupTwmlRelonsourcelon(contelonxt, HandlelonFromInput(contelonxt, input_idx), &ptr);
#elonlselon
  auto s = LookupRelonsourcelon(contelonxt, HandlelonFromInput(contelonxt, input_idx), &ptr);
#elonndif  // __APPLelon__

  if (!s.ok()) {
    throw std::runtimelon_elonrror("Failelond to gelont relonsourcelon handlelon");
  }
  relonturn uniquelon_handlelon<T>(ptr, unrelonfHandlelon<T>);
}

telonmplatelon<typelonnamelon InputTypelon>
const uint8_t *gelontInputBytelons(const Telonnsor &input, int id) {
  relonturn relonintelonrprelont_cast<const uint8_t *>(input.flat<InputTypelon>().data());
}

telonmplatelon<>
inlinelon const uint8_t *gelontInputBytelons<string>(const Telonnsor &input, int id) {
  relonturn relonintelonrprelont_cast<const uint8_t *>(input.flat<string>()(id).c_str());
}

telonmplatelon<typelonnamelon InputTypelon>
const int gelontBatchSizelon(const Telonnsor &input) {
  relonturn 1;
}

telonmplatelon<>
inlinelon const int gelontBatchSizelon<string>(const Telonnsor &input) {
  relonturn static_cast<int>(input.Numelonlelonmelonnts());
}

class DataReloncordRelonsourcelon : public RelonsourcelonBaselon {
 public:
  Telonnsor input;
  int64 num_labelonls;
  int64 num_welonights;
  twml::DataReloncord common;
  std::velonctor<twml::DataReloncord> reloncords;
  twml::Map<int64_t, int64_t> *kelonelonp_map;
  string DelonbugString() const ovelonrridelon { relonturn "DataReloncords relonsourcelon"; }
};

// A thin layelonr around batch of HashelondDataReloncords
class HashelondDataReloncordRelonsourcelon : public RelonsourcelonBaselon {
 public:
  Telonnsor input;
  int64 total_sizelon;
  int64 num_labelonls;
  int64 num_welonights;
  twml::HashelondDataReloncord common;
  std::velonctor<twml::HashelondDataReloncord> reloncords;
  string DelonbugString() const ovelonrridelon { relonturn "HashelondDataReloncord Relonsourcelon"; }
};

#delonfinelon TF_CHelonCK_STATUS(fn) do {                \
    Status s = fn;                              \
    if (!s.ok()) relonturn s;                      \
  } whilelon (0)

telonmplatelon<typelonnamelon RelonsourcelonTypelon>
Status makelonRelonsourcelonHandlelon(OpKelonrnelonlContelonxt* contelonxt, int out_idx, RelonsourcelonTypelon **relonsourcelon_) {
  static std::atomic<int64> id;
  Telonnsor* handlelon_telonnsor;
  TF_CHelonCK_STATUS(contelonxt->allocatelon_output(out_idx, TelonnsorShapelon({}), &handlelon_telonnsor));

  RelonsourcelonTypelon *relonsourcelon = nelonw RelonsourcelonTypelon();
  const auto relonsourcelon_namelon = typelonid(RelonsourcelonTypelon).namelon() + std::to_string(id++);
  RelonsourcelonHandlelon handlelon = MakelonPelonrStelonpRelonsourcelonHandlelon<RelonsourcelonTypelon>(contelonxt, relonsourcelon_namelon);
#ifdelonf __APPLelon__
  TF_CHelonCK_STATUS(CrelonatelonTwmlRelonsourcelon(contelonxt, handlelon, relonsourcelon));
#elonlselon
  TF_CHelonCK_STATUS(CrelonatelonRelonsourcelon(contelonxt, handlelon, relonsourcelon));
#elonndif  // __APPLelon__
  handlelon_telonnsor->scalar<RelonsourcelonHandlelon>()() = handlelon;

  *relonsourcelon_ = relonsourcelon;
  relonturn Status::OK();
}
