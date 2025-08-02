package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.adapters.inferred_topic

import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContetwittert
import com.twitter.ml.api.RichDataRecord
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import scala.collection.JavaConverters._

object InferredTopicAdapter etwittertends TimelinesMutatingAdapterBase[Map[Long, Double]] {

  override val getFeatureContetwittert: FeatureContetwittert = new FeatureContetwittert(
    TimelinesSharedFeatures.INFERRED_TOPIC_IDS)

  override val commonFeatures: Set[Feature[_]] = Set.empty

  override def setFeatures(
    inferredTopicFeatures: Map[Long, Double],
    richDataRecord: RichDataRecord
  ): Unit = {
    richDataRecord.setFeatureValue(
      TimelinesSharedFeatures.INFERRED_TOPIC_IDS,
      inferredTopicFeatures.keys.map(_.toString).toSet.asJava)
  }
}
