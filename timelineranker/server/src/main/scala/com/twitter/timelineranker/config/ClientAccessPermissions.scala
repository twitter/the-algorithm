package com.twitter.timelineranker.config

import com.twitter.timelineranker.decider.DeciderKey._
import com.twitter.timelines.authorization.TrustedPermission
import com.twitter.timelines.authorization.RateLimitingTrustedPermission
import com.twitter.timelines.authorization.RateLimitingUntrustedPermission
import com.twitter.timelines.authorization.ClientDetails

object ClientAccessPermissions {
  // We want timelineranker locked down for requests outside of what's defined here.
  val DefaultRateLimit = 0d

  def unknown(name: String): ClientDetails = {
    ClientDetails(name, RateLimitingUntrustedPermission(RateLimitOverrideUnknown, DefaultRateLimit))
  }

  val All: Seq[ClientDetails] = Seq(
    /**
     * Production clients for timelinemitwitterer.
     */
    new ClientDetails(
      "timelinemitwitterer.recap.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererRecapProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.recycled.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererRecycledProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.hydrate.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererHydrateProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.hydrate_recos.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererHydrateRecosProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.seed_author_ids.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererSeedAuthorsProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.simcluster.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererSimclusterProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.entity_tweets.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererEntityTweetsProd),
      protectedWriteAccess = TrustedPermission
    ),
    /**
     * This client is whitelisted for timelinemitwitterer only as it used by
     * List injection service which will not be migrated to timelinescorer.
     */
    new ClientDetails(
      "timelinemitwitterer.list.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererListProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.list_tweet.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererListTweetProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.community.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererCommunityProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.community_tweet.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererCommunityTweetProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.uteg_liked_by_tweets.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererUtegLikedByTweetsProd),
      protectedWriteAccess = TrustedPermission
    ),
    /**
     * Production clients for timelinescorer. Most of these clients have their
     * equivalents under the timelinemitwitterer scope (with etwitterception of list injection
     * client).
     */
    new ClientDetails(
      "timelinescorer.recap.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererRecapProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.recycled.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererRecycledProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.hydrate.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererHydrateProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.hydrate_recos.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererHydrateRecosProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.seed_author_ids.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererSeedAuthorsProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.simcluster.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererSimclusterProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.entity_tweets.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererEntityTweetsProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.list_tweet.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererListTweetProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.uteg_liked_by_tweets.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererUtegLikedByTweetsProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelineservice.prod",
      RateLimitingTrustedPermission(AllowTimelineServiceProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.hydrate_tweet_scoring.prod",
      RateLimitingTrustedPermission(AllowTimelineScorerHydrateTweetScoringProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.community_tweet.prod",
      RateLimitingTrustedPermission(AllowTimelineMitwittererCommunityTweetProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.recommended_trend_tweet.prod",
      RateLimitingTrustedPermission(AllowTimelineScorerRecommendedTrendTweetProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.rec_topic_tweets.prod",
      RateLimitingTrustedPermission(AllowTimelineScorerRecTopicTweetsProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.popular_topic_tweets.prod",
      RateLimitingTrustedPermission(AllowTimelineScorerPopularTopicTweetsProd),
      protectedWriteAccess = TrustedPermission
    ),
    /**
     * TimelineRanker utilities. Traffic protwittery, warmups, and console.
     */
    new ClientDetails(
      "timelineranker.protwittery",
      RateLimitingTrustedPermission(AllowTimelineRankerProtwittery),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      TimelineRankerConstants.WarmupClientName,
      RateLimitingTrustedPermission(AllowTimelineRankerWarmup),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      TimelineRankerConstants.ForwardedClientName,
      RateLimitingTrustedPermission(AllowTimelineRankerWarmup),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelineranker.console",
      RateLimitingUntrustedPermission(RateLimitOverrideUnknown, 1d),
      protectedWriteAccess = TrustedPermission
    ),
    /**
     * Staging clients.
     */
    new ClientDetails(
      "timelinemitwitterer.recap.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.recycled.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.hydrate.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.hydrate_recos.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.seed_author_ids.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.simcluster.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.entity_tweets.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.list.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.list_tweet.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.community.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.community_tweet.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.community_tweet.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.recommended_trend_tweet.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.uteg_liked_by_tweets.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemitwitterer.entity_tweets.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.hydrate_tweet_scoring.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.rec_topic_tweets.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.popular_topic_tweets.staging",
      RateLimitingTrustedPermission(AllowTimelineMitwittererStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelineservice.staging",
      RateLimitingTrustedPermission(AllowTimelineServiceStaging),
      protectedWriteAccess = TrustedPermission
    )
  )
}
