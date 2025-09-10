package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.twhin_embeddings

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

  val TwhinRebuildUserEngagementEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "user.twhin.tw_hi_n.rebuild_user_engagement_as_float_tensor",
    DataType.FLOAT
  )

  val TwhinUserFollowEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "user.twhin.tw_hi_n.user_follow_as_float_tensor",
    DataType.FLOAT
  )

  val TwhinUserPositiveEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "user.twhin.tw_hi_n.user_positive_as_float_tensor",
    DataType.FLOAT
  )

  val TwhinRebuildUserPositiveEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "user.twhin.tw_hi_n.rebuild_user_positive_as_float_tensor",
    DataType.FLOAT
  )

  val TwhinUserNegativeEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "user.twhin.tw_hi_n.user_negative_as_float_tensor",
    DataType.FLOAT
  )

  val TwhinTweetEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "original_tweet.twhin.tw_hi_n.tweet_v_2_as_float_tensor",
    DataType.FLOAT
  )

  val TwhinRebuildTweetEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "original_tweet.twhin.tw_hi_n.rebuild_tweet_as_float_tensor",
    DataType.FLOAT
  )

  val TwhinVideoEmbeddingsFeature: Feature.Tensor = new Feature.Tensor(
    "original_tweet.twhin.tw_hi_n.video_as_float_tensor",
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

object TwhinRebuildUserEngagementEmbeddingsAdapter extends TwhinEmbeddingsAdapter {
  override val twhinEmbeddingsFeature: Feature.Tensor =
    TwhinEmbeddingsFeatures.TwhinRebuildUserEngagementEmbeddingsFeature

  override val commonFeatures: Set[Feature[_]] = Set(twhinEmbeddingsFeature)
}

object TwhinUserFollowEmbeddingsAdapter extends TwhinEmbeddingsAdapter {
  override val twhinEmbeddingsFeature: Feature.Tensor =
    TwhinEmbeddingsFeatures.TwhinUserFollowEmbeddingsFeature

  override val commonFeatures: Set[Feature[_]] = Set(twhinEmbeddingsFeature)
}

object TwhinUserPositiveEmbeddingsAdapter extends TwhinEmbeddingsAdapter {
  override val twhinEmbeddingsFeature: Feature.Tensor =
    TwhinEmbeddingsFeatures.TwhinUserPositiveEmbeddingsFeature

  override val commonFeatures: Set[Feature[_]] = Set(twhinEmbeddingsFeature)
}

object TwhinRebuildUserPositiveEmbeddingsAdapter extends TwhinEmbeddingsAdapter {
  override val twhinEmbeddingsFeature: Feature.Tensor =
    TwhinEmbeddingsFeatures.TwhinRebuildUserPositiveEmbeddingsFeature

  override val commonFeatures: Set[Feature[_]] = Set(twhinEmbeddingsFeature)
}

object TwhinTweetEmbeddingsAdapter extends TwhinEmbeddingsAdapter {
  override val twhinEmbeddingsFeature: Feature.Tensor =
    TwhinEmbeddingsFeatures.TwhinTweetEmbeddingsFeature

  override val commonFeatures: Set[Feature[_]] = Set.empty
}

object TwhinRebuildTweetEmbeddingsAdapter extends TwhinEmbeddingsAdapter {
  override val twhinEmbeddingsFeature: Feature.Tensor =
    TwhinEmbeddingsFeatures.TwhinRebuildTweetEmbeddingsFeature

  override val commonFeatures: Set[Feature[_]] = Set.empty
}

object TwhinVideoEmbeddingsAdapter extends TwhinEmbeddingsAdapter {
  override val twhinEmbeddingsFeature: Feature.Tensor =
    TwhinEmbeddingsFeatures.TwhinVideoEmbeddingsFeature

  override val commonFeatures: Set[Feature[_]] = Set.empty
}

object TwhinUserNegativeEmbeddingsAdapter extends TwhinEmbeddingsAdapter {
  override val twhinEmbeddingsFeature: Feature.Tensor =
    TwhinEmbeddingsFeatures.TwhinUserNegativeEmbeddingsFeature

  override val commonFeatures: Set[Feature[_]] = Set(twhinEmbeddingsFeature)
}
