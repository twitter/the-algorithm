package com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates

import com.twitter.ml.api.FeatureContext
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.offline_aggregates.BaseAggregateRootFeature
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateType.AggregateType
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.TypedAggregateGroup
import scala.jdk.CollectionConverters.asJavaIterableConverter

// A helper class deriving aggregate feature info from the given configuration parameters.
class AggregateFeatureInfo(
  val aggregateGroups: Set[AggregateGroup],
  val aggregateType: AggregateType) {

  private val typedAggregateGroups = aggregateGroups.flatMap(_.buildTypedAggregateGroups()).toList

  val featureContext: FeatureContext =
    new FeatureContext(
      (typedAggregateGroups.flatMap(_.allOutputFeatures) ++
        typedAggregateGroups.flatMap(_.allOutputKeys) ++
        Seq(TypedAggregateGroup.timestampFeature)).asJava)

  val feature: BaseAggregateRootFeature =
    AggregateFeatureInfo.pickFeature(aggregateType)
}

object AggregateFeatureInfo {
  val features: Set[BaseAggregateRootFeature] =
    Set(PartAAggregateRootFeature, PartBAggregateRootFeature)

  def pickFeature(aggregateType: AggregateType): BaseAggregateRootFeature = {
    val filtered = features.filter(_.aggregateTypes.contains(aggregateType))
    require(
      filtered.size == 1,
      "requested AggregateType must be backed by exactly one physical store.")
    filtered.head
  }
}
