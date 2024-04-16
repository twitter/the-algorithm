package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.ut l.Arrays;
 mport java.ut l.Collect on;
 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.Opt onal;
 mport java.ut l.concurrent.ConcurrentMap;
 mport java.ut l.concurrent.T  Un ;

 mport javax.nam ng.Nam ngExcept on;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;

 mport org.apac .commons.lang.Str ngUt ls;
 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.stage. nstru ntedBaseStage;

 mport com.tw ter.common. tr cs. tr cs;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.debug.DebugEventAccumulator;
 mport com.tw ter.search.common.debug.DebugEventUt l;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common. tr cs.Percent le;
 mport com.tw ter.search.common. tr cs.Percent leUt l;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchCustomGauge;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageExcept on;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageRunt  Except on;
 mport com.tw ter.search. ngester.p pel ne.w re.W reModule;

/**
 * Common funct onal y for all stages.
 */
publ c class Tw terBaseStage<T, R> extends  nstru ntedBaseStage {
  // Currently, all stages run  n separate threads, so   could use s mple maps  re.
  // Ho ver,   seems safer to use concurrent maps,  n case   ever change   stage set up.
  // T  performance  mpact should be negl g ble.
  pr vate f nal ConcurrentMap<Opt onal<Str ng>, SearchRateCounter> branchEm ObjectsRateCounters =
      Maps.newConcurrentMap();
  pr vate f nal ConcurrentMap<Opt onal<Str ng>, SearchRateCounter>
    branchEm BatchObjectsRateCounters = Maps.newConcurrentMap();

  pr vate Str ng stageNa Pref x = null;

  protected W reModule w reModule;
  protected Dec der dec der;
  protected Clock clock;
  protected Earlyb rdCluster earlyb rdCluster;

  pr vate Str ng fullStageNa  = null;
  pr vate Percent le<Long> processPercent le = null;
  pr vate SearchT  rStats processT  rStats = null;
  pr vate SearchRateCounter dropped ems = null;
  pr vate SearchLongGauge stageExcept ons = null;

  pr vate SearchRateCounter  ncom ngBatc sRateCounter;
  pr vate SearchRateCounter  ncom ngBatchObjectsRateCounter;

  pr vate L st<Str ng> passThroughToBranc s = Collect ons.emptyL st();
  pr vate L st<Str ng> add  onalEm ToBranc s = Collect ons.emptyL st();

  pr vate boolean passThroughDownstream = false;
  pr vate boolean em Downstream = true;

  pr vate Str ng drop emsDec derKey;

  // From XML conf g.
  publ c vo d setPassThroughToBranc s(Str ng passThroughToBranc sStr ng) {
    // T   s a comma-del m ed str ng wh ch  s a l st of branc s to wh ch   just
    // pass through t   ncom ng object w hout any process ng/f lter ng.
    t .passThroughToBranc s = Arrays.asL st(passThroughToBranc sStr ng.spl (","));
  }

  // From XML conf g.
  publ c vo d setAdd  onalEm ToBranc s(Str ng em ToBranc sStr ng) {
    // T   s a comma-del m ed str ng wh ch  s a l st of branc s to wh ch  
    // w ll em  w n   call actuallyEm AndCount(obj).
    t .add  onalEm ToBranc s = Arrays.asL st(em ToBranc sStr ng.spl (","));
  }

  // From XML conf g.
  publ c vo d setPassThroughDownstream(boolean passThroughDownstream) {
    //  f true,   em  t  raw object downstream
    t .passThroughDownstream = passThroughDownstream;
  }

  // From XML conf g.
  publ c vo d setEm Downstream(boolean em Downstream) {
    //  f true,   em  t  processed object downstream.
    t .em Downstream = em Downstream;
  }

  @Overr de
  publ c f nal vo d  nnerPreprocess() throws StageExcept on {
    try {
      setupEssent alObjects();
      do nnerPreprocess();
    } catch (Nam ngExcept on e) {
      throw new StageExcept on(t , "Fa led to  n  al ze stage.", e);
    }
  }

  /***
   * Sets up all necessary objects for t  stage of t  P pel ne. Prev ously, t  task was done
   * by t  preprocess()  thod prov ded by t  ACP l brary.
   * @throws P pel neStageExcept on
   */
  publ c vo d setupStageV2() throws P pel neStageExcept on {
    try {
      setupCommonStats();
       nnerSetupStats();
      setupEssent alObjects();
       nnerSetup();
    } catch (Nam ngExcept on e) {
      throw new P pel neStageExcept on(t , "Fa led to  n  al ze stage", e);
    }
  }

  protected vo d  nnerSetup() throws P pel neStageExcept on, Nam ngExcept on { }

  /***
   * Takes  n an argu nt of type T, processes   and returns an argu nt of Type R. T   s t 
   * ma n  thod of a p pel ne stage.
   */
  publ c R runStageV2(T arg) {
    long start ngT   = startProcess ng();
    R processed =  nnerRunStageV2(arg);
    endProcess ng(start ngT  );
    return processed;
  }

  /***
   * Takes  n an argu nt of type T, processes   and pus s t  processed ele nt to so  place.
   * T   thod does not return anyth ng as any t   t   thod  s called on a stage,    ans
   * t re  s no stage after t  one. An example stage  s any KafkaProducerStage.
   */
  publ c vo d runF nalStageOfBranchV2(T arg) {
    long start ngT   = startProcess ng();
     nnerRunF nalStageOfBranchV2(arg);
    endProcess ng(start ngT  );
  }

  protected R  nnerRunStageV2(T arg) {
    return null;
  }

  protected vo d  nnerRunF nalStageOfBranchV2(T arg) { }

  /***
   * called at t  end of a p pel ne. Cleans up all res ces of t  stage.
   */
  publ c vo d cleanupStageV2() { }

  pr vate vo d setupEssent alObjects() throws Nam ngExcept on {
    w reModule = W reModule.getW reModule();
    dec der = w reModule.getDec der();
    clock = w reModule.getClock();
    earlyb rdCluster = w reModule.getEarlyb rdCluster();
    drop emsDec derKey =
          "drop_ ems_" + earlyb rdCluster.getNa ForStats() + "_" + fullStageNa ;
  }

  protected vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on { }

  @Overr de
  protected vo d  n Stats() {
    super. n Stats();
    setupCommonStats();
    // Export stage t  rs
    SearchCustomGauge.export(stageNa Pref x + "_queue_s ze",
        () -> Opt onal.ofNullable(getQueueS zeAverage()).orElse(0.0));
    SearchCustomGauge.export(stageNa Pref x + "_queue_percentage_full",
        () -> Opt onal.ofNullable(getQueuePercentFull()).orElse(0.0));

    // T  only called once on startup
    //  n so  un  tests, getQueueCapac y can return null.  nce t  guard  s added.
    // getQueueCapac y() does not return null  re  n prod.
    SearchLongGauge.export(stageNa Pref x + "_queue_capac y")
        .set(getQueueCapac y() == null ? 0 : getQueueCapac y());
  }

  pr vate vo d setupCommonStats() {
    //  f t  stage  s  nstant ated only once, t  class na   s used for stats export
    //  f t  stage  s  nstant ated mult ple t  s, t  "stageNa " spec f ed  n t 
    // p pel ne def n  on xml f le  s also  ncluded.
     f (Str ngUt ls. sBlank(t .getStageNa ())) {
      fullStageNa  = t .getClass().getS mpleNa ();
    } else {
      fullStageNa  = Str ng.format(
          "%s_%s",
          t .getClass().getS mpleNa (),
          t .getStageNa ());
    }

    stageNa Pref x =  tr cs.normal zeNa (fullStageNa ).toLo rCase();

    dropped ems = SearchRateCounter.export(stageNa Pref x + "_dropped_ ssages");
    stageExcept ons = SearchLongGauge.export(stageNa Pref x + "_stage_except ons");

    processT  rStats = SearchT  rStats.export(stageNa Pref x, T  Un .NANOSECONDS,
        true);
    processPercent le = Percent leUt l.createPercent le(stageNa Pref x);

     ncom ngBatc sRateCounter = SearchRateCounter.export(stageNa Pref x + "_ ncom ng_batc s");
     ncom ngBatchObjectsRateCounter =
        SearchRateCounter.export(stageNa Pref x + "_ ncom ng_batch_objects");
  }

  protected vo d  nnerSetupStats() {

  }

  protected SearchCounter makeStageCounter(Str ng counterNa ) {
    return SearchCounter.export(getStageNa Pref x() + "_" + counterNa );
  }

  pr vate SearchRateCounter getEm ObjectsRateCounterFor(Opt onal<Str ng> maybeBranch) {
    return getRateCounterFor(maybeBranch, "em _objects", branchEm ObjectsRateCounters);
  }

  pr vate SearchRateCounter getEm BatchObjectsRateCounterFor(Opt onal<Str ng> maybeBranch) {
    return getRateCounterFor(maybeBranch, "em _batch_objects", branchEm BatchObjectsRateCounters);
  }

  pr vate SearchRateCounter getRateCounterFor(
      Opt onal<Str ng> maybeBranch,
      Str ng statSuff x,
      ConcurrentMap<Opt onal<Str ng>, SearchRateCounter> rateCountersMap) {
    SearchRateCounter rateCounter = rateCountersMap.get(maybeBranch);
     f (rateCounter == null) {
      Str ng branchSuff x = maybeBranch.map(b -> "_" + b.toLo rCase()).orElse("");
      rateCounter = SearchRateCounter.export(stageNa Pref x + branchSuff x + "_" + statSuff x);
      SearchRateCounter ex st ngRateCounter = rateCountersMap.put fAbsent(maybeBranch, rateCounter);
       f (ex st ngRateCounter != null) {
        Precond  ons.c ckState(
            ex st ngRateCounter == rateCounter,
            "SearchRateCounter.export() should always return t  sa  stat  nstance.");
      }
    }
    return rateCounter;
  }

  publ c Str ng getStageNa Pref x() {
    return stageNa Pref x;
  }

  publ c Str ng getFullStageNa () {
    return fullStageNa ;
  }

  @Overr de
  publ c vo d process(Object obj) throws StageExcept on {
    long startT   = System.nanoT  ();
    try {
      // t  needs to be updated before call ng super.process() so that  nnerProcess can actually
      // use t  updated  ncom ng rates
      update ncom ngBatchStats(obj);
      // Track t m ng events for w n t ets enter each stage.
      captureStageDebugEvents(obj);

       f (Dec derUt l. sAva lableForRandomRec p ent(dec der, drop emsDec derKey)) {
        dropped ems. ncre nt();
        return;
      }

      super.process(obj);

      // Now em  t  object raw to w rever   need to
      em ToPassThroughBranc s(obj);
    } f nally {
      long processT   = System.nanoT  () - startT  ;
      processT  rStats.t  r ncre nt(processT  );
      processPercent le.record(processT  );
      stageExcept ons.set(stats.getExcept onCount());
    }
  }

  protected long startProcess ng() {
    long start ngT   = System.nanoT  ();
    c ck fObjectShouldBeEm tedOrThrowRunt  Except on();
    return start ngT  ;
  }

  protected vo d endProcess ng(long start ngT  ) {
    long processT   = System.nanoT  () - start ngT  ;
    processT  rStats.t  r ncre nt(processT  );
    processPercent le.record(processT  );
  }

  pr vate vo d c ck fObjectShouldBeEm tedOrThrowRunt  Except on() {
     f (Dec derUt l. sAva lableForRandomRec p ent(dec der, drop emsDec derKey)) {
      dropped ems. ncre nt();
      throw new P pel neStageRunt  Except on("Object does not have to be processed and passed"
          + " to t  next stage");
    }
  }

  pr vate vo d em ToPassThroughBranc s(Object obj) {
    for (Str ng branch : passThroughToBranc s) {
      actuallyEm AndCount(Opt onal.of(branch), obj);
    }
     f (passThroughDownstream) {
      actuallyEm AndCount(Opt onal.empty(), obj);
    }
  }

  pr vate vo d update ncom ngBatchStats(Object obj) {
     ncom ngBatc sRateCounter. ncre nt();
     ncom ngBatchObjectsRateCounter. ncre nt(getBatchS zeForStats(obj));
  }

  protected vo d captureStageDebugEvents(Object obj) {
     f (obj  nstanceof DebugEventAccumulator) {
      DebugEventUt l.addDebugEvent(
          (DebugEventAccumulator) obj, getFullStageNa (), clock.nowM ll s());
    } else  f (obj  nstanceof Collect on) {
      DebugEventUt l.addDebugEventToCollect on(
          (Collect on<?>) obj, getFullStageNa (), clock.nowM ll s());
    } else {
      SearchCounter debugEventsNotSupportedCounter = SearchCounter.export(
          stageNa Pref x + "_debug_events_not_supported_for_" + obj.getClass());
      debugEventsNotSupportedCounter. ncre nt();
    }
  }

  protected  nt getBatchS zeForStats(Object obj) {
    return (obj  nstanceof Collect on) ? ((Collect on<?>) obj).s ze() : 1;
  }

  protected vo d em AndCount(Object obj) {
    for (Str ng branch : add  onalEm ToBranc s) {
      actuallyEm AndCount(Opt onal.of(branch), obj);
    }
     f (em Downstream) {
      actuallyEm AndCount(Opt onal.empty(), obj);
    }
  }

  protected vo d em ToBranchAndCount(Str ng branch, Object obj) {
    actuallyEm AndCount(Opt onal.of(branch), obj);
  }

  //  f t  branch  s none, em  downstream
  pr vate vo d actuallyEm AndCount(Opt onal<Str ng> maybeBranch, Object obj) {
     f (maybeBranch. sPresent()) {
      em (maybeBranch.get(), obj);
    } else {
      em (obj);
    }
    getEm ObjectsRateCounterFor(maybeBranch). ncre nt();
    getEm BatchObjectsRateCounterFor(maybeBranch). ncre nt(getBatchS zeForStats(obj));
  }
}
