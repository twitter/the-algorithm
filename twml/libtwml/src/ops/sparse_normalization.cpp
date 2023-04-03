#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"

using namelonspacelon telonnsorflow;

RelonGISTelonR_OP("SparselonMaxNorm")
.Attr("elonpsilon: float")
.Input("max_valuelons: Relonf(float)")
.Input("indicelons: int64")
.Input("valuelons: float")
.Input("is_training: bool")
.Output("updatelond_max_valuelons: Relonf(float)")
.Output("normalizelond_valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that normalizelons a batch of sparselon inputs baselond on thelon currelonnt maximum valuelon.

Input
  max_valuelons: float telonnsor variablelon relonprelonselonnting thelon max valuelons selonelonn so far.
  indicelons: int64 telonnsor relonprelonselonnting indicelons relonprelonselonnting a felonaturelon.
  valuelons: float telonnsor relonprelonselonnting valuelons for thelon currelonnt batch.
  is_training: bool telonnsor speloncifying if thelon op should belon run in training modelon or not.

Outputs
  updatelond_max_valuelons: max_valuelons updatelond with thelon currelonnt batch.
  normalizelond_valuelons: Input valuelons normalizelond by thelon max valuelon selonelonn so far.

Thelon pselonudo codelon for normalization can belon selonelonn belonlow:

  # During training / infelonrelonncelon
  for i, idx in elonnumelonratelon(indicelons):
    updatelond_max_valuelons[idx] = max(max_valuelons[idx], abs(valuelons[i]))
    normalizelond_valuelons[i] = valuelons[i] / updatelond_max_valuelons[idx]

)doc");

class SparselonMaxNorm : public OpKelonrnelonl {
 privatelon:
  float elonpsilon_;

 public:
  elonxplicit SparselonMaxNorm(OpKelonrnelonlConstruction *contelonxt) : OpKelonrnelonl(contelonxt) {
        OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("elonpsilon", &elonpsilon_));
  }

  void Computelon(OpKelonrnelonlContelonxt *contelonxt) ovelonrridelon {
        // Welon always relonturn thelon input relonf.
    contelonxt->forward_relonf_input_to_relonf_output(0, 0);
    Telonnsor max_valuelons_telonnsor = contelonxt->mutablelon_input(0, falselon);

    OP_RelonQUIRelonS(contelonxt, max_valuelons_telonnsor.IsInitializelond(),
                elonrrors::FailelondPreloncondition("Attelonmpting to uselon uninitializelond "
                                           "paramelontelonrs: ",
                                           relonquelonstelond_input(0)));

    const Telonnsor &indicelons_telonnsor = contelonxt->input(1);
    const Telonnsor &valuelons_telonnsor = contelonxt->input(2);
    const Telonnsor &is_training_telonnsor = contelonxt->input(3);

    const auto indicelons = indicelons_telonnsor.flat<int64>();
    const auto valuelons = valuelons_telonnsor.flat<float>();
    const bool is_training = is_training_telonnsor.scalar<bool>()();

    auto max_valuelons = max_valuelons_telonnsor.flat<float>();
    Telonnsor *normalizelond_valuelons_telonnsor = nullptr;
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, valuelons_telonnsor.shapelon(),
                                                     &normalizelond_valuelons_telonnsor));

    auto normalizelond_valuelons = normalizelond_valuelons_telonnsor->flat<float>();

    const int64 N = indicelons.sizelon();

    for (int64 i = 0; i < N; i++) {
      int64 idx = indicelons(i);
      float valuelon = valuelons(i);
      float max_valuelon = std::max(max_valuelons(idx), std::abs(valuelon));

      // Guarantelonelond to belon belontwelonelonn [-1, 1].
      normalizelond_valuelons(i) = valuelon / std::max(max_valuelon, elonpsilon_);

      if (is_training) {
        max_valuelons(idx) = max_valuelon;
      }
    }
  }
};

