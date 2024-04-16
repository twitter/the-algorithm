package com.tw ter.search. ngester.p pel ne.tw ter.userupdates;

 mport java.t  .Durat on;
 mport java.ut l.ArrayL st;
 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.concurrent.Semaphore;
 mport java.ut l.funct on.Suppl er;

 mport scala.runt  .BoxedUn ;

 mport com.google.common.base.Precond  ons;

 mport org.apac .kafka.cl ents.consu r.Consu rRecord;
 mport org.apac .kafka.cl ents.consu r.KafkaConsu r;
 mport org.apac .kafka.cl ents.producer.ProducerRecord;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f natra.kafka.producers.Block ngF nagleKafkaProducer;
 mport com.tw ter.g zmoduck.thr ftjava.UserMod f cat on;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Ant soc alUserUpdate;
 mport com.tw ter.search.common. tr cs.SearchCustomGauge;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.ut l. o.kafka.CompactThr ftSer al zer;
 mport com.tw ter.search.common.ut l. o.kafka.Thr ftDeser al zer;
 mport com.tw ter.search. ngester.p pel ne.w re.W reModule;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.Futures;

/**
 * T  class reads UserMod f cat on events from Kafka, transforms t m  nto Ant soc alUserUpdates,
 * and wr es t m to Kafka.
 */
