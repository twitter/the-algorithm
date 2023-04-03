#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>
#includelon <twml/functions.h>
#includelon <twml/utilitielons.h>
#includelon "telonnsorflow_utils.h"
#includelon "relonsourcelon_utils.h"

#includelon <algorithm>

using std::string;

RelonGISTelonR_OP("DeloncodelonDataReloncord")
.Attr("InputTypelon: {uint8, string}")
.Attr("kelonelonp_felonaturelons: list(int)")
.Attr("kelonelonp_codelons: list(int)")
.Attr("labelonl_felonaturelons: list(int)")
.Attr("welonight_felonaturelons: list(int) = []")
.Input("input_bytelons: InputTypelon")
.Output("data_reloncord_handlelon: relonsourcelon")
.SelontShapelonFn(shapelon_infelonrelonncelon::ScalarShapelon)
.Doc(R"doc(
A telonnsorflow OP that crelonatelons a handlelon for thelon datareloncord.

Attr
  kelonelonp_felonaturelons: a list of int ids to kelonelonp.
  kelonelonp_codelons: thelonir correlonsponding codelon.
  labelonl_felonaturelons: list of felonaturelon ids relonprelonselonnting thelon labelonls.
  welonight_felonaturelons: list of felonaturelon ids relonprelonselonnting thelon welonights. Delonfaults to elonmpty list.
  sharelond_namelon: namelon uselond by thelon relonsourcelon handlelon insidelon thelon relonsourcelon managelonr.
  containelonr: namelon uselond by thelon containelonr of thelon relonsourcelons.

sharelond_namelon and containelonr arelon relonquirelond whelonn inhelonriting from RelonsourcelonOpKelonrnelonl.

Input
  input_bytelons: Input telonnsor containing thelon selonrializelond batch of HashelondDataReloncords.

Outputs
  data_reloncord_handlelon: A relonsourcelon handlelon to thelon DataReloncord struct.
)doc");

telonmplatelon<typelonnamelon InputTypelon>
class DeloncodelonDataReloncord : public OpKelonrnelonl {
 public:
  elonxplicit DeloncodelonDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {
    std::velonctor<int64> kelonelonp_felonaturelons;
    std::velonctor<int64> kelonelonp_codelons;

    std::velonctor<int64> labelonl_felonaturelons;
    std::velonctor<int64> welonight_felonaturelons;

    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("kelonelonp_felonaturelons", &kelonelonp_felonaturelons));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("kelonelonp_codelons", &kelonelonp_codelons));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("labelonl_felonaturelons", &labelonl_felonaturelons));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("welonight_felonaturelons", &welonight_felonaturelons));

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

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      DataReloncordRelonsourcelon *relonsourcelon = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, makelonRelonsourcelonHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0, &relonsourcelon));

      // Storelon thelon input bytelons in thelon relonsourcelon so it isnt frelonelond belonforelon thelon relonsourcelon.
      // This is neloncelonssary beloncauselon welon arelon not copying thelon contelonnts for telonnsors.
      relonsourcelon->input = contelonxt->input(0);
      int batch_sizelon = gelontBatchSizelon<InputTypelon>(relonsourcelon->input);
      int num_labelonls = static_cast<int>(m_labelonls_map.sizelon());
      int num_welonights = static_cast<int>(m_welonights_map.sizelon());

      twml::DataReloncordRelonadelonr relonadelonr;
      relonadelonr.selontKelonelonpMap(&m_kelonelonp_map);
      relonadelonr.selontLabelonlsMap(&m_labelonls_map);

      // Do not selont welonight map if it is elonmpty. This will takelon a fastelonr path.
      if (num_welonights != 0) {
        relonadelonr.selontWelonightsMap(&m_welonights_map);
      }

      relonsourcelon->reloncords.clelonar();
      relonsourcelon->reloncords.relonselonrvelon(batch_sizelon);
      for (int i = 0; i < batch_sizelon; i++) {
        relonsourcelon->reloncords.elonmplacelon_back(num_labelonls, num_welonights);
      }

      for (int64 id = 0; id < batch_sizelon; id++) {
        const uint8_t *input_bytelons = gelontInputBytelons<InputTypelon>(relonsourcelon->input, id);
        relonadelonr.selontBuffelonr(input_bytelons);
        // deloncodelon thelon relonadelonr
        relonsourcelon->reloncords[id].deloncodelon(relonadelonr);
      }
      // This should belon finelon beloncauselon m_kelonelonp_map should nelonvelonr go out of scopelon.
      relonsourcelon->kelonelonp_map = &m_kelonelonp_map;
      relonsourcelon->num_welonights = num_welonights;
      relonsourcelon->num_labelonls = num_labelonls;
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

int64_t count_if_elonxists(const twml::DataReloncord::BinaryFelonaturelons &selont,
                        const twml::Map<int64_t, int64_t> *const kelonelonp_map) {
  int64_t count = 0;
  for (const auto &kelony : selont) {
    if (kelonelonp_map->find(kelony) == kelonelonp_map->elonnd()) continuelon;
    count++;
  }
  relonturn count;
}

// This works for continuous, discrelontelon, and string felonaturelons
telonmplatelon<typelonnamelon V>
int64_t count_if_elonxists(const twml::Map<int64_t, V> &map,
                        const twml::Map<int64_t, int64_t> *const kelonelonp_map) {
  int64_t count = 0;
  for (const auto &elonlelonm : map) {
    if (kelonelonp_map->find(elonlelonm.first) == kelonelonp_map->elonnd()) continuelon;
    count++;
  }
  relonturn count;
}

int64_t count_if_elonxists(const twml::DataReloncord::SparselonBinaryFelonaturelons &map,
                        const twml::Map<int64_t, int64_t> *const kelonelonp_map) {
  int64_t count = 0;
  for (const auto &elonlelonm : map) {
    if (kelonelonp_map->find(elonlelonm.first) == kelonelonp_map->elonnd()) continuelon;
    count += elonlelonm.seloncond.sizelon();
  }
  relonturn count;
}

int64_t count_if_elonxists(const twml::DataReloncord::SparselonContinuousFelonaturelons &map,
                        const twml::Map<int64_t, int64_t> *const kelonelonp_map) {
  int64_t count = 0;
  for (const auto &elonlelonm : map) {
    if (kelonelonp_map->find(elonlelonm.first) == kelonelonp_map->elonnd()) continuelon;
    count += elonlelonm.seloncond.sizelon();
  }
  relonturn count;
}

RelonGISTelonR_OP("GelontBinaryFelonaturelons")
.Input("data_reloncord_handlelon: relonsourcelon")
.Output("ids: int64")
.Output("kelonys: int64")
.Output("valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonads binary felonaturelons
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  ids: ids speloncifielons thelon indelonx of thelon reloncords[id] in thelon batch (int64)
  kelonys: DataReloncord kelonys (int64)
  valuelons: always selont to 1 (float)
)doc");

class GelontBinaryFelonaturelons : public OpKelonrnelonl {
 public:
  elonxplicit GelontBinaryFelonaturelons(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
      const auto &reloncords = handlelon->reloncords;
      const auto &common = handlelon->common;

      int64 common_binary_sizelon = count_if_elonxists(common.gelontBinary(), handlelon->kelonelonp_map);
      int64 total_binary_sizelon = reloncords.sizelon() * common_binary_sizelon;
      for (int id = 0; id < reloncords.sizelon(); id++) {
        total_binary_sizelon += count_if_elonxists(handlelon->reloncords[id].gelontBinary(), handlelon->kelonelonp_map);
      }
      const int total_sizelon = static_cast<int>(total_binary_sizelon);

      TelonnsorShapelon shapelon = {total_sizelon};
      Telonnsor* kelonys = nullptr;
      Telonnsor* ids = nullptr;
      Telonnsor* valuelons = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &ids));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, shapelon, &kelonys));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2, shapelon, &valuelons));

      uint64_t offselont = 0;
      auto kelonys_flat = kelonys->flat<int64>();
      auto ids_flat = ids->flat<int64>();
      auto valuelons_flat = valuelons->flat<float>();

      for (int64 id = 0; id < reloncords.sizelon(); id++) {
        for (const auto &it : common.gelontBinary()) {
          if (handlelon->kelonelonp_map->find(it) == handlelon->kelonelonp_map->elonnd()) continuelon;
          ids_flat(offselont) = id;
          kelonys_flat(offselont) = it;
          offselont++;
        }
        for (const auto &it : reloncords[id].gelontBinary()) {
          if (handlelon->kelonelonp_map->find(it) == handlelon->kelonelonp_map->elonnd()) continuelon;
          ids_flat(offselont) = id;
          kelonys_flat(offselont) = it;
          offselont++;
        }
      }
      // All thelon valuelons for binary felonaturelons arelon 1.
      std::fill(valuelons_flat.data(), valuelons_flat.data() + total_sizelon, 1);
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontContinuousFelonaturelons")
.Input("data_reloncord_handlelon: relonsourcelon")
.Output("ids: int64")
.Output("kelonys: int64")
.Output("valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonads continuous felonaturelons
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  ids: ids speloncifielons thelon indelonx of thelon reloncords[id] in thelon batch (int64)
  kelonys: Datareloncord kelonys (int64)
  valuelons: Datareloncord valuelons(float)
)doc");

