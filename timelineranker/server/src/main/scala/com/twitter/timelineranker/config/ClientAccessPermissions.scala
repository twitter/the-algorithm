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
     * Production clients for timelinemixer.
     */
    new ClientDetails(
      "timelinemixer.recap.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerRecapProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.recycled.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerRecycledProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.hydrate.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerHydrateProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.hydrate_recos.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerHydrateRecosProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.seed_author_ids.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerSeedAuthorsProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.simcluster.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerSimclusterProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.entity_tweets.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerEntityTweetsProd),
      protectedWriteAccess = TrustedPermission
    ),
    /**
     * This client is whitelisted for timelinemixer only as it used by
     * List injection service which will not be migrated to timelinescorer.
     */
    new ClientDetails(
      "timelinemixer.list.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerListProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.list_tweet.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerListTweetProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.community.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerCommunityProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.community_tweet.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerCommunityTweetProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.uteg_liked_by_tweets.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerUtegLikedByTweetsProd),
      protectedWriteAccess = TrustedPermission
    ),
    /**
     * Production clients for timelinescorer. Most of these clients have their
     * equivalents under the timelinemixer scope (with exception of list injection
     * client).
     */
    new ClientDetails(
      "timelinescorer.recap.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerRecapProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.recycled.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerRecycledProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.hydrate.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerHydrateProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.hydrate_recos.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerHydrateRecosProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.seed_author_ids.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerSeedAuthorsProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.simcluster.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerSimclusterProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.entity_tweets.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerEntityTweetsProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.list_tweet.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerListTweetProd),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.uteg_liked_by_tweets.prod",
      RateLimitingTrustedPermission(AllowTimelineMixerUtegLikedByTweetsProd),
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
      RateLimitingTrustedPermission(AllowTimelineMixerCommunityTweetProd),
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
     * TimelineRanker utilities. Traffic proxy, warmups, and console.
     */
    new ClientDetails(
      "timelineranker.proxy",
      RateLimitingTrustedPermission(AllowTimelineRankerProxy),
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
      "timelinemixer.recap.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.recycled.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.hydrate.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.hydrate_recos.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.seed_author_ids.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.simcluster.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.entity_tweets.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.list.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.list_tweet.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.community.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.community_tweet.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.community_tweet.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.recommended_trend_tweet.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.uteg_liked_by_tweets.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinemixer.entity_tweets.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.hydrate_tweet_scoring.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.rec_topic_tweets.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelinescorer.popular_topic_tweets.staging",
      RateLimitingTrustedPermission(AllowTimelineMixerStaging),
      protectedWriteAccess = TrustedPermission
    ),
    new ClientDetails(
      "timelineservice.staging",
      RateLimitingTrustedPermission(AllowTimelineServiceStaging),
      protectedWriteAccess = TrustedPermission
    )
  )
}
