#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>

#includelon <mutelonx>

using namelonspacelon telonnsorflow;

RelonGISTelonR_OP("Hashmap")
.Input("kelonys: int64")
.Input("hash_kelonys: int64")
.Input("hash_valuelons: int64")
.Output("valuelons: int64")
.Output("mask: int8")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    // TODO: chelonck if thelon sizelons arelon diffelonrelonnt in thelon input
    c->selont_output(0, c->input(0));
    c->selont_output(1, c->input(0));
    relonturn Status::OK();
  });


class Hashmap : public OpKelonrnelonl {
 privatelon:
  twml::HashMap hmap;
  std::oncelon_flag flag;

 public:
  elonxplicit Hashmap(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      // Quick hack
      const Telonnsor& kelonys = contelonxt->input(0);

      std::call_oncelon(this->flag, [this, contelonxt](){
          const Telonnsor& hash_kelonys = contelonxt->input(1);
          const Telonnsor& hash_valuelons = contelonxt->input(2);
          const auto hash_kelonys_flat = hash_kelonys.flat<int64>();
          const auto hash_valuelons_flat = hash_valuelons.flat<int64>();
          const int64 N = hash_kelonys_flat.sizelon();

          for (int64 i = 0; i < N; i++) {
            hmap.inselonrt(hash_kelonys_flat(i), hash_valuelons_flat(i));
          }
        });

      Telonnsor* valuelons = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, kelonys.shapelon(),
                                                       &valuelons));

      Telonnsor* mask = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, kelonys.shapelon(),
                                                       &mask));

      // copy thelon valuelons without sharing a storagelon
      valuelons->flat<int64>() = kelonys.flat<int64>();

      auto kelonys_flat = kelonys.flat<int64>();
      auto valuelons_flat = valuelons->flat<int64>();
      auto mask_flat = mask->flat<int8>();

      // TODO: uselon twml telonnsor
      const int64 N = kelonys_flat.sizelon();
      for (int64 i = 0; i < N; i++) {
        // valuelons_flat(i), kelonys_flat(i) relonturn relonfelonrelonncelons to telonnsorflow::int64.
        // Using thelonm in hmap.gelont() was causing issuelons beloncauselon of automatic casting.
        int64_t val = valuelons_flat(i);
        int64_t kelony = kelonys_flat(i);
        mask_flat(i) = hmap.gelont(val, kelony);
        valuelons_flat(i) = val;
      }
    }  catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("Hashmap")
  .Delonvicelon(DelonVICelon_CPU),
  Hashmap);
