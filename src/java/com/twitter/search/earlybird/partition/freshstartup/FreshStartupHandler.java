package com.tw ter.search.earlyb rd.part  on.freshstartup;

 mport java. o. OExcept on;
 mport java.t  .Durat on;
 mport java.ut l.ArrayL st;
 mport java.ut l.HashSet;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;

 mport com.google.common.base.Stopwatch;
 mport com.google.common.base.Ver fy;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.L sts;

 mport org.apac .kafka.cl ents.consu r.Consu rRecord;
 mport org.apac .kafka.cl ents.consu r.Consu rRecords;
 mport org.apac .kafka.cl ents.consu r.KafkaConsu r;
 mport org.apac .kafka.cl ents.consu r.OffsetAndT  stamp;
 mport org.apac .kafka.common.Top cPart  on;
 mport org.apac .kafka.common.errors.Ap Except on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport stat c com.tw ter.search.common.ut l.LogFormatUt l.format nt;

 mport com.tw ter.search.common.ut l.GCUt l;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.ut l.LogFormatUt l;
 mport com.tw ter.search.earlyb rd.common.NonPag ngAssert;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.except on.Earlyb rdStartupExcept on;
 mport com.tw ter.search.earlyb rd.except on.WrappedKafkaAp Except on;
 mport com.tw ter.search.earlyb rd.factory.Earlyb rdKafkaConsu rsFactory;
 mport com.tw ter.search.earlyb rd.part  on.Earlyb rd ndex;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager;
 mport com.tw ter.search.earlyb rd.ut l.ParallelUt l;

/**
 * Bootstraps an  ndex by  ndex ng t ets and updates  n parallel.
 *
 * DEVELOPMENT
 * ===========
 *
 * 1.  n earlyb rd-search.yml, set t  follow ng values  n t  "product on" sect on:
 *   - max_seg nt_s ze to 200000
 *   - late_t et_buffer to 10000
 *
 * 2.  n KafkaStartup, don't load t   ndex, replace t  .load ndex call as  nstructed
 *   n t  f le.
 *
 * 3.  n t  aurora conf gs, set serv ng_t  sl ces to a low number (l ke 5) for stag ng.
 */
