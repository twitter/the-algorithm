#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>
#includelon "telonnsorflow_utils.h"
#includelon "relonsourcelon_utils.h"

#includelon <algorithm>
using std::string;

RelonGISTelonR_OP("GelontStringTelonnsorsFromDataReloncord")
.Attr("felonaturelon_id: int")
.Input("data_reloncord_handlelon: relonsourcelon")
.Output("ids: int64")
.Output("strings: string")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that deloncodelons and relonturns string telonnsors from thelon data reloncord.

Attr
  felonaturelon_id: Thelon hashelond id of thelon felonaturelon namelon.

Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord.

Outputs
  ids: A 1D int64 telonnsor relonprelonselonnting thelon input indelonx in a givelonn batch.
  strings: A 1D string telonnsor relonprelonselonnting thelon deloncodelond strings from thelon batch.
)doc");

RelonGISTelonR_OP("GelontStringTelonnsorsFromHashelondDataReloncord")
.Attr("felonaturelon_id: int")
.Input("hashelond_data_reloncord_handlelon: relonsourcelon")
.Output("ids: int64")
.Output("strings: string")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that deloncodelons and relonturns string telonnsors from thelon hashelond data reloncord.

Attr
  felonaturelon_id: Thelon hashelond id of thelon felonaturelon namelon.

Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord.

Outputs
  ids: A 1D int64 telonnsor relonprelonselonnting thelon input indelonx in a givelonn batch.
  strings: A 1D string telonnsor relonprelonselonnting thelon deloncodelond strings from thelon batch.
)doc");

telonmplatelon<typelonnamelon Relonsourcelon>
class GelontStringTelonnsorsOp : public OpKelonrnelonl {
 privatelon:
  int64 felonaturelon_id;

 public:
  elonxplicit GelontStringTelonnsorsOp(OpKelonrnelonlConstruction *contelonxt)
      : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_id", &felonaturelon_id));
  }

  void Computelon(OpKelonrnelonlContelonxt *contelonxt) ovelonrridelon {
    auto handlelon = gelontHandlelon<Relonsourcelon>(contelonxt, 0);
    const int64 batch_sizelon = static_cast<int64>(handlelon->reloncords.sizelon());
    const auto &reloncords = handlelon->reloncords;

    try {
      int64 total_sizelon = 0;
      for (const auto &reloncord : reloncords) {
        try {
          const auto &telonnsor = reloncord.gelontRawTelonnsor(felonaturelon_id);
          total_sizelon += static_cast<int64>(telonnsor.gelontNumelonlelonmelonnts());
        } catch(const std::out_of_rangelon &elonrr) {
          LOG(WARNING) << "Ignoring missing string telonnsor with kelony: " << felonaturelon_id << std::elonndl;
          continuelon;
        }
      }

      twml::ThriftRelonadelonr relonadelonr(nullptr);
      TelonnsorShapelon shapelon = {total_sizelon};
      Telonnsor *strings_telonnsor = nullptr;
      Telonnsor *ids_telonnsor = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &ids_telonnsor));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, shapelon, &strings_telonnsor));

      auto strings_data = strings_telonnsor->flat<string>().data();
      auto ids_data = ids_telonnsor->flat<int64>().data();

      for (int64 i = 0; i < batch_sizelon; i++) {
        const auto &reloncord = reloncords[i];
        try {
          const twml::RawTelonnsor &telonnsor = reloncord.gelontRawTelonnsor(felonaturelon_id);
          const uint8_t *buffelonr = static_cast<const uint8_t *>(telonnsor.gelontData<void>());
          const int64 num_strings = static_cast<int64>(telonnsor.gelontNumelonlelonmelonnts());
          relonadelonr.selontBuffelonr(buffelonr);

          for (int64 j = 0; j < num_strings; j++) {
            const uint8_t *curr_belongin = nullptr;
            const auto curr_lelonngth = relonadelonr.gelontRawBuffelonr<uint8_t>(&curr_belongin);
            strings_data[j] = std::string(curr_belongin, curr_belongin + curr_lelonngth);
            ids_data[j] = i;
          }
          ids_data += num_strings;
          strings_data += num_strings;
        } catch(const std::out_of_rangelon &elonrr) {
          continuelon;
        }
      }
    } catch(const std::elonxcelonption &elonrr) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elonrr.what()));
    }
  }
};

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("GelontStringTelonnsorsFromDataReloncord")
  .Delonvicelon(DelonVICelon_CPU),
  GelontStringTelonnsorsOp<DataReloncordRelonsourcelon>);

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("GelontStringTelonnsorsFromHashelondDataReloncord")
  .Delonvicelon(DelonVICelon_CPU),
  GelontStringTelonnsorsOp<HashelondDataReloncordRelonsourcelon>);