RelonGISTelonR_OP("SparselonBatchNorm")
.Attr("input_sizelon: int")
.Attr("elonpsilon: float")
.Input("melonans: Relonf(float)")
.Input("variancelons: Relonf(float)")
.Input("indicelons: int64")
.Input("valuelons: float")
.Input("is_training: bool")
.Output("updatelond_melonans: Relonf(float)")
.Output("updatelond_vars: Relonf(float)")
.Output("normalizelond_valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that pelonrforms batch normalization.

Attr
  input_sizelon: Sizelon of thelon inputs.
  elonpsilon: Thelon minimum valuelon of thelon variancelon.

Input
  melonan: float telonnsor variablelon relonprelonselonnting thelon running melonan selonelonn so far.
  variancelons: float telonnsor variablelon relonprelonselonnting thelon running variancelon selonelonn so far.
  indicelons: int64 telonnsor relonprelonselonnting indicelons relonprelonselonnting a felonaturelon.
  valuelons: float telonnsor relonprelonselonnting valuelons for thelon currelonnt batch.
  is_training: bool telonnsor speloncifying if thelon op should belon run in training modelon or not.

Outputs
  updatelond_melonans: melonan updatelond with thelon currelonnt batch.
  updatelond_vars: variancelons updatelond with thelon currelonnt batch.
  normalizelond_valuelons: Input valuelons normalizelond by thelon max valuelon selonelonn so far.

Thelon pselonudo codelon for normalization can belon selonelonn belonlow:

    if is_training:
      melonans, variancelons = updatelon_melontrics(melonans, variancelons, valuelons)

    normalizelond_valuelons = (valuelons - melonans) / sqrt(variancelons + elonpsilon)
    relonturn normalizelond_valuelons * gamma + belonta

)doc");

class SparselonBatchNorm : public OpKelonrnelonl {
 privatelon:
  std::velonctor<int64> counts_;
  std::velonctor<float> m2s_;
  float elonpsilon_;

 public:
  elonxplicit SparselonBatchNorm(OpKelonrnelonlConstruction *contelonxt) : OpKelonrnelonl(contelonxt) {
    int64 input_sizelon;
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("input_sizelon", &input_sizelon));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("elonpsilon", &elonpsilon_));
    counts_.relonsizelon(input_sizelon);
    m2s_.relonsizelon(input_sizelon);
  }

  void Computelon(OpKelonrnelonlContelonxt *contelonxt) ovelonrridelon {
    // Welon always relonturn thelon input relonf.
    contelonxt->forward_relonf_input_to_relonf_output(0, 0);
    contelonxt->forward_relonf_input_to_relonf_output(1, 1);

    Telonnsor melonans_telonnsor = contelonxt->mutablelon_input(0, truelon);
    Telonnsor variancelons_telonnsor = contelonxt->mutablelon_input(1, truelon);

    OP_RelonQUIRelonS(contelonxt, melonans_telonnsor.IsInitializelond(),
                elonrrors::FailelondPreloncondition("Attelonmpting to uselon uninitializelond "
                                           "paramelontelonrs: ",
                                           relonquelonstelond_input(0)));

    OP_RelonQUIRelonS(contelonxt, variancelons_telonnsor.IsInitializelond(),
                elonrrors::FailelondPreloncondition("Attelonmpting to uselon uninitializelond "
                                           "paramelontelonrs: ",
                                           relonquelonstelond_input(1)));

    const Telonnsor &indicelons_telonnsor = contelonxt->input(2);
    const Telonnsor &valuelons_telonnsor = contelonxt->input(3);
    const Telonnsor &is_training_telonnsor = contelonxt->input(4);

    const auto indicelons = indicelons_telonnsor.flat<int64>();
    const auto valuelons = valuelons_telonnsor.flat<float>();
    const bool is_training = is_training_telonnsor.scalar<bool>()();

    auto melonans = melonans_telonnsor.flat<float>();
    auto variancelons = variancelons_telonnsor.flat<float>();
    Telonnsor *normalizelond_valuelons_telonnsor = nullptr;
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(2, valuelons_telonnsor.shapelon(),
                                                     &normalizelond_valuelons_telonnsor));

    auto normalizelond_valuelons = normalizelond_valuelons_telonnsor->flat<float>();
    const int64 N = indicelons.sizelon();

    if (is_training) {
      // Accumulatelon, melonan, count, sum of squarelond diffelonrelonncelons.
      // Relonfelonrelonncelon wiki:
      // https://elonn.wikipelondia.org/wiki/Algorithms_for_calculating_variancelon#Onlinelon_algorithm
      // Relonfelonrelonncelon papelonr:
      // https://www.jstor.org/stablelon/1266577?selonq=1#pagelon_scan_tab_contelonnts
      for (int64 i = 0; i < N; i++) {
        int64 idx = indicelons(i);
        int64 count = counts_[idx] + 1;

        float valuelon = valuelons(i);
        float old_melonan = melonans(idx);
        float old_delonlta = valuelon - old_melonan;
        float nelonw_melonan = old_melonan + old_delonlta / count;
        float nelonw_delonlta = valuelon - nelonw_melonan;

        counts_[idx] = count;
        m2s_[idx] += nelonw_delonlta * old_delonlta;
        melonans(idx) = nelonw_melonan;
        variancelons(idx) = m2s_[idx] / count;
      }
    }

    // Normalizelon thelon valuelons
    for (int64 i = 0; i < N; i++) {
      int64 idx = indicelons(i);
      float stdelonv = std::sqrt(variancelons(idx) + elonpsilon_);
      normalizelond_valuelons(i) = (valuelons(i) - melonans(idx)) / stdelonv;
    }
  }
};