publ c f nal class UserUpdatesP pel ne {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(UserUpdatesP pel ne.class);
  pr vate stat c f nal Durat on POLL_T MEOUT = Durat on.ofSeconds(1);
  pr vate stat c f nal  nt MAX_PEND NG_EVENTS = 100;
  pr vate stat c f nal Str ng KAFKA_CL ENT_ D = "";
  pr vate stat c f nal  nt MAX_POLL_RECORDS = 1;
  pr vate stat c f nal Str ng USER_MOD F CAT ONS_KAFKA_TOP C = "";
  pr vate stat c f nal Str ng USER_UPDATES_KAFKA_TOP C_PREF X = "";
  pr vate stat c f nal Str ng KAFKA_PRODUCER_DEST = "";
  pr vate stat c f nal Str ng KAFKA_CONSUMER_DEST = "";

  // T  semaphore stops us from hav ng more than MAX_PEND NG_EVENTS  n t  p pel ne at any po nt
  //  n t  .
  pr vate f nal Semaphore pend ngEvents = new Semaphore(MAX_PEND NG_EVENTS);
  pr vate f nal Suppl er<Boolean>  sRunn ng;
  pr vate f nal KafkaConsu r<Long, UserMod f cat on> userMod f cat onConsu r;
  pr vate f nal UserUpdate ngester userUpdate ngester;
  pr vate f nal SearchRateCounter records;
  pr vate f nal SearchRateCounter success;
  pr vate f nal SearchRateCounter fa lure;

  pr vate f nal Str ng userUpdatesKafkaTop c;
  pr vate f nal Block ngF nagleKafkaProducer<Long, Ant soc alUserUpdate> userUpdatesProducer;
  pr vate f nal Clock clock;

  /**
   * Bu lds t  p pel ne.
   */
  publ c stat c UserUpdatesP pel ne bu ldP pel ne(
      Str ng env ron nt,
      W reModule w reModule,
      Str ng statsPref x,
      Suppl er<Boolean>  sRunn ng,
      Clock clock) throws Except on {

    //   only have G zmoduck cl ents for stag ng and prod.
    Str ng g zmoduckCl ent;
     f (env ron nt.startsW h("stag ng")) {
      g zmoduckCl ent = "";
    } else {
      Precond  ons.c ckState("prod".equals(env ron nt));
      g zmoduckCl ent = "";
    }
    LOG. nfo("G zmoduck cl ent: {}", g zmoduckCl ent);

    Str ng kafkaConsu rGroup = "" + env ron nt;
    KafkaConsu r<Long, UserMod f cat on> userMod f cat onConsu r = w reModule.newKafkaConsu r(
        KAFKA_CONSUMER_DEST,
        new Thr ftDeser al zer<>(UserMod f cat on.class),
        KAFKA_CL ENT_ D,
        kafkaConsu rGroup,
        MAX_POLL_RECORDS);
    userMod f cat onConsu r.subscr be(Collect ons.s ngleton(USER_MOD F CAT ONS_KAFKA_TOP C));
    LOG. nfo("User mod f cat ons top c: {}", USER_MOD F CAT ONS_KAFKA_TOP C);
    LOG. nfo("User updates Kafka top c pref x: {}", USER_UPDATES_KAFKA_TOP C_PREF X);
    LOG. nfo("Kafka consu r group: {}", kafkaConsu rGroup);
    LOG. nfo("Kafka cl ent  d: {}", KAFKA_CL ENT_ D);

    UserUpdate ngester userUpdate ngester = new UserUpdate ngester(
        statsPref x,
        w reModule.getG zmoduckCl ent(g zmoduckCl ent),
        w reModule.getDec der());

    Str ng userUpdatesKafkaTop c = USER_UPDATES_KAFKA_TOP C_PREF X + env ron nt;
    Block ngF nagleKafkaProducer<Long, Ant soc alUserUpdate> userUpdatesProducer =
        w reModule.newF nagleKafkaProducer(
            KAFKA_PRODUCER_DEST,
            new CompactThr ftSer al zer<Ant soc alUserUpdate>(),
            KAFKA_CL ENT_ D,
            null);

    return new UserUpdatesP pel ne(
         sRunn ng,
        userMod f cat onConsu r,
        userUpdate ngester,
        userUpdatesProducer,
        userUpdatesKafkaTop c,
        clock);
  }

  pr vate UserUpdatesP pel ne(
      Suppl er<Boolean>  sRunn ng,
      KafkaConsu r<Long, UserMod f cat on> userMod f cat onConsu r,
      UserUpdate ngester userUpdate ngester,
      Block ngF nagleKafkaProducer<Long, Ant soc alUserUpdate> userUpdatesProducer,
      Str ng userUpdatesKafkaTop c,
      Clock clock) {
    t . sRunn ng =  sRunn ng;
    t .userMod f cat onConsu r = userMod f cat onConsu r;
    t .userUpdate ngester = userUpdate ngester;
    t .userUpdatesProducer = userUpdatesProducer;
    t .userUpdatesKafkaTop c = userUpdatesKafkaTop c;
    t .clock = clock;

    Str ng statPref x = "user_updates_p pel ne_";
    SearchCustomGauge.export(statPref x + "semaphore_perm s", pend ngEvents::ava lablePerm s);

    records = SearchRateCounter.export(statPref x + "records_processed_total");
    success = SearchRateCounter.export(statPref x + "records_processed_success");
    fa lure = SearchRateCounter.export(statPref x + "records_processed_fa lure");
  }

  /**
   * Start t  user updates p pel ne.
   */
  publ c vo d run() {
    wh le ( sRunn ng.get()) {
      try {
        pollFromKafka();
      } catch (Throwable e) {
        LOG.error("Except on process ng event.", e);
      }
    }
    close();
  }
  /**
   * Polls records from Kafka and handles t  outs, back-pressure, and error handl ng.
   * All consu d  ssages are passed to t   ssageHandler.
   */
  pr vate vo d pollFromKafka() throws Except on {
    for (Consu rRecord<Long, UserMod f cat on> record
        : userMod f cat onConsu r.poll(POLL_T MEOUT)) {
      pend ngEvents.acqu re();
      records. ncre nt();

      handleUserMod f cat on(record.value())
          .onFa lure(e -> {
            fa lure. ncre nt();
            return null;
          })
          .onSuccess(u -> {
            success. ncre nt();
            return null;
          })
          .ensure(() -> {
            pend ngEvents.release();
            return null;
          });
    }
  }

  /**
   * Handles t  bus ness log c for t  user updates p pel ne:
   * 1. Converts  ncom ng event  nto poss bly empty set of Ant soc alUserUpdates
   * 2. Wr es t  result to Kafka so that Earlyb rd can consu   .
   */
  pr vate Future<BoxedUn > handleUserMod f cat on(UserMod f cat on event) {
    return userUpdate ngester
        .transform(event)
        .flatMap(t ::wr eL stToKafka);
  }

  pr vate Future<BoxedUn > wr eL stToKafka(L st<Ant soc alUserUpdate> updates) {
    L st<Future<BoxedUn >> futures = new ArrayL st<>();
    for (Ant soc alUserUpdate update : updates) {
      futures.add(wr eToKafka(update));
    }
    return Futures.jo n(futures).onFa lure(e -> {
      LOG. nfo("Except on wh le wr  ng to kafka", e);
      return null;
    });
  }

  pr vate Future<BoxedUn > wr eToKafka(Ant soc alUserUpdate update) {
      ProducerRecord<Long, Ant soc alUserUpdate> record = new ProducerRecord<>(
          userUpdatesKafkaTop c,
          null,
          clock.nowM ll s(),
          null,
          update);
      try {
        return userUpdatesProducer.send(record).un ();
      } catch (Except on e) {
        return Future.except on(e);
      }
  }

  pr vate vo d close() {
    userMod f cat onConsu r.close();
    try {
      // Acqu re all of t  perm s, so   know all pend ng events have been wr ten.
      pend ngEvents.acqu re(MAX_PEND NG_EVENTS);
    } catch (Except on e) {
      LOG.error("Error shutt ng down stage", e);
    }
  }
}