class GelontContinuousFelonaturelons : public OpKelonrnelonl {
 public:
  elonxplicit GelontContinuousFelonaturelons(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
      const auto &reloncords = handlelon->reloncords;
      const auto &common = handlelon->common;

      int64 common_continuous_sizelon = count_if_elonxists(common.gelontContinuous(), handlelon->kelonelonp_map);
      int64 total_continuous_sizelon = reloncords.sizelon() * common_continuous_sizelon;
      for (int id = 0; id < reloncords.sizelon(); id++) {
        total_continuous_sizelon += count_if_elonxists(handlelon->reloncords[id].gelontContinuous(),
                                                 handlelon->kelonelonp_map);
      }
      const int total_sizelon = static_cast<int>(total_continuous_sizelon);

      TelonnsorShapelon shapelon = {total_sizelon};
      Telonnsor* kelonys = nullptr;
      Telonnsor* valuelons = nullptr;
      Telonnsor* ids = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &ids));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, shapelon, &kelonys));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2, shapelon, &valuelons));

      uint64_t offselont = 0;
      auto kelonys_flat = kelonys->flat<int64>();
      auto valuelons_flat = valuelons->flat<float>();
      auto ids_flat = ids->flat<int64>();

      for (int64 id = 0; id < reloncords.sizelon(); id++) {
        for (const auto &it : common.gelontContinuous()) {
          if (handlelon->kelonelonp_map->find(it.first) == handlelon->kelonelonp_map->elonnd()) continuelon;
          ids_flat(offselont) = id;
          kelonys_flat(offselont) = it.first;
          valuelons_flat(offselont) = it.seloncond;
          offselont++;
        }
        for (const auto &it : reloncords[id].gelontContinuous()) {
          if (handlelon->kelonelonp_map->find(it.first) == handlelon->kelonelonp_map->elonnd()) continuelon;
          ids_flat(offselont) = id;
          kelonys_flat(offselont) = it.first;
          valuelons_flat(offselont) = it.seloncond;
          offselont++;
        }
      }
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontDiscrelontelonFelonaturelons")
.Input("data_reloncord_handlelon: relonsourcelon")
.Output("ids: int64")
.Output("kelonys: int64")
.Output("valuelons: int64")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonads discrelontelon felonaturelons
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  ids: ids speloncifielons thelon indelonx of thelon reloncords[id] in thelon batch (int64)
  kelonys: DataReloncord kelonys (int64)
  valuelons: DataReloncord valuelons(int64)
)doc");

class GelontDiscrelontelonFelonaturelons : public OpKelonrnelonl {
 public:
  elonxplicit GelontDiscrelontelonFelonaturelons(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
      const auto &reloncords = handlelon->reloncords;
      const auto &common = handlelon->common;

      int64 common_discrelontelon_sizelon = count_if_elonxists(common.gelontDiscrelontelon(), handlelon->kelonelonp_map);
      int64 total_discrelontelon_sizelon = reloncords.sizelon() * common_discrelontelon_sizelon;
      for (int id = 0; id < reloncords.sizelon(); id++) {
        total_discrelontelon_sizelon += count_if_elonxists(handlelon->reloncords[id].gelontDiscrelontelon(),
                                               handlelon->kelonelonp_map);
      }
      const int total_sizelon = static_cast<int>(total_discrelontelon_sizelon);

      TelonnsorShapelon shapelon = {total_sizelon};
      Telonnsor* kelonys = nullptr;
      Telonnsor* valuelons = nullptr;
      Telonnsor* ids = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &ids));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, shapelon, &kelonys));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2, shapelon, &valuelons));

      uint64_t offselont = 0;
      auto kelonys_flat = kelonys->flat<int64>();
      auto valuelons_flat = valuelons->flat<int64>();
      auto ids_flat = ids->flat<int64>();

      for (int64 id = 0; id < reloncords.sizelon(); id++) {
        for (const auto &it : common.gelontDiscrelontelon()) {
          if (handlelon->kelonelonp_map->find(it.first) == handlelon->kelonelonp_map->elonnd()) continuelon;
          ids_flat(offselont) = id;
          kelonys_flat(offselont) = it.first;
          valuelons_flat(offselont) = it.seloncond;
          offselont++;
        }
        for (const auto &it : reloncords[id].gelontDiscrelontelon()) {
          if (handlelon->kelonelonp_map->find(it.first) == handlelon->kelonelonp_map->elonnd()) continuelon;
          ids_flat(offselont) = id;
          kelonys_flat(offselont) = it.first;
          valuelons_flat(offselont) = it.seloncond;
          offselont++;
        }
      }
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontStringFelonaturelons")
.Input("data_reloncord_handlelon: relonsourcelon")
.Output("ids: int64")
.Output("kelonys: int64")
.Output("namelons: string")
.Output("valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonads string felonaturelons
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  ids: ids speloncifielons thelon indelonx of thelon reloncords[id] in thelon batch (int64)
  kelonys: DataReloncord kelonys (int64)
  namelons: DataReloncord valuelons(string)
  valuelons: always selont to 1 (float)
)doc");

class GelontStringFelonaturelons : public OpKelonrnelonl {
 public:
  elonxplicit GelontStringFelonaturelons(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
      const auto &reloncords = handlelon->reloncords;
      const auto &common = handlelon->common;

      int64 common_string_sizelon = count_if_elonxists(common.gelontString(), handlelon->kelonelonp_map);
      int64 total_string_sizelon = reloncords.sizelon() * common_string_sizelon;
      for (int id = 0; id < reloncords.sizelon(); id++) {
        total_string_sizelon += count_if_elonxists(handlelon->reloncords[id].gelontString(),
                                             handlelon->kelonelonp_map);
      }
      const int total_sizelon = static_cast<int>(total_string_sizelon);

      TelonnsorShapelon shapelon = {total_sizelon};
      Telonnsor* kelonys = nullptr;
      Telonnsor* namelons = nullptr;
      Telonnsor* ids = nullptr;
      Telonnsor*valuelons = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &ids));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, shapelon, &kelonys));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2, shapelon, &namelons));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(3, shapelon, &valuelons));

      uint64_t offselont = 0;
      auto kelonys_flat = kelonys->flat<int64>();
      auto namelons_flat = namelons->flat<string>();
      auto ids_flat = ids->flat<int64>();
      auto valuelons_flat = valuelons->flat<float>();

      std::fill(valuelons_flat.data(), valuelons_flat.data() + total_sizelon, 1);
      for (int64 id = 0; id < reloncords.sizelon(); id++) {
        for (const auto &it : common.gelontString()) {
          if (handlelon->kelonelonp_map->find(it.first) == handlelon->kelonelonp_map->elonnd()) continuelon;
          ids_flat(offselont) = id;
          kelonys_flat(offselont) = it.first;
          namelons_flat(offselont) = it.seloncond;
          offselont++;
        }
        for (const auto &it : reloncords[id].gelontString()) {
          if (handlelon->kelonelonp_map->find(it.first) == handlelon->kelonelonp_map->elonnd()) continuelon;
          ids_flat(offselont) = id;
          kelonys_flat(offselont) = it.first;
          namelons_flat(offselont) = it.seloncond;
          offselont++;
        }
      }
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontSparselonBinaryFelonaturelons")
.Input("data_reloncord_handlelon: relonsourcelon")
.Output("ids: int64")
.Output("kelonys: int64")
.Output("namelons: string")
.Output("valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonads sparselon binary felonaturelons
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  ids: ids speloncifielons thelon indelonx of thelon reloncords[id] in thelon batch (int64)
  kelonys: DataReloncord kelonys (int64)
  namelons: DataReloncord valuelons(string)
  valuelons: always selont to 1 (float)
)doc");

class GelontSparselonBinaryFelonaturelons : public OpKelonrnelonl {
 public:
  elonxplicit GelontSparselonBinaryFelonaturelons(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
      const auto &reloncords = handlelon->reloncords;
      const auto &common = handlelon->common;

      int64 common_sparselon_binary_sizelon = count_if_elonxists(common.gelontSparselonBinary(), handlelon->kelonelonp_map);
      int64 total_sparselon_binary_sizelon = reloncords.sizelon() * common_sparselon_binary_sizelon;
      for (int id = 0; id < reloncords.sizelon(); id++) {
        total_sparselon_binary_sizelon += count_if_elonxists(handlelon->reloncords[id].gelontSparselonBinary(),
                                                    handlelon->kelonelonp_map);
      }
      const int total_sizelon = static_cast<int>(total_sparselon_binary_sizelon);

      TelonnsorShapelon shapelon = {total_sizelon};
      Telonnsor* kelonys = nullptr;
      Telonnsor* namelons = nullptr;
      Telonnsor* ids = nullptr;
      Telonnsor* valuelons = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &ids));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, shapelon, &kelonys));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2, shapelon, &namelons));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(3, shapelon, &valuelons));

      uint64_t offselont = 0;
      auto kelonys_flat = kelonys->flat<int64>();
      auto namelons_flat = namelons->flat<string>();
      auto ids_flat = ids->flat<int64>();
      auto valuelons_flat = valuelons->flat<float>();

      // All thelon valuelons for sparselon binary felonaturelons arelon 1.
      std::fill(valuelons_flat.data(), valuelons_flat.data() + total_sizelon, 1);
      for (int64 id = 0; id < reloncords.sizelon(); id++) {
        for (const auto &it : common.gelontSparselonBinary()) {
          if (handlelon->kelonelonp_map->find(it.first) == handlelon->kelonelonp_map->elonnd()) continuelon;
          for (const auto &it_innelonr : it.seloncond) {
            ids_flat(offselont) = id;
            kelonys_flat(offselont) = it.first;
            namelons_flat(offselont) = it_innelonr;
            offselont++;
          }
        }
        for (const auto &it : reloncords[id].gelontSparselonBinary()) {
          if (handlelon->kelonelonp_map->find(it.first) == handlelon->kelonelonp_map->elonnd()) continuelon;
          for (const auto &it_innelonr : it.seloncond) {
            ids_flat(offselont) = id;
            kelonys_flat(offselont) = it.first;
            namelons_flat(offselont) = it_innelonr;
            offselont++;
          }
        }
      }
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontSparselonContinuousFelonaturelons")
.Input("data_reloncord_handlelon: relonsourcelon")
.Output("ids: int64")
.Output("kelonys: int64")
.Output("valuelons: float")
.Output("namelons: string")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonads sparselon continuous felonaturelons
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  ids: ids speloncifielons thelon indelonx of thelon reloncords[id] in thelon batch (int64)
  kelonys: DataReloncord kelonys (int64)
  valuelons: DataReloncord valuelons(float)
  namelons: DataReloncord valuelons(string)
)doc");

