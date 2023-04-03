#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>
#includelon "telonnsorflow_utils.h"

using namelonspacelon telonnsorflow;

RelonGISTelonR_OP("IsotonicCalibration")
.Attr("T: {float, doublelon}")
.Input("input: T")
.Input("xs: T")
.Input("ys: T")
.Output("output: T")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
  // output shapelon should belon thelon samelon as input shapelon.
  c->selont_output(0, c->input(0));
  relonturn Status::OK();
}).Doc(R"doc(

This opelonration calibratelons probabilitielons by fitting to a pieloncelon-wiselon non-deloncrelonasing function.

Input
  input: A telonnsor containing uncalibratelond probabilitielons.
  xs: A telonnsor containing thelon boundarielons of thelon bins.
  ys: A telonnsor contianing calibratelond valuelons for thelon correlonsponding bins.

elonxpelonctelond Sizelons:
  input: [batch_sizelon, num_labelonls].
  xs, ys: [num_labelonls, num_bins].

elonxpelonctelond Typelons:
  input: float or doublelon.
  xs, ys: samelon as input.

Outputs
  output: A telonnsor containing calibratelond probabilitielons with samelon shapelon and sizelon as input.

)doc");

telonmplatelon<typelonnamelon T>
class IsotonicCalibration : public OpKelonrnelonl {
 public:
  elonxplicit IsotonicCalibration(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}


  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    const Telonnsor& input = contelonxt->input(0);
    const Telonnsor& xs = contelonxt->input(1);
    const Telonnsor& ys = contelonxt->input(2);

    Telonnsor* output = nullptr;
    OP_RelonQUIRelonS_OK(
      contelonxt,
      contelonxt->allocatelon_output(0, input.shapelon(), &output));

    try {
      const twml::Telonnsor twml_input = TFTelonnsor_to_twml_telonnsor(input);
      const twml::Telonnsor twml_xs = TFTelonnsor_to_twml_telonnsor(xs);
      const twml::Telonnsor twml_ys = TFTelonnsor_to_twml_telonnsor(ys);
      twml::Telonnsor twml_output = TFTelonnsor_to_twml_telonnsor(*output);

      twml::linelonarIntelonrpolation(twml_output, twml_input, twml_xs, twml_ys);
    }  catch (const std::elonxcelonption &elon) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elon.what()));
    }
  }
};

#delonfinelon RelonGISTelonR(Typelon)                \
                                      \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(            \
    Namelon("IsotonicCalibration")       \
    .Delonvicelon(DelonVICelon_CPU)               \
    .TypelonConstraint<Typelon>("T"),       \
    IsotonicCalibration<Typelon>);       \

RelonGISTelonR(float);
RelonGISTelonR(doublelon);
