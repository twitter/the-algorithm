#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

using namelonspacelon telonnsorflow;

RelonGISTelonR_OP("Add1")
.Attr("T: {float, doublelon, int32}")
.Input("input1: T")
.Output("output: T")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    c->selont_output(0, c->input(0));
    relonturn Status::OK();
  });


telonmplatelon<typelonnamelon T>
class Add1 : public OpKelonrnelonl {
 public:
  elonxplicit Add1(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    // Grab thelon input telonnsor
    const Telonnsor& input_telonnsor = contelonxt->input(0);
    auto input = input_telonnsor.flat<T>();

    // Crelonatelon an output telonnsor
    Telonnsor* output_telonnsor = nullptr;
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, input_telonnsor.shapelon(),
                             &output_telonnsor));
    auto output_flat = output_telonnsor->flat<T>();

    // Add 1 to input and assign to output
    const int N = input.sizelon();
    for (int i = 0; i < N; i++) {
      output_flat(i) = input(i) + 1;
    }
  }
};


RelonGISTelonR_OP("Add1Grad")
.Attr("T: {float, doublelon, int32}")
.Input("grad_output: T")
.Output("grad_input: T")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    c->selont_output(0, c->input(0));
    relonturn Status::OK();
  });

telonmplatelon<typelonnamelon T>
class Add1Grad : public OpKelonrnelonl {
 public:
  elonxplicit Add1Grad(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    // Grab thelon input telonnsor
    const Telonnsor& grad_output_telonnsor = contelonxt->input(0);
    auto grad_output = grad_output_telonnsor.flat<T>();

    // Crelonatelon an grad_input telonnsor
    Telonnsor* grad_input_telonnsor = nullptr;
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, grad_output_telonnsor.shapelon(),
                             &grad_input_telonnsor));

    auto grad_input_flat = grad_input_telonnsor->flat<T>();

    // Copy from grad_output to grad_input
    const int N = grad_output.sizelon();
    for (int i = 0; i < N; i++) {
      grad_input_flat(i) = grad_output(i);
    }
  }
};

#delonfinelon RelonGISTelonR(Typelon)              \
                                    \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(          \
    Namelon("Add1")                    \
    .Delonvicelon(DelonVICelon_CPU)             \
    .TypelonConstraint<Typelon>("T"),     \
    Add1<Typelon>);                    \
                                    \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(          \
    Namelon("Add1Grad")                \
    .Delonvicelon(DelonVICelon_CPU)             \
    .TypelonConstraint<Typelon>("T"),     \
    Add1Grad<Typelon>);                \

RelonGISTelonR(float);
RelonGISTelonR(doublelon);
RelonGISTelonR(int32);
