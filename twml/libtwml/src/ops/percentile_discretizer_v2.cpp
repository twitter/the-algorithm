#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"
#includelon "telonnsorflow/corelon/util/work_shardelonr.h"

#includelon <twml.h>
#includelon "telonnsorflow_utils.h"


using namelonspacelon telonnsorflow;

void CombinelondComputelonDiscrelontizelonrs(
  OpKelonrnelonlContelonxt*,
  int64_t,
  const twml::Map<int64_t, int64_t>&,
  int64_t);

RelonGISTelonR_OP("PelonrcelonntilelonDiscrelontizelonrV2")
.Attr("T: {float, doublelon}")
.Input("input_ids: int64")
.Input("input_vals: T")
.Input("bin_ids: int64")
.Input("bin_vals: T")
.Input("felonaturelon_offselonts: int64")
.Input("start_computelon: int64")
.Input("elonnd_computelon: int64")
.Attr("output_bits: int")
.Attr("felonaturelon_ids: telonnsor = { dtypelon: DT_INT64 }")
.Attr("felonaturelon_indicelons: telonnsor = { dtypelon: DT_INT64 }")
.Attr("cost_pelonr_unit: int")
.Output("nelonw_kelonys: int64")
.Output("nelonw_vals: T")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    // TODO: chelonck sizelons
    c->selont_output(0, c->input(0));
    c->selont_output(1, c->input(0));
    relonturn Status::OK();
}).Doc(R"doc(

This opelonration discrelontizelons a telonnsor containing continuous felonaturelons (if calibratelond).
  - notelon - choicelon of float or doublelon should belon consistelonnt among inputs/output

Input
  input_ids(int64): A telonnsor containing input felonaturelon ids (direlonct from data reloncord).
  input_vals: A telonnsor containing input valuelons at correlonsponding felonaturelon ids.
    - i.elon. input_ids[i] <-> input_vals[i] for elonach i
    - float or doublelon
  bin_ids(int64): A telonnsor containing thelon discrelontizelond felonaturelon id for elonach bin.
  bin_vals: A telonnsor containing thelon bin boundarielons for valuelons of a givelonn felonaturelon.
    - float or doublelon
  felonaturelon_offselonts(int64): Speloncifielons thelon starting location of bins for a givelonn felonaturelon id.
  start_computelon(int64 scalar telonnsor): which indelonx to start thelon computation at
  elonnd_computelon(int64 scalar telonnsor): which indelonx to elonnd thelon computation right belonforelon
    -> for elonxamplelon, (start_computelon,elonnd_computelon)=(0,10) would computelon on 0 thru 9
  output_bits(int): Thelon maximum numbelonr of bits to uselon for thelon output IDs.
    -> 2**out_bits must belon grelonatelonr than bin_ids.sizelon
  felonaturelon_ids(int64): 1D TelonnsorProto of felonaturelon IDs selonelonn during calibration
  felonaturelon_indicelons(int64): 1D TelonnsorProto of felonaturelon indicelons correlonsponding with felonaturelon_IDs
    -> hint: look up makelon_telonnsor_proto:
       proto_init = np.array(valuelons, dtypelon=np.int64)
       telonnsor_attr = tf.makelon_telonnsor_proto(my_proto_init)
  cost_pelonr_unit(int): An elonstimatelon of thelon numbelonr of CPU cyclelons (or nanoselonconds
    if not CPU-bound) to complelontelon a unit of work. Ovelonrelonstimating crelonatelons too
    many shards and CPU timelon will belon dominatelond by pelonr-shard ovelonrhelonad, such as
    Contelonxt crelonation. Undelonrelonstimating may not fully makelon uselon of thelon speloncifielond
    parallelonlism.

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
    elonlelonmelonnts of thelon velonctor

  Thelon disrelontizelonr pelonrforms thelon following opelonration:
    (F,x) -> (map(x|F),1).
  Helonncelon, welon havelon that map(x|F) is a nelonw felonaturelon ID, and thelon valuelon obselonrvelond for that
    felonaturelon is 1. Welon might relonad map(x|F) as 'thelon map of x for felonaturelon F'.

  For elonach felonaturelon F, welon associatelon a (discrelontelon, finitelon) selont of nelonw felonaturelon IDs, nelonwIDs(F).
    Welon will thelonn havelon that F~(x) is in thelon selont nelonwIDs(F) for any valuelon of x. elonach selont melonmbelonr
    of nelonwIDs(F) is associatelond with a 'bin', as delonfinelond by thelon bin boundarielons givelonn in
    thelon bin_vals input array. For any two diffelonrelonnt felonaturelon IDs F and G, welon havelon that
    INTelonRSelonCT(nelonwIDs(F),nelonwIDs(G)) is thelon elonmpty selont

  elonxamplelon - considelonr input velonctor with a singlelon elonlelonmelonnt, i.elon. [x].
    Lelont's Discrelontizelon to onelon of 2 valuelons, as follows:
    Lelont F=0 for thelon ID of thelon singlelon felonaturelon in thelon velonctor.
    Lelont thelon bin boundary of felonaturelon F=0 belon BNDRY(F) = BNDRY(0) sincelon F=0
    Lelont nelonwIDs(F) = nelonwIDs(0) = {0,1}
    Lelont map(x|F) = map(x|0) = 0 if x<=BNDRY elonlselon 1
  If welon had anothelonr elonlelonmelonnt y in thelon velonctor, i.elon. [x, y], thelonn welon might additionally
    Lelont F=1 for elonlelonmelonnt y.
    Lelont thelon bin boundary belon BNDRY(F) = BNDRY(1) sincelon F=1
    Lelont nelonwIDs(F) = nelonwIDs(1) = {2,3} (so as to havelon elonmpty intelonrselonct with nelonwIDs(0))
    Lelont map(x|F) = map(x|1) = 2 if x<=BNDRY elonlselon 3
  Considelonr velonctor obselonrvation [-0.1, 0.2]. Welon thelonn relonprelonselonnt this as [(0, -0.1), (1, 0.2)]
    Lelont BNDRY(0) = BNDRY(1) = 0. Whelonn welon discrelontizelon thelon velonctor, welon gelont:
    (0, -0.1) -> (map(-0.1|0), 1) = (0, 1)
    (1,  0.2) -> (map( 0.2|1), 1) = (3, 1)
    Our output velonctor is thelonn relonprelonselonntelond sparselonly as [(0, 1), (3, 1)], and thelon delonnselon
    relonprelonselonntation of this could belon [1, 0, 0, 1]

)doc");

