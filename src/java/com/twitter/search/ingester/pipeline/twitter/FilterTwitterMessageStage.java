package com.tw ter.search. ngester.p pel ne.tw ter;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducesConsu d;

 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search. ngester.p pel ne.tw ter.f lters. ngesterVal d ssageF lter;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageRunt  Except on;

/**
 * F lter out Tw ter  ssages  et ng so  f lter ng rule.
 */
@Consu dTypes(Tw ter ssage.class)
@ProducesConsu d
publ c class F lterTw ter ssageStage extends Tw terBaseStage
    <Tw ter ssage, Tw ter ssage> {
  pr vate  ngesterVal d ssageF lter f lter = null;
  pr vate SearchRateCounter val d ssages;
  pr vate SearchRateCounter  nval d ssages;

  @Overr de
  protected vo d  n Stats() {
    super. n Stats();
     nnerSetupStats();
  }

  @Overr de
  protected vo d  nnerSetupStats() {
    val d ssages = SearchRateCounter.export(getStageNa Pref x() + "_val d_ ssages");
     nval d ssages = SearchRateCounter.export(getStageNa Pref x() + "_f ltered_ ssages");
  }

  @Overr de
  protected vo d do nnerPreprocess() {
     nnerSetup();
  }

  @Overr de
  protected vo d  nnerSetup() {
    f lter = new  ngesterVal d ssageF lter(dec der);
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof Tw ter ssage)) {
      throw new StageExcept on(t , "Object  s not a  ngesterTw ter ssage: "
      + obj);
    }

    Tw ter ssage  ssage = (Tw ter ssage) obj;
     f (tryToF lter( ssage)) {
      em AndCount( ssage);
    }
  }

  @Overr de
  protected Tw ter ssage  nnerRunStageV2(Tw ter ssage  ssage) {
     f (!tryToF lter( ssage)) {
      throw new P pel neStageRunt  Except on("Fa led to f lter, does not have to "
      + "pass to t  next stage");
    }
    return  ssage;
  }

  pr vate boolean tryToF lter(Tw ter ssage  ssage) {
    boolean ableToF lter = false;
     f ( ssage != null && f lter.accepts( ssage)) {
      val d ssages. ncre nt();
      ableToF lter = true;
    } else {
       nval d ssages. ncre nt();
    }
    return ableToF lter;
  }
}
