package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common

 mport com.tw ter.follow_recom ndat ons.common.models.HasMutualFollo dUser ds
 mport com.tw ter.follow_recom ndat ons.common.models.HasWtf mpress ons
 mport com.tw ter.follow_recom ndat ons.common.models.Wtf mpress on
 mport com.tw ter.ut l.T  

tra  HasPreFetc dFeature extends HasMutualFollo dUser ds w h HasWtf mpress ons {

  lazy val follo d mpress ons: Seq[Wtf mpress on] = {
    for {
      wtf mprL st <- wtf mpress ons.toSeq
      wtf mpr <- wtf mprL st
       f recentFollo dUser ds.ex sts(_.conta ns(wtf mpr.cand date d))
    } y eld wtf mpr
  }

  lazy val numFollo d mpress ons:  nt = follo d mpress ons.s ze

  lazy val lastFollo d mpress onDurat onMs: Opt on[Long] = {
     f (follo d mpress ons.nonEmpty) {
      So ((T  .now - follo d mpress ons.map(_.latestT  ).max). nM ll s)
    } else None
  }
}