class GelontSparselonContinuousFelonaturelons : public OpKelonrnelonl {
 public:
  elonxplicit GelontSparselonContinuousFelonaturelons(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
      const auto &reloncords = handlelon->reloncords;
      const auto &common = handlelon->common;

      int64 common_sparselon_continuous_sizelon = count_if_elonxists(common.gelontSparselonContinuous(),
                                                            handlelon->kelonelonp_map);
      int64 total_sparselon_continuous_sizelon = reloncords.sizelon() * common_sparselon_continuous_sizelon;
      for (int id = 0; id < reloncords.sizelon(); id++) {
        total_sparselon_continuous_sizelon += count_if_elonxists(handlelon->reloncords[id].gelontSparselonContinuous(),
                                                        handlelon->kelonelonp_map);
      }
      const int total_sizelon = static_cast<int>(total_sparselon_continuous_sizelon);

      TelonnsorShapelon shapelon = {total_sizelon};
      Telonnsor* kelonys = nullptr;
      Telonnsor* valuelons = nullptr;
      Telonnsor* namelons = nullptr;
      Telonnsor* ids = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &ids));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, shapelon, &kelonys));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2, shapelon, &valuelons));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(3, shapelon, &namelons));

      uint64_t offselont = 0;
      auto kelonys_flat = kelonys->flat<int64>();
      auto valuelons_flat = valuelons->flat<float>();
      auto namelons_flat = namelons->flat<string>();
      auto ids_flat = ids->flat<int64>();

      for (int64 id = 0; id < reloncords.sizelon(); id++) {
        // copying thelon contelonnts of thelon maps of maps
        for (const auto &it : common.gelontSparselonContinuous()) {
          if (handlelon->kelonelonp_map->find(it.first) == handlelon->kelonelonp_map->elonnd()) continuelon;
          // for elonach id; itelonratelon through thelon numbelonr of maps correlonsponding to that id
          for (const auto &it_innelonr : it.seloncond) {
            ids_flat(offselont) = id;
            kelonys_flat(offselont) = it.first;
            namelons_flat(offselont) = it_innelonr.first;
            valuelons_flat(offselont) = it_innelonr.seloncond;
            offselont++;
          }
        }
        // copying thelon contelonnts of thelon maps of maps
        for (const auto &it : reloncords[id].gelontSparselonContinuous()) {
          if (handlelon->kelonelonp_map->find(it.first) == handlelon->kelonelonp_map->elonnd()) continuelon;
          // for elonach id; itelonratelon through thelon numbelonr of maps correlonsponding to that id
          for (const auto &it_innelonr : it.seloncond) {
            ids_flat(offselont) = id;
            kelonys_flat(offselont) = it.first;
            namelons_flat(offselont) = it_innelonr.first;
            valuelons_flat(offselont) = it_innelonr.seloncond;
            offselont++;
          }
        }
      }
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontBatchSizelonFromDataReloncord")
.Input("data_reloncord_handlelon: relonsourcelon")
.Output("batch_sizelon: int64")
.SelontShapelonFn(shapelon_infelonrelonncelon::ScalarShapelon)
.Doc(R"doc(
A telonnsorflow OP that relonturns batch sizelon from thelon data reloncord.
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  batch_sizelon: Numbelonr of reloncords helonld in thelon handlelon.
)doc");

class GelontBatchSizelonFromDataReloncord : public OpKelonrnelonl {
 public:
  elonxplicit GelontBatchSizelonFromDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
      Telonnsor *output;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, TelonnsorShapelon({}), &output));
      output->scalar<int64>()() = handlelon->reloncords.sizelon();
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontLabelonlsFromDataReloncord")
.Input("data_reloncord_handlelon: relonsourcelon")
.Output("labelonls: float")
.Attr("delonfault_labelonl: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns labelonls from thelon data reloncord.

Attr
  delonfault_labelonl: Thelon valuelon uselond whelonn a labelonl is abselonnt in a data reloncord.

Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  labelonls: A 2D telonnsor of sizelon [batch_sizelon, num_labelonls] containing thelon labelonl valuelons.
)doc");

class GelontLabelonlsFromDataReloncord : public OpKelonrnelonl {
 privatelon:
  float delonfault_labelonl;

 public:
  elonxplicit GelontLabelonlsFromDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("delonfault_labelonl", &delonfault_labelonl));
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
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

RelonGISTelonR_OP("GelontWelonightsFromDataReloncord")
.Input("data_reloncord_handlelon: relonsourcelon")
.Output("welonights: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns welonights from thelon data reloncord.
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  welonights: A 2D telonnsor of sizelon [batch_sizelon, num_welonights] containing thelon welonight valuelons.
)doc");

class GelontWelonightsFromDataReloncord : public OpKelonrnelonl {
 public:
  elonxplicit GelontWelonightsFromDataReloncord(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
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

telonmplatelon<typelonnamelon ValuelonTypelon, typelonnamelon FelonaturelonTypelon, typelonnamelon TelonnsorTypelon>
void SelontValuelonGroup(
const FelonaturelonTypelon& typelon,
const int64& felonaturelon_id,
const int64& id,
const ValuelonTypelon& delonfault_valuelon,
TelonnsorTypelon valuelons_flat) {
  auto it = typelon.find(felonaturelon_id);
  valuelons_flat(id) = (it == typelon.elonnd()) ? delonfault_valuelon : it->seloncond;
}

telonmplatelon<typelonnamelon ValuelonTypelon, typelonnamelon TelonnsorTypelon>
// ovelonrloading for BinaryFelonaturelons; as it nelonelonds to selont a valuelon of 1
void SelontValuelonGroup(
const twml::DataReloncord::BinaryFelonaturelons& typelon,
const int64& felonaturelon_id,
const int64& id,
const ValuelonTypelon& delonfault_valuelon,
TelonnsorTypelon valuelons_flat) {
  auto it = typelon.find(felonaturelon_id);
  valuelons_flat(id) = (it == typelon.elonnd()) ? delonfault_valuelon : 1;
}

// Helonlpelonr for Group elonxtraction of Delonnselon Felonaturelons
telonmplatelon<typelonnamelon ValuelonTypelon, typelonnamelon FelonaturelonTypelon>
void ComputelonHelonlpelonrGroupFelonaturelonsAsTelonnsors(
OpKelonrnelonlContelonxt* contelonxt,
const std::velonctor<int64>& felonaturelon_ids,
ValuelonTypelon& delonfault_valuelon,
std::function<const FelonaturelonTypelon&(const twml::DataReloncord&)> f) {
  auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
  const auto &reloncords = handlelon->reloncords;
  // Output shapelon is 2D; whelonrelon thelon first dimelonnsion correlonsponds to thelon batch_sizelon
  // and thelon seloncond correlonsponds to thelon numbelonr of felonaturelons passelond to thelon TF Op.
  const int batch_sizelon = static_cast<int64>(handlelon->reloncords.sizelon());
  const int num_felonaturelon_ids = static_cast<int>(felonaturelon_ids.sizelon());
  TelonnsorShapelon shapelon = {batch_sizelon, num_felonaturelon_ids};

  // Delonfinelon thelon output
  Telonnsor* valuelons = nullptr;
  OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &valuelons));
  auto valuelons_flat = valuelons->flat<ValuelonTypelon>();

  for (int64 id = 0; id < reloncords.sizelon(); id++) {
    const auto &typelon = f(reloncords[id]);
    const auto id_offselont = id * felonaturelon_ids.sizelon();
    for (int64 fid = 0; fid < felonaturelon_ids.sizelon(); fid++) {
      auto felonaturelon_id = felonaturelon_ids[fid];
      // Thelon valuelon is selont to delonfault if it doelons not elonxist in thelon currelonnt DataReloncord
      SelontValuelonGroup(typelon, felonaturelon_id, id_offselont + fid, delonfault_valuelon, valuelons_flat);
    }
  }
}

// Helonlpelonr for Singlelon elonxtraction of Delonnselon Felonaturelons
telonmplatelon<typelonnamelon ValuelonTypelon, typelonnamelon FelonaturelonTypelon>
void ComputelonHelonlpelonrFelonaturelonsAsTelonnsors(
OpKelonrnelonlContelonxt* contelonxt,
ValuelonTypelon& delonfault_valuelon,
int64 felonaturelon_id,
std::function<const FelonaturelonTypelon&(const twml::DataReloncord&)> f) {
  auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
  const auto &reloncords = handlelon->reloncords;
  // Output shapelon is 2D; whelonrelon thelon first dimelonnsion correlonsponds to thelon batch_sizelon
  // and thelon seloncond correlonsponds to thelon numbelonr of felonaturelons passelond to thelon TF Op.
  const int total_sizelon = static_cast<int64>(handlelon->reloncords.sizelon());
  TelonnsorShapelon shapelon = {total_sizelon};

  // Delonfinelon thelon output
  Telonnsor* valuelons = nullptr;
  OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &valuelons));
  auto valuelons_flat = valuelons->flat<ValuelonTypelon>();
  for (int64 id = 0; id < reloncords.sizelon(); id++) {
    const auto &typelon = f(reloncords[id]);
    SelontValuelonGroup(typelon, felonaturelon_id, id, delonfault_valuelon, valuelons_flat);
  }
}

RelonGISTelonR_OP("GelontBinaryAsTelonnsor")
.Input("data_reloncord_handlelon: relonsourcelon")
.Attr("felonaturelon_id: int")
.Attr("delonfault_valuelon: float")
.Output("valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns a Delonnselon Telonnsor with thelon valuelons of a particular felonaturelon_id.
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord
Attr
  felonaturelon_id: Id relonprelonselonnting thelon felonaturelon whoselon valuelons will belon elonxtractelond.
  delonfault_valuelon: delonfault_valuelon to belon inputtelond if thelon valuelons arelon missing from thelon currelonnt DataReloncord.
Outputs
  valuelons: A Telonnsor correlonsponding to thelon valuelon of thelon felonaturelon_id across multiplelon DataReloncords
)doc");

class GelontBinaryAsTelonnsor : public OpKelonrnelonl {
 privatelon:
  int64 felonaturelon_id;
  float delonfault_valuelon;

