package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.twhin_embeddings

import com.twitter.ml.api.util.BufferToIterators.RichFloatBuffer
import com.twitter.ml.api.util.ScalaToJavaDataRecordConversions
import com.twitter.ml.api.DataType
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.RichDataRecord
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase

import java.nio.ByteOrder

sealed trait TwhinEmbeddingsAdapter extends TimelinesMutatingAdapterBase[Option[ml.Embedding]] {
  def twhinEmbeddingsFeature: Feature.Tensor

  override def getFeatureContext: FeatureContext = new FeatureContext(
    twhinEmbeddingsFeature
  )

  override def setFeatures(
    embedding: Option[ml.Embedding],
    richDataRecord: RichDataRecord
  ): Unit = {
    embedding.foreach { embedding =>
      val floatTensor = embedding.tensor map { tensor =>
        ml.FloatTensor(
          tensor.content
            .order(ByteOrder.LITTLE_ENDIAN)
            .asFloatBuffer
            .iterator.toList
            .map(_.toDouble))
      }

      floatTensor.foreach { v =>
        richDataRecord.setFeatureValue(
          twhinEmbeddingsFeature,
          ScalaToJavaDataRecordConversions.scalaTensor2Java(ml.GeneralTensor.FloatTensor(v))
        )
      }
    }
  }
}

object TwhinEmbeddingsFeatures {
  val TwhinAuthorFollowEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "original_author.timelines.twhin_author_follow_embeddings.twhin_author_follow_embeddings",
    DataType.FLOAT
  )

  val TwhinUserEngagementEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "user.timelines.twhin_user_engagement_embeddings.twhin_user_engagement_embeddings",
    DataType.FLOAT
  )

  val TwhinUserFollowEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "user.timelines.twhin_user_follow_embeddings.twhin_user_follow_embeddings",
    DataType.FLOAT
  )
}

object TwhinAuthorFollowEmbeddingsAdapter extends TwhinEmbeddingsAdapter {
  override val twhinEmbeddingsFeature: Feature.Tensor =
    TwhinEmbeddingsFeatures.TwhinAuthorFollowEmbeddingsFeature

  override val commonFeatures: Set[Feature[_]] = Set.empty
}

object TwhinUserEngagementEmbeddingsAdapter extends TwhinEmbeddingsAdapter {
  override val twhinEmbeddingsFeature: Feature.Tensor =
    TwhinEmbeddingsFeatures.TwhinUserEngagementEmbeddingsFeature

  override val commonFeatures: Set[Feature[_]] = Set(twhinEmbeddingsFeature)
}

object TwhinUserFollowEmbeddingsAdapter extends TwhinEmbeddingsAdapter {
  override val twhinEmbeddingsFeature: Feature.Tensor =
    TwhinEmbeddingsFeatures.TwhinUserFollowEmbeddingsFeature

  override val commonFeatures: Set[Feature[_]] = Set(twhinEmbeddingsFeature)
}
