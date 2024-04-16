package com.tw ter.search.earlyb rd.part  on;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport org.apac .kafka.cl ents.consu r.Consu rRecord;
 mport org.apac .kafka.cl ents.consu r.KafkaConsu r;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common. tr cs.SearchT  r;
 mport com.tw ter.search.common.ut l. o.kafka.F nagleKafkaCl entUt ls;
 mport com.tw ter.search.common.ut l. o.kafka.Thr ftDeser al zer;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdProperty;
 mport com.tw ter.search.earlyb rd.except on.M ss ngKafkaTop cExcept on;
 mport com.tw ter.t etyp e.thr ftjava.T etEvent;
 mport com.tw ter.t etyp e.thr ftjava.UserScrubGeoEvent;

publ c class UserScrubGeoEventStream ndexer extends S mpleStream ndexer<Long, T etEvent> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(UserScrubGeoEventStream ndexer.class);

  protected stat c Str ng kafkaCl ent d = "earlyb rd_user_scrub_geo_kafka_consu r";
  pr vate stat c f nal SearchCounter NUM_M SS NG_DATA_ERRORS =
      SearchCounter.export("num_user_scrub_geo_event_kafka_consu r_num_m ss ng_data_errors");

  pr vate f nal Seg ntManager seg ntManager;
  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;

  publ c UserScrubGeoEventStream ndexer(KafkaConsu r<Long, T etEvent> kafkaConsu r,
                                        Str ng top c,
                                        Search ndex ng tr cSet search ndex ng tr cSet,
                                        Seg ntManager seg ntManager)
      throws M ss ngKafkaTop cExcept on {
    super(kafkaConsu r, top c);

    t .seg ntManager = seg ntManager;
    t .search ndex ng tr cSet = search ndex ng tr cSet;

     ndex ngSuccesses = SearchRateCounter.export("user_scrub_geo_ ndex ng_successes");
     ndex ngFa lures = SearchRateCounter.export("user_scrub_geo_ ndex ng_fa lures");
  }

  /**
   * Prov des UserScrubGeoEvent Kafka Consu r to Earlyb rdW reModule.
   * @return
   */
  publ c stat c KafkaConsu r<Long, T etEvent> prov deKafkaConsu r() {
    return F nagleKafkaCl entUt ls.newKafkaConsu rForAss gn ng(
        Earlyb rdProperty.TWEET_EVENTS_KAFKA_PATH.get(),
        new Thr ftDeser al zer<>(T etEvent.class),
        kafkaCl ent d,
        MAX_POLL_RECORDS);
  }

  @V s bleForTest ng
  protected vo d val dateAnd ndexRecord(Consu rRecord<Long, T etEvent> record) {
    T etEvent event = record.value();
    UserScrubGeoEvent geoEvent;
    try {
     geoEvent = event.getData().getUser_scrub_geo_event();
    } catch (Except on e) {
      LOG.warn("T etEventData  s null for T etEvent: " + event.toStr ng());
       ndex ngFa lures. ncre nt();
      return;
    }

     f (geoEvent == null) {
      LOG.warn("UserScrubGeoEvent  s null");
       ndex ngFa lures. ncre nt();

    } else  f (!geoEvent. sSetMax_t et_ d() || !geoEvent. sSetUser_ d()) {
      //   should not consu  an event that does not conta n both a maxT et d & user d s nce  
      //   won't have enough data to properly store t m  n t  map.   should, ho ver, keep
      // track of t se cases s nce   don't want to m ss out on users who have scrubbed t  r
      // geo data from t  r t ets w n apply ng t  UserScrubGeoF lter.
      LOG.warn("UserScrubGeoEvent  s m ss ng f elds: " + geoEvent.toStr ng());
       ndex ngFa lures. ncre nt();
      NUM_M SS NG_DATA_ERRORS. ncre nt();

    } else {
      SearchT  r t  r = search ndex ng tr cSet.userScrubGeo ndex ngStats.startNewT  r();
      seg ntManager. ndexUserScrubGeoEvent(geoEvent);
       ndex ngSuccesses. ncre nt();
      search ndex ng tr cSet.userScrubGeo ndex ngStats.stopT  rAnd ncre nt(t  r);
    }
  }
}
