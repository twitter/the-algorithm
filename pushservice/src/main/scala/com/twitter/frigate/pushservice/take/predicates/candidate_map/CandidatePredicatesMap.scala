package com.twitter.frigate.pushservice.take.predicates.candidate_map

import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.model._
import com.twitter.frigate.pushservice.config.Config
import com.twitter.frigate.thriftscala.CommonRecommendationType
import com.twitter.frigate.thriftscala.CommonRecommendationType._
import com.twitter.hermit.predicate.NamedPredicate

object CandidatePredicatesMap {

  def apply(
    implicit config: Config
  ): Map[CommonRecommendationType, List[NamedPredicate[_ <: PushCandidate]]] = {

    val trendTweetCandidatePredicates = TrendTweetPredicates(config).predicates
    val tripTweetCandidatePredicates = TripTweetCandidatePredicates(config).predicates
    val f1TweetCandidatePredicates = F1TweetCandidatePredicates(config).predicates
    val oonTweetCandidatePredicates = OutOfNetworkTweetCandidatePredicates(config).predicates
    val tweetActionCandidatePredicates = TweetActionCandidatePredicates(config).predicates
    val topicProofTweetCandidatePredicates = TopicProofTweetCandidatePredicates(config).predicates
    val addressBookPushPredicates = AddressBookPushCandidatePredicates(config).predicates
    val completeOnboardingPushPredicates = CompleteOnboardingPushCandidatePredicates(
      config).predicates
    val popGeoTweetCandidatePredicate = PopGeoTweetCandidatePredicates(config).predicates
    val topTweetImpressionsCandidatePredicates = TopTweetImpressionsPushCandidatePredicates(
      config).predicates
    val listCandidatePredicates = ListRecommendationPredicates(config).predicates
    val subscribedSearchTweetCandidatePredicates = SubscribedSearchTweetCandidatePredicates(
      config).predicates

    Map(
      F1FirstdegreeTweet -> f1TweetCandidatePredicates,
      F1FirstdegreePhoto -> f1TweetCandidatePredicates,
      F1FirstdegreeVideo -> f1TweetCandidatePredicates,
      ElasticTimelineTweet -> oonTweetCandidatePredicates,
      ElasticTimelinePhoto -> oonTweetCandidatePredicates,
      ElasticTimelineVideo -> oonTweetCandidatePredicates,
      TwistlyTweet -> oonTweetCandidatePredicates,
      TwistlyPhoto -> oonTweetCandidatePredicates,
      TwistlyVideo -> oonTweetCandidatePredicates,
      ExploreVideoTweet -> oonTweetCandidatePredicates,
      UserInterestinTweet -> oonTweetCandidatePredicates,
      UserInterestinPhoto -> oonTweetCandidatePredicates,
      UserInterestinVideo -> oonTweetCandidatePredicates,
      PastEmailEngagementTweet -> oonTweetCandidatePredicates,
      PastEmailEngagementPhoto -> oonTweetCandidatePredicates,
      PastEmailEngagementVideo -> oonTweetCandidatePredicates,
      TagSpaceTweet -> oonTweetCandidatePredicates,
      TwhinTweet -> oonTweetCandidatePredicates,
      FrsTweet -> oonTweetCandidatePredicates,
      MrModelingBasedTweet -> oonTweetCandidatePredicates,
      TrendTweet -> trendTweetCandidatePredicates,
      ReverseAddressbookTweet -> oonTweetCandidatePredicates,
      ForwardAddressbookTweet -> oonTweetCandidatePredicates,
      TripGeoTweet -> oonTweetCandidatePredicates,
      TripHqTweet -> tripTweetCandidatePredicates,
      DetopicTweet -> oonTweetCandidatePredicates,
      CrowdSearchTweet -> oonTweetCandidatePredicates,
      TweetFavorite -> tweetActionCandidatePredicates,
      TweetFavoritePhoto -> tweetActionCandidatePredicates,
      TweetFavoriteVideo -> tweetActionCandidatePredicates,
      TweetRetweet -> tweetActionCandidatePredicates,
      TweetRetweetPhoto -> tweetActionCandidatePredicates,
      TweetRetweetVideo -> tweetActionCandidatePredicates,
      TopicProofTweet -> topicProofTweetCandidatePredicates,
      SubscribedSearch -> subscribedSearchTweetCandidatePredicates,
      AddressBookUploadPush -> addressBookPushPredicates,
      CompleteOnboardingPush -> completeOnboardingPushPredicates,
      List -> listCandidatePredicates,
      GeoPopTweet -> popGeoTweetCandidatePredicate,
      TweetImpressions -> topTweetImpressionsCandidatePredicates
    )
  }
}
