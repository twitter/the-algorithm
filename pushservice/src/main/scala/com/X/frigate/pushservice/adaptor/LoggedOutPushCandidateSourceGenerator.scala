package com.X.frigate.pushservice.adaptor

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.CandidateSource
import com.X.frigate.common.base.CandidateSourceEligible
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.geoduck.service.thriftscala.LocationResponse
import com.X.stitch.tweetypie.TweetyPie.TweetyPieResult
import com.X.storehaus.ReadableStore
import com.X.trends.trip_v1.trip_tweets.thriftscala.TripDomain
import com.X.trends.trip_v1.trip_tweets.thriftscala.TripTweets
import com.X.content_mixer.thriftscala.ContentMixerRequest
import com.X.content_mixer.thriftscala.ContentMixerResponse
import com.X.geoduck.common.thriftscala.Location
import com.X.hermit.pop_geo.thriftscala.PopTweetsInPlace
import com.X.recommendation.interests.discovery.core.model.InterestDomain

class LoggedOutPushCandidateSourceGenerator(
  tripTweetCandidateStore: ReadableStore[TripDomain, TripTweets],
  geoDuckV2Store: ReadableStore[Long, LocationResponse],
  safeCachedTweetyPieStoreV2: ReadableStore[Long, TweetyPieResult],
  cachedTweetyPieStoreV2NoVF: ReadableStore[Long, TweetyPieResult],
  cachedTweetyPieStoreV2: ReadableStore[Long, TweetyPieResult],
  contentMixerStore: ReadableStore[ContentMixerRequest, ContentMixerResponse],
  softUserLocationStore: ReadableStore[Long, Location],
  topTweetsByGeoStore: ReadableStore[InterestDomain[String], Map[String, List[(Long, Double)]]],
  topTweetsByGeoV2VersionedStore: ReadableStore[String, PopTweetsInPlace],
)(
  implicit val globalStats: StatsReceiver) {
  val sources: Seq[CandidateSource[Target, RawCandidate] with CandidateSourceEligible[
    Target,
    RawCandidate
  ]] = {
    Seq(
      TripGeoCandidatesAdaptor(
        tripTweetCandidateStore,
        contentMixerStore,
        safeCachedTweetyPieStoreV2,
        cachedTweetyPieStoreV2NoVF,
        globalStats
      ),
      TopTweetsByGeoAdaptor(
        geoDuckV2Store,
        softUserLocationStore,
        topTweetsByGeoStore,
        topTweetsByGeoV2VersionedStore,
        cachedTweetyPieStoreV2,
        cachedTweetyPieStoreV2NoVF,
        globalStats
      )
    )
  }
}
