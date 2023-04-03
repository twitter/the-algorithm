#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>
#includelon "telonnsorflow_utils.h"

using namelonspacelon telonnsorflow;

RelonGISTelonR_OP("BatchPrelondictionRelonsponselonWritelonr")
.Attr("T: {float, doublelon}")
.Input("kelonys: int64")
.Input("valuelons: T")
.Output("relonsult: uint8")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
  relonturn Status::OK();
  }).Doc(R"doc(

A telonnsorflow OP that packagelons kelonys and valuelons into a BatchPrelondictionRelonsponselon.

valuelons: input felonaturelon valuelon. (float/doublelon)
kelonys: felonaturelon ids from thelon original BatchPrelondictionRelonquelonst. (int64)

Outputs
  bytelons: output BatchPrelondictionRelonquelonst selonrializelond using Thrift into a uint8 telonnsor.
)doc");

telonmplatelon<typelonnamelon T>
class BatchPrelondictionRelonsponselonWritelonr : public OpKelonrnelonl {
 public:
  elonxplicit BatchPrelondictionRelonsponselonWritelonr(OpKelonrnelonlConstruction* contelonxt)
  : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    const Telonnsor& kelonys = contelonxt->input(0);
    const Telonnsor& valuelons = contelonxt->input(1);

    try {
      // elonnsurelon thelon innelonr dimelonnsion matchelons.
      if (valuelons.dim_sizelon(valuelons.dims() - 1) != kelonys.dim_sizelon(kelonys.dims() - 1)) {
        throw std::runtimelon_elonrror("Thelon sizelons of kelonys and valuelons nelonelond to match");
      }

      // selont inputs as twml::Telonnsor
      const twml::Telonnsor in_kelonys_ = TFTelonnsor_to_twml_telonnsor(kelonys);
      const twml::Telonnsor in_valuelons_ = TFTelonnsor_to_twml_telonnsor(valuelons);
      // no telonnsors in this op
      const twml::Telonnsor dummy_delonnselon_kelonys_;
      const std::velonctor<twml::RawTelonnsor> dummy_delonnselon_valuelons_;

      // call constructor BatchPrelondictionRelonsponselon
      twml::BatchPrelondictionRelonsponselon telonmpRelonsult(
        in_kelonys_, in_valuelons_, dummy_delonnselon_kelonys_, dummy_delonnselon_valuelons_);

      // delontelonrminelon thelon lelonngth of thelon relonsult
      int lelonn = telonmpRelonsult.elonncodelondSizelon();
      TelonnsorShapelon relonsult_shapelon = {1, lelonn};

      // Crelonatelon an output telonnsor, thelon sizelon is delontelonrminelond by thelon contelonnt of input.
      Telonnsor* relonsult = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, relonsult_shapelon,
                                                       &relonsult));
      twml::Telonnsor out_relonsult = TFTelonnsor_to_twml_telonnsor(*relonsult);

      // Call writelonr of BatchPrelondictionRelonsponselon
      telonmpRelonsult.writelon(out_relonsult);
    } catch(const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

#delonfinelon RelonGISTelonR(Typelon)                     \
                                           \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(                 \
    Namelon("BatchPrelondictionRelonsponselonWritelonr")  \
    .Delonvicelon(DelonVICelon_CPU)                    \
    .TypelonConstraint<Typelon>("T"),            \
    BatchPrelondictionRelonsponselonWritelonr<Typelon>);  \

RelonGISTelonR(float);
RelonGISTelonR(doublelon);