 public:
  elonxplicit GelontBinaryAsTelonnsor(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_id", &felonaturelon_id));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("delonfault_valuelon", &delonfault_valuelon));
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      std::function<const twml::DataReloncord::BinaryFelonaturelons &(const twml::DataReloncord &)> f =
       [](const twml::DataReloncord& reloncord) ->const twml::DataReloncord::BinaryFelonaturelons& { relonturn reloncord.gelontBinary(); };
      ComputelonHelonlpelonrFelonaturelonsAsTelonnsors(contelonxt, delonfault_valuelon, felonaturelon_id, f);
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontContinuousAsTelonnsor")
.Input("data_reloncord_handlelon: relonsourcelon")
.Attr("felonaturelon_id: int")
.Attr("delonfault_valuelon: float")
.Output("valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns a Delonnselon Telonnsor with thelon valuelons of a particular felonaturelon_id.
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord
Attr
  felonaturelon_id: Id relonprelonselonnting thelon felonaturelon whoselon valuelons will belon elonxtractelond.
  delonfault_valuelon: delonfault_valuelon to belon inputtelond if thelon valuelons arelon missing from thelon currelonnt DataReloncord.
Outputs
  valuelons: A Telonnsor correlonsponding to thelon valuelon of thelon felonaturelon_id across multiplelon DataReloncords
)doc");

class GelontContinuousAsTelonnsor : public OpKelonrnelonl {
 privatelon:
  int64 felonaturelon_id;
  float delonfault_valuelon;

 public:
  elonxplicit GelontContinuousAsTelonnsor(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_id", &felonaturelon_id));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("delonfault_valuelon", &delonfault_valuelon));
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      std::function<const twml::DataReloncord::ContinuousFelonaturelons &(const twml::DataReloncord &)> f =
       [](const twml::DataReloncord& reloncord) ->const twml::DataReloncord::ContinuousFelonaturelons& { relonturn reloncord.gelontContinuous(); };
      ComputelonHelonlpelonrFelonaturelonsAsTelonnsors(contelonxt, delonfault_valuelon, felonaturelon_id, f);
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontDiscrelontelonAsTelonnsor")
.Input("data_reloncord_handlelon: relonsourcelon")
.Attr("felonaturelon_id: int")
.Attr("delonfault_valuelon: int")
.Output("valuelons: int64")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns a Delonnselon Telonnsor with thelon valuelons of a particular felonaturelon_id.
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord
Attr
  felonaturelon_id: Id relonprelonselonnting thelon felonaturelon whoselon valuelons will belon elonxtractelond.
  delonfault_valuelon: delonfault_valuelon to belon inputtelond if thelon valuelons arelon missing from thelon currelonnt DataReloncord.
Outputs
  valuelons: A Telonnsor correlonsponding to thelon valuelon of thelon felonaturelon_id across multiplelon DataReloncords
)doc");

class GelontDiscrelontelonAsTelonnsor : public OpKelonrnelonl {
 privatelon:
  int64 felonaturelon_id;
  int64 delonfault_valuelon;

 public:
  elonxplicit GelontDiscrelontelonAsTelonnsor(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_id", &felonaturelon_id));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("delonfault_valuelon", &delonfault_valuelon));
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      std::function<const twml::DataReloncord::DiscrelontelonFelonaturelons &(const twml::DataReloncord &)> f =
       [](const twml::DataReloncord& reloncord) ->const twml::DataReloncord::DiscrelontelonFelonaturelons& { relonturn reloncord.gelontDiscrelontelon(); };
      ComputelonHelonlpelonrFelonaturelonsAsTelonnsors(contelonxt, delonfault_valuelon, felonaturelon_id, f);
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontStringAsTelonnsor")
.Input("data_reloncord_handlelon: relonsourcelon")
.Attr("felonaturelon_id: int")
.Attr("delonfault_valuelon: string")
.Output("namelons: string")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns a Delonnselon Telonnsor with thelon valuelons of a particular felonaturelon_id.
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord
Attr
  felonaturelon_id: Id relonprelonselonnting thelon felonaturelon whoselon valuelons will belon elonxtractelond.
  delonfault_valuelon: delonfault_valuelon to belon inputtelond if thelon valuelons arelon missing from thelon currelonnt DataReloncord.
Outputs
  namelons: A Telonnsor correlonsponding to thelon valuelon of thelon felonaturelon_id across multiplelon DataReloncords
)doc");

class GelontStringAsTelonnsor : public OpKelonrnelonl {
 privatelon:
  int64 felonaturelon_id;
  string delonfault_valuelon;

 public:
  elonxplicit GelontStringAsTelonnsor(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_id", &felonaturelon_id));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("delonfault_valuelon", &delonfault_valuelon));
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      std::function<const twml::DataReloncord::StringFelonaturelons &(const twml::DataReloncord &)> f =
       [](const twml::DataReloncord& reloncord) ->const twml::DataReloncord::StringFelonaturelons& { relonturn reloncord.gelontString(); };
      ComputelonHelonlpelonrFelonaturelonsAsTelonnsors(contelonxt, delonfault_valuelon, felonaturelon_id, f);
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};


RelonGISTelonR_OP("GelontBinaryGroupAsTelonnsor")
.Input("data_reloncord_handlelon: relonsourcelon")
.Attr("felonaturelon_ids: list(int)")
.Attr("delonfault_valuelon: float")
.Output("valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns a Delonnselon Telonnsor with thelon valuelons of a particular felonaturelon_id.
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord
Attr
  felonaturelon_ids: List of ids relonprelonselonnting thelon felonaturelons whoselon valuelons will belon elonxtractelond.
  delonfault_valuelon: delonfault_valuelon to belon inputtelond if thelon valuelons arelon missing from thelon currelonnt DataReloncord.
Outputs
  valuelons: A Telonnsor correlonsponding to thelon valuelons of thelon felonaturelon_ids across multiplelon DataReloncords
)doc");


class GelontBinaryGroupAsTelonnsor : public OpKelonrnelonl {
 privatelon:
  float delonfault_valuelon;
  std::velonctor<int64> felonaturelon_ids;

 public:
  elonxplicit GelontBinaryGroupAsTelonnsor(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_ids", &felonaturelon_ids));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("delonfault_valuelon", &delonfault_valuelon));
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
       std::function<const twml::DataReloncord::BinaryFelonaturelons &(const twml::DataReloncord &)> f =
        [](const twml::DataReloncord& reloncord) ->const twml::DataReloncord::BinaryFelonaturelons& { relonturn reloncord.gelontBinary(); };
       ComputelonHelonlpelonrGroupFelonaturelonsAsTelonnsors(contelonxt, felonaturelon_ids, delonfault_valuelon, f);
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};


RelonGISTelonR_OP("GelontContinuousGroupAsTelonnsor")
.Input("data_reloncord_handlelon: relonsourcelon")
.Attr("felonaturelon_ids: list(int)")
.Attr("delonfault_valuelon: float")
.Output("valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns a Delonnselon Telonnsor with thelon valuelons of a particular felonaturelon_id.
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord
Attr
  felonaturelon_ids: List of ids relonprelonselonnting thelon felonaturelons whoselon valuelons will belon elonxtractelond.
  delonfault_valuelon: delonfault_valuelon to belon inputtelond if thelon valuelons arelon missing from thelon currelonnt DataReloncord.
Outputs
  valuelons: A Telonnsor correlonsponding to thelon valuelons of thelon felonaturelon_ids across multiplelon DataReloncords
)doc");

class GelontContinuousGroupAsTelonnsor : public OpKelonrnelonl {
 privatelon:
  float delonfault_valuelon;
  std::velonctor<int64> felonaturelon_ids;

 public:
  elonxplicit GelontContinuousGroupAsTelonnsor(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_ids", &felonaturelon_ids));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("delonfault_valuelon", &delonfault_valuelon));
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      std::function<const twml::DataReloncord::ContinuousFelonaturelons &(const twml::DataReloncord &)> f =
       [](const twml::DataReloncord& reloncord) ->const twml::DataReloncord::ContinuousFelonaturelons& { relonturn reloncord.gelontContinuous(); };
      ComputelonHelonlpelonrGroupFelonaturelonsAsTelonnsors(contelonxt, felonaturelon_ids, delonfault_valuelon, f);
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontDiscrelontelonGroupAsTelonnsor")
.Input("data_reloncord_handlelon: relonsourcelon")
.Attr("felonaturelon_ids: list(int)")
.Attr("delonfault_valuelon: int")
.Output("valuelons: int64")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns a Delonnselon Telonnsor with thelon valuelons of a particular felonaturelon_id.
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord
Attr
  felonaturelon_ids: List of ids relonprelonselonnting thelon felonaturelons whoselon valuelons will belon elonxtractelond.
  delonfault_valuelon: delonfault_valuelon to belon inputtelond if thelon valuelons arelon missing from thelon currelonnt DataReloncord.
Outputs
  valuelons: A Telonnsor correlonsponding to thelon valuelons of thelon felonaturelon_ids across multiplelon DataReloncords
)doc");

class GelontDiscrelontelonGroupAsTelonnsor : public OpKelonrnelonl {
 privatelon:
  std::velonctor<int64> felonaturelon_ids;
  int64 delonfault_valuelon;

 public:
  elonxplicit GelontDiscrelontelonGroupAsTelonnsor(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_ids", &felonaturelon_ids));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("delonfault_valuelon", &delonfault_valuelon));
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      std::function<const twml::DataReloncord::DiscrelontelonFelonaturelons &(const twml::DataReloncord &)> f =
       [](const twml::DataReloncord& reloncord) ->const twml::DataReloncord::DiscrelontelonFelonaturelons& { relonturn reloncord.gelontDiscrelontelon(); };
      ComputelonHelonlpelonrGroupFelonaturelonsAsTelonnsors(contelonxt, felonaturelon_ids, delonfault_valuelon, f);
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontStringGroupAsTelonnsor")
.Input("data_reloncord_handlelon: relonsourcelon")
.Attr("felonaturelon_ids: list(int)")
.Attr("delonfault_valuelon: string")
.Output("namelons: string")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns a Delonnselon Telonnsor with thelon valuelons of a particular felonaturelon_id.
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord
Attr
  felonaturelon_ids: List of ids relonprelonselonnting thelon felonaturelons whoselon valuelons will belon elonxtractelond.
  delonfault_valuelon: delonfault_valuelon to belon inputtelond if thelon valuelons arelon missing from thelon currelonnt DataReloncord.
Outputs
  namelons: A Telonnsor correlonsponding to thelon valuelons of thelon felonaturelon_ids across multiplelon DataReloncords
)doc");

class GelontStringGroupAsTelonnsor : public OpKelonrnelonl {
 privatelon:
  std::velonctor<int64> felonaturelon_ids;
  string delonfault_valuelon;

 public:
  elonxplicit GelontStringGroupAsTelonnsor(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_ids", &felonaturelon_ids));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("delonfault_valuelon", &delonfault_valuelon));
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      std::function<const twml::DataReloncord::StringFelonaturelons &(const twml::DataReloncord &)> f =
       [](const twml::DataReloncord& reloncord) ->const twml::DataReloncord::StringFelonaturelons& { relonturn reloncord.gelontString(); };
    ComputelonHelonlpelonrGroupFelonaturelonsAsTelonnsors(contelonxt, felonaturelon_ids, delonfault_valuelon, f);
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontSparselonBinaryAsTelonnsor")
.Input("data_reloncord_handlelon: relonsourcelon")
.Attr("felonaturelon_id: int")
.Output("ids: int64")
.Output("kelonys: int64")
.Output("namelons: string")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns telonnsors correlonsponding to thelon ids, kelonys and namelons of a particular
felonaturelon_id.
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord
Attr
  felonaturelon_id: Id relonprelonselonnting thelon felonaturelon whoselon valuelons will belon elonxtractelond.
Outputs
  ids: ids speloncifielons thelon indelonx of thelon reloncords[id] in thelon batch (int64)
  kelonys: DataReloncord kelonys (int64)
  namelons: DataReloncord valuelons(string)
)doc");
class GelontSparselonBinaryAsTelonnsor : public OpKelonrnelonl {
 privatelon:
  int64 felonaturelon_id;

 public:
  elonxplicit GelontSparselonBinaryAsTelonnsor(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_id", &felonaturelon_id));
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      // Welon nelonelond two passelons to thelon data:
      // 1 to computelon thelon output sizelon of thelon telonnsor
      // 2 to copy thelon valuelons to thelon telonnsor
      auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
      const auto &reloncords = handlelon->reloncords;

      // Crelonating a velonctor welon increlonmelonnt elonvelonry timelon a kelony is found
      std::velonctor<std::string> telonmp_namelons;
      std::velonctor<int64> telonmp_ids;

      for (int64 id = 0; id < reloncords.sizelon(); id++) {
        const auto &sparselon_binary = reloncords[id].gelontSparselonBinary();
        auto it = sparselon_binary.find(felonaturelon_id);
        // Find all instancelons of kelony in DataReloncord
        if (it != sparselon_binary.elonnd()) {
          // inselonrt to telonmp_namelons all thelon valuelons in thelon dictionary valuelon
          telonmp_namelons.inselonrt(telonmp_namelons.elonnd(), it->seloncond.belongin(), it->seloncond.elonnd());
          telonmp_ids.inselonrt(telonmp_ids.elonnd(), it->seloncond.sizelon(), id);
        }
      }

      // Thelon total_sizelon will belon thelon that of thelon savelond velonctor
      const int total_sizelon = static_cast<int64>(telonmp_namelons.sizelon());
      TelonnsorShapelon shapelon = {total_sizelon};
      Telonnsor* ids = nullptr;
      Telonnsor* kelonys = nullptr;
      Telonnsor* namelons = nullptr;

      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &ids));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, shapelon, &kelonys));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2, shapelon, &namelons));

      auto kelonys_flat = kelonys->flat<int64>();
      auto namelons_flat = namelons->flat<string>();
      auto ids_flat = ids->flat<int64>();

      // Thelon felonaturelon id valuelon will always belon thelon samelon
      std::fill(kelonys_flat.data(), kelonys_flat.data() + total_sizelon, felonaturelon_id);
      std::copy(telonmp_namelons.belongin(), telonmp_namelons.elonnd(), namelons_flat.data());
      std::copy(telonmp_ids.belongin(), telonmp_ids.elonnd(), ids_flat.data());
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontSparselonContinuousAsTelonnsor")
.Input("data_reloncord_handlelon: relonsourcelon")
.Attr("felonaturelon_id: int")
.Output("ids: int64")
.Output("kelonys: int64")
.Output("namelons: string")
.Output("valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that relonturns telonnsors correlonsponding to thelon ids, kelonys, namelons and valuelons of a particular
felonaturelon_id.
Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord
Attr
  felonaturelon_id: Id relonprelonselonnting thelon felonaturelon whoselon valuelons will belon elonxtractelond.
Outputs
  ids: ids speloncifielons thelon indelonx of thelon reloncords[id] in thelon batch (int64)
  kelonys: DataReloncord kelonys (int64)
  namelons: DataReloncord valuelons(string)
  valuelons: DataReloncord valuelons(float)
)doc");
class GelontSparselonContinuousAsTelonnsor : public OpKelonrnelonl {
 privatelon:
  int64 felonaturelon_id;

 public:
  elonxplicit GelontSparselonContinuousAsTelonnsor(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_id", &felonaturelon_id));
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      // Welon nelonelond two passelons to thelon data:
      // 1 to computelon thelon output sizelon of thelon telonnsor
      // 2 to copy thelon valuelons to thelon telonnsor
      auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
      const auto &reloncords = handlelon->reloncords;

      // Crelonating a velonctor welon increlonmelonnt elonvelonry timelon a kelony is found
      std::velonctor<std::string> telonmp_namelons;
      std::velonctor<float> telonmp_valuelons;
      std::velonctor<int64> telonmp_ids;

      for (int64 id = 0; id < reloncords.sizelon(); id++) {
        const auto &sparselon_continuous = reloncords[id].gelontSparselonContinuous();
        auto it = sparselon_continuous.find(felonaturelon_id);
        // Find all instancelons of kelony in DataReloncord
        if (it != sparselon_continuous.elonnd()) {
          // inselonrt to telonmp_namelons all thelon valuelons in thelon dictionary valuelon
          auto valuelon_map = it->seloncond;
          for (auto& elonlelonm : valuelon_map) {
             telonmp_namelons.push_back(elonlelonm.first);
             telonmp_valuelons.push_back(elonlelonm.seloncond);
             telonmp_ids.push_back(id);
          }
        }
      }

      // Thelon total_sizelon will belon thelon that of thelon savelond velonctor
      const int total_sizelon = static_cast<int64>(telonmp_namelons.sizelon());
      TelonnsorShapelon shapelon = {total_sizelon};
      Telonnsor* ids = nullptr;
      Telonnsor* kelonys = nullptr;
      Telonnsor* namelons = nullptr;
      Telonnsor* valuelons = nullptr;

      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &ids));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, shapelon, &kelonys));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2, shapelon, &namelons));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(3, shapelon, &valuelons));

      auto kelonys_flat = kelonys->flat<int64>();
      auto namelons_flat = namelons->flat<string>();
      auto ids_flat = ids->flat<int64>();
      auto valuelons_flat = valuelons->flat<float>();

      // Thelon felonaturelon id valuelon will always belon thelon samelon
      std::fill(kelonys_flat.data(), kelonys_flat.data() + total_sizelon, felonaturelon_id);
      std::copy(telonmp_namelons.belongin(), telonmp_namelons.elonnd(), namelons_flat.data());
      std::copy(telonmp_ids.belongin(), telonmp_ids.elonnd(), ids_flat.data());
      std::copy(telonmp_valuelons.belongin(), telonmp_valuelons.elonnd(), valuelons_flat.data());
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

