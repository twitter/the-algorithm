package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.adapters

 mport com.tw ter.follow_recom ndat ons.common.models.UserCand dateS ceDeta ls
 mport com.tw ter. rm .constants.Algor hmFeedbackTokens.Algor hmToFeedbackTokenMap
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter. rm .model.Algor hm.Algor hm
 mport com.tw ter. rm .model.Algor hm.UttProducerOffl neMbcgV1
 mport com.tw ter. rm .model.Algor hm.UttProducerOnl neMbcgV1
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature.SparseB nary
 mport com.tw ter.ml.ap .Feature.SparseCont nuous
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap . RecordOneToOneAdapter
 mport com.tw ter.ml.ap .ut l.FDsl._

object Cand dateAlgor hmAdapter
    extends  RecordOneToOneAdapter[Opt on[UserCand dateS ceDeta ls]] {

  val CAND DATE_ALGOR THMS: SparseB nary = new SparseB nary("cand date.s ce.algor hm_ ds")
  val CAND DATE_SOURCE_SCORES: SparseCont nuous =
    new SparseCont nuous("cand date.s ce.scores")
  val CAND DATE_SOURCE_RANKS: SparseCont nuous =
    new SparseCont nuous("cand date.s ce.ranks")

  overr de val getFeatureContext: FeatureContext = new FeatureContext(
    CAND DATE_ALGOR THMS,
    CAND DATE_SOURCE_SCORES,
    CAND DATE_SOURCE_RANKS
  )

  /** l st of cand date s ce remaps to avo d creat ng d fferent features for exper  ntal s ces.
   *  t  LHS should conta n t  exper  ntal s ce, and t  RHS should conta n t  prod s ce.
   */
  def remapCand dateS ce(a: Algor hm): Algor hm = a match {
    case UttProducerOnl neMbcgV1 => UttProducerOffl neMbcgV1
    case _ => a
  }

  // add t  l st of algor hm feedback tokens ( ntegers) as a sparse b nary feature
  overr de def adaptToDataRecord(
    userCand dateS ceDeta lsOpt: Opt on[UserCand dateS ceDeta ls]
  ): DataRecord = {
    val dr = new DataRecord()
    userCand dateS ceDeta lsOpt.foreach { userCand dateS ceDeta ls =>
      val scoreMap = for {
        (s ce, scoreOpt) <- userCand dateS ceDeta ls.cand dateS ceScores
        score <- scoreOpt
        algo <- Algor hm.w hNa Opt(s ce.na )
        algo d <- Algor hmToFeedbackTokenMap.get(remapCand dateS ce(algo))
      } y eld algo d.toStr ng -> score
      val rankMap = for {
        (s ce, rank) <- userCand dateS ceDeta ls.cand dateS ceRanks
        algo <- Algor hm.w hNa Opt(s ce.na )
        algo d <- Algor hmToFeedbackTokenMap.get(remapCand dateS ce(algo))
      } y eld algo d.toStr ng -> rank.toDouble

      val algo ds = scoreMap.keys.toSet ++ rankMap.keys.toSet

      // hydrate  f not empty
       f (rankMap.nonEmpty) {
        dr.setFeatureValue(CAND DATE_SOURCE_RANKS, rankMap)
      }
       f (scoreMap.nonEmpty) {
        dr.setFeatureValue(CAND DATE_SOURCE_SCORES, scoreMap)
      }
       f (algo ds.nonEmpty) {
        dr.setFeatureValue(CAND DATE_ALGOR THMS, algo ds)
      }
    }
    dr
  }
}
