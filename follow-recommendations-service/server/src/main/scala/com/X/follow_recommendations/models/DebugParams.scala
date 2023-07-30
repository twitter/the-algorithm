package com.X.follow_recommendations.models

import com.X.follow_recommendations.common.models.DebugOptions
import com.X.follow_recommendations.common.models.DebugOptions.fromDebugParamsThrift
import com.X.follow_recommendations.logging.{thriftscala => offline}
import com.X.follow_recommendations.{thriftscala => t}
import com.X.timelines.configapi.{FeatureValue => ConfigApiFeatureValue}

case class DebugParams(
  featureOverrides: Option[Map[String, ConfigApiFeatureValue]],
  debugOptions: Option[DebugOptions])

object DebugParams {
  def fromThrift(thrift: t.DebugParams): DebugParams = DebugParams(
    featureOverrides = thrift.featureOverrides.map { map =>
      map.mapValues(FeatureValue.fromThrift).toMap
    },
    debugOptions = Some(
      fromDebugParamsThrift(thrift)
    )
  )
  def toOfflineThrift(model: DebugParams): offline.OfflineDebugParams =
    offline.OfflineDebugParams(randomizationSeed = model.debugOptions.flatMap(_.randomizationSeed))
}

trait HasFrsDebugParams {
  def frsDebugParams: Option[DebugParams]
}