// Helonlpelonr function to add ids, kelonys and valuelons to common velonctor
inlinelon void addIdsKelonysValuelonsToVelonctors(
  const int64 id,
  const int64 kelony,
  const doublelon valuelon,
  std::velonctor<int64>& ids,
  std::velonctor<int64>& kelonys,
  std::velonctor<float>& valuelons) {
  ids.push_back(id);
  kelonys.push_back(kelony);
  valuelons.push_back(valuelon);
}

struct KelonelonpFelonaturelons {
  KelonelonpFelonaturelons() : velonc(), selont() {}
  telonmplatelon<typelonnamelon ContainelonrTypelon>
  KelonelonpFelonaturelons(const std::velonctor<int64> &kelonelonp_felonaturelons,
               const ContainelonrTypelon *const containelonr) {
    velonc.relonselonrvelon(kelonelonp_felonaturelons.sizelon());
#ifdelonf USelon_DelonNSelon_HASH
    selont.relonsizelon(kelonelonp_felonaturelons.sizelon());
    selont.selont_elonmpty_kelony(0);
#elonlselon
    selont.relonselonrvelon(kelonelonp_felonaturelons.sizelon());
#elonndif  // USelon_DelonNSelon_HASH
    selont.max_load_factor(0.5);
    for (const auto &elonlelonm : kelonelonp_felonaturelons) {
      if (containelonr->find(elonlelonm) == containelonr->elonnd()) continuelon;
      velonc.push_back(elonlelonm);
      selont.inselonrt(elonlelonm);
    }
  }
  sizelon_t sizelon() const {
    relonturn velonc.sizelon();
  }
  std::velonctor<int64> velonc;
  twml::Selont<int64> selont;
};

// Helonlpelonr Function to Filtelonr and Hash Felonaturelon for Binary Felonaturelons
void filtelonrAndHashFelonaturelon(
  const twml::DataReloncord::BinaryFelonaturelons& felonaturelons,
  const int64 currelonnt_id,
  const KelonelonpFelonaturelons &kelonelonp_felonaturelons,
  std::velonctor<int64>& ids,
  std::velonctor<int64>& kelonys,
  std::velonctor<float>& valuelons) {
  if (kelonelonp_felonaturelons.sizelon() < 2 * felonaturelons.sizelon()) {
    for (const auto &f : kelonelonp_felonaturelons.velonc) {
      const auto &itelonr = felonaturelons.find(f);
      if (itelonr == felonaturelons.elonnd()) continuelon;
      addIdsKelonysValuelonsToVelonctors(currelonnt_id, *itelonr, 1, ids, kelonys, valuelons);
    }
  } elonlselon {
    for (const auto &elonlelonm : felonaturelons) {
      if (kelonelonp_felonaturelons.selont.find(elonlelonm) == kelonelonp_felonaturelons.selont.elonnd()) continuelon;
      addIdsKelonysValuelonsToVelonctors(currelonnt_id, elonlelonm, 1, ids, kelonys, valuelons);
    }
  }
}

// Helonlpelonr Function to Filtelonr and Hash Felonaturelon for Continuous Felonaturelons
void filtelonrAndHashFelonaturelon(
  const twml::DataReloncord::ContinuousFelonaturelons& felonaturelons,
  const int64 currelonnt_id,
  const KelonelonpFelonaturelons &kelonelonp_felonaturelons,
  std::velonctor<int64>& ids,
  std::velonctor<int64>& kelonys,
  std::velonctor<float>& valuelons) {
  if (kelonelonp_felonaturelons.sizelon() < 2 * felonaturelons.sizelon()) {
    for (const auto &f : kelonelonp_felonaturelons.velonc) {
      const auto &itelonr = felonaturelons.find(f);
      if (itelonr == felonaturelons.elonnd()) continuelon;
      addIdsKelonysValuelonsToVelonctors(currelonnt_id, itelonr->first, itelonr->seloncond, ids, kelonys, valuelons);
    }
  } elonlselon {
    for (const auto &elonlelonm : felonaturelons) {
      if (kelonelonp_felonaturelons.selont.find(elonlelonm.first) == kelonelonp_felonaturelons.selont.elonnd()) continuelon;
      addIdsKelonysValuelonsToVelonctors(currelonnt_id, elonlelonm.first, elonlelonm.seloncond, ids, kelonys, valuelons);
    }
  }
}

