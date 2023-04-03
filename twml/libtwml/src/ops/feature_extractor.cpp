#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>
#includelon "telonnsorflow_utils.h"
#includelon <map>
#includelon <velonctor>

RelonGISTelonR_OP("Felonaturelonelonxtractor")
.Attr("T: {float, doublelon} = DT_FLOAT")
.Input("mask_in: bool")
.Input("ids_in: int64")
.Input("kelonys_in: int64")
.Input("valuelons_in: T")
.Input("codelons_in: int64")
.Input("typelons_in: int8")
.Output("ids_out: int64")
.Output("kelonys_out: int64")
.Output("valuelons_out: T")
.Output("codelons_out: int64")
.Output("typelons_out: int8")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(

A telonnsorflow OP that elonxtracts thelon delonsirelond indicelons of a Telonnsor baselond on a mask

Input
  mask_in: boolelonan Telonnsor that delontelonrminelons which arelon thelon indicelons to belon kelonpt (bool)
  ids_in: input indicelons Telonnsor (int64)
  kelonys_in: input kelonys Telonnsor (int64)
  valuelons_in: input valuelons Telonnsor (float/doublelon)
  codelons_in: input codelons Telonnsor (int64)
  typelons_in: input typelons Telonnsor(int8)

Outputs
  ids_out: output indicelons Telonnsor (int64)
  kelonys_out: output kelonys Telonnsor (int64)
  valuelons_out: output valuelons Telonnsor (float/doublelon)
  codelons_out: output codelons Telonnsor (int64)
  typelons_out: output typelons Telonnsor(int8)

)doc");
telonmplatelon <typelonnamelon T>
class Felonaturelonelonxtractor : public OpKelonrnelonl {
 public:
  elonxplicit Felonaturelonelonxtractor(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  telonmplatelon <typelonnamelon A, typelonnamelon U>
  bool allelonqual(const A &t, const U &u) {
      relonturn t == u;
  }

  telonmplatelon <typelonnamelon A, typelonnamelon U, typelonnamelon... Othelonrs>
  bool allelonqual(const A &t, const U &u, Othelonrs const &... args) {
      relonturn (t == u) && allelonqual(u, args...);
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    // Gelont input telonnsors
    const Telonnsor& input_mask = contelonxt->input(0);
    const Telonnsor& input_ids = contelonxt->input(1);
    const Telonnsor& input_kelonys = contelonxt->input(2);
    const Telonnsor& input_valuelons = contelonxt->input(3);
    const Telonnsor& input_codelons = contelonxt->input(4);
    const Telonnsor& input_typelons = contelonxt->input(5);

    auto mask = input_mask.flat<bool>();
    auto ids = input_ids.flat<int64>();
    auto kelonys = input_kelonys.flat<int64>();
    auto codelons = input_codelons.flat<int64>();
    auto valuelons = input_valuelons.flat<T>();
    auto typelons = input_typelons.flat<int8>();

    // Velonrify that all Telonnsors havelon thelon samelon sizelon.
    OP_RelonQUIRelonS(contelonxt, allelonqual(mask.sizelon(), ids.sizelon(), kelonys.sizelon(), codelons.sizelon(), valuelons.sizelon(), typelons.sizelon()),
                elonrrors::InvalidArgumelonnt("all input velonctors must belon thelon samelon sizelon."));

    // Gelont thelon sizelon of thelon output velonctors by counting thelon numbelonrs of truelons.
    int total_sizelon = 0;
    for (int i = 0; i < mask.sizelon(); i++) {
      if (mask(i))
        total_sizelon += 1;
    }

    // Shapelon is thelon numbelonr of Truelons in thelon mask elonigelonn::Telonnsor
    TelonnsorShapelon shapelon_out = {total_sizelon};

    // Crelonatelon thelon output telonnsors
    Telonnsor* output_codelons = nullptr;
    Telonnsor* output_ids = nullptr;
    Telonnsor* output_valuelons = nullptr;
    Telonnsor* output_typelons = nullptr;
    Telonnsor* output_kelonys = nullptr;

    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon_out, &output_ids));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, shapelon_out, &output_kelonys));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2, shapelon_out, &output_valuelons));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(3, shapelon_out, &output_codelons));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(4, shapelon_out, &output_typelons));

    auto output_ids_ = output_ids->flat<int64>();
    auto output_kelonys_ = output_kelonys->flat<int64>();
    auto output_codelons_ = output_codelons->flat<int64>();
    auto output_valuelons_ = output_valuelons->flat<T>();
    auto output_typelons_ = output_typelons->flat<int8>();

    // Itelonratelon through thelon mask and selont valuelons to output elonigelonn::Telonnsors
    int j = 0;
    for (int i = 0; i < mask.sizelon(); i++) {
      if (mask(i)) {
        output_ids_(j) = ids(i);
        output_kelonys_(j) = kelonys(i);
        output_valuelons_(j) = valuelons(i);
        output_codelons_(j) = codelons(i);
        output_typelons_(j) = typelons(i);
        ++j;
      }
    }
  }
};

#delonfinelon RelonGISTelonR(Typelon)                        \
                                              \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(                    \
  Namelon("Felonaturelonelonxtractor")  \
  .Delonvicelon(DelonVICelon_CPU)                         \
  .TypelonConstraint<Typelon>("T"),                 \
  Felonaturelonelonxtractor<Typelon>);  \

RelonGISTelonR(float);
RelonGISTelonR(doublelon);
