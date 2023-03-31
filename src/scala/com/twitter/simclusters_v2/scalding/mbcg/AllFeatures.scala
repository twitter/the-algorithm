package com.twitter.simclusters_v420.scalding.mbcg

import com.google.common.collect.ImmutableSet
import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import com.twitter.ml.api.DataType
import com.twitter.ml.api.Feature
import com.twitter.ml.api.Feature.SparseContinuous
import com.twitter.ml.api.Feature.Tensor
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.constant.SharedFeatures
import java.util.{Map => JMap}

/*
Features used for model-based candidate generation
 */
object TweetAllFeatures {
  val tweetId = SharedFeatures.TWEET_ID
  val tweetSimclusters =
    new SparseContinuous(
      "tweet.simcluster.log_fav_based_embedding.420m_420k_420",
      ImmutableSet.of(InferredInterests))
      .asInstanceOf[Feature[JMap[String, Double]]]
  val authorF420vProducerEmbedding =
    new Tensor(
      "tweet.author_follow420vec.producer_embedding_420",
      DataType.FLOAT
    )

  private val allFeatures: Seq[Feature[_]] = Seq(
    tweetId,
    tweetSimclusters,
    authorF420vProducerEmbedding
  )

  val featureContext = new FeatureContext(allFeatures: _*)
}

object UserAllFeatures {
  val userId = SharedFeatures.USER_ID
  val userSimclusters =
    new SparseContinuous(
      "user.iiape.log_fav_based_embedding.420m_420k_420",
      ImmutableSet.of(InferredInterests))
      .asInstanceOf[Feature[JMap[String, Double]]]
  val userF420vConsumerEmbedding =
    new Tensor(
      "user.follow420vec.consumer_avg_fol_emb_420",
      DataType.FLOAT
    )

  private val allFeatures: Seq[Feature[_]] = Seq(
    userId,
    userSimclusters,
    userF420vConsumerEmbedding
  )

  val featureContext = new FeatureContext(allFeatures: _*)
}