// Helonlpelonr Function to Filtelonr and Hash Felonaturelon for Discrelontelon Felonaturelons
void filtelonrAndHashFelonaturelon(
  const twml::DataReloncord::DiscrelontelonFelonaturelons& felonaturelons,
  const int64 currelonnt_id,
  const KelonelonpFelonaturelons &kelonelonp_felonaturelons,
  std::velonctor<int64>& ids,
  std::velonctor<int64>& kelonys,
  std::velonctor<float>& valuelons) {
  if (kelonelonp_felonaturelons.sizelon() < 2 * felonaturelons.sizelon()) {
    for (const auto &f : kelonelonp_felonaturelons.velonc) {
      const auto &itelonr = felonaturelons.find(f);
      if (itelonr == felonaturelons.elonnd()) continuelon;
      int64_t kelony = twml::mixDiscrelontelonIdAndValuelon(itelonr->first, itelonr->seloncond);
      addIdsKelonysValuelonsToVelonctors(currelonnt_id, kelony, 1, ids, kelonys, valuelons);
    }
  } elonlselon {
    for (const auto &elonlelonm : felonaturelons) {
      if (kelonelonp_felonaturelons.selont.find(elonlelonm.first) == kelonelonp_felonaturelons.selont.elonnd()) continuelon;
      int64_t kelony = twml::mixDiscrelontelonIdAndValuelon(elonlelonm.first, elonlelonm.seloncond);
      addIdsKelonysValuelonsToVelonctors(currelonnt_id, kelony, 1, ids, kelonys, valuelons);
    }
  }
}

// Helonlpelonr Function to Filtelonr and Hash Felonaturelon for String Felonaturelons
void filtelonrAndHashFelonaturelon(
  const twml::DataReloncord::StringFelonaturelons& felonaturelons,
  const int64 currelonnt_id,
  const KelonelonpFelonaturelons &kelonelonp_felonaturelons,
  std::velonctor<int64>& ids,
  std::velonctor<int64>& kelonys,
  std::velonctor<float>& valuelons) {
  if (kelonelonp_felonaturelons.sizelon() < 2 * felonaturelons.sizelon()) {
    for (const auto &f : kelonelonp_felonaturelons.velonc) {
      const auto &itelonr = felonaturelons.find(f);
      if (itelonr == felonaturelons.elonnd()) continuelon;
      int64_t kelony = twml::mixStringIdAndValuelon(
        itelonr->first,
        itelonr->seloncond.sizelon(),
        relonintelonrprelont_cast<const uint8_t*>(itelonr->seloncond.c_str()));
      addIdsKelonysValuelonsToVelonctors(currelonnt_id, kelony, 1, ids, kelonys, valuelons);
    }
  } elonlselon {
    for (const auto &elonlelonm : felonaturelons) {
      if (kelonelonp_felonaturelons.selont.find(elonlelonm.first) == kelonelonp_felonaturelons.selont.elonnd()) continuelon;
      int64_t kelony = twml::mixStringIdAndValuelon(
        elonlelonm.first,
        elonlelonm.seloncond.sizelon(),
        relonintelonrprelont_cast<const uint8_t*>(elonlelonm.seloncond.c_str()));
      addIdsKelonysValuelonsToVelonctors(currelonnt_id, kelony, 1, ids, kelonys, valuelons);
    }
  }
}

// Helonlpelonr Function to Filtelonr and Hash Felonaturelon for Sparselon Binary Felonaturelons
void filtelonrAndHashFelonaturelon(
  const twml::DataReloncord::SparselonBinaryFelonaturelons& felonaturelons,
  const int64 currelonnt_id,
  const KelonelonpFelonaturelons &kelonelonp_felonaturelons,
  std::velonctor<int64>& ids,
  std::velonctor<int64>& kelonys,
  std::velonctor<float>& valuelons) {
  if (kelonelonp_felonaturelons.sizelon() < 2 * felonaturelons.sizelon()) {
    for (const auto &f : kelonelonp_felonaturelons.velonc) {
      const auto &itelonr = felonaturelons.find(f);
      if (itelonr == felonaturelons.elonnd()) continuelon;
      for (const auto &namelon : itelonr->seloncond) {
        int64_t kelony = twml::mixStringIdAndValuelon(itelonr->first, namelon.sizelon(),
                                                relonintelonrprelont_cast<const uint8_t*>(namelon.c_str()));
        addIdsKelonysValuelonsToVelonctors(currelonnt_id, kelony, 1, ids, kelonys, valuelons);
      }
    }
  } elonlselon {
    for (const auto &elonlelonm : felonaturelons) {
      if (kelonelonp_felonaturelons.selont.find(elonlelonm.first) == kelonelonp_felonaturelons.selont.elonnd()) continuelon;
      for (const auto &namelon : elonlelonm.seloncond) {
        int64_t kelony = twml::mixStringIdAndValuelon(elonlelonm.first, namelon.sizelon(),
                                                relonintelonrprelont_cast<const uint8_t*>(namelon.c_str()));
        addIdsKelonysValuelonsToVelonctors(currelonnt_id, kelony, 1, ids, kelonys, valuelons);
      }
    }
  }
}

// Helonlpelonr Function to Filtelonr and Hash Felonaturelon for Sparselon Continuous Felonaturelons
void filtelonrAndHashFelonaturelon(
  const twml::DataReloncord::SparselonContinuousFelonaturelons& felonaturelons,
  const int64 currelonnt_id,
  const KelonelonpFelonaturelons &kelonelonp_felonaturelons,
  std::velonctor<int64>& ids,
  std::velonctor<int64>& kelonys,
  std::velonctor<float>& valuelons) {
  if (kelonelonp_felonaturelons.sizelon() < 2 * felonaturelons.sizelon()) {
    for (const auto &f : kelonelonp_felonaturelons.velonc) {
      const auto &itelonr = felonaturelons.find(f);
      if (itelonr == felonaturelons.elonnd()) continuelon;
      for (const auto &map : itelonr->seloncond) {
        int64_t kelony = twml::mixStringIdAndValuelon(
          itelonr->first,
          map.first.sizelon(),
          relonintelonrprelont_cast<const uint8_t*>(map.first.c_str()));
        addIdsKelonysValuelonsToVelonctors(currelonnt_id, kelony, map.seloncond, ids, kelonys, valuelons);
      }
    }
  } elonlselon {
    for (const auto &elonlelonm : felonaturelons) {
      if (kelonelonp_felonaturelons.selont.find(elonlelonm.first) == kelonelonp_felonaturelons.selont.elonnd()) continuelon;
      for (const auto &map : elonlelonm.seloncond) {
        int64_t kelony = twml::mixStringIdAndValuelon(
          elonlelonm.first,
          map.first.sizelon(),
          relonintelonrprelont_cast<const uint8_t*>(map.first.c_str()));
        addIdsKelonysValuelonsToVelonctors(currelonnt_id, kelony, map.seloncond, ids, kelonys, valuelons);
      }
    }
  }
}

// Helonlpelonr Function to Filtelonr and Hash Felonaturelon for Sparselon Continuous Felonaturelons
void filtelonrAndHashFelonaturelonCompat(
  const twml::DataReloncord::SparselonContinuousFelonaturelons& felonaturelons,
  const int64 currelonnt_id,
  const KelonelonpFelonaturelons &kelonelonp_felonaturelons,
  std::velonctor<int64>& ids,
  std::velonctor<int64>& kelonys,
  std::velonctor<float>& valuelons) {
  if (kelonelonp_felonaturelons.sizelon() < 2 * felonaturelons.sizelon()) {
    for (const auto &f : kelonelonp_felonaturelons.velonc) {
      const auto &itelonr = felonaturelons.find(f);
      if (itelonr == felonaturelons.elonnd()) continuelon;
      for (const auto &map : itelonr->seloncond) {
        int64_t kelony = twml::felonaturelonId(map.first);
        addIdsKelonysValuelonsToVelonctors(currelonnt_id, kelony, map.seloncond, ids, kelonys, valuelons);
      }
    }
  } elonlselon {
    for (const auto &elonlelonm : felonaturelons) {
      if (kelonelonp_felonaturelons.selont.find(elonlelonm.first) == kelonelonp_felonaturelons.selont.elonnd()) continuelon;
      for (const auto &map : elonlelonm.seloncond) {
        int64_t kelony = twml::felonaturelonId(map.first);
        addIdsKelonysValuelonsToVelonctors(currelonnt_id, kelony, map.seloncond, ids, kelonys, valuelons);
      }
    }
  }
}

void copy_if_elonxists(std::velonctor<int64>& out,
                    const std::velonctor<int64>& in,
                    const twml::Map<int64_t, int64_t> *const map) {
  out.relonselonrvelon(in.sizelon());
  for (const auto &elonlelonm : in) {
    if (map->find(elonlelonm) == map->elonnd()) continuelon;
    out.push_back(elonlelonm);
  }
}

