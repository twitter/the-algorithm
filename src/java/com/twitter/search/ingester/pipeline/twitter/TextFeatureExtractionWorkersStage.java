package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.ut l.concurrent.Block ngQueue;
 mport java.ut l.concurrent.ExecutorServ ce;
 mport javax.nam ng.Nam ngExcept on;

 mport com.google.common.collect.Queues;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducesConsu d;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCustomGauge;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.relevance.text.T etParser;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageRunt  Except on;

@Consu dTypes(Tw ter ssage.class)
@ProducesConsu d
publ c class TextFeatureExtract onWorkersStage extends Tw terBaseStage
    <Tw ter ssage, Tw ter ssage> {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(TextFeatureExtract onWorkersStage.class);

  pr vate stat c f nal  nt NUM_THREADS = 5;
  pr vate stat c f nal  nt MAX_QUEUE_S ZE = 100;
  pr vate stat c f nal long SLOW_TWEET_T ME_M LL S = 1000;
  pr vate ExecutorServ ce executorServ ce = null;

  // def ne as stat c so that FeatureExtractorWorker thread can use  
  pr vate stat c SearchRateCounter slowT etCounter;
  pr vate SearchRateCounter threadErrorCounter;
  pr vate SearchRateCounter thread nterrupt onCounter;
  pr vate f nal Block ngQueue<Tw ter ssage>  ssageQueue =
      Queues.newL nkedBlock ngQueue(MAX_QUEUE_S ZE);
  pr vate T etParser t etParser;

  @Overr de
  publ c vo d  n Stats() {
    super. n Stats();
     nnerSetupStats();
  }

  @Overr de
  protected vo d  nnerSetupStats() {
    slowT etCounter = SearchRateCounter.export(
        getStageNa Pref x() + "_text_feature_extract on_slow_t et_count");
    SearchCustomGauge.export(getStageNa Pref x() + "_queue_s ze",
         ssageQueue::s ze);
    threadErrorCounter = SearchRateCounter.export(
        getStageNa Pref x() + "_text_qual y_evaluat on_thread_error");
    thread nterrupt onCounter = SearchRateCounter.export(
        getStageNa Pref x() + "_text_qual y_evaluat on_thread_ nterrupt on");
  }

  @Overr de
  protected vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on {
     nnerSetup();
    // anyth ng thread ng related,   don't need  n V2 as of yet.
    executorServ ce = w reModule.getThreadPool(NUM_THREADS);
    for ( nt   = 0;   < NUM_THREADS; ++ ) {
      executorServ ce.subm (new FeatureExtractorWorker());
    }
    LOG. nfo(" n  al zed {} parsers.", NUM_THREADS);
  }

  @Overr de
  protected vo d  nnerSetup() {
    t etParser = new T etParser();
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof Tw ter ssage)) {
      LOG.error("Object  s not a Tw ter ssage object: {}", obj);
      return;
    }

    Tw ter ssage  ssage = Tw ter ssage.class.cast(obj);
    try {
       ssageQueue.put( ssage);
    } catch ( nterruptedExcept on  e) {
      LOG.error(" nterrupted except on add ng to t  queue",  e);
    }
  }

  pr vate boolean tryToParse(Tw ter ssage  ssage) {
    boolean  sAbleToParse = false;
    long startT   = clock.nowM ll s();
    // Parse t et and  rge t  parsed out features  nto what   already have  n t   ssage.
    try {
      synchron zed (t ) {
        t etParser.parseT et( ssage, false, false);
      }
      //  f pars ng fa led   don't need to pass t  t et down t  p pel ne.
       sAbleToParse = true;
    } catch (Except on e) {
      threadErrorCounter. ncre nt();
      LOG.error("Uncaught except on from t etParser.parseT et()", e);
    } f nally {
      long elapsedT   = clock.nowM ll s() - startT  ;
       f (elapsedT   > SLOW_TWEET_T ME_M LL S) {
        LOG.debug("Took {}ms to parse t et {}: {}", elapsedT  ,  ssage.get d(),  ssage);
        slowT etCounter. ncre nt();
      }
    }
    return  sAbleToParse;
  }

  @Overr de
  protected Tw ter ssage  nnerRunStageV2(Tw ter ssage  ssage) {
     f (!tryToParse( ssage)) {
      throw new P pel neStageRunt  Except on("Fa led to parse, not pass ng to next stage.");
    }

    return  ssage;
  }

  @Overr de
  publ c vo d  nnerPostprocess() {
     f (executorServ ce != null) {
      executorServ ce.shutdownNow();
    }
    executorServ ce = null;
  }

  pr vate class FeatureExtractorWorker  mple nts Runnable {
    publ c vo d run() {
      wh le (!Thread.currentThread(). s nterrupted()) {
        Tw ter ssage  ssage = null;
        try {
           ssage =  ssageQueue.take();
        } catch ( nterruptedExcept on  e) {
          thread nterrupt onCounter. ncre nt();
          LOG.error(" nterrupted except on poll ng from t  queue",  e);
          cont nue;
        } f nally {
           f (tryToParse( ssage)) {
            em AndCount( ssage);
          }
        }
      }
    }
  }
}
