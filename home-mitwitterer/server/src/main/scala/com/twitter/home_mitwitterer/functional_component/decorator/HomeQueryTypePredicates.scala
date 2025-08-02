package com.twitter.home_mitwitterer.functional_component.decorator

import com.twitter.home_mitwitterer.model.HomeFeatures._
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap

object HomeQueryTypePredicates {
  private[this] val QueryPredicates: Seq[(String, FeatureMap => Boolean)] = Seq(
    ("request", _ => true),
    ("get_initial", _.getOrElse(GetInitialFeature, false)),
    ("get_newer", _.getOrElse(GetNewerFeature, false)),
    ("get_older", _.getOrElse(GetOlderFeature, false)),
    ("pull_to_refresh", _.getOrElse(PullToRefreshFeature, false)),
    ("request_contetwittert_launch", _.getOrElse(IsLaunchRequestFeature, false)),
    ("request_contetwittert_foreground", _.getOrElse(IsForegroundRequestFeature, false))
  )

  val PredicateMap = QueryPredicates.toMap
}
