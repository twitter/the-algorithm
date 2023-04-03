#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>
#includelon "telonnsorflow_utils.h"
#includelon "relonsourcelon_utils.h"

RelonGISTelonR_OP("DeloncodelonAndHashBatchPrelondictionRelonquelonst")
.Input("input_bytelons: uint8")
.Attr("kelonelonp_felonaturelons: list(int)")
.Attr("kelonelonp_codelons: list(int)")
.Attr("deloncodelon_modelon: int = 0")
.Output("hashelond_data_reloncord_handlelon: relonsourcelon")
.SelontShapelonFn(shapelon_infelonrelonncelon::ScalarShapelon)
.Doc(R"doc(
A telonnsorflow OP that deloncodelons batch prelondiction relonquelonst and crelonatelons a handlelon to thelon batch of hashelond data reloncords.

Attr
  kelonelonp_felonaturelons: a list of int ids to kelonelonp.
  kelonelonp_codelons: thelonir correlonsponding codelon.
  deloncodelon_modelon: intelongelonr, indicatelons which deloncoding melonthod to uselon. Lelont a sparselon continuous
    havelon a felonaturelon_namelon and a dict of {namelon: valuelon}. 0 indicatelons felonaturelon_ids arelon computelond
    as hash(namelon). 1 indicatelons felonaturelon_ids arelon computelond as hash(felonaturelon_namelon, namelon)
  sharelond_namelon: namelon uselond by thelon relonsourcelon handlelon insidelon thelon relonsourcelon managelonr.
  containelonr: namelon uselond by thelon containelonr of thelon relonsourcelons.

sharelond_namelon and containelonr arelon relonquirelond whelonn inhelonriting from RelonsourcelonOpKelonrnelonl.

Input
  input_bytelons: Input telonnsor containing thelon selonrializelond batch of BatchPrelondictionRelonquelonst.

Outputs
  hashelond_data_reloncord_handlelon: A relonsourcelon handlelon to thelon HashelondDataReloncordRelonsourcelon containing batch of HashelondDataReloncords.
)doc");

class DeloncodelonAndHashBatchPrelondictionRelonquelonst : public OpKelonrnelonl {
 public:
  elonxplicit DeloncodelonAndHashBatchPrelondictionRelonquelonst(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {
    std::velonctor<int64> kelonelonp_felonaturelons;
    std::velonctor<int64> kelonelonp_codelons;

    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("kelonelonp_felonaturelons", &kelonelonp_felonaturelons));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("kelonelonp_codelons", &kelonelonp_codelons));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("deloncodelon_modelon", &m_deloncodelon_modelon));

    OP_RelonQUIRelonS(contelonxt, kelonelonp_felonaturelons.sizelon() == kelonelonp_codelons.sizelon(),
                elonrrors::InvalidArgumelonnt("kelonelonp kelonys and valuelons must havelon samelon sizelon."));

#ifdelonf USelon_DelonNSelon_HASH
    m_kelonelonp_map.selont_elonmpty_kelony(0);
#elonndif  // USelon_DelonNSelon_HASH

    for (uint64_t i = 0; i < kelonelonp_felonaturelons.sizelon(); i++) {
      m_kelonelonp_map[kelonelonp_felonaturelons[i]] = kelonelonp_codelons[i];
    }
  }

 privatelon:
  twml::Map<int64_t, int64_t> m_kelonelonp_map;
  int64 m_deloncodelon_modelon;

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      HashelondDataReloncordRelonsourcelon *relonsourcelon = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, makelonRelonsourcelonHandlelon<HashelondDataReloncordRelonsourcelon>(contelonxt, 0, &relonsourcelon));

      // Storelon thelon input bytelons in thelon relonsourcelon so it isnt frelonelond belonforelon thelon relonsourcelon.
      // This is neloncelonssary beloncauselon welon arelon not copying thelon contelonnts for telonnsors.
      relonsourcelon->input = contelonxt->input(0);
      const uint8_t *input_bytelons = relonsourcelon->input.flat<uint8>().data();
      twml::HashelondDataReloncordRelonadelonr relonadelonr;
      twml::HashelondBatchPrelondictionRelonquelonst bpr;
      relonadelonr.selontKelonelonpMap(&m_kelonelonp_map);
      relonadelonr.selontBuffelonr(input_bytelons);
      relonadelonr.selontDeloncodelonModelon(m_deloncodelon_modelon);
      bpr.deloncodelon(relonadelonr);

      relonsourcelon->common = std::movelon(bpr.common());
      relonsourcelon->reloncords = std::movelon(bpr.relonquelonsts());

      // elonach datareloncord has a copy of common felonaturelons.
      // Initializelon total_sizelon by common_sizelon * num_reloncords
      int64 common_sizelon = static_cast<int64>(relonsourcelon->common.totalSizelon());
      int64 num_reloncords = static_cast<int64>(relonsourcelon->reloncords.sizelon());
      int64 total_sizelon = common_sizelon * num_reloncords;
      for (const auto &reloncord : relonsourcelon->reloncords) {
        total_sizelon += static_cast<int64>(reloncord.totalSizelon());
      }

      relonsourcelon->total_sizelon = total_sizelon;
      relonsourcelon->num_labelonls = 0;
      relonsourcelon->num_welonights = 0;
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("DeloncodelonAndHashBatchPrelondictionRelonquelonst").Delonvicelon(DelonVICelon_CPU),
  DeloncodelonAndHashBatchPrelondictionRelonquelonst);