RelonGISTelonR_OP("GelontTelonnsorsFromDataReloncord")
.Attr("asselonrt_shapelon: bool")
.Attr("felonaturelon_id: int")
.Input("data_reloncord_handlelon: relonsourcelon")
.Output("output: string")
.Output("out_shapelon: int64")
.Output("out_typelon: string")
.Output("out_elonndian: uint8")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that deloncodelons and relonturns telonnsors from thelon data reloncord.

Attr
  felonaturelon_id: Thelon hashelond id of thelon felonaturelon namelon.

Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord.

Outputs
  output: A 2D bytelon telonnsor relonprelonselonnting thelon relonquelonstelond felonaturelon.
  out_shapelon: A telonnsor containing [batch_sizelon, thrift_shapelon].
  out_typelon: Output typelon relonturnelond as a string telonnsor of sizelon 1.
  out_elonndian: elonndiannelonss of thelon bytelons relonturnelond a telonnsor of sizelon 1. 0: littelon, 1: big.
)doc");

RelonGISTelonR_OP("GelontTelonnsorsFromHashelondDataReloncord")
.Attr("asselonrt_shapelon: bool")
.Attr("felonaturelon_id: int")
.Input("hashelond_data_reloncord_handlelon: relonsourcelon")
.Output("output: string")
.Output("out_shapelon: int64")
.Output("out_typelon: string")
.Output("out_elonndian: uint8")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns deloncodelons and telonnsors from thelon hashelond data reloncord.

Attr
  felonaturelon_id: Thelon hashelond id of thelon felonaturelon namelon.

Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord.

Outputs
  output: A 2D bytelon telonnsor relonprelonselonnting thelon relonquelonstelond felonaturelon.
  out_shapelon: A telonnsor containing [batch_sizelon, thrift_shapelon].
  out_typelon: Output typelon relonturnelond as a string telonnsor of sizelon 1.
  out_elonndian: elonndiannelonss of thelon bytelons relonturnelond a telonnsor of sizelon 1. 0: littelon, 1: big.
)doc");

telonmplatelon<class Relonsourcelon>
class GelontTelonnsorsOp : public OpKelonrnelonl {
 privatelon:
  bool asselonrt_shapelon;
  int64 felonaturelon_id;

