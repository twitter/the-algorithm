package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.content

import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.FloatTensor
import com.twitter.ml.api.GeneralTensor
import com.twitter.ml.api.RichDataRecord
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import scala.collection.JavaConverters._

object ClipEmbeddingFeaturesAdapter extends TimelinesMutatingAdapterBase[Seq[Double]] {

  val ClipEmbeddingsFeature: Feature.Tensor = TimelinesSharedFeatures.CLIP_EMBEDDING

  override val getFeatureContext: FeatureContext = new FeatureContext(ClipEmbeddingsFeature)

  override val commonFeatures: Set[Feature[_]] = Set.empty

  override def setFeatures(
    clipEmbedding: Seq[Double],
    richDataRecord: RichDataRecord
  ): Unit = {
    val clipEmbeddingTensor = new GeneralTensor()
    clipEmbeddingTensor.setFloatTensor(new FloatTensor(clipEmbedding.map(Double.box).asJava))
    richDataRecord.setFeatureValue(
      ClipEmbeddingsFeature,
      clipEmbeddingTensor
    )
  }
}
