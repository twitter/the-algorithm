package com.tw ter.search.earlyb rd.part  on;

 mport java. o. OExcept on;
 mport java.ut l.concurrent.ConcurrentL nkedQueue;
 mport java.ut l.concurrent.atom c.Atom cReference;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Stopwatch;
 mport com.google.common.base.Ver fy;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common.ut l.GCUt l;
 mport com.tw ter.search.earlyb rd.Earlyb rdStatus;
 mport com.tw ter.search.earlyb rd.common.CaughtUpMon or;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdSeg nt;
 mport com.tw ter.search.earlyb rd.ut l.Coord natedEarlyb rdAct on nterface;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.Prom se;

/**
 * T  class opt m zes a seg nt w hout block ng reads or wr es.
 *
 *  n steady state operat on ( ndex ng or Opt m zed),   delegates operat ons d rectly to a
 * Seg ntWr er.
 *
 * Opt m zat on  s naturally a copy ng operat on --   don't need to mutate anyth ng  nternally.
 *   need to be able to apply updates to t  unopt m zed seg nt wh le   are creat ng
 * t  opt m zed seg nt.   also need to be able to apply t se updates to t  opt m zed seg nt,
 * but   can't apply updates wh le a seg nt  s be ng opt m zed, because docu nt  Ds w ll be
 * chang ng  nternally and post ng l sts could be any state. To deal w h t ,   queue updates
 * that occur dur ng opt m zat on, and t n apply t m as t  last step of opt m zat on. At that
 * po nt, t  seg nt w ll be opt m zed and up to date, so   can swap t  unopt m zed seg nt for
 * t  opt m zed one.
 */
