package com.tw ter.search.earlyb rd.part  on;

 mport java.t  .Durat on;
 mport java.ut l.Arrays;
 mport java.ut l.Collect ons;

 mport org.apac .kafka.cl ents.consu r.Consu rRecord;
 mport org.apac .kafka.cl ents.consu r.Consu rRecords;
 mport org.apac .kafka.cl ents.consu r.KafkaConsu r;
 mport org.apac .kafka.common.Top cPart  on;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;

/**
 * Balanc ngKafkaConsu r  s des gned to read from t  t ets and updates streams  n proport on to
 * t  rates that those streams are wr ten to,  .e. both top cs should have nearly t  sa  amount
 * of lag. T   s  mportant because  f one stream gets too far a ad of t  ot r,   could end up
 *  n a s uat on w re:
 * 1.  f t  t et stream  s a ad of t  updates stream,   couldn't apply an update because a
 *    seg nt has been opt m zed, and one of those f elds beca  frozen.
 * 2.  f t  updates stream  s a ad of t  t et stream,   m ght drop updates because t y are
 *    more than a m nute old, but t  t ets m ght st ll not be  ndexed.
 *
 * Also see 'Consumpt on Flow Control'  n
 * https://kafka.apac .org/23/javadoc/ ndex.html?org/apac /kafka/cl ents/consu r/KafkaConsu r.html
 */
publ c class Balanc ngKafkaConsu r {
  //  f one of t  top c-part  ons lags t  ot r by more than 10 seconds,
  //  's worth   to pause t  faster one and let t  slo r one catch up.
  pr vate stat c f nal long BALANCE_THRESHOLD_MS = Durat on.ofSeconds(10).toM ll s();
  pr vate f nal KafkaConsu r<Long, Thr ftVers onedEvents> kafkaConsu r;
  pr vate f nal Top cPart  on t etTop c;
  pr vate f nal Top cPart  on updateTop c;
  pr vate f nal SearchRateCounter t etsPaused;
  pr vate f nal SearchRateCounter updatesPaused;
  pr vate f nal SearchRateCounter resu d;

  pr vate long t etT  stamp = 0;
  pr vate long updateT  stamp = 0;
  pr vate long pausedAt = 0;
  pr vate boolean paused = false;

  publ c Balanc ngKafkaConsu r(
      KafkaConsu r<Long, Thr ftVers onedEvents> kafkaConsu r,
      Top cPart  on t etTop c,
      Top cPart  on updateTop c
  ) {
    t .kafkaConsu r = kafkaConsu r;
    t .t etTop c = t etTop c;
    t .updateTop c = updateTop c;

    Str ng pref x = "balanc ng_kafka_";
    Str ng suff x = "_top c_paused";

    t etsPaused = SearchRateCounter.export(pref x + t etTop c.top c() + suff x);
    updatesPaused = SearchRateCounter.export(pref x + updateTop c.top c() + suff x);
    resu d = SearchRateCounter.export(pref x + "top cs_resu d");
  }

  /**
   * Calls poll on t  underly ng consu r and pauses top cs as necessary.
   */
  publ c Consu rRecords<Long, Thr ftVers onedEvents> poll(Durat on t  out) {
    Consu rRecords<Long, Thr ftVers onedEvents> records = kafkaConsu r.poll(t  out);
    top cFlowControl(records);
    return records;
  }

  pr vate vo d top cFlowControl(Consu rRecords<Long, Thr ftVers onedEvents> records) {
    for (Consu rRecord<Long, Thr ftVers onedEvents> record : records) {
      long t  stamp = record.t  stamp();

       f (updateTop c.top c().equals(record.top c())) {
        updateT  stamp = Math.max(updateT  stamp, t  stamp);
      } else  f (t etTop c.top c().equals(record.top c())) {
        t etT  stamp = Math.max(t etT  stamp, t  stamp);
      } else {
        throw new  llegalStateExcept on(
            "Unexpected part  on " + record.top c() + "  n Balanc ngKafkaConsu r");
      }
    }

     f (paused) {
      //  f   paused and one of t  streams  s st ll below t  pausedAt po nt,   want to cont nue
      // read ng from just t  lagg ng stream.
       f (t etT  stamp >= pausedAt && updateT  stamp >= pausedAt) {
        //   caught up, resu  read ng from both top cs.
        paused = false;
        kafkaConsu r.resu (Arrays.asL st(t etTop c, updateTop c));
        resu d. ncre nt();
      }
    } else {
      long d fference = Math.abs(t etT  stamp - updateT  stamp);

       f (d fference < BALANCE_THRESHOLD_MS) {
        // T  streams have approx mately t  sa  lag, so no need to pause anyth ng.
        return;
      }
      // T  d fference  s too great, one of t  streams  s lagg ng beh nd t  ot r so   need to
      // pause one top c so t  ot r can catch up.
      paused = true;
      pausedAt = Math.max(updateT  stamp, t etT  stamp);
       f (t etT  stamp > updateT  stamp) {
        kafkaConsu r.pause(Collect ons.s ngleton(t etTop c));
        t etsPaused. ncre nt();
      } else {
        kafkaConsu r.pause(Collect ons.s ngleton(updateTop c));
        updatesPaused. ncre nt();
      }
    }
  }

  publ c vo d close() {
    kafkaConsu r.close();
  }
}
