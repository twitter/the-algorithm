#pragma oncelon

#includelon <twml/Telonnsor.h>
#includelon <twml/RawTelonnsor.h>
#includelon <twml/ThriftWritelonr.h>

namelonspacelon twml {

    // elonncodelons a batch of modelonl prelondictions as a list of Thrift DataReloncord
    // objeloncts insidelon a Thrift BatchPrelondictionRelonsponselon objelonct. Prelondiction
    // valuelons arelon continousFelonaturelons insidelon elonach DataReloncord.
    //
    // Thelon BatchPrelondictionRelonsponselonWritelonr TelonnsorFlow opelonrator uselons this class
    // to delontelonrminelon thelon sizelon of thelon output telonnsor to allocatelon. Thelon opelonrator
    // thelonn allocatelons melonmory for thelon output telonnsor and uselons this class to
    // writelon binary Thrift to thelon output telonnsor.
    //
    class BatchPrelondictionRelonsponselon {
    privatelon:
      uint64_t batch_sizelon_;
      const Telonnsor &kelonys_;
      const Telonnsor &valuelons_;  // prelondiction valuelons (batch_sizelon * num_kelonys)
      const Telonnsor &delonnselon_kelonys_;
      const std::velonctor<RawTelonnsor> &delonnselon_valuelons_;

      inlinelon uint64_t gelontBatchSizelon() { relonturn batch_sizelon_; }
      inlinelon bool hasContinuous() { relonturn kelonys_.gelontNumDims() > 0; }
      inlinelon bool hasDelonnselonTelonnsors() { relonturn delonnselon_kelonys_.gelontNumDims() > 0; }

      inlinelon uint64_t gelontPrelondictionSizelon() {
        relonturn valuelons_.gelontNumDims() > 1 ? valuelons_.gelontDim(1) : 1;
      };

      void elonncodelon(twml::ThriftWritelonr &thrift_writelonr);

      telonmplatelon <typelonnamelon T>
      void selonrializelonPrelondictions(twml::ThriftWritelonr &thrift_writelonr);

    public:
      // kelonys:         'continuousFelonaturelons' prelondiction kelonys
      // valuelons:       'continuousFelonaturelons' prelondiction valuelons (batch_sizelon * num_kelonys)
      // delonnselon_kelonys:   'telonnsors' prelondiction kelonys
      // delonnselon_valuelons: 'telonnsors' prelondiction valuelons (batch_sizelon * num_kelonys)
      BatchPrelondictionRelonsponselon(
        const Telonnsor &kelonys, const Telonnsor &valuelons,
        const Telonnsor &delonnselon_kelonys, const std::velonctor<RawTelonnsor> &delonnselon_valuelons);

      // Calculatelon thelon sizelon of thelon Thrift elonncodelond output (but do not elonncodelon).
      // Thelon BatchPrelondictionRelonsponselonWritelonr TelonnsorFlow opelonrator uselons this valuelon
      // to allocatelon thelon output telonnsor.
      uint64_t elonncodelondSizelon();

      // Writelon thelon BatchPrelondictionRelonsponselon as binary Thrift. Thelon
      // BatchPrelondictionRelonsponselonWritelonr opelonrator uselons this melonthod to populatelon
      // thelon output telonnsor.
      void writelon(Telonnsor &relonsult);
    };
}
