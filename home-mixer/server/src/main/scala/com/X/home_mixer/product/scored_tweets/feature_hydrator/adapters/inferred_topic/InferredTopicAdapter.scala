package com.X.home_mixer.product.scored_tweets.feature_hydrator.adapters.inferred_topic

import com.X.ml.api.Feature
import com.X.ml.api.FeatureContext
import com.X.ml.api.RichDataRecord
import com.X.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.X.timelines.prediction.features.common.TimelinesSharedFeatures
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
