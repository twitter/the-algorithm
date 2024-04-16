package com.tw ter.search. ngester.p pel ne.tw ter;
 mport java.ut l.ArrayL st;
 mport java.ut l.Collect on;
 mport java.ut l. erator;
 mport java.ut l.Opt onal;
 mport java.ut l.Queue;
 mport java.ut l.concurrent.CompletableFuture;
 mport java.ut l.concurrent.T  Un ;
 mport java.ut l.stream.Collectors;
 mport javax.nam ng.Nam ngExcept on;

 mport scala.runt  .BoxedUn ;

 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Queues;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCustomGauge;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search. ngester.p pel ne.ut l.Batc dEle nt;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageExcept on;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

publ c abstract class Tw terBatc dBaseStage<T, R> extends
    Tw terBaseStage<T, CompletableFuture<R>> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Tw terBatc dBaseStage.class);

  protected f nal Queue<Batc dEle nt<T, R>> queue =
      Queues.newL nkedBlock ngQueue(MAX_BATCH NG_QUEUE_S ZE);

  pr vate  nt batc dStageBatchS ze = 100;
  pr vate  nt forceProcessAfterMs = 500;

  pr vate long lastProcess ngT  ;

  pr vate SearchRateCounter t  BasedQueueFlush;
  pr vate SearchRateCounter s zeBasedQueueFlush;
  pr vate SearchRateCounter eventsFa led;
  pr vate SearchRateCounter numberOfCallsToNextBatch fReady;
  pr vate SearchT  rStats batchExecut onT  ;
  pr vate SearchT  rStats batchFa ledExecut onT  ;
  pr vate SearchRateCounter val dEle nts;
  pr vate SearchRateCounter batc dEle nts;
  pr vate SearchRateCounter em tedEle nts;
  pr vate stat c f nal  nt MAX_BATCH NG_QUEUE_S ZE = 10000;

  // force t   mple nt ng class to set type correctly to avo d catch ng  ssues at runt  
  protected abstract Class<T> getQueueObjectType();

  // up to t  developer on how each batch  s processed.
  protected abstract Future<Collect on<R>>  nnerProcessBatch(Collect on<Batc dEle nt<T, R>>
                                                                 batch);

  // classes that need to update t  r batch e.g after a dec der change
  // can overr de t 
  protected vo d updateBatchS ze() {
  }

  protected Collect on<T> extractOnlyEle ntsFromBatch(Collect on<Batc dEle nt<T, R>> batch) {
    Collect on<T> ele ntsOnly = new ArrayL st<>();

    for (Batc dEle nt<T, R> batc dEle nt : batch) {
      ele ntsOnly.add(batc dEle nt.get em());
    }
    return ele ntsOnly;
  }
  /**
   * T  funct on  s used to f lter t  ele nts that   want to batch.
   * e.g.  f a t et has urls batch   to resolve t  urls,  f   doesn't conta n urls
   * do not batch.
   *
   * @param ele nt to be evaluated
   */
  protected abstract boolean needsToBeBatc d(T ele nt);

  /**
   * Tranform from type T to U ele nt.
   * T and U m ght be d fferent types so t  funct on w ll  lp w h t  transformat on
   *  f t   ncom ng T ele nt  s f ltered out and  s bypass d rectly to t  next stage
   * that takes  ncom ng objects of type U
   *
   * @param ele nt  ncom ng ele nt
   */
  protected abstract R transform(T ele nt);

  protected vo d reEnqueueAndRetry(Batc dEle nt<T, R> batc dEle nt) {
    queue.add(batc dEle nt);
  }

  @Overr de
  protected vo d  n Stats() {
    super. n Stats();
    common nnerSetupStats();
  }

  pr vate vo d common nnerSetupStats() {
    t  BasedQueueFlush = SearchRateCounter.export(getStageNa Pref x()
        + "_t  _based_queue_flush");
    s zeBasedQueueFlush = SearchRateCounter.export(getStageNa Pref x()
        + "_s ze_based_queue_flush");
    batchExecut onT   = SearchT  rStats.export(getStageNa Pref x()
        + "_batch_execut on_t  ", T  Un .M LL SECONDS, false, true);
    batchFa ledExecut onT   = SearchT  rStats.export(getStageNa Pref x()
        + "_batch_fa led_execut on_t  ", T  Un .M LL SECONDS, false, true);
    eventsFa led = SearchRateCounter.export(getStageNa Pref x() + "_events_dropped");
    SearchCustomGauge.export(getStageNa Pref x() + "_batc d_stage_queue_s ze", queue::s ze);
    numberOfCallsToNextBatch fReady = SearchRateCounter.export(getStageNa Pref x()
        + "_calls_to_nextBatch fReady");
    val dEle nts = SearchRateCounter.export(getStageNa Pref x() + "_val d_ele nts");
    batc dEle nts = SearchRateCounter.export(getStageNa Pref x() + "_batc d_ele nts");
    em tedEle nts = SearchRateCounter.export(getStageNa Pref x() + "_em ted_ele nts");
  }

  @Overr de
  protected vo d  nnerSetupStats() {
    common nnerSetupStats();
  }

  // return a poss ble batch of ele nts to process.  f   have enough for one batch
  protected Opt onal<Collect on<Batc dEle nt<T, R>>> nextBatch fReady() {
    numberOfCallsToNextBatch fReady. ncre nt();
    Opt onal<Collect on<Batc dEle nt<T, R>>> batch = Opt onal.empty();

     f (!queue. sEmpty()) {
      long elapsed = clock.nowM ll s() - lastProcess ngT  ;
       f (elapsed > forceProcessAfterMs) {
        batch = Opt onal.of(L sts.newArrayL st(queue));
        t  BasedQueueFlush. ncre nt();
        queue.clear();
      } else  f (queue.s ze() >= batc dStageBatchS ze) {
        batch = Opt onal.of(queue.stream()
            .l m (batc dStageBatchS ze)
            .map(ele nt -> queue.remove())
            .collect(Collectors.toL st()));
        s zeBasedQueueFlush. ncre nt();
      }
    }
    return batch;
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
    T ele nt;
     f (getQueueObjectType(). s nstance(obj)) {
      ele nt = getQueueObjectType().cast(obj);
    } else {
      throw new StageExcept on(t , "Try ng to add an object of t  wrong type to a queue. "
          + getQueueObjectType().getS mpleNa ()
          + "  s t  expected type");
    }

    f (!tryToAddEle ntToBatch(ele nt)) {
     em AndCount(transform(ele nt));
   }

   tryToSendBatc dRequest();
  }

  @Overr de
  protected CompletableFuture<R>  nnerRunStageV2(T ele nt) {
    CompletableFuture<R> completableFuture = new CompletableFuture<>();
     f (!tryToAddEle ntToBatch(ele nt, completableFuture)) {
      completableFuture.complete(transform(ele nt));
    }

    tryToSendBatc dRequestV2();

    return completableFuture;
  }

  pr vate boolean tryToAddEle ntToBatch(T ele nt, CompletableFuture<R> cf) {
    boolean needsToBeBatc d = needsToBeBatc d(ele nt);
     f (needsToBeBatc d) {
      queue.add(new Batc dEle nt<>(ele nt, cf));
    }

    return needsToBeBatc d;
  }

  pr vate boolean tryToAddEle ntToBatch(T ele nt) {
    return tryToAddEle ntToBatch(ele nt, CompletableFuture.completedFuture(null));
  }

  pr vate vo d tryToSendBatc dRequest() {
    Opt onal<Collect on<Batc dEle nt<T, R>>> maybeToProcess = nextBatch fReady();
     f (maybeToProcess. sPresent()) {
      Collect on<Batc dEle nt<T, R>> batch = maybeToProcess.get();
      lastProcess ngT   = clock.nowM ll s();
      processBatch(batch, getOnSuccessFunct on(lastProcess ngT  ),
          getOnFa lureFunct on(batch, lastProcess ngT  ));
    }
  }

  pr vate vo d tryToSendBatc dRequestV2() {
    Opt onal<Collect on<Batc dEle nt<T, R>>> maybeToProcess = nextBatch fReady();
     f (maybeToProcess. sPresent()) {
      Collect on<Batc dEle nt<T, R>> batch = maybeToProcess.get();
      lastProcess ngT   = clock.nowM ll s();
      processBatch(batch, getOnSuccessFunct onV2(batch, lastProcess ngT  ),
          getOnFa lureFunct onV2(batch, lastProcess ngT  ));
    }
  }

  pr vate vo d processBatch(Collect on<Batc dEle nt<T, R>> batch,
                            Funct on<Collect on<R>, BoxedUn > onSuccess,
                            Funct on<Throwable, BoxedUn > onFa lure) {
    updateBatchS ze();

    Future<Collect on<R>> futureComputat on =  nnerProcessBatch(batch);

    futureComputat on.onSuccess(onSuccess);

    futureComputat on.onFa lure(onFa lure);
  }

  pr vate Funct on<Collect on<R>, BoxedUn > getOnSuccessFunct on(long started) {
    return Funct on.cons((ele nts) -> {
      ele nts.forEach(t ::em AndCount);
      batchExecut onT  .t  r ncre nt(clock.nowM ll s() - started);
    });
  }

  pr vate Funct on<Collect on<R>, BoxedUn > getOnSuccessFunct onV2(Collect on<Batc dEle nt<T, R>>
                                                                        batch, long started) {
    return Funct on.cons((ele nts) -> {
       erator<Batc dEle nt<T, R>>  erator = batch. erator();
      for (R ele nt : ele nts) {
         f ( erator.hasNext()) {
           erator.next().getCompletableFuture().complete(ele nt);
        } else {
          LOG.error("Gett ng Response from Batc d Request, but no CompleteableFuture object"
              + " to complete.");
        }
      }
      batchExecut onT  .t  r ncre nt(clock.nowM ll s() - started);

    });
  }

  pr vate Funct on<Throwable, BoxedUn > getOnFa lureFunct on(Collect on<Batc dEle nt<T, R>>
                                                                    batch, long started) {
    return Funct on.cons((throwable) -> {
      batch.forEach(batc dEle nt -> {
        eventsFa led. ncre nt();
        // pass t  t et event down better to  ndex an  ncomplete event than noth ng at all
        em AndCount(transform(batc dEle nt.get em()));
      });
      batchFa ledExecut onT  .t  r ncre nt(clock.nowM ll s() - started);
      LOG.error("Fa led process ng batch", throwable);
    });
  }

  pr vate Funct on<Throwable, BoxedUn > getOnFa lureFunct onV2(Collect on<Batc dEle nt<T, R>>
                                                                  batch, long started) {
    return Funct on.cons((throwable) -> {
      batch.forEach(batc dEle nt -> {
        eventsFa led. ncre nt();
        R  emTransfor d = transform(batc dEle nt.get em());
        // complete t  future,  s better to  ndex an  ncomplete event than noth ng at all
        batc dEle nt.getCompletableFuture().complete( emTransfor d);
      });
      batchFa ledExecut onT  .t  r ncre nt(clock.nowM ll s() - started);
      LOG.error("Fa led process ng batch", throwable);
    });
  }

  @Overr de
  protected vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on {
    try {
      common nnerSetup();
    } catch (P pel neStageExcept on e) {
      throw new StageExcept on(t , e);
    }
  }

  pr vate vo d common nnerSetup() throws P pel neStageExcept on, Nam ngExcept on {
    updateBatchS ze();

     f (batc dStageBatchS ze < 1) {
      throw new P pel neStageExcept on(t ,
          "Batch s ze must be set at least to 1 for batc d stages but  s set to"
              + batc dStageBatchS ze);
    }

     f (forceProcessAfterMs < 1) {
      throw new P pel neStageExcept on(t , "forceProcessAfterMs needs to be at least 1 "
          + "ms but  s set to " + forceProcessAfterMs);
    }
  }

  @Overr de
  protected vo d  nnerSetup() throws P pel neStageExcept on, Nam ngExcept on {
    common nnerSetup();
  }

  // Setters for conf gurat on para ters
  publ c vo d setBatc dStageBatchS ze( nt maxEle ntsToWa For) {
    t .batc dStageBatchS ze = maxEle ntsToWa For;
  }

  publ c vo d setForceProcessAfter( nt forceProcessAfterMS) {
    t .forceProcessAfterMs = forceProcessAfterMS;
  }
}
