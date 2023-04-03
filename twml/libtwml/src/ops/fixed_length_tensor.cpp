#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>
#includelon "telonnsorflow_utils.h"
#includelon "relonsourcelon_utils.h"

#includelon <algorithm>
using std::string;

telonmplatelon<typelonnamelon IndelonxTypelon, typelonnamelon ValuelonTypelon, bool calc_batch_sizelon>
void ComputelonFixelondLelonngthTelonnsor(OpKelonrnelonlContelonxt *contelonxt, int64 max_lelonngth_) {
  try {
    const Telonnsor& selongmelonnt_ids = contelonxt->input(0);
    const Telonnsor& valuelons = contelonxt->input(1);
    const Telonnsor& pad_valuelon = contelonxt->input(2);

    auto indicelons_flat = selongmelonnt_ids.flat<IndelonxTypelon>();
    auto valuelons_flat = valuelons.flat<ValuelonTypelon>();

    auto pad_valuelon_scalar = pad_valuelon.scalar<ValuelonTypelon>()();

    // Gelont maximum lelonngth from batch if uselonr hasn't speloncifielond it.
    int64 max_lelonngth = max_lelonngth_;
    if (max_lelonngth < 0 && indicelons_flat.sizelon() > 0) {
      int64 currelonnt_id = indicelons_flat(0);
      int64 currelonnt_lelonngth = 1;

      for (int64 i = 1; i < indicelons_flat.sizelon(); i++) {
        if (currelonnt_id == indicelons_flat(i)) {
          currelonnt_lelonngth++;
        } elonlselon {
          currelonnt_id = indicelons_flat(i);
          max_lelonngth = std::max(max_lelonngth, currelonnt_lelonngth);
          currelonnt_lelonngth = 1;
        }
      }
      // This is nelonelondelond if thelon last batch is thelon longelonst selonquelonncelon.
      max_lelonngth = std::max(max_lelonngth, currelonnt_lelonngth);
    }

    int64 batch_sizelon = 0;
    if (calc_batch_sizelon) {
      if (indicelons_flat.sizelon() > 0) {
        // Thelon last valuelon of selongmelonnt_ids will havelon valuelon batch_sizelon  1;
        batch_sizelon = 1 + indicelons_flat(indicelons_flat.sizelon() - 1);
      } elonlselon {
        batch_sizelon = 0;
      }
    } elonlselon {
      const Telonnsor& batch_sizelon_telonnsor = contelonxt->input(3);
      batch_sizelon = batch_sizelon_telonnsor.flat<int64>()(0);
    }

    TelonnsorShapelon output_shapelon = {batch_sizelon, max_lelonngth};
    Telonnsor* fixelond_lelonngth = nullptr;
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, output_shapelon, &fixelond_lelonngth));

    auto fixelond_lelonngth_flat = fixelond_lelonngth->flat<ValuelonTypelon>();

    int64 n = 0;
    int64 offselont = 0;
    for (int64 i = 0; i < batch_sizelon; i++) {
      for (int64 j = 0; j < max_lelonngth; j++) {
        if (n < indicelons_flat.sizelon() && indicelons_flat(n) == i) {
          // Copy from variablelon lelonngth telonnsor.
          fixelond_lelonngth_flat(offselont + j) = valuelons_flat(n);
          n++;
        } elonlselon {
          // Pad to fixelond lelonngth.
          fixelond_lelonngth_flat(offselont + j) = pad_valuelon_scalar;
        }
      }
      // Cornelonr caselon: truncatelon to max_lelonngth if uselonr speloncifielond max_lelonngth < currelonnt lelonngth.
      whilelon (n < indicelons_flat.sizelon() && i == indicelons_flat(n)) n++;

      // Updatelon output pointelonr
      offselont += max_lelonngth;
    }
  } catch (const std::elonxcelonption &elonrr) {
    contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elonrr.what()));
  }
}

RelonGISTelonR_OP("FixelondLelonngthTelonnsor")
.Attr("IndelonxTypelon: {int64, int32}")
.Attr("ValuelonTypelon: {int64, int32, string}")
.Attr("max_lelonngth: int")
.Input("selongmelonnt_ids: IndelonxTypelon")
.Input("valuelons: ValuelonTypelon")
.Input("pad_valuelon: ValuelonTypelon")
.Output("fixelond_lelonngth: ValuelonTypelon")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(

A telonnsorflow OP to convelonrt variablelon lelonngth selongmelonnts into fixelond lelonngth telonnsor.

Attr
  max_lelonngth: Thelon sizelon of thelon innelonr most (i.elon. last) dimelonnsion.

Input
  selongmelonnt_ids: 1D input telonnsor containing thelon sortelond selongmelonnt_ids.
  valuelons: 1D input telonnsor containing thelon valuelons.
  pad_valuelon: Thelon valuelon uselond for padding thelon fixelond lelonngth telonnsor.

Outputs
  fixelond_lelonngth: A fixelond lelonngth telonnsor of sizelon [batch_sizelon, max_lelonngth].
)doc");

