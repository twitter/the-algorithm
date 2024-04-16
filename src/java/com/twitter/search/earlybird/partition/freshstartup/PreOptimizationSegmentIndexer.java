package com.tw ter.search.earlyb rd.part  on.freshstartup;

 mport java. o. OExcept on;
 mport java.t  .Durat on;
 mport java.ut l.ArrayL st;
 mport java.ut l.Opt onal;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Stopwatch;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect. mmutableMap;

 mport org.apac .kafka.cl ents.consu r.Consu rRecord;
 mport org.apac .kafka.cl ents.consu r.Consu rRecords;
 mport org.apac .kafka.cl ents.consu r.KafkaConsu r;
 mport org.apac .kafka.cl ents.consu r.OffsetAndT  stamp;
 mport org.apac .kafka.common.Top cPart  on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.earlyb rd.factory.Earlyb rdKafkaConsu rsFactory;
 mport com.tw ter.search.earlyb rd.part  on. ndex ngResultCounts;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntWr er;

/**
 * Respons ble for  ndex ng t  t ets and updates that need to be appl ed to a s ngle seg nt
 * before   gets opt m zed and t n opt m z ng t  seg nt (except  f  's t  last one).
 *
 * After that, no more t ets are added to t  seg nt and t  rest of t  updates are added
 *  n PostOpt m zat onUpdates ndexer.
 */
class PreOpt m zat onSeg nt ndexer {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(PreOpt m zat onSeg nt ndexer.class);

  pr vate Seg ntBu ld nfo seg ntBu ld nfo;
  pr vate f nal ArrayL st<Seg ntBu ld nfo> seg ntBu ld nfos;
  pr vate Seg ntManager seg ntManager;
  pr vate f nal Top cPart  on t etTop c;
  pr vate f nal Top cPart  on updateTop c;
  pr vate f nal Earlyb rdKafkaConsu rsFactory earlyb rdKafkaConsu rsFactory;
  pr vate f nal long lateT etBuffer;

  publ c PreOpt m zat onSeg nt ndexer(
      Seg ntBu ld nfo seg ntBu ld nfo,
      ArrayL st<Seg ntBu ld nfo> seg ntBu ld nfos,
      Seg ntManager seg ntManager,
      Top cPart  on t etTop c,
      Top cPart  on updateTop c,
      Earlyb rdKafkaConsu rsFactory earlyb rdKafkaConsu rsFactory,
      long lateT etBuffer) {
    t .seg ntBu ld nfo = seg ntBu ld nfo;
    t .seg ntBu ld nfos = seg ntBu ld nfos;
    t .seg ntManager = seg ntManager;
    t .t etTop c = t etTop c;
    t .updateTop c = updateTop c;
    t .earlyb rdKafkaConsu rsFactory = earlyb rdKafkaConsu rsFactory;
    t .lateT etBuffer = lateT etBuffer;
  }

