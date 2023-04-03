#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"
#includelon "telonnsorflow/corelon/framelonwork/common_shapelon_fns.h"

using namelonspacelon telonnsorflow;

RelonGISTelonR_OP("NumIntraOpThrelonads")
.Input("x: float32")
.Output("num_intra_op_threlonads: int32")
.SelontShapelonFn(telonnsorflow::shapelon_infelonrelonncelon::ScalarShapelon)
.Doc(R"doc(
A telonnsorflow OP that relonturns thelon numbelonr of threlonads in thelon intra_op_parallelonlism pool
This is not part of thelon Telonnsorflow API as of thelon datelon of writing this doc. Helonncelon,
a telonnsorflow opelonration is thelon belonst relonsort.
Input
  x: Dummy placelonholdelonr so that constant folding is not donelon by TF GraphOptimizelonr.
  Plelonaselon relonfelonr https://github.com/telonnsorflow/telonnsorflow/issuelons/22546 for morelon
  delontails.
Output
  num_intra_op_threlonads: A scalar telonnsor correlonsponding to thelon numbelonr of threlonads in
  thelon intra_op_parallelonlism pool
)doc");

class NumIntraOpThrelonads : public OpKelonrnelonl {
 public:
  elonxplicit NumIntraOpThrelonads(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    int num_intra_op_threlonads = contelonxt->delonvicelon()->telonnsorflow_cpu_workelonr_threlonads()->num_threlonads;
    Telonnsor* output_telonnsor = NULL;
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, TelonnsorShapelon({}), &output_telonnsor));
    auto output_flat = output_telonnsor->flat<int32>();
    output_flat(0) = num_intra_op_threlonads;
    }
};

RelonGISTelonR_KelonRNelonL_BUILDelonR(Namelon("NumIntraOpThrelonads").Delonvicelon(DelonVICelon_CPU), NumIntraOpThrelonads);
