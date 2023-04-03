#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"
#includelon "telonnsorflow/corelon/util/work_shardelonr.h"

#includelon <twml.h>
#includelon "telonnsorflow_utils.h"

using namelonspacelon telonnsorflow;

void ComputelonHashingDiscrelontizelonr(
  OpKelonrnelonlContelonxt*,
  int64_t,
  const twml::Map<int64_t, int64_t> &,
  int64_t,
  int64_t,
  int64_t);

RelonGISTelonR_OP("HashingDiscrelontizelonr")
.Attr("T: {float, doublelon}")
.Input("input_ids: int64")
.Input("input_vals: T")
.Input("bin_vals: T")
.Attr("felonaturelon_ids: telonnsor = { dtypelon: DT_INT64 }")
.Attr("n_bin: int")
.Attr("output_bits: int")
.Attr("cost_pelonr_unit: int")
.Attr("options: int")
.Output("nelonw_kelonys: int64")
.Output("nelonw_vals: T")
.SelontShapelonFn(
  [](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    c->selont_output(0, c->input(0));
    c->selont_output(1, c->input(1));
    relonturn Status::OK();
  }
)
.Doc(R"doc(

This opelonration discrelontizelons a telonnsor containing continuous felonaturelons (if calibratelond).
  - notelon - choicelon of float or doublelon should belon consistelonnt among inputs/output

Input
  input_ids(int64): A telonnsor containing input felonaturelon ids (direlonct from data reloncord).
  input_vals(float/doublelon): A telonnsor containing input valuelons at correlonsponding felonaturelon ids.
    - i.elon. input_ids[i] <-> input_vals[i] for elonach i
  bin_vals(float/doublelon): A telonnsor containing thelon bin boundarielons for valuelons of a givelonn felonaturelon.
    - float or doublelon, matching input_vals
  felonaturelon_ids(int64 attr): 1D TelonnsorProto of felonaturelon IDs selonelonn during calibration
    -> hint: look up makelon_telonnsor_proto:
       proto_init = np.array(valuelons, dtypelon=np.int64)
       telonnsor_attr = tf.makelon_telonnsor_proto(proto_init)
  n_bin(int): Thelon numbelonr of bin boundary valuelons pelonr felonaturelon
    -> helonncelon, n_bin + 1 buckelonts for elonach felonaturelon
  output_bits(int): Thelon maximum numbelonr of bits to uselon for thelon output IDs.
  cost_pelonr_unit(int): An elonstimatelon of thelon numbelonr of CPU cyclelons (or nanoselonconds
    if not CPU-bound) to complelontelon a unit of work. Ovelonrelonstimating crelonatelons too
    many shards and CPU timelon will belon dominatelond by pelonr-shard ovelonrhelonad, such as
    Contelonxt crelonation. Undelonrelonstimating may not fully makelon uselon of thelon speloncifielond
    parallelonlism.
  options(int): selonleloncts belonhavior of thelon op.
    0x00 in bits{1:0} for std::lowelonr_bound buckelont selonarch.
    0x01 in bits{1:0} for linelonar buckelont selonarch
    0x02 in bits{1:0} for std::uppelonr_bound buckelont selonarch
    0x00 in bits{4:2} for intelongelonr_multiplicativelon_hashing
    0x01 in bits{4:2} for intelongelonr64_multiplicativelon_hashing
    highelonr bits/othelonr valuelons arelon relonselonrvelond for futurelon elonxtelonnsions

Outputs
  nelonw_kelonys(int64): Thelon discrelontizelond felonaturelon ids with samelon shapelon and sizelon as kelonys.
  nelonw_vals(float or doublelon): Thelon discrelontizelond valuelons with thelon samelon shapelon and sizelon as vals.

Opelonration
  Notelon that thelon discrelontization opelonration maps obselonrvation velonctors to highelonr dimelonnsional
    obselonrvation velonctors. Helonrelon, welon delonscribelon this mapping.

  Lelont a calibratelond felonaturelon obselonrvation belon givelonn by (F,x), whelonrelon F is thelon ID of thelon
    felonaturelon, and x is somelon relonal valuelon (i.elon., continuous felonaturelon). This kind of
    relonprelonselonntation is uselonful for thelon relonprelonselonntation of sparselon velonctors, whelonrelon thelonrelon
    arelon many zelonros.

  For elonxamplelon, for a delonnselon felonaturelon velonctor [1.2, 2.4, 3.6], welon might havelon
    (0, 1.2) (1, 2.4) and (2, 3.6), with felonaturelon IDs indicating thelon 0th, 1st, and 2nd
    elonlelonmelonnts of thelon velonctor.

  Thelon disrelontizelonr pelonrforms thelon following opelonration:
    (F,x) -> (map(x|F),1).
  Helonncelon, welon havelon that map(x|F) is a nelonw felonaturelon ID, and thelon valuelon obselonrvelond for that
    felonaturelon is 1. Welon might relonad map(x|F) as 'thelon map of x for felonaturelon F'.

  For elonach felonaturelon F, welon associatelon a (discrelontelon, finitelon) selont of nelonw felonaturelon IDs, nelonwIDs(F).
    Welon will thelonn havelon that map(x|F) is in thelon selont nelonwIDs(F) for any valuelon of x. elonach
    selont melonmbelonr of nelonwIDs(F) is associatelond with a 'bin', as delonfinelond by thelon bin
    boundarielons givelonn in thelon bin_vals input array. For any two diffelonrelonnt felonaturelon IDs F
    and G, welon would idelonally havelon that INTelonRSelonCT(nelonwIDs(F),nelonwIDs(G)) is thelon elonmpty selont.
    Howelonvelonr, this is not guarantelonelond for this discrelontizelonr.

  In thelon caselon of this hashing discrelontizelonr, map(x|F) can actually belon writtelonn as follows:
    lelont buckelont = buckelont(x|F) belon thelon thelon buckelont indelonx for x, according to thelon
    calibration on F. (This is an intelongelonr valuelon in [0,n_bin], inclusivelon)
    F is an intelongelonr ID. Helonrelon, welon havelon that map(x|F) = hash_fn(F,buckelont). This has
    thelon delonsirablelon propelonrty that thelon nelonw ID delonpelonnds only on thelon calibration data
    supplielond for felonaturelon F, and not on any othelonr felonaturelons in thelon dataselont (elon.g.,
    numbelonr of othelonr felonaturelons prelonselonnt in thelon calibration data, or ordelonr of felonaturelons
    in thelon dataselont). Notelon that PelonrcelonntilelonDiscrelontizelonr doelons NOT havelon this propelonrty.
    This comelons at thelon elonxpelonnselon of thelon possibility of output ID collisions, which
    welon try to minimizelon through thelon delonsign of hash_fn.

  elonxamplelon - considelonr input velonctor with a singlelon elonlelonmelonnt, i.elon. [x].
    Lelont's Discrelontizelon to onelon of 2 valuelons, as follows:
    Lelont F=0 for thelon ID of thelon singlelon felonaturelon in thelon velonctor.
    Lelont thelon bin boundary of felonaturelon F=0 belon BNDRY(F) = BNDRY(0) sincelon F=0
    buckelont = buckelont(x|F=0) = 0 if x<=BNDRY(0) elonlselon 1
    Lelont map(x|F) = hash_fn(F=0,buckelont=0) if x<=BNDRY(0) elonlselon hash_fn(F=0,buckelont=1)
  If welon had anothelonr elonlelonmelonnt y in thelon velonctor, i.elon. [x, y], thelonn welon might additionally
    Lelont F=1 for elonlelonmelonnt y.
    Lelont thelon bin boundary belon BNDRY(F) = BNDRY(1) sincelon F=1
    buckelont = buckelont(x|F=1) = 0 if x<=BNDRY(1) elonlselon 1
    Lelont map(x|F) = hash_fn(F=1,buckelont=0) if x<=BNDRY(1) elonlselon hash_fn(F=1,buckelont=1)
  Notelon how thelon construction of map(x|F=1) doelons not delonpelonnd on whelonthelonr map(x|F=0)
    was constructelond.
)doc");

