package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings

import com.twitter.ml.api.DataType
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.RichDataRecord
import com.twitter.ml.api.util.ScalaToJavaDataRecordConversions
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import scala.collection.JavaConverters._

sealed trait TransformerEmbeddingsAdapter
    extends TimelinesMutatingAdapterBase[Option[ml.FloatTensor]] {
  def embeddingsFeature: Feature.Tensor

  override def getFeatureContext: FeatureContext = new FeatureContext(
    embeddingsFeature
  )

  override def setFeatures(
    embedding: Option[ml.FloatTensor],
    richDataRecord: RichDataRecord
  ): Unit = {
    embedding.foreach { floatTensor =>
      richDataRecord.setFeatureValue(
        embeddingsFeature,
        ScalaToJavaDataRecordConversions.scalaTensor2Java(
          ml.GeneralTensor
            .FloatTensor(floatTensor)))
    }
  }
}

sealed trait TransformerByteEmbeddingsAdapter
    extends TimelinesMutatingAdapterBase[Option[ml.RawTypedTensor]] {
  def embeddingsFeature: Feature.Tensor

  override def getFeatureContext: FeatureContext = new FeatureContext(
    embeddingsFeature
  )

  override def setFeatures(
    embedding: Option[ml.RawTypedTensor],
    richDataRecord: RichDataRecord
  ): Unit = {
    embedding.foreach { tensor =>
      richDataRecord.setFeatureValue(
        embeddingsFeature,
        ScalaToJavaDataRecordConversions.scalaTensor2Java(
          ml.GeneralTensor
            .RawTypedTensor(tensor)))
    }
  }
}

object TransformerEmbeddingsFeatures {
  val UserHistoryTransformerEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "user.transformer.user_history_as_float_tensor",
    DataType.FLOAT,
    List(128L).map(long2Long).asJava,
  )
  val UserHistoryTransformerEmbeddingsGreenFeature: Feature.Tensor = new Feature.Tensor(
    "user.transformer.green.user_history_as_float_tensor",
    DataType.BYTE,
    List(1024L).map(long2Long).asJava,
  )
  val UserHistoryTransformerEmbeddingsJointBlueFeature: Feature.Tensor = new Feature.Tensor(
    "user.transformer.joint.blue.user_history_as_float_tensor",
    DataType.BYTE,
    List(1024L).map(long2Long).asJava,
  )
  val PostTransformerEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "tweet_id.transformer.post_as_float_tensor",
    DataType.FLOAT,
    List(128L).map(long2Long).asJava,
  )
  val PostTransformerEmbeddingsGreenFeature: Feature.Tensor = new Feature.Tensor(
    "tweet_id.transformer.green.post_as_float_tensor",
    DataType.FLOAT,
    List(128L).map(long2Long).asJava,
  )
  val PostTransformerEmbeddingsJointBlueFeature: Feature.Tensor = new Feature.Tensor(
    "tweet_id.transformer.joint.blue.post_as_float_tensor",
    DataType.FLOAT,
    List(128L).map(long2Long).asJava,
  )
}

object UserHistoryTransformerEmbeddingsHomeBlueAdapter extends TransformerEmbeddingsAdapter {
  override val embeddingsFeature: Feature.Tensor =
    TransformerEmbeddingsFeatures.UserHistoryTransformerEmbeddingsFeature

  override val commonFeatures: Set[Feature[_]] = Set.empty
}

object UserHistoryTransformerEmbeddingsHomeGreenAdapter extends TransformerByteEmbeddingsAdapter {
  override val embeddingsFeature: Feature.Tensor =
    TransformerEmbeddingsFeatures.UserHistoryTransformerEmbeddingsGreenFeature

  override val commonFeatures: Set[Feature[_]] = Set.empty
}

object UserHistoryTransformerEmbeddingsJointBlueAdapter extends TransformerByteEmbeddingsAdapter {
  override val embeddingsFeature: Feature.Tensor =
    TransformerEmbeddingsFeatures.UserHistoryTransformerEmbeddingsJointBlueFeature

  override val commonFeatures: Set[Feature[_]] = Set.empty
}

object PostTransformerEmbeddingsHomeBlueAdapter extends TransformerEmbeddingsAdapter {
  override val embeddingsFeature: Feature.Tensor =
    TransformerEmbeddingsFeatures.PostTransformerEmbeddingsFeature

  override val commonFeatures: Set[Feature[_]] = Set.empty
}

object PostTransformerEmbeddingsHomeGreenAdapter extends TransformerEmbeddingsAdapter {
  override val embeddingsFeature: Feature.Tensor =
    TransformerEmbeddingsFeatures.PostTransformerEmbeddingsGreenFeature

  override val commonFeatures: Set[Feature[_]] = Set.empty
}

object PostTransformerEmbeddingsJointBlueAdapter extends TransformerEmbeddingsAdapter {
  override val embeddingsFeature: Feature.Tensor =
    TransformerEmbeddingsFeatures.PostTransformerEmbeddingsJointBlueFeature

  override val commonFeatures: Set[Feature[_]] = Set.empty
}