publ c class FreshStartupHandler {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(FreshStartupHandler.class);
  pr vate stat c f nal NonPag ngAssert BU LD NG_FEWER_THAN_SPEC F ED_SEGMENTS =
          new NonPag ngAssert("bu ld ng_fe r_than_spec f ed_seg nts");

  pr vate f nal Clock clock;
  pr vate f nal Top cPart  on t etTop c;
  pr vate f nal Top cPart  on updateTop c;
  pr vate f nal Seg ntManager seg ntManager;
  pr vate f nal  nt maxSeg ntS ze;
  pr vate f nal  nt lateT etBuffer;
  pr vate f nal Earlyb rdKafkaConsu rsFactory earlyb rdKafkaConsu rsFactory;
  pr vate f nal Cr  calExcept onHandler cr  calExcept onHandler;

  publ c FreshStartupHandler(
    Clock clock,
    Earlyb rdKafkaConsu rsFactory earlyb rdKafkaConsu rsFactory,
    Top cPart  on t etTop c,
    Top cPart  on updateTop c,
    Seg ntManager seg ntManager,
     nt maxSeg ntS ze,
     nt lateT etBuffer,
    Cr  calExcept onHandler cr  calExcept onHandler
  ) {
    t .clock = clock;
    t .earlyb rdKafkaConsu rsFactory = earlyb rdKafkaConsu rsFactory;
    t .t etTop c = t etTop c;
    t .updateTop c = updateTop c;
    t .seg ntManager = seg ntManager;
    t .maxSeg ntS ze = maxSeg ntS ze;
    t .cr  calExcept onHandler = cr  calExcept onHandler;
    t .lateT etBuffer = lateT etBuffer;
  }

  /**
   * Don't  ndex  n parallel, just pass so  t   back that t  Earlyb rdKafkaConsu r
   * can start  ndex ng from.
   */
  publ c Earlyb rd ndex  ndexFromScratch() {
    long  ndexT  Per od = Durat on.ofH s(
        Earlyb rdConf g.get nt(" ndex_from_scratch_h s", 12)
    ).toM ll s();

    return run ndexFromScratch( ndexT  Per od);
  }

  publ c Earlyb rd ndex fast ndexFromScratchForDevelop nt() {
    LOG. nfo("Runn ng fast  ndex from scratch...");
    return run ndexFromScratch(Durat on.ofM nutes(10).toM ll s());
  }

  pr vate Earlyb rd ndex run ndexFromScratch(long  ndexT  Per odMs) {
    KafkaConsu r<Long, Thr ftVers onedEvents> consu rForF nd ngOffsets =
        earlyb rdKafkaConsu rsFactory.createKafkaConsu r("consu r_for_offsets");

    long t  stamp = clock.nowM ll s() -  ndexT  Per odMs;

    Map<Top cPart  on, OffsetAndT  stamp> offsets;
    try {
      offsets = consu rForF nd ngOffsets
          .offsetsForT  s( mmutableMap.of(t etTop c, t  stamp, updateTop c, t  stamp));
    } catch (Ap Except on kafkaAp Except on) {
      throw new WrappedKafkaAp Except on(kafkaAp Except on);
    }

    return new Earlyb rd ndex(
        L sts.newArrayL st(),
        offsets.get(t etTop c).offset(),
        offsets.get(updateTop c).offset());
  }


  /**
   *  ndex T ets and updates from scratch, w hout rely ng on a ser al zed  ndex  n HDFS.
   *
   * T  funct on  ndexes t  seg nts  n parallel, l m  ng t  number of seg nts that
   * are currently  ndexed, due to  mory l m at ons. That's follo d by anot r pass to  ndex
   * so  updates - see t   mple ntat on for more deta ls.
   *
   * T   ndex t  funct on outputs conta ns N seg nts, w re t  f rst N-1 are opt m zed and
   * t  last one  s not.
   */
  publ c Earlyb rd ndex parallel ndexFromScratch() throws Except on {
    Stopwatch parallel ndexStopwatch = Stopwatch.createStarted();

    LOG. nfo("Start ng parallel fresh startup.");
    LOG. nfo("Max seg nt s ze: {}", maxSeg ntS ze);
    LOG. nfo("Late t et buffer s ze: {}", lateT etBuffer);

    // Once   f n sh fresh startup and proceed to  ndex ng from t  streams,  'll  m d ately
    // start a new seg nt, s nce t  output of t  fresh startup  s full seg nts.
    //
    // That's why    ndex max_seg nts-1 seg nts  re  nstead of  ndex ng max_seg nts seg nts
    // and d scard ng t  f rst one later.
     nt numSeg nts = seg ntManager.getMaxEnabledSeg nts() - 1;
    LOG. nfo("Number of seg nts to bu ld: {}", numSeg nts);

    // F nd end offsets.
    KafkaOffsetPa r t etsOffsetRange = f ndOffsetRangeForT etsKafkaTop c();

    ArrayL st<Seg ntBu ld nfo> seg ntBu ld nfos = makeSeg ntBu ld nfos(
        numSeg nts, t etsOffsetRange);

    seg ntManager.logState("Before start ng fresh startup");

    //  ndex t ets and events.
    Stopwatch  n  al ndexStopwatch = Stopwatch.createStarted();

    //    ndex at most `MAX_PARALLEL_ NDEXED` (MP ) seg nts at t  sa  t  .  f   need to
    // produce 20 seg nts  re,  'd need  mory for MP  unopt m zed and 20-MP  opt m zed seg nts.
    //
    // For back of envelope calculat ons   can assu  opt m zed seg nts take ~6GB and unopt m zed
    // ones ~12GB.
    f nal  nt MAX_PARALLEL_ NDEXED = 8;

    L st<Seg nt nfo> seg nt nfos = ParallelUt l.parmap(
        "fresh-startup",
        MAX_PARALLEL_ NDEXED,
        seg ntBu ld nfo ->  ndexT etsAndUpdatesForSeg nt(seg ntBu ld nfo, seg ntBu ld nfos),
        seg ntBu ld nfos
    );

    LOG. nfo("F n s d  ndex ng t ets and updates  n {}",  n  al ndexStopwatch);

    PostOpt m zat onUpdates ndexer postOpt m zat onUpdates ndexer =
        new PostOpt m zat onUpdates ndexer(
            seg ntBu ld nfos,
            earlyb rdKafkaConsu rsFactory,
            updateTop c);

    postOpt m zat onUpdates ndexer. ndexRestOfUpdates();

    // F n s d  ndex ng t ets and updates.
    LOG. nfo("Seg nt bu ld  nfos after  're done:");
    for (Seg ntBu ld nfo seg ntBu ld nfo : seg ntBu ld nfos) {
      seg ntBu ld nfo.logState();
    }

    seg ntManager.logState("After f n sh ng fresh startup");

    LOG. nfo("Collected {} seg nt  nfos", seg nt nfos.s ze());
    LOG. nfo("Seg nt na s:");
    for (Seg nt nfo seg nt nfo : seg nt nfos) {
      LOG. nfo(seg nt nfo.getSeg ntNa ());
    }

    Seg ntBu ld nfo lastSeg ntBu ld nfo = seg ntBu ld nfos.get(seg ntBu ld nfos.s ze() - 1);
    long f n s dUpdatesAtOffset = lastSeg ntBu ld nfo.getUpdateKafkaOffsetPa r().getEndOffset();
    long max ndexedT et d = lastSeg ntBu ld nfo.getMax ndexedT et d();

    LOG. nfo("Max  ndexed t et  d: {}", max ndexedT et d);
    LOG. nfo("Parallel startup f n s d  n {}", parallel ndexStopwatch);

    // ver fyConstructed ndex(seg ntBu ld nfos);
    // Run a GC to free up so   mory after t  fresh startup.
    GCUt l.runGC();
    log moryStats();

    return new Earlyb rd ndex(
        seg nt nfos,
        t etsOffsetRange.getEndOffset() + 1,
        f n s dUpdatesAtOffset + 1,
        max ndexedT et d
    );
  }

  pr vate vo d log moryStats() {
    double toGB = 1024 * 1024 * 1024;
    double total moryGB = Runt  .getRunt  ().total mory() / toGB;
    double free moryGB = Runt  .getRunt  ().free mory() / toGB;
    LOG. nfo(" mory stats: Total  mory GB: {}, Free  mory GB: {}",
        total moryGB, free moryGB);
  }

  /**
   * Pr nts stat st cs about t  constructed  ndex compared to all t ets  n t 
   * t ets stream.
   *
   * Only run t  for test ng and debugg ng purposes, never  n prod env ron nt.
   */
  pr vate vo d ver fyConstructed ndex(L st<Seg ntBu ld nfo> seg ntBu ld nfos)
      throws  OExcept on {
    LOG. nfo("Ver fy ng constructed  ndex...");
    // Read every t et from t  offset range that  're construct ng an  ndex for.
    KafkaConsu r<Long, Thr ftVers onedEvents> t etsKafkaConsu r =
        earlyb rdKafkaConsu rsFactory.createKafkaConsu r("t ets_ver fy");
    try {
      t etsKafkaConsu r.ass gn( mmutableL st.of(t etTop c));
      t etsKafkaConsu r.seek(t etTop c, seg ntBu ld nfos.get(0).getT etStartOffset());
    } catch (Ap Except on ap Except on) {
      throw new WrappedKafkaAp Except on(ap Except on);
    }
    long f nalT etOffset = seg ntBu ld nfos.get(seg ntBu ld nfos.s ze() - 1).getT etEndOffset();
    boolean done = false;
    Set<Long> un queT et ds = new HashSet<>();
    long readT etsCount = 0;
    do {
      for (Consu rRecord<Long, Thr ftVers onedEvents> record
          : t etsKafkaConsu r.poll(Durat on.ofSeconds(1))) {
         f (record.offset() > f nalT etOffset) {
          done = true;
          break;
        }
        readT etsCount++;
        un queT et ds.add(record.value().get d());
      }
    } wh le (!done);

    LOG. nfo("Total amount of read t ets: {}", format nt(readT etsCount));
    // M ght be less, due to dupl cates.
    LOG. nfo("Un que t et  ds : {}", LogFormatUt l.format nt(un queT et ds.s ze()));

     nt notFound n ndex = 0;
    for (Long t et d : un queT et ds) {
      boolean found = false;
      for (Seg ntBu ld nfo seg ntBu ld nfo : seg ntBu ld nfos) {
         f (seg ntBu ld nfo.getSeg ntWr er().hasT et(t et d)) {
          found = true;
          break;
        }
      }
       f (!found) {
        notFound n ndex++;
      }
    }

    LOG. nfo("T ets not found  n t   ndex: {}", LogFormatUt l.format nt(notFound n ndex));

    long total ndexedT ets = 0;
    for (Seg ntBu ld nfo seg ntBu ld nfo : seg ntBu ld nfos) {
      Seg nt nfo s  = seg ntBu ld nfo.getSeg ntWr er().getSeg nt nfo();
      total ndexedT ets += s .get ndexStats().getStatusCount();
    }

    LOG. nfo("Total  ndexed t ets: {}", format nt(total ndexedT ets));
  }

  /**
   * F nd t  end offsets for t  t ets Kafka top c t  part  on  s read ng
   * from.
   */
  pr vate KafkaOffsetPa r f ndOffsetRangeForT etsKafkaTop c() {
    KafkaConsu r<Long, Thr ftVers onedEvents> consu rForF nd ngOffsets =
        earlyb rdKafkaConsu rsFactory.createKafkaConsu r("consu r_for_end_offsets");

    Map<Top cPart  on, Long> endOffsets;
    Map<Top cPart  on, Long> beg nn ngOffsets;

    try {
      endOffsets = consu rForF nd ngOffsets.endOffsets( mmutableL st.of(t etTop c));
      beg nn ngOffsets = consu rForF nd ngOffsets.beg nn ngOffsets( mmutableL st.of(t etTop c));
    } catch (Ap Except on kafkaAp Except on) {
      throw new WrappedKafkaAp Except on(kafkaAp Except on);
    } f nally {
      consu rForF nd ngOffsets.close();
    }

    long t etsBeg nn ngOffset = beg nn ngOffsets.get(t etTop c);
    long t etsEndOffset = endOffsets.get(t etTop c);
    LOG. nfo(Str ng.format("T ets beg nn ng offset: %,d", t etsBeg nn ngOffset));
    LOG. nfo(Str ng.format("T ets end offset: %,d", t etsEndOffset));
    LOG. nfo(Str ng.format("Total amount of records  n t  stream: %,d",
        t etsEndOffset - t etsBeg nn ngOffset + 1));

    return new KafkaOffsetPa r(t etsBeg nn ngOffset, t etsEndOffset);
  }

  /**
   * For each seg nt,   know what offset   beg ns at. T  funct on f nds t  t et  ds
   * for t se offsets.
   */
  pr vate vo d f llT et dsForSeg ntStarts(L st<Seg ntBu ld nfo> seg ntBu ld nfos)
      throws Earlyb rdStartupExcept on {
    KafkaConsu r<Long, Thr ftVers onedEvents> consu rForT et ds =
        earlyb rdKafkaConsu rsFactory.createKafkaConsu r("consu r_for_t et_ ds", 1);
    consu rForT et ds.ass gn( mmutableL st.of(t etTop c));

    // F nd f rst t et  ds for each seg nt.
    for (Seg ntBu ld nfo bu ld nfo : seg ntBu ld nfos) {
      long t etOffset = bu ld nfo.getT etStartOffset();
      Consu rRecords<Long, Thr ftVers onedEvents> records;
      try {
        consu rForT et ds.seek(t etTop c, t etOffset);
        records = consu rForT et ds.poll(Durat on.ofSeconds(1));
      } catch (Ap Except on kafkaAp Except on) {
        throw new WrappedKafkaAp Except on(kafkaAp Except on);
      }

       f (records.count() > 0) {
        Consu rRecord<Long, Thr ftVers onedEvents> recordAtOffset = records. erator().next();
         f (recordAtOffset.offset() != t etOffset) {
          LOG.error(Str ng.format("   re look ng for offset %,d. Found a record at offset %,d",
              t etOffset, recordAtOffset.offset()));
        }

        bu ld nfo.setStartT et d(recordAtOffset.value().get d());
      } else {
        throw new Earlyb rdStartupExcept on("D dn't get any t ets back for an offset");
      }
    }

    // C ck that so th ng   rd d dn't happen w re   end up w h seg nt  ds
    // wh ch are  n non- ncres ng order.
    // Goes from oldest to ne st.
    for ( nt   = 1;   < seg ntBu ld nfos.s ze();  ++) {
      long startT et d = seg ntBu ld nfos.get( ).getStartT et d();
      long prevStartT et d = seg ntBu ld nfos.get(  - 1).getStartT et d();
      Ver fy.ver fy(prevStartT et d < startT et d);
    }
  }

  /**
   * Generate t  offsets at wh ch t ets beg n and end for each seg nt that   want
   * to create.
   */
  pr vate ArrayL st<Seg ntBu ld nfo> makeSeg ntBu ld nfos(
       nt numSeg nts, KafkaOffsetPa r t etsOffsets) throws Earlyb rdStartupExcept on {
    ArrayL st<Seg ntBu ld nfo> seg ntBu ld nfos = new ArrayL st<>();

    //  f   have 3 seg nts, t  start ng t et offsets are:
    // end-3N, end-2N, end-N
     nt seg ntS ze = maxSeg ntS ze - lateT etBuffer;
    LOG. nfo("Seg nt s ze: {}", seg ntS ze);

    long t ets nStream = t etsOffsets.getEndOffset() - t etsOffsets.getBeg nOffset() + 1;
    double numBu ldableSeg nts = ((double) t ets nStream) / seg ntS ze;

    LOG. nfo("Number of seg nts   can bu ld: {}", numBu ldableSeg nts);

     nt numSeg ntsToBu ld = numSeg nts;
     nt numBu ldableSeg nts nt = ( nt) numBu ldableSeg nts;

     f (numBu ldableSeg nts nt < numSeg ntsToBu ld) {
      // T  can happen  f   get a low amount of t ets such that t  ~10 days of t ets stored  n
      // Kafka are not enough to bu ld t  spec f ed number of seg nts.
      LOG.warn("Bu ld ng {} seg nts  nstead of t  spec f ed {} seg nts because t re are not "
              + "enough t ets", numSeg ntsToBu ld, numSeg nts);
      BU LD NG_FEWER_THAN_SPEC F ED_SEGMENTS.assertFa led();
      numSeg ntsToBu ld = numBu ldableSeg nts nt;
    }

    for ( nt rew nd = numSeg ntsToBu ld; rew nd >= 1; rew nd--) {
      long t etStartOffset = (t etsOffsets.getEndOffset() + 1) - (rew nd * seg ntS ze);
      long t etEndOffset = t etStartOffset + seg ntS ze - 1;

       nt  ndex = seg ntBu ld nfos.s ze();

      seg ntBu ld nfos.add(new Seg ntBu ld nfo(
          t etStartOffset,
          t etEndOffset,
           ndex,
          rew nd == 1
      ));
    }

    Ver fy.ver fy(seg ntBu ld nfos.get(seg ntBu ld nfos.s ze() - 1)
        .getT etEndOffset() == t etsOffsets.getEndOffset());

    LOG. nfo("F ll ng start t et  ds ...");
    f llT et dsForSeg ntStarts(seg ntBu ld nfos);

    return seg ntBu ld nfos;
  }

  pr vate Seg nt nfo  ndexT etsAndUpdatesForSeg nt(
      Seg ntBu ld nfo seg ntBu ld nfo,
      ArrayL st<Seg ntBu ld nfo> seg ntBu ld nfos) throws Except on {

    PreOpt m zat onSeg nt ndexer preOpt m zat onSeg nt ndexer =
        new PreOpt m zat onSeg nt ndexer(
            seg ntBu ld nfo,
            seg ntBu ld nfos,
            t .seg ntManager,
            t .t etTop c,
            t .updateTop c,
            t .earlyb rdKafkaConsu rsFactory,
            t .lateT etBuffer
        );

    return preOpt m zat onSeg nt ndexer.run ndex ng();
  }
}
