package com.tw ter.follow_recom ndat ons.modules

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.relevance.ep_model.common.CommonConstants
 mport com.tw ter.relevance.ep_model.scorer.EPScorer
 mport com.tw ter.relevance.ep_model.scorer.EPScorerBu lder
 mport java. o.F le
 mport java. o.F leOutputStream
 mport scala.language.postf xOps

object ScorerModule extends Tw terModule {
  pr vate val STPScorerPath = "/qual y/stp_models/20141223"

  pr vate def f leFromRes ce(res ce: Str ng): F le = {
    val  nputStream = getClass.getRes ceAsStream(res ce)
    val f le = F le.createTempF le(res ce, "temp")
    val fos = new F leOutputStream(f le)
     erator
      .cont nually( nputStream.read)
      .takeWh le(-1 !=)
      .foreach(fos.wr e)
    f le
  }

  @Prov des
  @S ngleton
  def prov deEpScorer: EPScorer = {
    val modelPath =
      f leFromRes ce(STPScorerPath + "/" + CommonConstants.EP_MODEL_F LE_NAME).getAbsolutePath
    val tra n ngConf gPath =
      f leFromRes ce(STPScorerPath + "/" + CommonConstants.TRA N NG_CONF G).getAbsolutePath
    val epScorer = new EPScorerBu lder
    epScorer
      .w hModelPath(modelPath)
      .w hTra n ngConf g(tra n ngConf gPath)
      .bu ld()
  }
}
