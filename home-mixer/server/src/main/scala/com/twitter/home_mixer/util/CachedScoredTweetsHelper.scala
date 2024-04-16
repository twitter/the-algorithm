package com.tw ter.ho _m xer.ut l

 mport com.tw ter.ho _m xer.model.Ho Features.Cac dScoredT etsFeature
 mport com.tw ter.ho _m xer.{thr ftscala => hmt}
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.ut l.T  

object Cac dScoredT ets lper {

  def t et mpress onsAndCac dScoredT ets(
    features: FeatureMap,
    cand dateP pel ne dent f er: Cand dateP pel ne dent f er
  ): Seq[Long] = {
    val t et mpress ons = T et mpress ons lper.t et mpress ons(features)
    val cac dScoredT ets = features
      .getOrElse(Cac dScoredT etsFeature, Seq.empty)
      .f lter { t et =>
        t et.cand dateP pel ne dent f er.ex sts(
          Cand dateP pel ne dent f er(_).equals(cand dateP pel ne dent f er))
      }.map(_.t et d)

    (t et mpress ons ++ cac dScoredT ets).toSeq
  }

  def t et mpress onsAndCac dScoredT ets nRange(
    features: FeatureMap,
    cand dateP pel ne dent f er: Cand dateP pel ne dent f er,
    maxNum mpress ons:  nt,
    s nceT  : T  ,
    unt lT  : T  
  ): Seq[Long] =
    t et mpress onsAndCac dScoredT ets(features, cand dateP pel ne dent f er)
      .f lter { t et d => Snowflake d. sSnowflake d(t et d) }
      .f lter { t et d =>
        val creat onT   = Snowflake d.t  From d(t et d)
        s nceT   <= creat onT   && unt lT   >= creat onT  
      }.take(maxNum mpress ons)

  def unseenCac dScoredT ets(
    features: FeatureMap
  ): Seq[hmt.ScoredT et] = {
    val seenT et ds = T et mpress ons lper.t et mpress ons(features)

    features
      .getOrElse(Cac dScoredT etsFeature, Seq.empty)
      .f lter(t et => !seenT et ds.conta ns(t et.t et d))
  }
}
