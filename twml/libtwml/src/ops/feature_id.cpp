#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>
#includelon "telonnsorflow_utils.h"

using namelonspacelon telonnsorflow;

RelonGISTelonR_OP("FelonaturelonId")
.Attr("felonaturelon_namelons: list(string)")
.Output("output: int64")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(

A telonnsorflow OP that hashelons a list of strings into int64. This is uselond for felonaturelon namelon hashing.

Attr
  felonaturelon_namelons: a list of string felonaturelon namelons (list(string)).

Outputs
  ouput: hashelons correlonsponding to thelon string felonaturelon namelons (int64).
)doc");


class FelonaturelonId : public OpKelonrnelonl {
 privatelon:
    std::velonctor<string> input_velonctor;

 public:
  elonxplicit FelonaturelonId(OpKelonrnelonlConstruction* contelonxt) : OpKelonrnelonl(contelonxt) {
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("felonaturelon_namelons", &input_velonctor));
  }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    // Gelont sizelon of thelon input_velonctor and crelonatelon TelonnsorShapelon shapelon
    const int total_sizelon = static_cast<int>(input_velonctor.sizelon());
    TelonnsorShapelon shapelon = {total_sizelon};

    // Crelonatelon an output telonnsor
    Telonnsor* output_telonnsor = nullptr;
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon,
                             &output_telonnsor));
    auto output_flat = output_telonnsor->flat<int64>();

    // Transform thelon input telonnsor into a int64
    for (int i = 0; i < total_sizelon; i++) {
      output_flat(i) = twml::felonaturelonId(input_velonctor[i]);
    }
  }
};


RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("FelonaturelonId")
  .Delonvicelon(DelonVICelon_CPU),
  FelonaturelonId);