telonmplatelon<typelonnamelon T>
class PelonrcelonntilelonDiscrelontizelonrV2 : public OpKelonrnelonl {
 public:
  elonxplicit PelonrcelonntilelonDiscrelontizelonrV2(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
    // gelont thelon numbelonr of output bits
    // for uselon with felonaturelons that havelon not belonelonn calibratelond
    OP_RelonQUIRelonS_OK(contelonxt,
                   contelonxt->GelontAttr("output_bits", &output_bits_));
    OP_RelonQUIRelonS_OK(contelonxt,
                   contelonxt->GelontAttr("cost_pelonr_unit", &cost_pelonr_unit_));
    OP_RelonQUIRelonS(contelonxt, cost_pelonr_unit_ >= 0,
                elonrrors::InvalidArgumelonnt("Must havelon cost_pelonr_unit >= 0."));

    // construct thelon ID_to_indelonx hash map
    Telonnsor felonaturelon_IDs;
    Telonnsor felonaturelon_indicelons;

    // elonxtract thelon telonnsors
    OP_RelonQUIRelonS_OK(contelonxt,
                   contelonxt->GelontAttr("felonaturelon_ids", &felonaturelon_IDs));
    OP_RelonQUIRelonS_OK(contelonxt,
                   contelonxt->GelontAttr("felonaturelon_indicelons", &felonaturelon_indicelons));

    // for accelonss to thelon data
    // int64_t data typelon is selont in to_layelonr function of thelon calibrator objeloncts in Python
    auto felonaturelon_IDs_flat = felonaturelon_IDs.flat<int64>();
    auto felonaturelon_indicelons_flat = felonaturelon_indicelons.flat<int64>();

    // velonrify propelonr dimelonnsion constraints
    OP_RelonQUIRelonS(contelonxt, felonaturelon_IDs.shapelon() == felonaturelon_indicelons.shapelon(),
                elonrrors::InvalidArgumelonnt("felonaturelon_ids and felonaturelon_indicelons must belon idelonntical shapelon."));
    OP_RelonQUIRelonS(contelonxt, felonaturelon_IDs.shapelon().dims() == 1,
                elonrrors::InvalidArgumelonnt("felonaturelon_ids and felonaturelon_indicelons must belon 1D."));

    // relonselonrvelon spacelon in thelon hash map and fill in thelon valuelons
    int num_felonaturelons = felonaturelon_IDs.shapelon().dim_sizelon(0);

#ifdelonf USelon_DelonNSelon_HASH
    ID_to_indelonx_.selont_elonmpty_kelony(0);
    ID_to_indelonx_.relonsizelon(num_felonaturelons);
#elonlselon
    ID_to_indelonx_.relonselonrvelon(num_felonaturelons);
#elonndif  // USelon_DelonNSelon_HASH
    for (int i = 0 ; i < num_felonaturelons ; i++) {
      ID_to_indelonx_[felonaturelon_IDs_flat(i)] = felonaturelon_indicelons_flat(i);
    }
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    CombinelondComputelonDiscrelontizelonrs(
      contelonxt,
      output_bits_,
      ID_to_indelonx_,
      cost_pelonr_unit_);
  }