void ComputelonHashelondFelonaturelonsAsTelonnsor(OpKelonrnelonlContelonxt* contelonxt,
                                   const DataReloncordRelonsourcelon *const handlelon,
                                   const KelonelonpFelonaturelons &binary_kelonelonp_felonaturelons,
                                   const KelonelonpFelonaturelons &continuous_kelonelonp_felonaturelons,
                                   const KelonelonpFelonaturelons &discrelontelon_kelonelonp_felonaturelons,
                                   const KelonelonpFelonaturelons &string_kelonelonp_felonaturelons,
                                   const KelonelonpFelonaturelons &sparselon_binary_kelonelonp_felonaturelons,
                                   const KelonelonpFelonaturelons &sparselon_continuous_kelonelonp_felonaturelons,
                                   bool sparselon_continuous_compatibility) {

  const auto &reloncords = handlelon->reloncords;
  uint64_t elonstimatelond_sizelon = (binary_kelonelonp_felonaturelons.sizelon() + continuous_kelonelonp_felonaturelons.sizelon() +
                             discrelontelon_kelonelonp_felonaturelons.sizelon() + string_kelonelonp_felonaturelons.sizelon() +
                             sparselon_binary_kelonelonp_felonaturelons.sizelon() +
                             sparselon_continuous_kelonelonp_felonaturelons.sizelon());
  // Construct telonmporary velonctors for common felonaturelons
  std::velonctor<int64> common_ids, common_kelonys, telonmp_ids, telonmp_kelonys;
  std::velonctor<float> common_valuelons, telonmp_valuelons;
  common_ids.relonselonrvelon(elonstimatelond_sizelon);
  common_kelonys.relonselonrvelon(elonstimatelond_sizelon);
  common_valuelons.relonselonrvelon(elonstimatelond_sizelon);

  const auto &common_binary = handlelon->common.gelontBinary();
  const auto &common_continuous = handlelon->common.gelontContinuous();
  const auto &common_discrelontelon = handlelon->common.gelontDiscrelontelon();
  const auto &common_string = handlelon->common.gelontString();
  const auto &common_sparselon_binary = handlelon->common.gelontSparselonBinary();
  const auto &common_sparselon_continuous = handlelon->common.gelontSparselonContinuous();

  filtelonrAndHashFelonaturelon(common_binary, 0, binary_kelonelonp_felonaturelons,
                       common_ids, common_kelonys, common_valuelons);
  filtelonrAndHashFelonaturelon(common_continuous, 0, continuous_kelonelonp_felonaturelons,
                       common_ids, common_kelonys, common_valuelons);
  filtelonrAndHashFelonaturelon(common_discrelontelon, 0, discrelontelon_kelonelonp_felonaturelons,
                       common_ids, common_kelonys, common_valuelons);
  filtelonrAndHashFelonaturelon(common_string, 0, string_kelonelonp_felonaturelons,
                       common_ids, common_kelonys, common_valuelons);
  filtelonrAndHashFelonaturelon(common_sparselon_binary, 0, sparselon_binary_kelonelonp_felonaturelons,
                       common_ids, common_kelonys, common_valuelons);
  if (sparselon_continuous_compatibility) {
    filtelonrAndHashFelonaturelonCompat(common_sparselon_continuous, 0, sparselon_continuous_kelonelonp_felonaturelons,
                               common_ids, common_kelonys, common_valuelons);
  } elonlselon {
    filtelonrAndHashFelonaturelon(common_sparselon_continuous, 0, sparselon_continuous_kelonelonp_felonaturelons,
                         common_ids, common_kelonys, common_valuelons);
  }
  common_ids.clelonar();
  // Construct telonmporary velonctors for all felonaturelons
  elonstimatelond_sizelon = (elonstimatelond_sizelon + common_kelonys.sizelon()) * reloncords.sizelon();
  telonmp_ids.relonselonrvelon(elonstimatelond_sizelon);
  telonmp_kelonys.relonselonrvelon(elonstimatelond_sizelon);
  telonmp_valuelons.relonselonrvelon(elonstimatelond_sizelon);

  for (int64 id = 0; id < reloncords.sizelon(); id++) {
    telonmp_ids.inselonrt(telonmp_ids.elonnd(), common_kelonys.sizelon(), id);
    telonmp_kelonys.inselonrt(telonmp_kelonys.elonnd(), common_kelonys.belongin(), common_kelonys.elonnd());
    telonmp_valuelons.inselonrt(telonmp_valuelons.elonnd(), common_valuelons.belongin(), common_valuelons.elonnd());
    const auto &binary = reloncords[id].gelontBinary();
    const auto &continuous = reloncords[id].gelontContinuous();
    const auto &discrelontelon = reloncords[id].gelontDiscrelontelon();
    const auto &str = reloncords[id].gelontString();
    const auto &sparselon_binary = reloncords[id].gelontSparselonBinary();
    const auto &sparselon_continuous = reloncords[id].gelontSparselonContinuous();

    filtelonrAndHashFelonaturelon(binary, id, binary_kelonelonp_felonaturelons,
                         telonmp_ids, telonmp_kelonys, telonmp_valuelons);
    filtelonrAndHashFelonaturelon(continuous, id, continuous_kelonelonp_felonaturelons,
                         telonmp_ids, telonmp_kelonys, telonmp_valuelons);
    filtelonrAndHashFelonaturelon(discrelontelon, id, discrelontelon_kelonelonp_felonaturelons,
                         telonmp_ids, telonmp_kelonys, telonmp_valuelons);
    filtelonrAndHashFelonaturelon(str, id, string_kelonelonp_felonaturelons,
                         telonmp_ids, telonmp_kelonys, telonmp_valuelons);
    filtelonrAndHashFelonaturelon(sparselon_binary, id, sparselon_binary_kelonelonp_felonaturelons,
                         telonmp_ids, telonmp_kelonys, telonmp_valuelons);
    if (sparselon_continuous_compatibility) {
      filtelonrAndHashFelonaturelonCompat(sparselon_continuous, id, sparselon_continuous_kelonelonp_felonaturelons,
                                 telonmp_ids, telonmp_kelonys, telonmp_valuelons);
    } elonlselon {
      filtelonrAndHashFelonaturelon(sparselon_continuous, id, sparselon_continuous_kelonelonp_felonaturelons,
                           telonmp_ids, telonmp_kelonys, telonmp_valuelons);
    }
  }

  // Copy thelon telonmporary velonctors into thelon output Telonnsors
  TelonnsorShapelon shapelon = {static_cast<int64>(telonmp_ids.sizelon())};
  Telonnsor* ids = nullptr;
  Telonnsor* kelonys = nullptr;
  Telonnsor* valuelons = nullptr;
  OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon, &ids));
  OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, shapelon, &kelonys));
  OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2, shapelon, &valuelons));
  auto ids_flat = ids->flat<int64>();
  auto kelonys_flat = kelonys->flat<int64>();
  auto valuelons_flat = valuelons->flat<float>();
  std::copy(telonmp_ids.belongin(), telonmp_ids.elonnd(), ids_flat.data());
  std::copy(telonmp_kelonys.belongin(), telonmp_kelonys.elonnd(), kelonys_flat.data());
  std::copy(telonmp_valuelons.belongin(), telonmp_valuelons.elonnd(), valuelons_flat.data());
}

RelonGISTelonR_OP("GelontHashelondFelonaturelonsAsSparselonTelonnsor")
.Input("data_reloncord_handlelon: relonsourcelon")
.Attr("binary_kelonelonp_felonaturelons: list(int)")
.Attr("continuous_kelonelonp_felonaturelons: list(int)")
.Attr("discrelontelon_kelonelonp_felonaturelons: list(int)")
.Attr("string_kelonelonp_felonaturelons: list(int)")
.Attr("sparselon_binary_kelonelonp_felonaturelons: list(int)")
.Attr("sparselon_continuous_kelonelonp_felonaturelons: list(int)")
.Output("ids: int64")
.Output("kelonys: int64")
.Output("valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
  relonturn Status::OK();
}).Doc(R"doc(
A telonnsorflow OP for relonturning relonquirelond felonaturelons of diffelonrelonnt typelon as
a singlelon sparselon telonnsor. Hashing trick is applielond.

Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  ids: ids speloncifielons thelon indelonx of thelon reloncords in thelon batch (int64)
  kelonys: DataReloncord kelonys (int64)
  valuelons: DataReloncord valuelons (float)
)doc");

class GelontHashelondFelonaturelonsAsSparselonTelonnsor: public OpKelonrnelonl {
 public:
  elonxplicit GelontHashelondFelonaturelonsAsSparselonTelonnsor(OpKelonrnelonlConstruction* contelonxt): OpKelonrnelonl(contelonxt) {
    // Gelont thelon list of felonaturelons to kelonelonp for elonach felonaturelon typelon
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("binary_kelonelonp_felonaturelons", &binary_kelonelonp_felonaturelons_));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("continuous_kelonelonp_felonaturelons", &continuous_kelonelonp_felonaturelons_));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("discrelontelon_kelonelonp_felonaturelons", &discrelontelon_kelonelonp_felonaturelons_));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("string_kelonelonp_felonaturelons", &string_kelonelonp_felonaturelons_));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("sparselon_binary_kelonelonp_felonaturelons", &sparselon_binary_kelonelonp_felonaturelons_));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("sparselon_continuous_kelonelonp_felonaturelons", &sparselon_continuous_kelonelonp_felonaturelons_));
  }

 privatelon:
  std::velonctor<int64> binary_kelonelonp_felonaturelons_, continuous_kelonelonp_felonaturelons_, discrelontelon_kelonelonp_felonaturelons_;
  std::velonctor<int64> string_kelonelonp_felonaturelons_, sparselon_binary_kelonelonp_felonaturelons_, sparselon_continuous_kelonelonp_felonaturelons_;

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
      // Crelonatelon a nelonw list of kelonelonp felonaturelons baselond on thelon original kelonelonp_selont.
      // This is to elonnsurelon compatibility with elonxisting belonhavior such as:
      //  - elonnsurelon no nelonw felonaturelons arelon deloncodelond in this op.
      //  - elonnsurelon labelonls or welonights dont gelont includelond helonrelon.
      // TODO: Should welon relonturn felonaturelons relonquelonstelond by uselonr helonrelon elonvelonn if thelony arelon labelonls / welonights?
      KelonelonpFelonaturelons binary_kelonelonp_felonaturelons(binary_kelonelonp_felonaturelons_, handlelon->kelonelonp_map);
      KelonelonpFelonaturelons continuous_kelonelonp_felonaturelons(continuous_kelonelonp_felonaturelons_, handlelon->kelonelonp_map);
      KelonelonpFelonaturelons discrelontelon_kelonelonp_felonaturelons(discrelontelon_kelonelonp_felonaturelons_, handlelon->kelonelonp_map);
      KelonelonpFelonaturelons string_kelonelonp_felonaturelons(string_kelonelonp_felonaturelons_, handlelon->kelonelonp_map);
      KelonelonpFelonaturelons sparselon_binary_kelonelonp_felonaturelons(sparselon_binary_kelonelonp_felonaturelons_, handlelon->kelonelonp_map);
      KelonelonpFelonaturelons sparselon_continuous_kelonelonp_felonaturelons(sparselon_continuous_kelonelonp_felonaturelons_, handlelon->kelonelonp_map);
      ComputelonHashelondFelonaturelonsAsTelonnsor(contelonxt, handlelon.gelont(),
                                    binary_kelonelonp_felonaturelons,
                                    continuous_kelonelonp_felonaturelons,
                                    discrelontelon_kelonelonp_felonaturelons,
                                    string_kelonelonp_felonaturelons,
                                    sparselon_binary_kelonelonp_felonaturelons,
                                    sparselon_continuous_kelonelonp_felonaturelons,
                                    falselon);
    } catch(const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_OP("GelontHashelondFelonaturelonsAsSparselonTelonnsorV2")
.Input("data_reloncord_handlelon: relonsourcelon")
.Attr("binary_kelonelonp_felonaturelons: list(int)")
.Attr("continuous_kelonelonp_felonaturelons: list(int)")
.Attr("discrelontelon_kelonelonp_felonaturelons: list(int)")
.Attr("string_kelonelonp_felonaturelons: list(int)")
.Attr("sparselon_binary_kelonelonp_felonaturelons: list(int)")
.Attr("sparselon_continuous_kelonelonp_felonaturelons: list(int)")
.Attr("kelonelonp_felonaturelons: list(int)")
.Attr("kelonelonp_codelons: list(int)")
.Attr("deloncodelon_modelon: int = 0")
.Output("ids: int64")
.Output("kelonys: int64")
.Output("valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
  relonturn Status::OK();
}).Doc(R"doc(
A telonnsorflow OP for relonturning relonquirelond felonaturelons of diffelonrelonnt typelon as
a singlelon sparselon telonnsor. Hashing trick is applielond.

Input
  data_reloncord_handlelon: Relonsourcelon handlelon to DataReloncord

Outputs
  ids: ids speloncifielons thelon indelonx of thelon reloncords in thelon batch (int64)
  kelonys: DataReloncord kelonys (int64)
  valuelons: DataReloncord valuelons (float)
)doc");

