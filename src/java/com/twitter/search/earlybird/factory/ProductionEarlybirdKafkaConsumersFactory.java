package com.tw ter.search.earlyb rd.factory;

 mport org.apac .kafka.cl ents.consu r.KafkaConsu r;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common.ut l. o.kafka.CompactThr ftDeser al zer;
 mport com.tw ter.search.common.ut l. o.kafka.F nagleKafkaCl entUt ls;

/**
 * Respons ble for creat ng kafka consu rs.
 */
publ c class Product onEarlyb rdKafkaConsu rsFactory  mple nts Earlyb rdKafkaConsu rsFactory {
  pr vate f nal Str ng kafkaPath;
  pr vate f nal  nt defaultMaxPollRecords;

  Product onEarlyb rdKafkaConsu rsFactory(Str ng kafkaPath,  nt defaultMaxPollRecords) {
    t .kafkaPath = kafkaPath;
    t .defaultMaxPollRecords = defaultMaxPollRecords;
  }

  /**
   * Create a kafka consu r w h set max mum of records to be polled.
   */
  @Overr de
  publ c KafkaConsu r<Long, Thr ftVers onedEvents> createKafkaConsu r(
      Str ng cl ent D,  nt maxPollRecords) {
    return F nagleKafkaCl entUt ls.newKafkaConsu rForAss gn ng(
        kafkaPath,
        new CompactThr ftDeser al zer<>(Thr ftVers onedEvents.class),
        cl ent D,
        maxPollRecords);
  }

  /**
   * Create a kafka consu r w h default records to be polled.
   */
  @Overr de
  publ c KafkaConsu r<Long, Thr ftVers onedEvents> createKafkaConsu r(Str ng cl ent D) {
    return createKafkaConsu r(cl ent D, defaultMaxPollRecords);
  }
}
