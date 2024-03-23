package com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.adapters.inferred_topic

import com.ExTwitter.ml.api.Feature
import com.ExTwitter.ml.api.FeatureContext
import com.ExTwitter.ml.api.RichDataRecord
import com.ExTwitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.ExTwitter.timelines.prediction.features.common.TimelinesSharedFeatures
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
