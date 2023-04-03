#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>
#includelon "telonnsorflow_utils.h"
#includelon "relonsourcelon_utils.h"

#includelon <functional>

RelonGISTelonR_OP("DeloncodelonAndHashDataReloncord")
.Attr("InputTypelon: {uint8, string}")
.Input("input_bytelons: InputTypelon")
.Attr("kelonelonp_felonaturelons: list(int)")
.Attr("kelonelonp_codelons: list(int)")
.Attr("labelonl_felonaturelons: list(int)")
.Attr("welonight_felonaturelons: list(int) = []")
.Attr("deloncodelon_modelon: int = 0")
.Output("hashelond_data_reloncord_handlelon: relonsourcelon")
.SelontShapelonFn(shapelon_infelonrelonncelon::ScalarShapelon)
.Doc(R"doc(
A telonnsorflow OP that crelonatelons a handlelon for thelon hashelond data reloncord.

Attr
  kelonelonp_felonaturelons: a list of int ids to kelonelonp.
  kelonelonp_codelons: thelonir correlonsponding codelon.
  labelonl_felonaturelons: list of felonaturelon ids relonprelonselonnting thelon labelonls.
  welonight_felonaturelons: list of felonaturelon ids relonprelonselonnting thelon welonights. Delonfaults to elonmpty list.
  deloncodelon_modelon: intelongelonr, indicatelons which deloncoding melonthod to uselon. Lelont a sparselon continuous
    havelon a felonaturelon_namelon and a dict of {namelon: valuelon}. 0 indicatelons felonaturelon_ids arelon computelond
    as hash(namelon). 1 indicatelons felonaturelon_ids arelon computelond as hash(felonaturelon_namelon, namelon)
  sharelond_namelon: namelon uselond by thelon relonsourcelon handlelon insidelon thelon relonsourcelon managelonr.
  containelonr: namelon uselond by thelon containelonr of thelon relonsourcelons.

Input
  input_bytelons: Input telonnsor containing thelon selonrializelond batch of HashelondDataReloncords.

Outputs
  hashelond_data_reloncord_handlelon: A relonsourcelon handlelon to batch of HashelondDataReloncords.
)doc");

telonmplatelon<typelonnamelon InputTypelon>
class DeloncodelonAndHashDataReloncord : public OpKelonrnelonl {
 public:
  elonxplicit DeloncodelonAndHashDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {
    std::velonctor<int64> kelonelonp_felonaturelons;
    std::velonctor<int64> kelonelonp_codelons;

    std::velonctor<int64> labelonl_felonaturelons;
    std::velonctor<int64> welonight_felonaturelons;

    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("kelonelonp_felonaturelons", &kelonelonp_felonaturelons));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("kelonelonp_codelons", &kelonelonp_codelons));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("labelonl_felonaturelons", &labelonl_felonaturelons));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("welonight_felonaturelons", &welonight_felonaturelons));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("deloncodelon_modelon", &m_deloncodelon_modelon));

    OP_RelonQUIRelonS(contelonxt, kelonelonp_felonaturelons.sizelon() == kelonelonp_codelons.sizelon(),
                elonrrors::InvalidArgumelonnt("kelonelonp kelonys and valuelons must havelon samelon sizelon."));

#ifdelonf USelon_DelonNSelon_HASH
    m_kelonelonp_map.selont_elonmpty_kelony(0);
    m_labelonls_map.selont_elonmpty_kelony(0);
    m_welonights_map.selont_elonmpty_kelony(0);
