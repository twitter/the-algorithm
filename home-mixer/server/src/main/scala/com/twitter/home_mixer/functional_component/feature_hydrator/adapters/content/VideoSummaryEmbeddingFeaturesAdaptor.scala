package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.content

import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.FloatTensor
import com.twitter.ml.api.RichDataRecord
import com.twitter.ml.api.GeneralTensor
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import scala.collection.JavaConverters._

object VideoSummaryEmbeddingFeaturesAdaptor extends TimelinesMutatingAdapterBase[Seq[Double]] {

  val VideoSummaryEmbeddingFeature: Feature.Tensor = TimelinesSharedFeatures.VIDEO_SUMMARY_EMBEDDING

  override val getFeatureContext: FeatureContext = new FeatureContext(VideoSummaryEmbeddingFeature)

  override val commonFeatures: Set[Feature[_]] = Set.empty

  override def setFeatures(
    videoSummaryEmbedding: Seq[Double],
    richDataRecord: RichDataRecord
  ): Unit = {
    val summaryEmbeddingTensor = new GeneralTensor()
    summaryEmbeddingTensor.setFloatTensor(
      new FloatTensor(videoSummaryEmbedding.map(Double.box).asJava))
    richDataRecord.setFeatureValue(
      VideoSummaryEmbeddingFeature,
      summaryEmbeddingTensor
    )
  }
}
