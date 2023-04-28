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
      extends FSBoundedParam[Long](
        name = "topic_tweets_semantic_core_annotation_version_id",
        default = 1433487161551032320L,
        min = 0L,
        max = Long.MaxValue
      )
  object TopicTweetsSemanticCoreVersionIdsSet
      extends FSParam[Set[Long]](
        name = "topic_tweets_semantic_core_annotation_version_id_allowed_set",
        default = Set(TopicTweetsSemanticCoreVersionId.default))

  /**
   * Controls the Topic Social Proof cosine similarity threshold for the Topic Tweets.
   */
  object TweetToTopicCosineSimilarityThreshold
      extends FSBoundedParam[Double](
        name = "topic_tweets_cosine_similarity_threshold_tsp",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  object EnablePersonalizedContextTopics // master feature switch to enable backfill
      extends FSParam[Boolean](
        name = "topic_tweets_personalized_contexts_enable_personalized_contexts",
        default = false
      )

  object EnableYouMightLikeTopic
      extends FSParam[Boolean](
        name = "topic_tweets_personalized_contexts_enable_you_might_like",
        default = false
      )

  object EnableRecentEngagementsTopic
      extends FSParam[Boolean](
        name = "topic_tweets_personalized_contexts_enable_recent_engagements",
        default = false
      )

  object EnableTopicTweetHealthFilterPersonalizedContexts
      extends FSParam[Boolean](
        name = "topic_tweets_personalized_contexts_health_switch",
        default = true
      )

  object EnableTweetToTopicScoreRanking
      extends FSParam[Boolean](
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
    TopicSocialProofParams.EnablePersonalizedContextTopics,
    TopicSocialProofParams.EnableYouMightLikeTopic,
    TopicSocialProofParams.EnableRecentEngagementsTopic,
    TopicSocialProofParams.EnableTopicTweetHealthFilterPersonalizedContexts,
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