publ c class Opt m z ngSeg ntWr er  mple nts  Seg ntWr er {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Opt m z ngSeg ntWr er.class);

  pr vate f nal Atom cReference<State> state = new Atom cReference<>(State. ndex ng);
  pr vate f nal ConcurrentL nkedQueue<Thr ftVers onedEvents> queuedEvents =
      new ConcurrentL nkedQueue<>();

  pr vate f nal Cr  calExcept onHandler cr  calExcept onHandler;
  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;
  pr vate f nal Str ng seg ntNa ;
  pr vate f nal Prom se<Seg nt nfo> opt m zat onProm se = new Prom se<>();

  //   use t  lock to ensure that t  opt m z ng thread and t  wr er thread do not attempt
  // to call  ndexThr ftVers onedEvents on t  underly ng wr er s multaneously.
  pr vate f nal Object lock = new Object();
  // T  reference to t  current wr er. Protected by lock.
  pr vate f nal Atom cReference<Seg ntWr er> seg ntWr erReference;

  pr vate f nal CaughtUpMon or  ndexCaughtUpMon or;

  /**
   * T  state flow:
   *  ndex ng -> Opt m z ng ->
   * ONE OF:
   * - Opt m zed
   * - Fa ledToOpt m ze
   */
  @V s bleForTest ng
  enum State {
     ndex ng,
    Opt m z ng,
    Fa ledToOpt m ze,
    Opt m zed,
  }

  publ c Opt m z ngSeg ntWr er(
      Seg ntWr er seg ntWr er,
      Cr  calExcept onHandler cr  calExcept onHandler,
      Search ndex ng tr cSet search ndex ng tr cSet,
      CaughtUpMon or  ndexCaughtUpMon or
  ) {
    Precond  ons.c ckState(!seg ntWr er.getSeg nt nfo(). sOpt m zed());
    seg ntWr erReference = new Atom cReference<>(seg ntWr er);

    t .cr  calExcept onHandler = cr  calExcept onHandler;
    t .search ndex ng tr cSet = search ndex ng tr cSet;
    t .seg ntNa  = seg ntWr er.getSeg nt nfo().getSeg ntNa ();
    t . ndexCaughtUpMon or =  ndexCaughtUpMon or;
  }

  /**
   * Start opt m z ng t  seg nt  n t  background. Returns a Future that w ll complete w n
   * t  opt m zat on  s complete.
   * Acqu res t  opt m zat onAndFlush ngCoord nat onLock before attempt ng to opt m ze.
   */
  publ c Future<Seg nt nfo> startOpt m zat on(
      Coord natedEarlyb rdAct on nterface gcAct on,
      Opt m zat onAndFlush ngCoord nat onLock opt m zat onAndFlush ngCoord nat onLock) {
    new Thread(() -> {
      // Acqu re lock to ensure that flush ng  s not  n progress.  f t  lock  s not ava lable,
      // t n wa  unt l    s.
      LOG. nfo("Acqu re coord nat on lock before beg nn ng gc_before_opt m zat on act on.");
      try {
        opt m zat onAndFlush ngCoord nat onLock.lock();
        LOG. nfo("Successfully acqu red coord nat on lock for gc_before_opt m zat on act on.");
        gcAct on.retryAct onUnt lRan("gc before opt m zat on", () -> {
          LOG. nfo("Run GC before opt m zat on");
          GCUt l.runGC();
          // Wa  for  ndex ng to catch up before gcAct on rejo ns t  serverset.   only need to do
          // t   f t  host has already f n s d startup.
           f (Earlyb rdStatus.hasStarted()) {
             ndexCaughtUpMon or.resetAndWa Unt lCaughtUp();
          }
        });
      } f nally {
        LOG. nfo("F n s d gc_before_opt m zat on act on. "
            + "Releas ng coord nat on lock and beg nn ng opt m zat on.");
        opt m zat onAndFlush ngCoord nat onLock.unlock();
      }

      trans  on(State. ndex ng, State.Opt m z ng);

      Seg nt nfo unopt m zedSeg nt nfo = null;
      try {
        unopt m zedSeg nt nfo = seg ntWr erReference.get().getSeg nt nfo();
        Precond  ons.c ckState(!unopt m zedSeg nt nfo. sOpt m zed());

        Stopwatch stopwatch = Stopwatch.createStarted();
        LOG. nfo("Started opt m z ng seg nt data {}.", seg ntNa );
        Earlyb rdSeg nt opt m zedSeg nt =
            unopt m zedSeg nt nfo.get ndexSeg nt().makeOpt m zedSeg nt();
        LOG. nfo("F n s d opt m z ng seg nt data {}  n {}.", seg ntNa , stopwatch);

        Seg nt nfo newSeg nt nfo = unopt m zedSeg nt nfo
            .copyW hEarlyb rdSeg nt(opt m zedSeg nt);

        Seg ntWr er opt m zedWr er =
            new Seg ntWr er(newSeg nt nfo, search ndex ng tr cSet.updateFreshness);
        Ver fy.ver fy(opt m zedWr er.getSeg nt nfo(). sOpt m zed());

        //   want to apply all updates to t  new seg nt tw ce, because t  f rst call may apply
        // many thousands of updates and take a wh le to complete.
        applyAllPend ngUpdates(opt m zedWr er);

        //   try to do as l tle as poss ble wh le hold ng t  lock, so t  wr er can cont nue
        // to make progress. F rst   apply all t  updates that have been queued up before  
        // grabbed t  lock, t n   need to swap t  new wr er for t  old one.
        synchron zed (lock) {
          applyAllPend ngUpdates(opt m zedWr er);
          seg ntWr erReference.getAndSet(opt m zedWr er);
          trans  on(State.Opt m z ng, State.Opt m zed);
        }

         f (!unopt m zedSeg nt nfo. sEnabled()) {
          LOG. nfo("D sabl ng seg nt: {}", unopt m zedSeg nt nfo.getSeg ntNa ());
          newSeg nt nfo.set sEnabled(false);
        }

        opt m zat onProm se.setValue(newSeg nt nfo);
      } catch (Throwable e) {
         f (unopt m zedSeg nt nfo != null) {
          unopt m zedSeg nt nfo.setFa ledOpt m ze();
        }

        trans  on(State.Opt m z ng, State.Fa ledToOpt m ze);
        opt m zat onProm se.setExcept on(e);
      }
    }, "opt m z ng-seg nt-wr er").start();

    return opt m zat onProm se;
  }

  pr vate vo d applyAllPend ngUpdates(Seg ntWr er seg ntWr er) throws  OExcept on {
    LOG. nfo("Apply ng {} queued updates to seg nt {}.", queuedEvents.s ze(), seg ntNa );
    // More events can be enqueued wh le t   thod  s runn ng, so   track t  total appl ed too.
    long eventCount = 0;
    Stopwatch stopwatch = Stopwatch.createStarted();
    Thr ftVers onedEvents update;
    wh le ((update = queuedEvents.poll()) != null) {
      seg ntWr er. ndexThr ftVers onedEvents(update);
      eventCount++;
    }
    LOG. nfo("Appl ed {} queued updates to seg nt {}  n {}.",
        eventCount, seg ntNa , stopwatch);
  }

  @Overr de
  publ c Result  ndexThr ftVers onedEvents(Thr ftVers onedEvents tve) throws  OExcept on {
    synchron zed (lock) {
       f (state.get() == State.Opt m z ng) {
        queuedEvents.add(tve);
      }
      return seg ntWr erReference.get(). ndexThr ftVers onedEvents(tve);
    }
  }

  @Overr de
  publ c Seg nt nfo getSeg nt nfo() {
    return seg ntWr erReference.get().getSeg nt nfo();
  }

  pr vate vo d trans  on(State from, State to) {
    Precond  ons.c ckState(state.compareAndSet(from, to));
    LOG. nfo("Trans  oned from {} to {} for seg nt {}.", from, to, seg ntNa );
  }

  @V s bleForTest ng
  publ c Future<Seg nt nfo> getOpt m zat onProm se() {
    return opt m zat onProm se;
  }
}
