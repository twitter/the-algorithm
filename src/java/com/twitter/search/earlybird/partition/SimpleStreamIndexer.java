package com.tw ter.search.earlyb rd.part  on;

 mport java.t  .Durat on;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.concurrent.atom c.Atom cBoolean;
 mport java.ut l.stream.Collectors;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Ver fy;

 mport org.apac .kafka.cl ents.consu r.Consu rRecord;
 mport org.apac .kafka.cl ents.consu r.Consu rRecords;
 mport org.apac .kafka.cl ents.consu r.KafkaConsu r;
 mport org.apac .kafka.cl ents.consu r.OffsetAndT  stamp;
 mport org.apac .kafka.common.Part  on nfo;
 mport org.apac .kafka.common.Top cPart  on;
 mport org.apac .kafka.common.errors.WakeupExcept on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.earlyb rd.common.NonPag ngAssert;
 mport com.tw ter.search.earlyb rd.except on.M ss ngKafkaTop cExcept on;

/**
 * Abstract base class for process ng events from Kafka w h t  goal of  ndex ng t m and
 * keep ng Earlyb rds up to date w h t  latest events.  ndex ng  s def ned by t 
 *  mple ntat on.
 *
 * NOTE: {@l nk Earlyb rdKafkaConsu r} (t et/t et events consu r)  s do ng t   n  s
 * own way,   m ght  rge  n t  future.
 *
 * @param <K> (Long)
 * @param <V> (Event/Thr ft type to be consu d)
 */
publ c abstract class S mpleStream ndexer<K, V> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(S mpleStream ndexer.class);

  pr vate stat c f nal Durat on POLL_T MEOUT = Durat on.ofM ll s(250);
  pr vate stat c f nal Durat on CAUGHT_UP_FRESHNESS = Durat on.ofSeconds(5);

  protected stat c f nal  nt MAX_POLL_RECORDS = 1000;

  pr vate f nal SearchCounter numPollErrors;
  protected SearchRateCounter  ndex ngSuccesses;
  protected SearchRateCounter  ndex ngFa lures;

  protected L st<Top cPart  on> top cPart  onL st;
  protected f nal KafkaConsu r<K, V> kafkaConsu r;
  pr vate f nal Atom cBoolean runn ng = new Atom cBoolean(true);
  pr vate f nal Str ng top c;

  pr vate boolean  sCaughtUp = false;

  /**
   * Create a s mple stream  ndexer.
   *
   * @throws M ss ngKafkaTop cExcept on - t  shouldn't happen, but  n case so 
   * external stream  s not present,   want to have t  caller dec de how to
   * handle  . So  m ss ng streams m ght be fatal, for ot rs   m ght not be
   * just f ed to block startup. T re's no po nt  n construct ng t  object  f
   * a stream  s m ss ng, so   don't allow that to happen.
   */
  publ c S mpleStream ndexer(KafkaConsu r<K, V> kafkaConsu r,
                             Str ng top c) throws M ss ngKafkaTop cExcept on {
    t .kafkaConsu r = kafkaConsu r;
    t .top c = top c;
    L st<Part  on nfo> part  on nfos = t .kafkaConsu r.part  onsFor(top c);

     f (part  on nfos == null) {
      LOG.error("Ooops, no part  ons for {}", top c);
      NonPag ngAssert.assertFa led("m ss ng_top c_" + top c);
      throw new M ss ngKafkaTop cExcept on(top c);
    }
    LOG. nfo("D scovered {} part  ons for top c: {}", part  on nfos.s ze(), top c);

    numPollErrors = SearchCounter.export("stream_ ndexer_poll_errors_" + top c);

    t .top cPart  onL st = part  on nfos
        .stream()
        .map( nfo -> new Top cPart  on(top c,  nfo.part  on()))
        .collect(Collectors.toL st());
    t .kafkaConsu r.ass gn(top cPart  onL st);
  }

  /**
   * Consu  updates on startup unt l current (eg. unt l  've seen a record w h n 5 seconds
   * of current t  .)
   */
  publ c vo d readRecordsUnt lCurrent() {
    do {
      Consu rRecords<K, V> records = poll();

      for (Consu rRecord<K, V> record : records) {
         f (record.t  stamp() > System.currentT  M ll s() - CAUGHT_UP_FRESHNESS.toM ll s()) {
           sCaughtUp = true;
        }
        val dateAnd ndexRecord(record);
      }
    } wh le (! sCaughtUp());
  }

  /**
   * Run t  consu r,  ndex ng record values d rectly  nto t  r respect ve structures.
   */
  publ c vo d run() {
    try {
      wh le (runn ng.get()) {
        for (Consu rRecord<K, V> record : poll()) {
          val dateAnd ndexRecord(record);
        }
      }
    } catch (WakeupExcept on e) {
       f (runn ng.get()) {
        LOG.error("Caught wakeup except on wh le runn ng", e);
      }
    } f nally {
      kafkaConsu r.close();
      LOG. nfo("Consu r closed.");
    }
  }

  publ c boolean  sCaughtUp() {
    return  sCaughtUp;
  }

  /**
   * For every part  on  n t  top c, seek to an offset that has a t  stamp greater
   * than or equal to t  g ven t  stamp.
   * @param t  stamp
   */
  publ c vo d seekToT  stamp(Long t  stamp) {
    Map<Top cPart  on, Long> part  onT  stampMap = top cPart  onL st.stream()
        .collect(Collectors.toMap(tp -> tp, tp -> t  stamp));
    Map<Top cPart  on, OffsetAndT  stamp> part  onOffsetMap =
        kafkaConsu r.offsetsForT  s(part  onT  stampMap);

    part  onOffsetMap.forEach((tp, offsetAndT  stamp) -> {
      Ver fy.ver fy(offsetAndT  stamp != null,
        "Couldn't f nd records after t  stamp: " + t  stamp);

      kafkaConsu r.seek(tp, offsetAndT  stamp.offset());
    });
  }

  /**
   * Seeks t  kafka consu r to t  beg nn ng.
   */
  publ c vo d seekToBeg nn ng() {
    kafkaConsu r.seekToBeg nn ng(top cPart  onL st);
  }

  /**
   * Polls and returns at most MAX_POLL_RECORDS records.
   * @return
   */
  @V s bleForTest ng
  protected Consu rRecords<K, V> poll() {
    Consu rRecords<K, V> records;
    try {
      records = kafkaConsu r.poll(POLL_T MEOUT);
    } catch (Except on e) {
      records = Consu rRecords.empty();
       f (e  nstanceof WakeupExcept on) {
        throw e;
      } else {
        LOG.warn("Error poll ng from {} kafka top c.", top c, e);
        numPollErrors. ncre nt();
      }
    }
    return records;
  }

  protected abstract vo d val dateAnd ndexRecord(Consu rRecord<K, V> record);

  // Shutdown hook wh ch can be called from a seperate thread. Call ng consu r.wakeup()  nterrupts
  // t  runn ng  ndexer and causes   to f rst stop poll ng for new records before gracefully
  // clos ng t  consu r.
  publ c vo d close() {
    LOG. nfo("Shutt ng down stream  ndexer for top c {}", top c);
    runn ng.set(false);
    kafkaConsu r.wakeup();
  }
}

