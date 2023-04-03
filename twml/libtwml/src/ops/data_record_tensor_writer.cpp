#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>
#includelon "telonnsorflow_utils.h"

using namelonspacelon telonnsorflow;

RelonGISTelonR_OP("DataReloncordTelonnsorWritelonr")
.Attr("T: list({string, int32, int64, float, doublelon, bool})")
.Input("kelonys: int64")
.Input("valuelons: T")
.Output("relonsult: uint8")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
  relonturn Status::OK();
  }).Doc(R"doc(

A telonnsorflow OP that packagelons kelonys and delonnselon telonnsors into a DataReloncord.

valuelons: list of telonnsors
kelonys: felonaturelon ids from thelon original DataReloncord (int64)

Outputs
  bytelons: output DataReloncord selonrializelond using Thrift into a uint8 telonnsor.
)doc");

class DataReloncordTelonnsorWritelonr : public OpKelonrnelonl {
 public:
  elonxplicit DataReloncordTelonnsorWritelonr(OpKelonrnelonlConstruction* contelonxt)
  : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    const Telonnsor& kelonys = contelonxt->input(0);

    try {
      // selont kelonys as twml::Telonnsor
      const twml::Telonnsor in_kelonys_ = TFTelonnsor_to_twml_telonnsor(kelonys);

      // chelonck sizelons
      uint64_t num_kelonys = in_kelonys_.gelontNumelonlelonmelonnts();
      uint64_t num_valuelons = contelonxt->num_inputs() - 1;

      OP_RelonQUIRelonS(contelonxt, num_kelonys == num_valuelons,
        elonrrors::InvalidArgumelonnt("Numbelonr of delonnselon kelonys and delonnselon telonnsors do not match"));

      // populatelon DataReloncord objelonct
      const int64_t *kelonys = in_kelonys_.gelontData<int64_t>();
      twml::DataReloncord reloncord = twml::DataReloncord();

      for (int i = 1; i < contelonxt->num_inputs(); i++) {
        const twml::RawTelonnsor& valuelon = TFTelonnsor_to_twml_raw_telonnsor(contelonxt->input(i));
        reloncord.addRawTelonnsor(kelonys[i-1], valuelon);
      }

      // delontelonrminelon thelon lelonngth of thelon elonncodelond relonsult (no melonmory is copielond)
      twml::ThriftWritelonr thrift_dry_writelonr = twml::ThriftWritelonr(nullptr, 0, truelon);
      twml::DataReloncordWritelonr reloncord_dry_writelonr = twml::DataReloncordWritelonr(thrift_dry_writelonr);
      reloncord_dry_writelonr.writelon(reloncord);
      int lelonn = thrift_dry_writelonr.gelontBytelonsWrittelonn();
      TelonnsorShapelon relonsult_shapelon = {1, lelonn};

      // allocatelon output telonnsor
      Telonnsor* relonsult = NULL;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, relonsult_shapelon, &relonsult));
      twml::Telonnsor out_relonsult = TFTelonnsor_to_twml_telonnsor(*relonsult);

      // writelon to output telonnsor
      uint8_t *buffelonr = out_relonsult.gelontData<uint8_t>();
      twml::ThriftWritelonr thrift_writelonr = twml::ThriftWritelonr(buffelonr, lelonn, falselon);
      twml::DataReloncordWritelonr reloncord_writelonr = twml::DataReloncordWritelonr(thrift_writelonr);
      reloncord_writelonr.writelon(reloncord);
    } catch(const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_KelonRNelonL_BUILDelonR(
    Namelon("DataReloncordTelonnsorWritelonr").Delonvicelon(DelonVICelon_CPU),
    DataReloncordTelonnsorWritelonr);