  Seg nt nfo run ndex ng() throws  OExcept on {
    LOG. nfo(Str ng.format("Start ng seg nt bu ld ng for seg nt %d. "
            + "T et offset range [ %,d, %,d ]",
        seg ntBu ld nfo.get ndex(),
        seg ntBu ld nfo.getT etStartOffset(),
        seg ntBu ld nfo.getT etEndOffset()));

    Opt onal<Long> f rstT et d nNextSeg nt = Opt onal.empty();
     nt  ndex = seg ntBu ld nfo.get ndex();
     f ( ndex + 1 < seg ntBu ld nfos.s ze()) {
      f rstT et d nNextSeg nt = Opt onal.of(
          seg ntBu ld nfos.get( ndex + 1).getStartT et d());
    }

    //  ndex t ets.
    Seg ntT ets ndex ngResult t et ndex ngResult =  ndexSeg ntT etsFromStream(
        t etTop c,
        Str ng.format("t et_consu r_for_seg nt_%d", seg ntBu ld nfo.get ndex()),
        f rstT et d nNextSeg nt
    );

    //  ndex updates.
    KafkaOffsetPa r updates ndex ngOffsets = f ndUpdateStreamOffsetRange(t et ndex ngResult);

    Str ng updatesConsu rCl ent d =
        Str ng.format("update_consu r_for_seg nt_%d", seg ntBu ld nfo.get ndex());

    LOG. nfo(Str ng.format("Consu r: %s :: T ets start t  : %d, end t  : %d ==> "
            + "Updates start offset: %,d, end offset: %,d",
        updatesConsu rCl ent d,
        t et ndex ngResult.getM nRecordT  stampMs(),
        t et ndex ngResult.getMaxRecordT  stampMs(),
        updates ndex ngOffsets.getBeg nOffset(),
        updates ndex ngOffsets.getEndOffset()));

     ndexUpdatesFromStream(
        updateTop c,
        updatesConsu rCl ent d,
        updates ndex ngOffsets.getBeg nOffset(),
        updates ndex ngOffsets.getEndOffset(),
        t et ndex ngResult.getSeg ntWr er()
    );

     f (seg ntBu ld nfo. sLastSeg nt()) {
      /*
       *   don't opt m ze t  last seg nt for a few reasons:
       *
       * 1.   m ght have t ets com ng next  n t  stream, wh ch are supposed to end
       *    up  n t  seg nt.
       *
       * 2.   m ght have updates com ng next  n t  stream, wh ch need to be appl ed to
       *    t  seg nt before  's opt m zed.
       *
       * So t  seg nt  s kept unopt m zed and later   take care of sett ng up th ngs
       * so that Part  onWr er and t  t et create/update handlers can start correctly.
       */
      LOG. nfo("Not opt m z ng t  last seg nt ({})", seg ntBu ld nfo.get ndex());
    } else {
      Stopwatch opt m zat onStopwatch = Stopwatch.createStarted();
      try {
        LOG. nfo("Start ng to opt m ze seg nt: {}", seg ntBu ld nfo.get ndex());
        t et ndex ngResult.getSeg ntWr er().getSeg nt nfo()
            .get ndexSeg nt().opt m ze ndexes();
      } f nally {
        LOG. nfo("Opt m zat on of seg nt {} f n s d  n {}.",
            seg ntBu ld nfo.get ndex(), opt m zat onStopwatch);
      }
    }

    seg ntBu ld nfo.setUpdateKafkaOffsetPa r(updates ndex ngOffsets);
    seg ntBu ld nfo.setMax ndexedT et d(t et ndex ngResult.getMax ndexedT et d());
    seg ntBu ld nfo.setSeg ntWr er(t et ndex ngResult.getSeg ntWr er());

    return t et ndex ngResult.getSeg ntWr er().getSeg nt nfo();
  }

