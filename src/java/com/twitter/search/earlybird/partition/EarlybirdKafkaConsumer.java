package com.tw ter.search.earlyb rd.part  on;

 mport java. o.Closeable;
 mport java.t  .Durat on;
 mport java.ut l.Map;
 mport java.ut l.concurrent.atom c.Atom cBoolean;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Stopwatch;
 mport com.google.common.collect. mmutableL st;

 mport org.apac .kafka.cl ents.consu r.Consu rRecords;
 mport org.apac .kafka.cl ents.consu r.KafkaConsu r;
 mport org.apac .kafka.common.Top cPart  on;
 mport org.apac .kafka.common.errors.Ap Except on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common. tr cs.SearchT  r;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.ut l.LogFormatUt l;
 mport com.tw ter.search.earlyb rd.Earlyb rdStatus;
 mport com.tw ter.search.earlyb rd.common.CaughtUpMon or;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.except on.WrappedKafkaAp Except on;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdStatusCode;

/**
 * Reads TVEs from Kafka and wr es t m to a Part  onWr er.
 */
publ c class Earlyb rdKafkaConsu r  mple nts Closeable {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdKafkaConsu r.class);

  pr vate stat c f nal Durat on POLL_T MEOUT = Durat on.ofSeconds(1);
  pr vate stat c f nal Str ng STATS_PREF X = "earlyb rd_kafka_consu r_";

  // See SEARCH-31827
  pr vate stat c f nal SearchCounter  NGEST NG_DONE =
      SearchCounter.export(STATS_PREF X + " ngest ng_done");
  pr vate stat c f nal SearchRateCounter POLL_LOOP_EXCEPT ONS =
      SearchRateCounter.export(STATS_PREF X + "poll_loop_except ons");
  pr vate stat c f nal SearchRateCounter FLUSH NG_EXCEPT ONS =
      SearchRateCounter.export(STATS_PREF X + "flush ng_except ons");

  pr vate stat c f nal SearchT  rStats T MED_POLLS =
      SearchT  rStats.export(STATS_PREF X + "t  d_polls");
  pr vate stat c f nal SearchT  rStats T MED_ NDEX_EVENTS =
      SearchT  rStats.export(STATS_PREF X + "t  d_ ndex_events");

  pr vate f nal Atom cBoolean runn ng = new Atom cBoolean(true);
  pr vate f nal Balanc ngKafkaConsu r balanc ngKafkaConsu r;
  pr vate f nal Part  onWr er part  onWr er;
  protected f nal Top cPart  on t etTop c;
  protected f nal Top cPart  on updateTop c;
  pr vate f nal KafkaConsu r<Long, Thr ftVers onedEvents> underly ngKafkaConsu r;
  pr vate f nal Cr  calExcept onHandler cr  calExcept onHandler;
  pr vate f nal Earlyb rd ndexFlus r earlyb rd ndexFlus r;
  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;
  pr vate boolean f n s d ngestUnt lCurrent;
  pr vate f nal CaughtUpMon or  ndexCaughtUpMon or;

  protected class Consu BatchResult {
    pr vate boolean  sCaughtUp;
    pr vate long readRecordsCount;

    publ c Consu BatchResult(boolean  sCaughtUp, long readRecordsCount) {
      t . sCaughtUp =  sCaughtUp;
      t .readRecordsCount = readRecordsCount;
    }

    publ c boolean  sCaughtUp() {
      return  sCaughtUp;
    }

    publ c long getReadRecordsCount() {
      return readRecordsCount;
    }
  }

  publ c Earlyb rdKafkaConsu r(
      KafkaConsu r<Long, Thr ftVers onedEvents> underly ngKafkaConsu r,
      Search ndex ng tr cSet search ndex ng tr cSet,
      Cr  calExcept onHandler cr  calExcept onHandler,
      Part  onWr er part  onWr er,
      Top cPart  on t etTop c,
      Top cPart  on updateTop c,
      Earlyb rd ndexFlus r earlyb rd ndexFlus r,
      CaughtUpMon or kafka ndexCaughtUpMon or
  ) {
    t .part  onWr er = part  onWr er;
    t .underly ngKafkaConsu r = underly ngKafkaConsu r;
    t .cr  calExcept onHandler = cr  calExcept onHandler;
    t .search ndex ng tr cSet = search ndex ng tr cSet;
    t .t etTop c = t etTop c;
    t .updateTop c = updateTop c;
    t .earlyb rd ndexFlus r = earlyb rd ndexFlus r;

    LOG. nfo("Read ng from Kafka top cs: t etTop c={}, updateTop c={}", t etTop c, updateTop c);
    underly ngKafkaConsu r.ass gn( mmutableL st.of(updateTop c, t etTop c));

    t .balanc ngKafkaConsu r =
        new Balanc ngKafkaConsu r(underly ngKafkaConsu r, t etTop c, updateTop c);
    t .f n s d ngestUnt lCurrent = false;
    t . ndexCaughtUpMon or = kafka ndexCaughtUpMon or;
  }

  /**
   * Run t  consu r,  ndex ng from Kafka.
   */
  @V s bleForTest ng
  publ c vo d run() {
    wh le ( sRunn ng()) {
      Consu BatchResult result = consu Batch(true);
       ndexCaughtUpMon or.setAndNot fy(result. sCaughtUp());
    }
  }

  /**
   * Reads from Kafka, start ng at t  g ven offsets, and appl es t  events unt l   are caught up
   * w h t  current streams.
   */
  publ c vo d  ngestUnt lCurrent(long t etOffset, long updateOffset) {
    Precond  ons.c ckState(!f n s d ngestUnt lCurrent);
    Stopwatch stopwatch = Stopwatch.createStarted();
    LOG. nfo(" ngest unt l current: seek ng to Kafka offset {} for t ets and {} for updates.",
        t etOffset, updateOffset);

    try {
      underly ngKafkaConsu r.seek(t etTop c, t etOffset);
      underly ngKafkaConsu r.seek(updateTop c, updateOffset);
    } catch (Ap Except on kafkaAp Except on) {
      throw new WrappedKafkaAp Except on("Can't seek to t et and update offsets",
          kafkaAp Except on);
    }

    Map<Top cPart  on, Long> endOffsets;
    try {
      endOffsets = underly ngKafkaConsu r.endOffsets( mmutableL st.of(t etTop c, updateTop c));
    } catch (Ap Except on kafkaAp Except on) {
      throw new WrappedKafkaAp Except on("Can't f nd end offsets",
          kafkaAp Except on);
    }

     f (endOffsets.s ze() > 0) {
      LOG. nfo(Str ng.format("Records unt l current: t ets=%,d, updates=%,d",
          endOffsets.get(t etTop c) - t etOffset + 1,
          endOffsets.get(updateTop c) - updateOffset + 1));
    }

    consu Batc sUnt lCurrent(true);

    LOG. nfo(" ngestUnt lCurrent f n s d  n {}.", stopwatch);

    part  onWr er.logState();
     NGEST NG_DONE. ncre nt();
    f n s d ngestUnt lCurrent = true;
  }

  /**
   * Consu  t ets and updates from streams unt l  're up to date.
   *
   * @return total number of read records.
   */
  pr vate long consu Batc sUnt lCurrent(boolean flush ngEnabled) {
    long totalRecordsRead = 0;
    long batc sConsu d = 0;

    wh le ( sRunn ng()) {
      Consu BatchResult result = consu Batch(flush ngEnabled);
      batc sConsu d++;
      totalRecordsRead += result.getReadRecordsCount();
       f ( sCurrent(result. sCaughtUp())) {
        break;
      }
    }

    LOG. nfo("Processed batc s: {}", batc sConsu d);

    return totalRecordsRead;
  }

  // T   thod  s overr den  n MockEarlyb rdKafkaConsu r.
  publ c boolean  sCurrent(boolean current) {
    return current;
  }

  /**
   *   don't  ndex dur ng flush ng, so after t  flush  s done, t   ndex  s stale.
   *   need to get to current, before   rejo n t  serverset so that upon rejo n ng  're
   * not serv ng a stale  ndex.
   */
  @V s bleForTest ng
  vo d getToCurrentPostFlush() {
    LOG. nfo("Gett ng to current post flush");
    Stopwatch stopwatch = Stopwatch.createStarted();

    long totalRecordsRead = consu Batc sUnt lCurrent(false);

    LOG. nfo("Post flush, beca  current  n: {}, after read ng {} records.",
        stopwatch, LogFormatUt l.format nt(totalRecordsRead));
  }

  /*
   * @return true  f   are current after  ndex ng t  batch.
   */
  @V s bleForTest ng
  protected Consu BatchResult consu Batch(boolean flush ngEnabled) {
    long readRecordsCount = 0;
    boolean  sCaughtUp = false;

    try {
      // Poll.
      SearchT  r pollT  r = T MED_POLLS.startNewT  r();
      Consu rRecords<Long, Thr ftVers onedEvents> records =
          balanc ngKafkaConsu r.poll(POLL_T MEOUT);
      readRecordsCount += records.count();
      T MED_POLLS.stopT  rAnd ncre nt(pollT  r);

      //  ndex.
      SearchT  r  ndexT  r = T MED_ NDEX_EVENTS.startNewT  r();
       sCaughtUp = part  onWr er. ndexBatch(records);
      T MED_ NDEX_EVENTS.stopT  rAnd ncre nt( ndexT  r);
    } catch (Except on ex) {
      POLL_LOOP_EXCEPT ONS. ncre nt();
      LOG.error("Except on  n poll loop", ex);
    }

    try {
      // Poss bly flush t   ndex.
       f ( sCaughtUp && flush ngEnabled) {
        long t etOffset = 0;
        long updateOffset = 0;

        try {
          t etOffset = underly ngKafkaConsu r.pos  on(t etTop c);
          updateOffset = underly ngKafkaConsu r.pos  on(updateTop c);
        } catch (Ap Except on kafkaAp Except on) {
          throw new WrappedKafkaAp Except on("can't get top c pos  ons", kafkaAp Except on);
        }

        Earlyb rd ndexFlus r.FlushAttemptResult flushAttemptResult =
            earlyb rd ndexFlus r.flush fNecessary(
                t etOffset, updateOffset, t ::getToCurrentPostFlush);

         f (flushAttemptResult == Earlyb rd ndexFlus r.FlushAttemptResult.FLUSH_ATTEMPT_MADE) {
          // V z m ght show t  as a fa rly h gh number, so  're pr nt ng    re to conf rm
          // t  value on t  server.
          LOG. nfo("F n s d flush ng.  ndex freshness  n ms: {}",
              LogFormatUt l.format nt(search ndex ng tr cSet.get ndexFreshness nM ll s()));
        }

         f (!f n s d ngestUnt lCurrent) {
          LOG. nfo("Beca  current on startup. Tr ed to flush w h result: {}",
              flushAttemptResult);
        }
      }
    } catch (Except on ex) {
      FLUSH NG_EXCEPT ONS. ncre nt();
      LOG.error("Except on wh le flush ng", ex);
    }

    return new Consu BatchResult( sCaughtUp, readRecordsCount);
  }

  publ c boolean  sRunn ng() {
    return runn ng.get() && Earlyb rdStatus.getStatusCode() != Earlyb rdStatusCode.STOPP NG;
  }

  publ c vo d prepareAfterStart ngW h ndex(long max ndexedT et d) {
    part  onWr er.prepareAfterStart ngW h ndex(max ndexedT et d);
  }

  publ c vo d close() {
    balanc ngKafkaConsu r.close();
    runn ng.set(false);
  }
}
