package com.twitter.simclusters_v2.scalding.mbcg

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
      "tweet.simcluster.log_fav_based_embedding.20m_145k_2020",
      ImmutableSet.of(InferredInterests))
      .asInstanceOf[Feature[JMap[String, Double]]]
  val authorF2vProducerEmbedding =
    new Tensor(
      "tweet.author_follow2vec.producer_embedding_200",
      DataType.FLOAT
    )

  private val allFeatures: Seq[Feature[_]] = Seq(
    tweetId,
    tweetSimclusters,
    authorF2vProducerEmbedding
  )

  val featureContext = new FeatureContext(allFeatures: _*)
}

object UserAllFeatures {
  val userId = SharedFeatures.USER_ID
  val userSimclusters =
    new SparseContinuous(
      "user.iiape.log_fav_based_embedding.20m_145k_2020",
      ImmutableSet.of(InferredInterests))
      .asInstanceOf[Feature[JMap[String, Double]]]
  val userF2vConsumerEmbedding =
    new Tensor(
      "user.follow2vec.consumer_avg_fol_emb_200",
      DataType.FLOAT
    )

  private val allFeatures: Seq[Feature[_]] = Seq(
    userId,
    userSimclusters,
    userF2vConsumerEmbedding
  )

  val featureContext = new FeatureContext(allFeatures: _*)
}
