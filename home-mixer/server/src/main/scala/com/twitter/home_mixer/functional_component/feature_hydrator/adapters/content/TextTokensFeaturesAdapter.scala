package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.content

import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.RichDataRecord
import com.twitter.ml.api.thriftscala.Int32Tensor
import com.twitter.ml.api.util.ScalaToJavaDataRecordConversions
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import com.twitter.ml.api.{thriftscala => ml}

object TextTokensFeaturesAdapter extends TimelinesMutatingAdapterBase[Seq[Int]] {

  private val TextTokenFeature: Feature.Tensor =
    TimelinesSharedFeatures.TEXT_TOKENS_EMBEDDING

  override val getFeatureContext: FeatureContext = new FeatureContext(TextTokenFeature)

  override val commonFeatures: Set[Feature[_]] = Set.empty

  override def setFeatures(
    textTokens: Seq[Int],
    richDataRecord: RichDataRecord
  ): Unit = {
    richDataRecord.setFeatureValue(
      TextTokenFeature,
      ScalaToJavaDataRecordConversions.scalaTensor2Java(
        ml.GeneralTensor.Int32Tensor(Int32Tensor(ints = textTokens))
      ))
  }
}