  pr vate Seg ntT ets ndex ngResult  ndexSeg ntT etsFromStream(
      Top cPart  on top cPart  on,
      Str ng consu rCl ent d,
      Opt onal<Long> f rstT et d nNextSeg nt) throws  OExcept on {
    long startOffset = seg ntBu ld nfo.getT etStartOffset();
    long endOffset = seg ntBu ld nfo.getT etEndOffset();
    long marg nS ze = lateT etBuffer / 2;

    boolean  sF rstSeg nt = seg ntBu ld nfo.get ndex() == 0;

    long startRead ngAtOffset = startOffset;
     f (! sF rstSeg nt) {
      startRead ngAtOffset -= marg nS ze;
    } else {
      LOG. nfo("Not mov ng start offset backwards for seg nt {}.", seg ntBu ld nfo.get ndex());
    }

    long endRead ngAtOffset = endOffset;
     f (f rstT et d nNextSeg nt. sPresent()) {
      endRead ngAtOffset += marg nS ze;
    } else {
      LOG. nfo("Not mov ng end offset forwards for seg nt {}.", seg ntBu ld nfo.get ndex());
    }

    KafkaConsu r<Long, Thr ftVers onedEvents> t etsKafkaConsu r =
        makeKafkaConsu rFor ndex ng(consu rCl ent d,
            top cPart  on, startRead ngAtOffset);

    boolean done = false;
    long m n ndexedT  stampMs = Long.MAX_VALUE;
    long max ndexedT  stampMs = Long.M N_VALUE;
     nt  ndexedEvents = 0;

    Stopwatch stopwatch = Stopwatch.createStarted();

    LOG. nfo("Creat ng seg nt wr er for t  sl ce  D {}.", seg ntBu ld nfo.getStartT et d());
    Seg ntWr er seg ntWr er = seg ntManager.createSeg ntWr er(
        seg ntBu ld nfo.getStartT et d());

    /*
     *   don't have a guarantee that t ets co   n sorted order, so w n  're bu ld ng seg nt
     * X',   try to p ck so  t ets from t  prev ous and next ranges  're go ng to  ndex.
     *
     *   also  gnore t ets  n t  beg nn ng and t  end of   t ets range, wh ch are p cked
     * by t  prev ous or follow ng seg nt.
     *
     *   Seg nt X        Seg nt X'                              Seg nt X''
     * -------------- o ----------------------------------------- o ---------------
     *        [~~~~~] ^ [~~~~~]                           [~~~~~] | [~~~~~]
     *           |    |    |                                 |    |    |
     *  front marg n  |    front padd ng (s ze K)   back padd ng  |   back marg n
     *                |                                           |
     *                seg nt boundary at offset B' (1)           B''
     *
     * (1) T   s at a predeterm ned t et offset / t et  d.
     *
     * For seg nt X',   start to read t ets at offset B'-K and f n sh read ng
     * t ets at offset B''+K. K  s a constant.
     *
     * For m ddle seg nts X'
     * ======================
     *   move so  t ets from t  front marg n and back marg n  nto seg nt X'.
     * So  t ets from t  front and back padd ng are  gnored, as t y are moved
     *  nto t  prev ous and next seg nts.
     *
     * For t  f rst seg nt
     * =====================
     * No front marg n, no front padd ng.   just read from t  beg nn ng offset
     * and  nsert everyth ng.
     *
     * For t  last seg nt
     * ====================
     * No back marg n, no back padd ng.   just read unt l t  end.
     */

    Sk ppedP ckedCounter frontMarg n = new Sk ppedP ckedCounter("front marg n");
    Sk ppedP ckedCounter backMarg n = new Sk ppedP ckedCounter("back marg n");
    Sk ppedP ckedCounter frontPadd ng = new Sk ppedP ckedCounter("front padd ng");
    Sk ppedP ckedCounter backPadd ng = new Sk ppedP ckedCounter("back padd ng");
    Sk ppedP ckedCounter regular = new Sk ppedP ckedCounter("regular");
     nt totalRead = 0;
    long max ndexedT et d = -1;

    Stopwatch pollT  r = Stopwatch.createUnstarted();
    Stopwatch  ndexT  r = Stopwatch.createUnstarted();

    do {
      // T  can cause an except on, See P33896
      pollT  r.start();
      Consu rRecords<Long, Thr ftVers onedEvents> records =
          t etsKafkaConsu r.poll(Durat on.ofSeconds(1));
      pollT  r.stop();

       ndexT  r.start();
      for (Consu rRecord<Long, Thr ftVers onedEvents> record : records) {
        // Done read ng?
         f (record.offset() >= endRead ngAtOffset) {
          done = true;
        }

        Thr ftVers onedEvents tve = record.value();
        boolean  ndexT et = false;
        Sk ppedP ckedCounter sk ppedP ckedCounter;

         f (record.offset() < seg ntBu ld nfo.getT etStartOffset()) {
          // Front marg n.
          sk ppedP ckedCounter = frontMarg n;
           f (tve.get d() > seg ntBu ld nfo.getStartT et d()) {
             ndexT et = true;
          }
        } else  f (record.offset() > seg ntBu ld nfo.getT etEndOffset()) {
          // Back marg n.
          sk ppedP ckedCounter = backMarg n;
           f (f rstT et d nNextSeg nt. sPresent()
              && tve.get d() < f rstT et d nNextSeg nt.get()) {
             ndexT et = true;
          }
        } else  f (record.offset() < seg ntBu ld nfo.getT etStartOffset() + marg nS ze) {
          // Front padd ng.
          sk ppedP ckedCounter = frontPadd ng;
           f (tve.get d() >= seg ntBu ld nfo.getStartT et d()) {
             ndexT et = true;
          }
        } else  f (f rstT et d nNextSeg nt. sPresent()
            && record.offset() > seg ntBu ld nfo.getT etEndOffset() - marg nS ze) {
          // Back padd ng.
          sk ppedP ckedCounter = backPadd ng;
           f (tve.get d() < f rstT et d nNextSeg nt.get()) {
             ndexT et = true;
          }
        } else {
          sk ppedP ckedCounter = regular;
          // T se   just p ck. A t et that ca  very late can end up  n t  wrong
          // seg nt, but  's better for   to be present  n a seg nt than dropped.
           ndexT et = true;
        }

         f ( ndexT et) {
          sk ppedP ckedCounter. ncre ntP cked();
          seg ntWr er. ndexThr ftVers onedEvents(tve);
          max ndexedT et d = Math.max(max ndexedT et d, tve.get d());
           ndexedEvents++;

          // Note that records don't necessar ly have  ncreas ng t  stamps.
          // Why? T  t  stamps whatever t  stamp   p cked w n creat ng t  record
          //  n  ngesters and t re are many  ngesters.
          m n ndexedT  stampMs = Math.m n(m n ndexedT  stampMs, record.t  stamp());
          max ndexedT  stampMs = Math.max(max ndexedT  stampMs, record.t  stamp());
        } else {
          sk ppedP ckedCounter. ncre ntSk pped();
        }
        totalRead++;

         f (record.offset() >= endRead ngAtOffset) {
          break;
        }
      }
       ndexT  r.stop();
    } wh le (!done);

    t etsKafkaConsu r.close();

    Seg ntT ets ndex ngResult result = new Seg ntT ets ndex ngResult(
        m n ndexedT  stampMs, max ndexedT  stampMs, max ndexedT et d, seg ntWr er);

    LOG. nfo("F n s d  ndex ng {} t ets for {}  n {}. Read {} t ets. Result: {}."
            + " T   poll ng: {}, T    ndex ng: {}.",
         ndexedEvents, consu rCl ent d, stopwatch, totalRead, result,
        pollT  r,  ndexT  r);

    //  n normal cond  ons, expect to p ck just a few  n front and  n t  back.
    LOG. nfo("Sk ppedP cked ({}) -- {}, {}, {}, {}, {}",
        consu rCl ent d, frontMarg n, frontPadd ng, backPadd ng, backMarg n, regular);

    return result;
  }