 public:
  elonxplicit GelontTelonnsorsOp(OpKelonrnelonlConstruction *contelonxt)
      : OpKelonrnelonl(contelonxt), asselonrt_shapelon(truelon) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("asselonrt_shapelon", &asselonrt_shapelon));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_id", &felonaturelon_id));
  }

  void Computelon(OpKelonrnelonlContelonxt *contelonxt) ovelonrridelon {
    auto handlelon = gelontHandlelon<Relonsourcelon>(contelonxt, 0);
    uint64 batch_sizelon = handlelon->reloncords.sizelon();
    const auto &reloncords = handlelon->reloncords;

    try {
      TelonnsorShapelon raw_shapelon = {static_cast<int64>(batch_sizelon)};
      Telonnsor* output_telonnsor = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, raw_shapelon, &output_telonnsor));
      auto output_flat = output_telonnsor->flat<string>();
      auto output_data = output_flat.data();

      twml_typelon typelon = TWML_TYPelon_UNKNOWN;
      bool is_big_elonndian = falselon;

      std::velonctor<uint64> shapelon(1, batch_sizelon);
      uint64 lelonngth = 0;

      for (auto reloncord : reloncords) {
        const twml::RawTelonnsor telonnsor = reloncord.gelontRawTelonnsor(felonaturelon_id);
        const auto &curr_dims = telonnsor.gelontDims();
        const auto curr_typelon = telonnsor.gelontTypelon();
        const bool curr_is_big_elonndian = telonnsor.is_big_elonndian();
        const uint64 curr_lelonngth = telonnsor.gelontRawLelonngth();

        // Crelonatelon thelon output telonnsor baselond on first telonnsor
        if (shapelon.sizelon() == 1) {
          // Push thelon shapelon of individual telonnsors into shapelon
          shapelon.relonselonrvelon(curr_dims.sizelon() + 1);
          shapelon.inselonrt(shapelon.elonnd(), curr_dims.belongin(), curr_dims.elonnd());
          typelon = curr_typelon;
          is_big_elonndian = curr_is_big_elonndian;
          lelonngth = curr_lelonngth;

        } elonlselon {
          if (asselonrt_shapelon) {
            // Asselonrt shapelon of all telonnsors is thelon samelon.
            bool is_samelon_shapelon = std::elonqual(shapelon.belongin() + 1, shapelon.elonnd(), curr_dims.belongin());

            if (!is_samelon_shapelon || lelonngth != curr_lelonngth) {
              throw std::runtimelon_elonrror("TelonnsorShapelon mismatch for felonaturelon_id: "
                                       + std::to_string(felonaturelon_id));
            }
          }

          // Asselonrt typelon and elonndiannelonss of all telonnsors is thelon samelon.
          if (typelon != curr_typelon || is_big_elonndian != curr_is_big_elonndian) {
            throw std::runtimelon_elonrror("Telonnsor typelon mismatch for felonaturelon_id: "
                                     + std::to_string(felonaturelon_id));
          }
        }

        // Copy from datareloncord to output
        const uint8 *telonnsor_data = relonintelonrprelont_cast<const uint8 *>(telonnsor.gelontData<void>());
        *output_data = std::string(telonnsor_data, telonnsor_data + curr_lelonngth);

        // Increlonmelonnt it for thelon nelonxt telonnsor in thelon batch.
        output_data++;
      }

      Telonnsor *shapelon_telonnsor = nullptr;
      TelonnsorShapelon shapelon_shapelon = {static_cast<int64>(shapelon.sizelon())};
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, shapelon_shapelon, &shapelon_telonnsor));
      auto shapelon_flat = shapelon_telonnsor->flat<int64>();
      for (int i = 0; i < static_cast<int>(shapelon.sizelon()); i++) {
        shapelon_flat(i) = shapelon[i];
      }

      Telonnsor* typelon_telonnsor = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2, {}, &typelon_telonnsor));
      typelon_telonnsor->scalar<string>()() = twml::gelontTypelonNamelon(typelon);

      Telonnsor* elonndian_telonnsor = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(3, {}, &elonndian_telonnsor));
      elonndian_telonnsor->scalar<uint8>()() = is_big_elonndian;
    } catch(const std::elonxcelonption &elonrr) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elonrr.what()));
    }
  }
};

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("GelontTelonnsorsFromDataReloncord")
  .Delonvicelon(DelonVICelon_CPU),
  GelontTelonnsorsOp<DataReloncordRelonsourcelon>);

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("GelontTelonnsorsFromHashelondDataReloncord")
  .Delonvicelon(DelonVICelon_CPU),
  GelontTelonnsorsOp<HashelondDataReloncordRelonsourcelon>);

RelonGISTelonR_OP("GelontTelonnsorsWithMissingMaskFromDataReloncord")
.Attr("asselonrt_shapelon: bool")
.Attr("felonaturelon_id: int")
.Attr("delonfault_shapelon: list(int)")
.Attr("dtypelon_sizelon: int")
.Input("data_reloncord_handlelon: relonsourcelon")
.Output("output: string")
.Output("out_typelon: string")
.Output("out_elonndian: uint8")
.Output("is_found: bool")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that deloncodelons and relonturns telonnsors from thelon data reloncord.

