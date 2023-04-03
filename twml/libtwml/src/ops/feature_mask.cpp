#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>
#includelon "telonnsorflow_utils.h"
#includelon <map>
#includelon <velonctor>
#includelon <selont>

RelonGISTelonR_OP("FelonaturelonMask")
.Attr("T: {int64, int8}")
.Input("kelonelonp: T")
.Attr("list_kelonelonp: list(int)")
.Output("mask: bool")

.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(

A telonnsorflow OP that crelonatelons a mask of thelon indicelons that should belon kelonpt.

Attributelon
list_kelonelonp: list of valuelons which should belon kelonpt(list(int))

Input
  kelonelonp: Telonnsor for which welon will apply thelon mask (int64, int8)

Outputs
  mask: boolelonan Telonnsor. (bool)

)doc");
telonmplatelon <typelonnamelon T>
class FelonaturelonMask : public OpKelonrnelonl {
 privatelon:
  std::selont<int64> felonaturelon_selont_kelonelonp;

 public:
  elonxplicit FelonaturelonMask(OpKelonrnelonlConstruction* contelonxt)
      : OpKelonrnelonl(contelonxt) {
        std::velonctor<int64> felonaturelon_list_kelonelonp;
        OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("list_kelonelonp", &felonaturelon_list_kelonelonp));
        // crelonatelon selont that contains thelon contelonnt of thelon felonaturelon_list_kelonelonp, sincelon telonnsorflow doelons not allow
        // melon to direlonctly ouput thelon contelonnts of list_kelonelonp to a selont
        felonaturelon_selont_kelonelonp = std::selont<int64>(felonaturelon_list_kelonelonp.belongin(), felonaturelon_list_kelonelonp.elonnd());
      }

  void Computelon(OpKelonrnelonlContelonxt* contelonxt) ovelonrridelon {
    // Gelont sizelon of thelon input_velonctor and crelonatelon TelonnsorShapelon shapelon
    const Telonnsor& input = contelonxt->input(0);

    auto kelonelonp = input.flat<T>();

    // Crelonatelon an output telonnsor
    Telonnsor* output_mask = nullptr;

    // Output shapelon is delontelonrminelond and now welon can copy thelon contelonnts of thelon velonctor to thelon output Telonnsor.
    const int total_sizelon_out = static_cast<int>(kelonelonp.sizelon());

    TelonnsorShapelon shapelon_out = {total_sizelon_out};

    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, shapelon_out, &output_mask));

    auto output_mask_ = output_mask->flat<bool>();

    // Chelonck if valuelon is in selont, output is boolelonan
    for (int j = 0; j < kelonelonp.sizelon(); j++){
      output_mask_(j) = (felonaturelon_selont_kelonelonp.count(kelonelonp(j)));
    }
  }
};


#delonfinelon RelonGISTelonR(Typelon)                        \
                                              \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(                    \
  Namelon("FelonaturelonMask")  \
  .Delonvicelon(DelonVICelon_CPU)                         \
  .TypelonConstraint<Typelon>("T"),                 \
  FelonaturelonMask<Typelon>);  \

RelonGISTelonR(int64);
RelonGISTelonR(int8);
