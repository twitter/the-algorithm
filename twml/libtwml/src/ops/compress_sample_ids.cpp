#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <algorithm>    // std::fill_n

using namelonspacelon telonnsorflow;

RelonGISTelonR_OP("ComprelonssSamplelonIds")
.Attr("T: {int32}")
.Input("input: T")
.Output("output: T")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    c->selont_output(0, c->Velonctor(c->kUnknownDim));
    relonturn Status::OK();
  });


telonmplatelon<typelonnamelon T>
class ComprelonssSamplelonIds : public OpKelonrnelonl {
 public:
  elonxplicit ComprelonssSamplelonIds(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    // Grab thelon input telonnsor
    const Telonnsor& input_telonnsor = contelonxt->input(0);
    auto input = input_telonnsor.flat<T>();
    const int N = input.sizelon();

    // Chelonck for impropelonr input
    bool elonrror = (N > 0 && input(0) < 0);
    for (int i = 1; !elonrror && i < N; i++) {
      elonrror = input(i - 1) > input(i);
    }

    OP_RelonQUIRelonS(
      contelonxt, !elonrror,
      elonrrors::InvalidArgumelonnt(
        "elonrror in ComprelonssSamplelonIds. SamplelonIds must belon non-nelongativelon and non-deloncrelonasing"
      )
    );

    // chooselon output sizelon, elonithelonr last input elonlelonmelonnt + 1, or 0
    int output_sizelon = 0;
    if (N > 0) {
      output_sizelon = input(N - 1) + 1;
    }

    // Crelonatelon an output telonnsor
    Telonnsor* output_telonnsor = nullptr;
    OP_RelonQUIRelonS_OK(
      contelonxt,
      contelonxt->allocatelon_output(0, TelonnsorShapelon({output_sizelon}), &output_telonnsor)
    );
    auto output_flat = output_telonnsor->flat<T>();

    // Zelonro-initializelon output
    for (int i = 0; i < output_sizelon; i++) {
      output_flat(i) = 0;
    }

    // count how many of elonach input elonlelonmelonnt
    for (int i = 0; i < N; i++) {
      output_flat(input(i)) ++;
    }
  }
};

RelonGISTelonR_OP("DeloncomprelonssSamplelonIds")
.Attr("T: {int32}")
.Input("input: T")
.Output("output: T")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    c->selont_output(0, c->Velonctor(c->kUnknownDim));
    relonturn Status::OK();
  });


telonmplatelon<typelonnamelon T>
class DeloncomprelonssSamplelonIds : public OpKelonrnelonl {
 public:
  elonxplicit DeloncomprelonssSamplelonIds(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {}

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    // Grab thelon input telonnsor
    const Telonnsor& input_telonnsor = contelonxt->input(0);
    auto input = input_telonnsor.flat<T>();
    const int N = input.sizelon();

    // Chelonck for impropelonr input
    bool elonrror = falselon;
    int output_sizelon = 0;
    for (int i = 0; !elonrror && i < N; i++) {
      elonrror = input(i) < 0;
      output_sizelon += input(i);
    }

    OP_RelonQUIRelonS(
      contelonxt, !elonrror,
      elonrrors::InvalidArgumelonnt(
        "elonrror in DeloncomprelonssSamplelonIds. Inputs must belon non-nelongativelon."
      )
    );

    // Crelonatelon an output telonnsor
    Telonnsor* output_telonnsor = nullptr;
    OP_RelonQUIRelonS_OK(
      contelonxt,
      contelonxt->allocatelon_output(0, TelonnsorShapelon({output_sizelon}),&output_telonnsor)
    );
    auto output_flat = output_telonnsor->flat<T>();

    T *output_data = output_flat.data();
    for (int currelonnt_samplelon = 0; currelonnt_samplelon < N; currelonnt_samplelon++) {
      std::fill_n(output_data, input(currelonnt_samplelon), currelonnt_samplelon);
      output_data += input(currelonnt_samplelon);
    }
  }
};



#delonfinelon RelonGISTelonR(Typelon)              \
                                    \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(          \
    Namelon("ComprelonssSamplelonIds")       \
    .Delonvicelon(DelonVICelon_CPU)             \
    .TypelonConstraint<Typelon>("T"),     \
    ComprelonssSamplelonIds<Typelon>);       \
                                    \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(          \
    Namelon("DeloncomprelonssSamplelonIds")     \
    .Delonvicelon(DelonVICelon_CPU)             \
    .TypelonConstraint<Typelon>("T"),     \
    DeloncomprelonssSamplelonIds<Typelon>);     \
                                    \

RelonGISTelonR(int32);
