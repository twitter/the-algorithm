package com.twitter.timelines.data_processing.ml_util.aggregation_framework.scalding

import com.twitter.scalding_internal.job.RequiredBinaryComparators.ordSer
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregationKey
import com.twitter.scalding.serialization.macros.impl.ordered_serialization.runtime_helpers.MacroEqualityOrderedSerialization

object AggregationKeyOrdering extends Ordering[AggregationKey] {
  implicit val featureMapsOrdering: MacroEqualityOrderedSerialization[
    (Map[Long, Long], Map[Long, String])
  ] = ordSer[(Map[Long, Long], Map[Long, String])]

  override def compare(left: AggregationKey, right: AggregationKey): Int =
    featureMapsOrdering.compare(
      AggregationKey.unapply(left).get,
      AggregationKey.unapply(right).get
    )
}