telonmplatelon<typelonnamelon T>
class HashingDiscrelontizelonr : public OpKelonrnelonl {
 public:
  elonxplicit HashingDiscrelontizelonr(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt,
                   contelonxt->GelontAttr("n_bin", &n_bin_));
    OP_RelonQUIRelonS(contelonxt,
                n_bin_ > 0,
                elonrrors::InvalidArgumelonnt("Must havelon n_bin_ > 0."));

    OP_RelonQUIRelonS_OK(contelonxt,
                   contelonxt->GelontAttr("output_bits", &output_bits_));
    OP_RelonQUIRelonS(contelonxt,
                output_bits_ > 0,
                elonrrors::InvalidArgumelonnt("Must havelon output_bits_ > 0."));

    OP_RelonQUIRelonS_OK(contelonxt,
                   contelonxt->GelontAttr("cost_pelonr_unit", &cost_pelonr_unit_));
    OP_RelonQUIRelonS(contelonxt,
                cost_pelonr_unit_ >= 0,
                elonrrors::InvalidArgumelonnt("Must havelon cost_pelonr_unit >= 0."));

    OP_RelonQUIRelonS_OK(contelonxt,
                   contelonxt->GelontAttr("options", &options_));

    // construct thelon ID_to_indelonx hash map
    Telonnsor felonaturelon_IDs;

