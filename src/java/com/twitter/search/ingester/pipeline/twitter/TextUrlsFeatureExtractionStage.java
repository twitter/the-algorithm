package com.tw ter.search. ngester.p pel ne.tw ter;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducesConsu d;

 mport com.tw ter.search.common.relevance.class f ers.T etOffens veEvaluator;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.relevance.scorers.T etTextScorer;
 mport com.tw ter.search.common.relevance.text.T etParser;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;

@Consu dTypes(Tw ter ssage.class)
@ProducesConsu d
publ c class TextUrlsFeatureExtract onStage extends Tw terBaseStage
    < ngesterTw ter ssage,  ngesterTw ter ssage> {
  pr vate f nal T etParser t etParser = new T etParser();
  pr vate T etOffens veEvaluator offens veEvaluator;
  pr vate f nal T etTextScorer t etTextScorer = new T etTextScorer(null);

  @Overr de
  protected vo d do nnerPreprocess()  {
     nnerSetup();
  }

  @Overr de
  protected vo d  nnerSetup() {
    offens veEvaluator = w reModule.getT etOffens veEvaluator();
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof  ngesterTw ter ssage)) {
      throw new StageExcept on(t , "Object  s not a Tw ter ssage  nstance: " + obj);
    }

     ngesterTw ter ssage  ssage =  ngesterTw ter ssage.class.cast(obj);
    extract( ssage);
    em AndCount( ssage);
  }

  pr vate vo d extract( ngesterTw ter ssage  ssage) {
    t etParser.parseUrls( ssage);
    offens veEvaluator.evaluate( ssage);
    t etTextScorer.scoreT et( ssage);
  }

  @Overr de
  protected  ngesterTw ter ssage  nnerRunStageV2( ngesterTw ter ssage  ssage) {
    extract( ssage);
    return  ssage;
  }
}