#elonndif  // USelon_DelonNSelon_HASH

    for (uint64_t i = 0; i < kelonelonp_felonaturelons.sizelon(); i++) {
      m_kelonelonp_map[kelonelonp_felonaturelons[i]] = kelonelonp_codelons[i];
    }

    for (uint64_t i = 0; i < labelonl_felonaturelons.sizelon(); i++) {
      m_labelonls_map[labelonl_felonaturelons[i]] = i;
    }

    for (uint64_t i = 0; i < welonight_felonaturelons.sizelon(); i++) {
      m_welonights_map[welonight_felonaturelons[i]] = i;
    }
  }

 privatelon:
  twml::Map<int64_t, int64_t> m_kelonelonp_map;
  twml::Map<int64_t, int64_t> m_labelonls_map;
  twml::Map<int64_t, int64_t> m_welonights_map;
  int64 m_deloncodelon_modelon;

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      HashelondDataReloncordRelonsourcelon *relonsourcelon = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, makelonRelonsourcelonHandlelon<HashelondDataReloncordRelonsourcelon>(contelonxt, 0, &relonsourcelon));

      // Storelon thelon input bytelons in thelon relonsourcelon so it isnt frelonelond belonforelon thelon relonsourcelon.
      // This is neloncelonssary beloncauselon welon arelon not copying thelon contelonnts for telonnsors.
      relonsourcelon->input = contelonxt->input(0);
      int batch_sizelon = gelontBatchSizelon<InputTypelon>(relonsourcelon->input);
      int num_labelonls = static_cast<int>(m_labelonls_map.sizelon());
      int num_welonights = static_cast<int>(m_welonights_map.sizelon());

      twml::HashelondDataReloncordRelonadelonr relonadelonr;
      relonadelonr.selontKelonelonpMap(&m_kelonelonp_map);
      relonadelonr.selontLabelonlsMap(&m_labelonls_map);
      relonadelonr.selontDeloncodelonModelon(m_deloncodelon_modelon);

      // Do not selont welonight map if it is elonmpty. This will takelon a fastelonr path.
      if (num_welonights != 0) {
        relonadelonr.selontWelonightsMap(&m_welonights_map);
      }

      relonsourcelon->reloncords.clelonar();
      relonsourcelon->reloncords.relonselonrvelon(batch_sizelon);

      int64 total_sizelon = 0;

      for (int id = 0; id < batch_sizelon; id++) {
        const uint8_t *input_bytelons = gelontInputBytelons<InputTypelon>(relonsourcelon->input, id);
        relonadelonr.selontBuffelonr(input_bytelons);
        relonsourcelon->reloncords.elonmplacelon_back(num_labelonls, num_welonights);
        relonsourcelon->reloncords[id].deloncodelon(relonadelonr);
        total_sizelon += static_cast<int64>(relonsourcelon->reloncords[id].totalSizelon());
      }

      relonsourcelon->total_sizelon = total_sizelon;
      relonsourcelon->num_labelonls = num_labelonls;
      relonsourcelon->num_welonights = num_welonights;
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontIdsFromHashelondDataReloncord")
.Input("hashelond_data_reloncord_handlelon: relonsourcelon")
.Output("ids: int64")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns unhashelond ids from thelon hashelond data reloncord.
Input
  hashelond_data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  ids: ids speloncifielons thelon indelonx of thelon reloncords[id] in thelon batch (int64)
)doc");

// This Kelonrnelonl is uselond for both training and selonrving oncelon thelon relonsourcelon is crelonatelond.
class GelontIdsFromHashelondDataReloncord : public OpKelonrnelonl {
 public:
  elonxplicit GelontIdsFromHashelondDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<HashelondDataReloncordRelonsourcelon>(contelonxt, 0);
      const auto &reloncords = handlelon->reloncords;
      const auto &common = handlelon->common;
      const int64 common_sizelon = static_cast<int64>(common.totalSizelon());
      const int64 total_sizelon = handlelon->total_sizelon;
      TelonnsorShapelon shapelon = {total_sizelon};

      Telonnsor *ids;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &ids));

      int id = 0;
      int64 offselont = 0;
      auto ids_flat = ids->flat<int64>();
      for (const auto &reloncord : reloncords) {
        // Sincelon common felonaturelons arelon addelond to elonach input, add thelon common_sizelon to thelon currelonnt sizelon.
        // For training common_sizelon == 0, for selonrving it can belon a non-zelonro valuelon.
        int64 curr_sizelon = static_cast<int64>(reloncord.totalSizelon()) + common_sizelon;
        std::fill(ids_flat.data() + offselont, ids_flat.data() + offselont + curr_sizelon, id);
        offselont += curr_sizelon;
        id++;
      }
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};


