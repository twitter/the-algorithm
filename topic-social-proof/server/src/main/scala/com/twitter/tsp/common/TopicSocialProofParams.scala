package com.twitter.tsp.common

import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.logging.Logger
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil

object TopicSocialProofParams {

  object TopicTweetsSemanticCoreVersionId
      etwittertends FSBoundedParam[Long](
        name = "topic_tweets_semantic_core_annotation_version_id",
        default = 1433487161551032320L,
        min = 0L,
        matwitter = Long.MatwitterValue
      )
  object TopicTweetsSemanticCoreVersionIdsSet
      etwittertends FSParam[Set[Long]](
        name = "topic_tweets_semantic_core_annotation_version_id_allowed_set",
        default = Set(TopicTweetsSemanticCoreVersionId.default))

  /**
   * Controls the Topic Social Proof cosine similarity threshold for the Topic Tweets.
   */
  object TweetToTopicCosineSimilarityThreshold
      etwittertends FSBoundedParam[Double](
        name = "topic_tweets_cosine_similarity_threshold_tsp",
        default = 0.0,
        min = 0.0,
        matwitter = 1.0
      )

  object EnablePersonalizedContetwittertTopics // master feature switch to enable backfill
      etwittertends FSParam[Boolean](
        name = "topic_tweets_personalized_contetwitterts_enable_personalized_contetwitterts",
        default = false
      )

  object EnableYouMightLikeTopic
      etwittertends FSParam[Boolean](
        name = "topic_tweets_personalized_contetwitterts_enable_you_might_like",
        default = false
      )

  object EnableRecentEngagementsTopic
      etwittertends FSParam[Boolean](
        name = "topic_tweets_personalized_contetwitterts_enable_recent_engagements",
        default = false
      )

  object EnableTopicTweetHealthFilterPersonalizedContetwitterts
      etwittertends FSParam[Boolean](
        name = "topic_tweets_personalized_contetwitterts_health_switch",
        default = true
      )

  object EnableTweetToTopicScoreRanking
      etwittertends FSParam[Boolean](
        name = "topic_tweets_enable_tweet_to_topic_score_ranking",
        default = true
      )

}

object FeatureSwitchConfig {
  private val enumFeatureSwitchOverrides = FeatureSwitchOverrideUtil
    .getEnumFSOverrides(
      NullStatsReceiver,
      Logger(getClass),
    )

  private val intFeatureSwitchOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides()

  private val longFeatureSwitchOverrides = FeatureSwitchOverrideUtil.getBoundedLongFSOverrides(
    TopicSocialProofParams.TopicTweetsSemanticCoreVersionId
  )

  private val doubleFeatureSwitchOverrides = FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(
    TopicSocialProofParams.TweetToTopicCosineSimilarityThreshold,
  )

  private val longSetFeatureSwitchOverrides = FeatureSwitchOverrideUtil.getLongSetFSOverrides(
    TopicSocialProofParams.TopicTweetsSemanticCoreVersionIdsSet,
  )

  private val booleanFeatureSwitchOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
    TopicSocialProofParams.EnablePersonalizedContetwittertTopics,
    TopicSocialProofParams.EnableYouMightLikeTopic,
    TopicSocialProofParams.EnableRecentEngagementsTopic,
    TopicSocialProofParams.EnableTopicTweetHealthFilterPersonalizedContetwitterts,
    TopicSocialProofParams.EnableTweetToTopicScoreRanking,
  )
  val config: BaseConfig = BaseConfigBuilder()
    .set(enumFeatureSwitchOverrides: _*)
    .set(intFeatureSwitchOverrides: _*)
    .set(longFeatureSwitchOverrides: _*)
    .set(doubleFeatureSwitchOverrides: _*)
    .set(longSetFeatureSwitchOverrides: _*)
    .set(booleanFeatureSwitchOverrides: _*)
    .build()
}