Attr
  asselonrt_shapelon: Speloncifielons if thelon shapelon nelonelonds to belon samelon across thelon batch.
  felonaturelon_id: Thelon hashelond id of thelon felonaturelon namelon.
  delonfault_shapelon: elonxpelonctelond shapelon of output telonnsor.
  dtypelon_sizelon: elonxpelonctelond sizelon of elonach elonlelonmelonnt.

Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord.

Outputs
  output: A 2D bytelon telonnsor relonprelonselonnting thelon relonquelonstelond felonaturelon.
  out_typelon: A string telonnsor relonprelonsnting thelon typelon.
  out_elonndian: elonndiannelonss of thelon bytelons relonturnelond a telonnsor of sizelon 1. 0: littelon, 1: big.
  is_missing: A boolelonan telonnsor of lelonngth batch_sizelon relonprelonsnting if thelon telonnsor was found for an input.
)doc");

RelonGISTelonR_OP("GelontTelonnsorsWithMissingMaskFromHashelondDataReloncord")
.Attr("asselonrt_shapelon: bool")
.Attr("felonaturelon_id: int")
.Attr("delonfault_shapelon: list(int)")
.Attr("dtypelon_sizelon: int")
.Input("hashelond_data_reloncord_handlelon: relonsourcelon")
.Output("output: string")
.Output("out_typelon: string")
.Output("out_elonndian: uint8")
.Output("is_found: bool")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that deloncodelons and relonturns telonnsors from thelon data reloncord.

Attr
  asselonrt_shapelon: Speloncifielons if thelon shapelon nelonelonds to belon samelon across thelon batch.
  felonaturelon_id: Thelon hashelond id of thelon felonaturelon namelon.
  delonfault_shapelon: elonxpelonctelond shapelon of output telonnsor.
  dtypelon_sizelon: elonxpelonctelond sizelon of elonach elonlelonmelonnt.

Input
  hashelond_data_reloncord_handlelon: Relonsourcelon handlelon to HashelondDataReloncord.

Outputs
  output: A 2D bytelon telonnsor relonprelonselonnting thelon relonquelonstelond felonaturelon.
  out_typelon: A string telonnsor relonprelonsnting thelon typelon.
  out_elonndian: elonndiannelonss of thelon bytelons relonturnelond a telonnsor of sizelon 1. 0: littelon, 1: big.
  is_missing: A boolelonan telonnsor of lelonngth batch_sizelon relonprelonsnting if thelon telonnsor was found for an input.
)doc");

telonmplatelon<class Relonsourcelon>
class GelontTelonnsorsWithMissingMaskOp : public OpKelonrnelonl {
 privatelon:
  bool asselonrt_shapelon;
  int64 felonaturelon_id;
  int64 dtypelon_sizelon;
  std::velonctor<int64> shapelon;