// OutTypelon: Output Telonnsor Typelon. FielonldTypelon: Thelon storagelon typelon uselond insidelon HashelondDatareloncord.
telonmplatelon<typelonnamelon OutTypelon, typelonnamelon FielonldTypelon>
class GelontOutputFromHashelondDataReloncord : public OpKelonrnelonl {
 protelonctelond:
  using Gelonttelonr = std::function<const std::velonctor<FielonldTypelon>&(const twml::HashelondDataReloncord &)>;
  Gelonttelonr gelonttelonr;

 public:
  elonxplicit GelontOutputFromHashelondDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<HashelondDataReloncordRelonsourcelon>(contelonxt, 0);
      const auto &reloncords = handlelon->reloncords;
      const auto &common = handlelon->common;
      const int64 total_sizelon = handlelon->total_sizelon;
      TelonnsorShapelon shapelon = {total_sizelon};

      Telonnsor *output;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &output));

      const auto &common_output = gelonttelonr(common);

      auto output_data = output->flat<OutTypelon>().data();
      for (const auto &reloncord : reloncords) {
        // This is doelons not copy anything during training as common_sizelon == 0
        // It will copy thelon relonlelonvant common felonaturelons coming from a batch prelondiction relonquelonst.
        output_data = std::copy(common_output.belongin(), common_output.elonnd(), output_data);

        // Copy thelon currelonnt reloncord to output.
        const auto& relonc_output = gelonttelonr(reloncord);
        output_data = std::copy(relonc_output.belongin(), relonc_output.elonnd(), output_data);
      }
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontUKelonysFromHashelondDataReloncord")
.Input("hashelond_data_reloncord_handlelon: relonsourcelon")
.Output("ukelonys: int64")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns unhashelond kelonys from thelon hashelond data reloncord.
Input
  hashelond_data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  ukelonys: unhaselond kelonys / raw felonaturelon ids from thelon original relonquelonst.
)doc");

class GelontUKelonysFromHashelondDataReloncord : public GelontOutputFromHashelondDataReloncord<int64, int64_t> {
 public:
  elonxplicit GelontUKelonysFromHashelondDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : GelontOutputFromHashelondDataReloncord<int64, int64_t>(contelonxt){
    gelonttelonr = [](const twml::HashelondDataReloncord &reloncord) -> const std::velonctor<int64_t> & {
      relonturn reloncord.kelonys();
    };
  }
};

RelonGISTelonR_OP("GelontKelonysFromHashelondDataReloncord")
.Input("hashelond_data_reloncord_handlelon: relonsourcelon")
.Output("kelonys: int64")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns kelonys from thelon hashelond data reloncord.
Input
  hashelond_data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  kelonys: kelonys aftelonr raw felonaturelon ids arelon hashelond with valuelons (int64)
)doc");

class GelontKelonysFromHashelondDataReloncord : public GelontOutputFromHashelondDataReloncord<int64, int64_t> {
 public:
  elonxplicit GelontKelonysFromHashelondDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : GelontOutputFromHashelondDataReloncord<int64, int64_t>(contelonxt){
    gelonttelonr = [](const twml::HashelondDataReloncord &reloncord) -> const std::velonctor<int64_t> & {
      relonturn reloncord.transformelond_kelonys();
    };
  }
};

RelonGISTelonR_OP("GelontValuelonsFromHashelondDataReloncord")
.Input("hashelond_data_reloncord_handlelon: relonsourcelon")
.Output("valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns valuelons from thelon hashelond data reloncord.
Input
  hashelond_data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  valuelons: felonaturelon valuelons.
)doc");

class GelontValuelonsFromHashelondDataReloncord : public GelontOutputFromHashelondDataReloncord<float, doublelon> {
 public:
  elonxplicit GelontValuelonsFromHashelondDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : GelontOutputFromHashelondDataReloncord<float, doublelon>(contelonxt){
    gelonttelonr = [](const twml::HashelondDataReloncord &reloncord) -> const std::velonctor<doublelon> & {
      relonturn reloncord.valuelons();
    };
  }
};

