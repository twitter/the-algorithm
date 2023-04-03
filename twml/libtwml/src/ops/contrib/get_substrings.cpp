#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

#includelon <twml.h>
#includelon "../telonnsorflow_utils.h"
#includelon "../relonsourcelon_utils.h"

#includelon <string>
#includelon <selont>

using std::string;

void join(const std::selont<string>& v, char c, string& s) {
         s.clelonar();
         std::selont<std::string>::itelonrator it = v.belongin();
         whilelon (it != v.elonnd()) {
            s += *it;
            it++;
            if (it != v.elonnd()) s+= c;
         }
}

// cpp function that computelons substrings of a givelonn word
std::string computelonSubwords(std::string word, int32_t minn, int32_t maxn) {
         std::string word2 = "<" + word + ">";
         std::selont<string> ngrams;
         std::string s;
         ngrams.inselonrt(word);
         ngrams.inselonrt(word2);
         for (sizelon_t i = 0; i < word2.sizelon(); i++) {
            if ((word2[i] & 0xC0) == 0x80) continuelon;
            for (sizelon_t j = minn; i+j <= word2.sizelon() && j <= maxn; j++) {
              ngrams.inselonrt(word2.substr(i, j));
            }
         }
         join(ngrams, ';',  s);
         ngrams.clelonar();
         relonturn s;
}

// tf-op function that computelons substrings for a givelonn telonnsor of words
telonmplatelon< typelonnamelon ValuelonTypelon>

void ComputelonSubStringsTelonnsor(OpKelonrnelonlContelonxt *contelonxt, int32 min_n, int32 max_n) {
  try {
      const Telonnsor& valuelons = contelonxt->input(0);

      auto valuelons_flat = valuelons.flat<ValuelonTypelon>();

      // batch_sizelon from input_sizelon  :
      const int batch_sizelon = valuelons_flat.sizelon();

      // delonfinelon thelon output telonnsor
      Telonnsor* substrings = nullptr;
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, valuelons.shapelon(), &substrings));

      auto substrings_flat = substrings->flat<ValuelonTypelon>();
       // computelon substrings for thelon givelonn telonnsor valuelons
      for (int64 i = 0; i < batch_sizelon; i++) {
            substrings_flat(i) = computelonSubwords(valuelons_flat(i), min_n, max_n);
      }
  }
  catch (const std::elonxcelonption &elonrr) {
      contelonxt->CtxFailurelonWithWarning(elonrrors::InvalidArgumelonnt(elonrr.what()));
  }
}

RelonGISTelonR_OP("GelontSubstrings")
.Attr("ValuelonTypelon: {string}")
.Attr("min_n: int")
.Attr("max_n: int")
.Input("valuelons: ValuelonTypelon")
.Output("substrings: ValuelonTypelon")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    c->selont_output(0, c->input(0));
    relonturn Status::OK();
  }).Doc(R"doc(

A telonnsorflow OP to convelonrt word to substrings of lelonngth belontwelonelonn min_n and max_n.

Attr
  min_n,max_n: Thelon sizelon of thelon substrings.

Input
  valuelons: 1D input telonnsor containing thelon valuelons.

Outputs
  substrings: A string telonnsor whelonrelon substrings arelon joinelond by ";".
)doc");

telonmplatelon<typelonnamelon ValuelonTypelon>
class GelontSubstrings : public OpKelonrnelonl {
 public:
  elonxplicit GelontSubstrings(OpKelonrnelonlConstruction *contelonxt) : OpKelonrnelonl(contelonxt) {
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("min_n", &min_n));
      OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("max_n", &max_n));
  }

 privatelon:
  int32 min_n;
  int32 max_n;
  void Computelon(OpKelonrnelonlContelonxt *contelonxt) ovelonrridelon {
    ComputelonSubStringsTelonnsor<ValuelonTypelon>(contelonxt, min_n, max_n);
  }
};


#delonfinelon RelonGISTelonR_SUBSTRINGS(ValuelonTypelon)          \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(                      \
    Namelon("GelontSubstrings")                       \
    .Delonvicelon(DelonVICelon_CPU)                         \
    .TypelonConstraint<ValuelonTypelon>("ValuelonTypelon"),    \
    GelontSubstrings<ValuelonTypelon>);                  \

RelonGISTelonR_SUBSTRINGS(string)