  /**
   * After  ndex ng all t  t ets for a seg nt,  ndex updates that need to be appl ed before
   * t  seg nt  s opt m zed.
   *
   * T   s requ red because so  updates (URL updates, cards and Na d Ent  es) can only be
   * appl ed to an unopt m zed seg nt. Luck ly, all of t se updates should arr ve close to w n
   * t  T et  s created.
   */
  pr vate KafkaOffsetPa r f ndUpdateStreamOffsetRange(
      Seg ntT ets ndex ngResult t ets ndex ngResult) {
    KafkaConsu r<Long, Thr ftVers onedEvents> offsetsConsu r =
        earlyb rdKafkaConsu rsFactory.createKafkaConsu r(
            "consu r_for_update_offsets_" + seg ntBu ld nfo.get ndex());

    // Start one m nute before t  f rst  ndexed t et. One m nute  s excess ve, but
    //   need to start a b  earl er  n case t  f rst t et    ndexed ca   n
    // later than so  of  s updates.
    long updatesStartOffset = offsetForT  (offsetsConsu r, updateTop c,
        t ets ndex ngResult.getM nRecordT  stampMs() - Durat on.ofM nutes(1).toM ll s());

    // Two cases:
    //
    // 1.  f  're not  ndex ng t  last seg nt, end 10 m nutes after t  last t et. So for
    //    example  f   resolve an url  n a t et 3 m nutes after t  t et  s publ s d,
    //     'll apply that update before t  seg nt  s opt m zed. 10 m nutes  s a b  too
    //    much, but that doesn't matter a whole lot, s nce  're  ndex ng about ~10 h s of
    //    updates.
    //
    // 2.  f  're  ndex ng t  last seg nt, end a b  before t  last  ndexed t et.   m ght
    //    have  ncom ng t ets that are a b  late.  n fresh startup,   don't have a  chan sm
    //    to store t se t ets to be appl ed w n t  t et arr ves, as  n T etUpdateHandler,
    //    so just stop a b  earl er and let T etCreateHandler and T etUpdateHandler deal w h
    //    that.
    long m ll sAdjust;
     f (seg ntBu ld nfo.get ndex() == seg ntBu ld nfos.s ze() - 1) {
      m ll sAdjust = -Durat on.ofM nutes(1).toM ll s();
    } else {
      m ll sAdjust = Durat on.ofM nutes(10).toM ll s();
    }
    long updatesEndOffset = offsetForT  (offsetsConsu r, updateTop c,
        t ets ndex ngResult.getMaxRecordT  stampMs() + m ll sAdjust);

    offsetsConsu r.close();

    return new KafkaOffsetPa r(updatesStartOffset, updatesEndOffset);
  }

