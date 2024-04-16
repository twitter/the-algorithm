package com.tw ter.search. ngester.p pel ne.tw ter;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducesConsu d;

 mport com.tw ter.search.common.relevance.class f ers.T etQual yFeatureExtractor;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;

@Consu dTypes( ngesterTw ter ssage.class)
@ProducesConsu d
publ c class ComputeT etS gnatureStage extends Tw terBaseStage
    < ngesterTw ter ssage,  ngesterTw ter ssage> {
  pr vate f nal T etQual yFeatureExtractor t etS gnatureExtractor =
      new T etQual yFeatureExtractor();

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
    t etS gnatureExtractor.extractT etTextFeatures( ssage);
  }

  @Overr de
  protected  ngesterTw ter ssage  nnerRunStageV2( ngesterTw ter ssage  ssage) {
    extract( ssage);
    return  ssage;
  }
}

