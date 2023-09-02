package com.twitter.timelines.prediction.features.simcluster

import com.twitter.dal.personal_data.thriftjava.PersonalDataType.SemanticcoreClassification
import com.twitter.ml.api.Feature
import com.twitter.ml.api.Feature.Continuous
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.conversion.CombineCountsBase
import scala.collection.JavaConverters._

object SimclustersScoresFeatures extends CombineCountsBase {
  override def topK: Int = 2

  override def hardLimit: Option[Int] = Some(20)

  val prefix = s"recommendations.sim_clusters_scores"
  val TOPIC_CONSUMER_TWEET_EMBEDDING_Cs = new Continuous(
    s"$prefix.localized_topic_consumer_tweet_embedding_cosine_similarity",
    Set(SemanticcoreClassification).asJava)
  val TOPIC_PRODUCER_TWEET_EMBEDDING_Cs = new Continuous(
    s"$prefix.topic_producer_tweet_embedding_cosine_similarity",
    Set(SemanticcoreClassification).asJava)
  val USER_TOPIC_CONSUMER_TWEET_EMBEDDING_COSINE_SIM = new Continuous(
    s"$prefix.user_interested_in_localized_topic_consumer_embedding_cosine_similarity",
    Set(SemanticcoreClassification).asJava)
  val USER_TOPIC_CONSUMER_TWEET_EMBEDDING_DOT_PRODUCT = new Continuous(
    s"$prefix.user_interested_in_localized_topic_consumer_embedding_dot_product",
    Set(SemanticcoreClassification).asJava)
  val USER_TOPIC_PRODUCER_TWEET_EMBEDDING_COSINE_SIM = new Continuous(
    s"$prefix.user_interested_in_localized_topic_producer_embedding_cosine_similarity",
    Set(SemanticcoreClassification).asJava)
  val USER_TOPIC_PRODUCER_TWEET_EMBEDDING_DOT_PRODUCT = new Continuous(
    s"$prefix.user_interested_in_localized_topic_producer_embedding_dot_product",
    Set(SemanticcoreClassification).asJava)

  override def precomputedCountFeatures: Seq[Feature[_]] =
    Seq(
      TOPIC_CONSUMER_TWEET_EMBEDDING_Cs,
      TOPIC_PRODUCER_TWEET_EMBEDDING_Cs,
      USER_TOPIC_CONSUMER_TWEET_EMBEDDING_COSINE_SIM,
      USER_TOPIC_CONSUMER_TWEET_EMBEDDING_DOT_PRODUCT,
      USER_TOPIC_PRODUCER_TWEET_EMBEDDING_COSINE_SIM,
      USER_TOPIC_PRODUCER_TWEET_EMBEDDING_DOT_PRODUCT
    )
}