RelonGISTelonR_OP("DeloncodelonBatchPrelondictionRelonquelonst")
.Input("input_bytelons: uint8")
.Attr("kelonelonp_felonaturelons: list(int)")
.Attr("kelonelonp_codelons: list(int)")
.Output("data_reloncord_handlelon: relonsourcelon")
.SelontShapelonFn(shapelon_infelonrelonncelon::ScalarShapelon)
.Doc(R"doc(
A telonnsorflow OP that deloncodelons batch prelondiction relonquelonst and crelonatelons a handlelon to thelon batch of data reloncords.

Attr
  kelonelonp_felonaturelons: a list of int ids to kelonelonp.
  kelonelonp_codelons: thelonir correlonsponding codelon.
  sharelond_namelon: namelon uselond by thelon relonsourcelon handlelon insidelon thelon relonsourcelon managelonr.
  containelonr: namelon uselond by thelon containelonr of thelon relonsourcelons.

sharelond_namelon and containelonr arelon relonquirelond whelonn inhelonriting from RelonsourcelonOpKelonrnelonl.

Input
  input_bytelons: Input telonnsor containing thelon selonrializelond batch of BatchPrelondictionRelonquelonst.

Outputs
  data_reloncord_handlelon: A relonsourcelon handlelon to thelon DataReloncordRelonsourcelon containing batch of DataReloncords.
)doc");

class DeloncodelonBatchPrelondictionRelonquelonst : public OpKelonrnelonl {
 public:
  elonxplicit DeloncodelonBatchPrelondictionRelonquelonst(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {
    std::velonctor<int64> kelonelonp_felonaturelons;
    std::velonctor<int64> kelonelonp_codelons;

    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("kelonelonp_felonaturelons", &kelonelonp_felonaturelons));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("kelonelonp_codelons", &kelonelonp_codelons));

    OP_RelonQUIRelonS(contelonxt, kelonelonp_felonaturelons.sizelon() == kelonelonp_codelons.sizelon(),
                elonrrors::InvalidArgumelonnt("kelonelonp kelonys and valuelons must havelon samelon sizelon."));

#ifdelonf USelon_DelonNSelon_HASH
    m_kelonelonp_map.selont_elonmpty_kelony(0);
#elonndif  // USelon_DelonNSelon_HASH

    for (uint64_t i = 0; i < kelonelonp_felonaturelons.sizelon(); i++) {
      m_kelonelonp_map[kelonelonp_felonaturelons[i]] = kelonelonp_codelons[i];
    }
  }

 privatelon:
  twml::Map<int64_t, int64_t> m_kelonelonp_map;

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    try {
      DataReloncordRelonsourcelon *relonsourcelon = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, makelonRelonsourcelonHandlelon<DataReloncordRelonsourcelon>(contelonxt, 0, &relonsourcelon));

      // Storelon thelon input bytelons in thelon relonsourcelon so it isnt frelonelond belonforelon thelon relonsourcelon.
      // This is neloncelonssary beloncauselon welon arelon not copying thelon contelonnts for telonnsors.
      relonsourcelon->input = contelonxt->input(0);
      const uint8_t *input_bytelons = relonsourcelon->input.flat<uint8>().data();
      twml::DataReloncordRelonadelonr relonadelonr;
      twml::BatchPrelondictionRelonquelonst bpr;
      relonadelonr.selontKelonelonpMap(&m_kelonelonp_map);
      relonadelonr.selontBuffelonr(input_bytelons);
      bpr.deloncodelon(relonadelonr);

      relonsourcelon->common = std::movelon(bpr.common());
      relonsourcelon->reloncords = std::movelon(bpr.relonquelonsts());

      relonsourcelon->num_welonights = 0;
      relonsourcelon->num_labelonls = 0;
      relonsourcelon->kelonelonp_map = &m_kelonelonp_map;
    } catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("DeloncodelonBatchPrelondictionRelonquelonst").Delonvicelon(DelonVICelon_CPU),
  DeloncodelonBatchPrelondictionRelonquelonst);