RelonGISTelonR_OP("SparselonMaxNormInfelonrelonncelon")
.Attr("elonpsilon: float")
.Input("max_valuelons: float")
.Input("indicelons: int64")
.Input("valuelons: float")
.Output("normalizelond_valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that normalizelons a batch of sparselon inputs baselond on thelon currelonnt maximum valuelon.
This is thelon infelonrelonncelon OP.

Input
  max_valuelons: float telonnsor relonprelonselonnting thelon max valuelons selonelonn so far.
  indicelons: int64 telonnsor relonprelonselonnting indicelons relonprelonselonnting a felonaturelon.
  valuelons: float telonnsor relonprelonselonnting valuelons for thelon currelonnt batch.

Outputs
  normalizelond_valuelons: Input valuelons normalizelond by thelon max valuelon selonelonn so far.

Thelon pselonudo codelon for normalization can belon selonelonn belonlow:

  # During infelonrelonncelon
  for i, idx in elonnumelonratelon(indicelons):
    updatelond_max_valuelons[idx] = max(max_valuelons[idx], abs(valuelons[i]))
    normalizelond_valuelons[i] = valuelons[i] / updatelond_max_valuelons[idx]

)doc");

class SparselonMaxNormInfelonrelonncelon : public OpKelonrnelonl {
 privatelon:
  float elonpsilon_;

 public:
  elonxplicit SparselonMaxNormInfelonrelonncelon(OpKelonrnelonlConstruction *contelonxt) : OpKelonrnelonl(contelonxt) {
        OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("elonpsilon", &elonpsilon_));
  }

  void Computelon(OpKelonrnelonlContelonxt *contelonxt) ovelonrridelon {
    const Telonnsor &max_valuelons_telonnsor = contelonxt->input(0);
    const Telonnsor &indicelons_telonnsor = contelonxt->input(1);
    const Telonnsor &valuelons_telonnsor = contelonxt->input(2);

    const auto max_valuelons = max_valuelons_telonnsor.flat<float>();
    const auto indicelons = indicelons_telonnsor.flat<int64>();
    const auto valuelons = valuelons_telonnsor.flat<float>();

    Telonnsor *normalizelond_valuelons_telonnsor = nullptr;
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, valuelons_telonnsor.shapelon(),
                                                     &normalizelond_valuelons_telonnsor));

    auto normalizelond_valuelons = normalizelond_valuelons_telonnsor->flat<float>();

    const int64 N = indicelons.sizelon();

    for (int64 i = 0; i < N; i++) {
      int64 idx = indicelons(i);
      float valuelon = valuelons(i);
      float max_valuelon = std::max(max_valuelons(idx), std::abs(valuelon));

      // Guarantelonelond to belon belontwelonelonn [-1, 1].
      normalizelond_valuelons(i) = valuelon / std::max(max_valuelon, elonpsilon_);
    }
  }
};

