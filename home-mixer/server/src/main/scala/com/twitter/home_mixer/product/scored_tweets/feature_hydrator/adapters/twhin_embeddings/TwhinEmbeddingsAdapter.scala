package com.twitter.home_mixer.product.scored_tweets.feature_hydrator.adapters.twhin_embeddings

import com.twitter.ml.api.DataType
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.RichDataRecord
import com.twitter.ml.api.util.ScalaToJavaDataRecordConversions
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase

sealed trait TwhinEmbeddingsAdapter extends TimelinesMutatingAdapterBase[Option[ml.FloatTensor]] {
  def twhinEmbeddingsFeature: Feature.Tensor

  override def getFeatureContext: FeatureContext = new FeatureContext(
    twhinEmbeddingsFeature
  )

  override def setFeatures(
    embedding: Option[ml.FloatTensor],
    richDataRecord: RichDataRecord
  ): Unit = {
    embedding.foreach { floatTensor =>
      richDataRecord.setFeatureValue(
        twhinEmbeddingsFeature,
        ScalaToJavaDataRecordConversions.scalaTensor2Java(
          ml.GeneralTensor
            .FloatTensor(floatTensor)))
    }
  }
}

object TwhinEmbeddingsFeatures {
  val TwhinAuthorFollowEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "original_author.twhin.tw_hi_n.author_follow_as_float_tensor",
    DataType.FLOAT
  )

  val TwhinUserEngagementEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "user.twhin.tw_hi_n.user_engagement_as_float_tensor",
    DataType.FLOAT
  )

  val TwhinUserFollowEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "user.twhin.tw_hi_n.user_follow_as_float_tensor",
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