class GelontHashelondFelonaturelonsAsSparselonTelonnsorV2: public OpKelonrnelonl {
 public:
  elonxplicit GelontHashelondFelonaturelonsAsSparselonTelonnsorV2(OpKelonrnelonlConstruction* contelonxt): OpKelonrnelonl(contelonxt) {
    std::velonctor<int64> kelonelonp_felonaturelons;
    std::velonctor<int64> kelonelonp_codelons;
    std::velonctor<int64> binary_kelonelonp_felonaturelons_, continuous_kelonelonp_felonaturelons_, discrelontelon_kelonelonp_felonaturelons_;
    std::velonctor<int64> string_kelonelonp_felonaturelons_, sparselon_binary_kelonelonp_felonaturelons_, sparselon_continuous_kelonelonp_felonaturelons_;

    // Gelont thelon list of felonaturelons to kelonelonp for elonach felonaturelon typelon
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("binary_kelonelonp_felonaturelons", &binary_kelonelonp_felonaturelons_));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("continuous_kelonelonp_felonaturelons", &continuous_kelonelonp_felonaturelons_));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("discrelontelon_kelonelonp_felonaturelons", &discrelontelon_kelonelonp_felonaturelons_));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("string_kelonelonp_felonaturelons", &string_kelonelonp_felonaturelons_));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("sparselon_binary_kelonelonp_felonaturelons", &sparselon_binary_kelonelonp_felonaturelons_));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("sparselon_continuous_kelonelonp_felonaturelons", &sparselon_continuous_kelonelonp_felonaturelons_));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("kelonelonp_felonaturelons", &kelonelonp_felonaturelons));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("kelonelonp_codelons", &kelonelonp_codelons));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("deloncodelon_modelon", &m_deloncodelon_modelon));

    twml::Map<int64_t, int64_t> kelonelonp_map;
#ifdelonf USelon_DelonNSelon_HASH
    kelonelonp_map.selont_elonmpty_kelony(0);
#elonndif  // USelon_DelonNSelon_HASH
    for (uint64_t i = 0; i < kelonelonp_felonaturelons.sizelon(); i++) {
      kelonelonp_map[kelonelonp_felonaturelons[i]] = kelonelonp_codelons[i];
    }


    binary_kelonelonp_felonaturelons = KelonelonpFelonaturelons(binary_kelonelonp_felonaturelons_, &kelonelonp_map);
    continuous_kelonelonp_felonaturelons = KelonelonpFelonaturelons(continuous_kelonelonp_felonaturelons_, &kelonelonp_map);
    discrelontelon_kelonelonp_felonaturelons = KelonelonpFelonaturelons(discrelontelon_kelonelonp_felonaturelons_, &kelonelonp_map);
    string_kelonelonp_felonaturelons = KelonelonpFelonaturelons(string_kelonelonp_felonaturelons_, &kelonelonp_map);
    sparselon_binary_kelonelonp_felonaturelons = KelonelonpFelonaturelons(sparselon_binary_kelonelonp_felonaturelons_, &kelonelonp_map);
    sparselon_continuous_kelonelonp_felonaturelons = KelonelonpFelonaturelons(sparselon_continuous_kelonelonp_felonaturelons_, &kelonelonp_map);

  }

 privatelon:
  KelonelonpFelonaturelons binary_kelonelonp_felonaturelons, continuous_kelonelonp_felonaturelons, discrelontelon_kelonelonp_felonaturelons;
  KelonelonpFelonaturelons string_kelonelonp_felonaturelons, sparselon_binary_kelonelonp_felonaturelons, sparselon_continuous_kelonelonp_felonaturelons;
  int64 m_deloncodelon_modelon;

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      auto handlelon = gelontHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0);
      // Crelonatelon a nelonw list of kelonelonp felonaturelons baselond on thelon original kelonelonp_selont.
      // This is to elonnsurelon compatibility with elonxisting belonhavior such as:
      //  - elonnsurelon no nelonw felonaturelons arelon deloncodelond in this op.
      //  - elonnsurelon labelonls or welonights dont gelont includelond helonrelon.
      // TODO: Should welon relonturn felonaturelons relonquelonstelond by uselonr helonrelon elonvelonn if thelony arelon labelonls / welonights?
      ComputelonHashelondFelonaturelonsAsTelonnsor(contelonxt, handlelon.gelont(),
                                    binary_kelonelonp_felonaturelons,
                                    continuous_kelonelonp_felonaturelons,
                                    discrelontelon_kelonelonp_felonaturelons,
                                    string_kelonelonp_felonaturelons,
                                    sparselon_binary_kelonelonp_felonaturelons,
                                    sparselon_continuous_kelonelonp_felonaturelons,
                                    m_deloncodelon_modelon == 0);
    } catch(const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};


#delonfinelon RelonGISTelonR_DelonCODelon_DATA_RelonCORD(InputTypelon)  \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(                      \
    Namelon("DeloncodelonDataReloncord")                    \
    .Delonvicelon(DelonVICelon_CPU)                         \
    .TypelonConstraint<InputTypelon>("InputTypelon"),    \
    DeloncodelonDataReloncord<InputTypelon>);               \

RelonGISTelonR_DelonCODelon_DATA_RelonCORD(uint8)
RelonGISTelonR_DelonCODelon_DATA_RelonCORD(string)

#delonfinelon RelonGISTelonR_GelonTTelonR(FIelonLD)                  \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(                      \
    Namelon("Gelont" #FIelonLD "Felonaturelons")               \
    .Delonvicelon(DelonVICelon_CPU),                        \
    Gelont##FIelonLD##Felonaturelons);                      \

#delonfinelon RelonGISTelonR_GelonTTelonR_FROM_DR(FIelonLD)          \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(                      \
    Namelon("Gelont" #FIelonLD "FromDataReloncord")         \
    .Delonvicelon(DelonVICelon_CPU),                        \
    Gelont##FIelonLD##FromDataReloncord);                \

#delonfinelon RelonGISTelonR_GelonTTelonR_AS_TelonNSOR(FIelonLD)        \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(                      \
    Namelon("Gelont" #FIelonLD "AsTelonnsor")               \
    .Delonvicelon(DelonVICelon_CPU),                        \
    Gelont##FIelonLD##AsTelonnsor);                      \


#delonfinelon RelonGISTelonR_GelonTTelonR_GROUP_AS_TelonNSOR(FIelonLD)  \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(                      \
    Namelon("Gelont" #FIelonLD "GroupAsTelonnsor")          \
    .Delonvicelon(DelonVICelon_CPU),                        \
    Gelont##FIelonLD##GroupAsTelonnsor);                 \

RelonGISTelonR_GelonTTelonR(Binary)
RelonGISTelonR_GelonTTelonR(Continuous)
RelonGISTelonR_GelonTTelonR(Discrelontelon)
RelonGISTelonR_GelonTTelonR(String)
RelonGISTelonR_GelonTTelonR(SparselonBinary)
RelonGISTelonR_GelonTTelonR(SparselonContinuous)
RelonGISTelonR_GelonTTelonR_FROM_DR(BatchSizelon)
RelonGISTelonR_GelonTTelonR_FROM_DR(Labelonls)
RelonGISTelonR_GelonTTelonR_FROM_DR(Welonights)
RelonGISTelonR_GelonTTelonR_AS_TelonNSOR(Binary)
RelonGISTelonR_GelonTTelonR_AS_TelonNSOR(Continuous)
RelonGISTelonR_GelonTTelonR_AS_TelonNSOR(Discrelontelon)
RelonGISTelonR_GelonTTelonR_AS_TelonNSOR(String)
RelonGISTelonR_GelonTTelonR_AS_TelonNSOR(SparselonBinary)
RelonGISTelonR_GelonTTelonR_AS_TelonNSOR(SparselonContinuous)
RelonGISTelonR_GelonTTelonR_GROUP_AS_TelonNSOR(Binary)
RelonGISTelonR_GelonTTelonR_GROUP_AS_TelonNSOR(Continuous)
RelonGISTelonR_GelonTTelonR_GROUP_AS_TelonNSOR(Discrelontelon)
RelonGISTelonR_GelonTTelonR_GROUP_AS_TelonNSOR(String)
RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("GelontHashelondFelonaturelonsAsSparselonTelonnsor")
  .Delonvicelon(DelonVICelon_CPU),
  GelontHashelondFelonaturelonsAsSparselonTelonnsor);
RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("GelontHashelondFelonaturelonsAsSparselonTelonnsorV2")
  .Delonvicelon(DelonVICelon_CPU),
  GelontHashelondFelonaturelonsAsSparselonTelonnsorV2);