  /**
   * Get t  earl est offset w h a t  stamp >= $t  stamp.
   *
   * T  guarantee   get  s that  f   start read ng from  re on,   w ll get
   * every s ngle  ssage that ca   n w h a t  stamp >= $t  stamp.
   */
  pr vate long offsetForT  (KafkaConsu r<Long, Thr ftVers onedEvents> kafkaConsu r,
                             Top cPart  on part  on,
                             long t  stamp) {
    Precond  ons.c ckNotNull(kafkaConsu r);
    Precond  ons.c ckNotNull(part  on);

    OffsetAndT  stamp offsetAndT  stamp = kafkaConsu r
        .offsetsForT  s( mmutableMap.of(part  on, t  stamp))
        .get(part  on);
     f (offsetAndT  stamp == null) {
      return -1;
    } else {
      return offsetAndT  stamp.offset();
    }
  }

  pr vate vo d  ndexUpdatesFromStream(
      Top cPart  on top cPart  on,
      Str ng consu rCl ent d,
      long startOffset,
      long endOffset,
      Seg ntWr er seg ntWr er) throws  OExcept on {
    KafkaConsu r<Long, Thr ftVers onedEvents> kafkaConsu r =
        makeKafkaConsu rFor ndex ng(consu rCl ent d, top cPart  on, startOffset);

    //  ndex TVEs.
    boolean done = false;

    Stopwatch pollT  r = Stopwatch.createUnstarted();
    Stopwatch  ndexT  r = Stopwatch.createUnstarted();

    Sk ppedP ckedCounter updatesSk ppedP cked = new Sk ppedP ckedCounter("strea d_updates");
     ndex ngResultCounts  ndex ngResultCounts = new  ndex ngResultCounts();

    long seg ntT  sl ce d = seg ntWr er.getSeg nt nfo().getT  Sl ce D();

    Stopwatch totalT   = Stopwatch.createStarted();

    do {
      pollT  r.start();
      Consu rRecords<Long, Thr ftVers onedEvents> records =
          kafkaConsu r.poll(Durat on.ofSeconds(1));
      pollT  r.stop();

       ndexT  r.start();
      for (Consu rRecord<Long, Thr ftVers onedEvents> record : records) {
         f (record.value().get d() < seg ntT  sl ce d) {
          // Doesn't apply to t  seg nt, can be sk pped  nstead of sk pp ng  
          //  ns de t  more costly seg ntWr er. ndexThr ftVers onedEvents call.
          updatesSk ppedP cked. ncre ntSk pped();
        } else {
           f (record.offset() >= endOffset) {
            done = true;
          }

          updatesSk ppedP cked. ncre ntP cked();
           ndex ngResultCounts.countResult(
              seg ntWr er. ndexThr ftVers onedEvents(record.value()));
        }

         f (record.offset() >= endOffset) {
          break;
        }
      }
       ndexT  r.stop();
    } wh le (!done);

    // Note that t re'll be a decent amount of fa led retryable updates. S nce    ndex
    // updates  n a range that's a b  w der, t y can't be appl ed  re.
    LOG. nfo("Cl ent: {}, F n s d  ndex ng updates: {}. "
            + "T  s -- total: {}. poll ng: {},  ndex ng: {}.  ndex ng result counts: {}",
        consu rCl ent d, updatesSk ppedP cked,
        totalT  , pollT  r,  ndexT  r,  ndex ngResultCounts);
  }

  /**
   * Make a consu r that reads from a s ngle part  on, start ng at so  offset.
   */
  pr vate KafkaConsu r<Long, Thr ftVers onedEvents> makeKafkaConsu rFor ndex ng(
      Str ng consu rCl ent d,
      Top cPart  on top cPart  on,
      long offset) {
    KafkaConsu r<Long, Thr ftVers onedEvents> kafkaConsu r =
        earlyb rdKafkaConsu rsFactory.createKafkaConsu r(consu rCl ent d);
    kafkaConsu r.ass gn( mmutableL st.of(top cPart  on));
    kafkaConsu r.seek(top cPart  on, offset);
    LOG. nfo(" ndex ng TVEs. Kafka consu r: {}", consu rCl ent d);
    return kafkaConsu r;
  }
}
