package com.tw ter.search.earlyb rd.part  on;

 mport java. o. OExcept on;
 mport java.ut l. erator;

 mport scala.runt  .BoxedUn ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Stopwatch;
 mport com.google.common.base.Ver fy;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.conf g.Conf g;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common. tr cs.SearchT  r;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.common.ut l.GCUt l;
 mport com.tw ter.search.earlyb rd.Earlyb rdStatus;
 mport com.tw ter.search.earlyb rd.common.CaughtUpMon or;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd. ndex.OutOfOrderRealt  T et DMapper;
 mport com.tw ter.search.earlyb rd.querycac .QueryCac Manager;
 mport com.tw ter.search.earlyb rd.ut l.Coord natedEarlyb rdAct on nterface;
 mport com.tw ter.ut l.Awa ;
 mport com.tw ter.ut l.Durat on;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.T  outExcept on;

/**
 * T  class handles  ncom ng new T ets.    s respons ble for creat ng seg nts for t   ncom ng
 * T ets w n necessary, tr gger ng opt m zat on on those seg nts, and wr  ng T ets to t 
 * correct seg nt.
 */
publ c class T etCreateHandler {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(T etCreateHandler.class);

  publ c stat c f nal long LATE_TWEET_T ME_BUFFER_MS = Durat on.fromM nutes(1). nM ll seconds();

  pr vate stat c f nal Str ng STATS_PREF X = "t et_create_handler_";

  // To get a better  dea of wh ch of t se succeeded and so on, see stats  n Seg ntManager.
  pr vate  ndex ngResultCounts  ndex ngResultCounts;
  pr vate stat c f nal SearchRateCounter TWEETS_ N_WRONG_SEGMENT =
      SearchRateCounter.export(STATS_PREF X + "t ets_ n_wrong_seg nt");
  pr vate stat c f nal SearchRateCounter SEGMENTS_CLOSED_EARLY =
      SearchRateCounter.export(STATS_PREF X + "seg nts_closed_early");
  pr vate stat c f nal SearchRateCounter  NSERTED_ N_CURRENT_SEGMENT =
      SearchRateCounter.export(STATS_PREF X + " nserted_ n_current_seg nt");
  pr vate stat c f nal SearchRateCounter  NSERTED_ N_PREV OUS_SEGMENT =
      SearchRateCounter.export(STATS_PREF X + " nserted_ n_prev ous_seg nt");
  pr vate stat c f nal NewSeg ntStats NEW_SEGMENT_STATS = new NewSeg ntStats();
  pr vate stat c f nal SearchCounter CREATED_SEGMENTS =
      SearchCounter.export(STATS_PREF X + "created_seg nts");
  pr vate stat c f nal SearchRateCounter  NCOM NG_TWEETS =
          SearchRateCounter.export(STATS_PREF X + " ncom ng_t ets");
  pr vate stat c f nal SearchRateCounter  NDEX NG_SUCCESS =
          SearchRateCounter.export(STATS_PREF X + " ndex ng_success");
  pr vate stat c f nal SearchRateCounter  NDEX NG_FA LURE =
          SearchRateCounter.export(STATS_PREF X + " ndex ng_fa lure");

  // Var ous stats and logg ng around creat on of new seg nts, put  n t 
  // class so that t  code  s not watered down too much by t .
  pr vate stat c class NewSeg ntStats {
    pr vate stat c f nal Str ng NEW_SEGMENT_STATS_PREF X =
      STATS_PREF X + "new_seg nt_";

    pr vate stat c f nal SearchCounter START_NEW_AFTER_REACH NG_L M T =
        SearchCounter.export(NEW_SEGMENT_STATS_PREF X + "start_after_reach ng_l m ");
    pr vate stat c f nal SearchCounter START_NEW_AFTER_EXCEED NG_MAX_ D =
        SearchCounter.export(NEW_SEGMENT_STATS_PREF X + "start_after_exceed ng_max_ d");
    pr vate stat c f nal SearchCounter T MESL CE_SET_TO_CURRENT_ D =
        SearchCounter.export(NEW_SEGMENT_STATS_PREF X + "t  sl ce_set_to_current_ d");
    pr vate stat c f nal SearchCounter T MESL CE_SET_TO_MAX_ D =
        SearchCounter.export(NEW_SEGMENT_STATS_PREF X + "t  sl ce_set_to_max_ d");
    pr vate stat c f nal SearchLongGauge T MESPAN_BETWEEN_MAX_AND_CURRENT =
        SearchLongGauge.export(NEW_SEGMENT_STATS_PREF X + "t  span_bet en_ d_and_max");

    vo d recordCreateNewSeg nt() {
      CREATED_SEGMENTS. ncre nt();
    }

    vo d recordStartAfterReach ngT etsL m ( nt numDocs,  nt numDocsCutoff,
                                              nt maxSeg ntS ze,  nt lateT etBuffer) {
      START_NEW_AFTER_REACH NG_L M T. ncre nt();
      LOG. nfo(Str ng.format(
          "W ll create new seg nt: numDocs=%,d, numDocsCutoff=%,d"
              + " | maxSeg ntS ze=%,d, lateT etBuffer=%,d",
          numDocs, numDocsCutoff, maxSeg ntS ze, lateT etBuffer));
    }

    vo d recordStartAfterExceed ngLargestVal dT et d(long t et d, long largestVal dT et d) {
      START_NEW_AFTER_EXCEED NG_MAX_ D. ncre nt();
      LOG. nfo(Str ng.format(
          "W ll create new seg nt: t etDd=%,d, largestVal dT et D for seg nt=%,d",
          t et d, largestVal dT et d));
    }

    vo d recordSett ngT  sl ceToCurrentT et(long t et D) {
      T MESL CE_SET_TO_CURRENT_ D. ncre nt();
      LOG. nfo("Creat ng new seg nt: t et that tr ggered   has t  largest  d  've seen. "
          + "  d={}", t et D);
    }

    vo d recordSett ngT  sl ceToMaxT et d(long t et D, long maxT et D) {
      T MESL CE_SET_TO_MAX_ D. ncre nt();
      LOG. nfo("Creat ng new seg nt: t et that tr ggered   doesn't have t  largest  d"
          + "  've seen. t et d={}, maxT et d={}",
          t et D, maxT et D);
      long t  D fference =
          Snowflake dParser.getT  D fferenceBet enT et Ds(maxT et D, t et D);
      LOG. nfo("T   d fference bet en max seen and last seen: {} ms", t  D fference);
      T MESPAN_BETWEEN_MAX_AND_CURRENT.set(t  D fference);
    }

    vo d wrapNewSeg ntCreat on(long t et D, long maxT et D,
                                long currentSeg ntT  sl ceBoundary,
                                long largestVal dT et DForCurrentSeg nt) {
      long t  D fferenceStartToMax = Snowflake dParser.getT  D fferenceBet enT et Ds(
          largestVal dT et DForCurrentSeg nt,
          currentSeg ntT  sl ceBoundary);
      LOG. nfo("T   bet en t  sl ce boundary and largest val d t et  d: {} ms",
          t  D fferenceStartToMax);

      LOG. nfo("Created new seg nt: (t et d={}, maxT et d={}, maxT et d-t et d={} "
              + " | currentSeg ntT  sl ceBoundary={}, largestVal dT et DForSeg nt={})",
          t et D, maxT et D, maxT et D - t et D, currentSeg ntT  sl ceBoundary,
          largestVal dT et DForCurrentSeg nt);
    }
  }


  pr vate f nal Seg ntManager seg ntManager;
  pr vate f nal Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager;
  pr vate f nal  nt maxSeg ntS ze;
  pr vate f nal  nt lateT etBuffer;

  pr vate long maxT et D = Long.M N_VALUE;

  pr vate long largestVal dT et DForCurrentSeg nt;
  pr vate long currentSeg ntT  sl ceBoundary;
  pr vate Opt m z ngSeg ntWr er currentSeg nt;
  pr vate Opt m z ngSeg ntWr er prev ousSeg nt;
  pr vate f nal QueryCac Manager queryCac Manager;
  pr vate f nal Cr  calExcept onHandler cr  calExcept onHandler;
  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;
  pr vate f nal Coord natedEarlyb rdAct on nterface postOpt m zat onRebu ldsAct on;
  pr vate f nal Coord natedEarlyb rdAct on nterface gcAct on;
  pr vate f nal CaughtUpMon or  ndexCaughtUpMon or;
  pr vate f nal Opt m zat onAndFlush ngCoord nat onLock opt m zat onAndFlush ngCoord nat onLock;

  publ c T etCreateHandler(
      Seg ntManager seg ntManager,
      Search ndex ng tr cSet search ndex ng tr cSet,
      Cr  calExcept onHandler cr  calExcept onHandler,
      Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager,
      QueryCac Manager queryCac Manager,
      Coord natedEarlyb rdAct on nterface postOpt m zat onRebu ldsAct on,
      Coord natedEarlyb rdAct on nterface gcAct on,
       nt lateT etBuffer,
       nt maxSeg ntS ze,
      CaughtUpMon or  ndexCaughtUpMon or,
      Opt m zat onAndFlush ngCoord nat onLock opt m zat onAndFlush ngCoord nat onLock
  ) {
    t .seg ntManager = seg ntManager;
    t .cr  calExcept onHandler = cr  calExcept onHandler;
    t .mult Seg ntTermD ct onaryManager = mult Seg ntTermD ct onaryManager;
    t .queryCac Manager = queryCac Manager;
    t . ndex ngResultCounts = new  ndex ngResultCounts();
    t .search ndex ng tr cSet = search ndex ng tr cSet;
    t .postOpt m zat onRebu ldsAct on = postOpt m zat onRebu ldsAct on;
    t .gcAct on = gcAct on;
    t . ndexCaughtUpMon or =  ndexCaughtUpMon or;

    Precond  ons.c ckState(lateT etBuffer < maxSeg ntS ze);
    t .lateT etBuffer = lateT etBuffer;
    t .maxSeg ntS ze = maxSeg ntS ze;
    t .opt m zat onAndFlush ngCoord nat onLock = opt m zat onAndFlush ngCoord nat onLock;
  }

  vo d prepareAfterStart ngW h ndex(long max ndexedT et d) {
    LOG. nfo("Prepar ng after start ng w h an  ndex.");

     erator<Seg nt nfo> seg nt nfos erator =
        seg ntManager
            .getSeg nt nfos(Seg ntManager.F lter.All, Seg ntManager.Order.NEW_TO_OLD)
            . erator();

    // Setup t  last seg nt.
    Ver fy.ver fy(seg nt nfos erator.hasNext(), "at least one seg nt expected");
     Seg ntWr er lastWr er = seg ntManager.getSeg ntWr erFor D(
        seg nt nfos erator.next().getT  Sl ce D());
    Ver fy.ver fy(lastWr er != null);

    LOG. nfo("T etCreateHandler found last wr er: {}", lastWr er.getSeg nt nfo().toStr ng());
    t .currentSeg ntT  sl ceBoundary = lastWr er.getSeg nt nfo().getT  Sl ce D();
    t .largestVal dT et DForCurrentSeg nt =
        OutOfOrderRealt  T et DMapper.calculateMaxT et D(currentSeg ntT  sl ceBoundary);
    t .currentSeg nt = (Opt m z ngSeg ntWr er) lastWr er;

     f (max ndexedT et d == -1) {
      maxT et D = lastWr er.getSeg nt nfo().get ndexSeg nt().getMaxT et d();
      LOG. nfo("Max t et  d = {}", maxT et D);
    } else {
      // See SEARCH-31032
      maxT et D = max ndexedT et d;
    }

    //  f   have a prev ous seg nt that's not opt m zed, set   up too,   st ll need to p ck
    //   up for opt m zat on and   m ght st ll be able to add t ets to  .
     f (seg nt nfos erator.hasNext()) {
      Seg nt nfo prev ousSeg nt nfo = seg nt nfos erator.next();
       f (!prev ousSeg nt nfo. sOpt m zed()) {
         Seg ntWr er prev ousSeg ntWr er = seg ntManager.getSeg ntWr erFor D(
            prev ousSeg nt nfo.getT  Sl ce D());

         f (prev ousSeg ntWr er != null) {
          LOG. nfo("P cked prev ous seg nt");
          t .prev ousSeg nt = (Opt m z ngSeg ntWr er) prev ousSeg ntWr er;
        } else {
          // Should not happen.
          LOG.error("Not found prev ous seg nt wr er");
        }
      } else {
        LOG. nfo("Prev ous seg nt  nfo  s opt m zed");
      }
    } else {
      LOG. nfo("Prev ous seg nt  nfo not found,   only have one seg nt");
    }
  }

  pr vate vo d update ndexFreshness() {
    search ndex ng tr cSet.h g stStatus d.set(maxT et D);

    long t etT  stamp = Snowflake dParser.getT  stampFromT et d(
        search ndex ng tr cSet.h g stStatus d.get());
    search ndex ng tr cSet.fres stT etT  M ll s.set(t etT  stamp);
  }

  /**
   *  ndex a new TVE represent ng a T et create event.
   */
  publ c vo d handleT etCreate(Thr ftVers onedEvents tve) throws  OExcept on {
     NCOM NG_TWEETS. ncre nt();
    long  d = tve.get d();
    maxT et D = Math.max( d, maxT et D);

    update ndexFreshness();

    boolean shouldCreateNewSeg nt = false;

     f (currentSeg nt == null) {
      shouldCreateNewSeg nt = true;
      LOG. nfo("W ll create new seg nt: current seg nt  s null");
    } else {
       nt numDocs = currentSeg nt.getSeg nt nfo().get ndexSeg nt().getNumDocs();
       nt numDocsCutoff = maxSeg ntS ze - lateT etBuffer;
       f (numDocs >= numDocsCutoff) {
        NEW_SEGMENT_STATS.recordStartAfterReach ngT etsL m (numDocs, numDocsCutoff,
            maxSeg ntS ze, lateT etBuffer);
        shouldCreateNewSeg nt = true;
      } else  f ( d > largestVal dT et DForCurrentSeg nt) {
        NEW_SEGMENT_STATS.recordStartAfterExceed ngLargestVal dT et d( d,
            largestVal dT et DForCurrentSeg nt);
        shouldCreateNewSeg nt = true;
      }
    }

     f (shouldCreateNewSeg nt) {
      createNewSeg nt( d);
    }

     f (prev ousSeg nt != null) {
      //  nserts and so  updates can't be appl ed to an opt m zed seg nt, so   want to wa  at
      // least LATE_TWEET_T ME_BUFFER bet en w n   created t  new seg nt and w n   opt m ze
      // t  prev ous seg nt,  n case t re are late t ets.
      //   leave a large (150k, typ cally) buffer  n t  seg nt so that   don't have to close
      // t  prev ousSeg nt before LATE_TWEET_T ME_BUFFER has passed, but  f    ndex
      // lateT etBuffer T ets before opt m z ng, t n   must opt m ze,
      // so that   don't  nsert more than max seg nt s ze t ets  nto t  prev ous seg nt.
      long relat veT etAgeMs =
          Snowflake dParser.getT  D fferenceBet enT et Ds( d, currentSeg ntT  sl ceBoundary);

      boolean needToOpt m ze = false;
       nt numDocs = prev ousSeg nt.getSeg nt nfo().get ndexSeg nt().getNumDocs();
      Str ng prev ousSeg ntNa  = prev ousSeg nt.getSeg nt nfo().getSeg ntNa ();
       f (numDocs >= maxSeg ntS ze) {
        LOG. nfo(Str ng.format("Prev ous seg nt (%s) reac d maxSeg ntS ze, need to opt m ze  ."
            + " numDocs=%,d, maxSeg ntS ze=%,d", prev ousSeg ntNa , numDocs, maxSeg ntS ze));
        needToOpt m ze = true;
      } else  f (relat veT etAgeMs > LATE_TWEET_T ME_BUFFER_MS) {
        LOG. nfo(Str ng.format("Prev ous seg nt (%s)  s old enough,   can opt m ze  ."
            + " Got t et past t   buffer of %,d ms by: %,d ms", prev ousSeg ntNa ,
            LATE_TWEET_T ME_BUFFER_MS, relat veT etAgeMs - LATE_TWEET_T ME_BUFFER_MS));
        needToOpt m ze = true;
      }

       f (needToOpt m ze) {
        opt m zePrev ousSeg nt();
      }
    }

     Seg ntWr er seg ntWr er;
     f ( d >= currentSeg ntT  sl ceBoundary) {
       NSERTED_ N_CURRENT_SEGMENT. ncre nt();
      seg ntWr er = currentSeg nt;
    } else  f (prev ousSeg nt != null) {
       NSERTED_ N_PREV OUS_SEGMENT. ncre nt();
      seg ntWr er = prev ousSeg nt;
    } else {
      TWEETS_ N_WRONG_SEGMENT. ncre nt();
      LOG. nfo(" nsert ng TVE ({})  nto t  current seg nt ({}) even though   should have gone "
          + " n a prev ous seg nt.",  d, currentSeg ntT  sl ceBoundary);
      seg ntWr er = currentSeg nt;
    }

    SearchT  r t  r = search ndex ng tr cSet.statusStats.startNewT  r();
     Seg ntWr er.Result result = seg ntWr er. ndexThr ftVers onedEvents(tve);
    search ndex ng tr cSet.statusStats.stopT  rAnd ncre nt(t  r);

     f (result ==  Seg ntWr er.Result.SUCCESS) {
       NDEX NG_SUCCESS. ncre nt();
    } else {
       NDEX NG_FA LURE. ncre nt();
    }

     ndex ngResultCounts.countResult(result);
  }

  /**
   * Many tests need to ver fy behav or w h seg nts opt m zed & unopt m zed, so   need to expose
   * t .
   */
  @V s bleForTest ng
  publ c Future<Seg nt nfo> opt m zePrev ousSeg nt() {
    Str ng seg ntNa  = prev ousSeg nt.getSeg nt nfo().getSeg ntNa ();
    prev ousSeg nt.getSeg nt nfo().set ndex ng(false);
    LOG. nfo("Opt m z ng prev ous seg nt: {}", seg ntNa );
    seg ntManager.logState("Start ng opt m zat on for seg nt: " + seg ntNa );

    Future<Seg nt nfo> future = prev ousSeg nt
        .startOpt m zat on(gcAct on, opt m zat onAndFlush ngCoord nat onLock)
        .map(t ::postOpt m zat onSteps)
        .onFa lure(t -> {
          cr  calExcept onHandler.handle(t , t);
          return BoxedUn .UN T;
        });

    wa ForOpt m zat on f nTest(future);

    prev ousSeg nt = null;
    return future;
  }

  /**
   *  n tests,  's eas er  f w n a seg nt starts opt m z ng,   know that   w ll f n sh
   * opt m z ng. T  way   have no race cond  on w re  're surpr sed that so th ng that
   * started opt m z ng  s not ready.
   *
   *  n prod   don't have t  problem. Seg nts run for 10 h s and opt m zat on  s 20 m nutes
   * so t re's no need for extra synchron zat on.
   */
  pr vate vo d wa ForOpt m zat on f nTest(Future<Seg nt nfo> future) {
     f (Conf g.env ron nt sTest()) {
      try {
        Awa .ready(future);
        LOG. nfo("Opt m z ng  s done");
      } catch ( nterruptedExcept on | T  outExcept on ex) {
        LOG. nfo("Except on wh le opt m z ng", ex);
      }
    }
  }

  pr vate Seg nt nfo postOpt m zat onSteps(Seg nt nfo opt m zedSeg nt nfo) {
    seg ntManager.updateStats();
    // See SEARCH-32175
    opt m zedSeg nt nfo.setComplete(true);

    Str ng seg ntNa  = opt m zedSeg nt nfo.getSeg ntNa ();
    LOG. nfo("F n s d opt m zat on for seg nt: " + seg ntNa );
    seg ntManager.logState(
            "F n s d opt m zat on for seg nt: " + seg ntNa );

    /*
     * Bu ld ng t  mult  seg nt term d ct onary causes GC pauses. T  reason for t   s because
     *  's pretty b g (poss ble ~15GB). W n  's allocated,   have to copy a lot of data from
     * surv vor space to old gen. That causes several GC pauses. See SEARCH-33544
     *
     * GC pauses are  n general not fatal, but s nce all  nstances f n sh a seg nt at roughly t 
     * sa  t  , t y m ght happen at t  sa  t   and t n  's a problem.
     *
     * So  poss ble solut ons to t  problem would be to bu ld t  d ct onary  n so  data
     * structures that are pre-allocated or to bu ld only t  part for t  last seg nt, as
     * everyth ng else doesn't change. T se solut ons are a b  d ff cult to  mple nt and t 
     *  re  s an easy workaround.
     *
     * Note that   m ght f n sh opt m z ng a seg nt and t n   m ght take ~60+ m nutes unt l  's
     * a part cular Earlyb rd's turn to run t  code. T  effect of t   s go ng to be that  
     * are not go ng to use t  mult  seg nt d ct onary for t  last two seg nts, one of wh ch  s
     * st ll pretty small. That's not terr ble, s nce r ght before opt m zat on  're not us ng
     * t  d ct onary for t  last seg nt anyways, s nce  's st ll not opt m zed.
     */
    try {
      LOG. nfo("Acqu re coord nat on lock before beg nn ng post_opt m zat on_rebu lds act on.");
      opt m zat onAndFlush ngCoord nat onLock.lock();
      LOG. nfo("Successfully acqu red coord nat on lock for post_opt m zat on_rebu lds act on.");
      postOpt m zat onRebu ldsAct on.retryAct onUnt lRan(
          "post opt m zat on rebu lds", () -> {
            Stopwatch stopwatch = Stopwatch.createStarted();
            LOG. nfo("Start ng to bu ld mult  term d ct onary for {}", seg ntNa );
            boolean result = mult Seg ntTermD ct onaryManager.bu ldD ct onary();
            LOG. nfo("Done bu ld ng mult  term d ct onary for {}  n {}, result: {}",
                seg ntNa , stopwatch, result);
            queryCac Manager.rebu ldQueryCac sAfterSeg ntOpt m zat on(
                opt m zedSeg nt nfo);

            // T   s a ser al full GC and   defrag nts t   mory so th ngs can run smoothly
            // unt l t  next seg nt rolls. What   have observed  s that  f   don't do that
            // later on so  earlyb rds can have promot on fa lures on an old gen that hasn't
            // reac d t   n  at ng occupancy l m  and t se promot ons fa lures can tr gger a
            // long (1.5 m n) full GC. That usually happens because of frag ntat on  ssues.
            GCUt l.runGC();
            // Wa  for  ndex ng to catch up before rejo n ng t  serverset.   only need to do
            // t   f t  host has already f n s d startup.
             f (Earlyb rdStatus.hasStarted()) {
               ndexCaughtUpMon or.resetAndWa Unt lCaughtUp();
            }
          });
    } f nally {
      LOG. nfo("F n s d post_opt m zat on_rebu lds act on. Releas ng coord nat on lock.");
      opt m zat onAndFlush ngCoord nat onLock.unlock();
    }

    return opt m zedSeg nt nfo;
  }

  /**
   * Many tests rely on prec se seg nt boundar es, so   expose t  to allow t m to create a
   * part cular seg nt.
   */
  @V s bleForTest ng
  publ c vo d createNewSeg nt(long t et D) throws  OExcept on {
    NEW_SEGMENT_STATS.recordCreateNewSeg nt();

     f (prev ousSeg nt != null) {
      //   shouldn't have more than one unopt m zed seg nt, so  f   get to t  po nt and t 
      // prev ousSeg nt has not been opt m zed and set to null, start opt m z ng   before
      // creat ng t  next one. Note that t   s a   rd case and would only happen  f   get
      // T ets w h drast cally d fferent  Ds than   expect, or t re  s a large amount of t  
      // w re no T ets are created  n t  part  on.
      LOG.error("Creat ng new seg nt for T et {} w n t  prev ous seg nt {} was not sealed. "
          + "Current seg nt: {}. Docu nts: {}. largestVal dT et DForSeg nt: {}.",
          t et D,
          prev ousSeg nt.getSeg nt nfo().getT  Sl ce D(),
          currentSeg nt.getSeg nt nfo().getT  Sl ce D(),
          currentSeg nt.getSeg nt nfo().get ndexSeg nt().getNumDocs(),
          largestVal dT et DForCurrentSeg nt);
      opt m zePrev ousSeg nt();
      SEGMENTS_CLOSED_EARLY. ncre nt();
    }

    prev ousSeg nt = currentSeg nt;

    //   have two cases:
    //
    // Case 1:
    //  f t  greatest T et  D   have seen  s t et D, t n w n   want to create a new seg nt
    // w h that  D, so t  T et be ng processed goes  nto t  new seg nt.
    //
    // Case 2:
    //  f t  t et D  s b gger than t  max t et D, t n t   thod  s be ng called d rectly from
    // tests, so   d dn't update t  maxT et D, so   can create a new seg nt w h t  new
    // T et  D.
    //
    // Case 3:
    //  f  's not t  greatest T et  D   have seen, t n   don't want to create a
    // seg nt boundary that  s lo r than any T et  Ds  n t  current seg nt, because t n
    // so  t ets from t  prev ous seg nt would be  n t  wrong seg nt, so create a seg nt
    // that has a greater  D than any T ets that   have seen.
    //
    //   Example:
    //     -   have seen t ets 3, 10, 5, 6.
    //     -   now see t et 7 and   dec de  's t   to create a new seg nt.
    //     - T  new seg nt w ll start at t et 11.   can't start at t et 7, because
    //       t et 10 w ll be  n t  wrong seg nt.
    //     - T et 7 that   just saw w ll end up  n t  prev ous seg nt.
     f (maxT et D <= t et D) {
      currentSeg ntT  sl ceBoundary = t et D;
      NEW_SEGMENT_STATS.recordSett ngT  sl ceToCurrentT et(t et D);
    } else {
      currentSeg ntT  sl ceBoundary = maxT et D + 1;
      NEW_SEGMENT_STATS.recordSett ngT  sl ceToMaxT et d(t et D, maxT et D);
    }
    currentSeg nt = seg ntManager.createAndPutOpt m z ngSeg ntWr er(
        currentSeg ntT  sl ceBoundary);

    currentSeg nt.getSeg nt nfo().set ndex ng(true);

    largestVal dT et DForCurrentSeg nt =
        OutOfOrderRealt  T et DMapper.calculateMaxT et D(currentSeg ntT  sl ceBoundary);

    NEW_SEGMENT_STATS.wrapNewSeg ntCreat on(t et D, maxT et D,
        currentSeg ntT  sl ceBoundary, largestVal dT et DForCurrentSeg nt);

    seg ntManager.removeExcessSeg nts();
  }

  vo d logState() {
    LOG. nfo("T etCreateHandler:");
    LOG. nfo(Str ng.format("  t ets sent for  ndex ng: %,d",
         ndex ngResultCounts.get ndex ngCalls()));
    LOG. nfo(Str ng.format("  non-retr able fa lure: %,d",
         ndex ngResultCounts.getFa lureNotRetr able()));
    LOG. nfo(Str ng.format("  retr able fa lure: %,d",
         ndex ngResultCounts.getFa lureRetr able()));
    LOG. nfo(Str ng.format("  successfully  ndexed: %,d",
         ndex ngResultCounts.get ndex ngSuccess()));
    LOG. nfo(Str ng.format("  t ets  n wrong seg nt: %,d", TWEETS_ N_WRONG_SEGMENT.getCount()));
    LOG. nfo(Str ng.format("  seg nts closed early: %,d", SEGMENTS_CLOSED_EARLY.getCount()));
  }
}
