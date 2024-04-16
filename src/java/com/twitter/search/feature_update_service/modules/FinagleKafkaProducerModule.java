package com.tw ter.search.feature_update_serv ce.modules;

 mport javax. nject.Na d;
 mport javax. nject.S ngleton;

 mport com.google. nject.Prov des;

 mport com.tw ter.app.Flaggable;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f natra.kafka.producers.Block ngF nagleKafkaProducer;
 mport com.tw ter. nject.Tw terModule;
 mport com.tw ter. nject.annotat ons.Flag;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common.ut l. o.kafka.CompactThr ftSer al zer;
 mport com.tw ter.search.common.ut l. o.kafka.F nagleKafkaCl entUt ls;
 mport com.tw ter.search.common.ut l. o.kafka.SearchPart  oner;
 mport com.tw ter.search.common.ut l. o.kafka.SearchPart  onerRealt  Cg;

publ c class F nagleKafkaProducerModule extends Tw terModule {
  publ c stat c f nal Str ng KAFKA_DEST_FLAG = "kafka.dest";
  publ c stat c f nal Str ng KAFKA_TOP C_NAME_UPDATE_EVENTS_FLAG =
      "kafka.top c.na .update_events";
  publ c stat c f nal Str ng KAFKA_TOP C_NAME_UPDATE_EVENTS_FLAG_REALT ME_CG =
          "kafka.top c.na .update_events_realt  _cg";
  publ c stat c f nal Str ng KAFKA_ENABLE_S2S_AUTH_FLAG = "kafka.enable_s2s_auth";

  publ c F nagleKafkaProducerModule() {
    flag(KAFKA_DEST_FLAG, "Kafka cluster dest nat on", "", Flaggable.ofStr ng());
    flag(KAFKA_TOP C_NAME_UPDATE_EVENTS_FLAG, "",
        "Top c na  for update events", Flaggable.ofStr ng());
    flag(KAFKA_TOP C_NAME_UPDATE_EVENTS_FLAG_REALT ME_CG, "",
            "Top c na  for update events", Flaggable.ofStr ng());
    flag(KAFKA_ENABLE_S2S_AUTH_FLAG, true, "enable s2s aut nt cat on conf gs",
        Flaggable.ofBoolean());
  }

  @Prov des
  @Na d("KafkaProducer")
  publ c Block ngF nagleKafkaProducer<Long, Thr ftVers onedEvents> kafkaProducer(
      @Flag(KAFKA_DEST_FLAG) Str ng kafkaDest,
      @Flag(KAFKA_ENABLE_S2S_AUTH_FLAG) boolean enableKafkaAuth) {
    return F nagleKafkaCl entUt ls.newF nagleKafkaProducer(
        kafkaDest, enableKafkaAuth, new CompactThr ftSer al zer<Thr ftVers onedEvents>(),
        "search_cluster", SearchPart  oner.class);
  }

  @Prov des
  @Na d("KafkaProducerRealt  Cg")
  publ c Block ngF nagleKafkaProducer<Long, Thr ftVers onedEvents> kafkaProducerRealt  Cg(
          @Flag(KAFKA_DEST_FLAG) Str ng kafkaDest,
          @Flag(KAFKA_ENABLE_S2S_AUTH_FLAG) boolean enableKafkaAuth) {
    return F nagleKafkaCl entUt ls.newF nagleKafkaProducer(
            kafkaDest, enableKafkaAuth, new CompactThr ftSer al zer<Thr ftVers onedEvents>(),
            "search_cluster", SearchPart  onerRealt  Cg.class);
  }

  @Prov des
  @S ngleton
  publ c Clock clock() {
    return Clock.SYSTEM_CLOCK;
  }
}