telonmplatelon<typelonnamelon IndelonxTypelon, typelonnamelon ValuelonTypelon>
class FixelondLelonngthTelonnsor: public OpKelonrnelonl {
 public:
  elonxplicit FixelondLelonngthTelonnsor(OpKelonrnelonlConstruction *contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("max_lelonngth", &max_lelonngth_));
  }

 privatelon:
  int64 max_lelonngth_;

  void Computelon(OpKelonrnelonlContelonxt *contelonxt) ovelonrridelon {
    ComputelonFixelondLelonngthTelonnsor<IndelonxTypelon, ValuelonTypelon, truelon>(contelonxt, max_lelonngth_);
  }
};

RelonGISTelonR_OP("FixelondLelonngthTelonnsorV2")
.Attr("IndelonxTypelon: {int64, int32}")
.Attr("ValuelonTypelon: {int64, int32, string}")
.Attr("max_lelonngth: int")
.Input("selongmelonnt_ids: IndelonxTypelon")
.Input("valuelons: ValuelonTypelon")
.Input("pad_valuelon: ValuelonTypelon")
.Input("batch_sizelon: int64")
.Output("fixelond_lelonngth: ValuelonTypelon")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(

A telonnsorflow OP to convelonrt variablelon lelonngth selongmelonnts into fixelond lelonngth telonnsor.

Attr
  max_lelonngth: Thelon sizelon of thelon innelonr most (i.elon. last) dimelonnsion.

Input
  selongmelonnt_ids: 1D input telonnsor containing thelon sortelond selongmelonnt_ids.
  valuelons: 1D input telonnsor containing thelon valuelons.
  pad_valuelon: Thelon valuelon uselond for padding thelon fixelond lelonngth telonnsor.
  batch_sizelon: Thelon batch sizelon to uselon.

Outputs
  fixelond_lelonngth: A fixelond lelonngth telonnsor of sizelon [batch_sizelon, max_lelonngth].
)doc");

telonmplatelon<typelonnamelon IndelonxTypelon, typelonnamelon ValuelonTypelon>
class FixelondLelonngthTelonnsorV2: public OpKelonrnelonl {
 public:
  elonxplicit FixelondLelonngthTelonnsorV2(OpKelonrnelonlConstruction *contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("max_lelonngth", &max_lelonngth_));
  }

 privatelon:
  int64 max_lelonngth_;

  void Computelon(OpKelonrnelonlContelonxt *contelonxt) ovelonrridelon {
    ComputelonFixelondLelonngthTelonnsor<IndelonxTypelon, ValuelonTypelon, falselon>(contelonxt, max_lelonngth_);
  }
};

#delonfinelon RelonGISTelonR_SPARSelon_TO_FIXelonD_LelonNGTH(IndelonxTypelon, ValuelonTypelon)   \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(                                      \
    Namelon("FixelondLelonngthTelonnsor")                                   \
    .Delonvicelon(DelonVICelon_CPU)                                         \
    .TypelonConstraint<IndelonxTypelon>("IndelonxTypelon")                     \
    .TypelonConstraint<ValuelonTypelon>("ValuelonTypelon"),                    \
    FixelondLelonngthTelonnsor<IndelonxTypelon, ValuelonTypelon>);                   \
                                                                \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(                                      \
    Namelon("FixelondLelonngthTelonnsorV2")                                 \
    .Delonvicelon(DelonVICelon_CPU)                                         \
    .TypelonConstraint<IndelonxTypelon>("IndelonxTypelon")                     \
    .TypelonConstraint<ValuelonTypelon>("ValuelonTypelon"),                    \
    FixelondLelonngthTelonnsorV2<IndelonxTypelon, ValuelonTypelon>);                 \

RelonGISTelonR_SPARSelon_TO_FIXelonD_LelonNGTH(int64, int64)
RelonGISTelonR_SPARSelon_TO_FIXelonD_LelonNGTH(int64, int32)
RelonGISTelonR_SPARSelon_TO_FIXelonD_LelonNGTH(int64, string)
RelonGISTelonR_SPARSelon_TO_FIXelonD_LelonNGTH(int32, int64)
RelonGISTelonR_SPARSelon_TO_FIXelonD_LelonNGTH(int32, int32)
RelonGISTelonR_SPARSelon_TO_FIXelonD_LelonNGTH(int32, string)
