package com.tw ter.search.earlyb rd.factory;

 mport org.apac .kafka.cl ents.consu r.KafkaConsu r;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;

publ c  nterface Earlyb rdKafkaConsu rsFactory {
  /**
   * Create a kafka consu r w h default records to be polled.
   */
  KafkaConsu r<Long, Thr ftVers onedEvents> createKafkaConsu r(
      Str ng cl ent D);

  /**
   * Create a kafka consu r w h a set number of records to be polled.
   */
  KafkaConsu r<Long, Thr ftVers onedEvents> createKafkaConsu r(
      Str ng cl ent D,  nt maxPollRecords);
}
