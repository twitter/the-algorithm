package com.twitter.home_mixer.product.scored_tweets.feature_hydrator.adapters.inferred_topic

import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.RichDataRecord
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import scala.collection.JavaConverters._

object InferredTopicAdapter extends TimelinesMutatingAdapterBase[Map[Long, Double]] {

  override val getFeatureContext: FeatureContext = new FeatureContext(
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
