package com.tw ter.search. ngester.p pel ne.tw ter;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducedTypes;

 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageRunt  Except on;

/**
 * F lters out t ets that are not ret ets or repl es.
 */
@Consu dTypes( ngesterTw ter ssage.class)
@ProducedTypes( ngesterTw ter ssage.class)
publ c class F lterRet etsAndRepl esStage extends Tw terBaseStage
    < ngesterTw ter ssage,  ngesterTw ter ssage> {
  pr vate stat c f nal Str ng EM T_RETWEET_AND_REPLY_ENGAGEMENTS_DEC DER_KEY =
      " ngester_realt  _em _ret et_and_reply_engage nts";

  pr vate SearchRateCounter f lteredRet etsCount;
  pr vate SearchRateCounter f lteredRepl esToT etsCount;
  pr vate SearchRateCounter  ncom ngRet etsAndRepl esToT etsCount;

  @Overr de
  publ c vo d  n Stats() {
    super. n Stats();
     nnerSetupStats();
  }

  @Overr de
  protected vo d  nnerSetupStats() {
    f lteredRet etsCount =
        SearchRateCounter.export(getStageNa Pref x() + "_f ltered_ret ets_count");
    f lteredRepl esToT etsCount =
        SearchRateCounter.export(getStageNa Pref x() + "_f ltered_repl es_to_t ets_count");
     ncom ngRet etsAndRepl esToT etsCount =
        SearchRateCounter.export(
            getStageNa Pref x() + "_ ncom ng_ret ets_and_repl es_to_t ets_count");
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof  ngesterTw ter ssage)) {
      throw new StageExcept on(t , "Object  s not an  ngesterTw ter ssage: " + obj);
    }

     ngesterTw ter ssage status = ( ngesterTw ter ssage) obj;
     f (tryToF lter(status)) {
      em AndCount(status);
    }
  }

  @Overr de
  publ c  ngesterTw ter ssage runStageV2( ngesterTw ter ssage  ssage) {
     f (!tryToF lter( ssage)) {
      throw new P pel neStageRunt  Except on("Does not have to pass to t  next stage.");
    }
    return  ssage;
  }

  pr vate boolean tryToF lter( ngesterTw ter ssage status) {
    boolean shouldEm  = false;
     f (status. sRet et() || status. sReplyToT et()) {
       ncom ngRet etsAndRepl esToT etsCount. ncre nt();
       f (Dec derUt l. sAva lableForRandomRec p ent(
          dec der, EM T_RETWEET_AND_REPLY_ENGAGEMENTS_DEC DER_KEY)) {
         f (status. sRet et()) {
          f lteredRet etsCount. ncre nt();
        } else {
          f lteredRepl esToT etsCount. ncre nt();
        }
        shouldEm  = true;
      }
    }
    return shouldEm ;
  }
}
