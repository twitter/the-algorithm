package com.twitter.frigate.pushservice.adaptor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.base.CandidateSource
import com.twitter.frigate.common.base.CandidateSourceEligible
import com.twitter.frigate.pushservice.model.PushTypes.RawCandidate
import com.twitter.frigate.pushservice.model.PushTypes.Target
import com.twitter.geoduck.service.thriftscala.LocationResponse
import com.twitter.stitch.tweetypie.TweetyPie.TweetyPieResult
import com.twitter.storehaus.ReadableStore
import com.twitter.trends.trip_v1.trip_tweets.thriftscala.TripDomain
import com.twitter.trends.trip_v1.trip_tweets.thriftscala.TripTweets
import com.twitter.content_mixer.thriftscala.ContentMixerRequest
import com.twitter.content_mixer.thriftscala.ContentMixerResponse
import com.twitter.geoduck.common.thriftscala.Location
import com.twitter.hermit.pop_geo.thriftscala.PopTweetsInPlace
import com.twitter.recommendation.interests.discovery.core.model.InterestDomain

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
