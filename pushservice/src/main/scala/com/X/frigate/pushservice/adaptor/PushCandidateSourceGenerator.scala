package com.X.frigate.pushservice.adaptor

import com.X.content_mixer.thriftscala.ContentMixerRequest
import com.X.content_mixer.thriftscala.ContentMixerResponse
import com.X.explore_ranker.thriftscala.ExploreRankerRequest
import com.X.explore_ranker.thriftscala.ExploreRankerResponse
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base._
import com.X.frigate.common.candidate._
import com.X.frigate.common.store.RecentTweetsQuery
import com.X.frigate.common.store.interests.InterestsLookupRequestWithContext
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.store._
import com.X.geoduck.common.thriftscala.Location
import com.X.geoduck.service.thriftscala.LocationResponse
import com.X.hermit.pop_geo.thriftscala.PopTweetsInPlace
import com.X.hermit.predicate.socialgraph.RelationEdge
import com.X.hermit.store.tweetypie.UserTweet
import com.X.interests.thriftscala.UserInterests
import com.X.interests_discovery.thriftscala.NonPersonalizedRecommendedLists
import com.X.interests_discovery.thriftscala.RecommendedListsRequest
import com.X.interests_discovery.thriftscala.RecommendedListsResponse
import com.X.recommendation.interests.discovery.core.model.InterestDomain
import com.X.stitch.tweetypie.TweetyPie.TweetyPieResult
import com.X.storehaus.ReadableStore
import com.X.trends.trip_v1.trip_tweets.thriftscala.TripDomain
import com.X.trends.trip_v1.trip_tweets.thriftscala.TripTweets
import com.X.tsp.thriftscala.TopicSocialProofRequest
import com.X.tsp.thriftscala.TopicSocialProofResponse

/**
 * PushCandidateSourceGenerator generates candidate source list for a given Target user
 */
class PushCandidateSourceGenerator(
  earlybirdCandidates: CandidateSource[EarlybirdCandidateSource.Query, EarlybirdCandidate],
  userTweetEntityGraphCandidates: CandidateSource[UserTweetEntityGraphCandidates.Target, Candidate],
  cachedTweetyPieStoreV2: ReadableStore[Long, TweetyPieResult],
  safeCachedTweetyPieStoreV2: ReadableStore[Long, TweetyPieResult],
  userTweetTweetyPieStore: ReadableStore[UserTweet, TweetyPieResult],
  safeUserTweetTweetyPieStore: ReadableStore[UserTweet, TweetyPieResult],
  cachedTweetyPieStoreV2NoVF: ReadableStore[Long, TweetyPieResult],
  edgeStore: ReadableStore[RelationEdge, Boolean],
  interestsLookupStore: ReadableStore[InterestsLookupRequestWithContext, UserInterests],
  uttEntityHydrationStore: UttEntityHydrationStore,
  geoDuckV2Store: ReadableStore[Long, LocationResponse],
  topTweetsByGeoStore: ReadableStore[InterestDomain[String], Map[String, List[(Long, Double)]]],
  topTweetsByGeoV2VersionedStore: ReadableStore[String, PopTweetsInPlace],
  tweetImpressionsStore: TweetImpressionsStore,
  recommendedTrendsCandidateSource: RecommendedTrendsCandidateSource,
  recentTweetsByAuthorStore: ReadableStore[RecentTweetsQuery, Seq[Seq[Long]]],
  topicSocialProofServiceStore: ReadableStore[TopicSocialProofRequest, TopicSocialProofResponse],
  crMixerStore: CrMixerTweetStore,
  contentMixerStore: ReadableStore[ContentMixerRequest, ContentMixerResponse],
  exploreRankerStore: ReadableStore[ExploreRankerRequest, ExploreRankerResponse],
  softUserLocationStore: ReadableStore[Long, Location],
  tripTweetCandidateStore: ReadableStore[TripDomain, TripTweets],
  listRecsStore: ReadableStore[String, NonPersonalizedRecommendedLists],
  idsStore: ReadableStore[RecommendedListsRequest, RecommendedListsResponse]
)(
  implicit val globalStats: StatsReceiver) {

  private val earlyBirdFirstDegreeCandidateAdaptor = EarlyBirdFirstDegreeCandidateAdaptor(
    earlybirdCandidates,
    cachedTweetyPieStoreV2,
    cachedTweetyPieStoreV2NoVF,
    userTweetTweetyPieStore,
    PushFeatureSwitchParams.NumberOfMaxEarlybirdInNetworkCandidatesParam,
    globalStats
  )

  private val frsTweetCandidateAdaptor = FRSTweetCandidateAdaptor(
    crMixerStore,
    cachedTweetyPieStoreV2,
    cachedTweetyPieStoreV2NoVF,
    userTweetTweetyPieStore,
    uttEntityHydrationStore,
    topicSocialProofServiceStore,
    globalStats
  )

  private val contentRecommenderMixerAdaptor = ContentRecommenderMixerAdaptor(
    crMixerStore,
    safeCachedTweetyPieStoreV2,
    edgeStore,
    topicSocialProofServiceStore,
    uttEntityHydrationStore,
    globalStats
  )

  private val tripGeoCandidatesAdaptor = TripGeoCandidatesAdaptor(
    tripTweetCandidateStore,
    contentMixerStore,
    safeCachedTweetyPieStoreV2,
    cachedTweetyPieStoreV2NoVF,
    globalStats
  )

  val sources: Seq[
    CandidateSource[Target, RawCandidate] with CandidateSourceEligible[
      Target,
      RawCandidate
    ]
  ] = {
    Seq(
      earlyBirdFirstDegreeCandidateAdaptor,
      GenericCandidateAdaptor(
        userTweetEntityGraphCandidates,
        cachedTweetyPieStoreV2,
        cachedTweetyPieStoreV2NoVF,
        globalStats.scope("UserTweetEntityGraphCandidates")
      ),
      new OnboardingPushCandidateAdaptor(globalStats),
      TopTweetsByGeoAdaptor(
        geoDuckV2Store,
        softUserLocationStore,
        topTweetsByGeoStore,
        topTweetsByGeoV2VersionedStore,
        cachedTweetyPieStoreV2,
        cachedTweetyPieStoreV2NoVF,
        globalStats
      ),
      frsTweetCandidateAdaptor,
      TopTweetImpressionsCandidateAdaptor(
        recentTweetsByAuthorStore,
        cachedTweetyPieStoreV2,
        cachedTweetyPieStoreV2NoVF,
        tweetImpressionsStore,
        globalStats
      ),
      TrendsCandidatesAdaptor(
        softUserLocationStore,
        recommendedTrendsCandidateSource,
        safeCachedTweetyPieStoreV2,
        cachedTweetyPieStoreV2NoVF,
        safeUserTweetTweetyPieStore,
        globalStats
      ),
      contentRecommenderMixerAdaptor,
      tripGeoCandidatesAdaptor,
      HighQualityTweetsAdaptor(
        tripTweetCandidateStore,
        interestsLookupStore,
        cachedTweetyPieStoreV2,
        cachedTweetyPieStoreV2NoVF,
        globalStats
      ),
      ExploreVideoTweetCandidateAdaptor(
        exploreRankerStore,
        cachedTweetyPieStoreV2,
        globalStats
      ),
      ListsToRecommendCandidateAdaptor(
        listRecsStore,
        geoDuckV2Store,
        idsStore,
        globalStats
      )
    )
  }
}
