package com.tw ter.search.earlyb rd.part  on;

 mport java.ut l.Date;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport org.apac .kafka.cl ents.consu r.Consu rRecord;
 mport org.apac .kafka.cl ents.consu r.KafkaConsu r;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Ant soc alUserUpdate;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common. tr cs.SearchT  r;
 mport com.tw ter.search.common.ut l. o.kafka.CompactThr ftDeser al zer;
 mport com.tw ter.search.common.ut l. o.kafka.F nagleKafkaCl entUt ls;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdProperty;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserUpdate;
 mport com.tw ter.search.earlyb rd.except on.M ss ngKafkaTop cExcept on;

publ c class UserUpdatesStream ndexer extends S mpleStream ndexer<Long, Ant soc alUserUpdate> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(UserUpdatesStream ndexer.class);

  pr vate stat c f nal SearchCounter NUM_CORRUPT_DATA_ERRORS =
      SearchCounter.export("num_user_updates_kafka_consu r_corrupt_data_errors");
  protected stat c Str ng kafkaCl ent d = "";

  pr vate f nal Seg ntManager seg ntManager;
  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;

  publ c UserUpdatesStream ndexer(KafkaConsu r<Long, Ant soc alUserUpdate> kafkaConsu r,
                                  Str ng top c,
                                  Search ndex ng tr cSet search ndex ng tr cSet,
                                  Seg ntManager seg ntManager)
      throws M ss ngKafkaTop cExcept on {
    super(kafkaConsu r, top c);
    t .seg ntManager = seg ntManager;
    t .search ndex ng tr cSet = search ndex ng tr cSet;

     ndex ngSuccesses = SearchRateCounter.export("user_update_ ndex ng_successes");
     ndex ngFa lures = SearchRateCounter.export("user_update_ ndex ng_fa lures");
  }

  /**
   * Prov des user updates kafka consu r to Earlyb rdW reModule.
   * @return
   */
  publ c stat c KafkaConsu r<Long, Ant soc alUserUpdate> prov deKafkaConsu r() {
    return F nagleKafkaCl entUt ls.newKafkaConsu rForAss gn ng(
        Earlyb rdProperty.KAFKA_PATH.get(),
        new CompactThr ftDeser al zer<>(Ant soc alUserUpdate.class),
        kafkaCl ent d,
        MAX_POLL_RECORDS);
  }

  UserUpdate convertToUser nfoUpdate(Ant soc alUserUpdate update) {
    return new UserUpdate(
        update.getUser D(),
        update.getType(),
        update. sValue() ? 1 : 0,
        new Date(update.getUpdatedAt()));
  }

  @V s bleForTest ng
  protected vo d val dateAnd ndexRecord(Consu rRecord<Long, Ant soc alUserUpdate> record) {
    Ant soc alUserUpdate update = record.value();
     f (update == null) {
      LOG.warn("null value returned from poll");
      return;
    }
     f (update.getType() == null) {
      LOG.error("User update does not have type set: " + update);
      NUM_CORRUPT_DATA_ERRORS. ncre nt();
      return;
    }

    SearchT  r t  r = search ndex ng tr cSet.userUpdate ndex ngStats.startNewT  r();
    boolean  sUpdate ndexed = seg ntManager. ndexUserUpdate(
        convertToUser nfoUpdate(update));
    search ndex ng tr cSet.userUpdate ndex ngStats.stopT  rAnd ncre nt(t  r);

     f ( sUpdate ndexed) {
       ndex ngSuccesses. ncre nt();
    } else {
       ndex ngFa lures. ncre nt();
    }
  }
}