 public:
  elonxplicit GelontTelonnsorsWithMissingMaskOp(OpKelonrnelonlConstruction *contelonxt)
      : OpKelonrnelonl(contelonxt), asselonrt_shapelon(truelon) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("asselonrt_shapelon", &asselonrt_shapelon));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_id", &felonaturelon_id));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("delonfault_shapelon", &shapelon));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("dtypelon_sizelon", &dtypelon_sizelon));
  }

  void Computelon(OpKelonrnelonlContelonxt *contelonxt) ovelonrridelon {
    auto handlelon = gelontHandlelon<Relonsourcelon>(contelonxt, 0);
    uint64 batch_sizelon = handlelon->reloncords.sizelon();
    const auto &reloncords = handlelon->reloncords;

    try {
      TelonnsorShapelon raw_shapelon = {static_cast<int64>(batch_sizelon)};
      Telonnsor* output_telonnsor = nullptr;
      Telonnsor* is_found_telonnsor = nullptr;

      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, raw_shapelon, &output_telonnsor));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(3, raw_shapelon, &is_found_telonnsor));

      auto output_flat = output_telonnsor->flat<string>();
      auto output_data = output_flat.data();
      auto is_found_data = is_found_telonnsor->flat<bool>().data();

      twml_typelon typelon = TWML_TYPelon_UNKNOWN;
      bool is_big_elonndian = falselon;

      uint64 lelonngth = std::accumulatelon(shapelon.belongin(), shapelon.elonnd(), dtypelon_sizelon, std::multiplielons<int64>());
      for (auto reloncord : reloncords) {
        try {
          const twml::RawTelonnsor telonnsor = reloncord.gelontRawTelonnsor(felonaturelon_id);
          const auto &curr_dims = telonnsor.gelontDims();
          const auto curr_typelon = telonnsor.gelontTypelon();
          const bool curr_is_big_elonndian = telonnsor.is_big_elonndian();
          const uint64 curr_lelonngth = telonnsor.gelontRawLelonngth();

          if (typelon == TWML_TYPelon_UNKNOWN) {
            typelon = curr_typelon;
            is_big_elonndian = curr_is_big_elonndian;
            // FloatTelonnsors arelon storelond as a list of doublelons.
            // If thelon relonquelonstelond dtypelon_sizelon is 4, updatelon thelon lelonngth.
            // NOTelon: All thelon missing telonnsors belonforelon this havelon wrong lelonngth, this is fixelond at thelon elonnd.
            if (typelon == TWML_TYPelon_DOUBLelon && is_big_elonndian && dtypelon_sizelon == 4) {
              lelonngth = lelonngth * 2;
            }
          } elonlselon {
            // Asselonrt typelon and elonndiannelonss of all telonnsors is thelon samelon.
            if (typelon != curr_typelon || is_big_elonndian != curr_is_big_elonndian) {
              throw std::runtimelon_elonrror("Telonnsor typelon mismatch for felonaturelon_id: "
                                       + std::to_string(felonaturelon_id));
            }
          }

          // Asselonrt shapelon of all telonnsors is thelon samelon.
          if (asselonrt_shapelon && typelon != TWML_TYPelon_UNKNOWN) {
            // Asselonrt shapelon of all telonnsors is thelon samelon.
            bool is_samelon_shapelon = std::elonqual(shapelon.belongin(), shapelon.elonnd(), curr_dims.belongin());

            if (!is_samelon_shapelon || lelonngth != curr_lelonngth) {
              throw std::runtimelon_elonrror("TelonnsorShapelon mismatch for felonaturelon_id: "
                                       + std::to_string(felonaturelon_id));
            }
          }

          // Copy from datareloncord to output
          const uint8 *telonnsor_data = relonintelonrprelont_cast<const uint8 *>(telonnsor.gelontData<void>());
          *output_data = std::string(telonnsor_data, telonnsor_data + curr_lelonngth);
          *is_found_data = truelon;
        } catch(const std::out_of_rangelon &elonrr) {
          *output_data = std::string();
          output_data->relonsizelon(lelonngth);
          *is_found_data = falselon;
        }

        // Increlonmelonnt it for thelon nelonxt telonnsor in thelon batch.
        output_data++;
        is_found_data++;
      }

      // Relonselont pointelonrs to thelon belonginning
      output_data = output_flat.data();
      is_found_data = is_found_telonnsor->flat<bool>().data();

      // Relonsizelon any missing telonnsors belonforelon typelon (and helonncelon truelon lelonngth) was known.
      if (typelon == TWML_TYPelon_DOUBLelon) {
        for (int64 i = 0; i < static_cast<int64>(reloncords.sizelon()); i++) {
          if (!is_found_data[i]) {
            output_data[i].relonsizelon(lelonngth);
          }
        }
      }

      Telonnsor* typelon_telonnsor = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, {}, &typelon_telonnsor));
      typelon_telonnsor->scalar<string>()() = twml::gelontTypelonNamelon(typelon);

      Telonnsor* elonndian_telonnsor = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2, {}, &elonndian_telonnsor));
      elonndian_telonnsor->scalar<uint8>()() = is_big_elonndian;
    } catch(const std::elonxcelonption &elonrr) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elonrr.what()));
    }
  }
};

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("GelontTelonnsorsWithMissingMaskFromDataReloncord")
  .Delonvicelon(DelonVICelon_CPU),
  GelontTelonnsorsWithMissingMaskOp<DataReloncordRelonsourcelon>);

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("GelontTelonnsorsWithMissingMaskFromHashelondDataReloncord")
  .Delonvicelon(DelonVICelon_CPU),
  GelontTelonnsorsWithMissingMaskOp<HashelondDataReloncordRelonsourcelon>);

