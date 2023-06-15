package com.twitter.timelines.prediction.features.client_log_event

import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import com.twitter.ml.api.Feature
import com.twitter.ml.api.Feature.Binary
import com.twitter.ml.api.Feature.Continuous
import com.twitter.ml.api.Feature.Discrete
import scala.collection.JavaConverters._
import com.twitter.timelineservice.suggests.logging.candidate_tweet_source_id.thriftscala.CandidateTweetSourceId

object ClientLogEventDataRecordFeatures {
  val HasConsumerVideo = new Binary(
    "client_log_event.tweet.has_consumer_video",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val PhotoCount = new Continuous(
    "client_log_event.tweet.photo_count",
    Set(CountOfPrivateTweetEntitiesAndMetadata, CountOfPublicTweetEntitiesAndMetadata).asJava)
  val HasImage = new Binary(
    "client_log_event.tweet.has_image",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val IsReply =
    new Binary("client_log_event.tweet.is_reply", Set(PublicReplies, PrivateReplies).asJava)
  val IsRetweet =
    new Binary("client_log_event.tweet.is_retweet", Set(PublicRetweets, PrivateRetweets).asJava)
  val IsPromoted =
    new Binary(
      "client_log_event.tweet.is_promoted",
      Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HasVisibleLink = new Binary(
    "client_log_event.tweet.has_visible_link",
    Set(UrlFoundFlag, PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HasHashtag = new Binary(
    "client_log_event.tweet.has_hashtag",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val FromMutualFollow = new Binary("client_log_event.tweet.from_mutual_follow")
  val IsInNetwork = new Binary("client_log_event.tweet.is_in_network")
  val IsNotInNetwork = new Binary("client_log_event.tweet.is_not_in_network")
  val FromRecap = new Binary("client_log_event.tweet.from_recap")
  val FromRecycled = new Binary("client_log_event.tweet.from_recycled")
  val FromActivity = new Binary("client_log_event.tweet.from_activity")
  val FromSimcluster = new Binary("client_log_event.tweet.from_simcluster")
  val FromErg = new Binary("client_log_event.tweet.from_erg")
  val FromCroon = new Binary("client_log_event.tweet.from_croon")
  val FromList = new Binary("client_log_event.tweet.from_list")
  val FromRecTopic = new Binary("client_log_event.tweet.from_rec_topic")
  val InjectedPosition = new Discrete("client_log_event.tweet.injectedPosition")
  val TextOnly = new Binary("client_log_event.tweet.text_only")
  val HasLikedBySocialContext = new Binary("client_log_event.tweet.has_liked_by_social_context")
  val HasFollowedBySocialContext = new Binary(
    "client_log_event.tweet.has_followed_by_social_context")
  val HasTopicSocialContext = new Binary("client_log_event.tweet.has_topic_social_context")
  val IsFollowedTopicTweet = new Binary("client_log_event.tweet.is_followed_topic_tweet")
  val IsRecommendedTopicTweet = new Binary("client_log_event.tweet.is_recommended_topic_tweet")
  val IsTweetAgeLessThan15Seconds = new Binary(
    "client_log_event.tweet.tweet_age_less_than_15_seconds")
  val IsTweetAgeLessThanOrEqualTo30Minutes = new Binary(
    "client_log_event.tweet.tweet_age_lte_30_minutes")
  val IsTweetAgeLessThanOrEqualTo1Hour = new Binary("client_log_event.tweet.tweet_age_lte_1_hour")
  val IsTweetAgeLessThanOrEqualTo6Hours = new Binary("client_log_event.tweet.tweet_age_lte_6_hours")
  val IsTweetAgeLessThanOrEqualTo12Hours = new Binary(
    "client_log_event.tweet.tweet_age_lte_12_hours")
  val IsTweetAgeGreaterThanOrEqualTo24Hours = new Binary(
    "client_log_event.tweet.tweet_age_gte_24_hours")
  val HasGreaterThanOrEqualTo100Favs = new Binary("client_log_event.tweet.has_gte_100_favs")
  val HasGreaterThanOrEqualTo1KFavs = new Binary("client_log_event.tweet.has_gte_1k_favs")
  val HasGreaterThanOrEqualTo10KFavs = new Binary("client_log_event.tweet.has_gte_10k_favs")
  val HasGreaterThanOrEqualTo100KFavs = new Binary("client_log_event.tweet.has_gte_100k_favs")
  val HasGreaterThanOrEqualTo10Retweets = new Binary("client_log_event.tweet.has_gte_10_retweets")
  val HasGreaterThanOrEqualTo100Retweets = new Binary("client_log_event.tweet.has_gte_100_retweets")
  val HasGreaterThanOrEqualTo1KRetweets = new Binary("client_log_event.tweet.has_gte_1k_retweets")

  val TweetTypeToFeatureMap: Map[String, Binary] = Map(
    "link" -> HasVisibleLink,
    "hashtag" -> HasHashtag,
    "mutual_follow" -> FromMutualFollow,
    "in_network" -> IsInNetwork,
    "text_only" -> TextOnly,
    "has_liked_by_social_context" -> HasLikedBySocialContext,
    "has_followed_by_social_context" -> HasFollowedBySocialContext,
    "has_topic_social_context" -> HasTopicSocialContext,
    "is_followed_topic_tweet" -> IsFollowedTopicTweet,
    "is_recommended_topic_tweet" -> IsRecommendedTopicTweet,
    "tweet_age_less_than_15_seconds" -> IsTweetAgeLessThan15Seconds,
    "tweet_age_lte_30_minutes" -> IsTweetAgeLessThanOrEqualTo30Minutes,
    "tweet_age_lte_1_hour" -> IsTweetAgeLessThanOrEqualTo1Hour,
    "tweet_age_lte_6_hours" -> IsTweetAgeLessThanOrEqualTo6Hours,
    "tweet_age_lte_12_hours" -> IsTweetAgeLessThanOrEqualTo12Hours,
    "tweet_age_gte_24_hours" -> IsTweetAgeGreaterThanOrEqualTo24Hours,
    "has_gte_100_favs" -> HasGreaterThanOrEqualTo100Favs,
    "has_gte_1k_favs" -> HasGreaterThanOrEqualTo1KFavs,
    "has_gte_10k_favs" -> HasGreaterThanOrEqualTo10KFavs,
    "has_gte_100k_favs" -> HasGreaterThanOrEqualTo100KFavs,
    "has_gte_10_retweets" -> HasGreaterThanOrEqualTo10Retweets,
    "has_gte_100_retweets" -> HasGreaterThanOrEqualTo100Retweets,
    "has_gte_1k_retweets" -> HasGreaterThanOrEqualTo1KRetweets
  )

  val CandidateTweetSourceIdFeatureMap: Map[Int, Binary] = Map(
    CandidateTweetSourceId.RecapTweet.value -> FromRecap,
    CandidateTweetSourceId.RecycledTweet.value -> FromRecycled,
    CandidateTweetSourceId.RecommendedTweet.value -> FromActivity,
    CandidateTweetSourceId.Simcluster.value -> FromSimcluster,
    CandidateTweetSourceId.ErgTweet.value -> FromErg,
    CandidateTweetSourceId.CroonTopicTweet.value -> FromCroon,
    CandidateTweetSourceId.CroonTweet.value -> FromCroon,
    CandidateTweetSourceId.ListTweet.value -> FromList,
    CandidateTweetSourceId.RecommendedTopicTweet.value -> FromRecTopic
  )

  val TweetFeaturesV2: Set[Feature[_]] = Set(
    HasImage,
    IsReply,
    IsRetweet,
    HasVisibleLink,
    HasHashtag,
    FromMutualFollow,
    IsInNetwork
  )

  val ContentTweetTypeFeatures: Set[Feature[_]] = Set(
    HasImage,
    HasVisibleLink,
    HasHashtag,
    TextOnly,
    HasVisibleLink
  )

  val FreshnessTweetTypeFeatures: Set[Feature[_]] = Set(
    IsTweetAgeLessThan15Seconds,
    IsTweetAgeLessThanOrEqualTo30Minutes,
    IsTweetAgeLessThanOrEqualTo1Hour,
    IsTweetAgeLessThanOrEqualTo6Hours,
    IsTweetAgeLessThanOrEqualTo12Hours,
    IsTweetAgeGreaterThanOrEqualTo24Hours
  )

  val SocialProofTweetTypeFeatures: Set[Feature[_]] = Set(
    HasLikedBySocialContext,
    HasFollowedBySocialContext,
    HasTopicSocialContext
  )

  val TopicTweetPreferenceTweetTypeFeatures: Set[Feature[_]] = Set(
    IsFollowedTopicTweet,
    IsRecommendedTopicTweet
  )

  val TweetPopularityTweetTypeFeatures: Set[Feature[_]] = Set(
    HasGreaterThanOrEqualTo100Favs,
    HasGreaterThanOrEqualTo1KFavs,
    HasGreaterThanOrEqualTo10KFavs,
    HasGreaterThanOrEqualTo100KFavs,
    HasGreaterThanOrEqualTo10Retweets,
    HasGreaterThanOrEqualTo100Retweets,
    HasGreaterThanOrEqualTo1KRetweets
  )

  val UserGraphInteractionTweetTypeFeatures: Set[Feature[_]] = Set(
    IsInNetwork,
    FromMutualFollow,
    IsNotInNetwork,
    IsPromoted
  )

  val UserContentPreferenceTweetTypeFeatures: Set[Feature[_]] =
    ContentTweetTypeFeatures ++ FreshnessTweetTypeFeatures ++ SocialProofTweetTypeFeatures ++ TopicTweetPreferenceTweetTypeFeatures ++ TweetPopularityTweetTypeFeatures ++ UserGraphInteractionTweetTypeFeatures
  val AuthorContentPreferenceTweetTypeFeatures: Set[Feature[_]] =
    Set(IsInNetwork, FromMutualFollow, IsNotInNetwork) ++ ContentTweetTypeFeatures
}
