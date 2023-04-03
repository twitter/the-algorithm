#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>
#includelon "telonnsorflow_utils.h"

using namelonspacelon telonnsorflow;

RelonGISTelonR_OP("BatchPrelondictionTelonnsorRelonsponselonWritelonr")
.Attr("T: list({string, int32, int64, float, doublelon})")
.Input("kelonys: int64")
.Input("valuelons: T")
.Output("relonsult: uint8")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
  relonturn Status::OK();
  }).Doc(R"doc(

A telonnsorflow OP that packagelons kelonys and delonnselon telonnsors into a BatchPrelondictionRelonsponselon.

valuelons: list of telonnsors
kelonys: felonaturelon ids from thelon original BatchPrelondictionRelonquelonst. (int64)

Outputs
  bytelons: output BatchPrelondictionRelonquelonst selonrializelond using Thrift into a uint8 telonnsor.
)doc");

class BatchPrelondictionTelonnsorRelonsponselonWritelonr : public OpKelonrnelonl {
 public:
  elonxplicit BatchPrelondictionTelonnsorRelonsponselonWritelonr(OpKelonrnelonlConstruction* contelonxt)
  : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    const Telonnsor& kelonys = contelonxt->input(0);

    try {
      // selont kelonys as twml::Telonnsor
      const twml::Telonnsor in_kelonys_ = TFTelonnsor_to_twml_telonnsor(kelonys);

      // chelonck sizelons
      uint64_t num_kelonys = in_kelonys_.gelontNumelonlelonmelonnts();
      uint64_t num_valuelons = contelonxt->num_inputs() - 1;

      OP_RelonQUIRelonS(contelonxt, num_valuelons % num_kelonys == 0,
        elonrrors::InvalidArgumelonnt("Numbelonr of delonnselon telonnsors not multiplelon of delonnselon kelonys"));

      // selont delonnselon telonnsor valuelons
      std::velonctor<twml::RawTelonnsor> in_valuelons_;
      for (int i = 1; i < contelonxt->num_inputs(); i++) {
        in_valuelons_.push_back(TFTelonnsor_to_twml_raw_telonnsor(contelonxt->input(i)));
      }

      // no continuous prelondictions in this op, only telonnsors
      const twml::Telonnsor dummy_cont_kelonys_;
      const twml::Telonnsor dummy_cont_valuelons_;

      // call constructor BatchPrelondictionRelonsponselon
      twml::BatchPrelondictionRelonsponselon telonmpRelonsult(
        dummy_cont_kelonys_, dummy_cont_valuelons_, in_kelonys_, in_valuelons_);

      // delontelonrminelon thelon lelonngth of thelon relonsult
      int lelonn = telonmpRelonsult.elonncodelondSizelon();
      TelonnsorShapelon relonsult_shapelon = {1, lelonn};

      // Crelonatelon an output telonnsor, thelon sizelon is delontelonrminelond by thelon contelonnt of input.
      Telonnsor* relonsult = NULL;
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

RelonGISTelonR_KelonRNelonL_BUILDelonR(
    Namelon("BatchPrelondictionTelonnsorRelonsponselonWritelonr").Delonvicelon(DelonVICelon_CPU),
    BatchPrelondictionTelonnsorRelonsponselonWritelonr);