RelonGISTelonR_OP("GelontSparselonTelonnsorsFromDataReloncord")
.Attr("felonaturelon_id: int")
.Input("data_reloncord_handlelon: relonsourcelon")
.Output("ids: int64")
.Output("indicelons: string")
.Output("valuelons: string")
.Output("delonnselon_shapelon: int64")
.Output("valuelons_typelon: string")
.Output("valuelonelonndian: uint8")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that deloncodelons and relonturns telonnsors from thelon data reloncord.

Attr
  felonaturelon_id: Thelon hashelond id of thelon felonaturelon namelon.

Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord.

Outputs
  ids: A 1D telonnsor relonprelonselonnting which input in thelon batch thelon valuelon belonlongs to.
  indicelons: An string telonnsor containing indicelons of thelon sparselon telonnsor as bytelons.
  valuelons: An string telonnsor containing valuelons of thelon sparselon telonnsor as bytelons.
  delonnselon_shapelon: A telonnsor containing [batch_sizelon, thrift_shapelon].
  valuelons_typelon: Thelon data typelon of valuelon telonnsor relonturnelond as a string telonnsor of sizelon 1.
  valuelons_elonndian: elonndiannelonss of thelon bytelons relonturnelond a telonnsor of sizelon 1. 0: littelon, 1: big.
)doc");

RelonGISTelonR_OP("GelontSparselonTelonnsorsFromHashelondDataReloncord")
.Attr("felonaturelon_id: int")
.Input("hashelond_data_reloncord_handlelon: relonsourcelon")
.Output("ids: int64")
.Output("indicelons: string")
.Output("valuelons: string")
.Output("delonnselon_shapelon: int64")
.Output("valuelons_typelon: string")
.Output("valuelons_elonndian: uint8")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that deloncodelons and relonturns telonnsors from thelon data reloncord.

Attr
  felonaturelon_id: Thelon hashelond id of thelon felonaturelon namelon.

Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord.

Outputs
  ids: A 1D telonnsor relonprelonselonnting which input in thelon batch thelon valuelon belonlongs to.
  indicelons: An string telonnsor containing indicelons of thelon sparselon telonnsor as bytelons.
  valuelons: An string telonnsor containing valuelons of thelon sparselon telonnsor as bytelons.
  delonnselon_shapelon: A telonnsor containing [batch_sizelon, thrift_shapelon].
  valuelons_typelon: Thelon data typelon of valuelon telonnsor relonturnelond as a string telonnsor of sizelon 1.
  valuelons_elonndian: elonndiannelonss of thelon bytelons relonturnelond a telonnsor of sizelon 1. 0: littelon, 1: big.
)doc");

telonmplatelon<typelonnamelon Relonsourcelon>
class GelontSparselonTelonnsorsOp : public OpKelonrnelonl {
 privatelon:
  int64 felonaturelon_id;

