package com.tw ter.follow_recom ndat ons.common.rankers.common

object Ranker d extends Enu rat on {
  type Ranker d = Value

  val RandomRanker: Ranker d = Value("random")
  // T  product on PostNUX ML warm-start auto-retra n ng model ranker
  val PostNuxProdRanker: Ranker d = Value("postnux_prod")
  val None: Ranker d = Value("none")

  // Sampl ng from t  Placket-Luce d str but on. Appl ed after ranker step.  s ranker  d  s ma nly used for logg ng.
  val PlacketLuceSampl ngTransfor r: Ranker d = Value("placket_luce_sampl ng_transfor r")

  def getRankerByNa (na : Str ng): Opt on[Ranker d] =
    Ranker d.values.toSeq.f nd(_.equals(Value(na )))

}

/**
 * ML model based  avy ranker  ds.
 */
object ModelBased avyRanker d {
   mport Ranker d._
  val  avyRanker ds: Set[Str ng] = Set(
    PostNuxProdRanker.toStr ng,
  )
}