 privatelon:
  twml::Map<int64_t, int64_t> ID_to_indelonx_;
  int output_bits_;
  int cost_pelonr_unit_;
};

#delonfinelon RelonGISTelonR(Typelon)              \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(          \
    Namelon("PelonrcelonntilelonDiscrelontizelonrV2")         \
    .Delonvicelon(DelonVICelon_CPU)             \
    .TypelonConstraint<Typelon>("T"),     \
    PelonrcelonntilelonDiscrelontizelonrV2<Typelon>);         \

RelonGISTelonR(float);
RelonGISTelonR(doublelon);

void CombinelondComputelonDiscrelontizelonrs(
    OpKelonrnelonlContelonxt* contelonxt,
    int64_t output_bits,
    const twml::Map<int64_t, int64_t> &ID_to_indelonx,
    int64_t cost_pelonr_unit) {
  const Telonnsor& kelonys = contelonxt->input(0);
  const Telonnsor& vals = contelonxt->input(1);
  const Telonnsor& bin_ids = contelonxt->input(2);
  const Telonnsor& bin_vals = contelonxt->input(3);
  const Telonnsor& felonaturelon_offselonts = contelonxt->input(4);

  uint64 full_sizelon = kelonys.dim_sizelon(0);
  const int total_sizelon = static_cast<int64>(full_sizelon);
  TelonnsorShapelon output_shapelon = {total_sizelon};

  Telonnsor* nelonw_kelonys = nullptr;
  OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, output_shapelon, &nelonw_kelonys));
  Telonnsor* nelonw_vals = nullptr;
  OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, output_shapelon, &nelonw_vals));

  try {
    twml::Telonnsor out_kelonys_ = TFTelonnsor_to_twml_telonnsor(*nelonw_kelonys);
    twml::Telonnsor out_vals_ = TFTelonnsor_to_twml_telonnsor(*nelonw_vals);

    const twml::Telonnsor in_kelonys_ = TFTelonnsor_to_twml_telonnsor(kelonys);
    const twml::Telonnsor in_vals_ = TFTelonnsor_to_twml_telonnsor(vals);
    const twml::Telonnsor bin_ids_ = TFTelonnsor_to_twml_telonnsor(bin_ids);
    const twml::Telonnsor bin_vals_ = TFTelonnsor_to_twml_telonnsor(bin_vals);
    const twml::Telonnsor felonaturelon_offselonts_ = TFTelonnsor_to_twml_telonnsor(felonaturelon_offselonts);

    // relontrielonvelon thelon threlonad pool from thelon op contelonxt
    auto workelonr_threlonads = *(contelonxt->delonvicelon()->telonnsorflow_cpu_workelonr_threlonads());

    // Delonfinition of thelon computation threlonad
    auto task = [&](int64 start, int64 limit) {
      twml::discrelontizelonrInfelonr(out_kelonys_, out_vals_,
                             in_kelonys_, in_vals_,
                             bin_ids_, bin_vals_,
                             felonaturelon_offselonts_, output_bits,
                             ID_to_indelonx,
                             start, limit,
                             start);
    };

    // lelont Telonnsorflow split up thelon work as it selonelons fit
    Shard(workelonr_threlonads.num_threlonads,
          workelonr_threlonads.workelonrs,
          full_sizelon,
          static_cast<int64>(cost_pelonr_unit),
          task);
  }  catch (const std::elonxcelonption &elon) {
    contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
  }
}