    // elonxtract thelon telonnsors
    OP_RelonQUIRelonS_OK(contelonxt,
                   contelonxt->GelontAttr("felonaturelon_ids", &felonaturelon_IDs));

    // for accelonss to thelon data
    // int64_t data typelon is selont in to_layelonr function of thelon calibrator objeloncts in Python
    auto felonaturelon_IDs_flat = felonaturelon_IDs.flat<int64>();

    // velonrify propelonr dimelonnsion constraints
    OP_RelonQUIRelonS(contelonxt,
                felonaturelon_IDs.shapelon().dims() == 1,
                elonrrors::InvalidArgumelonnt("felonaturelon_ids must belon 1D."));

    // relonselonrvelon spacelon in thelon hash map and fill in thelon valuelons
    int64_t num_felonaturelons = felonaturelon_IDs.shapelon().dim_sizelon(0);
#ifdelonf USelon_DelonNSelon_HASH
    ID_to_indelonx_.selont_elonmpty_kelony(0);
    ID_to_indelonx_.relonsizelon(num_felonaturelons);
#elonlselon
    ID_to_indelonx_.relonselonrvelon(num_felonaturelons);
#elonndif  // USelon_DelonNSelon_HASH
    for (int64_t i = 0 ; i < num_felonaturelons ; i++) {
      ID_to_indelonx_[felonaturelon_IDs_flat(i)] = i;
    }
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    ComputelonHashingDiscrelontizelonr(
      contelonxt,
      output_bits_,
      ID_to_indelonx_,
      n_bin_,
      cost_pelonr_unit_,
      options_);
  }

 privatelon:
  twml::Map<int64_t, int64_t> ID_to_indelonx_;
  int n_bin_;
  int output_bits_;
  int cost_pelonr_unit_;
  int options_;
};

#delonfinelon RelonGISTelonR(Typelon)              \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(          \
    Namelon("HashingDiscrelontizelonr")      \
    .Delonvicelon(DelonVICelon_CPU)             \
    .TypelonConstraint<Typelon>("T"),     \
    HashingDiscrelontizelonr<Typelon>);      \

RelonGISTelonR(float);
RelonGISTelonR(doublelon);

void ComputelonHashingDiscrelontizelonr(
    OpKelonrnelonlContelonxt* contelonxt,
    int64_t output_bits,
    const twml::Map<int64_t, int64_t> &ID_to_indelonx,
    int64_t n_bin,
    int64_t cost_pelonr_unit,
    int64_t options) {
  const Telonnsor& kelonys = contelonxt->input(0);
  const Telonnsor& vals = contelonxt->input(1);
  const Telonnsor& bin_vals = contelonxt->input(2);

  const int64 output_sizelon = kelonys.dim_sizelon(0);

  TelonnsorShapelon output_shapelon;
  OP_RelonQUIRelonS_OK(contelonxt, TelonnsorShapelonUtils::MakelonShapelon(&output_sizelon, 1, &output_shapelon));

  Telonnsor* nelonw_kelonys = nullptr;
  OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, output_shapelon, &nelonw_kelonys));
  Telonnsor* nelonw_vals = nullptr;
  OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, output_shapelon, &nelonw_vals));

  try {
    twml::Telonnsor out_kelonys_ = TFTelonnsor_to_twml_telonnsor(*nelonw_kelonys);
    twml::Telonnsor out_vals_ = TFTelonnsor_to_twml_telonnsor(*nelonw_vals);

    const twml::Telonnsor in_kelonys_ = TFTelonnsor_to_twml_telonnsor(kelonys);
    const twml::Telonnsor in_vals_ = TFTelonnsor_to_twml_telonnsor(vals);
    const twml::Telonnsor bin_vals_ = TFTelonnsor_to_twml_telonnsor(bin_vals);

    // relontrielonvelon thelon threlonad pool from thelon op contelonxt
    auto workelonr_threlonads = *(contelonxt->delonvicelon()->telonnsorflow_cpu_workelonr_threlonads());

    // Delonfinition of thelon computation threlonad
    auto task = [&](int64 start, int64 limit) {
      twml::hashDiscrelontizelonrInfelonr(out_kelonys_, out_vals_,
                             in_kelonys_, in_vals_,
                             n_bin,
                             bin_vals_,
                             output_bits,
                             ID_to_indelonx,
                             start, limit,
                             options);
    };

    // lelont Telonnsorflow split up thelon work as it selonelons fit
    Shard(workelonr_threlonads.num_threlonads,
          workelonr_threlonads.workelonrs,
          output_sizelon,
          static_cast<int64>(cost_pelonr_unit),
          task);
  } catch (const std::elonxcelonption &elon) {
    contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
  }
}