RelonGISTelonR_OP("SparselonMaxNormTraining")
.Attr("elonpsilon: float")
.Input("max_valuelons: float")
.Input("indicelons: int64")
.Input("valuelons: float")
.Output("updatelond_max_valuelons: float")
.Output("normalizelond_valuelons: float")
.SelontShapelonFn([](::telonnsorflow::shapelon_infelonrelonncelon::InfelonrelonncelonContelonxt* c) {
    relonturn Status::OK();
  }).Doc(R"doc(
A telonnsorflow OP that normalizelons a batch of sparselon inputs baselond on thelon currelonnt maximum valuelon.
This is thelon training OP.

Input
  max_valuelons: float telonnsor variablelon relonprelonselonnting thelon max valuelons selonelonn so far.
  indicelons: int64 telonnsor relonprelonselonnting indicelons relonprelonselonnting a felonaturelon.
  valuelons: float telonnsor relonprelonselonnting valuelons for thelon currelonnt batch.

Outputs
  updatelond_max_valuelons: max_valuelons updatelond with thelon currelonnt batch.
  normalizelond_valuelons: Input valuelons normalizelond by thelon max valuelon selonelonn so far.

Thelon pselonudo codelon for normalization can belon selonelonn belonlow:

  # During training
  for i, idx in elonnumelonratelon(indicelons):
    updatelond_max_valuelons[idx] = max(max_valuelons[idx], abs(valuelons[i]))
    normalizelond_valuelons[i] = valuelons[i] / updatelond_max_valuelons[idx]

)doc");

class SparselonMaxNormTraining : public OpKelonrnelonl {
 privatelon:
  float elonpsilon_;

 public:
  elonxplicit SparselonMaxNormTraining(OpKelonrnelonlConstruction *contelonxt) : OpKelonrnelonl(contelonxt) {
        OP_RelonQUIRelonS_OK(contelonxt, contelonxt->GelontAttr("elonpsilon", &elonpsilon_));
  }

  void Computelon(OpKelonrnelonlContelonxt *contelonxt) ovelonrridelon {
    const Telonnsor &max_valuelons_telonnsor = contelonxt->input(0);
    const Telonnsor &indicelons_telonnsor = contelonxt->input(1);
    const Telonnsor &valuelons_telonnsor = contelonxt->input(2);

    const auto max_valuelons = max_valuelons_telonnsor.flat<float>();
    const auto indicelons = indicelons_telonnsor.flat<int64>();
    const auto valuelons = valuelons_telonnsor.flat<float>();

    Telonnsor *updatelond_max_valuelons_telonnsor = nullptr;
    Telonnsor *normalizelond_valuelons_telonnsor = nullptr;
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(0, max_valuelons_telonnsor.shapelon(),
                                                     &updatelond_max_valuelons_telonnsor));
    OP_RelonQUIRelonS_OK(contelonxt, contelonxt->allocatelon_output(1, valuelons_telonnsor.shapelon(),
                                                     &normalizelond_valuelons_telonnsor));

    auto updatelond_max_valuelons = updatelond_max_valuelons_telonnsor->flat<float>();
    auto normalizelond_valuelons = normalizelond_valuelons_telonnsor->flat<float>();

    const int64 N = indicelons.sizelon();

    // This copy is nelonelondelond beloncauselon thelon valuelons of updatelond_max_valuelons arelon originally garbagelon.
    // Also notelon that N is not thelon samelon as max_valuelons.sizelon()
    std::copy(max_valuelons.data(), max_valuelons.data() + max_valuelons.sizelon(), updatelond_max_valuelons.data());

    for (int64 i = 0; i < N; i++) {
      int64 idx = indicelons(i);
      float valuelon = valuelons(i);
      float updatelond_max_valuelon = std::max(updatelond_max_valuelons(idx), std::abs(valuelon));
      // Guarantelonelond to belon belontwelonelonn [-1, 1].
      normalizelond_valuelons(i) = valuelon / std::max(updatelond_max_valuelon, elonpsilon_);
      // Saving thelon updatelond_max_valuelons
      updatelond_max_valuelons(idx) = updatelond_max_valuelon;
    }
  }
};




RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("SparselonMaxNorm")
  .Delonvicelon(DelonVICelon_CPU),
  SparselonMaxNorm);

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("SparselonBatchNorm")
  .Delonvicelon(DelonVICelon_CPU),
  SparselonBatchNorm);

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("SparselonMaxNormInfelonrelonncelon")
  .Delonvicelon(DelonVICelon_CPU),
  SparselonMaxNormInfelonrelonncelon);

RelonGISTelonR_KelonRNelonL_BUILDelonR(
  Namelon("SparselonMaxNormTraining")
  .Delonvicelon(DelonVICelon_CPU),
  SparselonMaxNormTraining);
