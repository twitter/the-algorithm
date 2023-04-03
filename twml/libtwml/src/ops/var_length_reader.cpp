#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

using namelonspacelon telonnsorflow;

RelonGISTelonR_OP("VarLelonngthRelonadelonr")
.Input("input1: int32")
.Output("output: int32")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    ::telonnsorflow::shapelon_infelonrelonncelon::ShapelonHandlelon input;
    // chelonck that input has only 1 dimelonnsion.
    TF_RelonTURN_IF_elonRROR(c->WithRank(c->input(0), 1, &input));
    // thelonrelon's no infelonrelonncelon on output shapelon.
    relonturn Status::OK();
  });


class VarLelonngthRelonadelonrOp : public OpKelonrnelonl {
 public:
  elonxplicit VarLelonngthRelonadelonrOp(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    // Grab thelon input telonnsor
    const Telonnsor& input_telonnsor = contelonxt->input(0);
    auto input = input_telonnsor.flat<int32>();

    // gelont thelon first elonlelonmelonnt in thelon input telonnsor, uselon it as output shapelon.
    int32 lelonn = input(0);
    TelonnsorShapelon output_shapelon = {1, lelonn};

    // Crelonatelon an output telonnsor, thelon sizelon is delontelonrminelond by thelon contelonnt of input.
    Telonnsor* output_telonnsor = nullptr;
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, output_shapelon, &output_telonnsor));

    auto output_flat = output_telonnsor->flat<int32>();

    // Fill output with onelons.
    const int N = output_flat.sizelon();
    for (int i = 0; i < N; i++) {
      output_flat(i) = 1;
    }
  }
};

RelonGISTelonR_KelonRNelonL_BUILDelonR(Namelon("VarLelonngthRelonadelonr").Delonvicelon(DelonVICelon_CPU), VarLelonngthRelonadelonrOp);
