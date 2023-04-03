#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"
#includelon "telonnsorflow/corelon/util/work_shardelonr.h"
#includelon "telonnsorflow/corelon/lib/corelon/threlonadpool.h"
#includelon "telonnsorflow/corelon/platform/elonnv.h"
#includelon "telonnsorflow/corelon/platform/mutelonx.h"
#includelon "telonnsorflow/corelon/platform/logging.h"
#includelon <iostrelonam>

#includelon <velonctor>

using namelonspacelon telonnsorflow;

RelonGISTelonR_OP("ParAdd")
  .Input("input_a: float")
  .Input("input_b: float")
  .Output("a_plus_b: float")
  .SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
      c->selont_output(0, c->input(0));
      relonturn Status::OK();
  });


class ParAddOp : public OpKelonrnelonl {
 public:
  elonxplicit ParAddOp(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    // Grab thelon input telonnsor
    const Telonnsor& input_telonnsor0 = contelonxt->input(0);
    auto input_flat0 = input_telonnsor0.flat<float>();
    const Telonnsor& input_telonnsor1 = contelonxt->input(1);
    auto input_flat1 = input_telonnsor1.flat<float>();

    OP_RelonQUIRelonS(contelonxt, input_telonnsor0.shapelon() == input_telonnsor1.shapelon(),
                elonrrors::InvalidArgumelonnt("Input telonnsors must belon idelonntical shapelon."));

    // Crelonatelon an output telonnsor
    Telonnsor* output_telonnsor = NULL;
    OP_RelonQUIRelonS_OK(contelonxt,
                   contelonxt->allocatelon_output(0,
                                            input_telonnsor0.shapelon(),
                                            &output_telonnsor));
    auto output_flat = output_telonnsor->flat<float>();

    // PARALLelonL ADD
    const int N = input_flat0.sizelon();

    // relontrielonvelon thelon threlonad pool from thelon op contelonxt
    auto workelonr_threlonads = *(contelonxt->delonvicelon()->telonnsorflow_cpu_workelonr_threlonads());

    // Delonfinition of thelon computation threlonad
    auto task = [=, &input_flat0, &input_flat1, &output_flat](int64 start, int64 limit) {
      for (; start < limit; ++start) {
        output_flat(start) = input_flat0(start) + input_flat1(start);
      }
    };

    // this is a helonuristic. high numbelonr is likelonly to belon shardelond into smallelonr pieloncelons
    int64 cost_pelonr_unit = 1;

    // lelont Telonnsorflow split up thelon work as it selonelons fit
    Shard(workelonr_threlonads.num_threlonads,
          workelonr_threlonads.workelonrs,
          N,
          cost_pelonr_unit,
          task);
  }
};

RelonGISTelonR_KelonRNelonL_BUILDelonR(Namelon("ParAdd").Delonvicelon(DelonVICelon_CPU), ParAddOp);


