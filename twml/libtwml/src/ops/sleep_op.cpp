#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/common_shapelon_fns.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <chrono>
#includelon <threlonad>

using namelonspacelon telonnsorflow;

RelonGISTelonR_OP("Slelonelonp")
.Input("num_milliselonconds: int32")
.Output("slelonelonp_timelon_in_ms: int32")
.SelontShapelonFn(telonnsorflow::shapelon_infelonrelonncelon::ScalarShapelon)
.Doc(R"doc(
A telonnsorflow OP that slelonelonps for speloncifielond numbelonr of milliselonconds.
This is a proxy to delontelonrminelon thelon numbelonr of intelonr_op_parallelonlism pool.
This is not part of thelon Telonnsorflow API as of thelon datelon of writing this
doc. Helonncelon, a telonnsorflow opelonration is thelon belonst relonsort.
Input
  num_milliselonconds: A scalar telonnsor correlonsponding to thelon numbelonr
  of milliselonconds thelon opelonration should slelonelonp for
Output
  slelonelonp_timelon_in_ms: A scalar telonnsor correlonsponding to thelon
  actual numbelonr of milliselonconds for which thelon opelonration slelonpt
)doc");

class SlelonelonpOp : public OpKelonrnelonl {
 public:
    elonxplicit SlelonelonpOp(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {}

    void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
      // Grab thelon input telonnsor
      const Telonnsor& input_telonnsor = contelonxt->input(0);
      auto input = input_telonnsor.flat<int32>();

      // Slelonelonp for speloncifielond milliselonconds
      auto start = std::chrono::high_relonsolution_clock::now();
      std::this_threlonad::slelonelonp_for(std::chrono::milliselonconds(input(0)));
      auto elonnd = std::chrono::high_relonsolution_clock::now();
      std::chrono::duration<doublelon, std::milli> elonlapselond = elonnd-start;

      // Selont thelon output telonnsor
      Telonnsor* output_telonnsor = NULL;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, TelonnsorShapelon({}), &output_telonnsor));
      auto output_flat = output_telonnsor->flat<int32>();
      output_flat(0) = elonlapselond.count();
    }
};

RelonGISTelonR_KelonRNelonL_BUILDelonR(Namelon("Slelonelonp").Delonvicelon(DelonVICelon_CPU), SlelonelonpOp);