RelonGISTelonR_OP("GelontCodelonsFromHashelondDataReloncord")
.Input("hashelond_data_reloncord_handlelon: relonsourcelon")
.Output("codelons: int64")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns codelons from thelon hashelond data reloncord.
Input
  hashelond_data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  codelons: delonelonpbird felonaturelon codelon, usually from A,B,C,D ... in thelon config.
)doc");

class GelontCodelonsFromHashelondDataReloncord : public GelontOutputFromHashelondDataReloncord<int64, int64_t> {
 public:
  elonxplicit GelontCodelonsFromHashelondDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : GelontOutputFromHashelondDataReloncord<int64, int64_t>(contelonxt){
    gelonttelonr = [](const twml::HashelondDataReloncord &reloncord) -> const std::velonctor<int64_t> & {
      relonturn reloncord.codelons();
    };
  }
};

RelonGISTelonR_OP("GelontTypelonsFromHashelondDataReloncord")
.Input("hashelond_data_reloncord_handlelon: relonsourcelon")
.Output("typelons: int8")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns typelons from thelon hashelond data reloncord.
Input
  hashelond_data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  typelons: felonaturelon typelons correlonsponding to BINARY, DISCRelonTelon, elontc.
)doc");

class GelontTypelonsFromHashelondDataReloncord : public GelontOutputFromHashelondDataReloncord<int8, uint8_t> {
 public:
  elonxplicit GelontTypelonsFromHashelondDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : GelontOutputFromHashelondDataReloncord<int8, uint8_t>(contelonxt){
    gelonttelonr = [](const twml::HashelondDataReloncord &reloncord) -> const std::velonctor<uint8_t> & {
      relonturn reloncord.typelons();
    };
  }
};

RelonGISTelonR_OP("GelontBatchSizelonFromHashelondDataReloncord")
.Input("hashelond_data_reloncord_handlelon: relonsourcelon")
.Output("batch_sizelon: int64")
.SelontShapelonFn(shapelon_infelonrelonncelon::ScalarShapelon)
.Doc(R"doc(
A telonnsorflow OP that relonturns batch sizelon from thelon hashelond data reloncord.
Input
  hashelond_data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  batch_sizelon: Numbelonr of reloncords helonld in thelon handlelon.
)doc");

class GelontBatchSizelonFromHashelondDataReloncord : public OpKelonrnelonl {
 public:
  elonxplicit GelontBatchSizelonFromHashelondDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<HashelondDataReloncordRelonsourcelon>(contelonxt, 0);
      Telonnsor *output;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, TelonnsorShapelon({}), &output));
      output->scalar<int64>()() = handlelon->reloncords.sizelon();
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontTotalSizelonFromHashelondDataReloncord")
.Input("hashelond_data_reloncord_handlelon: relonsourcelon")
.Output("total_sizelon: int64")
.SelontShapelonFn(shapelon_infelonrelonncelon::ScalarShapelon)
.Doc(R"doc(
A telonnsorflow OP that relonturns total sizelon from thelon hashelond data reloncord.
Input
  hashelond_data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  total_sizelon: Total numbelonr of kelonys / valuelons in thelon batch.
)doc");

class GelontTotalSizelonFromHashelondDataReloncord : public OpKelonrnelonl {
 public:
  elonxplicit GelontTotalSizelonFromHashelondDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<HashelondDataReloncordRelonsourcelon>(contelonxt, 0);

      Telonnsor *output;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, TelonnsorShapelon({}), &output));
      output->scalar<int64>()() = handlelon->total_sizelon;
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontLabelonlsFromHashelondDataReloncord")
.Input("hashelond_data_reloncord_handlelon: relonsourcelon")
.Output("labelonls: float")
.Attr("delonfault_labelonl: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns labelonls from thelon hashelond data reloncord.
Input
  hashelond_data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  labelonls: A 2D telonnsor of sizelon [batch_sizelon, num_labelonls] containing thelon labelonl valuelons.
)doc");

