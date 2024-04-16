package com.tw ter.ho _m xer.funct onal_component.decorator

 mport com.tw ter.ho _m xer.model.Ho Features._
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap

object Ho QueryTypePred cates {
  pr vate[t ] val QueryPred cates: Seq[(Str ng, FeatureMap => Boolean)] = Seq(
    ("request", _ => true),
    ("get_ n  al", _.getOrElse(Get n  alFeature, false)),
    ("get_ne r", _.getOrElse(GetNe rFeature, false)),
    ("get_older", _.getOrElse(GetOlderFeature, false)),
    ("pull_to_refresh", _.getOrElse(PullToRefreshFeature, false)),
    ("request_context_launch", _.getOrElse( sLaunchRequestFeature, false)),
    ("request_context_foreground", _.getOrElse( sForegroundRequestFeature, false))
  )

  val Pred cateMap = QueryPred cates.toMap
}