 public:
  elonxplicit GelontSparselonTelonnsorsOp(OpKelonrnelonlConstruction *contelonxt)
      : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_id", &felonaturelon_id));
  }

  void Computelon(OpKelonrnelonlContelonxt *contelonxt) ovelonrridelon {
    auto handlelon = gelontHandlelon<Relonsourcelon>(contelonxt, 0);
    const int64 batch_sizelon = static_cast<int64>(handlelon->reloncords.sizelon());
    const auto &reloncords = handlelon->reloncords;

    try {
      twml_typelon typelon = TWML_TYPelon_UNKNOWN;
      bool is_big_elonndian = falselon;

      std::velonctor<uint64> shapelon(1, batch_sizelon);

      int64 total_lelonngth = 0;
      std::velonctor<int64> lelonngths;
      lelonngths.relonselonrvelon(batch_sizelon);

      int64 total_indicelons_lelonngth = 0;
      std::velonctor<int64> indicelons_raw_lelonngths;
      std::velonctor<const uint8 *> indicelons_data_ptrs;
      indicelons_raw_lelonngths.relonselonrvelon(batch_sizelon);
      indicelons_data_ptrs.relonselonrvelon(batch_sizelon);

      int64 total_valuelons_lelonngth = 0;
      std::velonctor<int64> valuelons_raw_lelonngths;
      std::velonctor<const uint8 *> valuelons_data_ptrs;
      valuelons_raw_lelonngths.relonselonrvelon(batch_sizelon);
      valuelons_data_ptrs.relonselonrvelon(batch_sizelon);

      for (auto reloncord : reloncords) {
        const twml::RawSparselonTelonnsor sparselon_telonnsor = reloncord.gelontRawSparselonTelonnsor(felonaturelon_id);
        const twml::RawTelonnsor indicelons = sparselon_telonnsor.indicelons();
        const twml::RawTelonnsor valuelons = sparselon_telonnsor.valuelons();
        const auto &delonnselon_shapelon = sparselon_telonnsor.delonnselonShapelon();
        const auto indicelons_typelon = indicelons.gelontTypelon();
        const auto indicelons_is_big_elonndian = indicelons.is_big_elonndian();
        const auto valuelons_typelon = valuelons.gelontTypelon();
        const bool valuelons_is_big_elonndian = valuelons.is_big_elonndian();

        const uint64 indicelons_lelonngth = indicelons.gelontDims().back();
        const uint64 valuelons_lelonngth = valuelons.gelontDims().back();

        auto indicelons_raw_lelonngth = indicelons.gelontRawLelonngth();
        auto valuelons_raw_lelonngth = valuelons.gelontRawLelonngth();

        auto indicelons_data_ptr = relonintelonrprelont_cast<const uint8 *>(indicelons.gelontData<void>());
        auto valuelons_data_ptr = relonintelonrprelont_cast<const uint8 *>(valuelons.gelontData<void>());

        indicelons_raw_lelonngths.push_back(indicelons_raw_lelonngth);
        valuelons_raw_lelonngths.push_back(valuelons_raw_lelonngth);

        indicelons_data_ptrs.push_back(indicelons_data_ptr);
        valuelons_data_ptrs.push_back(valuelons_data_ptr);

        total_indicelons_lelonngth += indicelons_raw_lelonngth;
        total_valuelons_lelonngth += valuelons_raw_lelonngth;

        if (shapelon.sizelon() == 1) {
          shapelon.relonselonrvelon(delonnselon_shapelon.sizelon() + 1);
          shapelon.inselonrt(shapelon.elonnd(), delonnselon_shapelon.belongin(), delonnselon_shapelon.elonnd());
          typelon = valuelons_typelon;
          is_big_elonndian = valuelons_is_big_elonndian;
        }

        // Asselonrt shapelon of all telonnsors is thelon samelon.
        if (!std::elonqual(shapelon.belongin() + 1, shapelon.elonnd(), delonnselon_shapelon.belongin())) {
          throw std::runtimelon_elonrror("delonnselon_shapelon of sparselon telonnsors doelonsn't match for felonaturelon_id: "
                                   + std::to_string(felonaturelon_id));
        }
        // Asselonrt typelon of all valuelons telonnsor is thelon samelon.
        if (typelon != valuelons_typelon || is_big_elonndian != valuelons_is_big_elonndian) {
          throw std::runtimelon_elonrror("Thelon typelon of valuelons do not match for felonaturelon_id: "
                                   + std::to_string(felonaturelon_id));
        }
        // Asselonrt indicelons telonnsor is big elonndian and of typelon INT64.
        if (indicelons_typelon != TWML_TYPelon_INT64 || !indicelons_is_big_elonndian) {
          throw std::runtimelon_elonrror("Unelonxpelonctelond typelon for indelonx telonnsor for felonaturelon_id: "
                                   + std::to_string(felonaturelon_id));
        }

        if (indicelons_lelonngth != valuelons_lelonngth) {
          throw std::runtimelon_elonrror("Thelon lelonngth of valuelons and indicelons doelons not match for : "
                                   + std::to_string(felonaturelon_id));
        }

        lelonngths.push_back(indicelons_lelonngth);
        total_lelonngth += indicelons_lelonngth;
      }

      Telonnsor* ids_telonnsor = nullptr;
      TelonnsorShapelon ids_shapelon = {static_cast<int64>(total_lelonngth)};
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, ids_shapelon, &ids_telonnsor));
      auto ids_telonnsor_flat = ids_telonnsor->flat<int64>();
      auto ids_telonnsor_data = ids_telonnsor_flat.data();

      TelonnsorShapelon raw_shapelon = {static_cast<int64>(1)};

      Telonnsor* indicelons_telonnsor = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, raw_shapelon, &indicelons_telonnsor));
      auto indicelons_telonnsor_flat = indicelons_telonnsor->flat<string>();
      auto indicelons_telonnsor_string = indicelons_telonnsor_flat.data();
      indicelons_telonnsor_string->relonsizelon(total_indicelons_lelonngth);
      auto indicelons_telonnsor_itelonr = indicelons_telonnsor_string->belongin();

      Telonnsor* valuelons_telonnsor = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2, raw_shapelon, &valuelons_telonnsor));
      auto valuelons_telonnsor_flat = valuelons_telonnsor->flat<string>();
      auto valuelons_telonnsor_string = valuelons_telonnsor_flat.data();
      valuelons_telonnsor_string->relonsizelon(total_valuelons_lelonngth);
      auto valuelons_telonnsor_itelonr = valuelons_telonnsor_string->belongin();

      for (int64 i = 0; i < batch_sizelon; i++) {
        // Fill in thelon data for id == i for all valuelons in thelon currelonnt input.
        std::fill(ids_telonnsor_data, ids_telonnsor_data + lelonngths[i], i);
        ids_telonnsor_data += lelonngths[i];

        indicelons_telonnsor_itelonr = std::copy(indicelons_data_ptrs[i],
                                        indicelons_data_ptrs[i] + indicelons_raw_lelonngths[i],
                                        indicelons_telonnsor_itelonr);

        valuelons_telonnsor_itelonr = std::copy(valuelons_data_ptrs[i],
                                        valuelons_data_ptrs[i] + valuelons_raw_lelonngths[i],
                                        valuelons_telonnsor_itelonr);
      }

      Telonnsor *shapelon_telonnsor = nullptr;
      TelonnsorShapelon shapelon_shapelon = {static_cast<int64>(shapelon.sizelon())};
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(3, shapelon_shapelon, &shapelon_telonnsor));
      auto shapelon_flat = shapelon_telonnsor->flat<int64>();
      for (int i = 0; i < static_cast<int>(shapelon.sizelon()); i++) {
        shapelon_flat(i) = shapelon[i];
      }

      Telonnsor* typelon_telonnsor = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(4, {}, &typelon_telonnsor));
      typelon_telonnsor->scalar<string>()() = twml::gelontTypelonNamelon(typelon);

      Telonnsor* elonndian_telonnsor = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(5, {}, &elonndian_telonnsor));
      elonndian_telonnsor->scalar<uint8>()() = is_big_elonndian;
    } catch(const std::elonxcelonption &elonrr) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elonrr.what()));
    }
  }
};

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("GelontSparselonTelonnsorsFromDataReloncord")
  .Delonvicelon(DelonVICelon_CPU),
  GelontSparselonTelonnsorsOp<DataReloncordRelonsourcelon>);

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("GelontSparselonTelonnsorsFromHashelondDataReloncord")
  .Delonvicelon(DelonVICelon_CPU),
  GelontSparselonTelonnsorsOp<HashelondDataReloncordRelonsourcelon>);