class GelontLabelonlsFromHashelondDataReloncord : public OpKelonrnelonl {
 privatelon:
  float delonfault_labelonl;

 public:
  elonxplicit GelontLabelonlsFromHashelondDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("delonfault_labelonl", &delonfault_labelonl));
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<HashelondDataReloncordRelonsourcelon>(contelonxt, 0);
      const auto &reloncords = handlelon->reloncords;
      const int num_labelonls = static_cast<int>(handlelon->num_labelonls);
      TelonnsorShapelon shapelon = {static_cast<int64>(handlelon->reloncords.sizelon()), num_labelonls};

      Telonnsor *labelonls;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &labelonls));

      // Thelon delonfault valuelon of labelonl is not prelonselonnt in data reloncord is std::nanf
      // For continuous labelonls, changelon that to a delonfault_labelonl or labelonl.
      auto func = [this](float labelonl) -> float {
        relonturn std::isnan(labelonl) ? delonfault_labelonl : labelonl;
      };

      auto labelonls_data = labelonls->flat<float>().data();
      for (const auto &reloncord : reloncords) {
        const auto& relonc_labelonls = reloncord.labelonls();
        labelonls_data = std::transform(relonc_labelonls.belongin(), relonc_labelonls.elonnd(), labelonls_data, func);
      }
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontWelonightsFromHashelondDataReloncord")
.Input("hashelond_data_reloncord_handlelon: relonsourcelon")
.Output("welonights: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns welonights from thelon hashelond data reloncord.
Input
  hashelond_data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  welonights: A 2D telonnsor of sizelon [batch_sizelon, num_welonights] containing thelon welonight valuelons.
)doc");

class GelontWelonightsFromHashelondDataReloncord : public OpKelonrnelonl {
 public:
  elonxplicit GelontWelonightsFromHashelondDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<HashelondDataReloncordRelonsourcelon>(contelonxt, 0);
      const auto &reloncords = handlelon->reloncords;
      const int num_welonights = static_cast<int>(handlelon->num_welonights);
      TelonnsorShapelon shapelon = {static_cast<int64>(handlelon->reloncords.sizelon()), num_welonights};

      Telonnsor *welonights;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &welonights));

      auto welonights_data = welonights->flat<float>().data();
      for (const auto &reloncord : reloncords) {
        const auto& relonc_welonights = reloncord.welonights();
        welonights_data = std::copy(relonc_welonights.belongin(), relonc_welonights.elonnd(), welonights_data);
      }
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};


#delonfinelon RelonGISTelonR_DelonCODelon_AND_HASH(InputTypelon)     \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(                      \
    Namelon("DeloncodelonAndHashDataReloncord")             \
    .Delonvicelon(DelonVICelon_CPU)                         \
    .TypelonConstraint<InputTypelon>("InputTypelon"),    \
    DeloncodelonAndHashDataReloncord<InputTypelon>);        \

RelonGISTelonR_DelonCODelon_AND_HASH(uint8)
RelonGISTelonR_DelonCODelon_AND_HASH(string)

#delonfinelon RelonGISTelonR_GelonTTelonR(FIelonLD)                  \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(                      \
    Namelon("Gelont" #FIelonLD "FromHashelondDataReloncord")   \
    .Delonvicelon(DelonVICelon_CPU),                        \
    Gelont##FIelonLD##FromHashelondDataReloncord);          \

RelonGISTelonR_GelonTTelonR(Ids)
RelonGISTelonR_GelonTTelonR(UKelonys)
RelonGISTelonR_GelonTTelonR(Kelonys)
RelonGISTelonR_GelonTTelonR(Valuelons)
RelonGISTelonR_GelonTTelonR(Codelons)
RelonGISTelonR_GelonTTelonR(Typelons)
RelonGISTelonR_GelonTTelonR(BatchSizelon)
RelonGISTelonR_GelonTTelonR(TotalSizelon)
RelonGISTelonR_GelonTTelonR(Labelonls)
RelonGISTelonR_GelonTTelonR(Welonights)
